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
package com.stgmastek.core.comm.server.vo;

/**
 * Instruction class from the Monitor 
 * 
 * @author grahesh.shanbhag
 *
 */
public class CReqInstruction extends CBaseRequestVO {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/* Fields from the QUEUE table and are self explanatory */
	private String message;
	private String param;
	
	/**
	 * Gets the message
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Sets the message
	 *
	 * @param message 
	 *        The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Gets the param
	 *
	 * @return the param
	 */
	public String getParam() {
		return param;
	}
	/**
	 * Sets the param
	 *
	 * @param param 
	 *        The param to set.
	 */
	public void setParam(String param) {
		this.param = param;
	}
	
	
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CReqInstruction.java                                                                  $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/