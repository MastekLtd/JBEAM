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
package com.stgmastek.monitor.comm.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import junit.framework.TestCase;

import com.stgmastek.monitor.comm.server.dao.IStatusDAO;
import com.stgmastek.monitor.comm.server.vo.BatchProgress;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.DAOFactory;

/**
 * JUnit class to test the StatusDAO method updateBatchProgress 
 * 
 * @author mandar.vaidya
 *
 */
public class TestStatusDAOUpdateBatchProgress extends TestCase{

	/** The Batch progress */
	BatchProgress batchProgress = null;
	
	
	protected void setUp() throws Exception {
		//Create new instance of MBatchLog 
		batchProgress = new BatchProgress();		
		batchProgress.setIndicatorNo(3451);		
		
	}

	/**
	 * Tests the update batch progress method  
	 * All fields
	 * TODO: Set all the fields 
	 */
	public void testUpdateBatchProgressAllFields(){	
		IStatusDAO dao = DAOFactory.getStatusDAO();
		Connection connection = null;

		try{
			connection = ConnectionManager.getInstance().getDefaultConnection();
			batchProgress.setBatchNo(5);
			batchProgress.setBatchRevNo(5);
			batchProgress.setEndDatetime(new Date().getTime() + 33440000);
			
			Integer iUpdt = dao.updateBatchProgress(batchProgress,connection);
			
			assertNotNull(iUpdt);			
			assertEquals(iUpdt.intValue(), 1);
			
		}catch(Exception e){
			System.out.println("Exception = [" + e + "]");
			fail();
		} finally {
			dao.releaseResources(null, null, connection);
		}
	}
	
	public void testFiailedObjectGraphData() {
		System.out.println(this.getClass().getName() + " # Executing queries...");
		Connection con = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtDelete = null;
		try {
			con = ConnectionManager.getInstance().getDefaultConnection();
			con.setAutoCommit(true);
			/** Query to get batch progress chart data */
			String query = "insert into graph_data_log (select ?, ?, ?, ?, systimestamp, object_name graph_x_axis, null, count(1) graph_value from log WHERE installation_code = ? AND batch_no = ? AND batch_rev_no = ? AND status = ? GROUP BY ?, ?, ?, ?, systimestamp, object_name, null)";
			
			pstmtInsert = con.prepareStatement(query);
			pstmtInsert.setString(1, "BILLING-UI");
			pstmtInsert.setObject(2, "FailedObjectsPieChartCollator");
			pstmtInsert.setObject(3, 329);
			pstmtInsert.setObject(4, 1);
			pstmtInsert.setString(5, "BILLING-UI");
			pstmtInsert.setObject(6, 329);
			pstmtInsert.setObject(7, 1);
			pstmtInsert.setObject(8, "99");
			pstmtInsert.setString(9, "BILLING-UI");
			pstmtInsert.setObject(10, "FailedObjectsPieChartCollator");
			pstmtInsert.setObject(11, 329);
			pstmtInsert.setObject(12, 1);
			
			String deleteQuery = "DELETE FROM graph_data_log WHERE installation_code = ? and graph_id = ? and batch_no = ? and batch_rev_no = ?";

			pstmtDelete = con.prepareStatement(deleteQuery);
			pstmtDelete.setObject(1, "BILLING-UI");
			pstmtDelete.setObject(2, "FailedObjectsPieChartCollator");
			pstmtDelete.setObject(3, 329);
			pstmtDelete.setObject(4, 1);
			System.out.println("Deleted # "  + pstmtDelete.executeUpdate());
			System.out.println("Inserted # " + pstmtInsert.executeUpdate());
			
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {
			if (pstmtInsert != null) {
				try {
					pstmtInsert.close();
				} catch (SQLException e) {
				}
			}
			
			if (pstmtDelete != null) {
				try {
					pstmtDelete.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
//			super.freeResources();
		}
		
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/test/TestStatusDAOUpdateBatchProgress.java                                                $
 * 
 * 4     6/18/10 12:35p Lakshmanp
 * added connection parameter for dao methods and modified getting dao object from daofactory
 * 
 * 3     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/