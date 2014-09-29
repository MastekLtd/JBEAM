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
 * Manages saving state to a local SharedObject.
 * The states saved are the current tab, pod order, minimized pods,
 * maximized pods and if applicable the ViewStack index of pod content.
 */

package com.majescomastek.common.util.dashboard
{
import com.majescomastek.common.containers.dashboard.Pod;
import com.majescomastek.common.events.LayoutChangeEvent;

import flash.net.SharedObject;
	
public class StateManager
{
	// Contains a viewIndex property for managing the tabs and an object for each view keyed by a viewId String.
	private static var sharedObject:SharedObject = SharedObject.getLocal("com.majescomastek.common");
	
	private static const VIEW_INDEX:String = "viewIndex";				// Number in sharedObject.
	private static const ITEMS:String = "items";						// Array in view object.
	private static const MINIMIZED_ITEMS:String = "minimizedItems";		// Array in view object.
	private static const MAXIMIZED_ITEM:String = "maximizedItem";		// String in view object.
	private static const POD_VIEW_INDEXES:String = "podViewIndexes";	// Object in view object.
	private static const CLOSED_ITEMS:String = "closedItems";			// Array in view object.
	
	// Saves the index of a viewStack within a pod.
	public static function setPodViewIndex(viewId:String, podId:String, index:Number):void
	{
		var o:Object = getViewIdObject(viewId);
		if (o[POD_VIEW_INDEXES] == null)
			o[POD_VIEW_INDEXES] = new Object();
		
		o[POD_VIEW_INDEXES][podId] = index;
		
		sharedObject.flush();
	}
	
	// Returns the index of a viewStack within a pod.
	public static function getPodViewIndex(viewId:String, podId:String):Number
	{
		var o:Object = getViewIdObject(viewId);
		var podViewIndexes:Object = o[POD_VIEW_INDEXES];
		if (podViewIndexes != null)
			return podViewIndexes[podId] == null ? -1 : podViewIndexes[podId];
		else		
			return -1;
	}
	
	// Saves the TabBar index in the main app view.
	public static function setViewIndex(n:Number):void
	{
		sharedObject.data[VIEW_INDEX] = n;
		sharedObject.flush();
	}
	
	// Returns the TabBar index in the main app view.
	public static function getViewIndex():Number
	{
		return isNaN(sharedObject.data[VIEW_INDEX]) ? 0 : sharedObject.data[VIEW_INDEX];
	}
	
	// Handles layout updates.
	public static function setPodLayout(e:LayoutChangeEvent):void
	{
		var manager:PodLayoutManager = PodLayoutManager(e.currentTarget);
		var o:Object = getViewIdObject(manager.id);
		o[MAXIMIZED_ITEM] = manager.maximizedPod != null ? manager.maximizedPod.id : null;
		o[MINIMIZED_ITEMS] = podArrayToStringArray(manager.minimizedItems);
		o[CLOSED_ITEMS] = podArrayToStringArray(manager.closedItems);
		o[ITEMS] = podArrayToStringArray(manager.items);
		
		sharedObject.flush();
	}
	
	// Gets the id of each pod and puts it into an array.
	private static function podArrayToStringArray(podArray:Array):Array
	{
		var len:Number = podArray.length;
		var a:Array = new Array();
		for (var i:Number = 0; i < len; i++)
		{
			if(podArray[i] != null){
				a.push(Pod(podArray[i]).id);
			}
		}
		
		return a;
	}
	
	// Gets the saved layout index of a pod.
	public static function getPodIndex(viewId:String, itemId:String):Number
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[ITEMS];
		if (a != null)
		{
			var len:Number = a.length;
			for (var i:Number = 0; i < len; i++)
			{
				if (a[i] == itemId)
					return i;
			}
		}
		
		return -1;
	}
	
	// Gets the minimized layout index of a pod.
	public static function getMinimizedPodIndex(viewId:String, itemId:String):Number
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[MINIMIZED_ITEMS];
		if (a != null)
		{
			var len:Number = a.length;
			for (var i:Number = 0; i < len; i++)
			{
				if (a[i] == itemId)
					return i;
			}
		}
		
		return -1;
	}
	
	// Gets the closed layout index of a pod.
	public static function getClosedPodIndex(viewId:String, itemId:String):Number
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[CLOSED_ITEMS];
		if (a != null)
		{
			var len:Number = a.length;
			for (var i:Number = 0; i < len; i++)
			{
				if (a[i] == itemId)
					return i;
			}
		}
		
		return -1;
	}
	
	// Gets the closed layout index of all pods.
	public static function getAllClosedPodIndex(viewId:String):Array
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[CLOSED_ITEMS];
		return a;
	}
	
	// Gets the closed layout index of a pod.
	public static function getAllMinimizedPodIndex(viewId:String):Array
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[MINIMIZED_ITEMS];
		return a;
	}
	
	// Determines if the pod was maximized. There can only be one maximized pod per view.
	public static function isPodMaximized(viewId:String, itemId:String):Boolean
	{
		var o:Object = getViewIdObject(viewId);
		return o[MAXIMIZED_ITEM] == itemId;
	}
	
	// Determines if the pod was minimized.
	public static function isPodMinimized(viewId:String, itemId:String):Boolean
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[MINIMIZED_ITEMS];
		if (a != null)
		{
			var len:Number = a.length;
			for (var i:Number = 0; i < len; i++)
			{
				if (a[i] == itemId)
					return true;
			}
		}
		return false;
	}
	
	// Determines if the pod was Closed.
	public static function isPodClosed(viewId:String, itemId:String):Boolean
	{
		var o:Object = getViewIdObject(viewId);
		var a:Array = o[CLOSED_ITEMS];
		if (a != null)
		{
			var len:Number = a.length;
			for (var i:Number = 0; i < len; i++)
			{
				if (a[i] == itemId)
					return true;
			}
		}
		return false;
	}
	
	// Get the view object from sharedObject.
	private static function getViewIdObject(viewId:String):Object
	{
		if (sharedObject.data[viewId] == null)
			sharedObject.data[viewId] = new Object();
			
		return sharedObject.data[viewId];
	}
}
}