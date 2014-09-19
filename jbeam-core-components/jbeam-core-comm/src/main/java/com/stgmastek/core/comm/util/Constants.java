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

/**
 * 
 * Constants class for the Core system
 * 
 * @author Lakshman Pendrum
 *
 */
public final class Constants {

		/** Constant for end reason as Cancelled by user */ 				
		public static final String USER_CANCELLED = "Cancelled by user";
		
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
			
			/**
			 * System property <code>jbeam-com.stgmastek.core.comm.server.dao.batchdaoimpl</code>.
			 * Define the class name that implements {@link com.stgmastek.core.comm.server.dao.IBatchDAO}
			 */
			BATCH_DAO("jbeam-com.stgmastek.core.comm.server.dao.batchdaoimpl"),
			/**
			 * System property <code>jbeam-com.stgmastek.core.comm.server.dao.configdaoimpl</code>.
			 * Define the class name that implements {@link com.stgmastek.core.comm.server.dao.IConfigDAO}
			 */
			CONFIG_DAO("jbeam-com.stgmastek.core.comm.server.dao.configdaoimpl"),
			/**
			 * System property <code>jbeam-com.stgmastek.core.comm.server.dao.statusdaoimpl</code>.
			 * Define the class name that implements {@link com.stgmastek.core.comm.server.dao.IStatusDAO}
			 */
			STATUS_DAO("jbeam-com.stgmastek.core.comm.server.dao.statusdaoimpl");
			
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

	}


	/*
	* Revision Log
	* -------------------------------
	* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/Constants.java                  $
 * 
 * 2     6/21/10 11:33a Lakshmanp
 * modified keys
 * 
 * 1     6/18/10 4:51p Lakshmanp
 * initial version
	 * 
	*
	*
	*/ 
