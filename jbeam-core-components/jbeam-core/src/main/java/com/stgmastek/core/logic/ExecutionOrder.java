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
package com.stgmastek.core.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchInfo;
import com.stgmastek.core.util.ColumnLookup;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.ExecutionStatus;
import com.stgmastek.core.util.GroupInfo;
import com.stgmastek.core.util.LookupTable;
import com.stgmastek.core.util.ProgressLevel;

/**
 * Final class that sets up the execution order of the current batch
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class ExecutionOrder {

	private final static Logger logger = Logger.getLogger(ExecutionOrder.class);
	
	/**
	 * Default constructor 
	 */
	private ExecutionOrder(){		
	}
	
	/**
	 * Static method to setup the execution order.
	 * This method essentially looks up in to the setup table BATCH_COLUMN_MAP to 
	 * get the details.Using the details it would setup the execution order for the batch 
	 * Once the execution order is set, it sets the order into the {@link BatchInfo#setOrderedMap(TreeMap)}
	 * for all other objects to derive knowledge from. 
	 * Note: The batch could be run for - 
	 * <OL>
	 * <LI> For a date i.e. all entities and all values for those entities. 
	 * <LI> For an entity i.e. batch for only policy records and all its values 
	 * 	    i.e. P1, P2 ... Pn
	 * <LI> For a single object identified as GENERAL type of job with a 
	 * 		sequence number i.e. JOB_SCHEDULE.job_seq
	 * <LI> For only Meta events like ALL PRE and ALL POST
	 * <LI> For any combination of above, a few given - 
	 * 		<UL>
	 * 			<LI> Policy P1 and ALL PRE 
	 * 			<LI> ALL Agency records and Policy P1
	 *   		<LI> Policy P1 and Agency A1
	 * 		</UL>  
	 * </OL>
	 *  
	 * Every step has inline comments associated with it. 
	 * 
	 * @param batchContext
	 * 		  The context for the batch 
	 * @return true 
	 * 		   If the setup is done successfully 
	 * @throws BatchException
	 * 		   Any database I/O exception 
	 */
	public synchronized static Boolean setExecutionOrder(BatchContext batchContext) throws BatchException {
		
		//Get request parameters
		HashMap<String, Object> params = batchContext.getRequestParams().getProcessRequestParams();
		
		//Check whether it is a date batch run or specific batch run			
		if(params.size() < 1){
			batchContext.getBatchInfo().setDateRun(true);
		}
		Connection con = batchContext.getBATCHConnection();
		IBatchDao bDao = DaoFactory.getBatchDao();
		try {
			//Query the setup table to get the setup values
			LookupTable lookupTable = bDao.getLookupTable(con);
			Map<String, String> orderByLookupTable = bDao.getOrderByLookupTable(con);
			
			TreeMap<Integer, EntityParams> orderedMap = new TreeMap<Integer, EntityParams>();
			
			//If it is date batch run, then for all entities, populate "ALL" 
			if(batchContext.getBatchInfo().isDateRun()){
				Iterator<String> lTableIter = lookupTable.keySet().iterator();
				while(lTableIter.hasNext()){				
					String entity = lTableIter.next();
					params.put(entity + "_1", "ALL");
				}
			}
			
			//Iterate over each parameters set 
			for (Entry<String, Object> entry : params.entrySet()) {
				String paramName = entry.getKey();
				Object paramValue = entry.getValue();
				if(logger.isDebugEnabled()) {
					logger.debug("In ExecutionOrder >>>> paramName  ==>" +paramName );
				}
				
				String entity = null;
				
				//Strip the last occurrence of _ and get the entity name
				entity = paramName.substring(0, paramName.lastIndexOf("_"));				
				if(logger.isDebugEnabled()) {
					logger.debug("In ExecutionOrder >>>> Entity  ==>" +entity );
				}
				//Validate whether the entity is setup appropriately in 
				//the BATCH_COLUMN_MAP table 
				if(!lookupTable.containsKey(entity)){
					//If the entity is not set, raise an exception and exit 
					throw new BatchException("The entity " + entity 
							+ " is not set up in the COLUMN_MAP table.");
				}else{
					
					//Get the lookup record 
					//Once found, get the details and set it against the entity 
					List<ColumnLookup> lookupColumns = lookupTable.get(entity);
					Integer order = lookupColumns.get(0).getPrecedenceOrder();
					if(!orderedMap.containsKey(order)){
						EntityParams entityParams = new EntityParams(entity);
						orderedMap.put(order, entityParams);
					}
					EntityParams entityParams = orderedMap.get(order); 
					entityParams.setLookupColumns(lookupColumns);
					entityParams.setOrderByMap(orderByLookupTable);//Added on 01-OCT-2013 - Mandar
					
					//Check 'ALL' or for specific entity values. 
					//Note: Batch could be run for a date i.e. all entities (and all values) 
					// or for any combination of entity and values 
					if(!paramValue.equals("ALL")){
						List<GroupInfo> list = entityParams.getValues();
						//check if all exists. If exists do not write the new value
						if(list.size() == 0  || !list.get(0).getEntityValue().equals("ALL"))
							entityParams.getValues().add(new GroupInfo((String)paramValue));					
					}else{
						entityParams.setAll(new GroupInfo((String)paramValue));
					}
				}
			}
			
			batchContext.getBatchInfo().setOrderedMap(orderedMap);
		} finally {
			bDao.releaseResources(null, null, con);
		}
		
		return true;
	}
	
	/**
	 * Saves the state of the batch for revision runs usage 
	 * If the current batch is 1000 and revision is 1 then the file would 
	 * be saved as '1000_1.savepoint' 
	 * 
	 * @param batchContext
	 * 		  The job batchContext of the batch 
	 * @throws BatchException
	 * 		   Any exception occurred during the serialization process 
	 */
	public static synchronized void saveBatchState(BatchContext batchContext) throws BatchException{
		BatchInfo toSaveBatchInfo = batchContext.getBatchInfo();
		toSaveBatchInfo.setProgressLevelAtLastSavePoint((ProgressLevel) ProgressLevel.getProgressLevel(toSaveBatchInfo.getBatchNo()).clone()); //clone is necessary as ProgresLevel is static
		if (logger.isDebugEnabled()) {
			logger.debug("Saving Current Batch progress level as ==>" + ProgressLevel.getProgressLevel(toSaveBatchInfo.getBatchNo()).toString());
		}
		String savepointFilePath = Configurations.getConfigurations().getConfigurations("CORE", "SAVEPOINT", "DIRECTORY");
		ObjectOutputStream oos = null; 
		try{
			oos = new ObjectOutputStream(new FileOutputStream(FilenameUtils.concat(
	                    savepointFilePath, toSaveBatchInfo.getBatchNo() + "_" + toSaveBatchInfo.getBatchRevNo() + ".savepoint")));
			oos.writeObject(toSaveBatchInfo);
			oos.flush();
			batchContext.setBatchStateSaved(true);
		} catch (FileNotFoundException e) {
			batchContext.setBatchStateSaved(false);
			logger.error(e);
			throw new BatchException(e.getMessage(), e);
        } catch (IOException e) {
        	batchContext.setBatchStateSaved(false);
        	logger.error(e);
        	throw new BatchException(e.getMessage(), e);
		} finally {
		    IOUtils.closeQuietly(oos);
		}
	}
 
	/**
	 * Reads the saved state of the batch for revision runs 
	 * If the current batch number it 1000 and revision is 5, 
	 * then this method would look for saved state of batch 1000 
	 * with revision (5 - 1) i.e. '1000_4.savepoint' 
	 * 
	 * @param batchContext
	 * 		  The context for the batch 
	 * @throws BatchException
	 * 		   Any exception thrown during reading of the serialized file 
	 */
	public static synchronized void updateBatchState(BatchContext batchContext) throws BatchException{
		BatchInfo newBatchInfo = batchContext.getBatchInfo();
		String savepointFilePath = 
			Configurations.getConfigurations().getConfigurations("CORE", "SAVEPOINT", "DIRECTORY");
		String savePointFile = FilenameUtils.concat(savepointFilePath , newBatchInfo.getBatchNo() + "_" 
								+ (newBatchInfo.getBatchRevNo() - 1) + ".savepoint");
		if (logger.isDebugEnabled()) {
			logger.debug("Reading the saved state from file : " + savePointFile);
		}
		FileInputStream fis = null;
		try{
			
			//Check whether the file exists
			File f = new File(savePointFile);
			if(!f.exists())
				throw new BatchException("Cannot locate the the save point file named :" + savePointFile);
			
			fis = new FileInputStream(f);
			ObjectInputStream ois = 
				new ObjectInputStream(fis);
			BatchInfo savedBatchInfo = (BatchInfo)ois.readObject();			
			newBatchInfo.setOrderedMap(savedBatchInfo.getOrderedMap());
			newBatchInfo.setProgressLevelAtLastSavePoint((ProgressLevel) savedBatchInfo.getProgressLevelAtLastSavePoint()); //This object is different but still cloned.
			newBatchInfo.setBatchRunDate(savedBatchInfo.getBatchRunDate());
			newBatchInfo.setDateRun(savedBatchInfo.isDateRun());
			
			if (logger.isDebugEnabled()) {
				logger.debug("Last batch saved state is " + savedBatchInfo.getProgressLevelAtLastSavePoint().toString());
			}
			//Set the ExecutionStatus in the ProgressLevel
			ExecutionStatus savedExecutionStatus = newBatchInfo.getProgressLevelAtLastSavePoint().getExecutionStatus();
			ProgressLevel.getProgressLevel(newBatchInfo.getBatchNo()).setExecutionStatus(
					savedExecutionStatus.getEntity(), savedExecutionStatus.getStageCode());
			fis.close();
			fis = null;
			ois.close();
			ois = null;
		} catch (FileNotFoundException e) {
			logger.error(e);
			throw new BatchException(e.getMessage(), e);
        } catch (IOException e) {
			logger.error(e);
			throw new BatchException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
        	logger.error(e);
        	throw new BatchException(e.getMessage(), e);
        } finally {
        	if (fis != null) {
        		try {
	                fis.close();
                } catch (IOException e) {
                }
        	}
		}
	}	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/ExecutionOrder.java                                                                                $
 * 
 * 11    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 10    4/27/10 9:27a Kedarr
 * Saves the state saved flag to true in batch context.
 * 
 * 9     4/21/10 12:07p Kedarr
 * Changes made to save the cloned progress level in batch info and subsequently load the same.
 * 
 * 8     3/09/10 2:51p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 7     3/03/10 5:33p Grahesh
 * Removed batchContext.getBATCHConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 6     2/24/10 5:01p Grahesh
 * Changes made to remove warnings as PRE was upgraded to use generics.
 * 
 * 5     2/17/10 9:15a Grahesh
 * Added synchronization to the methods used for saving / loading the state.
 * 
 * 4     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 3     12/22/09 11:06a Grahesh
 * Fetching the save point directory from the configurations
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/