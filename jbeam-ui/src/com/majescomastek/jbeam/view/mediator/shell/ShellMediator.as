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
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.MenuDetailsProxy;
	import com.majescomastek.jbeam.model.proxy.ShellProxy;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.shell.Footer;
	import com.majescomastek.jbeam.view.components.shell.LoginComponent;
	import com.majescomastek.jbeam.view.components.shell.Shell;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;
	import org.puremvc.as3.multicore.patterns.mediator.Mediator;
	
	public class ShellMediator extends Mediator
	{
		public static const NAME:String = 'SHELL_MEDIATOR';
		
		private var _menuProxy:MenuDetailsProxy;
		private var _shellProxy:ShellProxy;
		
		/**
		 * The ShellMediator constructor which creates a mediator
		 * for a given View Component passed in as the constructor
		 * argument.
		 */
		public function ShellMediator(application:Shell)
		{
			super(NAME, application);
			
			application.addEventListener(Shell.LOGOUT_CLICK, onLogoutClick, false, 0, true);
			application.addEventListener(Shell.HOME_CLICK, onHomeClick, false, 0, true);
			application.addEventListener(Shell.MENU_DETAILS_REQUEST, onMenuDetailsRequest, false, 0, true);
			application.addEventListener(Shell.DEFAULT_VIEW_REQUEST, onDefaultViewRequest, false, 0, true);
		}
		
		/**
		 * The function invoked when the LOGOUT_CLICK event is fired.
		 */
		private function onLogoutClick(event:Event):void
		{
			shell.cleanupMainView();
			sendNotification(ShellFacade.CHANGE_SERVER_TO_LOGIN_STARTUP);
		}
		
		/**
		 * The function invoked when the DEFAULT_VIEW_REQUEST event is fired.
		 */
		private function onDefaultViewRequest(event:Event):void
		{
			sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW);
		}
		
		/**
		 * The function invoked when the LOGOUT_CLICK event is fired.
		 */
		private function onMenuDetailsRequest(event:CustomDataEvent):void
		{
			_menuProxy.getMenuDetails(UserProfile(event.eventData));
			
		}
		
		/**
		 * The function invoked when the HOME_CLICK event is fired.
		 */
		private function onHomeClick(event:Event):void
		{
			sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW);
		}

		/**
         * The application component.
         */
        private function get shell():Shell
        {
            return viewComponent as Shell;
        }
        
        /**
		 * Cache the proxies required by this mediator when the mediator
		 * registration is complete.
		 */
		override public function onRegister():void
		{
			_menuProxy = MenuDetailsProxy(facade.retrieveProxy(MenuDetailsProxy.NAME));
			_shellProxy = ShellProxy(facade.retrieveProxy(ShellProxy.NAME));
		}
        /**
        * The notifications this mediator is interested in.
        */
        override public function listNotificationInterests():Array
        {
            return [
                    ShellFacade.STARTUP_COMPLETED,
                    ShellFacade.VIEW_MENU_SCREEN,
                    ShellFacade.MENU_DETAILS_SERIVCE_FAILED,
                    ShellFacade.BATCH_DETAILS_STARTUP,
                    ShellFacade.BATCH_DETAILS_STARTUP_COMPLETED,
                    ShellFacade.MENU_STARTUP_COMPLETED,                    
                    ShellFacade.MENU_DETAILS_SERIVCE_SUCCEEDED,
                    ShellFacade.VIEW_CHANGE_SERVER_CONFIG_SCREEN,
                    ShellFacade.CHANGE_SERVER_TO_LOGIN_STARTUP,
                    ShellFacade.RUN_BATCH_STARTUP,
                    ShellFacade.LOAD_REQUESTED_MODULE,
                    ShellFacade.NAVIGATE_DEFAULT_VIEW,
                    ShellFacade.CHANGE_MENU_CONTENTS,
					ShellProxy.LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED,
					ShellProxy.LOAD_COMPANY_LOGO_SERVICE_FAILED
                   ];
        }
        
        /**
        * This method will handle notifications normally generated by the
        * processing modules/classes like Shell controllers and Proxies to update
        * the View.
        * 
        * Typical scenarios include handling web service completion
        * notification generated by the Proxy.
        */
        override public function handleNotification(notification:INotification):void
        {
        	var fault:Fault;
        	var data:Object = notification.getBody();
            switch (notification.getName())
            {
            	case ShellFacade.VIEW_MENU_SCREEN:
                	shell.shellViewStack.selectedIndex = Shell.MENU_VIEW;
                	shell.userProfile = CommonConstants.USER_PROFILE;
                	sendNotification(ShellFacade.MENU_STARTUP, shell.userProfile);
                	break;
                case ShellFacade.MENU_STARTUP_COMPLETED:
                	shell.menuCollection = new ArrayCollection();
                	shell.handleMenuStartupCompletion();
                	break;
                case ShellFacade.MENU_DETAILS_SERIVCE_SUCCEEDED:
					shell.handleMenuDetailsRetreival(data);
//                	shell.menuCollection = notification.getBody() as ArrayCollection;
//                	sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW);
                 	break;
            	case ShellFacade.CHANGE_MENU_CONTENTS:
            		shell.handleMenuChange(data);
            		break;
            	case ShellFacade.NAVIGATE_DEFAULT_VIEW:
            		shell.showDefaultView(data);
            		shell.handleMenuChange(data);
            		break;
            	case ShellFacade.LOAD_REQUESTED_MODULE:
            		shell.loadModule(data);
            		shell.handleMenuChange(data);
            		break;
                case ShellFacade.STARTUP_COMPLETED:
                	shell.navigateToView(Shell.LOGIN);
                	sendNotification(ShellFacade.LOGIN_STARTUP,
                		shell.shellViewStack.selectedChild as LoginComponent);
                    break;
                case ShellFacade.VIEW_CHANGE_SERVER_CONFIG_SCREEN:
                	shell.navigateToView(Shell.CHANGE_SERVER_CONFIG);
                	sendNotification(ShellFacade.CHANGE_SERVER_CONFIG_STARTUP,
                		shell.shellViewStack.selectedChild);               	
                	break;
                case ShellFacade.CHANGE_SERVER_TO_LOGIN_STARTUP:
//					_shellProxy.removeAllConfiguredServers();
                	shell.unloadCurrentModule();
                	shell.navigateToView(Shell.LOGIN);
                	sendNotification(ShellFacade.LOGIN_STARTUP,
                		shell.shellViewStack.selectedChild as LoginComponent);
                    break;
				case ShellProxy.LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED:
					shell.handleLoadImages(data);
					break;
            }
        }
        
	}
}