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
package com.stgmastek.core.dao;

import java.sql.Connection;

import com.stgmastek.core.aspects.Log;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.ProgressLevel;

/**
 * DAO Utility class for all static helper methods
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class DAOUtil {

	/** Private constructor to avoid any outside initialization */
	private DAOUtil() {
	}

	/**
	 * Utility method to update the progress level 
	 * 
	 * @param batchContext
	 * 		  The context for the batch  
	 * @param newEntry
	 * 		  Whether its a entry to insert or update  
	 * @return the indicator number that is created 
	 * @throws BatchException
	 * 		   Any database I/O exception 
	 */
	@Log
	public static Integer updateProgress(BatchContext batchContext, boolean newEntry)
			throws BatchException {
		
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		Integer batchNo = batchContext.getBatchInfo().getBatchNo();
		try {
			con = batchContext.getBATCHConnection();
			if(newEntry) {
				return bDao.initiateProgressLevel(ProgressLevel.getProgressLevel(batchNo), con);
			} else {
				bDao.updateProgressLevel(ProgressLevel.getProgressLevel(batchNo), con);
			}
		} finally {
			bDao.releaseResources(null, null, con);
		}
		return ProgressLevel.getProgressLevel(batchNo).getIndicatorNo();
	}	
	
	/**
	 * Utility method to lock / un-lock the system 
	 * This locking mechanism is to ensure that for a given environment only one batch is running at 
	 * any given point in time
	 * 
	 * @param batchContext
	 * 		  The context for the batch  
	 * @param toLock
	 * 	   	  Indicator whether to lock system or unlock it 
	 * @return true is the locking / unlocking is done successfully 
	 * @throws BatchException
	 * 	 	   Any database I/O exception 
	 */
	@Log
	public static Boolean lockProcessor(BatchContext batchContext, boolean toLock)
			throws BatchException {
		Long requestId = batchContext.getRequestParams().getRequestId();
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		try {
			con = batchContext.getBATCHConnection();
			if(toLock)
				return bDao.lockProcessor(requestId, con);
			else
				return bDao.unlockProcessor(requestId, con);
			
		} finally {
			bDao.releaseResources(null, null, con);
		}
	}	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/dao/DAOUtil.java                                                                                         $
 * 
 * 6     3/19/10 7:05p Mandar.vaidya
 * Changes made to not close the connection object in the DAO. Now the caller is responsible to close the connection.
 * 
 * 5     3/09/10 12:21p Kedarr
 * Changes made to make use of DaoFactory to fetch appropriate data source.
 * 
 * 4     3/03/10 5:32p Grahesh
 * Removed batchContext.getBATCHConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 3     12/29/09 12:43p Grahesh
 * Added annotation for logging to all methods.
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/