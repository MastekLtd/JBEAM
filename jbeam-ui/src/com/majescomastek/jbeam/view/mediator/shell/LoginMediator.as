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
package com.majescomastek.jbeam.view.mediator.shell
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.ShellProxy;
	import com.majescomastek.jbeam.model.vo.JbeamServer;
	import com.majescomastek.jbeam.model.vo.ReqUserDetails;
	import com.majescomastek.jbeam.model.vo.ReqVersion;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.shell.LoginComponent;
	
	import flash.errors.SQLError;
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;
	
	/**
	 * The Mediator class for the Login module.
	 */
	public class LoginMediator extends BaseMediator
	{
		
		/** Mediator Name */
		public static const NAME:String = "LOGIN_MEDIATOR";
		
		private var _shellProxy:ShellProxy;
		
		public function LoginMediator(viewComponent:LoginComponent)
		{
			super(NAME, viewComponent);
			
			viewComponent.addEventListener(LoginComponent.SERVER_CONFIGURATION_CHANGE, onServerConfigurationChange);
			viewComponent.addEventListener(LoginComponent.LOGIN_SUBMIT, submitLoginDetails);
			viewComponent.addEventListener(LoginComponent.CLEANUP_LOGIN_VIEW, onLoginCleanup);
			viewComponent.addEventListener(LoginComponent.CHANGE_SERVER_SUBMIT, submitChangeServerDetails);
			viewComponent.addEventListener(LoginComponent.CHECK_COMPATIBILITY, onCheckCompatibility);
			viewComponent.addEventListener(LoginComponent.ADD_SERVER_CLICK, onAddServerClick);
		}
		
		/**
		 * The function invoked when the ADD_SERVER_CLICK event is fired.
		 */
		private function onAddServerClick(event:CustomDataEvent):void
		{
			_shellProxy.addServerToDatabase(JbeamServer(event.eventData));
		}
		
		/**
        * Handler function which will be called on click of the
        * Login button. 
        */
        private function submitLoginDetails(evt:CustomDataEvent):void
        {
        	try
			{
				_shellProxy.userAuthentication(UserProfile(evt.eventData));
			}
			catch(e:Error)
			{
				trace(e.message);
			}
        }
        
		/**
        * Handler function which will be called on click of the
        * 'Check Compatibility' link button. 
        */
        private function onCheckCompatibility(evt:CustomDataEvent):void
        {
        	try
			{
				_shellProxy.checkCompatibility(ReqVersion(evt.eventData));
			}
			catch(e:Error)
			{
				trace(e.message);
			}
        }
        
        /**
        * 
        */
        private function submitChangeServerDetails(evt:Event):void
        {
        	try
        	{
        		sendNotification(ShellFacade.VIEW_CHANGE_SERVER_CONFIG_SCREEN);
        	}
        	catch(e:Error)	{}
        }
        
        /**
        * This function is called whenever navigation happend from 
        * Login page to some other page.
        */
        private function onLoginCleanup(evt:Event):void
        {
        	try
        	{
	        	facade.removeMediator(NAME);
	        }
	        catch(e:Error)	{}
        } 
        
		/**
		 * The function invoked when the SERVER_CONFIGURATION_CHANGE event is fired.
		 */	
		private function onServerConfigurationChange(event:CustomDataEvent):void
		{
			try
			{
//				_shellProxy.loadCompanyLogo(String(event.eventData));				
			}
			catch(e:Error){}
		}

		private function get module():LoginComponent
		{
			return viewComponent as LoginComponent;
		}
		
		/**
		 * Cache the proxies required by this mediator when the mediator
		 * registration is complete.
		 */
		override public function onRegister():void
		{
			_shellProxy = ShellProxy(facade.retrieveProxy(ShellProxy.NAME));
		}

		/**
		 * The function invoked when this mediator is destroyed.
		 */
		override public function onRemove():void
		{
			module.removeEventListener(LoginComponent.SERVER_CONFIGURATION_CHANGE, onServerConfigurationChange);
        	module.removeEventListener(LoginComponent.LOGIN_SUBMIT, submitLoginDetails);
        	module.removeEventListener(LoginComponent.CLEANUP_LOGIN_VIEW, onLoginCleanup);
        	module.removeEventListener(LoginComponent.CHANGE_SERVER_SUBMIT, submitChangeServerDetails);
			module.removeEventListener(LoginComponent.CHECK_COMPATIBILITY, onCheckCompatibility);
			module.removeEventListener(LoginComponent.ADD_SERVER_CLICK, onAddServerClick);
        	
        	setViewComponent(null);
		}

		/**
        * The notifications this mediator is interested in.
        */
        override public function listNotificationInterests():Array
        {
            return [ShellFacade.LOGIN_STARTUP_COMPLETED,
					ShellProxy.ADD_SERVER_TO_DATABASE_FAILED,
					ShellProxy.ADD_SERVER_TO_DATABASE_SUCCEEDED,
					ShellProxy.CHECK_COMPATIBILITY_SERVICE_SUCCEEDED,
            		ShellProxy.CHECK_COMPATIBILITY_SERVICE_FAILED,
            		ShellProxy.USER_AUTHETICATION_SERVICE_SUCCEEDED,
            		ShellProxy.USER_AUTHETICATION_SERVICE_FAILED,
            		ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED,
            		ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_FAILED,
					ShellProxy.LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED,
					ShellProxy.LOAD_COMPANY_LOGO_SERVICE_FAILED
            ];
        }
        
        /**
        * Function which handles the notifications.
        */
        override public function handleNotification(notification:INotification):void
        {
        	var data:Object = notification.getBody();
			switch (notification.getName())
			{
				case ShellProxy.ADD_SERVER_TO_DATABASE_SUCCEEDED:
					_shellProxy.getAllServerConfigurations();
					break;
		    	case ShellFacade.LOGIN_STARTUP_COMPLETED:
		    		module.resetData();
		    		_shellProxy.getAllServerConfigurations();
//		    		_shellProxy.removeAllConfiguredServers();
//					module.processContent();
			        break;
			    case ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED:
			    	module.handleServerConfigurationRetrieval(ArrayCollection(data));
			    	break;
			   	case ShellProxy.CHECK_COMPATIBILITY_SERVICE_SUCCEEDED:
		    		module.handleCompatibilitySuccess(data);
			        break;
			   	case ShellProxy.USER_AUTHETICATION_SERVICE_SUCCEEDED:
		    		module.handleAuthenticationSuccess(ReqUserDetails(data));
		    		sendNotification(ShellFacade.VIEW_MENU_SCREEN);
//		    		sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW);
			        break;
				case ShellProxy.ADD_SERVER_TO_DATABASE_FAILED:
					AlertBuilder.getInstance().show("The server already exists.");
					break;
		   		case ShellProxy.CHECK_COMPATIBILITY_SERVICE_FAILED:
		   		case ShellProxy.USER_AUTHETICATION_SERVICE_FAILED:					
		    		module.handleAuthenticationFailure(Fault(data));
					break;
			    case ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_FAILED:
			    	CommonUtils.showDbFault(SQLError(data));
			    	break;
				case ShellProxy.LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED:
					module.handleMastekLogo(data);
					break;
			}
        }
        
	}
}