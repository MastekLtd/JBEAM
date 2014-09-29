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
/*
* Dispatched when a pod view state changes.
*/

package com.majescomastek.common.events
{
import flash.events.Event;

public class PodStateChangeEvent extends Event
{
	// Pod states
	public static var MINIMIZE:String = "minimize";
	public static var RESTORE:String = "restore";
	public static var MAXIMIZE:String = "maximize";
	public static var CLOSE:String = "close";
	public static var ADDALL:String = "addall";
	
	public function PodStateChangeEvent(type:String)
	{
		super(type, true, true);
	}
}
}