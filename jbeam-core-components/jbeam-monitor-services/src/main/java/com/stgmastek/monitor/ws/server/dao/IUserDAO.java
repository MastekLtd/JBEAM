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
package com.stgmastek.monitor.ws.server.dao;

import java.sql.Connection;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.vo.Installation;
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.RoleData;
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.server.vo.UserDetails;
import com.stgmastek.monitor.ws.server.vo.UserInstallationRole;
import com.stgmastek.monitor.ws.server.vo.UserProfile;

/**
 * DAO class for all user related I/O to the database 
 * 
 * @author Lakshman Pendrum
 * 
 */
public interface IUserDAO extends IBaseDAO {
	
	/**
	 * Authenticates the user and gets the user details for the supplied user
	 * credentials
	 * 
	 * @param userCredentials
	 *            The user id and user password.
	 * 
	 * @return the user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public UserDetails userAuthentication(UserCredentials userCredentials, Connection connection)
			throws CommDatabaseException ;
	
	/**
	 * Generates role list.
	 * 
	 * @return list of roles
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<RoleData> getRoleData(Connection connection) 
			throws CommDatabaseException ;
	
	/**
	 * Inserts user details into USER_MASTER table
	 * 
	 * @param userProfile
	 *            The reference of user data
	 * @param connection
	 * 			  connection reference
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer addUser(UserProfile userProfile, Connection connection)
			throws CommDatabaseException;
	/**
	 * Inserts user installation and role details into USER_INSTALLATION_ROLE table
	 * 
	 * @param userInstallationRoles
	 *            The list of user, installation and roles
	 *            
	 * @param connection
	 * 			  connection object
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer addUserInstallationRoleDetails(List<UserInstallationRole> userInstallationRoles, Connection connection)
			throws CommDatabaseException;
	/**
	 * Deletes the user, installation and role details from 
	 * USER_INSTALLATION_ROLE table for the supplied user profile (with user id).
	 * 
	 * @param userProfile
	 *             The reference of user data
	 *            
	 * @param connection
	 * 			  connection object
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer deleteUserInstallationRoleDetails(UserProfile userProfile, Connection connection)
	throws CommDatabaseException;
	
	/**
	 * Generates user installation role details list
	 * 
	 * @param reqUserDetailsVO
	 * 			The request object contains user and installation details
	 * 
	 * @param connection
	 * 			The connection reference
	 * 
	 * @return user installation role details list
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<UserInstallationRole> getUserInstallationRoleData(
				ReqUserDetailsVO reqUserDetailsVO, Connection connection) throws CommDatabaseException;
	
	/**
	 * Generates user details 
	 * 
	 * @param userProfile
	 * 			The user details
	 * 
	 * @param connection
	 * 			The connection object
	 * 
	 * @return user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public UserProfile getUserDetails(UserProfile userProfile, Connection connection) 
			throws CommDatabaseException;
	
	/**
	 * Changes the password for the provided user data. 
	 * 
	 * @param userCredentials
	 * 			The instance of {@link UserCredentials} which contains (old) 
	 * 			password and new password.
	 * 
	 * @param connection
	 * 			The connection object
	 * 
	 * @return 1 if the record was updated successfully
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer changePassword(UserCredentials userCredentials,
			Connection connection)	throws CommDatabaseException;
	
	/**
	 * Resets the password for the provided user data. 
	 * 
	 * @param userCredentials
	 * 			The instance of {@link UserCredentials} which contains (old) 
	 * 			password and new password.
	 * 
	 * @param connection
	 * 			The connection object
	 * 
	 * @return 1 if the record was updated successfully
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer resetPassword(UserCredentials userCredentials, 
			Connection connection) throws CommDatabaseException;
	
	/**
	 * Gets the list of installations in JBEAM.
	 * 
	 * @return the list of installations
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<Installation> getInstallationsList(Connection connection)
	throws CommDatabaseException ;
	
	/**
	 * Updates user details in USER_MASTER table
	 * 
	 * @param userProfile
	 *            The reference of user data
	 *            
	 * @param connection
	 * 			  connection reference
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer updateUser(UserProfile userProfile, Connection connection)
	throws CommDatabaseException;
	
	/**
	 * Updates user details in USER_MASTER table when admin makes any changes in user's profile.
	 * 
	 * @param userProfile
	 *            The reference of user data
	 * 
	 * @param connection
	 *            connection reference
	 * 
	 * @return 1 if the record was updated successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer updateUserByAdmin(UserProfile userProfile, Connection connection) throws CommDatabaseException;

	/**
	 * Generates security details that is hint question and hint answer. 
	 * 
	 * @param userProfile
	 * 			The user details
	 * 
	 * @param connection
	 * 			The connection object
	 * 
	 * @return user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public UserProfile getSecurityDetails(UserProfile userProfile, Connection connection) 
			throws CommDatabaseException;
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/IUserDAO.java                                                                $
 * 
 * 9     7/13/10 3:30p Lakshmanp
 * Added new DAO method resetPassword 
 * Moved the userAuthentication method from IMonitorDAO.
 * 
 * 8     7/10/10 2:14p Mandar.vaidya
 * Changed return type of manageUser to Integer.
 * 
 * 7     7/08/10 3:19p Mandar.vaidya
 * Renamed the methodd createUser as manageUser. Modified the javadoc comments for this method.
 * 
 * 6     7/07/10 5:16p Mandar.vaidya
 * Added new method getUserDetails.
 * 
 * 5     7/01/10 4:30p Lakshmanp
 * Added getUserInstallationRoleData method
 * 
 * 4     6/30/10 3:10p Lakshmanp
 * Added DAO method to create users
 * 
 * 3     6/25/10 3:52p Lakshmanp
 * add InstallationEntity parameter to get role details
 * 
 * 2     6/25/10 2:06p Lakshmanp
 * added get role details method.
 * 1     6/23/10 10:39a Lakshmanp
 * intial version
*
*/