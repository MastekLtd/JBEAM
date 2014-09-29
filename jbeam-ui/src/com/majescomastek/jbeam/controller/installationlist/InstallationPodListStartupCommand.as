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
	import com.majescomastek.jbeam.facade.installationlist.InstallationListModuleFacade;
	import com.majescomastek.jbeam.view.components.installationlist.InstallationPod;
	import com.majescomastek.jbeam.view.mediator.installationlist.InstallationPodMediator;
	
	import mx.collections.ArrayCollection;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class for performing the startup related activities for
	 * the InstallationPod view.
	 */
	public class InstallationPodListStartupCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var pods:ArrayCollection = notification.getBody() as ArrayCollection;
			for each(var pod:InstallationPod in pods)
			{
				facade.registerMediator(new InstallationPodMediator(pod));				
			} 
			sendNotification(InstallationListModuleFacade.INSTALLATION_POD_LIST_STARTUP_COMPLETE); 			
		}		
	}
}