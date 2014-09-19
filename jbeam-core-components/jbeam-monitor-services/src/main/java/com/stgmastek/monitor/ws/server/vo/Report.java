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


public class Report extends BaseResponseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	private String reportId;
    private String reportName;
    private String programName;
    private String reportNo;
    private String reportType;
    private String companyCode;
    private String parentId;
    private String query;
    private String requestId;
    private String preFlag;
    private String processType;
    private String fileName;
    private String sequenceNo;
    
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
	 * Gets the programName
	 *
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}
	/**
	 * Sets the programName
	 *
	 * @param programName 
	 *	      The programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	/**
	 * Gets the reportNo
	 *
	 * @return the reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	/**
	 * Sets the reportNo
	 *
	 * @param reportNo 
	 *	      The reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	 * Gets the reportType
	 *
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}
	/**
	 * Sets the reportType
	 *
	 * @param reportType 
	 *	      The reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	/**
	 * Gets the companyCode
	 *
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * Sets the companyCode
	 *
	 * @param companyCode 
	 *	      The companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * Gets the parentId
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * Sets the parentId
	 *
	 * @param parentId 
	 *	      The parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * Gets the preFlag
	 *
	 * @return the preFlag
	 */
	public String getPreFlag() {
		return preFlag;
	}
	/**
	 * Sets the preFlag
	 *
	 * @param preFlag 
	 *	      The preFlag to set
	 */
	public void setPreFlag(String preFlag) {
		this.preFlag = preFlag;
	}
	/**
	 * Gets the processType
	 *
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}
	/**
	 * Sets the processType
	 *
	 * @param processType 
	 *	      The processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	/**
	 * Gets the fileName
	 *
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Sets the fileName
	 *
	 * @param fileName 
	 *	      The fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Gets the sequenceNo
	 *
	 * @return the sequenceNo
	 */
	public String getSequenceNo() {
		return sequenceNo;
	}
	/**
	 * Sets the sequenceNo
	 *
	 * @param sequenceNo 
	 *	      The sequenceNo to set
	 */
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
