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
import java.util.List;

import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchJobMetaData;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.GroupInfo;

/**
 * Interface that defines the application data related operations.
 *
 * In the methods where the connection is passed as a parameter it is expected that the
 * implementor will not set auto commit or close the connection object. Though, the implementor
 * is responsible to close all the statements, result sets, etc that are created within the method.
 * 
 * @author Kedar Raybagkar
 * @since
 */
public interface IAppDao extends IBaseDao {
	
	/**
	 * Returns the next unique value that will be used as a Batch Sequence Number.
	 * 
	 * @param connection {@link Connection} 
	 * @return Long Sequence Number
	 * @throws DatabaseException
	 */
	public Long getBatchSequenceNo(Connection connection) throws DatabaseException;
	
	/**
	 * Returns the list of assigned batch objects for an entity as identified by
	 * EntityParams
	 * 
	 * @param batchRunDate
	 *            The batch run date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @param groupInfo
	 *            The group for which the jobs are to be picked
	 * @param listenerId
	 *            The listener identifier
	 * @return The list of batch objects
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public List<BatchObject> getAssignedBatchObjects(Date batchRunDate,
			EntityParams entityParams, GroupInfo groupInfo, Integer listenerId,
			Connection connection) throws DatabaseException;

	/**
	 * Resets the configurable items depending upon whether it is a revision run
	 * or not. If revision run then, the update would be fired that would mark
	 * '99' record(s) to 'PC' and in case of fresh batch would mark all '99' and
	 * 'PC' as 'TR' (TeRminated)
	 * 
	 * @param isRevisionRun
	 *            Whether it is a revision run or a fresh batch run
	 * @param isDateRun
	 * 			  Whether it is a DATE run or a SPECIAL run
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @return true if the resetting of the configurable items was achieved
	 *         successfully
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean resetConfigurables(Boolean isRevisionRun, Boolean isDateRun, 
			EntityParams entityParams, Connection connection)
			throws DatabaseException;

	/**
	 * Sets the configurable prior to execution or the start of the batch
	 * proceedings
	 * 
	 * @param configurables
	 *            The list of configurable jobs to set
	 * @param batchContext
	 * 			  The context object to reference for schedule date as well as user id.
	 * @return 0 if all configurable jobs have been pro-created as expected 1
	 *         otherwise
	 * @throws DatabaseException
	 *             Exception thrown during insertion of the records
	 */
	@Log
	public Integer setConfigurables(List<BatchJobMetaData> configurables,
			Connection connection, BatchContext batchContext) throws DatabaseException;

	/**
	 * Sets the execution status for the execution of a batch job
	 * 
	 * @param batchObject
	 *            The batch object for which the status has to be set
	 * @return 0 if updates was successfully 1 otherwise
	 * @throws DatabaseException
	 *             Exception thrown during insertion of the records
	 */
	@Log
	public Integer setBatchObjectStatus(BatchObject batchObject,
			Connection connection) throws DatabaseException;

	/**
	 * Checks whether the batch object has marked its status
	 * 
	 * @param batchObject
	 *            The batch object for which the status has to be set
	 * @return the current status as string
	 * @throws DatabaseException
	 *             Exception thrown during insertion of the records
	 */
	@Log
	public String checkBatchObjectStatus(BatchObject batchObject,
			Connection connection) throws DatabaseException;

	/**
	 * Returns whether there are more jobs of the 'same type' identified by
	 * EntityParams
	 * 
	 * @param batchRunDate
	 *            The batch run date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @return true if there are more jobs, false otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean moreJobsToExecute(Date batchRunDate,
			EntityParams entityParams, Connection connection)
			throws DatabaseException;

	/**
	 * Returns distinct set of values for the supplied criteria
	 * 
	 * @param batchRunDate
	 *            The batch run date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @return list of distinct group informations
	 * @throws DatabaseException
	 *             Any database I/O exception thrown
	 */
	@Log
	public List<GroupInfo> getDistinctSet(Date batchRunDate,
			EntityParams entityParams, Connection connection)
			throws DatabaseException;

	/**
	 * Returns distinct set of values for the supplied criteria
	 * 
	 * @param batchRunDate
	 *            The batch date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @param listenerId
	 *            The listener identifier The listener identifier is needed, as
	 *            when the listener is executing it needs to get the distinct
	 *            set for itself for the reason of marking all other objects for
	 *            the same category as 'SP' in case of an '99'.
	 *            <p />
	 *            EX: For a Policy say there is a distinct set of P1, P2 ...
	 *            P10. There are MAX 5 listeners alloted, so each listener would
	 *            have 2 distinct policies each to execute. If the listener
	 *            identifier is provided then the distinct set would be these
	 *            two values. If listener identifier is null then the entire set
	 *            i.e. 10 distinct policies would be returned.
	 * 
	 * @return list of distinct group informations
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public List<GroupInfo> getDistinctSet(Date batchRunDate,
			EntityParams entityParams, Integer listenerId,
			Connection connection) throws DatabaseException;

	/**
	 * Updates the listener indicators in JOB_SCHEDULE table for PRE/POST (or
	 * META) events. It is different from the
	 * {@link IAppDao#updateBatchListing(Date, EntityParams, Integer, Connection)} because of
	 * the basic behavior of PRE / POST jobs i.e. the classification of PRE /
	 * POST is such that it is categorized upon the PRIORITY_CODE_1 as set and
	 * also jobs with priority 2 has to wait on those jobs with priority code 1.
	 * Whereas for Policy, P2 do not have to wait on P1 to complete. Both can
	 * run in parallel.
	 * 
	 * @param batchRunDate
	 *            The batch run date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @param MAX_NO_OF_LISTENER
	 *            The maximum number of listeners to use
	 * @return the actual number of listeners to spawn
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Integer updatePrePostListing(int batchNo, Date batchRunDate,
			EntityParams entityParams, Integer MAX_NO_OF_LISTENER,
			Connection connection) throws DatabaseException;

	/**
	 * Updates the listener indicators in JOB_SCHEDULE table for BATCH events
	 * This method uniformly does the assignment for all batch jobs except for
	 * PRE and POST events or jobs. For PRE and POST assignment see
	 * {@link IAppDao#updatePrePostListing(Date, EntityParams, Integer, Connection)}
	 * <p />
	 * Working in brief -
	 * <p />
	 * <OL>
	 * <LI>Determines whether it is for 'ALL' or specific run
	 * <LI>If 'ALL', get the distinct set and assign for each item in the set
	 * depending upon the column lookup as specified
	 * <LI>If specific run, assign for each item in the set depending upon the
	 * column lookup as specified
	 * </OL>
	 * 
	 * @param batchRunDate
	 *            The batch run date
	 * @param entityParams
	 *            The entity parameters with the look up columns
	 * @param MAX_NO_OF_LISTENER
	 *            The maximum number of listeners to use
	 * @return the actual number of listeners to spawn
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Integer updateBatchListing(int batchNo, Date batchRunDate,
			EntityParams entityParams, Integer MAX_NO_OF_LISTENER,
			Connection connection) throws DatabaseException;

	/**
	 * Returns the error sequence for the batch
	 * 
	 * @return error sequence number as string
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public String getBatchErrorSequence(Connection connection)
			throws DatabaseException;

	/**
	 * Cleans the assignment done for previous batch (and not revision) run
	 * 
	 * @return true if the assignments have been cleaned successfully, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean cleanBatchAssignments(Connection connection)
			throws DatabaseException;

	/**
	 * Cleans the assignment done for previous batch (and not revision) run
	 * 
	 * @return true if the assignments have been cleaned successfully, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean cleanBatchAssignments(EntityParams entityParams, Connection connection)
			throws DatabaseException;
	
	/**
	 * Cleans the assignments done for previous revision
	 * 
	 * @return true if the assignments have been cleaned successfully, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean cleanRevisionAssignments(Connection connection)
			throws DatabaseException;
	
	/**
	 * Cleans the assignments done for previous revision
	 * 
	 * @return true if the assignments have been cleaned successfully, false
	 *         otherwise
	 * @throws DatabaseException
	 *             Any database I/O exception
	 */
	@Log
	public Boolean cleanRevisionAssignments(EntityParams entityParams, Connection connection)
			throws DatabaseException;

	/**
	 * Marks the batch object to UC.
	 * 
	 * This method marks the <code>batchObject</code> status to UC.
	 * 
	 * @param connection
	 * @param batchObject
	 * @return Boolean true if success.
	 * @throws DatabaseException
	 */
	@Log
	public Boolean markUC(Connection connection, BatchObject batchObject)
		throws DatabaseException;
	
	/**
	 * Sets the global parameters.
	 * This method will be invoked only if the configuration for {@link com.stgmastek.core.util.Constants#SET_GLOBAL_PARAMETERS}
	 * is set to true.
	 * 
	 * This method should manage the transaction.
	 * 
	 * @param connection Connection object on which the global parameters are to be set.
	 * @param batchContext
	 * @param batchObject
	 * @return Boolean
	 * @throws BatchException
	 */
	@Log
	public Boolean setGlobalParameters(Connection connection, BatchContext batchContext, BatchObject batchObject)
		throws BatchException;
}
