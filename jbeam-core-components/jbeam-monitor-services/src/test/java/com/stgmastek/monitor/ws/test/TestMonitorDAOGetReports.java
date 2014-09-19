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
 * JUnit class to test the UserDAO method change password  
 * 
 * @author grahesh.shanbhag
 *
 */
public class TestMonitorDAOGetReports extends TestCase{

	/**
	 * Tests getReports method of IMonitorDAO
	 */
	public void testGetBatchCompletedData(){
		IExecuteReportDAO dao = DAOFactory.getExecuteReportDAO();
		Connection connection = null;
		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			List<Report> reports =  dao.getReports(null, connection);
			for (Report report : reports) {
				System.out.println("Report name = " + report.getReportName());
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
 * 6     6/23/10 11:16a Lakshmanp
 * added the dao object  from DAOFactory, connection object to dao method and handled connection leak.
 * 
 * 5     3/11/10 12:06p Mandar.vaidya
 * Tested with batchEndReason in search fields.
 * 
 * 4     3/05/10 5:20p Mandar.vaidya
 * Added batch_end_reason for test.
 * 
 * 3     1/06/10 5:05p Grahesh
 * Modified the implementation
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/