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
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the BatchDAO method updateBatch
 * 
 * @author mandar.vaidya
 *
 */
public class TestBatchDAOUpdateBatch extends TestCase{

	/** The Batch */
	MReqBatch batch = null;
	
	
	protected void setUp() throws Exception {
		//Create new instance of MBatchLog that exists in the table  BATCH 
		batch = new MReqBatch();		
		batch.setInstallationCode("NY-DEMO");
		batch.setBatchNo(123);
		batch.setBatchRevNo(1);
	}

	/**
	 * Tests the update batch method  
	 * All fields
	 */
	public void testUpdateBatchAllFields(){		
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;

		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batch.setExecEndTime(1274709416795L);
			batch.setBatchEndUser("ADMIN");
			batch.setBatchEndReason("BATCH_COMPLETED");
			batch.setFailedOver("N");
			Integer iUpdt = dao.updateBatch(batch,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/test/TestBatchDAOUpdateBatch.java                                                         $
 * 
 * 6     6/18/10 12:33p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 5     6/17/10 10:51a Mandar.vaidya
 * Organized imports
 * 
 * 4     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 3     5/25/10 4:43p Mandar.vaidya
 * Tested with latest data
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/