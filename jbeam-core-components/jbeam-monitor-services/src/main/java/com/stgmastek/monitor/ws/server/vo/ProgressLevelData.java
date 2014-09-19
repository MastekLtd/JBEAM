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

public class ProgressLevelData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String installationCode;
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
	 * Gets the indicatorNo
	 *
	 * @return the indicatorNo
	 */
	public Integer getIndicatorNo() {
		return indicatorNo;
	}
	/**
	 * Sets the indicatorNo
	 *
	 * @param indicatorNo 
	 *        The indicatorNo to set.
	 */
	public void setIndicatorNo(Integer indicatorNo) {
		this.indicatorNo = indicatorNo;
	}
	/**
	 * Gets the prgLevelType
	 *
	 * @return the prgLevelType
	 */
	public String getPrgLevelType() {
		return prgLevelType;
	}
	/**
	 * Sets the prgLevelType
	 *
	 * @param prgLevelType 
	 *        The prgLevelType to set.
	 */
	public void setPrgLevelType(String prgLevelType) {
		this.prgLevelType = prgLevelType;
	}
	/**
	 * Gets the prgActivityType
	 *
	 * @return the prgActivityType
	 */
	public String getPrgActivityType() {
		return prgActivityType;
	}
	/**
	 * Sets the prgActivityType
	 *
	 * @param prgActivityType 
	 *        The prgActivityType to set.
	 */
	public void setPrgActivityType(String prgActivityType) {
		this.prgActivityType = prgActivityType;
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
	 *        The cycleNo to set.
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
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
	 *        The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Gets the startDatetime
	 *
	 * @return the startDatetime
	 */
	public Long getStartDatetime() {
		return startDatetime;
	}
	/**
	 * Sets the startDatetime
	 *
	 * @param startDatetime 
	 *        The startDatetime to set.
	 */
	public void setStartDatetime(Long startDatetime) {
		this.startDatetime = startDatetime;
	}
	/**
	 * Gets the endDatetime
	 *
	 * @return the endDatetime
	 */
	public Long getEndDatetime() {
		return endDatetime;
	}
	/**
	 * Sets the endDatetime
	 *
	 * @param endDatetime 
	 *        The endDatetime to set.
	 */
	public void setEndDatetime(Long endDatetime) {
		this.endDatetime = endDatetime;
	}
	/**
	 * Gets the errorDesc
	 *
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}
	/**
	 * Sets the errorDesc
	 *
	 * @param errorDesc 
	 *        The errorDesc to set.
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	/**
	 * Sets the failedOver
	 *
	 * @param failedOver 
	 *        The failedOver to set
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ProgressLevelData.java                                                        $
 * 
 * 2     4/20/10 2:09p Mandar.vaidya
 * Added the field failedOver with getter and setter methods.
 * 
 * 1     1/06/10 2:48p Grahesh
 * Initial Version
*
*
*/