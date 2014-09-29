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
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $
 * 
 * 2     4/25/10 7:55p Gourav.rai
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
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ReportsProxy;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.BaseValueObject;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.view.components.reports.ReportAttachedSchedule;
	import com.majescomastek.jbeam.view.components.reports.ReportsModule;
	
	import mx.collections.ArrayCollection;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportAttachedScheduleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "REPORT_ATTACHED_SCHEDULE_MEDIATOR";
		
		private var reportsProxy:ReportsProxy = null;
		/**
		 * Create a <code>Report Attached Schedule Mediator</code> object for the given view.
		 */
		public function ReportAttachedScheduleMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(ReportsModule.SELECT_VIEW,onSelectView,false,0,true);
			module.addEventListener(ReportsFacade.SCHEDULE_REPORT,onScheduleReport,false,0,true);
			module.addEventListener(ReportsFacade.SHOW_ALERT_MESSAGE,showAlertMessage,false,0,true);
		}
		
		/**
		 * Retrieve the reference to the view which this mediator mediates.
		 */
		public function get module():ReportAttachedSchedule
		{
			return viewComponent as ReportAttachedSchedule;
		}
		
		/**
		 * @inheritDoc
		 */	
		override public function listNotificationInterests():Array
		{
			return [
				ReportsFacade.REPORT_ATTACHED_SCHEDULE_STARTUP_COMPLETE,
				ReportsFacade.REPORT_ATTACHED_SCHEDULE_DATA,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED,
				ReportsFacade.SCHEDULE_REPORT_RESULT_HANDLER,
				ReportsFacade.SCHEDULE_REPORT_FAULT_HANDLER
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
//			module.enabled = true;
//			switch(notification.getName())
//			{
//				case ReportsFacade.REPORT_ATTACHED_SCHEDULE_STARTUP_COMPLETE:
//					break;
//				case ReportsFacade.REPORT_ATTACHED_SCHEDULE_DATA:
//					module.getDisplayData(notification.getBody() as ExecuteReport);
//					break;
//				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:
//					module.setDropDownData(notification.getBody() as ArrayCollection);
//					break;
//				case ReportsFacade.SCHEDULE_REPORT_RESULT_HANDLER:
//					var baseValueObject:BaseValueObject = notification.getBody() as BaseValueObject;
//					sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,baseValueObject.exceptionMessage);
//					sendNotification(ReportsModule.SELECT_VIEW,ReportsModule.REPORTS_PARAMETERS_VIEW);
//					break;
//				case ReportsFacade.SCHEDULE_REPORT_FAULT_HANDLER:
//					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
//					break;
//				default:
//					throw new Error("Invalid Notification");				
//			}
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
		 * @param customDataEvent
		 * 
		 */		
		private function onScheduleReport(customDataEvent:CustomDataEvent):void
		{
			var executeReport:ExecuteReport = customDataEvent.eventData as ExecuteReport;
			scheduleReportForPDF(executeReport);
			// Changes made according to ERIE 9.
			/* for each(var report:Report in executeReport.reports)
			{
				if(report.reportType == "PDF")
				{
					scheduleReportForPDF(executeReport);
				}
				else
				{
					//scheduleReportForExcel(executeReport);
					scheduleReportForPDF(executeReport);
				}
				break;
			} */			
		}
		
		/**
		 * 
		 * @param executeReport
		 * 
		 */		
		private function scheduleReportForExcel(executeReport:ExecuteReport):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy;
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
//			reportsProxy.processRequest(executeReport,CommonConstants.USER_PROFILE);
		}
		
		/**
		 * 
		 * @param executeReport
		 * 
		 */		
		private function scheduleReportForPDF(executeReport:ExecuteReport):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy;
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.scheduleReport(executeReport,CommonConstants.USER_PROFILE);
		}
		
		private function showAlertMessage(eveent:CustomDataEvent):void
		{
			sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,eveent.eventData);
		}
	}
}