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
 *         &lt;element name="OutputFormat" type="{}OutputFormat" maxOccurs="unbounded"/>
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
    "outputFormat"
})
@XmlRootElement(name = "OutputFormats")
public class OutputFormats
    implements Cloneable, CopyTo
{

    @XmlElement(name = "OutputFormat", required = true)
    protected List<OutputFormat> outputFormat;

    /**
     * Gets the value of the outputFormat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputFormat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputFormat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputFormat }
     * 
     * 
     */
    public List<OutputFormat> getOutputFormat() {
        if (outputFormat == null) {
            outputFormat = new ArrayList<OutputFormat>();
        }
        return this.outputFormat;
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
        if (draftCopy instanceof OutputFormats) {
            final OutputFormats copy = ((OutputFormats) draftCopy);
            if ((this.outputFormat!= null)&&(!this.outputFormat.isEmpty())) {
                List<OutputFormat> sourceOutputFormat;
                sourceOutputFormat = this.getOutputFormat();
                @SuppressWarnings("unchecked")
                List<OutputFormat> copyOutputFormat = ((List<OutputFormat> ) strategy.copy(LocatorUtils.property(locator, "outputFormat", sourceOutputFormat), sourceOutputFormat));
                copy.outputFormat = null;
                List<OutputFormat> uniqueOutputFormatl = copy.getOutputFormat();
                uniqueOutputFormatl.addAll(copyOutputFormat);
            } else {
                copy.outputFormat = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new OutputFormats();
    }

    /**
     * Sets the value of the outputFormat property.
     * 
     * @param outputFormat
     *     allowed object is
     *     {@link OutputFormat }
     *     
     */
    public void setOutputFormat(List<OutputFormat> outputFormat) {
        this.outputFormat = outputFormat;
    }

}
