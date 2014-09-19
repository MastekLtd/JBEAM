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
 * <p>Java class for batchInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="batchInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="batchEndReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="batchNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="batchRevNo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="batchTimeDiff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entityList" type="{http://services.server.ws.monitor.stgmastek.com/}installationEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="execEndTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="execStartTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="noOfListners" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="noOfObjects" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="noOfObjectsExecuted" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="noOfObjectsFailed" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="progressLevelDataList" type="{http://services.server.ws.monitor.stgmastek.com/}progressLevelData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "batchInfo", propOrder = {
    "batchEndReason",
    "batchNo",
    "batchRevNo",
    "batchTimeDiff",
    "entityList",
    "execEndTime",
    "execStartTime",
    "noOfListners",
    "noOfObjects",
    "noOfObjectsExecuted",
    "noOfObjectsFailed",
    "progressLevelDataList"
})
public class BatchInfo {

    protected String batchEndReason;
    protected Integer batchNo;
    protected Integer batchRevNo;
    protected String batchTimeDiff;
    @XmlElement(nillable = true)
    protected List<InstallationEntity> entityList;
    protected Long execEndTime;
    protected Long execStartTime;
    protected Integer noOfListners;
    protected Integer noOfObjects;
    protected Integer noOfObjectsExecuted;
    protected Integer noOfObjectsFailed;
    @XmlElement(nillable = true)
    protected List<ProgressLevelData> progressLevelDataList;

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
     * Gets the value of the batchTimeDiff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchTimeDiff() {
        return batchTimeDiff;
    }

    /**
     * Sets the value of the batchTimeDiff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchTimeDiff(String value) {
        this.batchTimeDiff = value;
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
     * Gets the value of the execEndTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExecEndTime() {
        return execEndTime;
    }

    /**
     * Sets the value of the execEndTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExecEndTime(Long value) {
        this.execEndTime = value;
    }

    /**
     * Gets the value of the execStartTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getExecStartTime() {
        return execStartTime;
    }

    /**
     * Sets the value of the execStartTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setExecStartTime(Long value) {
        this.execStartTime = value;
    }

    /**
     * Gets the value of the noOfListners property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfListners() {
        return noOfListners;
    }

    /**
     * Sets the value of the noOfListners property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfListners(Integer value) {
        this.noOfListners = value;
    }

    /**
     * Gets the value of the noOfObjects property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfObjects() {
        return noOfObjects;
    }

    /**
     * Sets the value of the noOfObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfObjects(Integer value) {
        this.noOfObjects = value;
    }

    /**
     * Gets the value of the noOfObjectsExecuted property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfObjectsExecuted() {
        return noOfObjectsExecuted;
    }

    /**
     * Sets the value of the noOfObjectsExecuted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfObjectsExecuted(Integer value) {
        this.noOfObjectsExecuted = value;
    }

    /**
     * Gets the value of the noOfObjectsFailed property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfObjectsFailed() {
        return noOfObjectsFailed;
    }

    /**
     * Sets the value of the noOfObjectsFailed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfObjectsFailed(Integer value) {
        this.noOfObjectsFailed = value;
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

}
