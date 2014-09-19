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

public class InstallationData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String instCode;
	private Integer bNo;
	private Integer bRevNo;
	private Long batchStartTime;
	private Long batchEndTime;
	private String batchEndReason;
	private Integer totalObjects;
	private Integer totalFailedObjects;
	//This field is optional if progress level is available for Batch no 
	//and batch revision no it would be populated
	private List<ProgressLevelData> progressLevelDataList;
	private List<InstallationEntity> entityList;
	
	private String timezoneId;
	private String timezoneShortName;	
	private long timezoneOffset;
	
	
	/**
	 * Gets the instCode.
	 *
	 * @return the instCode
	 */
	public String getInstCode() {
		return instCode;
	}
	/**
	 * Sets the instCode.
	 *
	 * @param instCode 
	 *        The instCode to set
	 */
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	/**
	 * Gets the bNo.
	 *
	 * @return the bNo
	 */
	public Integer getBNo() {
		return bNo;
	}
	/**
	 * Sets the bNo.
	 *
	 * @param no 
	 *        The bNo to set
	 */
	public void setBNo(Integer no) {
		bNo = no;
	}
	/**
	 * Gets the bRevNo.
	 *
	 * @return the bRevNo
	 */
	public Integer getBRevNo() {
		return bRevNo;
	}
	/**
	 * Sets the bRevNo.
	 *
	 * @param revNo 
	 *        The bRevNo to set
	 */
	public void setBRevNo(Integer revNo) {
		bRevNo = revNo;
	}
	/**
	 * Gets the batchStartTime.
	 *
	 * @return the batchStartTime
	 */
	public Long getBatchStartTime() {
		return batchStartTime;
	}
	/**
	 * Sets the batchStartTime.
	 *
	 * @param batchStartTime 
	 *        The batchStartTime to set
	 */
	public void setBatchStartTime(Long batchStartTime) {
		this.batchStartTime = batchStartTime;
	}
	/**
	 * Gets the batchEndTime.
	 *
	 * @return the batchEndTime
	 */
	public Long getBatchEndTime() {
		return batchEndTime;
	}
	/**
	 * Sets the batchEndTime.
	 *
	 * @param batchEndTime 
	 *        The batchEndTime to set
	 */
	public void setBatchEndTime(Long batchEndTime) {
		this.batchEndTime = batchEndTime;
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
	 * Gets the totalObjects.
	 *
	 * @return the totalObjects
	 */
	public Integer getTotalObjects() {
		return totalObjects;
	}
	/**
	 * Sets the totalObjects.
	 *
	 * @param totalObjects 
	 *        The totalObjects to set
	 */
	public void setTotalObjects(Integer totalObjects) {
		this.totalObjects = totalObjects;
	}
	/**
	 * Gets the totalFailedObjects.
	 *
	 * @return the totalFailedObjects
	 */
	public Integer getTotalFailedObjects() {
		return totalFailedObjects;
	}
	/**
	 * Sets the totalFailedObjects.
	 *
	 * @param totalFailedObjects 
	 *        The totalFailedObjects to set
	 */
	public void setTotalFailedObjects(Integer totalFailedObjects) {
		this.totalFailedObjects = totalFailedObjects;
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
	 * Gets the timezoneId
	 *
	 * @return the timezoneId
	 */
	public String getTimezoneId() {
		return timezoneId;
	}
	/**
	 * Sets the timezoneId
	 *
	 * @param timezoneId 
	 *        The timezoneId to set
	 */
	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}
	/**
	 * Gets the timezoneShortName
	 *
	 * @return the timezoneShortName
	 */
	public String getTimezoneShortName() {
		return timezoneShortName;
	}
	/**
	 * Sets the timezoneShortName
	 *
	 * @param timezoneShortName 
	 *        The timezoneShortName to set
	 */
	public void setTimezoneShortName(String timezoneShortName) {
		this.timezoneShortName = timezoneShortName;
	}
	/**
	 * Gets the timezoneOffset
	 *
	 * @return the timezoneOffset
	 */
	public long getTimezoneOffset() {
		return timezoneOffset;
	}
	/**
	 * Sets the timezoneOffset
	 *
	 * @param timezoneOffset 
	 *        The timezoneOffset to set
	 */
	public void setTimezoneOffset(long timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/InstallationData.java                                                         $
 * 
 * 5     4/27/10 3:17p Mandar.vaidya
 * Added Entity data to Installation data.
 * 
 * 4     4/14/10 1:57p Mandar.vaidya
 * Added new field timezoneShortName with getter and setter methods.
 * 
 * 3     4/13/10 2:29p Kedarr
 * Changes made for Timezone.
 * 
 * 2     1/08/10 10:15a Grahesh
 * Corrected the object hierarchy
 * 
 * 1     1/06/10 2:48p Grahesh
 * Initial Version
*
*
*/