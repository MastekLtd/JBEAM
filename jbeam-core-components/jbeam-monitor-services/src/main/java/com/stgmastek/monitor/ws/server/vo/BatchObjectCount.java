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

public class BatchObjectCount implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer noOfObjectsExecuted;
	private Integer noOfObjectsFailed;
	/**
	 * Gets the noOfObjectsExecuted.
	 *
	 * @return the noOfObjectsExecuted
	 */
	public Integer getNoOfObjectsExecuted() {
		return noOfObjectsExecuted;
	}
	/**
	 * Sets the noOfObjectsExecuted.
	 *
	 * @param noOfObjectsExecuted 
	 *        The noOfObjectsExecuted to set
	 */
	public void setNoOfObjectsExecuted(Integer noOfObjectsExecuted) {
		this.noOfObjectsExecuted = noOfObjectsExecuted;
	}
	/**
	 * Gets the noOfObjectsFailed.
	 *
	 * @return the noOfObjectsFailed
	 */
	public Integer getNoOfObjectsFailed() {
		return noOfObjectsFailed;
	}
	/**
	 * Sets the noOfObjectsFailed.
	 *
	 * @param noOfObjectsFailed 
	 *        The noOfObjectsFailed to set
	 */
	public void setNoOfObjectsFailed(Integer noOfObjectsFailed) {
		this.noOfObjectsFailed = noOfObjectsFailed;
	}

	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/BatchObjectCount.java                                                            $
 * 
 * 1     1/06/10 2:48p Grahesh
 * Initial Version
 * 
 * 4     1/06/10 10:49a Grahesh
 * Changed the object hierarchy
 * 
 * 3     1/06/10 10:46a Grahesh
 * Renamed BatchObjectCountVO.java to ResBatchObjectCountVO.java
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/