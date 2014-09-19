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
 * POJO class to map the CORE_CONFIG table 
 */
public class CConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* The fields in the CORE_CONFIG table and are self explanatory */
	private String code1;
	private String code2;
	private String code3;
	private String value;
	private String valueType;
	
	/**
	 * Returns the code1
	 * 
	 * @return the code1
	 */
	public String getCode1() {
		return code1;
	}
	
	/**
	 * Sets the code1
	 * 
	 * @param code1 
	 *        The code1 to set
	 */
	public void setCode1(String code1) {
		this.code1 = code1;
	}
	
	/**
	 * Returns the code2
	 * 
	 * @return the code2
	 */
	public String getCode2() {
		return code2;
	}
	
	/**
	 * Sets the code2
	 * 
	 * @param code2 
	 *        The code2 to set
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}
	
	/**
	 * Returns the code3
	 * 
	 * @return the code3
	 */
	public String getCode3() {
		return code3;
	}
	
	/**
	 * Sets the code3
	 * 
	 * @param code3 
	 *        The code3 to set
	 */
	public void setCode3(String code3) {
		this.code3 = code3;
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
	 * Sets the value
	 * 
	 * @param value 
	 *        The value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the valueType
	 * 
	 * @return the valueType
	 */
	public String getValueType() {
		return valueType;
	}
	
	/**
	 * Sets the valueType
	 * 
	 * @param valueType 
	 *        The valueType to set
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/util/CConfig.java                                                                               $
 * 
 * 3     12/18/09 3:57p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/