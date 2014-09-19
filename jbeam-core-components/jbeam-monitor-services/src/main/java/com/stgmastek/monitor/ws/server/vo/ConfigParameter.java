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
import java.util.Date;

public class ConfigParameter implements Serializable {
	 	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String masterCode;
	private String subCode;
	private String description;
    private Integer numericValue;
    private String charValue;
    private String charValue2;
    private String charValue4;
    private Date dateValue;
    private String orderNo;
    private String parentId;
    private Boolean excludeAllOption;
    private String charValue3;
    private String charValue5;
    private String charValue6;
    private String charValue7;
    private String charValue8;
    private String createdBy;
    private String modifiedBy;
    private Date modifiedDate;
    private String zeeValueCheck;
	/**
	 * Gets the masterCode
	 *
	 * @return the masterCode
	 */
	public String getMasterCode() {
		return masterCode;
	}
	/**
	 * Sets the masterCode
	 *
	 * @param masterCode 
	 *	      The masterCode to set
	 */
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}
	/**
	 * Gets the subCode
	 *
	 * @return the subCode
	 */
	public String getSubCode() {
		return subCode;
	}
	/**
	 * Sets the subCode
	 *
	 * @param subCode 
	 *	      The subCode to set
	 */
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	/**
	 * Gets the description
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
	 *	      The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the numericValue
	 *
	 * @return the numericValue
	 */
	public Integer getNumericValue() {
		return numericValue;
	}
	/**
	 * Sets the numericValue
	 *
	 * @param numericValue 
	 *	      The numericValue to set
	 */
	public void setNumericValue(Integer numericValue) {
		this.numericValue = numericValue;
	}
	/**
	 * Gets the charValue
	 *
	 * @return the charValue
	 */
	public String getCharValue() {
		return charValue;
	}
	/**
	 * Sets the charValue
	 *
	 * @param charValue 
	 *	      The charValue to set
	 */
	public void setCharValue(String charValue) {
		this.charValue = charValue;
	}
	/**
	 * Gets the charValue2
	 *
	 * @return the charValue2
	 */
	public String getCharValue2() {
		return charValue2;
	}
	/**
	 * Sets the charValue2
	 *
	 * @param charValue2 
	 *	      The charValue2 to set
	 */
	public void setCharValue2(String charValue2) {
		this.charValue2 = charValue2;
	}
	/**
	 * Gets the charValue4
	 *
	 * @return the charValue4
	 */
	public String getCharValue4() {
		return charValue4;
	}
	/**
	 * Sets the charValue4
	 *
	 * @param charValue4 
	 *	      The charValue4 to set
	 */
	public void setCharValue4(String charValue4) {
		this.charValue4 = charValue4;
	}
	/**
	 * Gets the dateValue
	 *
	 * @return the dateValue
	 */
	public Date getDateValue() {
		return dateValue;
	}
	/**
	 * Sets the dateValue
	 *
	 * @param dateValue 
	 *	      The dateValue to set
	 */
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	/**
	 * Gets the orderNo
	 *
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	/**
	 * Sets the orderNo
	 *
	 * @param orderNo 
	 *	      The orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * Gets the parentId
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * Sets the parentId
	 *
	 * @param parentId 
	 *	      The parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * Gets the excludeAllOption
	 *
	 * @return the excludeAllOption
	 */
	public Boolean getExcludeAllOption() {
		return excludeAllOption;
	}
	/**
	 * Sets the excludeAllOption
	 *
	 * @param excludeAllOption 
	 *	      The excludeAllOption to set
	 */
	public void setExcludeAllOption(Boolean excludeAllOption) {
		this.excludeAllOption = excludeAllOption;
	}
	/**
	 * Gets the charValue3
	 *
	 * @return the charValue3
	 */
	public String getCharValue3() {
		return charValue3;
	}
	/**
	 * Sets the charValue3
	 *
	 * @param charValue3 
	 *	      The charValue3 to set
	 */
	public void setCharValue3(String charValue3) {
		this.charValue3 = charValue3;
	}
	/**
	 * Gets the charValue5
	 *
	 * @return the charValue5
	 */
	public String getCharValue5() {
		return charValue5;
	}
	/**
	 * Sets the charValue5
	 *
	 * @param charValue5 
	 *	      The charValue5 to set
	 */
	public void setCharValue5(String charValue5) {
		this.charValue5 = charValue5;
	}
	/**
	 * Gets the charValue6
	 *
	 * @return the charValue6
	 */
	public String getCharValue6() {
		return charValue6;
	}
	/**
	 * Sets the charValue6
	 *
	 * @param charValue6 
	 *	      The charValue6 to set
	 */
	public void setCharValue6(String charValue6) {
		this.charValue6 = charValue6;
	}
	/**
	 * Gets the charValue7
	 *
	 * @return the charValue7
	 */
	public String getCharValue7() {
		return charValue7;
	}
	/**
	 * Sets the charValue7
	 *
	 * @param charValue7 
	 *	      The charValue7 to set
	 */
	public void setCharValue7(String charValue7) {
		this.charValue7 = charValue7;
	}
	/**
	 * Gets the charValue8
	 *
	 * @return the charValue8
	 */
	public String getCharValue8() {
		return charValue8;
	}
	/**
	 * Sets the charValue8
	 *
	 * @param charValue8 
	 *	      The charValue8 to set
	 */
	public void setCharValue8(String charValue8) {
		this.charValue8 = charValue8;
	}
	/**
	 * Gets the createdBy
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * Sets the createdBy
	 *
	 * @param createdBy 
	 *	      The createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * Gets the modifiedBy
	 *
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * Sets the modifiedBy
	 *
	 * @param modifiedBy 
	 *	      The modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * Gets the modifiedDate
	 *
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * Sets the modifiedDate
	 *
	 * @param modifiedDate 
	 *	      The modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * Gets the zeeValueCheck
	 *
	 * @return the zeeValueCheck
	 */
	public String getZeeValueCheck() {
		return zeeValueCheck;
	}
	/**
	 * Sets the zeeValueCheck
	 *
	 * @param zeeValueCheck 
	 *	      The zeeValueCheck to set
	 */
	public void setZeeValueCheck(String zeeValueCheck) {
		this.zeeValueCheck = zeeValueCheck;
	}
    
}
