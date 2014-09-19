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

import java.util.HashMap;
import java.util.Map;

/**
 * Class used for parsing the message parameters
 * It assumes that the mandatory checks of message parameter being not null 
 * is done by the caller
 * 
 * @author grahesh.shanbhag
 * 
 */
public final class MessageParamParser {

	/**
	 * Default private constructor to instantiation
	 */
	private MessageParamParser(){}
	
	/**
	 * Static method to parse the comma separated parameters
	 * 
	 * @param messageParams
	 * 		  The parameters in the format 'key1=value1,key2=value2'
	 * @return the parsed parameters as a map 
	 */
	public static Map<String, String> parse(String messageParams){
		HashMap<String, String> retMap = new HashMap<String, String>();		
		String[] params = messageParams.split(",");
		for(String param : params){
			String[] tokens = param.split("=");
			retMap.put(tokens[0].trim(), tokens[1].trim());
		}
		return retMap;		
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/MessageParamParser.java                                                              $
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/