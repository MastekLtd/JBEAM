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
import com.stgmastek.monitor.comm.server.dao.IStatusDAO;
import com.stgmastek.monitor.comm.server.vo.BatchProgress;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the StatusDAO method addBatchProgress 
 * 
 * @author mandar.vaidya
 *
 */
public class TestStatusDAOAddBatchProgress extends TestCase{

	/** The Batch progress */
	BatchProgress batchProgress = null;
	
	
	protected void setUp() throws Exception {
		
		//Create new instance of MBatchLog 
		batchProgress = new BatchProgress();
		
		//Set all the mandatory fields		
		batchProgress.setIndicatorNo(3451);
		batchProgress.setPrgActivityType("Activity Type");
		batchProgress.setStatus("CO");
		batchProgress.setStartDatetime(new Date().getTime());
		
		
	}

	/**
	 * Tests the update batch progress method  
	 * All fields
	 * TODO: Set all the fields 
	 */
	public void testAddBatchProgressAllFields(){		
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			//All the mandatory fields are set in the overridden setUp() method
			//Set all the non-mandatory fields	
			batchProgress.setInstallationCode("RVOS-DEV");
			batchProgress.setBatchNo(51);
			batchProgress.setBatchRevNo(2);
			batchProgress.setPrgLevelType("Level Type");
			batchProgress.setCycleNo(10);
			batchProgress.setFailedOver("N");
			
			Integer iUpdt = dao.addBatchProgress(batchProgress,connection);
			
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
	 * Tests the update batch progress method  
	 * Only mandatory fields
	 */
	public void testAddBatchProgressMandatoryFields(){	
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchProgress.setBatchNo(6);
			batchProgress.setBatchRevNo(11);
			batchProgress.setIndicatorNo(5454);
			
			Integer iUpdt = dao.addBatchProgress(batchProgress,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] IN testAddBatchProgressMandatoryFields()");
			e.printStackTrace();
			fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Tests the update batch progress method  
	 * Missing mandatory fields
	 * Exception would be raised 
	 */
	public void testAddBatchProgressMissingMandatoryFields(){	
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;

		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			//Set one mandatory field to null
			batchProgress.setPrgActivityType(null);
			
			//Set one non-mandatory field to null
			batchProgress.setEndDatetime(null);
			
			Integer iUpdt = dao.addBatchProgress(batchProgress,connection);
			
			assertNull(iUpdt);			
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] IN testAddBatchProgressMissingMandatoryFields()");
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/test/TestStatusDAOAddBatchProgress.java                                                   $
 * 
 * 5     6/18/10 12:34p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 4     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 3     3/11/10 1:56p Mandar.vaidya
 * Tested with failedOver while insert in PROGRESS_LEVEL table.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/