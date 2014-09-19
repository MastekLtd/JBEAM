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
package com.stgmastek.core.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.util.BatchInfo;
import com.stgmastek.core.util.BatchJobMetaData;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.CConfig;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.LookupTable;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.ScheduledExecutable;

/**
 * Interface that defines the batch data related operations.
 *
 * In the methods where the connection is passed as a parameter it is expected that the
 * implementor will not set auto commit or close the connection object. Though, the implementor
 * is responsible to close all the statements, result sets, etc that are created within the method.
 *
 * @author Kedar Raybagkar
 * @since
 */
public interface IBatchDao extends IBaseDao {
	
	/**
	 * Schedules the listeners as needed
	 * 
	 * @param batchNo
	 * 			  The batch no
	 * @param executionStartTime
	 *            The batch execution start time
	 * @param executionEndTime
	 *            The execution end time, optional
	 * @param noOfListeners
	 * @return list of the scheduled executables
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public LinkedList<ScheduledExecutable> scheduleListeners(Integer batchNo,
			Date executionStartTime, Date executionEndTime,
			Integer noOfListeners, Connection connection)
			throws DatabaseException;

	/**
	 * Returns the listeners execution status
	 * 
	 * @param scheduledExecutables
	 *            The list of scheduled listeners
	 * @param connection
	 * 			  The JDBC connection.
	 * @return true if all listeners have completed their execution, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean getListenerExecutionStatuses(
			List<ScheduledExecutable> scheduledExecutables,
			Connection connection) throws DatabaseException;

	
	/**
	 * Updates the status of the listeners scheduled status to cancel.
	 * 
	 * @param scheduledExecutables
	 *            The list of scheduled listeners
	 * @param connection
	 * @return Boolean true if queued listeners are canceled.
	 * @throws DatabaseException
	 */
	@Log
	public Boolean cancelListenersQueuedRequests(
			List<ScheduledExecutable> scheduledExecutables,
			Connection connection) throws DatabaseException;
	
	/**
	 * For Development Purpose. Sets the PRE status as completed for the loop to
	 * complete. In reality, PRE would be updating the status
	 * 
	 * @param scheduledExecutable
	 *            The scheduledExecutable
	 * @return true if update successful, false otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean setPREStatus(ScheduledExecutable scheduledExecutable,
			Connection connection) throws DatabaseException;

	/**
	 * Starts a new batch profile, either a new batch or a batch revision
	 * 
	 * @param batchInfo
	 *            The batch information until initialization
	 * @return The batch information as BatchInfo instance
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public BatchInfo initiateBatch(BatchInfo batchInfo, Connection connection)
			throws DatabaseException;

	/**
	 * Closes the batch profile earlier created Updates the BATCH table with the
	 * end time and the end user
	 * 
	 * @param batchInfo
	 *            The basic batch information
	 * @param connection
	 *            The connection object
	 * 
	 * @return true if the update is successful, false otherwise
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	@Log
	public Boolean closeBatch(final BatchInfo batchInfo, Connection connection)
			throws DatabaseException;

	/**
	 * Updates the BATCH with failed over status. Status is 'Y', if isFailedOver
	 * is true. Status is 'N', if isFailedOver is false
	 * 
	 * @param batchInfo
	 *            The basic batch information
	 * @param connection
	 *            The connection object
	 * @param isFailedOver
	 *            The fail over status (true/ false)
	 * 
	 * @return true if the update is successful, false otherwise
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	@Log
	public Boolean updateBatchForFailOver(final BatchInfo batchInfo,
			Connection connection, Boolean isFailedOver)
			throws DatabaseException;
	
	/**
	 * Updates the CONFIGURATION with encoded password.
	 * 
	 * @param encodedPassword
	 *            The encoded password
	 *            
	 * @param connection
	 *            The connection object
	 * 
	 * @return integer, i.e. the number of rows updated
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	@Log
	public int updateConfiguration(String encodedPassword, Connection connection) throws DatabaseException;

	/**
	 * Returns the configuration settings for the CORE as in the CORE_CONFIG
	 * table
	 * 
	 * @return list of CConfig objects
	 * @throws DatabaseException
	 *             Any exception thrown during the database I/O
	 */
	@Log
	public List<CConfig> getConfigurations(Connection connection)
			throws DatabaseException;

	/**
	 * Inserts into the LOG table and returns the sequence number of the log.
	 * 
	 * @param bo
	 *            The batch object
	 * @return sequence number
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Long setBatchLog(BatchObject bo, Connection connection)
			throws DatabaseException;

	/**
	 * Updates batch log for the given primary key.
	 * 
	 * @param seqNo primary key
	 * @param bo
	 * @param connection
	 * @return the update count.
	 * @throws DatabaseException
	 */
	@Log
	public Integer updateBatchLog(Long seqNo, BatchObject bo, Connection connection)
	throws DatabaseException;
	
	/**
	 * Inserts a new record into the PROGRESS_LEVEL table. Note: All fields are
	 * mandatory
	 * 
	 * @param pl
	 *            The progress level object ({@link ProgressLevel})
	 * @return the newly created indicator number
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Integer initiateProgressLevel(ProgressLevel pl,
			Connection connection) throws DatabaseException;

	/**
	 * Updates the progress level
	 * 
	 * @param pl
	 *            The progress level object ({@link ProgressLevel})
	 * @return 0 if the insert is successful, negative number otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Integer updateProgressLevel(ProgressLevel pl, Connection connection)
			throws DatabaseException;

	/**
	 * Locks a processor instance. So other scheduled or on-the-fly processors
	 * would be halted with immediate effect.
	 * 
	 * @param requestId
	 *            The request identifier as fetched from the PROCESS_REQUEST
	 *            table
	 * @return true if the request id has successfully locked, false otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean lockProcessor(Long requestId, Connection connection)
			throws DatabaseException;
	/**
	 * Returns process request params for the first revision of the batch no
	 * provided
	 * 
	 * @param batchNo
	 * @param connection
	 * @return
	 * @throws DatabaseException
	 */
	public Map<String, Object> getProcessReqParams(int batchNo, Connection connection) throws DatabaseException;
	
	/**
	 * Un-Locks a processor instance. So other processors could be scheduled.
	 * 
	 * @param requestId
	 *            The request identifier as fetched from the PROCESS_REQUEST
	 *            table
	 * @return true if the request id has successfully unlocked, false otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean unlockProcessor(Long requestId, Connection connection)
			throws DatabaseException;

	/**
	 * Returns the look up table i.e. the records as set in the COLUMN_MAP
	 * 
	 * @return the data as an instance of LookupTable
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public LookupTable getLookupTable(Connection connection)
			throws DatabaseException;

	/**
	 * Returns the set of configurable items as set in the META_DATA table.
	 * This method should return an empty list if the configurables are already pro-created. 
	 * 
	 * @param batchRunDate
	 *            The batch run date.
	 * @param entityParams
	 *            The entity parameters
	 * @param connection
	 * 			  The batch connection object.
	 * @return list of configurable items as instances of BatchJobMetaData
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public List<BatchJobMetaData> getConfigurables(
			Date batchRunDate, 
			EntityParams entityParams, Connection connection)
			throws DatabaseException;

	/**
	 * Returns the object map as set in the OBJECT_MAP table
	 * 
	 * @param batchRunDate
	 *            The batch date
	 * @return Map<String, ObjectMapDetails> i.e. Map<id, ObjectMapDetails>
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public HashMap<String, ObjectMapDetails> getObjectMap(Date batchRunDate,
			Connection connection) throws DatabaseException;

	/**
	 * Updates the instruction log for which the action is taken by the batch
	 * 
	 * @return integer, i.e. the number of rows updated
	 * 
	 */
	@Log
	public Integer updateInstructionLog(final BatchInfo batchInfo,
			Connection connection) throws DatabaseException;

	/**
	 * Updates Instruction Log table in case of error
	 * 
	 * @param batchInfo
	 * @param messaage
	 * @param connection
	 * @return
	 * @throws DatabaseException
	 */
	public Integer updateInstructionLogError(Integer instrSeqNo, String messaage, Connection connection) throws DatabaseException;

	/**
	 * Set the system information on which the batch is run
	 * 
	 * @return integer, i.e. the number of rows inserted
	 * 
	 */
	@Log
	public Integer setSystemInfo(final BatchInfo batchInfo,
			Connection connection) throws DatabaseException;

	/**
	 * Batch can have various endings. If the previous batch ending was
	 * completed, i.e. BATCH_COMPLETED then there can be no further revisions
	 * for that batch This method does that check
	 * 
	 * @param batchNo
	 *            The batch number
	 * @return true, if the batch can have revisions, false otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean canHaveRevision(Integer batchNo, Connection connection)
			throws DatabaseException;
	
	
	/**
	 * Returns a list of all batch objects that have not been completed or error'ed out and 
	 * have surpassed the execution maximum time limit.
	 * 
	 * The method should join the log with the object map table and get the estimated time.
	 * 
	 * @param batchNo Batch Number
	 * @param batchRevNo Batch Revision Number
	 * @param connection Connection
	 * @return list of batch objects that are currently being executed.
	 * @throws DatabaseException
	 */
	public List<BatchObject> getCurrentlyRunningJobs(Integer batchNo, Integer batchRevNo, Connection connection)
		throws DatabaseException;
	
	/**
	 * Returns a list of all objects for the given batch number and revision number whose execution time is less than
	 * the minimum time configured in the object map.
	 *  
	 * @param batchNo Batch Number
	 * @param batchRevNo Batch Revision Number
	 * @param connection Connection
	 * @return List of objects that have been executed in less than minimum time.
	 * @throws DatabaseException
	 */
	public List<BatchObject> getJobsExecutedLessThanMinTime(Integer batchNo, Integer batchRevNo, Connection connection)
		throws DatabaseException;

	/**
	 * Returns the map of records as set in the ORDERBY_MAP
	 * 
	 * @return the data as an instance of {@link Map}
	 * 
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Map<String, String> getOrderByLookupTable(Connection connection) throws DatabaseException;
}
