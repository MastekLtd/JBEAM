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
import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.model.vo.UserProfile;

[Bindable]
/** The variable holding the enable state of the hint question text input */
private var hintQuestionEnable:Boolean;

[Bindable]
/** The variable holding the enable state of the hint answer text input */
private var hintAnswerEnable:Boolean;

[Bindable]
/** The variable holding the visible state of the hint answer text input */
private var hintAnswerVisible:Boolean;

public function clearUserData():void 
{
	handleStartupComplete();
}


/**
 * Creates the userProfile object from data entered by user 
 */
private function preapreUserProfileToUpdate():UserProfile
{
	var userProfile:UserProfile = new UserProfile();
	userProfile.userId = this.txtUserId.text;
	userProfile.userName = this.txtUserName.text;
	userProfile.emailId = this.txtEmailId.text;
	
	if(txtPhoneNo.text.length > 0)
	{
		userProfile.contactNumber = this.txtPhoneNo.text;
	}
	userProfile.createdBy =  CommonConstants.USER_PROFILE.userId;
	userProfile.createdOn = new Date().getTime();
	userProfile.hintQuestion = this.txtHintQuestion.text;	
	userProfile.hintAnswer = this.txtHintAnswer.text;	
	if(this.radBtnListView.selected)
	{
		userProfile.defaultView = this.radBtnListView.value as String;		
	}
	if(this.radBtnPodView.selected)
	{
		userProfile.defaultView = this.radBtnPodView.value as String;		
	}
	return userProfile;
} 

/**
 * This function will map the retrieved user details with the respective 
 * components. 
 */
private function mapUserDetails(data:Object):void
{
	userData = data.userProfile;
	if(userData.defaultView == CommonConstants.PODS_VIEW )
	{
		this.radBtnPodView.selected = true;
	}
	else if(userData.defaultView == CommonConstants.LIST_VIEW )
	{
		this.radBtnListView.selected = true;
	}
	enableUserId = false;	
}

