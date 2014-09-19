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
package com.stgmastek.core.comm.server.vo;

import java.io.Serializable;

public class ScheduleData implements Serializable {

	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String batchName;
	private Integer schId;
	private String freqType;
	private Integer recur;
	private String schStat;
	private String onWeekDay;
	private Integer endOccur;
	private Long endDt;
	private String reqStat;
	private Integer occurCounter;
	private String skipFlag;
	private String weekdayCheckFlag;
	private String endReason;
	private String keepAlive;
	private String userId;
	private Long entryDt;
	private String modifyId;
	private Long modifyDt;
	private String processClassNm;
	private Long startTime;
	private Long endTime;
	private String futureSchedulingOnly;
	private String fixedDate;
	private String emailIds;
	private Long startDt;
	
	/**
	 * Sets the installationCode
	 *
	 * @param installationCode 
	 *        The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
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
	 * Sets the batchName
	 *
	 * @param batchName 
	 *        The batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	/**
	 * Gets the batchName
	 *
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}
	/**
	 * Gets the schId
	 *
	 * @return the schId
	 */
	public Integer getSchId() {
		return schId;
	}
	/**
	 * Sets the schId
	 *
	 * @param schId 
	 *		  The schId to set	
	 */
	public void setSchId(Integer schId) {
		this.schId = schId;
	}
	/**
	 * Gets the freqType
	 *
	 * @return the freqType
	 */
	public String getFreqType() {
		return freqType;
	}
	/**
	 * Sets the freqType
	 *
	 * @param freqType 
	 *		  The freqType to set	
	 */
	public void setFreqType(String freqType) {
		this.freqType = freqType;
	}
	/**
	 * Gets the recur
	 *
	 * @return the recur
	 */
	public Integer getRecur() {
		return recur;
	}
	/**
	 * Sets the recur
	 *
	 * @param recur 
	 *		  The recur to set	
	 */
	public void setRecur(Integer recur) {
		this.recur = recur;
	}
	/**
	 * Gets the schStat
	 *
	 * @return the schStat
	 */
	public String getSchStat() {
		return schStat;
	}
	/**
	 * Sets the schStat
	 *
	 * @param schStat 
	 *		  The schStat to set	
	 */
	public void setSchStat(String schStat) {
		this.schStat = schStat;
	}
	/**
	 * Gets the onWeekDay
	 *
	 * @return the onWeekDay
	 */
	public String getOnWeekDay() {
		return onWeekDay;
	}
	/**
	 * Sets the onWeekDay
	 *
	 * @param onWeekDay 
	 *		  The onWeekDay to set	
	 */
	public void setOnWeekDay(String onWeekDay) {
		this.onWeekDay = onWeekDay;
	}
	/**
	 * Gets the endOccur
	 *
	 * @return the endOccur
	 */
	public Integer getEndOccur() {
		return endOccur;
	}
	/**
	 * Sets the endOccur
	 *
	 * @param endOccur 
	 *		  The endOccur to set	
	 */
	public void setEndOccur(Integer endOccur) {
		this.endOccur = endOccur;
	}
	/**
	 * Sets the endDt
	 *
	 * @param endDt 
	 *		  The endDt to set	
	 */
	public void setEndDt(Long endDt) {
		this.endDt = endDt;
	}
	/**
	 * Gets the endDt
	 *
	 * @return the endDt
	 */
	public Long getEndDt() {
		return endDt;
	}
	/**
	 * Gets the reqStat
	 *
	 * @return the reqStat
	 */
	public String getReqStat() {
		return reqStat;
	}
	/**
	 * Sets the reqStat
	 *
	 * @param reqStat 
	 *		  The reqStat to set	
	 */
	public void setReqStat(String reqStat) {
		this.reqStat = reqStat;
	}
	/**
	 * Gets the occurCounter
	 *
	 * @return the occurCounter
	 */
	public Integer getOccurCounter() {
		return occurCounter;
	}
	/**
	 * Sets the occurCounter
	 *
	 * @param occurCounter 
	 *		  The occurCounter to set	
	 */
	public void setOccurCounter(Integer occurCounter) {
		this.occurCounter = occurCounter;
	}
	/**
	 * Gets the skipFlag
	 *
	 * @return the skipFlag
	 */
	public String getSkipFlag() {
		return skipFlag;
	}
	/**
	 * Sets the skipFlag
	 *
	 * @param skipFlag 
	 *		  The skipFlag to set	
	 */
	public void setSkipFlag(String skipFlag) {
		this.skipFlag = skipFlag;
	}
	/**
	 * Gets the weekdayCheckFlag
	 *
	 * @return the weekdayCheckFlag
	 */
	public String getWeekdayCheckFlag() {
		return weekdayCheckFlag;
	}
	/**
	 * Sets the weekdayCheckFlag
	 *
	 * @param weekdayCheckFlag 
	 *		  The weekdayCheckFlag to set	
	 */
	public void setWeekdayCheckFlag(String weekdayCheckFlag) {
		this.weekdayCheckFlag = weekdayCheckFlag;
	}
	/**
	 * Gets the endReason
	 *
	 * @return the endReason
	 */
	public String getEndReason() {
		return endReason;
	}
	/**
	 * Sets the endReason
	 *
	 * @param endReason 
	 *		  The endReason to set	
	 */
	public void setEndReason(String endReason) {
		this.endReason = endReason;
	}
	/**
	 * Gets the keepAlive
	 *
	 * @return the keepAlive
	 */
	public String getKeepAlive() {
		return keepAlive;
	}
	/**
	 * Sets the keepAlive
	 *
	 * @param keepAlive 
	 *		  The keepAlive to set	
	 */
	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}
	/**
	 * Sets the userId
	 *
	 * @param userId 
	 *		  The userId to set	
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Gets the userId
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Gets the entryDt
	 *
	 * @return the entryDt
	 */
	public Long getEntryDt() {
		return entryDt;
	}
	/**
	 * Sets the entryDt
	 *
	 * @param entryDt 
	 *        The entryDt to set
	 */
	public void setEntryDt(Long entryDt) {
		this.entryDt = entryDt;
	}
	/**
	 * Gets the modifyId
	 *
	 * @return the modifyId
	 */
	public String getModifyId() {
		return modifyId;
	}
	/**
	 * Sets the modifyId
	 *
	 * @param modifyId 
	 *        The modifyId to set
	 */
	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}
	/**
	 * Gets the modifyDt
	 *
	 * @return the modifyDt
	 */
	public Long getModifyDt() {
		return modifyDt;
	}
	/**
	 * Sets the modifyDt
	 *
	 * @param modifyDt 
	 *        The modifyDt to set
	 */
	public void setModifyDt(Long modifyDt) {
		this.modifyDt = modifyDt;
	}
	/**
	 * Gets the processClassNm
	 *
	 * @return the processClassNm
	 */
	public String getProcessClassNm() {
		return processClassNm;
	}
	/**
	 * Sets the processClassNm
	 *
	 * @param processClassNm 
	 *        The processClassNm to set
	 */
	public void setProcessClassNm(String processClassNm) {
		this.processClassNm = processClassNm;
	}
	/**
	 * Gets the startTime
	 *
	 * @return the startTime
	 */
	public Long getStartTime() {
		return startTime;
	}
	/**
	 * Sets the startTime
	 *
	 * @param startTime 
	 *        The startTime to set
	 */
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	 * Gets the endTime
	 *
	 * @return the endTime
	 */
	public Long getEndTime() {
		return endTime;
	}
	/**
	 * Sets the endTime
	 *
	 * @param endTime 
	 *        The endTime to set
	 */
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	/**
	 * Gets the futureSchedulingOnly
	 *
	 * @return the futureSchedulingOnly
	 */
	public String getFutureSchedulingOnly() {
		return futureSchedulingOnly;
	}
	/**
	 * Sets the futureSchedulingOnly
	 *
	 * @param futureSchedulingOnly 
	 *        The futureSchedulingOnly to set
	 */
	public void setFutureSchedulingOnly(String futureSchedulingOnly) {
		this.futureSchedulingOnly = futureSchedulingOnly;
	}
	/**
	 * Gets the fixedDate
	 *
	 * @return the fixedDate
	 */
	public String getFixedDate() {
		return fixedDate;
	}
	/**
	 * Sets the fixedDate
	 *
	 * @param fixedDate 
	 *        The fixedDate to set
	 */
	public void setFixedDate(String fixedDate) {
		this.fixedDate = fixedDate;
	}
	/**
	 * Gets the emailIds
	 *
	 * @return the emailIds
	 */
	public String getEmailIds() {
		return emailIds;
	}
	/**
	 * Sets the emailIds
	 *
	 * @param emailIds 
	 *        The emailIds to set
	 */
	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
	/**
	 * Gets the startDt
	 *
	 * @return the startDt
	 */
	public Long getStartDt() {
		return startDt;
	}
	/**
	 * Sets the startDt
	 *
	 * @param startDt 
	 *        The startDt to set
	 */
	public void setStartDt(Long startDt) {
		this.startDt = startDt;
	}
	
}
