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

import java.util.List;


/**
 * Java class for storing list of DropDownEntry with base response object
 * 
 */
public class ResDropDownEntry extends BaseResponseVO
{
	private static final long serialVersionUID = 1L;
	
	private List<DropDownEntry> dropDownEntries;

	/**
	 * Sets the dropDownEntries
	 *
	 * @param dropDownEntries 
	 *        The dropDownEntries to set.
	 */
	public void setDropDownEntries(List<DropDownEntry> dropDownEntries) {
		this.dropDownEntries = dropDownEntries;
	}

	/**
	 * Gets the dropDownEntries
	 *
	 * @return the dropDownEntries
	 */
	public List<DropDownEntry> getDropDownEntries() {
		return dropDownEntries;
	}

}
