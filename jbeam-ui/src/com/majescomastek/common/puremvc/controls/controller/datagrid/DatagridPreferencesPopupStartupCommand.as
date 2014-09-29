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
 * $Revision:: 2                                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $
 * 
 * 2     3/18/10 9:12a Sanjay.sharma
 * Updated DatagridPreferencesPopupStartupCommand
 * 
 * 1     3/05/10 4:26p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 1     3/03/10 4:26p Sandeepa
 * to work with Custom datagrid
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.controller.datagrid
{
	import com.majescomastek.common.puremvc.controls.facade.datagrid.CustomDataGridContainerFacade;
	import com.majescomastek.common.puremvc.controls.model.screenvo.DatagridPreferencesPopupScreenVO;
	import com.majescomastek.common.puremvc.controls.view.mediators.datagrid.DatagridPreferencesPopupMediator;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class responsible for the startup of the Datagrid common control.
	 */
	public class DatagridPreferencesPopupStartupCommand extends BaseSimpleCommand
	{
		
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var popupVO:DatagridPreferencesPopupScreenVO = notification.getBody() as DatagridPreferencesPopupScreenVO;
			facade.registerMediator(new DatagridPreferencesPopupMediator(popupVO.view));
			sendNotification(CustomDataGridContainerFacade.DATAGRID_PREFERENCES_POPUP_STARTUP_COMPLETE, popupVO);
		}
		
	}
}