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
 * @author Gourav Rai
 * 
 *
 * $Revision:: 5                  $
 *	
 * $Header:: /Product_Base/Projects/jbeam-UI/FLEX UI DEV/src/com/majescomastek/jbeam/facade/reports/ReportsFacade.as 5     4/25/10 7:53p Go                                              $	
 *
 * $Log:: /Product_Base/Projects/jbeam-UI/FLEX UI DEV/src/com/majescomastek/jbeam/facade/reports/ReportsFacade.as                                                 $
 * 
 * 5     4/25/10 7:53p Gourav.rai
 * 
 * 4     4/25/10 5:08p Gourav.rai
 * 
 * 3     4/25/10 12:02p Gourav.rai
 * constant added
 * 
 * 2     4/12/10 6:54p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 11:57a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.facade.reports
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.reports.ReportAttachedScheduleStartUpCommand;
	import com.majescomastek.jbeam.controller.reports.ReportEngineRequestLogStartUpCommand;
	import com.majescomastek.jbeam.controller.reports.ReportRequestStatusStartUpCommand;
	import com.majescomastek.jbeam.controller.reports.ReportsParametersViewStartUpCommand;
	import com.majescomastek.jbeam.controller.reports.ReportsStartUpCommand;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ReportsProxy;
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	
	public class ReportsFacade extends BaseFacade
	{
		/** constant used as Name of this Facade */
		public static const NAME:String = "REPORTS_FACADE";
		
		public function ReportsFacade(key:String)
		{
			super(key);
		}
		
		/**
		 * The static factory method for the ReportsFacade.
		 */
		public static function getInstance(key:String):ReportsFacade
		{
			var facade:ReportsFacade = instanceMap[key];
			if(facade == null)
			{
				facade = instanceMap[key] = new ReportsFacade(key);
			}
			return facade;
		}
		
		/**
		 *  This function is invoked when module creation is completed 
		 */
		public function startup(app:Object):void
		{
			sendNotification(REPORTS_MODULE_STARTUP,app)
		}
		
		/**
		 * Initialize the controller for Adjustment
		 */
		override protected function initializeController():void
		{
			super.initializeController();
			registerCommand(REPORTS_MODULE_STARTUP, ReportsStartUpCommand);
			registerCommand(REPORTS_PARAMETERS_VIEW_STARTUP, ReportsParametersViewStartUpCommand);
			
			registerCommand(REPORT_ENGINE_REQUEST_LOG_STARTUP, ReportEngineRequestLogStartUpCommand);
			registerCommand(REPORT_REQUEST_STATUS_STARTUP, ReportRequestStatusStartUpCommand);
			registerCommand(REPORT_ATTACHED_SCHEDULE_STARTUP, ReportAttachedScheduleStartUpCommand);
			registerProxy(new ReportsProxy());
			registerProxy(new CommonProxy());			
		}
		
		/** The notification constant for the Reports Module startup */
		public static const REPORTS_MODULE_STARTUP:String = "REPORTS_MODULE_STARTUP";
		
		/** The notification constant for the Reports Module startup complete */
		public static const REPORTS_MODULE_STARTUP_COMPLETE:String = "REPORTS_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant for the Reports Parameters View startup */
		public static const REPORTS_PARAMETERS_VIEW_STARTUP:String = "REPORTS_PARAMETERS_VIEW_STARTUP";
		
		/** The notification constant for the Reports Parameters View startup complete */
		public static const REPORTS_PARAMETERS_VIEW_STARTUP_COMPLETE:String = "REPORTS_PARAMETERS_VIEW_STARTUP_COMPLETE";
		
		/** The notification constant for the Reports alert message */
		public static const SHOW_ALERT_MESSAGE:String = "REPORTS_SHOW_ALERT_MESSAGE";
		
		/** The notification constant for the Retrieve Report DropDown Option For Company */
		//public static const RETRIEVE_REPORT_DROP_DOWN_OPTION:String = "RETRIEVE_REPORT_DROP_DOWN_OPTION";
		
		/** The notification constant for the Retrieve Report DropDown Option For Company Result Handler*/
		public static const RETRIEVE_REPORT_DROP_DOWN_OPTION_RESULT_HANDLER:String = "RETRIEVE_REPORT_DROP_DOWN_OPTION_RESULT_HANDLER";
		
		/** The notification constant for the Retrieve Report DropDown Option For Company fault Handler*/
		public static const RETRIEVE_REPORT_DROP_DOWN_OPTION_FAULT_HANDLER:String = "RETRIEVE_REPORT_DROP_DOWN_OPTION_FAULT_HANDLER";
		 
		/** The notification constant for the Retrieve Parameters For Report */
		public static const RETRIEVE_PARAMETERS_FOR_REPORT:String = "RETRIEVE_PARAMETERS_FOR_REPORT";
		
		/** The notification constant for the Retrieve Parameters For Report Result Handler*/
		public static const RETRIEVE_PARAMETERS_FOR_REPORT_RESULT_HANDLER:String = "RETRIEVE_PARAMETERS_FOR_REPORT_RESULT_HANDLER";
		
		/** The notification constant for the Retrieve Parameters For Report Fault Handler*/
		public static const RETRIEVE_PARAMETERS_FOR_REPORT_FAULT_HANDLER:String = "RETRIEVE_PARAMETERS_FOR_REPORT_FAULT_HANDLER";
		
		/** The notification constant for the Retrieve system date */
		public static const RETRIEVE_SYSTEM_DATE:String = "RETRIEVE_SYSTEM_DATE";
		
		/** The notification constant for the Retrieve system date Result Handler*/
		public static const RETRIEVE_SYSTEM_DATE_RESULT_HANDLER:String = "RETRIEVE_SYSTEM_DATE_RESULT_HANDLER";
		
		/** The notification constant for the Retrieve system date Fault Handler*/
		public static const RETRIEVE_SYSTEM_DATE_FAULT_HANDLER:String = "RETRIEVE_SYSTEM_DATE_FAULT_HANDLER";
		
		/** The notification constant for the Service Fault Handler*/
		public static const SERVICE_FAULT_HANDLER:String = "SERVICE_FAULT_HANDLER";
		
		
		
		/** The notification constant for the Report Engine Request Log startup */
		public static const REPORT_ENGINE_REQUEST_LOG_STARTUP:String = "REPORT_ENGINE_REQUEST_LOG_STARTUP";
		
		/** The notification constant for the Report Engine Request Log startup complete */
		public static const REPORT_ENGINE_REQUEST_LOG_STARTUP_COMPLETE:String = "REPORT_ENGINE_REQUEST_LOG_STARTUP_COMPLETE";
		
		/** The notification constant for the Report Request Status startup */
		public static const REPORT_REQUEST_STATUS_STARTUP:String = "REPORT_REQUEST_STATUS_STARTUP";
		
		/** The notification constant for the Report Request Status startup complete */
		public static const REPORT_REQUEST_STATUS_STARTUP_COMPLETE:String = "REPORT_REQUEST_STATUS_STARTUP_COMPLETE";
		
		/** The notification constant for the Report Attached Schedule startup */
		public static const REPORT_ATTACHED_SCHEDULE_STARTUP:String = "REPORT_ATTACHED_SCHEDULE_STARTUP";
		
		/** The notification constant for the Report Attached Schedule startup complete */
		public static const REPORT_ATTACHED_SCHEDULE_STARTUP_COMPLETE:String = "REPORT_ATTACHED_SCHEDULE_STARTUP_COMPLETE";
		
		/** The notification constant for the Report Engine Request Log Report Generate online*/
		public static const REPORT_GENERATE_ONLINE:String = "REPORT_GENERATE_ONLINE";
		
		/** The notification constant for the Report Engine Request Log Report Generate offline*/
		public static const REPORT_GENERATE_OFFLINE:String = "REPORT_GENERATE_OFFLINE";
		
		/** The notification constant for the Report Engine Request Log Report Generate offline result handler*/
		public static const REPORT_GENERATE_OFFLINE_RESULT:String = "REPORT_GENERATE_OFFLINE_RESULT_HANDLER";
		
		/** The notification constant for the Report Engine Request Log Report Generate offline fault handler*/
		public static const REPORT_GENERATE_OFFLINE_FAULT:String = "REPORT_GENERATE_OFFLINE_FAULT_HANDLER";
		
		/** The notification constant for the Report Engine Request Log Data */
		public static const REPORT_ENGINE_REQUEST_LOG_DATA:String = "REPORT_ENGINE_REQUEST_LOG_DATA";
		
		/** The notification constant for the Report Request Status */
		public static const REPORT_REQUEST_STATUS_ENQUIRY:String = "REPORT_REQUEST_STATUS_ENQUIRY";
		
		/** The notification constant for the Refetch Report Request Status */
		public static const REFETCH_REPORT_REQUEST_STATUS_ENQUIRY:String = "REFETCH_REPORT_REQUEST_STATUS_ENQUIRY";
		
		/** The notification constant for the Report Request Status Data */
		public static const REPORT_REQUEST_STATUS_DATA:String = "REPORT_REQUEST_STATUS_DATA";
		
		/** The notification constant for the Report Request Status Data Result Handler*/
		public static const REPORT_REQUEST_STATUS_DATA_RESULT_HANDLER:String = "REPORT_REQUEST_STATUS_DATA_RESULT_HANDLER";
		
		/** The notification constant for the Report Request Status Data Fault Handler*/
		public static const REPORT_REQUEST_STATUS_DATA_FAULT_HANDLER:String = "REPORT_REQUEST_STATUS_DATA_FAULT_HANDLER";
		
		/** The notification constant for the Report Request Parameters Result Handler*/
		public static const PROCESS_REQUEST_PARAMETERS_RESULT_HANDLER:String = "PROCESS_REQUEST_PARAMETERS_RESULT_HANDLER";
		
		/** The notification constant for the Report Request Parameters Fault Handler*/
		public static const PROCESS_REQUEST_PARAMETERS_FAULT_HANDLER:String = "PROCESS_REQUEST_PARAMETERS_FAULT_HANDLER";
				
		/** The notification constant for the Report Request Status Data */
		public static const REPORT_ATTACHED_SCHEDULE_DATA:String = "REPORT_ATTACHED_SCHEDULE_DATA";
		
		/** The notification constant for the Report Request Status Data Result Handler*/
		public static const UPDATE_REQUEST_STATUS_RESULT_HANDLER:String = "UPDATE_REQUEST_STATUS_RESULT_HANDLER";
		
		/** The notification constant for the Report Request Status Data Fault Handler*/
		public static const UPDATE_REQUEST_STATUS_FAULT_HANDLER:String = "UPDATE_REQUEST_STATUS_FAULT_HANDLER";
		
		/** The notification constant for the Report Request Parameters Result Handler*/
		public static const UPDATE_SCHEDULE_STATUS_RESULT_HANDLER:String = "UPDATE_SCHEDULE_STATUS_RESULT_HANDLER";
		
		/** The notification constant for the Report Request Parameters Fault Handler*/
		public static const UPDATE_SCHEDULE_STATUS_FAULT_HANDLER:String = "UPDATE_SCHEDULE_STATUS_FAULT_HANDLER";
		
		/** The notification constant for the Schedule Report*/
		public static const SCHEDULE_REPORT:String = "SCHEDULE_REPORT";
		
		/** The notification constant for the Schedule Report Result Handler*/
		public static const SCHEDULE_REPORT_RESULT_HANDLER:String = "SCHEDULE_REPORT_RESULT_HANDLER";
		
		/** The notification constant for the Schedule Report Fault Handler*/
		public static const SCHEDULE_REPORT_FAULT_HANDLER:String = "SCHEDULE_REPORT_FAULT_HANDLER";
		
		
		/** The notification constant for the Retrieve Schedule Activity*/
		public static const RETRIEVE_SCHEDULE_ACTIVITY:String = "RETRIEVE_SCHEDULE_ACTIVITY";
		
		/** The notification constant for the Retrieve Schedule Activity Result Handler*/
		public static const RETRIEVE_SCHEDULE_ACTIVITY_RESULT_HANDLER:String = "RETRIEVE_SCHEDULE_ACTIVITY_RESULT_HANDLER";
		
		/** The notification constant for the Retrieve Schedule Activity Fault Handler*/
		public static const RETRIEVE_SCHEDULE_ACTIVITY_FAULT_HANDLER:String = "RETRIEVE_SCHEDULE_ACTIVITY_FAULT_HANDLER";
		
	}
}