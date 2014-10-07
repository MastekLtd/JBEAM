/**
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
 * $Revision: 2717 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/PREDataType.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log:    $
 *
 */
package stg.utils;

/**
 * Defines the PRE data types.
 * 
 * This class replaces stg.utils.ILableNameSize.
 *
 * @author Kedar Raybagkar
 * @since V1.0R28.x
 */
public enum PREDataType {

	/**
	 * Boolean Data Type;
	 */
	BOOLEAN("B", "Boolean"), 
	/**
	 * Boolean Array Data Type
	 */
	BOOLEAN_ARRAY("BA", "Boolean Array"), 
	/**
	 * Integer Data Type 
	 */
	INTEGER("I", "Integer"), 
	/**
	 * Integer Array Data Type.
	 */
	INTEGER_ARRAY("IA", "Integer Array"), 
	/**
	 * Long data type. 
	 */
	LONG("L", "Long"),
	/**
	 * Long array data type.
	 */
	LONG_ARRAY("LA", "Long Array"),
	/**
	 * Double data type.
	 */
	DOUBLE("D", "Double"),
	/**
	 * Double array data type.
	 */
	DOUBLE_ARRAY("DA", "Double Array"),
	/**
	 * java.sql.Date data type. 
	 */
	DATE("DT", "Date"),
	/**
	 * java.sql.Date array data type. 
	 */
	DATE_ARRAY("DTA", "Date Array"),
	/**
	 * java.sql.Timestamp data type.
	 */
	TIMESTAMP("TS", "Timestamp"),
	/**
	 * java.sql.Timestamp array data type.
	 */
	TIMESTAMP_ARRAY("TSA", "Timestamp Array"),
	/**
	 * String data type.
	 */
	STRING("S", "String"),
	/**
	 * String array data type.
	 */
	STRING_ARRAY("SA", "String Array"),
	/**
	 * Time data type. 
	 */
	TIME("T", "Time"),
	/**
	 * Time array data type.
	 */
	TIME_ARRAY("TA", "Time Array");
	
	/**
	 * Stores the value. 
	 */
	private final String description;
	
	private final String ID;

	/**
	 * Constructs the data type by accepting a unique value.
	 * @param value
	 */
	private PREDataType(String id, String description) {
		this.ID = id;
		this.description = description;
	}
	
	/**
	 * Returns the ID of the data type.
	 * 
	 * @return String
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * Returns the description of the data type.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return ID + " : " + description;
	}
	
	/**
	 * Resolves the id to a registered data type.
	 * Returns null if could not be resolved.
	 * 
	 * @param id to be resolved.
	 * @return PREDataType
	 */
	public static PREDataType resolve(String id) {
		PREDataType returnValue = null;
		for (PREDataType type : values()) {
			if (type.getID().equals(id)) {
				returnValue = type;
				break;
			}
		}
		return returnValue;
	}
}
