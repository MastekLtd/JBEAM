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
	/**
	 * The class used for holding system information. 
	 */
	[Bindable]
	public class SystemInformation extends BaseValueObject
	{
		public function SystemInformation()
		{
			super();
		}
		
		private var _batchNo:Number;
		
		private var _batchRevNo:Number;
		
		private var _javaVersion:String;		
		
		private var _osConfig:String;
		
		private var _usedMemory:Number;
		
		private var _maxMemory:Number;
		
		private var _preVersion:String;	
		
		
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
		
		public function set javaVersion(value:String):void
	    { 
	    	_javaVersion = value;
    	}        
	   
		public function get javaVersion():String
		{ 
			return _javaVersion;
		}
		
		public function set osConfig(value:String):void
	    { 
	    	_osConfig = value;
    	}        
	   
		public function get osConfig():String
		{ 
			return _osConfig;
		}
		
		
		public function set usedMemory(value:Number):void
	    { 
	    	_usedMemory = value;
    	}        
	   
		public function get usedMemory():Number
		{ 
			return _usedMemory;
		}
		
		
		public function set maxMemory(value:Number):void
	    { 
	    	_maxMemory = value;
    	}        
	   
		public function get maxMemory():Number
		{ 
			return _maxMemory;
		}
		
		
		public function set preVersion(value:String):void
	    { 
	    	_preVersion = value;
    	}        
	   
		public function get preVersion():String
		{ 
			return _preVersion;
		}
	}
}