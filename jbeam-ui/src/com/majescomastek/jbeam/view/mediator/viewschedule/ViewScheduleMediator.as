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
 */
package com.majescomastek.jbeam.view.mediator.viewschedule
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.facade.viewschedule.ViewScheduleModuleFacade;
	import com.majescomastek.jbeam.model.proxy.ViewScheduleProxy;
	import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.ReqProcessRequestSchedule;
	import com.majescomastek.jbeam.view.components.viewschedule.ViewScheduleModule;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ViewScheduleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "VIEW_SCHEDULE_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		private var _viewScheduleProxy:ViewScheduleProxy;

		/**
		 * Create a new mediator object.
		 */
		public function ViewScheduleMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(ViewScheduleModule.GET_SCHEDULE_DATA_REQUEST,
				 onGetScheduleDataRequest, false, 0, true);
			viewComponent.addEventListener(ViewScheduleModule.SCHEDULE_DETAILS_CLICK,
				onScheduleDetailsClick, false, 0, true);
			viewComponent.addEventListener(ViewScheduleModule.SCHEDULE_STATUS_REFRESH_REQUEST,
				onRefreshScheduleStatusRequest, false, 0, true);
			viewComponent.addEventListener(ViewScheduleModule.CANCEL_CLICK,
				onScheduleCancelClick, false, 0, true);
			viewComponent.addEventListener(ViewScheduleModule.END_REASON_CLICK,				
				onShowEndReasonClick, false, 0, true);
		}
		
		/**
		 * The function invoked when the GET_SCHEDULE_DATA_REQUEST event is fired.
		 */
		private function onGetScheduleDataRequest(evt:CustomDataEvent):void
		{
			_viewScheduleProxy.getScheduleData(ReqProcessRequestSchedule(evt.eventData));
		}
		
		/**
		 * The function invoked when the SCHEDULE_STATUS_REFRESH_REQUEST event is fired.
		 */
		private function onRefreshScheduleStatusRequest(event:CustomDataEvent):void
		{
			_viewScheduleProxy.refreshProcessRequestScheduleData(
				ReqProcessRequestSchedule(event.eventData));
		}
		
		/**
		 * The function invoked when the SCHEDULE_DETAILS_CLICK event is fired.
		 */
		private function onScheduleDetailsClick(event:CustomDataEvent):void
		{
			sendNotification(ViewScheduleModuleFacade.SCHEDULE_DETAILS_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the END_REASON_CLICK event is fired.
		 */
		private function onShowEndReasonClick(event:CustomDataEvent):void
		{
			sendNotification(ViewScheduleModuleFacade.END_REASON_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the CANCEL_CLICK event is fired.
		 */
		private function onScheduleCancelClick(event:CustomDataEvent):void
		{
			_viewScheduleProxy.cancelSchedule(ReqProcessRequestSchedule(event.eventData));
		}
		
		/**
		 * The function invoked when the NAVIGATE_DEFAULT_VIEW_REQUEST event is fired.
		 * This function will navigate the user to the default Installation List Moddule. 
		 */
		private function onNavigateDefaultViewRequest(evt:CustomDataEvent):void
		{
			sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW, Object(evt.eventData));
		}
		
		/**
		 * The function invoked when the CLEANUP_SCHEDULE_BATCH event is fired.
		 */
		private function onCleanupViewSchedule(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ViewScheduleModule
		{
			return viewComponent as ViewScheduleModule;						
		}
		
		override public function onRegister():void
		{
			_viewScheduleProxy = ViewScheduleProxy(facade.retrieveProxy(ViewScheduleProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(ViewScheduleModule.GET_SCHEDULE_DATA_REQUEST,
				 onGetScheduleDataRequest, false);
			viewComponent.removeEventListener(ViewScheduleModule.SCHEDULE_DETAILS_CLICK,
				onScheduleDetailsClick, false);
			viewComponent.removeEventListener(ViewScheduleModule.SCHEDULE_STATUS_REFRESH_REQUEST,
				onRefreshScheduleStatusRequest, false);
			viewComponent.removeEventListener(ViewScheduleModule.CANCEL_CLICK,
				onScheduleCancelClick, false);
			viewComponent.removeEventListener(ViewScheduleModule.END_REASON_CLICK,				
				onShowEndReasonClick, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ViewScheduleModuleFacade.VIEW_SCHEDULE_MODULE_STARTUP_COMPLETE,
				ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_SUCCEEDED,
				ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_FAILED,
				ViewScheduleProxy.REFRESH_SCHDULE_DATA_SERVICE_SUCCEEDED,
				ViewScheduleProxy.REFRESH_SCHDULE_DATA_SERVICE_FAILED,
				ViewScheduleProxy.CANCEL_SCHDULE_SERVICE_SUCCEEDED,
				ViewScheduleProxy.CANCEL_SCHDULE_SERVICE_FAILED
				
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var name:String = notification.getName();
			var type:String = notification.getType();
			var data:Object = notification.getBody();
			
			// Halt processing if the notification is not meant for this mediator.
			if(type != null && type != mediatorName)	return;
			
			switch(name)
			{
				case ViewScheduleModuleFacade.VIEW_SCHEDULE_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_SUCCEEDED:
					module.handleGetScheduleDataServiceResult(data);
					break;
				case ViewScheduleProxy.REFRESH_SCHDULE_DATA_SERVICE_SUCCEEDED:
					module.handleRefreshScheduleDataServiceResult(data);
					break;
				case ViewScheduleProxy.CANCEL_SCHDULE_SERVICE_SUCCEEDED:
					module.handleCancelScheduleServiceResult(data);
					break;
				case ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_FAILED:
				case ViewScheduleProxy.REFRESH_SCHDULE_DATA_SERVICE_FAILED:
				case ViewScheduleProxy.CANCEL_SCHDULE_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ViewScheduleMediator');
					break;
			}
		}
		
	}
}