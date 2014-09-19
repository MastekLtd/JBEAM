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

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.interfaces.IExecutableBatchJob;
import com.stgmastek.core.util.BatchContext;

/**
 * This class acts like a pool that stores just one instance for all class that
 * implements {@link IExecutableBatchJob}.
 * 
 * This class returns the same instance of the class once created and does not
 * create and store multiple instances of the {@link IExecutableBatchJob}. This
 * means that the {@link IExecutableBatchJob#init(BatchContext)} and
 * {@link IExecutableBatchJob#destroy(BatchContext)} methods are called only
 * once during the entire lifetime of the job. Till the batch is being executed
 * all the jobs as and when encountered are instantiated and are kept alive in
 * the pool.
 * 
 * @author kedar.raybagkar
 * @since 4.0.0
 */
public final class ExecutableBatchJobPool {

	private static final Logger logger = Logger.getLogger(ExecutableBatchJobPool.class);

	private final HashMap<String, IExecutableBatchJob> jobs = new HashMap<String, IExecutableBatchJob>();

	private static ExecutableBatchJobPool instance;

	private static final Lock latch = new ReentrantLock();

	static {
		instance = new ExecutableBatchJobPool();
	}

	/**
	 * @param batchContext
	 */
	private ExecutableBatchJobPool() {
	}

	/**
	 * Returns the instance of the executable pool.
	 * 
	 * Creates the instance if not created earlier.
	 * 
	 * @param batchContext
	 * @return ExecutableBatchJobPool
	 */
	static ExecutableBatchJobPool getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	void destroy(BatchContext batchContext) {
		latch.lock();
		try {
			for (IExecutableBatchJob job : jobs.values()) {
				try {
					job.destroy(batchContext);
				} catch (Throwable e) {
					logger.error("Job " + job.getClass().getName() + " destroy method threw Exception", e);
				}
			}
			jobs.clear();
		} finally {
			latch.unlock();
		}
	}

	/**
	 * Loads and instantiates the {@link IExecutableBatchJob}.
	 * 
	 * @param className
	 *            name of the class that implements the IExecutableBatchJob
	 * @return IExecutableBatchJob
	 * @throws BatchException
	 */
	IExecutableBatchJob getJob(BatchContext batchContext, String className) throws BatchException {
		long time = System.currentTimeMillis();
		IExecutableBatchJob job = jobs.get(className);
		if (job != null) {
			if (logger.isEnabledFor(LogLevel.FINE)) {
				logger.log(LogLevel.FINE, "Returned the JOB in " + (System.currentTimeMillis() - time) + " millis.");
			}
			return job;
		}
		return instantiateJob(batchContext, className);
	}

	private IExecutableBatchJob instantiateJob(BatchContext batchContext, String className) throws BatchException {
		long time = System.currentTimeMillis();
		IExecutableBatchJob job = null;
		Class<?> clazz;
		Object obj;
		while (job == null) {
			job = jobs.get(className);
			if (job == null) {
				// latch.lock();
				if (latch.tryLock()) {
					try {
						job = jobs.get(className);
						if (job != null) {
							if (logger.isEnabledFor(LogLevel.FINE)) {
								logger.log(LogLevel.FINE, "Returned the JOB in " + (System.currentTimeMillis() - time) + " millis.");
							}
							return job;
						}
						clazz = Class.forName(className);
						obj = clazz.newInstance();
						if (!(obj instanceof IExecutableBatchJob))
							throw new BatchException("The executable batch job " + className + " is not an implementation of IExecutableBatchJob");

						job = (IExecutableBatchJob) obj;
						job.init(batchContext);
						jobs.put(className, job);
						if (logger.isEnabledFor(LogLevel.FINE)) {
							logger.log(LogLevel.FINE, "Instantiated the JOB in " + (System.currentTimeMillis() - time) + " millis.");
						}
						return job;
					} catch (ClassNotFoundException e) {
						throw new BatchException(e);
					} catch (InstantiationException e) {
						throw new BatchException(e);
					} catch (IllegalAccessException e) {
						throw new BatchException(e);
					} catch (RuntimeException e) {
						throw new BatchException("Job " + className + " was unable to instantiate", e);
					} finally {
						latch.unlock();
					}
				} // if tryLock
			} // if job == null
		} // while job is null
		if (logger.isEnabledFor(LogLevel.FINE)) {
			logger.log(LogLevel.FINE, "Returned the JOB in " + (System.currentTimeMillis() - time) + " millis.");
		}
		return job;
	}

}
