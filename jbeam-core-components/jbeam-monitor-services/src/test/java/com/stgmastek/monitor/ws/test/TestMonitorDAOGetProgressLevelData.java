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

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.vo.ReqBatch;

/**
 * JUnit class to test the MonitorDAO method getProgressLevelData  
 * 
 * @author mandar.vaidya
 *
 */
public class TestMonitorDAOGetProgressLevelData extends TestCase{
	ReqBatch batch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setInstallationCode("ERIE-UAT");
		batch.setBatchNo(773);
		batch.setBatchRevNo(1);
	}

	/**
	 * Tests Get system information   
	 */
	public void testGetProgressLevelData(){		
		try{
			
//			MonitorDAO dao = (MonitorDAO)DAOFactory.getInstance().getDAO();
//			List<ProgressLevelData> progressLevelVO = dao.getProgressLevelData(batch, "Y");
//			for(ProgressLevelData levelVO: progressLevelVO){
//				System.out.println(levelVO.getPrgLevelType()  
//					 	+ " >> " +  levelVO.getPrgActivityType());	
//			}
//			
//			System.out.println("=================\nLast Record = \t");
//			for(int i = 0; i < progressLevelVO.size(); i++){				
//				ProgressLevelData levelVO = progressLevelVO.get(i);
//				if(i == progressLevelVO.size() -1 )
//					System.out.println(levelVO.getPrgLevelType()  
//					 	+ " >> " +  levelVO.getPrgActivityType());	
//			}
//			
//				
//			
//			//assertNotNull(info);
//			assertNotNull(progressLevelVO);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetProgressLevelData.java                                            $
 * 
 * 4     1/13/10 4:10p Grahesh
 * Initial Version
 * 
 * 3     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/