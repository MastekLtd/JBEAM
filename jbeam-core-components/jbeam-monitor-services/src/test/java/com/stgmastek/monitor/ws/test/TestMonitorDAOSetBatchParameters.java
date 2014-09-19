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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;

/**
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOSetBatchParameters extends TestCase{
	
	private ReqInstructionLog instructionLog = null;
	private InstructionParameter instructionParameter = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception {
		instructionParameter = new InstructionParameter();
		instructionParameter.setName("BATCH_NAME");
		instructionParameter.setSlNo(1);
		instructionParameter.setType("S");
		instructionParameter.setValue("Test 1");
		
		
		
		instructionLog = new ReqInstructionLog();
		instructionLog.setInstallationCode("RVOS-DEV");
		instructionLog.setInstructingUser("ADMIN");
		instructionLog.setInstructionTime(new Date().getTime());
		List<InstructionParameter> instructionParameters = new LinkedList<InstructionParameter>();;
		instructionParameters.add(instructionParameter);
		instructionLog.setInstructionParameters(instructionParameters);
		// instructionLog.setBatchName("TEST BATCH");
		// instructionLog.setBatchType("DATE");
		// instructionLog.setBatchDate(new Date().getTime());
		// instructionLog.setInstructionParameters(
		// "POLICY=PL-1;CLAIM=CL-1;POLICY=PL-2;CLAIM=CL-2;POLICY=PL-3;POLICY=PL-4;CLAIM=CL-3"
		// );
		// instructionLog.setBatchEndDate(new Long(280) * 60 * 1000);
	}

	public void testSetBatchParams(){		
		try{
//			ReqBatchParams reqBatchParams = new ReqBatchParams();
//			reqBatchParams.setInstallationCode("GRAHESH");
//			reqBatchParams.setBatchName("SAMPLE TEST BATCH ");
//			reqBatchParams.setBatchStartTime(new Date().getTime());
//			reqBatchParams.setBatchParameters("POLICY_1=PL-1;CLAIM_1=CL-1");
//			reqBatchParams.setCreatedBy("ADMIN");
//			reqBatchParams.setCreatedOn(new Date().getTime());
//			
//			MonitorDAO dao = (MonitorDAO)DAOFactory.getInstance().getDAO();
//			int iInsertedRowCount = dao.setBatchParameters(reqBatchParams);
//			
//			assertTrue(iInsertedRowCount > 0);
			
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	public void testRunBatch(){		
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try {
			ResBatchInfo batchDetails = impl.runBatch(instructionLog);
			assertNotNull(batchDetails);

		} catch (Exception e) {
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOSetBatchParameters.java                                              $
 * 
 * 3     6/23/10 11:25a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 2     4/01/10 4:29p Mandar.vaidya
 * Tested with latest data.
 * 
 * 1     1/08/10 10:17a Grahesh
 * Initial Version
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/