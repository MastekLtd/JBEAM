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
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.BatchSummaryData;
	import com.majescomastek.jbeam.model.vo.Entity;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.InstructionParameter;
	import com.majescomastek.jbeam.model.vo.ListenerData;
	import com.majescomastek.jbeam.model.vo.ObjectExecutionGraphData;
	import com.majescomastek.jbeam.model.vo.PerScanExecutionCountGraphData;
	import com.majescomastek.jbeam.model.vo.ProgressLevelData;
	import com.majescomastek.jbeam.model.vo.SystemInformation;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	/**
	 * The delegate class for the Jbeam application used for invoking
	 * SOAP based webservices for batch details module.
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class BatchDetailsWsDelegate extends BaseSoapWsDelegate
	{
		public function BatchDetailsWsDelegate()
		{
			super();
		}
		
		/**
		 * Retrieve the BatchDetail list for a given installation.
		 */
		public function getBatchDetails
			(installationData:InstallationData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getBatchDetailsResultHandler, defaultFaultHandler);
			invoke("getBatchInfo", getInputForGetBatchDetails(installationData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetBatchDetails(installationData:InstallationData):XML
		{
			var xml:XML = 
					<s:getBatchInfo xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{installationData.batchNo}</batchNo>
            				<batchRevNo>{installationData.batchRevNo}</batchRevNo>
            				<installationCode>{installationData.installationCode}</installationCode>
         				</batch>
					</s:getBatchInfo>;
			return xml;
		}
		
		private function getBatchDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_BATCH_DETAILS_LIST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedBatchDetailList:ArrayCollection = evt.result.batchDataList;
				var batchDetailsList:ArrayCollection = new ArrayCollection();
				if(retrievedBatchDetailList != null && retrievedBatchDetailList.length > 0)
				{
					for(var i:uint = 0; i < retrievedBatchDetailList.length; ++i)
					{
						var retrievedBatchDetails:Object = retrievedBatchDetailList.getItemAt(i);
						var batchDetailsData:BatchDetailsData = createBatchDetailsData(retrievedBatchDetails);
						var retrievedProgressLevelDataList:ArrayCollection =
							retrievedBatchDetails.progressLevelDataList;
						for(var j:uint = 0; j < retrievedProgressLevelDataList.length; ++j)
						{
							var retrievedProgressLevelData:ObjectProxy = retrievedProgressLevelDataList[j];
							var progressLevelData:ProgressLevelData =
								createProgessLevelData(retrievedProgressLevelData);
							progressLevelData.serialNo = j + 1;
							batchDetailsData.progressLevelDataList.addItem(progressLevelData);
						}
						
						var retrievedEntityList:ArrayCollection = retrievedBatchDetails.entityList;
						for(var k:uint = 0; k < retrievedEntityList.length; ++k)
						{
							var retrievedEntity:ObjectProxy = retrievedEntityList[k];
							var entityData:Entity =	createEntity(retrievedEntity);
							batchDetailsData.entityList.addItem(entityData);
						}
						
						// Add logic for setting the batch status
						if(batchDetailsData.progressLevelDataList.length > 0)
						{
							batchDetailsData.batchStatus = ProgressLevelData(batchDetailsData.
								progressLevelDataList.getItemAt(0)).prgActivityType;
							batchDetailsData.installationCode = ProgressLevelData(batchDetailsData.
								progressLevelDataList.getItemAt(0)).installationCode; 
						}
						
						batchDetailsList.addItem(batchDetailsData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(batchDetailsList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a batch details data object based on the data returned by the webservice.
		 */
		private function createBatchDetailsData(retrievedBatchDetails:Object):BatchDetailsData
		{
			var batchDetailsData:BatchDetailsData = new BatchDetailsData();
			batchDetailsData.batchNo = retrievedBatchDetails['batchNo'];
			batchDetailsData.batchRevNo = retrievedBatchDetails['batchRevNo'];
			batchDetailsData.batchTimeDiff = retrievedBatchDetails['batchTimeDiff'];
			if(retrievedBatchDetails['execEndTime'] != undefined)
			{
				batchDetailsData.execEndTime = new Date(retrievedBatchDetails['execEndTime']);
			}
			batchDetailsData.execStartTime = new Date(retrievedBatchDetails['execStartTime']);
			batchDetailsData.noOfListners = retrievedBatchDetails['noOfListners'];
			batchDetailsData.noOfObjects = retrievedBatchDetails['noOfObjects'];
			batchDetailsData.noOfObjectsFailed = retrievedBatchDetails['noOfObjectsFailed'];			
			batchDetailsData.batchEndReason = retrievedBatchDetails['batchEndReason'];			
			return batchDetailsData;
		}
		
		private function createEntity(retrievedEntity:ObjectProxy):Entity
		{
			var entityData:Entity = new Entity();
			entityData.entityName = retrievedEntity['entity'];
			entityData.lookupColumn = retrievedEntity['lookupColumn'];			
			entityData.lookupValue = retrievedEntity['lookupValue'];			
			entityData.precedenceOrder = retrievedEntity['precedenceOrder'];			
			entityData.valueColumn = retrievedEntity['valueColumn'];			
			return entityData;
		}
		
		/**
		 * Create a progress level data object based on the data returned by the webservice.
		 */
		private function createProgessLevelData(retrievedProgressLevelData:ObjectProxy):ProgressLevelData
		{
			var progressLevelData:ProgressLevelData = new ProgressLevelData();
			progressLevelData.batchNo = retrievedProgressLevelData['batchNo'];
			progressLevelData.batchRevNo = retrievedProgressLevelData['batchRevNo'];
			if(retrievedProgressLevelData['endDatetime'] != undefined)
			{
				progressLevelData.endDatetime = CommonUtils.formatDate(new Date(retrievedProgressLevelData['endDatetime']));
				progressLevelData.timeTaken = (Number(retrievedProgressLevelData['endDatetime']) 
											- Number(retrievedProgressLevelData['startDatetime']))/ 1000;
			}
			else
			{
				progressLevelData.endDatetime = "----";
			}
			if(isNaN(progressLevelData.timeTaken))	progressLevelData.timeTaken = 0;
			
			progressLevelData.failedOver = retrievedProgressLevelData['failedOver'];
			progressLevelData.indicatorNo = retrievedProgressLevelData['indicatorNo'];
			progressLevelData.installationCode = retrievedProgressLevelData['installationCode'];
			progressLevelData.prgActivityType = retrievedProgressLevelData['prgActivityType'];
			progressLevelData.prgLevelType = retrievedProgressLevelData['prgLevelType'];
			progressLevelData.startDatetime = CommonUtils.formatDate(new Date(retrievedProgressLevelData['startDatetime']));
//			progressLevelData.status = retrievedProgressLevelData['status'];
			
			progressLevelData.cycleNo = retrievedProgressLevelData['cycleNo'];
			progressLevelData.installationCode = retrievedProgressLevelData['installationCode'];
			return progressLevelData;
		}
		
		
		
		/**
		 * Retrieve the summary for a given batch
		 */
		public function getBatchSummary
			(batchDetailsData:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getBatchSummaryResultHandler, defaultFaultHandler);
			invoke("getBatchData", getInputForGetBatchSummary(batchDetailsData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetBatchSummary(batchDetailsData:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getBatchData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{batchDetailsData.batchNo}</batchNo>
            				<batchRevNo>1</batchRevNo>
            				<installationCode>{batchDetailsData.installationCode}</installationCode>
         				</batch>
					</s:getBatchData>;
			return xml;
		}
		
		private function getBatchSummaryResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_BATCH_SUMMARY_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedBatchSummary:Object = evt.result.batchDetails;
				var batchSummaryData:BatchSummaryData = null;
				if(retrievedBatchSummary != null)
				{
					batchSummaryData = createBatchSummaryData(retrievedBatchSummary);
				}
				
				var retrievedBatchParametersList:ArrayCollection = evt.result.instructionParametersList;
				var instructionParameterList:ArrayCollection = new ArrayCollection();
				if(retrievedBatchParametersList != null && retrievedBatchParametersList.length > 0)
				{
					for(var i:uint = 0; i < retrievedBatchParametersList.length; ++i)
					{
						var retrievedBatchParameters:Object = retrievedBatchParametersList.getItemAt(i);
						var instructionParameter:InstructionParameter = 
								createInstructionParameter(retrievedBatchParameters);
											
						instructionParameterList.addItem(instructionParameter);
					}
					if(instructionParameterList.length > 0 && batchSummaryData != null)
					{
						batchSummaryData.instructionParametersList = instructionParameterList;
					}
				}
				
				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(batchSummaryData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a batch data object based on the data returned by the webservice.
		 */
		private function createBatchSummaryData(retrievedBatchSummary:Object):BatchSummaryData
		{
			var batchSummaryData:BatchSummaryData = new BatchSummaryData();
			batchSummaryData.batchNo = retrievedBatchSummary['batchNo'];
			batchSummaryData.batchRevNo = retrievedBatchSummary['batchRevNo'];
			batchSummaryData.batchName = retrievedBatchSummary['batchName'];
			batchSummaryData.execStartTime = new Date(retrievedBatchSummary['execStartTime']);
			batchSummaryData.execEndTime = new Date(retrievedBatchSummary['execEndTime']);
			batchSummaryData.batchType = retrievedBatchSummary['batchType'];
			batchSummaryData.processId = retrievedBatchSummary['processId'];
			batchSummaryData.failedOver = retrievedBatchSummary['failedOver'];			
			batchSummaryData.batchEndReason = retrievedBatchSummary['batchEndReason'];			
			return batchSummaryData;
		}
		
		/**
		 * Create a batch data object based on the data returned by the webservice.
		 */
		private function createInstructionParameter(retrievedBatchParameters:Object):InstructionParameter
		{
			var instructionParameter:InstructionParameter = new InstructionParameter();
			instructionParameter.slNo = retrievedBatchParameters['slNo'];
			instructionParameter.name = retrievedBatchParameters['name'];
			instructionParameter.value = retrievedBatchParameters['value'];
			return instructionParameter;
		}
		
		/**
		 * Retrieve the data for a given batch with instruction sequence number
		 */
		public function getBatchData
			(batchDetails:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getBatchSummaryResultHandler, defaultFaultHandler);
			invoke("getBatchData", getInputForGetBatchData(batchDetails),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetBatchData(batchDetails:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getBatchData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>            				
            				<installationCode>{batchDetails.installationCode}</installationCode>
            				<instructionSeqNo>{batchDetails.instructionSeqNo}</instructionSeqNo>
         				</batch>
					</s:getBatchData>;
			return xml;
		}
		
		
		
		/**
		 * Retrieve the system information for a given batch
		 */
		public function getSystemInformation
			(batchDetailsData:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getSystemInformationResultHandler, defaultFaultHandler);
			invoke("getSystemInfo", getInputForGetSystemInformation(batchDetailsData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetSystemInformation(batchDetailsData:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getSystemInfo xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{batchDetailsData.batchNo}</batchNo>
            				<batchRevNo>{batchDetailsData.batchRevNo}</batchRevNo>
            				<installationCode>{batchDetailsData.installationCode}</installationCode>
         				</batch>
					</s:getSystemInfo>;
			return xml;
		}
		
		private function getSystemInformationResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_SYSTEM_INFORMATION_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedSystemInformation:Object = evt.result.systemDetails;
				if(retrievedSystemInformation != null)
				{
					var systemInformation:SystemInformation = createSystemInformationData(retrievedSystemInformation);
					// Create a new event containing the webservice result and invoke the
					// external responders attached using the `defaultResultHandler` method
					var newEvt:ResultEvent = ResultEvent.createEvent(systemInformation, evt.token, evt.message);
					super.defaultResultHandler(newEvt);
				}
			}
		}
		
		/**
		 * Create a system information data object based on the data returned by the webservice.
		 */
		private function createSystemInformationData(retrievedSystemInformation:Object):SystemInformation
		{
			var systemInformation:SystemInformation = new SystemInformation();
			systemInformation.batchNo = retrievedSystemInformation['batchNo'];
			systemInformation.batchRevNo = retrievedSystemInformation['batchRevNo'];
			systemInformation.javaVersion = retrievedSystemInformation['javaVersion'];
			systemInformation.preVersion = retrievedSystemInformation['preVersion'];
			systemInformation.osConfig = retrievedSystemInformation['osConfig'];
			return systemInformation;
		}
		
		/**
		 * Retrieve the object execution graph data for a given batch
		 */
		public function getObjectExecutionGraphData
			(batchDetailsData:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getObjectExecutionGraphDataResultHandler, defaultFaultHandler);
			invoke("getGraphData", getInputForGetObjectExecutionGraphData(batchDetailsData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetObjectExecutionGraphData(batchDetailsData:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getGraphData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{batchDetailsData.batchNo}</batchNo>
            				<batchRevNo>{batchDetailsData.batchRevNo}</batchRevNo>
            				<graphId>GraphPlotter</graphId>
            				<installationCode>{batchDetailsData.installationCode}</installationCode>
         				</batch>
					</s:getGraphData>;
			return xml;
		}
		
		private function getObjectExecutionGraphDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_OBJECT_EXECUTION_GRAPH_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedObjectExecutionGraphDataList:ArrayCollection = evt.result.graphDataList;
				var retrievedBatchSummary:Object = evt.result.batchDetails;
				var batchSummaryData:BatchSummaryData = null;
				if(retrievedBatchSummary != null)
				{
					batchSummaryData = createBatchSummaryData(retrievedBatchSummary);
				}
				var objectExecutionGraphDataList:ArrayCollection = new ArrayCollection();
				if(retrievedObjectExecutionGraphDataList != null && retrievedObjectExecutionGraphDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedObjectExecutionGraphDataList.length; ++i)
					{
						var retrievedObjectExecutionGraphData:Object = retrievedObjectExecutionGraphDataList.getItemAt(i);
						var objectExecutionGraphData:ObjectExecutionGraphData = 
								createObjectExecutionGraphData(retrievedObjectExecutionGraphData);
											
						objectExecutionGraphDataList.addItem(objectExecutionGraphData);
					}
				}
				var objectExecutionData:Object = 
					{
						"objectExecutionGraphDataList": objectExecutionGraphDataList,
						"batchSummaryData": batchSummaryData
					}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(objectExecutionData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);				
			}
		}
		
		/**
		 * Create a object execution graph data data object based on the data returned by the webservice.
		 */
		private function createObjectExecutionGraphData(retrievedObjectExecutionGraphData:Object):ObjectExecutionGraphData
		{
			var objectExecutionGraphData:ObjectExecutionGraphData = new ObjectExecutionGraphData();
			objectExecutionGraphData.batchNo = retrievedObjectExecutionGraphData['batchNo'];
			objectExecutionGraphData.batchRevNo = retrievedObjectExecutionGraphData['batchRevNo'];
			objectExecutionGraphData.graphId = retrievedObjectExecutionGraphData['graphId'];
			objectExecutionGraphData.collectTime = retrievedObjectExecutionGraphData['collectTime'];
			objectExecutionGraphData.graphValue = retrievedObjectExecutionGraphData['graphValue'];
			return objectExecutionGraphData;
		}
		/**
		 * Retrieve the per scan Execution count graph data for a given batch
		 */
		public function getPerScanExecutionCountGraphData
			(batchDetailsData:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getPerScanExecutionCountGraphDataResultHandler, defaultFaultHandler);
			invoke("getGraphData", getInputForGetPerScanExecutionCountGraphData(batchDetailsData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetPerScanExecutionCountGraphData(batchDetailsData:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getGraphData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{batchDetailsData.batchNo}</batchNo>
            				<batchRevNo>{batchDetailsData.batchRevNo}</batchRevNo>
            				<graphId>PerScanExecutionCountCollator</graphId>
            				<installationCode>{batchDetailsData.installationCode}</installationCode>
         				</batch>
					</s:getGraphData>;
			return xml;
		}
		
		private function getPerScanExecutionCountGraphDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedPerScanExecutionCountGraphDataList:ArrayCollection = evt.result.graphDataList;
				var retrievedBatchSummary:Object = evt.result.batchDetails;
				var batchSummaryData:BatchSummaryData = null;
				if(retrievedBatchSummary != null)
				{
					batchSummaryData = createBatchSummaryData(retrievedBatchSummary);
				}
				var perScanExecutionCountGraphDataList:ArrayCollection = new ArrayCollection();
				if(retrievedPerScanExecutionCountGraphDataList != null && retrievedPerScanExecutionCountGraphDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedPerScanExecutionCountGraphDataList.length; ++i)
					{
						var retrievedPerScanExecutionCountGraphData:Object = retrievedPerScanExecutionCountGraphDataList.getItemAt(i);
						var perScanExecutionCountGraphData:PerScanExecutionCountGraphData = 
								createPerScanExecutionCountGraphData(retrievedPerScanExecutionCountGraphData);
											
						perScanExecutionCountGraphDataList.addItem(perScanExecutionCountGraphData);
					}
				}
				var objectExecutionData:Object = 
					{
						"perScanExecutionCountGraphDataList": perScanExecutionCountGraphDataList,
						"batchSummaryData": batchSummaryData
					}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(objectExecutionData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);				
			}
		}
		
		/**
		 * Create a per scan Execution count graph data data object based on the data returned by the webservice.
		 */
		private function createPerScanExecutionCountGraphData(retrievedPerScanExecutionCountGraphData:Object):PerScanExecutionCountGraphData
		{
			var perScanExecutionCountGraphData:PerScanExecutionCountGraphData = new PerScanExecutionCountGraphData();
			perScanExecutionCountGraphData.batchNo = retrievedPerScanExecutionCountGraphData['batchNo'];
			perScanExecutionCountGraphData.batchRevNo = retrievedPerScanExecutionCountGraphData['batchRevNo'];
			perScanExecutionCountGraphData.graphId = retrievedPerScanExecutionCountGraphData['graphId'];
			perScanExecutionCountGraphData.collectTime = retrievedPerScanExecutionCountGraphData['collectTime'];
			perScanExecutionCountGraphData.graphValue = retrievedPerScanExecutionCountGraphData['graphValue'];
			return perScanExecutionCountGraphData;
		}
		
		/**
		 * Retrieve the listener data for a given batch
		 */
		public function getListenerData
			(batchDetailsData:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getListenerDataResultHandler, defaultFaultHandler);
			invoke("getListenerInfo", getInputForGetListenerData(batchDetailsData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetListenerData(batchDetailsData:BatchDetailsData):XML
		{
			var xml:XML = 
					<s:getListenerInfo xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
						<batch>
            				<batchNo>{batchDetailsData.batchNo}</batchNo>
            				<batchRevNo>{batchDetailsData.batchRevNo}</batchRevNo>
            				<installationCode>{batchDetailsData.installationCode}</installationCode>
         				</batch>
					</s:getListenerInfo>;
			return xml;
		}
		
		private function getListenerDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_LISTENER_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedListenerDataList:ArrayCollection = evt.result.listenerData;
				var listenerDataList:ArrayCollection = new ArrayCollection();
				if(retrievedListenerDataList != null && retrievedListenerDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedListenerDataList.length; ++i)
					{
						var retrievedListenerData:Object = retrievedListenerDataList.getItemAt(i);
						var listenerData:ListenerData = 
								createListenerData(retrievedListenerData);
											
						listenerDataList.addItem(listenerData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(listenerDataList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);				
			}
		}
		
		/**
		 * Create a listener data data object based on the data returned by the webservice.
		 */
		private function createListenerData(retrievedListenerData:Object):ListenerData
		{
			var listenerData:ListenerData = new ListenerData();
			listenerData.listenerId = retrievedListenerData['listenerId'];
			listenerData.noOfObjectsExecuted = retrievedListenerData['noOfObjectsExecuted'];
			listenerData.noOfObjectsFailed = retrievedListenerData['noOfObjectsFailed'];
			listenerData.timeTaken = retrievedListenerData['timeTaken'];
			return listenerData;
		}
		
		
	}
}