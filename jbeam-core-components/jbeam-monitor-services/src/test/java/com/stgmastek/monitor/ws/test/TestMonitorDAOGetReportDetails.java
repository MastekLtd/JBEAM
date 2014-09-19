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

import com.stgmastek.monitor.ws.server.dao.IExecuteReportDAO;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.util.ConnectionManager;
import com.stgmastek.monitor.ws.util.DAOFactory;

/**
 * JUnit class to test the MonitorDAO method getReportDetails 
 * 
 *@author Mandar Vaidya
 */
public class TestMonitorDAOGetReportDetails extends TestCase{

	private Report report;
	
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception {
		report = new Report();
		report.setInstallationCode("BILLING-UI");
		report.setReportId("1");
	}



	/**
	 * Tests getReports method of IMonitorDAO
	 */
	public void testGetReportParametesForReportId(){
		IExecuteReportDAO dao = DAOFactory.getExecuteReportDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<Report> reports =  dao.getReportDetails(report, connection);
			for (Report reportDetails : reports) {
				System.out.print("Report id = " + reportDetails.getReportId());
				System.out.print("\tProgram name = " + reportDetails.getProgramName());
				System.out.println("\tReport name = " + reportDetails.getReportName());
				
			}
			assertNotNull(reports);
			
			
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/TestMonitorDAOGetBatchCompletedData.java                                           $
 * 
*
*
*/