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

public class ReportParameter extends BaseResponseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String reportId;
    private String paramName;
    private String dataType;
    private Integer fieldMaxlength;
    private String fixedLength;
    private String defaultValue;
    private String hint;
    private String label;
    private String queryFlag;
    private String query;
    private String mandatoryFlag;
    private String staticDynamicFlag;
    private List<ConfigParameter> entities;
    private String fieldName;
    private String paramOrder;
    private String paramDataType;
    private String operator;
    private String paramField;
    private String paramValue;
    private String groupId;
    private String groupSequence;
    private String reqLogFileName;
    private String menuId;
    private Date dateTime;
    private String requestState;
    private String scheduleId;
    private String stuckLimit;
    private String stuckMaxLimit;
    private String paramCount;
    private String reportFlag;
    private String requestId;
    private Date requestDate;
    private String userId;
    private Date startDate;
    private Date endDate;
    private Date requestEndDate;
    private String processName;
    private String reqLogFilePath;
    private Date scheduleDate;
    private String groupSeqIndicator;
    private String paramNo;
    private Date dateSpecificParamValue;
    private String successFailureFlag;
    private String exceptionMessage;
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
	 *        The reportId to set.
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	/**
	 * Gets the paramName
	 *
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}
	/**
	 * Sets the paramName
	 *
	 * @param paramName 
	 *        The paramName to set.
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * Gets the dataType
	 *
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * Sets the dataType
	 *
	 * @param dataType 
	 *        The dataType to set.
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * Gets the fieldMaxlength
	 *
	 * @return the fieldMaxlength
	 */
	public Integer getFieldMaxlength() {
		return fieldMaxlength;
	}
	/**
	 * Sets the fieldMaxlength
	 *
	 * @param fieldMaxlength 
	 *        The fieldMaxlength to set.
	 */
	public void setFieldMaxlength(Integer fieldMaxlength) {
		this.fieldMaxlength = fieldMaxlength;
	}
	/**
	 * Gets the fixedLength
	 *
	 * @return the fixedLength
	 */
	public String getFixedLength() {
		return fixedLength;
	}
	/**
	 * Sets the fixedLength
	 *
	 * @param fixedLength 
	 *        The fixedLength to set.
	 */
	public void setFixedLength(String fixedLength) {
		this.fixedLength = fixedLength;
	}
	/**
	 * Gets the defaultValue
	 *
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * Sets the defaultValue
	 *
	 * @param defaultValue 
	 *        The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * Gets the hint
	 *
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}
	/**
	 * Sets the hint
	 *
	 * @param hint 
	 *        The hint to set.
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
	/**
	 * Gets the label
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the label
	 *
	 * @param label 
	 *        The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the queryFlag
	 *
	 * @return the queryFlag
	 */
	public String getQueryFlag() {
		return queryFlag;
	}
	/**
	 * Sets the queryFlag
	 *
	 * @param queryFlag 
	 *        The queryFlag to set.
	 */
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
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
	 *        The query to set.
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * Gets the mandatoryFlag
	 *
	 * @return the mandatoryFlag
	 */
	public String getMandatoryFlag() {
		return mandatoryFlag;
	}
	/**
	 * Sets the mandatoryFlag
	 *
	 * @param mandatoryFlag 
	 *        The mandatoryFlag to set.
	 */
	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
	/**
	 * Gets the staticDynamicFlag
	 *
	 * @return the staticDynamicFlag
	 */
	public String getStaticDynamicFlag() {
		return staticDynamicFlag;
	}
	/**
	 * Sets the staticDynamicFlag
	 *
	 * @param staticDynamicFlag 
	 *        The staticDynamicFlag to set.
	 */
	public void setStaticDynamicFlag(String staticDynamicFlag) {
		this.staticDynamicFlag = staticDynamicFlag;
	}
	/**
	 * Gets the entities
	 *
	 * @return the entities
	 */
	public List<ConfigParameter> getEntities() {
		return entities;
	}
	/**
	 * Sets the entities
	 *
	 * @param entities 
	 *        The entities to set.
	 */
	public void setEntities(List<ConfigParameter> entities) {
		this.entities = entities;
	}
	/**
	 * Gets the fieldName
	 *
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * Sets the fieldName
	 *
	 * @param fieldName 
	 *        The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * Gets the paramOrder
	 *
	 * @return the paramOrder
	 */
	public String getParamOrder() {
		return paramOrder;
	}
	/**
	 * Sets the paramOrder
	 *
	 * @param paramOrder 
	 *        The paramOrder to set.
	 */
	public void setParamOrder(String paramOrder) {
		this.paramOrder = paramOrder;
	}
	/**
	 * Gets the paramDataType
	 *
	 * @return the paramDataType
	 */
	public String getParamDataType() {
		return paramDataType;
	}
	/**
	 * Sets the paramDataType
	 *
	 * @param paramDataType 
	 *        The paramDataType to set.
	 */
	public void setParamDataType(String paramDataType) {
		this.paramDataType = paramDataType;
	}
	/**
	 * Gets the operator
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * Sets the operator
	 *
	 * @param operator 
	 *        The operator to set.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * Gets the paramField
	 *
	 * @return the paramField
	 */
	public String getParamField() {
		return paramField;
	}
	/**
	 * Sets the paramField
	 *
	 * @param paramField 
	 *        The paramField to set.
	 */
	public void setParamField(String paramField) {
		this.paramField = paramField;
	}
	/**
	 * Gets the paramValue
	 *
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}
	/**
	 * Sets the paramValue
	 *
	 * @param paramValue 
	 *        The paramValue to set.
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * Gets the groupId
	 *
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * Sets the groupId
	 *
	 * @param groupId 
	 *        The groupId to set.
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * Gets the groupSequence
	 *
	 * @return the groupSequence
	 */
	public String getGroupSequence() {
		return groupSequence;
	}
	/**
	 * Sets the groupSequence
	 *
	 * @param groupSequence 
	 *        The groupSequence to set.
	 */
	public void setGroupSequence(String groupSequence) {
		this.groupSequence = groupSequence;
	}
	/**
	 * Gets the reqLogFileName
	 *
	 * @return the reqLogFileName
	 */
	public String getReqLogFileName() {
		return reqLogFileName;
	}
	/**
	 * Sets the reqLogFileName
	 *
	 * @param reqLogFileName 
	 *        The reqLogFileName to set.
	 */
	public void setReqLogFileName(String reqLogFileName) {
		this.reqLogFileName = reqLogFileName;
	}
	/**
	 * Gets the menuId
	 *
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * Sets the menuId
	 *
	 * @param menuId 
	 *        The menuId to set.
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * Gets the dateTime
	 *
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}
	/**
	 * Sets the dateTime
	 *
	 * @param dateTime 
	 *        The dateTime to set.
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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
	 *        The requestState to set.
	 */
	public void setRequestState(String requestState) {
		this.requestState = requestState;
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
	 *        The scheduleId to set.
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * Gets the stuckLimit
	 *
	 * @return the stuckLimit
	 */
	public String getStuckLimit() {
		return stuckLimit;
	}
	/**
	 * Sets the stuckLimit
	 *
	 * @param stuckLimit 
	 *        The stuckLimit to set.
	 */
	public void setStuckLimit(String stuckLimit) {
		this.stuckLimit = stuckLimit;
	}
	/**
	 * Gets the stuckMaxLimit
	 *
	 * @return the stuckMaxLimit
	 */
	public String getStuckMaxLimit() {
		return stuckMaxLimit;
	}
	/**
	 * Sets the stuckMaxLimit
	 *
	 * @param stuckMaxLimit 
	 *        The stuckMaxLimit to set.
	 */
	public void setStuckMaxLimit(String stuckMaxLimit) {
		this.stuckMaxLimit = stuckMaxLimit;
	}
	/**
	 * Gets the paramCount
	 *
	 * @return the paramCount
	 */
	public String getParamCount() {
		return paramCount;
	}
	/**
	 * Sets the paramCount
	 *
	 * @param paramCount 
	 *        The paramCount to set.
	 */
	public void setParamCount(String paramCount) {
		this.paramCount = paramCount;
	}
	/**
	 * Gets the reportFlag
	 *
	 * @return the reportFlag
	 */
	public String getReportFlag() {
		return reportFlag;
	}
	/**
	 * Sets the reportFlag
	 *
	 * @param reportFlag 
	 *        The reportFlag to set.
	 */
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
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
	 *        The requestId to set.
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * Gets the requestDate
	 *
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * Sets the requestDate
	 *
	 * @param requestDate 
	 *        The requestDate to set.
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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
	 *        The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 *        The startDate to set.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	 *        The endDate to set.
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * Gets the requestEndDate
	 *
	 * @return the requestEndDate
	 */
	public Date getRequestEndDate() {
		return requestEndDate;
	}
	/**
	 * Sets the requestEndDate
	 *
	 * @param requestEndDate 
	 *        The requestEndDate to set.
	 */
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
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
	 *        The processName to set.
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/**
	 * Gets the reqLogFilePath
	 *
	 * @return the reqLogFilePath
	 */
	public String getReqLogFilePath() {
		return reqLogFilePath;
	}
	/**
	 * Sets the reqLogFilePath
	 *
	 * @param reqLogFilePath 
	 *        The reqLogFilePath to set.
	 */
	public void setReqLogFilePath(String reqLogFilePath) {
		this.reqLogFilePath = reqLogFilePath;
	}
	/**
	 * Gets the scheduleDate
	 *
	 * @return the scheduleDate
	 */
	public Date getScheduleDate() {
		return scheduleDate;
	}
	/**
	 * Sets the scheduleDate
	 *
	 * @param scheduleDate 
	 *        The scheduleDate to set.
	 */
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	/**
	 * Gets the groupSeqIndicator
	 *
	 * @return the groupSeqIndicator
	 */
	public String getGroupSeqIndicator() {
		return groupSeqIndicator;
	}
	/**
	 * Sets the groupSeqIndicator
	 *
	 * @param groupSeqIndicator 
	 *        The groupSeqIndicator to set.
	 */
	public void setGroupSeqIndicator(String groupSeqIndicator) {
		this.groupSeqIndicator = groupSeqIndicator;
	}
	/**
	 * Gets the paramNo
	 *
	 * @return the paramNo
	 */
	public String getParamNo() {
		return paramNo;
	}
	/**
	 * Sets the paramNo
	 *
	 * @param paramNo 
	 *        The paramNo to set.
	 */
	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
	}
	/**
	 * Gets the dateSpecificParamValue
	 *
	 * @return the dateSpecificParamValue
	 */
	public Date getDateSpecificParamValue() {
		return dateSpecificParamValue;
	}
	/**
	 * Sets the dateSpecificParamValue
	 *
	 * @param dateSpecificParamValue 
	 *        The dateSpecificParamValue to set.
	 */
	public void setDateSpecificParamValue(Date dateSpecificParamValue) {
		this.dateSpecificParamValue = dateSpecificParamValue;
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
