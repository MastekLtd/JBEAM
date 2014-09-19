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

import com.stgmastek.monitor.ws.server.base.ServiceBase;

/**
 * The factory interface class to declare the server life cycle 
 * 
 * @author grahesh.shanbhag
 *
 */
public interface WSServerFactory{

	/**
	 * This method would create a server based on the information supplied. 
	 * Note that when the server is created, by default, the server should be up and running 
	 * 
	 * @param serviceClass
	 * 		  The service (skeleton or the interface) class 
	 * @param impl
	 * 		  The implementing class (of the skeleton or the interface)
	 * @param info
	 * 		  The meta data about the server @see {@link WSServerInfo}
	 * @return the newly created server WSServer
	 */
	public WSServer createService(Class<? extends ServiceBase> serviceClass, Object impl, WSServerInfo info); 
	
	/**
	 * Destroys the server created and frees any resources held by the server for garbage collection
	 * 
	 * @param info
	 * 		  The information of the server to be destroyed @see {@link WSServerInfo}
	 * @return true if the server was destroyed successfully, false otherwise
	 */
	public boolean destroyService(WSServerInfo info); 
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/util/WSServerFactory.java                                                        $
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/