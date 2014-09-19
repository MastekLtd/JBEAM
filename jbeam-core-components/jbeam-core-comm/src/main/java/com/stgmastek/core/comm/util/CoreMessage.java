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
package com.stgmastek.core.comm.util;

import java.io.Serializable;

/**
 * Class to hold the message information 
 * 
 * @author grahesh.shanbhag
 *
 */
public class CoreMessage implements Serializable{

	/** Default Static Version UID */
	private static final long serialVersionUID = 1L;
	
	
	/* The fields from the o_queue table and are self explanatory */
	private Integer id;	
	private String messageType;	
	private String message;
	private String param;
	
	//Additionally added fields 
	private String mode;
	
	/**
	 * Returns the id
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id 
	 *        The id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Returns the messageType
	 * 
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * Sets the messageType
	 * 
	 * @param messageType 
	 *        The messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * Returns the message
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
	 *        The message to set
	 */
	public void setMessage(String message) {
		setMessageType(message.substring(0, 2));
		this.message = message;
	}

	/**
	 * Returns the param
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
	 *        The param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * Returns the mode
	 * 
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets the mode to set
	 * 
	 * @param mode
	 * 		  The mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Message : ");
		sb.append(Constants.NEW_LINE);
		sb.append("\tType: ");
		sb.append(getMessageType());
		sb.append(Constants.NEW_LINE);
		sb.append("\tMessage: ");
		sb.append(getMessage());
		sb.append(Constants.NEW_LINE);
		sb.append("\tMessage Params : ");
		sb.append(getParam());
		sb.append(Constants.NEW_LINE);
		return sb.toString();
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/CoreMessage.java                                                                           $
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/