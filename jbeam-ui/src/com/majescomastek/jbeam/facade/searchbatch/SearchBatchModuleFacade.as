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
package com.majescomastek.jbeam.facade.searchbatch
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.searchbatch.SearchBatchModuleStartupCommand;
	import com.majescomastek.jbeam.controller.shell.ModuleLoadCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.view.components.searchbatch.SearchBatchModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class SearchBatchModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "SEARCH_BATCH_MODULE_FACADE";
		
		public function SearchBatchModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the search batch module */ 
		public static const SEARCH_BATCH_MODULE_STARTUP:String =
			"SEARCH_BATCH_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the search batch module */ 
		public static const SEARCH_BATCH_MODULE_STARTUP_COMPLETE:String =
			"SEARCH_BATCH_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant for the alert message */
		public static const SHOW_ALERT_MESSAGE:String = "SHOW_ALERT_MESSAGE";
		
		/**
		 * SearchBatchModuleFacade factory method
		 */
		public static function getInstance(key:String):SearchBatchModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new SearchBatchModuleFacade(key);
			}
			return SearchBatchModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the SearchBatchModule.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(SEARCH_BATCH_MODULE_STARTUP, SearchBatchModuleStartupCommand);
		 	registerCommand(ShellFacade.LOAD_REQUESTED_MODULE, ModuleLoadCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:SearchBatchModule):void
		{
			sendNotification(SEARCH_BATCH_MODULE_STARTUP, view);
		}

		
	}
}