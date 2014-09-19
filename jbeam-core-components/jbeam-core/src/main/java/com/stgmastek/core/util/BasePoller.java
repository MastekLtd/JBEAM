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
package com.stgmastek.core.util;

import org.apache.log4j.Logger;

import com.stgmastek.core.exception.BatchException;


/**
 * Base class for all pollers within the system. 
 * Declares the life cycle methods for the poller 
 *  
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public abstract class BasePoller extends Thread {

	
	/**
	 * An indicator which can be set by other thread or processes to mark the poller to stop 
	 * once the indicator is set to true i.e. STOP_POLLING = true
	 */
	protected boolean STOP_POLLING = false;
	
	private static final Logger logger = Logger.getLogger(BasePoller.class);
	
	/**
	 * The batch context
	 */
	protected BatchContext context = null;
	
	/**
	 * Constructor that takes a connection 
	 * 
	 * @param context
	 * 		  The context of the batch 
	 */
	protected BasePoller(BatchContext context) {
		this.context = context;
	}
	
	/**
	 * A convenient method to stop the poller
	 */
	public void stopPoller(){
		STOP_POLLING = true;
	}
	
	/**
	 * The run method. 
	 * @see Runnable#run()
	 */
	
	public void run() {
		try {
			startPoll();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Method to actually start the polling activities. 
	 * The methods are called in the following order 
	 * 1. <i>init()</i>  
	 * 2. <i>execute()</i>  
	 * 3. <i>freeResources()</i>  
	 */
	void startPoll() {
		try {
			init();
			execute();
		} catch (Exception e) {
			logger.error(e);
		}finally{
		}
	}

	/**
	 * The initialization method. 
	 * This method should create the polling task and then assign it to the 
	 * pollingExecutable. Usually a stored procedure. 
	 * 
	 * @throws BatchException
	 * 		   Any database related exception thrown during fetching of 
	 * 		   connection, creation of the callable statement and its 
	 * 		   execution  
	 * 
	 */
	protected abstract void init() throws BatchException;

	/**
	 * The execute method that contains the logic of the polling 
	 * 
	 * @throws BatchException
	 * 		   Any database related exception thrown during the execution
	 * 		   of the callable statement
	 * 
	 */
	protected abstract void execute() throws BatchException;

	/**
	 * Returns the BatchContext.
	 * @return BatchContext
	 */
	public BatchContext getBatchContext() {
	    return context;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BasePoller.java                                                                                     $
 * 
 * 7     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 6     3/12/10 5:40p Mandar.vaidya
 * Organized imports
 * 
 * 5     3/09/10 5:40p Kedarr
 * Removed dedicated connection and free resources method as were in-appropriate.
 * 
 * 4     2/24/10 5:04p Grahesh
 * Added a method to return the batch context. Sub classes can now make use of the batch context.
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/