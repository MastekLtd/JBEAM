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
 * @author Sandeep A
 * 
 *
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/mediat $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/mediat $
 * 
 * 2     3/18/10 9:12a Sanjay.sharma
 * Updated DatagridPreferencesPopupMediator
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 3     3/05/10 9:56a Sanjay.sharma
 * Renamed component
 * 
 * 2     3/04/10 5:18p Sanjay.sharma
 * handled retrieval success
 * 
 * 1     3/03/10 4:23p Sandeepa
 * to work with Custom datagrid
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.view.mediators.datagrid
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.common.puremvc.controls.facade.datagrid.CustomDataGridContainerFacade;
	import com.majescomastek.common.puremvc.controls.model.screenvo.DatagridPreferencesPopupScreenVO;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.DatagridPreferencesPopup;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	
	import flash.events.Event;
	
	import mx.managers.PopUpManager;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the DatagridPreferencesPopup view.
	 */
	public class DatagridPreferencesPopupMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = 'DATAGRID_PREFERENCES_POPUP_MEDIATOR';
		
		public function DatagridPreferencesPopupMediator(viewComponent:DatagridPreferencesPopup)
		{
			super(NAME, viewComponent);
			
			module.addEventListener(DatagridPreferencesPopup.SAVE_PREFERENCE_CLICK, onSavePreferenceClick, false, 0, true);
			module.addEventListener(DatagridPreferencesPopup.USE_DEFAULT_CLICK, onDefaultClick, false, 0, true);
			module.addEventListener(DatagridPreferencesPopup.CLEANUP, onPopupCleanup, false, 0, true);
		}
		
		/**
		 * The function invoked when the SAVE_PREFERENCE_CLICK is fired off.
		 */
		private function onSavePreferenceClick(event:CustomDataEvent):void
		{
			sendNotification(CustomDataGridContainerFacade.SAVE_DATAGRID_PREFERENCE, event.eventData);
		}
		
		/**
		 * The function invoked when the USE_DEFAULT_CLICK event is fired off.
		 */
		private function onDefaultClick(event:CustomDataEvent):void
		{
			sendNotification(CustomDataGridContainerFacade.RETRIEVE_DATAGRID_PREFERENCE, event.eventData);
		}
		
		/**
		 * The function invoked when the SAVE_PREFERENCE_CLICK is fired off.
		 */
		private function onPopupCleanup(event:Event):void
		{
			PopUpManager.removePopUp(module);
			
			module.removeEventListener(DatagridPreferencesPopup.SAVE_PREFERENCE_CLICK, onSavePreferenceClick, false);
			module.removeEventListener(DatagridPreferencesPopup.USE_DEFAULT_CLICK, onDefaultClick, false);
			module.removeEventListener(DatagridPreferencesPopup.CLEANUP, onPopupCleanup, false);			
			
			setViewComponent(null);
			facade.removeMediator(NAME);
		}
		
		/**
		 * Retrieve the view component which this medaitor mediates.
		 */
		public function get module():DatagridPreferencesPopup
		{
			return viewComponent as DatagridPreferencesPopup;
		}
		/**
		 * @inheritDoc
		 */
		override public function listNotificationInterests():Array
		{
			return [
				CustomDataGridContainerFacade.DATAGRID_PREFERENCES_POPUP_STARTUP_COMPLETE,
				CommonProxy.SAVE_DATAGRID_PREFERENCE_SUCCEEDED,
				CommonProxy.SAVE_DATAGRID_PREFERENCE_FAILED,
				CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED,
				CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_FAILED
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
				case CustomDataGridContainerFacade.DATAGRID_PREFERENCES_POPUP_STARTUP_COMPLETE:
					module.datagridPreferencesVO = body as DatagridPreferencesPopupScreenVO;
					break;
				case CommonProxy.SAVE_DATAGRID_PREFERENCE_SUCCEEDED:
					module.handleSaveSuccess(body);
					break;
				case CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_SUCCEEDED:
					module.handleRetrieveSuccess(body);
					break;
				case CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_FAILED:
				case CommonProxy.SAVE_DATAGRID_PREFERENCE_FAILED:
				case CommonProxy.RETRIEVE_DATAGRID_PREFERENCE_FAILED:
					CommonUtils.showWsFault(Fault(body));
					break;
				default:
					throw new Error("Invalid Notification in DatagridMediator");
			}
		}
	}
}