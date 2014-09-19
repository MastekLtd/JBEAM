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
package com.stgmastek.monitor.comm.server.base;

import java.util.Date;

import org.apache.log4j.Logger;

import com.stgmastek.monitor.comm.exception.CommException;
import com.stgmastek.monitor.comm.server.vo.MBaseResponseVO;
import com.stgmastek.monitor.comm.util.CommConstants;

/**
 * The base class for Service implementing classes within the system 
 * Contains utility methods that might be in use in the sub classes. 
 * 
 * @author grahesh.shanbhag
 *
 */
public class ServiceBaseImpl {

	private static final Logger logger = Logger.getLogger(ServiceBaseImpl.class);
	
	/**
	 * Utility method to return the error response
	 * 
	 * @param e
	 * 		  The exception occurred during servicing the request
	 * @return the error {@link MBaseResponseVO} instance 
	 */
	protected MBaseResponseVO getErrorResponse(CommException e) {

		MBaseResponseVO errorResponseObj = new MBaseResponseVO();
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(CommConstants.SERVICE_STATUS.ERROR.name());
		errorResponseObj.setDescription(e.getMessage());
		
		//Dump the stack trace onto the console
		logger.error(e);
		
		return errorResponseObj;
	}
	/**
	 * Utility method to return an OK response
	 * 
	 * @return the error {@link MBaseResponseVO} instance 
	 */
	protected MBaseResponseVO getOKResponse() {

		MBaseResponseVO okResponseObj = new MBaseResponseVO();
		okResponseObj.setResponseTime(new Date().getTime());
		okResponseObj.setResponseType(CommConstants.SERVICE_STATUS.OK.name());
		return okResponseObj;
	}
	
	/**
	 * Utility method to set OK response into the response object
	 * 
	 * @return the error {@link MBaseResponseVO} instance 
	 */
	protected <T extends MBaseResponseVO> T getOKResponse(T obj) {
		obj.setResponseTime(new Date().getTime());
		obj.setResponseType(CommConstants.SERVICE_STATUS.OK.name());
		return obj;
	}	
	
	/**
	 * Over ridden method to set the response object in the instantiated 
	 * class that is supplied.
	 * This class has to be a sub class of the MBaseResponseVO class. 
	 * 
	 * @param responseClass
	 * 		  The response class that should be set with the error response message 
	 * @param e
	 * 		  Exception that was raised. The error response object would be set with the 
	 * 		  message from this exception
	 * 
	 */
	protected <T extends MBaseResponseVO> T getErrorResponse(
									Class<T> responseClass, Exception e){
		T errorResponseObj = null;
		try {
			errorResponseObj = responseClass.newInstance();
		} catch (Exception ex) {
			return null;
		} 
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(CommConstants.SERVICE_STATUS.ERROR.name());
		errorResponseObj.setDescription(e.getMessage());
		return errorResponseObj;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/server/base/ServiceBaseImpl.java                                                          $
 * 
 * 3     12/30/09 12:47p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/