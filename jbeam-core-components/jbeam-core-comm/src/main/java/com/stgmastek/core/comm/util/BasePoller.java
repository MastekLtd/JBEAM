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
package com.stgmastek.core.comm.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.stgmastek.core.comm.exception.CommException;

/**
 * Base class for all pollers within the system. 
 * Declares the life cycle methods for the poller 
 *  
 * @author grahesh.shanbhag
 * 
 */
public abstract class BasePoller implements Runnable {

	private BlockingQueue<Runnable> queue;

	private ThreadPoolExecutor executor;
	
	private static final Logger logger = Logger.getLogger(BasePoller.class);
	

	/**
	 * An indicator which can be set by other thread or processes to mark the poller to stop 
	 * once the indicator is set to true i.e. STOP_POLLING = true
	 */
	protected boolean STOP_POLLING = false;
	
	/**
	 * Constructor that takes a connection 
	 * 
	 */
	protected BasePoller() {
		int noOfListeners = Integer.parseInt(Configurations.getConfigurations().getConfigurations("CORE", "BATCH_LISTENER", "MAX_LISTENERS"));
		queue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(noOfListeners, noOfListeners, 10, TimeUnit.MINUTES, queue);
	}
	
	public final ThreadPoolExecutor getExecutor() {
		return executor;
	}

	/**
	 * A convenient method to stop the poller
	 */
	public void stopPoller(){
		STOP_POLLING = true;
		executor.shutdown();
	}	
	
	/**
	 * The run method. 
	 * 
	 * @see Runnable#run()
	 */
	
	public void run() {
		try {
			startPoll();
		} catch (Exception e) {
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
			//Loop till there is an instruction to stop polling
			while (!STOP_POLLING) {
				try {
					int messages = execute();
					if ( messages <= 0) {
						if (messages < 0) {
							if (logger.isInfoEnabled()) {
								logger.info("Poller would poll again in next " + (CommConstants.POLLING_WAIT_PERIOD / 1000) + 
									" seconds");
							}
						} else {
							if (logger.isInfoEnabled()) {
								logger.info("No messages in the queue o_queue. " +
										"Poller would poll again in next " + (CommConstants.POLLING_WAIT_PERIOD / 1000) + 
								" seconds");
							}
						}
						try {
							Thread.sleep(CommConstants.POLLING_WAIT_PERIOD);
						} catch (InterruptedException e) {
						}					
					}
				} finally {
				}
			}
		} catch (Exception e) {
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
	 * @return size of records if the size is <= 0 then the poller will wait for the seconds specified. 
	 */
	protected abstract int execute() throws CommException;

	/**
	 * The destroy that would be called in normal shutdown of the poller
	 * 
	 * @throws CommException
	 * 		   Any database related exception closing of the database resources.
	 * 
	 */
	protected void freeResources(){
		executor.shutdownNow();
		while (!executor.isTerminated()) {
		}
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/BasePoller.java                                                                            $
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/