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
package com.majescomastek.jbeam.view.mediator.installationlist
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.installationlist.InstallationListModuleFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
	import com.majescomastek.jbeam.view.components.installationlist.FailedObjectsWindow;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The medaitor class for the FailedObjectsWindow view,
	 */
	public class FailedObjectsWindowMediator extends BaseMediator
	{
		/** The base name of this mediator */
		public static const NAME:String = "FAILED_OBJECTS_WINDOW_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		// Make sure the ShellProxy is registered in the startup command of the
		// facade which uses this mediator.
		private var _installationListProxy:InstallationListProxy;
		
		/**
		 * Create a new mediator object.
		 */
		public function FailedObjectsWindowMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener(FailedObjectsWindow.REFRESH_CLICK, onRefreshClick, false, 0, true);
			module.addEventListener
				(FailedObjectsWindow.CLEANUP_FAILED_OBJECT_WINDOW, onCleanupFailedObjectWindow, false, 0, true);
			module.addEventListener(FailedObjectsWindow.REQUEST_FAILED_OBJECT_DATA,
				onRequestFailedObjectData, false, 0, true);
		}
		
		/**
		 * The function invoked when the REQUEST_FAILED_OBJECT_DATA event is fired.
		 */
		private function onRequestFailedObjectData(event:CustomDataEvent):void
		{
			_installationListProxy.getFailedObjectDetails
				(RequestListenerInfo(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the REFRESH_CLICK event is fired.
		 */
		private function onRefreshClick(event:CustomDataEvent):void
		{
			_installationListProxy.getFailedObjectDetails
				(RequestListenerInfo(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the CLEANUP_FAILED_OBJECT_WINDOW event is fired.
		 */
		private function onCleanupFailedObjectWindow(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():FailedObjectsWindow
		{
			return viewComponent as FailedObjectsWindow;						
		}
		
		override public function onRegister():void
		{
			_installationListProxy = InstallationListProxy(facade.retrieveProxy(InstallationListProxy.NAME));
		}
		
		override public function onRemove():void
		{
			module.removeEventListener(FailedObjectsWindow.REFRESH_CLICK, onRefreshClick, false);
			module.removeEventListener
				(FailedObjectsWindow.CLEANUP_FAILED_OBJECT_WINDOW, onCleanupFailedObjectWindow, false);
			module.removeEventListener
				(FailedObjectsWindow.REQUEST_FAILED_OBJECT_DATA, onRequestFailedObjectData, false);

			setViewComponent(null);
		}
		
		override public function listNotificationInterests():Array
		{
			return [InstallationListModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE,
				InstallationListProxy.GET_FAILED_OBJECT_DETAILS_SUCCEEDED,
				InstallationListProxy.GET_FAILED_OBJECT_DETAILS_FAILED
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
				case InstallationListModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE:
					module.handleStartupComplete(data);
					break;
				case InstallationListProxy.GET_FAILED_OBJECT_DETAILS_SUCCEEDED:
					module.handleFailedObjectsRetreival(data);
					break;
				case InstallationListProxy.GET_FAILED_OBJECT_DETAILS_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator FailedObjectsWindowMediator');
					break;
			}
		}

	}
}