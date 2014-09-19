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
 * Class to hold the batch log data.
 *
 * @author Mandar Vaidya
 *
 */
public class BatchLog implements Serializable {

	/** Default Serial Version UID	 */
	private static final long serialVersionUID = 1L;

	/* Fields from the LOG table and are self explanatory */
	private String installationCode;
	private Integer seqNo;
	private Integer batchNo;
	private Integer batchRevNo;
	private String beSeqNo;
	private String taskName; 
	private Long objExecStartTime;
	private Long objExecEndTime;
	private String status;
	private String sysActNo; 
	private String userPriority;
	private Integer priorityCode1;
	private Integer priorityCode2;
	private String prePost;
	private String jobType;
	private String line;
	private String subline;
	private String broker; 
	private String policyNo; 
	private String policyRenewNo;
	private String vehRefNo; 
	private String cashBatchNo; 
	private String cashBatchRevNo; 
	private String gbiBillNo; 
	private String printFormNo; 
	private String notifyErrorTo;
	private Long dateGenerate;
	private String generateBy;
	private String recMessage;
	private String jobDesc;
	private String objectName;
	private Long dateExecuted;
	private Integer listInd;
	private String entityType;
	private String entityCode;
	private String refSystemActivityNo;
	private String errorType; 
	private String errorDescription;
	private Integer cycleNo;
	private String operationCode;
	private Integer usedMemoryBefore;
	private Integer usedMemoryAfter;
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
	 *        The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * Gets the seqNo
	 *
	 * @return the seqNo
	 */
	public Integer getSeqNo() {
		return seqNo;
	}
	/**
	 * Sets the seqNo
	 *
	 * @param seqNo 
	 *        The seqNo to set
	 */
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
	 *        The batchNo to set
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
	 *        The batchRevNo to set
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * Gets the beSeqNo
	 *
	 * @return the beSeqNo
	 */
	public String getBeSeqNo() {
		return beSeqNo;
	}
	/**
	 * Sets the beSeqNo
	 *
	 * @param beSeqNo 
	 *        The beSeqNo to set
	 */
	public void setBeSeqNo(String beSeqNo) {
		this.beSeqNo = beSeqNo;
	}
	/**
	 * Gets the taskName
	 *
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * Sets the taskName
	 *
	 * @param taskName 
	 *        The taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * Gets the objExecStartTime
	 *
	 * @return the objExecStartTime
	 */
	public Long getObjExecStartTime() {
		return objExecStartTime;
	}
	/**
	 * Sets the objExecStartTime
	 *
	 * @param objExecStartTime 
	 *        The objExecStartTime to set
	 */
	public void setObjExecStartTime(Long objExecStartTime) {
		this.objExecStartTime = objExecStartTime;
	}
	/**
	 * Gets the objExecEndTime
	 *
	 * @return the objExecEndTime
	 */
	public Long getObjExecEndTime() {
		return objExecEndTime;
	}
	/**
	 * Sets the objExecEndTime
	 *
	 * @param objExecEndTime 
	 *        The objExecEndTime to set
	 */
	public void setObjExecEndTime(Long objExecEndTime) {
		this.objExecEndTime = objExecEndTime;
	}
	/**
	 * Gets the status
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status
	 *
	 * @param status 
	 *        The status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Gets the sysActNo
	 *
	 * @return the sysActNo
	 */
	public String getSysActNo() {
		return sysActNo;
	}
	/**
	 * Sets the sysActNo
	 *
	 * @param sysActNo 
	 *        The sysActNo to set
	 */
	public void setSysActNo(String sysActNo) {
		this.sysActNo = sysActNo;
	}
	/**
	 * Gets the userPriority
	 *
	 * @return the userPriority
	 */
	public String getUserPriority() {
		return userPriority;
	}
	/**
	 * Sets the userPriority
	 *
	 * @param userPriority 
	 *        The userPriority to set
	 */
	public void setUserPriority(String userPriority) {
		this.userPriority = userPriority;
	}
	/**
	 * Gets the priorityCode1
	 *
	 * @return the priorityCode1
	 */
	public Integer getPriorityCode1() {
		return priorityCode1;
	}
	/**
	 * Sets the priorityCode1
	 *
	 * @param priorityCode1 
	 *        The priorityCode1 to set
	 */
	public void setPriorityCode1(Integer priorityCode1) {
		this.priorityCode1 = priorityCode1;
	}
	/**
	 * Gets the priorityCode2
	 *
	 * @return the priorityCode2
	 */
	public Integer getPriorityCode2() {
		return priorityCode2;
	}
	/**
	 * Sets the priorityCode2
	 *
	 * @param priorityCode2 
	 *        The priorityCode2 to set
	 */
	public void setPriorityCode2(Integer priorityCode2) {
		this.priorityCode2 = priorityCode2;
	}
	/**
	 * Gets the prePost
	 *
	 * @return the prePost
	 */
	public String getPrePost() {
		return prePost;
	}
	/**
	 * Sets the prePost
	 *
	 * @param prePost 
	 *        The prePost to set
	 */
	public void setPrePost(String prePost) {
		this.prePost = prePost;
	}
	/**
	 * Gets the jobType
	 *
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * Sets the jobType
	 *
	 * @param jobType 
	 *        The jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * Gets the line
	 *
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	/**
	 * Sets the line
	 *
	 * @param line 
	 *        The line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
	/**
	 * Gets the subline
	 *
	 * @return the subline
	 */
	public String getSubline() {
		return subline;
	}
	/**
	 * Sets the subline
	 *
	 * @param subline 
	 *        The subline to set
	 */
	public void setSubline(String subline) {
		this.subline = subline;
	}
	/**
	 * Gets the broker
	 *
	 * @return the broker
	 */
	public String getBroker() {
		return broker;
	}
	/**
	 * Sets the broker
	 *
	 * @param broker 
	 *        The broker to set
	 */
	public void setBroker(String broker) {
		this.broker = broker;
	}
	/**
	 * Gets the policyNo
	 *
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}
	/**
	 * Sets the policyNo
	 *
	 * @param policyNo 
	 *        The policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	/**
	 * Gets the policyRenewNo
	 *
	 * @return the policyRenewNo
	 */
	public String getPolicyRenewNo() {
		return policyRenewNo;
	}
	/**
	 * Sets the policyRenewNo
	 *
	 * @param policyRenewNo 
	 *        The policyRenewNo to set
	 */
	public void setPolicyRenewNo(String policyRenewNo) {
		this.policyRenewNo = policyRenewNo;
	}
	/**
	 * Gets the vehRefNo
	 *
	 * @return the vehRefNo
	 */
	public String getVehRefNo() {
		return vehRefNo;
	}
	/**
	 * Sets the vehRefNo
	 *
	 * @param vehRefNo 
	 *        The vehRefNo to set
	 */
	public void setVehRefNo(String vehRefNo) {
		this.vehRefNo = vehRefNo;
	}
	/**
	 * Gets the cashBatchNo
	 *
	 * @return the cashBatchNo
	 */
	public String getCashBatchNo() {
		return cashBatchNo;
	}
	/**
	 * Sets the cashBatchNo
	 *
	 * @param cashBatchNo 
	 *        The cashBatchNo to set
	 */
	public void setCashBatchNo(String cashBatchNo) {
		this.cashBatchNo = cashBatchNo;
	}
	/**
	 * Gets the cashBatchRevNo
	 *
	 * @return the cashBatchRevNo
	 */
	public String getCashBatchRevNo() {
		return cashBatchRevNo;
	}
	/**
	 * Sets the cashBatchRevNo
	 *
	 * @param cashBatchRevNo 
	 *        The cashBatchRevNo to set
	 */
	public void setCashBatchRevNo(String cashBatchRevNo) {
		this.cashBatchRevNo = cashBatchRevNo;
	}
	/**
	 * Gets the gbiBillNo
	 *
	 * @return the gbiBillNo
	 */
	public String getGbiBillNo() {
		return gbiBillNo;
	}
	/**
	 * Sets the gbiBillNo
	 *
	 * @param gbiBillNo 
	 *        The gbiBillNo to set
	 */
	public void setGbiBillNo(String gbiBillNo) {
		this.gbiBillNo = gbiBillNo;
	}
	/**
	 * Gets the printFormNo
	 *
	 * @return the printFormNo
	 */
	public String getPrintFormNo() {
		return printFormNo;
	}
	/**
	 * Sets the printFormNo
	 *
	 * @param printFormNo 
	 *        The printFormNo to set
	 */
	public void setPrintFormNo(String printFormNo) {
		this.printFormNo = printFormNo;
	}
	/**
	 * Gets the notifyErrorTo
	 *
	 * @return the notifyErrorTo
	 */
	public String getNotifyErrorTo() {
		return notifyErrorTo;
	}
	/**
	 * Sets the notifyErrorTo
	 *
	 * @param notifyErrorTo 
	 *        The notifyErrorTo to set
	 */
	public void setNotifyErrorTo(String notifyErrorTo) {
		this.notifyErrorTo = notifyErrorTo;
	}
	/**
	 * Gets the dateGenerate
	 *
	 * @return the dateGenerate
	 */
	public Long getDateGenerate() {
		return dateGenerate;
	}
	/**
	 * Sets the dateGenerate
	 *
	 * @param dateGenerate 
	 *        The dateGenerate to set
	 */
	public void setDateGenerate(Long dateGenerate) {
		this.dateGenerate = dateGenerate;
	}
	/**
	 * Gets the generateBy
	 *
	 * @return the generateBy
	 */
	public String getGenerateBy() {
		return generateBy;
	}
	/**
	 * Sets the generateBy
	 *
	 * @param generateBy 
	 *        The generateBy to set
	 */
	public void setGenerateBy(String generateBy) {
		this.generateBy = generateBy;
	}
	/**
	 * Gets the recMessage
	 *
	 * @return the recMessage
	 */
	public String getRecMessage() {
		return recMessage;
	}
	/**
	 * Sets the recMessage
	 *
	 * @param recMessage 
	 *        The recMessage to set
	 */
	public void setRecMessage(String recMessage) {
		this.recMessage = recMessage;
	}
	/**
	 * Gets the jobDesc
	 *
	 * @return the jobDesc
	 */
	public String getJobDesc() {
		return jobDesc;
	}
	/**
	 * Sets the jobDesc
	 *
	 * @param jobDesc 
	 *        The jobDesc to set
	 */
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	/**
	 * Gets the objectName
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
	 * Gets the dateExecuted
	 *
	 * @return the dateExecuted
	 */
	public Long getDateExecuted() {
		return dateExecuted;
	}
	/**
	 * Sets the dateExecuted
	 *
	 * @param dateExecuted 
	 *        The dateExecuted to set
	 */
	public void setDateExecuted(Long dateExecuted) {
		this.dateExecuted = dateExecuted;
	}
	/**
	 * Gets the listInd
	 *
	 * @return the listInd
	 */
	public Integer getListInd() {
		return listInd;
	}
	/**
	 * Sets the listInd
	 *
	 * @param listInd 
	 *        The listInd to set
	 */
	public void setListInd(Integer listInd) {
		this.listInd = listInd;
	}
	/**
	 * Gets the entityType
	 *
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}
	/**
	 * Sets the entityType
	 *
	 * @param entityType 
	 *        The entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	/**
	 * Gets the entityCode
	 *
	 * @return the entityCode
	 */
	public String getEntityCode() {
		return entityCode;
	}
	/**
	 * Sets the entityCode
	 *
	 * @param entityCode 
	 *        The entityCode to set
	 */
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	/**
	 * Gets the refSystemActivityNo
	 *
	 * @return the refSystemActivityNo
	 */
	public String getRefSystemActivityNo() {
		return refSystemActivityNo;
	}
	/**
	 * Sets the refSystemActivityNo
	 *
	 * @param refSystemActivityNo 
	 *        The refSystemActivityNo to set
	 */
	public void setRefSystemActivityNo(String refSystemActivityNo) {
		this.refSystemActivityNo = refSystemActivityNo;
	}
	/**
	 * Gets the errorType
	 *
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * Sets the errorType
	 *
	 * @param errorType 
	 *        The errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * Gets the errorDescription
	 *
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * Sets the errorDescription
	 *
	 * @param errorDescription 
	 *        The errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * Gets the cycleNo
	 *
	 * @return the cycleNo
	 */
	public Integer getCycleNo() {
		return cycleNo;
	}
	/**
	 * Sets the cycleNo
	 *
	 * @param cycleNo 
	 *        The cycleNo to set
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}
	/**
	 * Gets the operationCode
	 *
	 * @return the operationCode
	 */
	public String getOperationCode() {
		return operationCode;
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
	 * Gets the usedMemoryBefore
	 *
	 * @return the usedMemoryBefore
	 */
	public Integer getUsedMemoryBefore() {
		return usedMemoryBefore;
	}
	/**
	 * Sets the usedMemoryBefore
	 *
	 * @param usedMemoryBefore 
	 *        The usedMemoryBefore to set
	 */
	public void setUsedMemoryBefore(Integer usedMemoryBefore) {
		this.usedMemoryBefore = usedMemoryBefore;
	}
	/**
	 * Gets the usedMemoryAfter
	 *
	 * @return the usedMemoryAfter
	 */
	public Integer getUsedMemoryAfter() {
		return usedMemoryAfter;
	}
	/**
	 * Sets the usedMemoryAfter
	 *
	 * @param usedMemoryAfter 
	 *        The usedMemoryAfter to set
	 */
	public void setUsedMemoryAfter(Integer usedMemoryAfter) {
		this.usedMemoryAfter = usedMemoryAfter;
	}
}
