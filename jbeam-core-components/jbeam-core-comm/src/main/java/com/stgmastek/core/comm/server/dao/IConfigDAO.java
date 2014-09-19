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
package com.stgmastek.core.comm.server.dao;
import java.sql.Connection;
import java.util.List;

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.util.CConfig;
import com.stgmastek.core.comm.util.CoreMessage;
/**
 * DAO for all configuration related operations.
 * 
 * @author Lakshman Pendrum
 * 
 */
public interface IConfigDAO extends IBaseDAO {

		/**
		 * Returns the configuration settings for the CORE as in the
		 * CORE_CONFIG table
		 * 
 		 * @param connection
		 * 		  	connection object
		 * @return list of CConfig objects
		 * @throws CommDatabaseException
		 *         Any exception thrown during the database I/O
		 */
		public List<CConfig> getConfigurations(Connection connection) throws CommDatabaseException ;
		
		/**
		 * Inserts a record into the DEAD_MESSAGE_QUEUE when found that the 
		 * message cannot be processed
		 * 
		 * @param message
		 * 		  The message
		 * @param throwable
		 * 		  The throwable
		 * @param connection
		 * 		  connection object 
		 * @return true if the insert was successful, false otherwise
		 * @throws CommDatabaseException
		 *         Any exception thrown during the database I/O
		 */
		public Boolean setDeadMessage(CoreMessage message, Throwable throwable, Connection connection) 
											throws CommDatabaseException; 

	}

	/*
	* Revision Log
	* -------------------------------
	* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/IConfigDAO.java 1     6/21/10 11:28a Lakshm $
	* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 1     6/21/10 11:28a Lakshmanp
 * initial version
	 * 
	 * 
	*
	*
	*/
