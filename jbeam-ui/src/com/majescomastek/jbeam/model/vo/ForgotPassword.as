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
 *
 * $Revision:: 1                                                                                                      $
 *	
 * $Header:: /Product_ $Log:: 
 * 
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	[Bindable]	
	public class ForgotPassword
	{
		private var _userId:String;
		private var _newPassword:String;
		private var _confirmPassword:String;
		
		public function ForgotPassword()
		{
		}

		public function set userId(value:String):void {
			this._userId = value;
		}
		public function get userId():String {
			return this._userId;
		}

		public function set newPassword(value:String):void {
			this._newPassword = value;
		}
		public function get newPassword():String {
			return this._newPassword;
		}

		public function set confirmPassword(value:String):void {
			this._confirmPassword = value;
		}
		public function get confirmPassword():String {
			return this._confirmPassword;
		}
	
	}
}
