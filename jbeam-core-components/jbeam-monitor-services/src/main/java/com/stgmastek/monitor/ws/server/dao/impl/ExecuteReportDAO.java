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
package com.stgmastek.monitor.ws.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.dao.IExecuteReportDAO;
import com.stgmastek.monitor.ws.server.vo.ConfigParameter;
import com.stgmastek.monitor.ws.server.vo.ExecuteReport;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.server.vo.ReportParameter;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.BaseDAO;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all Execute Report related I/O to the database
 * 
 * @author mandar.vaidya
 * 
 */
public class ExecuteReportDAO extends BaseDAO implements IExecuteReportDAO {

	/** Query to get reports from REPORT_MASTER table */
	private static final String GET_REPORT_LIST = 
		"SELECT DISTINCT installation_code, id report_id, name report_name, " +
		"		prog_name program_name, sr_no report_no " +
		"FROM report_master " +
		"WHERE installation_code = ? " +
		"ORDER BY id";
	
	/** Query to get reports from REPORT_MASTER table */
	private static final String GET_REPORT_DETAILS = 
		"SELECT DISTINCT installation_code, id report_id, name report_name, " +
		"		prog_name program_name, sr_no report_no, type report_type " +
		"FROM report_master " +
		"WHERE installation_code = ? " +
		"and id = UPPER(?)";
	
	/** Query to get reports from REPORT_MASTER table */
	private static final String GET_REPORT_PARAMETERS_LIST = 
		"SELECT installation_code, id report_id, param_name, data_type, " +
		"	length field_maxlength,	fixed_length, default_value, hint,label, " +
		"	query_yn query_flag," +
		"	query, mandatory_yn mandatory_flag, static_dynamic_flag " +
		"FROM report_parameters " +
		"WHERE installation_code = ? " +
		"AND id = UPPER(?) " +
		"ORDER BY param_order";
	
	/** Query to get the requested report name */
	private static final String GET_REPORT_NAME = 
		"SELECT	name report_name " +
		"FROM report_master " +
		"WHERE id = ?";
	
//	/** Query to insert the request data */
//	private static final String INSERT_REQUEST_DATA = 
//		"INSERT INTO Process_Request(" +
//		"		req_id, req_type, user_id, req_dt, req_stat, process_class_nm," +
//		"		grp_st_ind, req_start_dt, req_end_dt, grp_id, grp_req_seq_no, " +
//		"		req_logfile_nm, job_id, job_name, scheduled_time, sch_id, " +
//		"		stuck_thread_limit, stuck_thread_max_limit, priority, email_ids," +
//		" 		verbose_time_elapsed, cal_scheduled_time, text1, text2, num1, num2) " +
//		" VALUES" +
//		"		(?,?,?, SYSDATE,'Q',?,?,NULL,NULL,?,?, NULL,?,prm_job_name, ?," +
//		"		?,?,?,?,?,NULL,?,?,?,?,?)";
	
//	/** Query to insert the request parameters */
//	private static final String INSERT_REQUEST_PARAMETERS = 
//		"INSERT INTO process_req_params" +
//		"	(	Req_id, Param_no, Param_fld," +
//		"		Param_val, Param_data_type,Static_dynamic_flag " +
//		"	) " +
//		"VALUES" +
//		"  (	?, ?, ?, ?, ?, ?)";
	
	/** Public constructor takes no argument */	
	public ExecuteReportDAO() {
		super();
	}

	/**
	 * Gets the list of reports.
	 * 
	 * @param userProfile
	 *            The reference of User Profile {@link UserProfile}
	 * 
	 * @param connection
	 * 			connection object
	 * 
	 * @return list of reports
	 * 
	 * @throws CommDatabaseException
	 */
	public List<Report> getReports(UserProfile userProfile,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_REPORT_LIST);
			pstmt.setObject(1, userProfile.getInstallationCode());
			rs = pstmt.executeQuery();

			List<Report> reportsList = ResultSetMapper
					.getInstance().mapMultipleRecords(rs,
							Report.class);

			return reportsList;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	

	/**
	 * Gets the report details.
	 * 
	 * @param report
	 * 			The reference to the report ({@link Report}
	 * 
	 * @param connection 
	 * 				connection object
	 * 
	 * @return details for given report
	 * 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred 
	 */
	public List<Report> getReportDetails(Report report,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_REPORT_DETAILS);
			pstmt.setObject(1, report.getInstallationCode());
			pstmt.setObject(2, report.getReportId());
			rs = pstmt.executeQuery();
			
			List<Report> reports = ResultSetMapper
					.getInstance().mapMultipleRecords(rs, Report.class);

			return reports;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.ws.server.dao.IMonitorDAO#getReportParametesForReportId(com.stgmastek.monitor.ws.server.vo.Report, java.sql.Connection)
	 */
	public List<ReportParameter> getReportParametesForReportId(Report report,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(GET_REPORT_PARAMETERS_LIST);
			pstmt.setObject(1, report.getInstallationCode());
			pstmt.setObject(2, report.getReportId());
			rs = pstmt.executeQuery();

			List<ReportParameter> reportParameters = ResultSetMapper
					.getInstance().mapMultipleRecords(rs,
							ReportParameter.class);

			return reportParameters;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.ws.server.dao.IMonitorDAO#getEntity(java.lang.String, java.sql.Connection)
	 */
	public List<ConfigParameter> getEntity(String query, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			List<ConfigParameter> configParameters = ResultSetMapper
					.getInstance().mapMultipleRecords(rs,
							ConfigParameter.class);

			return configParameters;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.ws.server.dao.IMonitorDAO#getReportName(com.stgmastek.monitor.ws.server.vo.ExecuteReport, java.sql.Connection)
	 */
	public String getReportName(ExecuteReport executeReport,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String reportName;

		try {
			pstmt = connection.prepareStatement(GET_REPORT_NAME);
			pstmt.setObject(1, executeReport.getReportId());
			rs = pstmt.executeQuery();
			rs.next();
			reportName = rs.getString(1); 
			return reportName;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
			
		}
	}
}
