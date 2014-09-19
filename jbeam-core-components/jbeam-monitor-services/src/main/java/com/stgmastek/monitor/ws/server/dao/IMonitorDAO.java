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
package com.stgmastek.monitor.ws.server.dao;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;

import com.stgmastek.core.comm.client.CResProcessRequestScheduleVO;
import com.stgmastek.core.comm.client.ScheduleData;
import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.util.DatabaseAgnosticCandidate;
import com.stgmastek.monitor.ws.server.vo.BatchDetails;
import com.stgmastek.monitor.ws.server.vo.BatchInfo;
import com.stgmastek.monitor.ws.server.vo.BatchObject;
import com.stgmastek.monitor.ws.server.vo.MonitorCalendarData;
import com.stgmastek.monitor.ws.server.vo.FailedObjectDetails;
import com.stgmastek.monitor.ws.server.vo.InstallationData;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.InstructionLog;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ListenerInfo;
import com.stgmastek.monitor.ws.server.vo.MenuData;
import com.stgmastek.monitor.ws.server.vo.ObjectExecutionGraphData;
import com.stgmastek.monitor.ws.server.vo.ProcessRequestScheduleData;
import com.stgmastek.monitor.ws.server.vo.ProgressLevelData;
import com.stgmastek.monitor.ws.server.vo.ReqBatch;
import com.stgmastek.monitor.ws.server.vo.ReqBatchDetails;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;
import com.stgmastek.monitor.ws.server.vo.ReqListenerInfo;
import com.stgmastek.monitor.ws.server.vo.ReqSearchBatch;
import com.stgmastek.monitor.ws.server.vo.SystemDetails;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * DAO interface for all Monitor System.
 * MonitorDAO class must implement this interface.
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 3 $ 
 */
public interface IMonitorDAO extends IBaseDAO {

		/**
		 * Gets the instruction log sequence number from database.
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return instruction log sequence number
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer getInstructionLogSeqNo(Connection connection) throws CommDatabaseException;
		
		/**
		 * Inserts the data into INSTRUCTION_LOG table.
		 * 
		 * @param reqInstructionLog
		 *            The instruction parameters
		 * @param connection
		 * 			  connection object
		 * @param instruction 
		 * 			the instruction can be 'Run' or 'Stop' batch
		 * 
		 * @return 1 if the record was inserted successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer insertInstructionLog(ReqInstructionLog reqInstructionLog, Connection connection, String instruction)
		throws CommDatabaseException;
		
		/**
		 * Inserts data in INSTRUCTION_PARAMETERS table.
		 * 
		 * @param instructionLogSeqNo
		 * 			The unique instruction log sequence
		 *  
		 * @param parameters
		 * 			The list of instruction parameters 
		 * 
		 * @param entityValuesMap
		 * 			The map of entities provided with instruction to run batch
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return  1 if the record was inserted successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 *             
		 */
		@DatabaseAgnosticCandidate
		public Integer addInstructionParameters(
				Integer instructionLogSeqNo, ReqInstructionLog log, List<InstructionParameter> parameters,
				LinkedHashMap<String, String> entityValuesMap, Connection connection)
				throws CommDatabaseException  ;

		/**
		 * Returns the batch entity data for the installation supplied
		 * 
		 * @param installationCode
		 *            The installation code
		 * @param connection
		 * 			  	connection object
		 *            
		 * @return List<InstallationEntity> The installation entities as list
		 */
		public List<InstallationEntity> getBatchEntityData(
				String installationCode, Connection connection) throws CommDatabaseException ;
		
		/**
		 * Returns the system information for the batch supplied
		 * 
		 * @param batch
		 *            The basic batch information
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return The system information as an instance of {@link SystemDetails}
		 */
		public SystemDetails getSystemInfo(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Fetches the batch information for the a given batch
		 * 
		 * @param batch
		 *            The basic batch information
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return The batch information
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 * 
		 */
		public BatchDetails getBatchData(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Gets the list of installations in JBEAM.
		 * 
		 * @param reqBatch
		 *            The basic batch information
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return the list of installations
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<InstallationData> getInstallationData(ReqBatch reqBatch, Connection connection)
				throws CommDatabaseException ;
		
		/**
		 * Returns a list of batch (and revision) information for the supplied batch
		 * 
		 * @param batch
		 *            The basic batch information
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return list of batch (and revision) information
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<BatchInfo> getBatchInfo(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Fetch the system date
		 * 
		 * @param connection
		 * 			connection object
		 *   
		 * @return The system date in dd-MON-yyyy hh24:mi:ss format  
		 * 
		 * @throws CommDatabaseException
		 * 		   Any database related I/O exception 
		 */
		@DatabaseAgnosticCandidate
		public String getSystemDate(Connection connection) throws CommDatabaseException ;
		/**
		 * Gets the list of listener details
		 * 
		 * 
		 * @param batch
		 *            The basic batch information
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return batchData Batch data from BATCH.
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<ListenerInfo> getListenerInfo(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Gets the list of failed objects for supplied listener data
		 * 
		 * 
		 * @param reqListenerInfo
		 *            The listener data
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return list of failed objects
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<FailedObjectDetails> getFaliedObjectDetails(
				ReqListenerInfo reqListenerInfo, Connection connection) throws CommDatabaseException ;
		
		/**
		 * Gets the list of batch object details for supplied batch details
		 * 
		 * @param reqBatchDetails
		 *            The Batch details
		 * 
		 * @param connection
		 * 			connection object
		 *            
		 * @return list of batch object details
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<BatchObject> getBatchObjectDetails(
				ReqBatchDetails reqBatchDetails, Connection connection) throws CommDatabaseException ;

		/**
		 * Gets the batch progress graph data from GRAPH_DATA_LOG for the provided
		 * batch information (@link ReqBatch}.
		 * 
		 * @param batch
		 *            The batch data (@link ReqBatch}.
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return list of batchProgressGraphVO Batch Progress graph data from
		 *         GRAPH_DATA_LOG.
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<ObjectExecutionGraphData> getGraphData(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Gets the batch completed data from BATCH and stores in
		 * {@link BatchDetails}.
		 * 
		 * 
		 * @param searchBatch
		 *            The basic batch information required for search
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return list of BatchDetails Batch data from BATCH for the search data
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<BatchDetails> getBatchCompletedData(ReqSearchBatch searchBatch, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Gets the batch progress level data from PROGRESS_LEVEL for the provided
		 * batch information (@link ReqBatch}.
		 * 
		 * @param batch
		 *            The batch data (@link ReqBatch}.
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return resProgressLevelVO Batch Progress level data from PROGRESS_LEVEL
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<ProgressLevelData> getProgressLevelData(ReqBatch batch, Connection connection)
				throws CommDatabaseException ;

		
		/**
		 * Gets the calendar details of supplied calendar information
		 * 
		 * @param calendarData
		 *            The reference of Calendar data {@link MonitorCalendarData}
		 * 
		 * @param connection
		 * 			  connection object
		 *            
		 * @return list of Calendar data {@link MonitorCalendarData}
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<MonitorCalendarData> getSingleCalendarDetails(MonitorCalendarData calendarData, Connection connection)
				throws CommDatabaseException ;

		/**
		 * Gets the details of a selected calendar
		 * 
		 * @param calendarData
		 *            The reference of Calendar data {@link MonitorCalendarData}
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return list of calendar data
		 * 
		 * @throws CommDatabaseException
		 */
		public List<MonitorCalendarData> getCalendarDetails(MonitorCalendarData calendarData, Connection connection) throws CommDatabaseException ;
		
		/**
		 * Inserts the calendar data into SCHEDULE table.
		 * 
		 * @param calendarDataList
		 *            The list of calendar data
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @return 1 if the record was inserted successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer addCalendar(List<MonitorCalendarData> calendarDataList, Connection connection)
				throws CommDatabaseException ;
		
		/**
		 * Deletes the calendar data from the table.
		 * 
		 * @param calendarData
		 *            The reference of calendar data
		 * 
		 * @param connection
		 * 			connection object
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public void deleteCalendar(MonitorCalendarData calendarData, Connection connection)
				throws CommDatabaseException ;
		
		/**
		 * Inserts message BSCALENDAR into O_QUEUE table.
		 * 
		 * @param calendarData
		 *            The reference of calendar data
		 * 
		 * @param connection
		 * 			  connection object
		 * 
		 * @return 1 if the record was inserted successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public Integer addCalendarMessageToOQueue(MonitorCalendarData calendarData, Connection connection)
				throws CommDatabaseException;
				
		/**
		 * Generates menu data list for JBEAM batch details as per the user role.
		 * 
		 * @param userProfile
		 * 			The user details with the user role id
		 * 
		 * @param connection
		 * 			  connection object
		 * 
		 * @return list of menu data
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<MenuData> getMenuData(UserProfile userProfile, Connection connection) throws CommDatabaseException;
		
		/**
		 * Generates prior (parent) menu data list for JBEAM batch details as per the user role.
		 * 
		 * @param userProfile
		 * 			The user details with the user role id
		 * 
		 * @param connection
		 * 			  connection object
		 * 
		 * @return list of prior (parent) menu data
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<MenuData> getPriorMenuData(UserProfile userProfile, Connection connection) throws CommDatabaseException;
		
		
		/**
		 * Creates the raw instruction parameters list for the provided batch
		 * information (@link ReqBatch}.
		 * 
		 * @param reqBatch
		 *            The batch data (@link ReqBatch}.
		 * 
		 * @param connection
		 *            connection object
		 * 
		 * @return list of batch parameters
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public List<InstructionParameter> createInstructionParamsList(
				ReqBatch reqBatch, Connection connection)
				throws CommDatabaseException;
		
		/**
		 * Retrieves the instruction log details 
		 * 
		 * @param instructionLogNo
		 *            The instruction log sequence number
		 * 
		 * @param connection
		 *            connection object
		 * 
		 * @return list of batch parameters
		 * 
		 * @throws CommDatabaseException
		 *             Any database I/O related exception occurred
		 */
		public InstructionLog getInstructionLog(
				Integer instructionLogNo, Connection connection)
				throws CommDatabaseException;
		
		
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
		 * Retrieves list of all schedules (Active /cancelled).
		 * 
		 * @param installationCode
		 * 		The installation code
		 *   
		 * @param connection
		 *            connection object
		 *            
		 * @return list of schedules.
		 * 
		 * @throws CommDatabaseException 
		 *  			Any database I/O related exception occurred
		 */
		public List<ProcessRequestScheduleData> getProcessRequestScheduleList(
				String installationCode, Connection connection) throws CommDatabaseException;

		
		/**
		 * Updates the status of all the schedules.
		 *  
		 * @param requestScheduleVO
		 * 				The reference of {@link CResProcessRequestScheduleVO}
		 * 
		 * @param connection
		 * 				connection object
		 * 
		 * @return 1 if the record was updated successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException 
		 *  			Any database I/O related exception occurred
		 */
		public Integer updateProcessRequestSchedule(CResProcessRequestScheduleVO requestScheduleVO,
				Connection connection) throws CommDatabaseException;
		
		/**
		 * Updates the status of the schedule.
		 * 
		 * @param processRequestScheduleData
		 * 			The reference of {@link ScheduleData}
		 * 
		 * @param connection
		 * 				connection object
		 * 
		 * @return 1 if the record was updated successfully 0 otherwise
		 * 
		 * @throws CommDatabaseException 
		 *  			Any database I/O related exception occurred
		 */
		public Integer updateProcessRequestSchedule(ScheduleData processRequestScheduleData,
				Connection connection) throws CommDatabaseException;		
		
}
/*
 * Revision Log ------------------------------- 
 * $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/IMonitorDAO.java                            $
 * 
 * 3     7/13/10 4:26p Mandar.vaidya
 * Moved userAuthentication method to IUserDAO.
 * 
 * 2     7/10/10 11:38a Lakshmanp
 * added getInstructionParameters method to show parameters list along with batch details
 * 
 * 1     6/23/10 10:53a Lakshmanp
 * initial version
 * 
 */
