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
 * <p>Java class for resInstructionVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resInstructionVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="instructionLog" type="{http://services.server.ws.monitor.stgmastek.com/}instructionLog" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInstructionVO", propOrder = {
    "instructionLog"
})
public class ResInstructionVO
    extends BaseResponseVO
{

    protected InstructionLog instructionLog;

    /**
     * Gets the value of the instructionLog property.
     * 
     * @return
     *     possible object is
     *     {@link InstructionLog }
     *     
     */
    public InstructionLog getInstructionLog() {
        return instructionLog;
    }

    /**
     * Sets the value of the instructionLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstructionLog }
     *     
     */
    public void setInstructionLog(InstructionLog value) {
        this.instructionLog = value;
    }

}
