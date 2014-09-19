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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for executeReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="executeReport">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="branchCd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cancelQueued" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataStream" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="displayOrder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="endOccurence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exceptionMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frequency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="frequencyUsage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="futureSchedule" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="headingDetails" type="{http://services.server.ws.monitor.stgmastek.com/}reportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hoFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hours" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="installationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="minutes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="noOfRequests" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occurence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="optionEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="preProgrammedFrequency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="query" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryResults" type="{http://services.server.ws.monitor.stgmastek.com/}reportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recurrence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportDataDetails" type="{http://services.server.ws.monitor.stgmastek.com/}reportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reportFields" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportParameters" type="{http://services.server.ws.monitor.stgmastek.com/}reportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reportPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportServerAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportServerUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reportUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reports" type="{http://services.server.ws.monitor.stgmastek.com/}report" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="requestReportParameters" type="{http://services.server.ws.monitor.stgmastek.com/}reportParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="requestState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduleProcessClassNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduleState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduleStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scheduleText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="seconds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="successFailureFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="systemDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="toHours" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toMinutes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toSeconds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="viewFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weekDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weekEndTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weekStartTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "executeReport", propOrder = {
    "branchCd",
    "cancelQueued",
    "count",
    "dataStream",
    "destinationType",
    "displayOrder",
    "emailId",
    "endDate",
    "endOccurence",
    "endTime",
    "exceptionMessage",
    "frequency",
    "frequencyUsage",
    "futureSchedule",
    "headingDetails",
    "hoFlag",
    "hours",
    "installationCode",
    "minutes",
    "noOfRequests",
    "occurence",
    "optionEndDate",
    "orderBy",
    "preProgrammedFrequency",
    "processDescription",
    "processName",
    "query",
    "queryResults",
    "recurrence",
    "reportDataDetails",
    "reportFields",
    "reportId",
    "reportName",
    "reportParameters",
    "reportPath",
    "reportServerAlias",
    "reportServerUrl",
    "reportUrl",
    "reports",
    "requestId",
    "requestReportParameters",
    "requestState",
    "scheduleId",
    "scheduleProcessClassNo",
    "scheduleState",
    "scheduleStatus",
    "scheduleText",
    "seconds",
    "startDate",
    "startTime",
    "successFailureFlag",
    "systemDate",
    "toHours",
    "toMinutes",
    "toSeconds",
    "userId",
    "viewFrom",
    "weekDay",
    "weekEndTime",
    "weekStartTime"
})
public class ExecuteReport
    extends BaseResponseVO
{

    protected String branchCd;
    protected String cancelQueued;
    protected String count;
    protected String dataStream;
    protected String destinationType;
    protected String displayOrder;
    protected String emailId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String endOccurence;
    protected String endTime;
    protected String exceptionMessage;
    protected String frequency;
    protected String frequencyUsage;
    protected String futureSchedule;
    @XmlElement(nillable = true)
    protected List<ReportParameter> headingDetails;
    protected String hoFlag;
    protected String hours;
    protected String installationCode;
    protected String minutes;
    protected String noOfRequests;
    protected String occurence;
    protected String optionEndDate;
    protected String orderBy;
    protected String preProgrammedFrequency;
    protected String processDescription;
    protected String processName;
    protected String query;
    @XmlElement(nillable = true)
    protected List<ReportParameter> queryResults;
    protected String recurrence;
    @XmlElement(nillable = true)
    protected List<ReportParameter> reportDataDetails;
    protected String reportFields;
    protected String reportId;
    protected String reportName;
    @XmlElement(nillable = true)
    protected List<ReportParameter> reportParameters;
    protected String reportPath;
    protected String reportServerAlias;
    protected String reportServerUrl;
    protected String reportUrl;
    @XmlElement(nillable = true)
    protected List<Report> reports;
    protected String requestId;
    @XmlElement(nillable = true)
    protected List<ReportParameter> requestReportParameters;
    protected String requestState;
    protected String scheduleId;
    protected String scheduleProcessClassNo;
    protected String scheduleState;
    protected String scheduleStatus;
    protected String scheduleText;
    protected String seconds;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    protected String startTime;
    protected String successFailureFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar systemDate;
    protected String toHours;
    protected String toMinutes;
    protected String toSeconds;
    protected String userId;
    protected String viewFrom;
    protected String weekDay;
    protected String weekEndTime;
    protected String weekStartTime;

    /**
     * Gets the value of the branchCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchCd() {
        return branchCd;
    }

    /**
     * Sets the value of the branchCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchCd(String value) {
        this.branchCd = value;
    }

    /**
     * Gets the value of the cancelQueued property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelQueued() {
        return cancelQueued;
    }

    /**
     * Sets the value of the cancelQueued property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelQueued(String value) {
        this.cancelQueued = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCount(String value) {
        this.count = value;
    }

    /**
     * Gets the value of the dataStream property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataStream() {
        return dataStream;
    }

    /**
     * Sets the value of the dataStream property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataStream(String value) {
        this.dataStream = value;
    }

    /**
     * Gets the value of the destinationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationType() {
        return destinationType;
    }

    /**
     * Sets the value of the destinationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationType(String value) {
        this.destinationType = value;
    }

    /**
     * Gets the value of the displayOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Sets the value of the displayOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayOrder(String value) {
        this.displayOrder = value;
    }

    /**
     * Gets the value of the emailId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets the value of the emailId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailId(String value) {
        this.emailId = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the endOccurence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndOccurence() {
        return endOccurence;
    }

    /**
     * Sets the value of the endOccurence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndOccurence(String value) {
        this.endOccurence = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(String value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the exceptionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * Sets the value of the exceptionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptionMessage(String value) {
        this.exceptionMessage = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrequency(String value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the frequencyUsage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrequencyUsage() {
        return frequencyUsage;
    }

    /**
     * Sets the value of the frequencyUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrequencyUsage(String value) {
        this.frequencyUsage = value;
    }

    /**
     * Gets the value of the futureSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFutureSchedule() {
        return futureSchedule;
    }

    /**
     * Sets the value of the futureSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFutureSchedule(String value) {
        this.futureSchedule = value;
    }

    /**
     * Gets the value of the headingDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the headingDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeadingDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getHeadingDetails() {
        if (headingDetails == null) {
            headingDetails = new ArrayList<ReportParameter>();
        }
        return this.headingDetails;
    }

    /**
     * Gets the value of the hoFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoFlag() {
        return hoFlag;
    }

    /**
     * Sets the value of the hoFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoFlag(String value) {
        this.hoFlag = value;
    }

    /**
     * Gets the value of the hours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHours() {
        return hours;
    }

    /**
     * Sets the value of the hours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHours(String value) {
        this.hours = value;
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
     * Gets the value of the minutes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinutes() {
        return minutes;
    }

    /**
     * Sets the value of the minutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinutes(String value) {
        this.minutes = value;
    }

    /**
     * Gets the value of the noOfRequests property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoOfRequests() {
        return noOfRequests;
    }

    /**
     * Sets the value of the noOfRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoOfRequests(String value) {
        this.noOfRequests = value;
    }

    /**
     * Gets the value of the occurence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccurence() {
        return occurence;
    }

    /**
     * Sets the value of the occurence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccurence(String value) {
        this.occurence = value;
    }

    /**
     * Gets the value of the optionEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionEndDate() {
        return optionEndDate;
    }

    /**
     * Sets the value of the optionEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionEndDate(String value) {
        this.optionEndDate = value;
    }

    /**
     * Gets the value of the orderBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the value of the orderBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * Gets the value of the preProgrammedFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreProgrammedFrequency() {
        return preProgrammedFrequency;
    }

    /**
     * Sets the value of the preProgrammedFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreProgrammedFrequency(String value) {
        this.preProgrammedFrequency = value;
    }

    /**
     * Gets the value of the processDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessDescription() {
        return processDescription;
    }

    /**
     * Sets the value of the processDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessDescription(String value) {
        this.processDescription = value;
    }

    /**
     * Gets the value of the processName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * Sets the value of the processName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessName(String value) {
        this.processName = value;
    }

    /**
     * Gets the value of the query property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the value of the query property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuery(String value) {
        this.query = value;
    }

    /**
     * Gets the value of the queryResults property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the queryResults property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQueryResults().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getQueryResults() {
        if (queryResults == null) {
            queryResults = new ArrayList<ReportParameter>();
        }
        return this.queryResults;
    }

    /**
     * Gets the value of the recurrence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurrence() {
        return recurrence;
    }

    /**
     * Sets the value of the recurrence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurrence(String value) {
        this.recurrence = value;
    }

    /**
     * Gets the value of the reportDataDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportDataDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportDataDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getReportDataDetails() {
        if (reportDataDetails == null) {
            reportDataDetails = new ArrayList<ReportParameter>();
        }
        return this.reportDataDetails;
    }

    /**
     * Gets the value of the reportFields property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportFields() {
        return reportFields;
    }

    /**
     * Sets the value of the reportFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportFields(String value) {
        this.reportFields = value;
    }

    /**
     * Gets the value of the reportId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * Sets the value of the reportId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportId(String value) {
        this.reportId = value;
    }

    /**
     * Gets the value of the reportName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * Sets the value of the reportName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportName(String value) {
        this.reportName = value;
    }

    /**
     * Gets the value of the reportParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getReportParameters() {
        if (reportParameters == null) {
            reportParameters = new ArrayList<ReportParameter>();
        }
        return this.reportParameters;
    }

    /**
     * Gets the value of the reportPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportPath() {
        return reportPath;
    }

    /**
     * Sets the value of the reportPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportPath(String value) {
        this.reportPath = value;
    }

    /**
     * Gets the value of the reportServerAlias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportServerAlias() {
        return reportServerAlias;
    }

    /**
     * Sets the value of the reportServerAlias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportServerAlias(String value) {
        this.reportServerAlias = value;
    }

    /**
     * Gets the value of the reportServerUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportServerUrl() {
        return reportServerUrl;
    }

    /**
     * Sets the value of the reportServerUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportServerUrl(String value) {
        this.reportServerUrl = value;
    }

    /**
     * Gets the value of the reportUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportUrl() {
        return reportUrl;
    }

    /**
     * Sets the value of the reportUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportUrl(String value) {
        this.reportUrl = value;
    }

    /**
     * Gets the value of the reports property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reports property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReports().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Report }
     * 
     * 
     */
    public List<Report> getReports() {
        if (reports == null) {
            reports = new ArrayList<Report>();
        }
        return this.reports;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the requestReportParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestReportParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestReportParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getRequestReportParameters() {
        if (requestReportParameters == null) {
            requestReportParameters = new ArrayList<ReportParameter>();
        }
        return this.requestReportParameters;
    }

    /**
     * Gets the value of the requestState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestState() {
        return requestState;
    }

    /**
     * Sets the value of the requestState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestState(String value) {
        this.requestState = value;
    }

    /**
     * Gets the value of the scheduleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * Sets the value of the scheduleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleId(String value) {
        this.scheduleId = value;
    }

    /**
     * Gets the value of the scheduleProcessClassNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleProcessClassNo() {
        return scheduleProcessClassNo;
    }

    /**
     * Sets the value of the scheduleProcessClassNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleProcessClassNo(String value) {
        this.scheduleProcessClassNo = value;
    }

    /**
     * Gets the value of the scheduleState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleState() {
        return scheduleState;
    }

    /**
     * Sets the value of the scheduleState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleState(String value) {
        this.scheduleState = value;
    }

    /**
     * Gets the value of the scheduleStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleStatus() {
        return scheduleStatus;
    }

    /**
     * Sets the value of the scheduleStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleStatus(String value) {
        this.scheduleStatus = value;
    }

    /**
     * Gets the value of the scheduleText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduleText() {
        return scheduleText;
    }

    /**
     * Sets the value of the scheduleText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduleText(String value) {
        this.scheduleText = value;
    }

    /**
     * Gets the value of the seconds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeconds() {
        return seconds;
    }

    /**
     * Sets the value of the seconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeconds(String value) {
        this.seconds = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(String value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the successFailureFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccessFailureFlag() {
        return successFailureFlag;
    }

    /**
     * Sets the value of the successFailureFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccessFailureFlag(String value) {
        this.successFailureFlag = value;
    }

    /**
     * Gets the value of the systemDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSystemDate() {
        return systemDate;
    }

    /**
     * Sets the value of the systemDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSystemDate(XMLGregorianCalendar value) {
        this.systemDate = value;
    }

    /**
     * Gets the value of the toHours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToHours() {
        return toHours;
    }

    /**
     * Sets the value of the toHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToHours(String value) {
        this.toHours = value;
    }

    /**
     * Gets the value of the toMinutes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToMinutes() {
        return toMinutes;
    }

    /**
     * Sets the value of the toMinutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToMinutes(String value) {
        this.toMinutes = value;
    }

    /**
     * Gets the value of the toSeconds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToSeconds() {
        return toSeconds;
    }

    /**
     * Sets the value of the toSeconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToSeconds(String value) {
        this.toSeconds = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the viewFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getViewFrom() {
        return viewFrom;
    }

    /**
     * Sets the value of the viewFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setViewFrom(String value) {
        this.viewFrom = value;
    }

    /**
     * Gets the value of the weekDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekDay() {
        return weekDay;
    }

    /**
     * Sets the value of the weekDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekDay(String value) {
        this.weekDay = value;
    }

    /**
     * Gets the value of the weekEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekEndTime() {
        return weekEndTime;
    }

    /**
     * Sets the value of the weekEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekEndTime(String value) {
        this.weekEndTime = value;
    }

    /**
     * Gets the value of the weekStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekStartTime() {
        return weekStartTime;
    }

    /**
     * Sets the value of the weekStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekStartTime(String value) {
        this.weekStartTime = value;
    }

}
