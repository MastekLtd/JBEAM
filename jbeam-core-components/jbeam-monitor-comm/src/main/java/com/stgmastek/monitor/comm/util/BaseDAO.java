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
package com.stgmastek.monitor.comm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.stgmastek.monitor.comm.server.dao.IBaseDAO;


/**
 * 
 * The base class for all DAO class(es) in the communication system
 * 
 * @author grahesh.shanbhag
 *
 */
public abstract class BaseDAO implements IBaseDAO {

	/** The connection object needed for database activities */
	protected CConnection connection = null;
	
	/** Public constructor takes no argument*/
	protected BaseDAO(){
		super();
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
		try {
			if(rs != null ){
				rs.close(); rs = null;
			}
			
			if(pstmt != null){
				pstmt.close(); pstmt = null;
			}
			
			if(con != null){
				con.close();
			}
			
		} catch (SQLException e) {
		}
	}
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/BaseDAO.java                                                                         $
 * 
 * 5     6/18/10 12:37p Lakshmanp
 * removed parameterized constructor and changed cconnection object in releaseresources
 * 
 * 4     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 3     12/30/09 12:49p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/