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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * The base class for all DAO class(es) in the communication system
 * 
 * @author grahesh.shanbhag
 *
 */
public abstract class BaseDAO {

	/** Constructor that takes the connection as the parameter */
	protected BaseDAO(){
	}
	
	/**
	 * Protected method to have a uniform way to release the resources
	 * This method is called in the <i>finally</i> block by the implementing sub classes 
	 * 
	 * @param rs
	 * 		  The <i>ResultSet</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 * @param pstmt
	 * 		  The <i>Statement</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 * @param con
	 * 		  The <i>CConnection</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 */
	public void releaseResources(ResultSet rs, PreparedStatement pstmt, Connection con){
		if(rs != null ){
			try {
					rs.close(); 
			} catch (SQLException e) {
			}
		}
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
			} 
		}
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/BaseDAO.java                                                                               $
 * 
 * 6     7/07/10 3:13p Kedarr
 * Changes made for connection leaks.
 * 
 * 5     6/21/10 11:30a Lakshmanp
 * removed parameter constructor and modified releaseresources as public
 * 
 * 4     12/30/09 12:20p Mandar.vaidya
 * Removed warnings
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/