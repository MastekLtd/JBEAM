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
	 * The class used for holding object execution graph data. 
	 */
	[Bindable]
	public class ObjectExecutionGraphData extends BaseValueObject
	{
		public function ObjectExecutionGraphData()
		{
			super();
		}
		
		private var _batchNo:Number;
		
		private var _batchRevNo:Number;
		
		private var _graphId:String;		
		
		private var _collectTime:Number;
		
		private var _graphValue:Number;
		
		private var _installationCode:String;	
		
		
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
		
		public function set graphId(value:String):void
	    { 
	    	_graphId = value;
    	}        
	   
		public function get graphId():String
		{ 
			return _graphId;
		}
		
		public function set collectTime(value:Number):void
	    { 
	    	_collectTime = value;
    	}        
	   
		public function get collectTime():Number
		{ 
			return _collectTime;
		}
		
		
		public function set graphValue(value:Number):void
	    { 
	    	_graphValue = value;
    	}        
	   
		public function get graphValue():Number
		{ 
			return _graphValue;
		}
		
		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}        
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
	}
}