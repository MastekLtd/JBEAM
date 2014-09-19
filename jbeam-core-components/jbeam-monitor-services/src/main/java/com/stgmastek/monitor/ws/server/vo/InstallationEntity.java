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

import com.stgmastek.monitor.ws.util.Constants;

import stg.utils.StringUtils;

public class InstallationEntity implements Serializable{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String entity;	
	private String lookupColumn;
	private String lookupValue;
	private String valueColumn;
	private Integer precedenceOrder;
	private String onErrorFailAll;
	private String description;

	private Integer numberOfRequiredParameters;
	/**
	 * Returns the installationCode
	 * 
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode to set
	 * 
	 * @param installationCode
	 * 		  The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * Returns the entity
	 * 
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}
	/**
	 * Sets the entity to set
	 * 
	 * @param entity
	 * 		  The entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}
	/**
	 * Returns the lookupColumn
	 * 
	 * @return the lookupColumn
	 */
	public String getLookupColumn() {
		return lookupColumn;
	}
	/**
	 * Sets the lookupColumn to set
	 * 
	 * @param lookupColumn
	 * 		  The lookupColumn to set
	 */
	public void setLookupColumn(String lookupColumn) {
		this.lookupColumn = lookupColumn;
	}
	/**
	 * Returns the lookupValue
	 * 
	 * @return the lookupValue
	 */
	public String getLookupValue() {
		return lookupValue;
	}
	/**
	 * Sets the lookupValue to set
	 * 
	 * @param lookupValue
	 * 		  The lookupValue to set
	 */
	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}
	/**
	 * Returns the valueColumn
	 * 
	 * @return the valueColumn
	 */
	public String getValueColumn() {
		return valueColumn;
	}
	/**
	 * Sets the valueColumn to set
	 * 
	 * @param valueColumn
	 * 		  The valueColumn to set
	 */
	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
		setNumberOfRequiredParameters(StringUtils.countTokens(valueColumn, Constants.DELIMITER_CHAR, Constants.ESCAPE_CHAR));
	}
	/**
	 * Returns the precedenceOrder
	 * 
	 * @return the precedenceOrder
	 */
	public Integer getPrecedenceOrder() {
		return precedenceOrder;
	}
	/**
	 * Sets the precedenceOrder to set
	 * 
	 * @param precedenceOrder
	 * 		  The precedenceOrder to set
	 */
	public void setPrecedenceOrder(Integer precedenceOrder) {
		this.precedenceOrder = precedenceOrder;
	}
	/**
	 * Returns the onErrorFailAll
	 * 
	 * @return the onErrorFailAll
	 */
	public String getOnErrorFailAll() {
		return onErrorFailAll;
	}
	/**
	 * Sets the onErrorFailAll to set
	 * 
	 * @param onErrorFailAll
	 * 		  The onErrorFailAll to set
	 */
	public void setOnErrorFailAll(String onErrorFailAll) {
		this.onErrorFailAll = onErrorFailAll;
	}
	
	/**
	 * Returns the number of required parameters for the entity.
	 * @return the numberOfRequiredParameters
	 */
	public Integer getNumberOfRequiredParameters() {
		return numberOfRequiredParameters;
	}
	
	/**
	 * Sets the description.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	public void setNumberOfRequiredParameters(Integer numberOfRequiredParameters) {
		this.numberOfRequiredParameters = numberOfRequiredParameters;
	}	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/InstallationEntity.java                                                       $
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/