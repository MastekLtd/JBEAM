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
package com.stgmastek.core.comm.server.dao;

import java.sql.Connection;
import java.util.List;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.vo.CReqCalendarLog;
import com.stgmastek.core.comm.server.vo.CReqInstructionLog;
import com.stgmastek.core.comm.server.vo.CReqProcessRequestScheduleVO;
import com.stgmastek.core.comm.server.vo.CalendarData;
import com.stgmastek.core.comm.server.vo.InstructionParameters;
import com.stgmastek.core.comm.server.vo.ScheduleData;
import com.stgmastek.monitor.comm.client.BatchLog;
import com.stgmastek.monitor.comm.client.MReqBatch;
import com.stgmastek.monitor.comm.client.MReqInstructionLog;

/**
 * DAO for all batch related operations.
 * 
 * @author Lakshman Pendrum
 * 
 */
public interface IBatchDAO extends IBaseDAO{

		/**
		 * DAO method to add instruction log with parameters into INSTRUCTION_LOG.
		 * This method will first insert instruction log into INSTRUCTION_LOG. 
		 * On successful insert, it will insert the instruction parameters into INSTRUCTION_PARAMETERS.
		 * On successful insert, it will insert the data into PRE tables.
		 * 
		 * @param reqInstructionLog
		 * 	 	  The instruction log to be inserted in INSTRUCTION_LOG of CORE @link CReqInstructionLog
		 * 
		 * @param connection
		 * 		  	connection object
		 * 
		 * @return reqId of the newly inserted row in PROECSS_REQUEST table,
		 * 		   provided the insert into the instruction log, instruction parameters and 
		 * 		   scheduling of the batch is successful
		 *  
		 * @throws CommDatabaseException
		 * 		   Any database I/O exception
		 */
		public Integer addInstructionLog(CReqInstructionLog reqInstructionLog, Connection connection) 
					throws CommDatabaseException ;
		
		
		/**
		 * DAO method to add instruction parameters into INSTRUCTION_PARAMETERS
		 * 
		 * @param list
		 * 		  List of instruction parameters to be inserted in INSTRUCTION_PARAMETERS of CORE
		 * @param connection
		 * 		  	connection object
		 * 
		 * @return true if the inserts into the instruction parameters is successful.  
		 * @throws CommDatabaseException
		 * 		   Any database I/O exception
		 */
		public Boolean addInstructionParameters(List<InstructionParameters> list, Integer seqNo, Connection connection) 
					throws CommDatabaseException ;

		/**
		 * Gets the batch data from BATCH and stores in {@link MReqBatch}.
		 * 
		 * @param batchNo
		 * 		  The batch no
		 * @param batchRevNo
		 * 		  The batch revision no
		 * @param connection
		 * 		  	connection object
		 * 
		 * @return batchData 
		 * 		   Batch data from BATCH.
		 * @throws CommDatabaseException 
		 * 		   Any database I/O exception
		 */
		public MReqBatch getBatchData(Integer batchNo, Integer batchRevNo, Connection connection)
				throws CommDatabaseException ;
		
		/**
		 * Gets the logged batch data from LOG 
		 * and stores in {@link BatchLog}.
		 * 
		 * @param batchSeqNo
		 * 		  The batch sequence no
		 * @return batchLogData 
		 * 		   Logged batch data from LOG.
		 * @throws CommDatabaseException
		 * 		   Any database I/O exception 
		 */
		public BatchLog getBatchLogData(Integer batchSeqNo, Connection connection)
				throws CommDatabaseException ;

	
		/**
		 * Defines a calendar with selected non working days.
		 * 
		 * @param calendarVO
		 *            The reference of Calendar data
		 * @param connection
		 * 		  	connection object
		 * 
		 * @throws CommDatabaseException
		 */
		public void defineCalendar(CReqCalendarLog calendarVO, Connection connection)
				throws CommDatabaseException ;
		
		/**
		 * Inserts the calendar data into SCHEDULE table.
		 * 
		 * @param calendarDataList
		 *            The list of calendar data
		 * 
		 * @return 1 if the record was inserted successfully 0 otherwise
		 * @param connection
		 * 		  	connection object
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer addCalendar(List<CalendarData> calendarDataList, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Deletes the calendar data from the table.
		 * 
		 * @param calendarData
		 *            The reference of calendar data
		 * @param connection
		 * 		  	connection object
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public void deleteCalendar(CalendarData calendarData, Connection connection)
				throws CommDatabaseException ;


		/**
		 * Returns true if the batch is locked else returns false.
		 * 
		 * @param connection
		 * 		  	connection object
		 * 
		 * @return Boolean true or false.
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Boolean isBatchLocked(Connection connection) throws CommDatabaseException;
		
		/**
		 * Retrieves the instruction log for the supplied sequence number   
		 * 
		 * @param seqNo
		 * 		  The sequence number
		 *  
		 * @param connection
		 * 		  	connection object
		 * 
		 * @return An instance of the request instruction log
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public MReqInstructionLog getInstructionLog(Integer seqNo,Connection connection) throws CommDatabaseException;
		
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
		 *             Any database I/O related exception occurred
		 * 
		 */
		public Integer updateInstructionLog(MReqInstructionLog instructionLog,
				Connection connection) throws CommDatabaseException;
		
		/**
		 * Retrieves the active schedule for the supplied schedule ids (wrapped
		 * in {@link CReqProcessRequestScheduleVO}     
		 * 
		 * @param requestScheduleData
		 * 			The reference to {@link ScheduleData}
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return An instance of active schedule from PROCESS_REQUEST_SCHEDULE table. 
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public ScheduleData getProcessRequestScheduleData(
				ScheduleData requestScheduleData, 
				Connection connection) throws CommDatabaseException;
		
		/**
		 * Retrieves the active schedule for the supplied request id.
		 * This request id is retrieved after batch scheduling operation.  
		 * 
		 * @param reqId
		 * 			A Request id retrieved from PROCESS_REQUEST table after batch scheduling operation.
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return An instance of active schedule from PROCESS_REQUEST_SCHEDULE table. 
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public ScheduleData getProcessRequestScheduleData(
				Integer reqId,	Connection connection) throws CommDatabaseException;
		
		
		/**
		 * Cancels the schedule in two steps.
		 * <li>i) Update the PROCESS_REQUEST table with req_stat = 'X' for provided schedule id 
		 * and the req_stat = 'Q'</li>.<BR>
		 * <li>ii)  Update the PROCESS_REQUEST_SCHEDULE table with sch_stat = 'C' and req_stat = 'X' 
		 * for provided schedule id and sch_stat = 'A'</li>
		 *  
		 * @param scheduleData
		 * 				The reference of {@link ScheduleData}
		 * 
		 * @param connection
		 * 			The Connection object
		 * 
		 * @return integer[] consisting of the records updated in PROCESS_REQUEST and 
		 * 			PROCESS_REQUEST_SCHEDULE tables.
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer[] cancelSchedule(ScheduleData scheduleData,
				Connection connection) throws CommDatabaseException;
		
	}

	
	/*
	* Revision Log
	* -------------------------------
	* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/IBatchDAO.java 1     6/21/10 11:28a Lakshma $
	* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 1     6/21/10 11:28a Lakshmanp
 * initial version
	 * 
	 * 
	*
	*
	*/
