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

import java.io.Serializable;
import java.util.List;


/**
 * Java class for DropDownEntry complex type.
 * 
 */
public class DropDownEntry implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private String dropDownKey;
	private List<ConfigParameter> dropDownValue;

	/**
	 * Gets the dropDownKey
	 *
	 * @return the dropDownKey
	 */
	public String getDropDownKey() {
		return dropDownKey;
	}
	/**
	 * Sets the dropDownKey
	 *
	 * @param dropDownKey 
	 *        The dropDownKey to set.
	 */
	public void setDropDownKey(String dropDownKey) {
		this.dropDownKey = dropDownKey;
	}
	/**
	 * Gets the dropDownValue
	 *
	 * @return the dropDownValue
	 */
	public List<ConfigParameter> getDropDownValue() {
		return dropDownValue;
	}
	/**
	 * Sets the dropDownValue
	 *
	 * @param dropDownValue 
	 *        The dropDownValue to set.
	 */
	public void setDropDownValue(List<ConfigParameter> dropDownValue) {
		this.dropDownValue = dropDownValue;
	}


}
