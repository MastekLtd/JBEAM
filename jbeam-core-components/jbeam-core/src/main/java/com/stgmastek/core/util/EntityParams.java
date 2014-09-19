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
import java.util.List;
import java.util.Map;

/**
 * Class to hold the entity definitions. 
 * Essentially would contain the list of column lookups and the values 
 * for the entity. 
 * 
 * @author grahesh.shanbhag
 *
 */
public class EntityParams implements Serializable{
	
	/** Serial Version UID */
	private static final long serialVersionUID = -801938942278412188L;
	
	/** The entity under consideration */
	private String entity;
	
	/** The column definitions as set up in COLUMN_MAP table */
	private List<ColumnLookup> lookupColumns;
	
	/**
	 * Stores the count of required parameters for the particular entity.
	 */
	private Integer numberOfRequiredParameters;
	
	/** 
	 * The values for which the entity (within the batch) has to be run
	 * If the list size is 1 with value as 'ALL' then it would internally detect 
	 * the distinct set and process the set  
	 */
	private List<GroupInfo> values;
	
	private Map<String, String> orderByMap;
	
	/**
	 * Default Constructor 
	 */
	public EntityParams(){}
	
	/**
	 * Overloaded constructor that takes entity name as the argument
	 *  
	 * @param entity
	 * 		  The entity name 
	 */
	public EntityParams(String entity){
		this.entity = entity;
		values = new ArrayList<GroupInfo>();		
	}

	/**
	 * Returns the entity name
	 * 
	 * @return the entity name 
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Sets the entity name 
	 * 
	 * @param entity 
	 *        The entity name to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * Returns the lookupColumns
	 * 
	 * @return the lookupColumns
	 */
	public List<ColumnLookup> getLookupColumns() {
		return lookupColumns;
	}

	/**
	 * Sets the lookupColumns
	 * 
	 * @param lookupColumns 
	 *        The lookupColumns to set
	 */
	public void setLookupColumns(List<ColumnLookup> lookupColumns) {
		this.lookupColumns = lookupColumns;
		Integer noOfReqParams = -1;
		for (ColumnLookup lookupColumn : lookupColumns) {
			if (noOfReqParams < 0) {
				noOfReqParams = lookupColumn.getNumberOfRequiredParameters();
			} else {
				if (noOfReqParams.intValue() != lookupColumn.getNumberOfRequiredParameters().intValue()) {
					throw new IllegalArgumentException("Invalid configuration for the entity " + lookupColumns);
				}
			}
		}
		numberOfRequiredParameters = noOfReqParams;
	}

	/**
	 * Returns the values
	 * 
	 * @return the values
	 */
	public List<GroupInfo> getValues() {
		return values;
	}

	/**
	 * Sets the values
	 * 
	 * @param values 
	 *        The values to set
	 */
	public void setValues(List<GroupInfo> values) {
		this.values = values;
	}

	/**
	 * Sets the values
	 * 
	 * @param value 
	 *        The value to set
	 */
	public void setAll(GroupInfo value) {
		List<GroupInfo> all = new ArrayList<GroupInfo>();
		all.add(value);
		setValues(all);
	}	
	
	/**
	 * Returns the string representation of the object 
	 * 
	 * @return the string representation of the object
	 */
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------");
		sb.append("\nEntity: " + getEntity());
		sb.append("\nValues: " + getValues());
		sb.append("\nLookup Column: " + getLookupColumns());
		sb.append("\n\n");
		return sb.toString();
	}

	/**
     * Returns the count of required parameters associated with the entity.
	 * @return the numberOfRequiredParameters
	 */
	public Integer getNumberOfRequiredParameters() {
		return numberOfRequiredParameters;
	}

	/**
	 * @param orderByMap the orderByMap to set
	 */
	public void setOrderByMap(Map<String, String> orderByMap) {
		this.orderByMap = orderByMap;
	}

	/**
	 * @return the orderByMap
	 */
	public Map<String, String> getOrderByMap() {
		return orderByMap;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/EntityParams.java                                                                                   $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/