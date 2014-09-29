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
 * @author Ritesh Umathe
 * 
 *
 * $Revision:: 4                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $
 * 
 * 4     4/25/10 7:55p Gourav.rai
 * 
 * 3     4/25/10 11:54a Gourav.rai
 * Changes on Run Report
 * 
 * 2     4/12/10 6:55p Gourav.rai
 * Srinivas is going to work on it.
 * 
 * 1     4/07/10 12:01p Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.view.mediator.reports
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ReportsProxy;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.model.vo.Report;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.reports.ReportsModule;
	import com.majescomastek.jbeam.view.components.reports.ReportsParametersView;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportsParametersViewMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "REPORTS_PARAMETERS_VIEW_MEDIATOR";
		
		private var reportsProxy:ReportsProxy = null;
		
		private var commonProxy:CommonProxy = null;
		
		/**
		 * Create a <code>Reports Parameters View Mediator</code> object for the given view.
		 */
		public function ReportsParametersViewMediator(viewComponent:Object)
		{
			super(NAME, viewComponent);
			module.addEventListener(ReportsFacade.RETRIEVE_SYSTEM_DATE,retrieveSystemDate,false,0,true);
			module.addEventListener(ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT,retrieveParametersForReports,false,0,true);
			module.addEventListener(ReportsFacade.SHOW_ALERT_MESSAGE,onShowAlertMessage,false,0,true);
			module.addEventListener(ReportsModule.SELECT_VIEW,onSelectView,false,0,true);
			module.addEventListener(ReportsFacade.REPORT_GENERATE_ONLINE,onlineReportGenerate,false,0,true);
			module.addEventListener(ReportsFacade.REPORT_GENERATE_OFFLINE,offlineReportGenerate,false,0,true);
		}
		
		/**
		 * Retrieve the reference to the view which this mediator mediates.
		 */
		public function get module():ReportsParametersView
		{
			return viewComponent as ReportsParametersView;
		}
		
		/**
		 * @inheritDoc
		 */	
		override public function listNotificationInterests():Array
		{
			return [
				ReportsFacade.REPORTS_PARAMETERS_VIEW_STARTUP_COMPLETE,
				ReportsFacade.REPORT_ATTACHED_SCHEDULE_DATA,
				ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_RESULT_HANDLER,
				ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_FAULT_HANDLER,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED,
				CommonProxy.RETRIEVE_SYSTEM_DATE_SUCCEEDED,
				CommonProxy.RETRIEVE_SYSTEM_DATE_FAILED,
				ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_RESULT_HANDLER,
				ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_FAULT_HANDLER,
				ReportsFacade.REPORT_GENERATE_OFFLINE_FAULT,
				ReportsFacade.REPORT_GENERATE_OFFLINE_RESULT
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var data:Object = notification.getBody();			
			module.enabled = true;
			switch(notification.getName())
			{
				case ReportsFacade.REPORTS_PARAMETERS_VIEW_STARTUP_COMPLETE:
					retrieveReportDropDownOptionForCompany(data as String);
					break;
				case ReportsFacade.REPORT_ATTACHED_SCHEDULE_DATA:
					module.getDisplayData(notification.getBody() as ExecuteReport);
					break;
				case ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_RESULT_HANDLER:
					module.reportDropDownResult(data as ExecuteReport);
					break;
				case CommonProxy.RETRIEVE_SYSTEM_DATE_SUCCEEDED:
					module.setSystemDate(data as ExecuteReport);
					break;
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:
					module.setDropDownData(data as ArrayCollection);
					break;
				case ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_RESULT_HANDLER:
					module.onRetrieveParametersForReportResult(data as ExecuteReport);
					break;
				case ReportsFacade.REPORT_GENERATE_OFFLINE_RESULT:
					module.offlineReportGenerateResult(data as BatchDetailsData);
					break;
				case ReportsFacade.RETRIEVE_PARAMETERS_FOR_REPORT_FAULT_HANDLER:
				case ReportsFacade.RETRIEVE_REPORT_DROP_DOWN_OPTION_FAULT_HANDLER:
				case CommonProxy.RETRIEVE_SYSTEM_DATE_FAILED:
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED:
				case ReportsFacade.REPORT_GENERATE_OFFLINE_FAULT:
					CommonUtils.showWsFault(Fault(data));
					break;				
				
				default:
					trace("Notification: "+ data);
					throw new Error("Invalid Notification");
					break;				
			}
		}
		
		/**
		 * Method is used to retrieve system date
		 */
		private function retrieveSystemDate(customDataEvent:CustomDataEvent):void
		{
			commonProxy = facade.retrieveProxy(CommonProxy.NAME) as CommonProxy;
			commonProxy.retrieveSystemDate();
		}
		
		/**
		 * method is used to retrieve report drop down option for company
		 */
		private function retrieveReportDropDownOptionForCompany(installationCode:String):void
		{
			var userProfile:UserProfile = CommonConstants.USER_PROFILE;
			userProfile.installationCode = installationCode; 
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.retrieveReportDropDownOptionForCompany(userProfile);
		}
		
		/**
		 * @ Retrieve relevant Parameters for the particular Reports
		 */
		private function retrieveParametersForReports(customDataEvent:CustomDataEvent):void
		{
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.retrieveParametersForReport(customDataEvent.eventData as Report);
		}
		
		
		/**
		 * 
		 */
		private function onShowAlertMessage(event:CustomDataEvent):void
		{
			sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,event.eventData);
		}
		
		/**
		 * 
		 */
		private function onSelectView(event:CustomDataEvent):void
		{
			switch(event.eventData)
			{
				case "SCHEDULE_HISTORY":
					sendNotification(ReportsModule.SELECT_VIEW,ReportsModule.REPORT_ENGINE_REQUEST_LOG);
					sendNotification(ReportsFacade.REPORT_ENGINE_REQUEST_LOG_DATA,module.getDataForScheduleHistory());
					break;
				case "ATTACHED_SCHEDULE":
					sendNotification(ReportsFacade.REPORT_ATTACHED_SCHEDULE_DATA,module.getDataForAttachedSchedule());
					break;
				default:
					trace("Invalid Action From Report Parameters View: "+event.eventData);
				break;
			}
		}
		
		/**
		 * 
		 * @param event
		 * 
		 */		
		private function onlineReportGenerate(event:CustomDataEvent):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy;
			var urlrequest:URLRequest = event.eventData as URLRequest;
			urlrequest.data["userId"] = CommonConstants.USER_PROFILE.userId;
			urlrequest.data["companyId"] = CommonConstants.USER_PROFILE.companyId;			
			urlrequest.data["appUser"] = CommonConstants.USER_PROFILE.userId;//This has to be changed.
			urlrequest.data["country"] = CommonConstants.USER_PROFILE.userCountry;
			urlrequest.data["language"] = CommonConstants.USER_PROFILE.userLanguage; 
			navigateToURL(urlrequest,"_blank");
		}
		
		private function offlineReportGenerate(event:CustomDataEvent):void
		{
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.processRequest(event.eventData as ExecuteReport, module.getReqInstructionLogData());
		}
	}
}