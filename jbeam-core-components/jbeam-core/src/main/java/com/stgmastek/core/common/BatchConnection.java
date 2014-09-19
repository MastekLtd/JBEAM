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
package com.stgmastek.core.common;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is a wrapper around the connection to the BATCH schema.
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class BatchConnection extends CConnection {

	/**
	 * Public constructor 
	 * 
	 * @param connection
	 * 		  The connection passed in by the PRE engine 
	 */
	public BatchConnection(Connection connection){
		super(connection);
	}

	/**
	 * The mechanism to release the connection. 
	 * The method explicitly closes the connection, (and hence returns it back to the pool) 
	 * if the connection is open. <p/>
	 * 
	 * Note: Normally the connection received by PRE is not closed in legacy batch classes. 
	 * With the advent of new version of PRE, it is imperative that all connections 
	 * are closed. Also connections from the PRE framework is not used and new connection pools 
	 * are created as needed. In this case there are three pools, one for PRE, one for BATCH, 
	 * and one for APPLICATION. The connection provided by PRE is from the PRE pool and SHOULD NOT BE USED. 
	 * The connections received from the BATCH and the APPLICATION schema should be closed once used as needed.
	 *    
	 * @throws SQLException
	 * 		   The SQLException that may be thrown for activities related to closing
	 * 	       the connection
	 */
	
	public void releaseConnection() throws SQLException{
		if(!connection.isClosed())
			connection.close();
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/common/BatchConnection.java                                                                              $
 * 
 * 3     12/17/09 12:26p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/