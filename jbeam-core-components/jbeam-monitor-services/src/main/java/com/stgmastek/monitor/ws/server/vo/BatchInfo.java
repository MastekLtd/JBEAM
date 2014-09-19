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
import java.util.ArrayList;
import java.util.List;

public class BatchInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer batchNo;
	private Integer batchRevNo;
	private Long execStartTime;
	private Long execEndTime;
	private Integer noOfObjects;
	private Integer noOfObjectsExecuted;
	private Integer noOfObjectsFailed;
	private Integer noOfListners;
	private String batchTimeDiff;
	private String batchEndReason;
	
	//This field is optional if progress level is available for Batch no 
	//and batch revision no it would be populated  
	private List<ProgressLevelData> progressLevelDataList;
	private List<InstallationEntity> entityList;
	
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
	 * Gets the noOfObjects.
	 *
	 * @return the noOfObjects
	 */
	public Integer getNoOfObjects() {
		return noOfObjects;
	}
	/**
	 * Sets the noOfObjects.
	 *
	 * @param noOfObjects 
	 *        The noOfObjects to set
	 */
	public void setNoOfObjects(Integer noOfObjects) {
		this.noOfObjects = noOfObjects;
	}
	/**
	 * Gets the noOfObjectsExecuted.
	 *
	 * @return the noOfObjectsExecuted
	 */
	public Integer getNoOfObjectsExecuted() {
		return noOfObjectsExecuted;
	}
	/**
	 * Sets the noOfObjectsExecuted.
	 *
	 * @param noOfObjectsExecuted 
	 *        The noOfObjectsExecuted to set
	 */
	public void setNoOfObjectsExecuted(Integer noOfObjectsExecuted) {
		this.noOfObjectsExecuted = noOfObjectsExecuted;
	}
	/**
	 * Gets the noOfObjectsFailed.
	 *
	 * @return the noOfObjectsFailed
	 */
	public Integer getNoOfObjectsFailed() {
		return noOfObjectsFailed;
	}
	/**
	 * Sets the noOfObjectsFailed.
	 *
	 * @param noOfObjectsFailed 
	 *        The noOfObjectsFailed to set
	 */
	public void setNoOfObjectsFailed(Integer noOfObjectsFailed) {
		this.noOfObjectsFailed = noOfObjectsFailed;
	}
	/**
	 * Gets the noOfListners.
	 *
	 * @return the noOfListners
	 */
	public Integer getNoOfListners() {
		return noOfListners;
	}
	/**
	 * Sets the noOfListners.
	 *
	 * @param noOfListners 
	 *        The noOfListners to set
	 */
	public void setNoOfListners(Integer noOfListners) {
		this.noOfListners = noOfListners;
	}
	/**
	 * Gets the batchTimeDiff.
	 *
	 * @return the batchTimeDiff
	 */
	public String getBatchTimeDiff() {
		return batchTimeDiff;
	}
	/**
	 * Sets the batchTimeDiff.
	 *
	 * @param batchTimeDiff 
	 *        The batchTimeDiff to set
	 */
	public void setBatchTimeDiff(String batchTimeDiff) {
		this.batchTimeDiff = batchTimeDiff;
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
	 * Gets the progressLevelDataList.
	 *
	 * @return the progressLevelDataList
	 */
	public List<ProgressLevelData> getProgressLevelDataList() {
		if(progressLevelDataList == null){
			return new ArrayList<ProgressLevelData>();
		}
		return progressLevelDataList;
	}
	/**
	 * Sets the progressLevelDataList.
	 *
	 * @param progressLevelDataList 
	 *        The progressLevelDataList to set
	 */
	public void setProgressLevelDataList(
			List<ProgressLevelData> progressLevelDataList) {
		this.progressLevelDataList = progressLevelDataList;
	}
	/**
	 * Sets the entityList
	 *
	 * @param entityList 
	 *        The entityList to set
	 */
	public void setEntityList(List<InstallationEntity> entityList) {
		this.entityList = entityList;
	}
	/**
	 * Gets the entityList
	 *
	 * @return the entityList
	 */
	public List<InstallationEntity> getEntityList() {
		return entityList;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BatchInfo.java                                                                $
 * 
 * 6     4/28/10 2:27p Mandar.vaidya
 * Added Entity data to BatchInfo
 * 
 * 5     1/08/10 10:15a Grahesh
 * Corrected the signature and object hierarchy
 * 
 * 4     1/06/10 10:49a Grahesh
 * Changed the object hierarchy
 * 
 * 3     1/05/10 11:54a Mandar.vaidya
 * Added batchEndReason field
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/