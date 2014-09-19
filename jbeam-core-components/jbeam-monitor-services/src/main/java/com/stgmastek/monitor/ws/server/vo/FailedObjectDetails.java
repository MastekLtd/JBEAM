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

import java.io.Serializable;

public class FailedObjectDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer failedObjectNo;
	private Integer failedObjectSequence;
	private Integer listenerId;
	private String failedObjectName;
	private String errorType;
	private String errorDescription;
	private String taskName;
	private Integer timeTaken;
	
	/**
	 * @return the failedObjectNo
	 */
	public Integer getFailedObjectNo() {
		return failedObjectNo;
	}
	/**
	 * @param failedObjectNo the failedObjectNo to set
	 */
	public void setFailedObjectNo(Integer failedObjectNo) {
		this.failedObjectNo = failedObjectNo;
	}
	
	
	/**
	 * @return the failedObjectSequence
	 */
	public Integer getFailedObjectSequence() {
		return failedObjectSequence;
	}
	/**
	 * @param failedObjectSequence the failedObjectSequence to set
	 */
	public void setFailedObjectSequence(Integer failedObjectSequence) {
		this.failedObjectSequence = failedObjectSequence;
	}
	/**
	 * @return the failedObjectName
	 */
	public String getFailedObjectName() {
		return failedObjectName;
	}
	/**
	 * @param failedObjectName the failedObjectName to set
	 */
	public void setFailedObjectName(String failedObjectName) {
		this.failedObjectName = failedObjectName;
	}
	/**
	 * @return the listenerId
	 */
	public Integer getListenerId() {
		return listenerId;
	}
	/**
	 * @param listenerId the listenerId to set
	 */
	public void setListenerId(Integer listenerId) {
		this.listenerId = listenerId;
	}
	/**
	 * Gets the errorType
	 *
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * Sets the errorType
	 *
	 * @param errorType 
	 *        The errorType to set.
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * Gets the errorDescription
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * Sets the errorDescription
	 *
	 * @param errorDescription 
	 *        The errorDescription to set.
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * Gets the taskName
	 *
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * Sets the taskName
	 *
	 * @param taskName 
	 *        The taskName to set.
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * Gets the timeTaken
	 *
	 * @return the timeTaken
	 */
	public Integer getTimeTaken() {
		return timeTaken;
	}
	/**
	 * Sets the timeTaken
	 *
	 * @param timeTaken 
	 *        The timeTaken to set.
	 */
	public void setTimeTaken(Integer timeTaken) {
		this.timeTaken = timeTaken;
	}


	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/FailedObjectDetails.java                                                      $
 * 
 * 3     1/06/10 11:31a Mandar.vaidya
 * Corrected the signature and object hierarchy
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/