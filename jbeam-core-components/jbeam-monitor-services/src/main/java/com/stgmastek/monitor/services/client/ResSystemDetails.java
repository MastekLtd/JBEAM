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
 * <p>Java class for resSystemDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resSystemDetails">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="systemDetails" type="{http://services.server.ws.monitor.stgmastek.com/}systemDetails" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resSystemDetails", propOrder = {
    "systemDetails"
})
public class ResSystemDetails
    extends BaseResponseVO
{

    protected SystemDetails systemDetails;

    /**
     * Gets the value of the systemDetails property.
     * 
     * @return
     *     possible object is
     *     {@link SystemDetails }
     *     
     */
    public SystemDetails getSystemDetails() {
        return systemDetails;
    }

    /**
     * Sets the value of the systemDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemDetails }
     *     
     */
    public void setSystemDetails(SystemDetails value) {
        this.systemDetails = value;
    }

}
