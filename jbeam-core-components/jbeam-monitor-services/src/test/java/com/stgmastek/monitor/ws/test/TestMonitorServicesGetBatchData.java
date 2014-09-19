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
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ReqInstallationVO;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorServicesGetBatchData extends TestCase{
	ReqBatch batch = null;
	ReqInstallationVO reqInstallationVO = null;
	//private static final String GET_SYSTEM_INFO = "select * from BATCH_SYSTEM_INFO where bsi_batch_no = 2239 and bsi_batch_rev_no = 1";
	
	
	protected void setUp() throws Exception {
		batch = new ReqBatch();
		batch.setInstallationCode("BILLING-DV");
		batch.setBatchNo(1901);
		batch.setBatchRevNo(1);
//		batch.setInstructionSeqNo(1310);
		
		reqInstallationVO = new ReqInstallationVO();
		reqInstallationVO.setInstallationCode("BILLING-DV");
	}

	/**
	 * Tests Get system information   
	 */
	public void testGetBatchData(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try{
			ResBatchInfo resBatchInfo =  impl.getBatchData(batch);
			BatchDetails batchDetails = resBatchInfo.getBatchDetails();			
			System.out.println("Batch Name = " + batchDetails.getBatchName());
			List<InstructionParameter> instructionParameters = resBatchInfo.getInstructionParametersList();
			for (InstructionParameter instructionParameter : instructionParameters) {
				System.out.print("Instruction param  = " + instructionParameter.getName());
				System.out.println("\t\tInstruction param value  = " + instructionParameter.getValue());
			}
			assertNotNull(resBatchInfo);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	/**
	 * Tests Get system information   
	 */
	public void testGetInstructionParameters(){		
//		IMonitorDAO dao = DAOFactory.getMonitorDAO();
//		Connection connection = null;
//		List<InstructionParameter> instructionParameterList;
//		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
//			instructionParameterList = dao.getInstructionParameters(batch, connection);			
//			for (InstructionParameter instructionParameter2 : instructionParameterList) {
//				System.out.println(
//						" Sr. No. = " + instructionParameter2.getSlNo() +
//						" Entity = " + instructionParameter2.getName()	+
//						" | Value = " + instructionParameter2.getValue());
//				
//			}
//			assertNotNull(instructionParameterList);
//			
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			fail();
//		}finally {
//			dao.releaseResources(null, null, connection);
//		}
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchData.java                                                    $
 * 
 * 10    7/10/10 2:18p Mandar.vaidya
 * Added new method to getInstructionParameters. Tested with latest data.
 * 
 * 9     6/23/10 11:19a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao method and handled connection leak.
 * 
 * 8     4/01/10 4:29p Mandar.vaidya
 * Tested with latest data.
 * 
 * 7     3/17/10 5:39p Mandar.vaidya
 * 
 * 6     3/17/10 4:39p Mandar.vaidya
 * Tested with updated data.
 * 
 * 5     1/08/10 10:16a Grahesh
 * Corrected the object hierarchy
 * 
 * 4     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 3     1/06/10 10:49a Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/