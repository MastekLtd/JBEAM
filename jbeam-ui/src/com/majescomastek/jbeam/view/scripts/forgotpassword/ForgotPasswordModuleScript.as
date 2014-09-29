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
 * @author Gourav Rai
 * 
 *
 * $Revision:: 1                                                                                                      $
 *	
 * $Header::  $	
 *
 * $Log::  $
 * 
 * 
 */
import com.majescomastek.common.events.BaseEvent;
import com.majescomastek.common.events.ForgotPasswordEvent;
import com.majescomastek.jbeam.facade.forgotpassword.ForgotPasswordFacade;

import flash.events.Event;

import org.puremvc.as3.multicore.patterns.facade.Facade;

private var _moduleInfo:Object = null;

public static const VALIDATE_USER_ID_STATE:String = "ValidateUserIdState";
public static const VALIDATE_SECURITY_QUESTIONS_STATE:String = "ValidateSecurityQuestionsState";
public static const CHANGE_PASSWORD_STATE:String = "ChangePasswordState";

public static const USER_ID:String = "userId";
public static const ARRAY_OF_QUESTIONS:String = "arrQuestions";

 /**
  * The key to be retrieved from this module. Key must be unique and combination of Facade Name and Module UID.
  */
public function get key():String
{
	return ForgotPasswordFacade.NAME+this.uid;
}

/**
 * The key to be passed to this module.
 */
public function set key(value:String):void
{
	
}
		
/**
 * The data to be retrieved from this module.
 */
public function get moduleInfo():Object
{
	return this._moduleInfo;
}

/**
 * The data to be passed to this module.
 */
public function set moduleInfo(value:Object):void
{
	this._moduleInfo = value;
}

/**
 * To cleanup the module.
 */
public function cleanup():void
{
	Facade.removeCore(ForgotPasswordFacade.NAME);
}

/**
 * To cretion complete of module.
 */
private function onCreationComplete(event:Event):void
{
	ForgotPasswordFacade.getInstance(ForgotPasswordFacade.NAME).startup(this);
}

/**
 * To take action on state change event.
 */
private function onStateChange(strTitleKey:String):void
{
	this.dispatchEvent(new BaseEvent(ForgotPasswordEvent.CHANGE_TITLE, strTitleKey, true));
}