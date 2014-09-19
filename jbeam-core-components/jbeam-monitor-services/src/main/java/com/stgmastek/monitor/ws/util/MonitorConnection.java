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
package com.stgmastek.monitor.ws.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is a wrapper around the schema connection provided by the 
 * PROCESS REQUEST ENGINE (PRE)
 * 
 * @author grahesh.shanbhag
 * 
 */
public class MonitorConnection extends CConnection {

	/**
	 * Public constructor 
	 * 
	 * @param connection
	 * 		  The connection passed in by the PRE engine 
	 */
	public MonitorConnection(Connection connection){
		super(connection);
	}

	/**
	 * A void implementation of the abstract method from the super class. 
	 * The connection is retrieved from the PRE, the PRE would do the releasing
	 * In development mode, since the core system uses a separate JDBCPool, 
	 * it is imperative that the connection is closed. 
	 * In production mode, PRE would do the closing of the MonitorConnection objects. 
	 */
	
	public void releaseConnection(){
		try {
			if(connection != null)
				connection.close();
		} catch (SQLException e) {
		}
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/util/MonitorConnection.java                                                             $
 * 
 * 5     12/30/09 1:10p Grahesh
 * Correcting the creation of the callable statement string
 * 
 * 4     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/