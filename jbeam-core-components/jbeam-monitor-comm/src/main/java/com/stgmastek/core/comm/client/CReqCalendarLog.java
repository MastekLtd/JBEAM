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
 * <p>Java class for cReqCalendarLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cReqCalendarLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="calendarData" type="{http://services.server.comm.core.stgmastek.com/}calendarData" minOccurs="0"/>
 *         &lt;element name="calendarList" type="{http://services.server.comm.core.stgmastek.com/}calendarData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cReqCalendarLog", propOrder = {
    "calendarData",
    "calendarList"
})
public class CReqCalendarLog {

    protected CalendarData calendarData;
    @XmlElement(nillable = true)
    protected List<CalendarData> calendarList;

    /**
     * Gets the value of the calendarData property.
     * 
     * @return
     *     possible object is
     *     {@link CalendarData }
     *     
     */
    public CalendarData getCalendarData() {
        return calendarData;
    }

    /**
     * Sets the value of the calendarData property.
     * 
     * @param value
     *     allowed object is
     *     {@link CalendarData }
     *     
     */
    public void setCalendarData(CalendarData value) {
        this.calendarData = value;
    }

    /**
     * Gets the value of the calendarList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the calendarList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCalendarList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CalendarData }
     * 
     * 
     */
    public List<CalendarData> getCalendarList() {
        if (calendarList == null) {
            calendarList = new ArrayList<CalendarData>();
        }
        return this.calendarList;
    }

}
