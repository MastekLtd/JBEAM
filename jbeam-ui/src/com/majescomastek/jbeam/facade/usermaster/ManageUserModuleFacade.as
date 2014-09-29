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
	import com.majescomastek.jbeam.controller.schedulebatch.NavigateToDefaultViewCommand;
	import com.majescomastek.jbeam.controller.usermaster.ManageUserModuleStartupCommand;
	import com.majescomastek.jbeam.view.components.usermaster.ManageUserModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class ManageUserModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "MANAGE_USER_MODULE_FACADE";
		
		/** The notification constant used to denote the request to start the manage user module */ 
		public static const MANAGE_USER_MODULE_STARTUP:String =
			"MANAGE_USER_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the manage user module */ 
		public static const MANAGE_USER_MODULE_STARTUP_COMPLETE:String =
			"MANAGE_USER_MODULE_STARTUP_COMPLETE";
		
		
		public function ManageUserModuleFacade(key:String)
		{
			super(key);			
		}
		
		/**
		 * ManageUserModuleFacade factory method
		 */
		public static function getInstance(key:String):ManageUserModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new ManageUserModuleFacade(key);
			}
			return ManageUserModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the ManageUserModule.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(MANAGE_USER_MODULE_STARTUP, ManageUserModuleStartupCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:ManageUserModule):void
		{
			sendNotification(MANAGE_USER_MODULE_STARTUP, view);
		}
		
	}
}