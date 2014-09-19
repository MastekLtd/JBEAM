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
package com.stgmastek.core.comm.server.vo;

/**
 * 
 * The user profile class that contains the user related information 
 * as set in the table USER_MASTER
 * 
 * @author grahesh.shanbhag
 *
 */
public class CResUserProfile extends CBaseResponseVO {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/* Fields from the USER_MASTER tables and are self explanatory */
	private String userId;
	private String userName;
	private String telephoneNo;
	private String faxNo;
	private String emailId;
	private Long effDate;
	private Long expDate;
	private Long createdOn;
	private String createdBy;
	private String assignedRole;
	/**
	 * Gets the userId
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId
	 *
	 * @param userId 
	 *        The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Gets the userName
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Sets the userName
	 *
	 * @param userName 
	 *        The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Gets the telephoneNo
	 *
	 * @return the telephoneNo
	 */
	public String getTelephoneNo() {
		return telephoneNo;
	}
	/**
	 * Sets the telephoneNo
	 *
	 * @param telephoneNo 
	 *        The telephoneNo to set.
	 */
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	/**
	 * Gets the faxNo
	 *
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}
	/**
	 * Sets the faxNo
	 *
	 * @param faxNo 
	 *        The faxNo to set.
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	/**
	 * Gets the emailId
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * Sets the emailId
	 *
	 * @param emailId 
	 *        The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * Gets the assignedRole
	 *
	 * @return the assignedRole
	 */
	public String getAssignedRole() {
		return assignedRole;
	}
	/**
	 * Sets the assignedRole
	 *
	 * @param assignedRole 
	 *        The assignedRole to set.
	 */
	public void setAssignedRole(String assignedRole) {
		this.assignedRole = assignedRole;
	}
		
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CResUserProfile.java                                                                  $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/