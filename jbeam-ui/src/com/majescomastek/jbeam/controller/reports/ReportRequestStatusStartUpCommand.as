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
 * @author Gourav Rai
 * 
 *
 * $Revision:: 1                  $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/reports/ReportRequestStatusStartUpCommand.as 1     4/07/10 11:56a Gourav.rai             $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/reports/ReportRequestStatusStartUpCommand.as                         $
 * 
 * 1     4/07/10 11:56a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.controller.reports
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.view.mediator.reports.ReportRequestStatusMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportRequestStatusStartUpCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{			
			facade.registerMediator(new ReportRequestStatusMediator(notification.getBody()));
			sendNotification(ReportsFacade.REPORT_REQUEST_STATUS_STARTUP_COMPLETE);
		}
		
	}
}