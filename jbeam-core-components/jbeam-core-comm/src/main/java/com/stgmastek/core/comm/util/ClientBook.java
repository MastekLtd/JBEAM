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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.stgmastek.core.comm.client.CoreCommServices;
import com.stgmastek.core.comm.exception.MonitorServiceDownException;
import com.stgmastek.monitor.comm.client.MonitorCommServices;


/**
 * The client book contains the cached stubs of the monitor services. 
 * Provision is also made that the there could be more than one monitor to which the core 
 * has to communicate (publish - subscribe model)
 * 
 * This class would cache the stubs (during booting) and keep it in ready-to-use state to 
 * avoid delay during the actual transmission. 
 * 
 * If the monitor communication is down when then the core communication is booted, the core 
 * communication system would be safely brought down as there are no monitor to communicate with.  
 * 
 */
public final class ClientBook{
	
	/** Private static instance */
	private static ClientBook book = new ClientBook();
	
	/** The map to hold the information of the registered services for monitor-comm */
	private ClientServices serviceMap = null;
	
	/** The map to hold the information of the registered services for core-comm */
	private ClientServices coreServiceMap = null;
	
//	/** 
//	 * The list of configurations
//	 */
//	List<CConfig> list = null;
	
	/**
	 * Private constructor to avoid outside instantiation 
	 * 
	 * This constructor would fetch the configurations and using the configurations 
	 * would cache the monitor client stubs 
	 */
	private ClientBook(){
		
		//Instantiates the map 
		serviceMap = new ClientServices();		
		coreServiceMap = new ClientServices();
		List<CConfig> list = getConfigurationData("MONITOR_WS");		
		List<CConfig> coreConfigs = getConfigurationData("INSTALLATION_WS");		
		serviceMap.setMap(list);
		coreServiceMap.setMap(coreConfigs);
	}
	
	/**
	 * Returns the client book 
	 * Special condition check to see if the monitor services is up. If not then exception is 
	 * thrown so the core communication can safely be shutdown.  
	 * 
	 * @return the client book 
	 * @throws MonitorServiceDownException
	 * 		   Exception thrown when the registered monitor service is down 
	 */
	public static ClientBook getBook() throws MonitorServiceDownException{
		if(book.serviceMap.serviceMap.size() == 0)
			throw new MonitorServiceDownException("Monitor Service is down");
		
		return book;
	}
	
	/**
	 * Returns the client book 
	 * Special condition check to see if the monitor services is up. If not then exception is 
	 * thrown so the core communication can safely be shutdown.  
	 * 
	 * @return the client book 
	 * @throws MonitorServiceDownException
	 * 		   Exception thrown when the registered monitor service is down 
	 */
	public static ClientBook getCoreBook() throws MonitorServiceDownException{
		if(book.serviceMap.coreServiceMap.size() == 0)
			throw new MonitorServiceDownException("Core Service is down");
		
		return book;
	}
	
	/**
	 * Returns the cached client stub class for the supplied installation 
	 *  
	 * @param installationCode
	 * 		  The installation code 
	 * @return the cached client 
	 */
	public MonitorCommServices getClientClass(String installationCode) {
		return serviceMap.getClientClass(installationCode);
	}
	
	/**
	 * Returns the cached client stub class for the supplied installation 
	 *  
	 * @param installationCode
	 * 		  The installation code 
	 * @return the cached client 
	 */
	public CoreCommServices getCoreClientClass(String installationCode) {
		return coreServiceMap.getCoreClientClass(installationCode);
	}
	
	private List<CConfig> getConfigurationData(String code1){
		List<CConfig> list = new ArrayList<CConfig>();
		HashMap<String, HashMap<String, String>> map = 
			Configurations.getConfigurations().getConfigurations(code1);
		for (Entry<String, HashMap<String, String>> code2Entries : map.entrySet() ) {
			String code2 = code2Entries.getKey();
			HashMap<String, String> mapI = map.get(code2);
			for (Entry<String, String> code3Entries : mapI.entrySet()) {
				String code3 = code3Entries.getKey();
				String value = code3Entries.getValue();
				CConfig config = new CConfig();
				config.setCode1(code1);
				config.setCode2(code2);
				config.setCode3(code3);
				config.setValue(value);
				list.add(config);
			}
		}
		return list;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/ClientBook.java                                                                            $
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/