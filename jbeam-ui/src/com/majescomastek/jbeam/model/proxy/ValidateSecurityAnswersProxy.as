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
 * @author Anand Singh
 *
 * $Revision:: 1                                                                                                         $Header:: 
 *
 * $Log:
 *  
 */
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.ForgotPasswordWsDelegate;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The Base class for all the proxies which interact with Web
	 * services in our application.
	 */
	public class ValidateSecurityAnswersProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "VALIDATE_SECURITY_ANSWERS_PROXY";
		
		public static const RESET_PASSWORD_SUCCESS:String = "RESET_PASSWORD_SUCCESS";
		public static const RESET_PASSWORD_FAILED:String = "RESET_PASSWORD_FAILED";
		
		public function ValidateSecurityAnswersProxy(data:Object = null)
		{
			super(NAME, data);
		}
		
		
		/**
		 * 
		 */
		public function resetPassword(securityDetails:UserProfile,
													tokenData:Object=null):void
		{
			var responder:IResponder =
				new Responder(resetPasswordResultHandler, resetPasswordFaultHandler);
			var delegate:ForgotPasswordWsDelegate = new ForgotPasswordWsDelegate();
			delegate.resetPassword(securityDetails, [responder], tokenData);
		}
		
		/**
		 * The result handler for the addLovRecord WS operation
		 */
		private function resetPasswordResultHandler(event:ResultEvent):void
		{
			sendNotification(RESET_PASSWORD_SUCCESS, event.result);
		}
		
		/**
		 * The fault handler for the addLovRecord WS operation
		 */
		private function resetPasswordFaultHandler(event:FaultEvent):void
		{
			sendNotification(RESET_PASSWORD_FAILED, event.fault);
		}
		
		
	}
}

