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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cReqProcessRequestScheduleVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cReqProcessRequestScheduleVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.comm.core.stgmastek.com/}cBaseRequestVO">
 *       &lt;sequence>
 *         &lt;element name="processRequestScheduleList" type="{http://services.server.comm.core.stgmastek.com/}scheduleData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="scheduleData" type="{http://services.server.comm.core.stgmastek.com/}scheduleData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cReqProcessRequestScheduleVO", propOrder = {
    "processRequestScheduleList",
    "scheduleData"
})
public class CReqProcessRequestScheduleVO
    extends CBaseRequestVO
{

    @XmlElement(nillable = true)
    protected List<ScheduleData> processRequestScheduleList;
    protected ScheduleData scheduleData;

    /**
     * Gets the value of the processRequestScheduleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processRequestScheduleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessRequestScheduleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ScheduleData }
     * 
     * 
     */
    public List<ScheduleData> getProcessRequestScheduleList() {
        if (processRequestScheduleList == null) {
            processRequestScheduleList = new ArrayList<ScheduleData>();
        }
        return this.processRequestScheduleList;
    }

    /**
     * Gets the value of the scheduleData property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleData }
     *     
     */
    public ScheduleData getScheduleData() {
        return scheduleData;
    }

    /**
     * Sets the value of the scheduleData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleData }
     *     
     */
    public void setScheduleData(ScheduleData value) {
        this.scheduleData = value;
    }

}
