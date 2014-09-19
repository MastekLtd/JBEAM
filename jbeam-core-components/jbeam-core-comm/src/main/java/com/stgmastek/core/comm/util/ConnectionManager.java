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
package com.stgmastek.core.comm.util;

import java.sql.Connection;

import jdbc.pool.CConnectionPoolManager;

import com.stgmastek.core.comm.exception.CommDatabaseException;

/**
 * 
 * The connection manager class for the communication system.
 * 
 * The manager is responsible for all connection related activities
 * 
 * Implements the singleton pattern for a single instance per communication system.
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class ConnectionManager {

	/** The static instance of the manager */
	private static ConnectionManager manager = new ConnectionManager();

	/**
	 * The private constructor to avoid any instantiation 
	 */
	private ConnectionManager() {
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
	 * Helper method to return the connection pool manager
	 * 
	 * @return the instance of {@link CConnectionPoolManager} 
	 * @throws CommDatabaseException
	 * 		   exception thrown during instantiating the pool manager 
	 */
	private CConnectionPoolManager getJDBCPoolManager()
			throws CommDatabaseException {
		try {
			return CConnectionPoolManager.getInstance(
												CommConstants.DEFAULT_LOG4J_PROPERTIES,
												CommConstants.DEFAULT_CONNECTION_POOL_CONFIG);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}
	}

	/**
	 * Returns the communication schema connection 
	 * 
	 * @return the {@link BATCHConnection} instance
	 * @throws CommDatabaseException
	 * 		   exception thrown while fetching the connection from the pool
	 */
	public BATCHConnection getDefaultConnection() throws CommDatabaseException {

		Connection connection = null;
		try {
			connection = getJDBCPoolManager().getConnection(
												CommConstants.DEFAULT_CONNECTION_POOL);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}
		return new BATCHConnection(connection);

	}	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/ConnectionManager.java                                                                     $
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/