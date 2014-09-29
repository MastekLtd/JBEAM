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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/mediat $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/mediat $
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 6     3/05/10 3:02p Sanjay.sharma
 * Renamed BaseDataGridMediator.as to CustomDataGridContainerMediator.as
 * 
 * 5     3/05/10 9:56a Sanjay.sharma
 * Renamed component
 * 
 * 4     3/05/10 9:48a Sanjay.sharma
 * Renamed DatagridMediator.as to BaseDataGridMediator.as
 * 
 * 3     3/04/10 5:09p Sanjay.sharma
 * updated mediator
 * 
 * 2     3/03/10 4:42p Sandeepa
 * to make the file sync
 * 
 * 2     2/25/10 7:34p Sanjay.sharma
 * Updated medaitor
 * 
 * 1     2/24/10 5:13p Sanjay.sharma
 * Added the mediator DatagridMediator
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.view.mediators.datagrid
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.common.puremvc.controls.facade.datagrid.CustomDataGridContainerFacade;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.CustomDataGridContainer;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.DatagridPreferencesPopup;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	
	import flash.events.Event;
	
	import mx.managers.PopUpManager;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The Mediator class for the Datagrid common control.
	 */
	public class CustomDataGridContainerMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "DATAGRID_MEDIATOR";
		
		/**
		 * Create a new DatagridMediator object.
		 */
		public function CustomDataGridContainerMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			
			module.addEventListener(CustomDataGridContainer.CLEANUP_DATAGRID, onCleanupDatagrid, false, 0, true);
			module.addEventListener(CustomDataGridContainer.CHANGE_DATAGRID_PREFERENCES_CLICK, onChangePreferencesClick, false, 0, true);
		}
		
		/**
		 * The function invoked when the CLEANUP_DATAGRID is fired off.
		 */
		private function onCleanupDatagrid(event:Event):void
		{
			facade.removeMediator(NAME);
		}
		
		/**
		 * The function invoked when the SAVE_PREFERENCE_CLICK is fired off.
		 */
		private function onSavePreferenceClick(event:CustomDataEvent):void
		{
			sendNotification(CustomDataGridContainerFacade.SAVE_DATAGRID_PREFERENCE, event.eventData);
		}
		
		/**
		 * The function invoked when the CHANGE_DATAGRID_PREFERENCES_CLICK is fired off.
		 */
		private function onChangePreferencesClick(event:Event):void
		{
			var preferencesPopup:DatagridPreferencesPopup = PopUpManager.createPopUp( module, DatagridPreferencesPopup, true) as DatagridPreferencesPopup;
			PopUpManager.centerPopUp(preferencesPopup);
			sendNotification(CustomDataGridContainerFacade.DATAGRID_PREFERENCES_POPUP_STARTUP, 
											module.getDatagridPreferences(preferencesPopup));
		}
		/**
		 * Retrieve the view component which this medaitor mediates.
		 */
		public function get module():CustomDataGridContainer
		{
			return viewComponent as CustomDataGridContainer;
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRemove():void
		{
			// Remove attached event listeners
			module.removeEventListener(CustomDataGridContainer.SAVE_PREFERENCE_CLICK, onSavePreferenceClick, false);
			
			// Nullify the view component			
			setViewComponent(null);
		}
		
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [
				CustomDataGridContainerFacade.DATAGRID_STARTUP_COMPLETE,
				CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED,
				CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_FAILED,
			];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var name:String = notification.getName();
			var body:Object = notification.getBody();
			
			switch(name)
			{
				case CustomDataGridContainerFacade.DATAGRID_STARTUP_COMPLETE:
					sendNotification(CustomDataGridContainerFacade.RETRIEVE_DATAGRID_PREFERENCE, module.getNotificationData());
					break;
				case CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED:
					module.handleRetrievalSuccess(body);
					break;
				case CommonProxy.SAVE_DATAGRID_PREFERENCE_SUCCEEDED:
					// nothing for the time being
					break;
				case CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_FAILED:
				case CommonProxy.SAVE_DATAGRID_PREFERENCE_FAILED:
					CommonUtils.showWsFault(Fault(body));
					break;
				default:
					throw new Error("Invalid Notification in DatagridMediator");
			}
		}
		
	}
}