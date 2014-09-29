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
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	/**
	 * The Base class for all the proxies which interact with Web
	 * services in our application.
	 */
	public class ForgotPasswordProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "FORGOT_PASSWORD_PROXY";
		
		public static const FORGOT_PASSWORD_SUCCESS:String = "FORGOT_PASSWORD_SUCCESS";
		public static const FORGOT_PASSWORD_FAILED:String = "FORGOTE_PASSWORD_FAILED";
		
		public function ForgotPasswordProxy(data:Object = null)
		{
			super(NAME, data);
		}
		
		
		/**
		 * 
		 */
		public function setNewPassword(forgotPassword:ForgotPassword):void
		{
//			var delegate:ForgotPasswordWSDelegate = new ForgotPasswordWSDelegate(this);			
//			setResultHandler(setNewPasswordResultHandler);
//			setFaultHandler(setNewPasswordFaultHandler);
//			delegate.setNewPassword(forgotPassword);
		}
		
		/**
		 * The result handler for the addLovRecord WS operation
		 */
		private function setNewPasswordResultHandler(event:ResultEvent):void
		{
			sendNotification(FORGOT_PASSWORD_SUCCESS, event.result);
		}
		
		/**
		 * The fault handler for the addLovRecord WS operation
		 */
		private function setNewPasswordFaultHandler(event:FaultEvent):void
		{
			sendNotification(FORGOT_PASSWORD_FAILED, event.fault);
		}
		
		
	}
}

