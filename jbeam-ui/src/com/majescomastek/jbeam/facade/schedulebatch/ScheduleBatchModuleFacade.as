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
package com.majescomastek.jbeam.facade.schedulebatch
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.schedulebatch.NavigateToDefaultViewCommand;
	import com.majescomastek.jbeam.controller.schedulebatch.ScheduleBatchModuleStartupCommand;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.view.components.schedulebatch.ScheduleBatchModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class ScheduleBatchModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "SCHEDULE_BATCH_MODULE_FACADE";
		
		public function ScheduleBatchModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the schedule batch module */ 
		public static const SCHEDULE_BATCH_MODULE_STARTUP:String =
			"SCHEDULE_BATCH_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the schedule batch module */ 
		public static const SCHEDULE_BATCH_MODULE_STARTUP_COMPLETE:String =
			"SCHEDULE_BATCH_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request to start the navigation to default view */ 
		public static const NAVIGATE_DEFAULT_VIEW_STARTUP:String =
			"NAVIGATE_DEFAULT_VIEW_STARTUP";
	
		/**
		 * ScheduleBatchModuleFacade factory method
		 */
		public static function getInstance(key:String):ScheduleBatchModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new ScheduleBatchModuleFacade(key);
			}
			return ScheduleBatchModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the ScheduleBatchModule.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(SCHEDULE_BATCH_MODULE_STARTUP, ScheduleBatchModuleStartupCommand);
		 	registerCommand(NAVIGATE_DEFAULT_VIEW_STARTUP, NavigateToDefaultViewCommand);
			registerCommand(ShellFacade.LOAD_REQUESTED_MODULE, ModuleLoadCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:ScheduleBatchModule):void
		{
			sendNotification(SCHEDULE_BATCH_MODULE_STARTUP, view);
		}

		
	}
}