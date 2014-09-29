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
 */
package com.majescomastek.jbeam.view.mediator.forgotpassword
{
	import com.majescomastek.common.events.ForgotPasswordEvent;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.forgotpassword.ForgotPasswordFacade;
	import com.majescomastek.jbeam.model.proxy.SecurityQuestionsProxy;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.forgotpassword.ValidateUserId;
	
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ValidateUserIdMediator extends BaseMediator
	{
		/** Name of the this mediator. */
		public static const NAME:String = 'VALIDATE_USER_ID_MEDIATOR';
		
		public function ValidateUserIdMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(ForgotPasswordEvent.VALIDATE_USER_ID_SUBMIT, onSubmit, false, 0, true);
		}
		
		/**
		 * Retrieve the reference to the view component handled by this mediator
		 */
		public function get module():ValidateUserId
		{
			return viewComponent as ValidateUserId;
		}
		
		/**
		 * List out the notifications which this mediator is interested in handling
		 */
		override public function listNotificationInterests():Array
		{
			return [				
				ForgotPasswordFacade.VALIDATE_USER_ID_STARTUP_COMPLETE,
				SecurityQuestionsProxy.SECURITY_QUESTIONS_SUCCESS,
				SecurityQuestionsProxy.SECURITY_QUESTIONS_FAILED
			];
		}
		
		/**
		 * Handle the notifications which this mediator is interested in handling.
		 */
		override public function handleNotification(notification:INotification):void
		{
			switch(notification.getName())
			{
				case ForgotPasswordFacade.VALIDATE_USER_ID_STARTUP_COMPLETE:
				 	break;
			 	case SecurityQuestionsProxy.SECURITY_QUESTIONS_SUCCESS:
					var objOutput:Object = module.getSecurityQuestionsResult(notification.getBody());
					sendNotification(ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_INIT, null);
					sendNotification(ForgotPasswordFacade.SET_SECURITY_QUESTIONS, objOutput);
				 	break;
			 	case SecurityQuestionsProxy.SECURITY_QUESTIONS_FAILED:
					CommonUtils.showWsFault(Fault(notification.getBody()));
				 	break;
			 	default:
			 		trace("Invaid Notification: "+notification.getName());
				 	break;
			}
		}
		
		private function getSecurityQuestionsProxy():SecurityQuestionsProxy
		{
			return facade.retrieveProxy(SecurityQuestionsProxy.NAME) as SecurityQuestionsProxy;
		}
		
		private function onSubmit(event:ForgotPasswordEvent):void
		{
			var userProfile:UserProfile = new UserProfile();
			userProfile.userId = event.eventData as String
			getSecurityQuestionsProxy().getMySecurityQuestions(userProfile);
		}
	}
}