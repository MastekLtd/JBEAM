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
package com.majescomastek.jbeam.model.dao
{
	import com.majescomastek.common.events.DaoFaultEvent;
	import com.majescomastek.common.events.DaoResultEvent;
	import com.majescomastek.jbeam.common.ResourceUtils;
	import com.majescomastek.jbeam.model.vo.StatementConfig;
	
	import flash.data.SQLConnection;
	import flash.data.SQLStatement;
	import flash.filesystem.File;
	import flash.net.Responder;
	
	/**
	 * The base/parent class for all DAO related classes in Jbeam application.
	 * 
	 * TODO:
	 * 	- add batch/bulk statement execution capabilities
	 *  - add statement/connection pooling
	 */
	public class BaseDao
	{
		private var _dbFile:File;
		
		private var _resultHandler:Function;
		
		private var _faultHandler:Function;

		public function BaseDao(dbFile:File=null)
		{
			super();
			
			_dbFile = dbFile;
		}
		
		public function set dbFile(dbFile:File):void
		{
			_dbFile = dbFile;
		}

		/**
		 * Optional nulls for result and fault handlers in case we need to ignore the
		 * return of the statement execution i.e. in cases of firing a delete or update statement.
		 * 
		 * When handling fault/result events, the `responder` parameter takes preference
		 * over the individual `resultHandler` and `faultHandler` function references.
		 */
		protected function executeQuery(config:StatementConfig,	stmtResponder:Responder=null,
			connResponder:Responder=null):void
		{
			var stmt:SQLStatement = getSqlStatement();
			if(config.itemClass != null)	stmt.itemClass = config.itemClass;
			stmt.text = config.query;
			mapParameters(stmt, config.parameters);
			stmt.execute(config.prefetch, stmtResponder);			
			stmt.sqlConnection.close(connResponder);
		}
		
		protected function getSqlStatement():SQLStatement
		{
			var stmt:SQLStatement = new SQLStatement();
			stmt.sqlConnection = getSqlConnection();
			return stmt;
		}
		
		/**
		 * Retrieve the SQL connection required for creating sql statements. This
		 * function uses the `dbFile` parameter passed to the constructor of your
		 * DAO to create connection and in absence of a database file reads it
		 * from the default location configured in properties file.
		 */
		protected function getSqlConnection():SQLConnection
		{
			var conn:SQLConnection = new SQLConnection();
			
			if(_dbFile == null)
			{
				var path:String = ResourceUtils.getString('ServerConfiguration','db_local_path');
				_dbFile = File.applicationStorageDirectory.resolvePath(path);				
			}
			conn.open(_dbFile);
			return conn;
		}
		
		/**
		 * Use the passed in map to populate the parameters of the given
		 * SQL statement.
		 */
		protected function mapParameters(stmt:SQLStatement, params:Object):void
		{
			for(var key:String in params)
			{
				if(params.hasOwnProperty(key))
				{
					stmt.parameters[key] = params[key];
				}
			}
		}
		
		/**
		 * The default SQL statement result handler. It creates a new
		 * DaoResultEvent object and passes it to the result handler
		 * of the DAO method in consideration.
		 * 
		 * Normally used in cases where no custom operation needs to
		 * be performed on the success/failure of the sql statement.
		 */
		protected function defaultSqlResultHandler(result:Object):void
		{
			var evt:DaoResultEvent = DaoResultEvent.createEvent(result);
			resultHandler(evt);
		}
		
		/**
		 * The default SQL statement fault handler. It creates a new
		 * DaoResultEvent object and passes it to the result handler
		 * of the DAO method in consideration.
		 * 
		 * Normally used in cases where no custom operation needs to
		 * be performed on the success/failure of the sql statement.
		 */
		protected function defaultSqlFaultHandler(fault:Object):void
		{
			var evt:DaoFaultEvent = DaoFaultEvent.createEvent(fault);
			faultHandler(evt);
		}
		
		/**
		 * Attach the result & fault handlers which are invoked when the
		 * DAO operation succeeds and fails respectively.
		 */
		protected function setHandlers(resultHandler:Function, faultHandler:Function):void
		{
			_resultHandler = resultHandler;
			_faultHandler = faultHandler;
		}

		protected function get resultHandler():Function
		{
			return _resultHandler;
		}
		
		protected function set resultHandler(resultHandler:Function):void
		{
			_resultHandler = resultHandler;
		}
		
		protected function get faultHandler():Function
		{
			return _faultHandler;
		}
		
		protected function set faultHandler(faultHandler:Function):void
		{
			_faultHandler = faultHandler;
		}

	}
	
}