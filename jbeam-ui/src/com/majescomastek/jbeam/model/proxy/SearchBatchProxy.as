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
	import com.majescomastek.jbeam.model.delegate.SearchBatchWsDelegate;
	import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class SearchBatchProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "SEARCH_BATCH_PROXY";
		
		/** Notification constant indicates success of run batch service */
		public static const SEARCH_BATCH_SERVICE_SUCCEEDED:String = "SEARCH_BATCH_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of SEARCH batch service */
		public static const SEARCH_BATCH_SERVICE_FAILED:String = "SEARCH_BATCH_SERVICE_FAILED";
		
		public function SearchBatchProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to SEARCH batch. 
		 */
		public function searchBatch(
			searchBatchData:ReqSearchBatch, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(searchBatchResultHandler, searchBatchFaultHandler);
			var delegate:SearchBatchWsDelegate = new SearchBatchWsDelegate();
			delegate.searchBatch(searchBatchData, [responder], tokenData);
		}
		
		private function searchBatchResultHandler(evt:ResultEvent):void
		{
			sendNotification(SEARCH_BATCH_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function searchBatchFaultHandler(evt:FaultEvent):void
		{
			sendNotification(SEARCH_BATCH_SERVICE_FAILED, evt.fault);
		}
		
	}
}