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
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ReportsProxy;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.BaseValueObject;
	import com.majescomastek.jbeam.model.vo.ExecuteReport;
	import com.majescomastek.jbeam.model.vo.ReportParameter;
	import com.majescomastek.jbeam.view.components.reports.CancelSchedulePopUp;
	import com.majescomastek.jbeam.view.components.reports.ReportCustomLinkButton;
	import com.majescomastek.jbeam.view.components.reports.ReportRequestStatus;
	import com.majescomastek.jbeam.view.components.reports.ReportsModule;
	import com.majescomastek.jbeam.view.components.reports.RequestStatusPopUp;
	import com.majescomastek.jbeam.view.components.reports.RequestSubmitWithFollowingParameterPopUp;
	
	import flash.display.DisplayObject;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.net.navigateToURL;
	
	import mx.collections.ArrayCollection;
	import mx.core.IFlexDisplayObject;
	import mx.managers.PopUpManager;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportRequestStatusMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "REPORT_REQUEST_STATUS_MEDIATOR";
		
		private var reportsProxy:ReportsProxy = null;
		/**
		 * Create a <code>Report Request Status Mediator</code> object for the given view.
		 */
		public function ReportRequestStatusMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(ReportsModule.SELECT_VIEW,onSelectView,false,0,true);
			module.addEventListener(ReportCustomLinkButton.LINK_CLICK,onLinkClick,false,0,true);
		}
		
		/**
		 * Retrieve the reference to the view which this mediator mediates.
		 */
		public function get module():ReportRequestStatus
		{
			return viewComponent as ReportRequestStatus;
		}
		
		/**
		 * @inheritDoc
		 */	
		override public function listNotificationInterests():Array
		{
			return [
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED,
				ReportsFacade.REPORT_REQUEST_STATUS_STARTUP_COMPLETE,
				ReportsFacade.REPORT_REQUEST_STATUS_DATA,
				ReportsFacade.PROCESS_REQUEST_PARAMETERS_RESULT_HANDLER,
				ReportsFacade.PROCESS_REQUEST_PARAMETERS_FAULT_HANDLER,
				ReportsFacade.UPDATE_REQUEST_STATUS_RESULT_HANDLER,
				ReportsFacade.UPDATE_REQUEST_STATUS_FAULT_HANDLER,
				ReportsFacade.UPDATE_SCHEDULE_STATUS_RESULT_HANDLER,
				ReportsFacade.UPDATE_SCHEDULE_STATUS_FAULT_HANDLER,
				ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_RESULT_HANDLER,
				ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_FAULT_HANDLER				
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
				case ReportsFacade.REPORT_REQUEST_STATUS_STARTUP_COMPLETE:
					
				break;
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:
					module.setDropDownData(notification.getBody() as ArrayCollection);
				break;
				case ReportsFacade.UPDATE_REQUEST_STATUS_RESULT_HANDLER:
					sendNotification(ReportsFacade.REFETCH_REPORT_REQUEST_STATUS_ENQUIRY,null);
					var baseValueObject:BaseValueObject = notification.getBody() as BaseValueObject;
					sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,baseValueObject.exceptionMessage);
				break;
				case ReportsFacade.UPDATE_REQUEST_STATUS_FAULT_HANDLER:
					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
				break;
				case ReportsFacade.REPORT_REQUEST_STATUS_DATA:
					module.getReportRequestStatusData(notification.getBody() as ExecuteReport);
				break;
				case ReportsFacade.PROCESS_REQUEST_PARAMETERS_RESULT_HANDLER:
					requestSubmitWithFollowingParameter(notification.getBody() as ExecuteReport);
				break;
				case ReportsFacade.PROCESS_REQUEST_PARAMETERS_FAULT_HANDLER:
					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
				break;
				case ReportsFacade.UPDATE_SCHEDULE_STATUS_RESULT_HANDLER:
					sendNotification(ReportsFacade.REFETCH_REPORT_REQUEST_STATUS_ENQUIRY,null);
					var baseValue:BaseValueObject = notification.getBody() as BaseValueObject;
					sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,baseValue.exceptionMessage);
				break;
				case ReportsFacade.UPDATE_SCHEDULE_STATUS_FAULT_HANDLER:
					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
				break;
				case ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_RESULT_HANDLER:				
					cancelSchedulePopUp(notification.getBody() as ExecuteReport);
				break;
				case ReportsFacade.RETRIEVE_SCHEDULE_ACTIVITY_FAULT_HANDLER:				
					sendNotification(ReportsFacade.SERVICE_FAULT_HANDLER,notification.getBody());
				break;
				default:
					throw new Error("Invalid Notification");				
			}
		}
		
		/**
		 * 
		 * @param event
		 * 
		 */		
		private function onSelectView(event:CustomDataEvent):void
		{
			sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,"");
			switch(event.eventData)
			{
				case "BACK":
					sendNotification(ReportsModule.SELECT_VIEW,ReportsModule.REPORT_ENGINE_REQUEST_LOG);
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
		private function onLinkClick(customDataEvent:CustomDataEvent):void
		{
			sendNotification(ReportsFacade.SHOW_ALERT_MESSAGE,"");
			var dataField:String = customDataEvent.eventData.dataField.toString();
			var reportParameter:ReportParameter = customDataEvent.eventData.data as ReportParameter;
			switch(dataField)
			{
				case "requestId":
					processRequestParameters(reportParameter);
					break;
				case "userId":
					processRequestParameters(reportParameter);
					break;
				case "menuId":
					processRequestParameters(reportParameter);
					break;
				case "scheduleId":
					retrieveScheduleActivity(reportParameter);
					break;
				case "reqLogFileName":
					onReqLogFileName(reportParameter);							
					break;
				case "requestState":
					requestStatusPopUp(reportParameter);
					break;					
				default:
					
				break;
			}
		}
		
		/**
		 * 
		 * @param reportParameter
		 * 
		 */		
		private function processRequestParameters(reportParameter:ReportParameter):void
		{
			var executeReport:ExecuteReport = new ExecuteReport();
			executeReport.requestId = reportParameter.requestId;
			executeReport.scheduleId = reportParameter.scheduleId;
			executeReport.userId = reportParameter.userId;
			executeReport.requestState = reportParameter.requestState;
			/* executeReport.startDate = reportParameter.startDate;
			executeReport.endDate = reportParameter.endDate; */
			executeReport.processName = reportParameter.processName;

			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.processRequestParameters(executeReport);
		}
		
		/**
		 * 
		 * @param reportParameter
		 * 
		 */		
		private function requestSubmitWithFollowingParameter(executeReport:ExecuteReport):void
		{
			var popup:IFlexDisplayObject = PopUpManager.createPopUp(module.parentApplication as DisplayObject, 
		 						RequestSubmitWithFollowingParameterPopUp, true);
		 	
		 	(popup as RequestSubmitWithFollowingParameterPopUp).executeReport = executeReport;
		}
		
		/**
		 * 
		 * @param reportParameter
		 * 
		 */		
		private function requestStatusPopUp(reportParameter:ReportParameter):void
		{
			var popup:IFlexDisplayObject = PopUpManager.createPopUp(module.parentApplication as DisplayObject, 
		 						RequestStatusPopUp, true);
		 	
		 	(popup as RequestStatusPopUp).reportParameter = reportParameter;
		 	(popup as RequestStatusPopUp).cancelRequest = cancelRequest;
		}		
		
		private function cancelRequest(executeReport:ExecuteReport):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy; 
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
//			reportsProxy.updateRequestStatus(executeReport,CommonConstants.USER_PROFILE);
		}
		
		private function onReqLogFileName(reportParameter:ReportParameter):void
		{
			var urlRequest:URLRequest = new URLRequest(module.reportUrl);
			var urlVariables:URLVariables = new URLVariables();
			urlVariables.docPath = reportParameter.reqLogFileName;
			urlRequest.data = urlVariables;
			urlRequest.method = URLRequestMethod.GET;
			navigateToURL(urlRequest,'_blank');
		}
		
		private function retrieveScheduleActivity(reportParameter:ReportParameter):void
		{
			var tempRptPara:ReportParameter = new ReportParameter();
			tempRptPara.scheduleId = reportParameter.scheduleId;
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
			reportsProxy.retrieveScheduleActivity(tempRptPara);
		}
		
		/**
		 * 
		 * @param reportParameter
		 * 
		 */		
		private function cancelSchedulePopUp(executeReport:ExecuteReport):void
		{
			var popup:IFlexDisplayObject = PopUpManager.createPopUp(module.parentApplication as DisplayObject, 
		 						CancelSchedulePopUp, true);
		 	
		 	(popup as CancelSchedulePopUp).frequecies = module.frequecies;
		 	(popup as CancelSchedulePopUp).weekDays = module.weekDays;
		 	(popup as CancelSchedulePopUp).executeReport = executeReport;
		 	(popup as CancelSchedulePopUp).cancelSchedule = cancelSchedule;
		}
		
		private function cancelSchedule(executeReport:ExecuteReport):void
		{
			var userProfileProxy:UserProfileProxy = ShellFacade.getInstance(ShellFacade.NAME).retrieveProxy(UserProfileProxy.NAME) as UserProfileProxy; 
			reportsProxy = facade.retrieveProxy(ReportsProxy.NAME) as ReportsProxy;
//			reportsProxy.updateScheduleStatus(executeReport,CommonConstants.USER_PROFILE);
		}
	}
}