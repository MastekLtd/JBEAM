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
	public class Installation
	{
		public function Installation()
		{
		}
		
		private var _installationCode:String;
		private var _installationDesc:String;
		private var _effDate:Number;
		private var _expDate:Number;
		private var _createdOn:Number;
		private var _createdBy:String;
		private var _modifiedOn:Number;
		private var _modifiedBy:String;
		private var _batchNo:Number;
		private var _batchRevNo:Number;
		private var _timezoneId:String;
		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}    
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set installationDesc(value:String):void
	    { 
	    	_installationDesc = value;
    	}        
	   
		public function get installationDesc():String
		{ 
			return _installationDesc;
		}
		
		public function set createdBy(value:String):void
	    { 
	    	_createdBy = value;
    	}        
	   
		public function get createdBy():String
		{ 
			return _createdBy;
		}
		
		public function set modifiedBy(value:String):void
	    { 
	    	_modifiedBy = value;
    	}        
	   
		public function get modifiedBy():String
		{ 
			return _modifiedBy;
		}
		
		public function set timezoneId(value:String):void
	    { 
	    	_timezoneId = value;
    	}        
	   
		public function get timezoneId():String
		{ 
			return _timezoneId;
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
		
		public function set createdOn(value:Number):void
	    { 
	    	_createdOn = value;
    	}        
	   
		public function get createdOn():Number
		{ 
			return _createdOn;
		}
		
		public function set modifiedOn(value:Number):void
	    { 
	    	_modifiedOn = value;
    	}        
	   
		public function get modifiedOn():Number
		{ 
			return _modifiedOn;
		}
		
		public function set batchNo(value:Number):void
	    { 
	    	_batchNo = value;
    	}        
	   
		public function get batchNo():Number
		{ 
			return _batchNo;
		}
		
		public function set batchRevNo(value:Number):void
	    { 
	    	_batchRevNo = value;
    	}        
	   
		public function get batchRevNo():Number
		{ 
			return _batchRevNo;
		}
		
		

	}
}