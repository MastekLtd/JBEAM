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
package com.majescomastek.jbeam.view.mediator.usermaster
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.usermaster.ManageUserModuleFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.proxy.ManageUserProxy;
	import com.majescomastek.jbeam.model.vo.ReqRoleDetails;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.usermaster.ManageUserModule;
	import com.majescomastek.jbeam.view.components.usermaster.UserTree;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ManageUserMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "MANAGE_USER_MEDIATOR";
		
		private var _manageUserProxy:ManageUserProxy;

		/**
		 * Create a new mediator object.
		 */
		public function ManageUserMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(ManageUserModule.ROLES_LIST_REQUEST,
				 onRolesListRequest, false, 0, true);
			viewComponent.addEventListener(ManageUserModule.INSTALLATION_LIST_REQUEST,
				 onInstallationListRequest, false, 0, true);
			viewComponent.addEventListener(ManageUserModule.CREATE_USER_REQUEST,
				 onCreateUserRequest, false, 0, true);
			viewComponent.addEventListener(ManageUserModule.GET_USER_INSTALLATION_ROLE_LIST_REQUEST,
				 onGetUserInstallationRolesRequest, false, 0, true);
			viewComponent.addEventListener(ManageUserModule.CHECK_USER_EXISTS_REQUEST,
				 onCheckUserExistsRequest, false, 0, true);
			viewComponent.addEventListener(UserTree.GET_USER_DETAILS_REQUEST,
				 onGetUserDetailsRequest, false, 0, true);
			viewComponent.addEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false, 0, true);
				 
		}
		
			
		/**
		 * The function invoked when the ROLES_LIST_REQUEST is fired.
		 */		
		private function onRolesListRequest(evt:CustomDataEvent):void
		{
			_manageUserProxy.getRolesList(ReqRoleDetails(evt.eventData));
		}
		
		/**
		 * The function invoked when the INSTALLATION_LIST_REQUEST is fired.
		 */		
		private function onInstallationListRequest(event:CustomDataEvent):void
		{
			_manageUserProxy.getInstallationsList(UserProfile(event.eventData));			
		}
		
		/**
		 * The function invoked when the GET_USER_INSTALLATION_ROLE_LIST_REQUEST event is fired.
		 */
		private function onGetUserInstallationRolesRequest(event:Event):void
		{
			_manageUserProxy.getUserInstallationRoleDetails(new ReqUserDetails());
		}
		
		/**
		 * The function invoked when the CREATE_USER_REQUEST event is fired.
		 */
		private function onCreateUserRequest(evt:CustomDataEvent):void
		{
			_manageUserProxy.createUser(ReqUserDetails(evt.eventData));
		}
		
		/**
		 * The function invoked when the CHECK_USER_EXISTS_REQUEST event is fired.
		 */
		private function onCheckUserExistsRequest(evt:CustomDataEvent):void
		{
			_manageUserProxy.checkUserExists(UserProfile(evt.eventData));
		}
		
		/**
		 * The function invoked when the GET_USER_DETAILS_REQUEST event is fired.
		 */
		private function onGetUserDetailsRequest(evt:CustomDataEvent):void
		{
			_manageUserProxy.getUserDetails(UserProfile(evt.eventData));
		}
		
		/**
		 * The function invoked when the CLEAR_USER_DETAILS_REQUEST event is fired.
		 */
		private function onClearUserDetailsRequest(event:Event):void
		{
			module.clearUserData();
		}
		
		/**
		 * The function invoked when the CLEANUP_MANAGE_USER event is fired.
		 */
		private function onCleanupManageUser(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ManageUserModule
		{
			return viewComponent as ManageUserModule;						
		}
		
		override public function onRegister():void
		{
			_manageUserProxy = ManageUserProxy(facade.retrieveProxy(ManageUserProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(ManageUserModule.ROLES_LIST_REQUEST,
				 onRolesListRequest, false);
			viewComponent.removeEventListener(ManageUserModule.INSTALLATION_LIST_REQUEST,
				 onInstallationListRequest, false);
			viewComponent.removeEventListener(ManageUserModule.CREATE_USER_REQUEST,
				 onCreateUserRequest, false);
			viewComponent.removeEventListener(ManageUserModule.GET_USER_INSTALLATION_ROLE_LIST_REQUEST,
				 onGetUserInstallationRolesRequest, false);
			viewComponent.removeEventListener(ManageUserModule.CHECK_USER_EXISTS_REQUEST,
				 onCheckUserExistsRequest, false);
			viewComponent.removeEventListener(UserTree.GET_USER_DETAILS_REQUEST,
				 onGetUserDetailsRequest, false);
			viewComponent.removeEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ManageUserModuleFacade.MANAGE_USER_MODULE_STARTUP_COMPLETE,
				ManageUserProxy.GET_INSTALLATIONS_LIST_SERVICE_SUCCEEDED,
				ManageUserProxy.GET_INSTALLATIONS_LIST_SERVICE_FAILED,
				ManageUserProxy.GET_ROLE_DATA_LIST_SERVICE_SUCCEEDED,
				ManageUserProxy.GET_ROLE_DATA_LIST_SERVICE_FAILED,
				ManageUserProxy.GET_USER_INSTALLATION_ROLES_SERVICE_SUCCEEDED,
				ManageUserProxy.GET_USER_INSTALLATION_ROLES_SERVICE_FAILED,
				ManageUserProxy.CREATE_USER_SERVICE_SUCCEEDED,
				ManageUserProxy.CREATE_USER_SERVICE_FAILED,
				ManageUserProxy.CHECK_USER_EXISTS_SUCCEEDED,
				ManageUserProxy.CHECK_USER_EXISTS_FAILED,				
				ManageUserProxy.GET_USER_DETAILS_SERVICE_SUCCEEDED,
				ManageUserProxy.GET_USER_DETAILS_SERVICE_FAILED				
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var name:String = notification.getName();
			var type:String = notification.getType();
			var data:Object = notification.getBody();
			
			// Halt processing if the notification is not meant for this mediator.
			if(type != null && type != mediatorName)	return;
			
			switch(name)
			{
				case ManageUserModuleFacade.MANAGE_USER_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case ManageUserProxy.GET_USER_INSTALLATION_ROLES_SERVICE_SUCCEEDED:
					module.handleGetUserInstallationRoleDetailsServiceResult(ArrayCollection(data));
					break;
				case ManageUserProxy.GET_ROLE_DATA_LIST_SERVICE_SUCCEEDED:
					module.handleGetRolesListServiceResult(ArrayCollection(data));
					break;
				case ManageUserProxy.CREATE_USER_SERVICE_SUCCEEDED:
					module.handleCreateUserServiceResult(data);
					break;
				case ManageUserProxy.CREATE_USER_SERVICE_FAILED:
					module.handleCreateUserServiceFault(data);
					break;
				case ManageUserProxy.GET_INSTALLATIONS_LIST_SERVICE_SUCCEEDED:
					module.handleInstallationListRetrieval(ArrayCollection(data));
					break;
				case ManageUserProxy.CHECK_USER_EXISTS_SUCCEEDED:
					module.handleCheckUserExistsResult(data);
					break;
				case ManageUserProxy.GET_USER_DETAILS_SERVICE_SUCCEEDED:
					module.handleGetUserDetailsServiceResult(data);
					break;
				case ManageUserProxy.GET_INSTALLATIONS_LIST_SERVICE_FAILED:
				case ManageUserProxy.GET_USER_INSTALLATION_ROLES_SERVICE_FAILED:
				case ManageUserProxy.GET_ROLE_DATA_LIST_SERVICE_FAILED:
				case ManageUserProxy.CHECK_USER_EXISTS_FAILED:
				case ManageUserProxy.GET_USER_DETAILS_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ManageUserMediator');
					break;
			}
		}
		

		
	}
}