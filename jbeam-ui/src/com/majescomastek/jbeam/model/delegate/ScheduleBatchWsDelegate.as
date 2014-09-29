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
package com.majescomastek.jbeam.model.delegate
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.InstructionParameter;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The delegate class for the Schedule batch module used for invoking
	 * SOAP based webservices..
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class ScheduleBatchWsDelegate extends BaseSoapWsDelegate
	{
		public function ScheduleBatchWsDelegate()
		{
			super();
		}
		
		/**
		 * Run / schedule a batch for a given instructions
		 */
		public function runBatch(reqInstructionLog:ReqInstructionLog,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(runBatchResultHandler, defaultFaultHandler);
			invoke("runBatch", getInputForRunBatch(reqInstructionLog),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRunBatch(reqInstructionLog:ReqInstructionLog):XML
		{
			var parentXml:XML = 
					<s:runBatch xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
				         <reqInstructionLog>
				            <installationCode>{reqInstructionLog.installationCode}</installationCode>
				            <instructingUser>{reqInstructionLog.instructingUser}</instructingUser>
				            <instructionTime>{reqInstructionLog.instructionTime}</instructionTime>				            
				            <message>{reqInstructionLog.message}</message>
				            <entityValues>{reqInstructionLog.entityValues}</entityValues>
				         </reqInstructionLog>
					</s:runBatch>;
					
					for each(var instParams:InstructionParameter in reqInstructionLog.instructionParameters)
					{
						parentXml.reqInstructionLog.appendChild(createInstructionParameterList(instParams));
					} 
			return parentXml;
		}
		
		private function createInstructionParameterList(instParams:InstructionParameter):XML
		{
			var childXml:XML = 
				<instructionParameters>
	            	<name>{instParams.name}</name>
	               	<slNo>{instParams.slNo}</slNo>
	               	<type>{instParams.type}</type>
	               	<value>{instParams.value}</value>
	            </instructionParameters>;			
			return childXml;
		}
		
		private function runBatchResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.RUN_BATCH_REQUEST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedRunBatchRequestData:Object = evt.result.batchDetails;
				retrievedRunBatchRequestData['responseTime'] = evt.result.responseTime;
				retrievedRunBatchRequestData['scheduledBatch'] = evt.result.scheduledBatch;
				retrievedRunBatchRequestData['revisionBatch'] = evt.result.revisionBatch;
				var runBatchRequestData:BatchDetailsData = null;
				if(retrievedRunBatchRequestData != null)
				{
					runBatchRequestData = createRunBatchRequestData(retrievedRunBatchRequestData);
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(runBatchRequestData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a run batch request object based on the data returned by the webservice.
		 */
		private function createRunBatchRequestData(retrievedRunBatchRequestData:Object):BatchDetailsData
		{
			var runBatchRequestData:BatchDetailsData = new BatchDetailsData();
			runBatchRequestData.installationCode = retrievedRunBatchRequestData['installationCode'];
			runBatchRequestData.instructionSeqNo = retrievedRunBatchRequestData['instructionSeqNo'];			
			runBatchRequestData.responseTime = retrievedRunBatchRequestData['responseTime'];			
			runBatchRequestData.isScheduledBatch = retrievedRunBatchRequestData['scheduledBatch'];			
			runBatchRequestData.isRevisionBatch = retrievedRunBatchRequestData['revisionBatch'];			
			return runBatchRequestData;
		}
		
		/**
		 * Stop a batch for a given instructions
		 */
		public function stopBatch(reqInstructionLog:ReqInstructionLog,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(stopBatchResultHandler, defaultFaultHandler);
			invoke("stopBatch", getInputForStopBatch(reqInstructionLog),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForStopBatch(reqInstructionLog:ReqInstructionLog):XML
		{
			var parentXml:XML = 
					<s:stopBatch xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
				         <reqInstructionLog>
				            <installationCode>{reqInstructionLog.installationCode}</installationCode>
				            <instructingUser>{reqInstructionLog.instructingUser}</instructingUser>
				            <instructionTime>{reqInstructionLog.instructionTime}</instructionTime>				            
				            <message>{reqInstructionLog.message}</message>				         
				            <batchNo>{reqInstructionLog.batchNo}</batchNo>
		                	<batchRevNo>{reqInstructionLog.batchRevNo}</batchRevNo>
				         </reqInstructionLog>				         
					</s:stopBatch>;
			return parentXml;
		}
		
				
		private function stopBatchResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.STOP_BATCH_REQUEST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedStopBatchRequestData:Object = evt.result.installationData;
				retrievedStopBatchRequestData['responseTime'] = evt.result.responseTime;
				var stopBatchRequestData:InstallationData = null;
				if(retrievedStopBatchRequestData != null)
				{
					stopBatchRequestData = createInstallationData(retrievedStopBatchRequestData);
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(stopBatchRequestData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a installation data object based on the data returned by the webservice.
		 */
		private function createInstallationData(
						retrievedStopBatchRequestData:Object):InstallationData
		{
			var installationData:InstallationData = new InstallationData();
			installationData.installationCode = retrievedStopBatchRequestData['instCode'];
			installationData.responseTime = retrievedStopBatchRequestData['responseTime'];			
			return installationData;
		}
		
	}
}