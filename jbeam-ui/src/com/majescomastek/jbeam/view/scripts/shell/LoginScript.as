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
include "MonitorServicesUploadScript.as"

import com.majescomastek.flexcontrols.SelectOneComboBox;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.JbeamServer;
import com.majescomastek.jbeam.model.vo.ReqUserDetails;
import com.majescomastek.jbeam.model.vo.ReqVersion;
import com.majescomastek.jbeam.model.vo.UserInstallationRole;
import com.majescomastek.jbeam.model.vo.UserProfile;
import com.majescomastek.jbeam.view.components.forgotpassword.ForgotPasswordPopup;
import com.majescomastek.jbeam.view.components.shell.Footer;
import com.majescomastek.jbeam.view.components.shell.Shell;
import com.majescomastek.jbeam.view.components.shell.TopBanner;

import flash.events.Event;
import flash.events.IOErrorEvent;
import flash.events.KeyboardEvent;
import flash.net.URLLoader;
import flash.net.URLRequest;
import flash.ui.Keyboard;

import mx.collections.ArrayCollection;
import mx.core.IFlexDisplayObject;
import mx.managers.PopUpManager;
import mx.resources.ResourceManager;
import mx.rpc.Fault;

/** MainPage screen index in view stack.*/
public static const LOGIN_SUBMIT:String = 'LOGIN_SUBMIT';

/** The event constant used to denote the click on the Check Compatibility link.*/
public static const CHECK_COMPATIBILITY:String = 'CHECK_COMPATIBILITY';

/** Change server screen index in view stack.*/
public static const CHANGE_SERVER_SUBMIT:String = 'CHANGE_SERVER_SUBMIT';

/** The event constant used to denote the click on the forgot password link.*/
public static const FORGOT_PASSWORD_CLICK:String = 'FORGOT_PASSWORD_CLICK';

/** Back to login screen index in view stack.*/
public static const BACK_TO_LOGIN:String = 'BACK_TO_LOGIN';

public static const CLEANUP_LOGIN_VIEW:String = 'CLEANUP_LOGIN_VIEW';

/** The event constant used to denote the change in the server configuration */
public static const SERVER_CONFIGURATION_CHANGE:String = "SERVER_CONFIGURATION_CHANGE";
	
[Bindable]
public var profileUpdter:UserProfile;

[Bindable]
public var enableLoginButton:Boolean;

[Bindable]
private var clientLogo:String;
[Bindable]
private var mastekLogo:String;

private  var _clientLogo:String = null;
private  var image_path:String = null;
private  var defaultImage:String = null;
private function loadFile():void
{
	clientLogo = resourceManager.getString('Image', 'stg_billing_logo');
	trace(clientLogo);
	mastekLogo = resourceManager.getString('Image', 'mastek_logo')
	
}
/*[Bindable]
public var serverList:ArrayCollection;*/

/**
 * Function to set focus on userId
 * 
 */  
private function setFocusOnIt():void
{
	this.txtUserId.setSelection(0,0);
}
/**
 * Handle the successful retrieval of the list of all the available servers.
 */
public function handleServerConfigurationRetrieval(result:ArrayCollection):void
{
	this.serverList.removeAll();
	if (!result) return;
	var jb:JbeamServer = new JbeamServer();
	jb.ipAddress = CommonConstants.DROP_DOWN_CODE_DEFAULT_VALUE;
	jb.serverName = resourceManager.getString('jbeam', 'default_label');
	
	this.serverList.addItem(jb);
	for each(var _data:JbeamServer in result)
	{
		this.serverList.addItem(_data);
	}
	this.serverList.refresh();
	cmbServerList.dataProvider = serverList;
/*	data.refresh();
	//serverList = data;	
	cmbServerList.dataProvider = data;*/
}

/**
 * Handle the change in the server configuration drop down option.
 */
private function changeEvt(event:Event):void
{
	var value:String = event.currentTarget.selectedLabel;
	// Do nothing if the user selects SELECT
	if(value != CommonConstants.DROP_DOWN_CODE_DEFAULT_VALUE)
	{
		var server:JbeamServer = JbeamServer(event.currentTarget.selectedItem);
		CommonConstants.SERVER_URL = "http://" + server.ipAddress + ":" + server.port + "/MonitorServices";
		trace("Setting server URL = " + CommonConstants.SERVER_URL);
		CommonConstants.SERVER_URL_IM = "http://" + server.ipAddress + ":" + server.port;
		
//		this.loadImages("client_logo.jpg", resourceManager.getString('Image', 'stg_billing_logo'));
//		this.loadImages("client_logo1.jpg", resourceManager.getString('Image', 'stg_billing_logo_small'));
//		var footer:Footer = Shell.shellViewStack.selectedChild as Footer;
//		footer.loadLogo();
//		var topBanner:TopBanner = Shell.shellViewStack.selectedChild as TopBanner;
//		topBanner.loadFile();
//		sendDataEvent(SERVER_CONFIGURATION_CHANGE, resourceManager.getString('Image', 'stg_billing_logo_small'));
	}
}

/**
 * Implement the IViewComponent method which would be called when desctruction
 * of this module is desired
 */
public function cleanup():void
{
	sendEvent(CLEANUP_LOGIN_VIEW);
}

public function handleMastekLogo(data:Object):void
{
	this.imgMastekLogo.source = data as String;
	mastekLogo = data as String;
}

private function loadImages(externalImageParam:String, defaultImageParam:String):void
{
	var loader:URLLoader;
	var request:URLRequest; 
	image_path = CommonConstants.SERVER_URL_IM + "/images/" + externalImageParam;
	trace("In loadImages >> "+ image_path);
	defaultImage = defaultImageParam;
	loader = new URLLoader();
	loader.addEventListener(Event.COMPLETE,onComplete);
	loader.addEventListener(IOErrorEvent.IO_ERROR,onError);		
	request = new URLRequest(image_path); 
	loader.load(request);
}

public  function onError(event:IOErrorEvent):void
{
	//				Alert.show(event.toString());
	trace("Image Loading Failed");
	trace("In ioError_handler >> default image set as "+defaultImage);
	this.clientLogo1.source = defaultImage;
	CommonConstants.COMPANY_LOGO = defaultImage;
}

private  function onComplete(event:Event):void
{
	trace("Image Loading Completed");
	trace("In onComplete >> image path set as "+image_path);
	this.clientLogo1.source = image_path;
	CommonConstants.COMPANY_LOGO = image_path;
}
/**
 * Function will be called to validate the login 
 * details.
 */
public function validateLoginDetails():Boolean
{
	var arrValidators:Array = [valUserID, valPassword, serverValidator];
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
 * Retrieve the UserProfile object corresponding to the entered
 * user details. 
 */
private function getLoginDetails():UserProfile
{
	var userProfile:UserProfile = new UserProfile();
	userProfile.userId = this.txtUserId.text;
	userProfile.password = this.txtPassword.text;
	return userProfile;
}


	
/**
 * Event handler function called when key pressed in the
 * password field.
 * If pressed key is ENTER then LOGIN_SUBMIT event will
 * be dispathched.
 */
private function onEnterClick(evt:KeyboardEvent):void
{
	if(evt.keyCode == Keyboard.ENTER)
	{
		submitLoginDetails();//onCheckCompatibility(); //
	}	
}

/** 
 * The function used to reset the data.
 */
public function resetData():void
{
	this.txtUserId.text = this.txtPassword.text
		= this.txtUserId.errorString = this.txtPassword.errorString = null;
	enableLoginButton = true;
}

/**
 * The function invoked when the SUBMIT link is clicked.
 */
private function submitLoginDetails():void
{
	if(validateLoginDetails())
	{
		sendDataEvent(LOGIN_SUBMIT, getLoginDetails());
	}
}

/**
 * Handle the success of the user authentication service.
 */
public function handleAuthenticationFailure(fault:Fault):void
{
	enableLoginButton = true;
	var faultString:String = fault.faultString;
	AlertBuilder.getInstance().show(faultString);	
}
/**
 * Handle the success of the user authentication service.
 */
public function handleAuthenticationSuccess(resUserDetails:ReqUserDetails):void
{
	enableLoginButton = false;
	CommonUtils.setLoggedInUser(resUserDetails.userProfile);
	CommonUtils.setUserInstallationRoles(resUserDetails.userInstallationRoleList);
	setRoles(resUserDetails.userInstallationRoleList);
	setAdminRole(resUserDetails.userProfile);
}

/**
 * Handle the success of the check compatibility service.
 */
public function handleCompatibilitySuccess(response:Object):void
{
//	AlertBuilder.getInstance().show("Response = " + response.responseType);
	if(response.responseType == CommonConstants.OK)
	{
		submitLoginDetails();
	}
	
}
  
/**
 * Function to set the admin roles for the session
 */    
private function setAdminRole(userDetails:UserProfile):void
{
	if(userDetails != null && userDetails.adminRole == "Y")
	{
		CommonUtils.setHaveAdminRole(true);
	}
}
/**
 * Function to set the user roles for the session
 */    
private function setRoles(userInstallationRoleDetailsList:ArrayCollection):void
{
	if(userInstallationRoleDetailsList != null &&
		userInstallationRoleDetailsList.length > 0)
	{
		for each(var userInstallationRole:UserInstallationRole 
			in userInstallationRoleDetailsList)
		{
			switch(userInstallationRole.roleId)
			{				
				case CommonConstants.USER:				
					CommonUtils.setHaveUserRole(true);
					break;
				case CommonConstants.OPERATOR:
					CommonUtils.setHaveOperatorRole(true);
					break;
			}
			
		}
	}	
}  
    
/**
 * Function invoked when the 'Configure Server' link is clicked.
 */    
private function openServerConfigurationWindow():void
{
	sendEvent(CHANGE_SERVER_SUBMIT);
}

/**
 * Function invoked when the 'Forgot Password' link is clicked.
 */    
private function openForgotPasswordWindow():void
{
	if(validateServerDetails())
	{
		openPopup(ForgotPasswordPopup);
	}
}

/**
 * Function invoked when the 'Check Compatibility' link is clicked.
 */    
private function onCheckCompatibility():void
{
	if(validateServerDetails())
	{
		sendDataEvent(CHECK_COMPATIBILITY, getVersionDetails());
	}
}

/**
 * Retrieve the version details. 
 */
private function getVersionDetails():ReqVersion
{
	var version:ReqVersion = new ReqVersion();
	version.majorVersion = ResourceManager.getInstance().getString("Login","major_version");
	version.minorVersion = ResourceManager.getInstance().getString("Login","minor_version");
	return version;
}
/**
 * Function will be called to validate the server details.
 */
public function validateServerDetails():Boolean
{
	var arrServerValidators:Array = [serverValidator];
	if(!CommonUtils.validateControls(arrServerValidators))
	{
		CommonUtils.showValidationMessage(arrServerValidators, true);
		return false;
	}
	else
	{
		return true;
	}
}
/**
 * This function is used to show the title window.  
 */
private function openPopup(className:Class):void 
{
	var popupModule:IFlexDisplayObject = PopUpManager.createPopUp(this, className, true);
	PopUpManager.centerPopUp(popupModule);
}
