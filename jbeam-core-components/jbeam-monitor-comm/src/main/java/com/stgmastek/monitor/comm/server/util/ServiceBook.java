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
package com.stgmastek.monitor.comm.server.util;

import java.util.HashMap;
import java.util.Set;

/**
 * 
 * Serves as the dictionary for the servers created within the monitor communication system   
 * 
 * @author grahesh.shanbhag
 *
 */
public final class ServiceBook {

	/** Private mao tio hold the information of all the servers created */
	private HashMap<WSServerInfo, WSServer> book = new HashMap<WSServerInfo, WSServer>();

	/**
	 * Default constructor 
	 */
	ServiceBook() {
	}

	/**
	 * Method to register a created server into the book
	 * 
	 * @param info
	 * 		  The information of the server 
	 * @param server
	 * 		  The server itself
	 */
	public void register(WSServerInfo info, WSServer server) {
		book.put(info, server);
	}

	/**
	 * Method to de-register the server 
	 * 
	 * @param info
	 * 		  The information of the server to de-register
	 */
	public void deregister(WSServerInfo info) {
		book.remove(info);
	}
	
	/**
	 * Returns the server with the supplied information 
	 * Would return null if the server is not registered in the system 
	 * 
	 * @param info
	 * 		  The information of the server 
	 * @return the server 
	 */
	public WSServer getServer(WSServerInfo info) {
		return book.get(info);
	}

	/**
	 * Returns the count of servers registered in the system
	 * 
	 * @return the count of servers 
	 */
	public Integer getServerCount() {
		return book.size();
	}

	/**
	 * Utility method to get the keys of the service book
	 * @see WSServerInfo
	 * 
	 * @return the iterator for key set
	 */
	public Set<WSServerInfo> getServerInfo(){
		return book.keySet();
	}

	/**
	 * Utility method to return whether the server is registered in the system 
	 * 
	 * @param info
	 * 		  The information of the server
	 * @return true if the server is registered, false otherwise 
	 */
	public boolean isServerRegistered(WSServerInfo info){
		if(book.containsKey(info))
			return true;
		
		return false;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/util/ServiceBook.java                                                              $
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/