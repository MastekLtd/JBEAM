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
package com.stgmastek.monitor.ws.server.services;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.stgmastek.monitor.ws.server.base.ServiceBase;
import com.stgmastek.monitor.ws.server.vo.BaseResponseVO;
import com.stgmastek.monitor.ws.server.vo.ConfigParameter;
import com.stgmastek.monitor.ws.server.vo.ExecuteReport;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ReqBatchDetails;
import com.stgmastek.monitor.ws.server.vo.ReqCalendarVO;
import com.stgmastek.monitor.ws.server.vo.ReqInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ReqListenerInfo;
import com.stgmastek.monitor.ws.server.vo.ReqProcessRequestScheduleVO;
import com.stgmastek.monitor.ws.server.vo.ReqSearchBatch;
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ReqVersionVO;
import com.stgmastek.monitor.ws.server.vo.ResBatchDataVO;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;
import com.stgmastek.monitor.ws.server.vo.ResBatchObjectVO;
import com.stgmastek.monitor.ws.server.vo.ResCalendarVO;
import com.stgmastek.monitor.ws.server.vo.ResDropDownEntry;
import com.stgmastek.monitor.ws.server.vo.ResFailedObjectVO;
import com.stgmastek.monitor.ws.server.vo.ResGraphVO;
import com.stgmastek.monitor.ws.server.vo.ResInstallationEntitiesVO;
import com.stgmastek.monitor.ws.server.vo.ResInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ResInstructionVO;
import com.stgmastek.monitor.ws.server.vo.ResListenerVO;
import com.stgmastek.monitor.ws.server.vo.ResMenuDetails;
import com.stgmastek.monitor.ws.server.vo.ResProcessRequestScheduleVO;
import com.stgmastek.monitor.ws.server.vo.ResRoleDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ResSystemDetails;
import com.stgmastek.monitor.ws.server.vo.ResUserDetails;
import com.stgmastek.monitor.ws.server.vo.ResUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.ResUserProfile;
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * The service (interface) class for all services pertaining to the JBEAM
 * Monitor related operations.
 * 
 * This interface consists of all the service declarations needed. The services
 * listed under this service class are as
 * 
 * <li>ResUserDetails userAuthentication(UserCredentials userCredentials)</li> -
 * Service to authenticate a JBEAM Monitor User.
 * 
 * <li>ResInstallationVO getInstallationData()</li> - Service to get the
 * installation data
 * 
 * <li>ResInstallationVO getInstallationData(ReqBatch batch)</li> - Service to
 * get the installation data for the supplied batch that was run
 * 
 * <li>ResSystemDetails getSystemInfo(ReqBatch batch)</li> - Service to get the
 * system information for the supplied batch that was run
 * 
 * <li>ResBatchInfo getBatchData( ReqBatch batch)</li> - Service to get the
 * batch data for the supplied batch that was run
 * 
 * <li>ResBatchDataVO getBatchInfo( ReqBatch batch)</li> - Service to get the
 * batch information data for the supplied batch that was run
 * 
 * <li>ResListenerVO getListenerInfo(ReqBatch batch)</li> - Service to get the
 * listener details for the supplied batch that was run
 * 
 * <li>ResFailedObjectVO getFaliedObjectDetails(ReqListenerInfo reqListenerInfo)
 * </li> - Service to get the failed object details for the supplied batch that
 * was run
 * 
 * <li>ResGraphVO getGraphData(ReqBatch batch)</li> - Service to get the
 * progress graph data for the supplied batch that was run
 * 
 * <li>InstallationEntitiesVO getBatchEntityData(ReqInstallationVO
 * reqInstallationVO )</li> - Service to get the batch entity (like PRE, POLICY,
 * POST etc.) data for the supplied installation data
 * 
 * <li>ResBatchInfo getBatchCompletedData(ReqSearchBatch searchBatch)</li> -
 * Service to get the completed batch data for the supplied search criteria.
 * 
 * <li>ResBatchInfo runBatch(ReqInstructionLog reqInstructionLog)</li> -
 * Service to run the batch as per given instructions.
 * 
 * <li>ResInstructionVO checkInstructionLog(ReqInstructionLog reqInstructionLog)</li> -
 * Service to check details of instruction log for given instruction sequence number.
	 
 * <li>ResCalendarVO getCalendarData(ReqCalendarVO calendarVO)</li> -
 * Service to get the details for given calendar data
 * 
 * <li>ResCalendarVO getCalendars()</li> -
 * Service to get all the existing calendars.
 * 
 * <li>BaseResponseVO defineCalendar(ReqCalendarVO calendarVO)</li> -
 * Service to define a calendar.
 * 
 * <li>BaseResponseVO deleteCalendar(ReqCalendarVO calendarVO)</li> -
 * Service to delete a calendar.
 * 
 * <li>ResBatchObjectVO getBatchObjectDetails(ReqBatchDetails batchDetails)</li> - 
 * Service to get the batch object details for the supplied batch that was run.
 * 
 * <li>ResMenuDetails getMenuDetails(UserDetails userDetails)</li> -
 * Service to get the menu details for the supplied user details.
 * 
 * <li>ResRoleDetailsVO getRoleData()</li> -
 * Service to get all the configured roles in role_master.
 * 
 * <li>ResInstallationVO getInstallationsList()</li> -
 * Service to get the list of all the configured installations.
 *  
 * <li>BaseResponseVO manageUser(ReqUserDetailsVO reqUserDetailsVO)</li> -
 * Service to manage user.
 * 
 * <li>ResUserDetailsVO getUserInstallationRoleDetails(ReqUserDetailsVO reqUserDetailsVO)</li> -
 * Service to get the existing user installation role details.
 * 
 * <li>BaseResponseVO updateUserProfile(ReqUserDetailsVO reqUserDetailsVO)</li> -
 * Service to update user profile.
 * 
 * <li>BaseResponseVO changePassword(UserCredentials userCredentials)</li> -
 * Service to change the password. This service will change the old password
 * with new password when the user will provide both.
 * 
 * <li>BaseResponseVO resetPassword(UserProfile userProfile);</li> -
 * Service to reset the password. This service will reset the old password
 * with new random password when the user has forgotten the password and
 * sent a request to generate new password. This password will be reset only
 * after getting the correct answer for the hint question.
 * 
 * <li>ResUserProfile getSecurityDetails(UserProfile userProfile); </li> -
 * 	Service to get the security details for the supplied user data
 * 
 * <li>ExecuteReport retrieveReportDropDownOptionForCompany(
 * 			UserProfile userProfile);</li> -
 * Service to retrieve the reports for the given company.
 * 
 * <li>ExecuteReport retrieveParametersForReport(Report report);</li> -
 * Service to retrieve the retrieval type and details of report id.
 * 
 * <li>ResDropDownEntry retrieveDropDownEntries(
 * 			List<ConfigParameter> dropDownIdentifierList);</li> -
 * Service to retrieve the list of Drop down entries based on the Drop down identifier
 * list passed in.
 * 
 * <li>ExecuteReport processRequest(ExecuteReport executeReport,
 * 		UserProfile userProfile)</li> - 
 * Service to process the request for the given report id.
 * 
 * <li>void stopMonitorServices()</li> - Service to stop MONITOR-SERVICES.
 * 
 * @author mandar.vaidya
 * 
 */

@WebService
public interface MonitorServices extends ServiceBase {

	/**
	 * Service to check compatibility of monitor-services version with 
	 * JBEAM version.
	 * 
	 * @param version
	 *            The requested version to check
	 * 
	 * @return the instance of {@link BaseResponseVO}
	 */
	@WebMethod
	public BaseResponseVO checkCompatibility(
			@WebParam(name = "version") ReqVersionVO version) ;
	/**
	 * Service to authenticate a JBEAM Monitor User. This service will be
	 * available to all users.
	 * 
	 * @param userCredentials
	 *            The user credentials
	 * @see UserCredentials
	 * 
	 * @return the instance of (@link ResUserDetails}
	 */
	@WebMethod
	public ResUserDetails userAuthentication(
			@WebParam(name = "userCredentials") UserCredentials userCredentials);

	/**
	 * Service to get the installation data
	 * 
	 * @param batch
	 *            The batch information
	 *            
	 * @return The list of installation data as an instance of
	 *         {@link ResInstallationVO}
	 */
	@WebMethod
	public ResInstallationVO getInstallationData(
			@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the installation data for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The list of installation data as an instance of
	 *         {@link ResInstallationVO}
	 * 
	 */
	@WebMethod
	public ResInstallationVO getInstallationDetailsForBatch(
			@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the system information for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The system information as an instance of {@link ResSystemDetails}
	 */
	@WebMethod
	public ResSystemDetails getSystemInfo(
			@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the batch data for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The batch data as an instance of {@link ResBatchInfo}
	 */
	@WebMethod
	public ResBatchInfo getBatchData(@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the batch information data for the supplied batch that was
	 * run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The batch information as an instance of {@link ResBatchDataVO}
	 */
	@WebMethod
	public ResBatchDataVO getBatchInfo(@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the listener details for the supplied batch that was run
	 * 
	 * @param batch
	 *            The batch information
	 * @return The listener details as an instance of {@link ResListenerVO}
	 */
	@WebMethod
	public ResListenerVO getListenerInfo(
			@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the failed object details for the supplied batch that was
	 * run
	 * 
	 * @param reqListenerInfo
	 *            The listener information for which the failed object details
	 *            are to be fetched
	 * @return The failed object details as an instance of
	 *         {@link ResFailedObjectVO}
	 */
	@WebMethod
	public ResFailedObjectVO getFaliedObjectDetails(
			@WebParam(name = "reqListenerInfo") ReqListenerInfo reqListenerInfo);

	/**
	 * Service to get the progress graph data for the supplied batch that was
	 * run
	 * 
	 * @param batch
	 *            The batch information
	 * 
	 * @return The batch progress data for graph as an instance of
	 *         {@link ResGraphVO}
	 */
	@WebMethod
	public ResGraphVO getGraphData(@WebParam(name = "batch") ReqBatch batch);

	/**
	 * Service to get the batch entity (like PRE, POLICY, POST etc.) data for
	 * the supplied installation data
	 * 
	 * @param reqInstallationVO
	 *            The installation information
	 * 
	 * @return The batch entity data as an instance of
	 *         {@link ResInstallationEntitiesVO}
	 */
	@WebMethod
	public ResInstallationEntitiesVO getBatchEntityData(
			@WebParam(name = "reqInstallationVO") ReqInstallationVO reqInstallationVO);

	/**
	 * Service to get the completed batch data for the supplied search criteria.
	 * 
	 * @param searchBatch
	 *            The search criteria
	 * @return The completed batch data as an instance of {@link ResBatchInfo}
	 */
	@WebMethod
	public ResBatchInfo getBatchCompletedData(
			@WebParam(name = "searchBatch") ReqSearchBatch searchBatch);

	/**
	 * Service to run the batch as per given instructions.
	 * 
	 * @param reqInstructionLog
	 *            The requested instruction log
	 * 
	 * @return The data for batch with sequence number as an instance of
	 *         {@link ResBatchInfo}.
	 */
	@WebMethod
	public ResBatchInfo runBatch(
			@WebParam(name = "reqInstructionLog") ReqInstructionLog reqInstructionLog);
	
	/**
	 * Service to check details of instruction log for given instruction sequence number.
	 * 
	 * @param reqInstructionLog
	 *            The requested instruction log
	 * 
	 * @return The data for instruction details with sequence number as an instance of
	 *         {@link ResInstructionVO}.
	 */
	@WebMethod
	public ResInstructionVO checkInstructionLog(
			@WebParam(name = "reqInstructionLog") ReqInstructionLog reqInstructionLog);

	/**
	 * Service to stop the batch.
	 * 
	 * @param reqInstructionLog
	 *            The instruction to stop the batch
	 * 
	 * @return {@link ResInstallationVO} to send the status of the request served
	 */
	@WebMethod
	public ResInstallationVO stopBatch(
			@WebParam(name = "reqInstructionLog") ReqInstructionLog reqInstructionLog);

	/**
	 * Service to get the details for given calendar data
	 * 
	 * @param calendarVO
	 *            The calendar data
	 * 
	 * @return {@link ResCalendarVO} the calendar details with non working days list.
	 */
	@WebMethod
	public ResCalendarVO getCalendarData(
			@WebParam(name = "calendarVO") ReqCalendarVO calendarVO);

	/**
	 * Service to get all the existing calendars
	 * 
	 * @param calendarVO
	 *            The calendar data
	 *            
	 * @return {@link ResCalendarVO} the calendar details with non working days list.
	 */
	@WebMethod
	public ResCalendarVO getCalendars(
			@WebParam(name = "calendarVO") ReqCalendarVO calendarVO);

	/**
	 * Service to define a calendar
	 * 
	 * @param calendarVO
	 *            The calendar data
	 *            
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */
	@WebMethod
	public BaseResponseVO defineCalendar(
			@WebParam(name = "calendarVO") ReqCalendarVO calendarVO);
	
	/**
	 * Service to get the batch object details for the supplied batch that was
	 * run.
	 * 
	 * @param reqBatchDetails
	 *            The batch details
	 *            
	 * @return The list of batch object details as an instance of
	 *         {@link ResBatchObjectVO}
	 */
	@WebMethod
	public ResBatchObjectVO getBatchObjectDetails(
			@WebParam(name = "reqBatchDetails") ReqBatchDetails reqBatchDetails);

	
	/**
	 * Service to get the menu details for the supplied user details.
	 * 
	 * @param userProfile
	 * 			The user details with the user role id
	 * 
	 * @return The menu details as an instance of {@link ResMenuDetails}
	 */
	@WebMethod
	public ResMenuDetails getMenuDetails(
			@WebParam(name = "userProfile") UserProfile userProfile);
	
	/**
	 * Service to get the roles 
	 *
	 * @return The role details as an instance of {@link ResRoleDetailsVO}
	 */
	@WebMethod
	public ResRoleDetailsVO getRoleData();
	
	/**
	 * Service to get the installations list
	 *  
	 * @return The list of installations as an instance of {@link ResInstallationVO}
	 */
	@WebMethod
	public ResInstallationVO getInstallationsList();
	
	/**
	 * Service to manage user.
	 * 
	 * @param reqUserDetailsVO
	 * 			The request contains user and installation details
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */	
	@WebMethod
	public BaseResponseVO manageUser(
			@WebParam(name = "reqUserDetailsVO") ReqUserDetailsVO reqUserDetailsVO);

	/**
	 * Service to get the existing user installation role details.
	 *  
	 * @param reqUserDetailsVO
	 * 			The request contains user and installation details
	 * 
	 * @return The existing user installation role details as 
	 * 			an instance of {@link ResUserDetailsVO}
	 */
	@WebMethod
	public ResUserDetailsVO getUserInstallationRoleDetails(
			@WebParam(name = "reqUserDetailsVO") ReqUserDetailsVO reqUserDetailsVO);
	
	/**
	 * Service to update user profile.
	 * 
	 * @param reqUserDetailsVO
	 * 			The request contains user details
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */	
	@WebMethod
	public BaseResponseVO updateUserProfile(
			@WebParam(name = "reqUserDetailsVO") ReqUserDetailsVO reqUserDetailsVO);

	/**
	 * Service to change the password. This service will change the old password
	 * with new password when the user will provide both.
	 * 
	 * @param userCredentials
	 *            The user credentials
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */	
	@WebMethod
	public BaseResponseVO changePassword(
			@WebParam(name = "userCredentials") UserCredentials userCredentials);

	/**
	 * Service to reset the password. This service will reset the old password
	 * with new random password when the user has forgotten the password and
	 * sent a request to generate new password. This password will be reset only
	 * after getting the correct answer for the hint question.
	 * 
	 * @param userProfile
	 *            The user Profile
	 * 
	 * @return {@link BaseResponseVO} to send the status of the request served
	 */	
	@WebMethod
	public BaseResponseVO resetPassword(
			@WebParam(name = "userProfile") UserProfile userProfile);
	
	/**
	 * Service to get the security details for the supplied user data 
	 * 
	 * @param userProfile
	 *            The user Profile
	 *            
	 * @return The user details as an instance of {@link ResUserProfile}
	 */
	@WebMethod
	public ResUserProfile getSecurityDetails(
			@WebParam(name = "userProfile") UserProfile userProfile);

	
	/**
	 * This operation retrieve the reports for the given company.
	 * 
	 * @param userProfile
	 *            contains the user details.
	 * @return {@link ExecuteReport}
	 */
	@WebMethod
	ExecuteReport retrieveReportDropDownOptionForCompany(
			@WebParam(name = "UserProfile") UserProfile userProfile);

	/**
	 * This operation retrieve the retrieval type and details of report id.
	 * 
	 * @param report
	 *            contains the report details.
	 * @return {@link ExecuteReport}
	 */
	@WebMethod
	ExecuteReport retrieveParametersForReport(
			@WebParam(name = "Report") Report report);
	
	
	/**
	 * Retrieve the list of Drop down entries based on the Drop down identifier
	 * list passed in.
	 * 
	 * @param dropDownIdentifierList
	 *            The list of the drop down identifiers.
	 * @return The instance of ({@link ResDropDownEntry} which contains the list of Drop down entries for the given Drop down
	 *         identifiers.
	 */
	@WebMethod
	ResDropDownEntry retrieveDropDownEntries(
			@WebParam(name = "DropDownIdentifierList") List<ConfigParameter> dropDownIdentifierList);
	
	/**
	 * This operation process the request for the given report id.
	 * The report data is inserted into INSTRUCTION_LOG and 
	 * INSTRUCTION_PARAMETERS tables of monitor schema. 
	 * If monitor-comm is up, it transmits this data to core-comm 
	 * (INSTRUCTION_LOG and INSTRUCTION_PARAMETERS tables of core schema. 
	 * If the user selects ATTACH_SCHEDULE, then the data is also inserted into
	 * PROCESS_REQUEST_SCHEDULE table).
	 * From core-comm the request for reports will be processed.
	 * 
	 * @param executeReport
	 *            contains report details
	 *            
	 * @param reqInstructionLog
	 *            contains the instruction data with parameters.
	 *            
	 * @return {@link ResBatchInfo}
	 */
	@WebMethod
	ResBatchInfo processRequest(
			@WebParam(name = "ExecuteReport") ExecuteReport executeReport,
			@WebParam(name = "ReqInstructionLog") ReqInstructionLog reqInstructionLog);
	
	/**
	 * Service to stop MONITOR-SERVICES.
	 */
	@WebMethod
	public void stopMonitorServices();
	
	/**
	 * Service to get all the records from PROCESS_REQUEST_SCHEDULE table.
	 * 
	 * @return {@link ResProcessRequestScheduleVO}
	 */
	@WebMethod
	public ResProcessRequestScheduleVO getProcessRequestScheduleData(
			@WebParam(name = "processRequestScheduleVO") ReqProcessRequestScheduleVO processRequestScheduleVO);
	
	/**
	 * Service to refresh all the records from PROCESS_REQUEST_SCHEDULE table for provided 
	 * schedule_ids.
	 * 
	 * @param processRequestScheduleVO
	 * 			contains processRequestSchedule data with schedule id (schId) 
	 * 
	 * @return {@link ResProcessRequestScheduleVO}
	 */
	@WebMethod
	public ResProcessRequestScheduleVO refreshProcessRequestScheduleData(
			@WebParam(name = "processRequestScheduleVO") ReqProcessRequestScheduleVO processRequestScheduleVO);
	
	
	/**
	 * Service to cancel a schedule for the provided schedule id (SchId).
	 *  
	 * @param processRequestScheduleVO
	 * 			contains processRequestSchedule data with schedule id (schId) 
	 * 
	 * @return {@link ResProcessRequestScheduleVO}
	 */
	@WebMethod
	public ResProcessRequestScheduleVO cancelSchedule(
			@WebParam(name = "processRequestScheduleVO") ReqProcessRequestScheduleVO processRequestScheduleVO);

}

/*
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/services/MonitorServices.java               $
 * 
 * 20    7/08/10 3:17p Mandar.vaidya
 * Renamed the service createUser as manageUser
 * 
 * 19    7/01/10 5:09p Mandar.vaidya
 * Added service to get the existing user installation role details.
 * 
 * 18    6/30/10 3:14p Lakshmanp
 * Added service to create user.
 * 
 * 17    6/25/10 3:54p Lakshmanp
 * added request role details param
 * 
 * 16    6/25/10 2:09p Lakshmanp
 * added  service to get roles.
 * 
 * 15    6/10/10 4:01p Mandar.vaidya
 * Added new webMethod getMenuDetails for getting menu for Batch details as per the  logged in user role.
 * 
 * 14    4/08/10 5:02p Mandar.vaidya
 * Added webparam for getCalendars webmethod
 * 
 * 13    4/01/10 4:31p Mandar.vaidya
 * Changed return type of runBatch from BaseResponseVO to ResBatchInfo.
 * 
 * 12    3/17/10 4:36p Mandar.vaidya
 * Added getBatchObjectDetails web method.
 * 
 * 11    2/24/10 9:47a Grahesh
 * Removed a method for schedule batch
 * 
 * 10    2/18/10 7:49p Grahesh
 * Implementation for Calendar Module
 * 
 * 9 1/13/10 4:23p Grahesh Corrected the java doc comments
 * 
 * 8 1/08/10 10:14a Grahesh Corrected the signature and object hierarchy
 * 
 * 7 1/06/10 5:05p Grahesh Corrected the signature and object hierarchy
 * 
 * 6 1/06/10 11:31a Mandar.vaidya Corrected the signature and object hierarchy
 * 
 * 5 1/06/10 10:49a Grahesh Changed the object hierarchy
 * 
 * 4 12/30/09 3:55p Grahesh Implementation for stopping the batch
 * 
 * 3 12/30/09 1:07p Grahesh Corrected the javadoc for warnings
 * 
 * 2 12/17/09 12:01p Grahesh Initial Version
 */