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
	import com.majescomastek.jbeam.facade.installationlist.InstallationListModuleFacade;
	import com.majescomastek.jbeam.facade.viewschedule.ViewScheduleModuleFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.model.proxy.ViewScheduleProxy;
	import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
	import com.majescomastek.jbeam.model.vo.ProgressLevelData;
	import com.majescomastek.jbeam.model.vo.ReqProcessRequestSchedule;
	import com.majescomastek.jbeam.view.components.viewschedule.ScheduleDetailsWindow;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the BatchObjectWindow view.
	 */
	public class ScheduleDetailsWindowMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "SCHEDULE_DETAILS_WINDOW_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		private var _viewScheduleProxy:ViewScheduleProxy;

		/**
		 * Create a new mediator object.
		 */
		public function ScheduleDetailsWindowMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener
				(ScheduleDetailsWindow.CLEANUP_BATCH_OBJECT_WINDOW, onCleanupScheduleDetailsWindow, false, 0, true);
			module.addEventListener(ScheduleDetailsWindow.REQUEST_BATCH_OBJECT_DATA,
				onRequestScheduleDetailsData, false, 0, true);
			module.addEventListener(ScheduleDetailsWindow.REFRESH_CLICK,
				onRefreshScheduleDetailsData, false, 0, true);
		}
		
		/**
		 * The function invoked when the REQUEST_BATCH_OBJECT_DATA event is fired.
		 */
		private function onRequestScheduleDetailsData(event:CustomDataEvent):void
		{
			_viewScheduleProxy.getScheduleData
				(ReqProcessRequestSchedule(event.eventData),this.mediatorName);
		}
		
		/**
		 * The function invoked when the REFRESH_CLICK event is fired.
		 */
		private function onRefreshScheduleDetailsData(event:CustomDataEvent):void
		{
			_viewScheduleProxy.refreshProcessRequestScheduleData
				(ReqProcessRequestSchedule(event.eventData), this.mediatorName);
		}
			
		/**
		 * The function invoked when the CLEANUP_BATCH_OBJECT_WINDOW event is fired.
		 */
		private function onCleanupScheduleDetailsWindow(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ScheduleDetailsWindow
		{
			return viewComponent as ScheduleDetailsWindow;						
		}
		
		override public function onRegister():void
		{
			_viewScheduleProxy = ViewScheduleProxy(facade.retrieveProxy(ViewScheduleProxy.NAME));
		}
		
		override public function onRemove():void
		{
			module.removeEventListener
				(ScheduleDetailsWindow.CLEANUP_BATCH_OBJECT_WINDOW, onCleanupScheduleDetailsWindow, false);
			module.removeEventListener
				(ScheduleDetailsWindow.REQUEST_BATCH_OBJECT_DATA, onRequestScheduleDetailsData, false);
			module.addEventListener(ScheduleDetailsWindow.REFRESH_CLICK,
				onRefreshScheduleDetailsData, false);

			setViewComponent(null);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ViewScheduleModuleFacade.SCHEDULE_DETAILS_WINDOW_STARTUP_COMPLETE,
				ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_SUCCEEDED,
				ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_FAILED
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
				case ViewScheduleModuleFacade.SCHEDULE_DETAILS_WINDOW_STARTUP_COMPLETE:
					module.handleStartupComplete(data);
					break;
				case ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_SUCCEEDED:
					module.handleScheduleDetailsRetrieval(data);
					break;
				case ViewScheduleProxy.GET_SCHEDULE_DATA_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ScheduleDetailsWindowMediator');
					break;
			}
		}
		
	}
}