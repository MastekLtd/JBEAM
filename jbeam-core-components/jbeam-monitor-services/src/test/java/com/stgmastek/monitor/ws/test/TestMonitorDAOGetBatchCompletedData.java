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
package com.stgmastek.monitor.ws.test;

import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.BatchDetails;
import com.stgmastek.monitor.ws.server.vo.ReqSearchBatch;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetBatchCompletedData extends TestCase{
	ReqSearchBatch searchBatch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		searchBatch = new ReqSearchBatch();
		searchBatch.setInstallationCode("BILLING-DV");
//		searchBatch.setBatchDate(1258345489406L);
//		searchBatch.setBatchDate("16-Nov-2009");
//		searchBatch.setBatchNo(1);
//		searchBatch.setBatchName("");
		searchBatch.setBatchEndReason("USER_INTERRUPTED");
	}

	/**
	 * Tests Get Batch Completed Data
	 */
	public void testGetBatchCompletedData(){
//		IMonitorDAO dao = DAOFactory.getMonitorDAO();
//		Connection connection = null;
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
//			List<BatchDetails> resBatchInfoList = dao.getBatchCompletedData(searchBatch,connection);
			ResBatchInfo batchInfo = impl.getBatchCompletedData(searchBatch);
			List<BatchDetails> batchDetails = batchInfo.getBatchDetailsList();
			
			for(BatchDetails batchDetails2 : batchDetails){
				
				System.out.println("Batch No #"+ batchDetails2.getBatchNo()
							+ "  >>> Batch start date = "+ batchDetails2.getExecStartTime()
							+ "  >>> Batch end reason = "+ batchDetails2.getBatchEndReason());
			}
//			
			//assertNotNull(info);
			assertNotNull(batchDetails);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
//			dao.releaseResources(null, null, connection);
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchCompletedData.java                                           $
 * 
 * 6     6/23/10 11:16a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao method and handled connection leak.
 * 
 * 5     3/11/10 12:06p Mandar.vaidya
 * Tested with batchEndReason in search fields.
 * 
 * 4     3/05/10 5:20p Mandar.vaidya
 * Added batch_end_reason for test.
 * 
 * 3     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/