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
package com.majescomastek.jbeam.facade.forgotpassword
{
	import com.majescomastek.jbeam.controller.forgotpassword.ForgotPasswordStartUpCommand;
	import com.majescomastek.jbeam.controller.forgotpassword.ValidateSecurityQuestionsStartUpCommand;
	import com.majescomastek.jbeam.controller.forgotpassword.ValidateUserIdStartUpCommand;
	
	import org.puremvc.as3.multicore.patterns.facade.Facade;

	public class ForgotPasswordFacade extends Facade
	{
		public static const NAME:String = 'VALIDATE_USER_ID_FACADE';
		
		/**
		 * Constructor
		 */
		public function ForgotPasswordFacade(key:String)
		{
			super(key);
		}
		
		/**
		 * ForgotPasswordFacade factory method
		 */
		public static function getInstance(key:String):ForgotPasswordFacade
		{
			if(instanceMap[key] == null)
		 	{
		 		instanceMap[key] = new ForgotPasswordFacade(key);
		 	}
		 	return instanceMap[key] as ForgotPasswordFacade;
		}
		
		/**
		 * Initialize the commands used by the Shell.
		 */
		 override protected function initializeController():void
		 {
		 	super.initializeController();
		 	registerCommand(FORGOT_PASSWORD_STARTUP, ForgotPasswordStartUpCommand);
		 	registerCommand(VALIDATE_USER_ID_STARTUP, ValidateUserIdStartUpCommand);
		 	registerCommand(VALIDATE_SECURITY_QUESTIONS_STARTUP, ValidateSecurityQuestionsStartUpCommand);
		 }

		/**
		 * This function is invoked when the module in consideration is loaded/
		 * initialized.
		 */
		public function startup(app:Object):void
		{
			sendNotification(FORGOT_PASSWORD_STARTUP, app);
		}
		
		/**
		 *  Function which is used to delete the previous loaded facade
		 */ 
		public function destoryFacade(key:String):void
		{
			if(Facade.hasCore(key))
			{
				Facade.removeCore(key);
			}
			
		}
		
		public static const VALIDATE_USER_ID_STARTUP:String = "VALIDATE_USER_ID_STARTUP";
		public static const VALIDATE_USER_ID_STARTUP_COMPLETE:String = "VALIDATE_USER_ID_STARTUP_COMPLETE";

		public static const VALIDATE_SECURITY_QUESTIONS_STARTUP:String = "VALIDATE_SECURITY_QUESTIONS_STARTUP";
		public static const VALIDATE_SECURITY_QUESTIONS_STARTUP_COMPLETE:String = "VALIDATE_SECURITY_QUESTIONS_STARTUP_COMPLETE";
		public static const VALIDATE_SECURITY_QUESTIONS_INIT:String = "VALIDATE_SECURITY_QUESTIONS_INIT";
		public static const SET_SECURITY_QUESTIONS:String = "SET_SECURITY_QUESTIONS";

		public static const FORGOT_PASSWORD_STARTUP:String = "FORGOT_PASSWORD_STARTUP";
		public static const FORGOT_PASSWORD_STARTUP_COMPLETE:String = "FORGOT_PASSWORD_STARTUP_COMPLETE";
		public static const FORGOT_PASSWORD_INIT:String = "FORGOT_PASSWORD_INIT";
		public static const SET_USER_ID:String = "SET_USER_ID";
	}
}