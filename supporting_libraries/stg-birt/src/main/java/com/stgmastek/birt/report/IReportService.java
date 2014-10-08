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


package com.stgmastek.birt.report;

import java.util.List;

import org.eclipse.birt.report.engine.api.IReportEngine;

import com.stgmastek.birt.report.beans.Report;

/**
 * Contract of the Report Service.
 * 
 * Service is responsible to manage the life cycle of the BIRT runtime reporting platform. 
 *
 * @author Kedar Raybagkar
 * @version $Revision:  $
 * @since 1.0
 */
public interface IReportService {	
	
	/**
	 * Returns the runtime report engine from the pool.
	 * @return engine {@link IReportEngine}
	 */
	public IReportEngine borrowEngine();	
	
	/**
	 * Releases the engine back to the pool.
	 * @param engine previously borrowed object
	 */
	public void releaseEngine(IReportEngine engine);

	/**
	 * Returns true if a shutdown was invoked. 
	 * @return boolean Current shutdown status 
	 */
	public boolean isShutdown() ;
	
	
	public boolean isTerminated();
	
	/**
	 * Submits a runnable report task.
	 * @param report to be executed
	 */
	public void submit(RunnableReportTask report) ;
	
	/**
	 * Invokes shutdown.
	 * 
	 * After calling this method no new reports will be accepted but the service will 
	 * continue to process all the queued reports submitted till this method was invoked.
	 */
	public void shutdown() ;
	
	/**
	 * Immediately shutdowns the service.
	 * 
	 * The service is destroyed with this method. No new reports will be accepted 
	 * and the pending reports in the queue are returned in the form of a list.
	 * @return List of reports.
	 */
	public List<Report> shutdownImmediate() ;
	
	/**
	 * Destroys the instance.
	 */
	public void destroy() ;
	
	/**
	 * Returns true if destroyed.
	 * @return boolean
	 * @throws IllegalStateException if the method is called before initializing the service.
	 */
	public boolean isDestroyed();
	
	/**
	 * Returns the current pending queue size.
	 * 
	 * Please note that the pending size may be zero but the active task count can be greater than zero.
	 * 
	 * @return integer -1 if unable to determine in case, the service is destroyed.
	 * @see #getCurrentActiveCount()
	 */
	public int getCurrentPendingQueueSize();
	
	/**
	 * Returns the current threads that are active executing tasks.
	 * 
	 * @return int -1 if unable to determine.
	 */
	public int getCurrentActiveCount();
}
