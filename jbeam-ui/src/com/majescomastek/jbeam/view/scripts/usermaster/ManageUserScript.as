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
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.common.CommonUtils;
import com.majescomastek.jbeam.model.vo.RoleData;
import com.majescomastek.jbeam.model.vo.UserInstallationRole;
import com.majescomastek.jbeam.model.vo.UserProfile;

import flash.events.Event;
import flash.events.FocusEvent;
import flash.filters.DropShadowFilter;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.controls.List;
import mx.events.ListEvent;
import mx.resources.ResourceManager;

[Bindable]
public var selectedItem:Object;

[Bindable]
public var clearInstallationRoleList:ArrayCollection = new ArrayCollection();

[Bindable]
public var selectedInstallationList:Array = new Array();

[Bindable]
public var selectedRolesList:Array = new Array();
  
[Bindable]
public var installationRoleList:ArrayCollection;

private function clearValidations():void
{
	txtUserId.errorString = "";
	txtUserId.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	txtUserName.errorString = "";
	txtUserName.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	txtEmailId.errorString = "";
	txtEmailId.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	txtEffectiveDate.errorString = "";
	txtEffectiveDate.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
	txtExpiryDate.errorString = "";
	txtExpiryDate.filters = [new DropShadowFilter(0,0,0,0,0,0,0,0)];
}

public function clearUserData():void 
{
	this.txtUserId.text = "";
	enableUserId = true;
	this.txtUserName.text = "";
	this.txtEmailId.text = "";
	this.txtEffectiveDate.text = "";
	this.txtExpiryDate.text = "";
	this.txtPhoneNo.text = "";
	this.chkAdminRole.selected = false;
//	connectRoleSelected = false;
	this.chkResetPassword.selected = false;
	this.dgInstallationRoles.dataProvider = null;
	clearLists();
	clearValidations();
	manageUserId = false;
	this.mainPanel.title = resourceManager.getString("jbeam", "create_user");
}

private function clearLists():void 
{
	this.lstRolesList.selectedIndex = -1;
	this.lstInstallationList.selectedIndex = -1;	
}
   

private function removeInstallationRole(event:Event):void
{	
	var tempInstRolesList:ArrayCollection = 
		dgInstallationRoles.dataProvider as ArrayCollection;
 	var selectedIndices:Array = dgInstallationRoles.selectedIndices; 		
		
	for(var k:uint = 0; k < tempInstRolesList.length; k++)
	{
		for(var j:uint = 0; j < selectedIndices.length; j++)
		{
			if(CommonConstants.USER == tempInstRolesList.getItemAt(selectedIndices[j]).roleId)
			{
				if(CommonConstants.OPERATOR == tempInstRolesList.getItemAt(k).roleId)
				{
					if(tempInstRolesList.getItemAt(k).installationCode == 
						tempInstRolesList.getItemAt(selectedIndices[j]).installationCode)
					{
						selectedIndices.push(k);
					}					
				}	
			}	
		}
		
	}
	
	if(selectedIndices.length == 0)
	{
		AlertBuilder.getInstance().show("Please select a record");
	}
	else
	{
		for(var i:uint = 0; i < selectedIndices.length; i++)
		{
			tempInstRolesList.removeItemAt(selectedIndices[i]);	
		}
	
		dgInstallationRoles.dataProvider = tempInstRolesList;
	}
}



private function selectItems(event:ListEvent):void
{
	this.selectedItem = List(event.target).selectedItem;
}

private function addToInstallationRoleList(event:Event):void
{	
		
	installationRoleList = dgInstallationRoles.dataProvider as ArrayCollection;
	if(installationRoleList == null)
	{
		installationRoleList = new ArrayCollection();
	}	
	
	if(lstRolesList.selectedItems.length == 0)
	{
		Alert.show("Please select a role");
	}
	else if(lstInstallationList.selectedItems.length == 0)
	{
		Alert.show("Please select an installation");
	}	
	else 
	{		
		selectedInstallationList = lstInstallationList.selectedItems;
		selectedRolesList = lstRolesList.selectedItems;
		
		if (selectedInstallationList.length > 0 && roleList.length > 0)
		{
			for each(var roleObject:Object in selectedRolesList)
			{
				for(var i:uint = 0; i < roleList.length; ++i)
				{
					var roleData:RoleData = roleList.getItemAt(i) as RoleData;				
					if(roleObject.roleId == roleData.roleId && 
										roleObject.roleId == CommonConstants.OPERATOR)
					{
						if(roleList.getItemAt(i + 1).roleId == CommonConstants.USER)
						{
							selectedRolesList.push({roleId: roleList.getItemAt(i + 1).roleId});
						}						
					}
				}				
			}
		}
		
		if (selectedInstallationList.length>0 && selectedRolesList.length>0  )
		{
			for each(var instObj:Object in selectedInstallationList)
			{
				for each(var roleObj:Object in selectedRolesList)
				{
					var instRoles:Object = {roleId:roleObj.roleId
											,installationCode:instObj.installationCode};
					if(containsInAL(installationRoleList,instRoles) == 0)
					{
						installationRoleList.addItem(instRoles);
					}
					else
					{
						AlertBuilder.getInstance().
							show("This Role ['" + roleObj.roleId + "'] " + 
									"- Installation ['" + instObj.installationCode + "']" + 
									" combination already exists.");
									
						clearLists();
					}
				} 
			}
			
		}
		
		selectedRolesList.pop();
		selectedInstallationList.pop();
		
		dgInstallationRoles.dataProvider = installationRoleList;
		lstInstallationList.selectedIndex = -1;
		lstRolesList.selectedIndex = -1;
	}
	
}
private function containsInAL(arrayCollection:ArrayCollection, item:Object):int 
{
	var val:int = 0;
	for each (var obj:Object in arrayCollection) 
	{
		if (obj.roleId == item.roleId 
			&& obj.installationCode == item.installationCode) 
		{
			val = 1;		
		}
	}
	return val;
}



/**
 * Creates the userProfile object from data entered by user 
 */
private function createUserInstallationRoleList():ArrayCollection
{
	var preparedUserInstallationRoleList:ArrayCollection = new ArrayCollection;
	var availableInstallationRoleList:ArrayCollection = 
				dgInstallationRoles.dataProvider as ArrayCollection;

	if(availableInstallationRoleList != null && availableInstallationRoleList.length > 0 )
	{
		for each(var userInstallationRoleObject:Object in
						availableInstallationRoleList)
		{
			var userInstallationRole:UserInstallationRole 
					= createUserInstallationRole(txtUserId.text, userInstallationRoleObject.installationCode,
						userInstallationRoleObject.roleId);
			preparedUserInstallationRoleList.addItem(userInstallationRole);
		}
	}
	if(this.chkConnectRole.selected == true)
	{
		var connectRole:UserInstallationRole = createUserInstallationRole(txtUserId.text, null, CommonConstants.CONNECT);
		preparedUserInstallationRoleList.addItem(connectRole);
	}
	if(this.chkAdminRole.selected == true)
	{
		var adminRole:UserInstallationRole = createUserInstallationRole(txtUserId.text, null, CommonConstants.ADMIN);
		preparedUserInstallationRoleList.addItem(adminRole);
	}
	
	return preparedUserInstallationRoleList;
}

/**
 * Creates the UserInstallationRole object from data entered by user 
 */
private function createUserInstallationRole(user:String, instCode:String, role:String):UserInstallationRole
{
	var userInstRole:UserInstallationRole = new UserInstallationRole();
	userInstRole.userId = user;
	userInstRole.installationCode = instCode
	userInstRole.roleId = role;
	return 	userInstRole;
}

/**
 * Creates the userProfile object from data entered by user 
 */
private function createUserProfile():UserProfile
{
	var userProfile:UserProfile = new UserProfile();
	userProfile.userId = txtUserId.text;
	userProfile.userName = txtUserName.text;
	userProfile.emailId = txtEmailId.text;
	if(txtEffectiveDate.text.length > 0)
	{
		userProfile.effectiveDate = Date.parse(txtEffectiveDate.text);
	}
	if(txtExpiryDate.text.length > 0)
	{
		userProfile.expiryDate = Date.parse(txtExpiryDate.text);
	}
	if(txtPhoneNo.text.length > 0)
	{
		userProfile.contactNumber = txtPhoneNo.text;
	}
	userProfile.createdBy =  CommonConstants.USER_PROFILE.userId;
	userProfile.createdOn = new Date().getTime();
	userProfile.adminRole = "N";
	userProfile.connectRole = "N";
	userProfile.forcePasswordFlag = "N";
	
	if(this.chkAdminRole.selected)	userProfile.adminRole = "Y";
	if(this.chkConnectRole.selected) userProfile.connectRole = "Y";
	if(this.chkResetPassword.selected) userProfile.forcePasswordFlag = "Y";
	return userProfile;
} 



/**
 * This function will map the retrieved user details with the respective 
 * components. 
 */
private function mapInstallationRoles(userInstallationRolesList:ArrayCollection):void
{
	//Empty the installationRoles datagrid 
	dgInstallationRoles.dataProvider = null;
	if(installationRoleList == null || installationRoleList.length >= 0)
	{
		installationRoleList = new ArrayCollection();
	}
	
	if (userInstallationRolesList.length > 0)
	{
		for each(var instRoles:UserInstallationRole in userInstallationRolesList)
		{
			var instRolesObj:Object = {roleId:instRoles.roleId
										,installationCode:instRoles.installationCode};
			if(!(CommonConstants.ADMIN == instRoles.roleId || CommonConstants.CONNECT == instRoles.roleId))
			{
				installationRoleList.addItem(instRolesObj);
			}
		} 
	}
	dgInstallationRoles.dataProvider = installationRoleList;
}
/**
 * This function will map the retrieved user details with the respective 
 * components. 
 */
private function mapUserDetails(data:Object):void
{
	if(data == null)	return;
	
	userProfile = data.userProfile;
	if(userProfile == null) return;
	
	this.txtUserId.text = userProfile.userId;
	enableUserId = false;
	
	this.txtUserName.text = userProfile.userName;
	this.txtEmailId.text = userProfile.emailId;
	this.txtEffectiveDate.text = CommonUtils.formatDate(
								new Date(userProfile.effectiveDate),
								CommonConstants.US_DATE_FORMAT);
	this.txtExpiryDate.text = CommonUtils.formatDate(
								new Date(userProfile.expiryDate),
								CommonConstants.US_DATE_FORMAT);
	if(userProfile.contactNumber != null && userProfile.contactNumber != "null")
	{
		this.txtPhoneNo.text = userProfile.contactNumber;				
	}
	else
	{
		this.txtPhoneNo.text = CommonConstants.BLANK_STRING;
	}
	
	if(userProfile.adminRole == "Y")
	{
		this.chkAdminRole.selected = true;
	}
	else
	{
		this.chkAdminRole.selected = false;
	}
	
	if(userProfile.connectRole == "Y")
	{
		connectRoleSelected = true;
	}
	else
	{
		connectRoleSelected = false;
	}
	
	mapInstallationRoles(data.userInstallationRoleList);
	
}

/**
 * Enable number entry in phone no field. No text is permitted.
 */
public function enableNumberEntry():void
{
	this.txtPhoneNo.addEventListener(FocusEvent.FOCUS_IN, CommonUtils.focusInHandlerNum);
	this.txtPhoneNo.addEventListener(FocusEvent.FOCUS_OUT, CommonUtils.focusOutHandlerNum);
	this.txtPhoneNo.restrict = "0-9";
	
}
