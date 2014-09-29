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
	import com.majescomastek.jbeam.model.vo.BatchObject;
	import com.majescomastek.jbeam.model.vo.Entity;
	import com.majescomastek.jbeam.model.vo.FailedObjectsData;
	import com.majescomastek.jbeam.model.vo.GraphData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.InstructionLog;
	import com.majescomastek.jbeam.model.vo.ProgressLevelData;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.ReqVersion;
	import com.majescomastek.jbeam.model.vo.RequestListenerInfo;
	import com.majescomastek.jbeam.model.vo.UserInstallationRole;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.AsyncToken;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	/**
	 * The delegate class for the Jbeam application used for invoking
	 * SOAP based webservices.
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class JbeamSoapWsDelegate extends BaseSoapWsDelegate
	{
		public function JbeamSoapWsDelegate()
		{
			super();
		}
		
		/**
		 * Authenticate the user in consideration. Responders contains the list of responders
		 * which would be invoked on the success/failure of the service. 
		 */
		public function checkCompatibility(reqVersion:ReqVersion, externalResponders:Array,
			tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(checkCompatibilityResultHandler, checkCompatibilityFaultHandler);
			invoke("checkCompatibility", getInputForCheckCompatibility(reqVersion),
				externalResponders, internalResponder, tokenData);			
		}
		
		private function getInputForCheckCompatibility(reqVersion:ReqVersion):XML
		{
			var xml:XML = <s:checkCompatibility xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
								<version>
						            <majorVersion>{reqVersion.majorVersion}</majorVersion>
						            <minorVersion>{reqVersion.minorVersion}</minorVersion>
						         </version>
							</s:checkCompatibility>;
			return xml;			
		}
		
		private function checkCompatibilityResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.CHECK_COMPATIBILITY_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedResponse:Object = evt.result;
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(retrievedResponse, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		/**
		 * The default implementation of a fault handler for the ws delegate
		 * class. It simply loops over the external responders attached to the
		 * async token.
		 */
		protected function checkCompatibilityFaultHandler(evt:FaultEvent):void
		{
			var token:AsyncToken = evt.token;
			var responders:Array = token['externalResponders'] as Array;
			for each(var responder:IResponder in responders)
			{
				responder.fault(evt);
			}
		}
		
		/**
		 * Authenticate the user in consideration. Responders contains the list of responders
		 * which would be invoked on the success/failure of the service. 
		 */
		public function userAuthentication(userProfile:UserProfile, externalResponders:Array,
			tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(userAuthenticationResultHandler, defaultFaultHandler);
			invoke("userAuthentication", getInputForUserAuthentication(userProfile),
				externalResponders, internalResponder, tokenData);			
		}
		
		private function getInputForUserAuthentication(userProfile:UserProfile):XML
		{
			var xml:XML = <s:userAuthentication xmlns:s="http://services.server.ws.monitor.stgmastek.com/"><userCredentials>
							<userId>{userProfile.userId}</userId><password>{userProfile.password}</password>
						  </userCredentials></s:userAuthentication>;
			return xml;			
		}
		
		private function userAuthenticationResultHandler(evt:ResultEvent):void
		{
			// TODO: Change the service so that fault is thrown at the server side
			// instead of returning a response even when the ws fails. Also make
			// sure an appropriate fault message is provided by the service impl.
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var description:String = evt.result.description;
				if(description == null)
				{
					description = CommonConstants.USER_AUTHENTICAION_FAILED;
				}
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, description), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var resUserDetails:ReqUserDetails = new ReqUserDetails();
				var retrievedUserProfile:ObjectProxy = evt.result.userDetails;
				var userProfile:UserProfile = createUserProfile(retrievedUserProfile);
				var retrievedUserInstallationRoleDetailsResultDataList:Object
											 = evt.result.userInstallationRoleList;
											 
				resUserDetails.userProfile = createUserProfile(retrievedUserProfile);
				resUserDetails.userInstallationRoleList = 
						getUserInstallationRoleDetailsList(retrievedUserInstallationRoleDetailsResultDataList); 
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(resUserDetails, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		/**
		 * Create a UserProfile object based on the data returned by the webservice.
		 */
		private function createUserProfile(retrievedUserProfile:ObjectProxy):UserProfile
		{
			var userProfile:UserProfile = new UserProfile();
			userProfile.userId = retrievedUserProfile['userId'];
			userProfile.userName = retrievedUserProfile['userName'];		
			userProfile.adminRole = retrievedUserProfile['adminRole'];		
			userProfile.forcePasswordFlag = retrievedUserProfile['forcePasswordFlag'];
			userProfile.contactNumber = retrievedUserProfile['telephoneNo'];		
			userProfile.hintQuestion = retrievedUserProfile['hintQuestion'];		
			userProfile.hintAnswer = retrievedUserProfile['hintAnswer'];		
			userProfile.defaultView = retrievedUserProfile['defaultView'];		
			return userProfile;
		}
		
		private function getUserInstallationRoleDetailsList(
			retrievedUserInstallationRoleDetailsResultDataList:Object):ArrayCollection
		{
			var userInstallationRoleDetailsList:ArrayCollection = new ArrayCollection();
			if(retrievedUserInstallationRoleDetailsResultDataList != null 
					&& retrievedUserInstallationRoleDetailsResultDataList.length > 0)
			{
				for(var i:uint = 0; 
						i < retrievedUserInstallationRoleDetailsResultDataList.length;
						++i)
				{
					var retrievedUserInstallationRoleDetailsResultData:Object 
							= retrievedUserInstallationRoleDetailsResultDataList.getItemAt(i);
					var userInstallationRoleDetailsResultData:UserInstallationRole 
						= createGetUserInstallationRoleDetailsResultData(retrievedUserInstallationRoleDetailsResultData);
					userInstallationRoleDetailsList.addItem(userInstallationRoleDetailsResultData);
				}
			}
			return userInstallationRoleDetailsList;
		}
		
		/**
		 * Create a UserInstallationRole Details object based on the data returned by the webservice.
		 */
		private function createGetUserInstallationRoleDetailsResultData(
				retrievedUserInstallationRoleDetailsResultData:Object):UserInstallationRole
		{
			var getUserInstallationRoleDetailsResultData:UserInstallationRole 
													= new UserInstallationRole();
			getUserInstallationRoleDetailsResultData.userId 
					= retrievedUserInstallationRoleDetailsResultData['userId'];
			getUserInstallationRoleDetailsResultData.installationCode 
					= retrievedUserInstallationRoleDetailsResultData['installationCode'];
			getUserInstallationRoleDetailsResultData.roleId 
					= retrievedUserInstallationRoleDetailsResultData['roleId'];
			return getUserInstallationRoleDetailsResultData;
		}
		
		/**
		 * Retrieve the list of all installations in the system.
		 */
		public function getInstallationData(
				userProfile:UserProfile, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getInstallationDataResultHandler, defaultFaultHandler);
			invoke("getInstallationData", getInputForGetInstallationData(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetInstallationData(userProfile:UserProfile):XML
		{
			var xml:XML =
				<s:getInstallationData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
			         <batch>
			            <userProfile>
			               <userId>{userProfile.userId}</userId>
			            </userProfile>
			         </batch>
			    </s:getInstallationData>

			return xml;			
		}
		
		private function getInstallationDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_INSTALLATION_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedInstallationList:ArrayCollection = evt.result.installationDataList;
				var installationList:ArrayCollection = new ArrayCollection();
				if(retrievedInstallationList != null && retrievedInstallationList.length > 0)
				{
					for(var i:uint = 0; i < retrievedInstallationList.length; ++i)
					{
						var retrievedInstallation:ObjectProxy = retrievedInstallationList[i];
						var installationData:InstallationData = createInstallationData(retrievedInstallation);
						var retrievedProgressLevelDataList:ArrayCollection =
							retrievedInstallation.progressLevelDataList;
						for(var j:uint = 0; j < retrievedProgressLevelDataList.length; ++j)
						{
							var retrievedProgressLevelData:ObjectProxy = retrievedProgressLevelDataList[j];
							var progressLevelData:ProgressLevelData =
								createProgessLevelData(retrievedProgressLevelData);
							progressLevelData.serialNo = j + 1;
							installationData.progressLevelDataList.addItem(progressLevelData);
						}
						
						var retrievedEntityList:ArrayCollection = retrievedInstallation.entityList;
						for(var k:uint = 0; k < retrievedEntityList.length; ++k)
						{
							var retrievedEntity:ObjectProxy = retrievedEntityList[k];
							var entityData:Entity =	createEntity(retrievedEntity);
							installationData.entityList.addItem(entityData);
						}
						
						// Add logic for setting the batch status
						if(installationData.progressLevelDataList.length > 0)
						{
							installationData.batchStatus = ProgressLevelData(installationData.
								progressLevelDataList.getItemAt(0)).prgActivityType;
						}
						
						installationList.addItem(installationData);
					}
				}
				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(installationList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			} 
		}
		
		private function createInstallationData(retrievedInstallation:ObjectProxy):InstallationData
		{
			var installationData:InstallationData = new InstallationData();
			
			installationData.batchNo = retrievedInstallation['BNo'];
			if(isNaN(installationData.batchNo))	installationData.batchNo = 0;	
			
			installationData.batchRevNo = retrievedInstallation['BRevNo'];
			if(isNaN(installationData.batchRevNo))	installationData.batchRevNo = 0;
			
			installationData.batchEndReason = retrievedInstallation['batchEndReason'];
			
			if(retrievedInstallation['batchStartTime'] != undefined)
			{
				installationData.batchStartTime = new Date(retrievedInstallation['batchStartTime']);
			}
			if(retrievedInstallation['batchEndTime'] != undefined)
			{
				installationData.batchEndTime = new Date(retrievedInstallation['batchEndTime']);
			}
			installationData.installationCode = retrievedInstallation['instCode'];
			installationData.totalFailedObjects = retrievedInstallation['totalFailedObjects'];
			installationData.totalObjects = retrievedInstallation['totalObjects'];
			installationData.timezoneOffset = retrievedInstallation['timezoneOffset'];
			installationData.timezoneId = retrievedInstallation['timezoneId'];
			installationData.timezoneShortName = retrievedInstallation['timezoneShortName'];
			return installationData;
		}
		
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
			return progressLevelData;
		}
		
		private function createEntity(retrievedEntity:ObjectProxy):Entity
		{
			var entityData:Entity = new Entity();
			entityData.entityName = retrievedEntity['entity'];
			entityData.lookupColumn = retrievedEntity['lookupColumn'];			
			entityData.lookupValue = retrievedEntity['lookupValue'];			
			entityData.precedenceOrder = retrievedEntity['precedenceOrder'];			
			entityData.valueColumn = retrievedEntity['valueColumn'];
			entityData.numberOfRequiredParameters = retrievedEntity['numberOfRequiredParameters'];
			entityData.description = retrievedEntity['description'];
			
			return entityData;
		}
		
		/**
		 * Retrieve the installation details for a given installation.
		 */
		public function getInstallationDetailsForBatch(
					installationData:InstallationData, userProfile:UserProfile,
					externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getInstallationDataResultHandler, defaultFaultHandler);
			invoke("getInstallationDetailsForBatch", 
				getInputForGetInstallationDetailsForBatch(
					installationData, userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetInstallationDetailsForBatch(
											installationData:InstallationData,
											userProfile:UserProfile):XML
		{
			var xml:XML = <s:getInstallationDetailsForBatch xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<batch>
					            <installationCode>
					            	{installationData.installationCode}
					            </installationCode>
					            <userProfile>
					               <userId>{userProfile.userId}</userId>
					            </userProfile>
					         </batch>
					         
						  </s:getInstallationDetailsForBatch>;
			return xml;
		}

		/**
		 * The function used to retrieve the failed object details for a given installation.
		 */		
		public function getFailedObjectDetails(
			requestListenerInfo:RequestListenerInfo, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getFailedObjectDetailsResultHandler, defaultFaultHandler);
			invoke("getFaliedObjectDetails", getInputForGetFailedObjectDetails(requestListenerInfo),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetFailedObjectDetails(requestListenerInfo:RequestListenerInfo):XML
		{
			var xml:XML = <s:getFaliedObjectDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<reqListenerInfo>
								<batchNo>{requestListenerInfo.batchNo}</batchNo>
								<batchRevNo>{requestListenerInfo.batchRevNo}</batchRevNo>
								<installationCode>{requestListenerInfo.installationCode}</installationCode>
								<listenerId>{requestListenerInfo.listenerId}</listenerId>
							</reqListenerInfo>
						  </s:getFaliedObjectDetails>;
			return xml;
		}
		
		private function getFailedObjectDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent(new Fault
					(null, CommonConstants.GET_FAILED_OBJECTS_DETAILS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedFailedObjectsList:ArrayCollection = evt.result.failedObjectsData;
				var failedObjectsList:ArrayCollection = new ArrayCollection();
				if(retrievedFailedObjectsList != null && retrievedFailedObjectsList.length > 0)
				{
					for(var i:uint = 0; i < retrievedFailedObjectsList.length; ++i)
					{
						var retrievedFailedObject:Object = retrievedFailedObjectsList.getItemAt(i);
						var failedObject:FailedObjectsData = createFailedObjectsData(retrievedFailedObject);
						failedObjectsList.addItem(failedObject);
					}
				}

				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(failedObjectsList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a failed object data object based on the data returned by the webservice.
		 */
		private function createFailedObjectsData(retrievedFailedObject:Object):FailedObjectsData
		{
			var failedObjectsData:FailedObjectsData = new FailedObjectsData();
			failedObjectsData.errorDescription = retrievedFailedObject['errorDescription'];
			failedObjectsData.errorType = retrievedFailedObject['errorType'];
			failedObjectsData.failedObjectName = retrievedFailedObject['failedObjectName'];
			failedObjectsData.failedObjectNo = retrievedFailedObject['failedObjectNo'];
			failedObjectsData.failedObjectSequence = retrievedFailedObject['failedObjectSequence'];
			failedObjectsData.listenerId = retrievedFailedObject['listenerId'];
			failedObjectsData.taskName = retrievedFailedObject['taskName'];
			failedObjectsData.timeTaken = retrievedFailedObject['timeTaken'];
			return failedObjectsData;
		}
		
		/**
		 * Retrieve the batch object details for the given progress data for the
		 * given installation.
		 */
		public function getBatchObjectDetails
			(progressLevelData:ProgressLevelData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getBatchObjectDetailsResultHandler, defaultFaultHandler);
			invoke("getBatchObjectDetails", getInputForGetBatchObjectDetails(progressLevelData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetBatchObjectDetails(progressLevelData:ProgressLevelData):XML
		{
			var xml:XML = 
				<s:getBatchObjectDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<reqBatchDetails>
			            <batchNo>{progressLevelData.batchNo}</batchNo>
			            <batchRevNo>{progressLevelData.batchRevNo}</batchRevNo>
			            <installationCode>{progressLevelData.installationCode}</installationCode>
			            <cycleNo>{progressLevelData.cycleNo}</cycleNo>
			            <prePost>{progressLevelData.prgLevelType}</prePost>
		         	</reqBatchDetails>
		         </s:getBatchObjectDetails>;
	         return xml;
		}
		
		private function getBatchObjectDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent(new Fault
					(null, CommonConstants.GET_BATCH_OBJECTS_DETAILS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedBatchObjectsList:ArrayCollection = evt.result.batchObjectList;
				var batchObjectList:ArrayCollection = new ArrayCollection();
				if(retrievedBatchObjectsList != null && retrievedBatchObjectsList.length > 0)
				{
					for(var i:uint = 0; i < retrievedBatchObjectsList.length; ++i)
					{
						var retrievedBatchObject:Object = retrievedBatchObjectsList.getItemAt(i);
						var failedObject:BatchObject = createBatchObjectsData(retrievedBatchObject);
						batchObjectList.addItem(failedObject);
					}
				}

				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(batchObjectList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		private function createBatchObjectsData(retrievedBatchObject:Object):BatchObject
		{
			var batchObject:BatchObject = new BatchObject();
			batchObject.batchSequenceNo = retrievedBatchObject['beSeqNo'];
			if(retrievedBatchObject['objExecEndTime'] != undefined)
			{
				batchObject.objExecutionEndTime = CommonUtils.formatDate(new Date(retrievedBatchObject['objExecEndTime']));
			}
			else
			{
				batchObject.objExecutionEndTime = "----";	
			}
			batchObject.objExecutionStartTime = CommonUtils.formatDate(new Date(retrievedBatchObject['objExecStartTime']));
			batchObject.status = retrievedBatchObject['status'];
			batchObject.taskName = retrievedBatchObject['taskName'];
			batchObject.timeTaken = retrievedBatchObject['timeTaken'];
			return batchObject;
		}

		/**
		 * Retrieve the graph data based on the passed in batch details
		 * and the graph identifier.
		 */
		public function getGraphData
			(graphData:GraphData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getGraphDataResultHandler, defaultFaultHandler);
			invoke("getGraphData", getInputForGetGraphData(graphData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetGraphData(graphData:GraphData):XML
		{
			var xml:XML = 
				<s:getGraphData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
		         <batch>
		            <batchNo>{graphData.batchNo}</batchNo>
		            <batchRevNo>{graphData.batchRevNo}</batchRevNo>
		            <graphId>{graphData.graphId}</graphId>
		            <installationCode>{graphData.installationCode}</installationCode>
		         </batch>
		       </s:getGraphData>;
	         return xml;
		}
		
		private function getGraphDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent(new Fault
					(null, CommonConstants.GET_GRAPH_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedGraphDataList:ArrayCollection = evt.result.graphDataList;
				var graphDataList:ArrayCollection = new ArrayCollection();
				if(retrievedGraphDataList != null && retrievedGraphDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedGraphDataList.length; ++i)
					{
						var retrievedGraphData:Object = retrievedGraphDataList.getItemAt(i);
						var graphData:GraphData = createGraphData(retrievedGraphData);
						graphDataList.addItem(graphData);
					}
				}

				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(graphDataList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		private function createGraphData(retrievedGraphData:Object):GraphData
		{
			var graphData:GraphData = new GraphData();
			graphData.batchNo = retrievedGraphData['batchNo'];
			graphData.batchRevNo = retrievedGraphData['batchRevNo'];
			graphData.collectTime = new Date(retrievedGraphData['collectTime']);
			graphData.graphId = retrievedGraphData['graphId'];
			graphData.graphValue = retrievedGraphData['graphValue'];
			graphData.xAxis = retrievedGraphData['graphXAxis'];
			graphData.installationCode = retrievedGraphData['installationCode'];
			return graphData;
		}
		
		/**
		 * Retrieve the instruction data based on the passed in batch details.		 
		 */
		public function checkInstructionLog
			(batchDetails:BatchDetailsData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(checkInstructionLogResultHandler, defaultFaultHandler);
			invoke("checkInstructionLog", checkInputForcheckInstructionLog(batchDetails),
				externalResponders, internalResponder, tokenData);
		}
		
		private function checkInputForcheckInstructionLog(batchDetails:BatchDetailsData):XML
		{
			var xml:XML = 
				<s:checkInstructionLog xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<reqInstructionLog>
					   <installationCode>{batchDetails.installationCode}</installationCode>
					   <seqNo>{batchDetails.instructionSeqNo}</seqNo>
					</reqInstructionLog>
				</s:checkInstructionLog>;
	         return xml;
		}
		
		private function checkInstructionLogResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent(new Fault
					(null, CommonConstants.GET_INSTRUCTION_LOG_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedInstructionLog:Object = evt.result.instructionLog;
				var instructionLog:InstructionLog = createInstructionLog(retrievedInstructionLog);
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(instructionLog, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		private function createInstructionLog(retrievedInstructionLog:Object):InstructionLog
		{
			var instructionLog:InstructionLog = new InstructionLog();
			instructionLog.installationCode = retrievedInstructionLog['installationCode'];
			instructionLog.seqNo = retrievedInstructionLog['seqNo'];
			instructionLog.batchAction = retrievedInstructionLog['batchAction'];
			return instructionLog;
		}

	}
}