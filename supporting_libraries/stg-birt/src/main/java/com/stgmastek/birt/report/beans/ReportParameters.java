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
 *         &lt;element ref="{}ReportParameter" maxOccurs="unbounded"/>
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
    "reportParameter"
})
@XmlRootElement(name = "ReportParameters")
public class ReportParameters
    implements Cloneable, CopyTo
{

    @XmlElement(name = "ReportParameter", required = true)
    protected List<ReportParameter> reportParameter;

    /**
     * Gets the value of the reportParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportParameter }
     * 
     * 
     */
    public List<ReportParameter> getReportParameter() {
        if (reportParameter == null) {
            reportParameter = new ArrayList<ReportParameter>();
        }
        return this.reportParameter;
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
        if (draftCopy instanceof ReportParameters) {
            final ReportParameters copy = ((ReportParameters) draftCopy);
            if ((this.reportParameter!= null)&&(!this.reportParameter.isEmpty())) {
                List<ReportParameter> sourceReportParameter;
                sourceReportParameter = this.getReportParameter();
                @SuppressWarnings("unchecked")
                List<ReportParameter> copyReportParameter = ((List<ReportParameter> ) strategy.copy(LocatorUtils.property(locator, "reportParameter", sourceReportParameter), sourceReportParameter));
                copy.reportParameter = null;
                List<ReportParameter> uniqueReportParameterl = copy.getReportParameter();
                uniqueReportParameterl.addAll(copyReportParameter);
            } else {
                copy.reportParameter = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new ReportParameters();
    }

    /**
     * Sets the value of the reportParameter property.
     * 
     * @param reportParameter
     *     allowed object is
     *     {@link ReportParameter }
     *     
     */
    public void setReportParameter(List<ReportParameter> reportParameter) {
        this.reportParameter = reportParameter;
    }

}
