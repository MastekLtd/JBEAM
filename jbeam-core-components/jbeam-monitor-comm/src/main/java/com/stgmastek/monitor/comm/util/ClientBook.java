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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.monitor.comm.client.MonitorCommServices;
import com.stgmastek.monitor.comm.server.vo.MConfig;

/**
 * Client book that holds client stubs 
 * Implements Singleton and lazy caching
 * The way it works - 
 * If on startup of the monitor communication no caching is done as core communication is 
 * not up by now. 
 * Upon the first request for a client stub, it would create an instance of the client stub, cache it 
 * and then returns it 
 *  
 */
public final class ClientBook{
	
	/** Private static instance */
	private static ClientBook book = new ClientBook();
	
	/** The map to hold the information of the registered services for core*/
	private ClientServices serviceMap = null;
	
	/** The map to hold the information of the registered services for monitor */
	private ClientServices monitorServiceMap = null;
	
	/**
	 * Private constructor that loads the fetches the configurations 
	 * and accordingly caches the client stubs 
	 */
	private ClientBook(){
		
		//Instantiates the map 
		serviceMap = new ClientServices();
		monitorServiceMap = new ClientServices();
		
		List<MConfig> list = getConfigurationData("INSTALLATION_WS");		
		serviceMap.setMap(list);
		
		List<MConfig> monitorList = getConfigurationData("MONITOR_WS");		
		monitorServiceMap.setMap(monitorList);
	}
	
	private List<MConfig> getConfigurationData(String code1){
		List<MConfig> list = new ArrayList<MConfig>();
		HashMap<String, HashMap<String, String>> map = 
			Configurations.getConfigurations().getConfigurations(code1);
		for (Entry<String, HashMap<String, String>> code2Entries : map.entrySet() ) {
			String code2 = code2Entries.getKey();
			HashMap<String, String> mapI = map.get(code2);
			for (Entry<String, String> code3Entries : mapI.entrySet()) {
				String code3 = code3Entries.getKey();
				String value = code3Entries.getValue();
				MConfig config = new MConfig();
				config.setCode1(code1);
				config.setCode2(code2);
				config.setCode3(code3);
				config.setValue(value);
				list.add(config);
			}
		}
		return list;
	}
	
	/**
	 * Returns the client book 
	 * 
	 * @return the client book 
	 */
	public static ClientBook getBook(){
		return book;
	}
	
	/**
	 * Returns the client class for the supplied installation code 
	 * 
	 * @param installationCode
	 * 		  The installation code for which the client stub is requested
	 * @return the core-comm client stub
	 */
	public CoreCommServices getClientClass(String installationCode) {
		return serviceMap.getClientClass(installationCode);
	}
	
	/**
	 * Returns the client class for the supplied installation code 
	 * 
	 * @param installationCode
	 * 		  The installation code for which the client stub is requested
	 * @return the monitor-comm client stub
	 */
	public MonitorCommServices getMonitorClientClass(String installationCode) {
		return monitorServiceMap.getMonitorClientClass(installationCode);
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/util/ClientBook.java                                                                      $
 * 
 * 4     1/07/10 5:30p Grahesh
 * Updated Java Doc comments
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/