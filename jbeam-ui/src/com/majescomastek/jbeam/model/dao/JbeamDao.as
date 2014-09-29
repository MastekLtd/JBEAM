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
	import com.majescomastek.common.events.DaoResultEvent;
	import com.majescomastek.jbeam.model.vo.JbeamServer;
	import com.majescomastek.jbeam.model.vo.StatementConfig;
	
	import flash.net.Responder;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * The DAO class used for the Jbeam application. This class is not multi-
	 * invocation safe and hence a fresh instance be created for invoking any
	 * method operation.
	 * 
	 * TODO: Can this be made multi-invocation safe? I guess this can be done
	 * by creating a custom Responder class which can be passed to the execute
	 * method of SqlStatement. Then when the result/fault handler of this
	 * responder is invoked on success/failure of the SQL statement, we can
	 * retrieve the proxy callback handlers in the fault/result handler based
	 * on some prototypal magic?
	 */
	public class JbeamDao extends BaseDao
	{
		public function JbeamDao()
		{
			super();
		}
		
		/**
		 * Persist a given server configuration to a serverless sqlite database.
		 */
		public function addServer
			(jbeamServer:JbeamServer, resultHandler:Function=null, faultHandler:Function=null):void
		{
			setHandlers(resultHandler, faultHandler);
			
			var config:StatementConfig = new StatementConfig();
			config.query =
				"INSERT INTO MONITOR_SERVER_CONFIGURATION(servername, ipAddress, port) " + 
				"VALUES(:servername, :ipAddress, :port)";
			config.parameters = {
				":servername": jbeamServer.serverName,
				":ipAddress": jbeamServer.ipAddress,
				":port": jbeamServer.port
			};
			executeQuery(config, new Responder(defaultSqlResultHandler, defaultSqlFaultHandler));
		}
	
		/**
		 * Update a given server configuration to a serverless sqlite database.
		 */
		public function updateServer
			(jbeamServer:JbeamServer, resultHandler:Function=null, faultHandler:Function=null):void
		{
			setHandlers(resultHandler, faultHandler);
			
			var config:StatementConfig = new StatementConfig();
			config.query =
				"UPDATE MONITOR_SERVER_CONFIGURATION set " + 
				"ipAddress = :ipAddress, port=:port " + 
				"WHERE servername = :servername";
			config.parameters = {
				":ipAddress": jbeamServer.ipAddress,
				":port": jbeamServer.port,
				":servername": jbeamServer.serverName
			};
			executeQuery(config, new Responder(defaultSqlResultHandler, defaultSqlFaultHandler));
		}
	
		/**
		 * Retrieve all the server configurations from the database.
		 * Pass in the fault and result handlers which would be invoked
		 * when the sql statement successfully completes or fails.
		 */ 
		public function getAllServerConfigurations(resultHandler:Function, faultHandler:Function):void
		{
			setHandlers(resultHandler, faultHandler);
			var config:StatementConfig = new StatementConfig();
			config.itemClass = JbeamServer;
			config.query =
				"SELECT servername, ipAddress, port FROM MONITOR_SERVER_CONFIGURATION";
			executeQuery(config, new Responder(getAllServerConfigurationsResultHandler, defaultSqlFaultHandler));	
		}
		
		private function getAllServerConfigurationsResultHandler(result:Object):void
		{
			var serverList:ArrayCollection = new ArrayCollection(result.data);
			var evt:DaoResultEvent = DaoResultEvent.createEvent(serverList);
			resultHandler(evt);
		}
		
		/**
		 * Remove a server configuration from the datastore.
		 */
		public function removeServerConfiguration(server:JbeamServer, resultHandler:Function,
			faultHandler:Function):void
		{
			setHandlers(resultHandler, faultHandler);
			var config:StatementConfig = new StatementConfig();
			config.query = "DELETE FROM MONITOR_SERVER_CONFIGURATION WHERE serverName=:serverName";
			config.parameters = {
				":serverName": server.serverName
			}
			
			// In case you need to show the user a custom SQLError message, instead of
			// the defaultSqlFaultHandler, create your own fault handler which would
			// read the error message from the properties file and show it to the user.
			executeQuery(config, new Responder(defaultSqlResultHandler, defaultSqlFaultHandler));
		}
		
		/**
		 * Remove a server configuration from the datastore.
		 */
		public function removeAllConfiguredServers(resultHandler:Function, faultHandler:Function):void
		{
			setHandlers(resultHandler, faultHandler);
			var config:StatementConfig = new StatementConfig();
			config.query = "DELETE FROM MONITOR_SERVER_CONFIGURATION";
			executeQuery(config, new Responder(defaultSqlResultHandler, defaultSqlFaultHandler));
		}
		
	}
}