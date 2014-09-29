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
package com.majescomastek.jbeam.controller.usermaster
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.usermaster.ManageUserModuleFacade;
	import com.majescomastek.jbeam.model.proxy.ManageUserProxy;
	import com.majescomastek.jbeam.view.components.usermaster.ManageUserModule;
	import com.majescomastek.jbeam.view.mediator.usermaster.ManageUserMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ManageUserModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:ManageUserModule = ManageUserModule(notification.getBody());						
			facade.registerProxy(new ManageUserProxy());
			facade.registerMediator(new ManageUserMediator(view));
			sendNotification(ManageUserModuleFacade.MANAGE_USER_MODULE_STARTUP_COMPLETE , view); 
		}		
	}
}