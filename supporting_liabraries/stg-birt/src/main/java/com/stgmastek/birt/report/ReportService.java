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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.IReportEngine;

import com.stgmastek.birt.report.beans.Report;
import com.stgmastek.birt.report.engine.config.EngineConfigWrapper;
import com.stgmastek.birt.report.exceptions.ReportServiceException;

/**
 * Implementation for {@link IReportService IReportService} contract. 
 *
 * @author Kedar Raybagkar
 * @version $Revision:  $
 * @since 1.0
 */
public final class ReportService implements IReportService {
	private static final Logger logger = Logger.getLogger(ReportService.class);
	
	private boolean initialized;
	private ExecutorService service;
	private GenericObjectPool pool = null;
	
	public IReportEngine borrowEngine() {
		IReportEngine reportEngine = null;
		try {
			reportEngine = (IReportEngine) pool.borrowObject();
		} catch(Exception e) {
			String msg = "Exception while fetching Engine Object from Pool : ["+e.getMessage()+"]";
			throw new ReportServiceException(msg, e);
		}
		return reportEngine;
	}
	
	public void releaseEngine(IReportEngine engine)  {
		try {
			pool.returnObject(engine);
		} catch (Exception e) {
			String msg = "Exception while releasing the engine object to pool : ["+e.getMessage()+"]";
			throw new ReportServiceException(msg, e);
		}
	}

	public boolean isShutdown() {
		if(service == null)
			return false;
		return service.isShutdown();
	}
	
	public boolean isTerminated() {
		if(service == null)
			return false;
		return service.isTerminated();
	}
	
	public void submit(RunnableReportTask report) {
		service.submit(report);
	}
	
	public void shutdown() {
		if (!isShutdown()) {
			service.shutdown();
		}
	}
	
	public List<Report> shutdownImmediate() {
		if (!isShutdown()) {
			shutdown();
		}
		List<Runnable> runnables = service.shutdownNow();
		ArrayList<Report> reports = new ArrayList<Report>();
		for (Runnable runnable : runnables) {
			RunnableReportTask task = (RunnableReportTask) runnable;
			reports.add(task.getReport());
		}
		return reports;
	}
	
	public void destroy() {
		if (isTerminated() == false) {
			String msg = "Unable to destroy the instance, as the Service is not yet Terminated.\n" + 
				"First call shutdown or shutdownImmediate and then call destroy";
			throw new ReportServiceException(msg);	
		}
		
		try {
			pool.close();
		} catch (Exception e) {}
		
		Platform.shutdown();
		pool = null;
	}
	
	public boolean isDestroyed() {
		if (initialized) {
			if (pool == null) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalStateException("ReportService not initialized.");
		}
	}
	
	public int getCurrentPendingQueueSize() {
		if (!isDestroyed()) {
			if (service instanceof ThreadPoolExecutor) {
				ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
				return executor.getQueue().size();
			}
		}
		return -1;
	}
	
	public int getCurrentActiveCount() {
		if (!isDestroyed()) {
			if (service instanceof ThreadPoolExecutor) {
				ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
				return executor.getActiveCount();
			}
		}
		return -1;
	}

	public ReportService(EngineConfigWrapper configWrapper) {
		initialize(configWrapper);
	}
	
	/**
	 * Initializes the service.
	 * @param config
	 * @throws ReportServiceException
	 */
	private void initialize(EngineConfigWrapper configWrapper) {
		//// Create Executor Service to hold ReportService Instance
		Integer size  = new Integer( (String) configWrapper.getEngineConfig().getProperty("threadPoolSize") );
		this.service = Executors.newFixedThreadPool(size.intValue());
		try {
			Platform.startup( configWrapper.getEngineConfig() );
		} catch (BirtException e) {
			throw new ReportServiceException(e.getMessage(), e);
		}

		//// Engine Object Pool
		pool = new GenericObjectPool( new BIRTEngineFactory(configWrapper.getEngineConfig()) );
		pool.setMaxActive(size);
		pool.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(5));
		initialized = true;
		logger.debug("ReportService pool initialized !! Total Pool size : ["+size+"]");
	}
}
