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
	import com.majescomastek.jbeam.model.vo.ProgressLevelData;
	import com.majescomastek.jbeam.view.components.installationlist.BatchObjectWindow;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the BatchObjectWindow view.
	 */
	public class BatchObjectWindowMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "BATCH_OBJECT_WINDOW_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		private var _installationListProxy:InstallationListProxy;

		/**
		 * Create a new mediator object.
		 */
		public function BatchObjectWindowMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener(BatchObjectWindow.REFRESH_CLICK, onRefreshClick, false, 0, true);
			module.addEventListener
				(BatchObjectWindow.CLEANUP_BATCH_OBJECT_WINDOW, onCleanupBatchObjectWindow, false, 0, true);
			module.addEventListener(BatchObjectWindow.REQUEST_BATCH_OBJECT_DATA,
				onRequestBatchObjectData, false, 0, true);
		}
		
		/**
		 * The function invoked when the REQUEST_BATCH_OBJECT_DATA event is fired.
		 */
		private function onRequestBatchObjectData(event:CustomDataEvent):void
		{
			_installationListProxy.getBatchObjectDetails
				(ProgressLevelData(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the REFRESH_CLICK event is fired.
		 */
		private function onRefreshClick(event:CustomDataEvent):void
		{
			_installationListProxy.getBatchObjectDetails
				(ProgressLevelData(event.eventData), this.mediatorName);
		}
		
		/**
		 * The function invoked when the CLEANUP_BATCH_OBJECT_WINDOW event is fired.
		 */
		private function onCleanupBatchObjectWindow(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():BatchObjectWindow
		{
			return viewComponent as BatchObjectWindow;						
		}
		
		override public function onRegister():void
		{
			_installationListProxy = InstallationListProxy(facade.retrieveProxy(InstallationListProxy.NAME));
		}
		
		override public function onRemove():void
		{
			module.removeEventListener(BatchObjectWindow.REFRESH_CLICK, onRefreshClick, false);
			module.removeEventListener
				(BatchObjectWindow.CLEANUP_BATCH_OBJECT_WINDOW, onCleanupBatchObjectWindow, false);
			module.removeEventListener
				(BatchObjectWindow.REQUEST_BATCH_OBJECT_DATA, onRequestBatchObjectData, false);

			setViewComponent(null);
		}
		
		override public function listNotificationInterests():Array
		{
			return [InstallationListModuleFacade.BATCH_OBJECTS_WINDOW_STARTUP_COMPLETE,
				InstallationListProxy.GET_BATCH_OBJECT_DETAILS_SUCCEEDED,
				InstallationListProxy.GET_BATCH_OBJECT_DETAILS_FAILED
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
				case InstallationListModuleFacade.BATCH_OBJECTS_WINDOW_STARTUP_COMPLETE:
					module.handleStartupComplete(data);
					break;
				case InstallationListProxy.GET_BATCH_OBJECT_DETAILS_SUCCEEDED:
					module.handleBatchObjectRetrieval(data);
					break;
				case InstallationListProxy.GET_BATCH_OBJECT_DETAILS_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator BatchObjectWindowMediator');
					break;
			}
		}
		
	}
}