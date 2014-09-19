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
package com.stgmastek.monitor.comm.util.graphs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import stg.utils.Day;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.monitor.comm.util.Collator;
import com.stgmastek.monitor.comm.util.CollatorKey;
import com.stgmastek.monitor.comm.util.Configurations;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.GraphData;
import com.stgmastek.util.ResultSetMapper;

/**
 * Per scan execution count collator graph utility program. 
 *
 * @author Kedar Raybagkar
 * @since
 */
public class PerScanExecutionCountCollator extends Collator {

	private Integer COLLATE_PERIOD = 0;
	
	private static final Logger logger = Logger.getLogger(PerScanExecutionCountCollator.class);
	
	private long lastScanTime;
	
	
	/**
	 * Public constructor that takes a dedicated connection
	 *  
	 * @param collatorKey
	 * 		  The collator key attributes
	 */
	public PerScanExecutionCountCollator(CollatorKey collatorKey) {
		super(collatorKey);
		setGraphId(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
	}
	
	protected void init() throws CommDatabaseException {
		/** Get the collator period */
		String str = Configurations.getConfigurations().getConfigurations(
				collatorKey.getInstallationCode(), "PERSCANCOLLATOR", "WAIT_PERIOD");
		if (str == null) {
			str = "1";
		}
		COLLATE_PERIOD = 
			Integer.valueOf(str);
		lastScanTime = new Day().getTimeInMillis();
	}

	
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
				logger.debug(this.getClass().getName() + " # Will sleep for " + COLLATE_PERIOD + " minutes.");
			}
			//Sleep for the interval 
			try {
				TimeUnit.MINUTES.sleep(COLLATE_PERIOD);
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
		long now = new Day().getTimeInMillis();
		try {
			con = ConnectionManager.getInstance().getDefaultConnection();
			con.setAutoCommit(true);
			/** Query to get batch progress chart data */
			String query = "select count(1) graph_value from log where installation_code = ? and batch_no = ? and batch_rev_no = ? and obj_exec_end_time is not null and last_updated_on between ? and ?";
			pstmtSelect = con.prepareStatement(query);
			pstmtSelect.setString(1, collatorKey.getInstallationCode());
			pstmtSelect.setObject(2, collatorKey.getBatchNo());
			pstmtSelect.setObject(3, collatorKey.getBatchRevNo());
			pstmtSelect.setObject(4, new Timestamp(lastScanTime));
			pstmtSelect.setObject(5, new Timestamp(now));
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
			graphData.setTime(now);
			
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
				logger.debug(getGraphId() +  " # " + collatorKey.getInstallationCode() + " # " +
						getGraphId() + " # " + collatorKey.getBatchNo() + " # " + collatorKey.getBatchRevNo() + " # "
						+ (new Timestamp(graphData.getTime())) + " # " + graphData.getGraphValue());
			}
			pstmtSelect.close();
			pstmtInsert.close();
			
		} catch (Exception sqe) {
			logger.error("Crashed!!", sqe);
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
			lastScanTime = now;
		}
		
	}

}
