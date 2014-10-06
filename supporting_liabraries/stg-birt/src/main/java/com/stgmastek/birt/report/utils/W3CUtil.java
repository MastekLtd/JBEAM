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
package com.stgmastek.birt.report.utils;

import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;

import org.w3c.dom.Node;


/**
 * Some Utility Methods for object transformations from Strings to W3C Elements/Documents and vice versa.
 * @author Prasanna Mondkar
 */
public class W3CUtil 
{
	public static String domElementToString(org.w3c.dom.Element element)
	{
		TransformerFactory transFactory = TransformerFactory.newInstance(); 
		StringWriter buffer = new StringWriter(); 
		try {
			Transformer transformer = transFactory.newTransformer(); 
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); 
			transformer.transform(new DOMSource(element), 
			      new StreamResult(buffer));
		} catch (TransformerException e) {
			e.printStackTrace();
		} 		 
		return buffer.toString();
	}
	
	public static String nodeToString(Node node) {
		try {
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
