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
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchSummaryPod;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationPod view.
	 */
	public class BatchSummaryPodMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "BATCH_SUMMARY_POD_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		/** The cached proxy reference */
		private var _batchDetailsProxy:BatchDetailsProxy;
		
		/** The cached ScheduleBatchProxy reference */
		private var _scheduleBatchProxy:ScheduleBatchProxy;
		
		public function BatchSummaryPodMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);			
			module.addEventListener(BatchSummaryPod.FETCH_BATCH_SUMMARY, onFetchBatchSummary, false, 0, true);
			module.addEventListener(BatchSummaryPod.STOP_CLICK,	onStopBatchClick, false, 0, true);
			module.addEventListener(BatchSummaryPod.RUN_BATCH_REQUEST,onRunBatchRequest, false, 0, true);
			module.addEventListener(BatchSummaryPod.REFRESH_BATCH_SUMMARY_POD, onRefreshPod, false, 0, true);
		}
		
		private function onFetchBatchSummary(evt:CustomDataEvent):void
		{
			_batchDetailsProxy.getBatchSummary(BatchDetailsData(evt.eventData));
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
		 * The function invoked when the REFRESH_BATCH_SUMMARY_POD event is fired.
		 */
		private function onRefreshPod(event:Event):void
		{
			var data:Object = module.getRequestDataForMediator(this.mediatorName);
			sendNotification(BatchDetailsModuleFacade.REFRESH_BATCH_SUMMARY_POD, data);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():BatchSummaryPod
		{
			return viewComponent as BatchSummaryPod;						
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_batchDetailsProxy = BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));
			_scheduleBatchProxy = ScheduleBatchProxy(facade.retrieveProxy(ScheduleBatchProxy.NAME));
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRemove():void
		{
			module.removeEventListener(BatchSummaryPod.FETCH_BATCH_SUMMARY,
				onFetchBatchSummary, false);
			module.addEventListener(BatchSummaryPod.STOP_CLICK,
				onStopBatchClick, false);
			module.addEventListener(BatchSummaryPod.RUN_BATCH_REQUEST,
				onRunBatchRequest, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.BATCH_SUMMARY_POD_STARTUP_COMPLETE,
					BatchDetailsProxy.GET_BATCH_SUMMARY_SUCCEEDED,							
					BatchDetailsProxy.GET_BATCH_SUMMARY_FAILED,
					ScheduleBatchProxy.STOP_BATCH_SUCCEEDED,
					ScheduleBatchProxy.STOP_BATCH_FAILED,
					ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED,
					ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED,
					BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED,
					BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_FAILED,
					BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH
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
				case BatchDetailsModuleFacade.BATCH_SUMMARY_POD_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;				
				case BatchDetailsProxy.GET_BATCH_SUMMARY_SUCCEEDED:
					module.handleBatchSummaryRetrieval(data);
					break;
				case ScheduleBatchProxy.STOP_BATCH_SUCCEEDED:
					module.handleStopBatchSuccess(data);
					break;
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED:
					module.handleRunBatchServiceResult(data);
					sendNotification(BatchDetailsModuleFacade.NAVIGATE_DEFAULT_VIEW_STARTUP, data);
					break;
				case BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH:
					module.handleRefreshRequest();
					break;
				case BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED:
					module.handleGetBatchSuccess(data);
					break;
				case BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_FAILED:
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED:
				case ScheduleBatchProxy.STOP_BATCH_FAILED:
				case BatchDetailsProxy.GET_BATCH_SUMMARY_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;				
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator BatchSummaryPodMediator');
					break;
			}
		}
		
	}
}