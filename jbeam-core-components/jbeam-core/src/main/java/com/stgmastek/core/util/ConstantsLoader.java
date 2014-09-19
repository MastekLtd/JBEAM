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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import stg.utils.CSettings;

import com.stg.crypto.PBEEncryptionRoutine;
import com.stgmastek.core.dao.DaoFactory;
import com.stgmastek.core.dao.IBatchDao;
import com.stgmastek.core.exception.BatchException;

/**
 * Constants loading class. These constants are as set in the CORE_CONFIG with CODE 1 as 'CORE' 
 * 
 * @author grahesh.shanbhag
 *
 */
public final class ConstantsLoader {

	private ConstantsLoader(){}
	
	/**
	 * Used in the PRE mode 
	 * 
	 * @param batchContext
	 * 		  The batchContext for the batch 
	 * @throws BatchException
	 * 	 	   Any exception during database I/O OR if the mode is incorrect  
	 */
	public static void bootCore(BatchContext batchContext) throws BatchException{		
		Connection connection = null;
		try {
			connection = batchContext.getBATCHConnection();
			Configurations config = Configurations.getConfigurations().loadConfigurations(connection);
			loadConstants(config, connection);
			if (batchContext.getPreContext().getPREInfo().getMajorVersion() < 29) {
				throw new BatchException("PRE version not compatible. Need at least V1.0R29");
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					//dummy
				}
			}
		}
	}	

	/**
	 * Helper method to load the constants with the supplied configurations 
	 * as set in the CORE_CONFIG table with CODE 1 as 'CORE' 
	 * 
	 * @param config
	 * 		  The configurations as set in the CORE_CONFIG 
	 * 		  table with CODE 1 as 'CORE' 
	 * @param connection 
	 */
	private static void loadConstants(Configurations config, Connection connection) throws BatchException{
				
		String configMode = config.getConfigurations("CORE", "MODE", "DEV_OR_PRE");
		
		//Note that the mode is set as DEV in the StartCore in case of DEV mode
		if((Constants.MODE.equals("") &&  configMode.equals(Constants.DEV))
				|| (Constants.MODE.equals(Constants.DEV)) && configMode.equals(Constants.PRE))
			throw new BatchException(
					"Cannot run the batch as the mode set {" + configMode + "}is not compatible", false);
		
		Constants.MODE = configMode;
		
		//Load the property files for EmailAgent when in DEV mode. 
		//Otherwise in PRE mode, it would be loaded by the PRE engine
		if(Constants.MODE.equals(Constants.DEV)){
			CSettings settings = CSettings.getInstance();
			try {
				settings.load("prinit.properties");
			} catch (IOException e) {
				throw new BatchException(e);
			}
		}
		
		Constants.DEFAULT_USER = config.getConfigurations("CORE", "USER", "DEFAULT");		
		Constants.BATCH_MAX_LISTENERS = Integer.valueOf(config.getConfigurations("CORE", "BATCH_LISTENER","MAX_LISTENERS"));
		Constants.PRE_POST_MAX_LISTENERS = Integer.valueOf(config.getConfigurations("CORE", "PRE_POST_LISTENER","MAX_LISTENERS"));		
		Constants.PRE_STATUS_CHECK_WAIT_PERIOD = Integer.valueOf(config.getConfigurations("CORE", "PRE", "WAIT_PERIOD"));
		Constants.POLLER_WAIT_PERIOD = Integer.valueOf(config.getConfigurations("CORE", "POLLER", "WAIT_PERIOD"));
		Constants.BATCH_RUN_DATE_FORMAT = config.getConfigurations("CORE", "DATE_FORMAT", Constants.PROCESS_REQUEST_PARAMS.BATCH_RUN_DATE.name());
		Constants.BATCH_JOB_DATE_FORMAT = config.getConfigurations("CORE", "DATE_FORMAT", "BATCH_JOB_DATE");
		
		Constants.CORE_SIZE = config.getConfigurations("CORE", "THREAD_POOL", Constants.THREAD_POOL.CORE_SIZE.name());
		Constants.MAX_CORE_SIZE = config.getConfigurations("CORE", "THREAD_POOL", Constants.THREAD_POOL.MAX_CORE_SIZE.name());
		
		Constants.ICD_END_POINT_URL = config.getConfigurations("CORE", "ICD_END_POINT_URL",	"URL");
		Constants.ICD_SERVICE_USER_ID = config.getConfigurations("CORE", "ICD_SERVICE",	"USER_ID");
		
		String icdServicePassword = config.getConfigurations("CORE", "ICD_SERVICE", "PASSWORD");
		
		loadICDPassword(icdServicePassword, connection);
		
		//For global parameter settings 
		String strSettings = config.getConfigurations("CORE", "GLOBAL_PARAMETER", "REQUIRED");
		if(strSettings.equals("Y"))
			Constants.SET_GLOBAL_PARAMETERS = true;
	
		strSettings = config.getConfigurations("CORE", "TEXT_LOGGING", "ENABLED");
		if(strSettings.equals("Y"))
			Constants.LOGGING_ENABLED = true;
		
		Constants.LOG_FILE_PATH = config.getConfigurations("CORE", "TEXT_LOGGING", "FILE_PATH");
		
		//Set the max date for which the batch can be run 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 
				Integer.valueOf(config.getConfigurations("CORE", "FUTURE_DATE_RUN", "MAX_NO_OF_DAYS")));
		Constants.MAX_BATCH_DATE = calendar.getTime();
		
		
	}
	
	private static void loadICDPassword(String icdServicePassword, Connection connection) throws BatchException{
		if(icdServicePassword == null){
			throw new BatchException("ICDPassword is not configured.", false);
		}
		PBEEncryptionRoutine encryptionRoutine = new PBEEncryptionRoutine();
		try {
			Constants.ICD_SERVICE_PASSWORD = encryptionRoutine.decode(icdServicePassword);
		} catch (SecurityException se) {
//			System.out.println("Password is not encrypted");
			Constants.ICD_SERVICE_PASSWORD = icdServicePassword;
//			System.out.println("Constants.ICD_SERVICE_PASSWORD =  " + icdServicePassword );
			IBatchDao bDao = DaoFactory.getBatchDao();
			icdServicePassword = encryptionRoutine.encode(icdServicePassword);
			bDao.updateConfiguration(icdServicePassword, connection);		
		}		
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ConstantsLoader.java                                                                                $
 * 
 * 5     3/19/10 7:07p Mandar.vaidya
 * Changes made to not close the connection object in the DAO. Corresponding changes made in the caller to close the connection.
 * 
 * 4     12/29/09 12:45p Grahesh
 * Added constant loading for  LOGGING_ENABLED & LOG_FILE_PATH
 * 
 * 3     12/24/09 2:32p Grahesh
 * Added loading for 
 * BATCH_RUN_DATE_FORMAT & BATCH_JOB_DATE_FORMAT
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/