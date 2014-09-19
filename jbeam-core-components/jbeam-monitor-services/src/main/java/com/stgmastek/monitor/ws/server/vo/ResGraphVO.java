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

public class ResGraphVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<ObjectExecutionGraphData> graphDataList;
	
	private BatchDetails batchDetails;

	/**
	 * Gets the graphDataList
	 *
	 * @return the graphDataList
	 */
	public List<ObjectExecutionGraphData> getGraphDataList() {
		return graphDataList;
	}

	/**
	 * Sets the graphDataList
	 *
	 * @param graphDataList 
	 *        The graphDataList to set.
	 */
	public void setGraphDataList(List<ObjectExecutionGraphData> graphDataList) {
		this.graphDataList = graphDataList;
	}

	/**
	 * Sets the batchDetails
	 *
	 * @param batchDetails 
	 *        The batchDetails to set
	 */
	public void setBatchDetails(BatchDetails batchDetails) {
		this.batchDetails = batchDetails;
	}

	/**
	 * Gets the batchDetails
	 *
	 * @return the batchDetails
	 */
	public BatchDetails getBatchDetails() {
		return batchDetails;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResGraphVO.java                                                               $
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/