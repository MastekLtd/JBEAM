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
 * 
 * The user profile class that contains the user related information 
 * as set in the table USER_MASTER
 * 
 * @author mandar.vaidya
 *
 */
public class UserProfile extends UserCredentials {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/** Fields from the USER_MASTER tables and are self explanatory */
	private String userName;
	private String telephoneNo;
	private String faxNo;
	private String emailId;
	private Long effDate;
	private Long expDate;
	private Long createdOn;
	private String createdBy;
	private String installationCode;
	private Long modifiedOn;
	private String modifiedBy;
	private String hintQuestion;
	private String hintAnswer;
	private String forcePasswordFlag;
	private String adminRole;
	private String connectRole;
	private String defaultView;	
	
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
	 * Gets the hintQuestion
	 *
	 * @return the hintQuestion
	 */
	public String getHintQuestion() {
		return hintQuestion;
	}
	/**
	 * Sets the hintQuestion
	 *
	 * @param hintQuestion 
	 *        The hintQuestion to set.
	 */
	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}
	/**
	 * Gets the hintAnswer
	 *
	 * @return the hintAnswer
	 */
	public String getHintAnswer() {
		return hintAnswer;
	}
	/**
	 * Sets the hintAnswer
	 *
	 * @param hintAnswer 
	 *        The hintAnswer to set.
	 */
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
	/**
	 * Sets the forcePasswordFlag
	 *
	 * @param forcePasswordFlag 
	 *        The forcePasswordFlag to set.
	 */
	public void setForcePasswordFlag(String forcePasswordFlag) {
		this.forcePasswordFlag = forcePasswordFlag;
	}
	/**
	 * Gets the forcePasswordFlag
	 *
	 * @return the forcePasswordFlag
	 */
	public String getForcePasswordFlag() {
		return forcePasswordFlag;
	}
	/**
	 * Gets the adminRole
	 *
	 * @return the adminRole
	 */
	public String getAdminRole() {
		return adminRole;
	}
	/**
	 * Sets the adminRole
	 *
	 * @param adminRole 
	 *        The adminRole to set.
	 */
	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}
	/**
	 * Gets the connectRole
	 *
	 * @return the connectRole
	 */
	public String getConnectRole() {
		return connectRole;
	}
	/**
	 * Sets the connectRole
	 *
	 * @param connectRole 
	 *        The connectRole to set.
	 */
	public void setConnectRole(String connectRole) {
		this.connectRole = connectRole;
	}
	/**
	 * Sets the defaultView
	 *
	 * @param defaultView 
	 *        The defaultView to set
	 */
	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
	/**
	 * Gets the defaultView
	 *
	 * @return the defaultView
	 */
	public String getDefaultView() {
		return defaultView;
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/UserProfile.java                                                              $
 * 
 * 5     7/14/10 3:36p Mandar.vaidya
 * Added field forcePasswordFlag with getter and setter methods.
 * 
 * 4     7/12/10 2:37p Mandar.vaidya
 * Added fields hintQuestion and hintAnswer with getter and setter methods.
 * 
 * 3     7/08/10 3:35p Mandar.vaidya
 * Added fields private Long modifiedOn and private String modifiedBy with getter and setter methods.
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/