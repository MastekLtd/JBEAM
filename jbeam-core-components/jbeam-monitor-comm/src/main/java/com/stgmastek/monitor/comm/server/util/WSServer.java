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

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.transport.http_jetty.JettyHTTPServerEngineFactory;

/**
 * This class is a wrapper to the actual JAXWS Server created by apache CXF.
 * It has all the utility method to provide the details for the server.
 * All needed server information should be derived from this class instead of calling any of the 
 * CXF server directly from the code 
 * 
 * @author grahesh.shanbhag
 *
 */
public class WSServer{

	/** Private instance of the apache CXF server */
	private Server server;
	
	/** Private instance of the Jetty HTTP Server Engine Factory */
	private JettyHTTPServerEngineFactory factory;
	
	/** Private instance of the Web Service Server data */
	private WSServerInfo info;
	
	/**
	 * 
	 * The constructor to wrap the apache CXF server 
	 * 
	 * @param server
	 * 		  The apache CXF server
	 * @param info
	 * 		  Additional information of the server for easy usage
	 * @param factory
	 * 		  An instance of {@link JettyHTTPServerEngineFactory} 	 
	 */
	WSServer(Server server, WSServerInfo info, JettyHTTPServerEngineFactory factory){
		this.server = server;
		this.info = info;
		this.factory = factory;
	}
	
	/**
	 * Method to indicate whether the server (and hence the services under the server) 
	 * are up and running
	 * 
	 * @return true if the server is up, false otherwise 
	 */
	public boolean isRunning(){
		return server.getDestination().getMessageObserver() != null ? true : false; 
	}
	
	/**
	 * Method to start the server (and hence the services under the server), in case it is down.
	 */
	public void start(){
		server.start();
	}
	
	/**
	 * Method to stop the server (and hence the services under the server)
	 */
	public void stop(){
		server.stop();
		factory.destroyForPort(info.getAddress().getPort());
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/util/WSServer.java                                                                 $
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/