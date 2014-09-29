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
package com.majescomastek.jbeam.facade.shell
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.shell.BatchDetailsCommand;
	import com.majescomastek.jbeam.controller.shell.LoginStartupCommand;
	import com.majescomastek.jbeam.controller.shell.MenuChangeCommand;
	import com.majescomastek.jbeam.controller.shell.MenuStartupCommand;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.controller.shell.ServerConfigStartupCommand;
	import com.majescomastek.jbeam.controller.shell.SetServerUrlCommand;
	import com.majescomastek.jbeam.controller.shell.StartupCommand;
	import com.majescomastek.jbeam.model.proxy.ShellProxy;
	import com.majescomastek.jbeam.view.components.shell.Shell;
	
	import org.puremvc.as3.multicore.patterns.facade.Facade;
	
	/**
	 * Concrete facade for the main application [shell]
	 */
	public class ShellFacade extends BaseFacade
	{
		public static const NAME:String = 'SHELL_FACADE';

		public static const STARTUP:String = 'STARTUP';

		public static const STARTUP_COMPLETED:String = 'STARTUP_COMPLETED';
		
		/** Notification constant indicates startup of Loin screen */
		public static const LOGIN_STARTUP:String = 'LOGIN_STARTUP';
		
		/** Notification constant indicates startup complete of Loin screen */
		public static const LOGIN_STARTUP_COMPLETED:String = 'LOGIN_STARTUP_COMPLETED';
		
		/** Notification constant indicates startup of the menu view */
		public static const MENU_STARTUP:String = 'MENU_STARTUP';
		
		/** Notification constant indicates startup complete of menu screen. */
		public static const MENU_STARTUP_COMPLETED:String = 'MENU_STARTUP_COMPLETED';
		
		/** Notification constant indicates viewing of menu screen */
		public static const VIEW_MENU_SCREEN:String = 'VIEW_MENU_SCREEN';	
		
		/** Notification constant indicates success of MenuDetails service */
		public static const MENU_DETAILS_SERIVCE_SUCCEEDED:String = 'MENU_DETAILS_SERIVCE_SUCCEEDED';
		
		/** Notification constant indicates failure of MenuDetails service */
		public static const MENU_DETAILS_SERIVCE_FAILED:String = 'MENU_DETAILS_SERIVCE_FAILED';
		
		public static const BATCH_DEAILS:String = 'BATCH_DEAILS';
		
		/** Notification constant indicates startup of batch details */
		public static const BATCH_DETAILS_STARTUP:String = 'BATCH_DETAILS_STARTUP';
		
		/** Notification constant indicates startup of batch details completed*/
		public static const BATCH_DETAILS_STARTUP_COMPLETED:String = 'BATCH_DETAILS_STARTUP_COMPLETED';
		
		/** Notification constant indicates startup of BPMS Installation */
		public static const BPMS_INSTALLATION_STARTUP:String = 'BPMS_INSTALLATION_STARTUP';	
		
		/** Notification constant indicates startup of BPMS Installation completed */
		public static const BPMS_INSTALLATION_STARTUP_COMPLETED:String = 'BPMS_INSTALLATION_STARTUP_COMPLETED';
		
		public static const VIEW_CHANGE_SERVER_CONFIG_SCREEN:String = 'VIEW_CHANGE_SERVER_CONFIG_SCREEN';
		
		public static const CHANGE_SERVER_CONFIG_STARTUP:String = 'CHANGE_SERVER_CONFIG_STARTUP';
		
		public static const CHANGE_SERVER_CONFIG_STARTUP_COMPLETED:String = 'CHANGE_SERVER_CONFIG_STARTUP_COMPLETED';
		
		public static const CHANGE_SERVER_TO_LOGIN_STARTUP:String = "CHANGE_SERVER_TO_LOGIN_STARTUP";
		
		public static const RUN_BATCH_STARTUP:String = "RUN_BATCH_STARTUP";
		
		/** The constant used to denote the request to set the url of the webservice server */
		public static const SET_SERVER_URL:String = "SET_SERVER_URL";
		
		/** Notification constant indicates load module  */
		public static const LOAD_MODULE:String = 'LOAD_MODULE';
		
		/** Load a new module programatically i.e. not via the menu item click */		
		public static const LOAD_REQUESTED_MODULE:String = "LOAD_REQUESTED_MODULE";	
		
		/**
		 * The notification constant used to denote the request to navigate to the default view
		 * when the user successfully logs in.
		 */
		public static const NAVIGATE_DEFAULT_VIEW:String = "NAVIGATE_DEFAULT_VIEW";
		
		/** The notification constant used by loaded modules to request a change in menu contents */
		public static const REQUEST_MENU_CHANGE:String = "REQUEST_MENU_CHANGE";
		
		/** The notification constant used to trigger the shell mediator to change menu contents */
		public static const CHANGE_MENU_CONTENTS:String = "CHANGE_MENU_CONTENTS";
		
		/**
		 * Constructor
		 */
		public function ShellFacade(key:String)
		{
			super(key);
		}
		
		/**
		 * ShellFacade factory method
		 */
		 public static function getInstance(key:String):ShellFacade
		 {
		 	if(instanceMap[key] == null)
		 	{
		 		instanceMap[key] = new ShellFacade(key);
		 	}
		 	return instanceMap[key] as ShellFacade;
		 }
		 
		/**
		 * Initialize the commands used by the Shell.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(STARTUP, StartupCommand);
		 	registerCommand(LOGIN_STARTUP, LoginStartupCommand);
		 	registerCommand(MENU_STARTUP, MenuStartupCommand);
		 	registerCommand(CHANGE_SERVER_CONFIG_STARTUP, ServerConfigStartupCommand);
		 	registerCommand(BATCH_DETAILS_STARTUP, BatchDetailsCommand);
//		 	registerCommand(SET_SERVER_URL, SetServerUrlCommand);
		 	registerCommand(LOAD_MODULE, ModuleLoadCommand);
		 	registerCommand(REQUEST_MENU_CHANGE, MenuChangeCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(shell:Shell):void
		{
			sendNotification(STARTUP, shell);
		}
		
		/**
		 *  Function which is used to delete the previous loaded facade
		 */ 
		public function destoryFacade(key:String):void
		{
			if(Facade.hasCore(key))
			{
				Facade.removeCore(key);
			}
			
		}

	}
}