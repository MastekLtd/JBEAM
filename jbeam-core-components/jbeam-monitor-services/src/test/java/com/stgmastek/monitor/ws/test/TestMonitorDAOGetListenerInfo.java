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
import com.stgmastek.monitor.ws.server.vo.ListenerInfo;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ResListenerVO;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetListenerInfo extends TestCase{
	ReqBatch batch = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setInstallationCode("BILLING-DV");
		batch.setBatchNo(1861);
		batch.setBatchRevNo(1);
	}

	/**
	 * Tests Get system information   
	 */
	public void testGetSystemInfo(){
//		IMonitorDAO dao = DAOFactory.getMonitorDAO();
//		Connection connection = null;
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
//			List<ListenerInfo> listListenerInfo = dao.getListenerInfo(batch, connection);
			ResListenerVO listenerVO = impl.getListenerInfo(batch);
			List<ListenerInfo> listListenerInfo = listenerVO.getListenerData();
			for(ListenerInfo listenerInfo : listListenerInfo){
				System.out.print(" Object executed  = " + listenerInfo.getNoOfObjectsExecuted() + "\t | \t ");
				System.out.println(" Time Taken = " + listenerInfo.getTimeTaken());
			}
			//assertNotNull(info);
			assertNotNull(listListenerInfo);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetListenerInfo.java                                                 $
 * 
 * 4     6/23/10 11:24a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 3     3/30/10 10:54a Mandar.vaidya
 * JUnit test with recent data.
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/