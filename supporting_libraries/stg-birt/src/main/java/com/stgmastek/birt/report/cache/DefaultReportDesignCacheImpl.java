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

import java.io.InputStream;
import java.util.concurrent.locks.Lock;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

//import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.IReportRunnable;

import com.stgmastek.birt.report.exceptions.ReportServiceException;

/**
 * Default implementor of the EhCache.
 */
public class DefaultReportDesignCacheImpl implements IReportDesignCache {
//	private static final Logger logger = Logger.getLogger(DefaultReportDesignCacheImpl.class);
	private Cache cache;				// Default instance of Cache.
	
//	private final Lock latch   

	public DefaultReportDesignCacheImpl() {}

	/* (non-Javadoc)
	 * @see com.stgmastek.birt.report.cache.IReportDesignCache.IJBEAMCache#get(java.lang.String)
	 */
	public IReportRunnable get(String key) {
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		return (IReportRunnable) element.getObjectValue();
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.birt.report.cache.IReportDesignCache#put(com.stgmastek.core.interfaces.IExecutableBatchJob)
	 */
	public void put(String designFileIdentifier, IReportRunnable runnableReport) {
		cache.putIfAbsent(new Element(designFileIdentifier, runnableReport));
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.birt.report.cache.IReportDesignCache#create(com.stgmastek.core.util.BatchContext)
	 */
	public void create(String configFileName) {
		if(configFileName == null || configFileName.trim().length() == 0) {
			throw new ReportServiceException("Cache Configuration File cannot be null or empty");
		}
		
		try {
			InputStream is = DefaultReportDesignCacheImpl.class.getResourceAsStream(configFileName);
			CacheManager cacheManager = CacheManager.create( is );
			this.cache = cacheManager.getCache("ReportServiceCache");
			
		} catch(Exception e) {
			throw new ReportServiceException("Exception during cache creation : ["+e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.stgmastek.birt.report.cache.IReportDesignCache#shutdown(com.stgmastek.core.util.BatchContext)
	 */
	public void shutdown() {
		try {
			cache.dispose();
		} catch(Exception e) {
			throw new ReportServiceException("Exception during shutting down cache : ["+e.getMessage(), e);
		}
	}
}
