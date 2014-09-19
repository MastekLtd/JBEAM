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
 * <p>Java class for resInstallationVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resInstallationVO">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="installation" type="{http://services.server.ws.monitor.stgmastek.com/}installation" minOccurs="0"/>
 *         &lt;element name="installationData" type="{http://services.server.ws.monitor.stgmastek.com/}installationData" minOccurs="0"/>
 *         &lt;element name="installationDataList" type="{http://services.server.ws.monitor.stgmastek.com/}installationData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="installationsList" type="{http://services.server.ws.monitor.stgmastek.com/}installation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInstallationVO", propOrder = {
    "installation",
    "installationData",
    "installationDataList",
    "installationsList"
})
public class ResInstallationVO
    extends BaseResponseVO
{

    protected Installation installation;
    protected InstallationData installationData;
    @XmlElement(nillable = true)
    protected List<InstallationData> installationDataList;
    @XmlElement(nillable = true)
    protected List<Installation> installationsList;

    /**
     * Gets the value of the installation property.
     * 
     * @return
     *     possible object is
     *     {@link Installation }
     *     
     */
    public Installation getInstallation() {
        return installation;
    }

    /**
     * Sets the value of the installation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Installation }
     *     
     */
    public void setInstallation(Installation value) {
        this.installation = value;
    }

    /**
     * Gets the value of the installationData property.
     * 
     * @return
     *     possible object is
     *     {@link InstallationData }
     *     
     */
    public InstallationData getInstallationData() {
        return installationData;
    }

    /**
     * Sets the value of the installationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstallationData }
     *     
     */
    public void setInstallationData(InstallationData value) {
        this.installationData = value;
    }

    /**
     * Gets the value of the installationDataList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installationDataList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstallationDataList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstallationData }
     * 
     * 
     */
    public List<InstallationData> getInstallationDataList() {
        if (installationDataList == null) {
            installationDataList = new ArrayList<InstallationData>();
        }
        return this.installationDataList;
    }

    /**
     * Gets the value of the installationsList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the installationsList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstallationsList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Installation }
     * 
     * 
     */
    public List<Installation> getInstallationsList() {
        if (installationsList == null) {
            installationsList = new ArrayList<Installation>();
        }
        return this.installationsList;
    }

}
