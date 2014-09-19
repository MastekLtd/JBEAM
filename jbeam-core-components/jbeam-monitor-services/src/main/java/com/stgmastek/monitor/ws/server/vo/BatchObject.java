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
 * Value Object class for BATCH_EXECUTOR table. 
 * All fields are used as it is. 
 * For more information check 
 * <a href="http://stgpedia.stgil-india.com/index.php/BATCH_EXECUTOR" >STGPedia</a> 
 * for the field (column) related information.
 *   
 * @author grahesh.shanbhag
 * 
 */
public class BatchObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Fields from the LOG table and are self explanatory */
	private String installationCode;
	private Integer batchNo;
	private Integer batchRevNo;
	private String beSeqNo;
	private String taskName; 
	private Long objExecStartTime;
	private Long objExecEndTime;
	private Long timeTaken;
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
	/**
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * @param installationCode the installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	/**
	 * @return the batchNo
	 */
	public Integer getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(Integer batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the batchRevNo
	 */
	public Integer getBatchRevNo() {
		return batchRevNo;
	}
	/**
	 * @param batchRevNo the batchRevNo to set
	 */
	public void setBatchRevNo(Integer batchRevNo) {
		this.batchRevNo = batchRevNo;
	}
	/**
	 * @return the beSeqNo
	 */
	public String getBeSeqNo() {
		return beSeqNo;
	}
	/**
	 * @param beSeqNo the beSeqNo to set
	 */
	public void setBeSeqNo(String beSeqNo) {
		this.beSeqNo = beSeqNo;
	}
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * @return the objExecStartTime
	 */
	public Long getObjExecStartTime() {
		return objExecStartTime;
	}
	/**
	 * @param objExecStartTime the objExecStartTime to set
	 */
	public void setObjExecStartTime(Long objExecStartTime) {
		this.objExecStartTime = objExecStartTime;
	}
	/**
	 * @return the objExecEndTime
	 */
	public Long getObjExecEndTime() {
		return objExecEndTime;
	}
	/**
	 * @param objExecEndTime the objExecEndTime to set
	 */
	public void setObjExecEndTime(Long objExecEndTime) {
		this.objExecEndTime = objExecEndTime;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the sysActNo
	 */
	public String getSysActNo() {
		return sysActNo;
	}
	/**
	 * @param sysActNo the sysActNo to set
	 */
	public void setSysActNo(String sysActNo) {
		this.sysActNo = sysActNo;
	}
	/**
	 * @return the userPriority
	 */
	public String getUserPriority() {
		return userPriority;
	}
	/**
	 * @param userPriority the userPriority to set
	 */
	public void setUserPriority(String userPriority) {
		this.userPriority = userPriority;
	}
	/**
	 * @return the priorityCode1
	 */
	public Integer getPriorityCode1() {
		return priorityCode1;
	}
	/**
	 * @param priorityCode1 the priorityCode1 to set
	 */
	public void setPriorityCode1(Integer priorityCode1) {
		this.priorityCode1 = priorityCode1;
	}
	/**
	 * @return the priorityCode2
	 */
	public Integer getPriorityCode2() {
		return priorityCode2;
	}
	/**
	 * @param priorityCode2 the priorityCode2 to set
	 */
	public void setPriorityCode2(Integer priorityCode2) {
		this.priorityCode2 = priorityCode2;
	}
	/**
	 * @return the prePost
	 */
	public String getPrePost() {
		return prePost;
	}
	/**
	 * @param prePost the prePost to set
	 */
	public void setPrePost(String prePost) {
		this.prePost = prePost;
	}
	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * @return the line
	 */
	public String getLine() {
		return line;
	}
	/**
	 * @param line the line to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
	/**
	 * @return the subline
	 */
	public String getSubline() {
		return subline;
	}
	/**
	 * @param subline the subline to set
	 */
	public void setSubline(String subline) {
		this.subline = subline;
	}
	/**
	 * @return the broker
	 */
	public String getBroker() {
		return broker;
	}
	/**
	 * @param broker the broker to set
	 */
	public void setBroker(String broker) {
		this.broker = broker;
	}
	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}
	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	/**
	 * @return the policyRenewNo
	 */
	public String getPolicyRenewNo() {
		return policyRenewNo;
	}
	/**
	 * @param policyRenewNo the policyRenewNo to set
	 */
	public void setPolicyRenewNo(String policyRenewNo) {
		this.policyRenewNo = policyRenewNo;
	}
	/**
	 * @return the vehRefNo
	 */
	public String getVehRefNo() {
		return vehRefNo;
	}
	/**
	 * @param vehRefNo the vehRefNo to set
	 */
	public void setVehRefNo(String vehRefNo) {
		this.vehRefNo = vehRefNo;
	}
	/**
	 * @return the cashBatchNo
	 */
	public String getCashBatchNo() {
		return cashBatchNo;
	}
	/**
	 * @param cashBatchNo the cashBatchNo to set
	 */
	public void setCashBatchNo(String cashBatchNo) {
		this.cashBatchNo = cashBatchNo;
	}
	/**
	 * @return the cashBatchRevNo
	 */
	public String getCashBatchRevNo() {
		return cashBatchRevNo;
	}
	/**
	 * @param cashBatchRevNo the cashBatchRevNo to set
	 */
	public void setCashBatchRevNo(String cashBatchRevNo) {
		this.cashBatchRevNo = cashBatchRevNo;
	}
	/**
	 * @return the gbiBillNo
	 */
	public String getGbiBillNo() {
		return gbiBillNo;
	}
	/**
	 * @param gbiBillNo the gbiBillNo to set
	 */
	public void setGbiBillNo(String gbiBillNo) {
		this.gbiBillNo = gbiBillNo;
	}
	/**
	 * @return the printFormNo
	 */
	public String getPrintFormNo() {
		return printFormNo;
	}
	/**
	 * @param printFormNo the printFormNo to set
	 */
	public void setPrintFormNo(String printFormNo) {
		this.printFormNo = printFormNo;
	}
	/**
	 * @return the notifyErrorTo
	 */
	public String getNotifyErrorTo() {
		return notifyErrorTo;
	}
	/**
	 * @param notifyErrorTo the notifyErrorTo to set
	 */
	public void setNotifyErrorTo(String notifyErrorTo) {
		this.notifyErrorTo = notifyErrorTo;
	}
	/**
	 * @return the dateGenerate
	 */
	public Long getDateGenerate() {
		return dateGenerate;
	}
	/**
	 * @param dateGenerate the dateGenerate to set
	 */
	public void setDateGenerate(Long dateGenerate) {
		this.dateGenerate = dateGenerate;
	}
	/**
	 * @return the generateBy
	 */
	public String getGenerateBy() {
		return generateBy;
	}
	/**
	 * @param generateBy the generateBy to set
	 */
	public void setGenerateBy(String generateBy) {
		this.generateBy = generateBy;
	}
	/**
	 * @return the recMessage
	 */
	public String getRecMessage() {
		return recMessage;
	}
	/**
	 * @param recMessage the recMessage to set
	 */
	public void setRecMessage(String recMessage) {
		this.recMessage = recMessage;
	}
	/**
	 * @return the jobDesc
	 */
	public String getJobDesc() {
		return jobDesc;
	}
	/**
	 * @param jobDesc the jobDesc to set
	 */
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}
	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	/**
	 * @return the dateExecuted
	 */
	public Long getDateExecuted() {
		return dateExecuted;
	}
	/**
	 * @param dateExecuted the dateExecuted to set
	 */
	public void setDateExecuted(Long dateExecuted) {
		this.dateExecuted = dateExecuted;
	}
	/**
	 * @return the listInd
	 */
	public Integer getListInd() {
		return listInd;
	}
	/**
	 * @param listInd the listInd to set
	 */
	public void setListInd(Integer listInd) {
		this.listInd = listInd;
	}
	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}
	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	/**
	 * @return the entityCode
	 */
	public String getEntityCode() {
		return entityCode;
	}
	/**
	 * @param entityCode the entityCode to set
	 */
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	/**
	 * @return the refSystemActivityNo
	 */
	public String getRefSystemActivityNo() {
		return refSystemActivityNo;
	}
	/**
	 * @param refSystemActivityNo the refSystemActivityNo to set
	 */
	public void setRefSystemActivityNo(String refSystemActivityNo) {
		this.refSystemActivityNo = refSystemActivityNo;
	}
	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * @return the cycleNo
	 */
	public Integer getCycleNo() {
		return cycleNo;
	}
	/**
	 * @param cycleNo the cycleNo to set
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}
	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}
	/**
	 * @return the timeTaken
	 */
	public Long getTimeTaken() {
		return timeTaken;
	}
	
	
	
}


/*
* Revision Log
* -------------------------------
* $Log: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BatchObject.java $
 * 
 * 2     3/17/10 4:38p Mandar.vaidya
 * Updated the field name and respective getter and setter methods.
*/