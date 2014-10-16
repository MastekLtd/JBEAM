/**
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
 */
package com.stgmastek.core.dao.defaultimpl;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import stg.pr.engine.IProcessRequest;
import stg.utils.CSettings;

import com.stgmastek.core.aspects.DatabaseAgnosticCandidate;
import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.common.BaseDAO;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.util.BatchInfo;
import com.stgmastek.core.util.BatchJobMetaData;
import com.stgmastek.core.util.BatchLockHandler;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.CConfig;
import com.stgmastek.core.util.ColumnLookup;
import com.stgmastek.core.util.Configurations;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.CoreUtil;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.GroupInfo;
import com.stgmastek.core.util.LookupTable;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.OrderLookup;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.QueryGenerator;
import com.stgmastek.core.util.ScheduledExecutable;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;
import com.stgmastek.util.ResultSetMapper;
 
/**
 * DAO class for all BATCH database (and PRE) database related activities.
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 * @see com.stgmastek.core.dao.IBaseDao
 * @see IBatchDao
 * 
 */
public class BatchDAO extends BaseDAO implements IBatchDao {

	private static final Logger logger = Logger.getLogger(BatchDAO.class);
	
	/** Query to get the configurations as set in the CORE_CONFIG */
	static final String GET_CORE_CONFIGURATIONS = "select code1,code2, code3, value, value_type from configuration order by code1, code2, code3";

	/** Query to insert into the BATCH_LOG table */
	static final String SET_BATCH_LOG = "insert into log (seq_no, batch_no, batch_rev_no, be_seq_no, task_name, obj_exec_start_time,  status, sys_act_no, user_priority, priority_code1, priority_code2, pre_post, job_type, line, subline, broker, policy_no, policy_renew_no, veh_ref_no, cash_batch_no, cash_batch_rev_no, gbi_bill_no, print_form_no, notify_error_to, date_generate, generate_by, rec_message, job_desc, object_name, date_executed, list_ind, entity_type, entity_code, ref_system_activity_no, cycle_no, used_memory_before) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	/** Query to generate the batch log sequence no */
	@DatabaseAgnosticCandidate
	static final String BATCH_LOG_SEQ = "SELECT log_seq.nextval FROM DUAL";

	/** Query to update the BATCH_LOG table */
	static final String UPDATE_BATCH_LOG = "update log set obj_exec_end_time = ?, status = ?, date_generate = ?, date_executed = ?, error_type = ?, error_description = ?, used_memory_after = ?  WHERE seq_no = ?";
	
	/** Query to get the PROGRESS LEVEL indicator */
	@DatabaseAgnosticCandidate
	static final String GET_PROGRESS_LEVEL_IND = "select nvl(max(indicator_no), 0) + 1 from progress_level where batch_no = ? and batch_rev_no = ?";
	
	/** Query to insert a new record into the PROGRESS_LEVEL table */
	static final String SET_PROGRESS_LEVEL = "insert into progress_level (batch_no, batch_rev_no, indicator_no, prg_level_type, prg_activity_type, cycle_no, status, start_datetime, end_datetime, failed_over) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/** Query to set the system information on which the batch is run */
	static final String SET_SYSTEM_INFO = "insert into system_info (batch_no,batch_rev_no,java_version,pre_version,os_config,output_dir_path,output_dir_free_mem, used_memory, max_memory) values (?,?,?,?,?,?,?,?,?)";
	
	/** Query to update the PROGRESS_LEVEL table with the END time */
	static final String UPDATE_PROGRESS_LEVEL = "update progress_level set status = ?, end_datetime = ?, error_desc = ?, failed_over = ? where batch_no = ? and  batch_rev_no = ? and indicator_no = ?";
	
	/** Gets the value from the lookup table */
	static final String GET_LOOKUP_TABLE = "select entity entity,lookup_column lookup_column,lookup_value lookup_value,value_column value_column,precedence_order precedence_order, on_error_fail_all on_error_fail_all from column_map ORDER BY precedence_order ASC";
	
	/** 
	 * Query to lock a processor request, so other 
	 * scheduled (or on the fly) processor could be shut down immediately
	 */
	@DatabaseAgnosticCandidate
	static final String GET_PROCESSOR_LOCK = "update batch_lock set req_id = ?, indicator = ?, lock_time = localtimestamp";
	
	@DatabaseAgnosticCandidate
	static final String GET_PROCESSOR_LOCK_STATUS = "select req_id from batch_lock where indicator = ?";
	
	@DatabaseAgnosticCandidate
	static final String GET_LOCK_STATUS = "select indicator from batch_lock";
	
	/** Query to unlock a processor request */
	static final String RELEASE_PROCESSOR_LOCK = "update batch_lock set indicator = ? ";
	
	/** Query to fetch the request id for new request */
	@DatabaseAgnosticCandidate	
	static final String PRE_GET_REQUEST_ID = "select process_req_seq.nextval from dual";
	
	/** Query to create a new request */
	static final String PRE_INSERT_NEW_REQUEST = "insert into process_request (req_id , user_id , req_dt , req_stat , process_class_nm , grp_st_ind , req_type , priority, stuck_thread_limit, stuck_thread_max_limit, job_name  ) values( ? , 'ADMIN' , ? , 'Q' , 'com.stgmastek.core.logic.Listener' , 'S' , 'GENERAL' , 999, ?, ?, ?) ";
	
	/** Query to insert request parameters */
	static final String PRE_INSERT_REQUEST_PARAMS = "insert into process_req_params (  req_id , param_no , param_fld , param_val , param_data_type ) values ( ? , ? , ? , ? , ? )";
	
	/** Query to get request parameters for the first revision run*/
	static final String PRE_GET_REQUEST_PARAMS = "select PARAM_FLD, PARAM_VAL from process_req_params where req_id = (select process_id from batch where batch_rev_no = 1 and batch_no = ?)";
	
	/** Query to update the status of the Process Request Engine's request */
	static final String PRE_UPDATE_STATUS = "update process_request set req_stat='S' where req_id = ?";
		
	/**
	 * Query to cancel the queued request when the PRE status becomes in-active.
	 */
	static final String CANCEL_PROCESS_REQUEST = "Update process_request set req_stat = ? where req_id = ? and req_stat = 'Q'";
	
	/** Query to get the revision number and other information */
	@DatabaseAgnosticCandidate
	static final String GET_REVISION_DATA = "select batch_name, batch_type, (batch_rev_no + 1) from batch where batch_no = ? and batch_rev_no = (select max(batch_rev_no) from batch where batch_no = ?)";
	
	/** Query to get the batch number */
	@DatabaseAgnosticCandidate	
	static final String GET_BATCH_NO = "select batch_seq.nextval from dual";
	
	/** Query to create a batch record */
	static final String CREATE_BATCH = " insert into batch (  batch_no, batch_rev_no, batch_name, batch_type, exec_start_time, exec_end_time, batch_start_user, batch_end_user, process_id, instruction_seq_no) values (?, ?, ?, ?, ?, null, ?, null, ?, ?) ";
	
	/** Query to close a batch record */
	static final String CLOSE_BATCH = " update  batch set exec_end_time = ?, batch_end_user = ?, batch_end_reason = ? where batch_no = ? and batch_rev_no = ? ";
	
	/** Query to update a batch record for fail over mechanism */
	static final String UPDATE_BATCH_FOR_FAIL_OVER = " update  batch set failed_over = ? where batch_no = ? and batch_rev_no = ? ";
	
	/** Query to get the object map definitions */
	@DatabaseAgnosticCandidate
	static final String GET_OBJECT_MAP = "select id id, object_name, object_type, default_values, on_fail_exit, on_fail_email, min_time, avg_time, max_time, escalation_level, case_data from object_map where ? between eff_date and nvl(exp_date, ?)";
	
	
	/** Query to update the instruction log */
	static final String UPDATE_INSTRUCTION_LOG = "update instruction_log set batch_no = ?, batch_rev_no = ?, batch_action = ?, batch_action_time = ? where seq_no = ?";
	
	/** Query to update the instruction log */
	static final String UPDATE_INSTRUCTION_LOG_ERROR = "update instruction_log set  batch_action = ?, message_param = ? where seq_no = ?";
	
	/** Query to check the closure reason for previous batch revision */
	static final String CHECK_PREVIOUS_CLOSURE_REASON = "select batch_end_reason from batch where batch_no = ? and batch_rev_no = (select max(batch_rev_no) from batch where batch_no = ?)";
	
	static final String GET_RUNNING_JOBS = "SELECT be_seq_no, object_name, job_desc, task_name, job_type, obj_exec_start_time " +
			"FROM log " +
			"WHERE batch_no = ? " +
			"AND batch_rev_no = ? " +
			"AND obj_exec_end_time is null";
	
	static final String GET_LESS_THAN_MIN_TIME_EXECUTED_JOBS = 
		" SELECT a.be_seq_no, a.object_name, a.job_desc, a.task_name, a.job_type, a.obj_exec_start_time, a.obj_exec_end_time, a.status, b.min_time " +
		" FROM log a, object_map b " +
		" WHERE a.object_name = b.id " +
		" AND   b.min_time > " +
		"		extract(day from (a.obj_exec_end_time - a.obj_exec_start_time))*24*3600 " +
		"	 +  extract(hour from (a.obj_exec_end_time - a.obj_exec_start_time))*3600 " +
		"	 +  extract(minute from (a.obj_exec_end_time - a.obj_exec_start_time))*60 " +
		"    +  extract(second from (a.obj_exec_end_time - a.obj_exec_start_time)) * 1000 " +
		" AND   a.batch_no = ? " +
		" AND   a.batch_rev_no = ? " +
		" AND   b.min_time_escl = 'Y'" +
		" AND   a.status in ('" + OBJECT_STATUS.COMPLETED.getID() + "', '" + OBJECT_STATUS.FAILED.getID() + "')";

	static final String CHECK_CONFIGURABLE_IF_COMPLETED = "SELECT 1 FROM log WHERE batch_no = ? AND status = '" + OBJECT_STATUS.COMPLETED.getID() + "' AND pre_post = ? AND object_name = ?";

	/** Query to update CONFIGURATION table for ICD password */
	static final String UPDATE_CONFIGURATION = " update CONFIGURATION set VALUE = ? " +
			"where CODE1 = 'CORE' " +
			"and CODE2 = 'ICD_SERVICE' " +
			"and CODE3 = 'PASSWORD'";
	
	/** Gets the values from ORDERBY_MAP table*/
	static final String GET_ORDERBY_MAP_TABLE = "select entity, order_by_column from orderby_map";
	
	/**
	 * Public constructor takes the connection as argument
	 * 
	 */
	public BatchDAO() {
		super();
	}

	/**
	 * Schedules the listeners as needed  
	 * 
	 * @param executionStartTime
	 * 		  The batch execution start time 
	 * @param executionEndTime
	 * 		  The execution end time, optional 	
	 * @param noOfListeners
	 * @return list of the scheduled executables 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@DatabaseAgnosticCandidate
	@Log
	public LinkedList<ScheduledExecutable> scheduleListeners(Integer batchNo, Date executionStartTime, 
										Date executionEndTime, 
										Integer noOfListeners,
										Connection connection) 
										throws DatabaseException {
		
		LinkedList<ScheduledExecutable> scheduledExecutables = new LinkedList<ScheduledExecutable>();
		PreparedStatement pstmtSeq = null;
		PreparedStatement pstmtPR = null;
		PreparedStatement pstmtPRP = null;
		ResultSet rs = null;
		int listenerId = -1;
		try {
			//Turn off the connection.autoCommit		
			connection.setAutoCommit(false);	
			
			Integer requestId = 0;
			listenerId = CoreUtil.getListenerNo(batchNo, 1);
			for(int i=1 ;i<=noOfListeners; i++){
				ScheduledExecutable scheduledExecutable = new ScheduledExecutable();
				//First get the sequence value
				pstmtSeq = connection.prepareStatement(PRE_GET_REQUEST_ID);
				rs = pstmtSeq.executeQuery();
				rs.next();
				requestId = rs.getInt(1);
				rs.close();rs = null;
				pstmtSeq.close();pstmtSeq = null;
				
				//Insert into Process Request 
				pstmtPR = connection.prepareStatement(PRE_INSERT_NEW_REQUEST);
				pstmtPR.setObject(1, requestId);
				pstmtPR.setTimestamp(2, new java.sql.Timestamp(executionStartTime.getTime()));
				pstmtPR.setObject(3, Integer.valueOf(Configurations.getConfigurations().getConfigurations("CORE", "PRE_STUCK_THREAD", "LIMIT")));
				pstmtPR.setObject(4, Integer.valueOf(Configurations.getConfigurations().getConfigurations("CORE", "PRE_STUCK_THREAD", "MAX_LIMIT")));
				pstmtPR.setObject(5, "Listener #" + listenerId);
				pstmtPR.addBatch();
				
				//Insert into Process Request Params
				pstmtPRP = connection.prepareStatement(PRE_INSERT_REQUEST_PARAMS);
				pstmtPRP.setObject(1, requestId);
				pstmtPRP.setObject(2, 1);
				pstmtPRP.setObject(3, "LISTENER_ID");
				pstmtPRP.setObject(4, listenerId);
				pstmtPRP.setObject(5, "I");				
				pstmtPRP.addBatch();
				pstmtPRP.setObject(1, requestId);
				pstmtPRP.setObject(2, 2);
				pstmtPRP.setObject(3, "BATCH_NO");
				pstmtPRP.setObject(4, batchNo);
				pstmtPRP.setObject(5, "I");				
				pstmtPRP.addBatch();
				if(executionEndTime != null){
					pstmtPRP.setObject(1, requestId);
					pstmtPRP.setObject(2, 3);
					pstmtPRP.setObject(3, "EXECUTION_END_DATE");
					pstmtPRP.setObject(4, new SimpleDateFormat(CSettings.get("pr.paramdateformat") + " " + CSettings.get("pr.paramtimeformat")).format(executionEndTime));
					pstmtPRP.setObject(5, "TS");				
					pstmtPRP.addBatch();
				}
				scheduledExecutable.setReqId(requestId);
				scheduledExecutable.setListenerId(listenerId++);//Increment the listenerId after assignment
				scheduledExecutables.add(scheduledExecutable);
			}
			if (pstmtPR != null && 	pstmtPRP != null) {
				pstmtPRP.executeBatch();
				pstmtPR.executeBatch();
				connection.commit();
			}
			//Issue Commit		
			
			return scheduledExecutables;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				logger.error(e1);
			}
			throw new DatabaseException(e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException se) {
				logger.error(se);
			}
			releaseResources(null, pstmtPR, null);
			releaseResources(null, pstmtPRP, null);
		}
	}

	/**
	 * Returns the listeners execution status
	 * 
	 * @param scheduledExecutables
	 *            The list of scheduled listeners
	 * @return true if all listeners have completed their execution, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean getListenerExecutionStatuses(
			List<ScheduledExecutable> scheduledExecutables,
			Connection connection)
			throws DatabaseException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean retFlag = true;
		String query = QueryGenerator.getGenerator().buildListenerExecutionStatusesQuery(scheduledExecutables);
		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String status = rs.getString("req_stat");
				if (!status.equals("S") && !status.equals("E")) {
					retFlag = false;
					break;
				}
			}
			return retFlag;
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.FATAL)) {
				logger.fatal(query, e);
			}
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * For Development Purpose. Sets the PRE status as
	 * completed for the loop to complete. In reality, PRE would be updating the
	 * status
	 * 
	 * @param scheduledExecutable
	 *        The scheduledExecutable
	 * @return true if update successful, false otherwise
	 * @throws DatabaseException
	 *         Any database I/O exception
	 */
	@Log
	public Boolean setPREStatus(ScheduledExecutable scheduledExecutable,
			Connection connection)
			throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean returnValue = false;
		try {
			pstmt = connection.prepareStatement(PRE_UPDATE_STATUS);
			pstmt.setObject(1, scheduledExecutable.getReqId());			
			pstmt.executeUpdate();
			returnValue = true;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
		
		return returnValue;
	}
	
	/**
	 * Starts a new batch profile, either a new batch or a batch revision 
	 * 
	 * @param batchInfo
	 * 		  The batch information until initialization 
	 * @return The batch information as BatchInfo instance 
	 * @throws DatabaseException
	 * 	 	   Any database I/O exception 
	 */
	@Log
	public synchronized BatchInfo initiateBatch(BatchInfo batchInfo,
			Connection connection) throws DatabaseException {
		
		PreparedStatement pstmtS = null;
		PreparedStatement pstmtI = null;
		ResultSet rs = null;
		try {
			Integer batchNo = batchInfo.getBatchNo();
			Integer batchRevNo = null;
			if(batchNo != null ){
				pstmtS = connection.prepareStatement(GET_REVISION_DATA);
				pstmtS.setObject(1, batchNo);
				pstmtS.setObject(2, batchNo);
				rs = pstmtS.executeQuery();
				rs.next();
				batchInfo.setBatchName(rs.getString(1));
				batchInfo.setBatchType(rs.getString(2));
				batchRevNo = rs.getInt(3);
				batchInfo.setBatchRevNo(batchRevNo);
			}else{
				pstmtS = connection.prepareStatement(GET_BATCH_NO);
				rs = pstmtS.executeQuery();
				rs.next();
				batchNo = rs.getInt(1);
				batchRevNo = 1;//Always 1 when a new batch is created
				batchInfo.setBatchRevNo(batchRevNo);
			}
			
			pstmtI = connection.prepareStatement(CREATE_BATCH);
			pstmtI.setObject(1, batchNo);
			pstmtI.setObject(2, batchRevNo);
			
			String batchName = batchInfo.getBatchName();
			if(batchName == null)
				batchName = Configurations.getConfigurations().getConfigurations("CORE","INSTALLATION","CODE");
			pstmtI.setObject(3, batchName);
			
			String batchType = batchInfo.getBatchType();
			if(batchType == null)
				batchType = "SCHEDULED";			
			pstmtI.setObject(4, batchType);
			
			pstmtI.setTimestamp(5, 
					new java.sql.Timestamp(batchInfo.getExecutionStartTime().getTime()));//start time 
			pstmtI.setString(6, batchInfo.getStartUser());
			pstmtI.setObject(7, batchInfo.getRequestId());
			pstmtI.setObject(8, batchInfo.getInstructionLogSeq());
			pstmtI.executeUpdate();
			
			batchInfo.setBatchNo(batchNo);
			batchInfo.setBatchRevNo(batchRevNo);
			
			return batchInfo;
			
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmtS, null);
			releaseResources(null, pstmtI, null);
		}
	}

	/**
	 * Closes the batch profile earlier created Updates the BATCH table
	 * with the end time and the end user
	 * 
	 * @param batchInfo
	 *            The basic batch information
	 * @param connection
	 *            The connection object
	 *            
	 * @return true if the update is successful, false otherwise
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	@Log
	public Boolean closeBatch(final BatchInfo batchInfo,
			Connection connection) throws DatabaseException {
		
		PreparedStatement pstmtU = null;
		try {			
			pstmtU = connection.prepareStatement(CLOSE_BATCH);			
			pstmtU.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
			pstmtU.setString(2, batchInfo.getEndUser());
			
			if(batchInfo.getClosureReason() == null)
				pstmtU.setObject(3, Constants.CLOSURE_REASON.BATCH_COMPLETED.name());
			else
				pstmtU.setObject(3, batchInfo.getClosureReason());
			
			pstmtU.setObject(4, batchInfo.getBatchNo());
			pstmtU.setObject(5, batchInfo.getBatchRevNo());
			pstmtU.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmtU, null);
		}
	}
	
	/**
	 * Updates the BATCH with failed over status. 
	 * Status is 'Y', if isFailedOver is true.
	 * Status is 'N', if isFailedOver is false
	 * 
	 * @param batchInfo
	 *            The basic batch information
	 * @param connection
	 *            The connection object
	 * @param isFailedOver
	 *			  The fail over status (true/ false)
	 *             
	 * @return true if the update is successful, false otherwise
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	@Log
	public Boolean updateBatchForFailOver(final BatchInfo batchInfo,
			Connection connection, Boolean isFailedOver) throws DatabaseException {
		
		PreparedStatement pstmtU = null;
		try {			
			pstmtU = connection.prepareStatement(UPDATE_BATCH_FOR_FAIL_OVER);			
			if(isFailedOver)
				pstmtU.setString(1, "Y" );
			else
				pstmtU.setString(1, "N" );
			pstmtU.setObject(2, batchInfo.getBatchNo());
			pstmtU.setObject(3, batchInfo.getBatchRevNo());
			return (pstmtU.executeUpdate() == 1);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmtU, null);
		}
	}

	/**
	 * Returns the configuration settings for the CORE as in the
	 * CORE_CONFIG table
	 * 
	 * @return list of CConfig objects
	 * @throws DatabaseException
	 *             Any exception thrown during the database I/O
	 */
	@Log
	public List<CConfig> getConfigurations(Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_CORE_CONFIGURATIONS);
			rs = pstmt.executeQuery();
			List<CConfig> configurations = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, CConfig.class);
			return configurations;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Inserts into the LOG table
	 * 
	 * @param bo
	 * 		  The batch object 
	 * @return 0 if the insert is successful, a negative number otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Long setBatchLog(BatchObject bo,Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Statement st = null;
		ResultSet rsSeq = null;
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		try {
			st = connection.createStatement();
			rsSeq = st.executeQuery(BATCH_LOG_SEQ);
			rsSeq.next();
			Long lSeq = rsSeq.getLong(1);
			
			pstmt = connection.prepareStatement(SET_BATCH_LOG);

			pstmt.setObject(1, lSeq);
			pstmt.setObject(2, bo.getBatchNo());
			pstmt.setObject(3, bo.getBatchRevNo());
			pstmt.setObject(4, bo.getSequence());
			pstmt.setString(5, bo.getTaskname());
			pstmt.setTimestamp(6, new java.sql.Timestamp(bo.getObjExecStartTime().getTime()));
//			pstmt.setTimestamp(6, new java.sql.Timestamp(bo.getObjExecEndTime().getTime()));
			pstmt.setString(7, bo.getStatus().getOldID());
			pstmt.setString(8, bo.getSystemActivityNo());
			pstmt.setString(9, bo.getUserOverridePriority()); 
			pstmt.setObject(10, bo.getPriorityCode1());
			pstmt.setObject(11, bo.getPriorityCode2());
			pstmt.setString(12, bo.getPrePost());
			pstmt.setString(13, bo.getJobType());
			pstmt.setString(14, bo.getLine());
			pstmt.setString(15, bo.getSubline());
			pstmt.setString(16, bo.getBroker());
			pstmt.setString(17, bo.getPolicyNo());
			pstmt.setString(18, bo.getPolicyRenewNo());
			pstmt.setString(19, bo.getVehRefNo());
			pstmt.setString(20, bo.getCashBatchNo());
			pstmt.setString(21, bo.getCashBatchRevisionNo());
			pstmt.setString(22, bo.getGbiBillNo());
			pstmt.setString(23, bo.getPrintFormNo());
			pstmt.setString(24, bo.getNotifyErrorTo());

			if (bo.getDateGenerate() != null)
				pstmt.setTimestamp(25, new java.sql.Timestamp(bo.getDateGenerate()
						.getTime()));
			else
				pstmt.setNull(25, Types.TIMESTAMP);

			pstmt.setString(26, bo.getGenerateBy());
			pstmt.setString(27, bo.getRecMessage());
			pstmt.setString(28, bo.getJobDesc());
			pstmt.setString(29, bo.getObjectName());

			if (bo.getDateExecuted() != null)
				pstmt.setTimestamp(30, new java.sql.Timestamp(bo.getDateExecuted()
						.getTime()));
			else
				pstmt.setNull(30, Types.TIMESTAMP);

			pstmt.setObject(31, bo.getListInd());
			pstmt.setString(32, bo.getEntityType());
			pstmt.setString(33, bo.getEntityCode());
			pstmt.setString(34, bo.getRefSystemActivityNo());
//			pstmt.setString(35, bo.getErrorType());
//			pstmt.setString(36, bo.getErrorDescription());
			pstmt.setInt(35, bo.getCycleNo());
			pstmt.setLong(36, memUsage.getUsed());

			pstmt.executeUpdate();
			return lSeq;
		} catch (SQLException e) {
			logger.fatal("BatchDAO", e);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rsSeq, st, null);
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Inserts a new record into the PROGRESS_LEVEL table. 
	 * Note: All fields are mandatory
	 * 
	 * @param pl
	 * 		  The progress level object ({@link ProgressLevel})
	 * @return the newly created indicator number
	 * @throws DatabaseException 
	 * 		   Any database I/O exception 
	 */
	@Log
	public Integer initiateProgressLevel(ProgressLevel pl,
			Connection connection) throws DatabaseException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//Get the Indicator as it had to be returned back 
			pstmt = connection.prepareStatement(GET_PROGRESS_LEVEL_IND);
			pstmt.setObject(1, pl.getBatchNo());
			pstmt.setObject(2, pl.getBatchRevNo());			
			rs = pstmt.executeQuery();
			rs.next();
			Integer indicator = rs.getInt(1);
			rs.close();
			pstmt.close();
			if (logger.isInfoEnabled()) {
				logger.debug("Indicator = " + indicator);
			}
			//Insert into the progress level table
			pstmt = connection.prepareStatement(SET_PROGRESS_LEVEL);
			pstmt.setObject(1, pl.getBatchNo());
			pstmt.setObject(2, pl.getBatchRevNo());			
			pstmt.setObject(3, indicator);
			pstmt.setString(4, pl.getPrgLevelType());
			pstmt.setString(5, pl.getPrgActivityType().name());
			pstmt.setObject(6, pl.getCycleNo());
			pstmt.setString(7, pl.getStatus());
			if (pl.getStartDatetime() == null) {
				pstmt.setNull(8, Types.TIMESTAMP); 
			} else {
				pstmt.setTimestamp(8, new java.sql.Timestamp(pl.getStartDatetime().getTime()));
			}
			if (pl.getEndDatetime() == null) {
				pstmt.setNull(9, Types.TIMESTAMP); 
			} else {
				pstmt.setTimestamp(9, new java.sql.Timestamp(pl.getEndDatetime().getTime()));
			}
			pstmt.setString(10, (pl.isFailedOver()?"Y":"N"));
			pstmt.executeUpdate();
			return indicator;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}	
	
	/**
	 * Updates the progress level   
	 * 
	 * @param pl
	 * 		  The progress level object ({@link ProgressLevel})
	 * @return 0 if the insert is successful, negative number otherwise
	 * @throws DatabaseException 
	 * 		   Any database I/O exception 
	 */
	@Log
	public Integer updateProgressLevel(ProgressLevel pl,
			Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_PROGRESS_LEVEL);
			pstmt.setString(1, pl.getStatus());
			if (pl.getEndDatetime() == null) {
				pstmt.setNull(2, Types.TIMESTAMP);
			} else {
				pstmt.setTimestamp(2, new java.sql.Timestamp(pl.getEndDatetime().getTime()));
			}
			pstmt.setObject(3, pl.getErrorDesc());
			pstmt.setString(4, (pl.isFailedOver()?"Y":"N"));
			pstmt.setObject(5, pl.getBatchNo());
			pstmt.setObject(6, pl.getBatchRevNo());
			pstmt.setObject(7, pl.getIndicatorNo());
			int i = pstmt.executeUpdate();
			return i;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}		
	

	/**
	 * Locks a processor instance. 
	 * So other scheduled or on-the-fly processors would be halted with immediate effect. 
	 * 
	 * @param requestId
	 * 		  The request identifier as fetched from the PROCESS_REQUEST table
	 * @return true if the request id has successfully locked, false otherwise
	 * @throws DatabaseException
	 * 		   Any database I/O exception
	 */
	@Log
	public Boolean lockProcessor(Long requestId,Connection connection) throws DatabaseException {
		PreparedStatement pstmtLock = null;
		PreparedStatement pstmtLockStatus = null;
		ResultSet rsLockStatus = null;
		try {
			pstmtLock = connection.prepareStatement(GET_PROCESSOR_LOCK);
			pstmtLock.setObject(1, requestId);
			pstmtLock.setString(2, "L");//Lock
			//pstmtLock.setString(3, "O");//Open
			Integer iUpdStatus = pstmtLock.executeUpdate();
			if(iUpdStatus > 0) {
			    return true;
			    
			} else {
			    pstmtLockStatus = connection.prepareStatement(GET_PROCESSOR_LOCK_STATUS);
	            pstmtLockStatus.setString(1, "L");//Lock
	            rsLockStatus = pstmtLockStatus.executeQuery();
	            if (rsLockStatus.next()) {
	                return (rsLockStatus.getLong(1) == requestId);
	            }
			    return false;
			}
			    
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmtLock, null);
			releaseResources(rsLockStatus, pstmtLockStatus, null);
		}
	}

	/**
	 * Returns process request params for the first revision of the batch no
	 * provided
	 * 
	 * @param batchNo
	 * @param connection
	 * @return
	 * @throws DatabaseException
	 */
	public Map<String, Object> getProcessReqParams(int batchNo, Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();

			pstmt = connection.prepareStatement(PRE_GET_REQUEST_PARAMS);
			pstmt.setInt(1, batchNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				paramMap.put(rs.getString(1), rs.getObject(2));
			}

			return paramMap;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Un-Locks a processor instance. 
	 * So other processors could be scheduled.   
	 * 
	 * @param requestId
	 * 		  The request identifier as fetched from the PROCESS_REQUEST table
	 * @return true if the request id has successfully unlocked, false otherwise
	 * @throws DatabaseException
	 * 		   Any database I/O exception
	 */
	@Log
	public Boolean unlockProcessor(Long requestId,Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(RELEASE_PROCESSOR_LOCK);
			pstmt.setString(1, "O");//Open the lock or unlock  
			//pstmt.setObject(2, requestId);
			Integer iUpdStatus = pstmt.executeUpdate();
			if(iUpdStatus > 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}		

	/**
	 * Returns the look up table i.e. the records as set in the COLUMN_MAP
	 * 
	 * @return the data as an instance of LookupTable 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public LookupTable getLookupTable(Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_LOOKUP_TABLE);
			rs = pstmt.executeQuery();
			List<ColumnLookup> list = 
				ResultSetMapper.getInstance().mapMultipleRecords(rs, ColumnLookup.class);
			LookupTable lookupTable = new LookupTable();
			
			for(int i=0; i<list.size(); i++){
				ColumnLookup colLookup = list.get(i);
				String entity = colLookup.getEntity();
				if(!lookupTable.containsKey(entity)){
					lookupTable.put(entity, new ArrayList<ColumnLookup>());					
				}
				lookupTable.get(entity).add(colLookup);
			}
			
			return lookupTable;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	/**
	 * Returns the map of records as set in the ORDERBY_MAP
	 * 
	 * @return the data as an instance of {@link Map}
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Map<String, String> getOrderByLookupTable(Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_ORDERBY_MAP_TABLE);
			rs = pstmt.executeQuery();
			List<OrderLookup> list = 
				ResultSetMapper.getInstance().mapMultipleRecords(rs, OrderLookup.class);
			Map<String, String> lookupTable = new HashMap<String, String>();
			
			for(int i=0; i<list.size(); i++){
				OrderLookup colLookup = list.get(i);
				String entity = colLookup.getEntity();
				if(!lookupTable.containsKey(entity)){
					lookupTable.put(entity, colLookup.getOrderByColumn());					
				}
			}
			
			return lookupTable;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Returns the set of configurable items as set in the META_DATA table 
	 *  
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param entityParams
	 * 	 	  The entity parameters 
	 * @return list of configurable items as instances of BatchJobMetaData
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public List<BatchJobMetaData> getConfigurables(
											Date batchRunDate, 
											EntityParams entityParams,
											Connection connection) 
											throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String query = null;
		boolean procreateAll = false;
		List<BatchJobMetaData> configurables = new ArrayList<BatchJobMetaData>();
		if(entityParams.getValues().get(0).getEntityValue().equals("ALL")) {
			procreateAll = true;
		}
		try {
			query = QueryGenerator.getGenerator().getQueryForProcreatingConfigurables(!procreateAll);	
			if(logger.isDebugEnabled()) {
				logger.debug("Configurables query = " + query);
			}
			pstmt = connection.prepareStatement(query);
			java.sql.Timestamp execSQLDate = new java.sql.Timestamp(batchRunDate.getTime());
			pstmt.setTimestamp(1, execSQLDate);
			pstmt.setTimestamp(2, execSQLDate);
			pstmt.setObject(3, entityParams.getEntity());
			if(!procreateAll){
				//Get objects as needed 
				List<GroupInfo> list = entityParams.getValues();
				for(GroupInfo gi : list){
					pstmt.setObject(4, Integer.valueOf(gi.getEntityValue()));			
					rs = pstmt.executeQuery();
					configurables.addAll(ResultSetMapper.getInstance().mapMultipleRecords(rs,
									BatchJobMetaData.class));
					rs.close();
				}
			}else{
				//Get all
				rs = pstmt.executeQuery();
				configurables.addAll(ResultSetMapper.getInstance().mapMultipleRecords(rs,
								BatchJobMetaData.class));
			}
			return configurables;
		} catch (Exception e) {
			System.err.println(query);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}	

	/**
	 * Returns the object map as set in the OBJECT_MAP table 
	 *  
	 * @param batchRunDate
	 * 		  The batch date 
	 * @return Map<String, ObjectMapDetails> i.e. Map<id, ObjectMapDetails> 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public HashMap<String, ObjectMapDetails> getObjectMap(Date batchRunDate, Connection connection) 
											throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		HashMap<String, ObjectMapDetails> mapping = new HashMap<String, ObjectMapDetails>();
		
		try {						
			pstmt = connection.prepareStatement(GET_OBJECT_MAP);
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(batchRunDate.getTime()); 
			pstmt.setTimestamp(1, sqlDate);
			pstmt.setTimestamp(2, sqlDate);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ObjectMapDetails det = new ObjectMapDetails();
				det.setId(rs.getString(1));
				det.setObjectName(rs.getString(2));
				det.setObjectType(rs.getString(3));
				det.setDefaultValues(rs.getString(4));
				det.setOnFailExit(rs.getString(5));
				det.setOnFailEmail(rs.getString(6));
				det.setMinTime(rs.getInt(7));
				det.setAvgTime(rs.getInt(8));
				det.setMaxTime(rs.getInt(9));
				det.setEscalationLevel(rs.getString(10));
				det.setCaseData(rs.getString(11));
//				if (det.getEscalationLevel() != null) {
//					Constants.OBJECT_ESCALATION_LEVEL.valueOf(det.getEscalationLevel());
//				}
				mapping.put(det.getId(), det);
			}
			return mapping;
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Updates the instruction log for which the action is taken by the batch   
	 * 
	 * @return integer, i.e. the number of rows updated
	 * 
	 */
	@Log
	public Integer updateInstructionLog(final BatchInfo batchInfo, Connection connection) throws DatabaseException{
		
		//It is not necessary that the instruction log sequence would be present. 
		//Instruction log is populated and should be updated only when the instruction comes 
		//from the monitor. 
		//For scheduled batches the instruction log sequence would always be null
		//In that case, exit as there is not record to update
		
		if(batchInfo.getInstructionLogSeq() == null)
			return -1;
		
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_INSTRUCTION_LOG);
			pstmt.setObject(1, batchInfo.getBatchNo());
			pstmt.setObject(2, batchInfo.getBatchRevNo());
			pstmt.setObject(3, batchInfo.getBatchAction());
			if (batchInfo.getBatchActionTime() != null) {
				pstmt.setTimestamp(4, new java.sql.Timestamp(batchInfo.getBatchActionTime().getTime()));
			} else {
				pstmt.setNull(4, Types.TIMESTAMP);
			}
			pstmt.setObject(5, batchInfo.getInstructionLogSeq());
			int i = pstmt.executeUpdate();			
			return i;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}		
	}		

	/**
	 * Updates the instruction log in case of any error   
	 * 
	 * @return integer, i.e. the number of rows updated
	 * 
	 */
	@Log
	public Integer updateInstructionLogError(Integer instrSeqNo, String messaage, Connection connection) throws DatabaseException{
		
		//It is not necessary that the instruction log sequence would be present. 
		//Instruction log is populated and should be updated only when the instruction comes 
		//from the monitor. 
		//For scheduled batches the instruction log sequence would always be null
		//In that case, exit as there is not record to update
		
		if(instrSeqNo == null)
			return -1;
		
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_INSTRUCTION_LOG_ERROR);
			pstmt.setObject(1, "BATCH ERROR");
			pstmt.setObject(2, messaage);
			pstmt.setObject(3, instrSeqNo);
			int i = pstmt.executeUpdate();			
			return i;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}		
	}		
	
	/**
	 * Set the system information on which the batch is run  
	 * 
	 * @return integer, i.e. the number of rows inserted
	 * 
	 */
	@Log
	public Integer setSystemInfo(final BatchInfo batchInfo,
			Connection connection) throws DatabaseException{
		PreparedStatement pstmt = null;
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		try {
			pstmt = connection.prepareStatement(SET_SYSTEM_INFO);
			
			//Get the system information 
			Map<String, String> systemInfo = getSystemInfo();
			
			pstmt.setObject(1, batchInfo.getBatchNo());
			pstmt.setObject(2, batchInfo.getBatchRevNo());
			pstmt.setString(3, systemInfo.get("JAVA_VERSION"));
			pstmt.setString(4, batchInfo.getPREVersion());
			pstmt.setString(5, systemInfo.get("CPU_CONFIG"));
			String outputDirPath = Configurations.getConfigurations().getConfigurations("CORE", "OUTPUT", "DIRECTORY");
			String freeSpace = "";
			if(outputDirPath != null)
				freeSpace = (new File(outputDirPath).getFreeSpace() /1024 / 1024) + " MB" ;
			pstmt.setString(6, (outputDirPath == null) ?  "" : outputDirPath);
			pstmt.setString(7, freeSpace);
			pstmt.setObject(8, Long.valueOf(memUsage.getUsed()));
			pstmt.setObject(9, Long.valueOf((memUsage.getMax()==-1)?memUsage.getCommitted():memUsage.getMax()));
			int i = pstmt.executeUpdate();			
			return i;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}		
	}	

	/**
	 * Batch can have various endings. If the previous batch ending was completed, i.e. BATCH_COMPLETED
	 * then there can be no further revisions for that batch  
	 * This method does that check 
	 *   
	 * @param batchNo
	 * 		  The batch number
	 * @return true, if the batch can have revisions, false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Boolean canHaveRevision(Integer batchNo, Connection connection) 
											throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {						
			pstmt = connection.prepareStatement(CHECK_PREVIOUS_CLOSURE_REASON);
			pstmt.setObject(1, batchNo);
			pstmt.setObject(2, batchNo);
			rs = pstmt.executeQuery();
			String reason = null;
			if(rs.next()){
				reason = rs.getString(1);//Only one record with only one column
			}
			return (reason != null && Constants.CLOSURE_REASON.BATCH_COMPLETED.name().equals(reason))
						? false : true;  
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	
	
	/**
	 * Returns the system / operating system information 
	 * 
	 * @return Map<String, String> instance with the operating system information 
	 */
	@Log
	private Map<String, String> getSystemInfo(){
		Map<String, String> map = new HashMap<String, String>();		
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		map.put("JAVA_VERSION", runtimeBean.getSystemProperties().get("java.version"));
		
		OperatingSystemMXBean operatingSysBean = ManagementFactory.getOperatingSystemMXBean();
		StringBuilder sb = new StringBuilder();
		String machineName = runtimeBean.getName();
		sb.append("|Machine Name:" + machineName.substring(machineName.indexOf("@") + 1));
		sb.append("|Arch:" + operatingSysBean.getArch());
		sb.append("|Name:" + operatingSysBean.getName());
		sb.append("|Version:" + operatingSysBean.getVersion());
		sb.append("|Processors:" + operatingSysBean.getAvailableProcessors());		
		map.put("CPU_CONFIG", sb.toString());
		
		return map;
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IBatchDao#updateBatchLog(java.lang.Long, com.stgmastek.core.util.BatchObject, java.sql.Connection)
	 */
	
	public Integer updateBatchLog(Long seqNo, BatchObject bo,
			Connection connection) throws DatabaseException {
		if (seqNo == null) {
			return -1; //Sequence was not generated as the DAO layer threw an exception. Thus simply return and do nothing.
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		try {
			pstmt = connection.prepareStatement(UPDATE_BATCH_LOG);

			pstmt.setTimestamp(1, new java.sql.Timestamp(bo.getObjExecEndTime().getTime()));
			pstmt.setString(2, bo.getStatus().getOldID());
			if (bo.getDateGenerate() != null)
				pstmt.setTimestamp(3, new java.sql.Timestamp(bo.getDateGenerate()
						.getTime()));
			else
				pstmt.setNull(3, Types.TIMESTAMP);

			if (bo.getDateExecuted() != null)
				pstmt.setTimestamp(4, new java.sql.Timestamp(bo.getDateExecuted()
						.getTime()));
			else
				pstmt.setNull(4, Types.TIMESTAMP);
			if (bo.getErrorType()  != null) {
				pstmt.setString(5, bo.getErrorType().substring(0, (bo.getErrorType().length() > 25)?25:bo.getErrorType().length()));
			} else {
				pstmt.setString(5, bo.getErrorType());
			}
			if (bo.getErrorDescription()  != null) {
				pstmt.setString(6, bo.getErrorDescription().substring(0, (bo.getErrorDescription().length() > 4000)?4000:bo.getErrorDescription().length()));
			} else {
				pstmt.setString(6, bo.getErrorDescription());
			}
			//pstmt.setString(6, bo.getErrorDescription());
			pstmt.setLong(7, memUsage.getUsed());
			pstmt.setLong(8, seqNo);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.fatal("BatchLog : ",e);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IBatchDao#getTimeConsumingObjects(java.lang.Integer, java.sql.Connection)
	 */
	
	public List<BatchObject> getCurrentlyRunningJobs(Integer batchNo, Integer batchRevNo,
			Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BatchObject> list = new LinkedList<BatchObject>();
		try {
			pstmt = connection.prepareStatement(GET_RUNNING_JOBS);
			pstmt.setInt(1, batchNo);
			pstmt.setInt(2, batchRevNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
//"SELECT be_seq_no sequence, object_name, job_desc, task_name taskname, job_type, obj_exec_start_time " +
				BatchObject obj = new BatchObject();
				obj.setSequence(rs.getString(1));
				obj.setObjectName(rs.getString(2));
				obj.setJobDesc(rs.getString(3));
				obj.setTaskname(rs.getString(4));
				obj.setJobType(rs.getString(5));
				obj.setObjExecStartTime(rs.getTimestamp(6));
				list.add(obj);
			}
		} catch (Exception e) {
			throw new DatabaseException("SQLException caught " + e.getMessage(), e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IBatchDao#getJobsExecutedLessThanMinTime(java.lang.Integer, java.lang.Integer, java.sql.Connection)
	 */
	
	public List<BatchObject> getJobsExecutedLessThanMinTime(Integer batchNo,
			Integer batchRevNo, Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BatchObject> list = new LinkedList<BatchObject>();
		try {
			pstmt = connection.prepareStatement(GET_LESS_THAN_MIN_TIME_EXECUTED_JOBS);
			pstmt.setInt(1, batchNo);
			pstmt.setInt(2, batchRevNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BatchObject obj = new BatchObject();
				obj.setSequence(rs.getString(1));
				obj.setObjectName(rs.getString(2));
				obj.setJobDesc(rs.getString(3));
				obj.setTaskname(rs.getString(4));
				obj.setJobType(rs.getString(5));
				obj.setObjExecStartTime(rs.getTimestamp(6));
				obj.setObjExecEndTime(rs.getTimestamp(7));
				try {
					obj.setStatus(OBJECT_STATUS.resolve(rs.getString(8)));
                } catch (IllegalArgumentException e) {
                	// This should not arise.
                }
				obj.setMinTime(rs.getInt(9));
				list.add(obj);
			}
		} catch (Exception e) {
			throw new DatabaseException("SQLException caught " + e.getMessage(), e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IBatchDao#cancelListenersQueuedRequests(java.util.List, java.sql.Connection)
	 */
	
	public Boolean cancelListenersQueuedRequests(
			List<ScheduledExecutable> scheduledExecutables,
			Connection connection) throws DatabaseException {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(CANCEL_PROCESS_REQUEST);
			for(ScheduledExecutable executable : scheduledExecutables) {
				pst.setObject(1, IProcessRequest.REQUEST_STATUS.USER_CANCELLED.getID());
				pst.setObject(2, executable.getReqId());
				pst.addBatch();
			}
			int[] array = pst.executeBatch();
			boolean value = false;
			ArrayList<ScheduledExecutable> checkExecutionStatusList = new ArrayList<ScheduledExecutable>();
			for (int i=0; i<array.length; i++) {
				value = (array[i] == 1);
				if (!value) {
					checkExecutionStatusList.add(scheduledExecutables.get(i));
				}
			}
			if (checkExecutionStatusList.size() > 0) {
				return getListenerExecutionStatuses(checkExecutionStatusList, connection);
			}
			return value;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			releaseResources(null, pst, null);
		}
	}

	/**
	 * Updates the CONFIGURATION with encoded password.
	 * 
	 * @param encodedPassword
	 *            The encoded password
	 *            
	 * @param connection
	 *            The connection object
	 * 
	 * @return integer, i.e. the number of rows updated
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	public int updateConfiguration(String encodedPassword,
			Connection connection) throws DatabaseException {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_CONFIGURATION);
			pstmt.setString(1, encodedPassword);
			int i = pstmt.executeUpdate();			
			return i;
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/dao/defaultimpl/BatchDAO.java                                                                             $
 * 
 * 31    6/30/10 4:14p Kedarr
 * Updated the auto commit to set to true in finally block and in exception block called rollback.
 * 
 * 30    5/04/10 4:52p Mandar.vaidya
 * Added stuck thread limits during the insert of listener
 * 
 * 29    4/28/10 10:37a Kedarr
 * Updated javadoc
 * 
 * 28    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 27    4/23/10 8:27p Kedarr
 * Changed to remove the null pointer exception.
 * 
 * 26    4/21/10 11:53a Kedarr
 * Reverted the changes made for getConfigurables.
 * 
 * 25    4/20/10 2:05p Kedarr
 * Added method to check if the configurable is already completed or not using the log table. If so then there is no need to pro-create the same object again,  otherwise do so.
 * 
 * 24    4/19/10 11:46a Kedarr
 * Updated the batch log method to capture the first 25 characters from error type.
 * 
 * 23    4/13/10 1:44p Kedarr
 * Changes made to retrieve the escalation level from the object map table.
 * 
 * 22    4/06/10 1:16p Mandar.vaidya
 * Corrected the query for min time executed jobs
 * 
 * 21    3/31/10 3:32p Kedarr
 * rectified a column name issue.
 * 
 * 20    3/30/10 2:30p Kedarr
 * Changes made to return a Linked List
 * 
 * 19    3/30/10 9:33a Kedarr
 * Added new method to get the objects that have taken less than minimum time as set in the object map
 * 
 * 18    3/26/10 5:16p Mandar.vaidya
 * Removed result mapper for get running jobs.
 * 
 * 17    3/26/10 5:04p Mandar.vaidya
 * changes made as per the batch object method names for query get running jobs.
 * 
 * 16    3/26/10 4:51p Mandar.vaidya
 * Changes made in query for getting currently running jobs.
 * 
 * 15    3/26/10 4:13p Mandar.vaidya
 * Changes made to get the running jobs instead of time consuming.
 * 
 * 14    3/26/10 1:53p Mandar.vaidya
 * Changed query for stuck objects
 * 
 * 13    3/26/10 1:30p Kedarr
 * Added a new method for identifying time consuming jobs.
 * 
 * 12    3/24/10 12:43p Kedarr
 * Added a new column in object map table.
 * 
 * 11    3/22/10 1:27p Kedarr
 * Changes made to capture memory usage against the system info as compared to batch table.
 * 
 * 10    3/22/10 11:36a Kedarr
 * Changes made to capture memory usage against the log table.
 * 
 * 9     3/19/10 7:06p Mandar.vaidya
 * Changes made to not close the connection object in the DAO. Now the caller is responsible to close the connection.
 * 
 * 8     3/19/10 2:51p Kedarr
 * Changes made for inserting and updating batch log. Earlier it was all insert no updates.
 * 
 * 7     3/17/10 8:04p Kedarr
 * Commented release of connection from the setBatchLog method.
 * 
 * 6     3/15/10 1:11p Mandar.vaidya
 * Rectified the bug in setBatchLog
 * 
 * 5     3/15/10 12:45p Kedarr
 * Changes made to insert cycleNo
 * 
 * 4     3/11/10 12:07p Kedarr
 * standardized the name of failed over column in other tables.
 * 
 * 3     3/11/10 11:48a Kedarr
 * Changes made for setting failed over status in progress level.
 * 
 * 2     3/09/10 2:24p Kedarr
 * Changed the package and changed the CConnection to java sql connection.
 * 
 * 1     3/08/10 10:09a Kedarr
 * 
 * 19    3/08/10 10:09a Kedarr
 * 
 * 18    3/05/10 1:04p Kedarr
 * Added instruction log seq in the create batch insert.
 * 
 * 17    3/04/10 10:51a Grahesh
 * Added a new method to update the batch for fail over status
 * 
 * 16    3/03/10 5:20p Grahesh
 * Removed the Connection object from the constructor and added in the individual methods
 * 
 * 15    2/25/10 10:40a Grahesh
 * Changes made for the fail over and the locking of the batch.
 * 
 * 14    2/17/10 9:13a Grahesh
 * Added final keyword
 * 
 * 13    2/15/10 12:20p Mandar.vaidya
 * Changes made to incorporate Serializable change that was made in PRE V1.0 R 28
 * 
 * 12    1/12/10 11:53a Grahesh
 * Put a transaction for those methods that have multiple updates / insert in the method with correction.
 * 
 * 11    1/11/10 5:03p Grahesh
 * Put a transaction for those methods that have multiple updates / insert in the method
 * 
 * 10    1/11/10 4:53p Grahesh
 * Changes the variables to static final instead of private final
 * 
 * 9     1/06/10 1:15p Grahesh
 * Avoiding null pointer exception
 * 
 * 8     12/29/09 12:43p Grahesh
 * Added annotation for logging to all methods.
 * 
 * 7     12/23/09 6:43p Grahesh
 * Default implementation for those batches for which the name and the type is not provided.
 * 
 * 6     12/23/09 3:23p Grahesh
 * Removed be_on_fail_exit from batch_executor
 * 
 * 5     12/23/09 11:54a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 4     12/21/09 5:13p Grahesh
 * 1. Updated closeBatch
 * 2. Added new method for canHaveRevision
 * 
 * 3     12/17/09 12:31p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/