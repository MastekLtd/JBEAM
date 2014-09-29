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
	import com.majescomastek.jbeam.facade.usermaster.ChangePasswordModuleFacade;
	import com.majescomastek.jbeam.model.proxy.ChangePasswordProxy;
	import com.majescomastek.jbeam.model.vo.UserCredential;
	import com.majescomastek.jbeam.view.components.usermaster.ChangePasswordModule;
	import com.majescomastek.jbeam.view.components.usermaster.UserTree;
	
	import flash.events.Event;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ChangePasswordMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "CHANGE_PASSWORD_MEDIATOR";
		
		private var _changePasswordProxy:ChangePasswordProxy;

		/**
		 * Create a new mediator object.
		 */
		public function ChangePasswordMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(ChangePasswordModule.CHANGE_PASSWORD_REQUEST,
				 onChangePasswordRequest, false, 0, true);
			viewComponent.addEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false, 0, true);
			viewComponent.addEventListener(ChangePasswordModule.SHOW_USER_PROFILE,
				 onShowUserProfile, false, 0, true);
		}
		
		/**
		 * The function invoked when the CHANGE_PASSWORD_REQUEST event is fired.
		 */
		private function onChangePasswordRequest(evt:CustomDataEvent):void
		{
			_changePasswordProxy.changePassword(UserCredential(evt.eventData));
		}
		
		/**
		 * The function invoked when the SHOW_USER_PROFILE event is fired.
		 */
		private function onShowUserProfile(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * The function invoked when the CLEAR_USER_DETAILS_REQUEST event is fired.
		 */
		private function onClearUserDetailsRequest(event:Event):void
		{
			module.resetFields();
		}
		
		/**
		 * The function invoked when the CLEANUP_CHANGE_PASSWORD event is fired.
		 */
		private function onCleanupChangePassword(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ChangePasswordModule
		{
			return viewComponent as ChangePasswordModule;						
		}
		
		override public function onRegister():void
		{
			_changePasswordProxy = ChangePasswordProxy(facade.retrieveProxy(ChangePasswordProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(ChangePasswordModule.CHANGE_PASSWORD_REQUEST,
				 onChangePasswordRequest, false);
			viewComponent.removeEventListener(UserTree.CLEAR_USER_DETAILS_REQUEST,
				 onClearUserDetailsRequest, false);
			viewComponent.removeEventListener(ChangePasswordModule.SHOW_USER_PROFILE,
				 onShowUserProfile, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ChangePasswordModuleFacade.CHANGE_PASSWORD_MODULE_STARTUP_COMPLETE,
				ChangePasswordProxy.CHANGE_PASSWORD_SERVICE_SUCCEEDED,
				ChangePasswordProxy.CHANGE_PASSWORD_SERVICE_FAILED
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
				case ChangePasswordModuleFacade.CHANGE_PASSWORD_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case ChangePasswordProxy.CHANGE_PASSWORD_SERVICE_SUCCEEDED:
					module.handleChangePasswordServiceResult(data);
					break;
				case ChangePasswordProxy.CHANGE_PASSWORD_SERVICE_FAILED:
					module.resetFields();
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ChangePasswordMediator');
					break;
			}
		}
		

		
	}
}