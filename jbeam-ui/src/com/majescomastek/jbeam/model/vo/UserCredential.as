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
package com.majescomastek.jbeam.model.vo
{
	[Bindable]
	//[RemoteClass(alias="com.majescomastek.sbs.ws.valueobject.UserCredential")]
	[RemoteClass(alias="com.majescomastek.bpms.vo.UserCredential")]
	
	public class UserCredential extends BaseValueObject
	{
		public function UserCredential()
		{
			
		}
		private var _userId:String;
    	private var _password:String;
    	private var _newPassword:String;
    	
    	public function set userId(value:String):void{
    		this._userId = value;
    	}
    	public function set password(value:String):void{
    		this._password = value;
    	}
    	public function set newPassword(value:String):void{
    		this._newPassword = value;
    	}
    	
		public function get userId():String{
			return this._userId ;
    	}
    	public function get password():String{
    		return this._password ;
    	}
    	public function get newPassword():String{
    		return this._newPassword ;
    	}
	}
}