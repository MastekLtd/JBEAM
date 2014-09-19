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

package com.stgmastek.monitor.comm.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updateBatchLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateBatchLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestBatchLog" type="{http://services.server.comm.monitor.stgmastek.com/}mReqBatchLog" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateBatchLog", propOrder = {
    "requestBatchLog"
})
public class UpdateBatchLog {

    protected MReqBatchLog requestBatchLog;

    /**
     * Gets the value of the requestBatchLog property.
     * 
     * @return
     *     possible object is
     *     {@link MReqBatchLog }
     *     
     */
    public MReqBatchLog getRequestBatchLog() {
        return requestBatchLog;
    }

    /**
     * Sets the value of the requestBatchLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link MReqBatchLog }
     *     
     */
    public void setRequestBatchLog(MReqBatchLog value) {
        this.requestBatchLog = value;
    }

}
