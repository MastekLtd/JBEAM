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

/**
 * This class holds the installation data
 *
 * @author Kedar Raybagkar
 *
 */
public class Installation implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String installationDesc;
	private Long effDate;
	private Long expDate;
	private Long createdOn;
	private String createdBy;
	private Long modifiedOn;
	private String modifiedBy;
	private Integer batchNo;
	private Integer batchRevNo;
	private String timezoneId;
	/**
	 * Gets the installationCode
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode
	 *
	 * @param installationCode 
	 *        The installationCode to set.
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * Gets the installationDesc
	 *
	 * @return the installationDesc
	 */
	public String getInstallationDesc() {
		return installationDesc;
	}
	/**
	 * Sets the installationDesc
	 *
	 * @param installationDesc 
	 *        The installationDesc to set.
	 */
	public void setInstallationDesc(String installationDesc) {
		this.installationDesc = installationDesc;
	}
	/**
	 * Gets the effDate
	 *
	 * @return the effDate
	 */
	public Long getEffDate() {
		return effDate;
	}
	/**
	 * Sets the effDate
	 *
	 * @param effDate 
	 *        The effDate to set.
	 */
	public void setEffDate(Long effDate) {
		this.effDate = effDate;
	}
	/**
	 * Gets the expDate
	 *
	 * @return the expDate
	 */
	public Long getExpDate() {
		return expDate;
	}
	/**
	 * Sets the expDate
	 *
	 * @param expDate 
	 *        The expDate to set.
	 */
	public void setExpDate(Long expDate) {
		this.expDate = expDate;
	}
	/**
	 * Gets the createdOn
	 *
	 * @return the createdOn
	 */
	public Long getCreatedOn() {
		return createdOn;
	}
	/**
	 * Sets the createdOn
	 *
	 * @param createdOn 
	 *        The createdOn to set.
	 */
	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
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
	 *        The createdBy to set.
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * Gets the modifiedOn
	 *
	 * @return the modifiedOn
	 */
	public Long getModifiedOn() {
		return modifiedOn;
	}
	/**
	 * Sets the modifiedOn
	 *
	 * @param modifiedOn 
	 *        The modifiedOn to set.
	 */
	public void setModifiedOn(Long modifiedOn) {
		this.modifiedOn = modifiedOn;
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
	 *        The modifiedBy to set.
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * Gets the batchNo
	 *
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * Sets the batchNo
	 *
	 * @param batchNo 
	 *        The batchNo to set.
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * Gets the batchRevNo
	 *
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * Sets the batchRevNo
	 *
	 * @param batchRevNo 
	 *        The batchRevNo to set.
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * Gets the timezoneId
	 *
	 * @return the timezoneId
	 */
	public String getTimezoneId() {
		return timezoneId;
	}
	/**
	 * Sets the timezoneId
	 *
	 * @param timezoneId 
	 *        The timezoneId to set.
	 */
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

}
