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

/**
 * 
 * PLSQL helper parameter class to hold the parameter values 
 * 
 * @author grahesh.shanbhag
 *
 */
public class PLSQLParam {
	
	/**
	 * Constructor that takes the type and the value as arguments 
	 * 
	 * @param type
	 * 		  The type of the parameter 
	 * @param value 
	 * 		  The value of the parameter
	 */
	public PLSQLParam(String type, String value){
		this.type = type;
		this.value = value;
	}
	/** The type of the parameter */
	private String type = null;
	
	/** The value of the parameter */	
	private String value = null;

	/**
	 * Returns the type
	 * 
	 * @return the type
	 */
	
	public String getType() {
		return type;
	}

	/**
	 * Sets the type to set
	 * 
	 * @param type
	 * 		  The type to set
	 */
	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the value
	 * 
	 * @return the value
	 */
	
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value to set
	 * 
	 * @param value
	 * 		  The value to set
	 */
	
	public void setValue(String value) {
		this.value = value;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/PLSQLParam.java                                                                                     $
 * 
 * 3     12/18/09 12:17p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/