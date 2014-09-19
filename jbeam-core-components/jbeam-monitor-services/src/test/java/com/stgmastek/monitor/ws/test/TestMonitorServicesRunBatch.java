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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ResBatchInfo;

/**
 * JUnit class to test the MonitorServices method run batch
 * 
 * @author grahesh.shanbhag
 * 
 */
public class TestMonitorServicesRunBatch extends TestCase {

	private ReqInstructionLog instructionLog = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception {
		instructionLog = new ReqInstructionLog();
		instructionLog.setInstallationCode("BILLING-DV");
		instructionLog.setInstructingUser("ADMIN");
		instructionLog.setInstructionTime(new Date().getTime());
		
		InstructionParameter parameter1 = new InstructionParameter();		
		parameter1.setSlNo(1);
		parameter1.setName("Batch Name");
		parameter1.setType("S");
		parameter1.setValue("TEST BATCH");
		
		InstructionParameter parameter2 = new InstructionParameter();		
		parameter2.setSlNo(1);
		parameter2.setName("Batch Date");
		parameter2.setType("S");
		parameter2.setValue(String.valueOf(new Date().getTime()));
		List<InstructionParameter> instructionParametersList = new ArrayList<InstructionParameter>();
		instructionParametersList.add(parameter1);
		instructionParametersList.add(parameter2);
		instructionLog.setEntityValues("POLICY=PL-1;CLAIM=CL-1;POLICY=PL-2;CLAIM=CL-2;POLICY=PL-3;POLICY=PL-4;CLAIM=CL-3");
		instructionLog.setInstructionParameters(instructionParametersList);
		// instructionLog.setBatchEndDate(new Long(280) * 60 * 1000);
	}

	public void testRunBatch() {
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
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAORunBatch.java               $
 * 
 */