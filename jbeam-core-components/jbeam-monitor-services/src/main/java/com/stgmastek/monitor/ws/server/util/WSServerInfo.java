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
package com.stgmastek.monitor.ws.server.util;

/**
 * The class to hold the entire server information. 
 * It includes all the details that is needed by the system to uniquely identify a server 
 * and operate on it. 
 *  
 * @author grahesh.shanbhag
 *
 */
public class WSServerInfo {

	/** The unique server name. Used internally by the Monitor WS system */
	private String serverName;
	
	/** The address related information.Used internally as well as to create the apache CXF server */
	private WSServerAddress address;
	
	/**
	 * Public default constructor 
	 */
	public WSServerInfo(){}
	
	/**
	 * Over loaded constructor that takes the server name and the address as the argument
	 * 
	 * @param serverName
	 * 		  The server name
	 * @param address
	 * 		  The address related information. @see {@link WSServerAddress}
	 */
	public WSServerInfo(String serverName, WSServerAddress address){
		this.serverName = serverName;
		this.address = address;
	}
	
	/**
	 * Returns the server name 
	 * 
	 * @return the server name
	 */
	public String getServerName() {
		return serverName;
	}
	
	/**
	 * Sets the server name
	 * 
	 * @param serverName
	 * 		  The server name to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	/**
	 * Returns the address information 
	 * @see WSServerAddress
	 * 
	 * @return the server information 
	 */
	public WSServerAddress getAddress() {
		return address;
	}
	
	/**
	 * Sets the address 
	 * 
	 * @param address
	 * 		  The address to set @see {@link WSServerAddress} 
	 */
	public void setAddress(WSServerAddress address) {
		this.address = address;
	}
	
	/**
	 * Over ridden hashCode method from the super class {@link Object#hashCode()}
	 * This is needed to maintain a service book with {@link WSServerInfo} as the key 
	 * The algorithm it follows is to convert each character into is integer value and 
	 * then add up to get a unique value
	 * 
	 * @return the hash code value for the {@link WSServerInfo} instance
	 */
	
	public int hashCode() {
		String url = getAddress().getHost().trim() + getAddress().getPort() + getAddress().getDomain().trim();
		char[] carr = url.toCharArray();
		int retInt = 0;
		for(char c : carr)
			retInt += c;
		
		return retInt;
		
	}
		
	/**
	 * Over ridden equals method from the super class {@link Object#equals(Object)}
	 * This is needed to compare to {@link WSServerInfo} objects for equality
	 * 
	 * @return true if the host, port and the end point domain are the same, false otherwise. 
	 * 	 	   Note that it does not take into account the server name as there would be never a need 
	 * 		   to store the same server with two names within the server. As it is the server name is 
	 * 		  used purely for internal purpose 
	 */
	
	public boolean equals(Object obj) {
		if(!(obj instanceof WSServerInfo)){
			return false;
		}
		WSServerInfo in = (WSServerInfo)obj;
		if(	in.getAddress().getHost().equals(getAddress().getHost())
		 && in.getAddress().getPort().equals(getAddress().getPort())
		 && in.getAddress().getDomain().equals(getAddress().getDomain())){
			return true;
		}
		
		return false;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/util/WSServerInfo.java                                                           $
 * 
 * 3     12/30/09 1:10p Grahesh
 * Correcting the creation of the callable statement string
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/