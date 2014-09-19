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
package com.stgmastek.core.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jdbc.pool.CConnectionPoolManager;
import junit.framework.TestCase;
import stg.utils.Day;

/**
 * JUnit test class for testing the connection manager for fetching active connections 
 * from both the schema and the APPLICATION schema
 *  
 * @author grahesh.shanbhag
 */
public class ReportProgressLevelTest extends TestCase{

//	/** The Connection reference */
//	BatchConnection bConnection = null;
//	
//	/** The Application Connection reference */
//	ApplicationConnection aConnection = null;
	
	private CConnectionPoolManager manager;
	
	/** The test SQL to test the connections with */
	static final String TEST_SQL = "select '1' from dual";
	
	/**
	 * Over ridden setUp method from the JUnit framework
	 */
	
	protected void setUp() throws Exception {
//		bConnection = ConnectionManager.getInstance().getBATCHConnection();
//		aConnection = ConnectionManager.getInstance().getApplicationConnection();
		manager = CConnectionPoolManager.getInstance(null, new File("poolconfig.xml"));
	}

	/**
	 * Test method to test the fetching and the closing of the connections 
	 * 
	 * @throws Exception
	 * 		   Any exception thrown while fetching or closing the connections
	 */
	public void testConnection() throws Exception{
		
//		assertNotNull(bConnection);
//		assertNotNull(aConnection);
//		
//		assertFalse(bConnection.isClosed());
//		assertFalse(aConnection.isClosed());
		Connection con = null;
		try {
			con = manager.getConnection("BATCH");
			PreparedStatement stmt = con.prepareStatement(
				" select prg_level_type, prg_activity_type, cycle_no, status, end_datetime, start_datetime from progress_level order by indicator_no"
			);
			ResultSet rs = stmt.executeQuery();
			StringBuilder sb = new StringBuilder();
			while (rs.next()) {
				sb.delete(0, sb.length());
				sb.append(rs.getString(1));
				sb.append(",");
				sb.append(rs.getString(2));
				sb.append(",");
				sb.append(rs.getString(3));
				sb.append(",");
				sb.append(rs.getString(4));
				sb.append(",");
				sb.append(Day.verboseTimeDifference(rs.getTimestamp(5), rs.getTimestamp(6)));
				System.out.println(sb.toString());
			}
			rs.close();rs = null;
			stmt.close();stmt = null;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
}	

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/test/TestConnectionManager.java                                                                          $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/