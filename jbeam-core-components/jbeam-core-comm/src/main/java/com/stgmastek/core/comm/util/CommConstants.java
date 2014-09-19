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

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Constants file for core communication systems 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class CommConstants {

	/** Private constructor to avoid outside instantiation */
	private CommConstants(){
	}
	
	/** The default connection pool name */
	public static final String DEFAULT_CONNECTION_POOL = "COMM_CORE";
	
	/** The default logging properties file name */
	public static final String DEFAULT_LOG4J_PROPERTIES = "log4j.properties";
	
	/** The default pool configuration file name */
	private static final String CONNECTION_POOL_CONFIG = "poolconfig.xml";
	
	/** The default pool configuration file */
	public static final File DEFAULT_CONNECTION_POOL_CONFIG = 
												new File(CONNECTION_POOL_CONFIG);   
	
	/** The default password encryption algorithm */
	public static final String PASSWORD_ENCRYPTY_ALGO = "SHA-1";
	
	/** The use cases within the Core Communication system */
	public static enum USE_CASES {USER, BATCH, STATUS, CONFIG} 
	
	/** 
	 * The instruction parameters for schedule batch in
	 * the Core Communication system 
	 */
	public static enum SCHEDULE_INSTRCUTION_PARAMS {
		BATCH_NAME, FREQUENCY, RECUR_EVERY, BATCH_RUN_DATE, WEEK_DAY
		, END_ON_DATE, END_ON_OCCURRENCE, SKIP_FLAG, SCHEDULE_DATE, KEEP_ALIVE
	} 
	
	/** Possible response types for a service */
	public static enum SERVICE_STATUS {OK, ERROR}
	
	/** Possible response types for a service */
	public static enum BATCH_LOCK_INDICATOR {O, L}
	
	/** Messages for interrupt batch */
	public static enum INTERRUPT_BATCH_MESSAGE {BSRUNBATCH, BSSTOBATCH, RUNEREPORT} 

	/** Default HOST */
	public static final String DEFAULT_HOST = "localhost";
	
	/** The constant for batch action BATCH LOCKED */
	public static final String BATCH_LOCKED = "BATCH LOCKED";
	
	/** The constant for batch action BATCH REQUESTED */
	public static final String BATCH_REQUESTED = "BATCH REQUESTED";
	
	/** The constant for batch action BATCH ERROR */
	public static final String BATCH_ERROR = "BATCH ERROR";
	
	/** Default PORT */
	public static final Integer DEFAULT_PORT = 10001;
	
	/** The installation code as derived from the CORE_CONFIG table */
	public static String INSTALLATION_CODE;
	
	/** The poller's waiting period when it is instructed to wait */
	public static Integer POLLING_WAIT_PERIOD;
	
	/**
	 * The format in which the global parameters are set before the execution 
	 * of a batch object
	 */
	public static final SimpleDateFormat BATCH_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy 23:59:59");
	
}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/CommConstants.java                                                                         $
 * 
 * 5     4/13/10 11:05a Mandar.vaidya
 * Adde enum INTERRUPT_BATCH_MESSAGE
 * 
 * 4     3/09/10 3:38p Mandar.vaidya
 * Added enum SCHEDULE_INSTRCUTION_PARAMS for schedule batch.
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/