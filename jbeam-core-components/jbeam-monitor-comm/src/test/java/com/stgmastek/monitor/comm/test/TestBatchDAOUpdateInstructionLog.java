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

import junit.framework.TestCase;

import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.vo.MReqInstructionLog;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the BatchDAO method updateBatchLog 
 * 
 * @author mandar.vaidya
 *
 */
public class TestBatchDAOUpdateInstructionLog extends TestCase{

	/**
	 * Tests the update instruction log method  
	 * All fields
	 */
	public void testUpdateInstructionLog(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		MReqInstructionLog instructionLog = null;
			
		try{
			instructionLog = new MReqInstructionLog();
			
			//Set all the mandatory fields
			instructionLog.setInstallationCode("BILLING-UI");
			instructionLog.setSeqNo(541);
			instructionLog.setBatchAction("BATCH STARTED"); 
			connection = ConnectionManager.getInstance().getDefaultConnection();
			
			Integer iUpdt = dao.updateInstructionLog(instructionLog,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
			System.out.println("For ["+ instructionLog.getSeqNo()+"] -->" + instructionLog.getBatchAction() );
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception = [" + e + "]");
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