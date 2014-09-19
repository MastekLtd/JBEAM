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
import java.util.HashMap;

/**
 * Singleton class to track the progress level At any given point in time, there
 * would always be only one level where the batch proceedings stand, hence
 * singleton.
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public final class ProgressLevel implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8847530689786268316L;

	private static HashMap<Integer, ProgressLevel> progressLevelMap = new HashMap<Integer, ProgressLevel>();

	/** Static instance of the execution status */
	private ExecutionStatus executionStatus = new ExecutionStatus();

	/**
	 * Private constructor to avoid outside instantiation
	 */
	private ProgressLevel() {
	}

	/**
	 * Returns the singleton instance of Progress Level
	 * 
	 * @return the singleton instance of Progress Level
	 */
	public synchronized static ProgressLevel getProgressLevel(int batchNo) {
		if(!progressLevelMap.containsKey(batchNo)){
			progressLevelMap.put(batchNo, new ProgressLevel());
		}
		return progressLevelMap.get(batchNo);
	}

	/** Exact replica of PROGRESS_LEVEL table */
	private Integer batchNo;
	private Integer batchRevNo;
	private Integer indicatorNo;
	private String prgLevelType;
	private ACTIVITY prgActivityType;
	private Integer cycleNo;
	private String status;
	private Long startDatetime;
	private Long endDatetime;
	private String errorDesc;
	private boolean failedOver;

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
	 *            The batchNo to set
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
	 *            The batchRevNo to set
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
	 * Sets the indicatorNo
	 * 
	 * @param indicatorNo
	 *            The indicatorNo to set
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
	 * Sets the prgLevelType
	 * 
	 * @param prgLevelType
	 *            The prgLevelType to set
	 */
	private void setPrgLevelType(String prgLevelType) {
		this.prgLevelType = prgLevelType;
	}

	/**
	 * Returns the prgActivityType
	 * 
	 * @return the prgActivityType
	 */
	public ACTIVITY getPrgActivityType() {
		return prgActivityType;
	}

	/**
	 * Sets the prgActivityType
	 * 
	 * @param prgActivityType
	 *            The prgActivityType to set
	 */
	private void setPrgActivityType(ACTIVITY prgActivityType) {
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
	 * Sets the cycleNo
	 * 
	 * @param cycleNo
	 *            The cycleNo to set
	 */
	private void setCycleNo(Integer cycleNo) {
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
	 * Sets the status
	 * 
	 * @param status
	 *            The status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the startDatetime
	 * 
	 * @return the startDatetime
	 */
	public Date getStartDatetime() {
		return (startDatetime==null?null:new Date(startDatetime));
	}

	/**
	 * Sets the startDatetime
	 * 
	 * @param startDatetime
	 *            The startDatetime to set
	 */
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = (startDatetime==null?null:startDatetime.getTime());
	}

	/**
	 * Returns the endDatetime
	 * 
	 * @return the endDatetime
	 */
	public Date getEndDatetime() {
		return (endDatetime==null?null:new Date(endDatetime));
	}

	/**
	 * Sets the endDatetime
	 * 
	 * @param endDatetime
	 *            The endDatetime to set
	 */
	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = (endDatetime==null?null:endDatetime.getTime());
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
	 * Sets the errorDesc
	 * 
	 * @param errorDesc
	 *            The errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * Sets the progress level for the current batch Method for those levels
	 * that executes under a cycle
	 * 
	 * @param levelType
	 *            The level or the entity under consideration
	 * @param activityType
	 *            The activity under the entity
	 * @param cycleCounter
	 *            The current cycle number
	 */
	public void setProgressLevel(String levelType, ACTIVITY activityType,
			Integer cycleCounter) {
		setPrgLevelType(levelType);
		setPrgActivityType(activityType);
		setCycleNo(cycleCounter);
	}

	/**
	 * Overloaded method that takes only the activity type as argument Method
	 * for those levels that executes without a cycle
	 * 
	 * @param activityType
	 *            The activity type to set
	 */
	public void setProgressLevel(ACTIVITY activityType) {
		setProgressLevel(null, activityType, null);
	}

	/**
	 * Overloaded method to set the progress level Method for those levels that
	 * executes without a cycle
	 * 
	 * @param levelType
	 *            The level or the entity under consideration
	 * @param activityType
	 *            The activity under the entity
	 */
	public void setProgressLevel(String levelType, ACTIVITY activityType) {
		setProgressLevel(levelType, activityType, null);
	}

	public ExecutionStatus getExecutionStatus() {
		return executionStatus;
	}

	/**
	 * Sets the status of the execution
	 * 
	 * @param entity
	 *            The entity name
	 * @param stageCode
	 *            The stage code
	 */
	public void setExecutionStatus(String entity, String stageCode) {
		executionStatus.setEntity(entity);
		executionStatus.setStageCode(stageCode);
	}

	/**
	 * Returns the string representation of the class
	 * 
	 * @return the string representation of the class
	 */
	
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(Constants.NEW_LINE);
		sb.append(Constants.NEW_LINE);
		sb.append("BatchNo=" + getBatchNo());
		sb.append(Constants.NEW_LINE);
		sb.append("BatchRevNo=" + getBatchRevNo());
		sb.append(Constants.NEW_LINE);
		sb.append("IndicatorNo=" + getIndicatorNo());
		sb.append(Constants.NEW_LINE);
		sb.append("PrgLevelType=" + getPrgLevelType());
		sb.append(Constants.NEW_LINE);
		sb.append("PrgActivityType=" + getPrgActivityType());
		sb.append(Constants.NEW_LINE);
		sb.append("CycleNo=" + getCycleNo());
		sb.append(Constants.NEW_LINE);
		sb.append("Status=" + getStatus());
		sb.append(Constants.NEW_LINE);
		sb.append("StartDatetime=" + getStartDatetime());
		sb.append(Constants.NEW_LINE);
		sb.append("EndDatetime=" + getEndDatetime());
		sb.append(Constants.NEW_LINE);
		sb.append("ErrorDesc=" + getErrorDesc());
		sb.append(Constants.NEW_LINE);
		sb.append("isFailedOver=" + isFailedOver());
		sb.append(Constants.NEW_LINE);
		sb.append("executionStatus=" + getExecutionStatus().toString());
		sb.append("============");
		return sb.toString();
	}

	/**
	 * Set the <code>failedOver</code> with the given parameter value.
	 * 
	 * @param failedOver
	 *            the failedOver to set
	 */
	public void setFailedOver(boolean failedOver) {
		this.failedOver = failedOver;
	}

	/**
	 * Returns true if the batch failed over.
	 * 
	 * @return the failedOver
	 */
	public boolean isFailedOver() {
		return failedOver;
	}

	/**
	 * Defines the Activities that will be carried out by the JBEAM.
	 *
	 * The activity is self explanatory.
	 * 
	 * @author Kedar Raybagkar
	 */
	public static enum ACTIVITY {
		INITIALIZATION(1),
		EXECUTION_ORDER(2),
		PROCREATION(3),
		ASSIGNMENT(4),
		SCHEDULING(5),
		EXECUTION(6),
		STOPPED(7),
		CLOSURE(8);
		
		private int level;
		
		private ACTIVITY(int i) {
		}
		
		public int getActivityLevel() {
			return level;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("CloneNotSupportedException caught", e);
		}
	}
}

/*
 * Revision Log ------------------------------- $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/ProgressLevel.java          $
 * 
 * 6     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 5     4/27/10 9:20a Kedarr
 * Added a new activity level.
 * 
 * 4     4/21/10 12:03p Kedarr
 * Implemented cloneable and serializable. Removed all STRING constants and instead add them as an enum.
 * 
 * 3     3/11/10 11:49a Kedarr
 * Changes made for setting failed over status in progress level.
 * 
 * 2 12/17/09 11:46a Grahesh Initial Version
 */