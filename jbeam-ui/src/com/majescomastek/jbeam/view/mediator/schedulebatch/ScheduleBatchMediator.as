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
package com.majescomastek.jbeam.view.mediator.schedulebatch
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.BusinessConstants;
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.ProgramNameConstants;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.schedulebatch.ScheduleBatchModuleFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.CommonProxy;
	import com.majescomastek.jbeam.model.proxy.ScheduleBatchProxy;
	import com.majescomastek.jbeam.model.vo.BatchDetailsData;
	import com.majescomastek.jbeam.model.vo.InstallationData;
	import com.majescomastek.jbeam.model.vo.ReqInstructionLog;
	import com.majescomastek.jbeam.view.components.schedulebatch.ScheduleBatchModule;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ScheduleBatchMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "SCHEDULE_BATCH_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		private var _scheduleBatchProxy:ScheduleBatchProxy;
		private var _commonProxy:CommonProxy;
		/**
		 * Create a new mediator object.
		 */
		public function ScheduleBatchMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(ScheduleBatchModule.RUN_BATCH_REQUEST,
				 onRunBatchRequest, false, 0, true);
			viewComponent.addEventListener(ScheduleBatchModule.GET_DROP_DOWN_DATA_REQUEST,
				retrieveDropDownEntries, false, 0, true);
		}
		
		/**
		 * Retrieve the list of Drop down entries based on the Drop down identifier
		 * list passed in.
		 */
		private function retrieveDropDownEntries(event:Event):void
		{
			_commonProxy.retrieveDropDownEntries(new ArrayCollection([BusinessConstants.SKIP_SCHEDULE_CODE]));
		}
		
		/**
		 * The function invoked when the RUN_BATCH_REQUEST event is fired.
		 */
		private function onRunBatchRequest(evt:CustomDataEvent):void
		{
			_scheduleBatchProxy.runBatch(ReqInstructionLog(evt.eventData));
		}
		
		/**
		 * The function invoked when the NAVIGATE_DEFAULT_VIEW_REQUEST event is fired.
		 * This function will navigate the user to the default Installation List Moddule. 
		 */
		private function onNavigateDefaultViewRequest(evt:CustomDataEvent):void
		{
			sendNotification(ShellFacade.NAVIGATE_DEFAULT_VIEW, Object(evt.eventData));
		}
		
		/**
		 * The function invoked when the CLEANUP_SCHEDULE_BATCH event is fired.
		 */
		private function onCleanupScheduleBatch(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():ScheduleBatchModule
		{
			return viewComponent as ScheduleBatchModule;						
		}
		
		override public function onRegister():void
		{
			_scheduleBatchProxy = ScheduleBatchProxy(facade.retrieveProxy(ScheduleBatchProxy.NAME));
			_commonProxy = CommonProxy(facade.retrieveProxy(CommonProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(ScheduleBatchModule.RUN_BATCH_REQUEST,
				 onRunBatchRequest, false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ScheduleBatchModuleFacade.SCHEDULE_BATCH_MODULE_STARTUP_COMPLETE,
				ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED,
				ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED,
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED,				
				CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED,
				ScheduleBatchProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED,
				ScheduleBatchProxy.GET_INSTALLATION_DATA_SERVICE_FAILED
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
				case ScheduleBatchModuleFacade.SCHEDULE_BATCH_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_SUCCEEDED:
//					var dropDownData:ArrayCollection = notification.getBody() as ArrayCollection;
					module.handleDropDownResult(data);
					break;
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_SUCCEEDED:
					module.handleRunBatchServiceResult(data);
					
					if(module.hBatchType.text == CommonConstants.DATE_RUN) {
						sendNotification(ScheduleBatchModuleFacade.NAVIGATE_DEFAULT_VIEW_STARTUP, data);
					} else {
						var moduleData:Object = {};
						moduleData["instructionSeqNo"] = BatchDetailsData(data).instructionSeqNo;
						moduleData["programName"] = ProgramNameConstants.BATCH_DETAILS_MODULE_PROGRAM_NAME;
						moduleData["installationData"] = module.moduleInfo.previousModuleData.installationData;
						sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, moduleData);
					}
					break;
				case ScheduleBatchProxy.GET_INSTALLATION_DATA_SERVICE_SUCCEEDED:
					module.handleInstallationListRetrieval(ArrayCollection(data));
					break;
				case ScheduleBatchProxy.RUN_BATCH_SERVICE_FAILED:
				case ScheduleBatchProxy.GET_INSTALLATION_DATA_SERVICE_FAILED:
				case CommonProxy.RETRIEVE_DROP_DOWN_ENTRIES_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator ScheduleBatchMediator');
					break;
			}
		}
		
	}
}