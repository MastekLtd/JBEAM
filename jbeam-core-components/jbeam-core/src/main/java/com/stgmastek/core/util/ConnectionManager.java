/*
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
package com.stgmastek.core.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import stg.pr.engine.datasource.IDataSourceFactory;

import jdbc.pool.CConnectionPoolManager;

import com.stgmastek.core.common.ApplicationConnection;
import com.stgmastek.core.common.BatchConnection;
import com.stgmastek.core.exception.DatabaseException;

/**
 * 
 * The connection manager class for the core system.
 * 
 * The manager is responsible for all connection related activities from any 
 * of the resources within the core system that needs the connection for 
 * some database (both BATCH and Application) activity  
 * 
 * Implements the singleton pattern for a single instance per core system.
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class ConnectionManager {

	/** The static instance of the manager */
	private static ConnectionManager manager;
	private static Lock latch = new ReentrantLock();
	private IDataSourceFactory dsFactory;

	/**
	 * The private constructor to avoid any instantiation 
	 */
	private ConnectionManager(IDataSourceFactory factory) {
		this.dsFactory = factory;
	}

	/**
	 * The public static method to return the singleton instance
	 *  
	 * @return ConnectionManager 
	 * 		   The singleton instance  
	 */
	public static ConnectionManager getInstance() {
		return manager;
	}

	/**
	 * The public static method to return the singleton instance
	 *  
	 * @return ConnectionManager 
	 * 		   The singleton instance  
	 */
	public static ConnectionManager getInstance(IDataSourceFactory factory) {
		while (manager == null) {
			if (latch.tryLock()) {
				try {
					if (manager == null) {
						manager = new ConnectionManager(factory);
					}
				} finally {
					latch.unlock();
				}
			}
		}
		return manager;
	}
	
	/**
	 * The instance method to return the BATCH schema connection  
	 * In the development mode, it would use the connection directly from the JDBC Pool 
	 * and not use the connection from the PRE, whereas, in the PRODUCTION mode it would use
	 * the connection provided by the PRE
	 * 
	 * @return {@link BatchConnection}
	 * 		   The BATCH schema connection 
	 * @throws {@link DatabaseException}
	 * 		   Any connection creation issues would be raised as
	 * 		   DatabaseException 
	 */
	public BatchConnection getBATCHConnection()
			throws DatabaseException {

		Connection connection = null;
		 
		try {
			if(Constants.MODE.equals(Constants.DEV))
				try {
					connection = CConnectionPoolManager.getInstance(
								Constants.LOG4J_PROP, Constants.POOL_CONFIG).getConnection(
													Constants.POOL_NAMES.BATCH.name());
				} catch (Exception e) {
					throw new DatabaseException(e);
				}
			else
				connection = dsFactory.getDataSource(Constants.POOL_NAMES.BATCH.name()).getConnection();
			return new BatchConnection(connection);
		} catch (SQLException sqe) {
			throw new DatabaseException(sqe);
		} catch (IOException ioe) {
			throw new DatabaseException(ioe);
		}

	}

	/**
	 * The instance method to return the Application schema connection  
	 * The connection would always be retrieved from the JDBC Pool
	 * 
	 * @return {@link ApplicationConnection}
	 * 		   The Application schema connection 
	 * @throws {@link ApplicationConnection}
	 * 		   Any connection creation issues would be raised as
	 * 		   DatabaseException 
	 */	
	public ApplicationConnection getApplicationConnection() throws DatabaseException {

		Connection connection = null;
		try {
			if(Constants.MODE.equals(Constants.DEV))
				try {
					connection = CConnectionPoolManager.getInstance(
								Constants.LOG4J_PROP, Constants.POOL_CONFIG).getConnection(
															Constants.POOL_NAMES.APPLICATION.name());
				} catch (Exception e) {
					throw new DatabaseException(e);
				}
			else
				connection = dsFactory.getDataSource(Constants.POOL_NAMES.APPLICATION.name()).getConnection();
			return new ApplicationConnection(connection);
		} catch (SQLException sqe) {
			throw new DatabaseException(sqe);
		} catch (IOException ioe) {
			throw new DatabaseException(ioe);
		}
	}	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ConnectionManager.java                                                                              $
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/