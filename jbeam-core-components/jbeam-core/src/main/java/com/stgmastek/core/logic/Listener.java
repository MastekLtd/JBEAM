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

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngineException;

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.exception.ExecutionException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchInfo;
import com.stgmastek.core.util.BatchJob;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.BatchParams;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.GroupInfo;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.ScheduledExecutable;
import com.stgmastek.core.util.time.JBeamTimeFactory;

/**
 * 
 * The specialized listener class
 * The threading behavior is purely for development purpose and would never be in use 
 * when used in conjunction with Process Request Engine 
 * 
 *  This class is the one that actually executes a particular batch object.
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public final class Listener extends BatchJob implements Runnable {
	
	/** The batch no identifier */
	private Integer batchNo = -1;

	/** The listener identifier */
	private Integer listenerId = -1;
	
	/** The below fields are used purely for development i.e. MODE = DEV */
	private ScheduledExecutable scheduledExecutable = null;
	
	/** The current entity parameters and specifications */
	private EntityParams entityParams = null;
	
	/** The object mapping that maps an object name with the actual executable */
	private Map<String, ObjectMapDetails> objectMapping = null;
	
	private static transient final Logger logger = Logger.getLogger(Listener.class);
	
	/**
	 * Default Constructor 
	 */
	public Listener(){}
	
	/**
	 * Overloaded constructor that takes the batch context as argument. 
	 * This constructor is purely for development purpose and not in use 
	 * with mode PRE 
	 *  
	 * @param batchContext
	 * 		  The context for the batch 
	 */
	public Listener(BatchContext batchContext){
		this.batchContext = batchContext;
	}
	
	/**
	 * Returns the batch context 
	 * This constructor is purely for development purpose and not in use 
	 * with mode PRE
	 *  
	 * @return the context for the listener 
	 */
	public BatchContext getListenerContext(){
		return this.batchContext;
	}
	
	/**
	 * Initialization of the listener 
	 * <p />
	 * Way it works. Explanation of logical steps - 
	 * <UL>
	 * 	<LI> Gets attributes / static information from the PRE Context and sets it for local usage
	 * </UL>
	 */
	@SuppressWarnings("unchecked")
	
	protected synchronized void init(BatchContext batchContext) throws BatchException {
		
		BatchParams params = batchContext.getRequestParams();
		batchNo = params.getIntValue("BATCH_NO");
		
		//Set the batch context for the listeners 
		entityParams = (EntityParams) getAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_ENTITY_PARAMS.name());
		
		BatchInfo batchInfo = (BatchInfo) getAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_BATCH_INFO.name());
		batchContext.setBatchInfo(batchInfo);
		if (!batchInfo.isFailedOver()) {
		    Date batchRunDate = (Date) getAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_BATCH_RUN_DATE.name());
		    if (logger.isDebugEnabled()) {
		    	logger.debug("In Listener >> BATCH_RUN_DATE = " + batchRunDate);
		    }
		    batchInfo.setBatchRunDate(batchRunDate);
		}
		
		objectMapping = (Map<String, ObjectMapDetails>) 
								getAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_OBJECT_MAP.name());
		
		//Set additionally received parameters from the PROCESS_REQUEST_PARAMS 
		listenerId = params.getIntValue("LISTENER_ID");
		
		
		HashMap<String,Object> PREParams = params.getProcessRequestParams();
        if (!batchInfo.isFailedOver()) {
            if(PREParams.containsKey("EXECUTION_END_DATE")
                    && PREParams.get("EXECUTION_END_DATE") != null)
                batchContext.getBatchInfo().setExecutionEndTime(
                        batchContext.getRequestParams().getDateValue("EXECUTION_END_DATE"));
        }
		
		//Needed for PRE fail over
		//Important that whatever attributes derived from context changes to be set into
		//the context again
		setAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_BATCH_INFO.name(), batchContext.getBatchInfo());
		
	}
	
	/**
	 * The method to execute the batch jobs for this current listener
	 * <p />
	 * Way it works. Explanation of logical steps - 
	 * <UL>
	 * 	<LI> If the listener identifier is not set, it raises an exception
	 *  <LI> Fetches the registered execution handlers in the system
	 *  <LI> Fetches the distinct set of values for an entity
	 *  	 EX: For policy as the entity, it would fetch all the distinct set of policies assigned to it. 
	 *  	 Note: There might be case when P1, P6, P11 be assigned to listener with # 1
	 *   <LI> For each of values in the set, it fetches the bunch of batch jobs assigned to it 
	 *   	  for that value
	 *   <LI> Before execution of any object, it checks for any instruction to stop the batch 
	 *   <LI> Batch object is executed using {@link ExecutionHandler#execute(BatchContext, BatchObject, ObjectMapDetails, HashMap, Boolean)}
	 *   <LI> If stop batch is expected then the listener marks completion and breaks out
	 *   <LI> If execution fails for an object then depending upon the settings 
	 *   	  in the BATCH_COLUMN_MAP table, it determines whether to mark all non-executed objects 
	 *   	  for the same entity and same value as 'SP' or ignore and proceed towards executing 
	 *   	  other objects <p />
	 *   	  EX: If there are 10 [1, 2 ... 10] objects for Policy P1 - 
	 *   	  If say object #4 fails (then it would be marked as '99') and if it is set in the 
	 *   	  BATCH_COLUMN_MAP table for POLICY as the entity as BCP_ON_ERROR_FAIL_ALL = 'Y', 
	 *   	  then the system would mark object #5 to #10 as 'SP' without execution. 
	 *        If  BCP_ON_ERROR_FAIL_ALL = 'N' then the system would continue executing objects #5 to #10 
	 *        in the normal way. 
	 *   <LI> Once all jobs are completed it marks completions and exits
	 * </UL>
	 * 
	 * 
	 * @param batchContext
	 * 		  The context for the batch
	 * @return true if the listener execution is completed, exception otherwise 
	 * @throws BatchException
	 */	
	
	protected boolean execute(BatchContext batchContext) throws BatchException{
		//Throw exception if listener identifier is not set 
		if(listenerId == -1){
			throw new BatchException("Listener ID is not set");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Starting Listener#" + listenerId);
		}
		
		//Determine whether to fail other objects for the same value
		String strOnFailSuspendOthers = entityParams.getLookupColumns().get(0).getOnErrorFailAll();		
		Boolean onFailSuspendOthers = 
			(strOnFailSuspendOthers != null && strOnFailSuspendOthers.equals("Y")) ? true : false;
		
		//Get the distinct set of entities assigned to this particular listener
		IAppDao aDao = DaoFactory.getAppDao();
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection aCon = null;
		Connection bCon = null;
		try {
			aCon = batchContext.getApplicationConnection();
			bCon = batchContext.getBATCHConnection();
			List<GroupInfo>  distinctset = 
				aDao.getDistinctSet(batchContext.getBatchInfo().getBatchRunDate(), entityParams, listenerId, aCon);
			
			//Get the registered execution handlers in the system 
			HashMap<String, String> registeredExecutionHandler = 
				Configurations.getConfigurations().getConfigurations("CORE", "EXECUTION_HANDLER");
			
			//Iterate over the distinct set 
			for(GroupInfo gi : distinctset){
	
				//Check End Of Batch
				if(batchContext.isBatchToBeStopped()){				
					break;
				}				
				
				//Get the listed or assigned jobs for the current group i.e. an entity i.e. 
				//a policy or claim or any other entity
				List<BatchObject> retList = aDao.getAssignedBatchObjects(
													batchContext.getBatchInfo().getBatchRunDate(),entityParams, 
														gi, listenerId, aCon);
				
				//Exception occurred indicator 
				Boolean exceptionOccured = false;
				
				//Iterate over the list and execute each job
				int counter = 0; 
				StringBuffer tracePath = new StringBuffer();
				for(BatchObject bo : retList){
					
					//Check End Of Batch
					if(batchContext.isBatchToBeStopped()){				
						break;
					}

					try {
						tracePath = new StringBuffer();
						tracePath.append("Step 1 ");
						if (bo.getStatus() == OBJECT_STATUS.UNDERCONSIDERATION) {
							tracePath.append("Step 2a ");
							if (isFailedOver()) {  //is this request failed over.
								logger.error("Batch Object has failed over. Batch will not get executed for " + bo);
							} else {
								logger.error("Listener did not fail over though the object is marked as UC. Batch will not get executed for " + bo);
							}
							bo.setStatus(OBJECT_STATUS.FAILEDOVER);
							bo.setObjExecEndTime(JBeamTimeFactory.getInstance().getCurrentTimestamp(aCon));
							bo.setDateExecuted(JBeamTimeFactory.getInstance().getCurrentTimestamp(batchContext));
							exceptionOccured = true;
							tracePath.append("Step 2b ");
						} else {
							tracePath.append("Step 3 ");
							//Execute the executable object and get it with updated status
							bo.setBatchNo(batchNo);
							bo.setBatchRevNo(batchContext.getBatchInfo().getBatchRevNo());
							bo.setCycleNo(ProgressLevel.getProgressLevel(batchNo).getCycleNo());
							bo.setPrePost(ProgressLevel.getProgressLevel(batchNo).getPrgLevelType());
							bo.setObjExecStartTime(JBeamTimeFactory.getInstance().getCurrentTimestamp(aCon));
							tracePath.append("Step 4 ");
							Long seqNo = null; //Update the log in the finally method
							try{
								tracePath.append("Step 4a ");
								seqNo = bDao.setBatchLog(bo, bCon);
								tracePath.append("Step 4b ");
								status = "EXEUCTING - " + bo.getObjectName() + "[" + bo.getSequence() + "]";
								tracePath.append("Step 4c ");
								bo = ExecutionHandler.execute(batchContext, bo, 
										objectMapping.get(bo.getObjectName().toUpperCase()), 
										registeredExecutionHandler, exceptionOccured);
								tracePath.append("Step 5 ");
							} catch (DatabaseException de) {
								tracePath.append("Step 6 ");
								setAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_EXIT.name(), "Y");
								setAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_EXIT_REASON.name(), Constants.CLOSURE_REASON.BATCH_FAILED.name());
								logger.fatal("Unable to proceed the batch due to Database Exception " + de.getMessage(), de);
								break;
							} catch(BatchStopException bbse){
								tracePath.append("Step 7 ");
								bbse.printStackTrace();
								setAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_EXIT.name(), "Y");
								setAttribute(batchNo, Constants.CONTEXT_KEYS.JBEAM_EXIT_REASON.name(), Constants.CLOSURE_REASON.BATCH_FAILED.name());
								break;
							} catch(ExecutionException bee){
								tracePath.append("Step 8 ");
								bee.printStackTrace();
								//Check the settings in entity parameters for on-error-fail-all
								if(onFailSuspendOthers)
									exceptionOccured = true;					
							} catch (Exception e) {
								tracePath.append("Step 8a ");
								e.printStackTrace();
								throw new RuntimeException(e);
							}
							finally{
								if (bo.getStatus() == null) { //set batch log may throw exception.
									bo.setStatus(OBJECT_STATUS.FAILED);
								}
								tracePath.append("Step 9 ");
								//Insert into the LOG table
								bo.setObjExecEndTime(JBeamTimeFactory.getInstance().getCurrentTimestamp(aCon));
								bDao.updateBatchLog(seqNo, bo, bCon);
							}			
						}
					} finally {
						tracePath.append("Step 10 ");
						//Update the status in the Application schema 
						if (logger.isDebugEnabled()) {
							logger.debug("Updating the Batch..." + bo.toString());
						}
						if(bo.getDateExecuted() == null) {
							logger.error(tracePath);
						}
						aDao.setBatchObjectStatus(bo,aCon);	
					}
					counter++;
					if (logger.isDebugEnabled()) {
						logger.debug(bo.getObjectName().toUpperCase());
					}
				}
				if (logger.isInfoEnabled()) {
					logger.info("Listener Execution completed with ID: " 
									+ listenerId + " for " + entityParams.getEntity() + ":" + gi.getEntityValue() 
									+ " with batch object size # : " + counter);
				}
			}
			
			//This mode specific activity would be needed, 
			//as it would be otherwise done by Process Request Engine
			if(Constants.MODE.equals(Constants.DEV) 
					|| (Constants.MODE.equals(Constants.DEV) && batchContext.isBatchToBeStopped())){
				bDao.setPREStatus(scheduledExecutable, bCon);
			}			
		
		} finally {
			aDao.releaseResources(null, null, aCon);
			bDao.releaseResources(null, null, bCon); //It is not necessary to close bCon using bDao.
		}
		return true;
	}

	/**
	 * This method would be used purely for development (not PRE) 
	 * Sets the executables into a local list
	 * 
	 * @param scheduledExecutable
	 * 		  the scheduled listener to be marked as completed. 
	 * 
	 */
	public void setExecutable(ScheduledExecutable scheduledExecutable){
		this.scheduledExecutable = scheduledExecutable;
	}
	
	/***
	 * Temporary method for development purpose only
	 * 
	 * Executes the listener as thread  
	 */
	
	public void run() {
		try {
			processRequest();
		} catch (CProcessRequestEngineException e) {
		}
		
	}
	
	/**
	 * Returns the status string to be displayed onto the Process 
	 * Request Engine console. 
	 */
	
	public String getStatus() {
		return status;
	}
	
   
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/Listener.java                                                                                      $
 * 
 * 26    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 25    4/26/10 2:52p Kedarr
 * Changed the logging.
 * 
 * 24    4/26/10 2:40p Kedarr
 * Modified to handle the UC status for the object.
 * 
 * 23    4/22/10 1:30p Kedarr
 * Changes related to refactoring (renamed) the method stop batch to is batch to be stopped in batch context.
 * 
 * 22    3/25/10 11:41a Kedarr
 * Rectified the mistake of invalid context key that was used.
 * 
 * 21    3/25/10 9:57a Kedarr
 * As the context is different for each batch job the get batch info method should not check if the object is in the context.
 * 
 * 20    3/19/10 2:20p Kedarr
 * Changes made to insert and update the log before starting the execution of the object and after its execution is complete.
 * 
 * 19    3/17/10 10:05a Kedarr
 * Changes made to add progress level type in the pre-post column of the log table.
 * 
 * 18    3/15/10 12:29p Kedarr
 * Changes made to insert the cycle number against the batch objects.
 * 
 * 17    3/09/10 3:00p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 16    3/03/10 5:38p Grahesh
 * Added Logger.
 * Removed batchContext.getBATCHConnection() from called IBatchDao constructor and added in called methods.
 * Removed batchContext.getApplicationConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 15    2/25/10 10:47a Grahesh
 * Changes made to make use of context keys enum from constants and for fail over of batch.
 * 
 * 14    1/13/10 4:14p Grahesh
 * Corrected the links in java doc comments
 * 
 * 13    1/07/10 6:03p Grahesh
 * Updated Java Doc comments
 * 
 * 12    1/06/10 2:39p Grahesh
 * Setting the batchInfo back into the PRE context for failover
 * 
 * 11    1/04/10 4:39p Grahesh
 * Added a helper SOP
 * 
 * 10    12/29/09 6:02p Grahesh
 * Comparing the object name in upper case
 * 
 * 9     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 8     12/23/09 4:12p Grahesh
 * Implementing IProcessStatus for display of status on to the PRE console
 * 
 * 7     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 6     12/22/09 2:42p Grahesh
 * Corrected the condition for settting the PRE status
 * 
 * 5     12/22/09 10:28a Grahesh
 * Updation done to include the closure reason.
 * 
 * 4     12/18/09 12:58p Grahesh
 * Updated the comments
 * 
 * 3     12/18/09 11:34a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/