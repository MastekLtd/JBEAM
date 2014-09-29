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
package com.majescomastek.jbeam.controller.forgotpassword
{
	import com.majescomastek.jbeam.common.framework.BaseSimpleCommand;
	import com.majescomastek.jbeam.facade.forgotpassword.ForgotPasswordFacade;
	import com.majescomastek.jbeam.model.proxy.ValidateSecurityAnswersProxy;
	import com.majescomastek.jbeam.view.mediator.forgotpassword.ValidateSecurityQuestionsMediator;
	
	import org.puremvc.as3.multicore.interfaces.INotification;

	public class ValidateSecurityQuestionsStartUpCommand extends BaseSimpleCommand
	{
		override public function execute(notification:INotification):void
		{
			facade.registerMediator(new ValidateSecurityQuestionsMediator(notification.getBody()));
			facade.registerProxy(new ValidateSecurityAnswersProxy());
			sendNotification(ForgotPasswordFacade.VALIDATE_SECURITY_QUESTIONS_STARTUP_COMPLETE);
		}
	}
}