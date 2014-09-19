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
package com.stgmastek.core.util;

import java.io.Serializable;
import java.util.Date;

import com.stgmastek.core.util.Constants.OBJECT_STATUS;

/**
 * Value Object class for JOB_SCHEDULE table. 
 * All fields are used as it is. 
 * For more information check 
 * <a href="http://stgpedia.stgil-india.com/index.php/JOB_SCHEDULE" >STGPedia</a> 
 * for the field (column) related information.
 *   
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public class BatchObject implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706512139080526607L;
	
	/**
	 * Additional fields as needed by the LOG table 
	 */
	private Integer batchNo;
	private Integer batchRevNo;
	private Long objExecStartTime;
	private Long objExecEndTime;
	private String errorType;
	private String errorDescription;
	
	/** All the fields represent each column from the JOB_SCHEDULE table */

	private String line;
	private String subline;
	private String policyNo;
	private String policyRenewNo;
	private String sequence;
	private String taskname;
	private Long executiondate;
	private OBJECT_STATUS status;
	private String systemActivityNo;
	private String userOverridePriority;
	private String prePost;
	private Integer priorityCode1;
	private Integer priorityCode2;
	private String jobType;
	private String broker;
	private String vehRefNo;
	private String cashBatchNo;
	private String cashBatchRevisionNo;
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
	private String context;
	private String referenceId;
	private String subreferenceId;
	private String batchType;
	private String entityType;
	private String entityCode;
	private String refSystemActivityNo;
	private Integer maxTime;
	private Integer minTime;
	private Integer avgTime;
	private String escalationLevel;
	
	/**
	 * This is indirectly used by the listener.
	 */
	private Integer cycleNo;
	
	/**
	 * Returns the batchNo
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
	 * Returns the batchRevNo
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
	 * Returns the objExecStartTime
	 * 
	 * @return the objExecStartTime
	 */
	public Date getObjExecStartTime() {
		return (objExecStartTime==null?null:new Date(objExecStartTime));
	}

	/**
	 * Sets the objExecStartTime
	 * 
	 * @param objExecStartTime 
	 *        The objExecStartTime to set
	 */
	public void setObjExecStartTime(Date objExecStartTime) {
		this.objExecStartTime = (objExecStartTime==null?null:objExecStartTime.getTime());
	}

	/**
	 * Returns the objExecEndTime
	 * 
	 * @return the objExecEndTime
	 */
	public Date getObjExecEndTime() {
		return (objExecEndTime==null?null:new Date(objExecEndTime));
	}

	/**
	 * Sets the objExecEndTime
	 * 
	 * @param objExecEndTime 
	 *        The objExecEndTime to set
	 */
	public void setObjExecEndTime(Date objExecEndTime) {
		this.objExecEndTime = (objExecEndTime==null?null:objExecEndTime.getTime());
	}

	/**
	 * Returns the line
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
	 * Returns the subline
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
	 * Returns the policyNo
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
	 * Returns the policyRenewNo
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
	public void setPolicyRenewNo(Integer policyRenewNo) {
		this.policyRenewNo = Integer.toString(policyRenewNo);
	}
	/**
	 * Returns the sequence
	 * 
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * Sets the sequence
	 * 
	 * @param sequence 
	 *        The sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = Integer.toString(sequence);
	}
	/**
	 * Returns the taskname
	 * 
	 * @return the taskname
	 */
	public String getTaskname() {
		return taskname;
	}

	/**
	 * Sets the taskname
	 * 
	 * @param taskname 
	 *        The taskname to set
	 */
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	/**
	 * Returns the executiondate
	 * 
	 * @return the executiondate
	 */
	public Date getExecutiondate() {
		return (executiondate==null?null:new Date(executiondate));
	}

	/**
	 * Sets the executiondate
	 * 
	 * @param executiondate 
	 *        The executiondate to set
	 */
	public void setExecutiondate(Date executiondate) {
		this.executiondate = (executiondate==null?null:executiondate.getTime());
	}
	public void setExecutiondate(Long executiondate) {
		this.executiondate = executiondate;
	}
	/**
	 * Returns the status
	 * 
	 * @return the status
	 */
	public OBJECT_STATUS getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 * 
	 * @param status 
	 *        The status to set
	 */
	public void setStatus(OBJECT_STATUS status) {
		this.status = status;
	}

	/**
	 * Sets the status
	 * 
	 * @param status 
	 *        The status to set
	 * @deprecated use {@link #setStatus(com.stgmastek.core.util.Constants.OBJECT_STATUS)}
	 */
	public void setStatus(String status) {
		this.status = OBJECT_STATUS.resolve(status);
	}
	
	/**
	 * Returns the systemActivityNo
	 * 
	 * @return the systemActivityNo
	 */
	public String getSystemActivityNo() {
		return systemActivityNo;
	}

	/**
	 * Sets the systemActivityNo
	 * 
	 * @param systemActivityNo 
	 *        The systemActivityNo to set
	 */
	public void setSystemActivityNo(String systemActivityNo) {
		this.systemActivityNo = systemActivityNo;
	}

	/**
	 * Returns the userOverridePriority
	 * 
	 * @return the userOverridePriority
	 */
	public String getUserOverridePriority() {
		return userOverridePriority;
	}

	/**
	 * Sets the userOverridePriority
	 * 
	 * @param userOverridePriority 
	 *        The userOverridePriority to set
	 */
	public void setUserOverridePriority(String userOverridePriority) {
		this.userOverridePriority = userOverridePriority;
	}

	/**
	 * Returns the prePost
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
	 * Returns the priorityCode1
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

	public void setPriorityCode1(Long priorityCode1) {
		this.priorityCode1 = priorityCode1.intValue();
	}
	
	public void setPriorityCode1(Double priorityCode1) {
		this.priorityCode1 = priorityCode1.intValue();
	}
	/**
	 * Returns the priorityCode2
	 * 
	 * @return the priorityCode2
	 */
	public Integer getPriorityCode2() {
		return priorityCode2;
	}
	public void setPriorityCode2(Long priorityCode2) {
		this.priorityCode2 = priorityCode2.intValue();
	}
	
	public void setPriorityCode2(Double priorityCode2) {
		this.priorityCode2 = priorityCode2.intValue();
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
	 * Returns the jobType
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
	 * Returns the broker
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
	 * Returns the vehRefNo
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
	 * Returns the cashBatchNo
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
	 * Returns the cashBatchRevisionNo
	 * 
	 * @return the cashBatchRevisionNo
	 */
	public String getCashBatchRevisionNo() {
		return cashBatchRevisionNo;
	}

	/**
	 * Sets the cashBatchRevisionNo
	 * 
	 * @param cashBatchRevisionNo 
	 *        The cashBatchRevisionNo to set
	 */
	public void setCashBatchRevisionNo(String cashBatchRevisionNo) {
		this.cashBatchRevisionNo = cashBatchRevisionNo;
	}

	/**
	 * Returns the gbiBillNo
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
	public void setGbiBillNo(Integer gbiBillNo) {
		this.gbiBillNo = Integer.toString(gbiBillNo);
	}
	/**
	 * Returns the printFormNo
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
	 * Returns the notifyErrorTo
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
	 * Returns the dateGenerate
	 * 
	 * @return the dateGenerate
	 */
	public Date getDateGenerate() {
		return (dateGenerate==null?null:new Date(dateGenerate));
	}

	/**
	 * Sets the dateGenerate
	 * 
	 * @param dateGenerate 
	 *        The dateGenerate to set
	 */
	public void setDateGenerate(Date dateGenerate) {
		this.dateGenerate = (dateGenerate==null?null:dateGenerate.getTime());
	}
	public void setDateGenerate(Long dateGenerate) {
		this.dateGenerate = dateGenerate;
	}
	/**
	 * Returns the generateBy
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
	 * Returns the recMessage
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
	 * Returns the jobDesc
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
	 * Returns the objectName
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
	 * Returns the dateExecuted
	 * 
	 * @return the dateExecuted
	 */
	public Date getDateExecuted() {
		return (dateExecuted==null?null:new Date(dateExecuted));
	}

	/**
	 * Sets the dateExecuted
	 * 
	 * @param dateExecuted 
	 *        The dateExecuted to set
	 */
	public void setDateExecuted(Date dateExecuted) {
		this.dateExecuted = (dateExecuted==null?null:dateExecuted.getTime());
	}
	public void setDateExecuted(Long dateExecuted) {
		this.dateExecuted = dateExecuted;
	}
	
	/**
	 * Returns the listInd
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
	public void setListInd(Long listInd) {
		this.listInd = listInd.intValue();
	}
	public void setListInd(Double listInd) {
		this.listInd = listInd.intValue();
	}
	/**
	 * Returns the context
	 * 
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Sets the context
	 * 
	 * @param context 
	 *        The context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Returns the referenceId
	 * 
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * Sets the referenceId
	 * 
	 * @param referenceId 
	 *        The referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * Returns the subreferenceId
	 * 
	 * @return the subreferenceId
	 */
	public String getSubreferenceId() {
		return subreferenceId;
	}

	/**
	 * Sets the subreferenceId
	 * 
	 * @param subreferenceId 
	 *        The subreferenceId to set
	 */
	public void setSubreferenceId(String subreferenceId) {
		this.subreferenceId = subreferenceId;
	}
	public void setSubreferenceId(Integer subreferenceId) {
		this.subreferenceId = Integer.toString(subreferenceId);
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
	 * Sets the batchType
	 * 
	 * @param batchType 
	 *        The batchType to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	/**
	 * Returns the entityType
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
	 * Returns the entityCode
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
	 * Returns the refSystemActivityNo
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
	 * Returns the errorType
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
	 * 		  the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	/**
	 * Returns the errorDescription
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
	 * 		  the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @param cycleNo the cycleNo to set
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}

	/**
	 * @return the cycleNo
	 */
	public Integer getCycleNo() {
		return cycleNo;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Integer maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the maxTime
	 */
	public Integer getMaxTime() {
		return maxTime;
	}

	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime(Integer minTime) {
		this.minTime = minTime;
	}

	/**
	 * @return the minTime
	 */
	public Integer getMinTime() {
		return minTime;
	}

	/**
	 * @param avgTime the avgTime to set
	 */
	public void setAvgTime(Integer avgTime) {
		this.avgTime = avgTime;
	}

	/**
	 * @return the avgTime
	 */
	public Integer getAvgTime() {
		return avgTime;
	}

	/**
	 * @param escalationLevel the escalationLevel to set
	 */
	public void setEscalationLevel(String escalationLevel) {
		this.escalationLevel = escalationLevel;
	}

	/**
	 * @return the escalationLevel
	 */
	public String getEscalationLevel() {
		return escalationLevel;
	}

	public String toString() {
		return "BatchObject [sequence=" + sequence + ", batchNo=" + batchNo
				+ ", batchRevNo=" + batchRevNo + ", prePost=" + prePost
				+ ", batchType=" + batchType + ", cycleNo=" + cycleNo
				+ ", jobDesc=" + jobDesc + ", jobType=" + jobType
				+ ", listInd=" + listInd + ", objectName=" + objectName
				+ ", status=" + status + ", taskname=" + taskname + ", execStartTime" + objExecStartTime + "]";
	}
	
}


/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchObject.java                                                                                    $
 * 
 * 10    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 9     4/26/10 2:26p Kedarr
 * Added to string
 * 
 * 8     4/13/10 2:31p Kedarr
 * Changes made to show escalation level.
 * 
 * 7     3/30/10 9:28a Kedarr
 * Added avg time and min time.
 * 
 * 6     3/26/10 12:49p Kedarr
 * Added a new column
 * 
 * 5     3/26/10 12:49p Kedarr
 * Added a new column
 * 
 * 4     3/15/10 12:29p Kedarr
 * Changes made to insert the cycle number against the batch objects.
 * 
 * 3     12/23/09 3:23p Grahesh
 * Removed be_on_fail_exit from batch_executor
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/