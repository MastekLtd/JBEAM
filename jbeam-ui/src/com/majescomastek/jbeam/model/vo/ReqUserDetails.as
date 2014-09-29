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
	
	public class ReqUserDetails extends BaseValueObject
	{
		public function ReqUserDetails()
		{
			super();
		}
		
		private var _userCredentials:UserCredential;		
		private var _roleData:RoleData;		
		private var _userProfile:UserProfile;		
		private var _installationEntity:InstallationEntity;
		private var _userInstallationRoleList:ArrayCollection;
		
		public function set userCredentials(value:UserCredential):void
	    { 
	    	_userCredentials = value;
    	}        
	   
		public function get userCredentials():UserCredential
		{ 
			return _userCredentials;
		}
		
		public function set roleData(value:RoleData):void
	    { 
	    	_roleData = value;
    	}        
	   
		public function get roleData():RoleData
		{ 
			return _roleData;
		}
		
		public function set userProfile(value:UserProfile):void
	    { 
	    	_userProfile = value;
    	}        
	   
		public function get userProfile():UserProfile
		{ 
			return _userProfile;
		}
		
		public function set installationEntity(value:InstallationEntity):void
	    { 
	    	_installationEntity = value;
    	}        
	   
		public function get installationEntity():InstallationEntity
		{ 
			return _installationEntity;
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