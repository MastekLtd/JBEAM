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

import java.io.Serializable;
import java.util.HashMap;

import stg.utils.StringUtils;

/**
 * Class to hold the message information 
 * 
 * @author grahesh.shanbhag
 *
 */
public class Message implements Serializable{

	/** Default Static Version UID */
	private static final long serialVersionUID = -1122798694527039068L;
	
	/**The fields from the I_QUEUE table and are self explanatory */
	private Integer id;	
	private String message;
	private String param;
	
	private HashMap<String, String> map = new HashMap<String, String>();

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
	
	public HashMap<String, String> getParams() {
		return map;
	}

	/**
	 * Sets the param
	 * 
	 * @param param 
	 *        The param to set
	 */
	public void setParam(String param) {
		this.param = param;
		if (param != null) {
			String[] params = StringUtils.split(param, ',');
			for (int i=0; i < params.length; i++) {
				String[] entry = StringUtils.split(params[i], '=');
				if (entry.length < 2) {
					map.put(entry[0], entry[0]);
				} else {
					map.put(entry[0], entry[1]);
				}
			}
		}
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Message : \n");
		sb.append("\tMessage: " + getMessage() + "\n");
		sb.append("\tMessage Params : " + getParam() + "\n");
		return sb.toString();
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/Message.java                                                                                        $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/