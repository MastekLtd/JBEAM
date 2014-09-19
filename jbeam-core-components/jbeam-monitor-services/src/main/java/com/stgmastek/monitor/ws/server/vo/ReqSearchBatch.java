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

public class ReqSearchBatch extends BaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private Integer batchNo;
	private String batchName;
	private String batchType;
	private String batchDate;
	private String batchEndReason;
	private String instructionSeqNo;
	
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
	 * Gets the batchDate.
	 *
	 * @return the batchDate
	 */
	public String getBatchDate() {
		return batchDate;
	}
	/**
	 * Sets the batchDate.
	 *
	 * @param batchDate 
	 *        The batchDate to set
	 */
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
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
	 * @return the instructionSeqNo
	 */
	public String getInstructionSeqNo() {
		return instructionSeqNo;
	}
	/**
	 * @param instructionSeqNo the instructionSeqNo to set
	 */
	public void setInstructionSeqNo(String instructionSeqNo) {
		this.instructionSeqNo = instructionSeqNo;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ReqSearchBatch.java                                                           $
 * 
 * 3     3/11/10 9:36a Mandar.vaidya
 * Added batchEndReason with getter and setter methods. Modified the comments.
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/