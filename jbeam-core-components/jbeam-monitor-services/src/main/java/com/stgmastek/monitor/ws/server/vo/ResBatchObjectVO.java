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

public class ResBatchObjectVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<BatchObject> batchObjectList;

	/**
	 * @param batchObjectList the batchObjectList to set
	 */
	public void setBatchObjectList(List<BatchObject> batchObjectList) {
		this.batchObjectList = batchObjectList;
	}

	/**
	 * @return the batchObjectList
	 */
	public List<BatchObject> getBatchObjectList() {
		return batchObjectList;
	}

	

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResBatchObjectVO.java                                                         $
 * 
 * 1     3/17/10 4:38p Mandar.vaidya
 * Initial Version
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