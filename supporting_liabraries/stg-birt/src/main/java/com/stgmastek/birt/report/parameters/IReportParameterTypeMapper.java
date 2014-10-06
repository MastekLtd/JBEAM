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


package com.stgmastek.birt.report.parameters;

import java.io.Serializable;

/**
 * Contract for Report Parameter Object Type Mapper. 	
 * 
 * @author Prasanna Mondkar
 */
public interface IReportParameterTypeMapper {
	
	/**
	 * Converts the provided value to a Concrete Java Object.	
	 * @param value Input
	 * @return Any Object, which is Serializable
	 */
	public Serializable convert(String value);
}
