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
package com.majescomastek.jbeam.facade.usermaster
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.controller.usermaster.UserProfileModuleStartupCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.view.components.usermaster.UserProfileModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class UserProfileModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "USER_PROFILE_MODULE_FACADE";
		
		/** The notification constant used to denote the request to start the user profile module */ 
		public static const USER_PROFILE_MODULE_STARTUP:String =
			"USER_PROFILE_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the user profile module */ 
		public static const USER_PROFILE_MODULE_STARTUP_COMPLETE:String =
			"USER_PROFILE_MODULE_STARTUP_COMPLETE";
		
		
		public function UserProfileModuleFacade(key:String)
		{
			super(key);			
		}
		
		/**
		 * UserProfileModuleFacade factory method
		 */
		public static function getInstance(key:String):UserProfileModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new UserProfileModuleFacade(key);
			}
			return UserProfileModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the UserProfileModule.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(USER_PROFILE_MODULE_STARTUP, UserProfileModuleStartupCommand);
		 	registerCommand(ShellFacade.LOAD_REQUESTED_MODULE, ModuleLoadCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:UserProfileModule):void
		{
			sendNotification(USER_PROFILE_MODULE_STARTUP, view);
		}
		
	}
}