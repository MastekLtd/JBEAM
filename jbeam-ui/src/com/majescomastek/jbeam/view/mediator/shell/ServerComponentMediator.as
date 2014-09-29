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
	import com.majescomastek.jbeam.view.components.shell.ServerComponent;
	
	import flash.errors.SQLError;
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ServerComponentMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "SERVER_COMPONENT_MEDIDATOR";
		
		private var _shellProxy:ShellProxy;
		
		public function ServerComponentMediator(viewComponent:ServerComponent)
		{
			super(NAME, viewComponent);
			
			module.addEventListener(ServerComponent.SERVER_CONFIGURATION_CHANGE, onServerConfigurationChange);
			module.addEventListener(ServerComponent.ADD_SERVER_CLICK, onAddServerClick, false, 0, true);
			module.addEventListener(ServerComponent.UPDATE_SERVER_CLICK, onUpdateServerClick, false, 0, true);
			module.addEventListener(ServerComponent.REMOVE_SERVER_CLICK, onRemoveServerClick, false, 0, true);
			module.addEventListener(ServerComponent.SHOW_LOGIN_SCREEN, onShowLoginScreen, false, 0, true);
			module.addEventListener(ServerComponent.CLEANUP_SERVER_COMPONENT_VIEW, onCleanupServerComponentView, false, 0, true);
		}
		
		/**
		 * The function invoked when the SERVER_CONFIGURATION_CHANGE event is fired.
		 */	
		private function onServerConfigurationChange(event:CustomDataEvent):void
		{
			try
			{
				_shellProxy.loadCompanyLogo(String(event.eventData));				
			}
			catch(e:Error){}
		}
		
		/**
		 * The function invoked when the CLEANUP_SERVER_COMPONENT_VIEW event is fired.
		 */
		private function onCleanupServerComponentView(event:Event=null):void
		{
			facade.removeMediator(NAME);
		}
		
		/**
		 * The function invoked when the SHOW_LOGIN_SCREEN event is fired.
		 */
		private function onShowLoginScreen(event:Event):void
		{
			sendNotification(ShellFacade.CHANGE_SERVER_TO_LOGIN_STARTUP);
		}
		
		/**
		 * The function invoked when the ADD_SERVER_CLICK event is fired.
		 */
		private function onAddServerClick(event:CustomDataEvent):void
		{
			_shellProxy.addServerToDatabase(JbeamServer(event.eventData));
		}
	
		/**
		 * The function invoked when the UPDATE_SERVER_CLICK event is fired.
		 */
		private function onUpdateServerClick(event:CustomDataEvent):void
		{
			_shellProxy.updateServerToDatabase(JbeamServer(event.eventData));
		}
	
		/**
		 * The function invoked when the REMOVE_SERVER_CLICK event is fired.
		 */		
		private function onRemoveServerClick(event:CustomDataEvent):void
		{
			_shellProxy.removeServerConfiguration(JbeamServer(event.eventData));			
		}

		/**
		 * Retrieve the view component associated with this mediator.
		 */
		private function get module():ServerComponent
		{
			return viewComponent as ServerComponent;
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
			module.removeEventListener(ServerComponent.ADD_SERVER_CLICK, onAddServerClick, false);
			module.removeEventListener(ServerComponent.UPDATE_SERVER_CLICK, onUpdateServerClick, false);
			module.removeEventListener(ServerComponent.REMOVE_SERVER_CLICK, onRemoveServerClick, false);
			module.removeEventListener(ServerComponent.SHOW_LOGIN_SCREEN, onShowLoginScreen, false);
			module.removeEventListener(ServerComponent.CLEANUP_SERVER_COMPONENT_VIEW, onCleanupServerComponentView, false);
		
			setViewComponent(null);
		}
		
		/**
        * The notifications this mediator is interested in.
        */
        override public function listNotificationInterests():Array
        {
            return [
            	ShellFacade.CHANGE_SERVER_CONFIG_STARTUP_COMPLETED,
            	ShellProxy.ADD_SERVER_TO_DATABASE_FAILED,
            	ShellProxy.ADD_SERVER_TO_DATABASE_SUCCEEDED,
            	ShellProxy.UPDATE_SERVER_TO_DATABASE_FAILED,
            	ShellProxy.UPDATE_SERVER_TO_DATABASE_SUCCEEDED,
            	ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED,
            	ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_FAILED,
            	ShellProxy.REMOVE_SERVER_CONFIGURATION_SUCCEEDED,
            	ShellProxy.REMOVE_SERVER_CONFIGURATION_FAILED,
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
        	var notificationName:String = notification.getName();
			switch (notificationName)
			{
		    	case ShellFacade.CHANGE_SERVER_CONFIG_STARTUP_COMPLETED:
		    	case ShellProxy.ADD_SERVER_TO_DATABASE_SUCCEEDED:
		    		module.reset();
		    		_shellProxy.getAllServerConfigurations();
		    		break;
		    	case ShellProxy.UPDATE_SERVER_TO_DATABASE_SUCCEEDED:
		    		module.reset();
		    		_shellProxy.getAllServerConfigurations();
		    		break;
				case ShellProxy.REMOVE_SERVER_CONFIGURATION_SUCCEEDED:
					module.reset();
					_shellProxy.getAllServerConfigurations();
					break;
				case ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_SUCCEEDED:
					module.handleServerConfigurationRetrieval(ArrayCollection(data));
					break;
				case ShellProxy.ADD_SERVER_TO_DATABASE_FAILED:
					AlertBuilder.getInstance().show("The server already exists.");
					break;
				case ShellProxy.LOAD_COMPANY_LOGO_SERVICE_SUCCEEDED:
					module.handleLoadImages(data);
					break;
				case ShellProxy.UPDATE_SERVER_TO_DATABASE_FAILED:
				case ShellProxy.GET_ALL_SERVER_CONFIGURATIONS_FAILED:
				case ShellProxy.REMOVE_SERVER_CONFIGURATION_FAILED:
					CommonUtils.showDbFault(SQLError(data));
					break;
			}
        }

	}
}