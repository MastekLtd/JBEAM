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
package com.majescomastek.jbeam.view.mediator.batchdetails
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.batchdetails.BatchDetailsModuleFacade;
	import com.majescomastek.jbeam.model.proxy.BatchDetailsProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.view.components.batchdetails.SystemInformationPod;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The mediator class for the InstallationPod view.
	 */
	public class SystemInformationPodMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "SYSTEM_INFORMATION_POD_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		/** The cached proxy reference */
		private var _batchDetailsProxy:BatchDetailsProxy;
		
		public function SystemInformationPodMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(SystemInformationPod.FETCH_SYSTEM_INFORMATION,
				onFetchSystemInformation, false, 0, true);			
			
		}
				
		private function onFetchSystemInformation(evt:CustomDataEvent):void
		{
			_batchDetailsProxy.getSystemInformation(BatchDetailsData(evt.eventData));
		}
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():SystemInformationPod
		{
			return viewComponent as SystemInformationPod;						
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRegister():void
		{
			_batchDetailsProxy = BatchDetailsProxy(facade.retrieveProxy(BatchDetailsProxy.NAME));
		}
		
		/**
		 * @inheritDoc
		 */
		override public function onRemove():void
		{
			module.removeEventListener(SystemInformationPod.FETCH_SYSTEM_INFORMATION,
				onFetchSystemInformation, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [BatchDetailsModuleFacade.SYSTEM_DETAILS_POD_STARTUP_COMPLETE,
					BatchDetailsProxy.GET_SYSTEM_INFORMATION_SUCCEEDED,
					BatchDetailsProxy.GET_SYSTEM_INFORMATION_FAILED				
				];
		}
		
		/**
		 * @inheritDoc
		 */
		override public function handleNotification(notification:INotification):void
		{
			var name:String = notification.getName();
			var type:String = notification.getType();
			var data:Object = notification.getBody();
			
			// Halt processing if the notification is not meant for this mediator.
			if(type != null && type != mediatorName)	return;
			
			switch(name)
			{
				case BatchDetailsModuleFacade.SYSTEM_DETAILS_POD_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;				
				case BatchDetailsProxy.GET_SYSTEM_INFORMATION_SUCCEEDED:
					module.handleSystemInformationRetrieval(data);
					break;				
				case BatchDetailsProxy.GET_SYSTEM_INFORMATION_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator SystemInformationPodMediator');
					break;
			}
		}
		
	}
}