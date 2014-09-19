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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for installationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="installationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="BRevNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="batchEndReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="batchEndTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="batchStartTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="entityList" type="{http://services.server.ws.monitor.stgmastek.com/}installationEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="instCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="progressLevelDataList" type="{http://services.server.ws.monitor.stgmastek.com/}progressLevelData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="timezoneId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timezoneOffset" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="timezoneShortName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalFailedObjects" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="totalObjects" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "installationData", propOrder = {
    "bNo",
    "bRevNo",
    "batchEndReason",
    "batchEndTime",
    "batchStartTime",
    "entityList",
    "instCode",
    "progressLevelDataList",
    "timezoneId",
    "timezoneOffset",
    "timezoneShortName",
    "totalFailedObjects",
    "totalObjects"
})
public class InstallationData {

    @XmlElement(name = "BNo")
    protected Integer bNo;
    @XmlElement(name = "BRevNo")
    protected Integer bRevNo;
    protected String batchEndReason;
    protected Long batchEndTime;
    protected Long batchStartTime;
    @XmlElement(nillable = true)
    protected List<InstallationEntity> entityList;
    protected String instCode;
    @XmlElement(nillable = true)
    protected List<ProgressLevelData> progressLevelDataList;
    protected String timezoneId;
    protected long timezoneOffset;
    protected String timezoneShortName;
    protected Integer totalFailedObjects;
    protected Integer totalObjects;

    /**
     * Gets the value of the bNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBNo() {
        return bNo;
    }

    /**
     * Sets the value of the bNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBNo(Integer value) {
        this.bNo = value;
    }

    /**
     * Gets the value of the bRevNo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBRevNo() {
        return bRevNo;
    }

    /**
     * Sets the value of the bRevNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBRevNo(Integer value) {
        this.bRevNo = value;
    }

    /**
     * Gets the value of the batchEndReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchEndReason() {
        return batchEndReason;
    }

    /**
     * Sets the value of the batchEndReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchEndReason(String value) {
        this.batchEndReason = value;
    }

    /**
     * Gets the value of the batchEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBatchEndTime() {
        return batchEndTime;
    }

    /**
     * Sets the value of the batchEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBatchEndTime(Long value) {
        this.batchEndTime = value;
    }

    /**
     * Gets the value of the batchStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBatchStartTime() {
        return batchStartTime;
    }

    /**
     * Sets the value of the batchStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBatchStartTime(Long value) {
        this.batchStartTime = value;
    }

    /**
     * Gets the value of the entityList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entityList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstallationEntity }
     * 
     * 
     */
    public List<InstallationEntity> getEntityList() {
        if (entityList == null) {
            entityList = new ArrayList<InstallationEntity>();
        }
        return this.entityList;
    }

    /**
     * Gets the value of the instCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstCode() {
        return instCode;
    }

    /**
     * Sets the value of the instCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstCode(String value) {
        this.instCode = value;
    }

    /**
     * Gets the value of the progressLevelDataList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the progressLevelDataList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProgressLevelDataList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProgressLevelData }
     * 
     * 
     */
    public List<ProgressLevelData> getProgressLevelDataList() {
        if (progressLevelDataList == null) {
            progressLevelDataList = new ArrayList<ProgressLevelData>();
        }
        return this.progressLevelDataList;
    }

    /**
     * Gets the value of the timezoneId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezoneId() {
        return timezoneId;
    }

    /**
     * Sets the value of the timezoneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezoneId(String value) {
        this.timezoneId = value;
    }

    /**
     * Gets the value of the timezoneOffset property.
     * 
     */
    public long getTimezoneOffset() {
        return timezoneOffset;
    }

    /**
     * Sets the value of the timezoneOffset property.
     * 
     */
    public void setTimezoneOffset(long value) {
        this.timezoneOffset = value;
    }

    /**
     * Gets the value of the timezoneShortName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezoneShortName() {
        return timezoneShortName;
    }

    /**
     * Sets the value of the timezoneShortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezoneShortName(String value) {
        this.timezoneShortName = value;
    }

    /**
     * Gets the value of the totalFailedObjects property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalFailedObjects() {
        return totalFailedObjects;
    }

    /**
     * Sets the value of the totalFailedObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalFailedObjects(Integer value) {
        this.totalFailedObjects = value;
    }

    /**
     * Gets the value of the totalObjects property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalObjects() {
        return totalObjects;
    }

    /**
     * Sets the value of the totalObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalObjects(Integer value) {
        this.totalObjects = value;
    }

}
