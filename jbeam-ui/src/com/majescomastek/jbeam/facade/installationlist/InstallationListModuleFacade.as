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
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodListStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodRefreshCommand;
	import com.majescomastek.jbeam.controller.installationlist.InstallationPodRefreshForInstructionCommand;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationListModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	/**
	 * The facade class for the InstallationListModule module.
	 */
	public class InstallationListModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "INSTALLATION_LIST_MODULE_FACADE";
		
		public function InstallationListModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the installation list module */ 
		public static const INSTALLATION_LIST_MODULE_STARTUP:String =
			"INSTALLATION_LIST_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the installation list module */ 
		public static const INSTALLATION_LIST_MODULE_STARTUP_COMPLETE:String =
			"INSTALLATION_LIST_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request for registering the created pods */
		public static const INSTALLATION_POD_LIST_STARTUP:String = "INSTALLATION_POD_LIST_STARTUP";
		
		/** The notification constant used to denote the registration completion of pods */
		public static const INSTALLATION_POD_LIST_STARTUP_COMPLETE:String =
			"INSTALLATION_POD_LIST_STARTUP_COMPLETE";

		/** The notification constant used to request the refresh of pod data */
		public static const REFRESH_INSTALLATION_POD:String = "REFRESH_INSTALLATION_POD";
		
		/** The notification constant used to request the refresh of pod data 
		 * for supplied instruction to run batch */
		public static const REFRESH_INSTALLATION_POD_FOR_INSTRUCTION:String 
				= "REFRESH_INSTALLATION_POD_FOR_INSTRUCTION";
		
		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_INSTALLATION_POD_REFRESH:String = "REQUEST_INSTALLATION_POD_REFRESH";

		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_INSTALLATION_POD_REFRESH_FOR_INSTRUCTION:String = "REQUEST_INSTALLATION_POD_REFRESH_FOR_INSTRUCTION";
		
		/** The notification constant used to denote the request to register the failed objects window */
		public static const FAILED_OBJECTS_WINDOW_STARTUP:String = "FAILED_OBJECTS_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the failed objects window */
		public static const FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE:String =
			"FAILED_OBJECTS_WINDOW_STARTUP_COMPLETE";
			
		/** The notification constant used to denote the request to register the batch object window */
		public static const BATCH_OBJECT_WINDOW_STARTUP:String = "BATCH_OBJECT_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the batch objects window */
		public static const BATCH_OBJECTS_WINDOW_STARTUP_COMPLETE:String =
			"BATCH_OBJECTS_WINDOW_STARTUP_COMPLETE";
		
		/**
		 * ShellFacade factory method
		 */
		public static function getInstance(key:String):InstallationListModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new InstallationListModuleFacade(key);
			}
			return InstallationListModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the Shell.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(INSTALLATION_LIST_MODULE_STARTUP, InstallationListModuleStartupCommand);
		 	registerCommand(INSTALLATION_POD_LIST_STARTUP, InstallationPodListStartupCommand);
		 	registerCommand(REFRESH_INSTALLATION_POD, InstallationPodRefreshCommand);
		 	registerCommand(REFRESH_INSTALLATION_POD_FOR_INSTRUCTION, InstallationPodRefreshForInstructionCommand);
		 	registerCommand(FAILED_OBJECTS_WINDOW_STARTUP, FailedObjectsWindowStartupCommand);
		 	registerCommand(BATCH_OBJECT_WINDOW_STARTUP, BatchObjectWindowStartupCommand);
		 	registerCommand(ShellFacade.LOAD_REQUESTED_MODULE, ModuleLoadCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:InstallationListModule):void
		{
			sendNotification(INSTALLATION_LIST_MODULE_STARTUP, view);
		}
		
	}

}