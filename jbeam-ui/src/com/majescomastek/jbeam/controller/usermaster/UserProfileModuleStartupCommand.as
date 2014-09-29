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
	import com.majescomastek.jbeam.facade.usermaster.UserProfileModuleFacade;
	import com.majescomastek.jbeam.model.proxy.UserProfileProxy;
	import com.majescomastek.jbeam.view.components.usermaster.UserProfileModule;
	import com.majescomastek.jbeam.view.mediator.usermaster.UserProfileMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class UserProfileModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:UserProfileModule = UserProfileModule(notification.getBody());						
			facade.registerProxy(new UserProfileProxy());
			facade.registerMediator(new UserProfileMediator(view));
			sendNotification(UserProfileModuleFacade.USER_PROFILE_MODULE_STARTUP_COMPLETE , view); 
		}		
	}
}