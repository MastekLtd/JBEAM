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
import java.util.ArrayList;

import stg.utils.StringUtils;


/**
 * 
 * This class hold information about the entity value
 * For EX: For entity type POLICY, the values would be the policy numbers
 *  
 * @author grahesh.shanbhag
 *
 */
public class GroupInfo implements Serializable{

	/** The serial version UID */
	private static final long serialVersionUID = -1586340235657832379L;
	
	/** The entity values as split using the {@link Constants#DELIMITER_CHAR} */
	private ArrayList<String> entityValues = new ArrayList<String>();
	
	/** The entity value */
	private String entityValue;
	
	/** The number of batch objects that fall under the current value */
	private Integer noOfRecords = 0;
	
	/**
	 * Default constructor that takes the value as the argument. 
	 * 
	 * The value is split using the {@link Constants#DELIMITER_CHAR}
	 * 
	 * @param entityValue
	 * 		  The entity value
	 */
	public GroupInfo(String entityValue){
		this.entityValue = entityValue;
		String[] strEntityValues = StringUtils.split(entityValue, Constants.DELIMITER_CHAR, Constants.ESCAPE_CHAR);
		for (String str : strEntityValues) {
			entityValues.add(str);
		}
	}
	
	/**
	 * Returns the entityValues that were split using the {@link Constants#DELIMITER_CHAR}.
	 * This is used for the entities other than PRE or POST as they may have multiple delimited fields
	 * associated in column map.
	 * 
	 * @return the entityValues
	 */
	public String[] getEntityValues() {
		return entityValues.toArray(new String[entityValues.size()]);
	}
	
	/**
	 * Returns the entityValue.
	 * This method is used in processing meta data entries wherein the entity value is always an 
	 * integer indicating the priority code of the meta data.
	 * 
	 * @return the entityValue
	 */
	public String getEntityValue() {
		return entityValue;
	}
	
	
	/**
	 * Returns the noOfRecords
	 * 
	 * @return the noOfRecords
	 */
	public Integer getNoOfRecords() {
		return noOfRecords;
	}
	
	/**
	 * Sets the noOfRecords
	 * 
	 * @param noOfRecords 
	 *        The noOfRecords to set
	 */
	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	
	/**
	 * Returns the string representation of the object
	 * 
	 * @return the string representation of the object
	 */
	
	public String toString() {
		return getEntityValue();
	}
	
	/**
	 * Returns the count of parameters.
	 * @return Integer
	 */
	public Integer getParameterCount() {
		return entityValues.size();
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/GroupInfo.java                                                                                      $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/