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
import com.stgmastek.monitor.ws.server.vo.ObjectExecutionGraphData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ResGraphVO;

/**
 * JUnit class to test the MonitorDAO method getGraphData  
 * 
 * @author mandar.vaidya
 *
 */
public class TestMonitorDAOGetGraphData extends TestCase{
	ReqBatch batch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setInstallationCode("BILLING-UI");
		batch.setBatchNo(350);
		batch.setBatchRevNo(1);
		batch.setGraphId("GraphPlotter");
	}

	/**
	 * Tests Get system information   
	 */
	public void testGetGraphData(){		
//		IMonitorDAO dao = DAOFactory.getMonitorDAO();
//		Connection connection = null;
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
//			List<ObjectExecutionGraphData> listObjectExecutionGraphData = dao.getGraphData(batch, connection);
			ResGraphVO graphVO = impl.getGraphData(batch);
			List<ObjectExecutionGraphData> listObjectExecutionGraphData = graphVO.getGraphDataList();
			BatchDetails batchDetails = graphVO.getBatchDetails();
			if(batchDetails != null) {
				System.out.println("Batch End Time is " + batchDetails.getExecEndTime());
				System.out.println("Batch End Reason is " + batchDetails.getBatchEndReason());
			}
			for(ObjectExecutionGraphData objectExecutionGraphData : listObjectExecutionGraphData) {
				 System.out.println(objectExecutionGraphData.getCollectTime()  
						 	+ " >> " +  objectExecutionGraphData.getGraphValue());
				
			}
			//assertNotNull(info);
			assertNotNull(listObjectExecutionGraphData);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetGraphData.java                                                    $
 * 
 * 5     6/23/10 11:22a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 4     3/12/10 11:49a Mandar.vaidya
 * Tested for new fields added for getGraphData
 * 
 * 3     1/06/10 11:48a Grahesh
 * Corrected the signature and object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/