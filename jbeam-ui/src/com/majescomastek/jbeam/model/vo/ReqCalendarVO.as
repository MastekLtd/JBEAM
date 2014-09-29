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
	
	[Bindable]
	public class ReqCalendarVO extends BaseValueObject
	{
		public function ReqCalendarVO()
		{
			super();
		}
		
		private var _calendarData:CalendarData;
		private var _calendarList:ArrayCollection;
	
		public function set calendarData(value:CalendarData):void{ 
			_calendarData = value;
		}
		
		public function get calendarData():CalendarData{ 
			return _calendarData;
		}
		
		public function set calendarList(value:ArrayCollection):void{ 
			_calendarList = value;
		}
		
		public function get calendarList():ArrayCollection{ 
			return _calendarList;
		}
	}
}