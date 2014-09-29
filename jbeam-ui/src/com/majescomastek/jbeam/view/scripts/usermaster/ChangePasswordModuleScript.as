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
include "ChangePasswordScript.as"
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.common.ProgramNameConstants;
import com.majescomastek.jbeam.facade.usermaster.ChangePasswordModuleFacade;
import com.majescomastek.jbeam.model.vo.UserCredential;
import com.majescomastek.jbeam.model.vo.UserProfile;

import flash.events.Event;

import mx.controls.Alert;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request for the cleanup of this module */
public static const CLEANUP_USER_PROFILE:String = 'CLEANUP_USER_PROFILE';

/** The event constant used to denote the request to Change password */
public static const CHANGE_PASSWORD_REQUEST:String = 'CHANGE_PASSWORD_REQUEST';

/** The event constant used to denote the notification to user profile */
public static const SHOW_USER_PROFILE:String = "SHOW_USER_PROFILE";

[Bindable]
/** Hold the data related to this module */
private var _moduleInfo:Object;

private var arrInputControls:Array = null;

[Bindable]
private var _userData:UserProfile;

[Bindable]
/** The variable holding the enable state of the user id */
private var enableUserId:Boolean;

/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	var facade:ChangePasswordModuleFacade =
		ChangePasswordModuleFacade.getInstance(ChangePasswordModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_USER_PROFILE);
	Facade.removeCore(ChangePasswordModuleFacade.NAME);
}

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

[Bindable]
public function get userData():UserProfile
{
	return _userData;
}

public function set userData(value:UserProfile):void
{
	_userData = value;
}

/**
 * Handle the startup completion of the ScheduleBatch module.
 */
public function handleStartupComplete():void
{	
	userData = CommonConstants.USER_PROFILE;
	arrInputControls = [txtOldPassword, txtNewPassword, txtConfirmNewPassword];
}

/**
 * Function will be called to validate the login 
 * details.
 */
public function validatePasswordDetails():Boolean
{
//	var arrValidators:Array = [valOldPassword, valNewPassword, valConfirmNewPassword];//, pwvPasswords];
//	if(!CommonUtils.validateControls(arrValidators))
//	{
//		CommonUtils.showValidationMessage(arrValidators, true);
//		return false;
//	}
//	else
//	{
//		return true;
//	}
	
	var arrValidators:Array = [valOldPassword, valNewPassword, valConfirmNewPassword];
	
	CommonUtils.removeErrorString(arrInputControls);
	
	if(txtOldPassword.text.length > 0 && (txtOldPassword.text == txtNewPassword.text))
	{
		txtNewPassword.errorString = resourceManager.getString('jbeam','different_new_password_required');
		CommonUtils.showValidationMessages([txtNewPassword]);			
		return false;
	}
	
	if(CommonUtils.validateControls(arrValidators))
	{
		if(txtNewPassword.text != txtConfirmNewPassword.text)
		{
			txtConfirmNewPassword.errorString = resourceManager.getString('jbeam','confirm_pwd_match');
			CommonUtils.showValidationMessages([txtConfirmNewPassword]);
			return false;
		}			
	}
	else
	{
		if(txtOldPassword.text.length == 0)
		{
			txtOldPassword.errorString = resourceManager.getString('jbeam', 'old_password_required');
		}
		if(txtNewPassword.text.length == 0)
		{
			txtNewPassword.errorString = resourceManager.getString('jbeam', 'new_password_required');			
		}
		if(txtConfirmNewPassword.text.length == 0)
		{
			txtConfirmNewPassword.errorString = resourceManager.getString('jbeam', 'confirm_password_required');			
		}
		CommonUtils.showValidationMessages(arrInputControls);
		return false;
	}	
	return true;
	
}

public function checkWithOldPassword(event:Event):void
{
	if(txtOldPassword.text.length > 0 && txtOldPassword.text == txtNewPassword.text)
	{
		txtNewPassword.errorString = resourceManager.getString('jbeam','different_new_password_required');
		CommonUtils.showValidationMessages([txtNewPassword]);			
	}
	else if(txtOldPassword.text.length == 0 && txtNewPassword.text.length == 0)
	{
		txtNewPassword.errorString = "";
	}
	else if(txtNewPassword.text.length == 0)
	{
		txtNewPassword.errorString = resourceManager.getString('jbeam', 'new_password_required');
	}
}

/**
 * Send request to Change password
 */
public function changePassword(event:Event):void
{	
	if(validatePasswordDetails())
	{
		var userCredentials:UserCredential = new UserCredential();
		userCredentials.userId = this.lblUserId.text;
		userCredentials.password = this.txtOldPassword.text;
		userCredentials.newPassword = this.txtNewPassword.text;			
		sendDataEvent(CHANGE_PASSWORD_REQUEST, userCredentials);
	}	
}

/**
 * Handle the Change password service result
 */
public function handleChangePasswordServiceResult(data:Object):void
{
	var data:Object = data;
	if(data != null && data.responseType == CommonConstants.OK)
	{
		resetFields();
		Alert.show(CommonConstants.CHANGE_PASSWORD_SUCCESS);
		if(CommonConstants.YES == CommonConstants.USER_PROFILE.forcePasswordFlag)
		{
			showUserProfile();
		}
	}
}

/** 
 * Display user profile  
 */
private function showUserProfile():void
{
	var data:Object = {
		'programName': ProgramNameConstants.USER_PROFILE_MODULE_PROGRAM_NAME
	};
	sendDataEvent(SHOW_USER_PROFILE, data);
}

