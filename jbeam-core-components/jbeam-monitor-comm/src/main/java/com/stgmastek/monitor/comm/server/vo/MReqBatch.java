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

package com.stgmastek.monitor.comm.server.vo;

import java.io.Serializable;

/**
 * This class would hold BATCH data.
 * 
 * 
 * @author mandar.vaidya
 *
 */
public class MReqBatch extends MBaseRequestVO implements Serializable {

	/** Default Serial Version UID	 */
	private static final long serialVersionUID = -1404663389339103472L;

	/* Fields from the BATCH table and are self explanatory */
	private String installationCode;
	private Integer batchNo;
	private Integer batchRevNo;
	private String batchName;
	private String batchType;
	private Long execStartTime;
	private Long execEndTime;
	private String batchStartUser;
	private String batchEndUser;
	private Long processId;
	private String batchEndReason;
	private String failedOver;
	private Integer instructionSeqNo;
	private String operationCode;
	
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
	 * Returns the batchNo
	 * 
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * Sets the batchNo to set
	 * 
	 * @param batchNo
	 * 		  The batchNo to set
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * Returns the batchRevNo
	 * 
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * Sets the batchRevNo to set
	 * 
	 * @param batchRevNo
	 * 		  The batchRevNo to set
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * Returns the batchName
	 * 
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}
	/**
	 * Sets the batchName to set
	 * 
	 * @param batchName
	 * 		  The batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	/**
	 * Returns the batchType
	 * 
	 * @return the batchType
	 */
	public String getBatchType() {
		return batchType;
	}
	/**
	 * Sets the batchType to set
	 * 
	 * @param batchType
	 * 		  The batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}
	/**
	 * Returns the execStartTime
	 * 
	 * @return the execStartTime
	 */
	public Long getExecStartTime() {
		return execStartTime;
	}
	/**
	 * Sets the execStartTime to set
	 * 
	 * @param execStartTime
	 * 		  The execStartTime to set
	 */
	public void setExecStartTime(Long execStartTime) {
		this.execStartTime = execStartTime;
	}
	/**
	 * Returns the execEndTime
	 * 
	 * @return the execEndTime
	 */
	public Long getExecEndTime() {
		return execEndTime;
	}
	/**
	 * Sets the execEndTime to set
	 * 
	 * @param execEndTime
	 * 		  The execEndTime to set
	 */
	public void setExecEndTime(Long execEndTime) {
		this.execEndTime = execEndTime;
	}
	/**
	 * Returns the batchStartUser
	 * 
	 * @return the batchStartUser
	 */
	public String getBatchStartUser() {
		return batchStartUser;
	}
	/**
	 * Sets the batchStartUser to set
	 * 
	 * @param batchStartUser
	 * 		  The batchStartUser to set
	 */
	public void setBatchStartUser(String batchStartUser) {
		this.batchStartUser = batchStartUser;
	}
	/**
	 * Returns the batchEndUser
	 * 
	 * @return the batchEndUser
	 */
	public String getBatchEndUser() {
		return batchEndUser;
	}
	/**
	 * Sets the batchEndUser to set
	 * 
	 * @param batchEndUser
	 * 		  The batchEndUser to set
	 */
	public void setBatchEndUser(String batchEndUser) {
		this.batchEndUser = batchEndUser;
	}
	/**
	 * Returns the processId
	 * 
	 * @return the processId
	 */
	public Long getProcessId() {
		return processId;
	}
	/**
	 * Sets the processId to set
	 * 
	 * @param processId
	 * 		  The processId to set
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public void setProcessId(Integer processId) {
		this.processId =processId.longValue();
	}
	/**
	 * Sets the batchEndReason.
	 *
	 * @param batchEndReason 
	 *        The batchEndReason to set.
	 */
	public void setBatchEndReason(String batchEndReason) {
		this.batchEndReason = batchEndReason;
	}
	/**
	 * Returns the batchEndReason.
	 *
	 * @return the batchEndReason
	 */
	public String getBatchEndReason() {
		return batchEndReason;
	}
	/**
	 * Sets the failedOver.
	 *
	 * @param failedOver 
	 *        The failedOver to set.
	 */
	public void setFailedOver(String failedOver) {
		this.failedOver = failedOver;
	}
	/**
	 * Gets the failedOver.
	 *
	 * @return the failedOver
	 */
	public String getFailedOver() {
		return failedOver;
	}
	/**
	 * Sets the instructionSeqNo.
	 *
	 * @param instructionSeqNo 
	 *        The instructionSeqNo to set.
	 */
	public void setInstructionSeqNo(Integer instructionSeqNo) {
		this.instructionSeqNo = instructionSeqNo;
	}
	/**
	 * Gets the instructionSeqNo.
	 *
	 * @return the instructionSeqNo
	 */
	public Integer getInstructionSeqNo() {
		return instructionSeqNo;
	}
	/**
	 * Sets the operationCode
	 *
	 * @param operationCode 
	 *        The operationCode to set
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	/**
	 * Gets the operationCode
	 *
	 * @return the operationCode
	 */
	public String getOperationCode() {
		return operationCode;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MReqBatch.java                                                                  $
 * 
 * 8     3/22/10 1:24p Mandar.vaidya
 * Changes for maxMemory reverted.
 * 
 * 7     3/22/10 12:18p Mandar.vaidya
 * Added the field maxMemory with getter and setter methods.
 * 
 * 6     3/15/10 12:48p Mandar.vaidya
 * Added the field instructionSeqNo with getter and setter methods.
 * 
 * 5     3/11/10 2:22p Mandar.vaidya
 * Added failedOver field with getter & setter methods.
 * 
 * 4     3/03/10 11:11a Grahesh
 * Added batchEndReason with getter and setter methods
 * 
 * 3     12/18/09 4:19p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/