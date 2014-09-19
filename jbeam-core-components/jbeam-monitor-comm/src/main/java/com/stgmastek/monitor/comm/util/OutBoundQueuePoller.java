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

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.util.ResultSetMapper;

/**
 * A poller class for polling into the o_queue table
 * 
 * @author grahesh.shanbhag
 * 
 */
public class OutBoundQueuePoller extends BasePoller {

	private static final Logger logger = Logger.getLogger(OutBoundQueuePoller.class);
	
	/** 
	 * This handle to the preparedStatement for deleting a queued message 
	 */
	protected PreparedStatement pstmtDelete = null;
	
	protected PreparedStatement pstmtSelect = null;		
	
	/**
	 * Public constructor that takes a dedicated connection
	 *  
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
	
	protected void execute() throws CommException{

		String query = "select id, message, param, installation_code from o_queue order by id";				

		while (!STOP_POLLING) {
			ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.getInstance().getDefaultConnection();
				con.setAutoCommit(true);
				pstmtSelect = con.prepareStatement(query);
				
				pstmtDelete = con.prepareStatement("delete from o_queue where id = ? ");
				
				rs = pstmtSelect.executeQuery();
				rs.setFetchSize(1);
				MonitorMessage message = 
					ResultSetMapper.getInstance().mapSingleRecord(rs, MonitorMessage.class);
				
				if(message != null){
					if (logger.isInfoEnabled()) {
						logger.info("Processing:" + message );					
					}
					rs.close();
					rs = null;
					message.setMode("O");
					MessageConsumer.getConsumer().processMessage(message);
				
					//Once message is consumed 
					//Delete the record not to be picked up again
					pstmtDelete.setObject(1, message.getId());
					pstmtDelete.execute();
					
				}else{
					if (logger.isInfoEnabled()) {
						logger.info("No messages in the queue o_queue. " +
								"Poller would poll again in next " + (CommConstants.POLLING_WAIT_PERIOD / 1000) + 
						" seconds");
						
					}
					try {
						Thread.sleep(CommConstants.POLLING_WAIT_PERIOD);					
					} catch (InterruptedException e) {
					}
				}
				
			} catch (Exception sqe) {
//				throw new CommException(sqe);
//				should not throw the exception as it is supposed to continue its execution.
//				but let the stack trace be printed.
				logger.error(sqe);
			} finally {
				if(rs != null) {
					try {
						rs.close();
						rs = null;					
					} catch (SQLException e) {
					}
				}
				
				if (pstmtDelete != null ) {
					try {
						pstmtDelete.close();
					} catch (SQLException e) {
					}
				}
				if (pstmtSelect != null) {
					try {
						pstmtSelect.close();
					} catch (SQLException e) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
					}
				}
//				super.freeResources();
			}
		}		
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/OutBoundQueuePoller.java                                                             $
 * 
 * 4     3/12/10 4:40p Mandar.vaidya
 * Changes made for not closing the jdbc connection as it is called from the collators.
 * 
 * 3     3/12/10 4:31p Kedarr
 * Added respective prepared statements as the base class statements were removed.
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/