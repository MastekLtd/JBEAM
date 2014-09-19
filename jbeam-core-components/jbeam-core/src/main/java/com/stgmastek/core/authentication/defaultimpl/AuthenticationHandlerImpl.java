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
package com.stgmastek.core.authentication.defaultimpl;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.io.SAXReader;

import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.interfaces.IAuthenticationHandler;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.ICDServiceHandler;

/**
 * This class is default implementation of IAuthenticationHandler which can be
 * used in case project specific handler is not required.
 * 
 * @author shantanuc
 * 
 */
public class AuthenticationHandlerImpl implements IAuthenticationHandler {

	private static Logger logger = Logger.getLogger(AuthenticationHandlerImpl.class); 

	private String AUTHENTICATION_REQUEST = 
		"<Request>  <Metadata>    "
		+ "<Authentication><UsernamePasswordAuth><Username>${userId}</Username>"
		+ "<Password>${password}</Password></UsernamePasswordAuth></Authentication>"
		+ "</Metadata></Request>";
	
	
	public AuthenticationHandlerImpl() {
	}
	
	/* (non-Javadoc)
	 * @see com.stgmastek.core.interfaces.IAuthenticationHandler#authenticate(com.stgmastek.core.util.BatchContext)
	 */
	public String authenticate(BatchContext batchContext) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", Constants.ICD_SERVICE_USER_ID);
		map.put("password", Constants.ICD_SERVICE_PASSWORD);

		// Substitute the place-holders with runtime data in the request
		String request = StrSubstitutor.replace(AUTHENTICATION_REQUEST, map);

		try {
			String response = ICDServiceHandler.getIntance().getClient().execute(request);
			return getTokenData(response);
		} catch (BatchStopException ex) {
			throw new RuntimeException(ex);
		} catch (Exception ex) {
			logger.warn("Error generating Authenication Token", ex);
		}
        return "";
	}
	
	/* 
	 * The authenticate method added so that caller can call without passing BatchContext object.
	 * of using IAuthenticationHandler.authenticate.
	 * 
	 */
	public String authenticate() {
		return authenticate(null);
	}

	/**
	 * @param responseString
	 * @return String : Returns authentication token. 
	 */
	public static String getTokenData(String responseString) throws BatchStopException {
		DocumentFactory factory = new DOMDocumentFactory();
		SAXReader reader = new SAXReader(factory);

		String token = null;
		try {
			Document responseDocument = reader.read(new StringReader(
					responseString));
			org.dom4j.Node dataNode = responseDocument
					.selectSingleNode("/Request/Metadata/AuthenticationResponse/Token");

			if ((dataNode != null)) {
				token = dataNode.getStringValue();
				System.out.println("Token Generated :- " + token);
			}
			return token;
		} catch (DocumentException e) {
			throw new RuntimeException("Invalid response", e);
		}
	}
}
