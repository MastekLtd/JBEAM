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
 * Specialized handler class for assignment of Meta events or jobs i.e. the PRE and the POST jobs  
 * 
 * @author grahesh.shanbhag
 *
 */
public final class MetaEventsHandler extends AssignerHandler {

	/**
	 * Implemented method to do the actual assignment of the META events i.e. the PRE and POST jobs  
	 * 
	 * <P />
	 * <b>This method is marked for logging of time as 'ASSIGNMENT'</b>
	 * 
	 * @see AssignerHandler#assign(BatchContext, EntityParams)
	 */
	
	@LogTime
	protected Integer assign(BatchContext batchContext,
			EntityParams entityParams) throws BatchException {
		Integer noOfListenersToSpawn;
		IAppDao appDao = DaoFactory.getAppDao();
		Connection con = null;
		try {
			con = batchContext.getApplicationConnection();
			noOfListenersToSpawn = appDao.updatePrePostListing(batchContext.getBatchInfo().getBatchNo(),
					batchContext.getBatchInfo().getBatchRunDate(), 
					entityParams, 
					Constants.PRE_POST_MAX_LISTENERS,
					con);
			
		} finally {
			appDao.releaseResources(null, null, con);
		}
		
		return noOfListenersToSpawn;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/MetaEventsHandler.java                                                                             $
 * 
 * 7     6/15/10 5:57p Mandar.vaidya
 * Handled ConnectionLeakException from updatePrePostListing method by passing the connection object
 * 
 * 6     3/09/10 3:00p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 5     3/03/10 5:38p Grahesh
 * Removed batchContext.getApplicationConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 4     12/23/09 11:55a Grahesh
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