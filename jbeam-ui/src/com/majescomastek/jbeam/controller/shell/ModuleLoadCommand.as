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
 * @author sanjayts
 * 
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/shell/ModuleLoad $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/shell/ModuleLoad $
 * 
 * 1     12/11/09 2:42p Sanjay.sharma
 * added the command to navigate to a new module
 * 
 * 
 */
package com.majescomastek.jbeam.controller.shell
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	
	import org.puremvc.as3.multicore.interfaces.INotification;
	import org.puremvc.as3.multicore.patterns.facade.Facade;

	/**
	 * The command class for loading the requested module based on the notification
	 * data passed in.
	 */
	public class ModuleLoadCommand extends BaseSimpleCommand
	{
		
		override public function execute(notification:INotification):void
		{
			// This functionality is encapsulated in the command so as to make
			// the sub-modules unaware of who the main core/facade is. If we
			// had not created this, we'd have to hardcode the reference to
			// ShellFacade in each and every module which needs the module
			// switching functionality.
			var o:Object = notification.getBody();
			var shellFacade:ShellFacade = ShellFacade(Facade.getInstance(ShellFacade.NAME));
			shellFacade.sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, o);
		}
		
	}
}