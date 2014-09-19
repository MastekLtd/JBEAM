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

import junit.framework.TestCase;

import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ResInstallationVO;

/**
 * JUnit class to test the UserDAO method change password
 * 
 * @author grahesh.shanbhag
 * 
 */
public class TestMonitorServicesStopBatch extends TestCase {

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
		instructionLog.setBatchNo(1);
		instructionLog.setBatchRevNo(1);
	}

	public void testRunBatch() {
		MonitorServicesImpl impl = new MonitorServicesImpl();
		try {
			ResInstallationVO installationVO = impl.stopBatch(instructionLog);

			assertNotNull(installationVO);

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
 * 3     6/23/10 11:25a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao methods.
 * 
 * 2     4/01/10 4:29p Mandar.vaidya
 * Tested with latest data.
 * 
 * 1 1/08/10 10:17a Grahesh Initial Version
 * 
 * 2 12/17/09 12:02p Grahesh Initial Version
 */