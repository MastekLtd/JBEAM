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

import java.util.List;

public class CResBatchSummary extends CBaseResponseVO {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;

	/* Fields from the BATCH table and are self explanatory */
	private Integer batchNo;
	private Integer batchRevNo;
	private String batchName;
	private String batchType;
	private String batchTypeValue;
	private Long execStartTime;
	private Long execEndTime;
	private String batchStartUser;
	private String batchEndUser;
	private String failedOver;
	
	/** The progress levels for the batch */
	private List<CResBatchProgressLevel> progressLevels;
	
	
	
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
	 * Gets the batchType
	 *
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}
	/**
	 * Sets the batchType
	 *
	 * @param batchType 
	 *        The batchType to set.
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	/**
	 * Gets the batchTypeValue
	 *
	 * @return the batchTypeValue
	 */
	public String getBatchTypeValue() {
		return batchTypeValue;
	}
	/**
	 * Sets the batchTypeValue
	 *
	 * @param batchTypeValue 
	 *        The batchTypeValue to set.
	 */
	public void setBatchTypeValue(String batchTypeValue) {
		this.batchTypeValue = batchTypeValue;
	}
	/**
	 * Gets the execStartTime
	 *
	 * @return the execStartTime
	 */
	public Long getExecStartTime() {
		return execStartTime;
	}
	/**
	 * Sets the execStartTime
	 *
	 * @param execStartTime 
	 *        The execStartTime to set.
	 */
	public void setExecStartTime(Long execStartTime) {
		this.execStartTime = execStartTime;
	}
	/**
	 * Gets the execEndTime
	 *
	 * @return the execEndTime
	 */
	public Long getExecEndTime() {
		return execEndTime;
	}
	/**
	 * Sets the execEndTime
	 *
	 * @param execEndTime 
	 *        The execEndTime to set.
	 */
	public void setExecEndTime(Long execEndTime) {
		this.execEndTime = execEndTime;
	}
	/**
	 * Gets the batchStartUser
	 *
	 * @return the batchStartUser
	 */
	public String getBatchStartUser() {
		return batchStartUser;
	}
	/**
	 * Sets the batchStartUser
	 *
	 * @param batchStartUser 
	 *        The batchStartUser to set.
	 */
	public void setBatchStartUser(String batchStartUser) {
		this.batchStartUser = batchStartUser;
	}
	/**
	 * Gets the batchEndUser
	 *
	 * @return the batchEndUser
	 */
	public String getBatchEndUser() {
		return batchEndUser;
	}
	/**
	 * Sets the batchEndUser
	 *
	 * @param batchEndUser 
	 *        The batchEndUser to set.
	 */
	public void setBatchEndUser(String batchEndUser) {
		this.batchEndUser = batchEndUser;
	}
	/**
	 * Returns the progressLevels
	 * 
	 * @return the progressLevels
	 */
	public List<CResBatchProgressLevel> getProgressLevels() {
		return progressLevels;
	}
	/**
	 * Sets the progressLevels
	 * 
	 * @param progressLevels 
	 *        The progresLevels to set
	 */
	public void setProgressLevels(List<CResBatchProgressLevel> progressLevels) {
		this.progressLevels = progressLevels;
	}
	/**
	 * Sets the failedOver
	 *
	 * @param failedOver 
	 *        The failedOver to set.
	 */
	public void setFailedOver(String failedOver) {
		this.failedOver = failedOver;
	}
	/**
	 * Gets the failedOver
	 *
	 * @return the failedOver
	 */
	public String getFailedOver() {
		return failedOver;
	}

	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CResBatchSummary.java                                                                 $
 * 
 * 4     3/11/10 2:15p Mandar.vaidya
 * Added failedOver field with getter & setter methods.
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/