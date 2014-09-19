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
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * The request parameters for the scheduled job under Process Request Engine 
 *  
 * It has all the parameters set in the PROCESS_REQ_PARAM table and additional fields for 
 * core system usage 
 * 
 * For setting up of parameters check  
 * <a href="http://stgpedia.stgil-india.com/index.php/PROCESS_REQ_PARAMS">STGPedia</a> 
 * for related information.
 * 
 * @author grahesh.shanbhag
 * 
 */
public class BatchParams implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1782856352160453000L;

	/** Private map to hold the information */
	private HashMap<String, Object> params = null;

	/** The Process Request Identifier */
	private Long requestId;
	
	/** 
	 * Public constructor with the parameters supplied by the PRE engine 
	 * @param params
	 * 		  The parameters as set in the PROCESS_REQ_PARAMS table and supplied by the PRE engine 
	 */	
	public BatchParams(HashMap<String, Object> params) {		
		this.params = params;
	}

	/**
	 * Public method to get the keys of map as collection 
	 * 
	 * @return the key set 
	 */
	public Set<String> getKeys(){
		return getProcessRequestParams().keySet();
	}
	
	/**
	 * Returns the integer value for the parameter defined by the supplied key 
	 *  
	 * @param key
	 * 		  The key
	 * @return the integer value of the parameter 
	 */
	public Integer getIntValue(String key){
		return (Integer)getProcessRequestParams().get(key);
	}

	/**
	 * Returns the string value for the parameter defined by the supplied key 
	 *  
	 * @param key
	 * 		  The key
	 * @return the string value of the parameter 
	 */
	public String getStringValue(String key){
		Object obj = getProcessRequestParams().get(key);
		if(obj == null)
			return "";
		else
			return (String) obj;
	}

	/**
	 * Returns the double value for the parameter defined by the supplied key 
	 *  
	 * @param key
	 * 		  The key
	 * @return the double value of the parameter 
	 */
	public Double getDoubleValue(String key){
		return (Double)getProcessRequestParams().get(key);
	}
	
	/**
	 * Returns the date value for the parameter defined by the supplied key 
	 *  
	 * @param key
	 * 		  The key
	 * @return the date value of the parameter 
	 */
	public Date getDateValue(String key){
		return (Date)getProcessRequestParams().get(key);
	}

	/**
	 * Returns the parameters as set for the {@link BatchJob}
	 * 
	 * @return the parameter map 
	 */
	public HashMap<String, Object> getProcessRequestParams() {
		if(params == null)
			params = new HashMap<String, Object>();
		return params;
	}	

	/**
	 * Returns the requestId
	 * 
	 * @return the requestId
	 */
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * Sets the requestId
	 * 
	 * @param requestId 
	 *        The requestId to set
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	/**
	 * Returns the string representation of the object
	 * 
	 * @return the string representation of the object
	 */
	
	public String toString() {
		return super.toString();
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/BatchParams.java                                                                                    $
 * 
 * 3     2/25/10 10:23a Grahesh
 * Changes made to remove suppressed warnings as PRE is now made compatible with Generics.
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/