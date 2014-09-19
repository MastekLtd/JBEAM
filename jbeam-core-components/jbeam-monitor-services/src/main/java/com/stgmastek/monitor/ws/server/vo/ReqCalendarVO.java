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

public class ReqCalendarVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private MonitorCalendarData calendarData;
	private List<MonitorCalendarData> calendarList;
	/**
	 * Gets the calendarData.
	 *
	 * @return the calendarData
	 */
	public MonitorCalendarData getCalendarData() {
		return calendarData;
	}
	/**
	 * Sets the calendarData.
	 *
	 * @param calendarData 
	 *        The calendarData to set
	 */
	public void setCalendarData(MonitorCalendarData calendarData) {
		this.calendarData = calendarData;
	}
	/**
	 * Gets the calendarList.
	 *
	 * @return the calendarList
	 */
	public List<MonitorCalendarData> getCalendarList() {
		return calendarList;
	}
	/**
	 * Sets the calendarList.
	 *
	 * @param calendarList 
	 *        The calendarList to set
	 */
	public void setCalendarList(List<MonitorCalendarData> calendarList) {
		this.calendarList = calendarList;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ReqCalendarVO.java                                                            $
 * 
 * 1     2/18/10 7:40p Grahesh
 * VO for Calendar Module
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/