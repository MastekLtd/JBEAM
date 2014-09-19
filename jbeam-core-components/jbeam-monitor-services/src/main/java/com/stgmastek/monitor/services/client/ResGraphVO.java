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
 * <p>Java class for resGraphVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resGraphVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="batchDetails" type="{http://services.server.ws.monitor.stgmastek.com/}batchDetails" minOccurs="0"/>
 *         &lt;element name="graphDataList" type="{http://services.server.ws.monitor.stgmastek.com/}objectExecutionGraphData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resGraphVO", propOrder = {
    "batchDetails",
    "graphDataList"
})
public class ResGraphVO
    extends BaseResponseVO
{

    protected BatchDetails batchDetails;
    @XmlElement(nillable = true)
    protected List<ObjectExecutionGraphData> graphDataList;

    /**
     * Gets the value of the batchDetails property.
     * 
     * @return
     *     possible object is
     *     {@link BatchDetails }
     *     
     */
    public BatchDetails getBatchDetails() {
        return batchDetails;
    }

    /**
     * Sets the value of the batchDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchDetails }
     *     
     */
    public void setBatchDetails(BatchDetails value) {
        this.batchDetails = value;
    }

    /**
     * Gets the value of the graphDataList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the graphDataList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGraphDataList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObjectExecutionGraphData }
     * 
     * 
     */
    public List<ObjectExecutionGraphData> getGraphDataList() {
        if (graphDataList == null) {
            graphDataList = new ArrayList<ObjectExecutionGraphData>();
        }
        return this.graphDataList;
    }

}
