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
package com.stgmastek.core.comm.test;

import java.sql.Connection;

import junit.framework.TestCase;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.dao.IStatusDAO;
import com.stgmastek.core.comm.server.vo.CReqInstruction;
import com.stgmastek.core.comm.util.ConnectionManager;
import com.stgmastek.core.comm.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestStatusDAOInterruptBatch extends TestCase{
	CReqInstruction ins = null;
	
	
	protected void setUp() throws Exception {
		ins = new CReqInstruction();
		ins.setMessage("SSADDBMCCI");
		ins.setParam("batchNo=501,batchRevNo=5");
	}

	/**
	 * Tests the interrupt batch method  
	 * All fields
	 */
	public void testInterruptBatchAllFields(){		
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			Integer iIns = dao.interruptBatch(ins,connection);
			
			assertNotNull(iIns);			
			assertEquals(iIns.intValue(), 1);
			
		}catch(Exception e){
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
		
	}
	

	/**
	 * Tests the interrupt batch method  
	 * Only mandatory fields
	 */
	public void testInterruptBatchMandatoryFields(){	
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ins.setParam(null);
			Integer iIns = dao.interruptBatch(ins,connection);
			
			assertNotNull(iIns);			
			assertEquals(iIns.intValue(), 1);
			
		}catch(Exception e){
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Tests the interrupt batch method  
	 * Missing mandatory fields
	 * Exception would be raised 
	 */
	public void testInterruptBatchMissingMandatoryFields(){	
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			ins.setMessage(null);
			ins.setParam(null);
			Integer iIns = dao.interruptBatch(ins,connection);
			
			assertNull(iIns);			
			
		}catch(Exception e){
			if(!(e instanceof CommDatabaseException))
				fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/test/TestStatusDAOInterruptBatch.java                                                           $
 * 
 * 3     6/21/10 11:37a Lakshmanp
 * added the code to get dao from DAOFactory and passed the connection parameter to dao methods
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/