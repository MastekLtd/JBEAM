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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for processRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="processRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExecuteReport" type="{http://services.server.ws.monitor.stgmastek.com/}executeReport" minOccurs="0"/>
 *         &lt;element name="ReqInstructionLog" type="{http://services.server.ws.monitor.stgmastek.com/}reqInstructionLog" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "processRequest", propOrder = {
    "executeReport",
    "reqInstructionLog"
})
public class ProcessRequest {

    @XmlElement(name = "ExecuteReport")
    protected ExecuteReport executeReport;
    @XmlElement(name = "ReqInstructionLog")
    protected ReqInstructionLog reqInstructionLog;

    /**
     * Gets the value of the executeReport property.
     * 
     * @return
     *     possible object is
     *     {@link ExecuteReport }
     *     
     */
    public ExecuteReport getExecuteReport() {
        return executeReport;
    }

    /**
     * Sets the value of the executeReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecuteReport }
     *     
     */
    public void setExecuteReport(ExecuteReport value) {
        this.executeReport = value;
    }

    /**
     * Gets the value of the reqInstructionLog property.
     * 
     * @return
     *     possible object is
     *     {@link ReqInstructionLog }
     *     
     */
    public ReqInstructionLog getReqInstructionLog() {
        return reqInstructionLog;
    }

    /**
     * Sets the value of the reqInstructionLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReqInstructionLog }
     *     
     */
    public void setReqInstructionLog(ReqInstructionLog value) {
        this.reqInstructionLog = value;
    }

}
