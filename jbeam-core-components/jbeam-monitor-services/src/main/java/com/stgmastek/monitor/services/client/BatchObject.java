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

package com.stgmastek.monitor.services.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for batchObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="batchObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="batchNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="batchRevNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="beSeqNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="broker" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cashBatchNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cashBatchRevNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cycleNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dateExecuted" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="dateGenerate" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="entityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entityType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="errorType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gbiBillNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="generateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="installationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="jobType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="line" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listInd" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="notifyErrorTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objExecEndTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="objExecStartTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="objectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="policyRenewNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prePost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="printFormNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="priorityCode1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="priorityCode2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="recMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refSystemActivityNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sysActNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="taskName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeTaken" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="userPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vehRefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "batchObject", propOrder = {
    "batchNo",
    "batchRevNo",
    "beSeqNo",
    "broker",
    "cashBatchNo",
    "cashBatchRevNo",
    "cycleNo",
    "dateExecuted",
    "dateGenerate",
    "entityCode",
    "entityType",
    "errorDescription",
    "errorType",
    "gbiBillNo",
    "generateBy",
    "installationCode",
    "jobDesc",
    "jobType",
    "line",
    "listInd",
    "notifyErrorTo",
    "objExecEndTime",
    "objExecStartTime",
    "objectName",
    "policyNo",
    "policyRenewNo",
    "prePost",
    "printFormNo",
    "priorityCode1",
    "priorityCode2",
    "recMessage",
    "refSystemActivityNo",
    "status",
    "subline",
    "sysActNo",
    "taskName",
    "timeTaken",
    "userPriority",
    "vehRefNo"
})
public class BatchObject {

    protected Integer batchNo;
    protected Integer batchRevNo;
    protected String beSeqNo;
    protected String broker;
    protected String cashBatchNo;
    protected String cashBatchRevNo;
    protected Integer cycleNo;
    protected Long dateExecuted;
    protected Long dateGenerate;
    protected String entityCode;
    protected String entityType;
    protected String errorDescription;
    protected String errorType;
    protected String gbiBillNo;
    protected String generateBy;
    protected String installationCode;
    protected String jobDesc;
    protected String jobType;
    protected String line;
    protected Integer listInd;
    protected String notifyErrorTo;
    protected Long objExecEndTime;
    protected Long objExecStartTime;
    protected String objectName;
    protected String policyNo;
    protected String policyRenewNo;
    protected String prePost;
    protected String printFormNo;
    protected Integer priorityCode1;
    protected Integer priorityCode2;
    protected String recMessage;
    protected String refSystemActivityNo;
    protected String status;
    protected String subline;
    protected String sysActNo;
    protected String taskName;
    protected Long timeTaken;
    protected String userPriority;
    protected String vehRefNo;

    /**
     * Gets the value of the batchNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBatchNo() {
        return batchNo;
    }

    /**
     * Sets the value of the batchNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBatchNo(Integer value) {
        this.batchNo = value;
    }

    /**
     * Gets the value of the batchRevNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBatchRevNo() {
        return batchRevNo;
    }

    /**
     * Sets the value of the batchRevNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBatchRevNo(Integer value) {
        this.batchRevNo = value;
    }

    /**
     * Gets the value of the beSeqNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeSeqNo() {
        return beSeqNo;
    }

    /**
     * Sets the value of the beSeqNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeSeqNo(String value) {
        this.beSeqNo = value;
    }

    /**
     * Gets the value of the broker property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroker() {
        return broker;
    }

    /**
     * Sets the value of the broker property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroker(String value) {
        this.broker = value;
    }

    /**
     * Gets the value of the cashBatchNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashBatchNo() {
        return cashBatchNo;
    }

    /**
     * Sets the value of the cashBatchNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashBatchNo(String value) {
        this.cashBatchNo = value;
    }

    /**
     * Gets the value of the cashBatchRevNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCashBatchRevNo() {
        return cashBatchRevNo;
    }

    /**
     * Sets the value of the cashBatchRevNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCashBatchRevNo(String value) {
        this.cashBatchRevNo = value;
    }

    /**
     * Gets the value of the cycleNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCycleNo() {
        return cycleNo;
    }

    /**
     * Sets the value of the cycleNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCycleNo(Integer value) {
        this.cycleNo = value;
    }

    /**
     * Gets the value of the dateExecuted property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDateExecuted() {
        return dateExecuted;
    }

    /**
     * Sets the value of the dateExecuted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDateExecuted(Long value) {
        this.dateExecuted = value;
    }

    /**
     * Gets the value of the dateGenerate property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDateGenerate() {
        return dateGenerate;
    }

    /**
     * Sets the value of the dateGenerate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDateGenerate(Long value) {
        this.dateGenerate = value;
    }

    /**
     * Gets the value of the entityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityCode() {
        return entityCode;
    }

    /**
     * Sets the value of the entityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityCode(String value) {
        this.entityCode = value;
    }

    /**
     * Gets the value of the entityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Sets the value of the entityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntityType(String value) {
        this.entityType = value;
    }

    /**
     * Gets the value of the errorDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the value of the errorDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

    /**
     * Gets the value of the errorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Sets the value of the errorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorType(String value) {
        this.errorType = value;
    }

    /**
     * Gets the value of the gbiBillNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGbiBillNo() {
        return gbiBillNo;
    }

    /**
     * Sets the value of the gbiBillNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGbiBillNo(String value) {
        this.gbiBillNo = value;
    }

    /**
     * Gets the value of the generateBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenerateBy() {
        return generateBy;
    }

    /**
     * Sets the value of the generateBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenerateBy(String value) {
        this.generateBy = value;
    }

    /**
     * Gets the value of the installationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallationCode() {
        return installationCode;
    }

    /**
     * Sets the value of the installationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallationCode(String value) {
        this.installationCode = value;
    }

    /**
     * Gets the value of the jobDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     * Sets the value of the jobDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobDesc(String value) {
        this.jobDesc = value;
    }

    /**
     * Gets the value of the jobType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Sets the value of the jobType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobType(String value) {
        this.jobType = value;
    }

    /**
     * Gets the value of the line property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine() {
        return line;
    }

    /**
     * Sets the value of the line property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine(String value) {
        this.line = value;
    }

    /**
     * Gets the value of the listInd property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getListInd() {
        return listInd;
    }

    /**
     * Sets the value of the listInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setListInd(Integer value) {
        this.listInd = value;
    }

    /**
     * Gets the value of the notifyErrorTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifyErrorTo() {
        return notifyErrorTo;
    }

    /**
     * Sets the value of the notifyErrorTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifyErrorTo(String value) {
        this.notifyErrorTo = value;
    }

    /**
     * Gets the value of the objExecEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getObjExecEndTime() {
        return objExecEndTime;
    }

    /**
     * Sets the value of the objExecEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setObjExecEndTime(Long value) {
        this.objExecEndTime = value;
    }

    /**
     * Gets the value of the objExecStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getObjExecStartTime() {
        return objExecStartTime;
    }

    /**
     * Sets the value of the objExecStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setObjExecStartTime(Long value) {
        this.objExecStartTime = value;
    }

    /**
     * Gets the value of the objectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Sets the value of the objectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectName(String value) {
        this.objectName = value;
    }

    /**
     * Gets the value of the policyNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
     * Sets the value of the policyNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyNo(String value) {
        this.policyNo = value;
    }

    /**
     * Gets the value of the policyRenewNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicyRenewNo() {
        return policyRenewNo;
    }

    /**
     * Sets the value of the policyRenewNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicyRenewNo(String value) {
        this.policyRenewNo = value;
    }

    /**
     * Gets the value of the prePost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrePost() {
        return prePost;
    }

    /**
     * Sets the value of the prePost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrePost(String value) {
        this.prePost = value;
    }

    /**
     * Gets the value of the printFormNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintFormNo() {
        return printFormNo;
    }

    /**
     * Sets the value of the printFormNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintFormNo(String value) {
        this.printFormNo = value;
    }

    /**
     * Gets the value of the priorityCode1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriorityCode1() {
        return priorityCode1;
    }

    /**
     * Sets the value of the priorityCode1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriorityCode1(Integer value) {
        this.priorityCode1 = value;
    }

    /**
     * Gets the value of the priorityCode2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriorityCode2() {
        return priorityCode2;
    }

    /**
     * Sets the value of the priorityCode2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriorityCode2(Integer value) {
        this.priorityCode2 = value;
    }

    /**
     * Gets the value of the recMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecMessage() {
        return recMessage;
    }

    /**
     * Sets the value of the recMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecMessage(String value) {
        this.recMessage = value;
    }

    /**
     * Gets the value of the refSystemActivityNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefSystemActivityNo() {
        return refSystemActivityNo;
    }

    /**
     * Sets the value of the refSystemActivityNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefSystemActivityNo(String value) {
        this.refSystemActivityNo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the subline property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubline() {
        return subline;
    }

    /**
     * Sets the value of the subline property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubline(String value) {
        this.subline = value;
    }

    /**
     * Gets the value of the sysActNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysActNo() {
        return sysActNo;
    }

    /**
     * Sets the value of the sysActNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysActNo(String value) {
        this.sysActNo = value;
    }

    /**
     * Gets the value of the taskName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the value of the taskName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaskName(String value) {
        this.taskName = value;
    }

    /**
     * Gets the value of the timeTaken property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTimeTaken() {
        return timeTaken;
    }

    /**
     * Sets the value of the timeTaken property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTimeTaken(Long value) {
        this.timeTaken = value;
    }

    /**
     * Gets the value of the userPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPriority() {
        return userPriority;
    }

    /**
     * Sets the value of the userPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPriority(String value) {
        this.userPriority = value;
    }

    /**
     * Gets the value of the vehRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVehRefNo() {
        return vehRefNo;
    }

    /**
     * Sets the value of the vehRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVehRefNo(String value) {
        this.vehRefNo = value;
    }

}
