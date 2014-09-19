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

import com.stgmastek.monitor.ws.server.services.MonitorServices;
import com.stgmastek.monitor.ws.server.services.MonitorServicesImpl;
import com.stgmastek.monitor.ws.server.vo.ExecuteReport;
import com.stgmastek.monitor.ws.server.vo.Report;
import com.stgmastek.monitor.ws.server.vo.ReportParameter;

/**
 * JUnit class to test the MonitorServices method retrieveReportParameters
 * 
 *@author Mandar Vaidya
 */
public class TestMonitorServicesRetrieveReportParameters extends TestCase{

	private Report report;
	private ExecuteReport retrieveParametersForReport;
	
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	
	protected void setUp() throws Exception {
		report = new Report();
		report.setReportId("1");
		report.setInstallationCode("BILLING-UI");
	}



	/**
	 * Tests getReports method of IMonitorDAO
	 */
	public void testGetReportParametesForReportId(){
		MonitorServices monitorServices = new MonitorServicesImpl(); 
//		IMonitorDAO dao = DAOFactory.getMonitorDAO();
//		Connection connection = null;
		try{
//			connection = ConnectionManager.getInstance().getDefaultConnection();
			retrieveParametersForReport = monitorServices.retrieveParametersForReport(report);
//			List<Report> reports =  dao.getReportDetails(report, connection);
			List<ReportParameter> reportParameters = retrieveParametersForReport.getReportParameters();
			for (ReportParameter reportParameter : reportParameters) {
				System.out.print("Label= " + reportParameter.getLabel());
				if(reportParameter.getQuery() != null)
					System.out.println("\tQuery = " + reportParameter.getQuery().substring(0, reportParameter.getQuery().length() - 1));
				
			}
			assertNotNull(retrieveParametersForReport);
			
			
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
*/