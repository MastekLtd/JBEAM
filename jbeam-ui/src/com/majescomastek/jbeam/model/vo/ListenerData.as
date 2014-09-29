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
	 * The class used for holding listener data.
	 */
	[Bindable]
	public class ListenerData extends BaseValueObject
	{
		public function ListenerData()
		{
		}
		
		private var _listenerId:Number;
		
		private var _noOfObjectsFailed:Number;
		
		private var _noOfObjectsExecuted:Number;
		
		private var _timeTaken:Number;	
		
		public function set listenerId(value:Number):void
	    { 
	    	_listenerId = value;
    	}        
	   
		public function get listenerId():Number
		{ 
			return _listenerId;
		}	
		public function set noOfObjectsFailed(value:Number):void
	    { 
	    	_noOfObjectsFailed = value;
    	}        
	   
		public function get noOfObjectsFailed():Number
		{ 
			return _noOfObjectsFailed;
		}	
		public function set noOfObjectsExecuted(value:Number):void
	    { 
	    	_noOfObjectsExecuted = value;
    	}        
	   
		public function get noOfObjectsExecuted():Number
		{ 
			return _noOfObjectsExecuted;
		}	
		public function set timeTaken(value:Number):void
	    { 
	    	_timeTaken = value;
    	}        
	   
		public function get timeTaken():Number
		{ 
			return _timeTaken;
		}	

	}
}