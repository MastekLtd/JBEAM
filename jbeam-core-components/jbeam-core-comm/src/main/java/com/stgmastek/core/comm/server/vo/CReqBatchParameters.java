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

public class CReqBatchParameters extends CBaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	/* Fields from the BATCH table and are self explanatory */
	private Integer id;
	private String installationCode;
	private String batchName;
	private Long batchStartTime;
	private Long batchEndTime;
	private String batchParameters;
	private Integer batchNo;
	private Integer batchRevNo;
	private String createdBy;
	private Long createdOn;
	/**
	 * Gets the id
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * Sets the id
	 *
	 * @param id 
	 *        The id to set.
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * Gets the batchName
	 *
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}
	/**
	 * Sets the batchName
	 *
	 * @param batchName 
	 *        The batchName to set.
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	/**
	 * Gets the batchStartTime
	 *
	 * @return the batchStartTime
	 */
	public Long getBatchStartTime() {
		return batchStartTime;
	}
	/**
	 * Sets the batchStartTime
	 *
	 * @param batchStartTime 
	 *        The batchStartTime to set.
	 */
	public void setBatchStartTime(Long batchStartTime) {
		this.batchStartTime = batchStartTime;
	}
	/**
	 * Gets the batchEndTime
	 *
	 * @return the batchEndTime
	 */
	public Long getBatchEndTime() {
		return batchEndTime;
	}
	/**
	 * Sets the batchEndTime
	 *
	 * @param batchEndTime 
	 *        The batchEndTime to set.
	 */
	public void setBatchEndTime(Long batchEndTime) {
		this.batchEndTime = batchEndTime;
	}
	/**
	 * Gets the batchParameters
	 *
	 * @return the batchParameters
	 */
	public String getBatchParameters() {
		return batchParameters;
	}
	/**
	 * Sets the batchParameters
	 *
	 * @param batchParameters 
	 *        The batchParameters to set.
	 */
	public void setBatchParameters(String batchParameters) {
		this.batchParameters = batchParameters;
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
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CReqBatchParameters.java                                                              $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/