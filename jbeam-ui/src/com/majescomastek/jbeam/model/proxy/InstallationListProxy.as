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
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.GraphData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.ProgressLevelData;
	import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	/**
	 * The proxy class for the Installation List module.
	 */
	public class InstallationListProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "INSTALLATION_LIST_PROXY";
		
		/** Notification constant indicates success of Installation data fetch service */
		public static const GET_INSTALLATION_DATA_SERVICE_SUCCEEDED:String = "GET_INSTALLATION_DATA_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of Installation data fetch service */
		public static const GET_INSTALLATION_DATA_SERVICE_FAILED:String = "GET_INSTALLATION_DATA_SERVICE_FAILED";
		
		/** Notification constant indicates successful retrieval of installation data for a batch */
		public static const GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED:String =
			"GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED";
		
		/** Notification constant indicates unsuccessful retrieval of installation data for a batch */
		public static const GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED:String =
			"GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED";

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

		/** Notification constant indicating the success of retrieval of batch data */
		public static const GET_BATCH_DATA_SUCCEEDED:String = 
			"GET_BATCH_DATA_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving batch data */
		public static const GET_BATCH_DATA_FAILED:String = 
			"GET_BATCH_DATA_FAILED";
		
		/** Notification constant indicating the success of retrieval of instruction log */
		public static const GET_INSTRUCTION_LOG_SUCCEEDED:String = 
			"GET_INSTRUCTION_LOG_SUCCEEDED";
		
		/** Notification constant indicating the failure when retrieving instruction log */
		public static const GET_INSTRUCTION_LOG_FAILED:String = 
			"GET_INSTRUCTION_LOG_FAILED";
		
			
		public function InstallationListProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Retrieve the list of all existing installations in our system.
		 */
		public function getInstallationData(
			userProfile:UserProfile, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getInstallationDataResultHandler, getInstallationDataFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.getInstallationData(userProfile, [responder], tokenData);
		}
		
		private function getInstallationDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_INSTALLATION_DATA_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getInstallationDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_INSTALLATION_DATA_SERVICE_FAILED, evt.fault);
		}
		
		public function getInstallationDetailsForBatch
			(installationData:InstallationData, userProfile:UserProfile,
			 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getInstallationDetailsForBatchResultHandler,
					getInstallationDetailsForBatchFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.getInstallationDetailsForBatch(
				installationData, userProfile, [responder], tokenData);
		}
		
		private function getInstallationDetailsForBatchResultHandler(evt:ResultEvent):void
		{
			// Here tokenData contains the name of the mediator which should be the one
			// receiving the notification. Passing it as the notification type helps
			// us implement filtering at the mediator level.
			sendNotification(GET_INSTALLATION_DETAILS_FOR_BATCH_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getInstallationDetailsForBatchFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_INSTALLATION_DETAILS_FOR_BATCH_FAILED,
				evt.fault, evt.token['tokenData']);
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
		 * The function used to retrieve the batch details for a given installation.
		 */
		public function getBatchObjectDetails
			(progressLevelData:ProgressLevelData, tokenData:Object):void
		{
			var responder:IResponder =
				new Responder(getBatchObjectDetailsResultHandler, getBatchObjectDetailsFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.getBatchObjectDetails(progressLevelData, [responder], tokenData);			
		}
		
		private function getBatchObjectDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_BATCH_OBJECT_DETAILS_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getBatchObjectDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_BATCH_OBJECT_DETAILS_FAILED,
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
		 * Retrieve the details for the selected batch.
		 */
		public function getBatchData(batchDetails:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getBatchDataResultHandler, getBatchDataFaultHandler);
			var delegate:BatchDetailsWsDelegate = new BatchDetailsWsDelegate();
			delegate.getBatchData(batchDetails, [responder], tokenData);
		}
		
		private function getBatchDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_BATCH_DATA_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getBatchDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_BATCH_DATA_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		/**
		 * Retrieve the details for the selected batch.
		 */
		public function getInstructionLog(batchDetails:BatchDetailsData,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getInstructionLogResultHandler, getInstructionLogFaultHandler);
			var delegate:JbeamSoapWsDelegate = new JbeamSoapWsDelegate();
			delegate.checkInstructionLog(batchDetails, [responder], tokenData);
		}
		
		private function getInstructionLogResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_INSTRUCTION_LOG_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function getInstructionLogFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_INSTRUCTION_LOG_FAILED,
				evt.fault, evt.token['tokenData']);			
		}
		
		

	}
}