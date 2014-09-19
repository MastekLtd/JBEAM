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
import java.util.LinkedList;

import com.stgmastek.core.aspects.LogTime;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.ScheduledExecutable;

/**
 * This class serves as the base assigner class for all 
 * assignment handler classes
 * This class consists of 
 * <UL>
 * <LI> abstract method assign for the handlers to implement
 * <LI> public method to schedule the listener. 
 * 		Note: scheduling of listener is not based on any condition 
 * 		other than the number of listeners to spawn   
 * </UL>  
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public abstract class AssignerHandler{
	
	/**
	 * Method to do the actual assignment for the current internal cycle. 
	 * All handler classes needs to implement this method 
	 *  
	 * @param batchContext
	 * 		  The context for the batch 
	 * @param entityParams
	 * 		  The entity details under consideration   
	 * @return the actual number of listeners that are to be spawned 
	 * @throws BatchException
	 * 		   Any exception occurred during the assignment process. 
	 * 		   It includes any database I/O exception wrapped 
	 */
	protected abstract Integer assign(BatchContext batchContext,
			EntityParams entityParams) throws BatchException;

	/**
	 * Generic method to schedule the number of listeners as needed  
	 * <P />
	 * <b>This method is marked for logging of time as 'SCHEDULING'</b>
	 * 
	 * @param batchContext
	 * 		  The context for the batch 
	 * @param noOfListenersToSpawn
	 * 		  The number of listeners to be spawned 
	 * @return the list of {@link ScheduledExecutable} containing the details 
	 * 		   of scheduled listeners  
	 * @throws BatchException
	 * 		   Any exception occurred during the assignment process. 
	 * 		   It includes any database I/O exception wrapped 
	 */
	@LogTime
	public LinkedList<ScheduledExecutable> schedule(BatchContext batchContext,
			Integer noOfListenersToSpawn) throws BatchException{
		
		IBatchDao bDao = DaoFactory.getBatchDao();
		Connection con = null;
		LinkedList<ScheduledExecutable>  scheduledExecutables = null;
		try {
			con = batchContext.getBATCHConnection();
			scheduledExecutables = bDao.scheduleListeners(batchContext.getBatchInfo().getBatchNo(), batchContext.getBatchInfo().getExecutionStartTime(), 
					batchContext.getBatchInfo().getExecutionEndTime(), 
					noOfListenersToSpawn, con);
		} finally {
			bDao.releaseResources(null, null, con);
		}
		
		return scheduledExecutables;
	}	
	
	
	
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/AssignerHandler.java                                                                               $
 * 
 * 7     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 6     3/30/10 1:58p Kedarr
 * Changes made to return a Linked List
 * 
 * 5     3/09/10 2:48p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection.
 * 
 * 4     3/03/10 5:32p Grahesh
 * Removed batchContext.getBATCHConnection() from called IBatchDao constructor and added in called methods.
 * 
 * 3     12/23/09 11:54a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/