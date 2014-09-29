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
package com.majescomastek.jbeam.view.mediator.viewschedule
{
	import com.majescomastek.jbeam.common.AlertBuilder;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.viewschedule.ViewScheduleModuleFacade;
	import com.majescomastek.jbeam.model.proxy.InstallationListProxy;
	import com.majescomastek.jbeam.view.components.viewschedule.EndReasonPopup;
	import com.majescomastek.jbeam.view.components.viewschedule.EndReasonWindow;
	
	import flash.events.Event;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	/**
	 * The medaitor class for the FailedObjectsWindow view,
	 */
	public class EndReasonPopUpMediator extends BaseMediator
	{
		/** The base name of this mediator */
		public static const NAME:String = "END_REASON_POPUP_MEDIATOR";
		
		/** The counter value which would be increased for every mediator created */
		public static var COUNTER:Number = 0;
		
		// Make sure the ShellProxy is registered in the startup command of the
		// facade which uses this mediator.
		private var _installationListProxy:InstallationListProxy;
		
		/**
		 * Create a new mediator object.
		 */
		public function EndReasonPopUpMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener
				(EndReasonWindow.CLEANUP_FAILED_OBJECT_WINDOW, onCleanupFailedObjectWindow, false, 0, true);
		}
		
		/**
		 * Retrieve the reference to the view mediated by this mediator
		 */
		private function get module():EndReasonPopup
		{
			return viewComponent as EndReasonPopup;						
		}
		
		/**
		 * The function invoked when the CLEANUP_FAILED_OBJECT_WINDOW event is fired.
		 */
		private function onCleanupFailedObjectWindow(event:Event):void
		{
			facade.removeMediator(this.mediatorName);
		}
		
		override public function onRegister():void
		{
			_installationListProxy = InstallationListProxy(facade.retrieveProxy(InstallationListProxy.NAME));
		}
		
		override public function onRemove():void
		{
			setViewComponent(null);
		}
		
		override public function listNotificationInterests():Array
		{
			return [ViewScheduleModuleFacade.END_REASON_WINDOW_STARTUP_COMPLETE
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
				case ViewScheduleModuleFacade.END_REASON_WINDOW_STARTUP_COMPLETE:
					module.handleStartupComplete(data);
					break;
				default:
					AlertBuilder.getInstance().show
						('Invalidation notification in mediator EndReasonPopUpMediator');
					break;
			}
		}

	}
}