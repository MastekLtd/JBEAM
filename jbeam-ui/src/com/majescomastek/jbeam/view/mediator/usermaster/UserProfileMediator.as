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
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.facade.usermaster.UserProfileModuleFacade;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.usermaster.UserProfileModule;
	import com.majescomastek.jbeam.view.components.usermaster.UserTree;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class UserProfileMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "USER_PROFILE_MEDIATOR";
		
		private var _userProfileProxy:UserProfileProxy;

		/**
		 * Create a new mediator object.
		 */
		public function UserProfileMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(UserProfileModule.EDIT_USER_PROFILE_REQUEST,
				 onEditUserProfileRequest, false, 0, true);
			viewComponent.addEventListener(UserTree.GET_USER_DETAILS_REQUEST,
				 onGetUserDetailsRequest, false, 0, true);
			viewComponent.addEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false, 0, true);
			viewComponent.addEventListener(UserProfileModule.SHOW_INSTALLATION_DETAILS,
				 onShowInstallationDetailsClick, false, 0, true);			
			viewComponent.addEventListener(UserProfileModule.LOGOUT_CLICK, 
				onLogoutClick, false, 0, true);
		}
		
		/**
		 * The function invoked when the LOGOUT_CLICK event is fired.
		 */
		private function onLogoutClick(event:Event):void
		{
			sendNotification(ShellFacade.CHANGE_SERVER_TO_LOGIN_STARTUP);
		}
		
		/**
		 * The function invoked when the EDIT_USER_PROFILE_REQUEST event is fired.
		 */
		private function onEditUserProfileRequest(evt:CustomDataEvent):void
		{
			_userProfileProxy.editUserProfile(ReqUserDetails(evt.eventData));
		}
		
		/**
		 * The function invoked when the GET_USER_DETAILS_REQUEST event is fired.
		 */
		private function onGetUserDetailsRequest(evt:CustomDataEvent):void
		{
			_userProfileProxy.getUserDetails(UserProfile(evt.eventData));
		}
		
		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onShowInstallationDetailsClick(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * The function invoked when the CLEAR_USER_DETAILS_REQUEST event is fired.
		 */
		private function onClearUserDetailsRequest(event:Event):void
		{
			module.clearUserData();
		}
		
		/**
		 * The function invoked when the CLEANUP_USER_PROFILE event is fired.
		 */
		private function onCleanupUserProfile(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():UserProfileModule
		{
			return viewComponent as UserProfileModule;						
		}
		
		override public function onRegister():void
		{
			_userProfileProxy = UserProfileProxy(facade.retrieveProxy(UserProfileProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(UserProfileModule.EDIT_USER_PROFILE_REQUEST,
				 onEditUserProfileRequest, false);
			viewComponent.removeEventListener(UserTree.GET_USER_DETAILS_REQUEST,
				 onGetUserDetailsRequest, false);
			viewComponent.removeEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false);
			viewComponent.removeEventListener(UserProfileModule.SHOW_INSTALLATION_DETAILS,
				 onShowInstallationDetailsClick, false);
			viewComponent.removeEventListener(UserProfileModule.LOGOUT_CLICK, 
				onLogoutClick, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [UserProfileModuleFacade.USER_PROFILE_MODULE_STARTUP_COMPLETE,
				UserProfileProxy.EDIT_USER_PROFILE_SERVICE_SUCCEEDED,
				UserProfileProxy.EDIT_USER_PROFILE_SERVICE_FAILED,
				UserProfileProxy.GET_USER_DETAILS_SERVICE_SUCCEEDED,
				UserProfileProxy.GET_USER_DETAILS_SERVICE_FAILED			
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
				case UserProfileModuleFacade.USER_PROFILE_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case UserProfileProxy.EDIT_USER_PROFILE_SERVICE_SUCCEEDED:
					module.handleEditUserProfileServiceResult(data);
					break;
				case UserProfileProxy.GET_USER_DETAILS_SERVICE_SUCCEEDED:
					module.handleGetUserDetailsServiceResult(data);
//					sendNotification(UserProfileModuleFacade.NAVIGATE_DEFAULT_VIEW_STARTUP, data);
					break;				
				case UserProfileProxy.EDIT_USER_PROFILE_SERVICE_FAILED:
				case UserProfileProxy.GET_USER_DETAILS_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator UserProfileMediator');
					break;
			}
		}
		

		
	}
}