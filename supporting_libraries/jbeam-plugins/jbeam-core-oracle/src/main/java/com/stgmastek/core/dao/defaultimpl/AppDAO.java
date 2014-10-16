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
 */
package com.stgmastek.core.dao.defaultimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import stg.utils.Day;
import stg.utils.StringUtils;

import com.stgmastek.core.aspects.DatabaseAgnosticCandidate;
import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.common.BaseDAO;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchJobMetaData;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.CoreUtil;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.GroupInfo;
import com.stgmastek.core.util.QueryGenerator;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;
import com.stgmastek.core.util.time.JBeamTimeFactory;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all Application database related activities
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 * @see com.stgmastek.core.dao.IBaseDao
 * @see IAppDao
 */
public class AppDAO extends BaseDAO implements IAppDao {

	private static final Logger logger = Logger.getLogger(AppDAO.class);
	
	/** The query to set the configurable items into the JOB_SCHEDULE table */
	static final String SET_CONFIGURABLES = "insert into job_schedule (job_seq, job_detail, execution_date," +
			" job_status, system_activity_no, priority_code_1, priority_code_2, pre_post, " +
			"job_type, created_on, created_by, job_name) " +
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/** Get MAX job_seq from JOB_SCHEDULE: To be changed to a sequence value */
	@DatabaseAgnosticCandidate
	static final String GET_MAX_SEQUENCE_NO = "select max(to_number(job_seq)) from job_schedule";
	
	/** The query to update the JOB_SCHEDULE table with the execution status */
	static final String UPDATE_BATCH_JOB_STATUS = "update job_schedule set job_status = ?, date_executed  = ? where job_seq = ?";
	
	/** Checks the batch object status */
	static final String CHECK_BATCH_JOB_STATUS = "select job_status from job_schedule where job_seq = ?";
	
	/** Query to update the listing of PRE / POST jobs */
	static final String UPDATE_PRE_POST_LISTING = "update job_schedule set listener_indicator = ? where job_seq = ?";
	
	/** Cleans the previous batch assignment for those objects that have failed in the previous batch run. */
	static String CLEAN_ASSIGNMENT_FOR_BATCH = "update job_schedule set job_status = '"
		+ OBJECT_STATUS.PROCREATED.getID()+ "', listener_indicator = NULL "
			+ " where (job_status in ('" + OBJECT_STATUS.FAILED.getID()	+ "', '" 
				+ OBJECT_STATUS.UNDERCONSIDERATION.getID() + "', '"
					+ OBJECT_STATUS.SUSPENDED.getID() + "', '"
						+ OBJECT_STATUS.FAILEDOVER.getID() + "') " 
							+ " OR ( job_status in ('"
								+ OBJECT_STATUS.PROCREATED.getID() + "') AND listener_indicator is not null))";
	
	/** Cleans the current batch (with a new revision) assignments if any */
	static final String CLEAN_ASSIGNMENT_FOR_REVISION = "update job_schedule  set  listener_indicator = null where  listener_indicator is not null and job_status in ('" +
		OBJECT_STATUS.FAILED.getID() + "', '" +
			OBJECT_STATUS.UNDERCONSIDERATION.getID() + "', '" +
				OBJECT_STATUS.SUSPENDED.getID() + "', '" +
					OBJECT_STATUS.NC.getID() + "', '" +
						OBJECT_STATUS.FAILEDOVER.getID() + "', '" +
							OBJECT_STATUS.PROCREATED.getID() + "')";

	/** Query to update the statuses of PRE / POST batch jobs as needed depending upon whether it is a fresh batch or a revision run */
	static final String RESET_CONFIGURABLES = "update job_schedule set job_status = ? ";
	
	static final String RESET_CONFIGURABLES_REVISION_RUN = "update job_schedule set job_status = ? where pre_post = ? and job_status in (?)";
	
	/** Additional queries called from within the listeners. */
	public static final String MARK_UC = "UPDATE job_schedule SET  job_status = '" + OBJECT_STATUS.UNDERCONSIDERATION.getID() + "', date_executed = ? " +
			"WHERE  job_seq = ? and job_status in ('" +
			OBJECT_STATUS.NC.getID() + "', '" + OBJECT_STATUS.PROCREATED.getID() + "')";
	
	/** Procedure to set the global parameters */
	@DatabaseAgnosticCandidate
	public static final String SET_GLOBAL_PARAMETERS = "{call set_core_app_val_lis(?, ?, ?, ?, ?)}";
	
	/** Query to get the error sequence for the batch and or the revision */
	@DatabaseAgnosticCandidate	
	private final String GET_BATCH_ERROR_SEQUENCE = "select error_seq.NEXTVAL from dual";
	
	
	/**
	 * Public constructor takes the connection as argument
	 *  
	 */
	public AppDAO() {
		super();
	}
	
	/**
	 * Returns the list of assigned batch objects for an entity as identified by 
	 * EntityParams   
	 * 
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param entityParams
	 * 		  The entity parameters with the look up columns 
	 * @param groupInfo
	 * 		  The group for which the jobs are to be picked 
	 * @param listenerId
	 * 		  The listener identifier 
	 * @return The list of batch objects 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public List<BatchObject> getAssignedBatchObjects(Date batchRunDate, 
													EntityParams entityParams, 
													GroupInfo groupInfo, 
													Integer listenerId,
													Connection connection)
													throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BatchObject> returnList = null;
		Day day = new Day(batchRunDate.getTime());
		day.advance(Calendar.HOUR_OF_DAY, day.getMaximum(Calendar.HOUR_OF_DAY) - day.getHour());
		day.advance(Calendar.MINUTE, day.getMaximum(Calendar.MINUTE) - day.getMinutes());
		day.advance(Calendar.SECOND, day.getMaximum(Calendar.SECOND) - day.getSeconds());
		day.advance(Calendar.MILLISECOND, day.getMaximum(Calendar.MILLISECOND) - day.getMilliseconds());
		
		//Fetch the query from the QueryGenerator 
		String query = QueryGenerator.getGenerator().getListenerAssignedJob(entityParams);
		try {			
			pstmt = connection.prepareStatement(query);
			short counter = 0;
			
			//In case there are multiple of column lookups -  
			//Iterate over the column lookups and set the values
			for(int i=0; i<entityParams.getLookupColumns().size(); i++){
				pstmt.setObject(++counter, listenerId);
				pstmt.setTimestamp(++counter, day.getTimestamp());
				for (String str : groupInfo.getEntityValues()) {
					pstmt.setString(++counter, str);			
				}
			}
			
			//Execute the query 
			rs = pstmt.executeQuery();
			returnList = ResultSetMapper.getInstance().mapMultipleRecords(rs, BatchObject.class);
		} catch (Exception sqe) {
			System.err.println(query);
			throw new DatabaseException(sqe);
		}finally{
			releaseResources(rs, pstmt, null);
		}
		return returnList;
	}
	
	/** 
	 * Resets the configurable items depending upon whether it is a revision run or not. 
     * If revision run then, the update would be fired that would mark '99' record(s) to 'PC'   
	 * and in case of fresh batch would mark all '99' and 'PC' as 'TR' (TeRminated)
	 * 
	 * @param isRevisionRun
	 * 		  Whether it is a revision run or a fresh batch run  
	 * @param entityParams
	 * 		  The entity parameters with the look up columns 
	 * @return true if the resetting of the configurable items was achieved successfully 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Boolean resetConfigurables(Boolean isRevisionRun, Boolean isDateRun, 
										EntityParams entityParams,
										Connection connection) 
										throws DatabaseException{
		PreparedStatement pstmt = null;
		try{
			if(isRevisionRun){
				pstmt = connection.prepareStatement(RESET_CONFIGURABLES_REVISION_RUN);
				pstmt.setString(1, OBJECT_STATUS.PROCREATED.getID());
				pstmt.setString(2, entityParams.getEntity());
				pstmt.setString(3, OBJECT_STATUS.FAILED.getID());
				pstmt.executeUpdate();
			} else {
				StringBuilder sb = new StringBuilder(RESET_CONFIGURABLES);
				if(isDateRun){
					sb.append(" WHERE pre_post in (");
					for (Constants.META_EVENTS event : Constants.META_EVENTS.values()) {
						sb.append(" '");
						sb.append(event.name());
						sb.append("',");
					}
					sb.deleteCharAt(sb.length()-1);
					sb.append(" ) and job_status in (?, ?) ");
				} else {
					sb.append(" WHERE pre_post = '");
					sb.append(entityParams.getEntity());
					sb.append("'");
					List<GroupInfo> list = entityParams.getValues();
					if(list != null && list.size() > 0 && !list.get(0).getEntityValue().equals("ALL")){
						sb.append(" and PRIORITY_CODE_1 = " + list.get(0).getEntityValue());
					}
					sb.append(" and job_status in (?, ?) ");
				}
				pstmt = connection.prepareStatement(sb.toString());
				pstmt.setString(1, OBJECT_STATUS.TR.getID());
				pstmt.setString(2, OBJECT_STATUS.FAILED.getID());
				pstmt.setString(3, OBJECT_STATUS.PROCREATED.getID());
				pstmt.executeUpdate();
			}
			return true;
		}catch(SQLException sqe){
			throw new DatabaseException(sqe);
		}finally{
			releaseResources(null, pstmt, null);
		}
	}
	
	
	/**
	 * Sets the configurable prior to execution or the start of the batch proceedings
	 * 
	 * @param configurables
	 * 		  The list of configurable jobs to set 
	 * @return 0 if all configurable jobs have been pro-created as expected
	 * 		   1 otherwise 
	 * @throws DatabaseException
	 * 		   Exception thrown during insertion of the records 
	 */
	@Log
	public Integer setConfigurables(List<BatchJobMetaData> configurables,
			Connection connection, BatchContext batchContext) 
												throws DatabaseException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{					
			pstmt = connection.prepareStatement(SET_CONFIGURABLES);
			for(BatchJobMetaData configurable : configurables){
				String seqNo = String.valueOf(getBatchSequenceNo(connection));
				Date now = JBeamTimeFactory.getInstance().getCurrentTimestamp(connection);
				pstmt.setString(1, String.valueOf(seqNo));
				pstmt.setString(2, configurable.getTaskName());
				pstmt.setTimestamp(3, new Timestamp(batchContext.getBatchInfo().getBatchRunDate().getTime()));
				pstmt.setString(4, OBJECT_STATUS.PROCREATED.getID());
				pstmt.setString(5, String.valueOf(seqNo));
				pstmt.setObject(6, configurable.getPriorityCode1());
				pstmt.setObject(7, configurable.getPriorityCode2());
				pstmt.setString(8, configurable.getPrePost());
				pstmt.setString(9, configurable.getJobType());
//				pstmt.setString(10, configurable.getLine());
//				pstmt.setString(11, configurable.getSubline());
				pstmt.setTimestamp(10, new java.sql.Timestamp(now.getTime()));
				pstmt.setString(11, batchContext.getBatchInfo().getStartUser());
//				pstmt.setString(12, configurable.getJobDesc());
				pstmt.setString(12, configurable.getObjectName());
				pstmt.addBatch();				
			}
			pstmt.executeBatch();
			return 0;
		}catch(Exception e){
			throw new DatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}			
	}
	
	/**
	 * Sets the execution status for the execution of a batch job
	 * 
	 * @param batchObject
	 * 		  The batch object for which the status has to be set 
	 * @return 0 if updates was successfully 
	 * 		   1 otherwise 
	 * @throws DatabaseException
	 * 		   Exception thrown during insertion of the records 
	 */
	@Log
	public Integer setBatchObjectStatus(BatchObject batchObject,
			Connection connection) 
												throws DatabaseException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(UPDATE_BATCH_JOB_STATUS);
			pstmt.setString(1, batchObject.getStatus().getID());
			pstmt.setTimestamp(2, new java.sql.Timestamp(batchObject.getDateExecuted().getTime()));
			pstmt.setString(3, batchObject.getSequence());
			return pstmt.executeUpdate();
		}catch(Exception e){
			logger.error("Error updating batchObject : " + batchObject);
			throw new DatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}			
	}

	/**
	 * Checks whether the batch object has marked its status 
	 * 
	 * @param batchObject
	 * 		  The batch object for which the status has to be set
	 * @return the current status as string  
	 * @throws DatabaseException
	 * 		   Exception thrown during insertion of the records 
	 */
	@Log
	public String checkBatchObjectStatus(BatchObject batchObject,
			Connection connection) 
												throws DatabaseException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{					
			pstmt = connection.prepareStatement(CHECK_BATCH_JOB_STATUS);
			pstmt.setString(1, batchObject.getSequence());
			rs = pstmt.executeQuery();
			rs.next();
			return rs.getString(1);//Always there would be one record with one column 
		}catch(Exception e){
			throw new DatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}			
	}
	
	/**
	 * Returns whether there are more jobs of the 'same type' identified by EntityParams 
	 * 
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param entityParams
	 * 		  The entity parameters with the look up columns
	 * @return true if there are more jobs, false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Boolean moreJobsToExecute(Date batchRunDate, EntityParams entityParams,
			Connection connection) 
													throws DatabaseException{
		
		//Get the values from the entity parameters 
		List<GroupInfo> values = entityParams.getValues();
		
		//Check for 'ALL' conditions
		Boolean isAll = false;
		if(values.get(0).getEntityValue().equals("ALL"))
			isAll = true;		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean returnIndicator = false;
		Day day = new Day(batchRunDate.getTime());
		day.advance(Calendar.HOUR_OF_DAY, day.getMaximum(Calendar.HOUR_OF_DAY) - day.getHour());
		day.advance(Calendar.MINUTE, day.getMaximum(Calendar.MINUTE) - day.getMinutes());
		day.advance(Calendar.SECOND, day.getMaximum(Calendar.SECOND) - day.getSeconds());
		day.advance(Calendar.MILLISECOND, day.getMaximum(Calendar.MILLISECOND) - day.getMilliseconds());

		//Get the query QueryGenerator
		String query = QueryGenerator.getGenerator().moreJobsExist(entityParams, !isAll);		
		try{					
			pstmt = connection.prepareStatement(query);
			
			//If condition is not 'ALL' and is a specific run
			//The case also includes for those checks for procreated jobs 
			if(!isAll){
				//Special iterative check in case there are any special runs on more than one entity 
				//of the same type. EX: Batch could be run for POLICY P1 and P2, which might
				//pro-create other objects for the same P1 and or P2
				for(GroupInfo gi : values){								
					int counter = 0;
					if (gi.getParameterCount().intValue() != entityParams.getNumberOfRequiredParameters().intValue()) {
						StringBuilder sb = new StringBuilder();
						sb.append("Number of required parameters are #");
						sb.append(entityParams.getNumberOfRequiredParameters());
						sb.append(" for entity [");
						sb.append(entityParams.getEntity());
						sb.append("] where as the number of parameters passed are #");
						sb.append(gi.getParameterCount());
						sb.append(" derived from parameter value [");
						sb.append(gi.getEntityValue());
						sb.append("]");
						throw new DatabaseException(sb.toString());
					}
					//In case there are multiple of column lookups -  
					//Iterate over the column lookups and set the values					
					for(int i=0; i<entityParams.getLookupColumns().size(); i++){
						pstmt.setTimestamp(++counter, day.getTimestamp());
						for (String str : gi.getEntityValues()) {
							pstmt.setString(++counter, str);	
						}
					}
					rs = pstmt.executeQuery();
					if(rs.next()){
						returnIndicator = true;
						break;
					}
				}
			}else{ //If 'ALL'
				int counter = 0;
				
				//In case there are multiple of column lookups -  
				//Iterate over the column lookups and set the values				
				for(int i=0; i<entityParams.getLookupColumns().size(); i++){
					pstmt.setTimestamp(++counter, day.getTimestamp());
				}				
				rs = pstmt.executeQuery();
				
				//If records exists then return true 
				if(rs.next()){
					returnIndicator = true;
				}
			}
			return returnIndicator;
		}catch(Exception e){
			System.err.println(query);
			throw new DatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}					
	}

	/**
	 * Returns distinct set of values for the supplied criteria 
	 * 
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param entityParams
	 * 		  The entity parameters with the look up columns
	 * @return list of distinct group informations  
	 * @throws DatabaseException
	 * 		   Any database I/O exception thrown 
	 */
	@Log
	public List<GroupInfo> getDistinctSet(Date batchRunDate, EntityParams entityParams, Connection connection) 
																throws DatabaseException {
		return getDistinctSet(batchRunDate, entityParams, null, connection);
	}
	
	/**
	 * Returns distinct set of values for the supplied criteria 
	 * 
	 * @param batchRunDate
	 * 		  The batch date 
	 * @param entityParams
	 * 		  The entity parameters with the look up columns
	 * @param listenerId
	 * 		  The listener identifier
	 * 		  The listener identifier is needed, as when the listener is 
	 * 		  executing it needs to get the distinct set for itself for the reason
	 * 		  of marking all other objects for the same category as 'SP' in case of
	 * 		  an '99'. <p />
	 * 		  EX: For a Policy say there is a distinct set of P1, P2 ... P10. 
	 * 		  There are MAX 5 listeners alloted, so each listener would have 2 distinct 
	 * 		  policies each to execute. If the listener identifier is provided then 
	 * 		  the distinct set would be these two values. If listener identifier is null
	 * 		  then the entire set i.e. 10 distinct policies would be returned.     
	 *    	
	 * @return list of distinct group informations 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public List<GroupInfo> getDistinctSet(
							Date batchRunDate, EntityParams entityParams, 
							Integer listenerId,
							Connection connection) throws DatabaseException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GroupInfo> groups = new ArrayList<GroupInfo>();
		String query = null;
		Day day = new Day(batchRunDate.getTime());
		day.advance(Calendar.HOUR_OF_DAY, day.getMaximum(Calendar.HOUR_OF_DAY) - day.getHour());
		day.advance(Calendar.MINUTE, day.getMaximum(Calendar.MINUTE) - day.getMinutes());
		day.advance(Calendar.SECOND, day.getMaximum(Calendar.SECOND) - day.getSeconds());
		day.advance(Calendar.MILLISECOND, day.getMaximum(Calendar.MILLISECOND) - day.getMilliseconds());

		try {
			
			//Depending upon the listener identifier passed, generate the query 
			if(listenerId != null)
				query = QueryGenerator.getGenerator().getDistinctSet(entityParams, true);
			else
				query = QueryGenerator.getGenerator().getDistinctSet(entityParams, false);

			pstmt = connection.prepareStatement(query);
			short counter = 0;		
			
			//In case there are multiple of column lookups -  
			//Iterate over the column lookups and set the values
			for(int i=0; i<entityParams.getLookupColumns().size(); i++){
				if(listenerId != null)
					pstmt.setObject(++counter, listenerId);			
				pstmt.setTimestamp(++counter, day.getTimestamp());
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				StringBuilder sb = new StringBuilder();
				for (int i=1; i <= entityParams.getNumberOfRequiredParameters(); i++) {
					sb.append(StringUtils.join(new String[] {rs.getString(i)}, Constants.DELIMITER_CHAR, Constants.ESCAPE_CHAR));
					sb.append(Constants.DELIMITER_CHAR);
				}
				sb.deleteCharAt(sb.length()-1);
				GroupInfo info = new GroupInfo(sb.toString());
				groups.add(info);
			}
			return groups;
		} catch (Exception e) {
			System.err.println(query);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Updates the listener indicators in JOB_SCHEDULE table for 
	 * PRE/POST (or META) events. 
	 * It is different from the {@link AppDAO#updateBatchListing(Date, EntityParams, Integer, Connection)} 
	 * because of the basic behavior of PRE / POST jobs i.e. the classification of PRE / POST 
	 * is such that it is categorized upon the PRIORITY_CODE_1 as set and also jobs with 
	 * priority 2 has to wait on those jobs with priority code 1. Whereas for Policy, P2 do 
	 * not have to wait on P1 to complete. Both can run in parallel.    
	 * 
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param isDateRun
	 * 			  Whether it is a DATE run or a SPECIAL run
	 * @param entityParams
	 * 		  The entity parameters with the look up columns
	 * @param MAX_NO_OF_LISTENER
	 * 		  The maximum number of listeners to use 
	 * @return the actual number of listeners to spawn 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Integer updatePrePostListing(int batchNo, Date batchRunDate, 
					EntityParams entityParams, 
					Integer MAX_NO_OF_LISTENER,
					Connection connection) throws DatabaseException {
				
		PreparedStatement pstmt = null;
		PreparedStatement pstmtUpdate = null;
		ResultSet rs = null;		
		Integer noOfListenersToSpawn = 0;
		String queryS = null;
		String queryU = null;
		Day day = new Day(batchRunDate.getTime());
		day.advance(Calendar.HOUR_OF_DAY, day.getMaximum(Calendar.HOUR_OF_DAY) - day.getHour());
		day.advance(Calendar.MINUTE, day.getMaximum(Calendar.MINUTE) - day.getMinutes());
		day.advance(Calendar.SECOND, day.getMaximum(Calendar.SECOND) - day.getSeconds());
		day.advance(Calendar.MILLISECOND, day.getMaximum(Calendar.MILLISECOND) - day.getMilliseconds());

		try {			
			
			//First get the record count for the supplied priority code 1
			queryS = QueryGenerator.getGenerator().getUpdateCount(entityParams);
			
			//Static query for updating 
			queryU = UPDATE_PRE_POST_LISTING;
			
			pstmt = connection.prepareStatement(queryS);			
			pstmtUpdate = connection.prepareStatement(queryU);
			
			//Set the parameters for the select query 
			pstmt.setTimestamp(1, day.getTimestamp());
			
			Integer priorityCode1 = Integer.valueOf(entityParams.getValues().get(0).getEntityValue());
			pstmt.setObject(2, priorityCode1);
			rs = pstmt.executeQuery();
			Integer listenerCount = 0;
			Integer listenerNumber = CoreUtil.getListenerNo(batchNo, 0);
			
			
			//For each record assign listener iteratively 
			while(rs.next()){			
				String sequence = rs.getString(1);
				pstmtUpdate.setObject(1, ++listenerNumber);
				pstmtUpdate.setString(2, sequence);
				pstmtUpdate.addBatch();
				++listenerCount;
				
				if(listenerCount >= MAX_NO_OF_LISTENER){
					listenerCount = 0;
					listenerNumber = CoreUtil.getListenerNo(batchNo, 0);
					noOfListenersToSpawn = MAX_NO_OF_LISTENER;
				}
			}
			
			//Commit the changes 
			pstmtUpdate.executeBatch();
			
			//Determine the actual number of listeners to spawn 
			if(noOfListenersToSpawn < MAX_NO_OF_LISTENER)
				noOfListenersToSpawn = listenerCount;			
			
			return noOfListenersToSpawn;
		} catch (Exception e) {
			System.err.println(queryS);
			System.err.println(queryU);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
			releaseResources(null, pstmtUpdate, null);
		}
	}
	
	/**
	 * Updates the listener indicators in JOB_SCHEDULE table for 
	 * BATCH events
	 * This method uniformly does the assignment for all batch jobs except 
	 * for PRE and POST events or jobs. For PRE and POST assignment 
	 * see {@link AppDAO#updatePrePostListing(Date, EntityParams, Integer, Connection)} <p />
	 * Working in brief - <p />
	 * <OL>
	 * 	<LI> Determines whether it is for 'ALL' or specific run
	 *  <LI> If 'ALL', get the distinct set and assign for each item in the set
	 *  	 depending upon the column lookup as specified 
	 *  <LI> If specific run, assign for each item in the set depending upon 
	 *  	 the column lookup as specified
	 * </OL>
	 * 
	 * @param batchRunDate
	 * 		  The batch run date 
	 * @param entityParams
	 * 		  The entity parameters with the look up columns
	 * @param MAX_NO_OF_LISTENER
	 * 		  The maximum number of listeners to use
	 * @return the actual number of listeners to spawn 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Integer updateBatchListing(int batchNo, Date batchRunDate, 
					EntityParams entityParams, 
					Integer MAX_NO_OF_LISTENER,
					Connection connection) throws DatabaseException {

		
		//Determine whether the assignment is for 'ALL' or specific run 
		//EX in the method is -
		//Consider Policy assignment for P1, P2 ... P10 (for both cases 'ALL' or specific)
		//Policy has two column lookup i.e. POLICY_NO 
		//and REFERENCE_ID (with BE_CONTEXT = 'P') 
		List<GroupInfo> values = entityParams.getValues();		
		Boolean isAll = false;
		if(values.get(0).getEntityValue().equals("ALL"))
			isAll = true;		
		
		Day day = new Day(batchRunDate.getTime());
		day.advance(Calendar.HOUR_OF_DAY, day.getMaximum(Calendar.HOUR_OF_DAY) - day.getHour());
		day.advance(Calendar.MINUTE, day.getMaximum(Calendar.MINUTE) - day.getMinutes());
		day.advance(Calendar.SECOND, day.getMaximum(Calendar.SECOND) - day.getSeconds());
		day.advance(Calendar.MILLISECOND, day.getMaximum(Calendar.MILLISECOND) - day.getMilliseconds());

		PreparedStatement pstmt = null;
		List<PreparedStatement> pstmtUpdates = new ArrayList<PreparedStatement>();
		ResultSet rs = null;		
		Integer noOfListenersToSpawn = 0;
		String distinctQuery = null;
		List<String> updateQueries = null;
		Integer listenerCount = 0;
		Integer listenerNumber = 0;
		Integer startingListenerNumber = 0;
		int counter = 0;
		try {			
			
			//Turn off the connection.autoCommit		
			connection.setAutoCommit(false);	
			
			//If for all condition then 
			//Get the distinct set
			//And then iterate over each to to update
			if(isAll){
				
				//Generate the query to fetch the distinct set
				//Query to select P1, P2 ... P10
				distinctQuery = QueryGenerator.getGenerator().getDistinctSet(entityParams, false);
				
				//Generate the query for the update 
				//If the entity has a single column lookup then the size of the queries is 1
				//and the size of query set is directly proportional to number of column 
				//lookup with a factor of 1 
				//EX: The size of the queries list is 2, one each for POLICY_NO 
				//and REFERENCE_ID
				updateQueries = QueryGenerator.getGenerator().getUpdateListing(entityParams);
				java.sql.Timestamp sqlBatchRunDate = day.getTimestamp();
				
				//Create the select statement  
				pstmt = connection.prepareStatement(distinctQuery);
				
				//Create the update statement(s) depending upon 
				//the number of column lookups
				//EX: The size of pstmtUpdates would be two as it is a two column 
				//lookup entity  
				for(int i=0; i<updateQueries.size(); i++){
					pstmtUpdates.add(connection.prepareStatement(updateQueries.get(i)));
				}
				
				//Set the parameters for select query depending upon the column lookups 
				for(int i=0; i<entityParams.getLookupColumns().size(); i++){					
					pstmt.setTimestamp(++counter, sqlBatchRunDate);
				}
				//Execute the query 
				rs = pstmt.executeQuery();
				rs.setFetchSize(1000);
				ResultSetMetaData rsmd = rs.getMetaData();
				
				listenerNumber = startingListenerNumber = CoreUtil.getListenerNo(batchNo, listenerCount);
				//For each select value 
				while(rs.next()){
					++listenerCount;
					++listenerNumber;
					
					//Set the parameters for each of the update queries in the list
					//EX: Both the queries would have the same parameter indexes
					//It is done purely to avoid OR condition 
					for(int i=0; i<pstmtUpdates.size(); i++){
						pstmtUpdates.get(i).setObject(1, Integer.valueOf(listenerNumber));
						pstmtUpdates.get(i).setTimestamp(2, sqlBatchRunDate);
						for (int j=1; j<=rsmd.getColumnCount(); j++) { //split of the object is not necessary as the object is taken from the result set.
							pstmtUpdates.get(i).setObject(j+2, rs.getObject(j));
						}
						pstmtUpdates.get(i).addBatch();
					}					
					
					if(listenerCount >= MAX_NO_OF_LISTENER.intValue()){
						listenerCount = 0;
						listenerNumber = startingListenerNumber;
						noOfListenersToSpawn = MAX_NO_OF_LISTENER.intValue();
					}
				}		
				
				//Iteratively execute the update statements 
				for(int i=0; i<pstmtUpdates.size(); i++){
					pstmtUpdates.get(i).executeBatch();
				}
			
			}else{
				
				//If not for all
				//Then assign only for the ones specified in the list of values
				//Special case for specific runs of batches
				//In this case there is no need to fetch the distinct set 
				//as it is a special run, the values would be provided in the entity 
				//parameters 
				updateQueries = QueryGenerator.getGenerator().getUpdateListing(entityParams);
				java.sql.Timestamp sqlBatchRunDate = day.getTimestamp();
				
				//Create the update statement(s) depending upon 
				//the number of column lookups
				//EX: The size of pstmtUpdates would be two as it is a two column 
				//lookup entity  
				for(int i=0; i<updateQueries.size(); i++){
					pstmtUpdates.add(connection.prepareStatement(updateQueries.get(i)));
				}				
				listenerCount = 0;
				listenerNumber = startingListenerNumber = CoreUtil.getListenerNo(batchNo, listenerCount);
				for(GroupInfo gi : values){
					++listenerCount;
					++listenerNumber;
					
					//Set the parameters for each of the update queries in the list
					//EX: Both the queries would have the same parameter indexes
					//It is done purely to avoid OR condition
					for(int i=0; i<pstmtUpdates.size(); i++){
						int iStmtIndex = 0;
						pstmtUpdates.get(i).setObject(++iStmtIndex, Integer.valueOf(listenerNumber));
						pstmtUpdates.get(i).setTimestamp(++iStmtIndex, sqlBatchRunDate);
						for (String str : gi.getEntityValues()) {
							pstmtUpdates.get(i).setString(++iStmtIndex, str);
						}
						pstmtUpdates.get(i).addBatch();
					}						
				
					if(listenerCount >= MAX_NO_OF_LISTENER.intValue()){
						listenerCount = 0;
						listenerNumber = startingListenerNumber;
						noOfListenersToSpawn = MAX_NO_OF_LISTENER.intValue();
					}
				}		
				
				//Iteratively execute the update statements 				
				for(int i=0; i<pstmtUpdates.size(); i++){
					pstmtUpdates.get(i).executeBatch();
				}
				
			}
			
			//Commit all updates		
			connection.commit();
			
			//Determine the number of listeners to spawn 
			if(noOfListenersToSpawn < MAX_NO_OF_LISTENER.intValue())
				noOfListenersToSpawn = listenerCount;
			
			return noOfListenersToSpawn;
		} catch (Exception e) {
			System.err.println(distinctQuery);
			System.err.println(updateQueries);
			try {
				connection.rollback();
			} catch (SQLException e2) {
			}
			throw new DatabaseException(e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e2) {
			}
			releaseResources(rs, pstmt, null);
			for(int i=0; i<pstmtUpdates.size(); i++){
				releaseResources(null, pstmtUpdates.get(i), null);
			}			
		}
	}	
	
	/**
	 * Returns the error sequence for the batch 
	 * 
	 * @return error sequence number as string 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public String getBatchErrorSequence(Connection connection) throws DatabaseException {
		
		PreparedStatement pstmtErrorSequence = null;
		ResultSet rs = null;
		try {
			//Get the error sequence and set it
			pstmtErrorSequence = connection.prepareStatement(GET_BATCH_ERROR_SEQUENCE);
			rs = pstmtErrorSequence.executeQuery();
			rs.next();
			String errorSequence = rs.getString(1);//Always one column with one record returned
			return errorSequence;
			
		} catch (Exception e) {
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmtErrorSequence, null);
		}
	}	
	
	/**
	 * Cleans the assignment done for previous batch (and not revision) run  
	 * 
	 * @return true if the assignments have been cleaned successfully,
	 * 		   false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception
	 */
	@Log
	public Boolean cleanBatchAssignments(Connection connection) throws DatabaseException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = CLEAN_ASSIGNMENT_FOR_BATCH;
		if(logger.isDebugEnabled()) {
			logger.debug("In AppDAO >> Query[CLEAN_ASSIGNMENT_FOR_BATCH] = " + query);
		}
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("In AppDAO >> " + e);
//			System.err.println(query);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}		
	} 	
	
	/**
	 * Cleans the assignment done for previous batch (and not revision) run  
	 * 
	 * @return true if the assignments have been cleaned successfully,
	 * 		   false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception
	 */
	@Log
	public Boolean cleanBatchAssignments(EntityParams entityParams, Connection connection) throws DatabaseException{

		return cleanAssignments(CLEAN_ASSIGNMENT_FOR_BATCH, entityParams,
				connection);
	} 	

	/**
	 * Cleans the assignments done for previous revision 
	 *  
	 * @return true if the assignments have been cleaned successfully,
	 * 		   false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Boolean cleanRevisionAssignments(Connection connection) throws DatabaseException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = CLEAN_ASSIGNMENT_FOR_REVISION;
		try {
			pstmt = connection.prepareStatement(query);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(query);
			throw new DatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}		
	}

	/**
	 * Cleans the assignments done for previous revision 
	 *  
	 * @return true if the assignments have been cleaned successfully,
	 * 		   false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception 
	 */
	@Log
	public Boolean cleanRevisionAssignments(EntityParams entityParams, Connection connection) throws DatabaseException{
		
		return cleanAssignments(CLEAN_ASSIGNMENT_FOR_REVISION, entityParams,
				connection);
	}

	
	/**
	 * Cleans the assignment done for previous batch (and not revision) run  
	 * 
	 * @return true if the assignments have been cleaned successfully,
	 * 		   false otherwise 
	 * @throws DatabaseException
	 * 		   Any database I/O exception
	 */
	@Log
	private Boolean cleanAssignments(String baseQuery, EntityParams entityParams, Connection connection) throws DatabaseException{

		
		//Get the values from the entity parameters 
		List<GroupInfo> values = entityParams.getValues();
		
		PreparedStatement pstmt = null;

		//Get the query QueryGenerator
		String query = QueryGenerator.getGenerator().cleanAssignments(baseQuery, entityParams);
		if(logger.isDebugEnabled()) {
			logger.debug("In AppDAO >> Query[CLEAN_ASSIGNMENT] = " + query);
		}
		try{					
			pstmt = connection.prepareStatement(query);
			
			//If condition is not 'ALL' and is a specific run
			//The case also includes for those checks for procreated jobs 

			//Special iterative check in case there are any special runs on more than one entity 
			//of the same type. EX: Batch could be run for POLICY P1 and P2, which might
			//pro-create other objects for the same P1 and or P2
			for(GroupInfo gi : values){								
				int counter = 0;
				if (gi.getParameterCount().intValue() != entityParams.getNumberOfRequiredParameters().intValue()) {
					StringBuilder sb = new StringBuilder();
					sb.append("Number of required parameters are #");
					sb.append(entityParams.getNumberOfRequiredParameters());
					sb.append(" for entity [");
					sb.append(entityParams.getEntity());
					sb.append("] where as the number of parameters passed are #");
					sb.append(gi.getParameterCount());
					sb.append(" derived from parameter value [");
					sb.append(gi.getEntityValue());
					sb.append("]");
					throw new DatabaseException(sb.toString());
				}
				//In case there are multiple of column lookups -  
				//Iterate over the column lookups and set the values					
				for(int i=0; i<entityParams.getLookupColumns().size(); i++){
					for (String str : gi.getEntityValues()) {
						pstmt.setString(++counter, str);	
					}
				}
			}
			
			int i = pstmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			logger.error("In AppDAO >> " + e);
			throw new DatabaseException(e);
		} catch (Exception e) {
			logger.error(query);
			throw new DatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	} 	

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IAppDao#markUC(com.stgmastek.core.common.Connection, java.lang.Long)
	 */
	
	public Boolean markUC(Connection connection, BatchObject batchObject)
			throws DatabaseException {
		PreparedStatement pstmt = null;
		try {
			connection.setAutoCommit(true);
			pstmt = connection.prepareStatement(AppDAO.MARK_UC);
			pstmt.setObject(1, JBeamTimeFactory.getInstance().getCurrentTimestamp(connection));
			pstmt.setObject(2, batchObject.getSequence());
			int rows = pstmt.executeUpdate();
			pstmt.close();
			batchObject.setStatus(OBJECT_STATUS.UNDERCONSIDERATION);
			return (rows == 1);
		} catch (SQLException e1) {
			throw new DatabaseException("Unable to mark object as "
					+ AppDAO.MARK_UC, e1);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.core.dao.IAppDao#setGlobalParameters(com.stgmastek.core.util.BatchContext, com.stgmastek.core.util.BatchObject)
	 */
	
	public Boolean setGlobalParameters(Connection connection, BatchContext batchContext,
			BatchObject batchObject) throws BatchException {
		CallableStatement cstmt = null;
		try {
			cstmt = connection.prepareCall(AppDAO.SET_GLOBAL_PARAMETERS);
			String policyNo = batchObject.getPolicyNo();
			String policyRenewNo = batchObject.getPolicyRenewNo();
			if(policyNo == null){
				if("P".equals(batchObject.getContext())){
					policyNo = batchObject.getReferenceId();
					policyRenewNo = batchObject.getSubreferenceId();
				}
			} 								
			cstmt.setString(1, policyNo);
			cstmt.setString(2, policyRenewNo);
			cstmt.setString(3, batchObject.getSequence());
			cstmt.setString(4, batchContext.getBatchInfo().getBatchErrorSequence());
			cstmt.setTimestamp(5, new Timestamp(batchContext.getBatchInfo().getBatchRunDate().getTime()));
			return cstmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		} finally {
			releaseResources(null, cstmt, null);
		}
	}

	/** 
	 * The default implementation fires the query
	 * <code>select max(to_number(job_seq)) + 1 from job_schedule</code>.
	 * In case the default generation is not to be used then override the default implementation
	 * and set the system property {@link com.stgmastek.core.util.Constants.SYSTEM_KEY#APP_DAO}.
	 * 
	 * @see com.stgmastek.core.dao.IAppDao#getBatchSequenceNo(java.sql.Connection)
	 */
	public Long getBatchSequenceNo(Connection connection)
			throws DatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{					
			long l = 0;
			pstmt = connection.prepareStatement(GET_MAX_SEQUENCE_NO);
			rs = pstmt.executeQuery();
			if(rs.next())
				l = rs.getInt(1);
			else
				l = 0;
			return Long.valueOf(++l);
		} catch (SQLException e) {
			throw new DatabaseException("SQLException caught while generating batch sequence number", e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/dao/defaultimpl/AppDAO.java                                                                              $
 * 
 * 15    6/30/10 4:55p Kedarr
 * Changed the method signature of the mark UC method to accept the batch Object.
 * 
 * 14    6/30/10 2:38p Kedarr
 * Modified to set the auto commit to false in the finally and do a rollback in the exception block.
 * 
 * 13    5/04/10 4:34p Mandar.vaidya
 * Removed the bug in set configurables for generating the sequence number.
 * 
 * 12    5/04/10 2:12p Kedarr
 * Changed the method to pro-create to make use of the new api that was exposed to generate the batch sequence number,
 * 
 * 11    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 10    4/19/10 3:47p Mandar.vaidya
 * Rectified the bug, added one input parameter
 * 
 * 9     4/19/10 3:41p Mandar.vaidya
 * Rectified the bug, reversed the if condition for reset configurables.
 * 
 * 8     4/19/10 3:28p Kedarr
 * Reset configurables altered for reseting for all meta events.
 * 
 * 7     4/19/10 12:40p Mandar.vaidya
 * Changes made for re setting the configurables.
 * 
 * 6     4/06/10 5:08p Kedarr
 * corrected java doc for missing tags or improper tags due to change in method signatures.
 * 
 * 5     4/06/10 3:13p Mandar.vaidya
 * Added new parameter executionDate to setConfigurables method.
 * 
 * 4     3/19/10 2:24p Kedarr
 * Added all other statuses that were missing during the update.
 * 
 * 3     3/17/10 2:27p Kedarr
 * Changes made to pass the batch run date as a timestamp instead of a String.
 * 
 * 2     3/09/10 1:55p Kedarr
 * Changes made to not close the connection object in the finally blocks. Now it is the responsibility of the caller to close the connection.
 * 
 * 1     3/08/10 10:09a Kedarr
 * 
 * 15    3/08/10 10:09a Kedarr
 * 
 * 14    3/03/10 5:19p Grahesh
 * Removed the Connection object from the constructor and added in the individual methods
 * 
 * 13    3/03/10 3:19p Grahesh
 * Changes made to execute update statement and return the value as returned by the update statement.
 * 
 * 12    1/12/10 11:53a Grahesh
 * Put a transaction for those methods that have multiple updates / insert in the method with correction.
 * 
 * 11    1/11/10 5:03p Grahesh
 * Put a transaction for those methods that have multiple updates / insert in the method
 * 
 * 10    1/11/10 4:54p Grahesh
 * Marked public those queries that were called from the listener
 * 
 * 9     1/11/10 4:53p Grahesh
 * Changes the variables to static final instead of private final
 * 
 * 8     1/04/10 4:38p Grahesh
 * Corrected the query for cleaning up during the initialization.
 * 
 * 7     12/29/09 12:43p Grahesh
 * Added annotation for logging to all methods.
 * 
 * 6     12/23/09 3:23p Grahesh
 * Removed be_on_fail_exit from batch_executor
 * 
 * 5     12/23/09 11:54a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 4     12/21/09 3:37p Grahesh
 * Added condition to include 'NC' objects
 * 
 * 3     12/17/09 12:31p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/