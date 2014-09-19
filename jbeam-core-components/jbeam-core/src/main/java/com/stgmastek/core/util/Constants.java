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
package com.stgmastek.core.util;

import java.io.File;
import java.util.Date;

/**
 * 
 * Constants class for the Core system
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public final class Constants {

	/**
	 * Private constructor to avoid any instantiation
	 */
	private Constants(){}

	/** Determines the mode in which the CORE is starting */
	public static String MODE = ""; 
	
	/** Constant for "DEV" */
	public static final String DEV = "DEV";
	
	/** Constant for "DEV" */
	public static final String PRE = "PRE";
	
	/** Default User */
	public static String DEFAULT_USER = ""; 
	
	/**
	 * The wait period for the system to poll the Process Request Engine 
	 * status for execution completion 
	 */
	public static Integer PRE_STATUS_CHECK_WAIT_PERIOD = 0; 
	
	/**
	 * Maximum number of listeners allowed in the Batch events or jobs  
	 */
	public static Integer BATCH_MAX_LISTENERS = 0;
	
	/**
	 * Maximum number of listeners allowed in the PRE / POST events jobs  
	 */
	public static Integer PRE_POST_MAX_LISTENERS = 0;
	
	/** 
	 * A convenient way to check whether global parameter needs to be set before 
	 * executing a batch object 
	 */
	public static Boolean SET_GLOBAL_PARAMETERS = false;
	
	/** 
	 * Upper limit to run future date batches. 
	 * If todays date is Sept 05 2009 17:01:50 then batch can be run 
	 * to a max of today + 3 days i.e. Sept 08 2009 17:01:50. 
	 * 
	 * Sept 08 2009 17:01:51 would not be allowed 
	 */
	public static Date MAX_BATCH_DATE = null; 
	
	/**
	 * Upper limit of the exception stack trace to be stored
	 */
	public static final Short MAX_EXCEPTION_TRACE_LENGTH = 4000; 
	
	/** The batch run date format to be used by the system */
	public static String BATCH_RUN_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	
	/** The date format of the batch job */
	public static String BATCH_JOB_DATE_FORMAT = "";
	
	/** 
	 * The properties file for JDBC pool for logging. 
	 * The constant is used only in the development mode
	 */
	public static final String LOG4J_PROP = "log4j.properties";
	
	/**
	 * The properties or configuration file for JDBC Pool
	 * The constant is used only in the development mode
	 */
	public static final String POOL_CONFIG_PROP = "poolconfig.xml";
	
	/**
	 * The static configuration file instance for JDBC Pool
	 * The constant is used only in the development mode
	 */
	public static final File POOL_CONFIG = new File(POOL_CONFIG_PROP);
	
	
	/**
	 * The end point URL of ICD
	 */
	public static String ICD_END_POINT_URL = null;
	
	/** 
	 * The userId for ICDService
	 */
	public static String ICD_SERVICE_USER_ID = null;
	
	/**
	 * The password for ICDService
	 */
	public static String ICD_SERVICE_PASSWORD = null;
	
	/**
	 * The number of threads to keep in the pool, even if they are idle
	 */
	public static String CORE_SIZE = null;
	
	/**
	 * The maximum number of threads to allow in the pool.
	 */
	public static String MAX_CORE_SIZE = null; 
	
	/** 
	 * The names of the pools configured in the JDBC Pool. 
	 * Used primarily to switch between the development and production mode
	 */
	public static enum POOL_NAMES {BATCH, APPLICATION};
	
	/** 
	 * Meta events within the Core system
	 * Meta events are 'other than the batch objects' 
	 * EX: the pre-processing events or post-processing events 
	 */
	public static enum META_EVENTS {PRE, POST};
	
	/** The Poller wait period for the polling of the monitor instruction */
	public static Integer POLLER_WAIT_PERIOD = 0;
	
	public static enum SYSTEM_KEY {
		/**
		 * Set the system property jbeam-com.stgmastek.core.dao.IAppDao.impl=&lt;your implementation of {@link com.stgmastek.core.dao.IAppDao}&gt;
		 */
		APP_DAO("jbeam-com.stgmastek.core.dao.IAppDao.impl"),
		/**
		 * Set the system property jbeam-com.stgmastek.core.dao.IBatchDao.impl=&lt;your implementation of {@link com.stgmastek.core.dao.IBatchDao}&gt;
		 */
		BATCH_DAO("jbeam-com.stgmastek.core.dao.IBatchDao.impl");
		
		private String key;
		
		private SYSTEM_KEY(String key) {
			this.key = key;
		}
		
		public String getKey() {
			return key;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("To override the default ");
			sb.append(this.name().replace('_', ' '));
			sb.append(" implementations add the following system property and define your own class: ");
			sb.append(key);
			sb.append("=<fully qualified classname>. (Make sure that it is in the classpath as well)");
			return sb.toString();
		}
	};
	
	public static enum MESSAGE_KEY {

		USER_ID("UserId"),
		BATCH_NO("BatchNo");
		
		private String key;
		
		private MESSAGE_KEY(String key) {
			this.key = key;
		}
		
		/**
		 * Returns the key defined by the MESSAGE_KEY enum.
		 * @return String
		 */
		public String getKey() {
			return key;
		}
	}

	
	/** 
	 * Batch can have endings for many reasons.
	 * <OL>
	 * <LI> BATCH_COMPLETED - COMPLETED successfully with or without errors 
	 * <LI> USER_INTERRUPTED - COMPLETED because of user interruption
	 * <LI> END_OF_TIME - COMPLETED because the end time for the batch is realized
	 * <LI> BATCH_FAILED - The batch has failed and stopped, because an object marked as 
	 * 		on-fail-exit = 'Y' has failed
	 * </OL>
	 * This variable is used to store the closure reason
	 */
	public static enum CLOSURE_REASON {BATCH_COMPLETED, USER_INTERRUPTED, END_OF_TIME, BATCH_FAILED, PRE_ISSUED_STOP};
	
	
	/** Constant to determine whether the logging into the text file is enabled */
	public static Boolean LOGGING_ENABLED = false;
	
	public static String LOG_FILE_PATH = "";
	
	/**
	 * Stores the system property of line separator.
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	/**
	 * <code>Enum</code> for keys against which the objects are stored in the Context.
     * <OL>
     * <LI> JBEAM_BATCH_INFO - Stores the object {@link BatchInfo} 
     * <LI> JBEAM_EXIT - Stores the exit flag <code>"Y"</code>.
     * <LI> JBEAM_EXIT_REASON - Stores the reason behind the exit.
     * <LI> JBEAM_BATCH_RUN_DATE - Stores the batch run date. 
     * <LI> JBEAM_ENTITY_PARAMS - Stores the object {@link EntityParams} 
     * <LI> JBEAM_OBJECT_MAP - Stores the {@link ObjectMapDetails}. 
     * <LI> JBEAM_BO_FAILED_EMAIL_COUNTER - Stores the counter for the failed batch objects counter}. 
     * </OL>
	 * 
	 */
	public static enum CONTEXT_KEYS {JBEAM_BATCH_INFO, JBEAM_EXIT, 
	    JBEAM_EXIT_REASON, JBEAM_BATCH_RUN_DATE, 
	    JBEAM_ENTITY_PARAMS, JBEAM_OBJECT_MAP, JBEAM_BO_FAILED_EMAIL_COUNTER, JBEAM_SCHEDULED_EXECUTABLE_LIST, JBEAM_BUSINESS_DATE};
	    
	
    /**
     * <code>Enum</code> for escalation levels that is stored against the object.
     * <OL>
     * <LI> CRITICAL 
     * <LI> HIGH
     * <LI> MEDIUM
     * <LI> LOW 
     * </OL>
     * 
     * One can define the email ids against these levels in the configuration table.
     */
	public static enum OBJECT_ESCALATION_LEVEL { CRITICAL, HIGH, MEDIUM, LOW };
	
	public static enum PROCESS_REQUEST_PARAMS {
		BATCH_RUN_DATE, BATCH_NAME, BATCH_TYPE, INSTRUCTION_LOG_SEQ, INSTRUCTING_USER, BUSINESS_DATE, BATCH_NO
	}
	
	public static enum BATCH_TYPE {
		DATE, SPECIAL
	}
	
	/**
	 * Escape char for parameter parsing in case of multiple column look up values.
	 * for later use.
	 */
	public static final char ESCAPE_CHAR = '/';
	
	/**
	 * Delimiter char for parameter parsing in case of multiple column look up values.
	 */
	public static final char DELIMITER_CHAR = '#';
	
	/**
	 * Name of the cache.
	 */
	public static enum CACHE {
		CACHE_NAME("com.mmpnc.product.jbeam"), CACHE_CONFIG_NAME("jbeam-ehcache.xml");
		
		private String id;

		private CACHE(String id) {
			this.id = id;
		}
		
		public String getID() {
			return id;
		}
	}
	
	public static enum OBJECT_STATUS {
		
		/**
		 * Failed '99' 
		 */
		FAILED("FAILED", "99"),
		/**
		 * Completed 'CO'
		 */
		COMPLETED("COMPLETED", "CO"),
		/**
		 * Under Consideration 'UC'
		 */
		UNDERCONSIDERATION("STARTED", "UC"),
		/**
		 * Suspended 'SP'
		 */
		SUSPENDED("SUSPENDED", "SP"),
		/**
		 * Pro-created 'PC'
		 */
		PROCREATED("SCHEDULED", "PC"),
		/**
		 * Failed Over 'FO'
		 */
		FAILEDOVER("FAILEDOVER", "FO"),
		/**
		 * 'NC'
		 */
		NC("SCHEDULED", "NC"),
		/**
		 * 'TR'
		 */
		TR("TR", "TR");
		
		private final String id;
		private final String oldId;

		private OBJECT_STATUS(String id, String oldId) {
			this.id = id;
			this.oldId = oldId;
		}

		public String getID() {
			return id;
		}
		
		public String getOldID() {
			return oldId;
		}
		
		public static OBJECT_STATUS resolve(String id) {
			for (OBJECT_STATUS status : OBJECT_STATUS.values()) {
				if (status.getID().equalsIgnoreCase(id) || status.getOldID().equalsIgnoreCase(id)) {
					return status;
				}
			}
			throw new IllegalArgumentException("Invalid Status. Could not resolve #" + id);
		}
	}
	
	public static enum ConfigConstants{
		CORE,
		JV,
		AUTH_HANDLER, 
		AUTH_FILTER_HANDLER;
	}
	
	public static enum THREAD_POOL{
		CORE_SIZE,
		MAX_CORE_SIZE
	}
	
	public enum PRE_SINGLETON_PLUGINS {
	    ReportGenerator;
	}

}


/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/Constants.java                                                                                      $
 * 
 * 20    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 19    4/23/10 1:07p Kedarr
 * Changes made to make the system properties keys into an enum.
 * 
 * 18    4/13/10 2:31p Kedarr
 * Added new enum for object escalation level
 * 
 * 17    4/06/10 5:08p Kedarr
 * corrected java doc for missing tags or improper tags due to change in method signatures.
 * 
 * 16    3/30/10 1:56p Kedarr
 * Added a new context key
 * 
 * 15    3/25/10 3:27p Kedarr
 * Added a new context key
 * 
 * 14    3/09/10 6:29p Kedarr
 * Added new keys for DAO factory
 * 
 * 13    2/25/10 10:34a Grahesh
 * Added a final variable for new line property
 * 
 * 12    2/25/10 10:29a Grahesh
 * Added context keys enum.
 * 
 * 11    1/22/10 4:05p Mandar.vaidya
 * modified the bacth run date format.
 * 
 * 10    1/06/10 1:59p Grahesh
 * Added PRE_ISSUED_STOP into the batch stop reason enum
 * 
 * 9     12/29/09 12:44p Grahesh
 * Added constants for  LOGGING_ENABLED & LOG_FILE_PATH
 * 
 * 8     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 7     12/24/09 2:31p Grahesh
 * Revised the logic for 
 * BATCH_RUN_DATE_FORMAT & BATCH_JOB_DATE_FORMAT
 * 
 * 6     12/23/09 3:56p Grahesh
 * Added enum for BATCH_OBJECT_TYPE
 * 
 * 5     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 4     12/21/09 5:13p Grahesh
 * New enum for CLOSURE_REASON
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/