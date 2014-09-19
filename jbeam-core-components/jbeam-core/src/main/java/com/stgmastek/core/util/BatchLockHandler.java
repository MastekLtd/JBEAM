/*
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.core.util;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchInProgressException;
import com.stgmastek.core.util.Constants.BATCH_TYPE;
import com.stgmastek.core.util.Constants.PROCESS_REQUEST_PARAMS;


/**
 * 
 * @author shantanuc
 *
 */
public class BatchLockHandler {

	private static final Logger logger = Logger.getLogger(BatchLockHandler.class);
	
	/**
	 * @param batchContext
	 * @return
	 * @throws BatchException
	 */
	public synchronized static boolean lockProcessor(BatchContext batchContext) throws BatchException{
		try {
			Map<String, Object> processReqParamMap = getProcessRequestParams(batchContext);
			
			printMap(processReqParamMap);
			
			boolean bProcessorLocked = isProcessorLocked();
			//Is the new request for a DATE run
			if(isDateRun(processReqParamMap))
			{
				if (bProcessorLocked) {
					throw new BatchInProgressException("Another batch(DATE/SPECIAL) running. Cannot run Date batch concurrently with it.");
				} else {
					return lockProcessor(batchContext, processReqParamMap);
				}
			} else {
				System.out.println("Special Run ");
				//new request is for a SPECIAL run
				if (!bProcessorLocked) {
					if(logger.isDebugEnabled()){
						logger.debug("Processor Not locked. Can lock processor for this batch.");
					}
					return lockProcessor(batchContext, processReqParamMap);
				} else {
					//If the processor is locked for DATE run, no other batch can execute
					if (isCurrentDateRun()) {
						//Batch already locked for a DATE run
						throw new BatchInProgressException("Batch already locked for a DATE run. Cannot run any other batch with it.");
					} else {
						if(logger.isDebugEnabled()){
							logger.debug("Batch is locked for a SPECIAL run.");
						}
						//Batch is locked for a SPECIAL run
						if (isValidForConcurrentExecution(processReqParamMap)) {
							if(logger.isDebugEnabled()){
								logger.debug("Valid for concurrent run.");
							}
							return lockProcessor(batchContext, processReqParamMap);
						} else {
							if(logger.isDebugEnabled()){
								logger.debug("In-Valid for concurrent run.");
							}
							return false;
						}
					}
				}
			}
		} catch (BatchInProgressException e) {
			throw e;
		} catch (Exception e) {
			throw new BatchException(e);
		}
	}
	
	private static void printMap(Map<String, Object> processReqParamMap) {
		for(Entry<String, Object> entry : processReqParamMap.entrySet()){
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	/**
	 * Releases processor lock
	 *  
	 * @throws BatchException 
	 */
	public synchronized static boolean releaseProcessorLock(BatchContext batchContext) throws BatchException{
		Long requestId = batchContext.getRequestParams().getRequestId();

		BatchLockMonitor.removeLockingEntry(requestId);
		if(!BatchLockMonitor.hasMoreLockingEntries()){
			IBatchDao bDao = DaoFactory.getBatchDao();
			Connection batchConn = batchContext.getBATCHConnection();
			
			return bDao.unlockProcessor(requestId, batchConn);
		}
		return true;
	}

	/**
	 * Verifies whether the processor is locked by inspecting BatchLockMonitor
	 * entries
	 * 
	 * @return
	 */
	private synchronized static boolean isProcessorLocked(){
		return !(BatchLockMonitor.getLockingEntries().isEmpty());
	}

	/**
	 * This method locks the processor by marking it as locked in the database
	 * as well as making an entry in the BatchLockMonitor
	 * 
	 * @param batchContext
	 * @param processReqParamMap
	 * @return
	 * @throws BatchException
	 */
	private synchronized static boolean lockProcessor(BatchContext batchContext, Map<String, Object> processReqParamMap) throws BatchException {
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection batchConn = batchContext.getBATCHConnection();
		Long requestId = batchContext.getRequestParams().getRequestId();
		
		boolean result = bDao.lockProcessor(requestId, batchConn);
		if(result){
			BatchLockMonitor.addLockingEntry(requestId, processReqParamMap);
		}
		return result;
	}

	/**
	 * Checks whether the given paramMap is for date run or not
	 * 
	 * @param paramMap
	 * @return
	 */
	private static boolean isDateRun(Map<String, Object> paramMap){
		String batchType = (String)paramMap.get(PROCESS_REQUEST_PARAMS.BATCH_TYPE.name());
		if(BATCH_TYPE.DATE.name().equals(batchType)){
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the given paramMap is for date run or not
	 * 
	 * @param paramMap
	 * @return
	 */
	public synchronized static boolean isCurrentDateRun(){
		//If the processor is locked for DATE run, then the BatchLockMonitor will have only one entry
		//so to verify for is DATE run, we need to verify only first entry
		Iterator<Map<String, Object>> iter = BatchLockMonitor.getLockingEntries().values().iterator();
		if(iter.hasNext()){
			String batchType = (String)iter .next().get(PROCESS_REQUEST_PARAMS.BATCH_TYPE.name());
			if(BATCH_TYPE.DATE.name().equals(batchType)){
				return true;
			}
		}
		return false;
	}

	/**
	 * This method verifies if the new batch with provided paramMap is eligible
	 * for concurrent execution with already running batches
	 * 
	 * Step 1: It first gets the entity type for the new batch in question
	 * 
	 * Step 2: Call containsConflictingEntry() to validate whether this batch
	 * can execute with already running batches
	 * 
	 * @param paramMap
	 * @return
	 * @throws BatchException
	 */
	private static boolean isValidForConcurrentExecution(Map<String, Object> paramMap) throws BatchException{
		String entityType = null;
		String entityValue = null;
		
		if(isDateRun(paramMap)){
			//Concurrent execution not allowed for date run
			throw new BatchException("Day batch can not run with currently running Special batch");
		}
		for (Entry<String, Object> entry : paramMap.entrySet()) {
			
			if ((entityType = getKeyIfEntity(entry.getKey())) != null) {
				entityValue = entry.getValue().toString();
				if (containsConflictingEntry(entityType, entityValue)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the entity key without suffixed _numericValue if it is and Entity
	 * 
	 * @param strEntity
	 * @return
	 */
	private static String getKeyIfEntity(String strEntity){
		//entity is suffixed by '_numericValue'
		//Using this information to decide whether a passes key is entity or not
		int lastIndex = strEntity.lastIndexOf('_');
		if(lastIndex == -1)
		{
			return null;
		}
		try {
			Integer.parseInt(strEntity.substring(lastIndex + 1));
		} catch(Exception e) {
			return null;
		}
		return strEntity.substring(0, lastIndex);
		
	}

	/**
	 * The method checks whether the new batch with given entityType and
	 * entityValue can execute with already running batches
	 * 
	 * Logic: 
	 * If Entity Value is "All", check if any batch running for same
	 * entity type. 
	 * 		If yes, return false 
	 * 		Else return true
	 * 
	 * Else when Entity Value is not "All", check for batch running for same
	 * entity type as well as same entity value
	 * 		If yes, return false
	 * 		Else return true
	 * 
	 * 
	 * @param entityType
	 * @param entityValue
	 * @return
	 */
	private static boolean containsConflictingEntry(String entityType, String entityValue) throws BatchException{
		Collection<Map<String, Object>> paramsMapList = BatchLockMonitor
				.getLockingEntries().values();
		String errorString = null;

		//Iterate through all batches
		for (Map<String, Object> paramMap : paramsMapList) {
			//Iterate through paramMap of each batch
			for (String key : paramMap.keySet()) {
				//If entity types matches
				if(entityType.equals(getKeyIfEntity(key))){
					if(entityValue.equals("ALL")){
						errorString = "Batch already running for " + entityType
								+ "/" + paramMap.get(key) + ". Cannot run another concurrent batch for " + entityType + "/" + entityValue;
						throw new BatchInProgressException(errorString);
					} else if(paramMap.get(key).equals("ALL")){
						errorString = "Batch already running for " + entityType
						+ "/" + paramMap.get(key) + ". Cannot run another concurrent batch for " + entityType + ".";
						throw new BatchInProgressException(errorString);
					} else if (entityValue.equals(paramMap.get(key).toString())) {
						errorString = "Batch already running for " + entityType
						+ "/" + paramMap.get(key) + ". Cannot run another concurrent batch for same parameters.";
						throw new BatchInProgressException(errorString);
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param batchContext
	 * @return
	 * @throws BatchException
	 */
	private static Map<String, Object> getProcessRequestParams(BatchContext batchContext) throws BatchException {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.putAll(batchContext.getRequestParams().getProcessRequestParams());
		if(!paramsMap.containsKey(PROCESS_REQUEST_PARAMS.BATCH_TYPE.name()))
		{
			Integer batchNo = (Integer)paramsMap.get(PROCESS_REQUEST_PARAMS.BATCH_NO.name());
			IBatchDao bDao = DaoFactory.getBatchDao();
			Connection batchConn = batchContext.getBATCHConnection();

			paramsMap = bDao.getProcessReqParams(batchNo, batchConn);
		}
		return paramsMap;
	}
	
}
