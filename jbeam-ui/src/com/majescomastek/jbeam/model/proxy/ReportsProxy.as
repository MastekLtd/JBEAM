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
 * $Revision:: 3              $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/ReportsProxy.as 3     4/25 $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/proxy/ReportsProxy.as        $
 * 
 * 3     4/25/10 7:54p Gourav.rai
 * 
 * 2     4/12/10 6:53p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 11:58a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.model.delegate.ReportsWSDelegate;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.model.vo.Report;
	import com.majescomastek.jbeam.model.vo.ReportParameter;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class ReportsProxy extends BaseProxy
	{
		/** Name of this proxy */
		public static const NAME:String = 'REPORTS_PROXY';
		
		public function ReportsProxy(data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * The function used to invoke the `retrieveReportDropDownOptionForCompany' method 
		 * from the delegate.
		 * @param userProfile
		 * 
		 */		
		public function retrieveReportDropDownOptionForCompany(
			userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(reportDropDownResultHandler, reportDropDownFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
			delegate.retrieveReportDropDownOptionForCompany(userProfile, [responder], tokenData);
		}
		private function reportDropDownResultHandler(evt:ResultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_RESULT_HANDLER, evt.result);
		}
		private function reportDropDownFaultHandler(evt:FaultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_FAULT_HANDLER, evt.fault);
		}
		/**
		 * This operation retrieve the retrieval type and details of report id.
		 * 
		 * @param report
		 * report object to pass as input.
		 * 
		 * @return list of report parameters.
		 */
		public function retrieveParametersForReport(report:Report, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(retrieveParametersForReportResultHandler, retrieveParametersForReportFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
			delegate.retrieveParametersForReport(report, [responder], tokenData);
		}
		
		private function retrieveParametersForReportResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_RESULT_HANDLER,result.result);
		}
		private function retrieveParametersForReportFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_FAULT_HANDLER,fault.fault);
		}
		
		
	   /**
		* This operation retrieve the retrieval type and details of report id.
		* 
		* @param executeReport
		*            executeReport object to pass as input.
		* @param userProfile
		*            userProfile object to pass as input. 
		* 
		* @return process request status in offline.
		*/
		public function processRequest(executeReport:ExecuteReport
				,reqInstructionLog:ReqInstructionLog, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(processRequestResultHandler, processRequestFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
			delegate.processRequest(executeReport,reqInstructionLog, [responder], tokenData);
		}
		private function processRequestResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.REPORT_GENERATE_OFFLINE_RESULT,result.result);
		}
		private function processRequestFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.REPORT_GENERATE_OFFLINE_FAULT,fault.fault);
		}
		
		
		/**
		 * This operation used to fetch the request status details for the given details.
		 * @param reportParameter
		 * 		reportParameter object to pass as input.
		 * @param userProfile
		 *            userProfile object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function retrieveRequestStatusDetails(executeReport:ExecuteReport
							,userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(retrieveRequestStatusDetailsResultHandler
									, retrieveRequestStatusDetailsFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.retrieveRequestStatusDetails(executeReport
//						,userProfile, [responder], tokenData);
		}
		private function retrieveRequestStatusDetailsResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.REPORT_REQUEST_STATUS_DATA_RESULT_HANDLER,result.result);
		}
		private function retrieveRequestStatusDetailsFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.REPORT_REQUEST_STATUS_DATA_FAULT_HANDLER,fault.fault);
		}
		
		
		/**
		 * This operation is used to get the request parameters.
		 * @param reportParameter
		 * 		reportParameter object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function processRequestParameters(executeReport:ExecuteReport, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(processRequestParametersResultHandler, processRequestParametersFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.processRequestParameters(executeReport, [responder], tokenData);
		}
		private function processRequestParametersResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.PROCESS_REQUEST_PARAMETERS_RESULT_HANDLER,result.result);
		}
		private function processRequestParametersFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.PROCESS_REQUEST_PARAMETERS_FAULT_HANDLER,fault.fault);
		}
		
		
		/**
		 * This operation is used to update the request status.
		 * @param executeReport
		 * 		executeReport object to pass as input.
		 * @return {@link BaseValueObject}
		 */
		public function updateRequestStatus(executeReport:ExecuteReport,userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(updateRequestStatusResultHandler, updateRequestStatusFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.updateRequestStatus(executeReport,userProfile, [responder], tokenData);
		}
		private function updateRequestStatusResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.UPDATE_REQUEST_STATUS_RESULT_HANDLER,result.result);
		}
		private function updateRequestStatusFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.UPDATE_REQUEST_STATUS_FAULT_HANDLER,fault.fault);
		}
		/**
		 * This operation is used to update the schedule status.
		 * @param executeReport
		 * 		executeReport object to pass as input.
		 * @return {@link BaseValueObject}
		 */
		public function updateScheduleStatus(executeReport:ExecuteReport,userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(updateScheduleStatusResultHandler, updateScheduleStatusFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.updateScheduleStatus(executeReport,userProfile, [responder], tokenData);
		}
		private function updateScheduleStatusResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.UPDATE_SCHEDULE_STATUS_RESULT_HANDLER,result.result);
		}
		private function updateScheduleStatusFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.UPDATE_SCHEDULE_STATUS_FAULT_HANDLER,fault.fault);
		}
		
		/**
		 * This operation schedule the report for the given details.
		 * 
		 * @param executeReport
		 *            executeReport object to pass as input.
		 * @param userProfile
		 *            userProfile object to pass as input. 
		 * 
		 * @return process request status in off-line.
		 */
		public function scheduleReport(executeReport:ExecuteReport,userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(scheduleReportResultHandler, scheduleReportFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.scheduleReport(executeReport,userProfile, [responder], tokenData);
		}
		private function scheduleReportResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.SCHEDULE_REPORT_RESULT_HANDLER,result.result);
		}
		private function scheduleReportFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.SCHEDULE_REPORT_FAULT_HANDLER,fault.fault);
		}
		
		/**
		 * This operation fetch the schedule activity.
		 * @param reportParameter 
		 *  			reportParameter object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function retrieveScheduleActivity(reportParameter:ReportParameter, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(retrieveScheduleActivityResultHandler, retrieveScheduleActivityFaultHandler);
			var delegate:ReportsWSDelegate = new ReportsWSDelegate();
//			delegate.retrieveScheduleActivity(reportParameter, [responder], tokenData);
		}
		private function retrieveScheduleActivityResultHandler(result:ResultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_RESULT_HANDLER,result.result);
		}
		private function retrieveScheduleActivityFaultHandler(fault:FaultEvent):void
		{
			sendNotification(ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_FAULT_HANDLER,fault.fault);
		}
	}
}