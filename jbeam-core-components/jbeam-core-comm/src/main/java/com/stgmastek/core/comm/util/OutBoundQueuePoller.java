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
package com.stgmastek.core.comm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.exception.CommException;

/**
 * A poller class for polling into the o_queue table
 * 
 * @author grahesh.shanbhag
 * 
 */
public class OutBoundQueuePoller extends BasePoller {

	/** 
	 * This handle to the preparedStatement for deleting a queued message 
	 */
	protected PreparedStatement pstmtDelete = null;

	private static final String SELECT_QUERY = "select id, message, param from o_queue order by id";
	
	private static final String DELETE_QUERY = "delete from o_queue where id = ? ";
	
	private static final Logger logger = Logger.getLogger(OutBoundQueuePoller.class);
		
	/**
	 * Public constructor that takes a dedicated connection
	 */
	public OutBoundQueuePoller() {
	}
	

	/**
	 * @see BasePoller#init()
	 */
	
	protected void init() throws CommDatabaseException {
	}

	/**
	 * @see BasePoller#execute()
	 */	
	
	protected int execute() throws CommException {
	
		Connection con = null;
		int messages = -1;
		try {
			con = ConnectionManager.getInstance().getDefaultConnection();
			if (!super.getExecutor().isShutdown())
				messages = doPoll(con);
		} catch (SQLException e) {
			logger.error("Unable to do Poll due to the following exception.", e);
		} catch (CommDatabaseException cde) {
			logger.error("Unable to do Poll due to the following exception.", cde);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					//dummy catch
				}
			}
		}
		return messages;
	}

	/**
	 * Polls the queue table and returns the size of the queue.
	 * 
	 * @param con
	 * @return size of the queue
	 * @throws CommException
	 * @throws SQLException
	 */
	private int doPoll(Connection con) throws CommException, SQLException {
		ResultSet rs = null;
		PreparedStatement pstmtSelect = null;
		PreparedStatement pstmtDelete = null;
		try {
			
			pstmtSelect = con.prepareStatement(SELECT_QUERY);
			
			pstmtDelete = con.prepareStatement(DELETE_QUERY);
			int count = 0;
			while (true) {
				//Get the message
				int records = 0;
				rs = pstmtSelect.executeQuery();
				rs.setFetchSize(500);
				while (rs.next()) {
					records++;
					CoreMessage message = new CoreMessage();
					message.setId(rs.getInt(1));
					message.setMessage(rs.getString(2));
					message.setParam(rs.getString(3));
					message.setMode("O");
					pstmtDelete.setObject(1, message.getId());
					pstmtDelete.execute();
					if (logger.isDebugEnabled()) {
						logger.debug("Processing " + message.toString());
					}
					if ("BSADDBATCH".equals(message.getMessage())) {
						MessageConsumer.getConsumer().consume(message);
					} else {
						RunnableMessageProcessor runnable = new RunnableMessageProcessor(message);
						if (super.getExecutor().isShutdown()) {
							break;
						}
						super.getExecutor().execute(runnable);
					}
					
					if (logger.isInfoEnabled()) {
						logger.info("Total Messages in queue waiting for transmission #" + super.getExecutor().getActiveCount() + super.getExecutor().getQueue().size());
					}
				}
				if (rs != null) {
					rs.close();
				}
				count+=records;
				if (records == 0 ) {
					break;
				}
			}
			return count;
		} catch (IllegalArgumentException e) {
		} finally {
			if (logger.isInfoEnabled()) {
				logger.info("Total Messages in queue waiting for transmission #" + super.getExecutor().getActiveCount() + super.getExecutor().getQueue().size());
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmtSelect != null) {
				pstmtSelect.close();
			}
			if (pstmtDelete != null) {
				pstmtDelete.close();
			}
			//no harm in calling again.
//			MessageConsumer.getConsumer().transmitAllMessages();
		}
		return -1;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/OutBoundQueuePoller.java                                                                   $
 * 
 * 5     6/16/10 11:56a Mandar.vaidya
 * Readded the row num condition
 * 
 * 4     3/19/10 5:43p Mandar.vaidya
 * Modified to get multiple records instead of single record.
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/