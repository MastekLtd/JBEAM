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


package com.stgmastek.birt.report.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.CopyStrategy;
import org.jvnet.jaxb2_commons.lang.CopyTo;
import org.jvnet.jaxb2_commons.lang.JAXBCopyStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManipulateFileIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "manipulateFileIdentifier"
})
@XmlRootElement(name = "ReportManipulator")
public class ReportManipulator
    implements Cloneable, CopyTo
{

    @XmlElement(name = "ManipulateFileIdentifier", required = true)
    protected String manipulateFileIdentifier;

    /**
     * Gets the value of the manipulateFileIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManipulateFileIdentifier() {
        return manipulateFileIdentifier;
    }

    /**
     * Sets the value of the manipulateFileIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManipulateFileIdentifier(String value) {
        this.manipulateFileIdentifier = value;
    }

    public Object clone() {
        return copyTo(createNewInstance());
    }

    public Object copyTo(Object target) {
        final CopyStrategy strategy = JAXBCopyStrategy.INSTANCE;
        return copyTo(null, target, strategy);
    }

    public Object copyTo(ObjectLocator locator, Object target, CopyStrategy strategy) {
        final Object draftCopy = ((target == null)?createNewInstance():target);
        if (draftCopy instanceof ReportManipulator) {
            final ReportManipulator copy = ((ReportManipulator) draftCopy);
            if (this.manipulateFileIdentifier!= null) {
                String sourceManipulateFileIdentifier;
                sourceManipulateFileIdentifier = this.getManipulateFileIdentifier();
                String copyManipulateFileIdentifier = ((String) strategy.copy(LocatorUtils.property(locator, "manipulateFileIdentifier", sourceManipulateFileIdentifier), sourceManipulateFileIdentifier));
                copy.setManipulateFileIdentifier(copyManipulateFileIdentifier);
            } else {
                copy.manipulateFileIdentifier = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ReportManipulator();
    }

}
