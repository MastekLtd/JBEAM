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
	
	
	public class ReqUserInstallationRole
	{
		public function ReqUserInstallationRole()
		{
		}

		private var _userCredential:UserCredential;
		private var _userProfile:UserProfile;
		private var _installationEntity:InstallationEntity;
		private var _userInstallationRoleList:ArrayCollection;
		
		public function set userCredential(value:UserCredential):void
	    { 
	    	_userCredential = value;
    	}        
	   
		public function get userCredential():UserCredential
		{ 
			return _userCredential;
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