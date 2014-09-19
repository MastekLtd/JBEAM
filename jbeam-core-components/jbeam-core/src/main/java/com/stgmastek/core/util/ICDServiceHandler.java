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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.majescomastek.stgicd.service.router.api.ICDServiceRouter;
import com.majescomastek.stgsuite.icd.service.invoker.ICDServiceClient;
import com.majescomastek.stgsuite.icd.service.invoker.exception.ServiceException;
import com.majescomastek.stgsuite.icd.service.invoker.util.XMLUtil;
import com.stgmastek.core.authentication.defaultimpl.AuthenticationHandlerImpl;
import com.stgmastek.core.authentication.defaultimpl.AuthenticationRequestInterceptor;
import com.stgmastek.core.interfaces.IRequestInterceptor;
import com.stgmastek.core.logic.ChainOfInterceptors;

/**
 * This class handles the ICDService client invocation 
 * and processing of requests to it.
 * It also handles the SSO authentication. 
 * 
 * It uses the Bill Pugh implementation of Singleton pattern.
 * 
 * @author MandarVaidya
 *
 */
public class ICDServiceHandler {

	private static final Logger logger = Logger.getLogger(ICDServiceHandler.class);
	private final static String ERROR = "ERROR";
	private final static String AUTH_ERR_CD = "AUTH-004";
	private final static String MESSAGE_TAG = "Message";
	private final Boolean useICDServiceRouter;
	private AtomicBoolean ssoUrlFetched;

	private static final ConcurrentHashMap<String, String> icdServiceURLMap = new ConcurrentHashMap<String, String>();
	private static final Lock latch = new ReentrantLock();
	
	private String APPLICATION_SERVICE_URL_REQUEST = 
		"<Request> <Metadata> <Instructions> "
		+ " <Execute> <Chain> <Name>getApplServiceUrlChain</Name> </Chain> </Execute> "
		+ "</Instructions> </Metadata> </Request>";

    private final ConcurrentHashMap<String, ICDServiceClient> icdServiceClientMap = new ConcurrentHashMap<String, ICDServiceClient>();

	/** Constructor */
	private ICDServiceHandler(){
		String strUseIcdServiceRouter = Configurations.getConfigurations().getConfigurations("CORE", "ICD_SERVICE", "USE_ICD_SERVICE_ROUTER");
		if (strUseIcdServiceRouter != null) {
			useICDServiceRouter = "true".equalsIgnoreCase(strUseIcdServiceRouter.trim());
		} else {
			useICDServiceRouter = false;
		}
		ssoUrlFetched = new AtomicBoolean(false);
	}
	
	/**
	 * Get the instance of {@link ICDServiceHandler}
	 * 
	 * @return the instance of {@link ICDServiceHandler} 
	 */
	public static ICDServiceHandler getIntance(){
		return SingletonHelper.INSTANCE;
	}

	/**
	 * Singleton Helper class.
	 */
	private static class SingletonHelper{
		private static final ICDServiceHandler INSTANCE = new ICDServiceHandler();
	}

	/**
	 * Invocation of ICDService client and processing of request.
	 *  
	 * @param request
	 * 			ICDService request 
	 * 
	 * @param batchContext
	 * 			The {@link BatchContext} object
	 * 
	 * @return response
	 * 			Response from ICDService in the form of list of {@link Element}
	 */
	public List<Element> callICDService(String request) {
		return handleRequest(request, Constants.ICD_END_POINT_URL);
	}
	
	public ICDServiceClient getClient() {
		return accessClient(Constants.ICD_END_POINT_URL);
	}

	/**
	 * Invocation of ICDService client and processing of request.
	 *  
	 * @param request
	 * 			ICDService request 
	 * 
	 * @param batchContext
	 * 			The {@link BatchContext} object
	 * 
	 * @return response
	 * 			Response from ICDService in the form of list of {@link Element}
	 */
	public List<Element> callICDService(String request, String applicationKey) {
		if(logger.isDebugEnabled()) {
			logger.debug("Request : " + request + " applicationKey : " + applicationKey);
		}
		if(!ssoUrlFetched.get()){
			latch.lock();
			try {
				retrieveICDServiceURLMap();
			} finally {
				ssoUrlFetched.compareAndSet(false, true);
				latch.unlock();
			}
		}
		String icdServiceURL = icdServiceURLMap.get(applicationKey);
		if(logger.isDebugEnabled()) {
			logger.debug(" applicationKey : " + applicationKey + " icdServiceURL : " + icdServiceURL );
		}
		return handleRequest(request, icdServiceURL);
	}

	/**
	 * Invocation of ICDService client and processing of request.
	 *  
	 * @param request
	 * 			ICDService request 
	 * 
	 * @param batchContext
	 * 			The {@link BatchContext} object
	 * 
	 * @return response
	 * 			Response from ICDService in the form of list of {@link Element}
	 */
	private List<Element> handleRequest(String request, String icdServiceURL) {
		List<Element> listOfElements = null;
		boolean sessionExpiredError = false;
		String origRequest = request;
		try {			
			if (logger.isDebugEnabled()) {
				logger.debug("ICDServiceHandler >> ICD_END_POINT_URL = " + 
						icdServiceURL);
			}
			
			if(ChainOfInterceptors.get()==null){
				setAuthenticationInterceptor();
			}
			
			request = getAuthenticatedRequest(request);
			if (logger.isDebugEnabled()) {
				logger.debug("ICDServiceHandler >> Final request to ICD = " + 
						request);
			}
			
			if(useICDServiceRouter){
				listOfElements = handleICDServiceRequest(request);
			} else {
				listOfElements = handleICDServiceRequest(icdServiceURL, request);
			}
			
			sessionExpiredError = isSessionExpired(listOfElements); 
			if(sessionExpiredError) {
				
				//since previous session has expired set new token.
				setAuthenticationInterceptor();
				
				//try again with new token.
				request = getAuthenticatedRequest(origRequest);
				if (logger.isDebugEnabled()) {
					logger.debug("ICDServiceHandler >> Final request to ICD = " + 
							request);
				}
				if(useICDServiceRouter){
					listOfElements = handleICDServiceRequest(request);
				} else {
					listOfElements = handleICDServiceRequest(icdServiceURL, request);
				}
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("ICDServiceHandler >> Request handled successfully.");
			}
			
		} catch (ServiceException se) {
			logger.error("ICDServiceHandler >> Connection to ICDServiceClient failed. Error = "
							+ se.getMessage(), se);
			return listOfElements;
		}
		return listOfElements;
	}

	public void shutdown() {
		for (ICDServiceClient icdServiceClient : icdServiceClientMap.values()) {
			icdServiceClient.shutdown();
		}
		icdServiceClientMap.clear();
		icdServiceURLMap.clear();
		ssoUrlFetched.compareAndSet(true, false);
	}
	
	/**
	 * Authenticate the request with ICD UserId and Password.
	 * 
	 * @param request
	 * 			ICDService request 
	 * 
	 * @param batchContext
	 * 			The {@link BatchContext} object
	 * 
	 * @return String
	 * 			The authenticated request
	 */
	private String getAuthenticatedRequest(String request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", Constants.ICD_SERVICE_USER_ID);
		map.put("password", Constants.ICD_SERVICE_PASSWORD);		
		
		request = StrSubstitutor.replace(request, map);
		for (IRequestInterceptor interceptor : ChainOfInterceptors.get()) {
		    request = interceptor.intercept(request);
		}
		return request;
		
	}

	/**
	 * The method generates authorization token and stores it in batch context
	 * if AuthrticationHandler is defined.
	 * 
	 * @param batchContext
	 */
	private void setAuthenticationInterceptor(){
	    AuthenticationHandlerImpl impl = new AuthenticationHandlerImpl();
		try{
		    String token = impl.authenticate();
		    List<IRequestInterceptor> chain = new ArrayList<IRequestInterceptor>();
		    AuthenticationRequestInterceptor interceptor = AuthenticationRequestInterceptor.newInstance(token);
		    chain.add(interceptor);
		    ChainOfInterceptors.set(chain);
		} catch (Exception e) {
			if(logger.isEnabledFor(Level.WARN)) {
				logger.warn("Error generating authorization token", e);
			}
		}
	}
	
    /**
     * Returns true if the session has expired.
     * 
     * @param listOfElements
     *            as returned by the response.
     * @return true if expired else false
     */
    private boolean isSessionExpired(List<Element> listOfElements) {
        boolean expired = false;
        String authCode = "";
        if (listOfElements != null && listOfElements.size() == 1) {

            try {
                if (MESSAGE_TAG.equals(listOfElements.get(0).getNodeName())) {
                    org.jdom.Element domElement = CommonUtils.convertW3CToJDOMElement(listOfElements.get(0));
                    String errorType = domElement.getChildText("Type");
                    
                    if (errorType != null && ERROR.equals(errorType)) {
                        authCode = domElement.getChildText("Code");
                        if (authCode != null) {
                            expired = AUTH_ERR_CD.equals(authCode);
                        }
                    }
					if (expired) {
						if (logger.isEnabledFor(Level.WARN)) {
							logger.warn("Session expired");
						}
                    }
                }
            } catch (Exception e) {
            	if(logger.isEnabledFor(Level.WARN)) {
            		logger.warn("Exception in isSessionExpired", e);
            	}
            }
        }
        return expired;
    }
	
	/**
	 * Retrieves 
	 */
	private void retrieveICDServiceURLMap() {
		List<Element> listOfElements = handleICDServiceRequest(Constants.ICD_END_POINT_URL, APPLICATION_SERVICE_URL_REQUEST);
        if (listOfElements != null) {
        	for(Element element:listOfElements){
        		if("SSOContext".equals(element.getNodeName())){
        			org.jdom.Element domElement = CommonUtils.convertW3CToJDOMElement(element);
        			String ApplicationId = domElement.getChildText("ApplicationId");
        			String ICDServiceURL = domElement.getChildText("IcdServiceUrl");
        			icdServiceURLMap.putIfAbsent(ApplicationId, ICDServiceURL);
        		}
        	}
        }
	}
	
	/**
	 * Handles ICDService request through ICDServiceClient.
	 * 
	 * @param request
	 * @return
	 */
	private List<Element> handleICDServiceRequest(String endPointURL, String request){
		return accessClient(endPointURL).handleRequest(request);
	}

	private ICDServiceClient accessClient(String endPointURL) {
		if (icdServiceClientMap.containsKey(endPointURL)) {
			return icdServiceClientMap.get(endPointURL);
		} else {
			latch.lock();
			try {
				ICDServiceClient client = icdServiceClientMap.get(endPointURL);
				if (client == null) {
					client = new ICDServiceClient(endPointURL, 100);
					icdServiceClientMap.put(endPointURL, client);
				}
				return client;
			} finally {
				latch.unlock();
			}
		}
	}
	
	/**
	 * Handles ICDService request through ICDServiceRouter.
	 * 
	 * @param request
	 * @return
	 */
	private List<Element> handleICDServiceRequest(String request){
		String response = ICDServiceRouter.handleRequest(request);
		return XMLUtil.getData(response);
	}

	public Boolean isUseICDServiceRouter() {
		return useICDServiceRouter;
	}
}
