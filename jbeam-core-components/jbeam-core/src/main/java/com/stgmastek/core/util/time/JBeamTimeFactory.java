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
package com.stgmastek.core.util.time;

import stg.utils.CSettings;

/**
 * JBeam Time factory is responsible to fetch correct time factory implementation.
 * The factory reads the configuration of PRE to identify if the time is to be taken
 * from Database or from the Server. The Database time will necessarily fire query in order to
 * fetch the time, every time, whenever the method is invoked.
 *
 * @author Kedar Raybagkar
 * @since 3.3
 */
public class JBeamTimeFactory {

	public static enum TIME {
		/**
		 * Identifier to fetch time to be taken from Database. 
		 */
		DATABASE, 
		/**
		 * Identifier to fetch time to be taken from Server. 
		 */
		SERVER;
	}
	
	/**
	 * Variable stores the default {@link JBeamTime} implementation.
	 */
	private static JBeamTime jtime = null;
	
	/**
	 * Default private constructor. 
	 */
	private JBeamTimeFactory() {
	}

	static {
		if (TIME.DATABASE.name().equals(CSettings.get("pr.currenttimestamp", TIME.SERVER.name()))){
			jtime = new DatabaseTime();
		} else {
			jtime = new ServerTime();
		}
	}
	/**
	 * Returns the instance of {@link JBeamTime} appropriately chosen from the configuration.
	 * 
	 * @return JBeamTime
	 */
	public static JBeamTime getInstance() {
		return jtime;
	}
}
