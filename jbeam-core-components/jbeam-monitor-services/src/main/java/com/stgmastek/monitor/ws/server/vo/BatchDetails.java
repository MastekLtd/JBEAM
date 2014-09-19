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

public class BatchDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** Fields from the BATCH table and are self explanatory */
	private String installationCode;
	private Integer batchNo;
	private Integer batchRevNo;
	private String batchName;
	private String batchType;
	private Long execStartTime;
	private Long execEndTime;
	private String batchStartUser;
	private String batchEndUser;
	private Integer processId;
	private String batchEndReason;
	private String failedOver;
	private Integer instructionSeqNo;		
	

	
	/**
	 * Gets the installationCode.
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode.
	 *
	 * @param installationCode 
	 *        The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * Gets the batchNo.
	 *
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * Sets the batchNo.
	 *
	 * @param batchNo 
	 *        The batchNo to set
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * Gets the batchRevNo.
	 *
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * Sets the batchRevNo.
	 *
	 * @param batchRevNo 
	 *        The batchRevNo to set
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * Gets the batchName.
	 *
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}
	/**
	 * Sets the batchName.
	 *
	 * @param batchName 
	 *        The batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	/**
	 * Gets the batchType.
	 *
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}
	/**
	 * Sets the batchType.
	 *
	 * @param batchType 
	 *        The batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	/**
	 * Gets the execStartTime.
	 *
	 * @return the execStartTime
	 */
	public Long getExecStartTime() {
		return execStartTime;
	}
	/**
	 * Sets the execStartTime.
	 *
	 * @param execStartTime 
	 *        The execStartTime to set
	 */
	public void setExecStartTime(Long execStartTime) {
		this.execStartTime = execStartTime;
	}
	/**
	 * Gets the execEndTime.
	 *
	 * @return the execEndTime
	 */
	public Long getExecEndTime() {
		return execEndTime;
	}
	/**
	 * Sets the execEndTime.
	 *
	 * @param execEndTime 
	 *        The execEndTime to set
	 */
	public void setExecEndTime(Long execEndTime) {
		this.execEndTime = execEndTime;
	}
	/**
	 * Gets the batchStartUser.
	 *
	 * @return the batchStartUser
	 */
	public String getBatchStartUser() {
		return batchStartUser;
	}
	/**
	 * Sets the batchStartUser.
	 *
	 * @param batchStartUser 
	 *        The batchStartUser to set
	 */
	public void setBatchStartUser(String batchStartUser) {
		this.batchStartUser = batchStartUser;
	}
	/**
	 * Gets the batchEndUser.
	 *
	 * @return the batchEndUser
	 */
	public String getBatchEndUser() {
		return batchEndUser;
	}
	/**
	 * Sets the batchEndUser.
	 *
	 * @param batchEndUser 
	 *        The batchEndUser to set
	 */
	public void setBatchEndUser(String batchEndUser) {
		this.batchEndUser = batchEndUser;
	}
	/**
	 * Gets the processId.
	 *
	 * @return the processId
	 */
	public Integer getProcessId() {
		return processId;
	}
	/**
	 * Sets the processId.
	 *
	 * @param processId 
	 *        The processId to set
	 */
	public void setProcessId(Integer processId) {
		this.processId = processId;
	}
	/**
	 * Gets the batchEndReason.
	 *
	 * @return the batchEndReason
	 */
	public String getBatchEndReason() {
		return batchEndReason;
	}
	/**
	 * Sets the batchEndReason.
	 *
	 * @param batchEndReason 
	 *        The batchEndReason to set
	 */
	public void setBatchEndReason(String batchEndReason) {
		this.batchEndReason = batchEndReason;
	}
	/**
	 * Gets the failedOver
	 *
	 * @return the failedOver
	 */
	public String getFailedOver() {
		return failedOver;
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
	 * Gets the instructionSeqNo
	 *
	 * @return the instructionSeqNo
	 */
	public Integer getInstructionSeqNo() {
		return instructionSeqNo;
	}
	/**
	 * Sets the instructionSeqNo
	 *
	 * @param instructionSeqNo 
	 *        The instructionSeqNo to set.
	 */
	public void setInstructionSeqNo(Integer instructionSeqNo) {
		this.instructionSeqNo = instructionSeqNo;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BatchDetails.java                                                             $
 * 
 * 5     7/10/10 2:02p Mandar.vaidya
 * Removed the field instructionParameterList. Modified the javadoc comments.
 * 
 * 3     3/25/10 10:54a Mandar.vaidya
 * Removed the field maxMemory with getter and setter methods.
 * 
 * 2     3/22/10 12:02p Mandar.vaidya
 * Added 3 more fields viz failedOver, instructionSeqNo and maxMemory with getter and setter methods.
 * 
 * 1     1/06/10 1:26p Grahesh
 * Initial Version
*
*/