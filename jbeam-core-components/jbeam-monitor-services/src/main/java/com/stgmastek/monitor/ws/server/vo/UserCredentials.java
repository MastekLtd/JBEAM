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
 * User Credentials value object class
 *  
 * @author mandar.vaidya
 *
 */
public class UserCredentials extends UserInfo{

	/** Default Serial Version UID for the class */
	private static final long serialVersionUID = 1L;

	/** The password for the user */
	private String password;
	
	/** 
	 * Provision for new password for some services. 
	 * Usually this field should not be used, unless it for changing of password
	 * The mandatory check re-confirming the new password should be done at clients 
	 * end and the service using this field assumes that the confirmation of the 
	 * change is done at service client end.  
	 */
	private String newPassword;

	/**
	 * Gets the password
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password
	 *
	 * @param password 
	 *        The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the newPassword
	 *
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * Sets the newPassword
	 *
	 * @param newPassword 
	 *        The newPassword to set.
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

		
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/UserCredentials.java                                                          $
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/