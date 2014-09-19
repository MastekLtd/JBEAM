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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import stg.utils.CDate;

import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.DatabaseException;
import com.stgmastek.core.exception.InvalidConfigurationException;
import com.stgmastek.core.logic.ExecutionHandler;

/**
 * Specialized class for handling any request to the configurations as set in
 * the CORE_CONFIG table Implements Singleton pattern
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class Configurations {

	private static final Lock latch = new ReentrantLock();

	private static final Logger logger = Logger.getLogger(Configurations.class);

	private enum MESSAGE_TYPE {
		ERRORS, WARNINGS
	}

	/** Static private instance variable */
	private static Configurations instance = new Configurations();

	/** Private map to initially populate and later retrieve values from */
	private static final HashMap<String, HashMap<String, HashMap<String, String>>> configurationsMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();

	/**
	 * Private constructor to avoid outside instantiation
	 */
	private Configurations() {
	}

	/**
	 * Returns the single instance of the class
	 * 
	 * @return the single instance of the class
	 */
	public static Configurations getConfigurations() {
		return instance;
	}

	/**
	 * Load / Reloads the configurations
	 * 
	 * @param connection
	 *            the connection to the BATCH schema
	 * @return Configurations the refreshed instance of the
	 *         {@link Configurations}
	 * @throws DatabaseException
	 *             Any database I/O exception occurred
	 */
	public Configurations loadConfigurations(Connection connection)
			throws DatabaseException {
		if (configurationsMap.size() == 0) {
			latch.lock();
			try {
				if (configurationsMap.size() == 0) {
					IBatchDao dao = DaoFactory.getBatchDao();
					List<CConfig> list = dao.getConfigurations(connection);
					loadConfigurationMap(list);
					return instance;
				}
			} finally {
				latch.unlock();
			}
		}
		return instance;
	}

	/**
	 * Private method that would populate the map.
	 * 
	 * @param list
	 *            the list of {@link CConfig} instances
	 */
	private void loadConfigurationMap(List<CConfig> list) {

		HashMap<String, HashMap<String, HashMap<String, String>>> returnMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();

		for (CConfig entry : list) {
			String code1 = entry.getCode1();
			String code2 = entry.getCode2();
			String code3 = entry.getCode3();
			String value = entry.getValue();

			if (returnMap.containsKey(code1)) {
				HashMap<String, HashMap<String, String>> code1Map = returnMap
						.get(code1);
				if (!code1Map.containsKey(code2))
					code1Map.put(code2, new HashMap<String, String>());
				code1Map.get(code2).put(code3, value);
			} else {
				returnMap.put(code1,
						new HashMap<String, HashMap<String, String>>());
				returnMap.get(code1).put(code2, new HashMap<String, String>());
				returnMap.get(code1).get(code2).put(code3, value);
			}
		}
		configurationsMap.putAll(returnMap);
		Map<MESSAGE_TYPE, ArrayList<String>> all = validateConfigurations();
		List<String> errors = all.get(MESSAGE_TYPE.ERRORS);
		if (errors.size() > 0) {
			for (String str : errors) {
				logger.fatal(str);
			}
		}
		List<String> warnings = all.get(MESSAGE_TYPE.WARNINGS);
		for (String str : warnings) {
			logger.warn(str);
		}

		if (errors.size() > 0) {
			configurationsMap.clear();
			throw new InvalidConfigurationException(
					"There are errors in configuration. Check the above FATAL messages. Batch will not proceed.");
		}
	}

	/**
	 * Public method to get configurations as set using the code1 supplied
	 * 
	 * @param code1
	 *            the code1 value CORE_CONFIG.BCC_CODE1
	 * @return HashMap<String, HashMap<String, String>> the configurations as a
	 *         map for the supplied CODE 1
	 */
	public HashMap<String, HashMap<String, String>> getConfigurations(
			String code1) {
		if (code1 == null) {
			return null;
		}
		return configurationsMap.get(code1);
	}

	/**
	 * Public method to get configurations as set using the code1, code2
	 * supplied
	 * 
	 * @param code1
	 *            the code1 value CORE_CONFIG.BCC_CODE1
	 * @param code2
	 *            the code2 value CORE_CONFIG.BCC_CODE
	 * @return HashMap<String, String> the configurations as a map
	 */
	public HashMap<String, String> getConfigurations(String code1, String code2) {
		HashMap<String,HashMap<String,String>> code1Map = getConfigurations(code1);
		if (code1Map == null) {
			return null;
		}
		if (code2 == null) {
			return null;
		}
		return code1Map.get(code2);
	}

	/**
	 * Public method to get configurations as set using the code1, code2, code3
	 * supplied
	 * 
	 * @param code1
	 *            the code1 value CORE_CONFIG.BCC_CODE1
	 * @param code2
	 *            the code2 value CORE_CONFIG.BCC_CODE2
	 * @param code3
	 *            the code3 value CORE_CONFIG.BCC_CODE3
	 * @return String the value for the supplied information
	 */
	public String getConfigurations(String code1, String code2, String code3) {
		HashMap<String, String> code2Map = getConfigurations(code1, code2);
		if (code2Map == null) {
			return null;
		}
		if (code3 == null) {
			return null;
		}
		return code2Map.get(code3);
	}

	/**
	 * Returns list of errors and warnings in a map. Error is identified by key
	 * {@link MESSAGE_TYPE#ERRORS} and warnings are identified by key
	 * {@link MESSAGE_TYPE#WARNINGS}
	 * 
	 * @return map of errors and warnings.
	 */
	private Map<MESSAGE_TYPE, ArrayList<String>> validateConfigurations() {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> warnings = new ArrayList<String>();

		String str = getConfigurations("CORE", "BATCH_LISTENER",
				"MAX_LISTENERS");
		if (str == null) {
			errors.add("CORE:BATCH_LISTENER:MAX_LISTENERS is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= 0) {
					errors
							.add("CORE:BATCH_LISTENER:MAX_LISTENERS must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:BATCH_LISTENER:MAX_LISTENERS must be a numeric value indicating the maximum listener count.");
			}
		}

		str = null; // Just to be on the safer side.
		str = getConfigurations("CORE", "PRE_POST_LISTENER", "MAX_LISTENERS");
		if (str == null) {
			errors
					.add("CORE:PRE_POST_LISTENER:MAX_LISTENERS is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= 0) {
					errors
							.add("CORE:PRE_POST_LISTENER:MAX_LISTENERS must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:PRE_POST_LISTENER:MAX_LISTENERS must be a numeric value indicating the maximum listener count.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "CALENDAR_CLASS", "JV");
		if (str == null || "".equals(str)) {
			errors.add("CORE:CALENDAR_CLASS:JV is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "CRITICAL", "EMAIL");
		if (str == null || "".equals(str)) {
			warnings
					.add("CORE:CRITICAL:EMAIL is not configured therefore will not be able to send any critical priority emails.");
		}

		str = null;
		str = getConfigurations("CORE", "HIGH", "EMAIL");
		if (str == null || "".equals(str)) {
			warnings
					.add("CORE:HIGH:EMAIL is not configured therefore will not be able to send any high priority emails.");
		}

		str = null;
		str = getConfigurations("CORE", "MEDIUM", "EMAIL");
		if (str == null || "".equals(str)) {
			warnings
					.add("CORE:MEDIUM:EMAIL is not configured therefore will not be able to send any medium priority emails.");
		}

		str = null;
		str = getConfigurations("CORE", "LOW", "EMAIL");
		if (str == null || "".equals(str)) {
			warnings
					.add("CORE:LOW:EMAIL is not configured therefore will not be able to send any low priority emails.");
		}

		str = null;
		str = getConfigurations("CORE", "DATE_FORMAT", "BATCH_JOB_DATE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DATE_FORMAT:BATCH_JOB_DATE is not configured.");
		} else {
			try {
				CDate.getUDFDateString(new Date(), str);
			} catch (Exception e) {
				errors
						.add("CORE:DATE_FORMAT:BATCH_JOB_DATE is not configured correctly. Invalid format.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "DATE_FORMAT", "BATCH_RUN_DATE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DATE_FORMAT:BATCH_RUN_DATE is not configured.");
		} else {
			try {
				CDate.getUDFDateString(new Date(), str);
			} catch (Exception e) {
				errors
						.add("CORE:DATE_FORMAT:BATCH_RUN_DATE is not configured correctly. Invalid format.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "CALENDAR");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:CALENDAR is not configured.");
		}
		
		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "KEEP_ALIVE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:KEEP_ALIVE is not configured.");
		}
		
		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "END_ON_DATE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:END_ON_DATE is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "END_ON_OCCURRENCE");
		if (str == null || "".equals(str)) {
			errors
					.add("CORE:DEL_INSTR_PARAMS:END_ON_OCCURRENCE is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "FREQUENCY");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:FREQUENCY is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "RECUR_EVERY");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:RECUR_EVERY is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "SCHEDULE_DATE");
		if (str == null || "".equals(str)) {
			errors
					.add("CORE:DEL_INSTR_PARAMS:SCHEDULE_DATE is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "SKIP_FLAG");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:SKIP_FLAG is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "DEL_INSTR_PARAMS", "WEEK_DAY");
		if (str == null || "".equals(str)) {
			errors.add("CORE:DEL_INSTR_PARAMS:WEEK_DAY is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "CONTENT_HANDLER");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:CONTENT_HANDLER is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "CONTENT_HANDLER");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:CONTENT_HANDLER is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "MAX_EMAILS_FAILED_OBJ");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:MAX_EMAILS_FAILED_OBJ is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i < 0) {
					errors
							.add("CORE:EMAIL:MAX_EMAILS_FAILED_OBJ is not configured correctly. Must be a number greater than -1.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:EMAIL:MAX_EMAILS_FAILED_OBJ is not configured correctly. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "NOTIFICATION");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:NOTIFICATION is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "NOTIFICATION_GROUP");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:NOTIFICATION_GROUP is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "EMAIL", "WHEN_OBJECT_FAILS");
		if (str == null || "".equals(str)) {
			errors.add("CORE:EMAIL:WHEN_OBJECT_FAILS is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "FUTURE_DATE_RUN", "MAX_NO_OF_DAYS");
		if (str == null || "".equals(str)) {
			errors
					.add("CORE:FUTURE_DATE_RUN:MAX_NO_OF_DAYS is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i < 1) {
					errors
							.add("CORE:FUTURE_DATE_RUN:MAX_NO_OF_DAYS is not configured correctly. Must be a greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:FUTURE_DATE_RUN:MAX_NO_OF_DAYS is not configured correctly. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "GLOBAL_PARAMETER", "REQUIRED");
		if (str == null || "".equals(str)) {
			errors.add("CORE:GLOBAL_PARAMETER:REQUIRED is not configured.");
		}

		String installationCode = null;
		installationCode = getConfigurations("CORE", "INSTALLATION", "CODE");
		if (installationCode == null || "".equals(installationCode)) {
			errors.add("CORE:INSTALLATION:CODE is not configured.");
		}

		str = null;
		str = getConfigurations("INSTALLATION_WS", installationCode, "SERVICES");
		if (str == null || "".equals(str)) {
			errors.add("INSTALLATION_WS:" + installationCode
					+ ":SERVICES is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "INSTALLATION", "NAME");
		if (str == null || "".equals(str)) {
			errors.add("CORE:INSTALLATION:NAME is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "JOB_MONITOR_POLLER", "WAIT_PERIOD");
		if (str == null || "".equals(str)) {
			errors
					.add("CORE:JOB_MONITOR_POLLER:WAIT_PERIOD is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= 0) {
					errors
							.add("CORE:JOB_MONITOR_POLLER:WAIT_PERIOD is not configured. Must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:JOB_MONITOR_POLLER:WAIT_PERIOD is not configured. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "MODE", "DEV_OR_PRE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:MODE:DEV_OR_PRE is not configured.");
		} else {
			if (!("DEV".equals(str) || "PRE".equals(str))) {
				errors
						.add("CORE:MODE:DEV_OR_PRE is not configured correctly. Value must be either DEV or PRE.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "POLLER", "WAIT_PERIOD");
		if (str == null || "".equals(str)) {
			errors.add("CORE:POLLER:WAIT_PERIOD is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= 0) {
					errors
							.add("CORE:POLLER:WAIT_PERIOD is not configured correctly. Must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:POLLER:WAIT_PERIOD is not configured correctly. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "PRE_REQUEST_TYPE", "VALUE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PRE_REQUEST_TYPE:VALUE is not configured.");
		}

		str = null;
		int stuckThreadLimit = 0;
		str = getConfigurations("CORE", "PRE_STUCK_THREAD", "LIMIT");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PRE_STUCK_THREAD:LIMIT is not configured.");
		} else {
			try {
				stuckThreadLimit = Integer.parseInt(str);
				if (stuckThreadLimit <= 0) {
					errors
							.add("CORE:PRE_STUCK_THREAD:LIMIT is not configured. Must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:PRE_STUCK_THREAD:LIMIT is not configured. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "PRE_STUCK_THREAD", "MAX_LIMIT");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PRE_STUCK_THREAD:MAX_LIMIT is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= stuckThreadLimit) {
					errors
							.add("CORE:PRE_STUCK_THREAD:MAX_LIMIT is not configured. Must be a greater than the STUCK Thread Limit.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:PRE_STUCK_THREAD:MAX_LIMIT is not configured. Must be a number.");
			}
		}
		
		str = null;
		str = getConfigurations("CORE", "PROCESS_CLASS", "JV");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PROCESS_CLASS:JV is not configured.");
		}

		str = null;
		str = getConfigurations("CORE", "PURGE", "BACKUP_DIR");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PURGE:BACKUP_DIR is not configured.");
		} else {
			File file = new File(str);
			if (!file.exists()) {
				if (logger.isDebugEnabled()) {
					logger.debug("Creating directories " + file.getPath());
				}
				try {
					file.mkdirs();
				} catch (SecurityException e) {
					StringBuilder sb = new StringBuilder();
					ExecutionHandler.throwableToString(sb, e);
					errors.add("Unable to create directories " + file.getPath() + " due to :" + Constants.NEW_LINE + sb.toString());
				}
			}
		}

		str = null;
		str = getConfigurations("CORE", "PURGE", "RETAIN_DAYS");
		if (str == null || "".equals(str)) {
			errors.add("CORE:PURGE:RETAIN_DAYS is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i <= 0) {
					errors
							.add("CORE:PURGE:RETAIN_DAYS is not configured correctly. Must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
						.add("CORE:PURGE:RETAIN_DAYS is not configured correctly. Must be a number.");
			}
		}
		
//		str = null;
//		str = getConfigurations("CORE", "REPORT", "URL");
//		if (str == null || "".equals(str)) {
//			warnings.add("CORE:REPORT:URL is not configured. Report Generation may not work.");
//		}
		
		str = null;
		str = getConfigurations("CORE", "REPORT_RUNTIME", "OUTPUT_FOLDER");
		if (str == null || "".equals(str)) {
			warnings
					.add("CORE:REPORT_RUNTIME:OUTPUT_FOLDER is not configured. Batch Start/End report will not be generated.");
		}

		str = null;
		str = getConfigurations("CORE", "SAVEPOINT", "DIRECTORY");
		if (str == null || "".equals(str)) {
			errors
					.add("CORE:SAVEPOINT:DIRECTORY is not configured. Batch stop/resume will not work.");
		} else {
			File file = new File(str);
			if (file.exists()) {
				if (!file.isDirectory()) {
					errors
							.add("CORE:SAVEPOINT:DIRECTORY is not configured. Batch stop/resume will not work.");
				}
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("Creating savepoint directories");
				}
				try {
					file.mkdirs();
				} catch (SecurityException e) {
					StringBuilder sb = new StringBuilder();
					ExecutionHandler.throwableToString(sb, e);
					errors.add("Unable to create directories " + file.getPath() + " due to :" + Constants.NEW_LINE + sb.toString());
				}
			}
		}

		str = null;
		str = getConfigurations("CORE", "SCHEDULE_CLASS", "JV");
		if (str == null || "".equals(str)) {
			errors.add("CORE:SCHEDULE_CLASS:JV is not configured.");
		}

		String textLogging = null;
		textLogging = getConfigurations("CORE", "TEXT_LOGGING", "ENABLED");
		if (textLogging == null || "".equals(textLogging)) {
			warnings
					.add("CORE:TEXT_LOGGING:ENABLED is not configured. Default will be used which, is false.");
			textLogging = "N";
		} else {
			if (!("Y".equals(textLogging) || "N".equals(textLogging))) {
				errors
						.add("CORE:TEXT_LOGGING:ENABLED is not configured correctly. Valid values are Y and N.");
				textLogging = "N";
			}
		}

		str = null;
		str = getConfigurations("CORE", "TEXT_LOGGING", "FILE_PATH");
		if (str == null || "".equals(str)) {
			if ("Y".equals(textLogging)) {
				errors
						.add("CORE:TEXT_LOGGING:FILE_PATH is not configured. Must point to a file.");
			} else {
				warnings.add("CORE:TEXT_LOGGING:FILE_PATH is not configured.");
			}
		}

		str = null;
		str = getConfigurations("CORE", "USER", "DEFAULT");
		if (str == null || "".equals(str)) {
			errors.add("CORE:USER:DEFAULT is not configured.");
		}
		
		str = null;
		str = getConfigurations("CORE", "ICD_SERVICE", "USER_ID");
		if (str == null || "".equals(str)) {
			errors.add("CORE:ICD_SERVICE:USER_ID is not configured.");
		}
		
		str = null;
		str = getConfigurations("CORE", "ICD_SERVICE", "PASSWORD");
		if (str == null || "".equals(str)) {
			errors.add("CORE:ICD_SERVICE:PASSWORD is not configured.");
		}

		//Added 09/24/2013 - Release 5.0.8 - Start
		str = null;
		int coreSize = 0;
		str = getConfigurations("CORE", "THREAD_POOL", "CORE_SIZE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:THREAD_POOL:CORE_SIZE is not configured.");
		} else {
			try {
				coreSize = Integer.parseInt(str);
				if (coreSize <= 0) {
					errors
					.add("CORE:THREAD_POOL:CORE_SIZE is not configured. Must be greater than zero.");
				}
			} catch (NumberFormatException e) {
				errors
				.add("CORE:THREAD_POOL:CORE_SIZE is not configured. Must be a number.");
			}
		}
		
		str = null;
		str = getConfigurations("CORE", "THREAD_POOL", "MAX_CORE_SIZE");
		if (str == null || "".equals(str)) {
			errors.add("CORE:THREAD_POOL:MAX_CORE_SIZE is not configured.");
		} else {
			try {
				int i = Integer.parseInt(str);
				if (i < coreSize) {
					errors
					.add("CORE:THREAD_POOL:MAX_CORE_SIZE is not configured. " +
							"Must be a greater than the CORE:THREAD_POOL:CORE_SIZE.");
				}
			} catch (NumberFormatException e) {
				errors
				.add("CORE:THREAD_POOL:MAX_CORE_SIZE is not configured. Must be a number.");
			}
		}
		
		str = null;
		str = getConfigurations("CORE", "AUTH_HANDLER", "JV");
		if (str == null || "".equals(str)) {
			errors.add("CORE:AUTH_HANDLER:JV is not configured.");
		}
		str = null;
		str = getConfigurations("CORE", "AUTH_FILTER_HANDLER", "JV");
		if (str == null || "".equals(str)) {
			errors.add("CORE:AUTH_FILTER_HANDLER:JV is not configured.");
		}
		//Added 09/24/2013 - Release 5.0.8 - End

		str = null;
		str = getConfigurations("MONITOR_WS", "MONITOR_WS", "SERVICES");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR_WS:MONITOR_WS:SERVICES is not configured.");
		} else {
			if (!str.contains(":")) {
				errors
						.add("MONITOR_WS:MONITOR_WS:SERVICES is not configured correctly. Must be <ip/hostname>:<port>");
			}
		}

		HashMap<String, String> executionHandlers = getConfigurations("CORE",
				"EXECUTION_HANDLER");
		if (executionHandlers == null || executionHandlers.size() == 0) {
			errors
					.add("CORE:EXECUTION_HANDLER are not defined. No jobs will get executed.");
		}

		HashMap<MESSAGE_TYPE, ArrayList<String>> all = new HashMap<MESSAGE_TYPE, ArrayList<String>>();
		all.put(MESSAGE_TYPE.ERRORS, errors);
		all.put(MESSAGE_TYPE.WARNINGS, warnings);
		return all;
	}

}

/*
 * Revision Log ------------------------------- $Log::
 * /Product_Base/Projects/Batch
 * /Code/Java/Core/src/com/stgmastek/core/util/Configurations.java $
 * 
 * 5 3/09/10 6:28p Kedarr Changes made to use java sql connection as now
 * CConnection implements java sql connection.
 * 
 * 4 3/03/10 5:40p Grahesh Removed connection object from called IBatchDao
 * constructor and added in called method.
 * 
 * 3 1/06/10 1:14p Grahesh Avoiding null pointer exception
 * 
 * 2 12/17/09 11:46a Grahesh Initial Version
 */