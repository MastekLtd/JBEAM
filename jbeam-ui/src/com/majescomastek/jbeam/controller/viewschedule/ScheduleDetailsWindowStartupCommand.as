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
package com.majescomastek.jbeam.controller.viewschedule
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.installationlist.InstallationListModuleFacade;
	import com.majescomastek.jbeam.facade.schedulebatch.ScheduleBatchModuleFacade;
	import com.majescomastek.jbeam.facade.viewschedule.ViewScheduleModuleFacade;
	import com.majescomastek.jbeam.view.components.installationlist.BatchObjectWindow;
	import com.majescomastek.jbeam.view.components.viewschedule.ScheduleDetailsWindow;
	import com.majescomastek.jbeam.view.mediator.installationlist.BatchObjectWindowMediator;
	import com.majescomastek.jbeam.view.mediator.viewschedule.ScheduleDetailsWindowMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class for performing the startup related activites for
	 * the BatchObjectWindow view.
	 */
	public class ScheduleDetailsWindowStartupCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var scheduleDetailsWindow:ScheduleDetailsWindow = ScheduleDetailsWindow(data['view']);
			var mediator:ScheduleDetailsWindowMediator = new ScheduleDetailsWindowMediator(scheduleDetailsWindow); 
			facade.registerMediator(mediator);
			sendNotification(ViewScheduleModuleFacade.SCHEDULE_DETAILS_WINDOW_STARTUP_COMPLETE,
				data, mediator.getMediatorName());
		}		
	}
}