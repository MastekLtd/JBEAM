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
package com.majescomastek.jbeam.facade.viewschedule
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.viewschedule.EndReasonWindowStartupCommand;
	import com.majescomastek.jbeam.controller.schedulebatch.NavigateToDefaultViewCommand;
	import com.majescomastek.jbeam.controller.viewschedule.EndReasonWindowStartupCommand;
	import com.majescomastek.jbeam.controller.viewschedule.ScheduleDetailsWindowStartupCommand;
	import com.majescomastek.jbeam.controller.viewschedule.ViewScheduleModuleStartupCommand;
	import com.majescomastek.jbeam.view.components.viewschedule.ViewScheduleModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class ViewScheduleModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "VIEW_SCHEDULE_MODULE_FACADE";
		
		public function ViewScheduleModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the schedule batch module */ 
		public static const VIEW_SCHEDULE_MODULE_STARTUP:String =
			"VIEW_SCHEDULE_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the schedule batch module */ 
		public static const VIEW_SCHEDULE_MODULE_STARTUP_COMPLETE:String =
			"VIEW_SCHEDULE_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request to start the navigation to default view */ 
		public static const NAVIGATE_DEFAULT_VIEW_STARTUP:String =
			"NAVIGATE_DEFAULT_VIEW_STARTUP";
	
		/** The notification constant used to denote the request to register the schedule details window */
		public static const SCHEDULE_DETAILS_WINDOW_STARTUP:String = "SCHEDULE_DETAILS_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the schedule details window */
		public static const SCHEDULE_DETAILS_WINDOW_STARTUP_COMPLETE:String =
			"SCHEDULE_DETAILS_WINDOW_STARTUP_COMPLETE";
	
		/** The notification constant used to denote the request to register the schedule details window */
		public static const END_REASON_WINDOW_STARTUP:String = "END_REASON_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the schedule details window */
		public static const END_REASON_WINDOW_STARTUP_COMPLETE:String =
			"END_REASON_WINDOW_STARTUP_COMPLETE";
		
		/**
		 * ScheduleBatchModuleFacade factory method
		 */
		public static function getInstance(key:String):ViewScheduleModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new ViewScheduleModuleFacade(key);
			}
			return ViewScheduleModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the ScheduleBatchModule.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(VIEW_SCHEDULE_MODULE_STARTUP, ViewScheduleModuleStartupCommand);
//		 	registerCommand(SCHEDULE_DETAILS_WINDOW_STARTUP, ScheduleDetailsWindowStartupCommand);
		 	registerCommand(NAVIGATE_DEFAULT_VIEW_STARTUP, NavigateToDefaultViewCommand);
//		 	registerCommand(END_REASON_WINDOW_STARTUP, EndReasonWindowStartupCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:ViewScheduleModule):void
		{
			sendNotification(VIEW_SCHEDULE_MODULE_STARTUP, view);
		}

		
	}
}