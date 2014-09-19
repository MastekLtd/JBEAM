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

import com.stgmastek.core.comm.exception.CommDatabaseException;
import com.stgmastek.core.comm.server.vo.CReqInstruction;
import com.stgmastek.monitor.comm.client.BatchProgress;
import com.stgmastek.monitor.comm.client.MReqSystemInfo;


/** 
 * DAO for all batch status related operations 
 * 
 * @author Lakshman Pendrum
 * 
 */
public interface IStatusDAO extends IBaseDAO {


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
		public Integer interruptBatch(CReqInstruction instruction, Connection connection) 
				throws CommDatabaseException;

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
		 * @param connection
		 * 		  	connection object
		 * @return the progress 
		 * @throws CommDatabaseException 
		 * 		   Any database I/O exception 
		 */
		public BatchProgress getBatchProgress(Integer batchNo,Integer batchRevNo, Integer indicatorNo,
				Connection connection) throws CommDatabaseException ;
		
		/**
		 * Fetches the system information to pass it to the monitor system 
		 * 
		 * @param batchNo
		 * 		  The batch number 
		 * @param batchRevNo
		 * 		  The batch revision number 
 		 * @param connection
		 * 		  	connection object
		 * @return and instance of MReqSystemInfo to call the web service
		 * @throws CommDatabaseException
		 * 		   Any database I/O exception 
		 */
		public MReqSystemInfo getSystemInformation(Integer batchNo, Integer batchRevNo, Connection connection)
				throws CommDatabaseException ;		
	}

	/*
	* Revision Log
	* -------------------------------
	* $Header: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/dao/IStatusDAO.java 1     6/21/10 11:28a Lakshm $
	* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/ $
 * 
 * 1     6/21/10 11:28a Lakshmanp
 * initial version
	 * 
	*
	*
	*/
