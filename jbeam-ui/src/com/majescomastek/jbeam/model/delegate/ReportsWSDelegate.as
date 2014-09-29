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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/delegate/reports/ReportsWSDelega $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/delegate/reports/ReportsWSDe $
 * 
 * 3     4/25/10 7:53p Gourav.rai
 * 
 * 2     4/12/10 6:54p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 11:57a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.model.delegate
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.model.vo.InstructionParameter;
	import com.majescomastek.jbeam.model.vo.Report;
	import com.majescomastek.jbeam.model.vo.ReportParameter;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;

	/**
	 * The delegate class for the reports module used for invoking
	 * SOAP based webservices.
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class ReportsWSDelegate extends BaseSoapWsDelegate
	{
		public function ReportsWSDelegate()
		{
			super();
		}
		
		/**
		 * The function used to invoke the `retrieveReportDropDownOptionForCompany' method on
		 * the remote object.
		 * @param userProfile
		 * 
		 */		
		public function retrieveReportDropDownOptionForCompany(
			userProfile:UserProfile, externalResponders:Array,
			tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(retrieveReportDropDownOptionForCompanyResultHandler, defaultFaultHandler);
			invoke("retrieveReportDropDownOptionForCompany", getInputForRetrieveReportDropDownOptionForCompany(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRetrieveReportDropDownOptionForCompany(userProfile:UserProfile):XML
		{
			var xml:XML = <s:retrieveReportDropDownOptionForCompany xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<UserProfile>
								<userId>{userProfile.userId}</userId>
								<installationCode>{userProfile.installationCode}</installationCode>
						  	</UserProfile>
						  </s:retrieveReportDropDownOptionForCompany>;
			return xml;			
		}
		
		private function retrieveReportDropDownOptionForCompanyResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.RETRIEVE_REPORTS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var executeReport:ExecuteReport = new ExecuteReport();
				var retrievedReports:ObjectProxy = evt.result as ObjectProxy;				
				executeReport.reports = getReportsList(retrievedReports.reports);
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(executeReport, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		private function getReportsList(
			retrievedReportsResultDataList:ArrayCollection):ArrayCollection
		{
			var reportsList:ArrayCollection = new ArrayCollection();
			if(retrievedReportsResultDataList != null 
					&& retrievedReportsResultDataList.length > 0)
			{
				for(var i:uint = 0; 
						i < retrievedReportsResultDataList.length;
						++i)
				{
					var retrievedReports:ObjectProxy
							= retrievedReportsResultDataList.getItemAt(i) as ObjectProxy;
					var reportsResultData:Report 
						= createReports(retrievedReports);
					reportsList.addItem(reportsResultData);
				}
			}
			return reportsList;
		}
		
		/**
		 * Create a Reports object based on the data returned by the webservice.
		 */
		private function createReports(retrievedReports:ObjectProxy):Report
		{
			var report:Report = new Report();
			report.installationCode = retrievedReports['installationCode'];
			report.reportId = retrievedReports['reportId'];
			report.reportName = retrievedReports['reportName'];
			report.programName = retrievedReports['programName'];
			report.reportNo= retrievedReports['reportNo'];
			return report;
		}
		/**
		 * This operation retrieve the retrieval type and details of report id.
		 * 
		 * @param report
		 *            report object to pass as input.
		 * 
		 * @return list of report parameters.
		 */
		public function retrieveParametersForReport(report:Report, externalResponders:Array,
			tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(retrieveParametersForReportResultHandler, defaultFaultHandler);
			invoke("retrieveParametersForReport", getInputForRetrieveParametersForReport(report),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRetrieveParametersForReport(report:Report):XML
		{
			var xml:XML = <s:retrieveParametersForReport xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					         <Report>
					            <reportId>{report.reportId}</reportId>
					            <installationCode>{report.installationCode}</installationCode>
					         </Report>
					      </s:retrieveParametersForReport>;
			return xml;			
		}
		
		private function retrieveParametersForReportResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.RETRIEVE_PARAMETERS_FOR_REPORTS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var executeReport:ExecuteReport = new ExecuteReport();
				var retrievedReports:ObjectProxy = evt.result as ObjectProxy;				
				executeReport.reportParameters = getReportParametersList(retrievedReports.reportParameters);
				executeReport.reports = getReportsList(retrievedReports.reports);
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(executeReport, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		private function getReportParametersList(
			retrievedReportParametersResultDataList:ArrayCollection):ArrayCollection
		{
			var reportParametersList:ArrayCollection = new ArrayCollection();
			if(retrievedReportParametersResultDataList != null 
					&& retrievedReportParametersResultDataList.length > 0)
			{
				for(var i:uint = 0; 
						i < retrievedReportParametersResultDataList.length;
						++i)
				{
					var retrievedReportParameters:ObjectProxy
							= retrievedReportParametersResultDataList.getItemAt(i) as ObjectProxy;
					var reportParametersResultData:ReportParameter 
						= createReportParameters(retrievedReportParameters);
					reportParametersList.addItem(reportParametersResultData);
				}
			}
			return reportParametersList;
		}
		
		/**
		 * Create a ReportParameters object based on the data returned by the webservice.
		 */
		private function createReportParameters(retrievedReportParameters:ObjectProxy):ReportParameter
		{
			var reportParameter:ReportParameter = new ReportParameter();
			reportParameter.reportId = retrievedReportParameters['reportId'];
			reportParameter.dataType = retrievedReportParameters['dataType'];
			reportParameter.defaultValue = retrievedReportParameters['defaultValue'];
			reportParameter.hint = retrievedReportParameters['hint'];
			reportParameter.label = retrievedReportParameters['label'];
			reportParameter.mandatoryFlag = retrievedReportParameters['mandatoryFlag'];
			reportParameter.paramName = retrievedReportParameters['paramName'];
			reportParameter.query = retrievedReportParameters['query'];
			reportParameter.queryFlag = retrievedReportParameters['queryFlag'];
			reportParameter.staticDynamicFlag = retrievedReportParameters['staticDynamicFlag'];
			reportParameter.fieldMaxlength = retrievedReportParameters['fieldMaxlength'];
			return reportParameter;
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
		public function processRequest(executeReport:ExecuteReport,reqInstructionLog:ReqInstructionLog, 
						externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(processRequestResultHandler, defaultFaultHandler);
			invoke("processRequest", getInputForProcessRequest(executeReport, reqInstructionLog),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForProcessRequest(executeReport:ExecuteReport,
						reqInstructionLog:ReqInstructionLog):XML
		{
			var parentXml:XML = 
						      <s:processRequest xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						         <ExecuteReport>
						            <reportId>{executeReport.reportId}</reportId>
						            <startDate>{executeReport.startDate}</startDate>
						            <startTime>{executeReport.startTime}</startTime>
						         </ExecuteReport>
						         <ReqInstructionLog>						
						            <installationCode>{reqInstructionLog.installationCode}</installationCode>
						            <instructingUser>{reqInstructionLog.instructingUser}</instructingUser>
						            <instructionTime>{reqInstructionLog.instructionTime}</instructionTime>
						            <message>{reqInstructionLog.message}</message>						            
						         </ReqInstructionLog>
						      </s:processRequest>;
						      
					for each(var reportParams:ReportParameter in executeReport.reportParameters)
					{
						parentXml.ExecuteReport.appendChild(createReportParametersList(reportParams));
					} 
					for each(var report:Report in executeReport.reports)
					{
						parentXml.ExecuteReport.appendChild(createReportsList(report));
					} 
					for each(var instParams:InstructionParameter in reqInstructionLog.instructionParameters)
					{
						parentXml.ReqInstructionLog.appendChild(createInstructionParameterList(instParams));
					} 
			return parentXml;			
		}
		
		private function createInstructionParameterList(instParams:InstructionParameter):XML
		{
			var childXml:XML = 
				<instructionParameters>
	            	<name>{instParams.name}</name>
	               	<slNo>{instParams.slNo}</slNo>
	               	<type>{instParams.type}</type>
	               	<value>{instParams.value}</value>
	            </instructionParameters>;			
			return childXml;
		}
		
		private function createReportsList(reports:Report):XML
		{
			var childXml:XML = 
				<reports>
					<reportName>{reports.reportName}</reportName>						
				</reports>;			
			return childXml;
		}
		
		private function createReportParametersList(reportParam:ReportParameter):XML
		{
			var childXml:XML = 
				<reportParameters>						
	               <paramName>{reportParam.paramName}</paramName>
	               <paramValue>{reportParam.paramValue}</paramValue>						            
	               <paramDataType>{reportParam.dataType}</paramDataType>
	            </reportParameters>;			
			return childXml;
		}
		
		private function processRequestResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.PROCESS_REQUST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedRunBatchReportRequestData:Object = evt.result.batchDetails;
				retrievedRunBatchReportRequestData['responseTime'] = evt.result.responseTime;
				retrievedRunBatchReportRequestData['scheduledBatch'] = evt.result.scheduledBatch;
				var runBatchReportRequestData:BatchDetailsData = null;
				if(retrievedRunBatchReportRequestData != null)
				{
					runBatchReportRequestData = createRunBatchReportRequestData(retrievedRunBatchReportRequestData);
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(runBatchReportRequestData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		/**
		 * Create a run batch request object based on the data returned by the webservice.
		 */
		private function createRunBatchReportRequestData(retrievedRunBatchReportRequestData:Object):BatchDetailsData
		{
			var runBatchReportRequestData:BatchDetailsData = new BatchDetailsData();
			runBatchReportRequestData.installationCode = retrievedRunBatchReportRequestData['installationCode'];
			runBatchReportRequestData.instructionSeqNo = retrievedRunBatchReportRequestData['instructionSeqNo'];			
			runBatchReportRequestData.responseTime = retrievedRunBatchReportRequestData['responseTime'];			
			runBatchReportRequestData.isScheduledBatch = retrievedRunBatchReportRequestData['scheduledBatch'];			
			return runBatchReportRequestData;
		}
		
		/**
		 * This operation used to fetch the request status details for the given details.
		 * @param reportParameter
		 * 		reportParameter object to pass as input.
		 * @param userProfile
		 *            userProfile object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function retrieveRequestStatusDetails(executeReport:ExecuteReport,
				userProfile:UserProfile, externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(retrieveRequestStatusDetailsResultHandler, defaultFaultHandler);
//			invoke("retrieveRequestStatusDetails", getInputForRetrieveRequestStatusDetails(report),
//				externalResponders, internalResponder, tokenData);
//			remoteObject.retrieveRequestStatusDetails(executeReport,userProfile);
		}
		
		/**
		 * This operation is used to get the request parameters.
		 * @param reportParameter
		 * 		reportParameter object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function processRequestParameters(executeReport:ExecuteReport, 
						externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(processRequestParametersResultHandler, defaultFaultHandler);
//			invoke("processRequestParameters", getInputForProcessRequestParameters(report),
//				externalResponders, internalResponder, tokenData);	
//			remoteObject.processRequestParameters(executeReport);
		}
		
		/**
		 * This operation is used to update the request status.
		 * @param executeReport
		 * 		executeReport object to pass as input.
		 * @return {@link BaseValueObject}
		 */
		public function updateRequestStatus(executeReport:ExecuteReport,userProfile:UserProfile, 
						externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(updateRequestStatusResultHandler, defaultFaultHandler);
//			invoke("updateRequestStatus", getInputForUpdateRequestStatus(report),
//				externalResponders, internalResponder, tokenData);	
//			remoteObject.updateRequestStatus(executeReport,userProfile);			
		}
		
		/**
		 * This operation is used to update the schedule status.
		 * @param executeReport
		 * 		executeReport object to pass as input.
		 * @return {@link BaseValueObject}
		 */
		public function updateScheduleStatus(executeReport:ExecuteReport,userProfile:UserProfile, 
						externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(updateScheduleStatusResultHandler, defaultFaultHandler);
//			invoke("updateScheduleStatus", getInputForUpdateScheduleStatus(report),
//				externalResponders, internalResponder, tokenData);	
			//			remoteObject.updateScheduleStatus(executeReport,userProfile);			
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
		public function scheduleReport(executeReport:ExecuteReport,userProfile:UserProfile, 
						externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(scheduleReportResultHandler, defaultFaultHandler);
//			invoke("scheduleReport", getInputForScheduleReport(report),
//				externalResponders, internalResponder, tokenData);	
			//			remoteObject.scheduleReport(executeReport,userProfile);
		}
		
		/**
		 * This operation fetch the schedule activity.
		 * @param reportParameter 
		 *  			reportParameter object to pass as input.
		 * @return {@link ExecuteReport}
		 */
		public function retrieveScheduleActivity(reportParameter:ReportParameter, 
						externalResponders:Array, tokenData:Object=null):void
		{
//			var internalResponder:IResponder =
//				new Responder(retrieveScheduleActivityResultHandler, defaultFaultHandler);
//			invoke("retrieveScheduleActivity", getInputForRetrieveScheduleActivity(report),
//				externalResponders, internalResponder, tokenData);	
			//			remoteObject.retrieveScheduleActivity(reportParameter);
		}
	}
}