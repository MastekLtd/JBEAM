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
package com.stgmastek.monitor.ws.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.dao.IConfigDAO;
import com.stgmastek.monitor.ws.server.vo.Config;
import com.stgmastek.monitor.ws.util.BaseDAO;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all configuration related activity
 * 
 * @author mandar.vaidya
 * @since $Revision: 2 $ 
 */
public class ConfigDAO extends BaseDAO implements IConfigDAO {

	/** Query to get the configurations as set in the CONFIGURATION */
	private static final String GET_MONITOR_CONFIGURATIONS = "select code1 ,code2 , code3 , value , value_type from configuration order by code1, code2, code3";
	
	/** Public constructor takes no argument */
	public ConfigDAO() {
		super();
	}

	/**
	 * Returns the configuration settings for the MONITOR database as in the
	 * CONFIGURATION table
	 * @param connection
	 * 			  connection object
	 *  
	 * @return list of MConfig objects
	 * @throws CommDatabaseException
	 *             Any exception thrown during the database I/O
	 */
	public List<Config> getConfigurations(Connection connection) throws CommDatabaseException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(GET_MONITOR_CONFIGURATIONS);
			rs = pstmt.executeQuery();
			List<Config> configurations = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, Config.class);
			return configurations;
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/impl/ConfigDAO.java                                                          $
 * 
 * 2     6/23/10 10:56a Lakshmanp
 * removed parameterized constructor and added connection parameter to methods
 * 
 * 1     6/22/10 10:49a Lakshmanp
*
*
*/