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

public class ListenerInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer listenerId;
	private Integer noOfObjectsExecuted;
	private Integer noOfObjectsFailed;
	private Double timeTaken;
	
	/**
	 * @return the listenerId
	 */
	public Integer getListenerId() {
		return listenerId;
	}
	/**
	 * @param listenerId the listenerId to set
	 */
	public void setListenerId(Integer listenerId) {
		this.listenerId = listenerId;
	}
	/**
	 * @return the noOfObjectsExecuted
	 */
	public Integer getNoOfObjectsExecuted() {
		return noOfObjectsExecuted;
	}
	/**
	 * @param noOfObjectsExecuted the noOfObjectsExecuted to set
	 */
	public void setNoOfObjectsExecuted(Integer noOfObjectsExecuted) {
		this.noOfObjectsExecuted = noOfObjectsExecuted;
	}
	/**
	 * @return the noOfObjectsFailed
	 */
	public Integer getNoOfObjectsFailed() {
		return noOfObjectsFailed;
	}
	/**
	 * @param noOfObjectsFailed the noOfObjectsFailed to set
	 */
	public void setNoOfObjectsFailed(Integer noOfObjectsFailed) {
		this.noOfObjectsFailed = noOfObjectsFailed;
	}
	/**
	 * Gets the timeTaken
	 *
	 * @return the timeTaken
	 */
	public Double getTimeTaken() {
		return timeTaken;
	}
	/**
	 * Sets the timeTaken
	 *
	 * @param timeTaken 
	 *        The timeTaken to set.
	 */
	public void setTimeTaken(Double timeTaken) {
		this.timeTaken = timeTaken;
	}


	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ListenerInfo.java                                                             $
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/