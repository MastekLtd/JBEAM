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
package com.stgmastek.monitor.ws.util;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.ws.server.dao.IConfigDAO;
import com.stgmastek.monitor.ws.server.dao.IExecuteReportDAO;
import com.stgmastek.monitor.ws.server.dao.IMonitorDAO;
import com.stgmastek.monitor.ws.server.dao.IStatusDAO;
import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.dao.impl.ConfigDAO;
import com.stgmastek.monitor.ws.server.dao.impl.ExecuteReportDAO;
import com.stgmastek.monitor.ws.server.dao.impl.StatusDAO;
import com.stgmastek.monitor.ws.util.Constants.SYSTEM_KEY;

/**
 * 
 * The factory class for instantiating the DAO classes in the communication system
 * 
 * @author grahesh.shanbhag
 */
public final class DAOFactory {
	
	private static final Logger logger = Logger.getLogger(DAOFactory.class);
	private static IConfigDAO configDao;
	private static IStatusDAO statusDao;
	private static IMonitorDAO monitorDao;
	private static IUserDAO userDao;
	private static IExecuteReportDAO executeReportDAO;
	
//	/** The static singleton instance of the factory */
//	private static DAOFactory instance = new DAOFactory();
	
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
	
//	/**
//	 * The public method to get the instance of the DAO
//	 * 
//	 * @return the appropriate DAO instance as per the useCaseName provided. 
//	 * 		   @see Constants
//	 * @throws CommDatabaseException
//	 * 		   Exception thrown while creating the appropriate connection pool or 
//	 * 		   fetching the connection from the pool
//	 */
//	public BaseDAO getDAO() throws CommDatabaseException{
//		return new MonitorDAO(ConnectionManager.getInstance().getDefaultConnection());
//	}
	
	/**
	 * Returns the {@link IConfigDAO} implementation.
	 * This method uses the system property {@link SYSTEM_KEY#CONFIG_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IConfigDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return  {@link IConfigDAO}
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
	 * This method uses the system property {@link SYSTEM_KEY#STATUS_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IStatusDAO} as the
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
	 * Returns the {@link IMonitorDAO} implementation.
	 * This method uses the system property {@link SYSTEM_KEY#MONITOR_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IMonitorDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IMonitorDAO}
	 */
	public static synchronized IMonitorDAO getMonitorDAO() {
		if (monitorDao != null) return monitorDao;
		String className = System.getProperty(Constants.SYSTEM_KEY.MONITOR_DAO.getKey(), null);
		Object obj = instantiateClass(className);
//		if (obj == null) {
//			monitorDao = new MonitorDAO();
//			if (logger.isInfoEnabled()) {
//				logger.info(Constants.SYSTEM_KEY.MONITOR_DAO.toString());
//			}
//		} else {
			if (obj instanceof IMonitorDAO) {
				monitorDao = (IMonitorDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IMonitorDAO");
				}
//			} else {
//				logger.warn(className + " does not implement IMonitorDAO. Relying on default implementation.");
//				monitorDao = new MonitorDAO();
//				if (logger.isInfoEnabled()) {
//					logger.info(Constants.SYSTEM_KEY.MONITOR_DAO.toString());
//				}
			}
//		}
		return monitorDao;
	}
	
	/**
	 * Returns the {@link IUserDAO} implementation.
	 * This method uses the system property {@link SYSTEM_KEY#USER_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IUserDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IUserDAO}
	 */
	public static synchronized IUserDAO getUserDAO() {
		if (userDao != null) return userDao;
		String className = System.getProperty(Constants.SYSTEM_KEY.USER_DAO.getKey(), null);
		Object obj = instantiateClass(className);
//		if (obj == null) {
//			userDao = new UserDAO();
//			if (logger.isInfoEnabled()) {
//				logger.info(Constants.SYSTEM_KEY.USER_DAO.toString());
//			}
//		} else {
			if (obj instanceof IUserDAO) {
				userDao = (IUserDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IUserDAO");
				}
//			} else {
//				logger.warn(className + " does not implement IUserDAO. Relying on default implementation.");
//				userDao = new UserDAO();
//				if (logger.isInfoEnabled()) {
//					logger.info(Constants.SYSTEM_KEY.USER_DAO.toString());
//				}
			}
//		}
		return userDao;
	}

	/**
	 * Returns the {@link IExecuteReportDAO} implementation.
	 * This method uses the system property {@link SYSTEM_KEY#EXECUTE_REPORT_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IExecuteReportDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IExecuteReportDAO}
	 */
	public static synchronized IExecuteReportDAO getExecuteReportDAO() {
		if (executeReportDAO != null) return executeReportDAO;
		String className = System.getProperty(Constants.SYSTEM_KEY.EXECUTE_REPORT_DAO.getKey(), null);
		Object obj = instantiateClass(className);
		if (obj == null) {
			executeReportDAO = new ExecuteReportDAO();
			if (logger.isInfoEnabled()) {
				logger.info(Constants.SYSTEM_KEY.EXECUTE_REPORT_DAO.toString());
			}
		} else {
			if (obj instanceof IExecuteReportDAO) {
				executeReportDAO = (IExecuteReportDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IExecuteReportDAO");
				}
			} else {
				logger.warn(className + " does not implement IExecuteReportDAO. Relying on default implementation.");
				executeReportDAO = new ExecuteReportDAO();
				if (logger.isInfoEnabled()) {
					logger.info(Constants.SYSTEM_KEY.EXECUTE_REPORT_DAO.toString());
				}
			}
		}
		return executeReportDAO;
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/util/DAOFactory.java                                                                    $
 * 
 * 5     6/23/10 11:31a Lakshmanp
 * commented getinstance and getdao methods. added getxxxDAO() method for each dao.
 * 
 * 4     12/30/09 1:12p Grahesh
 * Correcting the creation of the callable statement string
 * 
 * 3     12/30/09 1:10p Grahesh
 * Correcting the creation of the callable statement string
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/