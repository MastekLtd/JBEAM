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
package com.majescomastek.jbeam.controller.schedulebatch
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.schedulebatch.ScheduleBatchModuleFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.view.components.schedulebatch.ScheduleBatchModule;
	import com.majescomastek.jbeam.view.mediator.schedulebatch.ScheduleBatchMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ScheduleBatchModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:ScheduleBatchModule = ScheduleBatchModule(notification.getBody());						
			facade.registerProxy(new ScheduleBatchProxy());
			facade.registerProxy(new CommonProxy());
			facade.registerMediator(new ScheduleBatchMediator(view));
			sendNotification(ScheduleBatchModuleFacade.SCHEDULE_BATCH_MODULE_STARTUP_COMPLETE , view); 
		}
		
	}
}