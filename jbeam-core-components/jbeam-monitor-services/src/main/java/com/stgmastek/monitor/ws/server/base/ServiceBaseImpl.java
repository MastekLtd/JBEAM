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
package com.stgmastek.monitor.ws.server.base;

import java.util.Date;

import com.stgmastek.monitor.ws.server.vo.BaseResponseVO;
import com.stgmastek.monitor.ws.util.Constants;


/**
 * The base class for Service implementing classes within the system 
 * Contains utility methods that might be in use in the sub classes. 
 * 
 * @author mandar.vaidya
 *
 */
public class ServiceBaseImpl {

	/**
	 * Utility method to return the error response
	 * 
	 * @param e
	 * 		  The exception occured during servicing the request
	 * @return the error {@link BaseResponseVO} instance 
	 */
	protected BaseResponseVO getErrorResponse(Throwable e) {

		BaseResponseVO errorResponseObj = new BaseResponseVO();
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(Constants.SERVICE_STATUS.ERROR.name());
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage());
		throwableToString(sb, e);
		errorResponseObj.setDescription(e.getMessage());
		
		
		//Dump the stack trace onto the console
		
		
		return errorResponseObj;
	}

	/**
	 * Utility method to return an OK response
	 * 
	 * @return the error {@link BaseResponseVO} instance 
	 */
	protected BaseResponseVO getOKResponse() {

		BaseResponseVO okResponseObj = new BaseResponseVO();
		okResponseObj.setResponseTime(new Date().getTime());
		okResponseObj.setResponseType(Constants.SERVICE_STATUS.OK.name());
		return okResponseObj;
	}
	
	/**
	 * Utility method to set OK response into the response object
	 * 
	 * @return the error {@link BaseResponseVO} instance 
	 */
	protected <T extends BaseResponseVO> T getOKResponse(T obj) {
		obj.setResponseTime(new Date().getTime());
		obj.setResponseType(Constants.SERVICE_STATUS.OK.name());
		
		return obj;
	}	
	
	
	/**
	 * Over ridden method to set the response object in the instantiated 
	 * class that is supplied.
	 * This class has to be a sub class of the CBaseResponseVO class. 
	 * 
	 * @param responseClass
	 * 		  The response class that should be set with the error response message 
	 * @param e
	 * 		  Exception that was raised. The error response object would be set with the 
	 * 		  message from this exception
	 * 
	 */
	protected <T extends BaseResponseVO> T getErrorResponse(
									Class<T> responseClass, Exception e){
		
		//Dump the stack trace onto the console
		
		T errorResponseObj = null;
		try {
			errorResponseObj = responseClass.newInstance();
		} catch (Exception ex) {
			return null;
		} 
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(Constants.SERVICE_STATUS.ERROR.name());
		errorResponseObj.setDescription(e.getMessage());
		return errorResponseObj;
	}
	
	/**
	 * Over loaded method to set the response object in the instantiated 
	 * class that is supplied.
	 * This class has to be a sub class of the CBaseResponseVO class. 
	 * 
	 * @param responseClass
	 * 		  The response class that should be set with the error response message
	 *  
	 * @param e
	 * 		  Exception that was raised. The error response object would be set with the 
	 * 		  message from this exception
	 * 
	 * @param message
	 * 		  Custom message set by the program 
	 * 
	 */
	protected <T extends BaseResponseVO> T getErrorResponse(
			Class<T> responseClass, Exception e, String message){
		
		//Dump the stack trace onto the console
		
		T errorResponseObj = null;
		try {
			errorResponseObj = responseClass.newInstance();
		} catch (Exception ex) {
			return null;
		} 
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(Constants.SERVICE_STATUS.ERROR.name());
		errorResponseObj.setDescription(message);
		return errorResponseObj;
	}
	
	private void throwableToString(StringBuilder sb, Throwable t) {
		StackTraceElement[] stack = t.getStackTrace();
		sb.append(t.getMessage() + " ");
		for(StackTraceElement se : stack){
			sb.append(se.toString() + " ");
		}
		if (t.getCause() != null) {
			sb.append(" caused by ");
			throwableToString(sb, t.getCause());
		}
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/base/ServiceBaseImpl.java                                                        $
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/