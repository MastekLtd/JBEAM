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
include "UserProfileScript.as"
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.facade.usermaster.UserProfileModuleFacade;
import com.majescomastek.jbeam.model.vo.ReqUserDetails;
import com.majescomastek.jbeam.model.vo.UserProfile;

import flash.events.Event;

import mx.controls.Alert;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request for the cleanup of this module */
public static const CLEANUP_USER_PROFILE:String = 'CLEANUP_USER_PROFILE';

/** The event constant used to denote the request to create user */
public static const EDIT_USER_PROFILE_REQUEST:String = 'EDIT_USER_PROFILE_REQUEST';

/** The event constant used to denote the request to get user details */
public static const GET_USER_DETAILS_REQUEST:String = 'GET_USER_DETAILS_REQUEST';

/** The event constant used to denote the request to navigate to default view */
public static const NAVIGATE_DEFAULT_VIEW_REQUEST:String = "NAVIGATE_DEFAULT_VIEW_REQUEST";

/** The event constant used to denote the notification to installation details */
public static const SHOW_INSTALLATION_DETAILS:String = "SHOW_INSTALLATION_DETAILS";

/** Event constant to indicate the click of Logout link */
public static const LOGOUT_CLICK:String = 'LOGOUT_CLICK';

[Bindable]
/** Hold the data related to this module */
private var _moduleInfo:Object;

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
	var facade:UserProfileModuleFacade =
		UserProfileModuleFacade.getInstance(UserProfileModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_USER_PROFILE);
	Facade.removeCore(UserProfileModuleFacade.NAME);
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
	CommonUtils.getIMEStatus();
	
	var userProfile:UserProfile = CommonConstants.USER_PROFILE;
	sendDataEvent(GET_USER_DETAILS_REQUEST, userProfile);
}

/**
 * Handle the get user details service result
 */
public function handleGetUserDetailsServiceResult(data:Object):void
{
	mapUserDetails(data);
	hintAnswerEnable = true;
	hintAnswerVisible = true;
	hintQuestionEnable = true;
}
 
/**
 * Function will be called to validate the login 
 * details.
 */
public function validateUserDetails():Boolean
{
	var arrValidators:Array = [valUserId, valUserName, valEmailId, 
					valHintQuestion, valHintAnswer];
	return CommonUtils.validateControls(arrValidators);
}

/**
 * Send request to create user
 */
public function editUserProfile(event:Event):void
{
	if(validateUserDetails())
	{
		var reqUserDetails:ReqUserDetails = new ReqUserDetails();
		reqUserDetails.userProfile = preapreUserProfileToUpdate();
		sendDataEvent(EDIT_USER_PROFILE_REQUEST, reqUserDetails);
	}
}

/**
 * Handle the create user service result
 */
public function handleEditUserProfileServiceResult(data:Object):void
{
	var data:Object = data;
	if(data != null && data.responseType == CommonConstants.OK)
	{
		if(CommonConstants.YES == CommonConstants.USER_PROFILE.forcePasswordFlag)
		{
			Alert.show(CommonConstants.EDIT_USER_PROFILE_SUCCEEDED + 
				"\nAs this is your first login after password change, you have been logged out. " +
				"Please login again with new credentials.");	
			sendEvent(LOGOUT_CLICK);
		}
		else
		{
			Alert.show(CommonConstants.EDIT_USER_PROFILE_SUCCEEDED);			
		}
	}
}

