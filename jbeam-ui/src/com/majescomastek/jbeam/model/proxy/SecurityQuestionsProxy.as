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
 * $Revision:: 1                                                                                                         
 * 
 * $Header:: 
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
	public class SecurityQuestionsProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "SECURITY_QUESTIONS_PROXY";
		
		public static const SECURITY_QUESTIONS_SUCCESS:String = "SECURITY_QUESTIONS_SUCCESS";
		public static const SECURITY_QUESTIONS_FAILED:String = "SECURITY_QUESTIONS_FAILED";
		
		public function SecurityQuestionsProxy(data:Object = null)
		{
			super(NAME, data);
		}
		
		
		/**
		 * 
		 */
		public function getMySecurityQuestions(userProfile:UserProfile,
													tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(getMySecurityQuestionsResultHandler, 
							  getMySecurityQuestionsFaultHandler);
			var delegate:ForgotPasswordWsDelegate = new ForgotPasswordWsDelegate();
			delegate.getSecurityDetails(userProfile, [responder], tokenData);
		}
		
		/**
		 * The result handler for the addLovRecord WS operation
		 */
		private function getMySecurityQuestionsResultHandler(event:ResultEvent):void
		{
			sendNotification(SECURITY_QUESTIONS_SUCCESS, event.result);
		}
		
		/**
		 * The fault handler for the addLovRecord WS operation
		 */
		private function getMySecurityQuestionsFaultHandler(event:FaultEvent):void
		{
			sendNotification(SECURITY_QUESTIONS_FAILED, event.fault);
		}
		
		
	}
}

