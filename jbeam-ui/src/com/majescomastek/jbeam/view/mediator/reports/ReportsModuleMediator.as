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
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/view/mediator/reports/Repor $
 * 
 * 1     4/07/10 12:01p Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.view.mediator.reports
{
	import com.majescomastek.jbeam.common.BusinessConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.view.components.reports.ReportsModule;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportsModuleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "REPORTS_MODULE_MEDIATOR";
		
		/**
		 * Create a <code>Reports Module Mediator</code> object for the given view.
		 */
		public function ReportsModuleMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
		}
		
		/**
		 * Retrieve the reference to the view which this mediator mediates.
		 */
		public function get module():ReportsModule
		{
			return viewComponent as ReportsModule;
		}
		
		/**
		 * @inheritDoc
		 */	
		override public function listNotificationInterests():Array
		{
			return [
				ReportsFacade.REPORTS_MODULE_STARTUP_COMPLETE,
				ReportsFacade.SHOW_ALERT_MESSAGE,
				ReportsFacade.SERVICE_FAULT_HANDLER,
				ReportsModule.SELECT_VIEW,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED
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
				case ReportsFacade.REPORTS_MODULE_STARTUP_COMPLETE:
					sendNotificationForView();
				break;
				case ReportsFacade.SHOW_ALERT_MESSAGE:
					module.showAlertMessage(notification.getBody() as String);
				break;
				case ReportsFacade.SERVICE_FAULT_HANDLER:
					CommonUtils.showWsFault(Fault(data));
				break;
				case ReportsModule.SELECT_VIEW:
					module.selectVSChild(int(notification.getBody()));
				break;
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:
					var dropDownData:ArrayCollection = notification.getBody() as ArrayCollection;
				break;
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED:
					CommonUtils.showWsFault(Fault(data));
				break;
				default:
					throw new Error("Invalid Notification");				
			}
		}
		
		private function sendNotificationForView():void
		{
			var data:Object = module.getModuleData();
			data['reportsParameterViewIndex'] = module.getNotificationDisplayObject(ReportsModule.REPORTS_PARAMETERS_VIEW);
			sendNotification(ReportsFacade.REPORTS_PARAMETERS_VIEW_STARTUP , data);
//			sendNotification(ReportsFacade.REPORT_ATTACHED_SCHEDULE_STARTUP,
//							module.getNotificationDisplayObject(ReportsModule.REPORT_ATTACHED_SCHEDULE));
			sendNotification(ReportsFacade.REPORT_ENGINE_REQUEST_LOG_STARTUP,
							module.getNotificationDisplayObject(ReportsModule.REPORT_ENGINE_REQUEST_LOG));
			sendNotification(ReportsFacade.REPORT_REQUEST_STATUS_STARTUP,
							module.getNotificationDisplayObject(ReportsModule.REPORT_REQUEST_STATUS));
							
			getBaseDropDown();
		}
		private function getBaseDropDown():void
		{
			//module.enabled = false;
			var commonProxy:CommonProxy = facade.retrieveProxy(CommonProxy.NAME) as CommonProxy;
			commonProxy.retrieveDropDownEntries(new ArrayCollection([BusinessConstants.FREQUENCY_CODES,
																	BusinessConstants.WEEKDAY_CODES,
																	BusinessConstants.REPORT_FORMAT_CODES 
																	]));
		}
	}
}