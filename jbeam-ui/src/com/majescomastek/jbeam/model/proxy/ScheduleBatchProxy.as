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
	import com.majescomastek.jbeam.model.delegate.JbeamSoapWsDelegate;
	import com.majescomastek.jbeam.model.delegate.ScheduleBatchWsDelegate;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class ScheduleBatchProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "SCHEDULE_BATCH_PROXY";
		
		/** Notification constant indicates success of run batch service */
		public static const RUN_BATCH_SERVICE_SUCCEEDED:String = "RUN_BATCH_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of run batch service */
		public static const RUN_BATCH_SERVICE_FAILED:String = "RUN_BATCH_SERVICE_FAILED";
			
		/** Notification constant indicating the success of request to stop batch */
		public static const STOP_BATCH_SUCCEEDED:String = 
			"STOP_BATCH_SUCCEEDED";
		
		/** Notification constant indicating the failure when requested to stop batch */
		public static const STOP_BATCH_FAILED:String = 
			"STOP_BATCH_FAILED";
		
		public static const GET_INSTALLATION_DATA_SERVICE_SUCCEEDED:String = 
			"GET_INSTALLATION_DATA_SERVICE_SUCCEEDED";
		
		public static const GET_INSTALLATION_DATA_SERVICE_FAILED:String = 
			"GET_INSTALLATION_DATA_SERVICE_FAILED";
		
		public function ScheduleBatchProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to run batch. 
		 */
		public function runBatch(
			reqInstructionLog:ReqInstructionLog, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(runBatchResultHandler, runBatchFaultHandler);
			var delegate:ScheduleBatchWsDelegate = new ScheduleBatchWsDelegate();
			delegate.runBatch(reqInstructionLog, [responder], tokenData);
		}
		
		private function runBatchResultHandler(evt:ResultEvent):void
		{
			sendNotification(RUN_BATCH_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function runBatchFaultHandler(evt:FaultEvent):void
		{
			sendNotification(RUN_BATCH_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Stop the selected batch.
		 */
		public function stopBatch(reqInstructionLog:ReqInstructionLog,
				 tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(stopBatchResultHandler, stopBatchFaultHandler);
			var delegate:ScheduleBatchWsDelegate = new ScheduleBatchWsDelegate();
			delegate.stopBatch(reqInstructionLog, [responder], tokenData);
		}
		
		private function stopBatchResultHandler(evt:ResultEvent):void
		{
			sendNotification(STOP_BATCH_SUCCEEDED,
				evt.result, evt.token['tokenData']);
		}
		
		private function stopBatchFaultHandler(evt:FaultEvent):void
		{
			sendNotification(STOP_BATCH_FAILED,
				evt.fault, evt.token['tokenData']);			
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
		
	}
}