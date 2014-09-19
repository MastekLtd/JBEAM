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
import java.util.List;


public class CReqCalendarLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private CalendarData calendarData;
	private List<CalendarData> calendarList;
	
	/**
	 * Gets the calendarData
	 *
	 * @return the calendarData
	 */
	public CalendarData getCalendarData() {
		return calendarData;
	}
	/**
	 * Sets the calendarData
	 *
	 * @param calendarData 
	 *        The calendarData to set.
	 */
	public void setCalendarData(CalendarData calendarData) {
		this.calendarData = calendarData;
	}
	/**
	 * Gets the calendarList
	 *
	 * @return the calendarList
	 */
	public List<CalendarData> getCalendarList() {
		return calendarList;
	}
	/**
	 * Sets the calendarList
	 *
	 * @param calendarList 
	 *        The calendarList to set.
	 */
	public void setCalendarList(List<CalendarData> calendarList) {
		this.calendarList = calendarList;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/server/vo/CReqCalendarLog.java                                                                  $
 * 
 * 1     4/09/10 8:49a Mandar.vaidya
 * Initial Version
 * 
 * 3     12/18/09 3:20p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/