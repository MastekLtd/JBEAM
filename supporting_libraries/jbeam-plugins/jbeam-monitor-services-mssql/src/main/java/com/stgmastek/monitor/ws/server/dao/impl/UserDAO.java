/**
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
 */
package com.stgmastek.monitor.ws.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.stgmastek.monitor.ws.exception.CommDatabaseException;
import com.stgmastek.monitor.ws.server.dao.IUserDAO;
import com.stgmastek.monitor.ws.server.vo.Installation;
import com.stgmastek.monitor.ws.server.vo.ReqUserDetailsVO;
import com.stgmastek.monitor.ws.server.vo.RoleData;
import com.stgmastek.monitor.ws.server.vo.UserCredentials;
import com.stgmastek.monitor.ws.server.vo.UserDetails;
import com.stgmastek.monitor.ws.server.vo.UserInstallationRole;
import com.stgmastek.monitor.ws.server.vo.UserProfile;
import com.stgmastek.monitor.ws.util.BaseDAO;
import com.stgmastek.util.ResultSetMapper;

/**
 * DAO class for all user related related I/O to the database 
 * 
 * @author mandar.vaidya
 * @since $Revision: 13 $  
 */
public class UserDAO extends BaseDAO implements IUserDAO {
	
	/** Query to get role details */
	private static final String GET_ROLE_DATA = 
		"select role_id, role_name, eff_date, exp_date from role_master"; 
	
	/** Query to insert the user details */
	private static final String INSERT_USER_MASTER = 
		"INSERT INTO user_master ( user_id, user_name, telephone_no, email_id, " +
		"eff_date, exp_date, created_on, created_by, password," +
		"admin_role, connect_role) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/** Query to insert the user installation role details */
	private static final String INSERT_USER_INSTALLATION_ROLE = 
		"INSERT INTO user_installation_role ( user_id, installation_code, role_id) VALUES (?, ?, ?)";
	
	/** Query to get role details */
	private static final String GET_USER_INSTALLATION_ROLE_DETAILS = 
		" SELECT user_id, installation_code, role_id FROM user_installation_role " +
		" WHERE user_id = ? " ;
//		" UNION ALL " +
//		" SELECT user_id, '', 'ADMIN'" +
//		" FROM USER_MASTER " +
//		" WHERE user_id = nvl(?, user_id) " +
//		" AND ADMIN_ROLE = 'Y' " +
//		" UNION ALL " +
//		" SELECT user_id, '', 'CONNECT'" +
//		" FROM USER_MASTER " +
//		" WHERE user_id = nvl(?, user_id) " +
//		" AND CONNECT_ROLE = 'Y'";
	
	/** Query to get user details */
	private static final String USER_AUTHENTICATION = 
		"SELECT user_id, user_name, telephone_no, fax_no, email_id, " +
		"		eff_date, exp_date, created_on, created_by, " +
		"		hint_question, hint_answer, password, " +
		"		force_password_flag, admin_role, connect_role, " +
		"		default_view " +
		"FROM user_master " +
		"WHERE user_id = ? " +
		"AND password = ? " +
		"AND eff_date <= getdate() " +
		"AND exp_date >= getdate()";
	
	/** Query to get user details */
	private static final String GET_USER_DETAILS = 
		"SELECT user_id, user_name, telephone_no, fax_no, email_id, " +
		"		eff_date, exp_date, created_on, created_by, " +
		"		hint_question, hint_answer, password, " +
		"		force_password_flag, admin_role, connect_role," +
		"		default_view " +
		"FROM user_master " +
		"WHERE user_id = ? ";
	
	/** Query to delete the details from user_installation_role table */
	private static final String DELETE_USER_INSTALLATION_ROLE_DETAILS = 
		"DELETE FROM user_installation_role WHERE user_id = ?";
	
	/** Query to update the user details by admin */
	private static final String UPDATE_USER_MASTER_BY_ADMIN = 
		"UPDATE user_master SET user_name= ?, telephone_no = ?, " +
		"		email_id = ?, force_password_flag = ?, admin_role = ?, " +
		"		connect_role = ?,eff_date = ?, exp_date = ?, " +
		"		modified_by = ?, modified_on = ?, password = ? " +
		"WHERE user_id = ?";
	
	/** Query to update the user details*/
	private static final String UPDATE_USER_MASTER = 
		"UPDATE user_master SET user_name= ?, telephone_no = ?, " +
		"		email_id = ?, hint_question = ?, hint_answer = ?, " +
		"		modified_by = ?, modified_on = ?, default_view = ?" +
		"		WHERE user_id = ?";
	
	/** Query to reset user password */
	private static final String CHANGE_USER_PASSWORD =
		"UPDATE user_master " +
		"SET password = ?, force_password_flag = 'N' " +
		"WHERE user_id = ? and password = ? " ;
		
	/** Query to reset user password */
	private static final String RESET_USER_PASSWORD =
		"UPDATE user_master " +
		"SET password = ?, force_password_flag = 'Y' " +
		"WHERE user_id = ?" ;
	
	/** Query to get installation data */
	private static final String GET_INSTALLATION_DATA = 
		"SELECT installation_code, installation_desc, eff_date, exp_date, " +
		"		created_on, created_by,modified_on, modified_by, batch_no, batch_rev_no," +
		"		timezone_id " +
		"FROM installation";

	/** Query to get user details */
	private static final String GET_SECURITY_DETAILS = 
		"SELECT user_id, hint_question, hint_answer, password " +
		"FROM user_master " +
		"WHERE user_id = ? ";
//		+ "AND hint_answer = NVL(?, hint_answer)";

	/** Public constructor takes no argument */
	public UserDAO() {
		super();
	}

	/**
	 * Authenticates the user and gets the user details for the supplied user
	 * credentials
	 * 
	 * @param userCredentials
	 *            The user id and user password.
	 * @param connection
	 * 			  connection object
	 * 
	 * @return the user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public UserDetails userAuthentication(UserCredentials userCredentials, Connection connection)
			throws CommDatabaseException {
		UserDetails userDetails = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = connection.prepareStatement(USER_AUTHENTICATION);
			pstmt.setString(1, userCredentials.getUserId());
			pstmt.setString(2, userCredentials.getPassword());
			rs = pstmt.executeQuery();
			userDetails = ResultSetMapper.getInstance().mapSingleRecord(rs,
					UserDetails.class);
			return userDetails;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
	
	/**
	 * Generates role data list.
	 * 
	 * @param connection
	 * 			  connection object
	 * 
	 * @return list of roles
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<RoleData> getRoleData(Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {			
			pstmt = connection.prepareStatement(GET_ROLE_DATA);
			rs = pstmt.executeQuery();
	
			List<RoleData> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, RoleData.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}
    
	/**
	 * Generates user installation role details list
	 * 
	 * @param reqUserDetailsVO
	 * 			The request object contains user and installation details
	 * @param connection
	 * 			The connection reference
	 * 
	 * @return user installation list
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<UserInstallationRole> getUserInstallationRoleData(ReqUserDetailsVO reqUserDetailsVO, Connection connection) 
										throws CommDatabaseException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {			
			pstmt = connection.prepareStatement(GET_USER_INSTALLATION_ROLE_DETAILS);
			if(reqUserDetailsVO == null || reqUserDetailsVO.getUserProfile() == null) {
				pstmt.setObject(1, null);
//				pstmt.setObject(2, null);
//				pstmt.setObject(3, null);
			}else {
				pstmt.setObject(1, reqUserDetailsVO.getUserProfile().getUserId());				
//				pstmt.setObject(2, reqUserDetailsVO.getUserProfile().getUserId());				
//				pstmt.setObject(3, reqUserDetailsVO.getUserProfile().getUserId());				
			}
			rs = pstmt.executeQuery();
	
			List<UserInstallationRole> list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, UserInstallationRole.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
		
	}
	/**
	 * Generates user details 
	 * 
	 * @param userProfile
	 * 			The instance of UserProfile contains user id
	 * 
	 * @param connection
	 * 			The connection reference
	 * 
	 * @return user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public UserProfile getUserDetails(UserProfile userProfile, Connection connection) 
	throws CommDatabaseException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {			
			pstmt = connection.prepareStatement(GET_USER_DETAILS);
			pstmt.setObject(1, userProfile.getUserId());				
			rs = pstmt.executeQuery();			
			userProfile = ResultSetMapper.getInstance().mapSingleRecord(rs, UserProfile.class);
			return userProfile;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
		
	}
	
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
			throws CommDatabaseException {

		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(INSERT_USER_MASTER);
			pstmt.setObject(1, userProfile.getUserId());
			pstmt.setObject(2, userProfile.getUserName());
			pstmt.setObject(3, userProfile.getTelephoneNo());
			pstmt.setObject(4, userProfile.getEmailId());
			pstmt.setDate(5, new java.sql.Date(userProfile.getEffDate()));
			
			if(userProfile.getExpDate() == null) {
				pstmt.setDate(6, null);
			}else {
				pstmt.setDate(6, new java.sql.Date(userProfile.getExpDate()));
			}
			pstmt.setObject(7, new java.sql.Date(userProfile.getCreatedOn()));
			pstmt.setObject(8, userProfile.getCreatedBy());
			pstmt.setObject(9, userProfile.getPassword());
			pstmt.setObject(10, userProfile.getAdminRole());
			pstmt.setObject(11, userProfile.getConnectRole());
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}

	/**
	 * Updates user details in USER_MASTER table
	 * 
	 * @param userProfile
	 *            The reference of user data
	 * 
	 * @param connection
	 *            connection reference
	 * 
	 * @return 1 if the record was inserted successfully 0 otherwise
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer updateUser(UserProfile userProfile, Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_USER_MASTER);
			pstmt.setObject(1, userProfile.getUserName());
			pstmt.setObject(2, userProfile.getTelephoneNo());
			pstmt.setObject(3, userProfile.getEmailId());
			pstmt.setObject(4, userProfile.getHintQuestion());
			pstmt.setObject(5, userProfile.getHintAnswer());
			pstmt.setObject(6, userProfile.getCreatedBy()); // Modified by
			pstmt.setDate(7, new java.sql.Date(userProfile.getCreatedOn())); // Modified on
			pstmt.setObject(8, userProfile.getDefaultView());
			pstmt.setObject(9, userProfile.getUserId());			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}

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
	public Integer updateUserByAdmin(UserProfile userProfile, Connection connection) throws CommDatabaseException {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(UPDATE_USER_MASTER_BY_ADMIN);
			pstmt.setObject(1, userProfile.getUserName());
			pstmt.setObject(2, userProfile.getTelephoneNo());
			pstmt.setObject(3, userProfile.getEmailId());
			pstmt.setObject(4, userProfile.getForcePasswordFlag());
			pstmt.setObject(5, userProfile.getAdminRole());
			pstmt.setObject(6, userProfile.getConnectRole());
			
			
			if (userProfile.getEffDate() == null) {
				pstmt.setDate(7, null);
			} else {
				pstmt.setDate(7,
						new java.sql.Date(userProfile.getEffDate()));
			}
			if (userProfile.getExpDate() == null) {
				pstmt.setDate(8, null);
			} else {
				pstmt.setDate(8,
						new java.sql.Date(userProfile.getExpDate()));
			}
			pstmt.setObject(9, userProfile.getCreatedBy()); // Modified by
			pstmt.setDate(10, new java.sql.Date(userProfile.getCreatedOn())); // Modified on
			pstmt.setObject(11, userProfile.getPassword());		
			pstmt.setObject(12, userProfile.getUserId());			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
		
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
			throws CommDatabaseException {

		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(INSERT_USER_INSTALLATION_ROLE);
			for (UserInstallationRole userInstallationRole: userInstallationRoles) {
				pstmt.setObject(1, userInstallationRole.getUserId());
				pstmt.setObject(2, userInstallationRole.getInstallationCode());
				pstmt.setObject(3, userInstallationRole.getRoleId());				
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			return 1;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
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
	throws CommDatabaseException {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(DELETE_USER_INSTALLATION_ROLE_DETAILS);
			pstmt.setObject(1, userProfile.getUserId());
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
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
	public Integer changePassword(UserCredentials userCredentials, Connection connection) throws CommDatabaseException{
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(CHANGE_USER_PASSWORD);
			pstmt.setString(1, userCredentials.getNewPassword());
			pstmt.setString(2, userCredentials.getUserId());
			pstmt.setString(3, userCredentials.getPassword());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Resets the password for the provided user data. 
	 * 
	 * @param userCredentials
	 * 			The instance of {@link UserCredentials} which contains new password.
	 * 
	 * @param connection
	 * 			The connection object
	 * 
	 * @return 1 if the record was updated successfully
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public Integer resetPassword(UserCredentials userCredentials, Connection connection) throws CommDatabaseException{
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(RESET_USER_PASSWORD);
			pstmt.setString(1, userCredentials.getNewPassword());
			pstmt.setString(2, userCredentials.getUserId());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(null, pstmt, null);
		}
	}
	
	/**
	 * Gets the list of installations in JBEAM.
	 * 
	 * @return the list of installations
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	public List<Installation> getInstallationsList(Connection connection)
			throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Installation> list;
		try {
			pstmt = connection.prepareStatement(GET_INSTALLATION_DATA);
			rs = pstmt.executeQuery();
			list = ResultSetMapper.getInstance()
					.mapMultipleRecords(rs, Installation.class);
			return list;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

	/**
	 * Generates security details that is hint question and hint answer with
	 * user id and password.
	 * 
	 * @param userProfile
	 *            The user details
	 * 
	 * @param connection
	 *            The connection object
	 * 
	 * @return user details
	 * 
	 * @throws CommDatabaseException
	 *             Any database I/O related exception occurred
	 */
	
	public UserProfile getSecurityDetails(UserProfile userProfile,
			Connection connection) throws CommDatabaseException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {			
			pstmt = connection.prepareStatement(GET_SECURITY_DETAILS);
			pstmt.setObject(1, userProfile.getUserId());				
			rs = pstmt.executeQuery();			
			userProfile = ResultSetMapper.getInstance().mapSingleRecord(rs, UserProfile.class);
			return userProfile;
		} catch (Exception e) {
			throw new CommDatabaseException(e);
		} finally {
			releaseResources(rs, pstmt, null);
		}
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/dao/impl/UserDAO.java                                                            $
 * 
 * 13    7/13/10 3:29p Lakshmanp
 * Added new DAO method and query to resetPassword 
 * Added a helper method getEncryptedPassword.
 * Modified the query GET_USER_DETAILS by adding password in WHERE clause.
 * Moved the userAuthentication method from MonitorDAO.
 * 
 * 12    7/10/10 2:15p Mandar.vaidya
 * Changed return type of manageUser to Integer.
 * 
 * 11    7/08/10 3:33p Mandar.vaidya
 * Removed fax_no from query INSERT_USER_MASTER and modified the method addUser for the same
 * Added queries DELETE_USER_INSTALLATION_ROLE_DETAILS, UPDATE_USER_MASTER with methods deleteUserInstallationRoleDetails and updateUser
 * Renamed the method addUserInstallationDetails as addUserInstallationRoleDetails and modified its javadoc comments.
 * Renamed the method createUser as manageUser and modified its functionality as well as the javadoc comments for this method.
 * 
 * 10    7/07/10 5:30p Mandar.vaidya
 * Modified the query GET_USER_INSTALLATION_ROLE_DETAILS.
 * Added the new query GET_USER_DETAILS.
 * Added new method getUserDetails()
 * 
 * 9     7/06/10 3:00p Lakshmanp
 * removed sendEmail()
 * 
 * 8     7/02/10 3:41p Lakshmanp
 * Modified addUser method for password encryption and removed unwanted code.
 * 
 * 7     7/01/10 5:29p Lakshmanp
 * Changed to get random password
 * 
 * 6     7/01/10 4:55p Lakshmanp
 * added getUserInstallationRoleData method
 * 
 * 5     6/30/10 3:08p Lakshmanp
 * Added DAO method to create users
 * 
 * 4     6/25/10 3:53p Lakshmanp
 * added InstallationEntity parameter to get role details
 * 
 * 3     6/25/10 2:07p Lakshmanp
 * added get role details method.
 * 
 * 2     6/23/10 11:10a Lakshmanp
 * removed parameterized constructor
 * 
 * 1     6/22/10 10:49a Lakshmanp
 * Initial Version
*
*
*/