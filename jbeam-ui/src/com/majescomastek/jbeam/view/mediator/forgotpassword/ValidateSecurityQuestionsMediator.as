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
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.common.CommonUtils;
	import com.majescomastek.jbeam.common.framework.BaseMediator;
	import com.majescomastek.jbeam.facade.forgotpassword.ForgotPasswordFacade;
	import com.majescomastek.jbeam.model.proxy.ValidateSecurityAnswersProxy;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	import com.majescomastek.jbeam.view.components.forgotpassword.ForgotPasswordModule;
	import com.majescomastek.jbeam.view.components.forgotpassword.ValidateSecurityQuestions;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.Fault;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ValidateSecurityQuestionsMediator extends BaseMediator
	{
		/** Name of the this mediator. */
		public static const NAME:String = 'VALIDATE_SECURITY_QUESTIONS_MEDIATOR';
		
		public function ValidateSecurityQuestionsMediator(viewComponent:Object=null)
		{
			super(NAME, viewComponent);
			module.addEventListener(ForgotPasswordEvent.VALIDATE_SECURITY_QUESTIONS_SUBMIT, onSubmit, false, 0, true);
		}
		
		/**
		 * Retrieve the reference to the view component handled by this mediator
		 */
		public function get module():ValidateSecurityQuestions
		{
			return viewComponent as ValidateSecurityQuestions;
		}
		
		/**
		 * List out the notifications which this mediator is interested in handling
		 */
		override public function listNotificationInterests():Array
		{
			return [				
				ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_STARTUP_COMPLETE,
				ForgotPasswordFacade.SET_SECURITY_QUESTIONS,
				ValidateSecurityAnswersProxy.RESET_PASSWORD_SUCCESS,
				ValidateSecurityAnswersProxy.RESET_PASSWORD_FAILED
			];
		}
		
		/**
		 * Handle the notifications which this mediator is interested in handling.
		 */
		override public function handleNotification(notification:INotification):void
		{
			switch(notification.getName())
			{
				case ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_STARTUP_COMPLETE:
				 	break;
				case ForgotPasswordFacade.SET_SECURITY_QUESTIONS:
					module.setSecurityQuestions(notification.getBody());
				 	break;
			 	case ValidateSecurityAnswersProxy.RESET_PASSWORD_SUCCESS:
					module.getValidateSecurityAnswersResult(notification.getBody());
				 	break;
			 	case ValidateSecurityAnswersProxy.RESET_PASSWORD_FAILED:
					module.getValidateSecurityAnswersFaultResult(notification.getBody());
					CommonUtils.showWsFault(Fault(notification.getBody()));
				 	break;
			 	default:
			 		trace("Invaid Notification: "+notification.getName());
				 	break;
			}
		}
		
		private function getValidateSecurityAnswersProxy():ValidateSecurityAnswersProxy
		{
			return facade.retrieveProxy(ValidateSecurityAnswersProxy.NAME) as ValidateSecurityAnswersProxy;
		}
		
		private function onSubmit(event:ForgotPasswordEvent):void
		{
			var objOutput:Object = event.eventData;

			var securityDetails:UserProfile = objOutput.userProfile;

			getValidateSecurityAnswersProxy().resetPassword(securityDetails);
		}
	}
}