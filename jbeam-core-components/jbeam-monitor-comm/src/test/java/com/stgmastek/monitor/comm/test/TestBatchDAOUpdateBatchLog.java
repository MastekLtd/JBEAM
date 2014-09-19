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
import com.stgmastek.monitor.comm.server.vo.BatchLog;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the BatchDAO method updateBatchLog 
 * 
 * @author mandar.vaidya
 *
 */
public class TestBatchDAOUpdateBatchLog extends TestCase{

	/** The Batch log */
	BatchLog batchLog = null;
	
	
	protected void setUp() throws Exception {
		//Create new instance of MBatchLog 
		batchLog = new BatchLog();
		
		//Set all the mandatory fields
		batchLog.setInstallationCode("BILLING-UI");
		batchLog.setBatchNo(340);
		batchLog.setBatchRevNo(1);
		batchLog.setTaskName("T"); 
		batchLog.setObjExecStartTime(new Date().getTime());		
		batchLog.setSysActNo("T"); 
		batchLog.setUserPriority("22");
		batchLog.setPriorityCode1(1);
		batchLog.setPriorityCode2(1);
		batchLog.setJobType("T");		
		batchLog.setDateGenerate(new Date().getTime());
		batchLog.setGenerateBy("T");		
		batchLog.setJobDesc("T");
		batchLog.setObjectName("T");
		batchLog.setListInd(2);
	}

	/**
	 * Tests the update batch log method  
	 * All fields
	 * TODO: Set all the fields 
	 */
	public void testUpdateBatchLogAllFields(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
			
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			//All the mandatory fields are set in the overridden setUp() method
			//Set all the non-mandatory fields	
			batchLog.setObjExecEndTime(new Date().getTime()); 		
			batchLog.setLine("T");
			batchLog.setSubline("T");
			batchLog.setBroker("T"); 
			batchLog.setPolicyNo("T"); 
			batchLog.setPolicyRenewNo("TB");
			batchLog.setVehRefNo("T"); 
			batchLog.setCashBatchNo("T"); 
			batchLog.setCashBatchRevNo("T"); 
			batchLog.setGbiBillNo("T"); 
			batchLog.setPrintFormNo("T"); 
			batchLog.setNotifyErrorTo("T");
			batchLog.setRecMessage("T");
			batchLog.setDateExecuted(new Date().getTime());
			batchLog.setListInd(1);
			batchLog.setEntityType("T");
			batchLog.setEntityCode("T");
			batchLog.setRefSystemActivityNo("T");
			batchLog.setErrorType("T"); 
			batchLog.setErrorDescription("T");
			batchLog.setSeqNo(2222);
			
			Integer iUpdt = dao.updateBatchLog(batchLog,connection);
			
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
	 * Tests the update batch log method  
	 * Only mandatory fields
	 */
	public void testUpdateBatchLogMandatoryFields(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			Integer iUpdt = dao.updateBatchLog(batchLog,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] IN testUpdateBatchLogMandatoryFields()");
			fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}

	/**
	 * Tests the update batch log method  
	 * Missing mandatory fields
	 * Exception would be raised 
	 */
	public void testUpdateBatchLogMissingMandatoryFields(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			batchLog.setTaskName(null); //Not Null
			batchLog.setObjExecEndTime(null); 
			Integer iUpdt = dao.updateBatchLog(batchLog,connection);
			
			assertNull(iUpdt);			
			
		}catch(Exception e){
			System.out.println("Exception [" + e + "] in testUpdateBatchLogMissingMandatoryFields()");
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/test/TestBatchDAOUpdateBatchLog.java                                                      $
 * 
 * 4     6/18/10 12:34p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 3     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/