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
 * 
 * The user profile class that contains the user related information 
 * as set in the table USER_MASTER
 * 
 * @author mandar.vaidya
 *
 */
public class ResUserProfile extends BaseResponseVO {

	/** Default Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	private UserProfile userProfile;
	
	private List<UserProfile> userProfiles;

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
	 *        The userProfile to set.
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Gets the userProfiles
	 *
	 * @return the userProfiles
	 */
	public List<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	/**
	 * Sets the userProfiles
	 *
	 * @param userProfiles 
	 *        The userProfiles to set.
	 */
	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}
	
}

