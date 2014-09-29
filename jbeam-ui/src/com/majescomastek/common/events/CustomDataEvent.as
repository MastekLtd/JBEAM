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
	 * A custom event class which carries data along with the event name.
	 */
	public class CustomDataEvent extends Event
	{
		/** The data carried by this event */
		private var _eventData:Object;
		
		/** The handle to the origin event just in case it is needed */
		private var _originEvent:Event;
		
		public function CustomDataEvent(type:String, eventData:Object, bubbles:Boolean=false, cancelable:Boolean=false, originEvent:Event=null)
		{
			super(type, bubbles, cancelable);
			_eventData = eventData;
		}
		
		/**
		 * Get the data which was used to create the event.
		 */
		public function get eventData():Object
		{
			return _eventData;
		}
		
		/**
		 * Set the data which needs to be sent with the event.
		 */
		public function set eventData(value:Object):void
		{
			_eventData = value;
		}
		
		/**
		 * Return the origin event which trigerred this event.
		 */
		public function get originEvent():Event
		{
			return _originEvent;
		}
		
		/**
		 * Set the origin event which fired this event.
		 */
		public function set originEvent(value:Event):void
		{
			_originEvent = value;
		}
		
	}
}