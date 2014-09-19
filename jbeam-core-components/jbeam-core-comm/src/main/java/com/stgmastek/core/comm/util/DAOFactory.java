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

import org.apache.log4j.Logger;

import com.stgmastek.core.comm.server.dao.IBatchDAO;
import com.stgmastek.core.comm.server.dao.IConfigDAO;
import com.stgmastek.core.comm.server.dao.IStatusDAO;
import com.stgmastek.core.comm.server.dao.impl.ConfigDAO;
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
//	 * The public method to get the instance of the DAO class based on the 
//	 * case as passed
//	 * 
//	 * 
//	 * @param useCaseName
//	 * 		  The use case name for the factory to return an instance of 
//	 * 		  appropriate DAO
//	 * @return the appropriate DAO instance as per the useCaseName provided. 
//	 * 		   @see CommConstants.USE_CASES
//	 * @throws CommDatabaseException
//	 * 		   Exception thrown while creating the appropriate connection pool or 
//	 * 		   fetching the connection from the pool
//	 */
//	public BaseDAO getDAO(String useCaseName) throws CommDatabaseException{
//		
//		if(useCaseName.equals(CommConstants.USE_CASES.USER.name()))
//			return new UserDAO(ConnectionManager.getInstance().getDefaultConnection());
//		
//		if(useCaseName.equals(CommConstants.USE_CASES.BATCH.name()))
//			return new BatchDAO(ConnectionManager.getInstance().getDefaultConnection());
//		
//		if(useCaseName.equals(CommConstants.USE_CASES.STATUS.name()))
//			return new StatusDAO(ConnectionManager.getInstance().getDefaultConnection());
//		
//		if(useCaseName.equals(CommConstants.USE_CASES.CONFIG.name()))
//			return new ConfigDAO(ConnectionManager.getInstance().getDefaultConnection());
//		
//		return null;
//	}
	/**
	 * Returns the {@link IBatchDAO} implementation.
	 * This method uses the system property {@link com.stgmastek.core.comm.util.Constants.SYSTEM_KEY#BATCH_DAO} to identify
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
//		if (obj == null) {
//			batchDao = new BatchDAO();
//			if (logger.isInfoEnabled()) {
//				logger.info(Constants.SYSTEM_KEY.BATCH_DAO.toString());
//			}
//		} else {
			if (obj instanceof IBatchDAO) {
				batchDao = (IBatchDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IBatchDAO");
				}
//			} else {
//				logger.warn(className + " does not implement IBatchDAO. Relying on default implementation.");
//				batchDao = new BatchDAO();
//				if (logger.isInfoEnabled()) {
//					logger.info(Constants.SYSTEM_KEY.BATCH_DAO.toString());
//				}
			}
//		}
		return batchDao;
	}
	/**
	 * Returns the {@link IConfigDAO} implementation.
	 * This method uses the system property {@link com.stgmastek.core.comm.util.Constants.SYSTEM_KEY#CONFIG_DAO} to identify
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
	 * This method uses the system property {@link com.stgmastek.core.comm.util.Constants.SYSTEM_KEY#STATUS_DAO} to identify
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
//		if (obj == null) {
//			statusDao = new StatusDAO();
//			if (logger.isInfoEnabled()) {
//				logger.info(Constants.SYSTEM_KEY.STATUS_DAO.toString());
//			}
//		} else {
			if (obj instanceof IStatusDAO) {
				statusDao = (IStatusDAO) obj;
				if (logger.isInfoEnabled()) {
					logger.info("Using the class " + className + " for IStatusDAO");
				}
//			} else {
//				logger.warn(className + " does not implement IStatusDAO. Relying on default implementation.");
//				statusDao = new StatusDAO();
//				if (logger.isInfoEnabled()) {
//					logger.info(Constants.SYSTEM_KEY.STATUS_DAO.toString());
//				}
			}
//		}
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/DAOFactory.java                                                                            $
 * 
 * 5     6/21/10 11:35a Lakshmanp
 * commented getDAO method and added methods for each DAO.
 * 
 * 4     12/30/09 12:24p Mandar.vaidya
 * Removed warnings
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/