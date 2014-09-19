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
import java.sql.ResultSet;
import java.util.List;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.dao.IConfigDAO;
import com.stgmastek.monitor.comm.server.vo.MConfig;
import com.stgmastek.monitor.comm.util.BaseDAO;
import com.stgmastek.monitor.comm.util.MonitorMessage;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all configuration related activity
 * 
 * @author grahesh.shanbhag
 * 
 */
public class ConfigDAO extends BaseDAO implements IConfigDAO { 

	/** Query to get the configurations as set in the CONFIGURATION */
	private static final String GET_MONITOR_CONFIGURATIONS = "select code1 ,code2 , code3 , value , value_type from configuration order by code1, code2, code3";

	/** Query to insert the dead message into the DEAD_MESSAGE_QUEUE table */
	private static final String SET_DEAD_MESSAGE = "insert into dead_message_queue (id,i_o_mode,installation_code,message,param,error_description) values (?, ?, ?, ?, ?, ?)";
	
	/**Public constructor takes no argument */
	public ConfigDAO() {
		super();
	}

	/**
	 * Returns the configuration settings for the MONITOR as in the
	 * CONFIGURATION table
	 * 
	 * @return list of MConfig objects
	 * @throws CommDatabaseException
	 *         Any exception thrown during the database I/O
	 */
	public List<MConfig> getConfigurations(Connection connection) throws CommDatabaseException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_MONITOR_CONFIGURATIONS);
			rs = pstmt.executeQuery();
			List<MConfig> configurations = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, MConfig.class);
			return configurations;
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
	 * @return true if the insert was successful, false otherwise
	 * @throws CommDatabaseException
	 *         Any exception thrown during the database I/O
	 */
	public Boolean setDeadMessage(MonitorMessage message, Throwable t,Connection connection) 
										throws CommDatabaseException{
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(SET_DEAD_MESSAGE);
			pstmt.setObject(1, message.getId());
			pstmt.setString(2, message.getMode());
			pstmt.setObject(3, message.getInstallationCode());
			pstmt.setString(4, message.getMessage());
			pstmt.setString(5, message.getParam());
			pstmt.setString(6, t.getMessage());
			
			int i = pstmt.executeUpdate();
			
			return i == 0 ? false : true;
			
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
		
	} 
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/dao/impl/ConfigDAO.java                                                            $
 * 
 * 5     6/21/10 11:48a Lakshmanp
 * removed closing connection in finally
 * 
 * 4     6/18/10 12:24p Lakshmanp
 * removed parameterised constructor and added connection as parameter in all methods
 * 
 * 3     6/17/10 11:54a Lakshmanp
 * added DAO interface implementation
 * 
 * 2     6/17/10 10:30a Kedarr
 * Needs to be modified as per the Interface.
 * 
 * 1     6/17/10 10:22a Kedarr
 * 
 * 3     12/18/09 4:14p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/