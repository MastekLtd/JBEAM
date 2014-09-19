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

import java.util.Date;
import java.util.List;

public class ExecuteReport extends BaseResponseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String reportId;
    private List<Report> reports;
    private List<ReportParameter> reportParameters;
    private List<ReportParameter> requestReportParameters;
    private List<ReportParameter> queryResults;
    private List<ReportParameter> headingDetails;
    private List<ReportParameter> reportDataDetails;
    private Date systemDate;
    private String reportName;
    private String query;
    private String count;
    private String reportUrl;
    private String reportFields;
    private String hours;
    private String minutes;
    private String seconds;
    private String requestId;
    private String reportServerUrl;
    private String reportServerAlias;
    private String reportPath;
    private String dataStream;
    private String frequency;
    private String recurrence;
    private String weekDay;
    private Date startDate;
    private String startTime;
    private String optionEndDate;
    private Date endDate;
    private String endTime;
    private String occurence;
    private String requestState;
    private String scheduleProcessClassNo;
    private String weekStartTime;
    private String weekEndTime;
    private String futureSchedule;
    private String emailId;
    private String destinationType;
    private String preProgrammedFrequency;
    private String frequencyUsage;
    private String scheduleState;
    private String endOccurence;
    private String scheduleText;
    private String scheduleStatus;
    private String viewFrom;
    private String processName;
    private String toHours;
    private String toMinutes;
    private String toSeconds;
    private String processDescription;
    private String displayOrder;
    private String scheduleId;
    private String noOfRequests;
    private String hoFlag;
    private String branchCd;
    private String orderBy;
    private String userId;
    private String cancelQueued;
    private String successFailureFlag;
    private String exceptionMessage;
    
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
	 * Gets the installationCode
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Gets the reportId
	 *
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}
	/**
	 * Sets the reportId
	 *
	 * @param reportId 
	 *	      The reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	/**
	 * Gets the reports
	 *
	 * @return the reports
	 */
	public List<Report> getReports() {
		return reports;
	}
	/**
	 * Sets the reports
	 *
	 * @param reports 
	 *	      The reports to set
	 */
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	/**
	 * Gets the reportParameters
	 *
	 * @return the reportParameters
	 */
	public List<ReportParameter> getReportParameters() {
		return reportParameters;
	}
	/**
	 * Sets the reportParameters
	 *
	 * @param reportParameters 
	 *	      The reportParameters to set
	 */
	public void setReportParameters(List<ReportParameter> reportParameters) {
		this.reportParameters = reportParameters;
	}
	/**
	 * Gets the requestReportParameters
	 *
	 * @return the requestReportParameters
	 */
	public List<ReportParameter> getRequestReportParameters() {
		return requestReportParameters;
	}
	/**
	 * Sets the requestReportParameters
	 *
	 * @param requestReportParameters 
	 *	      The requestReportParameters to set
	 */
	public void setRequestReportParameters(
			List<ReportParameter> requestReportParameters) {
		this.requestReportParameters = requestReportParameters;
	}
	/**
	 * Gets the queryResults
	 *
	 * @return the queryResults
	 */
	public List<ReportParameter> getQueryResults() {
		return queryResults;
	}
	/**
	 * Sets the queryResults
	 *
	 * @param queryResults 
	 *	      The queryResults to set
	 */
	public void setQueryResults(List<ReportParameter> queryResults) {
		this.queryResults = queryResults;
	}
	/**
	 * Gets the headingDetails
	 *
	 * @return the headingDetails
	 */
	public List<ReportParameter> getHeadingDetails() {
		return headingDetails;
	}
	/**
	 * Sets the headingDetails
	 *
	 * @param headingDetails 
	 *	      The headingDetails to set
	 */
	public void setHeadingDetails(List<ReportParameter> headingDetails) {
		this.headingDetails = headingDetails;
	}
	/**
	 * Gets the reportDataDetails
	 *
	 * @return the reportDataDetails
	 */
	public List<ReportParameter> getReportDataDetails() {
		return reportDataDetails;
	}
	/**
	 * Sets the reportDataDetails
	 *
	 * @param reportDataDetails 
	 *	      The reportDataDetails to set
	 */
	public void setReportDataDetails(List<ReportParameter> reportDataDetails) {
		this.reportDataDetails = reportDataDetails;
	}
	/**
	 * Gets the systemDate
	 *
	 * @return the systemDate
	 */
	public Date getSystemDate() {
		return systemDate;
	}
	/**
	 * Sets the systemDate
	 *
	 * @param systemDate 
	 *	      The systemDate to set
	 */
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}
	/**
	 * Gets the reportName
	 *
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * Sets the reportName
	 *
	 * @param reportName 
	 *	      The reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * Gets the query
	 *
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * Sets the query
	 *
	 * @param query 
	 *	      The query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * Gets the count
	 *
	 * @return the count
	 */
	public String getCount() {
		return count;
	}
	/**
	 * Sets the count
	 *
	 * @param count 
	 *	      The count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * Gets the reportUrl
	 *
	 * @return the reportUrl
	 */
	public String getReportUrl() {
		return reportUrl;
	}
	/**
	 * Sets the reportUrl
	 *
	 * @param reportUrl 
	 *	      The reportUrl to set
	 */
	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
	/**
	 * Gets the reportFields
	 *
	 * @return the reportFields
	 */
	public String getReportFields() {
		return reportFields;
	}
	/**
	 * Sets the reportFields
	 *
	 * @param reportFields 
	 *	      The reportFields to set
	 */
	public void setReportFields(String reportFields) {
		this.reportFields = reportFields;
	}
	/**
	 * Gets the hours
	 *
	 * @return the hours
	 */
	public String getHours() {
		return hours;
	}
	/**
	 * Sets the hours
	 *
	 * @param hours 
	 *	      The hours to set
	 */
	public void setHours(String hours) {
		this.hours = hours;
	}
	/**
	 * Gets the minutes
	 *
	 * @return the minutes
	 */
	public String getMinutes() {
		return minutes;
	}
	/**
	 * Sets the minutes
	 *
	 * @param minutes 
	 *	      The minutes to set
	 */
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	/**
	 * Gets the seconds
	 *
	 * @return the seconds
	 */
	public String getSeconds() {
		return seconds;
	}
	/**
	 * Sets the seconds
	 *
	 * @param seconds 
	 *	      The seconds to set
	 */
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	/**
	 * Gets the requestId
	 *
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * Sets the requestId
	 *
	 * @param requestId 
	 *	      The requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * Gets the reportServerUrl
	 *
	 * @return the reportServerUrl
	 */
	public String getReportServerUrl() {
		return reportServerUrl;
	}
	/**
	 * Sets the reportServerUrl
	 *
	 * @param reportServerUrl 
	 *	      The reportServerUrl to set
	 */
	public void setReportServerUrl(String reportServerUrl) {
		this.reportServerUrl = reportServerUrl;
	}
	/**
	 * Gets the reportServerAlias
	 *
	 * @return the reportServerAlias
	 */
	public String getReportServerAlias() {
		return reportServerAlias;
	}
	/**
	 * Sets the reportServerAlias
	 *
	 * @param reportServerAlias 
	 *	      The reportServerAlias to set
	 */
	public void setReportServerAlias(String reportServerAlias) {
		this.reportServerAlias = reportServerAlias;
	}
	/**
	 * Gets the reportPath
	 *
	 * @return the reportPath
	 */
	public String getReportPath() {
		return reportPath;
	}
	/**
	 * Sets the reportPath
	 *
	 * @param reportPath 
	 *	      The reportPath to set
	 */
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	/**
	 * Gets the dataStream
	 *
	 * @return the dataStream
	 */
	public String getDataStream() {
		return dataStream;
	}
	/**
	 * Sets the dataStream
	 *
	 * @param dataStream 
	 *	      The dataStream to set
	 */
	public void setDataStream(String dataStream) {
		this.dataStream = dataStream;
	}
	/**
	 * Gets the frequency
	 *
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}
	/**
	 * Sets the frequency
	 *
	 * @param frequency 
	 *	      The frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	/**
	 * Gets the recurrence
	 *
	 * @return the recurrence
	 */
	public String getRecurrence() {
		return recurrence;
	}
	/**
	 * Sets the recurrence
	 *
	 * @param recurrence 
	 *	      The recurrence to set
	 */
	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}
	/**
	 * Gets the weekDay
	 *
	 * @return the weekDay
	 */
	public String getWeekDay() {
		return weekDay;
	}
	/**
	 * Sets the weekDay
	 *
	 * @param weekDay 
	 *	      The weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	/**
	 * Gets the startDate
	 *
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * Sets the startDate
	 *
	 * @param startDate 
	 *	      The startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * Gets the startTime
	 *
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * Sets the startTime
	 *
	 * @param startTime 
	 *	      The startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * Gets the optionEndDate
	 *
	 * @return the optionEndDate
	 */
	public String getOptionEndDate() {
		return optionEndDate;
	}
	/**
	 * Sets the optionEndDate
	 *
	 * @param optionEndDate 
	 *	      The optionEndDate to set
	 */
	public void setOptionEndDate(String optionEndDate) {
		this.optionEndDate = optionEndDate;
	}
	/**
	 * Gets the endDate
	 *
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * Sets the endDate
	 *
	 * @param endDate 
	 *	      The endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * Gets the endTime
	 *
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * Sets the endTime
	 *
	 * @param endTime 
	 *	      The endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * Gets the occurence
	 *
	 * @return the occurence
	 */
	public String getOccurence() {
		return occurence;
	}
	/**
	 * Sets the occurence
	 *
	 * @param occurence 
	 *	      The occurence to set
	 */
	public void setOccurence(String occurence) {
		this.occurence = occurence;
	}
	/**
	 * Gets the requestState
	 *
	 * @return the requestState
	 */
	public String getRequestState() {
		return requestState;
	}
	/**
	 * Sets the requestState
	 *
	 * @param requestState 
	 *	      The requestState to set
	 */
	public void setRequestState(String requestState) {
		this.requestState = requestState;
	}
	/**
	 * Gets the scheduleProcessClassNo
	 *
	 * @return the scheduleProcessClassNo
	 */
	public String getScheduleProcessClassNo() {
		return scheduleProcessClassNo;
	}
	/**
	 * Sets the scheduleProcessClassNo
	 *
	 * @param scheduleProcessClassNo 
	 *	      The scheduleProcessClassNo to set
	 */
	public void setScheduleProcessClassNo(String scheduleProcessClassNo) {
		this.scheduleProcessClassNo = scheduleProcessClassNo;
	}
	/**
	 * Gets the weekStartTime
	 *
	 * @return the weekStartTime
	 */
	public String getWeekStartTime() {
		return weekStartTime;
	}
	/**
	 * Sets the weekStartTime
	 *
	 * @param weekStartTime 
	 *	      The weekStartTime to set
	 */
	public void setWeekStartTime(String weekStartTime) {
		this.weekStartTime = weekStartTime;
	}
	/**
	 * Gets the weekEndTime
	 *
	 * @return the weekEndTime
	 */
	public String getWeekEndTime() {
		return weekEndTime;
	}
	/**
	 * Sets the weekEndTime
	 *
	 * @param weekEndTime 
	 *	      The weekEndTime to set
	 */
	public void setWeekEndTime(String weekEndTime) {
		this.weekEndTime = weekEndTime;
	}
	/**
	 * Gets the futureSchedule
	 *
	 * @return the futureSchedule
	 */
	public String getFutureSchedule() {
		return futureSchedule;
	}
	/**
	 * Sets the futureSchedule
	 *
	 * @param futureSchedule 
	 *	      The futureSchedule to set
	 */
	public void setFutureSchedule(String futureSchedule) {
		this.futureSchedule = futureSchedule;
	}
	/**
	 * Gets the emailId
	 *
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * Sets the emailId
	 *
	 * @param emailId 
	 *	      The emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * Gets the destinationType
	 *
	 * @return the destinationType
	 */
	public String getDestinationType() {
		return destinationType;
	}
	/**
	 * Sets the destinationType
	 *
	 * @param destinationType 
	 *	      The destinationType to set
	 */
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	/**
	 * Gets the preProgrammedFrequency
	 *
	 * @return the preProgrammedFrequency
	 */
	public String getPreProgrammedFrequency() {
		return preProgrammedFrequency;
	}
	/**
	 * Sets the preProgrammedFrequency
	 *
	 * @param preProgrammedFrequency 
	 *	      The preProgrammedFrequency to set
	 */
	public void setPreProgrammedFrequency(String preProgrammedFrequency) {
		this.preProgrammedFrequency = preProgrammedFrequency;
	}
	/**
	 * Gets the frequencyUsage
	 *
	 * @return the frequencyUsage
	 */
	public String getFrequencyUsage() {
		return frequencyUsage;
	}
	/**
	 * Sets the frequencyUsage
	 *
	 * @param frequencyUsage 
	 *	      The frequencyUsage to set
	 */
	public void setFrequencyUsage(String frequencyUsage) {
		this.frequencyUsage = frequencyUsage;
	}
	/**
	 * Gets the scheduleState
	 *
	 * @return the scheduleState
	 */
	public String getScheduleState() {
		return scheduleState;
	}
	/**
	 * Sets the scheduleState
	 *
	 * @param scheduleState 
	 *	      The scheduleState to set
	 */
	public void setScheduleState(String scheduleState) {
		this.scheduleState = scheduleState;
	}
	/**
	 * Gets the endOccurence
	 *
	 * @return the endOccurence
	 */
	public String getEndOccurence() {
		return endOccurence;
	}
	/**
	 * Sets the endOccurence
	 *
	 * @param endOccurence 
	 *	      The endOccurence to set
	 */
	public void setEndOccurence(String endOccurence) {
		this.endOccurence = endOccurence;
	}
	/**
	 * Gets the scheduleText
	 *
	 * @return the scheduleText
	 */
	public String getScheduleText() {
		return scheduleText;
	}
	/**
	 * Sets the scheduleText
	 *
	 * @param scheduleText 
	 *	      The scheduleText to set
	 */
	public void setScheduleText(String scheduleText) {
		this.scheduleText = scheduleText;
	}
	/**
	 * Gets the scheduleStatus
	 *
	 * @return the scheduleStatus
	 */
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	/**
	 * Sets the scheduleStatus
	 *
	 * @param scheduleStatus 
	 *	      The scheduleStatus to set
	 */
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	/**
	 * Gets the viewFrom
	 *
	 * @return the viewFrom
	 */
	public String getViewFrom() {
		return viewFrom;
	}
	/**
	 * Sets the viewFrom
	 *
	 * @param viewFrom 
	 *	      The viewFrom to set
	 */
	public void setViewFrom(String viewFrom) {
		this.viewFrom = viewFrom;
	}
	/**
	 * Gets the processName
	 *
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	/**
	 * Sets the processName
	 *
	 * @param processName 
	 *	      The processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * Gets the toHours
	 *
	 * @return the toHours
	 */
	public String getToHours() {
		return toHours;
	}
	/**
	 * Sets the toHours
	 *
	 * @param toHours 
	 *	      The toHours to set
	 */
	public void setToHours(String toHours) {
		this.toHours = toHours;
	}
	/**
	 * Gets the toMinutes
	 *
	 * @return the toMinutes
	 */
	public String getToMinutes() {
		return toMinutes;
	}
	/**
	 * Sets the toMinutes
	 *
	 * @param toMinutes 
	 *	      The toMinutes to set
	 */
	public void setToMinutes(String toMinutes) {
		this.toMinutes = toMinutes;
	}
	/**
	 * Gets the toSeconds
	 *
	 * @return the toSeconds
	 */
	public String getToSeconds() {
		return toSeconds;
	}
	/**
	 * Sets the toSeconds
	 *
	 * @param toSeconds 
	 *	      The toSeconds to set
	 */
	public void setToSeconds(String toSeconds) {
		this.toSeconds = toSeconds;
	}
	/**
	 * Gets the processDescription
	 *
	 * @return the processDescription
	 */
	public String getProcessDescription() {
		return processDescription;
	}
	/**
	 * Sets the processDescription
	 *
	 * @param processDescription 
	 *	      The processDescription to set
	 */
	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}
	/**
	 * Gets the displayOrder
	 *
	 * @return the displayOrder
	 */
	public String getDisplayOrder() {
		return displayOrder;
	}
	/**
	 * Sets the displayOrder
	 *
	 * @param displayOrder 
	 *	      The displayOrder to set
	 */
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * Gets the scheduleId
	 *
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}
	/**
	 * Sets the scheduleId
	 *
	 * @param scheduleId 
	 *	      The scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * Gets the noOfRequests
	 *
	 * @return the noOfRequests
	 */
	public String getNoOfRequests() {
		return noOfRequests;
	}
	/**
	 * Sets the noOfRequests
	 *
	 * @param noOfRequests 
	 *	      The noOfRequests to set
	 */
	public void setNoOfRequests(String noOfRequests) {
		this.noOfRequests = noOfRequests;
	}
	/**
	 * Gets the hoFlag
	 *
	 * @return the hoFlag
	 */
	public String getHoFlag() {
		return hoFlag;
	}
	/**
	 * Sets the hoFlag
	 *
	 * @param hoFlag 
	 *	      The hoFlag to set
	 */
	public void setHoFlag(String hoFlag) {
		this.hoFlag = hoFlag;
	}
	/**
	 * Gets the branchCd
	 *
	 * @return the branchCd
	 */
	public String getBranchCd() {
		return branchCd;
	}
	/**
	 * Sets the branchCd
	 *
	 * @param branchCd 
	 *	      The branchCd to set
	 */
	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}
	/**
	 * Gets the orderBy
	 *
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * Sets the orderBy
	 *
	 * @param orderBy 
	 *	      The orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	 * Sets the userId
	 *
	 * @param userId 
	 *	      The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Gets the cancelQueued
	 *
	 * @return the cancelQueued
	 */
	public String getCancelQueued() {
		return cancelQueued;
	}
	/**
	 * Sets the cancelQueued
	 *
	 * @param cancelQueued 
	 *	      The cancelQueued to set
	 */
	public void setCancelQueued(String cancelQueued) {
		this.cancelQueued = cancelQueued;
	}
	/**
	 * Gets the successFailureFlag
	 *
	 * @return the successFailureFlag
	 */
	public String getSuccessFailureFlag() {
		return successFailureFlag;
	}
	/**
	 * Sets the successFailureFlag
	 *
	 * @param successFailureFlag 
	 *        The successFailureFlag to set.
	 */
	public void setSuccessFailureFlag(String successFailureFlag) {
		this.successFailureFlag = successFailureFlag;
	}
	/**
	 * Gets the exceptionMessage
	 *
	 * @return the exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	/**
	 * Sets the exceptionMessage
	 *
	 * @param exceptionMessage 
	 *        The exceptionMessage to set.
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
    
    
}
