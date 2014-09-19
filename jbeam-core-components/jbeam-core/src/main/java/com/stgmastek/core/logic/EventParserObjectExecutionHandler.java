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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IAppDao;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ObjectMapDetails;
import com.stgmastek.core.util.Constants.OBJECT_STATUS;

/**
 * Specialized class to handle batch jobs to be used with 
 * Event Parser sub-system i.e. job type = 'EV' 
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class EventParserObjectExecutionHandler extends BaseExecutionHandler{
	
	public EventParserObjectExecutionHandler() {
	}

	/**
	 *  The main execution method for Event Parser batch jobs. <p />
	 *  Way it works - 
	 *  <OL>
	 *  	<LI> Mark the object as 'UC' 
	 *  	<LI> If the configuration for setting the global parameter is set 
	 *  		 in the CORE_CONFIG, then it would first set the global 
	 *  		 parameters i.e. call to the stored procedure 
	 *  		 set_core_app_val_lis(?, ?, ?, ?, ?) 
	 *  	<LI> Prepares a procedure call <code>execute_batch_transaction(?, ?, ?, ?)</code>
	 *  		 and passes - 
	 *  		 <UL>
	 *  			<LI> policy number
	 *   			<LI> policy renew number
	 *   			<LI> object name
	 *   			<LI> task name
	 *  		 <UL>  
	 *  	<LI> Executes the stored procedure 
	 *  	<LI> After execution it checks the status (JOB_SCHEDULE.JOB_STATUS) of the object and sets the status
	 *  		 to the BatchObject.
	 *  		 If the object has marked itself '99' or if the status remains in 'UC' then it throws an exception. 
	 *  </OL>
	 * 
	 * @see PLSQLExecutionHandler#execute(BatchObject, ObjectMapDetails, BatchContext)
	 */
	
	public BatchObject execute(BatchObject batchObject, ObjectMapDetails objectMapDetails, BatchContext batchContext) throws BatchException {
		CallableStatement cstmt = null;

		// Call the set core app first to set the global parameters needed for
		// the event object
		
		//Create the connection for execution 
		Connection appConnection = null;
		IAppDao dao = DaoFactory.getAppDao();
		try{
			appConnection = batchContext.getApplicationConnection();
			//First mark the status as 'UC'
			dao.markUC(appConnection, batchObject);

			//Second Step would be to set the global parameters if needed  
			if(Constants.SET_GLOBAL_PARAMETERS){
				dao.setGlobalParameters(appConnection, batchContext, batchObject);
			}
			
			cstmt = appConnection.prepareCall("{call execute_batch_transaction(?, ?, ?, ?)}");
			cstmt.setObject(1, batchObject.getPolicyNo());
			cstmt.setObject(2, batchObject.getPolicyRenewNo());
			cstmt.setObject(3, batchObject.getObjectName());
			cstmt.setObject(4, batchObject.getTaskname());

			cstmt.execute();
			String afterExecutionStatus = dao.checkBatchObjectStatus(batchObject, appConnection);
			OBJECT_STATUS status;
			try {
				status = OBJECT_STATUS.resolve(afterExecutionStatus);
            } catch (IllegalArgumentException e) {
            	status = OBJECT_STATUS.FAILED;
            }
            batchObject.setStatus(status);
            if (status == OBJECT_STATUS.UNDERCONSIDERATION || status == OBJECT_STATUS.FAILED) {
            	throw new BatchException("Execution FAILED. Please check the table error log for further details");
            }
		} catch (SQLException sqe) {
			throw new BatchException(sqe);
		} finally {
			DaoFactory.getAppDao().releaseResources(null, cstmt, appConnection);
		}
		return batchObject;

	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/EventParserObjectExecutionHandler.java                                                             $
 * 
 * 8     7/13/10 2:00p Kedarr
 * Updated Javadoc
 * 
 * 7     7/13/10 1:43p Kedarr
 * Removed the sop.
 * 
 * 6     7/10/10 7:48p Kedarr
 * Modified to mark UC, set global parameters, and check status of the object after execution.
 * 
 * 5     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 4     3/09/10 2:49p Kedarr
 * Changes made to use java sql connection as now CConnection implements java sql connection. Also, Dao Factory is used to fetch the appropriate dao
 * 
 * 3     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/