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
package com.majescomastek.jbeam.model.delegate
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.vo.Installation;
	import com.majescomastek.jbeam.model.vo.ReqRoleDetails;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.RoleData;
	import com.majescomastek.jbeam.model.vo.UserCredential;
	import com.majescomastek.jbeam.model.vo.UserInstallationRole;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The delegate class for the Manage User module used for invoking
	 * SOAP based webservices..
	 * 
	 * In this class we deal with value objects since we might in the future
	 * need to change the normal ws calls to remoteobject calls. If we pass
	 * XML from the view layer to the webservice layer, it would expose the
	 * implementation detail of our webservice base class.
	 */
	public class ManageUserWsDelegate extends BaseSoapWsDelegate
	{
		public function ManageUserWsDelegate()
		{
			super();
		}
		
		/**
		 * Create user with provided data.
		 */
		public function createUser(reqUserDetails:ReqUserDetails,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(createUserResultHandler, defaultFaultHandler);
			invoke("manageUser", getInputForCreateUser(reqUserDetails),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForCreateUser(reqUserDetails:ReqUserDetails):XML
		{
			var parentXml:XML = 
				<s:manageUser xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<reqUserDetailsVO>
						<userProfile>
							<userId>{reqUserDetails.userProfile.userId}</userId>
							<userName>{reqUserDetails.userProfile.userName}</userName>
							<emailId>{reqUserDetails.userProfile.emailId}</emailId>
							<telephoneNo>{reqUserDetails.userProfile.contactNumber}</telephoneNo>
							<effDate>{reqUserDetails.userProfile.effectiveDate}</effDate>
							<expDate>{reqUserDetails.userProfile.expiryDate}</expDate>
							<createdBy>{reqUserDetails.userProfile.createdBy}</createdBy>
							<createdOn>{reqUserDetails.userProfile.createdOn}</createdOn>
							<adminRole>{reqUserDetails.userProfile.adminRole}</adminRole>
							<connectRole>{reqUserDetails.userProfile.connectRole}</connectRole>
							<forcePasswordFlag>{reqUserDetails.userProfile.forcePasswordFlag}</forcePasswordFlag>							
						</userProfile>
					</reqUserDetailsVO>
				</s:manageUser>;
			if(reqUserDetails.userInstallationRoleList.length > 0)
			{
			
				for each(var userInstallationRoles:UserInstallationRole 
							in reqUserDetails.userInstallationRoleList)
				{
					parentXml.reqUserDetailsVO.
							appendChild(createInstallationRoleList(userInstallationRoles));
				} 	
			}
            
			return parentXml;
		}
		
		private function createInstallationRoleList(userInstallationRoles:UserInstallationRole):XML
		{
			var childXml:XML = 
				<userInstallationRoles>
	               <installationCode>{userInstallationRoles.installationCode}</installationCode>
	               <roleId>{userInstallationRoles.roleId}</roleId>
	               <userId>{userInstallationRoles.userId}</userId>
	            </userInstallationRoles>;			
			return childXml;
		}
		
		private function createUserResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.CREATE_USER_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedCreateUserResultData:Object = evt.result;				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(retrievedCreateUserResultData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Retrieve the User Installation Role Details list for provided user data.
		 */
		public function getUserInstallationRoleDetails(reqUserDetails:ReqUserDetails,
			externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getUserInstallationRoleDetailsResultHandler, defaultFaultHandler);
			invoke("getUserInstallationRoleDetails", getInputForGetUserInstallationRoleDetails(),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetUserInstallationRoleDetails():XML
		{
			var parentXml:XML =  
				<s:getUserInstallationRoleDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/" />;					
			return parentXml;
		}
		
		
		
		private function getUserInstallationRoleDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_USER_INSTALLATION_ROLES_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedUserInstallationRoleDetailsResultDataList:Object
											 = evt.result.userInstallationRoles;
				var userInstallationRoleDetailsList:ArrayCollection = 
						getUserInstallationRoleDetailsList(retrievedUserInstallationRoleDetailsResultDataList);
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(userInstallationRoleDetailsList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a UserInstallationRole Details object based on the data returned by the webservice.
		 */
		private function createGetUserInstallationRoleDetailsResultData(
				retrievedUserInstallationRoleDetailsResultData:Object):UserInstallationRole
		{
			var getUserInstallationRoleDetailsResultData:UserInstallationRole 
													= new UserInstallationRole();
			getUserInstallationRoleDetailsResultData.userId 
					= retrievedUserInstallationRoleDetailsResultData['userId'];
			getUserInstallationRoleDetailsResultData.installationCode 
					= retrievedUserInstallationRoleDetailsResultData['installationCode'];
			getUserInstallationRoleDetailsResultData.roleId 
					= retrievedUserInstallationRoleDetailsResultData['roleId'];
			return getUserInstallationRoleDetailsResultData;
		}
		
		private function getUserInstallationRoleDetailsList(
			retrievedUserInstallationRoleDetailsResultDataList:Object):ArrayCollection
		{
			var userInstallationRoleDetailsList:ArrayCollection = new ArrayCollection();
			if(retrievedUserInstallationRoleDetailsResultDataList != null 
					&& retrievedUserInstallationRoleDetailsResultDataList.length > 0)
			{
				for(var i:uint = 0; 
						i < retrievedUserInstallationRoleDetailsResultDataList.length;
						++i)
				{
					var retrievedUserInstallationRoleDetailsResultData:Object 
							= retrievedUserInstallationRoleDetailsResultDataList.getItemAt(i);
					var userInstallationRoleDetailsResultData:UserInstallationRole 
						= createGetUserInstallationRoleDetailsResultData(retrievedUserInstallationRoleDetailsResultData);
					userInstallationRoleDetailsList.addItem(userInstallationRoleDetailsResultData);
				}
			}
			return userInstallationRoleDetailsList;
		}
		
		/**
		 * Retrieve the roles list for provided request object.
		 */
		public function getRolesList(roleData:ReqRoleDetails,
				externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getRoleDataResultHandler, defaultFaultHandler);
			invoke("getRoleData", getInputForGetRoleData(roleData),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetRoleData(roleData:ReqRoleDetails):XML
		{
			var parentXml:XML = <s:getRoleData xmlns:s="http://services.server.ws.monitor.stgmastek.com/" />
			    				
			return parentXml;
		}
		
		
		
		private function getRoleDataResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_ROLE_DATA_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedGetRoleDataResultDataList:Object = evt.result.roleDataList;
				var getRoleDataList:ArrayCollection = new ArrayCollection();
				if(retrievedGetRoleDataResultDataList != null && retrievedGetRoleDataResultDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedGetRoleDataResultDataList.length; ++i)
					{
						var retrievedGetRoleDataResultData:Object = retrievedGetRoleDataResultDataList.getItemAt(i);
						var getRoleDataResultData:RoleData = createGetRoleDataResultData(retrievedGetRoleDataResultData);
						getRoleDataList.addItem(getRoleDataResultData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(getRoleDataList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a RoleData object based on the data returned by the webservice.
		 */
		private function createGetRoleDataResultData(retrievedGetRoleDataResultData:Object):RoleData
		{
			var getRoleDataResultData:RoleData = new RoleData(); 
			getRoleDataResultData.roleId = retrievedGetRoleDataResultData['roleId'];
			getRoleDataResultData.roleName = retrievedGetRoleDataResultData['roleName'];
			getRoleDataResultData.effDate = retrievedGetRoleDataResultData['effDate'];
			getRoleDataResultData.expDate = retrievedGetRoleDataResultData['expDate'];
			return getRoleDataResultData;
		}
		
		/**
		 * Retrieve the user details with Installations and Roles list which is 
		 * assigned to the provided user data.
		 */
		public function getUserDetails(userProfile:UserProfile,
			externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getUserDetailsResultHandler, defaultFaultHandler);
			invoke("getUserInstallationRoleDetails", getInputForGetUserDetails(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetUserDetails(userProfile:UserProfile):XML
		{
			var parentXml:XML =  
				<s:getUserInstallationRoleDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
			         <reqUserDetailsVO>
			         	<userProfile>
			         		<userId>{userProfile.userId}</userId>
			         	</userProfile>
			         </reqUserDetailsVO>
			    </s:getUserInstallationRoleDetails>;					
			return parentXml;
		}
		
		
		
		private function getUserDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_USER_DETAILS_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var resUserDetails:ReqUserDetails = new ReqUserDetails();
				
				var retrievedUserDetailsResultData:Object = evt.result.userProfile;
				if(retrievedUserDetailsResultData != null)
				{
					resUserDetails.userProfile = 
						createGetUserDetailsResultData(retrievedUserDetailsResultData);
				}
				
				var retrievedUserInstallationRoleDetailsResultDataList:Object = evt.result.userInstallationRoles;
				if(retrievedUserInstallationRoleDetailsResultDataList != null)
				{
					resUserDetails.userInstallationRoleList = 
							getUserInstallationRoleDetailsList(retrievedUserInstallationRoleDetailsResultDataList);					
				}
				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(resUserDetails, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a UserDetails object based on the data returned by the webservice.
		 */
		private function createGetUserDetailsResultData(
				retrievedUserDetailsResultData:Object):UserProfile
		{
			var getUserDetailsResultData:UserProfile = new UserProfile();
			getUserDetailsResultData.userId = retrievedUserDetailsResultData['userId'];
			getUserDetailsResultData.userName = retrievedUserDetailsResultData['userName'];
			getUserDetailsResultData.emailId = retrievedUserDetailsResultData['emailId'];
			getUserDetailsResultData.effectiveDate = Number(retrievedUserDetailsResultData['effDate']);
			getUserDetailsResultData.expiryDate = Number(retrievedUserDetailsResultData['expDate']);
			getUserDetailsResultData.contactNumber = retrievedUserDetailsResultData['telephoneNo'];
			getUserDetailsResultData.adminRole = retrievedUserDetailsResultData['adminRole'];
			getUserDetailsResultData.connectRole = retrievedUserDetailsResultData['connectRole'];
			getUserDetailsResultData.hintQuestion = retrievedUserDetailsResultData['hintQuestion'];		
			getUserDetailsResultData.hintAnswer = retrievedUserDetailsResultData['hintAnswer'];
			getUserDetailsResultData.defaultView = retrievedUserDetailsResultData['defaultView'];
			return getUserDetailsResultData;
		}
		
		/**
		 * Retrieve the installations list for provided user data.
		 */
		public function getInstallationsList(userProfile:UserProfile,
				externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getInstallationsListResultHandler, defaultFaultHandler);
			invoke("getInstallationsList", getInputForGetInstallationsList(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetInstallationsList(userProfile:UserProfile):XML
		{
			var xml:XML =  <s:getInstallationsList xmlns:s="http://services.server.ws.monitor.stgmastek.com/" />;			
			return xml;
		}
		
		
		
		private function getInstallationsListResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.GET_INSTALLATIONS_LIST_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedInstallationsResultDataList:Object = evt.result.installationsList;
				var installationsList:ArrayCollection = new ArrayCollection();
				if(retrievedInstallationsResultDataList != null && retrievedInstallationsResultDataList.length > 0)
				{
					for(var i:uint = 0; i < retrievedInstallationsResultDataList.length; ++i)
					{
						var retrievedInstallationsResultData:Object = retrievedInstallationsResultDataList.getItemAt(i);
						var installationsResultData:Installation = createInstallationsResultData(retrievedInstallationsResultData);
						installationsList.addItem(installationsResultData);
					}
				}
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(installationsList, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a Installations object based on the data returned by the webservice.
		 */
		private function createInstallationsResultData(retrievedInstallationsResultData:Object):Installation
		{
			var installationsResultData:Installation = new Installation(); 
			installationsResultData.installationCode = retrievedInstallationsResultData['installationCode'];
			return installationsResultData;
		}
		
		/**
		 * Edit user profile with provided data.
		 */
		public function editUserProfile(reqUserDetails:ReqUserDetails,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(editUserProfileResultHandler, defaultFaultHandler);
			invoke("updateUserProfile", getInputForEditUserProfile(reqUserDetails),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForEditUserProfile(reqUserDetails:ReqUserDetails):XML
		{
			var parentXml:XML = 
				<s:updateUserProfile xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<reqUserDetailsVO>
						<userProfile>
							<userId>{reqUserDetails.userProfile.userId}</userId>
							<userName>{reqUserDetails.userProfile.userName}</userName>
							<emailId>{reqUserDetails.userProfile.emailId}</emailId>
							<createdBy>{reqUserDetails.userProfile.createdBy}</createdBy>
							<createdOn>{reqUserDetails.userProfile.createdOn}</createdOn>
			               	<telephoneNo>{reqUserDetails.userProfile.contactNumber}</telephoneNo>
			               	<hintAnswer>{reqUserDetails.userProfile.hintAnswer}</hintAnswer>
			               	<hintQuestion>{reqUserDetails.userProfile.hintQuestion}</hintQuestion>
							<defaultView>{reqUserDetails.userProfile.defaultView}</defaultView>
						</userProfile>
					</reqUserDetailsVO>
				</s:updateUserProfile>;
			return parentXml;
		}
		
		private function editUserProfileResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.EDIT_USER_PROFILE_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedEditUserProfileResultData:Object = evt.result;				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(retrievedEditUserProfileResultData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Change password with provided data.
		 */
		public function changePassword(userCredentials:UserCredential,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(changePasswordResultHandler, defaultFaultHandler);
			invoke("changePassword", getInputForChangePassword(userCredentials),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForChangePassword(userCredentials:UserCredential):XML
		{
			var xml:XML = 
				<s:changePassword xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<userCredentials>
			        	<userId>{userCredentials.userId}</userId>
			            <password>{userCredentials.password}</password>
			            <newPassword>{userCredentials.newPassword}</newPassword>
			       	</userCredentials>
			    </s:changePassword>;
			return xml;
		}
		
		private function changePasswordResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.CHANGE_PASSWORD_FAILED), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedChangePasswordResultData:Object = evt.result;				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(retrievedChangePasswordResultData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
	}
}