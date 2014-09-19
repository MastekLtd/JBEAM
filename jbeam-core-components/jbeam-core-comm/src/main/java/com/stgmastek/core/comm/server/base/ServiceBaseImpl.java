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
package com.stgmastek.core.comm.server.base;

import java.util.Date;

import com.stgmastek.core.comm.exception.CommException;
import com.stgmastek.core.comm.server.vo.CBaseResponseVO;
import com.stgmastek.core.comm.util.CommConstants;

/**
 * The base class for Service implementing classes within the system 
 * Contains utility methods that can be used by the sub classes. 
 * 
 * @author grahesh.shanbhag
 *
 */
public class ServiceBaseImpl {

	/**
	 * Utility method to return the error response
	 * 
	 * @param e
	 * 		  The exception occurred during servicing the request
	 * @return the error {@link CBaseResponseVO} instance 
	 */
	protected CBaseResponseVO getErrorResponse(CommException e) {

		CBaseResponseVO errorResponseObj = new CBaseResponseVO();
		errorResponseObj.setResponseTime(new Date().getTime());
		errorResponseObj.setResponseType(CommConstants.SERVICE_STATUS.ERROR.name());
		errorResponseObj.setDescription(e.getMessage());
		return errorResponseObj;
	}

	/**
	 * Utility method to return an OK response
	 * 
	 * @return the error {@link CBaseResponseVO} instance 
	 */
	protected CBaseResponseVO getOKResponse() {
		CBaseResponseVO okResponseObj = new CBaseResponseVO();
		okResponseObj.setResponseTime(new Date().getTime());
		okResponseObj.setResponseType(CommConstants.SERVICE_STATUS.OK.name());
		return okResponseObj;
	}
	
	/**
	 * Utility method to set OK response into the response object
	 * 
	 * @param obj
	 * 		  The generic response object which has to be set with the OK response
	 * @return the error {@link CBaseResponseVO} instance 
	 */
	protected <T extends CBaseResponseVO> T getOKResponse(T obj) {
		obj.setResponseTime(new Date().getTime());
		obj.setResponseType(CommConstants.SERVICE_STATUS.OK.name());
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
	protected <T extends CBaseResponseVO> T getErrorResponse(
									Class<T> responseClass, Exception e){
		T errorResponseObj = null;
		try {
			errorResponseObj = responseClass.newInstance();
		} catch (InstantiationException e1) {
			return null;
		} catch (IllegalAccessException e1) {
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
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/base/ServiceBaseImpl.java                                                                $
 * 
 * 3     12/18/09 2:59p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/