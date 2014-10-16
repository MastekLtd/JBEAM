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
package com.stgmastek.core.comm.server.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import stg.database.CBeanException;
import stg.pr.beans.ProcessReqParamsController;
import stg.pr.beans.ProcessReqParamsEntityBean;
import stg.pr.beans.ProcessRequestController;
import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.beans.ProcessRequestScheduleController;
import stg.pr.beans.ProcessRequestScheduleEntityBean;
import stg.pr.beans.ScheduleEventCalendarController;
import stg.pr.beans.ScheduleEventCalendarEntityBean;
import stg.pr.engine.IProcessRequest;
import stg.pr.engine.scheduler.ISchedule;
import stg.pr.engine.scheduler.ISchedule.KEEP_ALIVE;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.dao.IBatchDAO;
import com.stgmastek.core.comm.server.vo.CReqCalendarLog;
import com.stgmastek.core.comm.server.vo.CReqInstructionLog;
import com.stgmastek.core.comm.server.vo.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.CalendarData;
import com.stgmastek.core.comm.server.vo.InstructionParameters;
import com.stgmastek.core.comm.server.vo.ScheduleData;
import com.stgmastek.core.comm.util.BaseDAO;
import com.stgmastek.core.comm.util.CommConstants;
import com.stgmastek.core.comm.util.Configurations;
import com.stgmastek.core.comm.util.Constants;
import com.stgmastek.core.comm.util.DatabaseAgnosticCandidate;
import com.stgmastek.monitor.comm.client.BatchLog;
import com.stgmastek.monitor.comm.client.MReqBatch;
import com.stgmastek.monitor.comm.client.MReqInstructionLog;
import com.stgmastek.util.ResultSetMapper;


/**
 * DAO class for all batch related operations.
 * 
 * @author mandar.vaidya
 * 
 */
public class BatchDAO extends BaseDAO implements IBatchDAO {

	private static final Logger logger = Logger.getLogger(BatchDAO.class);
	
	/** Query to get data from BATCH table */
	private static final String GET_BATCH_SUMMARY = "select batch_no," +
			"batch_rev_no,batch_name,batch_type," +
			"exec_start_time,exec_end_time,batch_start_user," +
			"batch_end_user, process_id, batch_end_reason, failed_over," +
			"instruction_seq_no from" +
			" batch where batch_no = ? and batch_rev_no = ?"; 
	
	/** Query to get logged data from LOG table */
	private static final String GET_LOG = "select seq_no, batch_no," +
			" batch_rev_no, be_seq_no, task_name, obj_exec_start_time," +
			" obj_exec_end_time, status, status, sys_act_no," +
			" user_priority, priority_code1, priority_code2," +
			" pre_post, job_type, line, subline, broker," +
			" policy_no, policy_renew_no, veh_ref_no," +
			" cash_batch_no, cash_batch_rev_no, gbi_bill_no," +
			" print_form_no, notify_error_to, date_generate," +
			" generate_by, rec_message, job_desc, object_name," +
			" date_executed, list_ind, entity_type, entity_code," +
			" ref_system_activity_no, error_type, error_description, cycle_no," +
			" used_memory_before, used_memory_after" +
			" from log where " +
			" seq_no = ?";
	
		
	/** Query to fetch the request id for new request */
	@DatabaseAgnosticCandidate	
	private static final String PRE_GET_REQUEST_ID = "select nextval('process_req_seq')";
	
	/** Query to get logged data from BATCH_LOCK table */
	private static final String GET_BATCH_LOCK_DATA = "SELECT 'X' FROM batch_lock WHERE indicator = 'L'";
	
	/** Query to get data from INSTRUCTION_LOG table */
	private static final String GET_INSTRUCTION_LOG = 
		"SELECT seq_no, batch_action, batch_action_time " +
		"FROM instruction_log " +
		"WHERE seq_no = ?";
	
	/** Query to update data into INSTRUCTION_LOG table */
	private static final String UPDATE_INSTRUCTION_LOG = 
		"UPDATE instruction_log " +
		"SET batch_action = ?, batch_action_time = ? " +
		"WHERE seq_no = ?";
	
	/** Query to insert instruction parameters */
	@DatabaseAgnosticCandidate
	private static final String INSERT_INSTRUCTION_PARAMETERS = "insert into " +
			"INSTRUCTION_PARAMETERS ( instruction_log_no, sl_no, name, value, type) " +
			"values (?,?,?,?,?)";
	
	/** Query to insert instruction log */
	@DatabaseAgnosticCandidate
	private static final String INSERT_INSTRUCTION_LOG = "insert into instruction_log (seq_no,batch_no,batch_rev_no,message,message_param,instructing_user,instruction_time,batch_action,batch_action_time) values (?,?,?,?,?,?,?,?,?)";
	
	/** Query to insert the calendar */
	private static final String INSERT_CALENDAR = "INSERT INTO CALENDAR_LOG (calendar_name, year, non_working_date, remark, user_id) values (?,?,?,?,?)";

	/** Query to delete the calendar */
	private static final String DELETE_CALENDAR = "DELETE FROM CALENDAR_LOG where calendar_name = ? AND year = ?";
	
	/** Query to get data from PROCESS_REQUEST_SCHEDULE table for given schedule id (sch_id) */
	private static final String GET_PROCESS_REQUEST_SCHEDULE = 
		"SELECT sch_id, freq_type, recur, sch_stat, on_week_day, start_dt, end_dt, " +
		" end_occur, req_stat, occur_counter, skip_flag, end_reason, keep_alive, user_id " +
		" FROM process_request_schedule " +
		" WHERE sch_id = ?";
	
	/** Query to get data from PROCESS_REQUEST_SCHEDULE table */
	private static final String GET_PROCESS_REQUEST_SCHEDULE_DATA = 
		"SELECT pr.job_name job_name, prs.* " +
		" FROM process_request_schedule prs, process_request pr " +
		" Where prs.sch_id = ? " +
		" and prs.sch_id = pr.req_id";
//	"SELECT pr.job_name, prs.sch_id sch_id, prs.freq_type freq_type, prs.recur recur, prs.start_dt start_dt, " +
//	" prs.sch_stat sch_stat, prs.user_id user_id, prs.on_week_day on_week_day, prs.end_dt end_dt," +
//	" prs.end_occur end_occur, prs.entry_dt entry_dt, prs.modify_id modify_id, " +
//	" prs.modify_dt modify_dt, prs.req_stat req_stat, prs.occur_counter occur_counter, " +
//	" prs.process_class_nm process_class_nm, prs.start_time start_time, prs.end_time end_time, " +
//	" prs.future_scheduling_only future_scheduling_only, prs.fixed_date fixed_date, " +
//	" prs.email_ids email_ids, prs.skip_flag skip_flag, prs.weekday_check_flag weekday_check_flag," +
//	" prs.end_reason end_reason, prs.keep_alive keep_alive" +
//	" FROM process_request_schedule prs, process_request pr " +
//	" Where prs.sch_id = ? " +
//	" and prs.sch_id = pr.req_id";
	
	/** Query to update data from PROCESS_REQUEST_SCHEDULE table */
	private static final String UPDATE_PROCESS_REQUEST_SCHEDULE_DATA = 
		" UPDATE process_request_schedule SET sch_stat = ?, req_stat = ?, " +
		" end_reason = ? " +
		" WHERE sch_id = ? and sch_stat = ?";
	
	/** Query to update data from PROCESS_REQUEST_SCHEDULE table */
	private static final String UPDATE_PROCESS_REQUEST_DATA = 
		" UPDATE process_request SET req_stat = ? " +
		" WHERE sch_id = ? and req_stat = ?";
	
	private static String scheduledBatchName = null;
	private static Timestamp scheduleTime = null;
	
	/** Public zero argument constructor */
	public BatchDAO() {
		super();
	}

	
	/**
	 * DAO method to add instruction log with parameters into INSTRUCTION_LOG.
	 * This method will first insert instruction log into INSTRUCTION_LOG. 
	 * On successful insert, it will insert the instruction parameters into INSTRUCTION_PARAMETERS.
	 * On successful insert, it will insert the data into PRE tables.
	 * 
	 * @param reqInstructionLog
	 * 	 	  The instruction log to be inserted in INSTRUCTION_LOG of CORE @link CReqInstructionLog
	 * 
	 * @param connection
	 * 		  connection object
	 * 
	 * @return reqId of the newly inserted row in PROECSS_REQUEST table,
	 * 		   provided the insert into the instruction log, instruction parameters and 
	 * 		   scheduling of the batch is successful
	 *  
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception
	 */
	public Integer addInstructionLog(CReqInstructionLog reqInstructionLog, Connection connection) 
				throws CommDatabaseException {
		
		PreparedStatement psInLog = null;
		Boolean batchLocked = false;
		try{
			connection.setAutoCommit(false); //begin transaction
			
			batchLocked = isBatchLocked(connection);
			reqInstructionLog.setBatchAction((batchLocked?CommConstants.BATCH_LOCKED:null));
			
			psInLog = connection.prepareStatement(INSERT_INSTRUCTION_LOG);
			psInLog.setObject(1, reqInstructionLog.getSeqNo());
			psInLog.setObject(2, reqInstructionLog.getBatchNo());
			psInLog.setObject(3, reqInstructionLog.getBatchRevNo());			
			psInLog.setString(4, reqInstructionLog.getMessage());
			psInLog.setString(5, reqInstructionLog.getMessageParam());
			psInLog.setString(6, reqInstructionLog.getInstructingUser());
			
			if(reqInstructionLog.getInstructionTime() != null)
				psInLog.setTimestamp(7, new java.sql.Timestamp(reqInstructionLog.getInstructionTime()));
			else
				psInLog.setObject(7, null);
			
			psInLog.setString(8, reqInstructionLog.getBatchAction());
			
			if(reqInstructionLog.getBatchActionTime() != null)
				psInLog.setTimestamp(9, new java.sql.Timestamp(reqInstructionLog.getBatchActionTime()));
			else
				psInLog.setObject(9, null);
			
			psInLog.executeUpdate();
			addInstructionParameters(reqInstructionLog
					.getInstructionParametersList(), reqInstructionLog
					.getSeqNo(), connection);
			
			Integer reqId = 0;
			if (!CommConstants.INTERRUPT_BATCH_MESSAGE.BSSTOBATCH.name().equals(
					reqInstructionLog.getMessage())) {
				if(!batchLocked) {
					reqId = scheduleBatch(reqInstructionLog, connection);
				}
			}
			connection.commit();
			return reqId;
			
		
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, psInLog, null);
		}
		
	}
	
	
	/**
	 * DAO method to add instruction parameters into INSTRUCTION_PARAMETERS
	 * 
	 * @param list
	 * 		  List of instruction parameters to be inserted in INSTRUCTION_PARAMETERS of CORE
	 * @param connection
	 * 		  connection object
	 * @return true if the inserts into the instruction parameters is successful.  
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception
	 */
	public Boolean addInstructionParameters(List<InstructionParameters> list, Integer seqNo, Connection connection) 
				throws CommDatabaseException {
		
		PreparedStatement pstmt = null;
		try{
			pstmt = connection.prepareStatement(INSERT_INSTRUCTION_PARAMETERS);
			if(list != null) {
				for(InstructionParameters instructionParameters: list){
					pstmt.setObject(1, seqNo);
					pstmt.setObject(2, instructionParameters.getSlNo());
					pstmt.setObject(3, instructionParameters.getName());
					pstmt.setObject(4, instructionParameters.getValue());
					pstmt.setObject(5, instructionParameters.getType());
					pstmt.addBatch();
				}			
				pstmt.executeBatch();
			}
			
			return true;
				
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			releaseResources(null, pstmt, null);
		}
		
	}
	
	/**
	 * Schedules the batch as needed
	 * 
	 * @param reqInstructionLog
	 *            The reference of {@link CReqInstructionLog}
	 * @param connection
	 * 		  connection object
	 * @return true, if the batch is scheduled
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O exception
	 */
	private Integer scheduleBatch(CReqInstructionLog reqInstructionLog,
			Connection connection) throws CommDatabaseException {
		Integer requestId = 0;

		Timestamp currentDate = new Timestamp(new Date().getTime());
		String currentUser = null;
		Timestamp batchScheduleDate = null;
		List<InstructionParameters> instructionParamsList = null;
		try {

			currentUser = reqInstructionLog.getInstructingUser();
					
			String emailsGroup = Configurations.getConfigurations().getConfigurations("CORE", "EMAIL",
					"NOTIFICATION_GROUP");
			String secProcessClassName = Configurations.getConfigurations().getConfigurations("CORE",
					"CALENDAR_CLASS", "JV");
			String prProcessClassName = Configurations.getConfigurations().getConfigurations("CORE",
					"PROCESS_CLASS", "JV");
			String prsProcessClassName = Configurations.getConfigurations().getConfigurations("CORE",
					"SCHEDULE_CLASS", "JV");
			
			HashMap<String, String> doNotInsertMap = Configurations.getConfigurations().getConfigurations("CORE", "DEL_INSTR_PARAMS");
			
			instructionParamsList = reqInstructionLog
					.getInstructionParametersList();
			if(instructionParamsList != null && instructionParamsList.size() > 0) {
				//For reports, the processing class will be different.
				//Following code will retrieve the appropriate class for a report 
				for (InstructionParameters instructionParameters : instructionParamsList) {
					if("report".equalsIgnoreCase(instructionParameters.getName())){
						prProcessClassName = instructionParameters.getValue();
					}
				}
				InstructionParameters parameter = new InstructionParameters();
				parameter.setName("INSTRUCTING_USER");
				parameter.setSlNo(instructionParamsList.size()+1);
				parameter.setType("S");
				parameter.setValue(reqInstructionLog.getInstructingUser());
				instructionParamsList.add(parameter);
			}
			// First get the process request identifier
			requestId = getRequestId(connection);

			// Insert into Process Request			
			ScheduleEventCalendarEntityBean secBean = new ScheduleEventCalendarEntityBean();

			// Set schedule event calendar bean
			secBean.setSchId(requestId);
			secBean.setCategory("CALENDAR");
			secBean.setSerialNo(1);
			secBean.setProcessClassNm(secProcessClassName);

			// Now the main inserts...
			connection.setAutoCommit(false);
			// Set schedule bean
			ProcessRequestScheduleEntityBean prsBean = getProcessRequestScheduleEntityBean(
					reqInstructionLog, requestId, emailsGroup,
					prsProcessClassName, currentUser, currentDate);

			if (prsBean != null && prsBean.getFreqType() != null) {
				batchScheduleDate = prsBean.getStartDt();
				
				ProcessRequestScheduleController prsController = new ProcessRequestScheduleController(
						connection);
				prsController.create(prsBean);
				ScheduleEventCalendarController secController = new ScheduleEventCalendarController(
						connection);
				secController.create(secBean);
			}

			// Set Process Request Bean
			ProcessRequestEntityBean prBean = getProcessRequestEntityBean(
					requestId, prProcessClassName, currentUser, currentDate,
					Configurations.getConfigurations(), emailsGroup, batchScheduleDate, ((prsBean != null && prsBean.getFreqType() != null)?prsBean.getSchId():null) );

			ProcessRequestController prController = new ProcessRequestController(
					connection);
			prController.create(prBean);

			ProcessReqParamsController prpController = new ProcessReqParamsController(
					connection);
			// Set Process Request Parameter Bean
			if(instructionParamsList != null){
				for (InstructionParameters instructionParameter : instructionParamsList) {
					if (!doNotInsertMap.containsValue(instructionParameter.getName())) {
						ProcessReqParamsEntityBean prpBean = new ProcessReqParamsEntityBean();
						prpBean.setReqId(requestId);
						prpBean.setParamNo(instructionParameter.getSlNo());
						prpBean.setParamFld(instructionParameter.getName());
						prpBean.setParamVal(instructionParameter.getValue());
						
						String type = instructionParameter.getType();
						prpBean.setParamDataType(type);
						if (type.equals("TS") || type.equals("TSA")
								|| type.equals("DT") || type.equals("DTA"))
							prpBean.setStaticDynamicFlag("D");
						else
							prpBean.setStaticDynamicFlag("S");
						prpController.create(prpBean);
					} // if not in doNotInsertMap
				} //for each InstructionParameters
			}

			ProcessReqParamsEntityBean prpBean = new ProcessReqParamsEntityBean();
			prpBean.setReqId(requestId);
			if(instructionParamsList != null) {
				prpBean.setParamNo(instructionParamsList.size() + 1);
			}else {
				prpBean.setParamNo(1);
			}
				
			prpBean.setParamFld("INSTRUCTION_LOG_SEQ");
			prpBean.setParamVal(String.valueOf(reqInstructionLog.getSeqNo()));
			prpBean.setParamDataType("I");
			prpBean.setStaticDynamicFlag("S");
			prpController.create(prpBean);
			return requestId;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (CBeanException e) {
			throw new CommDatabaseException(e);
		} finally {
			
		}
	}


	/**
	 * Creates (Inserts) records in the process_request table.
	 * 
	 * @param requestId
	 *            Instruction request id.
	 *            
	 * @param processClassName
	 *            The process class name
	 * @param currentUser
	 *            The current application user
	 * 
	 * @param currentDate
	 *            The system date
	 * 
	 * @param config
	 *            The {@link Configurations} reference object
	 * 
	 * @param emailsGroup
	 *            The email group to which the notification regarding batch
	 *            process will be sent
	 * 
	 * @param batchScheduleDate
	 *            The scheduled batch date and time
	 * 
	 * @param scheduleId
	 * 			The schedule id
	 * 
	 * @return the reference of {@link ProcessRequestEntityBean}
	 * 
	 * @throws CommDatabaseException
	 *             Any database related I/O exception
	 */
	private ProcessRequestEntityBean getProcessRequestEntityBean(
			Integer requestId, String processClassName, String currentUser,
			Timestamp currentDate, Configurations config, String emailsGroup,
			Timestamp batchScheduleDate, Long scheduleId) throws CommDatabaseException {
		ProcessRequestEntityBean prBean = null;
		try {
			String preStuckThreadMaxLimit = config.getConfigurations("CORE",
					"PRE_STUCK_THREAD", "MAX_LIMIT");
			String preStuckThreadLimit = config.getConfigurations("CORE",
					"PRE_STUCK_THREAD", "LIMIT");
			String preRequestType = config.getConfigurations("CORE",
					"PRE_REQUEST_TYPE", "VALUE");

			prBean = new ProcessRequestEntityBean();
			prBean.setReqId(requestId);
			prBean.setReqDt(currentDate);
			if (scheduleId != null) {
				prBean.setSchId(scheduleId.longValue());
			}
			prBean.setScheduledTime(scheduleTime);
			prBean.setUserId(currentUser);
			prBean.setReqStat("Q");
			prBean.setProcessClassNm(processClassName);
			prBean.setGrpStInd("S");
			prBean.setReqType(preRequestType);
			prBean.setCalScheduledTime(scheduleTime);
			prBean.setEmailIds(emailsGroup);
			prBean.setJobName("JBEAM Core Processor" + ((scheduledBatchName == null)?"":" [" + scheduledBatchName+"]"));

			if(logger.isDebugEnabled()){
				logger.debug(prBean.getJobName() + " - " + prBean.getScheduledTime());
			}
			if (preStuckThreadLimit != null)
				prBean.setStuckThreadLimit(Long.parseLong(preStuckThreadLimit));

			if (preStuckThreadMaxLimit != null)
				prBean.setStuckThreadMaxLimit(Long
						.parseLong(preStuckThreadMaxLimit));

		} catch (CBeanException e) {
		} finally{
			scheduledBatchName = null;
//			scheduleTime = null;
		}
		return prBean;
	}
	
	/**
	 * Creates (Inserts) records in the process_request_schedule table.
	 * 
	 * @param reqInstructionLog
	 *            The instruction log to be inserted in INSTRUCTION_LOG of CORE @link
	 *            CReqInstructionLog
	 * 
	 * @param requestId
	 *            Instruction request id.
	 * 
	 * @param currentUser
	 *            The current application user
	 * 
	 * @param currentDate
	 *            The system date
	 * 
	 * @param processClassName
	 *            The process class name
	 * 
	 * @param emailsGroup
	 *            The email group to which the notification regarding batch
	 *            process will be sent
	 * 
	 * @throws CommDatabaseException
	 *             Any database related I/O exception
	 */
	private ProcessRequestScheduleEntityBean getProcessRequestScheduleEntityBean(
			CReqInstructionLog reqInstructionLog, Integer requestId,
			String emailsGroup, String processClassName, String currentUser,
			Timestamp currentDate) throws CommDatabaseException {

		ProcessRequestScheduleEntityBean scheduleBean = null;
		String instructionName = null;
		String instructionValue = null;


		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

		List<InstructionParameters> list = reqInstructionLog
				.getInstructionParametersList();
		KEEP_ALIVE keepAlive = KEEP_ALIVE.NO;
		if(list != null) {
			for (InstructionParameters instructionParameters : list) {
				instructionName = instructionParameters.getName();
				instructionValue = instructionParameters.getValue();
				
				if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.BATCH_NAME.name()
						.equals(instructionName)) {
					scheduledBatchName = instructionValue;
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.FREQUENCY.name()
						.equals(instructionName)) {
					if (scheduleBean == null) {
						scheduleBean = new ProcessRequestScheduleEntityBean();
					}
					scheduleBean.setFreqType(instructionValue.toUpperCase());
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.RECUR_EVERY
						.name().equals(instructionName)) {
					if (scheduleBean == null) {
						scheduleBean = new ProcessRequestScheduleEntityBean();
					}
					scheduleBean.setRecur(Integer.parseInt(instructionValue));
//				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.BATCH_RUN_DATE
//						.name().equals(instructionName)) {
//					
//					Date parsedDate;
//					try {
//						parsedDate = dateFormat2.parse(instructionValue);
//						if (scheduleBean == null) {
//							scheduleBean = new ProcessRequestScheduleEntityBean();
//						}
//						scheduleBean
//						.setStartDt(new Timestamp(parsedDate.getTime()));
//					} catch (ParseException e) {
//						// Dummy
//						e.printStackTrace();
//					}
					
				}else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.SCHEDULE_DATE
						.name().equals(instructionName)) {
					
					Date parsedDate;
					try {
						parsedDate = sdf.parse(instructionValue);
						scheduleTime = new Timestamp(parsedDate.getTime());
					} catch (ParseException e) {
						// Dummy
					}
					
				}else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.WEEK_DAY
						.name().equals(instructionName)) {
					if (scheduleBean == null) {
						scheduleBean = new ProcessRequestScheduleEntityBean();
					}
					scheduleBean.setOnWeekDay(instructionValue);
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.END_ON_DATE
						.name().equals(instructionName)) {
					Date parsedDate;
					try {
						parsedDate = sdf.parse(instructionValue);
						if (scheduleBean == null) {
							scheduleBean = new ProcessRequestScheduleEntityBean();
						}
						scheduleBean
						.setEndDt(new Timestamp(parsedDate.getTime()));
					} catch (ParseException e) {
						// Dummy
					}
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.END_ON_OCCURRENCE
						.name().equals(instructionName)) {
					if (scheduleBean == null) {
						scheduleBean = new ProcessRequestScheduleEntityBean();
					}
					scheduleBean.setEndOccur(Long.parseLong(instructionValue));
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.SKIP_FLAG
						.name().equals(instructionName)) {
					if (scheduleBean == null) {
						scheduleBean = new ProcessRequestScheduleEntityBean();
					}
					scheduleBean.setSkipFlag(instructionValue);
				} else if (CommConstants.SCHEDULE_INSTRCUTION_PARAMS.KEEP_ALIVE.name().equals(instructionName)) {
					keepAlive = KEEP_ALIVE.resolve(instructionValue);
				}
			}
		}

		if (scheduleBean != null) {
			scheduleBean.setSchId(requestId);
			scheduleBean.setSchStat(ISchedule.SCHEDULE_STATUS.ACTIVE.getID());
			scheduleBean.setStartDt(scheduleTime);
			scheduleBean.setUserId(currentUser);
			scheduleBean.setEntryDt(currentDate);
			scheduleBean.setReqStat(IProcessRequest.REQUEST_STATUS.QUEUED.getID());
			scheduleBean.setOccurCounter(1);
			scheduleBean.setProcessClassNm(processClassName);
			scheduleBean.setFutureSchedulingOnly(ISchedule.FUTURE_SCHEDULING_ONLY);
			scheduleBean.setFixedDate("N");
			scheduleBean.setEmailIds(emailsGroup);
			scheduleBean.setWeekdayCheckFlag("N");
			scheduleBean.setKeepAlive(keepAlive.getID());
		}

		return scheduleBean;
	}
	
	
	/**
	 * Gets the batch data from BATCH and stores in {@link MReqBatch}.
	 * 
	 * @param batchNo
	 * 		  The batch no
	 * @param batchRevNo
	 * 		  The batch revision no
	 * @param connection
	 * 		  connection object
	 * 
	 * @return batchData 
	 * 		   Batch data from BATCH.
	 * @throws CommDatabaseException 
	 * 		   Any database I/O exception
	 */
	public MReqBatch getBatchData(Integer batchNo, Integer batchRevNo, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(GET_BATCH_SUMMARY);
			pstmt.setInt(1, batchNo);
			pstmt.setInt(2, batchRevNo);
			rs = pstmt.executeQuery();
			MReqBatch batchData = ResultSetMapper.getInstance().mapSingleRecord(rs, MReqBatch.class); 
			return batchData;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		} catch (InstantiationException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new CommDatabaseException(e);
		} catch (InvocationTargetException e) {
			throw new CommDatabaseException(e);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}		
	}
	
	/**
	 * Gets the logged batch data from LOG 
	 * and stores in {@link BatchLog}.
	 * 
	 * @param batchSeqNo
	 * 		  The batch sequence no
	 * @param connection
	 * 		  connection object
	 * 
	 * @return batchLogData 
	 * 		   Logged batch data from LOG.
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception 
	 */
	public BatchLog getBatchLogData(Integer batchSeqNo, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(GET_LOG);
			pstmt.setInt(1, batchSeqNo);
			rs = pstmt.executeQuery();
			BatchLog batchLogData = ResultSetMapper.getInstance().mapSingleRecord(rs, BatchLog.class); 
			return batchLogData;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		} catch (InstantiationException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new CommDatabaseException(e);
		} catch (InvocationTargetException e) {
			throw new CommDatabaseException(e);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}		
	}

	/**
	 * Fetch the request
	 *   
	 * @param connection
	 * 		  connection object
	 * 
	 * @return The Process Request ID
	 * @throws CommDatabaseException
	 * 		   Any database related I/O exception 
	 */
	@DatabaseAgnosticCandidate
	private Integer getRequestId(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmtSeq = null;
		ResultSet rs = null;
		Integer requestId = 0;
		try {
			pstmtSeq = connection.prepareStatement(PRE_GET_REQUEST_ID);
			rs = pstmtSeq.executeQuery();
			rs.next();
			requestId = rs.getInt(1);
			return requestId;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmtSeq, null);
		}
	}
	
	/**
	 * Defines a calendar with selected non working days.
	 * 
	 * @param calendarVO
	 *            The reference of Calendar data
	 * @param connection
	 * 		  connection object
	 * 
	 * @throws CommDatabaseException
	 */
	public void defineCalendar(CReqCalendarLog calendarVO, Connection connection)
			throws CommDatabaseException {
		CalendarData calendarData = null;
		List<CalendarData> calendarDataList = null;
		try {
			if (calendarVO != null) {
				calendarDataList = calendarVO.getCalendarList();
				calendarData = calendarVO.getCalendarData();

				if (calendarData != null)
					deleteCalendar(calendarData, connection);

				if (calendarDataList != null)
					addCalendar(calendarDataList, connection);
			}

		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}
	}
	
	/**
	 * Inserts the calendar data into SCHEDULE table.
	 * 
	 * @param calendarDataList
	 *            The list of calendar data
	 * @param connection
	 * 		  connection object
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer addCalendar(List<CalendarData> calendarDataList, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(INSERT_CALENDAR);
			for (CalendarData calendarData : calendarDataList) {
				pstmt.setObject(1, calendarData.getCalendarName());
				pstmt.setObject(2, calendarData.getYear());
				pstmt.setDate(3, new java.sql.Date(calendarData
						.getNonWorkingDate()));
				pstmt.setObject(4, calendarData.getRemark());
				pstmt.setObject(5, calendarData.getUserId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			return 0;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}

	/**
	 * Deletes the calendar data from the table.
	 * 
	 * @param calendarData
	 *            The reference of calendar data
	 * @param connection
	 * 		  connection object
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public void deleteCalendar(CalendarData calendarData, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(DELETE_CALENDAR);
			pstmt.setString(1, calendarData.getCalendarName());
			pstmt.setString(2, calendarData.getYear());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}


	/**
	 * Retrieves the batch lock details.
	 * 
	 * @param connection
	 * 		  	connection object
	 * 
	 * @return batch lock object
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Boolean isBatchLocked(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(GET_BATCH_LOCK_DATA);
			rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}
	}


	/**
	 * Retrieves the instruction log for the supplied sequence number   
	 * 
	 * @param seqNo
	 * 		  The sequence number
	 *  
	 * @param connection
	 * 		  	connection object
	 * 
	 * @return An instance of the request instruction log
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public MReqInstructionLog getInstructionLog(Integer seqNo,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(GET_INSTRUCTION_LOG);
			pstmt.setInt(1, seqNo);
			rs = pstmt.executeQuery();
			MReqInstructionLog instructionLog = 
				ResultSetMapper.getInstance().mapSingleRecord(rs, MReqInstructionLog.class);
			return instructionLog;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		} catch (InstantiationException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new CommDatabaseException(e);
		} catch (InvocationTargetException e) {
			throw new CommDatabaseException(e);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
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
			
			pstmt.setInt(3, instructionLog.getSeqNo());
			
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
	
	/**
	 * Retrieves the active schedule for the supplied schedule ids (wrapped
	 * in {@link CReqProcessRequestScheduleVO}     
	 * 
	 * @param requestScheduleData
	 * 			The reference to {@link ScheduleData}
	 * 
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return An instance of active schedule from PROCESS_REQUEST_SCHEDULE table. 
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public ScheduleData getProcessRequestScheduleData(ScheduleData requestScheduleData,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_PROCESS_REQUEST_SCHEDULE);
			pstmt.setInt(1, requestScheduleData.getSchId());
			rs = pstmt.executeQuery();
			
			if(!rs.next())
				return null;
			
			ScheduleData scheduleData = new ScheduleData();
			scheduleData.setSchId(rs.getInt("sch_id"));
			scheduleData.setFreqType(rs.getString("freq_type"));
			scheduleData.setRecur(rs.getInt("recur"));
			scheduleData.setSchStat(rs.getString("sch_stat"));
			scheduleData.setOnWeekDay(rs.getString("on_week_day"));
			
			if(rs.getTimestamp("start_dt") == null)
				scheduleData.setStartDt(null);
			else
				scheduleData.setStartDt(rs.getTimestamp("start_dt").getTime());				
			
			if(rs.getTimestamp("end_dt") == null)
				scheduleData.setEndDt(null);
			else
				scheduleData.setEndDt(rs.getTimestamp("end_dt").getTime());
			
			scheduleData.setEndOccur(rs.getInt("end_occur"));
			scheduleData.setReqStat(rs.getString("req_stat"));
			scheduleData.setOccurCounter(rs.getInt("occur_counter"));
			scheduleData.setSkipFlag(rs.getString("skip_flag"));
			scheduleData.setEndReason(rs.getString("end_reason"));
			scheduleData.setKeepAlive(rs.getString("keep_alive"));
			scheduleData.setUserId(rs.getString("user_id"));

			return scheduleData;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Cancels the schedule in two steps.
	 * <li>i) Update the PROCESS_REQUEST table with req_stat = 'X' for provided schedule id</li>.<BR>
	 * If this step is completed successfully the next step will be taken
	 * <li>ii)  Update the PROCESS_REQUEST_SCHEDULE table with sch_stat = 'C' and req_stat = 'X' 
	 * for provided schedule id</li>
	 *  
	 * @param scheduleData
	 * 				The reference of {@link ScheduleData}
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return integer[] consisting of the records updated in PROCESS_REQUEST and 
	 * 			PROCESS_REQUEST_SCHEDULE tables.
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer[] cancelSchedule(ScheduleData scheduleData, Connection connection) throws CommDatabaseException {
		Integer executeUpdatePR = updateProcessRequest(scheduleData, connection);
		Integer executeUpdatePRS = updateProcessRequestSchedule(scheduleData, connection);
		return new Integer[] {executeUpdatePR, executeUpdatePRS};
		
	}
	
	/**
	 * Update the PROCESS_REQUEST table with req_stat = 'X' for provided schedule id 
	 * 
	 * @param scheduleData
	 * 				The reference of {@link ScheduleData}
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return 1 if the record was updated successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	private Integer updateProcessRequest(ScheduleData scheduleData, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_PROCESS_REQUEST_DATA);
			pstmt.setString(1, IProcessRequest.REQUEST_STATUS.USER_CANCELLED.getID());
			pstmt.setInt(2, scheduleData.getSchId());
			pstmt.setString(3, IProcessRequest.REQUEST_STATUS.QUEUED.getID());
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
	
	/**
	 * Update the PROCESS_REQUEST_SCHEDULE table with sch_stat = 'C' and req_stat = 'X' 
	 * for provided schedule id
	 * 
	 * @param scheduleData
	 * 				The reference of {@link ScheduleData}
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return 1 if the record was updated successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	private Integer updateProcessRequestSchedule(ScheduleData scheduleData, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_PROCESS_REQUEST_SCHEDULE_DATA);
			pstmt.setString(1, ISchedule.SCHEDULE_STATUS.USER_CANCELLED.getID());
			pstmt.setString(2, IProcessRequest.REQUEST_STATUS.USER_CANCELLED.getID());
			pstmt.setString(3, Constants.USER_CANCELLED);
			pstmt.setInt(4, scheduleData.getSchId());
			pstmt.setString(5, ISchedule.SCHEDULE_STATUS.ACTIVE.getID());
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


	/**
	 * Retrieves the active schedule for the supplied request id.
	 * This request id is retrieved after batch scheduling operation.  
	 * 
	 * @param reqId
	 * 			A Request id retrieved from PROCESS_REQUEST table after batch scheduling operation.
	 * 
	 * @param connection
	 * 			The Connection object
	 * 
	 * @return An instance of active schedule from PROCESS_REQUEST_SCHEDULE table. 
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public ScheduleData getProcessRequestScheduleData(Integer reqId,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ScheduleData scheduleData = new ScheduleData();

		try {
			pstmt = connection.prepareStatement(GET_PROCESS_REQUEST_SCHEDULE_DATA);
			pstmt.setInt(1, reqId);
			rs = pstmt.executeQuery();
			
			if(!rs.next())
				return null;
			
			String batchName = rs.getString("job_name");
			if(batchName.indexOf("[") != -1)
				batchName = batchName.substring(batchName.indexOf("[") + 1, batchName.indexOf("]"));
			else
				batchName = "----";//String.valueOf(rs.getInt("sch_id"));//
			
			scheduleData.setBatchName(batchName);
			scheduleData.setSchId(rs.getInt("sch_id"));
			scheduleData.setFreqType(rs.getString("freq_type"));
			scheduleData.setRecur(rs.getInt("recur"));
			scheduleData.setSchStat(rs.getString("sch_stat"));
			scheduleData.setOnWeekDay(rs.getString("on_week_day"));
			scheduleData.setEndOccur(rs.getInt("end_occur"));
			scheduleData.setReqStat(rs.getString("req_stat"));
			scheduleData.setOccurCounter(rs.getInt("occur_counter"));
			scheduleData.setSkipFlag(rs.getString("skip_flag"));
			scheduleData.setEndReason(rs.getString("end_reason"));
			scheduleData.setKeepAlive(rs.getString("keep_alive"));
			scheduleData.setUserId(rs.getString("user_id"));
			scheduleData.setFixedDate(rs.getString("fixed_date"));			
			scheduleData.setModifyId(rs.getString("modify_id"));
			scheduleData.setProcessClassNm(rs.getString("process_class_nm"));
			scheduleData.setFutureSchedulingOnly(rs.getString("future_scheduling_only"));
			scheduleData.setEmailIds(rs.getString("email_ids"));
			scheduleData.setWeekdayCheckFlag(rs.getString("weekday_check_flag"));
			
			if(rs.getTimestamp("start_dt") == null)
				scheduleData.setStartDt(null);
			else
				scheduleData.setStartDt(rs.getTimestamp("start_dt").getTime());				
			
			if(rs.getTimestamp("end_dt") == null)
				scheduleData.setEndDt(null);
			else
				scheduleData.setEndDt(rs.getTimestamp("end_dt").getTime());
			
			if(rs.getTimestamp("entry_dt") == null)
				scheduleData.setEntryDt(null);
			else
				scheduleData.setEntryDt(rs.getTimestamp("entry_dt").getTime());
			
			if(rs.getTimestamp("modify_dt") == null)
				scheduleData.setModifyDt(null);
			else
				scheduleData.setModifyDt(rs.getTimestamp("modify_dt").getTime());
			
			if(rs.getTimestamp("start_time") == null)
				scheduleData.setStartTime(null);
			else
				scheduleData.setStartTime(rs.getTimestamp("start_time").getTime());
			
			if(rs.getTimestamp("end_time") == null)
				scheduleData.setEndTime(null);
			else
				scheduleData.setEndTime(rs.getTimestamp("end_time").getTime());
			
			
//			ScheduleData processRequestScheduleData = ResultSetMapper.getInstance()
//					.mapSingleRecord(rs, ScheduleData.class);

			return scheduleData;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
}

/*
* Revision Log
* -------------------------------
* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/impl/BatchDAO.java 2     7/07/10 3:13p Keda $
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 2     7/07/10 3:13p Kedarr
 * Changes made for connection leaks.
 * 
 * 1     6/21/10 11:29a Lakshmanp
 * initial version
 * 
 * 
*
*
*/