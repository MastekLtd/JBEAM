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
package com.majescomastek.jbeam.facade.calendar
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.calendar.CalendarModuleStartupCommand;
	import com.majescomastek.jbeam.view.components.calendar.CalendarModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	/**
	 * The facade class for the CalendarModule module.
	 */
	public class CalendarModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "CALENDAR_MODULE_FACADE";
		
		public function CalendarModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the calendar module */ 
		public static const CALENDAR_MODULE_STARTUP:String =
			"CALENDAR_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the calendar module */ 
		public static const CALENDAR_MODULE_STARTUP_COMPLETE:String =
			"CALENDAR_MODULE_STARTUP_COMPLETE";
		
		/**
		 * ShellFacade factory method
		 */
		public static function getInstance(key:String):CalendarModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new CalendarModuleFacade(key);
			}
			return CalendarModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the Calendar Module
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(CALENDAR_MODULE_STARTUP, CalendarModuleStartupCommand);
		 	
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:CalendarModule):void
		{
			sendNotification(CALENDAR_MODULE_STARTUP, view);
		}
		
	}

}