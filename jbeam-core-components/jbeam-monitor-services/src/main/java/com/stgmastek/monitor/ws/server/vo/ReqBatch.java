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

public class ReqBatch extends BaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	private Integer batchNo;
	private Integer batchRevNo;
	private String installationCode;
	private String graphId;
	private String instructionSeqNo;
	private UserProfile userProfile;
	
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
	 * Sets the graphId.
	 *
	 * @param graphId 
	 *        The graphId to set
	 */
	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}
	/**
	 * Gets the graphId.
	 *
	 * @return the graphId
	 */
	public String getGraphId() {
		return graphId;
	}
	/**
	 * Sets the instructionSeqNo
	 *
	 * @param instructionSeqNo 
	 *        The instructionSeqNo to set
	 */
	public void setInstructionSeqNo(String instructionSeqNo) {
		this.instructionSeqNo = instructionSeqNo;
	}
	/**
	 * Gets the instructionSeqNo
	 *
	 * @return the instructionSeqNo
	 */
	public String getInstructionSeqNo() {
		return instructionSeqNo;
	}
	/**
	 * Sets the userProfile
	 *
	 * @param userProfile 
	 *        The userProfile to set.
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	/**
	 * Gets the userProfile
	 *
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ReqBatch.java                                                                 $
 * 
 * 5     4/01/10 4:35p Mandar.vaidya
 * Changed data type of instructionSeqNo from Integer to String.
 * 
 * 3     3/12/10 11:48a Mandar.vaidya
 * Added graphId with getter and setter methods.
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/