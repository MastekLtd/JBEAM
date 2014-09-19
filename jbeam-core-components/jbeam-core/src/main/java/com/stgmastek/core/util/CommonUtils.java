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
package com.stgmastek.core.util;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.FastDateFormat;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Element;

public class CommonUtils {

	private static FastDateFormat ISO_DATETIME_TIME_FORMAT = FastDateFormat
			.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS");

	public static String replace(String oldStr, String newStr, String inString) {
		int start = inString.indexOf(oldStr);
		if (start == -1) {
			return inString;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(inString.substring(0, start));
		sb.append(newStr);
		sb.append(inString.substring(start + oldStr.length()));
		return sb.toString();
	}

	/**
	 * A new method, that contains the code previously present in the above
	 * method, that is, to create an XML GC Object with both the Date and time
	 * component.
	 */
	public static XMLGregorianCalendar getXMLDateTime(Date date)
			throws DatatypeConfigurationException {
		GregorianCalendar gCal = new GregorianCalendar();
		if (date != null) {
			gCal.setTime(date);
			XMLGregorianCalendar xgCal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCal);
			return xgCal;
		} else {
			return null;
		}
	}

	/**
	 * Convert the {@link Date} to ISO_DATE_TIME format.
	 * (yyyy-MM-dd'T'HH:mm:ss.SSS)
	 * 
	 * @param date
	 *            The {@link Date} instance
	 * 
	 * @return date in ISO_DATE_TIME format.
	 */
	public static String getISODate(Date date) {
		return ISO_DATETIME_TIME_FORMAT.format(date);
	}

	/**
	 * Convert the {@link org.w3c.dom.Element} to {@link String}.
	 * 
	 * @param element
	 *            The {@link org.w3c.dom.Element} object
	 * 
	 * @return String The element converted in string format.
	 */
	public static String elementToString(org.w3c.dom.Element element) {
		org.jdom.input.DOMBuilder builder = new DOMBuilder();
		org.jdom.Element jdomElement = builder.build(element);

		XMLOutputter xmlOutputter = new XMLOutputter();
		return xmlOutputter.outputString(jdomElement);
	}

	/**
	 * Convert the {@link org.w3c.dom.Element} to {@link org.jdom.Element}.
	 * 
	 * @param W3CElement
	 *            The {@link org.w3c.dom.Element} object
	 * 
	 * @return jdomElement The {@link org.w3c.dom.Element} converted in
	 *         {@link org.jdom.Element} format
	 */
	public static org.jdom.Element convertW3CToJDOMElement(Element W3CElement) {
		org.jdom.input.DOMBuilder builder = new org.jdom.input.DOMBuilder();
		org.jdom.Element jdomElement = builder.build(W3CElement);
		return jdomElement;
	}

}
