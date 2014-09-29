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
package com.majescomastek.jbeam.view.mediator.calendar
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.calendar.CalendarModuleFacade;
	import com.majescomastek.jbeam.model.proxy.CalendarProxy;
	import com.majescomastek.jbeam.model.vo.CalendarData;
	import com.majescomastek.jbeam.model.vo.ReqCalendarVO;
	import com.majescomastek.jbeam.view.components.calendar.CalendarModule;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the CalendarModule view.
	 */
	public class CalendarModuleMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "CALENDAR_MODULE_MEDIATOR";
		
		private var _calendarProxy:CalendarProxy;
		
		public function CalendarModuleMediator(view:CalendarModule)
		{
			super(NAME, view);		
			
			view.addEventListener(CalendarModule.CALENDAR_REQUEST, 
					onCalendarRequest, false, 0, true);
			view.addEventListener(CalendarModule.CALENDAR_CLICK, 
					onCalendarClick, false, 0, true);
			view.addEventListener(CalendarModule.DEFINE_CALENDAR_REQUEST, 
					onDefineCalendar, false, 0, true);
		}
	
		/**
		 * The function invoked when the CALENDAR_REQUEST is fired.
		 */		
		private function onCalendarRequest(evt:CustomDataEvent):void
		{
			_calendarProxy.getCalendars(CalendarData(evt.eventData));
		}
		
		/**
		 * The function invoked when the CALENDAR_CLICK event is fired.
		 */
		private function onCalendarClick(evt:CustomDataEvent):void
		{
			_calendarProxy.getCalendarDetails(CalendarData(evt.eventData));
		}
		
		/**
		 * The function invoked when the DEFINE_CALENDAR_REQUEST event is fired.
		 */
		private function onDefineCalendar(evt:CustomDataEvent):void
		{
			_calendarProxy.defineCalendar(ReqCalendarVO(evt.eventData));
		}
		

		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_calendarProxy = CalendarProxy(facade.retrieveProxy(CalendarProxy.NAME));			
		}

		/**
		 * @inheritDoc
		 */		
		override public function onRemove():void
		{
			module.removeEventListener(CalendarModule.CALENDAR_REQUEST,
				onCalendarRequest, false);
							
			module.removeEventListener(CalendarModule.CALENDAR_CLICK,
				onCalendarClick, false);
			
			module.removeEventListener(CalendarModule.DEFINE_CALENDAR_REQUEST, 
				onDefineCalendar, false);
			setViewComponent(null);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [CalendarModuleFacade.CALENDAR_MODULE_STARTUP_COMPLETE,
				CalendarProxy.GET_CALENDARS_SERVICE_SUCCEEDED,
				CalendarProxy.GET_CALENDARS_SERVICE_FAILED,
				CalendarProxy.GET_CALENDAR_DETAILS_SERVICE_SUCCEEDED,
				CalendarProxy.GET_CALENDAR_DETAILS_SERVICE_FAILED,				
				CalendarProxy.DEFINE_CALENDAR_SERVICE_SUCCEEDED,
				CalendarProxy.DEFINE_CALENDAR_SERVICE_FAILED				
			];
		}
		
		/**
		 * Return the reference to the view which this mediator refers to.
		 */
		private function get module():CalendarModule
		{
			return CalendarModule(viewComponent);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var name:String = notification.getName();
			switch(name)
			{
				case CalendarModuleFacade.CALENDAR_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case CalendarProxy.GET_CALENDARS_SERVICE_SUCCEEDED:
					module.handleCalendarRetrieval(data);
					break;
				case CalendarProxy.GET_CALENDARS_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				case CalendarProxy.GET_CALENDAR_DETAILS_SERVICE_SUCCEEDED:
					module.handleCalendarDetailsRetrieval(data);
					break;
				case CalendarProxy.GET_CALENDAR_DETAILS_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				case CalendarProxy.DEFINE_CALENDAR_SERVICE_SUCCEEDED:
					module.handleDefineCalendarRetrieval(data);
					break;
				case CalendarProxy.DEFINE_CALENDAR_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalid notification provided in CalendarModuleMediator'); 
					break;				
			}
		}

	}
}