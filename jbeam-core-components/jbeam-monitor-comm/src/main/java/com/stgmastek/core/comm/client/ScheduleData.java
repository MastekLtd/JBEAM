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

package com.stgmastek.core.comm.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scheduleData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scheduleData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="batchName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endDt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="endOccur" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="endReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="endTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="entryDt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="fixedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="freqType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="futureSchedulingOnly" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="installationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keepAlive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modifyDt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="modifyId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="occurCounter" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="onWeekDay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="processClassNm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recur" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reqStat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="schId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="schStat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="skipFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="startDt" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="weekdayCheckFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scheduleData", propOrder = {
    "batchName",
    "emailIds",
    "endDt",
    "endOccur",
    "endReason",
    "endTime",
    "entryDt",
    "fixedDate",
    "freqType",
    "futureSchedulingOnly",
    "installationCode",
    "keepAlive",
    "modifyDt",
    "modifyId",
    "occurCounter",
    "onWeekDay",
    "processClassNm",
    "recur",
    "reqStat",
    "schId",
    "schStat",
    "skipFlag",
    "startDt",
    "startTime",
    "userId",
    "weekdayCheckFlag"
})
public class ScheduleData {

    protected String batchName;
    protected String emailIds;
    protected Long endDt;
    protected Integer endOccur;
    protected String endReason;
    protected Long endTime;
    protected Long entryDt;
    protected String fixedDate;
    protected String freqType;
    protected String futureSchedulingOnly;
    protected String installationCode;
    protected String keepAlive;
    protected Long modifyDt;
    protected String modifyId;
    protected Integer occurCounter;
    protected String onWeekDay;
    protected String processClassNm;
    protected Integer recur;
    protected String reqStat;
    protected Integer schId;
    protected String schStat;
    protected String skipFlag;
    protected Long startDt;
    protected Long startTime;
    protected String userId;
    protected String weekdayCheckFlag;

    /**
     * Gets the value of the batchName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * Sets the value of the batchName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchName(String value) {
        this.batchName = value;
    }

    /**
     * Gets the value of the emailIds property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailIds() {
        return emailIds;
    }

    /**
     * Sets the value of the emailIds property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailIds(String value) {
        this.emailIds = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndDt(Long value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the endOccur property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEndOccur() {
        return endOccur;
    }

    /**
     * Sets the value of the endOccur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEndOccur(Integer value) {
        this.endOccur = value;
    }

    /**
     * Gets the value of the endReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndReason() {
        return endReason;
    }

    /**
     * Sets the value of the endReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndReason(String value) {
        this.endReason = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndTime(Long value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the entryDt property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEntryDt() {
        return entryDt;
    }

    /**
     * Sets the value of the entryDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEntryDt(Long value) {
        this.entryDt = value;
    }

    /**
     * Gets the value of the fixedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFixedDate() {
        return fixedDate;
    }

    /**
     * Sets the value of the fixedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFixedDate(String value) {
        this.fixedDate = value;
    }

    /**
     * Gets the value of the freqType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreqType() {
        return freqType;
    }

    /**
     * Sets the value of the freqType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreqType(String value) {
        this.freqType = value;
    }

    /**
     * Gets the value of the futureSchedulingOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFutureSchedulingOnly() {
        return futureSchedulingOnly;
    }

    /**
     * Sets the value of the futureSchedulingOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFutureSchedulingOnly(String value) {
        this.futureSchedulingOnly = value;
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
     * Gets the value of the keepAlive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeepAlive() {
        return keepAlive;
    }

    /**
     * Sets the value of the keepAlive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeepAlive(String value) {
        this.keepAlive = value;
    }

    /**
     * Gets the value of the modifyDt property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getModifyDt() {
        return modifyDt;
    }

    /**
     * Sets the value of the modifyDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setModifyDt(Long value) {
        this.modifyDt = value;
    }

    /**
     * Gets the value of the modifyId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * Sets the value of the modifyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyId(String value) {
        this.modifyId = value;
    }

    /**
     * Gets the value of the occurCounter property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOccurCounter() {
        return occurCounter;
    }

    /**
     * Sets the value of the occurCounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOccurCounter(Integer value) {
        this.occurCounter = value;
    }

    /**
     * Gets the value of the onWeekDay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnWeekDay() {
        return onWeekDay;
    }

    /**
     * Sets the value of the onWeekDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnWeekDay(String value) {
        this.onWeekDay = value;
    }

    /**
     * Gets the value of the processClassNm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessClassNm() {
        return processClassNm;
    }

    /**
     * Sets the value of the processClassNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessClassNm(String value) {
        this.processClassNm = value;
    }

    /**
     * Gets the value of the recur property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRecur() {
        return recur;
    }

    /**
     * Sets the value of the recur property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRecur(Integer value) {
        this.recur = value;
    }

    /**
     * Gets the value of the reqStat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqStat() {
        return reqStat;
    }

    /**
     * Sets the value of the reqStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqStat(String value) {
        this.reqStat = value;
    }

    /**
     * Gets the value of the schId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSchId() {
        return schId;
    }

    /**
     * Sets the value of the schId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSchId(Integer value) {
        this.schId = value;
    }

    /**
     * Gets the value of the schStat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchStat() {
        return schStat;
    }

    /**
     * Sets the value of the schStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchStat(String value) {
        this.schStat = value;
    }

    /**
     * Gets the value of the skipFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkipFlag() {
        return skipFlag;
    }

    /**
     * Sets the value of the skipFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkipFlag(String value) {
        this.skipFlag = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartDt(Long value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartTime(Long value) {
        this.startTime = value;
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
     * Gets the value of the weekdayCheckFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekdayCheckFlag() {
        return weekdayCheckFlag;
    }

    /**
     * Sets the value of the weekdayCheckFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekdayCheckFlag(String value) {
        this.weekdayCheckFlag = value;
    }

}
