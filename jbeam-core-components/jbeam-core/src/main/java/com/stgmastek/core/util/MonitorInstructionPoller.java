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
package com.stgmastek.core.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.stgmastek.core.aspects.DatabaseAgnosticCandidate;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.Constants.MESSAGE_KEY;
import com.stgmastek.util.ResultSetMapper;

import java.util.concurrent.TimeUnit;

/**
 * A poller class for polling into the I_QUEUE table i.e. to receive monitor instructions
 * 
 * @author grahesh.shanbhag
 * 
 */
public class MonitorInstructionPoller extends BasePoller {

	/** 
	 * This handle to the preparedStatement for deleting a queued message 
	 */
	protected PreparedStatement pstmtDelete = null;
	
	private static final Logger logger = Logger.getLogger(MonitorInstructionPoller.class);
	
	
	/**
	 * Public constructor that takes a dedicated connection
	 *  
	 * @param context
	 * 		  The context of the batch 
	 */
	public MonitorInstructionPoller(BatchContext context){
		super(context);
	}

	/**
	 * @see BasePoller#init()
	 */
	
	@DatabaseAgnosticCandidate
	protected void init() throws BatchException {
	}

	/**
	 * @see BasePoller#execute()
	 */	
	
	protected void execute() throws BatchException{
		while (!STOP_POLLING) {
			ResultSet rs = null;
			PreparedStatement pstmtSelect = null;
			Connection dedicatedConnection = null;
			try {
				dedicatedConnection = context.getBATCHConnection();
				
				String query = "select id, message, param from i_queue order by id";				
				pstmtSelect = dedicatedConnection.prepareStatement(query);
				
				pstmtDelete = dedicatedConnection.prepareStatement("delete from i_queue where id = ? ");
				rs = pstmtSelect.executeQuery();
				rs.setFetchSize(1);
				Message message = 
					ResultSetMapper.getInstance().mapSingleRecord(rs, Message.class);
				
				if(message != null){
					if (logger.isDebugEnabled()) {
						logger.debug("Message:" + message );					
						
					}
				
					//Once message is consumed 
					//Delete the record not to be picked up again
					int batchNo = -1;
					
					if(message.getParams().containsKey(MESSAGE_KEY.BATCH_NO.getKey())){
						batchNo = Integer.parseInt(message.getParams().get(MESSAGE_KEY.BATCH_NO.getKey()));
						if(batchNo == context.getBatchInfo().getBatchNo()) {
							MessageConsumer.getConsumer().processMessage(message, context);
							pstmtDelete.setObject(1, message.getId());
							pstmtDelete.execute();
						}
					}
					
				} else {
					if (logger.isInfoEnabled()) {
						logger.info("No messages in the i_queue. " +
										"Poller would poll again for monitor instruction in next " 
										+ (Constants.POLLER_WAIT_PERIOD) + 
								" seconds");
					}
					TimeUnit.MILLISECONDS.sleep(Constants.POLLER_WAIT_PERIOD);					
				}
			} catch (Exception sqe) {
				throw new BatchException(sqe);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
				if (pstmtSelect != null) {
					try {
						pstmtSelect.close();
					} catch (SQLException e) {
					}
				}
				if (pstmtDelete != null) {
					try {
						pstmtDelete.close();
					} catch (SQLException e) {
					}
				}
				if (dedicatedConnection != null) {
					try {
						dedicatedConnection.close();
					} catch (SQLException e) {
					}
				}
			}
		}
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/MonitorInstructionPoller.java                                                                       $
 * 
 * 7     4/06/10 5:08p Kedarr
 * corrected java doc for missing tags or improper tags due to change in method signatures.
 * 
 * 6     3/19/10 6:30p Mandar.vaidya
 * Correction for open statement leak.
 * 
 * 5     3/09/10 6:30p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 4     12/22/09 2:43p Grahesh
 * Closing the result set appropriately
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/