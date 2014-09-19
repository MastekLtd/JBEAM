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

import java.sql.Connection;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.dao.IMonitorDAO;
import com.stgmastek.monitor.ws.server.vo.BatchInfo;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetBatchInfo extends TestCase{
	ReqBatch batch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setBatchNo(314);
		batch.setBatchRevNo(2);
		batch.setInstallationCode("BILLING-UI");
	}

	/**
	 * Tests Get Batch information   
	 */
	public void testGetBatchInfo(){
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<BatchInfo> listBatchInfo = dao.getBatchInfo(batch,connection);
			for(BatchInfo batchInfo:listBatchInfo) {
				System.out.println(" BatchEndReason = "+ batchInfo.getBatchEndReason());
				System.out.println(" BatchEndReason = "+ batchInfo.getBatchEndReason());				
				System.out.println(" Time Dif = "+ batchInfo.getBatchTimeDiff());
				for(ProgressLevelData progressLevelData: batchInfo.getProgressLevelDataList()) {
					System.out.println(progressLevelData.getFailedOver());
					
				}
//				for(InstallationEntity installationEntity: batchInfo.getEntityList()) {
//					System.out.println(installationEntity.getEntity() + " - " + installationEntity.getPrecedenceOrder() );
//					
//				}
			}
			assertNotNull(listBatchInfo);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}
	
	public void testGetSysDate(){		
		IMonitorDAO dao = DAOFactory.getMonitorDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			String sysDate = dao.getSystemDate(connection);
			System.out.println(sysDate);
			assertNotNull(sysDate);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			dao.releaseResources(null, null, connection);
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchInfo.java                                                    $
 * 
 * 10    6/23/10 11:20a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao method and handled connection leak.
 * 
 * 9     4/28/10 2:26p Mandar.vaidya
 * Tested with latest data
 * 
 * 8     4/27/10 3:17p Mandar.vaidya
 * Tested with latest data
 * 
 * 7     4/20/10 2:10p Mandar.vaidya
 * Tested with latest data
 * 
 * 6     3/30/10 10:23a Mandar.vaidya
 * Tested recent batch data
 * 
 * 5     3/29/10 11:32a Mandar.vaidya
 * Tested recent batch data
 * 
 * 4     3/25/10 1:50p Mandar.vaidya
 * Added testGetSysDate method.
 * 
 * 3     1/08/10 10:16a Grahesh
 * Corrected the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/