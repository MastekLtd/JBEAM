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


public class ResUserDetails extends BaseResponseVO{

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;

	private UserDetails userDetails;
	private List<UserInstallationRole> userInstallationRoleList;

	/**
	 * Sets the userDetails.
	 *
	 * @param userDetails 
	 *        The userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * Gets the userDetails.
	 *
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * Sets the userInstallationRoleList
	 *
	 * @param userInstallationRoleList 
	 *        The userInstallationRoleList to set.
	 */
	public void setUserInstallationRoleList(List<UserInstallationRole> userInstallationRoleList) {
		this.userInstallationRoleList = userInstallationRoleList;
	}

	/**
	 * Gets the userInstallationRoleList
	 *
	 * @return the userInstallationRoleList
	 */
	public List<UserInstallationRole> getUserInstallationRoleList() {
		return userInstallationRoleList;
	}

	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResUserDetails.java                                                           $
 * 
 * 2     7/13/10 3:52p Mandar.vaidya
 * Added field userInstallationRoleList
 * 
 * 1     1/06/10 3:18p Grahesh
 * Initial Version
 * 
*
*
*/