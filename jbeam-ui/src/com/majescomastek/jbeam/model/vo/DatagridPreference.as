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
 * @author sanjayts
 * 
 *
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/DatagridPreference $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/DatagridPreference $
 * 
 * 2     2/25/10 7:34p Sanjay.sharma
 * Added header
 * 
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	[Bindable]
	public class DatagridPreference extends BaseValueObject
	{
		public function DatagridPreference()
		{
			super();
		}
		
		private var _userId:String;
		
		private var _screenName:String;
		
		private var _datagridName:String;
		
		private var _datagridColumns:String;
		
		private var _defaultFlag:String;
		
		private var _isPaginationEnable:String;
		
		private var _defaultPageSize:String;
		
		private var _itemPerPageInterval:String;
		
		
		public function get userId():String
		{
			return _userId;
		}
		
		public function set userId(value:String):void
		{
			_userId = value;
		}
		
		public function get screenName():String
		{
			return _screenName;
		}
		
		public function set screenName(value:String):void
		{
			_screenName = value;
		}
		
		public function get datagridName():String
		{
			return _datagridName;
		}
		
		public function set datagridName(value:String):void
		{
			_datagridName = value;
		}
		
		public function get datagridColumns():String
		{
			return _datagridColumns;
		}
		
		public function set datagridColumns(value:String):void
		{
			_datagridColumns = value;
		}
		
		public function get defaultFlag():String
		{
			return _defaultFlag;
		}
		
		public function set defaultFlag(value:String):void
		{
			_defaultFlag = value;
		}
		
	
		public function get isPaginationEnable():String
		{
			return _isPaginationEnable;
		}
		
		public function set isPaginationEnable(value:String):void
		{
			_isPaginationEnable = value;
		}
		
		
		public function get defaultPageSize():String
		{
			return _defaultPageSize;
		}
		
		public function set defaultPageSize(value:String):void
		{
			_defaultPageSize = value;
		}
		
		
		public function get itemPerPageInterval():String
		{
			return _itemPerPageInterval;
		}
		
		public function set itemPerPageInterval(value:String):void
		{
			_itemPerPageInterval = value;
		}
		

	}

}