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

/**
 * Class to hold the information of the configurable within the system 
 * Usually holds the PRE and the POST configurable information 
 * 
 * @author grahesh.shanbhag
 *
 */
public class BatchJobMetaData implements Serializable{

	/** Default Serial Version UID */
	private static final long serialVersionUID = 2727729541436684372L;
	
	/** Exact replica from META_DATA table */
	private String seqNo;
	private String taskName;
	private Long effDate;
	private Long expDate;
	private String onFailExit;
	private Integer priorityCode1;
	private Integer priorityCode2;
	private String prePost;
	private String jobType;
	private String line;
	private String subline;
	private Long dateGenerate;
	private String generateBy;
	private String jobDesc;
	private String objectName;
	
	/**
	 * Returns the seqNo
	 * 
	 * @return the seqNo
	 */
	public String getSeqNo() {
		return seqNo;
	}
	
	/**
	 * Sets the seqNo
	 * 
	 * @param seqNo 
	 *        The seqNo to set
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	/**
	 * Returns the taskName
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
	 * Returns the effDate
	 * 
	 * @return the effDate
	 */
	public Date getEffDate() {
		return (effDate==null)?null:new Date(effDate);
	}
	
	/**
	 * Sets the effDate
	 * 
	 * @param effDate 
	 *        The effDate to set
	 */
	public void setEffDate(Date effDate) {
		this.effDate = (effDate==null)?null:Long.valueOf(effDate.getTime());
	}
	
	public void setEffDate(Long effDate) {
		this.effDate = effDate;
	}
	/**
	 * Returns the expDate
	 * 
	 * @return the expDate
	 */
	public Date getExpDate() {
		return (expDate==null)?null:new Date(expDate);
	}
	
	/**
	 * Sets the expDate
	 * 
	 * @param expDate 
	 *        The expDate to set
	 */
	public void setExpDate(Date expDate) {
		this.expDate = (expDate==null)?null:Long.valueOf(expDate.getTime());
	}
	public void setExpDate(Long expDate) {
		this.expDate = expDate;
	}
	/**
	 * Returns the onFailExit
	 * 
	 * @return the onFailExit
	 */
	public String getOnFailExit() {
		return onFailExit;
	}
	
	/**
	 * Sets the onFailExit
	 * 
	 * @param onFailExit 
	 *        The onFailExit to set
	 */
	public void setOnFailExit(String onFailExit) {
		this.onFailExit = onFailExit;
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
	
	/**
	 * Returns the priorityCode2
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
	 * Returns the dateGenerate
	 * 
	 * @return the dateGenerate
	 */
	public Date getDateGenerate() {
		return (dateGenerate==null)?null:new Date(dateGenerate);
	}
	
	/**
	 * Sets the dateGenerate
	 * 
	 * @param dateGenerate 
	 *        The dateGenerate to set
	 */
	public void setDateGenerate(Date dateGenerate) {
		this.dateGenerate = (dateGenerate==null)?null:Long.valueOf(dateGenerate.getTime());
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

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchJobMetaData.java                                                                               $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/