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
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.majescomastek.jbeam.common
{
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.utils.ObjectUtil;
	
	/**
	 * This class would hold all the commonly used constants like
	 * constants specify a blank string, a space character etc.
	 */
	public class CommonConstants
	{

		/** A blank string */
		public static const BLANK_STRING:String = '';
		
		/** A space character */
		public static const SPACE_CHAR:String = ' ';
		
		/** The string to be pre-pended when rendering negative amount */
		public static const NEG_AMT_PRE_STRING:String = '( ';
		
		/** The string to be appended when rendering negative amount */
		public static const NEG_AMT_POST_STRING:String = ' )';
		
		/** The value of default drop down selected index */
		public static const DROP_DOWN_INDEX_DEFAULT_VALUE:int = 0;
		
		/** The minimum valid value of a drop down selected index */
		public static const DROP_DOWN_INDEX_MIN_VALUE:int = 1;
		
		/** The default value of the drop down */
		public static const DROP_DOWN_CODE_DEFAULT_VALUE:String = 'DEFAULT';
		
		/** The base package path for the view components */
		public static const BASE_VIEW_PKG_PATH:String = 'com/majescomastek/jbeam/view/components/';
		
		/** The string to be displayed for installation which has no batch information */
		public static const NO_BATCH_INFORMATION_AVAILABLE:String = "No Batch information available.";
		
		/** The string to be displayed when service bus is down. */
		public static const SERVICE_BUS_DOWN:String = 
				"The Service Bus is down." + CONTACT_SYSTEM_ADMIN;
				
		/** The string to be displayed when loading new batch. */
		public static const LOADING_BATCH:String = 
				"Loading new batch. Please wait..";
				
		/** The string to be displayed when resuming batch. */
		public static const RESUMING_BATCH:String = 
				"Resuming the batch. Please wait..";
				
		/** The string to be displayed when its needed to contact the System Administrator. */
		public static const CONTACT_SYSTEM_ADMIN:String = 
				"\n Please contact your System Administrator.";
		
		/** The string to be displayed when compatibility check fails. */
		public static const CHECK_COMPATIBILITY_FAILED:String = 
				"The monitor-services version is not compatible with this JBEAM version."
				+ CONTACT_SYSTEM_ADMIN;
		
		/** The string to be displayed when batch type is not selected. */
		public static const BATCH_TYPE_NOT_SELECTED:String = 
			"Please enter batch parameters or select the batch type as Date Run.";
				
		/** The string to be displayed when user authentication fails. */
		public static const USER_AUTHENTICAION_FAILED:String = 
				"UserId/ Password is not valid. \nUser Authentication failed.";
		
		/** The string to be displayed when retrieval of installation data fails. */
		public static const GET_INSTALLATION_DATA_FAILED:String = 
				"The retrieval of Installation data failed.";
				
		/** The string to be displayed when retrieval of calendar list fails. */
		public static const GET_CALENDAR_LIST_FAILED:String = 
				"The retrieval of calendar list failed.";

		/** The string to be displayed when retrieval of calendar details fails. */
		public static const GET_CALENDAR_DETAILS_FAILED:String = 
				"The retrieval of calendar details failed.";

		/** The string to be displayed when request for define calendar fails. */
		public static const DEFINE_CALENDAR_FAILED:String = 
				"The request for define calendar failed.";
				
		/** The string to be displayed when retrieval of failed object data fails. */
		public static const GET_FAILED_OBJECTS_DETAILS_FAILED:String = 
				"The retrieval of Failed object details failed.";
		
		/** The string to be displayed when retrieval of batch object data fails. */
		public static const GET_BATCH_OBJECTS_DETAILS_FAILED:String = 
				"The retrieval of Batch object details failed.";
		
		/** The string to be displayed when retrieval of Batch details list fails. */
		public static const GET_BATCH_DETAILS_LIST_FAILED:String = 
				"The retrieval of Batch details list failed.";

		/** The string to be displayed when retrieval of Batch Summary fails. */
		public static const GET_BATCH_SUMMARY_FAILED:String = 
				"The retrieval of Batch summary failed.";

		/** The string to be displayed when retrieval of system information fails. */
		public static const GET_SYSTEM_INFORMATION_FAILED:String = 
				"The retrieval of system information failed.";

		/** The string to be displayed when retrieval of object execution graph data fails. */
		public static const GET_OBJECT_EXECUTION_GRAPH_DATA_FAILED:String = 
				"The retrieval of object execution graph data failed.";
		
		/** The string to be displayed when retrieval of per scan Execution count graph data fails. */
		public static const GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED:String = 
				"The retrieval of per scan Execution count graph data failed.";
				
		/** The string to be displayed when retrieval of listener data fails. */
		public static const GET_LISTENER_DATA_FAILED:String = 
				"The retrieval of listener data failed.";
		
		
		/** The string to be displayed when retrieval of graph data fails. */
		public static const GET_GRAPH_DATA_FAILED:String = "The retrieval of Graph data failed.";
		
		/** The string to be displayed when retrieval of instruction log fails. */
		public static const GET_INSTRUCTION_LOG_FAILED:String = "The retrieval of instruction log failed.";

		/** The string to be displayed when retrieval of menu details fails. */
		public static const GET_MENU_DETAILS_FAILED:String = "The retrieval of menu details failed.";
		
		/** 
		 * The string to be displayed when run batch request is submitted 
		 * successfully. 
		 */
		public static const RUN_BATCH_REQUEST_SUBMITTED:String = "A request to " + 
				"Run Batch has been submitted successfully. \nRequest time is ";

		/** 
		 * The string to be displayed when resume batch request is submitted 
		 * successfully. 
		 */
		public static const RESUME_BATCH_REQUEST_SUBMITTED:String = "A request to " + 
				"Resume batch has been submitted successfully. \nRequest time is ";
				
		/** 
		 * The string to be displayed when run report request is submitted 
		 * successfully. 
		 */
		public static const RUN_REPORT_REQUEST_SUBMITTED:String = "A request to " + 
				"run report has been submitted successfully. \nRequest time is ";
				
		/** 
		 * The string to be displayed when schedule batch request is submitted 
		 * successfully. 
		 */
		public static const SCHEDULE_BATCH_REQUEST_SUBMITTED:String = "A request to " + 
				"Schedule Batch has been submitted successfully. \nRequest time is ";
				
		/** The string to be displayed when run batch request fails. */
		public static const RUN_BATCH_REQUEST_FAILED:String = "A request to " + 
				"Run Batch failed.";
		
		/** The string to be displayed when get schedule data request fails. */
		public static const GET_SCHEDULE_REQUEST_FAILED:String = "A request to " + 
				"get schedule data failed.";
		
		/** The string to be displayed when refresh schedule data request fails. */
		public static const REFRESH_SCHEDULE_REQUEST_FAILED:String = "A request to " + 
				"refresh schedule data failed.";
		
		/** The string to be displayed when cancel schedule request fails. */
		public static const CANCEL_SCHEDULE_REQUEST_FAILED:String = "Schedule is already cancelled in core schema";

		/** The string to be displayed when retrieval of Search Batch fails. */
		public static const SEARCH_BATCH_FAILED:String = 
				"The retrieval of Search Batch failed.";

		/** The string to be displayed when retrieval of user installation roles fails. */
		public static const GET_USER_INSTALLATION_ROLES_FAILED:String = 
				"The retrieval of user installation roles failed.";

		/** The string to be displayed when retrieval of user details fails. */
		public static const GET_USER_DETAILS_FAILED:String = 
				"The retrieval of user details failed.";
				
		/** The string to be displayed when retrieval of security details fails. */
		public static const GET_SECURITY_DETAILS_FAILED:String = 
				"The retrieval of security details failed." + CONTACT_SYSTEM_ADMIN;
				
		/** The string to be displayed when submission of security details fails. */
		public static const SUBMIT_SECURITY_DETAILS_FAILED:String = 
				"The submission of security details failed.";
				
		/** The string to be displayed when submission of invalid answer. */
		public static const INVALID_ANSWER:String = 
				"Invalid answer.";
				
		/** The string to be displayed when retrieval of role data fails. */
		public static const GET_ROLE_DATA_FAILED:String = 
				"The retrieval of role data failed.";
				
		/** The string to be displayed when retrieval of installations list fails. */
		public static const GET_INSTALLATIONS_LIST_FAILED:String = 
				"The retrieval of installations list failed.";
				
		/** The string to be displayed when request to create user fails. */
		public static const CREATE_USER_FAILED:String = 
				"The request to create user failed.";
		
		/** The string to be displayed when user creation request succeeds partially. */
		public static const CREATE_USER_PART_SUCCEEDED:String =  
				"User created successfully. However email could not be sent due to network issue.";
		
		/** The string to be displayed when user create request succeeds. */
		public static const CREATE_USER_SUCCEEDED:String =  
				"User created successfully. " + EMAIL_SENT;
		
		/** The string to be displayed when user create request succeeds. */
		public static const USER_DETAILS_MODIFICATION_SUCCEEDED:String =  
				"User details modified successfully."; 
				
		/** The string to be displayed when forgot password request succeeds. */
		public static const FORGOT_PASSWORD_SUCCEEDED:String =  
				"Security check completed. " + EMAIL_SENT;
				
		/** The string to be displayed when an email is sent with new/ reset password . */
		public static const EMAIL_SENT:String =  
				"\nA new password has been sent to your registered e-mail Id.";

		/** The string to be displayed when request to edit user profile fails. */
		public static const EDIT_USER_PROFILE_FAILED:String = 
				"The request to edit user profile failed.";
		
		/** The string to be displayed when edit user profile request succeeds. */
		public static const EDIT_USER_PROFILE_SUCCEEDED:String = 
				"User profile modified successfully.";
				
		/** 
		 * The string to be displayed when start batch request is submitted 
		 * successfully. 
		 */
		public static const START_BATCH_REQUEST_SUBMITTED:String = "A request to " + 
				"Start Batch has been submitted successfully. \nRequest time is ";
				
		/** The string to be displayed when start batch request fails. */
		public static const START_BATCH_REQUEST_FAILED:String = "A request to " + 
				"Start Batch failed.";
		/** 
		 * The string to be displayed when stop batch request is submitted 
		 * successfully. 
		 */
		public static const STOP_BATCH_REQUEST_SUBMITTED:String = "A request to " + 
				"Stop Batch has been submitted successfully. \nRequest time is ";
				
		/** The string to be displayed when stop batch request fails. */
		public static const STOP_BATCH_REQUEST_FAILED:String = "A request to " + 
				"Stop Batch failed.";
				
		/** The string to be displayed when change password request succeeds. */
		public static const CHANGE_PASSWORD_SUCCESS:String =  
				"The password has been changed successfully."
				 
		/** The string to be displayed when change password request fails. */
		public static const CHANGE_PASSWORD_FAILED:String = "A request to " + 
				"change password failed. Please re-enter correct old password.";
				
		/** The string to be displayed when the retrieval of reports list fails. */
		public static const RETRIEVE_REPORTS_FAILED:String = 
				"The retrieval of reports list failed.";
				
		/** The string to be displayed when the retrieval of system date fails. */
		public static const RETRIEVE_SYSTEM_DATE_FAILED:String = 
				"The retrieval of system date failed.";
				
		/** The string to be displayed when the retrieval of parameters list for a report fails. */
		public static const RETRIEVE_PARAMETERS_FOR_REPORTS_FAILED:String = 
				"The retrieval of parameters list for a report fail failed.";
				
		/** The string to be displayed when the operation to process request fails. */
		public static const PROCESS_REQUST_FAILED:String = 
				"The operation to process request failed.";

		/** The default value of SUCCESS. */
		public static const SUCCESS:String = "SUCCESS";
		
		/** The default value of FAILED. */
		public static const FAILED:String = "FAILED";
		
		/** The default value of OK. */
		public static const OK:String = "OK";
		
		/** The default value of ERROR. */
		public static const ERROR:String = "ERROR";
		
		/** The default message value of stop batch. */
		public static const BSSTOBATCH:String = "BSSTOBATCH";
		
		/** The default message value of start batch. */
		public static const BSRUNBATCH:String = "BSRUNBATCH";
		
		/** The default message value of run e-Report. */
		public static const RUNEREPORT:String = "RUNEREPORT";
		
		/** The default message value of CLOSURE. */
		public static const CLOSURE:String = "CLOSURE";
		
		/** The default value of BATCH_COMPLETED. */
		public static const BATCH_COMPLETED:String = "BATCH_COMPLETED";
		
		/** The default value of USER_INTERRUPTED. */
		public static const USER_INTERRUPTED:String = "USER_INTERRUPTED";
		
		/** The default value of PRE_ISSUED_STOP. */
		public static const PRE_ISSUED_STOP:String = "PRE_ISSUED_STOP";
		
		/** The default value of SPECIAL_RUN batch. */
		public static const SPECIAL_RUN:String = "SPECIAL";
		
		/** The default value of DATE_RUN batch. */
		public static const DATE_RUN:String = "DATE";
		
		/** The default value of stopped batch. */
		public static const BSTOP:String = "BATCH_STOPPED";
		
		/** The default value of module initialised. */
		public static const MINIT:String = "MODULE_INIT";
		
		/** The default value of module exit. */
		public static const MEXIT:String = "MODULE_EXIT";
		
		/** The default value of menu batch current. */
		public static const CURRENT_BATCH:String = "Current";
		
		/** The default value of menu batch completed. */
		public static const COMPLETED:String = "Completed";

		/** The default value of menu Search (batch completed) */
		public static const SEARCH_BATCH:String = "Search";
		
		/** The default value of menu Run Batch. */
		public static const RUN_BATCH:String = "Run Batch";
		
		/** The default value of menu View Schedule. */
		public static const VIEW_SCHEDULE:String = "View Schedules";
		
		/** The default value of menu Run. */
		public static const RUN:String = "Run";
		
		/** The default value of menu Schedule Batch. */
		public static const SCHEDULE_BATCH:String = "Schedule Batch";
		
		/** The default value of menu Manage User. */
		public static const MANAGE_USER:String = "Manage User";
		
		/** The default value of menu User Master. */
		public static const USER_MASTER:String = "User Master";
		
		/** The default value of menu Profile. */
		public static const PROFILE:String = "Profile";
		
		/** The default value of menu Edit Profile. */
		public static const EDIT_PROFILE:String = "Edit Profile";
		
		/** The default value of menu Change Password. */
		public static const CHANGE_PASSWORD:String = "Change Password";
				
		/** The default value of menu Generate Reports. */
		public static const GENERATE_REPORTS:String = "Generate Reports";
		
		/** The default value of menu run Define Calendar. */
		public static const DEFINE_CALENDAR:String = "Define Calendar";
		
		/** The default value of menu view Historic. */
		public static const HISTORIC:String = "Historic";
		
		/** The default value of view PODS_VIEW. */
		public static const PODS_VIEW:String = "PODS_VIEW";
		
		/** The default value of view LIST_VIEW. */
		public static const LIST_VIEW:String = "LIST_VIEW";
		
		/** The default value of appender character (?). */
		public static const APPENDER:String = "?";
		
		/** The default value of YES value. */
		public static const YES:String = "Y";
		
		/** The default value of NO value. */
		public static const NO:String = "N";
		
		public static var dat:Date;
		
		/** The path to the Jbeam wsdl */
		public static var WSDL_PATH:String;
		
		/** The server url used for invoking web services */
		public static var SERVER_URL:String;
		
		public static var SERVER_URL_IM:String;
		
		public static var COMPANY_LOGO:String;
		
		public static var COMPANY_LOGO_SMALL:String;
		
		
		/** The logged in UserProfile object set when the authentication service succeeds */
		public static var USER_PROFILE:UserProfile;
		
		/** The current InstallationData object set when the getInstallationDetails service succeeds */
		public static var INSTALLATION_DATA:InstallationData;
		
		/** The current BatchDetailsData object set when the getBatchInfo service succeeds */
		public static var BATCH_DETAILS_DATA:BatchDetailsData;
		
		/** The UserInstallationRole object set when the authentication service succeeds */
		public static var USER_INSTALLATION_ROLES:ArrayCollection;
		
		/** The Admin Role object set when the authentication service succeeds */
		public static var HAVE_ADMIN_ROLE:Boolean;
		
		/** The User Role object set when the authentication service succeeds */
		public static var HAVE_USER_ROLE:Boolean;
		
		/** The Operator Role object set when the authentication service succeeds */
		public static var HAVE_OPERATOR_ROLE:Boolean;

		/** A default poll time of 5 seconds for refresh pod data */
		public static const DEFAULT_POLL_TIME:Number = 10000;
		
		/** A default number of times request will be sent to get batch details of newly run special batch  */
		public static const DEFAULT_POLL_COUNT:Number = 1000;
		
		/** The graph identifier for the failed object graph */
		public static const FAILED_OBJECTS_GRAPH_IDENTIFIER:String = "FailedObjectsPieChartCollator";

		/** The module info key used for representing the programName for that module */		
		public static const KEY_PROGRAM_NAME:String = "programName";
		
		/** The module info key used for representing the previous module data for that module */
		public static const KEY_PREVIOUS_MODULE_DATA:String = "previousModuleData";
		
		/** The default message value of INITIALIZATON. */
		public static const INITIALIZATON:String = "INITIALIZATON";
		
		/** The default message value of START. */
		public static const START:String = "START";
		
		/** The default message value of RESUME. */
		public static const RESUME:String = "RESUME";

		/** The default message value of STOPPED. */
		public static const STOPPED:String = "STOPPED";
		
		/** The default message value of US date time format. */
		public static const US_DATETIME_FORMAT:String = "MM/DD/YYYY JJ:NN:SS";
		
		/** The default message value of XML date time format. */
		public static const ICD_XML_DATETIME_FORMAT:String = "YYYY-MM-DDTHH:NN:SS";
		
		/** The default message value of US date format */
		public static const US_DATE_FORMAT:String = "MM/DD/YYYY";
		
		/** The default message value of ADMIN. */
		public static const ADMIN:String = "ADMIN";
		
		/** The default message value of CONNECT. */
		public static const CONNECT:String = "CONNECT";
		
		/** The default message value of User. */
		public static const USER:String = "USER";
		
		/** The default message value of OPERATOR. */
		public static const OPERATOR:String = "OPERATOR";
		
		/** The default message value of Holiday. */
		public static const HOLIDAY:String = "Holiday";
		
		private static var systemDate:Date = null;
		
		/** The separator for parameter **/
		public static const PARAM_SEPERATOR:String = '#';
		
		public static const PARAM_ESC:String = '/';
		public static const SEP_ESC_CHAR:String ='/#';
		public static const REP_BY_SPECIAL_CHAR:String = '^<^$@~@>';
		
		public static function set SYSTEM_DATE(val:Date):void{
			systemDate = val;
		}
		
		public static function get SYSTEM_DATE():Date
		{
			var systemDateCopy:Date = ObjectUtil.copy(systemDate) as Date;
			return systemDateCopy;
		}
		

	}
}