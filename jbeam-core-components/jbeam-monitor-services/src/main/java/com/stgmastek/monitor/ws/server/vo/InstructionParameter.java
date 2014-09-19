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
package com.stgmastek.monitor.ws.server.vo;

public class InstructionParameter extends BaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	private Integer slNo;
	private String name;
	private String value; 
	private String type;
	/**
	 * Returns the slNo
	 * 
	 * @return the slNo
	 */
	
	public Integer getSlNo() {
		return slNo;
	}
	/**
	 * Sets the slNo to set
	 * 
	 * @param slNo
	 * 		  The slNo to set
	 */
	
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}
	/**
	 * Returns the name
	 * 
	 * @return the name
	 */
	
	public String getName() {
		return name;
	}
	/**
	 * Sets the name to set
	 * 
	 * @param name
	 * 		  The name to set
	 */
	
	public void setName(String name) {
		this.name = name;
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

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/InstructionParameter.java                                                    $
 * 
 * 5     12/30/09 6:43p Mandar.vaidya
 * Corrected the implementation for instruction log and parameters
 * 
 * 4     12/30/09 4:49p Grahesh
 * Corrected the implementation for instruction log and parameters
 * 
 * 3     12/30/09 4:09p Grahesh
 * Renamed InstructionParameters.java to InstructionParameter.java
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/