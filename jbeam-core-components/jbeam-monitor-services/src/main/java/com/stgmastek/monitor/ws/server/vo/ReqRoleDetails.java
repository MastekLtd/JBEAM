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
 * class holds user and installation details
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class ReqRoleDetails extends BaseRequestVO{

	private static final long serialVersionUID = 1L;
	
	private UserDetails userDetails;
	
	private InstallationEntity installationEntity;

	/**
	 * Gets the userDetails
	 *
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * Sets the userDetails
	 *
	 * @param userDetails 
	 *        The userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * Gets the installationEntity
	 *
	 * @return the installationEntity
	 */
	public InstallationEntity getInstallationEntity() {
		return installationEntity;
	}

	/**
	 * Sets the installationEntity
	 *
	 * @param installationEntity 
	 *        The installationEntity to set
	 */
	public void setInstallationEntity(InstallationEntity installationEntity) {
		this.installationEntity = installationEntity;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ReqRoleDetails.java                                                           $
 * 
 * 1     6/25/10 3:58p Lakshmanp
 * intila version for role details service
 * 
*/