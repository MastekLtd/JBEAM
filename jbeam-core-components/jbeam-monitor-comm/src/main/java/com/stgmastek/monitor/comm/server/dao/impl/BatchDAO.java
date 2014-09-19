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
package com.stgmastek.monitor.comm.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import stg.utils.Day;

import com.stgmastek.core.comm.client.CReqCalendarLog;
import com.stgmastek.core.comm.client.CReqInstructionLog;
import com.stgmastek.core.comm.client.CalendarData;
import com.stgmastek.core.comm.client.InstructionParameters;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.server.vo.BatchLog;
import com.stgmastek.monitor.comm.server.vo.MReqInstructionLog;
import com.stgmastek.monitor.comm.util.BaseDAO;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all batch related operations
 * 
 * @author mandar.vaidya
 * 
 */
public class BatchDAO extends BaseDAO implements IBatchDAO {

	/** Query to INSERT data into LOG table */
	private static final String INSERT_LOG = "INSERT INTO LOG" +
			" (installation_code, batch_no, batch_rev_no, be_seq_no," +
			" task_name, obj_exec_start_time, obj_exec_end_time," +
			" sys_act_no, user_priority, priority_code1," +
			" priority_code2, job_type, line, subline, " +
			" broker, policy_no, policy_renew_no, veh_ref_no," +
			" cash_batch_no, cash_batch_rev_no, gbi_bill_no," +
			" print_form_no, notify_error_to, date_generate," +
			" generate_by, rec_message, job_desc, object_name," +
			" date_executed, list_ind, entity_type," +
			" entity_code, ref_system_activity_no, error_type," +
			" error_description, pre_post, status, time_taken, cycle_no," +
			" used_memory_before, used_memory_after, seq_no, last_updated_on) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
			" ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	/** Query to UPDATE data into BATCH table */
	private static final String UPDATE_LOG = "UPDATE LOG " +
			" SET task_name = ?, obj_exec_start_time = ?, obj_exec_end_time = ?," +
			" sys_act_no = ?, user_priority= ?,  priority_code1= ?, " +
			" priority_code2= ?,  job_type= ?,  line= ?,  subline= ?,  " +
			" broker= ?,  policy_no= ?,  policy_renew_no= ?,  veh_ref_no= ?, " +
			" cash_batch_no= ?,  cash_batch_rev_no= ?,  gbi_bill_no= ?, " +
			" print_form_no= ?,  notify_error_to= ?,  date_generate= ?, " +
			" generate_by= ?,  rec_message= ?,  job_desc= ?,  object_name= ?, " +
			" date_executed= ?,  list_ind= ?,  entity_type= ?, " +
			" entity_code= ?,  ref_system_activity_no= ?,  error_type= ?, " +
			" error_description= ?,  pre_post= ?,  status= ?,  time_taken= ?," +
			" cycle_no = ?, used_memory_before= ?, used_memory_after= ?, last_updated_on=? " +
			" WHERE installation_code = ? AND batch_no = ? " +
			" AND batch_rev_no = ? AND be_seq_no = ? AND seq_no = ?" ;
	
	/** Query to INSERT data into BATCH table */
	private static final String INSERT_BATCH = "INSERT INTO BATCH " +
		"(installation_code, batch_no, batch_rev_no, batch_name," +
		" batch_type, exec_start_time, exec_end_time," +
		" batch_start_user, batch_end_user, process_id, failed_over," +
		" instruction_seq_no )" +
		" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String UPDATE_INSTALLATION = 
		"UPDATE installation SET batch_no = ?, batch_rev_no = ? WHERE installation_code = ?";

	/** Query to UPDATE data into BATCH table */
	private static final String UPDATE_BATCH = "UPDATE BATCH " +
		" SET exec_end_time = ?, batch_end_user = ?, batch_end_reason = ?, failed_over = ? " +
		" WHERE batch_no = ? AND batch_rev_no = ?";

	
	/** Query to update data into INSTRUCTION_LOG table */
	private static final String UPDATE_INSTRUCTION_LOG = 
		"UPDATE instruction_log " +
		"SET batch_action = ?, batch_action_time = ?, message_param = ? " +
		"WHERE installation_code = ? " +
		"AND seq_no = ?";
	
	/** Query to get data from INSTRUCTION_LOG table */
	private static final String GET_INSTRUCTION_LOG = 
		"SELECT seq_no, batch_no, batch_rev_no, message, message_param, " +
		"instructing_user, instruction_time, batch_action, batch_action_time " +
		"FROM instruction_log " +
		"WHERE seq_no = ?";
	
	/** Query to get data from INSTRUCTION_PARAMETERS table */
	private static final String GET_INSTRUCTION_PARAMETERS = "select  sl_no, name, value, type from instruction_parameters where instruction_log_no = ?";
	
	/** Query to get calendar data */
	private static final String GET_CALENDAR_DATA = 
		"SELECT calendar_name, year, non_working_date, remark, user_id  " +
		"FROM calendar_log WHERE installation_code = ? " +
		"AND calendar_name = ? AND year = ?";
	
	/** Query to get installation code(s) */
	private static final String GET_INSTALLATION_CODES =
		"SELECT installation_code FROM installation";
	
	/** Query to insert data into PROCESS_REQUEST_SCHEDULE table */
	private static final String INSERT_PROCESS_REQUEST_SCHEDULE_DATA = 
		" Insert into PROCESS_REQUEST_SCHEDULE (sch_id, freq_type, recur, start_dt, sch_stat, " +
		" user_id, on_week_day, end_dt, end_occur, entry_dt, modify_id, modify_dt, req_stat, " +
		" occur_counter, process_class_nm, start_time, end_time, future_scheduling_only, " +
		" fixed_date, email_ids, skip_flag, weekday_check_flag, end_reason, keep_alive, " +
		" installation_code, batch_name) " +
		" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * Public constructor takes the connection as argument
	 *  
	 */
	public BatchDAO() {
	}
	
	/**
	 * Inserts the data into LOG table.
	 * 
	 * @param log
	 * 		  The batch log
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * @throws CommDatabaseException 
	 */
	public Integer addBatchLog(BatchLog log,Connection connection) throws CommDatabaseException{
		
		PreparedStatement pstmt = null;
		try	{
			connection.setAutoCommit(false); //begin transaction
			
			pstmt = connection.prepareStatement(INSERT_LOG);
			pstmt.setString(1, log.getInstallationCode());
			pstmt.setInt(2, log.getBatchNo());
			pstmt.setInt(3, log.getBatchRevNo());
			pstmt.setString(4, log.getBeSeqNo());
			pstmt.setString(5, log.getTaskName());
			pstmt.setTimestamp(6, new Timestamp(log
					.getObjExecStartTime()));
			Long objExecEndTime = log.getObjExecEndTime();
			if (objExecEndTime != null)
				pstmt.setTimestamp(7, new Timestamp(log
						.getObjExecEndTime()));
			else
				pstmt.setTimestamp(7, null);
			pstmt.setString(8, log.getSysActNo());
			pstmt.setString(9, log.getUserPriority());
			pstmt.setObject(10, log.getPriorityCode1());
			pstmt.setObject(11, log.getPriorityCode2());
			pstmt.setString(12, log.getJobType());
			pstmt.setString(13, log.getLine());
			pstmt.setString(14, log.getSubline());
			pstmt.setString(15, log.getBroker());
			pstmt.setString(16, log.getPolicyNo());
			pstmt.setString(17, log.getPolicyRenewNo());
			pstmt.setString(18, log.getVehRefNo());
			pstmt.setString(19, log.getCashBatchNo());
			pstmt.setString(20, log.getCashBatchRevNo());
			pstmt.setString(21, log.getGbiBillNo()); // 21
			pstmt.setString(22, log.getPrintFormNo());
			pstmt.setString(23, log.getNotifyErrorTo());

			Long objDateGenerate = log.getDateGenerate();
			if (objDateGenerate != null)
				pstmt.setTimestamp(24, new Timestamp(objDateGenerate));
			else
				pstmt.setTimestamp(24, null);

			pstmt.setString(25, log.getGenerateBy()); // 25
			pstmt.setString(26, log.getRecMessage());
			pstmt.setString(27, log.getJobDesc());
			pstmt.setString(28, log.getObjectName());

			Long objDateExecuted = log.getDateExecuted();
			if (objDateExecuted != null)
				pstmt.setTimestamp(29, new Timestamp(objDateExecuted));
			else
				pstmt.setTimestamp(29, null);

			pstmt.setObject(30, log.getListInd()); // 30
			pstmt.setString(31, log.getEntityType());
			pstmt.setString(32, log.getEntityCode());
			pstmt.setString(33, log.getRefSystemActivityNo());
			pstmt.setString(34, log.getErrorType());
			pstmt.setString(35, log.getErrorDescription());
			pstmt.setString(36, log.getPrePost());
			pstmt.setString(37, log.getStatus()); // 37

			if (log.getObjExecStartTime() != null
					&& log.getObjExecEndTime() != null)
				pstmt.setObject(38, log.getObjExecEndTime()
						- log.getObjExecStartTime());
			else
				pstmt.setObject(38, 0);
			pstmt.setObject(39, log.getCycleNo()); // 39
			pstmt.setObject(40, log.getUsedMemoryBefore()); // 40
			pstmt.setObject(41, log.getUsedMemoryAfter()); // 41
			pstmt.setObject(42, log.getSeqNo()); // 42
			pstmt.setTimestamp(43, (new Day()).getTimestamp()); // 43
			
			int iInsert = pstmt.executeUpdate();
			connection.commit();
			return iInsert;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Updates the batch log with the given batch log
	 * 
	 * @param batchLog
	 * 		  The batch log
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * @throws CommDatabaseException 
	 */
	public Integer updateBatchLog(BatchLog batchLog,Connection connection)
			throws CommDatabaseException {

		PreparedStatement pstmt = null;
		try {
			connection.setAutoCommit(false); //begin transaction
			
			pstmt = connection.prepareStatement(UPDATE_LOG);
			pstmt.setString(1, batchLog.getTaskName());
			pstmt.setTimestamp(2, new Timestamp(batchLog.getObjExecStartTime()));
			Long objExecEndTime = batchLog.getObjExecEndTime();
			if (objExecEndTime != null)
				pstmt.setTimestamp(3, new Timestamp(batchLog
						.getObjExecEndTime()));
			else
				pstmt.setTimestamp(3, null);
			pstmt.setString(4, batchLog.getSysActNo());
			pstmt.setString(5, batchLog.getUserPriority());
			pstmt.setObject(6, batchLog.getPriorityCode1());
			pstmt.setObject(7, batchLog.getPriorityCode2());
			pstmt.setString(8, batchLog.getJobType());
			pstmt.setString(9, batchLog.getLine());
			pstmt.setString(10, batchLog.getSubline());//10
			pstmt.setString(11, batchLog.getBroker());
			pstmt.setString(12, batchLog.getPolicyNo());
			pstmt.setString(13, batchLog.getPolicyRenewNo());
			pstmt.setString(14, batchLog.getVehRefNo());
			pstmt.setString(15, batchLog.getCashBatchNo());
			pstmt.setString(16, batchLog.getCashBatchRevNo());
			pstmt.setString(17, batchLog.getGbiBillNo()); // 21
			pstmt.setString(18, batchLog.getPrintFormNo());
			pstmt.setString(19, batchLog.getNotifyErrorTo());

			Long objDateGenerate = batchLog.getDateGenerate();
			if (objDateGenerate != null)
				pstmt.setTimestamp(20, new Timestamp(objDateGenerate));
			else
				pstmt.setTimestamp(20, null);

			pstmt.setString(21, batchLog.getGenerateBy()); 
			pstmt.setString(22, batchLog.getRecMessage());
			pstmt.setString(23, batchLog.getJobDesc());
			pstmt.setString(24, batchLog.getObjectName());

			Long objDateExecuted = batchLog.getDateExecuted();
			if (objDateExecuted != null)
				pstmt.setTimestamp(25, new Timestamp(objDateExecuted));// 25
			else
				pstmt.setTimestamp(25, null);

			pstmt.setObject(26, batchLog.getListInd()); 
			pstmt.setString(27, batchLog.getEntityType());
			pstmt.setString(28, batchLog.getEntityCode());
			pstmt.setString(29, batchLog.getRefSystemActivityNo());
			pstmt.setString(30, batchLog.getErrorType());// 30
			pstmt.setString(31, batchLog.getErrorDescription());
			pstmt.setString(32, batchLog.getPrePost());
			pstmt.setString(33, batchLog.getStatus()); // 33

			if (batchLog.getObjExecStartTime() != null
					&& batchLog.getObjExecEndTime() != null)
				pstmt.setObject(34, batchLog.getObjExecEndTime()
						- batchLog.getObjExecStartTime());
			else
				pstmt.setObject(34, 0);
			pstmt.setObject(35, batchLog.getCycleNo()); // 35
			pstmt.setObject(36, batchLog.getUsedMemoryBefore()); // 36
			pstmt.setObject(37, batchLog.getUsedMemoryAfter()); // 37
			pstmt.setTimestamp(38, (new Day()).getTimestamp()); // 38
			pstmt.setString(39, batchLog.getInstallationCode());
			pstmt.setInt(40, batchLog.getBatchNo());
			pstmt.setInt(41, batchLog.getBatchRevNo());
			pstmt.setString(42, batchLog.getBeSeqNo()); // 42
			pstmt.setInt(43, batchLog.getSeqNo());
			
			
			int iInsert = pstmt.executeUpdate();
			connection.commit();
			return iInsert;
		} catch (SQLException sqe) {
			throw new CommDatabaseException(sqe);
		} finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
	}

	/**
	 * Adds the batch with the given batch
	 * 
	 * @param batch
	 * 		  The batch
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 *  
	 * @throws CommDatabaseException 
	 */
	public Integer addBatch(MReqBatch batch,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		PreparedStatement psInstall = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(INSERT_BATCH);
			pstmt.setString(1, batch.getInstallationCode());
			pstmt.setInt(2, batch.getBatchNo());
			pstmt.setInt(3, batch.getBatchRevNo());
			pstmt.setString(4, batch.getBatchName());
			pstmt.setString(5, batch.getBatchType());
			pstmt.setTimestamp(6, new Timestamp(batch.getExecStartTime()));
			
			Long objExecEndTime = batch.getExecEndTime();			
			if(objExecEndTime != null)
				pstmt.setTimestamp(7, new Timestamp(batch.getExecEndTime()));
			else
				pstmt.setTimestamp(7, null);
			
			pstmt.setString(8, batch.getBatchStartUser());
			pstmt.setString(9, batch.getBatchEndUser());
			pstmt.setObject(10, batch.getProcessId());
			pstmt.setObject(11, batch.getFailedOver());
			pstmt.setObject(12, batch.getInstructionSeqNo());
			
			int iInsert = pstmt.executeUpdate();
			psInstall = connection.prepareStatement(UPDATE_INSTALLATION);
			psInstall.setInt(1, batch.getBatchNo());
			psInstall.setInt(2, batch.getBatchRevNo());
			psInstall.setString(3, batch.getInstallationCode());
			psInstall.executeUpdate();
			connection.commit();
			return iInsert;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			releaseResources(null, pstmt, null);
		}
	}
	
	
	/**
	 * Updates the batch with the given batch.	 * 
	 * This will update only Execution end time for the given 
	 * batch number and batch revision number.
	 * 
	 * @param batch
	 * 		  The batch
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 *  
	 * @throws CommDatabaseException 
	 */
	public Integer updateBatch(MReqBatch batch,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_BATCH);
			
			Long objExecEndTime = batch.getExecEndTime();
			if(objExecEndTime != null)
				pstmt.setTimestamp(1, new Timestamp(batch.getExecEndTime()));
			else
				pstmt.setTimestamp(1, null);
				
			pstmt.setString(2, batch.getBatchEndUser());
			pstmt.setString(3, batch.getBatchEndReason());
			pstmt.setString(4, batch.getFailedOver());
			pstmt.setInt(5, batch.getBatchNo());
			pstmt.setInt(6, batch.getBatchRevNo());
			
			int iInsert = pstmt.executeUpdate();
			connection.commit();
			return iInsert;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Updates the instruction log table 
	 * 
	 * @param instructionLog
	 * 			The reference to Instruction log ({@link MReqInstructionLog}
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * 
	 * @throws CommDatabaseException
	 * 
	 */
	public Integer updateInstructionLog(MReqInstructionLog instructionLog,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_INSTRUCTION_LOG);
			
			pstmt.setString(1, instructionLog.getBatchAction());
			if(instructionLog.getBatchActionTime() != null)
				pstmt.setTimestamp(2, new java.sql.Timestamp(instructionLog.getBatchActionTime()));
			else
				pstmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));

			if (instructionLog.getMessageParam() != null) {
				pstmt.setString(3, instructionLog.getMessageParam());
			} else {
				pstmt.setNull(3, Types.VARCHAR);
			}

			pstmt.setString(4, instructionLog.getInstallationCode());
			
			pstmt.setInt(5, instructionLog.getSeqNo());

			int iUpdate = pstmt.executeUpdate();
			connection.commit();
			return iUpdate;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////
	//OUT BOUND METHODS 
////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Fetches the instruction log for the supplied sequence number   
	 * 
	 * @param seqNo
	 * 		  The sequence number
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return An instance of the request instruction log
	 */
	public CReqInstructionLog getInstructionLog(Integer seqNo,Connection connection) throws CommDatabaseException{
		PreparedStatement pstmtIL = null;
		PreparedStatement pstmtIP = null;
		ResultSet rsIL = null;
		ResultSet rsIP = null;
		try{
			//Get the instruction log
			pstmtIL = connection.prepareStatement(GET_INSTRUCTION_LOG);
			pstmtIL.setObject(1, seqNo);
			rsIL = pstmtIL.executeQuery();
			CReqInstructionLog instructionLog = 
				ResultSetMapper.getInstance().mapSingleRecord(rsIL, CReqInstructionLog.class);
			
			//Get the log parameters 
			pstmtIP = connection.prepareStatement(GET_INSTRUCTION_PARAMETERS);
			pstmtIP.setObject(1, seqNo);
			rsIP = pstmtIP.executeQuery();
			List<InstructionParameters> instructionParameters = 
				ResultSetMapper.getInstance().mapMultipleRecords(rsIP, InstructionParameters.class);
			
			instructionLog.getInstructionParametersList().addAll(instructionParameters);
			
			return instructionLog;
		}catch(Exception e){
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rsIL, pstmtIL, null);
			releaseResources(rsIP, pstmtIP, null);
		}		
	}

	/**
	 * Gets the details of a selected calendar
	 * 
	 * @param calendarData
	 *            The reference of Calendar data {@link CalendarData}
	 * 
	 * @param installationCode
	 *            the installation code for which calendar data is requested.
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return the reference of Calendar log {@link CReqCalendarLog}
	 * 
	 * @throws CommDatabaseException
	 */
	public CReqCalendarLog getCalendarData(CalendarData calendarData,
			String installationCode,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CReqCalendarLog reqCalendarLog = null;
		try {
			reqCalendarLog = new CReqCalendarLog();
			// Get the calendar log
			pstmt = connection.prepareStatement(GET_CALENDAR_DATA);
			pstmt.setObject(1, installationCode);
			pstmt.setObject(2, calendarData.getCalendarName());
			pstmt.setObject(3, calendarData.getYear());
			rs = pstmt.executeQuery();
			List<CalendarData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, CalendarData.class);
			reqCalendarLog.getCalendarList().addAll(list);
			return reqCalendarLog;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.comm.server.dao.IBatchDAO#getInstallationTimeZone(java.lang.String, java.sql.Connection)
	 */
	
	public TimeZone getInstallationTimeZone(String installationCode,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		TimeZone tzone = TimeZone.getDefault();
		try {
			pst = connection.prepareStatement("SELECT timezone_id FROM installation where installation_code = ?");
			pst.setString(1, installationCode);
			rs = pst.executeQuery();
			if (rs.next()) {
				tzone = TimeZone.getTimeZone(rs.getString(1));
			}
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pst, null);
		}
		return tzone;
	}
	
	/**
	 * Retrieves the list of installation codes.
	 *  
	 * @param connection
	 * 			connection object
	 * 
	 * @return list of installation codes
	 * 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred
	 */
	public List<String> getInstallationCodeList(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> installationCodeList = null;
		try {
			pstmt = connection.prepareStatement(GET_INSTALLATION_CODES);
			rs = pstmt.executeQuery();
			if(rs != null) {
				installationCodeList = new ArrayList<String>();
				while(rs.next()) {
					installationCodeList.add(rs.getString(1));
				}
			}
			return installationCodeList;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Inserts the data into PROCESS_REQUEST_SCHEDULE table.
	 * 
	 * @param scheduleData
	 * 		  The reference of {@link ScheduleData}
	 *  
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * 
	 * @throws CommDatabaseException 
	 */
	public Integer insertProcessRequestSchedule(ScheduleData scheduleData,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(INSERT_PROCESS_REQUEST_SCHEDULE_DATA);
			pstmt.setObject(1, scheduleData.getSchId());
			pstmt.setObject(2, scheduleData.getFreqType());
			pstmt.setObject(3, scheduleData.getRecur());
			
			if(scheduleData.getStartDt() != null)
				pstmt.setTimestamp(4, new java.sql.Timestamp(scheduleData.getStartDt()));
			else
				pstmt.setObject(4, null);
			
			pstmt.setObject(5, scheduleData.getSchStat());
			pstmt.setObject(6, scheduleData.getUserId());
			pstmt.setObject(7, scheduleData.getOnWeekDay());
			
			if(scheduleData.getEndDt() != null)
				pstmt.setTimestamp(8, new java.sql.Timestamp(scheduleData.getEndDt()));
			else
				pstmt.setObject(8, null);
			
			pstmt.setObject(9, scheduleData.getEndOccur());
			
			if(scheduleData.getEntryDt() != null)
				pstmt.setTimestamp(10, new java.sql.Timestamp(scheduleData.getEntryDt()));
			else
				pstmt.setObject(10, null);			
			
			pstmt.setObject(11, scheduleData.getModifyId());
			
			if(scheduleData.getModifyDt() != null)
				pstmt.setTimestamp(12, new java.sql.Timestamp(scheduleData.getModifyDt()));
			else
				pstmt.setObject(12, null);
			
			pstmt.setObject(13, scheduleData.getReqStat());	    
			pstmt.setObject(14, scheduleData.getOccurCounter());
			pstmt.setObject(15, scheduleData.getProcessClassNm());
			
			if(scheduleData.getStartTime() != null)
				pstmt.setTimestamp(16, new java.sql.Timestamp(scheduleData.getStartTime()));
			else
				pstmt.setObject(16, null);
			
			if(scheduleData.getEndTime() != null)
				pstmt.setTimestamp(17, new java.sql.Timestamp(scheduleData.getEndTime()));
			else
				pstmt.setObject(17, null);
			
			pstmt.setObject(18, scheduleData.getFutureSchedulingOnly());
			pstmt.setObject(19, scheduleData.getFixedDate());
			pstmt.setObject(20, scheduleData.getEmailIds());
			pstmt.setObject(21, scheduleData.getSkipFlag());		
			pstmt.setObject(22, scheduleData.getWeekdayCheckFlag());
			pstmt.setObject(23, scheduleData.getEndReason());
			pstmt.setObject(24, scheduleData.getKeepAlive());
			pstmt.setObject(25, scheduleData.getInstallationCode());			
			pstmt.setObject(26, scheduleData.getBatchName());			
			
			int executeUpdate = pstmt.executeUpdate();
			connection.commit();
			return executeUpdate;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
		
	}

	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/dao/impl/BatchDAO.java                                                             $
 * 
 * 7     6/21/10 11:50a Lakshmanp
 * removed closing connection in finally
 * 
 * 6     6/21/10 11:47a Lakshmanp
 * removed closing connection in finally
 * 
 * 5     6/18/10 12:22p Lakshmanp
 * removed parameterised constructor and added connection as parameter in all methods
 * 
 * 4     6/17/10 12:32p Kedarr
 * Change the interface implementation.
 * 
 * 3     6/17/10 11:53a Lakshmanp
 * added DAO interface implementation
 * 
 * 2     6/17/10 10:30a Kedarr
 * Needs to be modified as per the Interface.
 * 
 * 1     6/17/10 10:22a Kedarr
 * 
 * 16    5/25/10 4:16p Mandar.vaidya
 * Removed bug from updateBatch method
 * 
 * 15    4/15/10 9:06a Kedarr
 * Changes made to add failed over in update batch
 * 
 * 14    4/09/10 11:10a Mandar.vaidya
 * Added new method getCalendarData with query.
 * 
 * 13    3/30/10 1:03p Kedarr
 * Changes made to update the batch number and revision number in the installation table.
 * 
 * 12    3/22/10 2:02p Mandar.vaidya
 * Modified the INSERT_BATCH query.
 * 
 * 11    3/22/10 1:22p Mandar.vaidya
 * Changes for maxMemory reverted.
 * 
 * 10    3/22/10 12:10p Mandar.vaidya
 * Modified the queries and implementation of methods for newly added fields usedMemoryBefore and usedMemoryAfter in LOG table and maxMemory in BATCH table.
 * 
 * 9     3/22/10 10:10a Mandar.vaidya
 * Changes to UPDATE_LOG query.
 * 
 * 8     3/19/10 8:02p Mandar.vaidya
 * Changes made to updateBatchLog and addBatchLog methods. Added logger.
 * 
 * 7     3/19/10 3:05p Mandar.vaidya
 * Modified the implementation of updateBatchLog method and added new query UPDATE_LOG, new methods addBatchLog and setPreparedStatementBatchLog.
 * 
 * 6     3/15/10 12:50p Mandar.vaidya
 * Added the field cycleNo in insert_log query and method.
 * Added the field instruction_seq_no in insert_batch query and method.
 * 
 * 5     3/11/10 2:29p Mandar.vaidya
 * Added failedOver column in the query INSERT_BATCH.
 * 
 * 4     3/03/10 1:42p Grahesh
 * Added batchEndReason in updateBatch method and in related query
 * 
 * 3     12/18/09 4:14p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/