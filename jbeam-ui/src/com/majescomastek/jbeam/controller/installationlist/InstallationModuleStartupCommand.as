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
package com.majescomastek.jbeam.controller.installationlist
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.installationlist.InstallationModuleFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationModule;
	import com.majescomastek.jbeam.view.mediator.installationlist.InstallationListModuleMediator;
	import com.majescomastek.jbeam.view.mediator.installationlist.InstallationModuleMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class used to perform the startup related activities of the
	 * InstallationModule view.
	 */
	public class InstallationModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:InstallationModule = InstallationModule(notification.getBody());
			facade.registerProxy(new InstallationListProxy());
			facade.registerProxy(new ScheduleBatchProxy());
			facade.registerMediator(new InstallationModuleMediator(view));
			sendNotification(InstallationModuleFacade.INSTALLATION_MODULE_STARTUP_COMPLETE, view); 
		}
		
	}
}