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
package com.stgmastek.core.logic;

import java.sql.Connection;

import com.stgmastek.core.aspects.LogTime;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.EntityParams;

/**
 * Specialized handler class for assignment of Batch events or jobs  
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class BatchEventsHandler extends AssignerHandler{
 
	/**
	 * Implementation method to do the assignment for batch jobs 
	 * 
	 * <b>This method is marked for logging of time as 'ASSIGNMENT'</b>
	 * 
	 * @see AssignerHandler#assign(BatchContext, EntityParams)
	 */
	
	@LogTime
	protected Integer assign(BatchContext batchContext,
			EntityParams entityParams) throws BatchException{
		IAppDao dao = DaoFactory.getAppDao();
		Integer noOfListenersToSpawn;
		Connection con = null;
		try {
			con = batchContext.getApplicationConnection();
			noOfListenersToSpawn = dao.updateBatchListing(batchContext.getBatchInfo().getBatchNo(),
					batchContext.getBatchInfo().getBatchRunDate(), 
					entityParams, 
					Constants.BATCH_MAX_LISTENERS, con);
		} finally {
			dao.releaseResources(null, null, con);
		}
		return noOfListenersToSpawn;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/BatchEventsHandler.java                                                                            $
 * 
 * 7     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 6     3/09/10 2:48p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection.
 * 
 * 5     3/03/10 5:38p Grahesh
 * Removed batchContext.getApplicationConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 4     12/23/09 11:54a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 3     12/18/09 11:34a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/