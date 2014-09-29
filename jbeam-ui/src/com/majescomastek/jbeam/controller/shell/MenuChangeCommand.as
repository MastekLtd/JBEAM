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
package com.majescomastek.jbeam.controller.shell
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	
	import org.puremvc.as3.multicore.interfaces.INotification;
	import org.puremvc.as3.multicore.patterns.facade.Facade;

	/**
	 * The command used for requesting the change in the contents
	 * of the application menu bar based on the loaded module.
	 */
	public class MenuChangeCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var shellFacade:ShellFacade = ShellFacade(Facade.getInstance(ShellFacade.NAME));
			shellFacade.sendNotification(ShellFacade.CHANGE_MENU_CONTENTS, data);			
		}		
	}
}