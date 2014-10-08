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
 * <p>Java class for OutputFormat.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OutputFormat">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="html"/>
 *     &lt;enumeration value="pdf"/>
 *     &lt;enumeration value="ps"/>
 *     &lt;enumeration value="xls"/>
 *     &lt;enumeration value="doc"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OutputFormat")
@XmlEnum
public enum OutputFormat {

    @XmlEnumValue("html")
    HTML("html"),
    @XmlEnumValue("pdf")
    PDF("pdf"),
    @XmlEnumValue("ps")
    PS("ps"),
    @XmlEnumValue("xls")
    XLS("xls"),
    @XmlEnumValue("doc")
    DOC("doc");
    private final String value;

    OutputFormat(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OutputFormat fromValue(String v) {
        for (OutputFormat c: OutputFormat.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
