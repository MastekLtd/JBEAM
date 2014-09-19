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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import stg.pr.engine.ITerminateProcess;
import stg.pr.engine.PREContext;
import stg.utils.StringUtils;

import com.stg.logger.LogLevel;
import com.stgmastek.core.aspects.Email;
import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.aspects.LogTime;
import com.stgmastek.core.aspects.Marker;
import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchInProgressException;
import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.util.BasePoller;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchInfo;
import com.stgmastek.core.util.BatchJob;
import com.stgmastek.core.util.BatchJobMetaData;
import com.stgmastek.core.util.BatchJobMonitorPoller;
import com.stgmastek.core.util.BatchLockHandler;
import com.stgmastek.core.util.BatchParams;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ConstantsLoader;
import com.stgmastek.core.util.CoreUtil;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.ExecutionStatus;
import com.stgmastek.core.util.GroupInfo;
import com.stgmastek.core.util.MonitorInstructionPoller;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.ScheduledExecutable;
import com.stgmastek.core.util.Constants.CLOSURE_REASON;
import com.stgmastek.core.util.Constants.CONTEXT_KEYS;
import com.stgmastek.core.util.Constants.META_EVENTS;
import com.stgmastek.core.util.Constants.PROCESS_REQUEST_PARAMS;

/**
 * 
 * The processor class is the most important class in the Core system. This
 * class is responsible for executing the entire batch process and uses all
 * other classes for its functioning. Each method would have a detailed
 * description of its functioning. <p /> Brief details -
 * <OL>
 * <LI><i>init(batchContext)</i> - Initialization consists of -
 * <OL>
 * <LI>Parsing the IN parameters for the batch
 * <LI>Locking the processor
 * <LI>Cleaning of previous batch / revision assignments
 * </OL>
 * <LI><i>validate(batchContext)</i> - Validation consists of -
 * <OL>
 * <LI>Validating the parameters that have been set in for the batch or the
 * revision
 * <LI>Setting up the execution order or reading from the saved state for a
 * revision run
 * </OL>
 * <LI><i>execute(batchContext)</i> - Execution consists of -
 * <OL>
 * <LI>Executing the batch. If it is a revision run then it starts from the
 * point where it was last saved. EX: If during the execution of POLICY, the
 * last revision was saved, then it starts by first cleaning any assignments for
 * policy and starts with the assignment afresh followed by execution <p />
 * NOTE: The revision parameters would be ignored as it is a revision for a
 * batch and SHOULD NOT have any additional parameters EX: If Batch # 100
 * Revision # 1 was a date run then Batch # 100 Revision # 2 would also be a
 * date run and no additional parameters except BATCH_ENDS_IN_MINUTES would be
 * taken into account
 * </OL>
 * <LI><i>destroy(batchContext)</i> - End of Batch consists of -
 * <OL>
 * <LI>Closing of the batch. <p /> NOTE: Except for the batch objects in the
 * JOB_SCHEDULE table, the status for the progress level would be marked 'CO'
 * even though the batch was stopped and did not complete. The save point would
 * be saved as a serialized file named as per the batch and revision for later
 * use during revision run. EX: If the current batch is 1000 and revision is 1
 * then the file would be saved as '1000_1.savepoint'.
 * {@link ExecutionOrder#saveBatchState(BatchContext)}
 * <LI>Unlocks the Processor for usage
 * </OL>
 * </OL>
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public final class Processor extends BatchJob implements ITerminateProcess {

	private static final Logger logger = Logger.getLogger(Processor.class);
	
	private final ArrayList<BasePoller> pollerList = new ArrayList<BasePoller>();
	
	private Integer batchNo = -1;
	
	/**
	 * The initialization method for the batch (or the Processor) <p />
	 * Execution Steps -
	 * <OL>
	 * <LI>Load the constants from the configuration settings as set in the
	 * CORE_CONFIG table CODE 1 = 'CORE'
	 * <LI>Locks the Processor
	 * <LI>Identifies and processes the parameters as needed
	 * <LI>Checks whether the batch is a revision or a fresh batch
	 * <LI>Creates a batch or revision accordingly. EX: If one of the parameters
	 * supplied is BATCH_NO then it would create a revision for the batch, else
	 * if not supplied then would create a new batch with revision # 1
	 * <LI>Initiates the progress level for the current batch
	 * <LI>Does additional initialization {@link Processor#doInit(BatchContext)}
	 * </OL>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @throws BatchException
	 *             Any exception thrown during initialization of the batch
	 *             wrapped as BatchException
	 */
	
	@Log
	protected synchronized void init(BatchContext batchContext)
			throws BatchException {

        BatchInfo batchInfo = null;
        if(logger.isDebugEnabled()) {
        	logger.debug("In init() of Processor");
        }
        
    	if (super.isFailedOver()) {
    		//TODO Now to get the batch info, we need to provide batchNo - Need to figure out where to get it from for failedOver batches.
            Object obj = getContext().getAttribute(CONTEXT_KEYS.JBEAM_BATCH_INFO.name());
    		batchInfo = (BatchInfo) obj; //This will happen only when the batch fails over.
    		batchInfo.setFailedOver(true);
    		setAttribute(batchInfo.getBatchNo(), CONTEXT_KEYS.JBEAM_BATCH_INFO.name(), batchInfo);
		} else {

            batchInfo = new BatchInfo();
            batchInfo.setExecutionStartTime(new java.sql.Timestamp(System.currentTimeMillis()));
            String[] str = getBundleDetails();
            if(logger.isDebugEnabled()) {
	        	logger.debug("Batch Info Version = "+str[1]);
	        }
            batchInfo.setVersion(str[1]);
            
            if (StringUtils.countTokens(batchInfo.getVersion(), '.') > 2) {
            	batchInfo.setMajorVersion(Integer.parseInt(StringUtils.extractTokenAt(batchInfo.getVersion(), '.', 1)));
            	batchInfo.setMinorVersion(Integer.parseInt(StringUtils.extractTokenAt(batchInfo.getVersion(), '.', 2)));
            	String tmp = StringUtils.extractTokenAt(batchInfo.getVersion(), '.', 3);
            	if (tmp.endsWith("-SNAPSHOT")) {
            		tmp = tmp.replace("-SNAPSHOT", "");
            	}
            	batchInfo.setMacroVersion(Integer.parseInt(tmp));
            	batchInfo.setBuildNumber(Integer.parseInt(str[3]));
            }
            String[] array = batchInfo.getVersion().split("\\.");
            if (array.length > 1) {
            	batchInfo.setMinorVersion(Integer.parseInt(array[1]));
            } else {
            	batchInfo.setMajorVersion(0);
            	batchInfo.setMinorVersion(0);
            	batchInfo.setMacroVersion(0);
            	batchInfo.setBuildNumber(0);
            }
           		
    	}
        
        batchContext.setBatchInfo(batchInfo);
        printVersion();
        
		// Load the constants
		ConstantsLoader.bootCore(batchContext);
		
		try{
			// First step is to lock the Processor
			if(!BatchLockHandler.lockProcessor(batchContext)) {
			//if (!DAOUtil.lockProcessor(batchContext, true)) {
				throw new BatchInProgressException(
						"A batch is currently being executed in the same environment."
								+ "Please contact your system administrator.");
			}
		}catch(BatchInProgressException e){
			updateInstructionLogError(batchContext, e);
			throw e;
		}

		HashMap<String, Object> params = batchContext.getRequestParams()
				.getProcessRequestParams();

		if (!batchInfo.isFailedOver()) {
			if (params.containsKey("BATCH_NO")) {
				batchNo = (Integer) params.get("BATCH_NO");
				params.remove("BATCH_NO");
			}
		}

		// Check for optional batch end time
		if (params.containsKey("BATCH_ENDS_IN_MINUTES")) {
			Integer batchEndsInMinutes = (Integer) params
					.get("BATCH_ENDS_IN_MINUTES");
			Integer batchEndsInMilliSeconds = batchEndsInMinutes * 60 * 1000;
			batchInfo.setExecutionEndTime(new Date(batchInfo
					.getExecutionStartTime().getTime()
					+ batchEndsInMilliSeconds));
			params.remove("BATCH_ENDS_IN_MINUTES");
		}

		if (!batchInfo.isFailedOver()) {
		
			if (batchNo != null && batchNo != -1) {
				clearAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT.name());
				clearAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT_REASON.name());
				batchInfo.setBatchNo(batchNo);
//				clearBatchInfo(batchNo);
				batchInfo = doInitiateBatchRevision(batchContext, batchInfo);
			} else {
				batchInfo = doInitiateBatch(batchContext, batchInfo);
				batchNo = batchContext.getBatchInfo().getBatchNo();
			}

			if (Constants.SET_GLOBAL_PARAMETERS) {
				IAppDao aDao = DaoFactory.getAppDao();
				Connection con = batchContext.getApplicationConnection();
				String batchErrorSequence;
				try {
					batchErrorSequence = "1";
//					aDao.getBatchErrorSequence(con);
					
				} finally {
					aDao.releaseResources(null, null, con);
				}
				batchInfo.setBatchErrorSequence(batchErrorSequence);
			}
	
			batchContext.setBatchInfo(batchInfo);
	
			setAttribute(batchInfo.getBatchNo(), 
					CONTEXT_KEYS.JBEAM_BATCH_INFO.name(), batchInfo);
		} else {
			filterParamForFailedOver(batchContext, batchInfo); 
			batchNo = batchContext.getBatchInfo().getBatchNo();
		}
		
		// A batch would always have a progress level, no matter where it stops
		// At the same time, a batch would have only one state at any
		// given point in time
		// Create an instance and the system would always work on this instance
		batchNo = batchContext.getBatchInfo().getBatchNo();
		ProgressLevel.getProgressLevel(batchNo).setBatchNo(batchInfo.getBatchNo());
		ProgressLevel.getProgressLevel(batchNo).setBatchRevNo(
				batchInfo.getBatchRevNo());
		ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
				ProgressLevel.ACTIVITY.INITIALIZATION);
		ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
		status = ProgressLevel.ACTIVITY.INITIALIZATION.name();

		//Updates the Batch for failed over status (true )
		//update only when it is failed over. Otherwise default is N. so no
		//need to fire the sql.
		if (batchInfo.isFailedOver()) {
			IBatchDao bDao = DaoFactory.getBatchDao();
			Connection con = null;
			try {
				con = batchContext.getBATCHConnection();
				bDao.updateBatchForFailOver(batchInfo, con , batchInfo.isFailedOver());
				
			} finally {
				bDao.releaseResources(null, null, con);
			}
		}
		ExecutableBatchJobPool.getInstance();
		doInit(batchContext);
	}

	/**
	 * Helper method to initiate a new batch
	 * 
	 * @param batchContext
	 *            The batch context
	 * @param batchInfo
	 *            The new batch basic information
	 * @return The updated batch information
	 * @throws BatchException
	 *             Any exception occurred during initiating the new batch
	 */
	private BatchInfo doInitiateBatch(BatchContext batchContext,
			BatchInfo batchInfo) throws BatchException {

		HashMap<String, Object> params = batchContext.getRequestParams()
				.getProcessRequestParams();
		String batchName = null;
		String batchType = null;
		Integer instructionLogSeq = null;
		String instructingUser = null;
		Date businessDate = null;

		// Check whether the mandatory batch run date is supplied
		// throw exception otherwise
		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name())) {

			Date batchRunDate = (Date) params.get(Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name());
			if (logger.isDebugEnabled()) {
				logger.debug("In Processor from PROCESS_REQUEST_PARAMS >> BATCH_RUN_DATE = " + batchRunDate);
			}
			if (batchRunDate.after(Constants.MAX_BATCH_DATE))
				throw new BatchException("The supplied BATCH_RUN_DATE :"
						+ batchRunDate
						+ " is "
						+ "later than or equal to the cut off date "
						+ new SimpleDateFormat(Constants.BATCH_RUN_DATE_FORMAT)
								.format(Constants.MAX_BATCH_DATE), false);

			params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name());
			batchInfo.setBatchRunDate(batchRunDate);
		} else {
			throw new BatchException(
					"Missing mandatory BATCH_RUN_DATE in format "
							+ Constants.BATCH_RUN_DATE_FORMAT.toString(), false);
		}

		// Check whether the batch name
		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.BATCH_NAME.name())) {
			batchName = (String) params.get(Constants.PROCESS_REQUEST_PARAMS.BATCH_NAME.name());
			params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_NAME.name());
		}

		// Check whether the batch type
		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.BATCH_TYPE.name())) {
			batchType = (String) params.get(Constants.PROCESS_REQUEST_PARAMS.BATCH_TYPE.name());
			params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_TYPE.name());			
		}

		// Check whether the batch type
		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name())) {
			instructionLogSeq = (Integer) params.get(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
			params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
		}

		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name())) {
			instructingUser = (String) params.get(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name());
			params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name());
		}
		
		if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.BUSINESS_DATE.name())) {
			businessDate = (Date) params.get(Constants.PROCESS_REQUEST_PARAMS.BUSINESS_DATE.name());
			if (logger.isDebugEnabled()) {
				logger.debug("In Processor from PROCESS_REQUEST_PARAMS >> BUSINESS_DATE = " + businessDate);
			}
			params.remove(Constants.PROCESS_REQUEST_PARAMS.BUSINESS_DATE.name());
		}
		
		batchInfo.setBatchName(batchName);
		batchInfo.setBatchType(batchType);
		batchInfo.setInstructionLogSeq(instructionLogSeq);
		batchInfo.setBatchAction("BATCH STARTED");
		batchInfo.setBatchActionTime(new Date());
		batchInfo.setStartUser(instructingUser);
		batchInfo.setRequestId(batchContext.getRequestParams().getRequestId());
		batchInfo.setPREVersion(batchContext.getPreContext().getPREInfo().getVersion());

		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		try {
			con = batchContext.getBATCHConnection();
			batchInfo = bDao.initiateBatch(batchInfo, con);
			this.batchNo = batchInfo.getBatchNo();
		} finally {
			bDao.releaseResources(null, null, con);
		}
		
		setAttribute(batchNo, 
				CONTEXT_KEYS.JBEAM_BUSINESS_DATE.name(),
				businessDate);

		return batchInfo;
	}
	
	
	private void filterParamForFailedOver(BatchContext batchContext,
			BatchInfo batchInfo) throws BatchException {

		HashMap<String, Object> params = batchContext.getRequestParams()
				.getProcessRequestParams();
//		String batchName = null;
//		String batchType = null;
//		Integer instructionLogSeq = null;
//		String instructingUser = null;
//		String businessDate = null;

		params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name());
		params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_NAME.name());
		params.remove(Constants.PROCESS_REQUEST_PARAMS.BATCH_TYPE.name());			
		params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
		params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name());
		params.remove(Constants.PROCESS_REQUEST_PARAMS.BUSINESS_DATE.name());
	}

	/**
	 * Helper method to initiate a revision batch
	 * 
	 * @param batchContext
	 *            The batch context
	 * @param batchInfo
	 *            The batch information
	 * @return The updated batch information
	 * @throws BatchException
	 *             Any exception occurred during initiating the revision
	 */
	private BatchInfo doInitiateBatchRevision(BatchContext batchContext,
			BatchInfo batchInfo) throws BatchException {
		boolean canHaveRevision = false;
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		Integer instructionLogSeq = null;
		try {
			con = batchContext.getBATCHConnection();
			canHaveRevision = bDao.canHaveRevision(batchInfo.getBatchNo(), con);
			if (!canHaveRevision)
				throw new BatchException(
						"Cannot run a revision for completed batches", false);
			HashMap<String, Object> params = batchContext.getRequestParams().getProcessRequestParams();
			String instructingUser = null;
			if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name())) {
				instructingUser = (String) params.get(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name());
				params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTING_USER.name());
			}
			if (params.containsKey(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name())) {
				instructionLogSeq = (Integer) params.get(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
				params.remove(Constants.PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
			}
			batchInfo.setStartUser(instructingUser);
			batchInfo.setBatchAction("BATCH RESUMED");
			batchInfo.setBatchActionTime(new Date());
			batchInfo.setRequestId(batchContext.getRequestParams().getRequestId());
			batchInfo.setPREVersion(batchContext.getPreContext().getPREInfo().getVersion());
			batchInfo.setInstructionLogSeq(instructionLogSeq);
			batchInfo = bDao.initiateBatch(batchInfo, con);
			batchInfo.setRevisionRun(true);
		} finally {
			bDao.releaseResources(null, null, con);
		}

		return batchInfo;
	}

	/**
	 * The validation method for the batch (or the Processor) <p /> Execution
	 * Steps -
	 * <OL>
	 * <LI>Calls a delegated method {@link Processor#doValidate(BatchContext)}
	 * </OL>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @throws BatchException
	 *             Any exception thrown during validation of the batch wrapped
	 *             as BatchException
	 */
	
	@Log
	protected void validate(BatchContext batchContext) throws BatchException {
		if(logger.isDebugEnabled()) {
			logger.debug("Processor >> in Validate method ");
		}
		Integer batchNo = batchContext.getBatchInfo().getBatchNo();
		ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
				ProgressLevel.ACTIVITY.EXECUTION_ORDER);
		ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
		status = ProgressLevel.ACTIVITY.EXECUTION_ORDER.name();
		doValidate(batchContext);
		if (logger.isEnabledFor(LogLevel.NOTICE)) {
			logger.log(LogLevel.NOTICE, batchContext.getBatchInfo());
		}
	}

	/**
	 * The execution method for the batch (or the Processor) Internally this
	 * method calls many smaller helper methods. <p /> Execution Steps -
	 * <OL>
	 * <LI>Fetches the execution order as set during the validation of the batch
	 * <LI>Iterates over each entity as set up for execution
	 * <OL>
	 * <LI>Calls the helper method
	 * {@link Processor#processBatch(BatchContext, EntityParams)} with
	 * appropriate parameters
	 * <LI>In case of a fresh batch, it executes all entities as per the
	 * parameter set for the batch. For date runs, it looks into the execution
	 * order as set during the validation of the batch (check COLUMN_MAP
	 * table)
	 * <LI>In case of revision runs it fetches the save point or the last noted
	 * step performed during the previous revision and starts from where it
	 * left. <p /> Note: If the last revision had left any assignment that were
	 * not executed, then it would clear the assignments first, do a fresh
	 * assignment and start the execution <p />
	 * </OL>
	 * <LI>Special case: BatchStopException The execution point is saved in a
	 * serialized file for usage during revision runs
	 * {@link ExecutionOrder#saveBatchState(BatchContext)}.
	 * </OL>
	 * 
	 * @param batchContext
	 *            the context for the batch
	 * @throws BatchException
	 *             Any exception thrown during execution of the batch wrapped as
	 *             BatchException
	 */
	@SuppressWarnings("unchecked")
	
	@Log
	protected boolean execute(BatchContext batchContext) throws BatchException {
		if (batchContext.getBatchInfo().isFailedOver()) {
			Serializable obj = getAttribute(batchNo, CONTEXT_KEYS.JBEAM_SCHEDULED_EXECUTABLE_LIST.name());
			if (obj != null && obj instanceof LinkedList<?>) {
				LinkedList<ScheduledExecutable> scheduledExecutables = (LinkedList<ScheduledExecutable>) obj;
				waitOnCycleToComplete(batchContext, scheduledExecutables);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("In Processor >> BATCH_RUN_DATE = " + batchContext.getBatchInfo().getBatchRunDate());
		}
		setAttribute(batchNo, 
				CONTEXT_KEYS.JBEAM_BATCH_RUN_DATE.name(),
				batchContext.getBatchInfo().getBatchRunDate());
		
		//clear all meta configurable from the main batch executor.
		// No need to clear because of unique listener no assignment in case of
		// Special run as cleanBatchAssignment() is not run for Special runs
		
		// Need to clear only in case of date run as cleanBatchAssignment() will
		// wipe out listener no and reset status eventually making the object
		// eligible for run in next batches which is not desirable
		if (CoreUtil.isDateRun(batchContext.getBatchInfo())) {
			Connection aCon = null;
			IAppDao aDao = DaoFactory.getAppDao();
			if (!batchContext.getBatchInfo().isRevisionRun()) {
				try {
					aCon = batchContext.getApplicationConnection();
					aDao.resetConfigurables(false, true, null, aCon);
				} finally {
					aDao.releaseResources(null, null, aCon);
				}
			}
		}

		// Get the ordered map from the batchContext
		TreeMap<Integer, EntityParams> orderedMap = batchContext.getBatchInfo()
				.getOrderedMap();
		Iterator<Entry<Integer, EntityParams>> orderIter = orderedMap.entrySet().iterator();
		try {
			// Indicator to continue with the proceedings in case for revision
			// run
			// Else, if execution status is POLICY:EX:IP, then it would not
			// continue for ACCOUNT
			// or other entities lower in the precedence order
			Boolean flag = true;
			while (orderIter.hasNext()) {
				Entry<Integer, EntityParams> entry = orderIter.next();
				EntityParams entityParams = entry.getValue();
				if (batchContext.getBatchInfo().isRevisionRun() && flag) {
					if (entityParams.getEntity().equals(
							batchContext.getBatchInfo().getProgressLevelAtLastSavePoint().getExecutionStatus().getEntity())) {
						flag = false;
						processBatch(batchContext, entityParams);
					} else {
						//skip the order as it was already executed in the previous run.
					}
				} else {
					processBatch(batchContext, entityParams);
				}
			}

		} catch (BatchStopException bbse) {
			if (logger.isDebugEnabled()) {
				logger.debug("JOB: IN STOP BATCH EXCEPTION ");
			}
			ExecutionOrder.saveBatchState(batchContext);
		}

		return true;
	}

	/**
	 * Helper method that would assist the execution of the batch <p />
	 * Execution steps -
	 * <OL>
	 * <LI>Checks for meta events i.e. PRE / POST as they have to separately
	 * dealt with and theoretically do not fall in the batch object category If
	 * meta events, then would -
	 * <OL>
	 * <LI>Pro-create as needed and as set in the META_DATA table.
	 * <LI>Invoke specialized method for execution of the meta events
	 * {@link Processor#doExecuteMetaEntities(BatchContext, EntityParams)} /
	 * batch events {@link Processor#doExecuteBatch(BatchContext, EntityParams)}
	 * as needed.
	 * </OL>
	 * </OL>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param entityParams
	 *            The entity and its settings for the batch
	 * @return true if the execution is OK, false otherwise
	 * @throws BatchException
	 *             Any exception raised during execution
	 * @throws BatchStopException
	 *             Special exception to indicate to the system to stop its
	 *             proceedings
	 */
	private Boolean processBatch(BatchContext batchContext,
			EntityParams entityParams) throws BatchException,
			BatchStopException {

		// Set the current entity as received
		setAttribute(batchNo, 
						CONTEXT_KEYS.JBEAM_ENTITY_PARAMS.name(),
						entityParams);

		String entity = entityParams.getEntity();
		if (entity.equals(META_EVENTS.PRE.name())
				|| entity.equals(META_EVENTS.POST.name())) {
			
			if (!CoreUtil.isDateRun(batchContext.getBatchInfo())) {
				//For special runs, reset configurables for particular entities
				IAppDao aDao = DaoFactory.getAppDao();
				Connection aCon = batchContext.getApplicationConnection();
				if(!batchContext.getBatchInfo().isRevisionRun()){
					aDao.resetConfigurables(false, false, entityParams, aCon);
				}
			}
			
			Integer batchNo = batchContext.getBatchInfo().getBatchNo();
			if (entity.equals(META_EVENTS.PRE.name())) {
				ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
						META_EVENTS.PRE.name(),
						ProgressLevel.ACTIVITY.PROCREATION);
				status = META_EVENTS.PRE.name() + " - "
						+ ProgressLevel.ACTIVITY.PROCREATION.name();
			} else {
				ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
						META_EVENTS.POST.name(),
						ProgressLevel.ACTIVITY.PROCREATION);
				status = META_EVENTS.POST.name() + " - "
						+ ProgressLevel.ACTIVITY.PROCREATION.name();
			}
			ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
			procreateConfigurables(batchContext, entityParams);
			doExecuteMetaEntities(batchContext, entityParams);
		} else {
			if (!CoreUtil.isDateRun(batchContext.getBatchInfo())) {
				//For special runs, clean batch assignments for particular entities
				IAppDao aDao = DaoFactory.getAppDao();
				Connection aCon = batchContext.getApplicationConnection();
				if(batchContext.getBatchInfo().isRevisionRun()){
					aDao.cleanRevisionAssignments(entityParams, aCon);
				} else {
					aDao.cleanBatchAssignments(entityParams, aCon);
				}
			}
			doExecuteBatch(batchContext, entityParams);
		}

		return true;
	}

	/**
	 * The method to wait on completion of an internal cycle of execution After
	 * each assignment & scheduling of an internal cycle the system has to wait
	 * on the execution to complete before picking up the next internal cycle
	 * for assignment and scheduling. If the execution is not completed then it
	 * waits for a specified period of time as set in the configuration table
	 * i.e. CORE_CONFIG[CORE - PRE_STATUS_CHECK - WAIT_PERIOD] before polling
	 * again to check the status <p />
	 * 
	 * <b>This method is marked for logging of time as 'EXECUTION'</b>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param scheduledExecutables
	 *            The scheduled executables which it has to check for status
	 * @return true if the wait is over, false otherwise
	 * @throws BatchException
	 *             Any exception occurred during database I/O
	 */
	@LogTime
	private boolean waitOnCycleToComplete(BatchContext batchContext,
			List<ScheduledExecutable> scheduledExecutables)
			throws BatchException {
		if (scheduledExecutables.size() < 1)
			return true;
		IBatchDao bDao = DaoFactory.getBatchDao();
		boolean cycleComplete = false; 
		while (!cycleComplete) {
			try {
				TimeUnit.MILLISECONDS.sleep(Constants.PRE_STATUS_CHECK_WAIT_PERIOD);
			} catch (InterruptedException e) {
				if (logger.isEnabledFor(Level.ERROR)) {
					logger.error(e);
				}
			}
			Connection con = null;
			try {
				con = batchContext.getBATCHConnection();
				cycleComplete = bDao.getListenerExecutionStatuses(scheduledExecutables, con);
			} finally {
				bDao.releaseResources(null, null, con);
			}
			
			if (!cycleComplete ) {
				if (checkAndCancelQueuedListeners(batchContext, scheduledExecutables)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Utility method to run the batch without Process Request Engine
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param scheduledExecutables
	 *            The scheduled executables i.e. listeners
	 * @return list of {@link Listener} scheduled and started
	 * @throws BatchStopException
	 *             Special exception to indicate that the system has to stop
	 */
	List<Listener> startListeners(BatchContext batchContext,
			List<ScheduledExecutable> scheduledExecutables)
			throws BatchStopException {

		List<Listener> returnList = new ArrayList<Listener>();
		for (ScheduledExecutable scheduledExecutable : scheduledExecutables) {

			// Parameters that the listener would get from the
			// PROCESS_REQ_PARAMS table
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("LISTENER_ID", Integer.valueOf(scheduledExecutable
					.getListenerId()));
			//TODO for dev -- add batchNo as param
			paramMap.put("EXECUTION_END_DATE", batchContext.getBatchInfo()
					.getExecutionEndTime());
			BatchContext jc = new BatchContext();
			jc.setRequestParams(new BatchParams(paramMap));

			// Parameters that the listener would get from the PRE Context
			jc.setPreContext(batchContext.getPreContext());

			Listener listener = new Listener(jc);
			listener.setExecutable(scheduledExecutable);

			new Thread(listener).start();
			returnList.add(listener);
		}
		return returnList;

	}

	/**
	 * The end of batch method. <p /> Note: Even if the batch has to be stopped
	 * due to insufficient time or some special case, this method would always
	 * be called. <p /> Ex: If the current batch is 100 and revision 1 and a
	 * case has arose where the batch has to be stopped, then even if there are
	 * more objects to be executed, even then destroy would be called. This may
	 * not be treated and end of batch in its entirety, but a logical completion
	 * of a batch and a revision.
	 * 
	 * @param batchContext
	 *            The batch context
	 * @param shouldCloseBatch
	 *            The boolean to indicate whether the batch is to be closed
	 * @throws BatchException
	 *             Exception thrown during closing of the batch
	 */
	
	@Log
	protected void destroy(BatchContext batchContext, Boolean shouldCloseBatch)
			throws BatchException {
		Integer batchNo = batchContext.getBatchInfo().getBatchNo();
		if (batchContext.isBatchToBeStopped()) {
			if (batchContext.isBatchStateSaved()) {
				ProgressLevel.getProgressLevel(batchNo)
				.setProgressLevel(ProgressLevel.ACTIVITY.STOPPED);
				status = ProgressLevel.ACTIVITY.STOPPED.name();
			} else {
				ProgressLevel.getProgressLevel(batchNo)
				.setProgressLevel(ProgressLevel.ACTIVITY.CLOSURE);
				status = ProgressLevel.ACTIVITY.CLOSURE.name();
				batchContext.getBatchInfo().setEndUser(batchContext.getBatchInfo().getStartUser());
			}
		} else {
			ProgressLevel.getProgressLevel(batchNo)
			.setProgressLevel(ProgressLevel.ACTIVITY.CLOSURE);
			status = ProgressLevel.ACTIVITY.CLOSURE.name();
			batchContext.getBatchInfo().setEndUser(batchContext.getBatchInfo().getStartUser());
		}
		ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
		doClose(batchContext, shouldCloseBatch);

	}

	/**
	 * Helper method to pro-create configurable Meta events. Pro-creation of
	 * Meta events happens differently for fresh batch runs and revision runs
	 * and is as illustrated below. <p /> EX: There are 10 PRE jobs P1, P2 ...
	 * P10, of which P4 fails or time slot is exhausted (during execution of PRE
	 * jobs) thereby giving a chance for revision run. Lets say the batch number
	 * is 1234 and revision 1 <p /> Case 1 : P4 fails. <p /> Case 1a: P4 is
	 * marked as on-fail-exit = 'Y' thereby stopping the batch and giving rise
	 * to statuses as P4 = '99' & P5, P6 ... P10 = 'PC' Case 1b: P4 is marked as
	 * on-fail-exit = 'N' but time slot is exhausted giving rise to statuses P4
	 * = '99'& P5,P6 ... P10 as either 'CO' or 'PC' depending upon when the time
	 * slot is exhausted.
	 * 
	 * In both cases i.e. 1a and 1b if <p /> Case Fresh Batch: Then all '99' and
	 * 'PC' objects would be marked as 'TR' (TeRminated), and all 10 (P1, P2 ...
	 * P10)jobs would be pro-created afresh Case Revision : Batch# 1234 and
	 * Revision #2, then in this case, '99' would be marked as 'PC' and
	 * execution would start from the point where the batch was saved. <p />
	 * <b>Important Note: If the processing for revision #1 had already crossed
	 * the execution of PRE jobs, then the system would not come back to execute
	 * the failed PRE jobs all over again. EX: If revision #1 was stopped, for
	 * any reason while executing POLICY as the entity which has the lower
	 * precedence than that of the PRE (or as set in the COLUMN_MAP table)
	 * then it would not even bother marking it back to 'PC' in revision #2. As
	 * a ground rule, during revision runs, the entities with higher precedence
	 * would not be taken into account. It would be ignored for the current
	 * revision and or a fresh batch would have to be issued to pick up these
	 * jobs.</b>
	 * 
	 * <P /> <b>This method is marked for logging of time as 'PROCREATION'</b>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param entityParams
	 *            The batch entity parameters
	 * @throws BatchException
	 *             Any database I/O exception
	 * @throws BatchStopException
	 *             Special exception to indicate that the system has to stop
	 */
	@LogTime
	@Log
	private void procreateConfigurables(BatchContext batchContext,
			EntityParams entityParams) throws BatchException,
			BatchStopException {
		IBatchDao bDao;
		IAppDao aDao;
		aDao = DaoFactory.getAppDao();
		bDao = DaoFactory.getBatchDao();

		Boolean isRevisionRun = batchContext.getBatchInfo().isRevisionRun();
		Connection aCon = null;
		Connection bCon = null;
		try {
			aCon = batchContext.getApplicationConnection();
			bCon = batchContext.getBATCHConnection();
//			if (isRevisionRun) //reset conf only if revision run.
//				aDao.resetConfigurables(isRevisionRun, entityParams, aCon);
			Boolean proCreate = false;
			if (isRevisionRun) {
				ProgressLevel progressLevelAtResume = batchContext.getBatchInfo().getProgressLevelAtLastSavePoint();
				//EntityParams.Entity=PRE equals REUME.ENTITY=PRE then check for activity level
				if (entityParams.getEntity().equals(progressLevelAtResume.getExecutionStatus().getEntity())) {
					if (progressLevelAtResume.getPrgActivityType().getActivityLevel() < ProgressLevel.ACTIVITY.PROCREATION.getActivityLevel()) {
						proCreate = true;
					}
				} else {
					//EntityParams.Entity=PRE does not equals REUME.ENTITY=PRE
					proCreate = true;
				}
			} else {
				proCreate = true; //explicitly set the pro-create to true.
			}
			if (proCreate) { 
				List<BatchJobMetaData> configurables = 
					bDao.getConfigurables(batchContext.getBatchInfo().getBatchRunDate(), entityParams, bCon);
				aDao.setConfigurables(configurables, aCon, batchContext);
			}
		} finally {
			bDao.releaseResources(null, null, bCon);
			aDao.releaseResources(null, null, aCon);
		}
	}

	/**
	 * Closure of the batch
	 * <OL>
	 * <LI>Sets the closure for the batch and the revision
	 * <LI>Stops all the poller to check for monitor instructions
	 * <LI>Unlocks the Processor for usage
	 * </OL>
	 * <P /> <b>This method is marked for logging of time as 'CLOSURE'</b>
	 * <b>This method is marked for email (upon completion) with type =
	 * {@link EMAIL_TYPE#WHEN_BATCH_ENDS}</b>
	 * 
	 * @param batchContext
	 *            The batch context
	 * @param shouldCloseBatch
	 *            The boolean to indicate whether the batch is to be closed
	 * @throws BatchException
	 *             Any exception thrown during database I/O
	 */
	@Email(type = EMAIL_TYPE.WHEN_BATCH_ENDS)
	@LogTime
	private void doClose(BatchContext batchContext, Boolean shouldCloseBatch)
			throws BatchException {
		
		// If the batch is initiated then close the batch
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		try {
			con = batchContext.getBATCHConnection();
			if (shouldCloseBatch) {
				bDao.closeBatch(batchContext.getBatchInfo(), con);
			}
			
			// Stop the poller
			for (BasePoller poller :  pollerList ) {
				poller.stopPoller();
				poller.interrupt(); //Wake if sleeping.
			}
			
			// Clear the PREContext
			PREContext preContext = batchContext.getPreContext();
			
			for (CONTEXT_KEYS key : CONTEXT_KEYS.values()) {
				preContext.removeAttribute(key.name());
			}
//			batchContext.getBatchInfo().setExecutionEndTime(ProgressLevel.getProgressLevel().getEndDatetime());
			//Progress level gets updated as a post activity due to annotation @LogTime. Therefore the above statement is commented
			//and replaced with the one below.
//			batchContext.getBatchInfo().setExecutionEndTime(new Date());
			// Lock the Batch Processor
			//DAOUtil.lockProcessor(batchContext, false);
			BatchLockHandler.releaseProcessorLock(batchContext);
			
			//Destroy the cache
			ExecutableBatchJobPool.getInstance().destroy(batchContext);
			ExecutionHandler.shutdown();
//			CacheFactory.getCache().shutdown(batchContext);
		} finally {
			bDao.releaseResources(null, null, con);
//			if(logger.isDebugEnabled()) {
//				logger.debug("Shutting down ICDServiceClient");
//			}
//			ICDServiceClient.shutdown();
		}

	}

	/**
	 * Specialized execution method for batch events or jobs <p /> Way it works
	 * -
	 * <OL>
	 * <LI>First checks whether there are executable for the entity (identified
	 * as entityParams) to be executed.
	 * <LI>Repetitively assigns and executes the set of object for the current
	 * criteria (identified in entityParams). Repetition of the check,
	 * assignment and execution is needed for pro-created objects. Once all
	 * executable objects are exhausted, the method returns true <p /> EX: If
	 * the current entity is ACCOUNT and there are 10 objects that pro-creates 4
	 * objects Then the first round would pick up, assign and execute 10
	 * objects. It would again check whether records exits, the answer being yes
	 * 4 pro-created objects, would assign, execute 4 objects, checks again for
	 * any more pro-created objects, the answer being no, returns true <p />
	 * Psuedo code:
	 * <OL>
	 * <LI>Jobs exists ?
	 * <LI>If no, return true
	 * <LI>If yes then assign and execute
	 * <LI>Loop to 1
	 * </OL>
	 * </OL>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param entityParams
	 *            The entity parameters
	 * @throws BatchException
	 *             Any exception occurred during execution of the batch
	 * @throws BatchStopException
	 *             Special exception raised when it has to indicate and or
	 *             received an indication to stop the batch proceedings
	 */
	private void doExecuteBatch(BatchContext batchContext,
			EntityParams entityParams) throws BatchException,
			BatchStopException {

		Integer iIterationCounter = 1;
		Boolean toContinue = false;
		IAppDao aDao = DaoFactory.getAppDao();
		Connection aCon = null;
		try {
			aCon = batchContext.getApplicationConnection();
			toContinue = aDao.moreJobsToExecute(batchContext
					.getBatchInfo().getBatchRunDate(), entityParams, aCon);
			
		} finally {
			aDao.releaseResources(null, null, aCon);
		}
		while (toContinue) {

			List<ScheduledExecutable> scheduledExecutables = Assigner
					.assignAndSchedule(batchContext, entityParams,
							iIterationCounter);
			Integer batchNo = batchContext.getBatchInfo().getBatchNo();
			ProgressLevel.getProgressLevel(batchNo).setExecutionStatus(
					entityParams.getEntity(), ExecutionStatus.EXECUTION);
			ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
			status = entityParams.getEntity() + " - "
					+ ExecutionStatus.EXECUTION;
			if (!executeCycle(batchContext, scheduledExecutables, entityParams,
					iIterationCounter)) {
				break;
			}

			iIterationCounter++;

			try {
				aCon = batchContext.getApplicationConnection();
				toContinue = aDao.moreJobsToExecute(batchContext
						.getBatchInfo().getBatchRunDate(), entityParams,
						aCon);
				
			} finally {
				aDao.releaseResources(null, null, aCon);
			}
		}
	}

	/**
	 * Specialized execution method for meta events i.e. PRE / POST or jobs <p
	 * /> A specialized method is needed as for PRE / POST jobs, the objects
	 * have priority code associated with it which forces the system to wait
	 * till objects of higher priority to be executed before. <p /> EX: PRE
	 * objects having priority code 2 have to wait execution before all PRE
	 * objects with priority code 1 have completed execution. The same follows
	 * for POST objects Additionally if a PRE / POST object marked as
	 * on-fail-exit as 'Y' then the system has to stop its execution safely.
	 * Moreover the system also takes care of pro-created objects. <p />
	 * Important Note: <b> The system ignores pro-created meta events with a
	 * higher precedence or priority. EX: PRE object 'P' having priority code
	 * '3' should pro-create only those PRE objects having same or lower
	 * precedence than the current one i.e 3, 4 ... n, for the current batch to
	 * pick it up. If 'P' pro-creates say object Pn with priority code 1 or 2,
	 * it would be ignored for the current batch </b>
	 * 
	 * Way it works -
	 * <OL>
	 * <LI>Copy of the original entity values is created for later probable
	 * usage
	 * <LI>If all the objects for the current entity (PRE / POST) are to be
	 * executed then a distinct set of priority codes are fetched.
	 * <LI>Assignment and scheduling follows for internal smaller cycles
	 * <LI>Internal smaller cycle would be run or executed for each priority
	 * code and also for procreated objects
	 * <LI>Once completed would take the next priority code and execute it
	 * iteratively.
	 * <LI>Process continues until no objects are to be executed. Note: Please
	 * read 'Important Note'.
	 * <LI>In case of special exception {@link BatchStopException}, the original
	 * values stored in step #1 is set back into the entity parameter or
	 * definition to be saved for revision runs.
	 * </OL>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param entityParams
	 *            The batch entity parameters i.e. for PRE / POST
	 * @throws BatchException
	 *             Any database I/O exception
	 * @throws BatchStopException
	 *             Special exception raised when it has to indicate and or
	 *             received an indication to stop the batch proceedings
	 */
	private void doExecuteMetaEntities(BatchContext batchContext,
			EntityParams entityParams) throws BatchException,
			BatchStopException {

		List<GroupInfo> values = entityParams.getValues();

		// Original values store, in case of stop exception to set back into the
		// entity parameters or definition
		List<GroupInfo> originalValues = new ArrayList<GroupInfo>(values);

		Boolean isAll = false;
		if (values.get(0).getEntityValue().equals("ALL"))
			isAll = true;

		if (isAll) {
			// Get the distinct set and set it in the values
			// This is only done for the PRE/POST as there is dependency
			// between the priority codes
			IAppDao aDao = DaoFactory.getAppDao();
			Connection aCon = null;
			List<GroupInfo> distinctSet;
			try {
				aCon = batchContext.getApplicationConnection();
				distinctSet = aDao.getDistinctSet(
						batchContext.getBatchInfo().getBatchRunDate(),
						entityParams, aCon);
				
			} finally {
				aDao.releaseResources(null, null, aCon);
			}
			values = distinctSet;
		}

		Integer iIterationCounter = 1;
		Iterator<GroupInfo> iter = values.iterator();
		IAppDao aDao = DaoFactory.getAppDao();
		while (iter.hasNext()) {

			GroupInfo priorityGroup = iter.next();
			List<GroupInfo> newTempList = new ArrayList<GroupInfo>();

			newTempList.add(new GroupInfo(priorityGroup.getEntityValue()));
			entityParams.setValues(newTempList);

			Boolean toContinue = false;
			Connection aCon = null;
			try {
				aCon = batchContext.getApplicationConnection();
				toContinue = aDao.moreJobsToExecute(batchContext
						.getBatchInfo().getBatchRunDate(), entityParams, aCon);
				
			} finally {
				aDao.releaseResources(null, null, aCon);
			}

			while (toContinue) {
				try {
					logger.info("processor : " + this);
					logger.info("batchContext : " + batchContext);
					LinkedList<ScheduledExecutable> scheduledExecutables = Assigner
							.assignAndSchedule(batchContext, entityParams,
									iIterationCounter);
					setAttribute(batchNo, CONTEXT_KEYS.JBEAM_SCHEDULED_EXECUTABLE_LIST.name(), scheduledExecutables);
					Integer batchNo = batchContext.getBatchInfo().getBatchNo();
					ProgressLevel.getProgressLevel(batchNo)
							.setExecutionStatus(entityParams.getEntity(),
									ExecutionStatus.EXECUTION);
					ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
					status = entityParams.getEntity() + " - "
							+ ExecutionStatus.EXECUTION;
					if (!executeCycle(batchContext, scheduledExecutables,
							entityParams, iIterationCounter)) {
						break;
					}
				} catch (BatchStopException bbse) {
					// It is important to set back the original values
					// as in case of stop exception it would serialize the state
					// into a file
					// for revision run
					entityParams.setValues(originalValues);
					throw bbse;
				} finally {
					batchContext.getPreContext().removeAttribute(CONTEXT_KEYS.JBEAM_SCHEDULED_EXECUTABLE_LIST.name());
				}
				iIterationCounter++;
				
				try {
					aCon = batchContext.getApplicationConnection();
					toContinue = aDao.moreJobsToExecute(batchContext
							.getBatchInfo().getBatchRunDate(), entityParams, aCon);
					
				} finally {
					aDao.releaseResources(null, null, aCon);
				}
			}
		}
	}

	/**
	 * Executes an internal cycle for the supplied entity parameters. This
	 * method is ignorant to any kind of events, PRE, BATCH and POST. It is
	 * called from all the specialized execution methods and a simple process.
	 * In DEV mode, it starts the listeners and waits for the listeners to
	 * complete execution
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @param scheduledExecutables
	 *            The scheduled executables i.e. listeners
	 * @param entityParams
	 *            The entity parameters
	 * @param cycleCounter
	 *            The internal cycle counter
	 * @return true if execution of the method is OK, false otherwise
	 * @throws BatchException
	 *             Any exception while creating / execution of listeners as
	 *             Threads, or database I/O
	 * @throws BatchStopException
	 *             Special exception raised when it has to indicate and or
	 *             received an indication to stop the batch proceedings
	 */
	@Marker
	private boolean executeCycle(BatchContext batchContext,
			List<ScheduledExecutable> scheduledExecutables,
			EntityParams entityParams, Integer cycleCounter)
			throws BatchException, BatchStopException {

		List<Listener> listeners = null;

		// This mode specific condition is needed to start the listeners
		// manually, as otherwise it would be done by the Process Request Engine
		if (Constants.MODE.equals(Constants.DEV))
			listeners = startListeners(batchContext, scheduledExecutables);

		Integer batchNo = batchContext.getBatchInfo().getBatchNo();
		ProgressLevel.getProgressLevel(batchNo)
				.setProgressLevel(entityParams.getEntity(),
						ProgressLevel.ACTIVITY.EXECUTION, cycleCounter);
		ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
		ProgressLevel.getProgressLevel(batchNo).setExecutionStatus(entityParams.getEntity(), ExecutionStatus.EXECUTION);
		status = entityParams.getEntity() + " - " + ProgressLevel.ACTIVITY.EXECUTION.name()
				+ " - " + cycleCounter;
		waitOnCycleToComplete(batchContext, scheduledExecutables);

		// This mode specific condition is needed to check whether there is an
		// indicator
		// from any of the spawned listeners to stop the batch
		// If MODE = DEV then iterate and check, else if MODE = PRE, then check
		// from the
		// batch context
		if (Constants.MODE.equals(Constants.DEV)) {
			for (Listener l : listeners)
				if (l.getListenerContext().isBatchToBeStopped())
					throw new BatchStopException();
		} else {
			if (batchContext.isBatchToBeStopped())
				throw new BatchStopException();
		}

		return true;

	}

	/**
	 * The additional initialization needed Steps -
	 * <OL>
	 * <LI>Sets the system information on which the current batch is being run
	 * <LI>Updates the instruction log
	 * <LI>Cleans the assignments for the batch and or the revision
	 * <LI>Starts the monitor instruction poller to poll on any messages from
	 * the monitor
	 * </OL>
	 * 
	 * <P /> <b>This method is marked for logging of time as 'INITIALIZATON'</b>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @throws BatchException
	 *             Any database I/O exceptions
	 */
	@LogTime
	private void doInit(BatchContext batchContext) throws BatchException {

		if (!batchContext.getBatchInfo().isFailedOver()) {
			// Set the system information
			IBatchDao bdao = DaoFactory.getBatchDao();
			Connection bcon = null;
			try {
				bcon = batchContext.getBATCHConnection();
				bdao.setSystemInfo(batchContext.getBatchInfo(), bcon);
				bdao.updateInstructionLog(batchContext.getBatchInfo(), bcon);
				if(logger.isDebugEnabled()) {
					logger.debug("updateInstructionLog completed >>bcon =  " + bcon.toString());				
				}
				
			} finally {
				bdao.releaseResources(null, null, bcon);
			}
			
			if (CoreUtil.isDateRun(batchContext.getBatchInfo())) {
				// Update the instruction log
				IAppDao aDao = DaoFactory.getAppDao();
				Connection acon = null;
				try {
					acon = batchContext.getApplicationConnection();
					if(logger.isDebugEnabled()) {
						logger.debug("Going to clean batch assignments");				
					}
					if (!batchContext.getBatchInfo().isRevisionRun()) {
						// Clean the existing batch jobs marked '99', 'UC', 'SP' and
						// nullify the
						// listener indicator
						aDao.cleanBatchAssignments(acon);
					} else {
						aDao.cleanRevisionAssignments(acon);
					}
					if(logger.isDebugEnabled()) {
						logger.debug("Cleaning of batch assignments completed");				
					}
				} finally {
					aDao.releaseResources(null, null, acon);
				}
			}

		}

		// Start the poller to poll on the monitor instruction
		pollerList.add(new MonitorInstructionPoller(batchContext));
		if(logger.isDebugEnabled()) {
			logger.debug("MonitorInstructionPoller added");				
		}
		pollerList.add(new BatchJobMonitorPoller(batchContext));
		if(logger.isDebugEnabled()) {
			logger.debug("BatchJobMonitorPoller added");				
		}
		for (BasePoller poller : pollerList) {
			if(logger.isDebugEnabled()) {
				logger.debug("In for loop of pollerList");				
			}
			new Thread(poller).start();
			if(logger.isDebugEnabled()) {
				logger.debug("["+poller.getName()+"] Started");				
			}
		}
	}

	/**
	 * Validates the batch parameters. Steps -
	 * <OL>
	 * <LI>If fresh batch sets up the execution order, else reads the last saved
	 * state of the batch and sets up the execution order accordingly
	 * <LI>Fetch the object mapping definitions as defined in the OBJECT_MAP and
	 * sets it up in the context
	 * </OL>
	 * 
	 * <P /> <b>This method is marked for logging of time as 'EXECUTION
	 * ORDER'</b> <b>This method is marked for email (upon completion) with type
	 * = {@link EMAIL_TYPE#WHEN_BATCH_STARTS}</b>
	 * 
	 * @param batchContext
	 *            The context for the batch
	 * @throws BatchException
	 *             Any database I/O exceptions
	 */
	@Email(type = EMAIL_TYPE.WHEN_BATCH_STARTS)
	@LogTime
	private void doValidate(BatchContext batchContext) throws BatchException {
		if(logger.isDebugEnabled()) {
			logger.debug("In doValidate");				
		}
		// Check whether the batch is run for a revision, if yes, then load the
		// save point file
		// and update the batch context
		// in case the batchContext.getBatchInfo().isFailedOver() then
		// it may be possible that it faild over before the ordered map was
		// created. Therefore
		// the isFailedOver() simply won't work.
		if (batchContext.getBatchInfo().getOrderedMap() == null) {

			if (!batchContext.getBatchInfo().isRevisionRun()) {
				ExecutionOrder.setExecutionOrder(batchContext);
			} else {
				ExecutionOrder.updateBatchState(batchContext);
			}
		}
		if (getAttribute(batchNo, 
				CONTEXT_KEYS.JBEAM_OBJECT_MAP.name()) == null) {
			// Get the object map and set it into the context
			HashMap<String, ObjectMapDetails> objectMapping;
			IBatchDao bDao = DaoFactory.getBatchDao();
			Connection con = null;
			try {
				con = batchContext.getBATCHConnection();
				objectMapping = bDao.getObjectMap(batchContext
						.getBatchInfo().getBatchRunDate(), con);
				
			} finally {
				bDao.releaseResources(null, null, con);
			}

			// Sets the object map for the batch as set in the OBJECT_MAP table
			setAttribute(batchNo, 
					CONTEXT_KEYS.JBEAM_OBJECT_MAP.name(),
					objectMapping);
		}
	}

	/**
	 * Returns the status for Process Request Engine to display onto the PRE
	 * console
	 */
	
	public String getStatus() {
		return status;
	}

	/**
	 * Call back method when Process Request Engine decides to halt its and also
	 * those in 'executing' state under PRE.
	 */
	
	public void terminate(String reason) {
		setAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT.name(), "Y");
		setAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT_REASON.name(),
				CLOSURE_REASON.PRE_ISSUED_STOP.name());
	}
	
	private void printVersion() {
		String[] strBundleDetailsArray = getBundleDetails();
		if (strBundleDetailsArray[0].equals("Unknown")) {
			logger.error("Illegal Usage. Please make use of the distributed files.");
		} else {
	        StringBuilder sb = new StringBuilder();
			sb.append("Product Name: \"");
	        sb.append(strBundleDetailsArray[0]);
	        sb.append("\" Version: \"");
	        sb.append(strBundleDetailsArray[1]);
	        sb.append("\" Packaged On \"");
	        sb.append(strBundleDetailsArray[2]);
	        sb.append("\" Build Number \"");
	        sb.append(strBundleDetailsArray[3]);
	        sb.append("\"");
	        if (logger.isEnabledFor(LogLevel.NOTICE)) {
	        	logger.log(LogLevel.NOTICE, sb.toString());
	        }
		}
	}
	
	private String[] getBundleDetails() {
        String[] strBundleDetailsArray_ = new String[] {"Unknown", "Unknown", "Unknown", Integer.MIN_VALUE + ""};
        String localFile = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        localFile = localFile.concat("!/");
        String tmpString = "jar:";
        String localJarFileString = tmpString.concat(localFile);
        URL localJarFileURL;
        try {
            localJarFileURL = new URL(localJarFileString);
            JarURLConnection localJarFile = (JarURLConnection) localJarFileURL.openConnection();
            Manifest mf = localJarFile.getManifest();
            Attributes attributes = mf.getMainAttributes();
            strBundleDetailsArray_[0] = (String) attributes.getValue("Bundle-Name");
            strBundleDetailsArray_[1] = (String) attributes.getValue("Bundle-Version");
            strBundleDetailsArray_[2] = (String) attributes.getValue("Bundled-On");
            strBundleDetailsArray_[3] = (String) attributes.getValue("Build-Number");
        } catch (MalformedURLException e) {
            //do nothing
        } catch (FileNotFoundException fnfe) {
            //do nothing
        } catch (IOException ioe) {
            //do nothing
        }
        return strBundleDetailsArray_;
	}
	
	/**
	 * Checks and cancels queued requests if any. 
	 * Return true in case all scheduled executables are cancelled else returns false.
	 *   
	 * @param batchContext {@link BatchContext}
	 * @param scheduledExecutables {@link ScheduledExecutable}
	 * @return Boolean
	 * @throws BatchException
	 */
	private Boolean checkAndCancelQueuedListeners(BatchContext batchContext, List<ScheduledExecutable> scheduledExecutables) throws BatchException {
		if (!batchContext.getPreContext().isPREActivelyScanning()) {
			IBatchDao bDao = DaoFactory.getBatchDao();
			batchContext.getBatchInfo().setEndUser("stopEng");
			setAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT.name(), "Y");
			setAttribute(batchNo, CONTEXT_KEYS.JBEAM_EXIT_REASON.name(), CLOSURE_REASON.USER_INTERRUPTED.name());
			Connection con = null;
			try {
				con = batchContext.getBATCHConnection();
				bDao.cancelListenersQueuedRequests(scheduledExecutables, con);
			} finally {
				bDao.releaseResources(null, null, con);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * @param batchContext
	 * @param e
	 * @throws BatchException
	 */
	private void updateInstructionLogError(BatchContext batchContext, BatchInProgressException e) throws BatchException{
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		try{
			con = batchContext.getBATCHConnection();
			Integer instrSeqNo = (Integer)batchContext.getRequestParams().getProcessRequestParams().get(PROCESS_REQUEST_PARAMS.INSTRUCTION_LOG_SEQ.name());
			bDao.updateInstructionLogError(instrSeqNo, e.getMessage(), con);
		} catch(BatchException ex){
			throw ex;
		}
	}

}

/*
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/Processor.java          $
 * 
 * 44    7/06/10 11:01a Kedarr
 * Changes made to handle another instance of runnign batch.
 * 
 * 43    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 42    4/27/10 9:29a Kedarr
 * Changed to add another progress level activity for stopped batch.
 * 
 * 41    4/22/10 2:53p Kedarr
 * Reverted the addition of isStoppedbatch.. needs to be added later..
 * 
 * 40    4/22/10 1:31p Kedarr
 * Changes related to refactoring (renamed) the method stop batch to is batch to be stopped in batch context
 * also added new checks for stopping the batch in between.
 * 
 * 39    4/21/10 2:12p Kedarr
 * Added a explict pro-create flag in case it is not a revision run.
 * 
 * 38    4/21/10 12:10p Kedarr
 * Changes made to make use of the enum in progress level and during the pro-creation method.
 * 
 * 37    4/20/10 2:01p Kedarr
 * Added method to check if the configurable is already completed or not using the log table. If so then
 * there is no need to pro-create the same object again,  otherwise do so.
 * 
 * 36    4/20/10 12:57p Kedarr
 * Changes made to call the reset configurables globally irrespective of whether its a Revision Run or not.
 * 
 * 35    4/19/10 3:29p Kedarr
 * Changes made to call the reset configurables globally and only if not a revision run.
 * 
 * 34    4/19/10 11:42a Kedarr
 * Instead of key set used entry set.
 * 
 * 33    4/06/10 3:13p Mandar.vaidya
 * Added new parameter executionDate to setConfigurables method.
 * 
 * 32    4/06/10 2:17p Kedarr
 * Changes made to update the context batch info with the execution end datetime.
 * 
 * 31    3/30/10 2:33p Kedarr
 * Changes made to use a Linked List as well as wait for cycle to complete in case of fail over.
 * 
 * 30    3/26/10 1:33p Kedarr
 * Added a new poller and its corresponding changes.
 * 
 * 29    3/25/10 1:17p Kedarr
 * Changes made to add batchInfo in the batchContext
 * 
 * 28    3/25/10 9:57a Kedarr
 * As the context is different for each batch job the get batch info method should not check if the object
 * is in the context.
 * 
 * 27    3/19/10 7:07p Mandar.vaidya
 * Changes made to not close the connection object in the DAO. Corresponding changes made in the caller to
 * close the connection.
 * 
 * 26    3/19/10 6:28p Mandar.vaidya
 * Correction for connection leak.
 * 
 * 25    3/12/10 5:39p Mandar.vaidya
 * Initialised connection object.
 * 
 * 24    3/11/10 11:49a Kedarr
 * Changes made for setting failed over status in progress level.
 * 
 * 23    3/09/10 5:39p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao
 * Factory is used to fetch the appropriate dao. Changes made to correct possible connection leaks.
 * 
 * 22    3/04/10 11:26a Grahesh
 * Changes made in init so that the revision number is not generated for a fail over batch.
 * 
 * 21    3/04/10 10:52a Grahesh
 * formatted the code and called the batch dao method to update the fail over status. Also, the do initiate
 * revision is not necessary to be called in case of fail over.
 * 
 * 20 3/03/10 5:36p Grahesh Removed batchContext.getBATCHConnection() from
 * called IBatchDao constructor and added in called methods. Removed
 * batchContext.getApplicationConnection() from called IBatchDao constructor and
 * added in called methods.
 * 
 * 19 2/25/10 10:48a Grahesh Changes made to make use of context keys enum from
 * constants and for fail over of batch.
 * 
 * 18 2/15/10 12:20p Mandar.vaidya Changes made to incorporate Serializable
 * change that was made in PRE V1.0 R 28
 * 
 * 17 1/07/10 6:25p Grahesh Updated Java Doc comments
 * 
 * 16 1/07/10 6:03p Grahesh Updated Java Doc comments
 * 
 * 15 1/06/10 5:19p Grahesh Arranged the annotations
 * 
 * 14 1/06/10 1:59p Grahesh Implementing ITerminateProcess
 * 
 * 13 1/06/10 1:13p Grahesh Logic for BATCH_RUN_DATE corrected.
 * 
 * 12 1/04/10 4:41p Grahesh Removing all the attributes set in the PREContext as
 * a part of the closing activity.
 * 
 * 11 12/29/09 12:43p Grahesh Added annotation for logging to lifecycle methods.
 * 
 * 10 12/24/09 2:33p Grahesh Implemented the logic for MAX_DATE for which the
 * batch could be run.
 * 
 * 9 12/23/09 6:43p Grahesh Default implementation for those batches for which
 * the name and the type is not provided.
 * 
 * 8 12/23/09 4:12p Grahesh Implementing IProcessStatus for display of status on
 * to the PRE console
 * 
 * 7 12/23/09 1:53p Grahesh Using BATCH_ENDS_IN_MINUTES instead of
 * BATCH_END_TIME
 * 
 * 6 12/23/09 11:55a Grahesh Changes done to separate batch run date from batch
 * execution date time
 * 
 * 5 12/21/09 5:14p Grahesh Added check canHaveRevision
 * 
 * 4 12/18/09 12:51p Grahesh Updated the comments
 * 
 * 3 12/18/09 11:34a Grahesh Updated the comments
 * 
 * 2 12/17/09 11:46a Grahesh Initial Version
 */