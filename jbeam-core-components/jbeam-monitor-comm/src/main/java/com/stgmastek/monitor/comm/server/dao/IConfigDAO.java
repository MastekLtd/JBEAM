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
package com.stgmastek.monitor.comm.server.dao;

import java.sql.Connection;
import java.util.List;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.vo.MConfig;
import com.stgmastek.monitor.comm.util.MonitorMessage;
/**
 * IConfigDAO for all configuration related activity
 * 
 * @author Lakshman Pendrum
 * @since 
 */

public interface IConfigDAO extends IBaseDAO {

		/**
		 * Returns the configuration settings for the MONITOR as in the
		 * CONFIGURATION table
		 * 
		 * @return list of MConfig objects
		 * @throws CommDatabaseException
		 *         Any exception thrown during the database I/O
		 */
		public List<MConfig> getConfigurations(Connection connection) throws CommDatabaseException;
		
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
		public Boolean setDeadMessage(MonitorMessage message, Throwable t,Connection connection) throws CommDatabaseException; 
		

	/*
	* Revision Log
	* -------------------------------
	* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monito $
 * 
 * 2     6/18/10 12:20p Lakshmanp
 * added connection as parameter in all methods
 * 
 * 1     6/17/10 11:12a Lakshmanp
	*
	*
	*/
}
