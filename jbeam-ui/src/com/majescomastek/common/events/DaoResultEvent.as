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

	/**
	 * The event class used to represent the success of of a SQL operation.
	 */
	public class DaoResultEvent extends Event
	{
		private static const RESULT:String = "RESULT";
		
		private var _result:Object;
		
		public function get result():Object
		{
			return _result;
		}

		public function DaoResultEvent(type:String, bubbles:Boolean=false,
			cancelable:Boolean=false, result:Object=null)
		{
			super(type, bubbles, cancelable);
			_result = result;
		}
		
		public static function createEvent(result:Object):DaoResultEvent
		{
			return new DaoResultEvent(RESULT, false, true, result);
		}
		
		override public function clone():Event
		{
			return new DaoResultEvent(type, bubbles, cancelable, result);
		}
		
	}
}