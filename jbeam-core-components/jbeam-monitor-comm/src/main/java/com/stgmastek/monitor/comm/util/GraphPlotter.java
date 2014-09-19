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
package com.stgmastek.monitor.comm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.util.ResultSetMapper;

/**
 * A collator class for collating data for the graph
 * 
 * @author grahesh.shanbhag
 * 
 */
public class GraphPlotter extends Collator{

	
	private Integer COLLATE_PERIOD = 0;

	private TimeZone timeZone;
	
	private static final Logger logger = Logger.getLogger(GraphPlotter.class);
	
	
	/**
	 * Public constructor that takes a dedicated connection
	 *  
	 * @param collatorKey
	 * 		  The collator key attributes
	 */
	public GraphPlotter(CollatorKey collatorKey) {
		super(collatorKey);
		setGraphId(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
	}
	
	/**
	 * @see BasePoller#init()
	 */
	
	protected void init() throws CommDatabaseException {
		/** Get the collator period */
		COLLATE_PERIOD = 
			Integer.valueOf(Configurations.getConfigurations().getConfigurations(
							collatorKey.getInstallationCode(), "COLLATOR", "WAIT_PERIOD"));
		timeZone = Constants.getTimeZone(collatorKey.getInstallationCode());
	}

	/**
	 * @see BasePoller#execute()
	 */	
	
	protected void execute() throws CommException{

		while (!CHECK_STOP) {
			poll();
			//Need to loop one additional time. 
			//Specially when the STOP_POLLING was set as true when the current thread
			//was asleep
			if(STOP_POLLING){
				CHECK_STOP = true;	
			}
			if (logger.isDebugEnabled()) {
				logger.debug(this.getClass().getName() + " # Will sleep for " + COLLATE_PERIOD);
			}
			//Sleep for the interval 
			try {
				TimeUnit.MILLISECONDS.sleep(COLLATE_PERIOD);
			} catch (InterruptedException e) {
			}					
		} //end while
		poll();
	}

	private void poll() throws CommException {
		ResultSet rs = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;
		Connection con = null;
		try {
			con = ConnectionManager.getInstance().getDefaultConnection();
			con.setAutoCommit(true);
			/** Query to get batch progress chart data */
			String query = "select count(1) graph_value from log where installation_code = ? and batch_no = ? and batch_rev_no = ?";
			pstmtSelect = con.prepareStatement(query);
			pstmtSelect.setString(1, collatorKey.getInstallationCode());
			pstmtSelect.setObject(2, collatorKey.getBatchNo());
			pstmtSelect.setObject(3, collatorKey.getBatchRevNo());
			//Get the collator data
			rs = pstmtSelect.executeQuery();				
			
			GraphData graphData = 
				ResultSetMapper.getInstance().mapSingleRecord(rs, GraphData.class);
			if(rs != null){
				rs.close();
				rs = null;					
			}
			graphData.setInstallationCode(collatorKey.getInstallationCode());
			graphData.setGraphId(getGraphId());
			graphData.setBatchNo(collatorKey.getBatchNo());
			graphData.setBatchRevNo(collatorKey.getBatchRevNo());
			graphData.setTime(Calendar.getInstance(timeZone).getTimeInMillis());
			
				//Submit the collated data 
			String insertQuery = "insert into graph_data_log (installation_code, graph_id, batch_no, batch_rev_no, collect_time, graph_value) values (?, ?, ?, ?, ?, ?)";
			pstmtInsert = con.prepareStatement(insertQuery);
			pstmtInsert.setObject(1, collatorKey.getInstallationCode());
			pstmtInsert.setObject(2, getGraphId());
			pstmtInsert.setObject(3, collatorKey.getBatchNo());
			pstmtInsert.setObject(4, collatorKey.getBatchRevNo());
			pstmtInsert.setObject(5, new Timestamp(graphData.getTime()));
			pstmtInsert.setObject(6, graphData.getGraphValue());
			pstmtInsert.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("GraphPlotter # " + collatorKey.getInstallationCode() + " # " +
						getGraphId() + " # " + collatorKey.getBatchNo() + " # " + collatorKey.getBatchRevNo() + " # "
						+ (new Timestamp(graphData.getTime())) + " # " + graphData.getGraphValue());
			}
			pstmtSelect.close();
			pstmtInsert.close();
			
			
		} catch (Exception sqe) {
			throw new CommException(sqe);
		} finally {
			if (pstmtSelect != null) {
				try {
					pstmtSelect.close();
				} catch (SQLException e) {
				}
			}
			if (pstmtInsert != null) {
				try {
					pstmtInsert.close();
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/GraphPlotter.java                                                                    $
 * 
 * 7     3/15/10 12:30p Mandar.vaidya
 * Added setAutoCommit(true) on connection in init method.
 * Modified the execute method for inserting data in to GRAPH_DATA_LOG.
 * 
 * 6     3/12/10 4:40p Mandar.vaidya
 * Changes made for not closing the jdbc connection as it is called from the collators.
 * 
 * 5     3/12/10 4:29p Kedarr
 * Added respective prepared statements as the base class statements were removed.
 * 
 * 4     3/12/10 10:50a Kedarr
 * changes made to adjust the refactoring of graph data
 * 
 * 3     3/11/10 4:24p Kedarr
 * Changed the graph data log table and its related changes.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/