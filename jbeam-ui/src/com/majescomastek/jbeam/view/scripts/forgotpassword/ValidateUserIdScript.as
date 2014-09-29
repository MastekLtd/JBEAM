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
import com.majescomastek.common.events.ForgotPasswordEvent;
import com.majescomastek.jbeam.common.CommonUtils;

import mx.utils.StringUtil;

private var arrInputControls:Array = null;

/**
 * The function to call on creation complete of module.
 */
private function onCreationComplete():void {
	arrInputControls = [txtUserId];
	//txtUserId.setFocus();
}

/**
 * The function to dispath forgot password events.
 */
private function sendEvent(type:String,eventData:Object):void
{
	dispatchEvent(new ForgotPasswordEvent(type,eventData));
}

/**
 * The function to submit the user id to get the security questions.
 */
private function submitUserId():void{
	var arrValidators:Array = [valUserId];

    if (CommonUtils.validateControls(arrValidators)) {
		var userId:String = StringUtil.trim(txtUserId.text);
		
		sendEvent(ForgotPasswordEvent.VALIDATE_USER_ID_SUBMIT, userId);
	}
	else
		CommonUtils.showValidationMessage(arrInputControls, false);
}

/**
 * The function to handle the result of the web service.
 */
public function getSecurityQuestionsResult(result:Object):Object {
	var objInput:Object = new Object();

	objInput[ForgotPasswordModule.USER_ID] = StringUtil.trim(txtUserId.text);
	objInput[ForgotPasswordModule.ARRAY_OF_QUESTIONS] = result;
	
	return objInput;
}