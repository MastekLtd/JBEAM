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
package com.majescomastek.jbeam.controller.batchdetails
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.batchdetails.BatchDetailsModuleFacade;
	import com.majescomastek.jbeam.model.proxy.BatchDetailsProxy;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchDetailsModule;
	import com.majescomastek.jbeam.view.mediator.batchdetails.BatchDetailsMediator;
	
	import org.puremvc.as3.multicore.interfaces.ICommand;
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class BatchDetailsModuleStartupCommand extends BaseSimpleCommand implements ICommand
	{
		/**
		 * @inheritDoc
		 */
		override public function execute(notification:INotification):void
		{
			var view:BatchDetailsModule = BatchDetailsModule(notification.getBody());						
			facade.registerProxy(new BatchDetailsProxy());
			facade.registerProxy(new ScheduleBatchProxy());
			facade.registerProxy(new InstallationListProxy());
			facade.registerMediator(new BatchDetailsMediator(view));
			sendNotification(BatchDetailsModuleFacade.BATCH_DETAILS_MODULE_STARTUP_COMPLETE , view); 
		}
		
	}
}