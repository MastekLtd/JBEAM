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

public class ReqBatchScheduleVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private String requestType;
	private MonitorCalendarData calendarData;
	private List<MonitorCalendarData> batchScheduleDataList;

	/**
	 * Gets the requestType.
	 *
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}
	/**
	 * Sets the requestType.
	 *
	 * @param requestType 
	 *        The requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	/**
	 * Gets the calendarData.
	 *
	 * @return the calendarData
	 */
	public MonitorCalendarData getBatchScheduleData() {
		return calendarData;
	}
	/**
	 * Sets the calendarData.
	 *
	 * @param calendarData 
	 *        The calendarData to set
	 */
	public void setBatchScheduleData(MonitorCalendarData calendarData) {
		this.calendarData = calendarData;
	}
	/**
	 * Gets the batchScheduleDataList.
	 *
	 * @return the batchScheduleDataList
	 */
	public List<MonitorCalendarData> getBatchScheduleDataList() {
		return batchScheduleDataList;
	}
	/**
	 * Sets the batchScheduleDataList.
	 *
	 * @param batchScheduleDataList 
	 *        The batchScheduleDataList to set
	 */
	public void setBatchScheduleDataList(
			List<MonitorCalendarData> batchScheduleDataList) {
		this.batchScheduleDataList = batchScheduleDataList;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ReqBatchScheduleVO.java                                                       $
 * 
 * 1     2/24/10 9:38a Grahesh
 * Initial Version
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/