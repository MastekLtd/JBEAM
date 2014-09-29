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
	public class InstructionParameter extends BaseValueObject
	{
		public function InstructionParameter()
		{
			super();
		}
		
		private var _slNo:int;
		private var _name:String;
		private var _value:String; 
		private var _type:String;
		
		public function set slNo(value:int):void
	    { 
	    	_slNo = value;
    	}        
	   
		public function get slNo():int
		{ 
			return _slNo;
		}
		
		public function set name(value:String):void
	    { 
	    	_name = value;
    	}        
	   
		public function get name():String
		{ 
			return _name;
		}
		
		
		public function set value(value:String):void
	    { 
	    	_value = value;
    	}        
	   
		public function get value():String
		{ 
			return _value;
		}
		
		public function set type(value:String):void
	    { 
	    	_type = value;
    	}        
	   
		public function get type():String
		{ 
			return _type;
		}
		
	}
}