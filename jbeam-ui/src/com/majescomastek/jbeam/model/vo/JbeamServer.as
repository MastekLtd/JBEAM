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
	public class JbeamServer
	{
		public function JbeamServer()
		{
			super();
		}
		private var _id:Number;
		private var _serverName:String;
	    private var _ipAddress:String;
	    private var _port:String;
	    
	    public function set id(value:Number):void
		{ 
			_id = value;
		}
		
		public function set serverName(value:String):void
		{ 
			_serverName = value;
		}
	    public function set ipAddress(value:String):void
	    { 
	    	_ipAddress = value;
    	}        
	   
	    public function set port(value:String):void
	    { 
	    	_port = value;
	    }
	    
	     //getters
		public function get id():Number
		{ 
			return _id;
		}
		public function get serverName():String
		{ 
			return _serverName;
		}
	    public function get ipAddress():String
	    { 
	    	return _ipAddress;
    	}        
	    
	    public function get port():String
	    { 
	    	return _port;
	    }

	}
}