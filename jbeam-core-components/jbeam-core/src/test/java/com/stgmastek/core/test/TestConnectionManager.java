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

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;

import com.stgmastek.core.common.ApplicationConnection;
import com.stgmastek.core.common.BatchConnection;
import com.stgmastek.core.util.ConnectionManager;

/**
 * JUnit test class for testing the connection manager for fetching active connections 
 * from both the schema and the APPLICATION schema
 *  
 * @author grahesh.shanbhag
 */
public class TestConnectionManager extends TestCase{

	/** The Connection reference */
	BatchConnection bConnection = null;
	
	/** The Application Connection reference */
	ApplicationConnection aConnection = null;
	
	/** The test SQL to test the connections with */
	static final String TEST_SQL = "select '1' from dual";
	
	/**
	 * Over ridden setUp method from the JUnit framework
	 */
	
	protected void setUp() throws Exception {
		bConnection = ConnectionManager.getInstance().getBATCHConnection();
		aConnection = ConnectionManager.getInstance().getApplicationConnection();
		
	}

	/**
	 * Test method to test the fetching and the closing of the connections 
	 * 
	 * @throws Exception
	 * 		   Any exception thrown while fetching or closing the connections
	 */
	public void testConnection() throws Exception{
		
		assertNotNull(bConnection);
		assertNotNull(aConnection);
		
		assertFalse(bConnection.isClosed());
		assertFalse(aConnection.isClosed());
	
		PreparedStatement stmt = bConnection.prepareStatement(TEST_SQL);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
		rs.close();rs = null;
		stmt.close();stmt = null;
		bConnection.close();
		
		stmt = aConnection.prepareStatement(TEST_SQL);
		rs = stmt.executeQuery();
		while (rs.next()) {
			assertEquals("1", rs.getString(1));
		}
		rs.close();rs = null;
		stmt.close();stmt = null;
		aConnection.close();		
		
		assertTrue(bConnection.isClosed());
		assertTrue(aConnection.isClosed());
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