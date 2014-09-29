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

import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;

import flash.events.Event;

[Bindable]
private var schendReasonTitle:String;

/** The event constant used for denoting the cleanup request for this view */
public static const CLEANUP_FAILED_OBJECT_WINDOW:String = "CLEANUP_FAILED_OBJECT_WINDOW";

private var scheduleData:ProcessRequestScheduleData;

/**
 * Handle the startup completion of this native window.
 */
public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
	scheduleData = ProcessRequestScheduleData(data['scheduleData']);
	
	this.endReason.text = scheduleData.endReason;
	derivePageState();	
}



/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	schendReasonTitle = "Schedule End Reason for id # " + scheduleData.schId;
}

/**
 * The function invoked when the title window is closed.
 */
public function performCleanup(event:Event):void
{
	this['close']();
	cleanup();
}


/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_FAILED_OBJECT_WINDOW);	
}

