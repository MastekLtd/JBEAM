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
package com.stgmastek.monitor.purge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import admin.InsertScripts;

/**
 * Class to Purge batch details.
 * 
 * Creates the insert scripts for the data that is being deleted and then purges the data.
 *
 * @author Lakshman Pendrum
 * @since $Revision: 1 $
 */
public class PurgeBatchDetailsMonitor extends ProcessRequestServicer {

	private static final MessageFormat format = new MessageFormat(
			" {0} table for {1} of {2} batches batchNo[{3}]");
	private final static Logger logger = Logger.
			getLogger(PurgeBatchDetailsMonitor.class);

	/**
	 *  The method for purge batch details
	 *  
	 *  <p>Fetches configuration details like no of days to retain, installation 
	 *  name, insert script back up directory from configuration as in the 
	 *  CONFIGURATION table.</br> Executes the SQL to get the batch number list 
	 *  for given no of days to retain. Creates the output file as 
	 *  JBeam<indtallation name>batchno.sql for each batch no in BACKUP_DIR</br> 
	 *  Verifies the output file in BACKUP_DIR before creating and if exists then
	 *  deletes it.</br>
	 *  </p>
	 *  Insert scripts of tables in the following order
	 *  <li>BATCH</li><li>PROGRESS_LEVEL</li><li>SYSTEM_INFO</li><li>LOG</li>
	 *  <li>GRAPH_DATA_LOG</li><li>INSTRUCTION_LOG</li><li>INSTRUCTION_PARAMETERS</li>
	 *  </br></br>
	 *  <p>Deletes the details from table in the following order</p>
	 *  <li>INSTRUCTION_PARAMETERS</li><li>INSTRUCTION_LOG</li><li>GRAPH_DATA_LOG</li>
	 *  <li>SYSTEM_INFO</li><li>LOG</li><li>PROGRESS_LEVEL</li><li>BATCH</li>
	 *  
	 * @see ProcessRequestServicer#processRequest()
	 * @return true if insert and delete successful, false otherwise
	 * @throws SQLException
	 * 		   Any exception occurred during the execution of the SQL 
	 */
	public boolean processRequest() throws CProcessRequestEngineException {
		Boolean returnValue = false;
		Integer noOfDaysToRetain = null;
		ResultSet rs = null;
		PreparedStatement psSelect = null;
		ArrayList<String> installationList = null;
		Map<String, ArrayList<String>> mapBatchDetails = null;
		Connection con = getConnection();
		PrintWriter out = null;
		try {
			out = getResponseWriter(); // super will close the object so no need to implement endProcess(..)
			psSelect = con.prepareStatement(" select VALUE from configuration where code1= 'MONITOR' " +
            								" and code2='PURGE' and code3='RETAIN_DAYS'");
			rs = psSelect.executeQuery();
			if(rs.next()){
				noOfDaysToRetain = Integer.valueOf(rs.getString(1));
			}
			rs.close();
			psSelect.close();
			if (noOfDaysToRetain == null) {
				logger.fatal("Number of Days must be configured. Unable to proceed.");
				return false;
			}
		    if (noOfDaysToRetain <= 0) {
				logger.fatal("Number of Days to be retained cannot be blank.");
				return false;
		    }
		    installationList = getInstallationList(con);
			mapBatchDetails = getBatchDetailsMap(con, installationList,noOfDaysToRetain);
			if(insertPurgeScripts(out, con, mapBatchDetails))
			{
				deleteBatchDetails(out, con, mapBatchDetails);
				returnValue = true;
			}
		} catch (SQLException sqle) {
			logger.error("SQLException:", sqle);
		}catch (Exception e) {
			logger.error("Exception:", e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}				
			} catch (SQLException e) {
				logger.error("SQLException:", e);
			}
			try {
				if(psSelect != null) {
					psSelect.close();
				}
			} catch (SQLException e) {
				logger.error("SQLException:", e);
			}
			
		}
		return returnValue;
	}

	/**
  	 *  The method logs output.
	 *  
	 * @param tableName
	 * 			Table name where the insert script created 
	 * @param cnt
	 * 			current count of total batch numbers for insert script 
	 * @param listSize
	 * 			batch numbers count in that installation  
	 * @param batchNo
	 * 			insert script batch number   
	 * @param mode
	 * 			1 for Creating script, 2 for Deleting  
	 */
	private void log(String tableName, Integer cnt, Integer listSize, String batchNo, Integer mode) {
		String strPrefix = null;
	    if (mode == 1) strPrefix="Creating insert scripts for";
	    if (mode == 2) strPrefix="Deleting from";
	    
		Object[] args = {strPrefix,tableName, cnt, listSize, batchNo};
  	  	if (logger.isInfoEnabled()){
			logger.info(format.format(args));
  	  	}
	}
	/**
	 *  The method to get installation codes as configured in the system
	 *  
	 * @return installationList 
	 * 			List of Installation Codes
	 * @throws SQLException
	 *             Any database SQL exception
	 */
	private ArrayList<String> getInstallationList(Connection con) throws Exception{
		ArrayList<String> installationList = new ArrayList<String>();
		PreparedStatement psSelect = null;
		ResultSet resultset =null;
		try{
			psSelect = con
					.prepareStatement("select installation_code from installation order by 1");
			resultset = psSelect.executeQuery();
			while(resultset.next()){
				installationList.add(resultset.getString(1));
			}
		}catch (SQLException e) {
				logger.error("SQLException in getting Installation codes ",e);
				throw e;
		}finally{
				try{
					if(resultset != null){
						resultset.close();
					}
				}catch (SQLException e) {
					logger.error("SQLException ", e);
				}
				resultset = null;
				try{
					if(psSelect != null){
						psSelect.close();
					}
				}catch (SQLException e) {
					logger.error("SQLException ", e);
				}
				psSelect = null;
		}
		return installationList;
	}
	/**
	 *  The method to get batch number list for all installation codes
	 *  
	 * @param installationCodesList
	 * 			installation codes  
	 * @return BatchDetailsMap 
	 * 			list of Installation Codes
	 * @throws SQLException
	 *          any database SQL exception
	 */
	private HashMap<String,ArrayList<String>> getBatchDetailsMap(Connection con, ArrayList<String> 
			installationCodesList,Integer noOfDaysToRetain)throws Exception{
		HashMap<String,ArrayList<String>> batchDetailsMap = new HashMap<String,ArrayList<String>>();
		PreparedStatement psSelect = null;
		ResultSet resultset = null;
		try {
			for(String installationCode:installationCodesList)
			{
				ArrayList<String> bacthNoList=new ArrayList<String>();
				psSelect = con
						.prepareStatement("select batch_no from batch where installation_code = ? "+
								   "and and exec_start_time < (sysdate-?)");
				psSelect.setString(1, installationCode);
				psSelect.setObject(2, Integer.valueOf(noOfDaysToRetain));
				resultset = psSelect.executeQuery();
				while(resultset.next()){
					bacthNoList.add(resultset.getString(1));
				}
				if(!bacthNoList.isEmpty()){
					batchDetailsMap.put(installationCode, bacthNoList);
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException in while getting batch details ",e);
			throw e;
		}finally{
			try{
				if(resultset != null){
					resultset.close();
				}
			}catch (SQLException e) {
				logger.error("SQL Exception", e);
			}
			resultset= null;
			try{
				if(psSelect != null){
					psSelect.close();
				}
			}catch (SQLException e) {
				logger.error("SQL Exception", e);
			}
			psSelect = null;
			
		}
		return batchDetailsMap;
	}
	
	/**
	 *  The method to create insert scripts
	 *  
	 * 	Installation codes with batch numbers. Script creation in the following order
	 *  <li>BATCH</li><li>PROGRESS_LEVEL</li><li>SYSTEM_INFO</li><li>LOG</li>
	 *  <li>GRAPH_DATA_LOG</li><li>INSTRUCTION_LOG</li><li>INSTRUCTION_PARAMETERS</li>
	 *  </br></br>
	 * @param mapBatchDetails map contains installation code and batch numbers
	 * @return true is create insert scripts, false otherwise
	 * 			
	 */
	private Boolean insertPurgeScripts(PrintWriter out, Connection con, Map<String, ArrayList<String>> mapBatchDetails)
			throws Exception {
		boolean insertFlag = false;
		PreparedStatement psSelect = null;
		ResultSet resultset      = null;
		String strOutputFileName = null;
		String strOutputDirectory = null;
		try {
			
			psSelect = con
			         .prepareStatement("select value from configuration where code1='MONITOR' " +
			         					"and code2 ='PURGE' and code3 =  'BACKUP_DIR'");
			resultset = psSelect.executeQuery();
			if(resultset.next()){
				strOutputDirectory    = resultset.getString(1);
			}
			resultset.close();
			psSelect.close();
			resolveDirectory(new File(strOutputDirectory));
				
	        InsertScripts is = new InsertScripts(con);
	        out.print("Insert scripts started..");
	        int count = 1;
	        
			for (Map.Entry <String,ArrayList<String>>entry :mapBatchDetails.entrySet()) {
					String installationCode = entry.getKey();
					ArrayList<String> batchNoList = new ArrayList<String>(entry.getValue());
				for(String batchno:batchNoList){
					  strOutputFileName = strOutputDirectory+"JBeam"+installationCode+batchno+".sql";
					  if (logger.isInfoEnabled()){
						  logger.info("Script file "+strOutputFileName+" created.");
					  }
					  File file = new File(strOutputFileName);
					  if (file.exists()) {
					     file.delete();
					  }
					  is.setFile(strOutputFileName, true);
					  is.setAppendToFile(true);
					  String strWhereClause ="WHERE BATCH_NO = "+ batchno+
					  						" and installation_code = '"+installationCode+"'";
					  
					  log("PROGRESS_LEVEL",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for PROGRESS_LEVEL table");
					  is.onTable("PROGRESS_LEVEL", strWhereClause);
					  
					  log("SYSTEM_INFO",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for SYSTEM_INFO table");
					  is.onTable("SYSTEM_INFO", strWhereClause);
					  
					  log("LOG",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for LOG table");
					  is.onTable("LOG", strWhereClause);
					  
					  log("GRAPH_DATA_LOG",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for GRAPH_DATA_LOG table");
					  is.onTable("GRAPH_DATA_LOG", strWhereClause);
					
					  log("INSTRUCTION_LOG",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for INSTRUCTION_LOG table");
					  strWhereClause ="WHERE BATCH_NO = "+ batchno;
					  is.onTable("INSTRUCTION_LOG", strWhereClause);
					  
					  log("INSTRUCTION_PARAMETERS",count,batchNoList.size(),batchno,1);
					  is.echo("insert script for INSTRUCTION_PARAMETERS table");
					  strWhereClause =" b where exists (select a.seq_no from instruction_log a " +
					  				  " where a.seq_no = b.instruction_log_no and a.batch_no = "+ batchno+
					  				  " and a.installation_code = '"+installationCode+"')";
					  is.onTable("INSTRUCTION_PARAMETERS ", strWhereClause);
					  count++;
				}
			}
	    	is.close();
	        out.print("Creating insert scripts completed");
		    insertFlag = true;
	    }catch (SQLException sqle) {
	    	logger.error("SQLException in while creating insert scripts ",sqle);
	    	throw sqle;
	    }catch (Exception e) {
	    	logger.error("Exception ",e);
	    	throw e;
	    }finally{
	    	try{
				if(resultset != null){
					resultset.close();
				}
	    	}catch (SQLException e) {
	    		logger.error("SQLException ",e);
	    	}
			resultset= null;
			try{
				if(psSelect != null){
					psSelect.close();
				}
			}catch (SQLException e) {
				logger.error("SQLException ",e);
			}
			psSelect = null;
	    }
		return insertFlag;
  }
	/**
	 *  The method to delete batch details
	 *  <p>Deletes the batch details from table in the following order</p>
	 *  <li>INSTRUCTION_PARAMETERS</li><li>INSTRUCTION_LOG</li><li>GRAPH_DATA_LOG</li>
	 *  <li>SYSTEM_INFO</li><li>LOG</li><li>PROGRESS_LEVEL</li>
	 *   
	 * @param con Connection object
  	 * @param mapBatchDetails
	 * 			map contains installation code and batch numbers
	 */
	private void deleteBatchDetails(PrintWriter out, Connection con, Map<String, ArrayList<String>> mapBatchDetails)
			throws SQLException {
		PreparedStatement psUpdate = null;
		ResultSet resultset = null;
		int count = 1;
        out.print("Deleting batch details started..");
  	  try{
  	       con.setAutoCommit(false);
	       for (Map.Entry <String,ArrayList<String>>entry :mapBatchDetails.entrySet()) {
			    String installationCode = entry.getKey();
			    ArrayList<String> batchNoList = new ArrayList<String>(entry.getValue());
			    count = 1;
		     for(String strBacthNo:batchNoList){
      	
		      	log("INSTRUCTION_PARAMETERS",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from INSTRUCTION_PARAMETERS b " +
		      			   "where exists (select a.seq_no from instruction_log a " +
		      			   "where a.seq_no = b.instruction_log_no and a.batch_no = ? " +
		      			   "and a.installation_code = ?)");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	psUpdate.close();
		      	
		      	log("INSTRUCTION_LOG",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from INSTRUCTION_LOG WHERE BATCH_NO = ? " +
		      			  "and installation_code = ?");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	psUpdate.close();
		      	
		      	log("GRAPH_DATA_LOG",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from GRAPH_DATA_LOG WHERE BATCH_NO = ?  " +
		      			  "and installation_code = ?");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	con.setAutoCommit(true);
		      	psUpdate.close();
		      	
		      	log("SYSTEM_INFO",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from SYSTEM_INFO WHERE BATCH_NO = ? " +
		      			  "and installation_code = ?");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	con.setAutoCommit(true);
		      	psUpdate.close();
		      	
		      	log("LOG",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from LOG WHERE BATCH_NO = ? " +
		      			  "and installation_code = ?");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	psUpdate.close();
		      	
		      	log("PROGRESS_LEVEL",count,batchNoList.size(),strBacthNo,2);
		      	psUpdate = con.prepareStatement("delete from PROGRESS_LEVEL WHERE BATCH_NO = ? " +
		      			  "and installation_code = ?");
		      	psUpdate.setString(1, strBacthNo);
		      	psUpdate.setString(2, installationCode);
		      	psUpdate.executeUpdate();
		      	psUpdate.close();
		      	count++;
			}
	  	 }
	        out.print("Deleting batch details completed.");
			con.commit();
	  	}catch (SQLException sqle) {
	    	logger.error("SQLException in while deleting batch details ",sqle);
	    	throw sqle;
	    }catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				logger.error("Exception ",sqle);
			}
	    	logger.error("Exception ",e);
	    }finally{
			try{
				if(resultset != null){
					resultset.close();
				}
			}catch (SQLException sqle) {
				logger.error("SQLException ",sqle);
			}
			   resultset= null;
			try{	
				if(psUpdate != null){
					psUpdate.close();
				}
				psUpdate= null;
			}catch (SQLException sqle) {
				logger.error("SQLException ",sqle);
			}
			try {
				con.setAutoCommit(true);
			}catch (Exception e) {
				logger.error("Exception ",e);
			}
	    }
  }
	/**
	 * Resolves the directory by creating it if necessary.
	 * 
	 * @param file output directory
	 * @throws IOException
	 * @throws NullPointerException in case the file is null
	 * @throws FileNotFoundException in case the file is not a directory and new file could not be created.
	 */
	private void resolveDirectory(File file) throws IOException {
		if (file == null) {
			throw new NullPointerException("Output directory is not configured properly.");
		}
		if (!file.exists()) {
			if (file.isFile()) {
				throw new FileNotFoundException("Directory is needed instead of a file configured properly.");
			}
			if (!file.createNewFile()) {
				throw new FileNotFoundException(file.getAbsolutePath() + " could not be created");
			}
		}
	}

  
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Moni $
 * 
 * 1     6/17/10 11:49a Lakshmanp
 * Initial version
 * 
 * 
*
*
*/
