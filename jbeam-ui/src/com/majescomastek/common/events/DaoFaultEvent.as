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
	 * The event class used to represent the failure of of a SQL operation.
	 */
	public class DaoFaultEvent extends Event
	{
		private static const FAULT:String = "FAULT";
		
		private var _fault:Object;
		
		public function get fault():Object
		{
			return _fault;
		}

		public function DaoFaultEvent(type:String, bubbles:Boolean=false,
			cancelable:Boolean=false, fault:Object=null)
		{
			super(type, bubbles, cancelable);
			_fault = fault;
		}
		
		public static function createEvent(fault:Object):DaoFaultEvent
		{
			return new DaoFaultEvent(FAULT, false, true, fault);
		}
		
		override public function clone():Event
		{
			return new DaoFaultEvent(type, bubbles, cancelable, fault);
		}
		
	}
}