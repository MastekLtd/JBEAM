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
	import com.majescomastek.jbeam.model.vo.GraphData;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchRevisionPod;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationPod view.
	 */
	public class BatchRevisionPodMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "BATCH_REVISION_POD_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		/** The cached proxy reference */
		private var _batchDetailsProxy:BatchDetailsProxy;
		
		public function BatchRevisionPodMediator(viewComponent:Object=null)
		{
			super(NAME + "_" + COUNTER++, viewComponent);
			
			module.addEventListener(BatchRevisionPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false, 0, true);
			module.addEventListener(BatchRevisionPod.LISTENER_CLICK, onListenerDataClick, false, 0, true);
			module.addEventListener(BatchRevisionPod.BATCH_OBJECT_CLICK, onBatchObjectClick, false, 0, true);
			module.addEventListener(BatchRevisionPod.REFRESH_POD, onRefreshPod, false, 0, true);
			module.addEventListener(BatchRevisionPod.FETCH_FAILED_OBJECT_GRAPH_DATA,
				onFetchFailedObjectGraphData, false, 0, true);
		}
		
		/**
		 * The function invoked when the FETCH_FAILED_OBJECT_GRAPH_DATA event is fired.
		 */
		private function onFetchFailedObjectGraphData(event:CustomDataEvent):void
		{
			_batchDetailsProxy.getFailedObjectGraphData(GraphData(event.eventData), this.mediatorName);						
		}
		
		/**
		 * The function invoked when the REFRESH_POD event is fired.
		 */
		private function onRefreshPod(event:Event):void
		{
			var data:Object = module.getRequestDataForMediator(this.mediatorName);
			sendNotification(BatchDetailsModuleFacade.REFRESH_BATCH_POD, data);
		}
		
		/**
		 * The function invoked when the BATCH_OBJECT_CLICK event is fired.
		 */
		private function onBatchObjectClick(event:CustomDataEvent):void
		{
			sendNotification(BatchDetailsModuleFacade.BATCH_OBJECT_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the FAILED_OBJECT_CLICK event is fired.
		 */
		private function onFailedObjectClick(event:CustomDataEvent):void
		{
			sendNotification(BatchDetailsModuleFacade.FAILED_OBJECTS_WINDOW_STARTUP, event.eventData);
		}
		
		/**
		 * The function invoked when the LISTENER_CLICK event is fired.
		 */
		private function onListenerDataClick(event:CustomDataEvent):void
		{
			sendNotification(BatchDetailsModuleFacade.LISTENER_WINDOW_STARTUP, event.eventData);
		}

		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():BatchRevisionPod
		{
			return viewComponent as BatchRevisionPod;						
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
			module.removeEventListener(BatchRevisionPod.FAILED_OBJECT_CLICK, onFailedObjectClick, false);
			module.removeEventListener(BatchRevisionPod.LISTENER_CLICK, onListenerDataClick, false);
			module.removeEventListener(BatchRevisionPod.BATCH_OBJECT_CLICK, onBatchObjectClick, false);
			module.removeEventListener(BatchRevisionPod.REFRESH_POD, onRefreshPod, false);
			module.removeEventListener(BatchRevisionPod.FETCH_FAILED_OBJECT_GRAPH_DATA,
				onFetchFailedObjectGraphData, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.BATCH_POD_STARTUP_COMPLETE,
				BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH,
				BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED,
				BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_FAILED,
				BatchDetailsProxy.GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED,
				BatchDetailsProxy.GET_FAILED_OBJECT_GRAPH_DATA_FAILED
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
				case BatchDetailsModuleFacade.BATCH_POD_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case BatchDetailsModuleFacade.REQUEST_BATCH_POD_REFRESH:
					module.handleRefreshRequest();
					break;
				case BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED:
					module.handleGetBatchSuccess(data);
					break;
				case BatchDetailsProxy.GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED:
					module.populateEntityDataProvider();
					module.populateBatchProgressBar();
					module.handleGraphDataRetrieval(data);
					break;
				case BatchDetailsProxy.GET_FAILED_OBJECT_GRAPH_DATA_FAILED:
				case BatchDetailsProxy.GET_BATCH_DETAILS_FOR_BATCH_FAILED:
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