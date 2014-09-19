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
/**
 * This class holds User Installation and Role id
 * 
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class UserInstallationRole implements Serializable{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String roleId;
	private String installationCode;
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
	 *        The userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * Gets the roleId
	 *
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * Sets the roleId
	 *
	 * @param roleId 
	 *        The roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * Gets the installationCode
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}
	/**
	 * Sets the installationCode
	 *
	 * @param installationCode 
	 *        The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/UserInstallationRole.java                                                     $
 * 
 * 1     6/30/10 3:18p Lakshmanp
 * Initial Version
 * 
*
*/