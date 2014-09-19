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
package com.stgmastek.core.comm.server.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.dao.IConfigDAO;
import com.stgmastek.core.comm.util.BaseDAO;
import com.stgmastek.core.comm.util.CConfig;
import com.stgmastek.core.comm.util.CoreMessage;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all configuration related operations
 * 
 * @author grahesh.shanbhag
 * 
 */
public class ConfigDAO extends BaseDAO implements IConfigDAO {

	/** Query to get the configurations as set in the CORE_CONFIG */
	private static final String GET_CORE_CONFIGURATIONS = "select code1, code2, code3," +
			" value, value_type from configuration order by code1,code2, code3";
	
	/** Query to insert the dead message into the DEAD_MESSAGE_QUEUE table */
	private static final String SET_DEAD_MESSAGE = "insert into dead_message_queue (id,i_o_mode,message,param,error_description) values (?, ?, ?, ?, ?)";

	/** Public zero argument constructor */
	public ConfigDAO() {
		super();
	}

	/**
	 * Returns the configuration settings for the CORE as in the
	 * CORE_CONFIG table
	 * 
	 * @return list of CConfig objects
	 * @throws CommDatabaseException
	 *         Any exception thrown during the database I/O
	 */
	public List<CConfig> getConfigurations(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_CORE_CONFIGURATIONS);
			rs = pstmt.executeQuery();
			List<CConfig> configurations = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, CConfig.class);
			return configurations;
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
	
	/**
	 * Inserts a record into the DEAD_MESSAGE_QUEUE when found that the 
	 * message cannot be processed
	 * 
	 * @param message
	 * 		  The message
	 * 
	 * @param throwable
	 * 		  The throwable
	 * 
	 * @return true if the insert was successful, false otherwise
	 * @throws CommDatabaseException
	 *         Any exception thrown during the database I/O
	 */
	public Boolean setDeadMessage(CoreMessage message, Throwable throwable, Connection connection) 
										throws CommDatabaseException{
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(SET_DEAD_MESSAGE);
			pstmt.setObject(1, message.getId());
			pstmt.setString(2, message.getMode());
			pstmt.setString(3, message.getMessage());
			pstmt.setString(4, message.getParam());
			pstmt.setString(5, throwable.getMessage());
			int i = pstmt.executeUpdate();
			return i == 0 ? false : true;
		} catch (SQLException e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
		
	} 

}

/*
* Revision Log
* -------------------------------
* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/impl/ConfigDAO.java 1     6/21/10 11:29a La $
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 1     6/21/10 11:29a Lakshmanp
 * initial version
 * 
 * Initial Version
*
*
*/