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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TimeZone;

import com.stgmastek.monitor.comm.exception.CommDatabaseException;

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
		BATCH_DAO("jbeam-com.stgmastek.monitor.comm.server.dao.batchdaoimpl"),
		CONFIG_DAO("jbeam-com.stgmastek.monitor.comm.server.dao.configdaoimpl"),
		STATUS_DAO("jbeam-com.stgmastek.monitor.comm.server.dao.statusdaoimpl");
		
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
	private static final HashMap<String, TimeZone> timeZoneMap = new HashMap<String, TimeZone>();
	
	public static TimeZone getTimeZone(String installationCode) throws CommDatabaseException {
		if (timeZoneMap.containsKey(installationCode)) return timeZoneMap.get(installationCode);
		return getTimezone(installationCode);
	}
	
	private static synchronized TimeZone getTimezone(String installationCode) throws CommDatabaseException{
		TimeZone timeZone = TimeZone.getDefault();
		if (!timeZoneMap.containsKey(installationCode)) {
			Connection con = null;
			try {
				con = ConnectionManager.getInstance().getDefaultConnection();
				timeZone = DAOFactory.getBatchDAO().getInstallationTimeZone(installationCode, con);
				timeZoneMap.put(installationCode, timeZone);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
					}
				}
			}
		} else {
			timeZone = timeZoneMap.get(installationCode);
		}
		return timeZone;
	}

}


/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Mo $
 * 
 * 1     6/21/10 11:40a Lakshmanp
 * intial version
 * 
*
*
*/