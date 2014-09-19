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
import com.stgmastek.monitor.ws.server.vo.BatchObject;
import com.stgmastek.monitor.ws.server.vo.ReqBatchDetails;
import com.stgmastek.monitor.ws.server.vo.ResBatchObjectVO;

/**
 * JUnit class to test the MonitorDAO method GetBatchObjectDetails  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetBatchObjectDetails extends TestCase{
	ReqBatchDetails reqBatchDetails = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		reqBatchDetails = new ReqBatchDetails();
		reqBatchDetails.setInstallationCode("BILLING-DV");
		reqBatchDetails.setBatchNo(2061);
		reqBatchDetails.setBatchRevNo(1);
		reqBatchDetails.setPrePost("BROKER");
		reqBatchDetails.setCycleNo(1);
	}

	/**
	 * Tests Get system information   
	 */
	public void testGetBatchObjectDetails(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
//		Connection connection = null;
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
			ResBatchObjectVO resBatchObjectVO =  impl.getBatchObjectDetails(reqBatchDetails);
			List<BatchObject> list = resBatchObjectVO.getBatchObjectList();
			for(BatchObject batchObject : list) {
				System.out.println("getStatus = "  +  batchObject.getStatus());
				System.out.println("getTaskName = "  +  batchObject.getTaskName());
				System.out.println("TASK NAME = "  +  batchObject.getTimeTaken());
			}
			//assertNotNull(info);
			assertNotNull(list);
			
			
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
* $Log::  $
 * 
 * 2     6/2
 * added the
 * dao
 * object
 * from
 * DAOFactor
 * y,
 * connectio
 * n object
 * to dao
 * method
 * and
 * handled
 * connectio
 * n leak.
 * 
 * 1     3/1
 * Initial
 * Version
 * 
 * 
*
*
*/