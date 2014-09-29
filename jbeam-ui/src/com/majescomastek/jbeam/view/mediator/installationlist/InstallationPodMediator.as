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
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.GraphData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationPod;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationPod view.
	 */
	public class InstallationPodMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "INSTALLATION_POD_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		/** The cached InstallationListProxy reference */
		private var _installationListProxy:InstallationListProxy;
		
		/** The cached ScheduleBatchProxy reference */
		private var _scheduleBatchProxy:ScheduleBatchProxy;
		
		public function InstallationPodMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener(InstallationPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false, 0, true);
			module.addEventListener(InstallationPod.BATCH_OBJECT_CLICK, onBatchObjectClick, false, 0, true);
			module.addEventListener(InstallationPod.REFRESH_POD, onRefreshPod, false, 0, true);
			module.addEventListener(InstallationPod.REFRESH_POD_FOR_INSTRUCTION, onRefreshPodForInstruction, false, 0, true);
			module.addEventListener(InstallationPod.GET_BATCH_DATA_REQUEST, onGetBatchDataRequest, false, 0, true);
			module.addEventListener(InstallationPod.FETCH_FAILED_OBJECT_GRAPH_DATA,
				onFetchFailedObjectGraphData, false, 0, true);
			module.addEventListener(InstallationPod.SHOW_BATCH_DETAILS_CLICK, onShowBatchDetailsClick, false, 0, true);
			module.addEventListener(InstallationPod.START_CLICK, onStartBatchClick, false, 0, true);
			module.addEventListener(InstallationPod.STOP_CLICK, onStopBatchClick, false, 0, true);
			module.addEventListener(InstallationPod.RUN_BATCH_REQUEST, onRunBatchRequest, false, 0, true);
			module.addEventListener(InstallationPod.DEFAULT_VIEW_REQUEST, onDefaultViewRequest, false, 0, true);
		}
		
		/**
		 * The function invoked when the DEFAULT_VIEW_REQUEST event is fired.
		 */
		private function onDefaultViewRequest(event:Event):void
		{
			sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW);
		}
		
		/**
		 * The function invoked when the FETCH_FAILED_OBJECT_GRAPH_DATA event is fired.
		 */
		private function onFetchFailedObjectGraphData(event:CustomDataEvent):void
		{
			_installationListProxy.getFailedObjectGraphData(GraphData(event.eventData), this.mediatorName);						
		}
		
		/**
		 * The function invoked when the REFRESH_POD event is fired.
		 */
		private function onRefreshPod(event:Event):void
		{
			var data:Object = module.getRequestDataForMediator(this.mediatorName);
			sendNotification(InstallationListModuleFacade.REFRESH_INSTALLATION_POD, data);
		}
		/**
		 * The function invoked when the REFRESH_POD_FOR_INSTRUCTION event is fired.
		 */
		private function onRefreshPodForInstruction(event:Event):void
		{
			var batchDetails:BatchDetailsData = BatchDetailsData(event.currentTarget.batchDetails);
			sendNotification(InstallationListModuleFacade.REFRESH_INSTALLATION_POD_FOR_INSTRUCTION, batchDetails);
		}
		
		/**
		 * The function invoked when the GET_BATCH_DATA_REQUEST event is fired.
		 */
		private function onGetBatchDataRequest(event:CustomDataEvent):void
		{
			_installationListProxy.getBatchData(BatchDetailsData(event.eventData));
		}
		
		/**
		 * The function invoked when the BATCH_OBJECT_CLICK event is fired.
		 */
		private function onBatchObjectClick(event:CustomDataEvent):void
		{
			sendNotification(InstallationListModuleFacade.BATCH_OBJECT_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the FAILED_OBJECT_CLICK event is fired.
		 */
		private function onFailedObjectClick(event:CustomDataEvent):void
		{
			sendNotification(InstallationListModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP, event.eventData);
		}

		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onShowBatchDetailsClick(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onStartBatchClick(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * The function invoked when the RUN_BATCH_REQUEST event is fired.
		 */
		private function onRunBatchRequest(evt:CustomDataEvent):void
		{
			_scheduleBatchProxy.runBatch(ReqInstructionLog(evt.eventData));
		}
		
		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onStopBatchClick(event:CustomDataEvent):void
		{
			_scheduleBatchProxy.stopBatch(ReqInstructionLog(event.eventData));
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():InstallationPod
		{
			return viewComponent as InstallationPod;						
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_installationListProxy = InstallationListProxy(
							facade.retrieveProxy(InstallationListProxy.NAME));
			_scheduleBatchProxy = ScheduleBatchProxy(
							facade.retrieveProxy(ScheduleBatchProxy.NAME));
			
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRemove():void
		{
			module.removeEventListener(InstallationPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false);
			module.removeEventListener(InstallationPod.BATCH_OBJECT_CLICK, onBatchObjectClick, false);
			module.removeEventListener(InstallationPod.REFRESH_POD, onRefreshPod, false);
			module.removeEventListener(InstallationPod.REFRESH_POD_FOR_INSTRUCTION, onRefreshPodForInstruction, false);
			module.removeEventListener(InstallationPod.GET_BATCH_DATA_REQUEST, onGetBatchDataRequest, false);
			module.removeEventListener(InstallationPod.FETCH_FAILED_OBJECT_GRAPH_DATA,
				onFetchFailedObjectGraphData, false);
			module.removeEventListener(InstallationPod.SHOW_BATCH_DETAILS_CLICK,
				onShowBatchDetailsClick, false);
			module.removeEventListener(InstallationPod.START_CLICK,
				onStartBatchClick, false);
			module.removeEventListener(InstallationPod.STOP_CLICK,
				onStopBatchClick, false);
			module.removeEventListener(InstallationPod.RUN_BATCH_REQUEST,
				onRunBatchRequest, false);
			module.removeEventListener(InstallationPod.DEFAULT_VIEW_REQUEST, 
				onDefaultViewRequest, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH,
				InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH_FOR_INSTRUCTION,
				InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED,
				InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED,
				InstallationListProxy.GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED,
				InstallationListProxy.GET_FAILED_OBJECT_GRAPH_DATA_FAILED,
				InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP_COMPLETE,
				InstallationListProxy.GET_INSTRUCTION_LOG_SUCCEEDED,
				InstallationListProxy.GET_INSTRUCTION_LOG_FAILED,
				InstallationListProxy.GET_BATCH_DATA_SUCCEEDED,
				InstallationListProxy.GET_BATCH_DATA_FAILED,
				ScheduleBatchProxy.STOP_BATCH_SUCCEEDED,
				ScheduleBatchProxy.STOP_BATCH_FAILED,
				ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED,
				ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED			
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
				case InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH:
					module.handleRefreshRequest();
					break;
				case InstallationListModuleFacade.REQUEST_INSTALLATION_POD_REFRESH_FOR_INSTRUCTION:
					module.handleRefreshRequestForInstruction();
					break;
				case InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED:
					module.handleGetInstallationSuccess(data);
					break;
				case InstallationListProxy.GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED:
					module.populateEntityDataProvider();
					module.populateBatchProgressBar();
					module.handleGraphDataRetrieval(data);
					break;
				case InstallationListProxy.GET_BATCH_DATA_SUCCEEDED:
					module.handleBatchDataRetrieval(data);
					break;
				case InstallationListProxy.GET_INSTRUCTION_LOG_SUCCEEDED:
					module.handleInstrictionLogRetrieval(data);
					break;
				case ScheduleBatchProxy.STOP_BATCH_SUCCEEDED:
					module.handleStopBatchSuccess(data);
					break;
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED:
					module.handleRunBatchServiceResult(data);
					break;
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED:
				case ScheduleBatchProxy.STOP_BATCH_FAILED:
				case InstallationListProxy.GET_BATCH_DATA_FAILED:
				case InstallationListProxy.GET_INSTRUCTION_LOG_FAILED:
				case InstallationListProxy.GET_FAILED_OBJECT_GRAPH_DATA_FAILED:
				case InstallationListProxy.GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator InstallationPodMediator');
					break;
			}
		}
		
	}
}