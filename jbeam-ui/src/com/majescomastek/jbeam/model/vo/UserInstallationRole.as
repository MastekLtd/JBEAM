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
	import mx.collections.ArrayCollection;
	
	
	public class UserInstallationRole
	{
		public function UserInstallationRole()
		{
		}
		private var _roleId:String;
		private var _userId:String;
		private var _installationCode:String;
		private var _userInstallationRoleList:ArrayCollection;
				
		public function set roleId(value:String):void
	    { 
	    	_roleId = value;
    	}        
	   
		public function get roleId():String
		{ 
			return _roleId;
		}
		
		public function set userId(value:String):void
	    { 
	    	_userId = value;
    	}        
	   
		public function get userId():String
		{ 
			return _userId;
		}
		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}        
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set userInstallationRoleList(value:ArrayCollection):void
	    { 
	    	_userInstallationRoleList = value;
    	}        
	   
		public function get userInstallationRoleList():ArrayCollection
		{ 
			return _userInstallationRoleList;
		}
	}
}