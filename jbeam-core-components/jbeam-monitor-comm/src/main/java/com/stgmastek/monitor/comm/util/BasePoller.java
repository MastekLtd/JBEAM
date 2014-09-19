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
package com.stgmastek.monitor.comm.util;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommException;

/**
 * Base class for all pollers within the system. 
 * Declares the life cycle methods for the poller 
 *  
 * @author grahesh.shanbhag
 * 
 */
public abstract class BasePoller extends Thread {

	private static final Logger logger = Logger.getLogger("BasePoller");
	
//	/** 
//	 * A dedicated connection only for the poller. 
//	 * The poller would always hold this connection during its polling needs. 
//	 * It would not request for any new connection, nor does it have to. 
//	 * In safe shutdown of the poller, the destroy method (@see {@link BasePoller#destroy()}) 
//	 */
//	protected CConnection dedicatedConnection = null;
	
	/**
	 * An indicator which can be set by other thread or processes to mark the poller to stop 
	 * once the indicator is set to true i.e. STOP_POLLING = true
	 */
	protected volatile boolean STOP_POLLING = false;
	
	/**
	 * Constructor that takes a connection 
	 */
	protected BasePoller() {
//		this.dedicatedConnection = dedicatedConnection;
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
			freeResources();
		}
	}

	/**
	 * The initialization method. 
	 * This method should create the polling task and then assign it to the 
	 * pollingExecutable. Usually a stored procedure. 
	 * 
	 * @throws CommException
	 * 		   Any database related exception thrown during fetching of 
	 * 		   connection, creation of the callable statement and its 
	 * 		   execution  
	 * 
	 */
	protected abstract void init() throws CommException;

	/**
	 * The execute method that contains the logic of the polling 
	 * 
	 * @throws CommException
	 * 		   Any database related exception thrown during the execution
	 * 		   of the callable statement
	 * 
	 */
	protected abstract void execute() throws CommException;

	/**
	 * The destroy that would be called in normal shutdown of the poller
	 * 
	 * @throws CommException
	 * 		   Any database related exception closing of the database resources.
	 * 
	 */
	protected void freeResources(){
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/BasePoller.java                                                                      $
 * 
 * 4     3/12/10 4:40p Mandar.vaidya
 * Changes made for not closing the jdbc connection as it is called from the collators.
 * 
 * 3     3/12/10 4:27p Kedarr
 * Removed unnecessary prepared statements from the base poller
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/