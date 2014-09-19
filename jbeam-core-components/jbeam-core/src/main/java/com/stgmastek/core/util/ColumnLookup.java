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
 * Class to hold the column definitions for entities as set in the
 * COLUMN_MAP table Exact replica of the BATCH_COLUMN_MAP table.
 * 
 * @author grahesh.shanbhag
 * 
 */
public class ColumnLookup implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1792214614033136686L;

	/** The entity name */
	private String entity;

	/** The primary look up column */
	private String lookupColumn;

	/** The primary lookup value */
	private String lookupValue;

	/** The value column that would have the actual entity value(s) */
	private ArrayList<String> valueColumns = new ArrayList<String>();

	/** The precedence or the execution order for the entity within the batch */
	private Integer precedenceOrder;

	private Integer numberOfRequiredParameters;

	/**
	 * Whether to fail all objects within the same category for the same entity
	 * value
	 * <p />
	 * EX: There exists 10 objects for entity POLICY, say P1, P2 ... P10. If
	 * during the execution P4 fails, then this indicator would denote whether
	 * to mark P4 to P10 objects as 'SP' i.e. suspended. If 'N' then it would
	 * continue with the execution of objects P4 to P10, if Y, then would mark
	 * P4 to P10 as 'SP'
	 */
	private String onErrorFailAll;

	/**
	 * Default Constructor
	 */
	public ColumnLookup() {
	}

	/**
	 * Returns the entity
	 * 
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Sets the entity
	 * 
	 * @param entity
	 *            The entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * Returns the precedenceOrder
	 * 
	 * @return the precedenceOrder
	 */
	public Integer getPrecedenceOrder() {
		return precedenceOrder;
	}

	/**
	 * Sets the precedenceOrder
	 * 
	 * @param precedenceOrder
	 *            The precedenceOrder to set
	 */
	public void setPrecedenceOrder(Integer precedenceOrder) {
		this.precedenceOrder = precedenceOrder;
	}

	/**
	 * Returns the lookupColumn
	 * 
	 * @return the lookupColumn
	 */
	public String getLookupColumn() {
		return lookupColumn;
	}

	/**
	 * Sets the lookupColumn
	 * 
	 * @param lookupColumn
	 *            The lookupColumn to set
	 */
	public void setLookupColumn(String lookupColumn) {
		this.lookupColumn = lookupColumn;
	}

	/**
	 * Returns the lookupValue
	 * 
	 * @return the lookupValue
	 */
	public String getLookupValue() {
		return lookupValue;
	}

	/**
	 * Sets the lookupValue
	 * 
	 * @param lookupValue
	 *            The lookupValue to set
	 */
	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}

	/**
	 * Returns the valueColumns
	 * 
	 * @return the valueColumns
	 */
	public String[] getValueColumns() {
		return valueColumns.toArray(new String[valueColumns.size()]);
	}

	/**
	 * Sets the valueColumn
	 * 
	 * @param valueColumn
	 *            The valueColumn to set
	 */
	public void setValueColumn(String valueColumn) {
		valueColumns.clear();
		String[] strValueColumns = StringUtils.split(valueColumn, Constants.DELIMITER_CHAR, Constants.ESCAPE_CHAR);
		for (String str : strValueColumns) {
			if (str == null || "".equals(str)) {
				throw new NullPointerException("Invalid delimiter placed in value column");
			}
			valueColumns.add(str);
		}
		numberOfRequiredParameters = valueColumns.size();
	}

	/**
	 * Returns the onErrorFailAll
	 * 
	 * @return the onErrorFailAll
	 */
	public String getOnErrorFailAll() {
		return onErrorFailAll;
	}

	/**
	 * Sets the onErrorFailAll
	 * 
	 * @param onErrorFailAll
	 *            The onErrorFailAll to set
	 */
	public void setOnErrorFailAll(String onErrorFailAll) {
		this.onErrorFailAll = onErrorFailAll;
	}

	/**
	 * Returns the string representation of the object
	 * 
	 * @return the string representation of the object
	 */

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getEntity());
		sb.append(":");
		sb.append(getLookupColumn());
		sb.append(":");
		sb.append(getLookupValue());
		sb.append(":[");
		for (String str : getValueColumns()) {
			sb.append(str);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]:/");
		sb.append(getPrecedenceOrder());
		sb.append("/");
		sb.append(getOnErrorFailAll());
		return sb.toString();
	}

	/**
	 * @return the numberOfRequiredParameters
	 */
	public Integer getNumberOfRequiredParameters() {
		return numberOfRequiredParameters;
	}

}

/*
 * Revision Log ------------------------------- $Log::
 * /Product_Base/Projects/Batch
 * /Code/Java/Core/src/com/stgmastek/core/util/ColumnLookup.java $
 * 
 * 3 12/18/09 12:17p Grahesh Updated the comments
 * 
 * 2 12/17/09 11:46a Grahesh Initial Version
 */