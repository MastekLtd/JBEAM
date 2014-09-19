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
package com.stgmastek.core.dao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.stgmastek.core.exception.InvalidConfigurationException;
import com.stgmastek.core.util.Constants;

/**
 * DaoFactory is responsible to provide the implementation of {@link IBatchDao} and {@link IAppDao}.
 *
 * Always get the appropriate DAO using one of its methods.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class DaoFactory {

	private static final Logger logger = Logger.getLogger(DaoFactory.class);
	
	private static final Lock latch = new ReentrantLock();
	
	private static IAppDao appDao;
	
	private static IBatchDao batchDao;
	
	/**
	 * Returns the {@link IAppDao} implementation.
	 * This method uses the system property {@link com.stgmastek.core.util.Constants.SYSTEM_KEY#APP_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link AppDAO} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IAppDao}
	 */
	public static IAppDao getAppDao() {
		if (appDao != null) return appDao;
		return instantiateAppDao();
	}
	
	/**
	 * Returns the instance of IAppDao.
	 * @return IAppDao
	 */
	private static synchronized IAppDao instantiateAppDao() {
		while (appDao == null) {
			if (latch.tryLock()) {
				try {
					if (appDao == null) {
						String className = System.getProperty(Constants.SYSTEM_KEY.APP_DAO.getKey(), null);
						Object obj;
						try {
							obj = instantiateClass(className);
						} catch (InvalidConfigurationException e) {
							logger.fatal("Invalid Configuration", e);
							throw e;
						}
//						if (obj == null) {
//							appDao = new AppDAO();
//							if (logger.isInfoEnabled()) {
//								logger.info(Constants.SYSTEM_KEY.APP_DAO.toString());
//							}
//						} else {
						if (obj instanceof IAppDao) {
							appDao = (IAppDao) obj;
							if (logger.isInfoEnabled()) {
								logger.info("Using the class " + className + " for IAppDao");
							}
							return appDao;
						} else {
							logger.fatal(className + " does not implement IAppDao.");
							throw new InvalidConfigurationException(className + " does not implement IAppDao.");
						}
//						}
					}
				} finally {
					latch.unlock();
				}
			} // if tryLock()
		} // While appDao is null
		return appDao;
	}
	
	/**
	 * Returns the {@link IBatchDao} implementation.
	 * This method uses the system property {@link com.stgmastek.core.util.Constants.SYSTEM_KEY#BATCH_DAO} to identify
	 * the implementation class. In case of any error during instantiation return the {@link IBatchDao} as the
	 * default implementation.
	 * 
	 * Once the class is instantiated then the same class is returned.
	 * 
	 * @return {@link IBatchDao}
	 */
	public static IBatchDao getBatchDao() {
		if (batchDao != null) return batchDao;
		return instantiateBatchDao();
	}
	
	/**
	 * Returns the instance of IBatchDao.
	 * @return IBatchDao
	 */
	private static IBatchDao instantiateBatchDao() {
		while (batchDao == null) {
			if (latch.tryLock()) {
				try {
					if (batchDao == null) {
						String className = System.getProperty(Constants.SYSTEM_KEY.BATCH_DAO.getKey(), null);
						Object obj;
						try {
							obj = instantiateClass(className);
						} catch (InvalidConfigurationException e) {
							logger.fatal("Invalid Configuration", e);
							throw e;
						}
//						if (obj == null) {
//							batchDao = new BatchDAO();
//							if (logger.isInfoEnabled()) {
//								logger.info(Constants.SYSTEM_KEY.BATCH_DAO.toString());
//							}
//						} else {
						if (obj instanceof IBatchDao) {
							if (logger.isInfoEnabled()) {
								logger.info("Using the class " + className + " for IBatchDao");
							}
							batchDao = (IBatchDao) obj;
						} else {
							logger.fatal(className + " does not implement IBatchDao.");
							throw new InvalidConfigurationException(className + " does not implement IBatchDao interface");
						}
//						}
					}
				} finally {
					latch.unlock();
				}
			}
		}
		return batchDao;
	}

	/**
	 * Protected method to instantiate the given class.
	 * 
	 * @param className Class to be instantiated.
	 * @return Object
	 */
	static Object instantiateClass(String className) throws InvalidConfigurationException {
		Object obj = null;
		if (className != null) {
			try {
				Class<?> implClass = Class.forName(className);
				obj = implClass.newInstance();
			} catch (ClassNotFoundException e) {
				throw new InvalidConfigurationException(className + "  could not be instantiated. Invalid Configuration", e);
			} catch (InstantiationException e) {
				throw new InvalidConfigurationException(className + "  could not be instantiated. Invalid Configuration", e);
			} catch (IllegalAccessException e) {
				throw new InvalidConfigurationException(className + "  could not be instantiated. Invalid Configuration", e);
			}
		}
		return obj;		
	}
}
