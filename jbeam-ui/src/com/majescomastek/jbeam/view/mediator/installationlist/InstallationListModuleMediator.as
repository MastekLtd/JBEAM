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
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationListModule;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationListModule view.
	 */
	public class InstallationListModuleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "INSTALLATION_LIST_MODULE_MEDIATOR";
		
		private var _installationListProxy:InstallationListProxy;
		
		public function InstallationListModuleMediator(view:InstallationListModule)
		{
			super(NAME, view);		
			
			view.addEventListener(InstallationListModule.INSTALLATION_LIST_REQUEST,
				onInstallationListRequest, false, 0, true);
			view.addEventListener(InstallationListModule.PODS_CREATION_COMPLETE, 
				onPodsCreationComplete, false, 0, true);
			view.addEventListener(InstallationListModule.REQUEST_POD_REFRESH,
				onRequestPodRefresh, false, 0, true);			
			view.addEventListener(InstallationListModule.REQUEST_POD_REFRESH_FOR_INSTRUCTION,
				onRequestPodRefreshForInstruction, false, 0, true);
			view.addEventListener(InstallationListModule.INSTALLATION_LIST_VIEW_REQUEST,
				onSwitchInstallationsList, false, 0, true);
		}

		/**
		 * The function invoked when the REQUEST_POD_REFRESH event is fired.
		 */
		private function onRequestPodRefresh(event:Event):void
		{
			sendNotification(InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH);
		}
		
		/**
		 * The function invoked when the REQUEST_POD_REFRESH_FOR_INSTRUCTION event is fired.
		 */
		private function onRequestPodRefreshForInstruction(event:Event):void
		{
			sendNotification(InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH_FOR_INSTRUCTION);
		}
		
		/**
		 * The function invoked when the PODS_CREATION_COMPLETE is fired.
		 */
		private function onPodsCreationComplete(event:CustomDataEvent):void
		{
			// retrieve the list of pods created and fire a command which would in
			// turn register the mediators of the pods
			var podList:ArrayCollection = ArrayCollection(event.eventData);
			sendNotification(InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP, podList);			
		}
		
		
		
		/**
		 * The function invoked when the INSTALLATION_LIST_REQUEST is fired.
		 */		
		private function onInstallationListRequest(event:CustomDataEvent):void
		{
			_installationListProxy.getInstallationData(UserProfile(event.eventData));			
		}

		/**
		 * The function invoked when the INSTALLATION_LIST_VIEW_REQUEST is fired.
		 */
		private function onSwitchInstallationsList(event:CustomDataEvent):void
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
			module.removeEventListener(InstallationListModule.INSTALLATION_LIST_REQUEST,
				onInstallationListRequest, false);
			module.removeEventListener(InstallationListModule.PODS_CREATION_COMPLETE, 
				onPodsCreationComplete, false);
			module.removeEventListener(InstallationListModule.REQUEST_POD_REFRESH,
				onRequestPodRefresh, false);
			module.removeEventListener(InstallationListModule.REQUEST_POD_REFRESH_FOR_INSTRUCTION,
				onRequestPodRefreshForInstruction, false);
			module.removeEventListener(InstallationListModule.INSTALLATION_LIST_VIEW_REQUEST,
				onSwitchInstallationsList, false);
			setViewComponent(null);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [InstallationListModuleFacade.INSTALLATION_LIST_MODULE_STARTUP_COMPLETE,
//				InstallationListModuleFacade.RUN_BATCH_INSTRUCTION_STARTUP_COMPLETE,
				InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED,
				InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_FAILED,
				InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP_COMPLETE
			];
		}
		
		/**
		 * Return the reference to the view which this mediator refers to.
		 */
		private function get module():InstallationListModule
		{
			return InstallationListModule(viewComponent);
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
				case InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP_COMPLETE:
					module.handlePodStartupCompletion();
					break;
				case InstallationListModuleFacade.INSTALLATION_LIST_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED:
					module.handleInstallationListRetrieval(ArrayCollection(data));
					break;
				case InstallationListProxy.GET_INSTALLATION_DATA_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalid notification provided in InstallationListModuleMediator'); 
					break;				
			}
		}

	}
}