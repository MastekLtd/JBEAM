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
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.BatchDetailsWsDelegate;
	import com.majescomastek.jbeam.model.delegate.JbeamSoapWsDelegate;
	import com.majescomastek.jbeam.model.delegate.SearchBatchWsDelegate;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.GraphData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
	import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class BatchDetailsProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "BATCH_DETAILS_PROXY";
		
		/** Notification constant indicates success of Batch details fetch service */
		public static const GET_BATCH_DETAILS_SERVICE_SUCCEEDED:String = "GET_BATCH_DETAILS_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of Batch details fetch service */
		public static const GET_BATCH_DETAILS_SERVICE_FAILED:String = "GET_BATCH_DETAILS_SERVICE_FAILED";
		
		
		/** Notification constant indicates successful retrieval of batch details */
		public static const GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED:String =
			"GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED";
		
		/** Notification constant indicates unsuccessful retrieval of batch details */
		public static const GET_BATCH_DETAILS_FOR_BATCH_FAILED:String =
			"GET_BATCH_DETAILS_FOR_BATCH_FAILED";

		/** Notification constant indicating the successful retrieval of failed object details */
		public static const GET_FAILED_OBJECT_DETAILS_SUCCEEDED:String =
			"GET_FAILED_OBJECT_DETAILS_SUCCEEDED";

		/** Notification constant indicating the failure retrieval of failed object details */
		public static const GET_FAILED_OBJECT_DETAILS_FAILED:String =
			"GET_FAILED_OBJECT_DETAILS_FAILED";

		/** Notification constant indicating the success of retrieval of batch object details */
		public static const GET_BATCH_OBJECT_DETAILS_SUCCEEDED:String = 
			"GET_BATCH_OBJECT_DETAILS_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving batch object details */
		public static const GET_BATCH_OBJECT_DETAILS_FAILED:String = 
			"GET_BATCH_OBJECT_DETAILS_FAILED";	
		
		/** Notification constant indicating the success of retrieval of failed object graph details */
		public static const GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED:String = 
			"GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving failed object graph details */
		public static const GET_FAILED_OBJECT_GRAPH_DATA_FAILED:String = 
			"GET_FAILED_OBJECT_GRAPH_DATA_FAILED";

		/** Notification constant indicating the success of retrieval of batch summary */
		public static const GET_BATCH_SUMMARY_SUCCEEDED:String = 
			"GET_BATCH_SUMMARY_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving batch summary */
		public static const GET_BATCH_SUMMARY_FAILED:String = 
			"GET_BATCH_SUMMARY_FAILED";

		/** Notification constant indicating the success of retrieval of system information */
		public static const GET_SYSTEM_INFORMATION_SUCCEEDED:String = 
			"GET_SYSTEM_INFORMATION_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving system information */
		public static const GET_SYSTEM_INFORMATION_FAILED:String = 
			"GET_SYSTEM_INFORMATION_FAILED";

		/** Notification constant indicating the success of retrieval of object execution graph data */
		public static const GET_OBJECT_EXECUTION_GRAPH_DATA_SUCCEEDED:String = 
			"GET_OBJECT_EXECUTION_GRAPH_DATA_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving object execution graph data */
		public static const GET_OBJECT_EXECUTION_GRAPH_DATA_FAILED:String = 
			"GET_OBJECT_EXECUTION_GRAPH_DATA_FAILED";

		/** Notification constant indicating the success of retrieval of per scan Execution count graph data */
		public static const GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_SUCCEEDED:String = 
			"GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving per scan Execution count graph data */
		public static const GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED:String = 
			"GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED";
			
		/** Notification constant indicating the success of retrieval of listener data */
		public static const GET_LISTENER_DATA_SUCCEEDED:String = 
			"GET_LISTENER_DATA_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving listener data */
		public static const GET_LISTENER_DATA_FAILED:String = 
			"GET_LISTENER_DATA_FAILED";
		
		public static const SEARCH_BATCH_DETAILS_SERVICE_SUCCEEDED:String = 
			"SEARCH_BATCH_DETAILS_SERVICE_SUCCEEDED";
		
		public static const SEARCH_BATCH_DETAILS_SERVICE_FAILED:String = 
			"SEARCH_BATCH_DETAILS_SERVICE_FAILED";
		
		public function BatchDetailsProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Retrieve the batch details 
		 */
		public function getBatchDetails(
			installationData:InstallationData, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getBatchDetailsResultHandler, getBatchDetailsFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getBatchDetails(installationData, [responder], tokenData);
		}
		
		private function getBatchDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_BATCH_DETAILS_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getBatchDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_BATCH_DETAILS_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Retrieve the batch details for revision pod
		 */
		public function getBatchDetailsForRevisionPod(
			installationData:InstallationData, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getBatchDetailsForRevisionPodResultHandler, getBatchDetailsForRevisionPodFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getBatchDetails(installationData, [responder], tokenData);
		}
		
		private function getBatchDetailsForRevisionPodResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_BATCH_DETAILS_FOR_BATCH_SUCCEEDED, evt.result);
		}
		
		private function getBatchDetailsForRevisionPodFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_BATCH_DETAILS_FOR_BATCH_FAILED, evt.fault);
		}
		
		public function getFailedObjectDetails
			(requestListenerInfo:RequestListenerInfo, tokenData:Object):void
		{
			var responder:IResponder =
				new Responder(getFailedObjectDetailsResultHandler, getFailedObjectDetailsFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.getFailedObjectDetails(requestListenerInfo, [responder], tokenData);
		}
		
		private function getFailedObjectDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_FAILED_OBJECT_DETAILS_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getFailedObjectDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_FAILED_OBJECT_DETAILS_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the graph data for the failed object pie chart.
		 */
		public function getFailedObjectGraphData(graphData:GraphData, tokenData:Object):void
		{
			var responder:IResponder =
				new Responder(getFailedObjectGraphDataResultHandler, getFailedObjectGraphDataFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.getGraphData(graphData, [responder], tokenData);
		}
		
		private function getFailedObjectGraphDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_FAILED_OBJECT_GRAPH_DATA_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getFailedObjectGraphDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_FAILED_OBJECT_GRAPH_DATA_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the summary for the selected batch.
		 */
		public function getBatchSummary(batchDetailsData:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getBatchSummaryResultHandler, getBatchSummaryFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getBatchSummary(batchDetailsData, [responder], tokenData);
		}
		
		private function getBatchSummaryResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_BATCH_SUMMARY_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getBatchSummaryFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_BATCH_SUMMARY_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the system information for the selected batch.
		 */
		public function getSystemInformation(batchDetailsData:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getSystemInformationResultHandler, getSystemInformationFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getSystemInformation(batchDetailsData, [responder], tokenData);
		}
		
		private function getSystemInformationResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_SYSTEM_INFORMATION_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getSystemInformationFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_SYSTEM_INFORMATION_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the object execution graph data for the selected batch.
		 */
		public function getObjectExecutionGraphData(batchDetailsData:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getObjectExecutionGraphDataResultHandler, getObjectExecutionGraphDataFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getObjectExecutionGraphData(batchDetailsData, [responder], tokenData);
		}
		
		private function getObjectExecutionGraphDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_OBJECT_EXECUTION_GRAPH_DATA_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getObjectExecutionGraphDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_OBJECT_EXECUTION_GRAPH_DATA_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the per scan Execution count graph data for the selected batch.
		 */
		public function getPerScanExecutionCountGraphData(batchDetailsData:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getPerScanExecutionCountGraphDataResultHandler, getPerScanExecutionCountGraphDataFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getPerScanExecutionCountGraphData(batchDetailsData, [responder], tokenData);
		}
		
		private function getPerScanExecutionCountGraphDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getPerScanExecutionCountGraphDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the listener data for the selected batch.
		 */
		public function getListenerData(batchDetailsData:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getListenerDataResultHandler, getListenerDataFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getListenerData(batchDetailsData, [responder], tokenData);
		}
		
		private function getListenerDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_LISTENER_DATA_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getListenerDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_LISTENER_DATA_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		public function searchBatch(reqSearchBatch:ReqSearchBatch,
									 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(searchBatchDetailsResultHandler, searchBatchDetailsFaultHandler);
			var delegate:SearchBatchWsDelegate = new SearchBatchWsDelegate();
			delegate.searchBatch(reqSearchBatch, [responder], tokenData);
		}
		
		private function searchBatchDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(SEARCH_BATCH_DETAILS_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function searchBatchDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(SEARCH_BATCH_DETAILS_SERVICE_FAILED, evt.token.result.description);
		}
	}
}