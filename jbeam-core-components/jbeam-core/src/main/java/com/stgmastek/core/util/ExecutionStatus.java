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
package com.stgmastek.core.util;

import java.io.Serializable;

/**
 * Simple class to record the execution status in case the batch has to be stopped 
 * 
 * @author grahesh.shanbhag
 *
 */
public class ExecutionStatus implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3994713109195851525L;
	
	/**
	 * Static final constants needed for different statuses 
	 * within the batch processing and are self explanatory
	 */
	public static final String ASSIGNMENT = "AS";  
	public static final String SCHEDULING = "SC";
	public static final String EXECUTION = "EX";
	
	/** 
	 * A constant to hold the in-progress status
	 * The status code at any stage would be always "IP"
	 * though can be enhanced in future if needed   
	 */
	private static final String STATUS_IN_PROGRESS = "IP";
	/**
	 * Default Constructor 
	 */
	public ExecutionStatus(){}
	
	/** The entity name */
	private String entity;
	
	/** 
	 * The stage within the processing 
	 * AS - Assignment
	 * SC - Scheduling  
	 * EX - Execution 
	 */
	private String stageCode;//AS/SC/EX
	
	/**
	 * The status code reserved for later enhancement 
	 * Usually would be - 
	 * IP - In Progress
	 */
	private String statusCode = STATUS_IN_PROGRESS;//IP
	
	/**
	 * Returns the entity
	 * 
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}
	
	/**
	 * Sets the entity
	 * 
	 * @param entity 
	 *        The entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	/**
	 * Returns the stageCode
	 * 
	 * @return the stageCode
	 */
	public String getStageCode() {
		return stageCode;
	}
	
	/**
	 * Sets the stageCode
	 * 
	 * @param stageCode 
	 *        The stageCode to set
	 */
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	
	/**
	 * Returns the statusCode
	 * 
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	
	/**
	 * Sets the statusCode
	 * 
	 * @param statusCode 
	 *        The statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * Returns the string representation of the class
	 * 
	 * @return the string representation of the class
	 */
	
	public String toString() {
		return getEntity() + ":" + getStageCode() + ":" + getStatusCode();
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ExecutionStatus.java                                                                                $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/