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
	
	public class ResProcessRequestSchedule extends BaseValueObject
	{
		public function ResProcessRequestSchedule()
		{
			super();
		}
		
		private var _installationCode:String;
		private var _processRequestSchedulesList:ArrayCollection;
		private var _processRequestScheduleData:ProcessRequestScheduleData;
		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}        
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set processRequestSchedulesList(value:ArrayCollection):void
	    { 
	    	_processRequestSchedulesList = value;
    	}        
	   
		public function get processRequestSchedulesList():ArrayCollection
		{ 
			return _processRequestSchedulesList;
		}
		
		public function set processRequestSchedulesData(value:ProcessRequestScheduleData):void
	    { 
	    	_processRequestScheduleData = value;
    	}        
	   
		public function get processRequestSchedulesData():ProcessRequestScheduleData
		{ 
			return _processRequestScheduleData;
		}
		
	}
}