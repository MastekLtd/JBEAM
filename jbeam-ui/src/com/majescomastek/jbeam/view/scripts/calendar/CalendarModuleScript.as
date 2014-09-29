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
include "../../../common/CommonScript.as"
include "CalendarScript.as"

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.facade.calendar.CalendarModuleFacade;
import com.majescomastek.jbeam.model.vo.CalendarData;
import com.majescomastek.jbeam.model.vo.InstallationData;
import com.majescomastek.jbeam.model.vo.ReqCalendarVO;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.controls.Alert;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request to fetch the list of all calendars */
public static const CALENDAR_REQUEST:String = "CALENDAR_REQUEST";

/** The event constant used to denote the request to define the calendar */
public static const DEFINE_CALENDAR_REQUEST:String = "DEFINE_CALENDAR_REQUEST";

/** The event constant used to denote the click on the Calendar link */
public static const CALENDAR_CLICK:String = "CALENDAR_CLICK";

/** The calendar details to be shown */
private var _calendarVO:ReqCalendarVO;

/** Hold the data related to this module */
private var _moduleInfo:Object;

/** Hold the data related to the installation in this module */
private var _installationData:InstallationData;


/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:CalendarModuleFacade = 
		CalendarModuleFacade.getInstance(CalendarModuleFacade.NAME);		
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	Facade.removeCore(CalendarModuleFacade.NAME);
}

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

public function get installationData():InstallationData
{
	return _installationData;
}

public function set installationData(value:InstallationData):void
{
	_installationData = value;
}

/**
 * Handle the startup completion of the calendar module.
 */
public function handleStartupComplete():void
{	
	installationData = moduleInfo.installationData;	
	if(installationData == null)
	{
		installationData = CommonConstants.INSTALLATION_DATA;
	}
	if(!installationData) return;
	
	createCalendar();
	var calendarData:CalendarData = new CalendarData();
	calendarData.installationCode = installationData.installationCode;	
	sendDataEvent(CALENDAR_REQUEST, calendarData);		
	
}

/**
 * Handle the successful completion of the calendar list retrieval
 */
public function handleCalendarRetrieval(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;		 
	
	calendarList = list;
}
/**
 * Handle the successful completion of the calendar details retrieval
 * service by displaying non working days in the calendar
 */
public function handleCalendarDetailsRetrieval(data:Object):void
{
	var list:ArrayCollection = data as ArrayCollection;
	if(list == null || list.length == 0)	return;		 
	
	displayCalendarDates(list);
}

/**
 * Handle the successful completion of the define Calendar service.
 */
public function handleDefineCalendarRetrieval(data:Object):void
{
	AlertBuilder.getInstance().show("Calendar Defined successfully.");
//	AlertBuilder.getInstance().setCloseHandler(messageCloseHandler).show("Calendar Defined successfully.");
	messageCloseHandler();
}

private function messageCloseHandler():void
{
	var calendarData:CalendarData = new CalendarData();
	calendarData.installationCode = installationData.installationCode;
	sendDataEvent(CALENDAR_REQUEST, calendarData);	
} 



private function defineCalendar(evt:Event):void 
{
	var newCalendarData:CalendarData = null;
	var newReqCalendarVO:ReqCalendarVO = new ReqCalendarVO();
	var calDate:Date;
	var calDataList:ArrayCollection = new ArrayCollection();
	var calType:String = this.txtCalendarType.text;
	var calYear:String = this.nsYear.value.toString();
	var stopFlag:int = 0;
	 
	if (nonWorkingDays == null || nonWorkingDays.length == 0)
	{
		Alert.show("Please select a non working day for a calendar type");
		return;
	}
	else
	{
		for(var j:int =0 ; j < nonWorkingDays.length; j++)
		{
			if(nonWorkingDays.getItemAt(j) != null)
			{
				newCalendarData = new CalendarData();
				newCalendarData.installationCode = installationData.installationCode;				
				newCalendarData.calendarName = calType;
				newCalendarData.year = calYear;
				newCalendarData.remark = CommonConstants.HOLIDAY;
				newCalendarData.userId = CommonConstants.USER_PROFILE.userId;			
				calDate = new Date(nonWorkingDays.getItemAt(j) as String);
				newCalendarData.nonWorkingDate = calDate.getTime();			
				calDataList.addItem(newCalendarData);
			}
		}
		newReqCalendarVO.calendarList = calDataList;
		newReqCalendarVO.calendarData = newCalendarData;
		
		sendDataEvent(DEFINE_CALENDAR_REQUEST, newReqCalendarVO);
		
	}
}
