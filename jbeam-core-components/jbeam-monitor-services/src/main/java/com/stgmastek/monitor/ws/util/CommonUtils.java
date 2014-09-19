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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.exception.CommException;
import com.stgmastek.monitor.ws.security.PasswordEncryptionService;
import com.stgmastek.monitor.ws.server.vo.InstallationEntity;
import com.stgmastek.monitor.ws.server.vo.InstructionParameter;
import com.stgmastek.monitor.ws.server.vo.ReqInstructionLog;

import stg.utils.RandomStringGenerator;

/**
 * Class for common utility methods
 *
 * @author Mandar Vaidya
 *
 */
public class CommonUtils {

	/**
	 * Generates the random string which can be used as a password.  
	 * 
	 * @return random string.
	 * 
	 * @throws CommException
	 */
	public static String generateRandomPassword(){
		String randomString = null;
		RandomStringGenerator rsg = new RandomStringGenerator(
				Configurations.getConfigurations().getConfigurations("MONITOR", "PASSWORD", "RNDFORMAT"));
		randomString = rsg.generate();
		return randomString;
	}
	
	/**
	 * Helper method to encrypt the password.
	 * 
	 * @param password
	 * 			Password sent to encrypt
	 * 
	 * @return encrypted password.
	 * @throws CommException 
	 * 
	 * @throws CommException
	 */
	public static String getEncryptedPassword(String password) throws CommException{
		String encryptedPassword = null;
		if (password != null) {
			encryptedPassword = PasswordEncryptionService.getInstance()
					.encrypt(password, Constants.ENCODING_TYPE);
		}
		return encryptedPassword;
	}
	
	/**
	 * Identify if the batch is scheduled. The scheduled batch will always have
	 * Frequency as a parameter. Based on this, the identification is done.
	 * 
	 * @param reqInstructionLog
	 *            The instruction parameters
	 * 
	 * @return true if the batch was scheduled false otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public static Boolean identifyScheduledBatch(ReqInstructionLog reqInstructionLog)
			throws CommDatabaseException {

		Boolean isScheduledBatch = false;
		try {
			List<InstructionParameter> parameters = reqInstructionLog
					.getInstructionParameters();
			for (InstructionParameter parameter : parameters) {
				if (parameter.getName() != null
						&& parameter.getName().equalsIgnoreCase(
								Constants.SCHEDULE_BATCH_PARAMS.FREQUENCY
										.name())) {
					isScheduledBatch = true;
					break;
				}
			}
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		}
		return isScheduledBatch;
	}
	
	/**
	 * Gets the parameters list for SPECIAL batch. 
	 * 
	 * @param instructionParams
	 * 			The instructionParams contains pair of batch parameters
	 * 			e.g. POLICY=PL01;PRE=ALL etc.
	 *  
	 * @return a linked hash map 
	 * 
	 */
	public static LinkedHashMap<String, String> getInstructionParameterList(
			String instructionParams) {

		LinkedHashMap<String, String> entityValuesMap = new LinkedHashMap<String, String>();
		if (instructionParams == null || "null".equals(instructionParams) 
				|| "".equals(instructionParams)) {
			return entityValuesMap;
		}else {
			String[] params = instructionParams.split(";");
			int iCount = 1;
			for (String param : params) {
				String[] tokens = param.split("=");
				if(tokens.length > 0) {
					tokens[0] = tokens[0] + "_" + iCount;
					entityValuesMap.put(tokens[0], tokens[1]);
					iCount++;				
				}
			}
			return entityValuesMap;
		}
	}

	/**
	 * Gets the parameters list for SPECIAL batch.
	 * 
	 * @param instructionParameterList
	 *            The list of instructionParams contains batch parameters e.g.
	 *            POLICY_1=PL01;PRE_1=ALL etc.
	 *            
	 * @param entityList
	 *            The list of entities configured for an installations
	 *            e.g. PRE, POLICY etc.
	 * 
	 * @return a list of instruction parameters containing entities with values
	 *         and serial number
	 */
	public static List<InstructionParameter> getInstructionParameterList(
			List<InstructionParameter> instructionParameterList,
			List<InstallationEntity> entityList) {

		Integer serialNumber = 1;
		List<InstructionParameter> newInstructionParameterList = new ArrayList<InstructionParameter>();

		for (InstructionParameter instructionParameter : instructionParameterList) {
			String str = instructionParameter.getName();
			try {
				Constants.SCHEDULE_BATCH_PARAMS.valueOf(str); // This is required as to throw exception if the key not found in ENUM.
				instructionParameter.setSlNo(serialNumber);
				newInstructionParameterList.add(instructionParameter);
				serialNumber++;
			} catch (IllegalArgumentException e) { // If key not found in ENUM then this indicates that the batch is of SPECIAL type.
				for (InstallationEntity installationEntity : entityList) {
					if (str.indexOf('_') >= 0) {
						instructionParameter.setName(str.substring(0, str
								.indexOf('_')));
						if (installationEntity.getEntity().equals(
								instructionParameter.getName())) {
							instructionParameter.setSlNo(serialNumber);
							newInstructionParameterList
									.add(instructionParameter);
							serialNumber++;
						}
					}
				}
			}
		}
		return newInstructionParameterList;
	}
	/**
	 * Gets the time difference in HH:MM:SS format
	 * 
	 * @param endTime
	 * 			The batch end time
	 * 
	 * @param startTime
	 * 			The batch start time
	 * 
	 * @return the time difference 
	 */
	public static String getTimeDiff(Long endTime, Long startTime) {
		// System.out.println("Start Time= " + startTime);
		// System.out.println("End Time  = " + endTime);
		Long timeElapsed = endTime - startTime;
		long timeElapsedSeconds = timeElapsed / 1000;
		long timeElapsedMinutes = timeElapsed / (60 * 1000);
		long timeElapsedHours = timeElapsed / (60 * 60 * 1000);

		String strtimeHrs = String.valueOf(timeElapsedHours);
		String strtimeMins = String.valueOf(timeElapsedMinutes);
		String strtimeSecs = String.valueOf(timeElapsedSeconds);
		if (strtimeHrs.length() > 0 && strtimeHrs.length() == 1)
			strtimeHrs = "0" + strtimeHrs;
		if (strtimeMins.length() > 0 && strtimeMins.length() == 1)
			strtimeMins = "0" + strtimeMins;
		if (strtimeSecs.length() > 0 && strtimeSecs.length() == 1)
			strtimeSecs = "0" + strtimeSecs;
		// System.out.println("Time Diiference is :"+ strtimeHrs +":" +
		// strtimeMins + ":" + strtimeSecs);
		String finalTimeElapsed = strtimeHrs + ":" + strtimeMins + ":"
				+ strtimeSecs;
		return finalTimeElapsed;
	}
	
	/**
	 * Return <code>IComponentConstants.BLANK_STRING</code> is the provided
	 * value is <code>null</code>; the same <code>String</code> object
	 * otherwise.
	 * 
	 * @return <code>IComponentConstants.BLANK_STRING</code> if the provided
	 *         value is <code>null</code> or the same <code>String</code> object
	 *         otherwise.
	 */
	public static String checkNull(String value) {
		return value != null ? value.trim() : Constants.BLANK_STRING;
	}
}
