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
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.ManageUserWsDelegate;
	import com.majescomastek.jbeam.model.vo.ReqRoleDetails;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class UserProfileProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "USER_PROFILE_PROXY";
		
		/** Notification constant indicates success of create user service */
		public static const EDIT_USER_PROFILE_SERVICE_SUCCEEDED:String = "EDIT_USER_PROFILE_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of create user service */
		public static const EDIT_USER_PROFILE_SERVICE_FAILED:String = "EDIT_USER_PROFILE_SERVICE_FAILED";
		
		/** Notification constant indicates success of user installation roles fetch service */
		public static const GET_USER_DETAILS_SERVICE_SUCCEEDED:String = "GET_USER_DETAILS_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of user installation roles fetch service */
		public static const GET_USER_DETAILS_SERVICE_FAILED:String = "GET_USER_DETAILS_SERVICE_FAILED";
		
		public function UserProfileProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to create user. 
		 */
		public function editUserProfile(
			reqUserDetails:ReqUserDetails, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(editUserProfileResultHandler, editUserProfileFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.editUserProfile(reqUserDetails, [responder], tokenData);
		}
		
		private function editUserProfileResultHandler(evt:ResultEvent):void
		{
			sendNotification(EDIT_USER_PROFILE_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function editUserProfileFaultHandler(evt:FaultEvent):void
		{
			sendNotification(EDIT_USER_PROFILE_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Send request to get user detailss. 
		 */
		public function getUserDetails(userProfile:UserProfile,
				tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getUserDetailsResultHandler, getUserDetailsFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.getUserDetails(userProfile, [responder], tokenData);
		}
		
		private function getUserDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_USER_DETAILS_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getUserDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_USER_DETAILS_SERVICE_FAILED, evt.fault);
		}
	}
}