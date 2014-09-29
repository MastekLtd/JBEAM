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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.majescomastek.common.events
{
	import flash.events.Event;

	public class ChangeViewEvent extends Event
	{
		private var _eventData:Object;
		
		public static const CHANGE_VIEW:String = "CHANGE_VIEW";
		
		public function ChangeViewEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public function get eventData():Object{
			return _eventData;
		}
		
		public function set eventData(eventData:Object):void{
			_eventData = eventData;
		}
		
		/*protected override function clone():Event{
			return 
		}*/
		
	}
}