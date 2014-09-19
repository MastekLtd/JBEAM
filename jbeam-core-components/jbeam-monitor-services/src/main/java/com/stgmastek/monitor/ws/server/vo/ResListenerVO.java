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

public class ResListenerVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<ListenerInfo> listenerData;

	/**
	 * Gets the listenerData.
	 *
	 * @return the listenerData
	 */
	public List<ListenerInfo> getListenerData() {
		return listenerData;
	}

	/**
	 * Sets the listenerData.
	 *
	 * @param listenerData 
	 *        The listenerData to set
	 */
	public void setListenerData(List<ListenerInfo> listenerData) {
		this.listenerData = listenerData;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResListenerVO.java                                                            $
 * 
 * 1     1/06/10 12:05p Grahesh
 * Corrected the signature and object hierarchy
 * 
 *
* 
*
*/