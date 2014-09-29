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
package com.majescomastek.jbeam.facade.batchdetails
{
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	import com.majescomastek.jbeam.controller.batchdetails.BatchDetailsModuleStartupCommand;
	import com.majescomastek.jbeam.controller.batchdetails.BatchDetailsPodListStartupCommand;
	import com.majescomastek.jbeam.controller.batchdetails.BatchPodRefreshCommand;
	import com.majescomastek.jbeam.controller.batchdetails.BatchSummaryPodRefreshCommand;
	import com.majescomastek.jbeam.controller.batchdetails.ListenerWindowStartupCommand;
	import com.majescomastek.jbeam.controller.batchdetails.ObjectExecutionGraphPodRefreshCommand;
	import com.majescomastek.jbeam.controller.batchdetails.PerScanExecutionCountGraphPodRefreshCommand;
	import com.majescomastek.jbeam.controller.installationlist.BatchObjectWindowStartupCommand;
	import com.majescomastek.jbeam.controller.installationlist.FailedObjectsWindowStartupCommand;
	import com.majescomastek.jbeam.controller.schedulebatch.NavigateToDefaultViewCommand;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchDetailsModule;
	
	import org.puremvc.as3.multicore.interfaces.IFacade;

	public class BatchDetailsModuleFacade extends BaseFacade implements IFacade
	{
		/** The name of this facade */
		public static const NAME:String = "BATCH_DETAILS_MODULE_FACADE";
		
		public function BatchDetailsModuleFacade(key:String)
		{
			super(key);			
		}

		/** The notification constant used to denote the request to start the batch details module */ 
		public static const BATCH_DETAILS_MODULE_STARTUP:String =
			"BATCH_DETAILS_MODULE_STARTUP";
		
		/** The notification constant used to denote the startup completion the batch details module */ 
		public static const BATCH_DETAILS_MODULE_STARTUP_COMPLETE:String =
			"BATCH_DETAILS_MODULE_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request to start the batch pod module */ 
		public static const BATCH_POD_STARTUP:String =
			"BATCH_POD_STARTUP";
		
		/** The notification constant used to denote the startup completion the batch pod module */ 
		public static const BATCH_POD_STARTUP_COMPLETE:String =
			"BATCH_POD_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the startup completion the batch summary module */ 
		public static const BATCH_SUMMARY_POD_STARTUP_COMPLETE:String =
			"BATCH_SUMMARY_POD_STARTUP_COMPLETE";

		/** The notification constant used to denote the startup completion the system details module */ 
		public static const SYSTEM_DETAILS_POD_STARTUP_COMPLETE:String =
			"SYSTEM_DETAILS_POD_STARTUP_COMPLETE";

		/** The notification constant used to denote the startup completion the object execution graph module */ 
		public static const OBJECT_EXECUTION_GRAPH_POD_STARTUP_COMPLETE:String =
			"OBJECT_EXECUTION_GRAPH_POD_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the startup completion the object execution graph module */ 
		public static const PER_SCAN_EXECUTION_COUNT_GRAPH_POD_STARTUP_COMPLETE:String =
			"PER_SCAN_EXECUTION_COUNT_GRAPH_POD_STARTUP_COMPLETE";
		
		/** The notification constant used to request the refresh of pod data */
		public static const REFRESH_BATCH_POD:String = "REFRESH_BATCH_POD";
		
		/** The notification constant used to request the refresh of batch summary pod data */
		public static const REFRESH_BATCH_SUMMARY_POD:String = "REFRESH_BATCH_SUMMARY_POD";
		
		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_BATCH_POD_REFRESH:String = "REQUEST_BATCH_POD_REFRESH";
		
		/** The notification constant used to request the refresh of pod data */
		public static const REFRESH_OBJECT_EXECUTION_GRAPH_POD:String = "REFRESH_OBJECT_EXECUTION_GRAPH_POD";
		
		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH:String = "REQUEST_OBJECT_EXECUTION_GRAPH_POD_REFRESH";

		/** The notification constant used to request the refresh of pod data */
		public static const REFRESH_PER_SCAN_EXECUTION_COUNT_GRAPH_POD:String = "REFRESH_PER_SCAN_EXECUTION_COUNT_GRAPH_POD";
		
		/** The notification constant used by the parent view to request its pod to refresh their content */
		public static const REQUEST_PER_SCAN_EXECUTION_COUNT_GRAPH_POD_REFRESH:String = "REQUEST_PER_SCAN_EXECUTION_COUNT_GRAPH_POD_REFRESH";
		
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
			
		/** The notification constant used to denote the request to register the listener window */
		public static const LISTENER_WINDOW_STARTUP:String = "LISTENER_WINDOW_STARTUP";
		
		/** The notification constant used to denote the registeration completion of the listener window */
		public static const LISTENER_WINDOW_STARTUP_COMPLETE:String =
			"LISTENER_WINDOW_STARTUP_COMPLETE";
		
		/** The notification constant used to denote the request to start the navigation to default view */ 
		public static const NAVIGATE_DEFAULT_VIEW_STARTUP:String =
			"NAVIGATE_DEFAULT_VIEW_STARTUP";
			
		/**
		 * ShellFacade factory method
		 */
		public static function getInstance(key:String):BatchDetailsModuleFacade
		{
			if(instanceMap[key] == null)
			{
				instanceMap[key] = new BatchDetailsModuleFacade(key);
			}
			return BatchDetailsModuleFacade(instanceMap[key]);
		}
		
		/**
		 * Initialize the commands used by the Shell.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();

		 	registerCommand(BATCH_DETAILS_MODULE_STARTUP, BatchDetailsModuleStartupCommand);		 	
		 	registerCommand(BATCH_POD_STARTUP, BatchDetailsPodListStartupCommand);	
		 	registerCommand(REFRESH_BATCH_POD, BatchPodRefreshCommand);
		 	registerCommand(REFRESH_BATCH_SUMMARY_POD, BatchSummaryPodRefreshCommand);
		 	registerCommand(REFRESH_OBJECT_EXECUTION_GRAPH_POD, ObjectExecutionGraphPodRefreshCommand);
		 	registerCommand(REFRESH_PER_SCAN_EXECUTION_COUNT_GRAPH_POD, PerScanExecutionCountGraphPodRefreshCommand);
		 	registerCommand(BATCH_OBJECT_WINDOW_STARTUP, BatchObjectWindowStartupCommand); 	
		 	registerCommand(FAILED_OBJECTS_WINDOW_STARTUP, FailedObjectsWindowStartupCommand);
		 	registerCommand(LISTENER_WINDOW_STARTUP, ListenerWindowStartupCommand);
			registerCommand(NAVIGATE_DEFAULT_VIEW_STARTUP, NavigateToDefaultViewCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(view:BatchDetailsModule):void
		{
			sendNotification(BATCH_DETAILS_MODULE_STARTUP, view);
		}

		
	}
}