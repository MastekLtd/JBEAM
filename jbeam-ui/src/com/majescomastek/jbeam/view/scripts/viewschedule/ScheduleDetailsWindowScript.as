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

import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.common.ScheduleUtils;
import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;
import com.majescomastek.jbeam.model.vo.ProgressLevelData;
import com.majescomastek.jbeam.model.vo.RequestListenerInfo;

import flash.events.Event;

import mx.collections.ArrayCollection;

[Bindable]
private var processRequestScheduleData:ProcessRequestScheduleData;

[Bindable]
private var batchObjectList:ArrayCollection;

[Bindable]
private var batchObjectTitle:String;

/** The event constant used for denoting the click of refresh button */
public static const REFRESH_CLICK:String = "REFRESH_CLICK";

/** The event constant used for denoting the cleanup request for this view */
public static const CLEANUP_BATCH_OBJECT_WINDOW:String = "CLEANUP_BATCH_OBJECT_WINDOW";

/** The event constant used to denote the request to fetch the list of failed objects */
public static const REQUEST_BATCH_OBJECT_DATA:String = "REQUEST_BATCH_OBJECT_DATA";

/**
 * Handle the startup completion of this native window.
 */
public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
	processRequestScheduleData = ProcessRequestScheduleData(data['scheduleData']);
	derivePageState();
	populateScheduleDetails();
}

private function populateScheduleDetails():void
{
	recurrenceStepper.value = processRequestScheduleData.recur;
	populateWeekDays(processRequestScheduleData.onWeekDay);
	if(processRequestScheduleData.endDt != null)
	{
		initEndOnTimeList();	
		grdEndOnOccur.visible = false;
	}
	else
	{
		endOnOccuranceStepper.value = processRequestScheduleData.endOccur;
		grdEndOnDate.visible = false;
		grdEndOntime.visible = false;
	}
	createSkipFlagList();
	createKeepAliveList();
	lblEndReason.text = processRequestScheduleData.endReason;
}

private function populateWeekDays(str:String):void
{
	if(str.charAt(0) == "1")
		this.chkSunday.selected = true;
	if(str.charAt(1) == "1")
		this.chkMonday.selected = true;
	if(str.charAt(2) == "1")
		this.chkTuesday.selected = true;
	if(str.charAt(3) == "1")
		this.chkWednesday.selected = true;
	if(str.charAt(4) == "1")
		this.chkThursday.selected = true;
	if(str.charAt(5) == "1")
		this.chkFriday.selected = true;
	if(str.charAt(6) == "1")
		this.chkSaturday.selected = true;
}


/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	batchObjectTitle = resourceManager.getString('jbeam','sch_details_title')
						+ " - Schedule # " + processRequestScheduleData.schId;
}

/**
 * The function invoked when the title window is closed.
 */
public function performCleanup(event:Event):void
{
	this.close();
	cleanup();
}

/**
 * The function invoked when the refresh button is clicked.
 */
private function refreshClick(event:Event):void
{
	sendDataEvent(REFRESH_CLICK, processRequestScheduleData);
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_BATCH_OBJECT_WINDOW);	
}

/**
 * Handle the success of the retrieval of failed objects data.
 */
public function handleScheduleDetailsRetrieval(data:Object):void
{
	if(data == null)	return;
	
//	batchObjectList = ArrayCollection(data);
}

[Bindable]
private var skipFlagList:ArrayCollection;

[Bindable]
private var keepAliveList:ArrayCollection;

[Bindable]
public var endOnTimeMinList:ArrayCollection;

[Bindable]
public var endOnTimeSecList:ArrayCollection;

[Bindable]
public var endOnTimeList:ArrayCollection;

/**
 * To populate the skip flag combo box 
 */
private function createSkipFlagList():void
{
	skipFlagList = new ArrayCollection();
	
	skipFlagList.addItem({flagObject: ScheduleUtils.showSkipSchedule(processRequestScheduleData.skipFlag),
		flagValue: processRequestScheduleData.skipFlag});	
}

/**
 * To populate the Keep Alive combo box 
 */
private function createKeepAliveList():void
{
	keepAliveList = new ArrayCollection();
	keepAliveList.addItem({flagObject: ScheduleUtils.showKeepAlive(processRequestScheduleData.keepAlive),
		flagValue: processRequestScheduleData.keepAlive});
}

/**
 * To populate the end on time 
 */
private function initEndOnTimeList():void
{
	var executeOnHH:String =  CommonUtils.formatDate(processRequestScheduleData.endDt, "HH");
	var executeOnMM:String =  CommonUtils.formatDate(processRequestScheduleData.endDt, "NN");
	var executeOnSS:String =  CommonUtils.formatDate(processRequestScheduleData.endDt, "SS");
	
	this.dcEndOnDate.text = CommonUtils.formatDate(processRequestScheduleData.endDt, CommonConstants.US_DATE_FORMAT);
	endOnTimeList = new ArrayCollection();
	endOnTimeList.addItem({object:executeOnHH, value:executeOnHH });
	
	endOnTimeMinList = new ArrayCollection();
	endOnTimeMinList.addItem({object:executeOnMM, value:executeOnMM});
	
	endOnTimeSecList = new ArrayCollection();
	endOnTimeSecList.addItem({object:executeOnSS, value:executeOnSS});
	
}	