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
	public class RoleData
	{
		public function RoleData()
		{
		}

		private var _roleId:String;
		private var _roleName:String;
		private var _effDate:Number;
		private var _expDate:Number;
		
		public function set roleId(value:String):void
	    { 
	    	_roleId = value;
    	}        
	   
		public function get roleId():String
		{ 
			return _roleId;
		}
		
		public function set roleName(value:String):void
	    { 
	    	_roleName = value;
    	}        
	   
		public function get roleName():String
		{ 
			return _roleName;
		}
		
		public function set effDate(value:Number):void
	    { 
	    	_effDate = value;
    	}        
	   
		public function get effDate():Number
		{ 
			return _effDate;
		}
		
		public function set expDate(value:Number):void
	    { 
	    	_expDate = value;
    	}        
	   
		public function get expDate():Number
		{ 
			return _expDate;
		}
		
		
	}
}