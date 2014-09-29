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
	import com.majescomastek.jbeam.view.components.shell.ServerComponent;
	import com.majescomastek.jbeam.view.mediator.shell.ServerComponentMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class used during the startup of the ServerComponent view
	 * class.
	 */
	public class ServerConfigStartupCommand extends BaseSimpleCommand
	{
		
		override public function execute(notification:INotification):void
		{
			var serverComponent:ServerComponent = ServerComponent(notification.getBody());
			facade.registerMediator(new ServerComponentMediator(serverComponent));
			sendNotification(ShellFacade.CHANGE_SERVER_CONFIG_STARTUP_COMPLETED);
		}
		
	}
}