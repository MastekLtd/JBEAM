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
	public class ReqVersion extends BaseValueObject
	{
		public function ReqVersion()
		{
			super();
		}
	    private var _majorVersion:String;
	    private var _minorVersion:String;
	    
	    public function set majorVersion(value:String):void
	    { 
	    	_majorVersion = value;
	    }
	    public function set minorVersion(value:String):void
	    { 
	    	_minorVersion = value;
	    }
	    
	    //getters
	    public function get majorVersion():String
	    { 
	    	return _majorVersion;
	    }
	    public function get minorVersion():String
	    { 
	    	return _minorVersion;
	    }

	}
}