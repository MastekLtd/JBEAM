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

public class BatchProgressChartVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private Integer noOfObjects;
	private Long timeElapsed;
	private String timeDiff;
	
	/**
	 * @return the noOfObjects
	 */
	public Integer getNoOfObjects() {
		return noOfObjects;
	}
	/**
	 * @param noOfObjects the noOfObjects to set
	 */
	public void setNoOfObjects(Integer noOfObjects) {
		this.noOfObjects = noOfObjects;
	}
	/**
	 * @return the timeElapsed
	 */
	public Long getTimeElapsed() {
		return timeElapsed;
	}
	/**
	 * @param timeElapsed the timeElapsed to set
	 */
	public void setTimeElapsed(Long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}
	/**
	 * @return the timeDiff
	 */
	public String getTimeDiff() {
		return timeDiff;
	}
	/**
	 * @param timeDiff the timeDiff to set
	 */
	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}

	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BatchProgressChartVO.java                                                     $
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/