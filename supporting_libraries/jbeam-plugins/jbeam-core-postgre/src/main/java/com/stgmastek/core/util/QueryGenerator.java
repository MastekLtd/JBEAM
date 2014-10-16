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
package com.stgmastek.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import com.stgmastek.core.aspects.DatabaseAgnosticCandidate;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;

/**
 * Query Generator class to generate queries as needed during the 
 * execution of the batch 
 * 
 * Implements the singleton pattern 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class QueryGenerator {

	/** Static instance of the class */
	private static QueryGenerator generator = new QueryGenerator();
	
	private static final transient Logger logger = Logger.getLogger(QueryGenerator.class); 
	
	/**
	 * Private constructor to avoid outside instantiation 
	 */
	private QueryGenerator(){}
	
	/**
	 * Public static method to get the instance of the class
	 * 
	 * @return the cached or static instance of the QueryGenerator class 
	 */
	public static QueryGenerator getGenerator(){
		return generator;
	}
	
	/**
	 * Method to test the different queries 
	 * 
	 * @param args
	 * 		  Null
	 */
	public static void main(String[] args) {
        logger.getLoggerRepository().getRootLogger().addAppender(
                new ConsoleAppender(new SimpleLayout()));
		QueryGenerator generator = QueryGenerator.getGenerator();
		EntityParams entityParams = new EntityParams("POLICY");
		List<ColumnLookup> colLookups = new ArrayList<ColumnLookup>();
		
		ColumnLookup colLookup = new ColumnLookup();
		colLookup.setEntity("POLICY");
		colLookup.setLookupColumn("POLICY_NO");
		colLookup.setValueColumn("POLICY_NO#POLICY_RENEW_NO");
		colLookup.setPrecedenceOrder(1);
		colLookups.add(colLookup);
		
		colLookup = new ColumnLookup();
		colLookup.setEntity("POLICY");
		colLookup.setLookupColumn("CONTEXT");
		colLookup.setLookupValue("P");
		colLookup.setValueColumn("REFERENCE_ID#SUB_REFERENCE_ID");
		colLookup.setPrecedenceOrder(2);
		colLookups.add(colLookup);
		entityParams.setLookupColumns(colLookups);
		if (logger.isDebugEnabled()) {
			logger.debug("moreJobsExist:false" + generator.moreJobsExist(entityParams, false));
			logger.debug("moreJobsExist:true" + generator.moreJobsExist(entityParams, true));
			
			logger.debug("getDistinctSet:false" + generator.getDistinctSet(entityParams, false));
			logger.debug("getDistinctSet:true" + generator.getDistinctSet(entityParams, true));
			
			logger.debug("getUpdateListing" + generator.getUpdateListing(entityParams));
			
			logger.debug("getListenerAssignedJob" + generator.getListenerAssignedJob(entityParams));
			
			logger.debug("getUpdateCount" + generator.getUpdateCount(entityParams));
		}
	}
	
	/**
	 * Query to check whether there exists more jobs of the 'same type' 
	 * to be executed. If there are more than one column lookup as set 
	 * in the COLUMN_MAP table, then union would be used to check 
	 * if records exists in either of the columns. <p /> 
	 * EX: If POLICY is set up for lookup into POLICY_NO and also 
	 * CONTEXT = 'P' with the values in the REFERENCE_ID, then 
	 * the query would be <p /> 
	 * 
	 * select '1' from dual where exists ( 
	 * 	select '1' 
	 * 	from JOB_SCHEDULE  
	 * 	where 1 = 1  
	 * 	and trunc(execution_date) <= ?  
	 * 	and job_status = 'PC'  
	 * and  POLICY_NO is not null ) 
	 * union  
	 * select '1' 
	 * from dual where exists ( 
	 * 	select '1' 
	 * 	from JOB_SCHEDULE  
	 * 	where 1 = 1  
	 * 	and trunc(execution_date) <= ?  
	 * 	and job_status = 'PC'  
	 * 	and  CONTEXT = 'P' 
	 * 	and REFERENCE_ID is not null ) <p/ >
	 *  Note: The above example is for ORACLE database 
	 * 
	 * @param entityParams
	 * 		  The batch entity parameters including the look up columns 
	 * @param paramterized
	 * 		  Whether to return a parameterized version of the query
	 * 		  Parameterized version would return the query with appropriate '?'
	 * 		  for the caller to set appropriate values 
	 * @return the query as string 
	 */
	@DatabaseAgnosticCandidate
	public String moreJobsExist(EntityParams entityParams, Boolean paramterized){
		StringBuilder sb = new StringBuilder();
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		for(int i=0; i<columnLookups.size(); i++ ){
			ColumnLookup lookupColumn = columnLookups.get(i); 
			if(i > 0)
				sb.append(" union ");
			
			sb.append("select '1' from JOB_SCHEDULE ");
			sb.append(" where 1 = 1 ");
			sb.append(getConditions(lookupColumn, paramterized));
			//sb.append(") as a");
			if (logger.isDebugEnabled()) {
				logger.debug("Query[" + sb.toString() + "]");
			}
		}
		return sb.toString();
	}	

	/**
	 * @param origQueryPart
	 * @param entityParams
	 * @return
	 */
	public String cleanAssignments(String origQueryPart, EntityParams entityParams){
		StringBuilder sb = new StringBuilder(origQueryPart);
		
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		sb.append(" and ( ");
		for(int i=0; i<columnLookups.size(); i++ ){
			ColumnLookup lookupColumn = columnLookups.get(i); 
			if(i > 0)
				sb.append(" OR ");
			
			sb.append("(");
			sb.append(handleUniColumns(lookupColumn, true));
			
			sb.append(")");
		}
		sb.append(" ) ");
		if (logger.isDebugEnabled()) {
			logger.debug("Query[" + sb.toString() + "]");
		}
		return sb.toString();
	}
	
	/**
	 * Query to get the distinct set of values for an entity 
	 * identified by the EntityParams. 
	 * If there are more than one column lookup as set 
	 * in the COLUMN_MAP table, then union would be used to fetch 
	 * the distinct set. <p /> 
	 * EX: If POLICY is set up for lookup into POLICY_NO and also 
	 * CONTEXT = 'P' with the values in the REFERENCE_ID, then 
	 * the query would be <p /> 
	 * 
	 * select distinct POLICY_NO 
	 * from JOB_SCHEDULE  
	 * where 1 = 1  
	 * and trunc(execution_date) <= ?  
	 * and job_status = 'PC'  
	 * and  POLICY_NO is not null  
	 * union  
	 * select distinct REFERENCE_ID 
	 * from JOB_SCHEDULE  
	 * where 1 = 1  
	 * and trunc(execution_date) <= ?  
	 * and job_status = 'PC'  
	 * and  CONTEXT = 'P' 
	 * and REFERENCE_ID is not null
	 *  
	 * @param entityParams
	 * 		  The batch entity parameters including the look up columns
	 * @param includeListener
	 * 		  Indicator for the query to include the condition for listener   
	 * @return the query as string 
	 */
	public String getDistinctSet(EntityParams entityParams, Boolean includeListener){
		
		StringBuilder sb = new StringBuilder();
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		for(int i=0; i<columnLookups.size(); i++ ){
			ColumnLookup lookupColumn = columnLookups.get(i); 
			if(i > 0)
				sb.append(" union ");
			sb.append(getDistinctSetForlookup(lookupColumn, includeListener));
		}
		
		//This order by is needed for meta events as they have to 
		//be ordered depending upon their priority code 1
		sb.append(" order by 1");
		
		return sb.toString();
	}
	
	/**
	 * Helper method to get the distinct set for a 
	 * single column lookup. 
	 *  
	 * @param lookupColumn
	 * 		  The lookup column definition  
	 * @param includeListener
	 * 		  Indicator to include the listener also into the query
	 * @return the query as string 
	 */
	private String getDistinctSetForlookup(
							ColumnLookup lookupColumn,
							Boolean includeListener){
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct ");
		for (String column : lookupColumn.getValueColumns()) {
			sb.append(column);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" from JOB_SCHEDULE ");
		sb.append(" where 1 = 1 ");
		if(includeListener)
			sb.append(" and listener_indicator = ? ");
		sb.append(getConditions(lookupColumn, false));
		return sb.toString();
	}	
	
	/**
	 * Query to get the distinct set of values (in this case is the JOB_SCHEDULE.job_seq
	 * for an entity identified by EntityParams
	 * The query would use UNION, if there are more than one column lookup setup 
	 * done for an entity to avoid duplicate records  
	 *  
	 * @param entityParams
	 * 		  The batch entity parameters including the look up columns	
	 * @return the query as string  
	 */
	public String getUpdateCount(EntityParams entityParams){
		StringBuilder sb = new StringBuilder();
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		for(int i=0; i<columnLookups.size(); i++ ){
			ColumnLookup lookupColumn = columnLookups.get(i); 
			if(i > 0)
				sb.append(" union ");
			sb.append(" select job_seq from JOB_SCHEDULE ");
			sb.append(" where 1 = 1 ");
			sb.append(getConditions(lookupColumn, true));
		}
		return sb.toString();
	}
	
	/**
	 * Queries for assigning of the listener indicator for an entity  
	 * identified by EntityParams. The list size would be dependent upon 
	 * the number of column lookup as set in the COLUMN_MAP table
	 * EX: If POLICY is set up for lookup into POLICY_NO and also 
	 * CONTEXT = 'P' with the values in the REFERENCE_ID, then 
	 * the list would return with size 2, one each for the column lookup
	 *  
	 * @param entityParams
	 * 		  The batch entity parameters including the look up columns	
	 * @return list of queries as string 
	 */
	public List<String> getUpdateListing(EntityParams entityParams){
		List<String> queries = new ArrayList<String>();
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		for(int i=0; i<columnLookups.size(); i++ ){
			StringBuilder sb = new StringBuilder();
			ColumnLookup lookupColumn = columnLookups.get(i);			
			sb.append(" update JOB_SCHEDULE  set listener_indicator = ? ");
			sb.append(" where 1 = 1 ");
			sb.append(getConditions(lookupColumn, true));
			queries.add(sb.toString());
		}
		return queries;
	}
	
	/**
	 * Helper method to get the conditions for the entity identified by EntityParams 
	 * 
	 * @param columnLookup
	 * 		  The column lookup definition for the entity 
	 * @param parameterized
	 * 		  Whether to return parameterized version. 
	 * 		  If true, then would return the query with '?' else 'is not null'
	 * @return the query embedded in a StringBuilder instance  
	 */
	private StringBuilder getConditions(ColumnLookup columnLookup, boolean parameterized){
		return getConditions(columnLookup, parameterized, false);
	}
	
	/**
	 * Helper method to get the conditions for the entity identified by EntityParams 
	 * 
	 * @param columnLookup
	 * 		  The column lookup definition for the entity
	 * @param parameterized
	 * 		  Whether to return parameterized version. 
	 * 		  If true, then would return the query with '?' else 'is not null'
	 * @param includeListener
	 * 		  Indicator to return whether the listener to be included in the query 
	 * 		  for ease of using the method before and after assignment
	 * @return the query embedded in a StringBuilder instance
	 */
	private StringBuilder getConditions(ColumnLookup columnLookup, 
										boolean parameterized, 
										boolean includeListener){
		
		StringBuilder sb = new StringBuilder();
		
		if(includeListener){
			if(parameterized)
				sb.append(" and listener_indicator = ? ");
			else
				sb.append(" and listener_indicator is not null ");
		}
		sb.append(" and execution_date <= ? ");
		sb.append(" and job_status in ('");
		sb.append(OBJECT_STATUS.PROCREATED.getID()); //PC 
		sb.append("', '");
		sb.append(OBJECT_STATUS.NC.getID()); //NC 
		sb.append("', '");
		sb.append(OBJECT_STATUS.UNDERCONSIDERATION.getID());
		sb.append("') ");
		
		handleUniColumns(sb, columnLookup, parameterized);
		
		return sb;
	}

	/**
	 * Helper method to handle single column look ups
	 * 
	 * @param sbEntity
	 * 		  The string builder to build the condition into 
	 * @param columnLookup
	 * 		  The column lookup definition for the entity 
	 * @param parameterized
	 * 		  Whether to return the parameterized version of the query 
	 */
	private void handleUniColumns(StringBuilder sbEntity, 
									ColumnLookup columnLookup, 
									boolean parameterized) {
		
//		sbEntity.append(" and ");
		
		String lookupCol = columnLookup.getLookupColumn();
		String lookupVal = columnLookup.getLookupValue();
		String[] valueCols = columnLookup.getValueColumns();
		
		if(lookupVal == null)
			for (String valCol : valueCols) {
				if(parameterized)
					sbEntity.append(" and " + valCol + " = ? ");
				else
					sbEntity.append(" and " + valCol + " is not null ");
			}
		else{
			sbEntity.append(" and " + lookupCol + " = " + "'" + lookupVal + "'");
			for (String valCol : valueCols) {
				if(parameterized)
						sbEntity.append(" and " + valCol + " = ? ");
				else
					sbEntity.append(" and " + valCol + " is not null ");
			}
		}
		
	}

	/**
	 * Helper method to handle single column look ups
	 * 
	 * @param columnLookup
	 * 		  The column lookup definition for the entity 
	 * @param parameterized
	 * 		  Whether to return the parameterized version of the query 
	 */
	private String handleUniColumns( 
									ColumnLookup columnLookup, 
									boolean parameterized) {
		
		StringBuilder sbEntity = new StringBuilder();
		
		String lookupCol = columnLookup.getLookupColumn();
		String lookupVal = columnLookup.getLookupValue();
		String[] valueCols = columnLookup.getValueColumns();
		
		if(lookupVal == null)
			for (String valCol : valueCols) {
				if(parameterized)
					sbEntity.append(" and " + valCol + " = ? ");
				else
					sbEntity.append(" and " + valCol + " is not null ");
			}
		else{
			sbEntity.append(" and " + lookupCol + " = " + "'" + lookupVal + "'");
			for (String valCol : valueCols) {
				if(parameterized)
						sbEntity.append(" and " + valCol + " = ? ");
				else
					sbEntity.append(" and " + valCol + " is not null ");
			}
		}
		return sbEntity.substring(4).trim();//Remove the leading " and"
	}

	/**
	 * Query to return the set to configurable's to pro-create 
	 * 
	 * @param parameterized
	 * 		  Whether to return the parameterized version or not 
	 * @return the query as string 
	 */
	public String getQueryForProcreatingConfigurables(Boolean parameterized) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append("  seq_no seq_no");
		sb.append(", task_name task_name");
		sb.append(", eff_date eff_date");
		sb.append(", exp_date exp_date");
		sb.append(", on_fail_exit on_fail_exit");
		sb.append(", priority_code1 priority_code1");
		sb.append(", priority_code2 priority_code2");
		sb.append(", pre_post pre_post");
		sb.append(", job_type job_type");
		sb.append(", line line");
		sb.append(", subline subline");
		sb.append(", date_generate date_generate");
		sb.append(", generate_by generate_by");
		sb.append(", job_desc job_desc");
		sb.append(", object_name object_name");
		sb.append(" from meta_data");
		sb.append(" where ((? > eff_date and exp_date is null) OR ( ? between eff_date and exp_date))");
		sb.append(" and pre_post = ?");
		
		
		if(parameterized) {
			sb.append(" and priority_code1 = ?");
//			sb.append(" order by priority_code2"); //TODO check if this needs to be present.
		} else {
			sb.append(" order by priority_code1");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("QueryForProcreatingConfigurables = " + sb.toString());
		}
		return sb.toString();	
	}

	/**
	 * Query to return the assigned set of jobs for a listener for an entity 
	 * identified by EntityParams 
	 * 
	 * @param entityParams
	 * 		  The batch entity parameters including the look up columns
	 * @return the query as string  
	 */
	public String getListenerAssignedJob(EntityParams entityParams){
		StringBuilder sb = new StringBuilder();
		List<ColumnLookup> columnLookups = entityParams.getLookupColumns();
		fillBESelect(sb, columnLookups.size() > 0);
		sb.append(" from JOB_SCHEDULE OBE ");

		if(columnLookups.size() > 1){
			StringBuilder sbTmp = new StringBuilder();
			sbTmp.append(" inner join ( ");
			for(int i=0; i<columnLookups.size(); i++ ){
				ColumnLookup lookupColumn = columnLookups.get(i);				
				if(i > 0)
					sbTmp.append(" union ");
				
				sbTmp.append(" select job_seq ");				
				sbTmp.append(" from JOB_SCHEDULE ");
				sbTmp.append(" where 1 = 1 ");
				sbTmp.append(getConditions(lookupColumn, true, true));
			}	
			sbTmp.append(" ) IBE ");
			
			sb.append(sbTmp);
			sb.append(" on OBE.job_seq = IBE.job_seq ");
		} else{
			sb.append(" where 1 = 1 ");
			sb.append(getConditions(columnLookups.get(0), true, true));
		}		
		sb.append(getOrderBy(entityParams));
		if(logger.isDebugEnabled()) {
			logger.debug("Query For getListenerAssignedJob = " + sb.toString());
		}
		
		return sb.toString();
		
	}
	
    /**
     * Returns the order by clause based on entity type.
     * If not found then returns empty string.
     * @param entityParams
     * @return Order by clause if applicable otherwise returns empty string.
     */
	private String getOrderBy(EntityParams entityParams) {
		String entity = entityParams.getEntity();
		Map<String, String> orderByMap = entityParams.getOrderByMap();
		
		if (!orderByMap.containsKey(entity))
			return "";
		String orderByClause = orderByMap.get(entity);
		
		if(orderByClause == null || "null".equals(orderByClause))
			return "";
		return " order by " + orderByClause;
		
	}

	/**
	 * Helper method to populate supplied StringBuilder instance with the selects 
	 * from the JOB_SCHEDULE table
	 *   
	 * @param sb
	 * 		  The StringBuilder instance 
	 * @param isInnerJoinNeeded
	 * 		  Special condition needed in case of multiple column lookups
	 * 		  If mulitple column lookup is set then this arguments should 
	 * 		  have value true 
	 */
	private void fillBESelect(StringBuilder sb, Boolean isInnerJoinNeeded){
//		sb.append(" select be_line line,");
//		sb.append(" be_subline subline,");
		sb.append(" select policy_no,");
		sb.append(" to_char(policy_renew_no, 'FM9999999999999999999MI') policy_renew_no ,");
		sb.append(isInnerJoinNeeded ? "to_char(OBE.job_seq, 'FM9999999999999999999MI') \"sequence\"," : " to_char(job_seq, 'FM9999999999999999999MI') \"sequence\",");
		sb.append(" job_detail taskname,");
		sb.append(" execution_date executiondate,");
		sb.append(" job_status status,");
		sb.append(" system_activity_no,");
//		sb.append(" be_user_override_priority user_override_priority,");
		sb.append(" pre_post,");
		sb.append(" priority_code_1,");
		sb.append(" priority_code_2,");
		sb.append(" job_type,");
//		sb.append(" be_broker broker,");
//		sb.append(" be_veh_ref_no veh_ref_no,");
//		sb.append(" be_cash_batch_no cash_batch_no,");
//		sb.append(" be_cash_batch_revision_no cash_batch_revision_no,");
		sb.append(" to_char(bill_no, 'FM9999999999999999999MI') gbi_bill_no,");
//		sb.append(" be_print_form_no print_form_no,");
//		sb.append(" be_notify_error_to notify_error_to,");
		sb.append(" created_on date_generate,");
		sb.append(" created_by generate_by,");
		sb.append(" system_remarks rec_message,");
//		sb.append(" be_job_desc job_desc,");
		sb.append(" job_name object_name,");
		sb.append(" date_executed,");
		sb.append(" listener_indicator list_ind,");
		sb.append(" context,");
		sb.append(" reference_id,");
		sb.append(" to_char(sub_reference_id, 'FM9999999999999999999MI') subreference_id,");
//		sb.append(" be_batch_type batch_type,");
		sb.append(" entity_type");
//		sb.append(" entity_code entity_code");
//		sb.append(" be_ref_system_activity_no ref_system_activity_no ");		
	}
	
	/**
	 * Helper method to append the StringBuilder instance order by clause for the query 
	 * 
	 * @param sb
	 * 		  The StringBuilder instance
	 */
//	private void fillOrderBy(StringBuilder sb){
//		sb.append(" order by ");
//		sb.append(" policy_no,");
//		sb.append(" policy_renew_no,");
//		sb.append(" entity_type,");
////		sb.append(" entity_code,");
//		sb.append(" context,");
//		sb.append(" reference_id,");
//		sb.append(" sub_reference_id,");
//		sb.append(" priority_code_1,");
//		sb.append(" priority_code_2");
//		
//		
//	}
	
	/**
	 * Builds the query to check the execution status of the listeners 
	 * 
	 * @param scheduledExecutables
	 * 		  The list of scheduled executables 
	 * @return the query as string 
	 */
	public String buildListenerExecutionStatusesQuery(
					List<ScheduledExecutable> scheduledExecutables){
		
		StringBuilder sb = new StringBuilder();
		sb.append("select req_stat from process_request where req_id in (");
		ScheduledExecutable scheduledExecutable = scheduledExecutables.get(0);
		sb.append(scheduledExecutable.getReqId());
		for(int i=1; i<scheduledExecutables.size(); i++){
			scheduledExecutable = scheduledExecutables.get(i);
			sb.append("," + scheduledExecutable.getReqId());
		}
		sb.append(")");
		return sb.toString();
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/QueryGenerator.java                                                                                 $
 * 
 * 7     7/14/10 2:45p Kedarr
 * Added order by clause to the query generator for procreating meta events.
 * 
 * 6     4/26/10 2:41p Kedarr
 * Added UC status for the get assigned objects for listener.
 * 
 * 5     3/02/10 3:36p Grahesh
 * Added logger
 * 
 * 4     12/23/09 3:25p Grahesh
 * Removed be_on_fail_exit from batch_executor
 * 
 * 3     12/21/09 3:37p Grahesh
 * Added condition to include 'NC' objects
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/