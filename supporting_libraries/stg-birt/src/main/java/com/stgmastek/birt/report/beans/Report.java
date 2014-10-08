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
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DesignFileIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GenerationMode" type="{}ReportGenerationMode"/>
 *         &lt;element name="FilePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}ReportParameters" minOccurs="0"/>
 *         &lt;element ref="{}ReportManipulators" minOccurs="0"/>
 *         &lt;element ref="{}OutputFormats"/>
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
    "id",
    "designFileIdentifier",
    "fileName",
    "generationMode",
    "filePath",
    "reportParameters",
    "reportManipulators",
    "outputFormats"
})
@XmlRootElement(name = "Report")
public class Report
    implements Cloneable, CopyTo
{

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "DesignFileIdentifier", required = true)
    protected String designFileIdentifier;
    @XmlElement(name = "FileName", required = true)
    protected String fileName;
    @XmlElement(name = "GenerationMode", required = true)
    protected ReportGenerationMode generationMode;
    @XmlElement(name = "FilePath", required = true)
    protected String filePath;
    @XmlElement(name = "ReportParameters")
    protected ReportParameters reportParameters;
    @XmlElement(name = "ReportManipulators")
    protected ReportManipulators reportManipulators;
    @XmlElement(name = "OutputFormats", required = true)
    protected OutputFormats outputFormats;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the designFileIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignFileIdentifier() {
        return designFileIdentifier;
    }

    /**
     * Sets the value of the designFileIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignFileIdentifier(String value) {
        this.designFileIdentifier = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the generationMode property.
     * 
     * @return
     *     possible object is
     *     {@link ReportGenerationMode }
     *     
     */
    public ReportGenerationMode getGenerationMode() {
        return generationMode;
    }

    /**
     * Sets the value of the generationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportGenerationMode }
     *     
     */
    public void setGenerationMode(ReportGenerationMode value) {
        this.generationMode = value;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the reportParameters property.
     * 
     * @return
     *     possible object is
     *     {@link ReportParameters }
     *     
     */
    public ReportParameters getReportParameters() {
        return reportParameters;
    }

    /**
     * Sets the value of the reportParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportParameters }
     *     
     */
    public void setReportParameters(ReportParameters value) {
        this.reportParameters = value;
    }

    /**
     * Gets the value of the reportManipulators property.
     * 
     * @return
     *     possible object is
     *     {@link ReportManipulators }
     *     
     */
    public ReportManipulators getReportManipulators() {
        return reportManipulators;
    }

    /**
     * Sets the value of the reportManipulators property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportManipulators }
     *     
     */
    public void setReportManipulators(ReportManipulators value) {
        this.reportManipulators = value;
    }

    /**
     * Gets the value of the outputFormats property.
     * 
     * @return
     *     possible object is
     *     {@link OutputFormats }
     *     
     */
    public OutputFormats getOutputFormats() {
        return outputFormats;
    }

    /**
     * Sets the value of the outputFormats property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputFormats }
     *     
     */
    public void setOutputFormats(OutputFormats value) {
        this.outputFormats = value;
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
        if (draftCopy instanceof Report) {
            final Report copy = ((Report) draftCopy);
            if (this.id!= null) {
                String sourceID;
                sourceID = this.getID();
                String copyID = ((String) strategy.copy(LocatorUtils.property(locator, "id", sourceID), sourceID));
                copy.setID(copyID);
            } else {
                copy.id = null;
            }
            if (this.designFileIdentifier!= null) {
                String sourceDesignFileIdentifier;
                sourceDesignFileIdentifier = this.getDesignFileIdentifier();
                String copyDesignFileIdentifier = ((String) strategy.copy(LocatorUtils.property(locator, "designFileIdentifier", sourceDesignFileIdentifier), sourceDesignFileIdentifier));
                copy.setDesignFileIdentifier(copyDesignFileIdentifier);
            } else {
                copy.designFileIdentifier = null;
            }
            if (this.fileName!= null) {
                String sourceFileName;
                sourceFileName = this.getFileName();
                String copyFileName = ((String) strategy.copy(LocatorUtils.property(locator, "fileName", sourceFileName), sourceFileName));
                copy.setFileName(copyFileName);
            } else {
                copy.fileName = null;
            }
            if (this.generationMode!= null) {
                ReportGenerationMode sourceGenerationMode;
                sourceGenerationMode = this.getGenerationMode();
                ReportGenerationMode copyGenerationMode = ((ReportGenerationMode) strategy.copy(LocatorUtils.property(locator, "generationMode", sourceGenerationMode), sourceGenerationMode));
                copy.setGenerationMode(copyGenerationMode);
            } else {
                copy.generationMode = null;
            }
            if (this.filePath!= null) {
                String sourceFilePath;
                sourceFilePath = this.getFilePath();
                String copyFilePath = ((String) strategy.copy(LocatorUtils.property(locator, "filePath", sourceFilePath), sourceFilePath));
                copy.setFilePath(copyFilePath);
            } else {
                copy.filePath = null;
            }
            if (this.reportParameters!= null) {
                ReportParameters sourceReportParameters;
                sourceReportParameters = this.getReportParameters();
                ReportParameters copyReportParameters = ((ReportParameters) strategy.copy(LocatorUtils.property(locator, "reportParameters", sourceReportParameters), sourceReportParameters));
                copy.setReportParameters(copyReportParameters);
            } else {
                copy.reportParameters = null;
            }
            if (this.reportManipulators!= null) {
                ReportManipulators sourceReportManipulators;
                sourceReportManipulators = this.getReportManipulators();
                ReportManipulators copyReportManipulators = ((ReportManipulators) strategy.copy(LocatorUtils.property(locator, "reportManipulators", sourceReportManipulators), sourceReportManipulators));
                copy.setReportManipulators(copyReportManipulators);
            } else {
                copy.reportManipulators = null;
            }
            if (this.outputFormats!= null) {
                OutputFormats sourceOutputFormats;
                sourceOutputFormats = this.getOutputFormats();
                OutputFormats copyOutputFormats = ((OutputFormats) strategy.copy(LocatorUtils.property(locator, "outputFormats", sourceOutputFormats), sourceOutputFormats));
                copy.setOutputFormats(copyOutputFormats);
            } else {
                copy.outputFormats = null;
            }
        }
        return draftCopy;
    }

    public Object createNewInstance() {
        return new Report();
    }

}
