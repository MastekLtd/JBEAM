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
 * @author Mandar Vaidya
 * 
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header::  $	
 *
 * $Log::  $
 * 
 * 
 */
import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;

import flash.events.Event;

import mx.controls.Label;
import mx.managers.PopUpManager;

private var arrInputControls:Array = null;
private var scheduleData:ProcessRequestScheduleData;


private function closePopup(event:Event):void
{
	PopUpManager.removePopUp(this);
}

public function handleStartupComplete(data:Object):void
{
	if(data == null)	return;
	
	scheduleData = ProcessRequestScheduleData(data['scheduleData']);
	this.username.text = scheduleData.endReason;	
}

