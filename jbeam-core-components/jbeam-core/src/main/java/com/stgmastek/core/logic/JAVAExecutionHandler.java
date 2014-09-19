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

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.interfaces.IExecutableBatchJob;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * The specialized class to execute JAVA batch objects - job_schedule.job_type='JV'
 * This class execute Java batch executables that adheres or implements {@link IExecutableBatchJob}
 * The order in which the method are called - 
 * <OL>
 * 	<LI> {@link IExecutableBatchJob#init(BatchContext)} - the batch information supplied is 
 * 	  	 the current batch information. Note: It does not take into account any previous 
 * 		 revision information. Information of only the batch (with a revision) 
 * 	 	 that picks up the java job for execution would be supplied    
 * 	<LI> {@link IExecutableBatchJob#execute(BatchContext, BatchObject, ObjectMapDetails)} - the main execution method 
 * 	<LI> {@link IExecutableBatchJob#destroy(BatchContext)} - the destroy method
 * </OL>
 *   
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class JAVAExecutionHandler extends BaseExecutionHandler{

	public JAVAExecutionHandler() {
	}
	
	/**
	 * The method to execute a Java executable object
	 * The order in which the method are called - 
	 * <OL>
	 * 	<LI> {@link IExecutableBatchJob#init(BatchContext)} - the batch information supplied is 
	 * 	  	 the current batch information. Note: It does not take into account any previous 
	 * 		 revision information. Information of only the batch (with a revision) 
	 * 	 	 that picks up the java job for execution would be supplied    
	 * 	<LI> {@link IExecutableBatchJob#execute(BatchContext, BatchObject, ObjectMapDetails) } - the main execution method 
	 * 	<LI> {@link IExecutableBatchJob#destroy(BatchContext)} - the destroy method
	 * </OL>
	 * 
	 * @param batchContext
	 * 		  The context for the batch 
	 * @throws BatchException 
	 * 		   Any exception thrown during execution of the object wrapped as BatchException  
	 */
	
	public BatchObject execute(BatchObject batchObject, ObjectMapDetails objectMapDetails, BatchContext batchContext) throws BatchException{
		markUC(batchObject, batchContext);
		IExecutableBatchJob javaBatchObject = ExecutableBatchJobPool.getInstance().getJob(batchContext, objectMapDetails.getObjectName()); 
		javaBatchObject.execute(batchContext, batchObject, objectMapDetails);
		return batchObject;
	}
	
	/**
	 * Marks the object to UC.
	 * 
	 * @param batchContext
	 * @throws BatchException
	 */
	private void markUC(BatchObject batchObject, BatchContext batchContext) throws BatchException {
		IAppDao dao = DaoFactory.getAppDao();
		Connection con = null;
		try {
			con = batchContext.getApplicationConnection();
			Boolean b = dao.markUC( con, batchObject);
			if (!b.booleanValue()) {
				throw new BatchException("Unable to mark object as UC ", true);
			}
		} finally {
			dao.releaseResources(null, null, con);
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/JAVAExecutionHandler.java                                                                          $
 * 
 * 16    7/10/10 1:30p Kedarr
 * Class modified to make use of object map details object name to instantiate the class instead of batch executor task name.
 * 
 * 15    6/30/10 4:56p Kedarr
 * Changed the method signature of the mark UC method to accept the batch Object.
 * 
 * 14    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 13    4/19/10 4:53p Mandar.vaidya
 * Removed the exception block as the execute method throws Batch Exception that need not be catched but thrown outside.
 * 
 * 12    3/09/10 2:55p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 11    3/03/10 5:29p Grahesh
 * Added autoCommit true. The code needs to be re-verified at a  later point in time to bring the transactional commits/rollbacks outside of the code in consideration.
 * 
 * 10    3/03/10 3:21p Grahesh
 * added a new method to mark UC
 * 
 * 9     3/02/10 3:38p Grahesh
 * Added code to mark the object as UC before delegating the execution.
 * 
 * 8     1/13/10 4:16p Grahesh
 * Corrected the java doc comments
 * 
 * 7     1/13/10 4:14p Grahesh
 * Corrected the links in java doc comments
 * 
 * 6     1/07/10 6:04p Grahesh
 * Removed unwanted comments
 * 
 * 5     1/07/10 5:12p Grahesh
 * Addition to the signature of init for Batch Object
 * 
 * 4     12/28/09 6:31p Grahesh
 * Refined the way the call back methods are called .
 * 
 * 3     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/