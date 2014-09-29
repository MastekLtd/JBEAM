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
 * $Revision:: 3                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $
 * 
 * 3     4/25/10 7:55p Gourav.rai
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
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.ReportsProxy;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.view.components.reports.ReportEngineRequestLog;
	import com.majescomastek.jbeam.view.components.reports.ReportsModule;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportEngineRequestLogMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "REPORT_ENGINE_REQUEST_LOG_MEDIATOR";
		
		private var reportsProxy:ReportsProxy = null;
		
		/**
		 * Create a <code>Report Engine Request Log Mediator</code> object for the given view.
		 */
		public function ReportEngineRequestLogMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(ReportsModule.SELECT_VIEW,onSelectView,false,0,true);
			module.addEventListener(ReportsFacade.REPORT_REQUEST_STATUS_ENQUIRY,onReportRequestEnquiry,false,0,true);
			module.addEventListener(ReportsFacade.SHOW_ALERT_MESSAGE,showAlertMsg,false,0,true);
		}
		
		/**
		 * Retrieve the reference to the view which this mediator mediates.
		 */
		public function get module():ReportEngineRequestLog
		{
			return viewComponent as ReportEngineRequestLog;
		}
		
		/**
		 * @inheritDoc
		 */	
		override public function listNotificationInterests():Array
		{
			return [
				ReportsFacade.REPORT_ENGINE_REQUEST_LOG_STARTUP_COMPLETE,
				ReportsFacade.REPORT_ENGINE_REQUEST_LOG_DATA,
				ReportsFacade.REPORT_REQUEST_STATUS_DATA_RESULT_HANDLER,
				ReportsFacade.REPORT_REQUEST_STATUS_DATA_FAULT_HANDLER,
				ReportsFacade.REFETCH_REPORT_REQUEST_STATUS_ENQUIRY				
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			module.enabled = true;
			switch(notification.getName())
			{
				case ReportsFacade.REPORT_ENGINE_REQUEST_LOG_STARTUP_COMPLETE:
					
				break;
				case ReportsFacade.REFETCH_REPORT_REQUEST_STATUS_ENQUIRY:
					module.onReport();
				break;
				case ReportsFacade.REPORT_ENGINE_REQUEST_LOG_DATA:
					module.getDisplayData(notification.getBody() as ExecuteReport);
				break;
				case ReportsFacade.REPORT_REQUEST_STATUS_DATA_RESULT_HANDLER:
					onReportRequestEnquiryResult(notification.getBody() as ExecuteReport);
				break;
				case ReportsFacade.REPORT_REQUEST_STATUS_DATA_FAULT_HANDLER:
					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
				break;
				default:
					throw new Error("Invalid Notification");				
			}
		}
		
		/**
		 * 
		 */
		private function onSelectView(event:CustomDataEvent):void
		{
			switch(event.eventData)
			{
				case "BACK":
					sendNotification(ReportsModule.SELECT_VIEW,ReportsModule.REPORTS_PARAMETERS_VIEW);
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
		private function showAlertMsg(event:CustomDataEvent):void
		{
			sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,event.eventData);
		}
		
		/**
		 * 
		 * @param event
		 * 
		 */		
		private function onReportRequestEnquiry(event:CustomDataEvent):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy;
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.retrieveRequestStatusDetails(event.eventData as ExecuteReport,CommonConstants.USER_PROFILE);
		}
		
		/**
		 * 
		 * @param executeReport
		 * 
		 */				
		private function onReportRequestEnquiryResult(executeReport:ExecuteReport):void
		{
			sendNotification(ReportsModule.SELECT_VIEW,ReportsModule.REPORT_REQUEST_STATUS);
			sendNotification(ReportsFacade.REPORT_REQUEST_STATUS_DATA,executeReport);						
		}
	}
}