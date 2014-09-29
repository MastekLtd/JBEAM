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
 * @author ritesh.umathe
 * 
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/events/PaginatedDataGridEven $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/events/PaginatedDataGridEven $
 * 
 * 1     2/25/10 9:29a Sanjay.sharma
 * Added a new custom event for custom datagrid class
 * 
 */
package com.majescomastek.common.events
{
	import flash.events.Event;
	
	/**
	 * The custom event class used by our custom datagrid.
	 */
	public class PaginatedDataGridEvent extends CustomDataEvent
	{
		
		public static const PAGE_NO_CLICK_EVENT:String = "pageNoClick";
		
		public static const PREVIOUS_CLICK_EVENT:String = "previousClick";
		
		public static const NEXT_CLICK_EVENT:String = "nextClick";
		
		public static const ITEM_PER_PAGE_CLICK_EVENT:String = "itemPerPageClick";
		
		public static const FETCH_DATA_EVENT:String = "fetchData";
		
		public static const GOTO_PAGE_CLICK_EVENT:String = "gotopage";
		
		/**
		 * Create a new DatagridEvent
		 */
		public function PaginatedDataGridEvent(type:String, eventData:Object, bubbles:Boolean=false, cancelable:Boolean=false, originEvent:Event=null)
		{
			super(type, eventData, bubbles, cancelable, originEvent);
		}
		
	}
}