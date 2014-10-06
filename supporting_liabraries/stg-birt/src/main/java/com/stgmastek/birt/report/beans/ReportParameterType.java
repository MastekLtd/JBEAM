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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportParameterType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReportParameterType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="Date_SQL"/>
 *     &lt;enumeration value="Date_Util"/>
 *     &lt;enumeration value="Integer"/>
 *     &lt;enumeration value="Double"/>
 *     &lt;enumeration value="Long"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReportParameterType")
@XmlEnum
public enum ReportParameterType {

    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("Date_SQL")
    DATE_SQL("Date_SQL"),
    @XmlEnumValue("Date_Util")
    DATE_UTIL("Date_Util"),
    @XmlEnumValue("Integer")
    INTEGER("Integer"),
    @XmlEnumValue("Double")
    DOUBLE("Double"),
    @XmlEnumValue("Long")
    LONG("Long");
    private final String value;

    ReportParameterType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReportParameterType fromValue(String v) {
        for (ReportParameterType c: ReportParameterType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
