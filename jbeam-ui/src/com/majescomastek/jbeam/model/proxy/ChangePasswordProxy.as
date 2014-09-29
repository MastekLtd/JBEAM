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
package com.majescomastek.jbeam.model.proxy
{
	import com.majescomastek.jbeam.common.framework.BaseProxy;
	import com.majescomastek.jbeam.model.delegate.ManageUserWsDelegate;
	import com.majescomastek.jbeam.model.vo.UserCredential;
	
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class ChangePasswordProxy extends BaseProxy
	{
		/** The name of this proxy */
		public static const NAME:String = "CHANGE_PASSWORD_PROXY";
		
		/** Notification constant indicates success of change password service */
		public static const CHANGE_PASSWORD_SERVICE_SUCCEEDED:String = "CHANGE_PASSWORD_SERVICE_SUCCEEDED";
		
		/** Notification constant indicates failure of change password service */
		public static const CHANGE_PASSWORD_SERVICE_FAILED:String = "CHANGE_PASSWORD_SERVICE_FAILED";
		
		public function ChangePasswordProxy(proxyName:String=null, data:Object=null)
		{
			super(NAME, data);
		}
		
		/**
		 * Send request to change password. 
		 */
		public function changePassword(
			userCredentials:UserCredential, tokenData:Object=null):void			
		{
			var responder:IResponder =
				new Responder(changePasswordResultHandler, changePasswordFaultHandler);
			var delegate:ManageUserWsDelegate = new ManageUserWsDelegate();
			delegate.changePassword(userCredentials, [responder], tokenData);
		}
		
		private function changePasswordResultHandler(evt:ResultEvent):void
		{
			sendNotification(CHANGE_PASSWORD_SERVICE_SUCCEEDED, evt.result);
		}
		
		private function changePasswordFaultHandler(evt:FaultEvent):void
		{
			sendNotification(CHANGE_PASSWORD_SERVICE_FAILED, evt.fault);
		}
	}
}