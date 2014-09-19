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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;
import com.stgmastek.monitor.comm.exception.InvalidConfigurationException;
import com.stgmastek.monitor.comm.server.dao.IBatchDAO;
import com.stgmastek.monitor.comm.server.dao.IConfigDAO;
import com.stgmastek.monitor.comm.server.services.MonitorCommServices;
import com.stgmastek.monitor.comm.server.services.MonitorCommServicesImpl;
import com.stgmastek.monitor.comm.server.vo.MConfig;


/**
 * Specialized class for handling any request to the configurations as set in the MONITOR_CONFIG table
 * Implements Singleton pattern  
 * 
 * @author grahesh.shanbhag
 *
 */
public final class Configurations {

	/** Static private instance variable */
	private static Configurations instance = new Configurations();
	
	/** Private map to initially populate and later retrieve values from */
	private HashMap<String, HashMap<String, HashMap<String, String>>> configurationsMap = null;
	
	private final static Logger logger = Logger.getLogger(Configurations.class);
	
	/**
	 * Private constructor to avoid outside instantiation
	 */
	private Configurations(){
	}
	
	/**
	 * Returns the single instance of the class
	 * 
	 * @return the single instance of the class
	 */
	public static Configurations getConfigurations(){
		return instance;
	}
	
	/**
	 * Load / Reloads the configurations 
	 * 
	 * @param connection
	 * 		  the connection to the schema
	 * @return the refreshed instance of the {@link Configurations} 
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception occurred 
	 */
	public Configurations loadConfigurations(Connection connection) throws CommDatabaseException{
		IConfigDAO dao = DAOFactory.getConfigDAO();
		List<MConfig> list = dao.getConfigurations(connection);
		configurationsMap = getConfigurationMap(list);
		Map<MESSAGE_TYPE, List<String>> all = validateConfigurations();
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
			throw new InvalidConfigurationException("There are errors in configuration. Check for the above <FATAL> messages. " +
					"Monitor-Comm Services will not start.");
		}
		
		return instance;
	}
	
	/**
	 * Private method that would populate the map 
	 * 
	 * @param list
	 * 		  the list of {@link MConfig} instances
	 * @return the map
	 */
	private HashMap<String, HashMap<String, HashMap<String, String>>> getConfigurationMap(List<MConfig> list) {
		
		HashMap<String, HashMap<String, HashMap<String, String>>> returnMap = new HashMap<String, HashMap<String,HashMap<String,String>>>();
		
		for(MConfig entry : list){
			String code1 = entry.getCode1();
			String code2 = entry.getCode2();
			String code3 = entry.getCode3();
			String value = entry.getValue();
			
			if(returnMap.containsKey(code1)){
				HashMap<String, HashMap<String, String>> code1Map = returnMap.get(code1);
				if(!code1Map.containsKey(code2))
					code1Map.put(code2, new HashMap<String, String>());
				code1Map.get(code2).put(code3, value);				
			}else{
				returnMap.put(code1, new HashMap<String, HashMap<String, String>>());
				returnMap.get(code1).put(code2, new HashMap<String, String>());
				returnMap.get(code1).get(code2).put(code3, value);
			}			
		}
		
		return returnMap;
	}
	
	/**
	 * Public method to get configurations as set using the code1 supplied 
	 * 
	 * @param code1
	 * 		  the code1 value MONITOR_CONFIG.BCC_CODE1
	 * @return the configurations for the supplied code1
	 */
	public HashMap<String, HashMap<String, String>> getConfigurations(String code1){
		if (code1 == null) {
			return null;
		}
		return configurationsMap.get(code1);
	}
 	
	/**
	 * Public method to get configurations as set using the code1, code2 supplied 
	 * 
	 * @param code1
	 * 		  the code1 value MONITOR_CONFIG.BCC_CODE1
	 * @param code2
	 * 		  the code2 value MONITOR_CONFIG.BCC_CODE
	 * @return the configurations for the supplied code1 and code2
	 */
	public HashMap<String, String> getConfigurations(String code1, String code2){
	    HashMap<String, HashMap<String, String>> code1Map = getConfigurations(code1);
	    if(code1Map == null || code2 == null) {
	    	return null;
	    }
		return code1Map.get(code2);
	}

	/**
	 * Public method to get configurations as set using the code1, code2, code3 supplied 
	 * 
	 * @param code1
	 * 		  the code1 value MONITOR_CONFIG.BCC_CODE1
	 * @param code2
	 * 		  the code2 value MONITOR_CONFIG.BCC_CODE2
	 * @param code3
	 * 		  the code3 value MONITOR_CONFIG.BCC_CODE3
	 * @return the configurations for the supplied code1, code2 and code3
	 */
	public String getConfigurations(String code1, String code2, String code3){
		HashMap<String, String> code2Map = getConfigurations(code1, code2);
		if(code2Map == null || code3 == null) {
			return null;
		}
		return code2Map.get(code3);
	}
	
	/**
	 * Returns all the services that needs to be hosted or published. 
	 *  
	 * @return a list of {@link DeploymentMap} containing the deployment information 
	 */
	public List<DeploymentMap> getHostedServices(){

		ArrayList<DeploymentMap> returnList = new ArrayList<DeploymentMap>();
		HashMap<String, String> deploymentMap = 
			getConfigurations("MONITOR_WS", "MONITOR_WS");		
		Iterator<String> iter =  deploymentMap.keySet().iterator();
		while(iter.hasNext()){
			DeploymentMap dMap = new DeploymentMap();
			dMap.setServiceInterface(MonitorCommServices.class);
			dMap.setServiceImpl(MonitorCommServicesImpl.class);
			String address[] = deploymentMap.get(iter.next()).split(":");
			dMap.setAddress(address[0]);			
			dMap.setPort(address[1]);
			returnList.add(dMap);
		}
		return returnList;
	}	
	
	private enum MESSAGE_TYPE {
		ERRORS, WARNINGS;
	}
	
	private HashMap<MESSAGE_TYPE, List<String>> validateConfigurations() {
		List<String> errors = new ArrayList<String>();
		List<String> warnings = new ArrayList<String>();
				
		String str = getConfigurations("MONITOR", "FREQUENCY", "DAY");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:DAY is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "FIRST_MTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:FIRST_MTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "FIRST_YR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:FIRST_YR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "FOURTH_MTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:FOURTH_MTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "FOURTH_YR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:FOURTH_YR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "HOUR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:HOUR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "LAST_MTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:LAST_MTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "LAST_YR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:LAST_YR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "LDMONTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:LDMONTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "MINUTE");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:MINUTE is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "MONTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:MONTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "PREDEFINED");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:PREDEFINED is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "SECOND_MTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:SECOND_MTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "SECOND_YR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:SECOND_YR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "THIRD_MTH");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:THIRD_MTH is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "THIRD_YR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:THIRD_YR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "WEEK");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:WEEK is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null; // Just to be on the safer side.
		str = getConfigurations("MONITOR", "FREQUENCY", "YEAR");
		if (str == null) {
			errors.add("MONITOR:FREQUENCY:YEAR is not configured. " +
					"This Frequency value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "SMTPServer");
		if (str == null || "".equals(str)) {
			warnings.add("MONITOR:MAIL:SMTPServer is not configured therefore system will not be able to send any emails.");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "SMTPServerPort");
		if (str == null || "".equals(str)) {
			warnings.add("MONITOR:MAIL:SMTPServerPort is not configured therefore system will not be able to send any emails.");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "defaultsubjectpassword");
		if (str == null || "".equals(str)) {
			warnings.add("MONITOR:MAIL:defaultsubjectpassword is not configured therefore system will send email without proper message regarding password.");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "defaultsubjectuser");
		if (str == null || "".equals(str)) {
			warnings.add("MONITOR:MAIL:defaultsubjectuser is not configured therefore system will send email without proper message for New User.");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "mailnotification");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:MAIL:mailnotification is not configured.");
		}else {
			if (!("ON".equals(str) || "OFF".equals(str))) {
				errors.add("MONITOR:MAIL:mailnotification is not configured correctly. The value must be either ON or OFF. " +
						"To get the emails regarding user creation or passwords, keep the value ON.");				
			}			
		}
		
		str = null;
		str = getConfigurations("MONITOR", "MAIL", "senderaddress");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:MAIL:senderaddress is not configured therefore system will not be able to send any emails.");
		}
		
		
		str = null;
		str = getConfigurations("MONITOR", "PASSWORD", "RNDFORMAT");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:PASSWORD:RNDFORMAT is not configured. The system cannot generate safe passwords.");
		}
		
		str = null;
		str = getConfigurations("MONITOR_WS", "OUTBOUND_Q_POLLER", "WAIT_PERIOD");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR_WS:OUTBOUND_Q_POLLER:WAIT_PERIOD is not configured.");
		} else {
			try {
				Integer.parseInt(str);
			} catch (NumberFormatException e) {
				errors.add("MONITOR_WS:OUTBOUND_Q_POLLER:WAIT_PERIOD is not configured. Must be a number.");
			}
		}

		str = null;
		str = getConfigurations("MONITOR", "REPORT_FORMAT", "DOC");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:REPORT_FORMAT:DOC is not configured." +
					"This Destination Format value will not be available in the section 'Schedule Report (Reports module)'");
		}
		str = null;
		str = getConfigurations("MONITOR", "REPORT_FORMAT", "PDF");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:REPORT_FORMAT:PDF is not configured." +
					"This Destination Format value will not be available in the section 'Schedule Report (Reports module)'");
		}
		str = null;
		str = getConfigurations("MONITOR", "REPORT_FORMAT", "PPT");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:REPORT_FORMAT:PPT is not configured." +
					"This Destination Format value will not be available in the section 'Schedule Report (Reports module)'");
		}
		str = null;
		str = getConfigurations("MONITOR", "REPORT_FORMAT", "RTF");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:REPORT_FORMAT:RTF is not configured." +
					"This Destination Format value will not be available in the section 'Schedule Report (Reports module)'");
		}
		str = null;
		str = getConfigurations("MONITOR", "REPORT_FORMAT", "XLS");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:REPORT_FORMAT:XLS is not configured." +
					"This Destination Format value will not be available in the section 'Schedule Report (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "SKIP_SCHEDULE_CODE", "D+");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:SKIP_SCHEDULE_CODE:D+ is not configured." +
					"This Destination Format value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "SKIP_SCHEDULE_CODE", "D-");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:SKIP_SCHEDULE_CODE:D- is not configured." +
					"This Destination Format value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "SKIP_SCHEDULE_CODE", "NA");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:SKIP_SCHEDULE_CODE:NA is not configured." +
					"This Destination Format value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "SKIP_SCHEDULE_CODE", "SS");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:SKIP_SCHEDULE_CODE:SS is not configured." +
					"This Destination Format value will not be available in the section 'Schedule- Request Status (Reports module)'");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "0");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:0 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "1");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:1 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "2");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:2 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "3");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:3 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "4");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:4 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "5");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:5 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "WEEKDAY", "6");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:WEEKDAY:6 is not configured.");
		} 
		
		str = null;
		str = getConfigurations("MONITOR", "PURGE", "BACKUP_DIR");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:PURGE:BACKUP_DIR is not configured.");
		}
		
		str = null;
		str = getConfigurations("MONITOR", "PURGE", "RETAIN_DAYS");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR:PURGE:RETAIN_DAYS is not configured.");
		} else {
			try {
				Integer.parseInt(str);
			} catch (NumberFormatException e) {
				errors.add("MONITOR:PURGE:RETAIN_DAYS is not configured correctly. Must be a number.");
			}
		}
		
		str = null;
		str = getConfigurations("MONITOR_WS", "MONITOR_WS", "SERVICES");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR_WS:MONITOR_WS:SERVICES is not configured.");
		} else {
			if (!str.contains(":")) {
				errors.add("MONITOR_WS:MONITOR_WS:SERVICES is not configured correctly. Must be <ip/hostname>:<port>");
			}
		}
		
		str = null;
		str = getConfigurations("MONITOR_WS", "MONITOR_UI_WS", "SERVICES");
		if (str == null || "".equals(str)) {
			errors.add("MONITOR_WS:MONITOR_UI_WS:SERVICES is not configured.");
		} else {
			if (!str.contains(":")) {
				errors.add("MONITOR_WS:MONITOR_UI_WS:SERVICES is not configured correctly. Must be <ip/hostname>:<port>");
			}
		}
		
		List<String> installationCodeList = null;
		try {
			installationCodeList = getInstallationsList();
		} catch (CommDatabaseException e) {
			errors.add("Installations list cannot be retreived");
		}
		if(installationCodeList != null) {
			for(String installationCode : installationCodeList) {
				str = getConfigurations("INSTALLATION_WS", installationCode, "SERVICES");
				if (str == null || "".equals(str)) {
					errors.add("INSTALLATION_WS:" + installationCode + ":SERVICES is not configured.");
				}
				
				str = getConfigurations(installationCode, "COLLATOR", "WAIT_PERIOD");
				if (str == null || "".equals(str)) {
					errors.add(installationCode + ":COLLATOR:WAIT_PERIOD is not configured.");
				}
				
				str = getConfigurations(installationCode, "PERSCANCOLLATOR", "WAIT_PERIOD");
				if (str == null || "".equals(str)) {
					errors.add(installationCode + ":PERSCANCOLLATOR:WAIT_PERIOD is not configured.");
				}
			}
			
		}
		
		
		
		HashMap<MESSAGE_TYPE, List<String>> all = new HashMap<MESSAGE_TYPE, List<String>>();
		all.put(MESSAGE_TYPE.ERRORS, errors);
		all.put(MESSAGE_TYPE.WARNINGS, warnings);
		return all;
	}
	
	/**
	 * Retrieves the list of registered installations 
	 * 
	 * @return the list of registered installations
	 *  
	 * @throws CommDatabaseException
	 * 		   Any database I/O exception occurred 
	 */
	public List<String> getInstallationsList() throws CommDatabaseException{
		IBatchDAO dao = DAOFactory.getBatchDAO();
		Connection connection = null;
		List<String> list;
		try {
			connection = ConnectionManager.getInstance().getDefaultConnection();
			list = dao.getInstallationCodeList(connection);			
		}finally {
			dao.releaseResources(null, null, connection);
		}
		return list;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/Configurations.java                                                                  $
 * 
 * 7     6/18/10 12:41p Lakshmanp
 * added connection parameter for dao method
 * 
 * 6     6/17/10 10:31a Kedarr
 * Changed the package for DAO
 * 
 * 5     1/07/10 5:30p Grahesh
 * Updated Java Doc comments
 * 
 * 4     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 3     12/21/09 10:20a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/