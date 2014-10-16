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
package com.stgmastek.core.comm.server.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.dao.IStatusDAO;
import com.stgmastek.core.comm.server.vo.CReqInstruction;
import com.stgmastek.core.comm.util.BaseDAO;
import com.stgmastek.core.comm.util.DatabaseAgnosticCandidate;
import com.stgmastek.monitor.comm.client.BatchProgress;
import com.stgmastek.monitor.comm.client.MReqSystemInfo;
import com.stgmastek.util.ResultSetMapper;

/** 
 * DAO class for all batch status related operations 
 * 
 * @author grahesh.shanbhag
 * 
 */
public class StatusDAO extends BaseDAO implements IStatusDAO {

	/** Query to INSERT data into I_QUEUE table */
	@DatabaseAgnosticCandidate
	private static final String INSERT_QUEUE = "insert into i_queue " +
			"(id, message, param)" +
			" values (?, ?, ?)"; 
	
	private static final String i_queue_seq_call = "{call [$SSMA_sp_get_nextval_I_QUEUE_SEQ](?)}";
	
	/** Query to get batch progress data from PROGRESS_LEVEL table */
	private static final String GET_BATCH_PROGRESS = "SELECT batch_no," +
			" batch_rev_no, indicator_no, PRG_LEVEL_TYPE, PRG_ACTIVITY_TYPE, CYCLE_NO, STATUS," +
			" start_datetime, end_datetime, failed_over FROM PROGRESS_LEVEL " +
			" WHERE batch_no = ? AND batch_rev_no = ? AND indicator_no = ?";

	/** Query to get the system information */
	private static final String GET_SYSTEM_INFORMATION = "select batch_no" +
			",batch_rev_no, java_version, pre_version, os_config, output_dir_path" +
			",output_dir_free_mem, max_memory, used_memory " +
			" from system_info " +
			" where batch_no = ? and batch_rev_no = ?";
	
	/** Public zero argument constructor */
	public StatusDAO() {
		super();
	}

	/**
	 * Interrupts the batch proceedings with the given instruction 
	 * 
	 * @param instruction
	 * 		  The instruction
	 * @param connection
	 * 		  	connection object
	 * @return 1 if the record was inserted in the table 
	 * 		   0 otherwise
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception
	 */
	public Integer interruptBatch(CReqInstruction instruction, Connection connection) throws CommDatabaseException{
		PreparedStatement pstmt = null;
		try{
			
			CallableStatement callable = connection.prepareCall(i_queue_seq_call);
			callable.registerOutParameter(1, java.sql.Types.INTEGER);
			callable.execute();
			int requestId=callable.getInt(1);
			pstmt = connection.prepareStatement(INSERT_QUEUE);
			pstmt.setInt(1, requestId);
			pstmt.setString(2, instruction.getMessage());
			pstmt.setString(3, instruction.getParam());
			int iInsert = pstmt.executeUpdate();
			connection.commit();
			return iInsert;
		}catch(SQLException sqe){
			throw new CommDatabaseException(sqe);
		}finally{
			releaseResources(null, pstmt, null);
		}
		
	}

	/**
	 * Gets the batch progress data from BATCH_PROGRESS 
	 * and stores in {@link BatchProgress}.
	 * 
	 * @param batchNo
	 * 		  The batch no
	 * @param batchRevNo
	 * 		  The batch revision no
	 * @param indicatorNo
	 * 		  The indicator no
	 * @return the progress 
	 * @throws CommDatabaseException 
	 * 		   Any database I/O exception 
	 */
	public BatchProgress getBatchProgress(Integer batchNo,
				Integer batchRevNo, Integer indicatorNo, Connection connection) 
		throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = connection.prepareStatement(GET_BATCH_PROGRESS);
			pstmt.setInt(1, batchNo);
			pstmt.setInt(2, batchRevNo);
			pstmt.setInt(3, indicatorNo);
			
			rs = pstmt.executeQuery();
			BatchProgress batchProgressData = ResultSetMapper.getInstance().mapSingleRecord(rs, BatchProgress.class); 
			return batchProgressData;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		} catch (InstantiationException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new CommDatabaseException(e);
		} catch (InvocationTargetException e) {
			throw new CommDatabaseException(e);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}finally{
			releaseResources(rs, pstmt, null);
		}		
	}
	
	/**
	 * Fetches the system information to pass it to the monitor system 
	 * 
	 * @param batchNo
	 * 		  The batch number 
	 * @param batchRevNo
	 * 		  The batch revision number 
	 * @return and instance of MReqSystemInfo to call the web service
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception 
	 */
	public MReqSystemInfo getSystemInformation(Integer batchNo, Integer batchRevNo, Connection connection)
													throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_SYSTEM_INFORMATION);
			pstmt.setInt(1, batchNo);
			pstmt.setInt(2, batchRevNo);
			
			rs = pstmt.executeQuery();
			MReqSystemInfo batchProgressData = 
				ResultSetMapper.getInstance().mapSingleRecord(rs, MReqSystemInfo.class);
			return batchProgressData;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new CommDatabaseException(e);
		} catch (InstantiationException e) {
			throw new CommDatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new CommDatabaseException(e);
		} catch (InvocationTargetException e) {
			throw new CommDatabaseException(e);
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}		
	}		
}

/*
* Revision Log
* -------------------------------
* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/impl/StatusDAO.java 1     6/21/10 11:29a La $
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 1     6/21/10 11:29a Lakshmanp
 * initial version
 * 
 * Initial Version
*
*
*/