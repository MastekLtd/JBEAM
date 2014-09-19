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
 * This class represents RoleMaster table in Monitor Schema
 *
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class RoleData implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/** the fields from the ROLE_MASTER table and are self explanatory */
	private String roleId;
	private String roleName;
	private Long effDate;
	private Long expDate;
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
	 * Gets the roleName
	 *
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * Sets the roleName
	 *
	 * @param roleName 
	 *        The roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * Gets the effDate
	 *
	 * @return the effDate
	 */
	public Long getEffDate() {
		return effDate;
	}
	/**
	 * Sets the effDate
	 *
	 * @param effDate 
	 *        The effDate to set
	 */
	public void setEffDate(Long effDate) {
		this.effDate = effDate;
	}
	/**
	 * Gets the expDate
	 *
	 * @return the expDate
	 */
	public Long getExpDate() {
		return expDate;
	}
	/**
	 * Sets the expDate
	 *
	 * @param expDate 
	 *        The expDate to set
	 */
	public void setExpDate(Long expDate) {
		this.expDate = expDate;
	}
	

}
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/RoleData.java                                                                 $
 * 
 * 1     6/25/10 2:25p Lakshmanp
 * initial version
*
*/
