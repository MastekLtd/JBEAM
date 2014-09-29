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
	import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.ReqProcessRequestSchedule;
	import com.majescomastek.jbeam.model.vo.ResProcessRequestSchedule;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.utils.ObjectProxy;
	
	/**
	 * The delegate class for the View Schedule module used for invoking
	 * SOAP based webservices..
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class ViewScheduleWsDelegate extends BaseSoapWsDelegate
	{

		public function ViewScheduleWsDelegate()
		{
			super();
		}
		
		/**
		 * Get schedule data from 'PROCESS_REQUEST_SCHEDULE.
		 */
		public function getProcessRequestScheduleData(reqProcessRequestSchedule:ReqProcessRequestSchedule,
													  externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getProcessRequestScheduleDataResultHandler, defaultFaultHandler);
			invoke("getProcessRequestScheduleData", getInputForGetProcessRequestScheduleData(reqProcessRequestSchedule),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetProcessRequestScheduleData(reqProcessRequestSchedule:ReqProcessRequestSchedule):XML
		{
			var parentXml:XML = <s:getProcessRequestScheduleData xmlns:s="http://services.server.ws.monitor.stgmastek.com/" >
									<processRequestScheduleVO>
							            <installationCode>{reqProcessRequestSchedule.installationCode}</installationCode>
									 </processRequestScheduleVO>
								</s:getProcessRequestScheduleData>;	
			return parentXml;
		}
		
		private function getProcessRequestScheduleDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_SCHEDULE_REQUEST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedProcessRequestScheduleList:Object = evt.result.processRequestScheduleList;
//				retrievedProcessRequestScheduleData['responseTime'] = evt.result.responseTime;
				var processRequestScheduleList:ArrayCollection = new ArrayCollection();
				
				if(retrievedProcessRequestScheduleList != null && retrievedProcessRequestScheduleList.length > 0)
				{
					for(var i:uint = 0; i < retrievedProcessRequestScheduleList.length; ++i)
					{
						var retrievedProcessRequestScheduleData:ObjectProxy = retrievedProcessRequestScheduleList[i];
						var processRequestScheduleData:ProcessRequestScheduleData = 
							createGetScheduleRequestData(retrievedProcessRequestScheduleData);
						processRequestScheduleList.addItem(processRequestScheduleData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(processRequestScheduleList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a run batch request object based on the data returned by the webservice.
		 */
		private function createGetScheduleRequestData(retrievedProcessRequestScheduleData:Object):ProcessRequestScheduleData
		{
			var processRequestScheduleData:ProcessRequestScheduleData = new ProcessRequestScheduleData();
			processRequestScheduleData.installationCode = retrievedProcessRequestScheduleData['installationCode'];
			processRequestScheduleData.batchName = retrievedProcessRequestScheduleData['batchName'];
			processRequestScheduleData.schId = retrievedProcessRequestScheduleData['schId'];
			processRequestScheduleData.schStat = retrievedProcessRequestScheduleData['schStat'];			
			processRequestScheduleData.endReason = retrievedProcessRequestScheduleData['endReason'];			
			processRequestScheduleData.freqType = retrievedProcessRequestScheduleData['freqType'];			
			
			processRequestScheduleData.occurCounter = retrievedProcessRequestScheduleData['occurCounter'];
			if(isNaN(processRequestScheduleData.occurCounter))	processRequestScheduleData.occurCounter = 0;
			
			processRequestScheduleData.endOccur = retrievedProcessRequestScheduleData['endOccur'];	
			if(isNaN(processRequestScheduleData.endOccur))	processRequestScheduleData.endOccur = 0;
			
			if(retrievedProcessRequestScheduleData['endDt'] != undefined)
			{
				processRequestScheduleData.endDt = new Date(retrievedProcessRequestScheduleData['endDt']);
			}
			if(retrievedProcessRequestScheduleData['startDt'] != undefined)
			{
				processRequestScheduleData.startDt = new Date(retrievedProcessRequestScheduleData['startDt']);
			}
			processRequestScheduleData.onWeekDay = retrievedProcessRequestScheduleData['onWeekDay'];			
			processRequestScheduleData.skipFlag = retrievedProcessRequestScheduleData['skipFlag'];			
			processRequestScheduleData.keepAlive = retrievedProcessRequestScheduleData['keepAlive'];			
			processRequestScheduleData.userId = retrievedProcessRequestScheduleData['userId'];			
			return processRequestScheduleData;
		}
		
		/**
		 * Refresh schedule details. 
		 */
		public function refreshProcessRequestScheduleData(reqProcessRequestSchedule:ReqProcessRequestSchedule,
														  externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(refreshProcessRequestScheduleDataResultHandler, defaultFaultHandler);
			invoke("refreshProcessRequestScheduleData", getInputForRefreshProcessRequestScheduleData(reqProcessRequestSchedule),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForRefreshProcessRequestScheduleData(reqProcessRequestSchedule:ReqProcessRequestSchedule):XML
		{
			var parentXml:XML = <s:refreshProcessRequestScheduleData xmlns:s="http://services.server.ws.monitor.stgmastek.com/" >
									<processRequestScheduleVO>
							            <installationCode>{reqProcessRequestSchedule.installationCode}</installationCode>
							         </processRequestScheduleVO>
								</s:refreshProcessRequestScheduleData>;		
			
			if(reqProcessRequestSchedule.processRequestSchedulesList.length > 0)
			{
				
				for each(var processRequestScheduleData:ProcessRequestScheduleData 
					in reqProcessRequestSchedule.processRequestSchedulesList)
				{
					parentXml.processRequestScheduleVO.
						appendChild(createProcessRequestScheduleList(processRequestScheduleData));
				} 	
			}
			return parentXml;
		}
		
		private function createProcessRequestScheduleList(processRequestScheduleData:ProcessRequestScheduleData):XML
		{
			var childXml:XML = <processRequestScheduleList>
							   		<schId>{processRequestScheduleData.schId}</schId>
							   </processRequestScheduleList>;
			return childXml;
		}
		
		private function refreshProcessRequestScheduleDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.REFRESH_SCHEDULE_REQUEST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedProcessRequestScheduleList:Object = evt.result.processRequestScheduleList;
				var processRequestScheduleList:ArrayCollection = new ArrayCollection();
				
				if(retrievedProcessRequestScheduleList != null && retrievedProcessRequestScheduleList.length > 0)
				{
					for(var i:uint = 0; i < retrievedProcessRequestScheduleList.length; ++i)
					{
						var retrievedProcessRequestScheduleData:ObjectProxy = retrievedProcessRequestScheduleList[i];
						var processRequestScheduleData:ProcessRequestScheduleData = 
							createGetScheduleRequestData(retrievedProcessRequestScheduleData);
						processRequestScheduleList.addItem(processRequestScheduleData);
					}
				}
				var newEvt:ResultEvent = ResultEvent.createEvent(processRequestScheduleList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}	
		
		/**
		 * Refresh schedule details. 
		 */
		public function cancelSchedule(reqProcessRequestSchedule:ReqProcessRequestSchedule,
														  externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(cancelScheduleResultHandler, defaultFaultHandler);
			invoke("cancelSchedule", getInputForCancelSchedule(reqProcessRequestSchedule),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForCancelSchedule(reqProcessRequestSchedule:ReqProcessRequestSchedule):XML
		{
//			var parentXml:XML = <s:cancelSchedule xmlns:s="http://services.server.ws.monitor.stgmastek.com/" >
//									<processRequestScheduleVO>
//							            <installationCode>{reqProcessRequestSchedule.installationCode}</installationCode>
//										<processRequestScheduleData>
//								            <schId>{reqProcessRequestSchedule.processRequestSchedulesData.schId}</schId>
//								         </processRequestScheduleData>
//									 </processRequestScheduleVO>
//								</s:cancelSchedule>;		
//			
//			return parentXml;
			var parentXml:XML = <s:cancelSchedule xmlns:s="http://services.server.ws.monitor.stgmastek.com/" >
												<processRequestScheduleVO>
													<installationCode>{reqProcessRequestSchedule.installationCode}</installationCode>
												 </processRequestScheduleVO>
											</s:cancelSchedule>;		
			
			if(reqProcessRequestSchedule.processRequestSchedulesList.length > 0)
			{
				
				for each(var processRequestScheduleData:ProcessRequestScheduleData 
					in reqProcessRequestSchedule.processRequestSchedulesList)
				{
					parentXml.processRequestScheduleVO.
						appendChild(createProcessRequestScheduleList(processRequestScheduleData));
				} 	
			}
			return parentXml;
		}
		
		private function cancelScheduleResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.CANCEL_SCHEDULE_REQUEST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedDesc:String = evt.result.description;
				var retrievedInstallationCode:String = evt.result.installationCode;
				var retrievedProcessReqSchList:Object = evt.result.processRequestScheduleList;
				var processRequestScheduleList:ArrayCollection = new ArrayCollection();
				var resProcessReqSch:ResProcessRequestSchedule = new ResProcessRequestSchedule();
				if(retrievedProcessReqSchList != null && retrievedProcessReqSchList.length > 0)
				{
					for(var i:uint = 0; i < retrievedProcessReqSchList.length; ++i)
					{
						var retrievedProcessRequestScheduleData:ObjectProxy = retrievedProcessReqSchList[i];
						var processRequestScheduleData:ProcessRequestScheduleData = 
							createGetScheduleRequestData(retrievedProcessRequestScheduleData);
						processRequestScheduleList.addItem(processRequestScheduleData);
					}
				}
				resProcessReqSch.processRequestSchedulesList = processRequestScheduleList;
				resProcessReqSch.successFailureFlag = retrievedDesc; 
				resProcessReqSch.installationCode = retrievedInstallationCode;
				var newEvt:ResultEvent = ResultEvent.createEvent(resProcessReqSch, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}		
	}
}