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
package com.majescomastek.jbeam.model.delegate
{
	import com.majescomastek.jbeam.common.CommonConstants;
	import com.majescomastek.jbeam.model.vo.UserProfile;
	
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class ForgotPasswordWsDelegate extends BaseSoapWsDelegate
	{
		public function ForgotPasswordWsDelegate()
		{
			super();
		}

		/**
		 * Retrieve the security details which are assigned to the provided user data.
		 */
		public function getSecurityDetails(userProfile:UserProfile,
			externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(getSecurityDetailsResultHandler, defaultFaultHandler);
			invoke("getSecurityDetails", getInputForGetSecurityDetails(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForGetSecurityDetails(userProfile:UserProfile):XML
		{
			var parentXml:XML =  
				<s:getSecurityDetails xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
		         	<userProfile>
			         	<userId>{userProfile.userId}</userId>
		         	</userProfile>
			    </s:getSecurityDetails>;					
			return parentXml;
		}
		
		
		
		private function getSecurityDetailsResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, String(evt.result.description) + 
							CommonConstants.CONTACT_SYSTEM_ADMIN), 
							evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedUserDetailsResultData:Object = evt.result.userProfile;
				var userProfile:UserProfile = 
						createGetSecurityDetailsResultData(retrievedUserDetailsResultData);
				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(userProfile, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
		/**
		 * Create a Security Details object based on the data returned by the webservice.
		 */
		private function createGetSecurityDetailsResultData(
				retrievedUserDetailsResultData:Object):UserProfile
		{
			var securityDetailsResultData:UserProfile = new UserProfile();
			securityDetailsResultData.userId = retrievedUserDetailsResultData['userId'];		
			securityDetailsResultData.hintQuestion = retrievedUserDetailsResultData['hintQuestion'];		
			securityDetailsResultData.hintAnswer = retrievedUserDetailsResultData['hintAnswer'];		
			securityDetailsResultData.password = retrievedUserDetailsResultData['password'];
			return securityDetailsResultData;
		}
		
		/**
		 * Reset the password with provided data.
		 */
		public function resetPassword(userProfile:UserProfile,
			 externalResponders:Array, tokenData:Object=null):void
		{
			var internalResponder:IResponder =
				new Responder(resetPasswordResultHandler, defaultFaultHandler);
			invoke("resetPassword", getInputForResetPassword(userProfile),
				externalResponders, internalResponder, tokenData);
		}
		
		private function getInputForResetPassword(userProfile:UserProfile):XML
		{
			var xml:XML = 
				<s:resetPassword xmlns:s="http://services.server.ws.monitor.stgmastek.com/">
					<userProfile>
			        	<userId>{userProfile.userId}</userId>
			        	<hintQuestion>{userProfile.hintQuestion}</hintQuestion>
			        	<hintAnswer>{userProfile.hintAnswer}</hintAnswer>
			       	</userProfile>
			    </s:resetPassword>;
			return xml;
		}
		
		private function resetPasswordResultHandler(evt:ResultEvent):void
		{
			if(evt.result.responseType == CommonConstants.ERROR)
			{
				var faultEvt:FaultEvent = FaultEvent.createEvent
					(new Fault(null, CommonConstants.INVALID_ANSWER), evt.token, evt.message);
				super.defaultFaultHandler(faultEvt);								
			}
			else
			{
				var retrievedResetPasswordResultData:Object = evt.result;				
				// Create a new event containing the webservice result and invoke the
				// external responders attached using the `defaultResultHandler` method
				var newEvt:ResultEvent = ResultEvent.createEvent(retrievedResetPasswordResultData, evt.token, evt.message);
				super.defaultResultHandler(newEvt);
			}
		}
		
	}
}