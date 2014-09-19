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

public class CalendarData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String calendarName;	
	private String year;
	private Long nonWorkingDate;	
	private String remark;
	private String userId;
	/**
	 * Gets the calendarName.
	 *
	 * @return the calendarName
	 */
	public String getCalendarName() {
		return calendarName;
	}
	/**
	 * Sets the calendarName.
	 *
	 * @param calendarName 
	 *        The calendarName to set
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * Sets the year.
	 *
	 * @param year 
	 *        The year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * Gets the nonWorkingDate.
	 *
	 * @return the nonWorkingDate
	 */
	public Long getNonWorkingDate() {
		return nonWorkingDate;
	}
	/**
	 * Sets the nonWorkingDate.
	 *
	 * @param nonWorkingDate 
	 *        The nonWorkingDate to set
	 */
	public void setNonWorkingDate(Long nonWorkingDate) {
		this.nonWorkingDate = nonWorkingDate;
	}
	/**
	 * Gets the remark.
	 *
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * Sets the remark.
	 *
	 * @param remark 
	 *        The remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * Gets the userId.
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * Sets the userId.
	 *
	 * @param userId 
	 *        The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CalendarData.java                                                                     $
 * 
 * 1     4/09/10 8:49a Mandar.vaidya
 * Initial Version
 * 
 * 1     2/18/10 7:40p Grahesh
 * VO for Calendar Module
 * 
 * 1     1/06/10 12:09p Grahesh
 * Initial Version
*
*
*/