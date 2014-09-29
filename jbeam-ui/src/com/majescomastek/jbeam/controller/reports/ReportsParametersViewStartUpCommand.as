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
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/reports/ReportsParametersViewStartUpCommand.as 1     4/07/10 11:56a Gourav.rai           $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/controller/reports/ReportsParametersViewStartUpCommand.as                       $
 * 
 * 1     4/07/10 11:56a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.controller.reports
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.reports.ReportsFacade;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.view.components.reports.ReportsParametersView;
	import com.majescomastek.jbeam.view.mediator.reports.ReportsParametersViewMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ReportsParametersViewStartUpCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{			
			var data:Object = notification.getBody();
			var reportsParameterView:ReportsParametersView = 
					ReportsParametersView(data['reportsParameterViewIndex']);
			var moduleData:Object = data['moduleInfo'];
			var instData:InstallationData = moduleData.previousModuleData.installationData;
			if(instData == null)
			{
				instData = CommonConstants.INSTALLATION_DATA;				
			}
			facade.registerMediator(new ReportsParametersViewMediator(reportsParameterView));
			sendNotification(ReportsFacade.REPORTS_PARAMETERS_VIEW_STARTUP_COMPLETE, instData.installationCode);
		}
		
	}
}