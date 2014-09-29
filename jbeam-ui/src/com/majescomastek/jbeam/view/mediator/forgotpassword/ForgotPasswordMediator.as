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
 * @author Mandar Vaidya
 * 
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header::  $	
 *
 * $Log::  $
 * 
 * 
 * 
 */
package com.majescomastek.jbeam.view.mediator.forgotpassword
{
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.forgotpassword.ForgotPasswordFacade;
	import com.majescomastek.jbeam.view.components.forgotpassword.ForgotPasswordModule;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ForgotPasswordMediator extends BaseMediator
	{
		/** Name of the this mediator. */
		public static const NAME:String = 'FORGOT_PASSWORD_MEDIATOR';
		
		public function ForgotPasswordMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
		}
		
		/**
		 * Retrieve the reference to the view component handled by this mediator
		 */
		public function get module():ForgotPasswordModule
		{
			return viewComponent as ForgotPasswordModule;
		}

		/**
		 * List out the notifications which this mediator is interested in handling
		 */
		override public function listNotificationInterests():Array
		{
			return [				
				ForgotPasswordFacade.FORGOT_PASSWORD_STARTUP_COMPLETE,
				ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_INIT,
			];
		}
		
		/**
		 * Handle the notifications which this mediator is interested in handling.
		 */
		override public function handleNotification(notification:INotification):void
		{
			switch(notification.getName())
			{
				case ForgotPasswordFacade.FORGOT_PASSWORD_STARTUP_COMPLETE:
					sendNotification(
						ForgotPasswordFacade.VALIDATE_USER_ID_STARTUP, 
						module.validateUserId);
					sendNotification(
						ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_STARTUP,
						module.validateSecurityQuestions);
				 	break;
				case ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_INIT:
					module.currentState = 
						ForgotPasswordModule.VALIDATE_SECURITY_QUESTIONS_STATE;
				 	break;
			 	default:
			 		trace("Invaid Notification: "+notification.getName());
				 	break;
			}
		}
	}
}