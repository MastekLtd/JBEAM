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

import java.io.File;

/**
 * Constants file for monitor communication systems 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class CommConstants {
	
	/** Private constructor to avoid outside instantiation */
	private CommConstants(){}
	
	/** The default connection pool name */
	public static final String DEFAULT_CONNECTION_POOL = "MONITOR_COMM";
	
	/** The default logging properties file name */
	public static final String DEFAULT_LOG4J_PROPERTIES = "log4j.properties";
	
	/** The default pool configuration file name */
	private static final String CONNECTION_POOL_CONFIG = "poolconfig.xml";
	
	/** The default pool configuration file */
	public static final File DEFAULT_CONNECTION_POOL_CONFIG = new File(CONNECTION_POOL_CONFIG);   
	
	/** The default password encryption algorithm */
	public static final String PASSWORD_ENCRYPTY_ALGO = "SHA-1";
	
	/** The use cases within the Monitor Communication system */
	public static enum USE_CASES {USER, BATCH, STATUS, CONFIG} 
	
	/** The batch operation within the Monitor Communication system */
	public static enum BATCH_OPERATION {BSADDBATCH, BSUPDBATCH} 

	/** The batch log operation within the Monitor Communication system */
	public static enum BATCH_LOG_OPERATION {BSADDBALOG, BSUPDBALOG}
	
	/** The batch progress level operation within the Monitor Communication system */
	public static enum BATCH_PROGRESS_LEVEL_OPERATION {SSADDBAPRG, SSUPDBAPRG} 
	
	/** 
	 * The message that would be returned when the queue is empty 
	 * and NO_DATA_FOUND is met 
	 */
	public static final String VOID_MESSAGE = "0000000000";
	
	/** Possible response types for a service */
	public static enum SERVICE_STATUS {OK, ERROR}
	
	/** Default host */
	public static final String DEFAULT_HOST = "localhost";
	
	/** Default port */
	public static final Integer DEFAULT_PORT = 10001;
	
	/** The poller's waiting period when it is instructed to wait */
	public static Integer POLLING_WAIT_PERIOD = 0;

}





/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/CommConstants.java                                                                   $
 * 
 * 4     3/19/10 3:02p Mandar.vaidya
 * Added enum BATCH_LOG_OPERATION.
 * 
 * 3     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/