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

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stgmastek.birt.report.beans package. 
 * <p>An ReportBeanFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ReportBeanFactory {


    /**
     * Create a new ReportBeanFactory that can be used to create new instances of schema derived classes for package: com.stgmastek.birt.report.beans
     * 
     */
    public ReportBeanFactory() {
    }

    /**
     * Create an instance of {@link OutputFormats }
     * 
     */
    public static OutputFormats createOutputFormats(OutputFormat... formats) {
        OutputFormats opFormats = new OutputFormats();
        for (OutputFormat format : formats) {
            opFormats.getOutputFormat().add(format);
        }
        return opFormats;
    }

    /**
     * Create an instance of {@link ReportParameter }
     * 
     */
    public static ReportParameter createReportParameter() {
        return new ReportParameter();
    }

    /**
     * Create an instance of {@link ReportParameter }
     * 
     */
    public static ReportParameter createReportParameter(String key, String val, ReportParameterType type) {
        ReportParameter param = new ReportParameter();
        param.setName(key);
        param.setValue(val);
        param.setReportParameterType(type);
        return param;
    }
    
    /**
     * Create an instance of {@link ReportManipulator }
     * 
     */
    public static ReportManipulator createReportManipulator() {
        return new ReportManipulator();
    }

    /**
     * Create an instance of {@link ReportParameters }
     * 
     */
    public static ReportParameters createReportParameters() {
        return new ReportParameters();
    }

    /**
     * Create an instance of {@link ReportManipulators }
     * 
     */
    public static ReportManipulators createReportManipulators() {
        return new ReportManipulators();
    }

    /**
     * Create an instance of {@link Report }
     * 
     */
    public static Report createReport() {
        return new Report();
    }

}
