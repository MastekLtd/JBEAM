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

/**
 * Base user related value object class
 * All User related value object classes should extend this base class 
 * 
 * @author mandar.vaidya
 *
 */
public class UserInfo extends BaseRequestVO{

	/** Default Serial Version UID for the class */
	private static final long serialVersionUID = 1L;
	
	/** The user identifier */
	private String userId;

	/**
	 * Gets the userId
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the userId
	 *
	 * @param userId 
	 *        The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/UserInfo.java                                                                 $
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/