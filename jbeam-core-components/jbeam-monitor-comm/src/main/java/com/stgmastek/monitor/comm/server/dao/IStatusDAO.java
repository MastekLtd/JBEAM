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

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.server.vo.BatchProgress;
import com.stgmastek.monitor.comm.server.vo.MReqSystemInfo;
/**
 * IStatusDAO for all Status related activities.
 * 
 * @author Lakshman Pendrum
 * 
 */

public interface IStatusDAO extends IBaseDAO{

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
	public Integer addBatchProgress(BatchProgress batchProgress,Connection connection) throws CommDatabaseException;
	 
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
	public Integer updateBatchProgress(BatchProgress batchProgress,Connection connection) throws CommDatabaseException;	

	/**
	 * Sets the system information on which the batch is run
	 * 
	 * @param sysInfo
	 * 		  The system information 
	 * @return 1, if the record is inserted successfully, false otherwise 
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception 
	 */
	public Integer updateSystemInformation(MReqSystemInfo sysInfo,Connection connection) throws CommDatabaseException;


}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Moni $
 * 
 * 2     6/18/10 12:20p Lakshmanp
 * added connection as parameter in all methods
 * 
 * 1     6/17/10 11:19a Lakshmanp
 * 
 * 
*
*/