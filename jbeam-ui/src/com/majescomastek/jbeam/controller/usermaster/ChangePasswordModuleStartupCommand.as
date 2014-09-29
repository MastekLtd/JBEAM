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
	import com.majescomastek.jbeam.facade.usermaster.ChangePasswordModuleFacade;
	import com.majescomastek.jbeam.model.proxy.ChangePasswordProxy;
	import com.majescomastek.jbeam.view.components.usermaster.ChangePasswordModule;
	import com.majescomastek.jbeam.view.mediator.usermaster.ChangePasswordMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ChangePasswordModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:ChangePasswordModule = ChangePasswordModule(notification.getBody());						
			facade.registerProxy(new ChangePasswordProxy());
			facade.registerMediator(new ChangePasswordMediator(view));
			sendNotification(ChangePasswordModuleFacade.CHANGE_PASSWORD_MODULE_STARTUP_COMPLETE , view); 
		}		
	}
}