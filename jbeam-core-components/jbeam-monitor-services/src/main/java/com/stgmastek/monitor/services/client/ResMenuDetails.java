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
 * <p>Java class for resMenuDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="resMenuDetails">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.server.ws.monitor.stgmastek.com/}baseResponseVO">
 *       &lt;sequence>
 *         &lt;element name="children" type="{http://services.server.ws.monitor.stgmastek.com/}menuData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="installationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menuData" type="{http://services.server.ws.monitor.stgmastek.com/}menuData" minOccurs="0"/>
 *         &lt;element name="menuList" type="{http://services.server.ws.monitor.stgmastek.com/}resMenuDetails" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parentFunctionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resMenuDetails", propOrder = {
    "children",
    "installationCode",
    "menuData",
    "menuList",
    "parentFunctionId"
})
public class ResMenuDetails
    extends BaseResponseVO
{

    @XmlElement(nillable = true)
    protected List<MenuData> children;
    protected String installationCode;
    protected MenuData menuData;
    @XmlElement(nillable = true)
    protected List<ResMenuDetails> menuList;
    protected String parentFunctionId;

    /**
     * Gets the value of the children property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the children property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildren().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuData }
     * 
     * 
     */
    public List<MenuData> getChildren() {
        if (children == null) {
            children = new ArrayList<MenuData>();
        }
        return this.children;
    }

    /**
     * Gets the value of the installationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstallationCode() {
        return installationCode;
    }

    /**
     * Sets the value of the installationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstallationCode(String value) {
        this.installationCode = value;
    }

    /**
     * Gets the value of the menuData property.
     * 
     * @return
     *     possible object is
     *     {@link MenuData }
     *     
     */
    public MenuData getMenuData() {
        return menuData;
    }

    /**
     * Sets the value of the menuData property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuData }
     *     
     */
    public void setMenuData(MenuData value) {
        this.menuData = value;
    }

    /**
     * Gets the value of the menuList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResMenuDetails }
     * 
     * 
     */
    public List<ResMenuDetails> getMenuList() {
        if (menuList == null) {
            menuList = new ArrayList<ResMenuDetails>();
        }
        return this.menuList;
    }

    /**
     * Gets the value of the parentFunctionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentFunctionId() {
        return parentFunctionId;
    }

    /**
     * Sets the value of the parentFunctionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentFunctionId(String value) {
        this.parentFunctionId = value;
    }

}
