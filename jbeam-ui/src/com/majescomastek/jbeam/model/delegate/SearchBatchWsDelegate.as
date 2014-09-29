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
	import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
	import com.majescomastek.jbeam.model.vo.SearchBatchData;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The delegate class for the Search batch module used for invoking
	 * SOAP based webservices..
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class SearchBatchWsDelegate extends BaseSoapWsDelegate
	{
		public function SearchBatchWsDelegate()
		{
			super();
		}
		
		/**
		 * Retrieve the completed batch search result list for provided batch data.
		 */
		public function searchBatch(searchBatchData:ReqSearchBatch,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(searchBatchResultHandler, defaultFaultHandler);
			invoke("getBatchCompletedData", getInputForSearchBatch(searchBatchData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForSearchBatch(searchBatchData:ReqSearchBatch):XML
		{
			var parentXml:XML = 
				<s:getBatchCompletedData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<searchBatch>
						<installationCode>{searchBatchData.installationCode}</installationCode>
						<batchNo>{searchBatchData.batchNo}</batchNo>
            			<batchName>{searchBatchData.batchName}</batchName>
						<batchDate>{searchBatchData.batchDate}</batchDate>
            			<batchType>{searchBatchData.batchType}</batchType>
            			<batchEndReason>{searchBatchData.batchEndReason}</batchEndReason>
						<instructionSeqNo>{searchBatchData.instructionSeqNo}</instructionSeqNo>
					</searchBatch>
				</s:getBatchCompletedData>;					
			return parentXml;
		}
		
		
		
		private function searchBatchResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.SEARCH_BATCH_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedSearchBatchResultDataList:Object = evt.result.batchDetailsList;
				var searchBatchList:ArrayCollection = new ArrayCollection();
				if(retrievedSearchBatchResultDataList != null && retrievedSearchBatchResultDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedSearchBatchResultDataList.length; ++i)
					{
						var retrievedSearchBatchResultData:Object = retrievedSearchBatchResultDataList.getItemAt(i);
						var searchBatchResultData:SearchBatchData = createSearchBatchResultData(retrievedSearchBatchResultData);
						searchBatchList.addItem(searchBatchResultData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(searchBatchList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a batch Data data object based on the data returned by the webservice.
		 */
		private function createSearchBatchResultData(retrievedSearchBatchResultData:Object):SearchBatchData
		{
			var searchBatchResultData:SearchBatchData = new SearchBatchData();
			searchBatchResultData.batchNo = retrievedSearchBatchResultData['batchNo'];
			searchBatchResultData.batchRevNo = retrievedSearchBatchResultData['batchRevNo'];			
			searchBatchResultData.batchName = retrievedSearchBatchResultData['batchName'];
			searchBatchResultData.batchType = retrievedSearchBatchResultData['batchType'];			
			searchBatchResultData.batchEndReason = retrievedSearchBatchResultData['batchEndReason'];			
			searchBatchResultData.execStartTime = CommonUtils.formatDate(new Date(retrievedSearchBatchResultData['execStartTime']));			
			searchBatchResultData.execEndTime = CommonUtils.formatDate(new Date(retrievedSearchBatchResultData['execEndTime']));			
			searchBatchResultData.processId = retrievedSearchBatchResultData['processId'];			
			return searchBatchResultData;
		}
	}
}