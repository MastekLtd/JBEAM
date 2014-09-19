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
package com.stgmastek.monitor.comm.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.dao.IStatusDAO;
import com.stgmastek.monitor.comm.server.vo.BatchProgress;
import com.stgmastek.monitor.comm.server.vo.MReqSystemInfo;
import com.stgmastek.monitor.comm.util.BaseDAO;

/**
 * DAO class for all Status related activities.
 * 
 * @author mandar.vaidya
 * 
 */
public class StatusDAO extends BaseDAO implements IStatusDAO {

	/** Query to INSERT data into PROGRESS_LEVEL table */
	private static final String INSERT_PROGRESS_LEVEL = 
			"INSERT INTO PROGRESS_LEVEL" + 
			" (installation_code, batch_no, batch_rev_no," +
			" indicator_no, prg_level_type, prg_activity_type," +
			" cycle_no, status, start_datetime, end_datetime," +
			" error_desc, failed_over) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/** Query to UPDATE data into PROGRESS_LEVEL table */
	private static final String UPDATE_PROGRESS_LEVEL = "UPDATE PROGRESS_LEVEL" +
			" SET end_datetime = ? WHERE " +
			" batch_no = ? AND batch_rev_no = ?" +
			" AND indicator_no = ?";
	
	/** Query to INSERT data into SYSTEM_INFO table */
	private static final String INSERT_SYSTEM_INFO = "INSERT INTO SYSTEM_INFO " +
			"(installation_code, batch_no, batch_rev_no," +
			" java_version, pre_version, os_config," +
			" output_dir_path, output_dir_free_mem, max_memory, used_memory)" +
			" VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	/** Public constructor takes no argument */
	public StatusDAO() {
		super();
	}

	/**
	 * Adds the batch progress level with the given batch progress
	 * 
	 * @param batchProgress
	 * 		  The batch progress
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * @throws CommDatabaseException 
	 * 		   Any database I/O exception
	 */
	public Integer addBatchProgress(BatchProgress batchProgress,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(INSERT_PROGRESS_LEVEL);
			pstmt.setObject(1, batchProgress.getInstallationCode());
			pstmt.setObject(2, batchProgress.getBatchNo());
			pstmt.setObject(3, batchProgress.getBatchRevNo());
			pstmt.setInt(4, batchProgress.getIndicatorNo());
			pstmt.setString(5, batchProgress.getPrgLevelType());
			pstmt.setString(6, batchProgress.getPrgActivityType());
			
			Integer objCycleNo = batchProgress.getCycleNo();
			if(objCycleNo !=null)
				pstmt.setObject(7, objCycleNo);
			else
				pstmt.setObject(7, null);
			
			pstmt.setString(8, batchProgress.getStatus());
			pstmt.setTimestamp(9, new Timestamp(batchProgress.getStartDatetime()));
			Long objendDateTime = batchProgress.getEndDatetime();
			
			if(objendDateTime !=null)
				pstmt.setTimestamp(10, new Timestamp(batchProgress.getEndDatetime()));
			else
				pstmt.setTimestamp(10, null);
			pstmt.setString(11, batchProgress.getErrorDesc());
			pstmt.setString(12, batchProgress.getFailedOver());
			
			int iInsert = pstmt.executeUpdate();
			
			connection.commit();
			return iInsert;
			
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
		
	}
	
	/**
	 * Updates the batch progress level with the given batch progress. 
	 * This will update only End Date Time field (end_date_time) for the given 
	 * batch number and batch revision number.
	 * 
	 * @param batchProgress
	 * 		  The batch progress
	 * 
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * @throws CommDatabaseException 
	 * 		   Any database I/O exception
	 */
	public Integer updateBatchProgress(BatchProgress batchProgress,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(UPDATE_PROGRESS_LEVEL);			
			Long objendDateTime = batchProgress.getEndDatetime();			
			if(objendDateTime != null)
				pstmt.setTimestamp(1, new Timestamp(batchProgress.getEndDatetime()));
			else
				pstmt.setTimestamp(1, null);
			
			pstmt.setObject(2, batchProgress.getBatchNo());
			pstmt.setObject(3, batchProgress.getBatchRevNo());
			pstmt.setInt(4, batchProgress.getIndicatorNo());
			
			int iUpdate = pstmt.executeUpdate();
			
			connection.commit();
			return iUpdate;
			
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}		
	}	

	/**
	 * Sets the system information on which the batch is run
	 * 
	 * @param sysInfo
	 * 		  The system information 
	 * @return 1, if the record is inserted successfully, false otherwise 
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception 
	 */
	public Integer updateSystemInformation(MReqSystemInfo sysInfo,Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try{
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(INSERT_SYSTEM_INFO);
			pstmt.setString(1, sysInfo.getInstallationCode());
			pstmt.setInt(2, sysInfo.getBatchNo());
			pstmt.setInt(3, sysInfo.getBatchRevNo());
			pstmt.setString(4, sysInfo.getJavaVersion());
			pstmt.setString(5, sysInfo.getPreVersion());
			pstmt.setString(6, sysInfo.getOsConfig());
			pstmt.setString(7, sysInfo.getOutputDirPath());
			pstmt.setString(8, sysInfo.getOutputDirFreeMem());
			pstmt.setObject(9, sysInfo.getUsedMemory());
			pstmt.setObject(10, sysInfo.getMaxMemory());
			
			int iInsert = pstmt.executeUpdate();
			
			connection.commit();
			return iInsert;
			
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
			}
			releaseResources(null, pstmt, null);
		}
		
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/dao/impl/StatusDAO.java                                                            $
 * 
 * 5     6/21/10 11:46a Lakshmanp
 * removed closing connection in finally
 * 
 * 4     6/18/10 12:24p Lakshmanp
 * removed parameterised constructor and added connection as parameter in all methods
 * 
 * 3     6/17/10 11:30a Lakshmanp
 * 
 * 2     6/17/10 10:30a Kedarr
 * Needs to be modified as per the Interface.
 * 
 * 1     6/17/10 10:22a Kedarr
 * 
 * 5     3/22/10 1:33p Mandar.vaidya
 * Modified the query INSERT_SYSTEM_INFO and the method updateSystemInformation for the newly added fields maxMemory and usedMemory in SYSTEM_INFO table.
 * 
 * 4     3/11/10 2:30p Mandar.vaidya
 * Added failedOver column in the query INSERT_PROGRESS_LEVEL.
 * 
 * 3     12/18/09 4:14p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/