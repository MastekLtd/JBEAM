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

/**
 * This class holds response user details
 * 
 * @author mandar440346
 * 
 * @since $Revision: 1 $  
 */
public class ResUserDetailsVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private UserProfile userProfile;
	private UserCredentials userCredentials;
	private List<UserInstallationRole> userInstallationRoles;
	
	
	/**
	 * Gets the userProfile
	 *
	 * @return the userProfile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}
	/**
	 * Sets the userProfile
	 *
	 * @param userProfile 
	 *        The userProfile to set
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	/**
	 * Gets the userCredentials
	 *
	 * @return the userCredentials
	 */
	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	/**
	 * Sets the userCredentials
	 *
	 * @param userCredentials 
	 *        The userCredentials to set
	 */
	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}
	/**
	 * Sets the userInstallationRoles
	 *
	 * @param userInstallationRoles 
	 *        The userInstallationRoles to set
	 */
	public void setUserInstallationRoles(List<UserInstallationRole> userInstallationRoles) {
		this.userInstallationRoles = userInstallationRoles;
	}
	/**
	 * Gets the userInstallationRoles
	 *
	 * @return the userInstallationRoles
	 */
	public List<UserInstallationRole> getUserInstallationRoles() {
		return userInstallationRoles;
	}
	

	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResUserDetailsVO.java                                                         $
 * 
 * 1     7/01/10 5:07p Mandar.vaidya
 * Initial Version
 * 
 * 
*/