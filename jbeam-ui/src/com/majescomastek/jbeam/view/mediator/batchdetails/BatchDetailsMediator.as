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
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.BatchDetailsProxy;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchDetailsModule;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class BatchDetailsMediator extends BaseMediator
	{
		private var _batchDetailsProxy:BatchDetailsProxy;
		private var _installationListProxy:InstallationListProxy;
		
		/** The name of this mediator */
		public static const NAME:String = "BATCH_DETAILS_MODULE_MEDIATOR";
		
		public function BatchDetailsMediator(view:BatchDetailsModule)
		{
			super(NAME, view);		
			view.addEventListener(BatchDetailsModule.INSTALLATION_DATA_REQUEST,
				onInstallationDataRequest, false, 0, true);
			view.addEventListener(BatchDetailsModule.BATCH_DETAILS_REQUEST,
				onBatchDetailsRequest, false, 0, true);
			view.addEventListener(BatchDetailsModule.BATCH_PODS_CREATION_COMPLETE, 
				onBatchPodsCreationComplete, false, 0, true);
			view.addEventListener(BatchDetailsModule.REQUEST_BATCH_POD_REFRESH,
				onRequestBatchPodRefresh, false, 0, true);			
			view.addEventListener(BatchDetailsModule.REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH,
				onRequestObjectExecGraphPodRefresh, false, 0, true);
			view.addEventListener(BatchDetailsModule.SEARCH_BATCH_REQUEST,
				onSearchBatchRequest, false, 0, true);
		}

		/**
		 * The function invoked when the REQUEST_BATCH_POD_REFRESH event is fired.
		 */
		private function onRequestBatchPodRefresh(event:Event):void
		{
			sendNotification(BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH);
		}
		
		/**
		 * The function invoked when the REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH event is fired.
		 */
		private function onRequestObjectExecGraphPodRefresh(event:Event):void
		{
			sendNotification(BatchDetailsModuleFacade.REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH);
		}

		private function onSearchBatchRequest(event:CustomDataEvent):void
		{
			_batchDetailsProxy.searchBatch(ReqSearchBatch(event.eventData));
		}
		
		/**
		 * The function invoked when the BATCH_DETAILS_REQUEST event is fired.
		 */
		private function onBatchDetailsRequest(evt:CustomDataEvent):void
		{
			_batchDetailsProxy.getBatchDetails(InstallationData(evt.eventData));
		}
		
		/**
		 * The function invoked when the INSTALLATION_DATA_REQUEST event is fired.
		 */
		private function onInstallationDataRequest(evt:CustomDataEvent):void
		{
			var data:Object = evt.eventData;
			var installationData:InstallationData = InstallationData(data['installationData']);
			var userProfile:UserProfile = UserProfile(data['userProfile']);
			
			_installationListProxy.getInstallationDetailsForBatch(installationData, userProfile);
		}
		
		/**
		 * The function invoked when the BATCH_PODS_CREATION_COMPLETE is fired.
		 */
		private function onBatchPodsCreationComplete(event:CustomDataEvent):void
		{
			// retrieve the list of pods created and fire a command which would in
			// turn register the mediators of the pods
			var podList:ArrayCollection = ArrayCollection(event.eventData);
			sendNotification(BatchDetailsModuleFacade.BATCH_POD_STARTUP, podList);			
					
		}
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_batchDetailsProxy = BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));
			_installationListProxy = InstallationListProxy(facade.retrieveProxy(InstallationListProxy.NAME));
		}

		/**
		 * @inheritDoc
		 */		
		override public function onRemove():void
		{
			module.removeEventListener(BatchDetailsModule.INSTALLATION_DATA_REQUEST,
				onInstallationDataRequest, false);
			module.removeEventListener(BatchDetailsModule.BATCH_DETAILS_REQUEST,
				onBatchDetailsRequest, false);
			module.removeEventListener(BatchDetailsModule.BATCH_PODS_CREATION_COMPLETE, 
				onBatchPodsCreationComplete, false);
			module.removeEventListener(BatchDetailsModule.REQUEST_BATCH_POD_REFRESH,
				onRequestBatchPodRefresh, false);			
			module.removeEventListener(BatchDetailsModule.REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH,
				onRequestObjectExecGraphPodRefresh, false);
			module.removeEventListener(BatchDetailsModule.SEARCH_BATCH_REQUEST,
				onSearchBatchRequest, false);
			setViewComponent(null);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.BATCH_POD_STARTUP_COMPLETE,
					BatchDetailsModuleFacade.OBJECT_EXECUTION_GRAPH_POD_STARTUP_COMPLETE,
					BatchDetailsModuleFacade.BATCH_DETAILS_MODULE_STARTUP_COMPLETE,
					InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED,
					InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED,
					BatchDetailsProxy.GET_BATCH_DETAILS_SERVICE_SUCCEEDED,
					BatchDetailsProxy.GET_BATCH_DETAILS_SERVICE_FAILED,
					BatchDetailsProxy.SEARCH_BATCH_DETAILS_SERVICE_SUCCEEDED,
					BatchDetailsProxy.SEARCH_BATCH_DETAILS_SERVICE_FAILED
				];
		}
		
		/**
		 * Return the reference to the view which this mediator refers to.
		 */
		private function get module():BatchDetailsModule
		{
			return BatchDetailsModule(viewComponent);
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
				case BatchDetailsModuleFacade.BATCH_POD_STARTUP_COMPLETE:
					module.handleBatchPodStartupCompletion();
					break;
				case BatchDetailsModuleFacade.OBJECT_EXECUTION_GRAPH_POD_STARTUP_COMPLETE:
					module.handleObjectExecGraphPodStartupCompletion();
					break;
				case BatchDetailsModuleFacade.BATCH_DETAILS_MODULE_STARTUP_COMPLETE:
					module.handleBatchDetailsModuleStartupComplete();
					break;
				case InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED:
					module.handleGetInstallationSuccess(data);
					break;					
				case BatchDetailsProxy.GET_BATCH_DETAILS_SERVICE_SUCCEEDED:
					module.handleBatchDetailsListRetrieval(ArrayCollection(data));
					break;
				case BatchDetailsProxy.SEARCH_BATCH_DETAILS_SERVICE_SUCCEEDED:
					module.handleSearchBatchDetailsListRetrieval(ArrayCollection(data));
					break;
				case BatchDetailsProxy.SEARCH_BATCH_DETAILS_SERVICE_FAILED:
					module.handleSearchBatchDetailsListRetrievalFailure();
					sendNotification(BatchDetailsModuleFacade.NAVIGATE_DEFAULT_VIEW_STARTUP);
					//CommonUtils.showWsFault(Fault(data));
					Alert.show(String(data));
					break;
				case InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED:
				case BatchDetailsProxy.GET_BATCH_DETAILS_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalid notification provided in BatchDetailsMediator'); 
					break;				
			}
		}

		
	}
}