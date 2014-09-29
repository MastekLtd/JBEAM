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
	import com.majescomastek.common.containers.dashboard.Pod;
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.batchdetails.BatchDetailsModuleFacade;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchRevisionPod;
	import com.majescomastek.jbeam.view.components.batchdetails.BatchSummaryPod;
	import com.majescomastek.jbeam.view.components.batchdetails.ObjectExecutionGraphPod;
	import com.majescomastek.jbeam.view.components.batchdetails.PerScanExecutionCountGraphPod;
	import com.majescomastek.jbeam.view.components.batchdetails.SystemInformationPod;
	import com.majescomastek.jbeam.view.mediator.batchdetails.BatchRevisionPodMediator;
	import com.majescomastek.jbeam.view.mediator.batchdetails.BatchSummaryPodMediator;
	import com.majescomastek.jbeam.view.mediator.batchdetails.ObjectExecutionGraphPodMediator;
	import com.majescomastek.jbeam.view.mediator.batchdetails.PerScanExecutionCountGraphPodMediator;
	import com.majescomastek.jbeam.view.mediator.batchdetails.SystemInformationPodMediator;
	
	import mx.collections.ArrayCollection;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class BatchDetailsPodListStartupCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			var pods:ArrayCollection = notification.getBody() as ArrayCollection;
			for each(var pod:Pod in pods)
			{
				if(pod is BatchRevisionPod)
				{
					facade.registerMediator(new BatchRevisionPodMediator(BatchRevisionPod(pod)));					
				}
				else if(pod is BatchSummaryPod)
				{
					facade.registerMediator(new BatchSummaryPodMediator(BatchSummaryPod(pod)));
				}
				else if(pod is ObjectExecutionGraphPod)
				{
					facade.registerMediator(new ObjectExecutionGraphPodMediator(ObjectExecutionGraphPod(pod)));
				}
				else if(pod is PerScanExecutionCountGraphPod)
				{
					facade.registerMediator(new PerScanExecutionCountGraphPodMediator(PerScanExecutionCountGraphPod(pod)));
				}
				else if(pod is SystemInformationPod)
				{
					facade.registerMediator(new SystemInformationPodMediator(SystemInformationPod(pod)));
				}
			}			
			sendNotification(BatchDetailsModuleFacade.BATCH_POD_STARTUP_COMPLETE);
			sendNotification(BatchDetailsModuleFacade.BATCH_SUMMARY_POD_STARTUP_COMPLETE);			
			sendNotification(BatchDetailsModuleFacade.SYSTEM_DETAILS_POD_STARTUP_COMPLETE);			
			sendNotification(BatchDetailsModuleFacade.OBJECT_EXECUTION_GRAPH_POD_STARTUP_COMPLETE);			
			sendNotification(BatchDetailsModuleFacade.PER_SCAN_EXECUTION_COUNT_GRAPH_POD_STARTUP_COMPLETE);			
		}
		
	}
}