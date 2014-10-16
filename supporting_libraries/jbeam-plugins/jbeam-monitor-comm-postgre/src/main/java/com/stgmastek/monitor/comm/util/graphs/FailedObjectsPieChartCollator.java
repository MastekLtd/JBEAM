/**
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
 * $Revision: 6 $
 *
 * $Header: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/graphs/FailedObjectsPieChartCollator.java 6 $
 *
 * $Log: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/graphs/FailedObjectsPieChartCollator.java $
 * 
 * 6     5/24/10 4:03p Mandar.vaidya
 * Modified select query(changed task_name to object_name) in execute
 * method.
 * 
 * 5     3/15/10 12:33p Mandar.vaidya
 * Modified the execute method for inserting data in to GRAPH_DATA_LOG.
 * 
 * 4     3/12/10 4:40p Mandar.vaidya
 * Changes made for not closing the jdbc connection as it is called from
 * the collators.
 * 
 * 3     3/12/10 4:32p Kedarr
 * Added respective prepared statements as the base class statements were
 * removed.
 * 
 * 2     3/12/10 1:50p Kedarr
 * Missed the group by clause.
 * 
 * 1     3/12/10 10:51a Kedarr
 * Added new failed object pie chart collator.
 *
 */
package com.stgmastek.monitor.comm.util.graphs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.monitor.comm.util.Collator;
import com.stgmastek.monitor.comm.util.CollatorKey;
import com.stgmastek.monitor.comm.util.Configurations;
import com.stgmastek.monitor.comm.util.ConnectionManager;
import com.stgmastek.monitor.comm.util.Constants;

/**
 * Failed object pie chart collator.
 *
 * Class is responsible to collate the data for failed objects pie chart requirement.
 *  
 * @author kedar460043
 * @since
 */
public class FailedObjectsPieChartCollator extends Collator {

	private Integer COLLATE_PERIOD;
	private static final Logger logger = Logger.getLogger(FailedObjectsPieChartCollator.class);
	
	private final String insertQuery = "insert into graph_data_log (select ?, ?, ?, ?, ?, object_name graph_x_axis, null, count(1) graph_value from log WHERE installation_code = ? AND batch_no = ? AND batch_rev_no = ? AND status = ? GROUP BY 1,2,3,4,object_name,6,7)";
	private final String deleteQuery = "DELETE FROM graph_data_log WHERE installation_code = ? and graph_id = ? and batch_no = ? and batch_rev_no = ?";

	/**
	 * Default constructor that accepts the {@link CollatorKey}.
	 * @param collatorKey
	 */
	public FailedObjectsPieChartCollator(CollatorKey collatorKey) {
		super(collatorKey);
		setGraphId(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".")+1));
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.comm.util.BasePoller#execute()
	 */
	
	protected void execute() throws CommException {
		while (!STOP_POLLING) {
			poll();
			if (logger.isDebugEnabled()) {
				logger.debug(this.getClass().getName() + " # Will sleep for " + COLLATE_PERIOD);
			}
			//Need to loop one additional time. 
			//Specially when the STOP_POLLING was set as true when the current thread
			//was asleep
			//Sleep for the interval
			try {
				Thread.sleep(COLLATE_PERIOD);					
			} catch (InterruptedException e) {
			}
		}
		poll(); //Just in case if the data had not reached when the poll was executed, re-execute it.
	}


	private void poll() throws CommException {
		Timestamp timestamp = new Timestamp(Calendar.getInstance(Constants.getTimeZone(collatorKey.getInstallationCode())).getTimeInMillis());
		Connection con = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtDelete = null;
		try {
			con = ConnectionManager.getInstance().getDefaultConnection();
			con.setAutoCommit(true);
			/** Query to get batch progress chart data */
			pstmtInsert = con.prepareStatement(insertQuery);
			pstmtInsert.setString(1, collatorKey.getInstallationCode());
			pstmtInsert.setObject(2, getGraphId());
			pstmtInsert.setObject(3, collatorKey.getBatchNo());
			pstmtInsert.setObject(4, collatorKey.getBatchRevNo());
			pstmtInsert.setObject(5, timestamp);
			pstmtInsert.setString(6, collatorKey.getInstallationCode());
			pstmtInsert.setObject(7, collatorKey.getBatchNo());
			pstmtInsert.setObject(8, collatorKey.getBatchRevNo());			
			pstmtInsert.setObject(9, "99");			
		/*	pstmtInsert.setString(10, collatorKey.getInstallationCode());
			pstmtInsert.setObject(11, getGraphId());
			pstmtInsert.setObject(12, collatorKey.getBatchNo());
			pstmtInsert.setObject(13, collatorKey.getBatchRevNo());
			pstmtInsert.setObject(14, timestamp);*/

			pstmtDelete = con.prepareStatement(deleteQuery);
			pstmtDelete.setObject(1, collatorKey.getInstallationCode());
			pstmtDelete.setObject(2, getGraphId());
			pstmtDelete.setObject(3, collatorKey.getBatchNo());
			pstmtDelete.setObject(4, collatorKey.getBatchRevNo());
			pstmtDelete.executeUpdate();
			pstmtInsert.executeUpdate();
		} catch (Exception sqe) {
			logger.error("Error in FailedObjectsPieChartCollator >> ",sqe);
			throw new CommException(sqe);
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
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.monitor.comm.util.BasePoller#init()
	 */
	
	protected void init() throws CommException {
		/** Get the collator period */
		COLLATE_PERIOD = 
			Integer.valueOf(Configurations.getConfigurations().getConfigurations(
							collatorKey.getInstallationCode(), "COLLATOR", "WAIT_PERIOD"));
	}

}
