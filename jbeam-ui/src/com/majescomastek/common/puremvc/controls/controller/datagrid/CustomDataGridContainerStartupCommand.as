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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/controller/ $
 * 
 * 1     3/05/10 4:26p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 4     3/05/10 3:02p Sanjay.sharma
 * Renamed BaseDataGridStartupCommand.as to CustomDataGridContainerStartupCommand.as
 * 
 * 3     3/05/10 9:56a Sanjay.sharma
 * Renamed component
 * 
 * 2     3/05/10 9:47a Sanjay.sharma
 * Renamed DatagridStartupCommand.as to BaseDataGridStartupCommand.as
 * 
 * 1     3/03/10 12:51p Admin
 * 
 * 3     2/25/10 7:32p Sanjay.sharma
 * Updated command
 * 
 * 2     2/24/10 5:14p Sanjay.sharma
 * Added header
 * 
 * 
 */
package com.majescomastek.common.puremvc.controls.controller.datagrid
{
	import com.majescomastek.common.puremvc.controls.facade.datagrid.CustomDataGridContainerFacade;
	import com.majescomastek.common.puremvc.controls.view.components.datagrid.CustomDataGridContainer;
	import com.majescomastek.common.puremvc.controls.view.mediators.datagrid.CustomDataGridContainerMediator;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class responsible for the startup of the Datagrid common control.
	 */
	public class CustomDataGridContainerStartupCommand extends BaseSimpleCommand
	{
		
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:CustomDataGridContainer = CustomDataGridContainer(notification.getBody());
			facade.registerProxy(new CommonProxy());
			facade.registerMediator(new CustomDataGridContainerMediator(view));
			sendNotification(CustomDataGridContainerFacade.DATAGRID_STARTUP_COMPLETE, notification);
		}
		
	}
}