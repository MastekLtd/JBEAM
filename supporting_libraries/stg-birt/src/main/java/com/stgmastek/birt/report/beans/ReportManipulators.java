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

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{}ReportManipulator" maxOccurs="unbounded"/>
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
    "reportManipulator"
})
@XmlRootElement(name = "ReportManipulators")
public class ReportManipulators
    implements Cloneable, CopyTo
{

    @XmlElement(name = "ReportManipulator", required = true)
    protected List<ReportManipulator> reportManipulator;

    /**
     * Gets the value of the reportManipulator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportManipulator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportManipulator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportManipulator }
     * 
     * 
     */
    public List<ReportManipulator> getReportManipulator() {
        if (reportManipulator == null) {
            reportManipulator = new ArrayList<ReportManipulator>();
        }
        return this.reportManipulator;
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
        if (draftCopy instanceof ReportManipulators) {
            final ReportManipulators copy = ((ReportManipulators) draftCopy);
            if ((this.reportManipulator!= null)&&(!this.reportManipulator.isEmpty())) {
                List<ReportManipulator> sourceReportManipulator;
                sourceReportManipulator = this.getReportManipulator();
                @SuppressWarnings("unchecked")
                List<ReportManipulator> copyReportManipulator = ((List<ReportManipulator> ) strategy.copy(LocatorUtils.property(locator, "reportManipulator", sourceReportManipulator), sourceReportManipulator));
                copy.reportManipulator = null;
                List<ReportManipulator> uniqueReportManipulatorl = copy.getReportManipulator();
                uniqueReportManipulatorl.addAll(copyReportManipulator);
            } else {
                copy.reportManipulator = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ReportManipulators();
    }

    /**
     * Sets the value of the reportManipulator property.
     * 
     * @param reportManipulator
     *     allowed object is
     *     {@link ReportManipulator }
     *     
     */
    public void setReportManipulator(List<ReportManipulator> reportManipulator) {
        this.reportManipulator = reportManipulator;
    }

}
