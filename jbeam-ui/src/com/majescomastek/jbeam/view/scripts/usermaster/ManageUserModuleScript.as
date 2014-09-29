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
include "ManageUserScript.as"

import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.facade.usermaster.ManageUserModuleFacade;
import com.majescomastek.jbeam.model.vo.ReqRoleDetails;
import com.majescomastek.jbeam.model.vo.ReqUserDetails;
import com.majescomastek.jbeam.model.vo.UserInstallationRole;
import com.majescomastek.jbeam.model.vo.UserProfile;

import flash.events.Event;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.events.CloseEvent;
import mx.resources.ResourceManager;

import org.puremvc.as3.multicore.patterns.facade.Facade;

/** The event constant used to denote the request for the cleanup of this module */
public static const CLEANUP_MANAGE_USER:String = 'CLEANUP_MANAGE_USER';

/** The event constant used to denote the request to create user */
public static const CREATE_USER_REQUEST:String = 'CREATE_USER_REQUEST';

/** The event constant used to denote the request to create user */
public static const CHECK_USER_EXISTS_REQUEST:String = "CHECK_USER_EXISTS_REQUEST";

/** The event constant used to denote the request to get user installation roles */
public static const GET_USER_INSTALLATION_ROLE_LIST_REQUEST:String = 'GET_USER_INSTALLATION_ROLE_LIST_REQUEST';

/** The event constant used to denote the request to fetch the list of all installations/environments */
public static const INSTALLATION_LIST_REQUEST:String = "INSTALLATION_LIST_REQUEST";

/** The event constant used to denote the request to fetch the list of all roles */
public static const ROLES_LIST_REQUEST:String = "ROLES_LIST_REQUEST";

[Bindable]
/** Hold the data related to this module */
private var _moduleInfo:Object;

[Bindable]
/** The dataprovider for the user list */
private var userInstallationRoleDetailsList:ArrayCollection;

[Bindable]
/** The dataprovider for the roles list */
private var roleList:ArrayCollection;

[Bindable]
private var installationsList:ArrayCollection;

[Bindable]
/** The dataprovider for the users list */
private var usersList:ArrayCollection;

[Bindable]
private var _userProfile:UserProfile;

[Bindable]
/** The variable holding the enable state of the user id */
private var enableUserId:Boolean;

[Bindable]
/** The variable holding the flag for manage user id */
private var manageUserId:Boolean;

[Bindable]
/** The variable holding the selected property of ADMIN role */
private var adminRoleSelected:Boolean;

[Bindable]
/** The variable holding the selected property of CONNECT role */
private var connectRoleSelected:Boolean;



/**
 * The function invoked when the creation of the module is complete.
 */
private function onCreationComplete(event:Event):void
{
	
	var facade:ManageUserModuleFacade =
		ManageUserModuleFacade.getInstance(ManageUserModuleFacade.NAME);
	facade.startup(this);	
}

/**
 * @inheritDoc
 */
public function cleanup():void
{
	sendEvent(CLEANUP_MANAGE_USER);
	Facade.removeCore(ManageUserModuleFacade.NAME);
}

public function get moduleInfo():Object
{
	return _moduleInfo;
}

public function set moduleInfo(value:Object):void
{
	_moduleInfo = value;
}

public function get userProfile():UserProfile
{
	return _userProfile;
}

public function set userProfile(value:UserProfile):void
{
	_userProfile = value;
}

/**
 * Handle the startup completion of the ScheduleBatch module.
 */
public function handleStartupComplete():void
{	
	CommonUtils.getIMEStatus();
	
	enableUserId = true;
	connectRoleSelected = true;
	enableNumberEntry();
	
	var reqRoleDetails:ReqRoleDetails = new ReqRoleDetails();	
	reqRoleDetails.userProfile = CommonConstants.USER_PROFILE;
	
	sendDataEvent(INSTALLATION_LIST_REQUEST, reqRoleDetails.userProfile);
	sendDataEvent(ROLES_LIST_REQUEST, reqRoleDetails);
	sendEvent(GET_USER_INSTALLATION_ROLE_LIST_REQUEST);	
}


/**
 * Handle the get user installation roles service result
 */
public function handleGetUserInstallationRoleDetailsServiceResult(list:ArrayCollection):void
{
	for each (var userInstallationRole:UserInstallationRole in list)
	{
		if(userInstallationRole.roleId == CommonConstants.ADMIN 
					|| userInstallationRole.roleId == CommonConstants.CONNECT )
		{
			userInstallationRole.installationCode = "All Installations";
		}
	}
	userInstallationRoleDetailsList  = list;
}

/**
 * Handle the get user details service result
 */
public function handleGetUserDetailsServiceResult(data:Object):void
{
	manageUserId = true;
	this.mainPanel.title = resourceManager.getString("jbeam", "edit_user");
	mapUserDetails(data);
}
 
/**
 * Handle the get users service result
 */
public function handleGetRolesListServiceResult(list:ArrayCollection):void
{
	if(list == null || list.length == 0)	return;		 
	
	roleList = list;
} 

/**
 * Function will be called to validate the user details.
 */
public function validateUserDetails():Boolean
{
	var arrValidators:Array = [valUserId, valUserName, valEmailId, valEffDate, valExpDate, strEffectiveDate, strExpiryDate];
	if(!CommonUtils.validateControls(arrValidators))
	{
		CommonUtils.showValidationMessage(arrValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}

public function validateUserId():Boolean
{
	var arrValidators:Array = [valUserId];
	if(!CommonUtils.validateControls(arrValidators))
	{
		CommonUtils.showValidationMessage(arrValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * Function will be called to validate the details.
 */
public function validateDates():Boolean
{
	var compareResult:Boolean = false;
	if(txtEffectiveDate.text.length > 0 && txtExpiryDate.text.length > 0)
	{
		var effDate:Date = new Date(Date.parse(this.txtEffectiveDate.text));
		var expDate:Date = new Date(Date.parse(this.txtExpiryDate.text));
		compareResult = CommonUtils.compareDates(effDate, expDate);
		if(!compareResult)
			AlertBuilder.getInstance().show(resourceManager.getString('jbeam','exp_date_greater_than_eff_Date'));
	}
	return compareResult;
}


/**
 * Send request to create user
 */
public function createUser(event:Event):void
{
	if(validateUserId()){
		var userProfile:UserProfile = new UserProfile();
		userProfile.userId = txtUserId.text;
		sendDataEvent(CHECK_USER_EXISTS_REQUEST, userProfile);		
	}
}


public function handleCheckUserExistsResult(data:Object):void
{
	if(data == null) return;
	
	var userProfile:UserProfile = data.userProfile;
	if(userProfile == null)
	{
		validateAndCreateUser();
	}
	else
	{
		if(!manageUserId)
		{
			Alert.show(resourceManager.getString("jbeam", "duplicate_user_id"),
				"Warning",Alert.OK|Alert.CANCEL, this,updateUserDetails,null,Alert.OK);
			return;			
		}
		else
		{
			validateAndCreateUser();			
		}
	}
}

public function updateUserDetails(eventObj:CloseEvent):void
{
	//if
	if(eventObj.detail==Alert.OK)
	{
		manageUserId = true;
		this.mainPanel.title = resourceManager.getString("jbeam", "edit_user");
		validateAndCreateUser();
	}
	else if(eventObj.detail==Alert.CANCEL)
	{
		this.txtUserId.setFocus();
		return;
	}
}


private function validateAndCreateUser():void
{
	if(validateUserDetails() && validateDates())
	{
		var reqUserDetails:ReqUserDetails = new ReqUserDetails();
		reqUserDetails.userProfile = createUserProfile();
		reqUserDetails.userInstallationRoleList = createUserInstallationRoleList();
		if(reqUserDetails.userInstallationRoleList.length > 1)
		{
			sendDataEvent(CREATE_USER_REQUEST, reqUserDetails);
		}
		else if(reqUserDetails.userInstallationRoleList.length <= 1)
		{
			AlertBuilder.getInstance().show("Please select a valid role (OPERATOR/ USER).");
			return;
		}
	}
	
}

/**
 * Handle the create user service fault
 */
public function handleCreateUserServiceFault(data:Object):void
{
	AlertBuilder.getInstance().show(CommonConstants.CREATE_USER_PART_SUCCEEDED);			
	clearUserData();
	sendEvent(GET_USER_INSTALLATION_ROLE_LIST_REQUEST);
}
/**
 * Handle the create user service result
 */
public function handleCreateUserServiceResult(data:Object):void
{
	var data:Object = data;
	if(data != null && data.responseType == CommonConstants.OK)
	{
		if(manageUserId)
		{
			AlertBuilder.getInstance().show(CommonConstants.USER_DETAILS_MODIFICATION_SUCCEEDED);
		}
		else
		{
			AlertBuilder.getInstance().show(CommonConstants.CREATE_USER_SUCCEEDED);			
		}
		clearUserData();
		sendEvent(GET_USER_INSTALLATION_ROLE_LIST_REQUEST);
	}
}
/**
 * Handle the successful completion of the installation list retrieval
 * service by creating pods, creating timers etc.
 */
public function handleInstallationListRetrieval(list:ArrayCollection):void
{
	if(list == null || list.length == 0)	return;		 
	
	installationsList = list;
}
        
