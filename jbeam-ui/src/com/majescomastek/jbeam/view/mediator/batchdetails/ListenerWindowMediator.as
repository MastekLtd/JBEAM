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
package com.majescomastek.jbeam.view.mediator.batchdetails
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.batchdetails.BatchDetailsModuleFacade;
	import com.majescomastek.jbeam.model.proxy.BatchDetailsProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchRevisionPod;
	import com.majescomastek.jbeam.view.components.batchdetails.ListenerWindow;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The medaitor class for the ListenerWindow view,
	 */
	public class ListenerWindowMediator extends BaseMediator
	{
		/** The base name of this mediator */
		public static const NAME:String = "LISTENER_WINDOW_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		// Make sure the BatchDetailsProxy is registered in the startup command of the
		// facade which uses this mediator.
		private var _batchDetailsProxy:BatchDetailsProxy;
		
		/**
		 * Create a new mediator object.
		 */
		public function ListenerWindowMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener(ListenerWindow.REFRESH_CLICK, onRefreshClick, false, 0, true);
			module.addEventListener
				(ListenerWindow.CLEANUP_LISTENER_WINDOW, onCleanupListenerWindow, false, 0, true);
			module.addEventListener(ListenerWindow.REQUEST_LISTENER_DATA,
				onRequestListenerData, false, 0, true);
			module.addEventListener(BatchRevisionPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false, 0, true);
		}
		
		/**
		 * The function invoked when the REQUEST_LISTENER_DATA event is fired.
		 */
		private function onRequestListenerData(event:CustomDataEvent):void
		{
			_batchDetailsProxy.getListenerData
				(BatchDetailsData(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the REFRESH_CLICK event is fired.
		 */
		private function onRefreshClick(event:CustomDataEvent):void
		{
			_batchDetailsProxy.getListenerData
				(BatchDetailsData(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the CLEANUP_LISTENER_WINDOW event is fired.
		 */
		private function onCleanupListenerWindow(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * The function invoked when the FAILED_OBJECT_CLICK event is fired.
		 */
		private function onFailedObjectClick(event:CustomDataEvent):void
		{
			sendNotification(BatchDetailsModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ListenerWindow
		{
			return viewComponent as ListenerWindow;						
		}
		
		override public function onRegister():void
		{
			_batchDetailsProxy = BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));
		}
		
		override public function onRemove():void
		{
			module.removeEventListener(ListenerWindow.REFRESH_CLICK, onRefreshClick, false);
			module.removeEventListener
				(ListenerWindow.CLEANUP_LISTENER_WINDOW, onCleanupListenerWindow, false);
			module.removeEventListener
				(ListenerWindow.REQUEST_LISTENER_DATA, onRequestListenerData, false);
			module.addEventListener
				(BatchRevisionPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false);

			setViewComponent(null);
		}
		
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.LISTENER_WINDOW_STARTUP_COMPLETE,
				BatchDetailsProxy.GET_LISTENER_DATA_SUCCEEDED,
				BatchDetailsProxy.GET_LISTENER_DATA_FAILED
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
				case BatchDetailsModuleFacade.LISTENER_WINDOW_STARTUP_COMPLETE:
					module.handleStartupComplete(data);
					break;
				case BatchDetailsProxy.GET_LISTENER_DATA_SUCCEEDED:
					module.handleListenerRetrieval(data);
					break;
				case BatchDetailsProxy.GET_LISTENER_DATA_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ListenerWindowMediator');
					break;
			}
		}

	}
}