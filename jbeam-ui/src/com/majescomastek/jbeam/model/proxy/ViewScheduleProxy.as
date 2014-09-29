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
	import com.majescomastek.jbeam.model.delegate.ScheduleBatchWsDelegate;
	import com.majescomastek.jbeam.model.delegate.ViewScheduleWsDelegate;
	import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.model.vo.ReqProcessRequestSchedule;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class ViewScheduleProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "VIEW_SCHEDULE_PROXY";
		
		/** Notification constant indicates success of get schdule data service */
		public static const GET_SCHEDULE_DATA_SERVICE_SUCCEEDED:String = 
			"GET_SCHEDULE_DATA_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of get schdule data service */
		public static const GET_SCHEDULE_DATA_SERVICE_FAILED:String = 
			"GET_SCHEDULE_DATA_SERVICE_FAILED";
		
		/** Notification constant indicates success of refresh schedule data service */
		public static const REFRESH_SCHDULE_DATA_SERVICE_SUCCEEDED:String = 
			"REFRESH_SCHDULE_DATA_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of refresh schedule data service */
		public static const REFRESH_SCHDULE_DATA_SERVICE_FAILED:String = 
			"REFRESH_SCHDULE_DATA_SERVICE_FAILED";
		
		/** Notification constant indicates success of cancel schedule data service */
		public static const CANCEL_SCHDULE_SERVICE_SUCCEEDED:String = 
			"CANCEL_SCHDULE_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of cancel schedule data service */
		public static const CANCEL_SCHDULE_SERVICE_FAILED:String = 
			"CANCEL_SCHDULE_SERVICE_FAILED";
			
		
		public function ViewScheduleProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to get Process Request Schedule data. 
		 */
		public function getScheduleData(
			reqProcessRequestSchedule:ReqProcessRequestSchedule, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getScheduleDataResultHandler, getScheduleDataFaultHandler);
			var delegate:ViewScheduleWsDelegate = new ViewScheduleWsDelegate();
			delegate.getProcessRequestScheduleData(reqProcessRequestSchedule,[responder], tokenData);
		}
		
		private function getScheduleDataResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_SCHEDULE_DATA_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getScheduleDataFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_SCHEDULE_DATA_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Send request to refresh details of schedule. 
		 */
		public function refreshProcessRequestScheduleData(
			reqProcessRequestSchedule:ReqProcessRequestSchedule,
			tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(refreshProcessRequestScheduleResultHandler, 
					refreshProcessRequestScheduleFaultHandler);
			var delegate:ViewScheduleWsDelegate = new ViewScheduleWsDelegate();
			delegate.refreshProcessRequestScheduleData(reqProcessRequestSchedule, 
				[responder], tokenData);
		}
		
		private function refreshProcessRequestScheduleResultHandler(evt:ResultEvent):void
		{
			sendNotification(REFRESH_SCHDULE_DATA_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function refreshProcessRequestScheduleFaultHandler(evt:FaultEvent):void
		{
			sendNotification(REFRESH_SCHDULE_DATA_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Send request to refresh details of schedule. 
		 */
		public function cancelSchedule(reqProcessRequestSchedule:ReqProcessRequestSchedule,
														  tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(cancelScheduleResultHandler, cancelScheduleFaultHandler);
			var delegate:ViewScheduleWsDelegate = new ViewScheduleWsDelegate();
			delegate.cancelSchedule(reqProcessRequestSchedule, [responder], tokenData);
		}
		
		private function cancelScheduleResultHandler(evt:ResultEvent):void
		{
			sendNotification(CANCEL_SCHDULE_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function cancelScheduleFaultHandler(evt:FaultEvent):void
		{
			sendNotification(CANCEL_SCHDULE_SERVICE_FAILED, evt.fault);
		}
		
	}
}