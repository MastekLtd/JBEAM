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
package com.stgmastek.monitor.ws.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import stg.utils.StringUtils;

import com.stgmastek.core.comm.client.CResProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.dao.IMonitorDAO;
import com.stgmastek.monitor.ws.server.util.DatabaseAgnosticCandidate;
import com.stgmastek.monitor.ws.server.vo.BatchDetails;
import com.stgmastek.monitor.ws.server.vo.BatchInfo;
import com.stgmastek.monitor.ws.server.vo.BatchObject;
import com.stgmastek.monitor.ws.server.vo.FailedObjectDetails;
import com.stgmastek.monitor.ws.server.vo.InstallationData;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.InstructionLog;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ListenerInfo;
import com.stgmastek.monitor.ws.server.vo.MenuData;
import com.stgmastek.monitor.ws.server.vo.MonitorCalendarData;
import com.stgmastek.monitor.ws.server.vo.ObjectExecutionGraphData;
import com.stgmastek.monitor.ws.server.vo.ProcessRequestScheduleData;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ReqBatchDetails;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ReqListenerInfo;
import com.stgmastek.monitor.ws.server.vo.ReqSearchBatch;
import com.stgmastek.monitor.ws.server.vo.SystemDetails;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.BaseDAO;
import com.stgmastek.monitor.ws.util.CommonUtils;
import com.stgmastek.monitor.ws.util.Constants;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all Monitor related I/O to the database
 * 
 * @author mandar.vaidya
 * 
 */
public class MonitorDAO extends BaseDAO implements IMonitorDAO {

	  private static final String GET_SYSTEM_INFO = "SELECT batch_no, batch_rev_no, java_version, pre_version, os_config,output_dir_path,output_dir_free_mem, max_memory, used_memory FROM system_info WHERE installation_code = ?   AND batch_no = ?   AND batch_rev_no = ?";
	  private static final String GET_BATCH_SUMMARY = "SELECT batch_no, batch_rev_no, batch_name,batch_type, exec_start_time,exec_end_time, batch_start_user, batch_end_user, process_id,failed_over, instruction_seq_no, batch_end_reason FROM batch WHERE installation_code = ? and batch_no = ? and batch_rev_no = ?";
	  private static final String GET_BATCH_DETAILS = "SELECT batch_no, batch_rev_no, batch_name,batch_type, exec_start_time,exec_end_time, batch_start_user, batch_end_user, process_id,failed_over FROM batch WHERE installation_code = ? and instruction_seq_no = ?";
	  private static final String GET_BATCH_INFO = "SELECT batch_no batch_number, batch_rev_no batch_rev_number, exec_start_time batch_start_time,        exec_end_time batch_end_time, sum(total_objs) total_objects_executed,        sum(failed_objs) total_objects_failed, sum(lst_cnt) total_listener_count, batch_end_reason FROM ( SELECT b.installation_code, b.batch_no, b.batch_rev_no,    b.exec_start_time, b.exec_end_time, b.batch_end_reason,    count(c.status) failed_objs, 0 total_objs, 0 lst_cnt FROM  batch b , log c   WHERE b.batch_no = c.batch_no       AND   b.batch_rev_no = c.batch_rev_no       AND   b.installation_code = c.installation_code        AND   c.status = '99'        AND   b.installation_code = ?       AND   b.batch_no = ?       GROUP BY b.installation_code, b.batch_no, b.batch_rev_no,  b.exec_start_time, b.exec_end_time, b.batch_end_reason       UNION ALL       SELECT b.installation_code, b.batch_no, b.batch_rev_no,    b.exec_start_time, b.exec_end_time, b.batch_end_reason,    sum(0) failed_objs, count(c.status) total_objs,    count(distinct list_ind) lst_cnt       FROM  batch b , log c       WHERE b.batch_no = c.batch_no       AND   b.batch_rev_no = c.batch_rev_no AND   b.installation_code = c.installation_code       AND   b.installation_code = ?       AND   b.batch_no = ?       GROUP BY b.installation_code, b.batch_no, b.batch_rev_no,      b.exec_start_time, b.exec_end_time, b.batch_end_reason) z GROUP BY installation_code, batch_no, batch_rev_no, exec_start_time,   exec_end_time, batch_end_reason";
	  private static final String GET_LISTENER_INFO = "select TOL.list_ind LIST_IND, TOTAL_OBJECTS, coalesce(FAILED_OBJECTS, 0) TOTAL_OBJECTS_FAILED, to_number((to_Char(TIME_TAKEN / 60000, '99.99')),'99.99') TIME_TAKEN from (  select count(status) TOTAL_OBJECTS, sum(time_taken) TIME_TAKEN, list_ind  from LOG  where installation_code = ? and batch_no = ? and batch_rev_no = ? group by list_ind) TOL left outer join (select count(status) FAILED_OBJECTS, list_ind from LOG where installation_code = ? and batch_no = ? and batch_rev_no = ? and status = '99' group by list_ind) TFL on TFL.list_ind = TOL.list_ind ORDER BY LIST_IND";
	  private static final String GET_GRAPH_DATA = "SELECT installation_code, graph_id, batch_no, batch_rev_no, collect_time, graph_x_axis, graph_y_axis, graph_value FROM GRAPH_DATA_LOG where installation_code = ? and graph_id = ? AND batch_no= ? AND batch_rev_no = ? ORDER BY collect_time DESC";

	  @DatabaseAgnosticCandidate
	  private static final String GET_BATCH_FAILED_OBJECTS = 
		  "select ROW_NUMBER() OVER (ORDER BY be_seq_no ASC) AS FailedObjectNo,be_seq_no FailedObjectSequence, object_name FailedObjectName,task_name TaskName, time_taken TimeTaken, coalesce(error_type, '---') ErrorType,coalesce(error_description, '---') ErrorDescription, list_ind ListenerId from LOG where installation_code = ? and batch_no = ? AND batch_rev_no = ? AND status = '99' and list_ind = coalesce(?, list_ind) ";

	  @DatabaseAgnosticCandidate
	  private static final String GET_INSTALLATION_DATA = "SELECT installation_code INST_CODE, batch_no B_NO,batch_rev_no B_REV_NO,exec_start_time BATCH_START_TIME,exec_end_time BATCH_END_TIME,batch_end_reason, SUM(failed_objs) TOTAL_FAILED_OBJECTS,sum(total_objs) TOTAL_OBJECTS, TIMEZONE_ID FROM (SELECT a.installation_code, a.batch_no, a.batch_rev_no,a.timezone_id TIMEZONE_ID, b.exec_start_time,b.exec_end_time,b.batch_end_reason,count(c.status) failed_objs,sum(0) total_objs FROM installation a left outer join batch b on (a.installation_code = b.installation_code and a.batch_no = b.batch_no and a.batch_rev_no = b.batch_rev_no) left outer join log c on (a.installation_code = c.installation_code and a.batch_no = c.batch_no and a.batch_rev_no = c.batch_rev_no) inner join (SELECT DISTINCT installation_code FROM user_installation_role WHERE user_id = ?) d on (a.installation_code = d.installation_code) WHERE c.status = '99' AND a.installation_code = coalesce(?,a.installation_code) GROUP BY a.installation_code, a.batch_no, a.batch_rev_no, b.exec_start_time, b.exec_end_time, b.batch_end_reason, a.timezone_id UNION ALL SELECT a.installation_code,a.batch_no, a.batch_rev_no, a.timezone_id TIMEZONE_ID, b.exec_start_time,b.exec_end_time, b.batch_end_reason, sum(0) failed_objs,count(c.status)total_objs FROM installation a left outer join batch b on (a.installation_code = b.installation_code and a.batch_no = b.batch_no and a.batch_rev_no = b.batch_rev_no) left outer join log c on (a.installation_code = c.installation_code and a.batch_no = c.batch_no and a.batch_rev_no = c.batch_rev_no) inner join (SELECT DISTINCT installation_code FROM user_installation_role WHERE user_id = ?) d on (a.installation_code = d.installation_code) WHERE  a.installation_code = coalesce(?,a.installation_code) GROUP BY a.installation_code,a.batch_no, a.batch_rev_no, a.timezone_id, b.exec_start_time,b.exec_end_time, b.batch_end_reason) y GROUP BY INSTALLATION_CODE, BATCH_NO, BATCH_REV_NO, EXEC_START_TIME, EXEC_END_TIME, BATCH_END_REASON, TIMEZONE_ID";

	  @DatabaseAgnosticCandidate
	  private static final String GET_BATCH_COMPLETED_DATA = "select BATCH_NO, BATCH_REV_NO, BATCH_NAME, BATCH_TYPE, EXEC_START_TIME, EXEC_END_TIME, PROCESS_ID, BATCH_END_REASON FROM BATCH WHERE installation_code = ? AND batch_no = coalesce(?, batch_no) AND upper(batch_name) like coalesce(?, upper(batch_name)) AND batch_type  = coalesce(?, batch_type) AND to_char(exec_start_time, 'dd-Mon-yyyy') = coalesce(?, to_char(exec_start_time, 'dd-Mon-yyyy') ) AND batch_end_reason  = coalesce(?, batch_end_reason) ";
	  private static final String GET_BATCH_ENTITY_DATA = "select entity ENTITY, lookup_column LOOKUP_COLUMN, lookup_value LOOKUP_VALUE, value_column VALUE_COLUMN, precedence_order PRECEDENCE_ORDER, on_error_fail_all ON_ERROR_FLAG, description FROM column_map WHERE installation_code = coalesce(?, installation_code) ORDER BY precedence_order";

	  @DatabaseAgnosticCandidate
	  private static final String GET_INSTRUCTION_LOG_SEQ = "select nextval('instruction_log_seq')";

	  @DatabaseAgnosticCandidate
	  private static final String INSERT_INSTRUCTION_PARAMETERS = "insert into INSTRUCTION_PARAMETERS ( instruction_log_no, sl_no, name, value, type) values (?,?,?,?,?)";

	  @DatabaseAgnosticCandidate
	  private static final String INSERT_INSTRUCTION_LOG = "insert into INSTRUCTION_LOG (installation_code, seq_no, batch_no, batch_rev_no, message, instructing_user, instruction_time) values (?,?,?,?,?,?,?)";
	  private static final String GET_PROGRESS_LEVEL_DATA = "SELECT installation_code, batch_no, batch_rev_no, indicator_no, prg_level_type, prg_activity_type, cycle_no, status, start_datetime, end_datetime, error_desc, failed_over FROM PROGRESS_LEVEL  where installation_code = ? AND batch_no= ? AND batch_rev_no = ? ORDER BY indicator_no DESC";
	  private static final String GET_CALENDAR_DATA = "SELECT calendar_name, year, non_working_date, remark, user_id  FROM CALENDAR_LOG where installation_code = ? and calendar_name = ? AND year = ?";
	  private static final String GET_CALENDARS = "SELECT distinct calendar_name, year FROM CALENDAR_LOG where installation_code = ?";
	  private static final String INSERT_CALENDAR = "INSERT INTO CALENDAR_LOG (installation_code, calendar_name, year, non_working_date, remark, user_id) values (?,?,?,?,?,?)";
	  private static final String DELETE_CALENDAR = "DELETE FROM CALENDAR_LOG where installation_code = ? and calendar_name = ? AND year = ?";
	  private static final String GET_BATCH_OBJECT_DATA = "select be_seq_no, object_name || ' - ' || task_name task_name, CASE WHEN status='UC' THEN 'Under Completion' WHEN status='CO' THEN 'Completed' WHEN status='IP' THEN 'In Progress' WHEN status='SP' THEN 'Suspended' WHEN status='99' THEN 'Failed' END as status, time_taken, obj_exec_start_time, obj_exec_end_time  from log WHERE installation_code = ? and batch_no = ? and batch_rev_no = ? and pre_post = ? and cycle_no = ? order by obj_exec_start_time desc";

	  @DatabaseAgnosticCandidate
	  private static final String GET_SYSTEM_DATE = "select to_char(current_timestamp, 'dd-MON-yyyy hh24:mi:ss')";
	  private static final String INSERT_O_QUEUE = "INSERT INTO O_QUEUE ( id, message, param, installation_code ) VALUES (nextval('o_queue_seq'), 'BSCALENDAR' , 'calendarName=' || ? || ', year=' || ? , ? )";
	  private static final String GET_MENU_DATA = "SELECT function_name, prior_function_id FROM function_master WHERE prior_function_id IN (SELECT a.function_id FROM function_master a, function_role_master b  WHERE a.function_id = b.function_id  AND b.role_id IN(SELECT role_id FROM user_installation_role WHERE user_id = ? and installation_code IN (?, 'null')     ))";
	  private static final String GET_PRIOR_MENU_DATA = "SELECT DISTINCT a.function_id prior_function_id FROM function_master a, function_role_master b WHERE a.function_id = b.function_id AND b.role_id IN(SELECT role_id FROM user_installation_role WHERE user_id = ? and installation_code IN(?, 'null')    )ORDER BY a.function_id DESC";
	  private static final String GET_INSTRUCTION_PARAMETERS = "select sl_no, name, value from instruction_parameters WHERE instruction_log_no = (select instruction_seq_no from batch WHERE installation_code = ? AND batch_no = ? AND batch_rev_no = ? )";
	  private static final String GET_INSTRUCTION_LOG = "SELECT seq_no, installation_code, batch_action FROM instruction_log WHERE seq_no = ?";
	  private static final String GET_INSTALLATION_CODES = "SELECT installation_code FROM installation";
	
	/** Query to get data from PROCESS_REQUEST_SCHEDULE table */
	private static final String GET_PROCESS_REQUEST_SCHEDULE_DATA =
		" SELECT installation_code, batch_name,sch_id, freq_type, recur, start_dt, sch_stat, " +
		" on_week_day, end_dt, end_occur, req_stat, occur_counter, skip_flag, end_reason, " +
		" keep_alive, user_id " +
		" FROM process_request_schedule " +
		" WHERE installation_code = ? " + 
		" AND sch_stat = ? " +
		" UNION  ALL " +
		" SELECT * FROM (" +
		" SELECT installation_code, batch_name,sch_id, freq_type, recur, start_dt, sch_stat, " +
		"   on_week_day, end_dt, end_occur, req_stat, occur_counter, skip_flag, end_reason, " +
		"   keep_alive, user_id " +
		"	FROM process_request_schedule " +
		"	WHERE installation_code = ?" + 
		"	AND sch_stat != ?) PRS2 " +
		" ORDER BY sch_id DESC, installation_code, batch_name,  freq_type, recur, " +
		" start_dt, sch_stat, on_week_day, end_dt, end_occur, req_stat, occur_counter, " +
		" skip_flag, end_reason, keep_alive, user_id";

	
	/** Query to get inactive schedule data from PROCESS_REQUEST_SCHEDULE table */
//	private static final String GET_INACTIVE_PROCESS_REQUEST_SCHEDULE_DATA = 
//	    "SELECT * FROM " +
//	    "(SELECT installation_code, batch_name,sch_id, freq_type, recur, start_dt, sch_stat, on_week_day, end_dt," + 
//	    " end_occur, req_stat, occur_counter, skip_flag, end_reason, keep_alive, user_id " +
//	    " FROM process_request_schedule " +
//	    " WHERE installation_code = ? " + 
//	    " and sch_stat != ?" +	        
//	    " ORDER BY sch_id DESC) PRS2" +
//	    " WHERE rownum <= 20" +
//	    " ORDER BY rownum";
	
	/** Query to get active schedule data from PROCESS_REQUEST_SCHEDULE table */
//	private static final String GET_ACTIVE_PROCESS_REQUEST_SCHEDULE_DATA = 
//		"SELECT installation_code, batch_name,sch_id, freq_type, recur, start_dt, sch_stat, " +
//		"on_week_day, end_dt, end_occur, req_stat, occur_counter, skip_flag, end_reason, " +
//		"keep_alive, user_id " +
//		"FROM process_request_schedule " +
//		"WHERE installation_code = ? " +
//		"AND sch_stat = ? " +
//		"ORDER BY sch_id DESC";
	
	/** Query to update data from PROCESS_REQUEST_SCHEDULE table */
	private static final String UPDATE_PROCESS_REQUEST_SCHEDULE_DATA = 
		" UPDATE process_request_schedule SET sch_stat = ?, req_stat = ?, " +
		" occur_counter = ?, end_reason = ? " +
		" WHERE sch_id = ? and sch_stat = ? " +
		" and installation_code = ? ";
	

	public MonitorDAO() {
		super();
	}

	/**
	 * Gets the instruction log sequence number from database.
	 * 
	 * @return instruction log sequence number
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer getInstructionLogSeqNo(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmtSeq = null;
		ResultSet rs = null;
		Integer requestId = 0;
		try {
			pstmtSeq = connection.prepareStatement(GET_INSTRUCTION_LOG_SEQ);
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
	 * Inserts the data into INSTRUCTION_LOG table.
	 * 
	 * @param reqInstructionLog
	 *            The instruction parameters
	 * @param connection
	 * 			  connection object
	 * @param instruction 
	 * 			the instruction can be 'Run' or 'Stop' batch
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer insertInstructionLog(ReqInstructionLog reqInstructionLog, Connection connection, String instruction)
	throws CommDatabaseException {
		PreparedStatement psInLog = null;
		Long instructionTime;
		try {
			psInLog = connection.prepareStatement(INSERT_INSTRUCTION_LOG);				
			psInLog.setString(1, reqInstructionLog.getInstallationCode());
			psInLog.setObject(2, reqInstructionLog.getSeqNo());
			psInLog.setObject(3, reqInstructionLog.getBatchNo());
			psInLog.setObject(4, reqInstructionLog.getBatchRevNo());
			psInLog.setString(5, instruction);
			psInLog.setString(6, reqInstructionLog.getInstructingUser());
			
			instructionTime = reqInstructionLog.getInstructionTime();
			if (instructionTime != null)
				psInLog.setTimestamp(7,	new java.sql.Timestamp(instructionTime));
			else
				psInLog.setObject(7, null);
			
			return psInLog.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, psInLog, null);
		}
	}


	/**
	 * Inserts data in INSTRUCTION_PARAMETERS table.
	 * 
	 * @param instructionLogSeqNo
	 * 			The unique instruction log sequence
	 *  
	 * @param parameters
	 * 			The list of instruction parameters 
	 * 
	 * @param entityValuesMap
	 * 			The map of entities provided with instruction to run batch
	 * 
	 * @param connection
	 * 			connection object
	 * 
	 * @return  1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 *             
	 */
	@DatabaseAgnosticCandidate
	public Integer addInstructionParameters(
			Integer instructionLogSeqNo, ReqInstructionLog log, List<InstructionParameter> parameters,
			LinkedHashMap<String, String> entityValuesMap, Connection connection)
			throws CommDatabaseException {

		List<InstallationEntity> entities = getBatchEntityData(log.getInstallationCode(), connection);
		Map<String, InstallationEntity> map = new HashMap<String, InstallationEntity>();
		for (InstallationEntity entity : entities) {
			map.put(entity.getEntity(), entity);
		}

		PreparedStatement pstmt = null;
		int counter = 0;
		try {
			pstmt = connection.prepareStatement(INSERT_INSTRUCTION_PARAMETERS);			
			for (InstructionParameter parameter : parameters) {
				if (parameter.getValue() != null
						&& !parameter.getValue().equals("")) {
					pstmt.setObject(1, instructionLogSeqNo);
					pstmt.setObject(2, ++counter);
					pstmt.setObject(3, parameter.getName());
					pstmt.setObject(4, parameter.getValue());
					pstmt.setObject(5, parameter.getType());
					pstmt.addBatch();
				}
			}
			if (entityValuesMap != null && entityValuesMap.size() > 0) {
				for (Entry<String, String> entry : entityValuesMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					String entity = StringUtils.extractTokenAt(key, '_', Constants.ESCAPE_CHAR, 1);
					if (map.get(entity) !=null) {
						int numOfRequiredParameters = map.get(entity).getNumberOfRequiredParameters();
						int numOfActualParameters = StringUtils.countTokens(value, Constants.DELIMITER_CHAR, Constants.ESCAPE_CHAR);
						if ( numOfRequiredParameters != numOfActualParameters) {
							throw new CommDatabaseException("Entity '" + entity + "' requires " + 
											numOfRequiredParameters + " parameters where as supplied are " + numOfActualParameters);
						}
					}
					
					pstmt.setObject(1, instructionLogSeqNo);
					pstmt.setObject(2, ++counter);
					pstmt.setObject(3, key);
					pstmt.setObject(4, value);
					pstmt.setObject(5, "S");
					pstmt.addBatch();
				}
			}

			pstmt.executeBatch();

			return 1;

		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}

	/**
	 * Returns the batch entity data for the installation supplied
	 * 
	 * @param installationCode
	 *            The installation code
	 *            
	 * @param connection
	 * 			  connection object
	 *            
	 * @return List<InstallationEntity> The installation entities as list
	 */
	public List<InstallationEntity> getBatchEntityData(
			String installationCode, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InstallationEntity> entityList = new ArrayList<InstallationEntity>();
		try {
			pstmt = connection.prepareStatement(GET_BATCH_ENTITY_DATA);
			pstmt.setString(1, installationCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				InstallationEntity entity = new InstallationEntity();
				entity.setEntity(rs.getString("ENTITY"));
				entity.setLookupColumn(rs.getString("LOOKUP_COLUMN"));
				entity.setLookupValue(rs.getString("LOOKUP_VALUE"));
				entity.setValueColumn(rs.getString("VALUE_COLUMN"));
				entity.setPrecedenceOrder(rs.getInt("PRECEDENCE_ORDER"));
				entity.setOnErrorFailAll(rs.getString("ON_ERROR_FLAG"));
				entity.setDescription(rs.getString("DESCRIPTION"));
				entityList.add(entity);
			}
			return entityList;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Returns the system information for the batch supplied
	 * 
	 * @param batch
	 *            The basic batch information
	 * @param connection
	 * 			  connection object
	 * 
	 * @return The system information as an instance of {@link SystemDetails}
	 */
	public SystemDetails getSystemInfo(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_SYSTEM_INFO);
			pstmt.setString(1, batch.getInstallationCode());
			pstmt.setInt(2, batch.getBatchNo());
			pstmt.setInt(3, batch.getBatchRevNo());
			rs = pstmt.executeQuery();
			SystemDetails systemInfo = ResultSetMapper.getInstance()
					.mapSingleRecord(rs, SystemDetails.class);
			return systemInfo;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Fetches the batch information for the a given batch
	 * 
	 * @param batch
	 *            The basic batch information
	 * @param connection
	 * 			  connection object
	 * 
	 * @return The batch information
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 * 
	 */
	public BatchDetails getBatchData(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String instructionSeqNo = batch.getInstructionSeqNo();
			if(instructionSeqNo == null || instructionSeqNo.length() == 0) {
				pstmt = connection.prepareStatement(GET_BATCH_SUMMARY);
				pstmt.setString(1, batch.getInstallationCode());
				pstmt.setInt(2, batch.getBatchNo());			
				pstmt.setInt(3, batch.getBatchRevNo());
			}else {
				pstmt = connection.prepareStatement(GET_BATCH_DETAILS);
				pstmt.setString(1, batch.getInstallationCode());
				pstmt.setInt(2, Integer.parseInt(batch.getInstructionSeqNo()));			
			}
			rs = pstmt.executeQuery();
			BatchDetails batchData = ResultSetMapper.getInstance()
					.mapSingleRecord(rs, BatchDetails.class);
			return batchData;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Gets the list of installations in JBEAM.
	 * 
	 * @param reqBatch
	 * 			  The basic batch information
	 * 
	 * @param connection
	 * 			  connection object
	 * 
	 * @return the list of installations
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<InstallationData> getInstallationData(ReqBatch reqBatch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<InstallationData> instList;
		try {
			pstmt = connection.prepareStatement(GET_INSTALLATION_DATA);
			pstmt.setString(1, reqBatch.getUserProfile().getUserId());
			pstmt.setString(2, reqBatch.getInstallationCode());
			pstmt.setString(3, reqBatch.getUserProfile().getUserId());
			pstmt.setString(4, reqBatch.getInstallationCode());
			rs = pstmt.executeQuery();
			instList = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, InstallationData.class);
			return instList;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Returns a list of batch (and revision) information for the supplied batch
	 * 
	 * @param batch
	 *            The basic batch information
	 * @param connection
	 * 			  connection object
	 *            
	 * @return list of batch (and revision) information
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<BatchInfo> getBatchInfo(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BatchInfo> list = new ArrayList<BatchInfo>();
		BatchInfo batchInfo = null;
		String timeDiffStr = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss");
		
		try {
			pstmt = connection.prepareStatement(GET_BATCH_INFO);
			pstmt.setString(1, batch.getInstallationCode());
			pstmt.setInt(2, batch.getBatchNo());
			pstmt.setString(3, batch.getInstallationCode());
			pstmt.setInt(4, batch.getBatchNo());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				batchInfo = new BatchInfo();
				batchInfo.setBatchNo(rs.getInt("BATCH_NUMBER"));
				batchInfo.setBatchRevNo(rs.getInt("BATCH_REV_NUMBER"));
				batchInfo
						.setExecStartTime((rs.getTimestamp("BATCH_START_TIME"))
								.getTime());
				if (rs.getTimestamp("BATCH_END_TIME") != null) {
					batchInfo
							.setExecEndTime((rs.getTimestamp("BATCH_END_TIME"))
									.getTime());
					timeDiffStr = CommonUtils.getTimeDiff((rs
							.getTimestamp("BATCH_END_TIME")).getTime(), (rs
							.getTimestamp("BATCH_START_TIME")).getTime());

				} else {
					batchInfo.setExecEndTime(null);
					Date parsedSysDate = dateFormat.parse(getSystemDate(connection));
					Long sysDate = parsedSysDate.getTime();
					timeDiffStr = CommonUtils.getTimeDiff(sysDate,
							(rs.getTimestamp("BATCH_START_TIME")).getTime());
				}
				batchInfo.setBatchTimeDiff(timeDiffStr);
				batchInfo.setNoOfObjects(rs.getInt("TOTAL_OBJECTS_EXECUTED"));
				batchInfo.setNoOfObjectsExecuted(rs
						.getInt("TOTAL_OBJECTS_EXECUTED"));
				batchInfo.setNoOfObjectsFailed(rs
						.getInt("TOTAL_OBJECTS_FAILED"));
				batchInfo.setNoOfListners(rs.getInt("TOTAL_LISTENER_COUNT"));
				batchInfo.setBatchEndReason(rs.getString("BATCH_END_REASON"));
				list.add(batchInfo);
			}
			return list;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (ParseException e) {
			throw new CommDatabaseException(e);
		}finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Fetch the system date
	 * @param connection
	 * 			  connection object
	 *   
	 * @return The System Date
	 * @throws CommDatabaseException
	 * 		   Any database related I/O exception 
	 */
	@DatabaseAgnosticCandidate
	public String getSystemDate(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmtSeq = null;
		ResultSet rs = null;
		String sysDate = null;
		try {
			pstmtSeq = connection.prepareStatement(GET_SYSTEM_DATE);
			rs = pstmtSeq.executeQuery();
			rs.next();
			sysDate = rs.getString(1);
			return sysDate;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmtSeq, null);
		}
	}
	/**
	 * Gets the list of listener details
	 * 
	 * 
	 * @param batch
	 *            The basic batch information
	 * @param connection
	 * 			  connection object
	 * 
	 * @return batchData Batch data from BATCH.
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<ListenerInfo> getListenerInfo(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ListenerInfo> list = new ArrayList<ListenerInfo>();
		try {
			pstmt = connection.prepareStatement(GET_LISTENER_INFO);
			pstmt.setObject(1, batch.getInstallationCode());
			pstmt.setInt(2, batch.getBatchNo());
			pstmt.setInt(3, batch.getBatchRevNo());
			pstmt.setObject(4, batch.getInstallationCode());
			pstmt.setInt(5, batch.getBatchNo());
			pstmt.setInt(6, batch.getBatchRevNo());
			rs = pstmt.executeQuery();
			ListenerInfo listenerInfo = null;
			while (rs.next()) {
				listenerInfo = new ListenerInfo();
				listenerInfo.setListenerId(rs.getInt("LIST_IND"));
				listenerInfo.setNoOfObjectsExecuted(rs.getInt("TOTAL_OBJECTS"));
				listenerInfo.setNoOfObjectsFailed(rs
						.getInt("TOTAL_OBJECTS_FAILED"));
				listenerInfo.setTimeTaken(rs.getDouble("TIME_TAKEN"));
				list.add(listenerInfo);
			}
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Gets the list of failed objects for supplied listener data
	 * 
	 * 
	 * @param reqListenerInfo
	 *            The listener data
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of failed objects
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<FailedObjectDetails> getFaliedObjectDetails(
			ReqListenerInfo reqListenerInfo, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FailedObjectDetails failedObjectDetails = null;
		List<FailedObjectDetails> list = new ArrayList<FailedObjectDetails>();
		try {
			pstmt = connection.prepareStatement(GET_BATCH_FAILED_OBJECTS);
			pstmt.setObject(1, reqListenerInfo.getInstallationCode());
			pstmt.setObject(2, reqListenerInfo.getBatchNo());
			pstmt.setObject(3, reqListenerInfo.getBatchRevNo());
			if(reqListenerInfo.getListenerId() != 0) {
				pstmt.setObject(4, reqListenerInfo.getListenerId());
			}else {
				pstmt.setObject(4, null);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				failedObjectDetails = new FailedObjectDetails();
				failedObjectDetails.setFailedObjectNo(rs
						.getInt("FailedObjectNo"));
				failedObjectDetails.setFailedObjectSequence(rs
						.getInt("FailedObjectSequence"));
				failedObjectDetails.setFailedObjectName(rs
						.getString("FailedObjectName"));
				failedObjectDetails.setListenerId(rs.getInt("ListenerId"));
				failedObjectDetails.setTaskName(rs.getString("TaskName"));
				failedObjectDetails.setErrorType(rs.getString("ErrorType"));
				failedObjectDetails.setErrorDescription(rs
						.getString("ErrorDescription"));
				failedObjectDetails.setTimeTaken(rs.getInt("TimeTaken"));
				list.add(failedObjectDetails);
			}
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Gets the list of batch object details for supplied batch details
	 * 
	 * @param reqBatchDetails
	 *            The Batch details
	 * @param connection
	 * 			  connection object
	 *            
	 * @return list of batch object details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<BatchObject> getBatchObjectDetails(
			ReqBatchDetails reqBatchDetails, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_BATCH_OBJECT_DATA);
			pstmt.setObject(1, reqBatchDetails.getInstallationCode());
			pstmt.setObject(2, reqBatchDetails.getBatchNo());
			pstmt.setObject(3, reqBatchDetails.getBatchRevNo());
			pstmt.setObject(4, reqBatchDetails.getPrePost());
			pstmt.setObject(5, reqBatchDetails.getCycleNo());

			rs = pstmt.executeQuery();
			List<BatchObject> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, BatchObject.class, 50);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Gets the batch progress graph data from GRAPH_DATA_LOG for the provided
	 * batch information (@link ReqBatch}.
	 * 
	 * @param batch
	 *            The batch data (@link ReqBatch}.
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of batchProgressGraphVO Batch Progress graph data from
	 *         GRAPH_DATA_LOG.
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<ObjectExecutionGraphData> getGraphData(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_GRAPH_DATA);
			pstmt.setString(1, batch.getInstallationCode());
			pstmt.setString(2, batch.getGraphId());
			pstmt.setInt(3, batch.getBatchNo());
			pstmt.setInt(4, batch.getBatchRevNo());
			rs = pstmt.executeQuery();
			List<ObjectExecutionGraphData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, ObjectExecutionGraphData.class, 100);
			Collections.sort(list);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Gets the batch completed data from BATCH and stores in
	 * {@link BatchDetails}.
	 * 
	 * 
	 * @param searchBatch
	 *            The basic batch information required for search
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of BatchDetails Batch data from BATCH for the search data
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<BatchDetails> getBatchCompletedData(ReqSearchBatch searchBatch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_BATCH_COMPLETED_DATA);

			pstmt.setObject(1, searchBatch.getInstallationCode());
			if (searchBatch.getBatchNo() == null
					|| searchBatch.getBatchNo() == 0)
				pstmt.setObject(2, null);
			else
				pstmt.setObject(2, searchBatch.getBatchNo());

			if (searchBatch.getBatchName() == null
					|| searchBatch.getBatchName().length() == 0)
				pstmt.setString(3, null);
			else
				pstmt.setString(3, "%"
						+ searchBatch.getBatchName().toUpperCase() + "%");

			if (searchBatch.getBatchType() == null
					|| searchBatch.getBatchType().length() == 0)
				pstmt.setString(4, null);
			else
				pstmt.setObject(4, searchBatch.getBatchType());
			
			if (searchBatch.getBatchDate() == null
					|| searchBatch.getBatchDate().length() == 0)
				pstmt.setString(5, null);
			else{
				SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
				Date date = dt.parse(searchBatch.getBatchDate());
				pstmt.setDate(5, new java.sql.Date(date.getTime()));
			}
			if (searchBatch.getBatchEndReason() == null
					|| searchBatch.getBatchEndReason().length() == 0)
				pstmt.setString(6, null);
			else
				pstmt.setObject(6, searchBatch.getBatchEndReason());

			rs = pstmt.executeQuery();
			List<BatchDetails> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, BatchDetails.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Gets the batch progress level data from PROGRESS_LEVEL for the provided
	 * batch information (@link ReqBatch}.
	 * 
	 * @param batch
	 *            The batch data (@link ReqBatch}.
	 * @param connection
	 * 			  connection object
	 * 
	 * @return resProgressLevelVO Batch Progress level data from PROGRESS_LEVEL
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<ProgressLevelData> getProgressLevelData(ReqBatch batch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_PROGRESS_LEVEL_DATA);
			pstmt.setString(1, batch.getInstallationCode());
			pstmt.setInt(2, batch.getBatchNo());
			pstmt.setInt(3, batch.getBatchRevNo());
			rs = pstmt.executeQuery();

			List<ProgressLevelData> progressLevelVO = ResultSetMapper
					.getInstance().mapMultipleRecords(rs,
							ProgressLevelData.class);

			return progressLevelVO;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	
	

	/**
	 * Gets the calendar details of supplied calendar information
	 * 
	 * @param calendarData
	 *            The reference of Calendar data {@link MonitorCalendarData}
	 * @param connection
	 * 			  connection object
	 *            
	 * @return list of Calendar data {@link MonitorCalendarData}
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<MonitorCalendarData> getSingleCalendarDetails(MonitorCalendarData calendarData, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_CALENDAR_DATA);
			pstmt.setString(1, calendarData.getInstallationCode());
			pstmt.setString(2, calendarData.getCalendarName());
			pstmt.setString(3, calendarData.getYear());
			rs = pstmt.executeQuery();

			List<MonitorCalendarData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, MonitorCalendarData.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Gets the details of a selected calendar
	 * 
	 * @param calendarData
	 *            The reference of Calendar data {@link MonitorCalendarData}
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of calendar data
	 * 
	 * @throws CommDatabaseException
	 */
	public List<MonitorCalendarData> getCalendarDetails(MonitorCalendarData calendarData, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_CALENDARS);
			pstmt.setString(1, calendarData.getInstallationCode());
			
			rs = pstmt.executeQuery();

			List<MonitorCalendarData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, MonitorCalendarData.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Inserts the calendar data into SCHEDULE table.
	 * 
	 * @param calendarDataList
	 *            The list of calendar data
	 * @param connection
	 * 			  connection object
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer addCalendar(List<MonitorCalendarData> calendarDataList, Connection connection)
			throws CommDatabaseException {

		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(INSERT_CALENDAR);
			for (MonitorCalendarData calendarData : calendarDataList) {
				pstmt.setString(1, calendarData.getInstallationCode());
				pstmt.setObject(2, calendarData.getCalendarName());
				pstmt.setObject(3, calendarData.getYear());
				pstmt.setDate(4, new java.sql.Date(calendarData
						.getNonWorkingDate()));
				pstmt.setObject(5, calendarData.getRemark());
				pstmt.setObject(6, calendarData.getUserId());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			return 1;
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
	 * 			  connection object
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public void deleteCalendar(MonitorCalendarData calendarData, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;

		try {
			pstmt = connection.prepareStatement(DELETE_CALENDAR);
			pstmt.setString(1, calendarData.getInstallationCode());
			pstmt.setString(2, calendarData.getCalendarName());
			pstmt.setString(3, calendarData.getYear());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Inserts message BSCALENDAR into O_QUEUE table.
	 * 
	 * @param calendarData
	 *            The reference of calendar data
	 * @param connection
	 * 			  connection object
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer addCalendarMessageToOQueue(MonitorCalendarData calendarData, Connection connection)
			throws CommDatabaseException {

		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(INSERT_O_QUEUE);
			pstmt.setObject(1, calendarData.getCalendarName());
			pstmt.setObject(2, calendarData.getYear());
			pstmt.setObject(3, calendarData.getInstallationCode());
			pstmt.execute();
			return 1;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Generates menu data list for JBEAM batch details as per the user role.
	 * 
	 * @param userProfile
	 * 			The user details with the user role id
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of menu data
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<MenuData> getMenuData(UserProfile userProfile, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {			
			pstmt = connection.prepareStatement(GET_MENU_DATA);
			pstmt.setString(1, userProfile.getUserId());
			pstmt.setString(2, userProfile.getInstallationCode());
			
			rs = pstmt.executeQuery();

			List<MenuData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, MenuData.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Generates prior (parent) menu data list for JBEAM batch details as per the user role.
	 * 
	 * @param userProfile
	 * 			The user details with the user role id
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of prior (parent) menu data
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<MenuData> getPriorMenuData(UserProfile userProfile, Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {			
			pstmt = connection.prepareStatement(GET_PRIOR_MENU_DATA);
			pstmt.setString(1, userProfile.getUserId());
			pstmt.setString(2, userProfile.getInstallationCode());
			
			rs = pstmt.executeQuery();
			
			List<MenuData> list = ResultSetMapper.getInstance()
			.mapMultipleRecords(rs, MenuData.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Creates the raw instruction parameters list for the provided batch
	 * information (@link ReqBatch}.
	 * 
	 * @param reqBatch
	 *            The batch data (@link ReqBatch}.
	 * @param connection
	 *            connection object
	 * 
	 * @return list of batch parameters
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<InstructionParameter> createInstructionParamsList(
			ReqBatch reqBatch, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_INSTRUCTION_PARAMETERS);
			pstmt.setString(1, reqBatch.getInstallationCode());
			pstmt.setInt(2, reqBatch.getBatchNo());
			pstmt.setInt(3, reqBatch.getBatchRevNo());
			rs = pstmt.executeQuery();

			List<InstructionParameter> instructionParameterList = ResultSetMapper
					.getInstance().mapMultipleRecords(rs,
							InstructionParameter.class);

			return instructionParameterList;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Retrieves the instruction log details 
	 * 
	 * @param instructionLogNo
	 *            The instruction log sequence number
	 * 
	 * @param connection
	 *            connection object
	 * 
	 * @return list of batch parameters
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public InstructionLog getInstructionLog(Integer instructionLogNo,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_INSTRUCTION_LOG);
			pstmt.setInt(1, instructionLogNo);
			rs = pstmt.executeQuery();

			InstructionLog instructionLog = ResultSetMapper.getInstance()
					.mapSingleRecord(rs, InstructionLog.class);

			return instructionLog;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
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
	
//	private List<ProcessRequestScheduleData> getConsolidatedProcessRequestScheduleList(
//			String installationCode, Connection connection)
//			throws CommDatabaseException {
//		List<ProcessRequestScheduleData> consolidatedScheduleList = new ArrayList<ProcessRequestScheduleData>();
//		List<ProcessRequestScheduleData> processRequestScheduleList = getProcessRequestScheduleList(
//				installationCode, connection);
//		consolidatedScheduleList.addAll(processRequestScheduleList);
//		List<ProcessRequestScheduleData> activeProcessRequestScheduleData = getActiveProcessRequestScheduleData(
//				installationCode, connection);
//		consolidatedScheduleList.addAll(activeProcessRequestScheduleData);
//
//		return consolidatedScheduleList;
//	}

	/**
	 * Retrieves list of all inactive schedules.
	 *   
	 * @param installationCode
	 * 			The installation code
	 * 
	 * @param connection
	 *            connection object
	 *            
	 * @return list of inactive schedules.
	 * 
	 * @throws CommDatabaseException 
	 *  			Any database I/O related exception occurred
	 */
	public List<ProcessRequestScheduleData> getProcessRequestScheduleList(String installationCode,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_PROCESS_REQUEST_SCHEDULE_DATA);
			pstmt.setString(1, installationCode);
			pstmt.setString(2, Constants.SCHEDULE_STATUS.ACTIVE.getID());
			pstmt.setString(3, installationCode);
			pstmt.setString(4, Constants.SCHEDULE_STATUS.ACTIVE.getID());
			rs = pstmt.executeQuery();

			List<ProcessRequestScheduleData> processRequestSchedulelist = new ArrayList<ProcessRequestScheduleData>();
			ProcessRequestScheduleData scheduleData = null;
			int schCount = 0;
			while (rs.next()) {
				if (schCount == 20)
					break;
				scheduleData = new ProcessRequestScheduleData();
				scheduleData.setInstallationCode(rs.getString("installation_code"));
				scheduleData.setBatchName(rs.getString("batch_name"));
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
				processRequestSchedulelist.add(scheduleData);
				schCount++;
			}
			return processRequestSchedulelist;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Retrieves list of all active schedules.
	 *   
	 * @param connection
	 *            connection object
	 *            
	 * @return list of schedules.
	 * 
	 * @throws CommDatabaseException 
	 *  			Any database I/O related exception occurred
	 */
//	private List<ProcessRequestScheduleData> getActiveProcessRequestScheduleData(String installationCode,
//			Connection connection) throws CommDatabaseException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			pstmt = connection.prepareStatement(GET_ACTIVE_PROCESS_REQUEST_SCHEDULE_DATA);
//			pstmt.setString(1, installationCode);
//			pstmt.setString(2, Constants.SCHEDULE_STATUS.ACTIVE.getID());
//			rs = pstmt.executeQuery();
//			
//			List<ProcessRequestScheduleData> processRequestSchedulelist = new ArrayList<ProcessRequestScheduleData>();
//			ProcessRequestScheduleData scheduleData = null;
//
//			while (rs.next()) {
//				scheduleData = new ProcessRequestScheduleData();
//				scheduleData.setInstallationCode(rs.getString("installation_code"));
//				scheduleData.setBatchName(rs.getString("batch_name"));
//				scheduleData.setSchId(rs.getInt("sch_id"));
//				scheduleData.setFreqType(rs.getString("freq_type"));
//				scheduleData.setRecur(rs.getInt("recur"));
//				scheduleData.setSchStat(rs.getString("sch_stat"));
//				scheduleData.setOnWeekDay(rs.getString("on_week_day"));
//				
//				if(rs.getTimestamp("start_dt") == null)
//					scheduleData.setStartDt(null);
//				else
//					scheduleData.setStartDt(rs.getTimestamp("start_dt").getTime());				
//				
//				if(rs.getTimestamp("end_dt") == null)
//					scheduleData.setEndDt(null);
//				else
//					scheduleData.setEndDt(rs.getTimestamp("end_dt").getTime());
//				
//				scheduleData.setEndOccur(rs.getInt("end_occur"));
//				scheduleData.setReqStat(rs.getString("req_stat"));
//				scheduleData.setOccurCounter(rs.getInt("occur_counter"));
//				scheduleData.setSkipFlag(rs.getString("skip_flag"));
//				scheduleData.setEndReason(rs.getString("end_reason"));
//				scheduleData.setKeepAlive(rs.getString("keep_alive"));
//				scheduleData.setUserId(rs.getString("user_id"));
//				processRequestSchedulelist.add(scheduleData);
//			}			
//			return processRequestSchedulelist;
//		} catch (Exception e) {
//			throw new CommDatabaseException(e);
//		} finally {
//			releaseResources(rs, pstmt, null);
//		}
//	}

	/**
	 * Updates the status of all the schedules.
	 *  
	 * @param requestScheduleVO
	 * 				The reference of {@link CResProcessRequestScheduleVO}
	 * 
	 * @param connection
	 * 				connection object
	 * @param requestScheduleVO 
	 * 
	 * @return 1 if the record was updated successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException 
	 *  			Any database I/O related exception occurred
	 */
	public Integer updateProcessRequestSchedule(
			CResProcessRequestScheduleVO requestScheduleVO, 
			Connection connection) throws CommDatabaseException {
		if(requestScheduleVO == null) return 0;
		
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_PROCESS_REQUEST_SCHEDULE_DATA);
			List<ScheduleData> processRequestScheduleList = 
				requestScheduleVO.getProcessRequestScheduleList();
			for (ScheduleData scheduleData : processRequestScheduleList) {
				pstmt.setString(1, scheduleData.getSchStat());
				pstmt.setString(2, scheduleData.getReqStat());
				pstmt.setInt(3, scheduleData.getOccurCounter());
				pstmt.setString(4, scheduleData.getEndReason());
				pstmt.setInt(5, scheduleData.getSchId());
				pstmt.setString(6, Constants.SCHEDULE_STATUS.ACTIVE.getID());
				pstmt.setString(7, scheduleData.getInstallationCode());
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			connection.commit();
			return 1;
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
	 * Updates the status of the schedule.
	 * 
	 * @param scheduleData
	 * 			The reference of {@link ScheduleData}
	 * 
	 * @param connection
	 * 				connection object
	 * 
	 * @return 1 if the record was updated successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException 
	 *  			Any database I/O related exception occurred
	 */
	public Integer updateProcessRequestSchedule(
			ScheduleData scheduleData,
			Connection connection) throws CommDatabaseException {
		
		if(scheduleData == null) return 0;
		
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_PROCESS_REQUEST_SCHEDULE_DATA);
			pstmt.setString(1, scheduleData.getSchStat());
			pstmt.setString(2, scheduleData.getReqStat());
			pstmt.setInt(3, scheduleData.getOccurCounter());
			pstmt.setString(4, scheduleData.getEndReason());
			pstmt.setInt(5, scheduleData.getSchId());
			pstmt.setString(6, Constants.SCHEDULE_STATUS.ACTIVE.getID());
			pstmt.setString(7, scheduleData.getInstallationCode());
			
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
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/impl/MonitorDAO.java          $
 * 
 * 7     7/14/10 5:46p Mandar.vaidya
 * Modified GET_BATCH_ENTITY_DATA. Modified getInstallationData method to set the entity list.
 * 
 * 6     7/13/10 4:27p Mandar.vaidya
 * Moved userAuthentication method to UserDAO.
 * 
 * 5     7/12/10 10:25a Mandar.vaidya
 * Changed logic to set serial number for batch (instruction) parameters
 * 
 * 4     7/10/10 2:07p Mandar.vaidya
 * Created the createInstructionParamsList method and modified the javadoc comments and implementation of getInstructionParameters
 * method.
 * 
 * 3     7/10/10 11:40a Lakshmanp
 * added getInstructionParameters method to show parameters list along with batch details
 * 
 * 2     6/23/10 11:06a Lakshmanp
 * removed parameterized constructor and added connection parameter to all dao methods
 * 
 * 1     6/22/10 10:49a Lakshmanp
 * 
 * 49    6/10/10 4:00p Mandar.vaidya
 * Added public method getMenuDetails and private methods getMenuData, getPriorMenuData for getting menu for Batch details as per the
 * logged in user role.
 * 
 * 48    6/07/10 3:32p Mandar.vaidya
 * Removed prefix 'um_' for all columns from the query GET_USER_DETAILS.
 * 
 * 47    4/28/10 2:28p Mandar.vaidya
 * Changes to add Entity data to BatchInfo list
 * 
 * 46    4/27/10 3:16p Mandar.vaidya
 * Added Entity data to Installation data.
 * 
 * 45    4/20/10 2:09p Mandar.vaidya
 * Changes in GET_PROGRESS_LEVEL_DATA.
 * 
 * 44    4/19/10 5:30p Mandar.vaidya
 * Removed SP for failed objects count.
 * 
 * 43    4/19/10 5:16p Mandar.vaidya
 * Removed SP for failed objects count.
 * 
 * 42    4/15/10 11:28a Mandar.vaidya
 * Changes in GET_INSTALLATION_DATA query
 * 
 * 41    4/14/10 1:59p Mandar.vaidya
 * Changes in getInstallationData method for setting timezoneShortName.
 * 
 * 40    4/13/10 2:35p Mandar.vaidya
 * Modified GET_INSTALLATION_DATA query.
 * 
 * 39    4/13/10 2:27p Kedarr
 * Changes made for Timezone.
 * 
 * 38    4/13/10 2:16p Mandar.vaidya
 * Removed call for addInstructionParameters from stopBatch method.
 * 
 * 37    4/09/10 11:17a Mandar.vaidya
 * Added new method addCalendarMessageToOQueue with query.
 * 
 * 36    4/08/10 5:04p Mandar.vaidya
 * Changes for installation_code in Calendar related queries and methods.
 * 
 * 35    4/07/10 5:36p Mandar.vaidya
 * Added installation code in query
 * 
 * 34    4/07/10 1:32p Mandar.vaidya
 * Changed the query for retrieving batch info
 * 
 * 33    4/01/10 4:34p Mandar.vaidya
 * Changed return type of runBatch from Integer to BatchDetails. Added new query GET_BATCH_DETAILS. Modified some queries and methods.
 * 
 * 31    3/30/10 10:53a Mandar.vaidya
 * Changes in queries and methods to add installation_code as input.
 * 
 * 30    3/30/10 10:24a Mandar.vaidya
 * Changed GET_BATCH_INFO. Added installation_code as input.
 * 
 * 29    3/25/10 1:51p Mandar.vaidya
 * Added GET_SYSTEM_DATE query and getSysDate method. Modified the getBatchInfo method.
 * 
 * 28    3/25/10 10:56a Mandar.vaidya
 * Added max_memory and used_memory in GET_SYSTEM_INFO query and removed the max_memory from GET_BATCH_SUMMARY query.
 * 
 * 27    3/22/10 12:22p Mandar.vaidya
 * Added the field maxMemory in GET_BATCH_SUMMARY query.
 * 
 * 26    3/19/10 3:08p Mandar.vaidya
 * Updated the GET_PROGRESS_LEVEL_DATA query.
 * 
 * 25    3/17/10 8:02p Mandar.vaidya
 * Modified the GET_BATCH_OBJECT_DATA query.
 * 
 * 24    3/17/10 7:57p Mandar.vaidya
 * Modified the GET_BATCH_OBJECT_DATA query.
 * 
 * 23    3/17/10 4:35p Mandar.vaidya
 * Added getBatchObjectDetails methods. Modified the GET_BATCH_SUMMARY query.
 * 
 * 22    3/12/10 11:47a Mandar.vaidya
 * Modification in GET_GRAPH_DATA query and getGraphdata method for new fields added in table GRAPH_DATA_LOG.
 * 
 * 21    3/11/10 12:05p Mandar.vaidya
 * Added batchEndReason in query GET_BATCH_COMPLETED_DATA and set the object in prepared statement for the batchEndReason.
 * 
 * 20    3/05/10 5:19p Mandar.vaidya
 * Added batch_end_reason in GET_BATCH_COMPLETED_DATA query.
 * 
 * 19    2/24/10 9:48a Grahesh
 * Removed a method for schedule batch
 * 
 * 18 2/18/10 7:49p Grahesh Implementation for Calendar Module
 * 
 * 17 1/18/10 11:57a Grahesh Modified implementation for getBatchInfo
 * 
 * 16 1/08/10 10:11a Grahesh Corrected the signature and object hierarchy
 * 
 * 15 1/06/10 5:05p Grahesh Corrected the signature and object hierarchy
 * 
 * 14 1/06/10 10:49a Grahesh Changed the object hierarchy
 * 
 * 13 1/05/10 10:31a Grahesh Added batch end reason in the query to fetch the
 * installation data. Also corrected the logic for inserts into instruction log
 * and parameters.
 * 
 * 12 1/04/10 4:46p Mandar.vaidya Corrected the implementation for stop batch
 * 
 * 11 12/30/09 4:51p Grahesh Corrected the implementation for instruction log
 * and parameters
 * 
 * 10 12/30/09 4:49p Grahesh Corrected the implementation for instruction log
 * and parameters
 * 
 * 9 12/30/09 3:55p Grahesh Implementation for stopping the batch
 * 
 * 8 12/30/09 2:39p Mandar.vaidya Corrected input for instruction log
 * 
 * 7 12/30/09 2:07p Grahesh Handling null pointer in getInstallationData method
 * 
 * 6 12/30/09 1:47p Grahesh Corrected the query to get the installation details.
 * 
 * 5 12/30/09 1:12p Grahesh Correcting the creation of the callable statement
 * string
 * 
 * 4 12/30/09 1:07p Grahesh Corrected the javadoc for warnings
 * 
 * 3 12/29/09 6:22p Mandar.vaidya
 * 
 * 2 12/17/09 12:01p Grahesh Initial Version
 */