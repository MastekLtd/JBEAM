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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author sanjayts
 * 
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/facade/data $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/facade/data $
 * 
 * 1     3/05/10 4:26p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 4     3/05/10 3:02p Sanjay.sharma
 * Renamed BaseDataGridFacade.as to CustomDataGridContainerFacade.as
 * 
 * 3     3/05/10 9:49a Sanjay.sharma
 * Renamed DatagridFacade.as to BaseDataGridFacade.as
 * 
 * 2     3/03/10 4:42p Sandeepa
 * to make the file sync
 * 
 * 2     2/25/10 7:33p Sanjay.sharma
 * Added command registration code
 * 
 * 1     2/24/10 5:13p Sanjay.sharma
 * Added the facade DatagridFacade
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.facade.datagrid
{
	import com.majescomastek.common.puremvc.controls.controller.datagrid.CustomDataGridContainerStartupCommand;
	import com.majescomastek.common.puremvc.controls.controller.datagrid.DatagridPreferenceRetrievalCommand;
	import com.majescomastek.common.puremvc.controls.controller.datagrid.DatagridPreferenceSaveCommand;
	import com.majescomastek.common.puremvc.controls.controller.datagrid.DatagridPreferencesPopupStartupCommand;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.CustomDataGridContainer;
	import com.majescomastek.jbeam.common.framework.BaseFacade;
	
	/**
	 * The Facade class for the common Datagrid control.
	 */
	public class CustomDataGridContainerFacade extends BaseFacade
	{
		/** The name of this facade */
		public static const NAME:String = "DATAGRID_FACADE";
		
		/**
		 * Create a new DatagridFacade object with the passed in `key' as the name.
		 */ 
		public function CustomDataGridContainerFacade(key:String)
		{
			super(key);
		}
		
		/**
		 * The static factory method for the Void payment checks File.
		 */
		public static function getInstance(key:String):CustomDataGridContainerFacade
		{
			var facade:CustomDataGridContainerFacade = instanceMap[key];
			if(facade == null)
			{
				facade = instanceMap[key] = new CustomDataGridContainerFacade(key);
			}
			return facade;
		}
		
		/**
		 *  This function is invoked when module creation is completed 
		 */
		public function startup(app:CustomDataGridContainer):void
		{
			sendNotification(DATAGRID_STARTUP, app)
		}
		
		/**
		 * Initialize the controller for Pending Payment file
		 */
		override protected function initializeController():void
		{
			super.initializeController();
			
			registerCommand(DATAGRID_STARTUP, CustomDataGridContainerStartupCommand);
			registerCommand(RETRIEVE_DATAGRID_PREFERENCE, DatagridPreferenceRetrievalCommand);
			registerCommand(SAVE_DATAGRID_PREFERENCE, DatagridPreferenceSaveCommand);
			registerCommand(DATAGRID_PREFERENCES_POPUP_STARTUP, DatagridPreferencesPopupStartupCommand);
		}
		
		/** The notification constant for the Datagrid control startup */
		public static const DATAGRID_STARTUP:String = "DATAGRID_STARTUP";
		
		/** The notification constant for the Datagrid control startup completion */
		public static const DATAGRID_STARTUP_COMPLETE:String = "DATAGRID_STARTUP_COMPLETE";
		
		/** The notification constant for the retrieval request of a datagrid preference */
		public static const RETRIEVE_DATAGRID_PREFERENCE:String = "RETRIEVE_DATAGRID_PREFERENCE";
		
		/** The notification constant for saving a datagrid preference */
		public static const SAVE_DATAGRID_PREFERENCE:String = "SAVE_DATAGRID_PREFERENCE";
		
		/** The notification constant for the Datagrid preferences popup startup */
		public static const DATAGRID_PREFERENCES_POPUP_STARTUP:String = 'DATAGRID_PREFERENCES_POPUP_STARTUP';
		
		/** The notification constant for the Datagrid preferences popup startup complete*/
		public static const DATAGRID_PREFERENCES_POPUP_STARTUP_COMPLETE:String = 'DATAGRID_PREFERENCES_POPUP_STARTUP_COMPLETE';
	}
}