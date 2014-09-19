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

public class ResBatchDataVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<BatchInfo> batchDataList;

	/**
	 * Gets the batchDataList.
	 *
	 * @return the batchDataList
	 */
	public List<BatchInfo> getBatchDataList() {
		return batchDataList;
	}

	/**
	 * Sets the batchDataList.
	 *
	 * @param batchDataList 
	 *        The batchDataList to set
	 */
	public void setBatchDataList(List<BatchInfo> batchDataList) {
		this.batchDataList = batchDataList;
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResBatchDataVO.java                                                           $
 * 
 * 1     1/06/10 12:19p Grahesh
 * Initial Version
*
*
*/