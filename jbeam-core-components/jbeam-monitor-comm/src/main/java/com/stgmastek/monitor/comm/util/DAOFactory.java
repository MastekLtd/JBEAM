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

import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.dao.IConfigDAO;
import com.stgmastek.monitor.comm.server.dao.IStatusDAO;
import com.stgmastek.monitor.comm.server.dao.impl.BatchDAO;
import com.stgmastek.monitor.comm.server.dao.impl.ConfigDAO;
import com.stgmastek.monitor.comm.server.dao.impl.StatusDAO;
/**
 * 
 * The factory class for instantiating the DAO classes in the communication system
 * 
 * @author grahesh.shanbhag
 */
public final class DAOFactory {
	
	private static final Logger logger = Logger.getLogger(DAOFactory.class);
	private static IBatchDAO batchDao;
	private static IConfigDAO configDao;
	private static IStatusDAO statusDao;
	
//	/** The static singleton instance of the factory */
//	private static DAOFactory instance = new DAOFactory();
//	
	/** Private constructor to avoid any initialization from the outside */
	private DAOFactory(){}
	
//	/**
//	 * Public static method to get the singleton instance
//	 * 
//	 * @return DAOFactory the singleton factory instance
//	 */
//	public static DAOFactory getInstance(){
//		return instance;
//	}
	
	
	/**
	 * Returns the {@link IBatchDAO} implementation.
	 * This method uses the system property {@link com.stgmastek.monitor.comm.util.Constants.SYSTEM_KEY#BATCH_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link BatchDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IBatchDAO}
	 */
	public static synchronized IBatchDAO getBatchDAO() {
		if (batchDao != null) return batchDao;
		String className = System.getProperty(Constants.SYSTEM_KEY.BATCH_DAO.getKey(), null);
		Object obj = instantiateClass(className);
		if (obj == null) {
			batchDao = new BatchDAO();
			if (logger.isInfoEnabled()) {
				logger.info(Constants.SYSTEM_KEY.BATCH_DAO.toString());
			}
		} else {
			if (obj instanceof IBatchDAO) {
				batchDao = (IBatchDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IBatchDAO");
				}
			} else {
				logger.warn(className + " does not implement IBatchDAO. Relying on default implementation.");
				batchDao = new BatchDAO();
				if (logger.isInfoEnabled()) {
					logger.info(Constants.SYSTEM_KEY.BATCH_DAO.toString());
				}
			}
		}
		return batchDao;
	}
	/**
	 * Returns the {@link IConfigDAO} implementation.
	 * This method uses the system property {@link com.stgmastek.monitor.comm.util.Constants.SYSTEM_KEY#CONFIG_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link ConfigDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IConfigDAO}
	 */
	public static synchronized IConfigDAO getConfigDAO() {
		if (configDao != null) return configDao;
		String className = System.getProperty(Constants.SYSTEM_KEY.CONFIG_DAO.getKey(), null);
		Object obj = instantiateClass(className);
		if (obj == null) {
			configDao = new ConfigDAO();
			if (logger.isInfoEnabled()) {
				logger.info(Constants.SYSTEM_KEY.CONFIG_DAO.toString());
			}
		} else {
			if (obj instanceof IConfigDAO) {
				configDao = (IConfigDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IConfigDAO");
				}
			} else {
				logger.warn(className + " does not implement IConfigDAO. Relying on default implementation.");
				configDao = new ConfigDAO();
				if (logger.isInfoEnabled()) {
					logger.info(Constants.SYSTEM_KEY.CONFIG_DAO.toString());
				}
			}
		}
		return configDao;
	}
	/**
	 * Returns the {@link IStatusDAO} implementation.
	 * This method uses the system property {@link com.stgmastek.monitor.comm.util.Constants.SYSTEM_KEY#STATUS_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link StatusDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IStatusDAO}
	 */
	public static synchronized IStatusDAO getStatusDAO() {
		if (statusDao != null) return statusDao;
		String className = System.getProperty(Constants.SYSTEM_KEY.STATUS_DAO.getKey(), null);
		Object obj = instantiateClass(className);
		if (obj == null) {
			statusDao = new StatusDAO();
			if (logger.isInfoEnabled()) {
				logger.info(Constants.SYSTEM_KEY.STATUS_DAO.toString());
			}
		} else {
			if (obj instanceof IStatusDAO) {
				statusDao = (IStatusDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IStatusDAO");
				}
			} else {
				logger.warn(className + " does not implement IStatusDAO. Relying on default implementation.");
				statusDao = new StatusDAO();
				if (logger.isInfoEnabled()) {
					logger.info(Constants.SYSTEM_KEY.STATUS_DAO.toString());
				}
			}
		}
		return statusDao;
	}
	/**
	 * Protected method to instantiate the given class.
	 * 
	 * @param className Class to be instantiated.
	 * @return Object
	 */
	static synchronized Object instantiateClass(String className) {
		Object obj = null;
		if (className != null) {
			try {
				Class<?> implClass = Class.forName(className);
				obj = implClass.newInstance();
			} catch (ClassNotFoundException e) {
				logger.error(e);
			} catch (InstantiationException e) {
				logger.error(e);
			} catch (IllegalAccessException e) {
				logger.error(e);
			}
		}
		return obj;		
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/DAOFactory.java                                                                      $
 * 
 * 7     6/21/10 11:42a Lakshmanp
 * modified ConfigDAO and StatusDAO typecast
 * 
 * 6     6/18/10 12:44p Lakshmanp
 * removed common method getDAO which returns dao object.added dao methods for each dao and same are added in constants class.
 * 
 * 5     6/17/10 12:33p Kedarr
 * Needs further modifications
 * 
 * 4     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/