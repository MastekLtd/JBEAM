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

import java.io.Serializable;

public class InstructionParameters implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/* Fields from the INSTRUCTION_PARAMETERS tables and are self explanatory */
	private Integer slNo;
	private String name;
	private String value;
	private String type;
	
	/**
	 * Gets the slNo
	 *
	 * @return the slNo
	 */
	public Integer getSlNo() {
		return slNo;
	}
	/**
	 * Sets the slNo
	 *
	 * @param slNo 
	 *        The slNo to set.
	 */
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}
	/**
	 * Gets the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name
	 *
	 * @param name 
	 *        The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the value
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
	 *        The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * Gets the type
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * Sets the type
	 *
	 * @param type 
	 *        The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/InstructionParameters.java                                                            $
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/