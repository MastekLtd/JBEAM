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
package com.majescomastek.jbeam.view.mediator.searchbatch
{
	import com.majescomastek.common.events.CustomDataEvent;
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.searchbatch.SearchBatchModuleFacade;
	import com.majescomastek.jbeam.facade.shell.ShellFacade;
	import com.majescomastek.jbeam.model.proxy.SearchBatchProxy;
	import com.majescomastek.jbeam.model.vo.ReqSearchBatch;
	import com.majescomastek.jbeam.view.components.searchbatch.SearchBatchModule;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class SearchBatchMediator extends BaseMediator
	{
		/** The name of this mediator */
		public static const NAME:String = "SEARCH_BATCH_MEDIATOR";
		
		private var _searchBatchProxy:SearchBatchProxy;

		/**
		 * Create a new mediator object.
		 */
		public function SearchBatchMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);		
			viewComponent.addEventListener(SearchBatchModule.SEARCH_BATCH_REQUEST,
				 onSearchBatchRequest, false, 0, true);
			viewComponent.addEventListener(SearchBatchModule.SHOW_BATCH_DETAILS_CLICK,
				onShowBatchDetailsClick, false, 0, true);
			viewComponent.addEventListener(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE,
				onShowAlertMessage,false,0,true);
		}
		
		/**
		 * The function invoked when the SHOW_ALERT_MESSAGE event is fired.
		 */
		private function onShowAlertMessage(event:CustomDataEvent):void
		{
			sendNotification(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE,event.eventData);
		}
		/**
		 * The function invoked when the SEARCH_BATCH_REQUEST event is fired.
		 */
		private function onSearchBatchRequest(evt:CustomDataEvent):void
		{
			_searchBatchProxy.searchBatch(ReqSearchBatch(evt.eventData));			
			sendNotification(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE, "");
		}
		
		/**
		 * The function invoked when the SHOW_BATCH_DETAILS_CLICK event is fired.
		 */
		private function onShowBatchDetailsClick(event:CustomDataEvent):void
		{
			sendNotification(ShellFacade.LOAD_REQUESTED_MODULE, event.eventData);
		}
		
		/**
		 * The function invoked when the CLEANUP_Search_BATCH event is fired.
		 */
		private function onCleanupSearchBatch(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():SearchBatchModule
		{
			return viewComponent as SearchBatchModule;						
		}
		
		override public function onRegister():void
		{
			_searchBatchProxy = SearchBatchProxy(facade.retrieveProxy(SearchBatchProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
			viewComponent.removeEventListener(SearchBatchModule.SEARCH_BATCH_REQUEST,
				 onSearchBatchRequest, false);
			viewComponent.removeEventListener(SearchBatchModule.SHOW_BATCH_DETAILS_CLICK, 
				onShowBatchDetailsClick);
			viewComponent.removeEventListener(SearchBatchModuleFacade.SHOW_ALERT_MESSAGE,
				onShowAlertMessage,false);
		}
		
		override public function listNotificationInterests():Array
		{
			return [SearchBatchModuleFacade.SEARCH_BATCH_MODULE_STARTUP_COMPLETE,
				SearchBatchProxy.SEARCH_BATCH_SERVICE_SUCCEEDED,
				SearchBatchProxy.SEARCH_BATCH_SERVICE_FAILED,
				SearchBatchModuleFacade.SHOW_ALERT_MESSAGE
				
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
				case SearchBatchModuleFacade.SEARCH_BATCH_MODULE_STARTUP_COMPLETE:
					module.handleStartupComplete();
					break;
				case SearchBatchProxy.SEARCH_BATCH_SERVICE_SUCCEEDED:
					module.handleSearchBatchServiceResult(ArrayCollection(data));
					break;
				case SearchBatchProxy.SEARCH_BATCH_SERVICE_FAILED:
					CommonUtils.showWsFault(Fault(data));
					break;
				case SearchBatchModuleFacade.SHOW_ALERT_MESSAGE:
					module.showAlertMessage(notification.getBody() as String);
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator SearchBatchMediator');
					break;
			}
		}
		
	}
}