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
	import flash.data.SQLConnection;
	
	/**
	 * The class containing the cofiguration data pertaining to the
	 * SQL statments.
	 */ 
	public class StatementConfig
	{
		public function StatementConfig()
		{
			super();
		}
		
		private var _query:String;
		
		private var _prefetch:int = -1;
		
		private var _parameters:Object = {};
		
		private var _itemClass:Class;
		
		private var _sqlConnection:SQLConnection;
		
		public function get query():String
		{
			return _query;
		}
		
		public function set query(value:String):void
		{
			_query = value;
		}
		
		public function get prefetch():int
		{
			return _prefetch;
		}
		
		public function set prefetch(value:int):void
		{
			_prefetch = value;
		}
		
		public function get parameters():Object
		{
			return _parameters;
		}
		
		public function set parameters(value:Object):void
		{
			_parameters = value;
		}
		
		public function get itemClass():Class
		{
			return _itemClass;
		}
		
		public function set itemClass(value:Class):void
		{
			_itemClass = value;
		}
		
		public function get sqlConnection():SQLConnection
		{
			return _sqlConnection;
		}
		
		public function set sqlConnection(value:SQLConnection):void
		{
			_sqlConnection = value;
		}

	}
}