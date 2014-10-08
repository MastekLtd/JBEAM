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
package com.stgmastek.birt.report.parameters.impl;

import java.io.Serializable;
import java.sql.Date;

import com.stgmastek.birt.report.parameters.IReportParameterTypeMapper;

public class DateSQLParameterTypeMapper implements IReportParameterTypeMapper {
	public Serializable convert(String value) {
		return new Date( Long.parseLong(value) );
	}
	
}
