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

import java.util.List;

public class CReqProcessRequestScheduleVO  extends CBaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	private ScheduleData scheduleData;
	private List<ScheduleData> processRequestScheduleList;

	/**
	 * Gets the ScheduleData
	 *
	 * @return the ScheduleData
	 */
	public ScheduleData getScheduleData() {
		return scheduleData;
	}
	/**
	 * Sets the ScheduleData
	 *
	 * @param scheduleData 
	 *		  The ScheduleData to set	
	 */
	public void setScheduleData(
			ScheduleData scheduleData) {
		this.scheduleData = scheduleData;
	}
	/**
	 * Gets the processRequestScheduleList
	 *
	 * @return the processRequestScheduleList
	 */
	public List<ScheduleData> getProcessRequestScheduleList() {
		return processRequestScheduleList;
	}
	/**
	 * Sets the processRequestScheduleList
	 *
	 * @param processRequestScheduleList 
	 *		  The processRequestScheduleList to set	
	 */
	public void setProcessRequestScheduleList(
			List<ScheduleData> processRequestScheduleList) {
		this.processRequestScheduleList = processRequestScheduleList;
	}
	
	

}
