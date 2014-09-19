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

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ResSystemDetails;
import com.stgmastek.monitor.ws.server.vo.SystemDetails;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestStatusDAOGetSystemInfo extends TestCase{
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
		MonitorServicesImpl impl =  new MonitorServicesImpl();
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
			
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.100.192.250:1603:fami2_QC", "bpms_monitor", "bpms_monitor");
//			
//			PreparedStatement pstmt = null;
//			ResultSet rs = null;
//			try{
//				//GET_SYSTEM_INFO
//				pstmt = connection.prepareStatement("select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1");
////				pstmt.setObject(1, batch.getBatchNo());
////				pstmt.setObject(2, batch.getBatchRevNo());
//				rs = pstmt.executeQuery();
//				
//				while(rs.next()){
//					System.out.println(rs.getString(1));	
//				}
//				
//				ResBatchSystemInfo systemInfo = null; 
//					//ResultSetMapper.getInstance().mapSingleRecord(rs, ResBatchSystemInfo.class);
////				return systemInfo;
//			}catch(Exception e){
//				throw new DatabaseException(e);
//			}				
//			SystemDetails info = dao.getSystemInfo(batch, connection);
			ResSystemDetails resSystemDetails =impl.getSystemInfo(batch);
			SystemDetails systemDetails = resSystemDetails.getSystemDetails();
			System.out.println(systemDetails.getBatchNo());
			System.out.println(systemDetails.getBatchRevNo());
			System.out.println(systemDetails.getJavaVersion());
			System.out.println(systemDetails.getPreVersion());
			System.out.println(systemDetails.getOsConfig());
			System.out.println(systemDetails.getOutputDirPath());
			System.out.println(systemDetails.getOutputDirFreeMem());
			System.out.println(systemDetails.getMaxMemory());
			System.out.println(systemDetails.getUsedMemory());
//			BatchDetails batchInfo = dao.getBatchData(batch);
			
			assertNotNull(systemDetails);
//			assertNotNull(info);
//			assertNotNull(batchInfo);
			//assertNotNull(info.getBsiJavaVersion());
			//assertNull(info);
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestStatusDAOGetSystemInfo.java                                                    $
 * 
 * 6     6/23/10 11:26a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 5     3/30/10 10:54a Mandar.vaidya
 * JUnit test with recent data.
 * 
 * 4     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 3     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/