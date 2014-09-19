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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import stg.utils.CDate;

import com.stgmastek.core.exception.BatchException;

/**
 * Class to hold the batch information 
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public class BatchInfo implements Serializable{

	/** The serial version UID */
	private static final long serialVersionUID = -6429860243939212180L;
	
	/** The batch number */
	private Integer batchNo;
	
	/** The batch revision number */
	private Integer batchRevNo;

	/** The name of the batch */
	private String batchName;
	
	/** The type of batch run, DATE RUN or SPECIAL RUN */
	private String batchType;
		
	/** 
	 * The batch run date. It is different from the batch start time .
	 * BATCH_RUN_DATE is the date from which the batch objects are to be picked up
	 */
	private Long batchRunDate = null;

	/** The batch date as string */
	private transient String strBatchRunDate = null;

	/** The execution end time */
	private transient Long executionStartTime = null;	
	
	/** The execution end time */
	private transient Long executionEndTime = null;	
	
	/** 
	 * The error sequence number provided for the batch that all batch objects should use 
	 * for logging purpose 
	 */
	private transient String batchErrorSequence = null;
	
	/** 
	 * Indicator to determine whether the current run of the batch
	 * is for a revision 
	 */
	private transient Boolean revisionRun = false;
	
	/**
	 * The ordered map that holds the execution order for entities
	 * (and hence the jobs under the entities)
	 */
	private TreeMap<Integer, EntityParams> orderedMap = null;	
	
	/** 
	 * Indicator to determine whether the batch is run for a date 
	 * or specific to a set of entities
	 */
	private Boolean dateRun = false;
	
	/** Instruction Log Sequence # */
	private Integer instructionLogSeq = null;
	
	/** Action taken by the Processor */
	private String batchAction;
	
	/** The time at which the Processor took action */
	private Long batchActionTime;
	
	/** The Process Request Identifier */
	private Long requestId;
	
	/** The Process Request Version */
	private String pREVersion;
	
	/**
	 * Stores the fail over flag.
	 */
	private boolean failOver = false;
	
	/**
	 * Special information to be saved for revision run, in case the batch 
	 * is shutdown due to conditions or time constraints. 
	 * This field should not be used otherwise during the processing. 
	 * This field is populated during the time of saving the state of the batch. 
	 * For accessing the progress level during the processing, always 
	 * use {@link ProgressLevel#getProgressLevel()}
	 * 
	 */
	private ProgressLevel progressLevelAtSavePoint;
	
	/** 
	 * Batch can have endings for many reasons.
	 * <OL>
	 * <LI> BATCH_COMPLETED - COMPLETED successfully with or without errors 
	 * <LI> USER_INTERRUPTED - COMPLETED because of user interruption
	 * <LI> END_OF_TIME - COMPLETED because the end time for the batch is realized
	 * <LI> BATCH_FAILED - The batch has failed and stopped, because an object marked as 
	 * 		on-fail-exit = 'Y' has failed
	 * </OL>
	 * This variable is used to store the closure reason
	 */
	private String closureReason;
	
	private String startUser;
	
	private String endUser;
	
	/**
	 * The hasp-map will contain any sharable batch execution related information
	 */
	private HashMap<String, Object> batchExecutionContext = new HashMap<String, Object>();

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
	 * Returns the dateRun
	 * 
	 * @return the dateRun
	 */
	public Boolean isDateRun() {
		return dateRun;
	}

	/**
	 * Sets the dateRun
	 * 
	 * @param dateRun 
	 *        The dateRun to set
	 */
	public void setDateRun(Boolean dateRun) {
		this.dateRun = dateRun;
	}	

	/**
	 * Returns the orderedMap
	 * 
	 * @return the orderedMap
	 */
	public TreeMap<Integer, EntityParams> getOrderedMap() {
		return orderedMap;
	}

	/**
	 * Sets the orderedMap
	 * 
	 * @param orderedMap 
	 *        The orderedMap to set
	 */
	public void setOrderedMap(TreeMap<Integer, EntityParams> orderedMap) {
		this.orderedMap = orderedMap;
	}	

	/**
	 * Returns the revisionRun
	 * 
	 * @return the revisionRun
	 */
	public Boolean isRevisionRun() {
		return revisionRun;
	}

	/**
	 * Sets the revisionRun
	 * 
	 * @param revisionRun 
	 *        The revisionRun to set
	 */
	public void setRevisionRun(Boolean revisionRun) {
		this.revisionRun = revisionRun;
	}	

	/**
	 * Returns the batchRunDate
	 * 
	 * @return the batchRunDate
	 */
	public Date getBatchRunDate() {
		return (batchRunDate==null?null:new Date(batchRunDate));
	}

	/**
	 * Sets the batchRunDate
	 * 
	 * @param batchRunDate
	 *        The batchRunDate to set
	 */
	public void setBatchRunDate(Date batchRunDate) throws BatchException{
		this.batchRunDate = (batchRunDate==null?null:batchRunDate.getTime());
		this.strBatchRunDate = (batchRunDate==null?null:CDate.getUDFDateString(batchRunDate, Constants.BATCH_RUN_DATE_FORMAT));
	}
	
	/**
	 * Returns the batchRunDate in the format dd-MMM-yyyy 23:59:59
	 * 
	 * @return the batchRunDate as String in the format dd-MMM-yyyy 23:59:59
	 */
	public String getBatchRunDateAsString() {
		return strBatchRunDate;
	}
	
	/**
	 * Returns the executionEndTime
	 * 
	 * @return the executionEndTime
	 */
	public Date getExecutionEndTime() {
		return (executionEndTime==null?null:new Date(executionEndTime));
	}

	/**
	 * Sets the executionEndTime
	 * 
	 * @param executionEndTime 
	 *        The executionEndTime to set
	 */
	public void setExecutionEndTime(Date executionEndTime) {
		this.executionEndTime = (executionEndTime==null?null:executionEndTime.getTime());
	}

	/**
	 * Returns the executionStartTime
	 * 
	 * @return the executionStartTime
	 */
	
	public Date getExecutionStartTime() {
		return (executionStartTime==null?null:new Date(executionStartTime));
	}

	/**
	 * Sets the executionStartTime to set
	 * 
	 * @param executionStartTime
	 * 		  The executionStartTime to set
	 */
	
	public void setExecutionStartTime(Date executionStartTime) {
		this.executionStartTime = (executionStartTime==null?null:executionStartTime.getTime());
	}


	/**
	 * Returns the batch name 
	 * 
	 * @return the batch name
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * Sets the batch name 
	 * 
	 * @param batchName 
	 * 		  The batch name to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * Returns the batch type 
	 * 
	 * @return the batch type
	 */
	public String getBatchType() {
		return batchType;
	}

	/**
	 * Sets the batch type 
	 * 
	 * @param batchType 
	 * 		  The batch type to set
	 */
	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	/**
	 * @return the batchErrorSequence
	 */
	public String getBatchErrorSequence() {
		return batchErrorSequence;
	}

	/**
	 * @param batchErrorSequence the batchErrorSequence to set
	 */
	public void setBatchErrorSequence(String batchErrorSequence) {
		this.batchErrorSequence = batchErrorSequence;
	}
	
	/**
	 * Returns the instructionLogSeq
	 * 
	 * @return the instructionLogSeq
	 */
	public Integer getInstructionLogSeq() {
		return instructionLogSeq;
	}

	/**
	 * Sets the instructionLogSeq
	 * 
	 * @param instructionLogSeq 
	 *        The instructionLogSeq to set
	 */
	public void setInstructionLogSeq(Integer instructionLogSeq) {
		this.instructionLogSeq = instructionLogSeq;
	}

	/**
	 * Returns the batchAction
	 * 
	 * @return the batchAction
	 */
	public String getBatchAction() {
		return batchAction;
	}

	/**
	 * Sets the batchAction
	 * 
	 * @param batchAction 
	 *        The batchAction to set
	 */
	public void setBatchAction(String batchAction) {
		this.batchAction = batchAction;
	}

	/**
	 * Returns the batchActionTime
	 * 
	 * @return the batchActionTime
	 */
	public Date getBatchActionTime() {
		return new Date(batchActionTime);
	}

	/**
	 * Sets the batchActionTime
	 * 
	 * @param batchActionTime 
	 *        The batchActionTime to set
	 */
	public void setBatchActionTime(Date batchActionTime) {
		this.batchActionTime = batchActionTime.getTime();
	}

	/**
	 * Returns the requestId
	 * 
	 * @return the requestId
	 */
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * Sets the requestId
	 * 
	 * @param requestId 
	 *        The requestId to set
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	/**
	 * Returns the pREVersion
	 * 
	 * @return the pREVersion
	 */
	public String getPREVersion() {
		return pREVersion;
	}

	/**
	 * Sets the pREVersion
	 * 
	 * @param version 
	 *        The pREVersion to set
	 */
	public void setPREVersion(String version) {
		pREVersion = version;
	}

	/**
	 * Returns the closureReason
	 * 
	 * @return the closureReason
	 */
	
	public String getClosureReason() {
		return closureReason;
	}

	/**
	 * Sets the closureReason to set
	 * 
	 * @param closureReason
	 * 		  The closureReason to set
	 */
	
	public void setClosureReason(String closureReason) {
		this.closureReason = closureReason;
	}

	/**
	 * Returns the string representation of the object
	 * 
	 *  @return the string representation of the object
	 */
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Batch Information");
		sb.append(Constants.NEW_LINE);
		sb.append("------------------------------------");
		sb.append(Constants.NEW_LINE);
		sb.append("Batch No : " + getBatchNo());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Rev No : " + getBatchRevNo());
		
		sb.append(Constants.NEW_LINE);
		sb.append("Instruction Log # : " + getInstructionLogSeq());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Name : " + getBatchName());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Type : " + getBatchType());		
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Run Date : " + getBatchRunDateAsString());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Error Sequence # : " + getBatchErrorSequence());
			
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Start Time : " + getExecutionStartTime());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch End Time : " + getExecutionEndTime());
		sb.append(Constants.NEW_LINE);
		sb.append("Batch Started By : " + getStartUser());
		sb.append(Constants.NEW_LINE);
		sb.append("Is Revision Run : " + isRevisionRun());
		sb.append(Constants.NEW_LINE);
		sb.append("Is Date Run : " + isDateRun());
		sb.append(Constants.NEW_LINE);
		sb.append("Is FAILED Over : " + isFailedOver());
		
		sb.append(Constants.NEW_LINE);
		sb.append("Process Request Id : " + getRequestId());
		sb.append(Constants.NEW_LINE);
		sb.append("PRE Version : " + getPREVersion());
		sb.append(Constants.NEW_LINE);
		sb.append("JBEAM Core Version : " + getVersion());
		if(isRevisionRun()) {
		    sb.append(Constants.NEW_LINE);
		    sb.append("Last Saved State : " + getProgressLevelAtLastSavePoint().getExecutionStatus());
		}
		
		sb.append(Constants.NEW_LINE);
		sb.append("Execution Order : ");
		sb.append(Constants.NEW_LINE);
		sb.append(getOrderedMap());
		
		return sb.toString();
	}
	
	/**
	 * Sets the fail over flag.
	 * 
	 * @param failOver True if failed over otherwise false.
	 */
	public void setFailedOver(boolean failOver) {
	    this.failOver = failOver;
	}
	
	/**
	 * Returns the failed over flag.
	 * True indicates that the batch has failed over to another instance of PRE else
	 * returns false.
	 * 
	 * @return boolean
	 */
	public boolean isFailedOver() {
	    return failOver;
	}

	/**
	 * Sets the {@link ProgressLevel} that should be at the resume.
	 * @param progressLevelAtLastSavePoint the progressLevelAtSavePoint to set
	 */
	public void setProgressLevelAtLastSavePoint(ProgressLevel progressLevelAtLastSavePoint) {
		this.progressLevelAtSavePoint = progressLevelAtLastSavePoint;
	}

	/**
	 * Returns the {@link ProgressLevel} at the resume time or what was saved during the last save point.
	 * May return null in case the save point was never created.
	 * @return the progressLevelAtSavePoint
	 */
	public ProgressLevel getProgressLevelAtLastSavePoint() {
		return progressLevelAtSavePoint;
	}

	private String version;
	private int majorVersion;
	private int minorVersion;
	private int macroVersion;
	private int buildNumber;

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the majorVersion
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @param majorVersion the majorVersion to set
	 */
	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @param minorVersion the minorVersion to set
	 */
	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @return the startUser
	 */
	public String getStartUser() {
		return startUser;
	}

	/**
	 * @param startUser the startUser to set
	 */
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}

	/**
	 * @return the endUser
	 */
	public String getEndUser() {
		return endUser;
	}

	/**
	 * @param endUser the endUser to set
	 */
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		revisionRun = false;
	}

	/**
     * @param macroVersion the macroVersion to set
     */
    public void setMacroVersion(int macroVersion) {
	    this.macroVersion = macroVersion;
    }

	/**
     * @return the macroVersion
     */
    public int getMacroVersion() {
	    return macroVersion;
    }

	/**
     * @param buildNumber the buildNumber to set
     */
    public void setBuildNumber(int buildNumber) {
	    this.buildNumber = buildNumber;
    }

	/**
     * @return the buildNumber
     */
    public int getBuildNumber() {
	    return buildNumber;
    }

	/**
	 * Returns batchExecutionContext.
	 * 
	 * @return HashMap<String, Object> : The context information stored during batch execution.  
	 */
	public HashMap<String, Object> getBatchExecutionContext() {
		return batchExecutionContext;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchInfo.java                                                                                      $
 * 
 * 12    4/28/10 10:37a Kedarr
 * Updated javadoc
 * 
 * 11    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 10    4/21/10 12:02p Kedarr
 * Removed the execution status string field and instead now the progress level cloned version is saved directly.
 * 
 * 9     2/25/10 10:39a Grahesh
 * Added fail over flag.
 * 
 * 8     2/17/10 9:12a Grahesh
 * 
 * 7     2/15/10 11:38a Mandar.vaidya
 * Modified the toMap method
 * 
 * 6     12/24/09 2:30p Grahesh
 * Corrected the logic for fetching the batch run date as string
 * 
 * 5     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 4     12/21/09 5:13p Grahesh
 * Added new attribute closureReason
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/