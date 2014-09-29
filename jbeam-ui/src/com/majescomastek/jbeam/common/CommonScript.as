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
 *
 */
import com.majescomastek.common.events.CustomDataEvent;

import flash.events.Event;

import mx.utils.StringUtil;

/**
 * Dispatch an event with the given name.
 */
public function sendEvent(eventName:String):void
{
	dispatchEvent(new Event(eventName, true));
}

/**
 * Dispatch a custom event with the given name & payload.
 */
public function sendDataEvent(eventName:String, eventData:Object):void
{
	dispatchEvent(new CustomDataEvent(eventName, eventData, true));
}

/**
 * Send a custom event which also encapsulates the source event.
 */
public function sendCustomEvent(eventName:String, eventData:Object, event:Event):void
{
	var customEvent:CustomDataEvent = new CustomDataEvent(eventName, eventData, true);
	customEvent.originEvent = event;
	dispatchEvent(customEvent);
}

/**
 * Determine whether the string passed in is NULL or contains only white spaces.
 */
public function isNullOrBlank(str:String):Boolean
{
	var valid:Boolean = str ? (StringUtil.trim(str).length == 0 ? true : false) : true;
	return valid; 
}