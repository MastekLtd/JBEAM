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
import com.stgmastek.monitor.ws.server.vo.BatchInfo;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ResBatchDataVO;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorServicesGetBatchInfo extends TestCase{
	ReqBatch batch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setBatchNo(1901);
		batch.setBatchRevNo(1);
		batch.setInstallationCode("BILLING-DV");
	}

	/**
	 * Tests Get Batch information   
	 */
	public void testGetBatchInfo(){
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResBatchDataVO resBatchDataVO =  impl.getBatchInfo(batch);
			List<BatchInfo> listBatchInfo = resBatchDataVO.getBatchDataList(); 
			for(BatchInfo batchInfo:listBatchInfo) {
				System.out.println(batchInfo.getBatchTimeDiff());
				for(ProgressLevelData progressLevelData: batchInfo.getProgressLevelDataList()) {
					System.out.println(progressLevelData.getFailedOver());
					
				}
				for(InstallationEntity installationEntity: batchInfo.getEntityList()) {
					System.out.println(installationEntity.getEntity() + " - " + installationEntity.getPrecedenceOrder() );
					
				}
			}
			assertNotNull(listBatchInfo);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
		}
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchInfo.java                                                    $
 * 
*
*
*/