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
package com.stgmastek.monitor.ws.server.dao;

import java.sql.Connection;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.vo.Config;
/**
 * DAO interface has all configuration details for Monitor System.
 * ConfigDAO class must implement this interface.
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $ 
 */

public interface IConfigDAO extends IBaseDAO {


		/**
		 * Returns the configuration settings for the MONITOR database as in the
		 * CONFIGURATION table
		 * 
		 * @return list of MConfig objects
		 * @throws CommDatabaseException
		 *             Any exception thrown during the database I/O
		 */
		public List<Config> getConfigurations(Connection connection) throws CommDatabaseException;

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/IConfigDAO.java                                                              $
 * 
 * 1     6/23/10 10:53a Lakshmanp
 * initial version
 * 
*
*/
