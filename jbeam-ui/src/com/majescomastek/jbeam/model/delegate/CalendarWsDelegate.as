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
	import com.majescomastek.jbeam.model.vo.CalendarData;
	import com.majescomastek.jbeam.model.vo.ReqCalendarVO;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The delegate class for the Jbeam application used for invoking
	 * SOAP based webservices for calendar module.
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class CalendarWsDelegate extends BaseSoapWsDelegate
	{
		public function CalendarWsDelegate()
		{
			super();
		}

		/**
		 * Retrieve the calendar list for a given installation.
		 */
		public function getCalendars
			(calendarData:CalendarData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getCalendarsResultHandler, defaultFaultHandler);
			invoke("getCalendars", getInputForGetCalendars(calendarData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetCalendars(calendarData:CalendarData):XML
		{
			var xml:XML = <s:getCalendars xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<calendarVO>
					            <calendarData>
					               <installationCode>{calendarData.installationCode}</installationCode>
					            </calendarData>
					         </calendarVO>
						  </s:getCalendars>;
			return xml;
		}
		
		private function getCalendarsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_CALENDAR_LIST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedCalendarList:ArrayCollection = evt.result.calendarList;
				var calendarList:ArrayCollection = new ArrayCollection();
				if(retrievedCalendarList != null && retrievedCalendarList.length > 0)
				{
					for(var i:uint = 0; i < retrievedCalendarList.length; ++i)
					{
						var retrievedCalendar:Object = retrievedCalendarList.getItemAt(i);
						var calendarData:CalendarData = createCalendarData(retrievedCalendar);
						calendarList.addItem(calendarData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(calendarList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Retrieve the calendar details for a given calendar.
		 */
		public function getCalendarDetails
			(calendarData:CalendarData, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getCalendarDetailsResultHandler, defaultFaultHandler);
			invoke("getCalendarData", getInputForGetCalendarDetails(calendarData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetCalendarDetails(calendarData:CalendarData):XML
		{
			var xml:XML = <s:getCalendarData xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<calendarVO>
					            <calendarData>
					               <calendarName>{calendarData.calendarName}</calendarName>
					               <installationCode>{calendarData.installationCode}</installationCode>
					               <year>{calendarData.year}</year>
					            </calendarData>
					         </calendarVO>
						  </s:getCalendarData>;
			return xml;
		}
		
		private function getCalendarDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_CALENDAR_DETAILS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				// Populate the remaining details if required...
				var retrievedCalendarDetailsList:ArrayCollection = evt.result.calendarList;
				var calendarDetailsList:ArrayCollection = new ArrayCollection();
				if(retrievedCalendarDetailsList != null && retrievedCalendarDetailsList.length > 0)
				{
					for(var i:uint = 0; i < retrievedCalendarDetailsList.length; ++i)
					{
						var retrievedCalendar:Object = retrievedCalendarDetailsList.getItemAt(i);
						var calendarData:CalendarData = createCalendarData(retrievedCalendar, 1);
						calendarDetailsList.addItem(calendarData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(calendarDetailsList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a calendar data object based on the data returned by the webservice.
		 */
		private function createCalendarData(retrievedCalendar:Object, index:int=0):CalendarData
		{
			var calendarData:CalendarData = new CalendarData();
			calendarData.calendarName = retrievedCalendar['calendarName'];
			calendarData.year = retrievedCalendar['year'];
			if(index == 1){
				calendarData.nonWorkingDate = retrievedCalendar['nonWorkingDate'];
				calendarData.remark = retrievedCalendar['remark'];
				calendarData.userId = retrievedCalendar['userId'];				
			}
			return calendarData;
		}
		
		/**
		 * Retrieve the calendar details for a given calendar.
		 */
		public function defineCalendar
			(calendarVO:ReqCalendarVO, externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(defineCalendarResultHandler, defaultFaultHandler);
			invoke("defineCalendar", getInputForDefineCalendar(calendarVO),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForDefineCalendar(calendarVO:ReqCalendarVO):XML
		{
			var parentXml:XML = <s:defineCalendar xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
							<calendarVO>
					            <calendarData>
					               <calendarName>{calendarVO.calendarData.calendarName}</calendarName>
					                 <installationCode>{calendarVO.calendarData.installationCode}</installationCode>
					                 <year>{calendarVO.calendarData.year}</year>
					               </calendarData>
					            </calendarVO>
					         </s:defineCalendar>;
			for each(var cal:CalendarData in calendarVO.calendarList)
			{
				parentXml.calendarVO.appendChild(createCalendarList(cal)); 
				
			}		          
					          
			
			return parentXml;
		}
		private function createCalendarList(calendarData:CalendarData):XML
		{
			var childXml:XML = 
				<calendarList>
	            	<calendarName>{calendarData.calendarName}</calendarName>
	               	<installationCode>{calendarData.installationCode}</installationCode>
	               	<nonWorkingDate>{calendarData.nonWorkingDate}</nonWorkingDate>
	               	<remark>{calendarData.remark}</remark>
	               	<userId>{calendarData.userId}</userId>
	               	<year>{calendarData.year}</year>
	            </calendarList>;
			
			return childXml;
		}
		
		private function defineCalendarResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.DEFINE_CALENDAR_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(evt.result, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
				
			}
		}

	}
}