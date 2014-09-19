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
package com.stgmastek.monitor.ws.server.dao;

import java.sql.Connection;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.vo.ConfigParameter;
import com.stgmastek.monitor.ws.server.vo.ExecuteReport;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.server.vo.ReportParameter;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * DAO interface for all Reports related operations.
 * ExecuteReportDAO class must implement this interface.
 *
 * @author Mandar Vaidya
 *
 */
public interface IExecuteReportDAO extends IBaseDAO {


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
	public List<Report> getReports(UserProfile userProfile, Connection connection) throws CommDatabaseException ;

	/**
	 * Gets the list of parameters for the given report id.
	 * 
	 * @param report
	 * 			The reference to the report ({@link Report}
	 * 
	 * @param connection 
	 * 				connection object
	 * @return list of the parameters for given report
	 * 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred 
	 */
	public List<ReportParameter> getReportParametesForReportId(Report report, Connection connection) throws CommDatabaseException;
	
	/**
	 * Gets the report details.
	 * 
	 * @param report
	 * 			The reference to the report ({@link Report}
	 * 
	 * @param connection 
	 * 				connection object
	 * 
	 * @return list of reports
	 * 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred 
	 */
	public List<Report> getReportDetails(Report report, Connection connection) throws CommDatabaseException;

	/**
	 * Gets the details for the supplied query
	 *  
	 * @param query
	 * 			The dynamic query 
	 * 
	 * @param connection
	 * 				connection object
	 * 
	 * @return list of entities
	 * 		 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred
	 */
	public List<ConfigParameter> getEntity(String query, Connection connection) throws CommDatabaseException;

	/**
	 * This method is used to get the report for the given report id.
	 * 
	 * @param executeReport
	 * 			The reference of {@link ExecuteReport}
	 * 
	 * @param connection
	 * 				connection object
	 * 
	 * @return the report name
	 * 
	 * @throws CommDatabaseException
	 * 			Any database I/O related exception occurred 
	 */
	public String getReportName(ExecuteReport executeReport,
			Connection connection) throws CommDatabaseException;
}
