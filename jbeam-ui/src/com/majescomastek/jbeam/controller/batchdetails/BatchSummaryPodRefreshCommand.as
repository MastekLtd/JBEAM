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
	import com.majescomastek.jbeam.model.proxy.BatchDetailsProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The command class used to refresh the batch pod data.
	 * 
	 * This refresh command can encapsulate multiple operations like
	 * invoking multiple proxy methods for refreshing different parts
	 * of the screen. 
	 */
	public class BatchSummaryPodRefreshCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var data:Object = notification.getBody();
			var mediatorName:String = String(data['mediatorName']);
			var installationData:InstallationData = InstallationData(data['installationData']);
			var proxy:BatchDetailsProxy =
				BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));

			proxy.getBatchDetailsForRevisionPod(installationData, mediatorName);
		}
	}
}