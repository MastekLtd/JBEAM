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
package com.stgmastek.monitor.comm.test;

import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the BatchDAO method addBatch
 * 
 * @author mandar.vaidya
 *
 */
public class TestBatchDAOAddBatch extends TestCase{

	/** The Batch */
	MReqBatch batch = null;
	
	
	protected void setUp() throws Exception { 
		//Create new instance of MBatchLog 
		batch = new MReqBatch();
		
		//Set all the mandatory fields
		batch.setInstallationCode("RVOS-DEV");
		batch.setBatchNo(5);
		batch.setBatchRevNo(1);
		batch.setBatchName("NEW BATCH #5");
		batch.setBatchType("All Types");
		batch.setFailedOver("N");
		batch.setExecStartTime(new Date().getTime() + 200000);
		
	}

	/**
	 * Tests the update Batch method  
	 * All fields
	 * TODO: Set all the fields 
	 */
	public void testAddBatchAllFields(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			//All the mandatory fields are set in the overridden setUp() method
			//Set all the non-mandatory fields				
			batch.setBatchStartUser("ADMIN");
			
			Integer iUpdt = dao.addBatch(batch,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
			System.out.println("Exception = [" + e + "]");
			fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}
	

	/**
	 * Tests the update Batch method  
	 * Only mandatory fields
	 */
	public void testAddBatchMandatoryFields(){	
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batch.setBatchNo(6);
			batch.setBatchRevNo(11);
			Integer iUpdt = dao.addBatch(batch,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] IN testAddBatchMandatoryFields()");
			fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Tests the update Batch method  
	 * Missing mandatory fields
	 * Exception would be raised 
	 */
	public void testAddBatchMissingMandatoryFields(){
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batch.setBatchName(null); //Not Null
			batch.setExecEndTime(null); 
			Integer iUpdt = dao.addBatch(batch,connection);
			
			assertTrue(iUpdt.intValue() == 0);			
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] in testAddBatchMissingMandatoryFields()");
			if(!(e instanceof CommDatabaseException))
				fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/test/TestBatchDAOAddBatch.java                                                            $
 * 
 * 5     6/18/10 12:31p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 4     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 3     3/11/10 1:47p Mandar.vaidya
 * Tested with failedOver while insert in BATCH table.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/