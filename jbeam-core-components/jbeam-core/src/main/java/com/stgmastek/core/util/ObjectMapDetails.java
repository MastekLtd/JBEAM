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
 *
 * POJO class mapping the OBJECT_MAP table 
 * 	
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public class ObjectMapDetails implements Serializable{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 3811351823706565693L;

	/** All fields are self explanatory*/
	private String id;
	private String objectName;
	private String defaultValues;
	private String objectType;
	private String onFailExit;
	private String onFailEmail;
	private Integer minTime;
	private Integer avgTime;
	private Integer maxTime;
	private String escalationLevel;
	private String caseData;

	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id
	 * 
	 * @param id 
	 *        The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Returns the objectName
	 * 
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}
	
	/**
	 * Sets the objectName
	 * 
	 * @param objectName 
	 *        The objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	/**
	 * Returns the defaultValues
	 * 
	 * @return the defaultValues
	 */
	public String getDefaultValues() {
		return defaultValues;
	}
	
	/**
	 * Sets the defaultValues
	 * 
	 * @param defaultValues 
	 *        The defaultValues to set
	 */
	public void setDefaultValues(String defaultValues) {
		this.defaultValues = defaultValues;
	}
	
	/**
	 * Returns the objectType
	 * 
	 * @return the objectType
	 */
	public String getObjectType() {
		return objectType;
	}
	
	/**
	 * Sets the objectType
	 * 
	 * @param objectType 
	 *        The objectType to set
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * Returns the onFailExit
	 * 
	 * @return the onFailExit
	 */
	
	public String getOnFailExit() {
		return onFailExit;
	}

	/**
	 * Sets the onFailExit to set
	 * 
	 * @param onFailExit
	 * 		  The onFailExit to set
	 */
	
	public void setOnFailExit(String onFailExit) {
		this.onFailExit = onFailExit;
	}

	/**
	 * Returns the string representation of the object 
	 * 
	 * @return the string representation of the object
	 */
	
	public String toString() {
		return getId() + ":" + getObjectName() + ":" + getObjectType() + ":" + getOnFailExit() + ":" + getOnFailEmail() + ":[" + getDefaultValues() + "]";  
	}

	/**
	 * Sets the <code>on_fail_email</code>.
	 * 
	 * @param onFailEmail the onFailEmail to set
	 */
	public void setOnFailEmail(String onFailEmail) {
		this.onFailEmail = onFailEmail;
	}

	/**
	 * Returns the <code>on_fail_email</code>.
	 * @return the onFailEmail
	 */
	public String getOnFailEmail() {
		return onFailEmail;
	}
	
	/**
	 * Returns the minimum time the object took to execute.
	 * @return the minTime
	 */
	public Integer getMinTime() {
		return minTime;
	}

	/**
	 * Sets the minimum time.
	 * 
	 * @param minTime the minTime to set
	 */
	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}

	/**
	 * Returns the average time.
	 * 
	 * @return the avgTime
	 */
	public Integer getAvgTime() {
		return avgTime;
	}

	/**
	 * Sets the average time.
	 * 
	 * @param avgTime the avgTime to set
	 */
	public void setAvgTime(Integer avgTime) {
		this.avgTime = avgTime;
	}

	/**
	 * Returns the maximum time.
	 * 
	 * @return the maxTime
	 */
	public Integer getMaxTime() {
		return maxTime;
	}

	/**
	 * Sets the maximum time.
	 * 
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * Sets the escalation level of the object.
	 * @param escalationLevel the escalationLevel to set
	 */
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	/**
	 * Returns the escalation level associated with this object.
	 * The escalation level drives the recipients of the email.
	 * @return the escalationLevel
	 */
	public String getEscalationLevel() {
		return escalationLevel;
	}

	/**
	 * @param caseData the caseData to set
	 */
	public void setCaseData(String caseData) {
		this.caseData = caseData;
	}

	/**
	 * @return the caseData
	 */
	public String getCaseData() {
		return caseData;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ObjectMapDetails.java                                                                               $
 * 
 * 8     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 7     4/13/10 1:44p Kedarr
 * Add field escalation level
 * 
 * 6     3/26/10 12:47p Kedarr
 * Changes made to add 3 new columns
 * 
 * 5     3/24/10 12:45p Kedarr
 * Add a new variable and its corresponding setters and getters.
 * 
 * 4     12/23/09 3:25p Grahesh
 * Added new attribute onFailExit
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/