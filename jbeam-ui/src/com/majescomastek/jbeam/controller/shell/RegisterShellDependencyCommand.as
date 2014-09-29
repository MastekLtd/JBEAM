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
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.MenuDetailsProxy;
	import com.majescomastek.jbeam.model.proxy.ShellProxy;
	import com.majescomastek.jbeam.view.components.shell.Shell;
	import com.majescomastek.jbeam.view.mediator.shell.ShellMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;
	import org.puremvc.as3.multicore.patterns.command.SimpleCommand;
	
	public class RegisterShellDependencyCommand extends SimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			facade.registerProxy(new ShellProxy());
			facade.registerProxy(new MenuDetailsProxy());
			// Here `facade' refers to the `Facade' instance which registered
			// this command.
			var application:Shell = notification.getBody() as Shell;
			facade.registerMediator(new ShellMediator(application));
			
			// Send notification to the mediator registered above
			// to load the appropriate module in the ModuleLoader
			// present in Shell.mxml
			sendNotification(ShellFacade.STARTUP_COMPLETED, application);
		}

	}
}