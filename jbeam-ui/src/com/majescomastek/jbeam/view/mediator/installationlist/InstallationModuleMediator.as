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
	import com.majescomastek.jbeam.facade.installationlist.InstallationModuleFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationModule;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationModule view.
	 */
	public class InstallationModuleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "INSTALLATION_MODULE_MEDIATOR";
		
		private var _installationListProxy:InstallationListProxy;
		
		public function InstallationModuleMediator(view:InstallationModule)
		{
			super(NAME, view);		
			
			view.addEventListener(InstallationModule.INSTALLATION_REQUEST,
				onInstallationRequest, false, 0, true);
			view.addEventListener(InstallationModule.REQUEST_LIST_REFRESH ,
				onRequestListRefresh, false, 0, true);			
			view.addEventListener(InstallationModule.FAILED_OBJECT_CLICK, 
				onFailedObjectClick, false, 0, true);
			view.addEventListener(InstallationModule.SHOW_BATCH_DETAILS_CLICK, 
				onShowBatchDetailsClick, false, 0, true);
			view.addEventListener(InstallationModule.INSTALLATION_PODS_VIEW_REQUEST, 
				onSwitchInstallationPods, false, 0, true);
		}

		/**
		 * The function invoked when the REQUEST_LIST_REFRESH event is fired.
		 */
		private function onRequestListRefresh(event:Event):void
		{
			sendNotification(InstallationModuleFacade.REQUEST_INSTALLATION_REFRESH);
		}
		
		/**
		 * The function invoked when the INSTALLATION_PODS_VIEW_REQUEST is fired.
		 */
		private function onSwitchInstallationPods(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);		
		}
		
		/**
		 * The function invoked when the INSTALLATION_REQUEST is fired.
		 */		
		private function onInstallationRequest(event:CustomDataEvent):void
		{
			_installationListProxy.getInstallationData(UserProfile(event.eventData));			
		}

		/**
		 * The function invoked when the FAILED_OBJECT_CLICK event is fired.
		 */
		private function onFailedObjectClick(event:CustomDataEvent):void
		{
			sendNotification(InstallationModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onShowBatchDetailsClick(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_installationListProxy = InstallationListProxy(facade.retrieveProxy(InstallationListProxy.NAME));			
		}

		/**
		 * @inheritDoc
		 */		
		override public function onRemove():void
		{
			module.removeEventListener(InstallationModule.INSTALLATION_REQUEST,
				onInstallationRequest, false);
			module.removeEventListener(InstallationModule.REQUEST_LIST_REFRESH,
				onRequestListRefresh, false);
			module.removeEventListener(InstallationModule.FAILED_OBJECT_CLICK, 
				onFailedObjectClick, false);
			module.removeEventListener(InstallationModule.SHOW_BATCH_DETAILS_CLICK, 
				onShowBatchDetailsClick, false);
			module.removeEventListener(InstallationModule.INSTALLATION_PODS_VIEW_REQUEST, 
				onSwitchInstallationPods, false);
			setViewComponent(null);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [InstallationModuleFacade.INSTALLATION_MODULE_STARTUP_COMPLETE,
				InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED,
				InstallationModuleFacade.REQUEST_INSTALLATION_REFRESH,
				InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_FAILED
			];
		}
		
		/**
		 * Return the reference to the view which this mediator refers to.
		 */
		private function get module():InstallationModule
		{
			return InstallationModule(viewComponent);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var name:String = notification.getName();
			switch(name)
			{
				case InstallationModuleFacade.INSTALLATION_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case InstallationModuleFacade.REQUEST_INSTALLATION_REFRESH:
					module.handleRefreshRequest();
					break;
				case InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED:
					module.handleInstallationRetrieval(ArrayCollection(data));
					break;
				case InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalid notification provided in InstallationModuleMediator'); 
					break;				
			}
		}

	}
}