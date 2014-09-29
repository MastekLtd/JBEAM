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
 * @author Sandeep A
 *
 * $Revision:: 3                                                                                      $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller $
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller $
 * 
 * 3     2/12/10 4:46p Sanjay.sharma
 * Made this class extend framework classes instead of PureMVC classes.
 * 
 * 2     2/09/10 2:32p Sandeepa
 * added copy right notice
 * 
 * 
 */
package com.majescomastek.jbeam.controller.shell
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.MenuDetailsProxy;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class MenuStartupCommand extends BaseSimpleCommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			facade.registerProxy(new MenuDetailsProxy());
			var menuDetailsProxy:MenuDetailsProxy = 
					facade.retrieveProxy(MenuDetailsProxy.NAME) as MenuDetailsProxy;
//			menuDetailsProxy.getMenuDetails(notification.getBody() as UserProfile);
			sendNotification(ShellFacade.MENU_STARTUP_COMPLETED);
		}
		
	}
}