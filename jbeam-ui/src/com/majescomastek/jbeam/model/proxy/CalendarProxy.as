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
	import com.majescomastek.jbeam.model.delegate.CalendarWsDelegate;
	import com.majescomastek.jbeam.model.vo.CalendarData;
	import com.majescomastek.jbeam.model.vo.ReqCalendarVO;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	/**
	 * The proxy class for the Calendar module.
	 */
	public class CalendarProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "CALENDAR_PROXY";
		
		/** Notification constant indicates success of Calendar data fetch service */
		public static const GET_CALENDARS_SERVICE_SUCCEEDED:String = "GET_CALENDARS_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of Calendar data fetch service */
		public static const GET_CALENDARS_SERVICE_FAILED:String = "GET_CALENDARS_SERVICE_FAILED";
		
		/** Notification constant indicates successful retrieval of calendar data for a batch */
		public static const GET_CALENDAR_DETAILS_SERVICE_SUCCEEDED:String =
			"GET_CALENDAR_DETAILS_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates unsuccessful retrieval of calendar data for a batch */
		public static const GET_CALENDAR_DETAILS_SERVICE_FAILED:String =
			"GET_CALENDAR_DETAILS_SERVICE_FAILED";

		/** Notification constant indicating the successful response for define calendar service */
		public static const DEFINE_CALENDAR_SERVICE_SUCCEEDED:String =
			"DEFINE_CALENDAR_SERVICE_SUCCEEDED";

		/** Notification constant indicating the failure response for define calendar service */
		public static const DEFINE_CALENDAR_SERVICE_FAILED:String =
			"DEFINE_CALENDAR_SERVICE_FAILED";

		/**	Default Constructor	 */ 
		public function CalendarProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Retrieve the list of all existing calendars in our system.
		 */
		public function getCalendars
			(calendarData:CalendarData, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getCalendarsResultHandler, getCalendarsFaultHandler);
			var delegate:CalendarWsDelegate = new CalendarWsDelegate();
			delegate.getCalendars(calendarData, [responder], tokenData);
		}
		
		private function getCalendarsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_CALENDARS_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getCalendarsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_CALENDARS_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Retrieve the details of calendar for the selected calendar
		 */
		public function getCalendarDetails
			(calendarData:CalendarData, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(getCalendarDetailsResultHandler,
					getCalendarDetailsFaultHandler);
			var delegate:CalendarWsDelegate = new CalendarWsDelegate();
			delegate.getCalendarDetails(calendarData, [responder], tokenData);
		}
		
		private function getCalendarDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_CALENDAR_DETAILS_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getCalendarDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_CALENDAR_DETAILS_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Send request to server for defining a new calendar
		 */
		public function defineCalendar
			(calendarVO:ReqCalendarVO, tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(defineCalendarResultHandler,
					defineCalendarFaultHandler);
			var delegate:CalendarWsDelegate = new CalendarWsDelegate();
			delegate.defineCalendar(calendarVO, [responder], tokenData);
		}
		
		private function defineCalendarResultHandler(evt:ResultEvent):void
		{
			sendNotification(DEFINE_CALENDAR_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function defineCalendarFaultHandler(evt:FaultEvent):void
		{
			sendNotification(DEFINE_CALENDAR_SERVICE_FAILED, evt.fault);
		}
		
	}
}