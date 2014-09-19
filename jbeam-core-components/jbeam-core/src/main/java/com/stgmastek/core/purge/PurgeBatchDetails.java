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
package com.stgmastek.core.purge;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import stg.utils.Day;
import admin.InsertScripts;

import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.time.JBeamTimeFactory;

/**
 * Class to Purge batch details.
 * 
 * Creates insert scripts for the data that is being deleted and then purges the
 * data. The insert scripts can be executed to insert the data back in the system.
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 3418 $
 */
public class PurgeBatchDetails extends ProcessRequestServicer {

	private PrintWriter out;
	
	private final MessageFormat format = new MessageFormat(
			" {0} table for {1} of {2} batches batchNo[{3}]");
	private final static Logger logger = Logger
			.getLogger(PurgeBatchDetails.class);

	
	public void endProcess() throws CProcessRequestEngineException {
	}

	/**
	 * The method for purge batch details
	 * <p>
	 * Fetches configuration details like no of days to retain, installation
	 * name, insert script back up directory from configuration as in the
	 * CONFIGURATION table.</br> Executes the SQL to get the batch number list
	 * for given no of days to retain. Creates the output file as
	 * JBeam<indtallation name>batchno.sql for each batch no in BACKUP_DIR</br>
	 * Verifies the output file in BACKUP_DIR before creating and if exists then
	 * deletes it.</br>
	 * </p>
	 * Insert scripts of tables in the following order <li>BATCH</li><li>
	 * PROGRESS_LEVEL</li><li>LOG</li><li>SYSTEM_INFO</li> <li>INSTRUCTION_LOG</li>
	 * <li>INSTRUCTION_PARAMETERS</li></br></br>
	 * <p>
	 * Deletes the details from table in the following order
	 * </p>
	 * <li>SYSTEM_INFO</li><li>LOG</li><li>INSTRUCTION_PARAMETERS</li><li>
	 * INSTRUCTION_LOG</li> <li>PROGRESS_LEVEL</li><li>BATCH</li>
	 * 
	 * @see ProcessRequestServicer#processRequest()
	 * @return true if insert and delete successful, false otherwise
	 * @throws SQLException
	 *             Any exception occurred during the execution of the SQL
	 */
	
	public boolean processRequest() throws CProcessRequestEngineException {
		try {
			out = getResponseWriter();
		} catch (IOException e1) {
			throw new CProcessRequestEngineException(e1.getMessage(), e1);
		}
		ResultSet rs = null;
		String strInstallation = null;
		String strOutputDirectory = null;
		PreparedStatement psSelect = null;
		ArrayList<String> batchNoList = new ArrayList<String>();
		Integer noOfDaysToRetain = null;
		Boolean returnValue = false;
		Connection con = null;
		try {
			con = getDataSourceFactory().getDataSource(Constants.POOL_NAMES.BATCH.name()).getConnection();

			psSelect = con
					.prepareStatement("select VALUE from configuration where code1= 'CORE' and code2='PURGE' and code3='RETAIN_DAYS'");
			rs = psSelect.executeQuery();
			if (rs.next()) {
				noOfDaysToRetain = Integer.valueOf(rs.getString(1));
			}
			rs.close();
			psSelect.close();
			if (noOfDaysToRetain == null) {
				logger.fatal("Number of Days must be configured. Unable to proceed.");
				return false;
			}
			if (noOfDaysToRetain <= 0) {
				logger.fatal("Number of Days to be retained cannot be <= zero.");
				return false;
			}

			psSelect = con
					.prepareStatement("select value from configuration where code1= 'CORE' and code2='INSTALLATION' and code3='CODE'");
			rs = psSelect.executeQuery();
			if (rs.next()) {
				strInstallation = rs.getString(1);
			} else {
				strInstallation = "NotConfigured";
			}
			rs.close();
			psSelect.close();

			psSelect = con
					.prepareStatement("select value from configuration where code1='CORE' and code2 ='PURGE' and code3 = 'BACKUP_DIR'");
			rs = psSelect.executeQuery();
			if (rs.next()) {
				strOutputDirectory = rs.getString(1);
			}
			rs.close();
			psSelect.close();
			Day day = new Day(JBeamTimeFactory.getInstance().getCurrentTimestamp(con));
			day.advance(noOfDaysToRetain * -1);
			resolveDirectory(new File(strOutputDirectory));
			psSelect = con
					.prepareStatement("select batch_no from batch where exec_start_time < ? order by batch_no");
			psSelect.setObject(1, day.getTimestamp());
			rs = psSelect.executeQuery();
			while (rs.next()) {
				batchNoList.add(rs.getString(1));
			}
			out.println("Purging BatchNo List--" + batchNoList);
			rs.close();

			if (insertPurgeScripts(con, batchNoList, strOutputDirectory,
					strInstallation)) {
				deleteBatchDetails(con, batchNoList);
				returnValue = true;
			}
		} catch (SQLException sqle1) {
			logger.error("SQLException:", sqle1);
		} catch (Exception e) {
			logger.error("Exception:", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (psSelect != null) {
					psSelect.close();
				}
				if (con != null) {
					con.close();
				}
				noOfDaysToRetain = null;
				strInstallation = null;
				batchNoList = null;
			} catch (SQLException e) {
				logger.error("Exception in finally:", e);
			}
		}
		return returnValue;
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
		if (file == null){
			throw new NullPointerException("Output directory is not configured properly.");
		}
		if (!file.exists()) {
			FileUtils.forceMkdir(file);
		}
	}


	/**
	 * The method logs output.
	 * 
	 * @param tableName
	 *            Table name where the insert script created
	 * @param cnt
	 *            current count of total batch numbers for insert script
	 * @param listSize
	 *            batch numbers count in that installation
	 * @param batchNo
	 *            insert script batch number
	 * @param mode
	 *            1 for Creating script, 2 for Deleting
	 */
	private void log(String tableName, Integer cnt, Integer listSize,
			String batchNo, Integer mode) {
		String strPrefix = null;
		if (mode == 1)
			strPrefix = "Creating insert scripts for";
		if (mode == 2)
			strPrefix = "Deleting from";
		Object[] args = { strPrefix, tableName, cnt, listSize, batchNo };
		if (logger.isInfoEnabled()) {
			logger.info(format.format(args));
		}
	}

	/**
	 * The method to create insert scripts.
	 * 
	 * Script creation is in the following order <li>BATCH</li><li>
	 * PROGRESS_LEVEL</li><li>LOG</li><li>SYSTEM_INFO</li> <li>INSTRUCTION_LOG</li>
	 * <li>INSTRUCTION_PARAMETERS</li></br></br>
	 * 
	 * @param batchDetailsList
	 * @param strOutputDirectory
	 * @param strInstallation
	 * @return true if create insert scripts successful, false otherwise
	 * @throws Exception
	 * 
	 */
	private Boolean insertPurgeScripts(Connection con, ArrayList<String> batchDetailsList,
			String strOutputDirectory, String strInstallation) throws Exception {
		boolean returnValue = false;
		String strOutputFileName;
		int count = 1;
		out.println("Create insert script started..");
		InsertScripts is = null;
			for (String batchno : batchDetailsList) {
				strOutputFileName = FilenameUtils.concat(strOutputDirectory, "JBeam" + strInstallation + batchno + ".sql");
				try {
					is = new InsertScripts(con);
					if (logger.isInfoEnabled()) {
						logger.info("Insert script file " + strOutputFileName
								+ " created.");
					}
					File file = new File(strOutputFileName);
					if (file.exists()) {
						if (!file.delete()) {
							if (logger.isDebugEnabled()) {
								logger.debug(strOutputFileName+ " could not be deleted.");
							}
						}
					}
					is.setAppendToFile(true);
					is.setFile(strOutputFileName, true);
					String strWhereClause = "WHERE BATCH_NO = " + batchno;
	
					log("BATCH", count, batchDetailsList.size(), batchno, 1);
					is.echo("insert script for BATCH table");
					is.onTable("BATCH", strWhereClause);
	
					log("PROGRESS_LEVEL", count, batchDetailsList.size(), batchno,
							1);
					is.echo("insert script for PROGRESS_LEVEL table");
					is.onTable("PROGRESS_LEVEL", strWhereClause);
	
					log("LOG", count, batchDetailsList.size(), batchno, 1);
					is.echo("insert script for LOG table");
					is.onTable("LOG", strWhereClause);
	
					log("SYSTEM_INFO", count, batchDetailsList.size(), batchno, 1);
					is.echo("insert script for SYSTEM_INFO table");
					is.onTable("SYSTEM_INFO", strWhereClause);
	
					log("INSTRUCTION_LOG", count, batchDetailsList.size(), batchno,
							1);
					is.echo("insert script for INSTRUCTION_LOG table");
					strWhereClause = "WHERE BATCH_NO = " + batchno;
					is.onTable("INSTRUCTION_LOG", strWhereClause);
	
					log("INSTRUCTION_PARAMETERS", count, batchDetailsList.size(),
							batchno, 1);
					is.echo("insert script for INSTRUCTION_PARAMETERS table");
					strWhereClause = " b where exists (select a.seq_no from instruction_log a where a.seq_no = b.instruction_log_no and a.batch_no = "
							+ batchno + " )";
					is.onTable("INSTRUCTION_PARAMETERS ", strWhereClause);
					count++;
				} finally {
					if (is != null) {
						is.close();
					}
				}
			}
			out.println("Create insert script completed..");
			returnValue = true;
		return returnValue;
	}

	/**
	 * The method to delete batch details
	 * <p>
	 * Deletes the batch details from tables in the following order
	 * </p>
	 * <li>SYSTEM_INFO</li><li>LOG</li><li>INSTRUCTION_PARAMETERS</li><li>
	 * INSTRUCTION_LOG</li> <li>PROGRESS_LEVEL</li><li>BATCH</li>
	 * 
	 * @param batchNoList
	 *            Batch numbers list
	 */
	private void deleteBatchDetails(Connection con, ArrayList<String> batchNoList) throws SQLException {
		PreparedStatement psUpdate = null;
		int count = 1;
		out.println("Deleting batch details started..");
		try {
			con.setAutoCommit(false);
			for (String strBacthNo : batchNoList) {
				log("SYSTEM_INFO", count, batchNoList.size(), strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from SYSTEM_INFO WHERE BATCH_NO = ?");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				con.setAutoCommit(true);
				psUpdate.close();

				log("LOG", count, batchNoList.size(), strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from LOG WHERE BATCH_NO = ?");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				psUpdate.close();

				log("INSTRUCTION_PARAMETERS", count, batchNoList.size(),
						strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from INSTRUCTION_PARAMETERS b where exists (select a.seq_no from instruction_log a where a.seq_no = b.instruction_log_no and a.batch_no = ? )");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				psUpdate.close();

				log("INSTRUCTION_LOG", count, batchNoList.size(), strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from INSTRUCTION_LOG WHERE BATCH_NO = ?");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				psUpdate.close();

				log("PROGRESS_LEVEL", count, batchNoList.size(), strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from PROGRESS_LEVEL WHERE BATCH_NO = ?");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				psUpdate.close();

				log("BATCH", count, batchNoList.size(), strBacthNo, 2);
				psUpdate = con
						.prepareStatement("delete from BATCH WHERE BATCH_NO = ?");
				psUpdate.setString(1, strBacthNo);
				psUpdate.executeUpdate();
				psUpdate.close();
				count++;
			}
			out.println("Deleting batch details completed.");
			con.commit();
		} catch (SQLException e) {
			logger
					.error(
							"SQLException in getting batch details based on installation codes ",
							e);
			try {
				con.rollback();
			} catch (SQLException e2) {
			}
			throw e;
		} finally {
			try {
				if (psUpdate != null) {
					psUpdate.close();
				}
			} catch (SQLException e) {
			}
			try {
				con.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
	}
}
