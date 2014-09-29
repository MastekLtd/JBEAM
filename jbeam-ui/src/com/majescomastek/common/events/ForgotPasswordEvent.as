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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
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
package com.majescomastek.common.events
{
	import flash.events.Event;

	public class ForgotPasswordEvent extends BaseEvent
	{
		public static const CLOSE_POPUP:String = "CLOSE_POPUP";
		public static const CHANGE_TITLE:String = "CHANGE_TITLE";
		public static const VALIDATE_USER_ID_SUBMIT:String = "VALIDATE_USER_ID_SUBMIT";
		public static const VALIDATE_SECURITY_QUESTIONS_SUBMIT:String = "VALIDATE_SECURITY_QUESTIONS_SUBMIT";
		public static const CHANGE_PASSWORD_SUBMIT:String = "CHANGE_PASSWORD_SUBMIT";
		
		public function ForgotPasswordEvent(type:String, eventData:Object, bubbles:Boolean=false, cancelable:Boolean=false, originEvent:Event=null)
		{
			super(type, eventData, bubbles, cancelable, originEvent);
		}
	}
}