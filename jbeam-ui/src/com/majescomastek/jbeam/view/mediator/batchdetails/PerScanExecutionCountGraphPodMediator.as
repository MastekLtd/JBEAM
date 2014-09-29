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
	import com.majescomastek.jbeam.view.components.batchdetails.PerScanExecutionCountGraphPod;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationPod view.
	 */
	public class PerScanExecutionCountGraphPodMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "PER_SCAN_EXECUTION_COUNT_GRAPH_POD_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		/** The cached proxy reference */
		private var _batchDetailsProxy:BatchDetailsProxy;
		
		public function PerScanExecutionCountGraphPodMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);			
			
			module.addEventListener(PerScanExecutionCountGraphPod.FETCH_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA, 
				onFetchPerScanExecutionCountGraphData, false, 0, true);
			module.addEventListener(PerScanExecutionCountGraphPod.REFRESH_POD, onRefreshPod, false, 0, true);
		}
		
		private function onFetchPerScanExecutionCountGraphData(evt:CustomDataEvent):void
		{
			_batchDetailsProxy.getPerScanExecutionCountGraphData(BatchDetailsData(evt.eventData));
		}
			
		/**
		 * The function invoked when the REFRESH_POD event is fired.
		 */
		private function onRefreshPod(event:Event):void
		{
			var data:Object = module.getRequestDataForMediator(this.mediatorName);
			sendNotification(BatchDetailsModuleFacade.REFRESH_PER_SCAN_EXECUTION_COUNT_GRAPH_POD, data);
		}	
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():PerScanExecutionCountGraphPod
		{
			return viewComponent as PerScanExecutionCountGraphPod;				
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_batchDetailsProxy = BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRemove():void
		{
			module.removeEventListener(PerScanExecutionCountGraphPod.FETCH_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA, 
				onFetchPerScanExecutionCountGraphData, false);
			module.removeEventListener(PerScanExecutionCountGraphPod.REFRESH_POD, 
				onRefreshPod, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.PER_SCAN_EXECUTION_COUNT_GRAPH_POD_STARTUP_COMPLETE,
				BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH,
				BatchDetailsProxy.GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_SUCCEEDED,
				BatchDetailsProxy.GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED				
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
				case BatchDetailsModuleFacade.PER_SCAN_EXECUTION_COUNT_GRAPH_POD_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;				
				case BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH:
					module.handleRefreshRequest();
					break;
				case BatchDetailsProxy.GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_SUCCEEDED:
					module.handlePerScanExecutionCountGraphDataRetrieval(data);
					break;				
				case BatchDetailsProxy.GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;				
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator PerScanExecutionCountGraphPodMediator');
					break;
			}
		}
		
	}
}