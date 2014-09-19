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

public class BatchProgress implements Serializable {

	/** Default Serial Version UID	 */
	private static final long serialVersionUID = -4876622945189045012L;

	/* Fields from the PROGRESS_LEVEL table and are self explanatory */
	private String installationCode ;	
	private Integer batchNo;
	private Integer batchRevNo;
	private Integer indicatorNo;
	private String prgLevelType;
	private String prgActivityType;
	private Integer cycleNo;
	private String status;
	private Long startDatetime;
	private Long endDatetime;
	private String errorDesc;
	private String failedOver;
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
	 * Returns the indicatorNo
	 * 
	 * @return the indicatorNo
	 */
	public Integer getIndicatorNo() {
		return indicatorNo;
	}
	/**
	 * Sets the indicatorNo to set
	 * 
	 * @param indicatorNo
	 * 		  The indicatorNo to set
	 */
	public void setIndicatorNo(Integer indicatorNo) {
		this.indicatorNo = indicatorNo;
	}
	/**
	 * Returns the prgLevelType
	 * 
	 * @return the prgLevelType
	 */
	public String getPrgLevelType() {
		return prgLevelType;
	}
	/**
	 * Sets the prgLevelType to set
	 * 
	 * @param prgLevelType
	 * 		  The prgLevelType to set
	 */
	public void setPrgLevelType(String prgLevelType) {
		this.prgLevelType = prgLevelType;
	}
	/**
	 * Returns the prgActivityType
	 * 
	 * @return the prgActivityType
	 */
	public String getPrgActivityType() {
		return prgActivityType;
	}
	/**
	 * Sets the prgActivityType to set
	 * 
	 * @param prgActivityType
	 * 		  The prgActivityType to set
	 */
	public void setPrgActivityType(String prgActivityType) {
		this.prgActivityType = prgActivityType;
	}
	/**
	 * Returns the cycleNo
	 * 
	 * @return the cycleNo
	 */
	public Integer getCycleNo() {
		return cycleNo;
	}
	/**
	 * Sets the cycleNo to set
	 * 
	 * @param cycleNo
	 * 		  The cycleNo to set
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}
	/**
	 * Returns the status
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the status to set
	 * 
	 * @param status
	 * 		  The status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Returns the startDatetime
	 * 
	 * @return the startDatetime
	 */
	public Long getStartDatetime() {
		return startDatetime;
	}
	/**
	 * Sets the startDatetime to set
	 * 
	 * @param startDatetime
	 * 		  The startDatetime to set
	 */
	public void setStartDatetime(Long startDatetime) {
		this.startDatetime = startDatetime;
	}
	/**
	 * Returns the endDatetime
	 * 
	 * @return the endDatetime
	 */
	public Long getEndDatetime() {
		return endDatetime;
	}
	/**
	 * Sets the endDatetime to set
	 * 
	 * @param endDatetime
	 * 		  The endDatetime to set
	 */
	public void setEndDatetime(Long endDatetime) {
		this.endDatetime = endDatetime;
	}
	/**
	 * Returns the errorDesc
	 * 
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * Sets the errorDesc to set
	 * 
	 * @param errorDesc
	 * 		  The errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/vo/MReqBatchProgress.java                                                          $
 * 
 * 4     3/11/10 2:22p Mandar.vaidya
 * Added failedOver field with getter & setter methods.
 * 
 * 3     12/18/09 4:19p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/