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
package com.stgmastek.monitor.comm.server.dao;

import java.sql.Connection;
import java.util.List;
import java.util.TimeZone;

import com.stgmastek.core.comm.client.CReqCalendarLog;
import com.stgmastek.core.comm.client.CReqInstructionLog;
import com.stgmastek.core.comm.client.CalendarData;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.vo.MReqBatch;
import com.stgmastek.monitor.comm.server.vo.BatchLog;
import com.stgmastek.monitor.comm.server.vo.MReqInstructionLog;


/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author Kedar Raybagkar
 * @since
 */
public interface IBatchDAO extends IBaseDAO {

		/**
		 * Inserts the data into LOG table.
		 * 
		 * @param log
		 * 		  The batch log
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 * @throws CommDatabaseException 
		 */
		public Integer addBatchLog(BatchLog log,Connection connection) throws CommDatabaseException;
		
		/**
		 * Updates the batch log with the given batch log
		 * 
		 * @param log
		 * 		  The batch log
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 * @throws CommDatabaseException 
		 */
		public Integer updateBatchLog(BatchLog log,Connection connection)
				throws CommDatabaseException;

		/**
		 * Adds the batch with the given batch
		 * 
		 * @param batch
		 * 		  The batch
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 *  
		 * @throws CommDatabaseException 
		 */
		public Integer addBatch(MReqBatch batch,Connection connection) throws CommDatabaseException; 
		
		
		/**
		 * Updates the batch with the given batch.	 * 
		 * This will update only Execution end time for the given 
		 * batch number and batch revision number.
		 * 
		 * @param batch
		 * 		  The batch
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 *  
		 * @throws CommDatabaseException 
		 */
		public Integer updateBatch(MReqBatch batch,Connection connection) throws CommDatabaseException;
		
		/**
		 * Updates the instruction log table 
		 * 
		 * @param instructionLog
		 * 			The reference to Instruction log ({@link MReqInstructionLog}
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 * 
		 * @throws CommDatabaseException
		 * 
		 */
		public Integer updateInstructionLog(MReqInstructionLog instructionLog,
				Connection connection) throws CommDatabaseException;
		
	////////////////////////////////////////////////////////////////////////////////////////////////
		//OUT BOUND METHODS 
	////////////////////////////////////////////////////////////////////////////////////////////////	
		
		/**
		 * Fetches the instruction log for the supplied sequence number   
		 * 
		 * @param seqNo
		 * 		  The sequence number
		 *  
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return An instance of the request instruction log
		 */
		public CReqInstructionLog getInstructionLog(Integer seqNo,Connection connection) throws CommDatabaseException;

		/**
		 * Gets the details of a selected calendar
		 * 
		 * @param calendarData
		 *            The reference of Calendar data {@link CalendarData}
		 * 
		 * @param installationCode
		 *            the installation code for which calendar data is requested.
		 *  
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return the reference of Calendar log {@link CReqCalendarLog}
		 * 
		 * @throws CommDatabaseException
		 */
		public CReqCalendarLog getCalendarData(CalendarData calendarData,
				String installationCode,Connection connection) throws CommDatabaseException;
		
		/**
		 * Returns the TimeZone associated with that installation.
		 * 
		 * @param installationCode
		 * @param connection
		 * @return the instance of @Timezone
		 * @throws CommDatabaseException
		 */
		public TimeZone getInstallationTimeZone(String installationCode, Connection connection) throws CommDatabaseException;
		

		/**
		 * Retrieves the list of installation codes.
		 * 
		 * @param connection
		 *            connection object
		 * 
		 * @return list of installation codes
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<String> getInstallationCodeList(Connection connection)
				throws CommDatabaseException;

		/**
		 * Inserts the data into PROCESS_REQUEST_SCHEDULE table.
		 * 
		 * @param scheduleData
		 * 		  The reference of {@link ScheduleData}
		 *  
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return 1 if the record was inserted in the table 
		 * 		   0 otherwise
		 * 
		 * @throws CommDatabaseException 
		 */
		public Integer insertProcessRequestSchedule(ScheduleData scheduleData,
				Connection connection)  throws CommDatabaseException;
		
	/*
	* Revision Log
	* -------------------------------
	* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/dao/IBatchDAO.java                                                                 $
 * 
 * 2     6/18/10 12:16p Lakshmanp
 * added connection as parameter in all methods
 * 
 * 1     6/17/10 10:30a Kedarr
 * Initial version
	 * 
	 * 1     6/17/10 10:22a Kedarr
	 * 
	 * 16    5/25/10 4:16p Mandar.vaidya
	 * Removed bug from updateBatch method
	 * 
	 * 15    4/15/10 9:06a Kedarr
	 * Changes made to add failed over in update batch
	 * 
	 * 14    4/09/10 11:10a Mandar.vaidya
	 * Added new method getCalendarData with query.
	 * 
	 * 13    3/30/10 1:03p Kedarr
	 * Changes made to update the batch number and revision number in the installation table.
	 * 
	 * 12    3/22/10 2:02p Mandar.vaidya
	 * Modified the INSERT_BATCH query.
	 * 
	 * 11    3/22/10 1:22p Mandar.vaidya
	 * Changes for maxMemory reverted.
	 * 
	 * 10    3/22/10 12:10p Mandar.vaidya
	 * Modified the queries and implementation of methods for newly added fields usedMemoryBefore and usedMemoryAfter in LOG table and maxMemory in BATCH table.
	 * 
	 * 9     3/22/10 10:10a Mandar.vaidya
	 * Changes to UPDATE_LOG query.
	 * 
	 * 8     3/19/10 8:02p Mandar.vaidya
	 * Changes made to updateBatchLog and addBatchLog methods. Added logger.
	 * 
	 * 7     3/19/10 3:05p Mandar.vaidya
	 * Modified the implementation of updateBatchLog method and added new query UPDATE_LOG, new methods addBatchLog and setPreparedStatementBatchLog.
	 * 
	 * 6     3/15/10 12:50p Mandar.vaidya
	 * Added the field cycleNo in insert_log query and method.
	 * Added the field instruction_seq_no in insert_batch query and method.
	 * 
	 * 5     3/11/10 2:29p Mandar.vaidya
	 * Added failedOver column in the query INSERT_BATCH.
	 * 
	 * 4     3/03/10 1:42p Grahesh
	 * Added batchEndReason in updateBatch method and in related query
	 * 
	 * 3     12/18/09 4:14p Grahesh
	 * Updated the comments
	 * 
	 * 2     12/17/09 11:59a Grahesh
	 * Initial Version
	*
	*
	*/
}
