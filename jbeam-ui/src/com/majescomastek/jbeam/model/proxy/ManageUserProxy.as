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

	public class ManageUserProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "MANAGE_USER_PROXY";
		
		/** Notification constant indicates success of create user service */
		public static const CREATE_USER_SERVICE_SUCCEEDED:String = "CREATE_USER_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of create user service */
		public static const CREATE_USER_SERVICE_FAILED:String = "CREATE_USER_SERVICE_FAILED";
		
		/** Notification constant indicates success of user installation roles fetch service */
		public static const GET_USER_INSTALLATION_ROLES_SERVICE_SUCCEEDED:String = "GET_USER_INSTALLATION_ROLES_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of user installation roles fetch service */
		public static const GET_USER_INSTALLATION_ROLES_SERVICE_FAILED:String = "GET_USER_INSTALLATION_ROLES_SERVICE_FAILED";
		
		/** Notification constant indicates success of role data fetch service */
		public static const GET_ROLE_DATA_LIST_SERVICE_SUCCEEDED:String = "GET_ROLE_DATA_LIST_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of role data fetch service */
		public static const GET_ROLE_DATA_LIST_SERVICE_FAILED:String = "GET_ROLE_DATA_LIST_SERVICE_FAILED";
		
		/** Notification constant indicates success of user details fetch service */
		public static const CHECK_USER_EXISTS_SUCCEEDED:String = "CHECK_USER_EXISTS_SUCCEEDED";
		
		/** Notification constant indicates failure of user details fetch service */
		public static const CHECK_USER_EXISTS_FAILED:String = "CHECK_USER_EXISTS_FAILED";
		
		/** Notification constant indicates success of user details fetch service */
		public static const GET_USER_DETAILS_SERVICE_SUCCEEDED:String = "GET_USER_DETAILS_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of user details fetch service */
		public static const GET_USER_DETAILS_SERVICE_FAILED:String = "GET_USER_DETAILS_SERVICE_FAILED";
		
		/** Notification constant indicates success of installations data fetch service */
		public static const GET_INSTALLATIONS_LIST_SERVICE_SUCCEEDED:String = "GET_INSTALLATIONS_LIST_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of installations data fetch service */
		public static const GET_INSTALLATIONS_LIST_SERVICE_FAILED:String = "GET_INSTALLATIONS_LIST_SERVICE_FAILED";
		
		public function ManageUserProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to create user. 
		 */
		public function createUser(
			reqUserDetails:ReqUserDetails, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(createUserResultHandler, createUserFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.createUser(reqUserDetails, [responder], tokenData);
		}
		
		private function createUserResultHandler(evt:ResultEvent):void
		{
			sendNotification(CREATE_USER_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function createUserFaultHandler(evt:FaultEvent):void
		{
			sendNotification(CREATE_USER_SERVICE_FAILED, evt.fault);
		}
		
		/**
		 * Send request to get user installation roles. 
		 */
		public function getUserInstallationRoleDetails(reqUserDetails:ReqUserDetails,
				tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getUserInstallationRoleDetailsResultHandler, getUserInstallationRoleDetailsFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.getUserInstallationRoleDetails(reqUserDetails, [responder], tokenData);
		}
		
		private function getUserInstallationRoleDetailsResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_USER_INSTALLATION_ROLES_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getUserInstallationRoleDetailsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_USER_INSTALLATION_ROLES_SERVICE_FAILED, evt.fault);
		}
		/**
		 * Send request to get role data. 
		 */
		public function getRolesList(roleData:ReqRoleDetails, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getRolesListResultHandler, getRolesListFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.getRolesList(roleData, [responder], tokenData);
		}
		
		private function getRolesListResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_ROLE_DATA_LIST_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getRolesListFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_ROLE_DATA_LIST_SERVICE_FAILED, evt.fault);
		}
		
		
		/**
		 * Send request to check if the user already exists. 
		 */
		public function checkUserExists(userProfile:UserProfile,
				tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(checkUserExistsResultHandler, checkUserExistsFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.getUserDetails(userProfile, [responder], tokenData);
		}
		
		private function checkUserExistsResultHandler(evt:ResultEvent):void
		{
			sendNotification(CHECK_USER_EXISTS_SUCCEEDED, evt.result);
		}
		
		private function checkUserExistsFaultHandler(evt:FaultEvent):void
		{
			sendNotification(CHECK_USER_EXISTS_FAILED, evt.fault);
		}
		/**
		 * Send request to get user details. 
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
		
		/**
		 * Send request to get installations list. 
		 */
		public function getInstallationsList(
							userProfile:UserProfile, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getInstallationsListResultHandler, getInstallationsListFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.getInstallationsList(userProfile, [responder], tokenData);
		}
		
		private function getInstallationsListResultHandler(evt:ResultEvent):void
		{
			sendNotification(GET_INSTALLATIONS_LIST_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function getInstallationsListFaultHandler(evt:FaultEvent):void
		{
			sendNotification(GET_INSTALLATIONS_LIST_SERVICE_FAILED, evt.fault);
		}
		
	}
}