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
	public class CalendarData
	{
		public function CalendarData()
		{
			super();
		}
		
		private var _installationCode:String;
		private var _calendarName:String;	
		private var _nonWorkingDate:Number;
		private var _userId:String;
		private var _remark:String;
		private var _year:String;
		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}        
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set calendarName(value:String):void{ 
			_calendarName = value;
		}
		
		public function get calendarName():String{ 
			return _calendarName;
		}
		
		public function set nonWorkingDate(value:Number):void{ 
			_nonWorkingDate = value;
		}
		
		public function get nonWorkingDate():Number{ 
			return _nonWorkingDate;
		}

		public function set year(value:String):void{ 
			_year = value;
		}
		
		public function get year():String{ 
			return _year;
		}
		
		public function set userId(value:String):void{ 
			_userId = value;
		}
		
		public function get userId():String{ 
			return _userId;
		}
		
		public function set remark(value:String):void{ 
			_remark = value;
		}
		
		public function get remark():String{ 
			return _remark;
		}
		
		

	}
}