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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMNodeHelper;
import org.dom4j.io.SAXReader;

/**
 * @author Narayana.Koppisetty
 * 
 */
public class XMLUtil {

	private static final String DATA_XPATH = "/Request/Data";

	/**
	 * Returns the response data object
	 * 
	 * @param responseString
	 * 		  The response string.
	 * 
	 * @return List of Element instances from data tag.
	 * 
	 * @throws Exception 
	 */
	public static List<org.w3c.dom.Element> getData(String responseString) throws Exception {

		DocumentFactory factory = new org.dom4j.dom.DOMDocumentFactory();
		SAXReader reader = new SAXReader(factory);
		List<org.w3c.dom.Element> elements = new ArrayList<org.w3c.dom.Element>();
		try {
			Document responseDocument = reader.read(new StringReader(responseString));
			org.dom4j.Node dataNode = responseDocument.selectSingleNode(DATA_XPATH);

			if ((dataNode != null) && (dataNode.getNodeType() == Node.ELEMENT_NODE)) {
				Element dataElement = (Element) dataNode;
				for (Iterator<?> nodeIterator = dataElement.elementIterator(); nodeIterator
						.hasNext();) {
					Element objectNode = (Element) nodeIterator.next();
					elements.add(DOMNodeHelper.asDOMElement(objectNode));
				}
			}

		} catch (DocumentException e) {
			throw new Exception("Invalid response", e);
		}
		return elements;
	}
}
