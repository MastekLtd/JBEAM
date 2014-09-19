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

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Constants file for monitor services systems 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class Constants {
	
	/** Private constructor to avoid outside instantiation */
	private Constants(){}
	
	/** The default connection pool name */
	public static final String DEFAULT_CONNECTION_POOL = "MONITOR_WS";
	
	/** The default logging properties file name */
	public static final String DEFAULT_LOG4J_PROPERTIES = "log4j.properties";
	
	/** The default pool configuration file name */
	private static final String CONNECTION_POOL_CONFIG = "poolconfig.xml";
	
	/** The default pool configuration file */
	public static final File DEFAULT_CONNECTION_POOL_CONFIG = new File(CONNECTION_POOL_CONFIG);   
	
	/** The default password encryption algorithm */
	public static final String PASSWORD_ENCRYPTY_ALGO = "SHA-1";
	
	/** The encoding type */
	public static final String ENCODING_TYPE = "UTF-8";
	
	/** The use cases within the Monitor WS system */
	public static enum USE_CASES {USER, BATCH, STATUS, CONFIG} 
	
	/** 
	 * The instruction parameters for schedule batch in
	 * the Core Communication system 
	 */
	public static enum SCHEDULE_BATCH_PARAMS {
		FREQUENCY, RECUR_EVERY, WEEK_DAY, 
		END_ON_DATE, END_ON_OCCURRENCE, SKIP_FLAG, KEEP_ALIVE
	} 
	
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
	
	/** 
	 * An indicator to the ALL PROCESSES that runs indefinitely should check this variable
	 * if there is an instruction to bring down the entire communication system
	 * It includes Poller / Client Book
	 */
	public static Boolean SHOULD_CONTINUE = true; 
	
	/** The poller's waiting period when it is instructed to wait */
	public static Integer POLLING_WAIT_PERIOD = 0;
	
	/** 
	 * The services stack as set in the config table MONITOR_CONFIG. 
	 * Any new entry in the config would need an entry in this enumerated data type
	 */
	public static enum SERVICES_STACK {BATCH_SERVICES, STATUS_SERVICES};		
	
	/** 
	 * The client stubs stack as set in the MONITOR_CONFIG. 
	 */
	public static enum CLIENT_STUB_STACK {USER_SERVICES, BATCH_SERVICES, STATUS_SERVICES};		
	
	/**
	 * JBEAM specific message for run batch 
	 */
	public static final String RUN_BATCH_MSG  = "BSRUNBATCH";
	public static final String STOP_BATCH_MSG = "BSSTOBATCH";
	public static final String RUN_REPORT_MSG = "RUNEREPORT";
	
	/** 
	 * The database operations. 
	 */
	public static enum DB_OPERATIONS {INSERT, DELETE };
	
	/**
	 * The format in which the global parameters are set before the execution 
	 * of a batch object
	 */
	public static final SimpleDateFormat BATCH_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy 23:59:59");
	
	/**
	 * The format in which the global parameters are set before the execution 
	 * of a batch object
	 */
	public static final SimpleDateFormat JBEAM_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	
	/** The error string to throw when calendar details not found. */
	public static final String CALENDAR_DETAILS_NOT_FOUND = "Calendar details not found";
	
	/** The error string to throw when listener details not found. */
	public static final String LISTENER_DETAILS_NOT_FOUND = "Listener details not found";
	
	/** The error string to throw when installation details not found. */
	public static final String INSTALLATION_DETAILS_NOT_FOUND = "Installation details not found";
	
	/** The error string to throw when batch details not found. */
	public static final String BATCH_DETAILS_NOT_FOUND = "Batch details not found";
	
	/** The error string to throw when instruction details not found. */
	public static final String INSTRUCTION_DETAILS_NOT_FOUND = "Instruction details not found";

	/** The error string to throw when instruction details not found. */
	public static final String SCHEDULE_CANCELLATION_FAILED = "Schedule is already cancelled in core schema";

	/** The error string to throw when execute report details not found. */
	public static final String EXECUTE_REPORT_DETAILS_NOT_FOUND = "Execute report details not found";
	
	/** The error string to throw when instruction log sequence number is null. */
	public static final String INSTRUCTION_LOG_SEQUENCE_NUMBER_IS_NULL = "Instruction log sequence number is null";

	/** The error string to throw when requested schedule data number is null. */
	public static final String REQ_SCHEDULE_IS_NULL = "Requested schedule data is null";

	/** The error string to throw when user details not found. */
	public static final String USER_DETAILS_NOT_FOUND = "User details not found";
	
	/** The error string to throw when answer is invalid. */
	public static final String INVALID_ANSWER = "Invalid answer.";
	
	/** The error string to throw when password is invalid. */
	public static final String INVALID_PASSWORD = "Invalid password.";
	
	/** The error string to throw when user id is invalid. */
	public static final String INVALID_USER_ID = "Invalid user id.";
	
	/** The error string to throw when access denied to system. */
	public static final String ACCESS_DENIED = "Access Denied.";
	
	/** The error string to throw when user id is not registered. */
	public static final String USER_ID_NOT_REGISTERED = "User id not yet registered.";

	public static final String BLANK_STRING = "";
	
	/** The context key constant used to hold the execute report list */
	public static final String EXECUTE_REPORT_LIST = "EXECUTE_REPORT_LIST";
	
	/** The context key constant used to hold the report details */
	public static final String REPORT = "REPORT";
	
	/** The context key constant used to hold the report parameter list */
	public static final String REPORT_PARAMETER_LIST = "REPORT_PARAMETER_LIST";
	
	/** The context key constant used to hold the execute report details */
	public static final String EXECUTE_REPORT = "EXECUTE_REPORT";
		
	public static final String DATATYPE_H = "H";
	public static final String DATATYPE_D = "D";
	public static final String DATATYPE_DT = "DT";
	public static final String DATATYPE_S = "S";
	
	public static final String PDF = "PDF";

	/** The context key constant used to hold the Error list */
	public static final String ERROR_LIST = "ERROR_LIST";
	
	/** The context key constant used to hold the execute report data insert status */
	public static final String  REPORT_DATA_STATUS= "REPORT_DATA_STATUS";
	
	/** The context key constant used to hold the report parameter details */
	public static final String REPORT_PARAMETER = "REPORT_PARAMETER";
	
	/** The context key constant used to hold the execute report name details */
	public static final String  REPORT_DATA= "REPORT_DATA";
	
	public static final String STUCK_LIMIT = "2";
	public static final String STUCK_MAX_LIMIT = "10";
	public static final String STATIC_DYNAMIC_FLAG = "S";
	public static final String REPORT_FLAG = "COnlineReports";
	public static final String REPORT_STATE_FLAG_Q = "Q";
	public static final String CACHE = "CACHE";
	
	public static final String REPORT_SERVER_PATH_MASTER_CODE ="REPORT_SERVER_PATH";
	public static final String OFFLINE ="OFFLINE";
	public static final String REPORT_MASTER ="REPORT_MASTER";
	public static final String MAIL ="MAIL";
	public static final String REPORTS_SCHEDULING ="REPORTS_SCHEDULING";
	public static final String REPORT_STATUS_LIST ="REPORT_STATUS_LIST";

	/** The error string to throw when report parameters is invalid. */
	public static final String REPORT_PARAMETER_ERROR = "Report parameter is invalid";
	
	/** The error string to throw when user has insufficient rights. */
	public static final String INSUFFICIENT_USER_RIGHTS_ERROR = 
												"User has insufficient rights.";
	
	/** The error string to throw when report name is invalid. */
	public static final String REPORT_NAME_ERROR = "Report name is invalid.";
	
	/** The error string to throw when schedule report fails. */
	public static final String SCHEDULE_REPORT_ERROR = "Schedule report failed.";
	
	/** 
	 * The error string to throw when an exception is thrown while retrieving 
	 * report master data. 
	 */
	public static final String REPORT_MASTER_ERROR = 
								"Exception while retrieving report master data";
	
	public static final String DATATYPE_TS = "TS";
	public static final String PRINT_TYPE = "printType";
	public static final String PRINT_TYPE_L = "L";
	public static final String PRINT_TYPE_LANDSCAPE = "Landscape";
	public static final String PRINT_TYPE_POTRAIT = "Potrait";
	public static final String UPDATE_REQUEST_STATUS = "UPDATE_REQUEST_STATUS";
	public static final String UPDATE_SCHEDULE_STATUS = "UPDATE_SCHEDULE_STATUS";
	public static final String STATUS_Y = "Y";
	
	public static final String MONITOR = "MONITOR";
	
	public static final String FAILURE_FLAG = "99";
	public static final String SUCCESS00_FLAG = "00";
//
//	/** Constant for end reason as User Cancelled */
//	public static final String USER_CANCELLED = "User Cancelled";

	public static enum SCHEDULE_STATUS {
		ACTIVE("A", "Active"), COMPLETED("F", "Completed"), TERMINATED("X",
				"Cancelled by PRE"), USER_CANCELLED("C", "Cancelled by user");

		private final String id;
		private final String description;

		private SCHEDULE_STATUS(String id, String description) {
			this.id = id;
			this.description = description;
		}

		public String getID() {
			return this.id;
		}

		public String getDescription() {
			return this.description;
		}

		public static SCHEDULE_STATUS resolve(String id) {
			for (SCHEDULE_STATUS status : values()) {
				if (status.getID().equals(id)) {
					return status;
				}
			}
			throw new IllegalArgumentException("Unable to resolve #" + id
					+ " against the defiend status.");
		}
	}

	
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	public static enum COMMAND {
		startup("-startup"), shutdown("-shutdown");
		
		private String command;
		
		COMMAND(String command) {
			this.command = command;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		
		public String toString() {
			return command;
		}
		
		/**
		 * Returns a COMMAND corresponding to the name given.
		 * @param name
		 * @return COMMAND
		 */
		public static COMMAND resolve(String name) {
			if (startup.toString().equals(name)) {
				return startup;
			} else if (shutdown.toString().equals(name)) {
				return shutdown;
			}
			throw new IllegalArgumentException();
		}
	}
	
	public static enum SYSTEM_KEY {
		CONFIG_DAO("jbeam-com.stgmastek.monitor.ws.server.dao.configdaoimpl"),
		STATUS_DAO("jbeam-com.stgmastek.monitor.ws.server.dao.statusdaoimpl"),
		MONITOR_DAO("jbeam-com.stgmastek.monitor.ws.server.dao.monitordaoimpl"),
		USER_DAO("jbeam-com.stgmastek.monitor.ws.server.dao.userdaoimpl"),
		EXECUTE_REPORT_DAO("jbeam-com.stgmastek.monitor.ws.server.dao.executereportdaoimpl");
		
		
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
	
	/**
	 * Escape character to be used while parsing value columns.
	 */
	public static final char ESCAPE_CHAR = '/';
	
	/**
	 * Delimiter character to be used for paring value columns.
	 */
	public static final char DELIMITER_CHAR = '#';
}




/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/util/Constants.java                                                                     $
 * 
 * 10    7/13/10 2:52p Mandar.vaidya
 * Removed the constant DEFAULT_PASSWORD and added the constant ENCODING_TYPE.
 * 
 * 9     6/30/10 2:25p Lakshmanp
 * Added constant DEFAULT_PASSWORD
 * 
 * 8     6/23/10 11:29a Lakshmanp
 * added enum SYSTEM_KEY its being used in DAOFactory
 * 
 * 7     2/10/10 10:42a Grahesh
 * added new database operations enum
 * 
 * 6     12/30/09 3:55p Grahesh
 * Implementation for stopping the batch
 * 
 * 5     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/