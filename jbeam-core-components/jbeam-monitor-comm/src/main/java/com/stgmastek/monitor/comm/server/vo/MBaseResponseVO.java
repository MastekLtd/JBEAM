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
package com.stgmastek.monitor.comm.server.vo;

/**
 * 
 * Base class for all web service responses
 * 
 * @author mandar.vaidya
 *
 */
public class MBaseResponseVO extends MBaseVO{

	/** Serial Version Id */
	private static final long serialVersionUID = 1L;

	/** 
	 * The response time
	 */
	private Long responseTime;
	
	/** Response Type */
	private String responseType;
	
	/** Response Description */
	private String description;

	/**
	 * Returns the responseTime
	 * 
	 * @return the responseTime
	 */
	public Long getResponseTime() {
		return responseTime;
	}

	/**
	 * Sets the responseTime
	 * 
	 * @param responseTime 
	 *        The responseTime to set
	 */
	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	/**
	 * Returns the responseType
	 * 
	 * @return the responseType
	 */
	public String getResponseType() {
		return responseType;
	}

	/**
	 * Sets the responseType
	 * 
	 * @param responseType 
	 *        The responseType to set
	 */
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * Returns the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description 
	 *        The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MBaseResponseVO.java                                                            $
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/