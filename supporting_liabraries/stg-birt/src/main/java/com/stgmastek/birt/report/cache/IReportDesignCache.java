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
package com.stgmastek.birt.report.cache;

import org.eclipse.birt.report.engine.api.IReportRunnable;

/**
 * Interface that defines the caching implementation needs.
 *
 * @author kedar.raybagkar
 */
public interface IReportDesignCache {

	/**
	 * Creates the cache for the first time.
	 * Called only once while startup. 
	 */
	public void create(String configFileName);
	
	/**
	 * Shutdowns the cache.
	 * Called only once while destroying.
	 */
	public void shutdown();
	
	/**
	 * Puts the instantiated <code>IReportRunnable</code> in the cache with it's fully qualified identifier as key.
	 * @param designFileIdentifier key
	 * @param runnableReport
	 */
	public void put(String designFileIdentifier, IReportRunnable runnableReport);
	
	/**
	 * Returns the {@link IReportRunnable} identified against the given key.
	 * @param key class name.
	 * @return IReportRunnable
	 */
	public IReportRunnable get(String key);
}
