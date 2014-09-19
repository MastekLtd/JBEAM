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
package com.stgmastek.monitor.ws.server.vo;

/**
 * This class base class for all request value objects within the core
 * system. These are classes for request to seek a service at the core's 
 * end
 * 
 * @author mandar.vaidya
 *
 */
public class BaseRequestVO extends BaseVO{

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** Request type */
	private String requestType;
	
	/** Request time */
	private Long requestTime;
	
	/**
	 * Returns the requestType
	 * 
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the requestType
	 * 
	 * @param requestType 
	 *        The requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Returns the requestTime
	 * 
	 * @return the requestTime
	 */
	public Long getRequestTime() {
		return requestTime;
	}

	/**
	 * Sets the requestTime
	 * 
	 * @param requestTime 
	 *        The requestTime to set
	 */
	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BaseRequestVO.java                                                            $
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/