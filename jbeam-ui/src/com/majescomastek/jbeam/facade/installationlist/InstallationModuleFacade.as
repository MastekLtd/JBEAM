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
package com.majescomastek.jbeam.facade.installationlist
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.installationlist.BatchObjectWindowStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.FailedObjectsWindowStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationListModuleStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationModuleStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodListStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodRefreshCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodRefreshForInstructionCommand;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationListModule;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	/**
	 * The facade class for the InstallationListModule module.
	 */
	public class InstallationModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "INSTALLATION_MODULE_FACADE";
		
		public function InstallationModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the installation list module */ 
		public static const INSTALLATION_MODULE_STARTUP:String =
			"INSTALLATION_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the installation list module */ 
		public static const INSTALLATION_MODULE_STARTUP_COMPLETE:String =
			"INSTALLATION_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request for registering the created pods */
		public static const INSTALLATION_STARTUP:String = "INSTALLATION_STARTUP";
		
		/** The notification constant used to denote the registration completion of pods */
		public static const INSTALLATION_STARTUP_COMPLETE:String =
			"INSTALLATION_STARTUP_COMPLETE";

		/** The notification constant used to request the refresh of pod data */
		public static const REFRESH_INSTALLATION:String = "REFRESH_INSTALLATION";
		
		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_INSTALLATION_REFRESH:String = "REQUEST_INSTALLATION_REFRESH";

		/** The notification constant used to denote the request to register the failed objects window */
		public static const FAILED_OBJECTS_WINDOW_STARTUP:String = "FAILED_OBJECTS_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the failed objects window */
		public static const FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE:String =
			"FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE";
			
		/**
		 * The notification constant used to denote the request to navigate to the default view
		 */
		public static const NAVIGATE_DEFAULT_VIEW:String = "NAVIGATE_DEFAULT_VIEW";
		
		/**
		 * ShellFacade factory method
		 */
		public static function getInstance(key:String):InstallationModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new InstallationModuleFacade(key);
			}
			return InstallationModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the Shell.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(INSTALLATION_MODULE_STARTUP, InstallationModuleStartupCommand);
//		 	registerCommand(INSTALLATION_STARTUP, InstallationPodStartupCommand);
//		 	registerCommand(REFRESH_INSTALLATION_POD, InstallationPodRefreshCommand);
//		 	registerCommand(REFRESH_INSTALLATION_FOR_INSTRUCTION, InstallationPodRefreshForInstructionCommand);
		 	registerCommand(FAILED_OBJECTS_WINDOW_STARTUP, FailedObjectsWindowStartupCommand);
//		 	registerCommand(BATCH_OBJECT_WINDOW_STARTUP, BatchObjectWindowStartupCommand);
		 	registerCommand(ShellFacade.LOAD_REQUESTED_MODULE, ModuleLoadCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:InstallationModule):void
		{
			sendNotification(INSTALLATION_MODULE_STARTUP, view);
		}
		
	}

}