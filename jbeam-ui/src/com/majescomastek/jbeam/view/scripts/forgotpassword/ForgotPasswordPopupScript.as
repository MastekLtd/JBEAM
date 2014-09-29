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
include "../../../common/CommonScript.as"
import com.majescomastek.common.events.BaseEvent;
import com.majescomastek.common.events.ForgotPasswordEvent;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.common.SWFConstants;

import flash.events.Event;

import mx.managers.PopUpManager;
import mx.utils.StringUtil;

private var arrInputControls:Array = null;

/**
 * The function to call on module creation complete event.
 */
public function onCreationComplete():void
{
	forgotPwdModule.moduleLoad(SWFConstants.FORGOT_PASSWORD_MODULE);	
	this.addEventListener(ForgotPasswordEvent.CHANGE_TITLE, changeTitle, false, 0, true);
	this.addEventListener(ForgotPasswordEvent.CLOSE_POPUP, closePopup, false, 0, true);
}

private function closePopup(event:Event):void{
	
	forgotPwdModule.unloadModule();
	
	this.removeEventListener(ForgotPasswordEvent.CHANGE_TITLE, changeTitle, false);
	this.removeEventListener(ForgotPasswordEvent.CLOSE_POPUP, closePopup, false);

	PopUpManager.removePopUp(this);
}

private function changeTitle(event:BaseEvent):void {
	this.title = resourceManager.getString('jbeam', event.eventData.toString());
}

