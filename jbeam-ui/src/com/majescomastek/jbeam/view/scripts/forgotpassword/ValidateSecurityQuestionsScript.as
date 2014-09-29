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
/**
 * This file forms part of the MajescoMastek 
 * SBS
 * Copyright (c) MajescoMastek 2009. 
 * All rights reserved.
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
 * 
 */
import com.majescomastek.common.events.ForgotPasswordEvent;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.UserProfile;
import com.majescomastek.jbeam.view.components.forgotpassword.ForgotPasswordModule;

import mx.collections.ArrayCollection;
import mx.utils.StringUtil;

private var arrInputControls:Array = null;
private var arrQuestions:ArrayCollection;
private var securityDetails:UserProfile;
public var userId:String;

/**
 * The function to call on creation complete of module.
 */
private function onCreationComplete():void {
	arrInputControls = [txtAnswer];
//	txtAnswer1.setFocus();
}

/**
 * The function to dispath forgot password events.
 */
private function sendEvent(type:String,eventData:Object):void
{
	dispatchEvent(new ForgotPasswordEvent(type,eventData));
}

/**
 * The function to show security questions for the user id.
 */
public function setSecurityQuestions(objOutput:Object):void 
{
	userId = objOutput[ForgotPasswordModule.USER_ID] as String;
	securityDetails = objOutput[ForgotPasswordModule.ARRAY_OF_QUESTIONS] as UserProfile;
	txtQuestion.label = securityDetails.hintQuestion;
	
}

/**
 * The function to submit the user id to get the security questions.
 */
private function submitAnswers():void
{
	var arrValidators:Array = [valAnswer];

    if (CommonUtils.validateControls(arrValidators)) 
    {
		securityDetails.hintAnswer = StringUtil.trim(txtAnswer.text);
		var objInput:Object = {
	 		'userProfile'	: securityDetails,
	 		'arrQuestions'	: arrQuestions
		};

		CommonUtils.closeErrorTip();
		
		sendEvent(ForgotPasswordEvent.VALIDATE_SECURITY_QUESTIONS_SUBMIT, objInput);		
	}
	else
		CommonUtils.showValidationMessage(arrInputControls, false);
}

/**
 * The function to handle the result of the web service.
 */
public function getValidateSecurityAnswersResult(data:Object):void
{
	if(data == null) return;
	
	if(data != null && data.responseType == CommonConstants.OK)
	{
		AlertBuilder.getInstance().show(CommonConstants.FORGOT_PASSWORD_SUCCEEDED);	
		this.dispatchEvent(new ForgotPasswordEvent(ForgotPasswordEvent.CLOSE_POPUP, null, true));
	}
}

/**
 * The function to handle the fault result of the web service.
 */
public function getValidateSecurityAnswersFaultResult(data:Object):void
{
	this.txtAnswer.text = "";	
}