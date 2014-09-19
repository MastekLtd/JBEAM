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
package com.stgmastek.core.common;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

/**
 * Super class for all the connection classes with the system.
 * 
 * This class implements {@link java.sql.Connection}.
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 * 
 */
public abstract class CConnection implements java.sql.Connection {
		
	/** The connection object retrieved from the PRE */	
	protected Connection connection = null;

	/**
	 * Public constructor that takes the connection supplied
	 * 
	 * @param connection
	 * 		  The connection passed in
	 */
	public CConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * @see Connection#clearWarnings()
	 */
	public void clearWarnings() throws SQLException {
		connection.clearWarnings();
	}

	/**
	 * @see Connection#close()
	 */	
	public void close() throws SQLException {
		connection.close();
	}

	/**
	 * @see Connection#commit()
	 */
	public void commit() throws SQLException {
		connection.commit();
	}

	/**
	 * @see Connection#createArrayOf(String, Object[])
	 */
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		return connection.createArrayOf(typeName, elements);
	}

	/**
	 * @see Connection#createBlob()
	 */
	public Blob createBlob() throws SQLException {

		return connection.createBlob();
	}

	/**
	 * @see Connection#createClob()
	 */
	public Clob createClob() throws SQLException {

		return connection.createClob();
	}

	/**
	 * @see Connection#createNClob()
	 */
	public NClob createNClob() throws SQLException {

		return connection.createNClob();
	}

	/**
	 * @see Connection#createSQLXML()
	 */
	public SQLXML createSQLXML() throws SQLException {

		return connection.createSQLXML();
	}

	/**
	 * @see Connection#createStatement()
	 */
	public Statement createStatement() throws SQLException {

		return connection.createStatement();
	}

	/**
	 * @see Connection#createStatement(int, int, int)
	 */
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return connection.createStatement(resultSetType, resultSetConcurrency,
				resultSetHoldability);
	}

	/**
	 * @see Connection#createStatement(int, int)
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {

		return connection.createStatement(resultSetType, resultSetConcurrency);
	}

	/**
	 * @see Connection#createStruct(String, Object[])
	 */
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {

		return connection.createStruct(typeName, attributes);
	}

	/**
	 * @see Connection#getAutoCommit()
	 */
	public boolean getAutoCommit() throws SQLException {

		return connection.getAutoCommit();
	}

	/**
	 * @see Connection#getCatalog()
	 */
	public String getCatalog() throws SQLException {

		return connection.getCatalog();
	}

	/**
	 * @see Connection#getClientInfo()
	 */
	public Properties getClientInfo() throws SQLException {

		return connection.getClientInfo();
	}

	/**
	 * @see Connection#getClientInfo(String)
	 */
	public String getClientInfo(String name) throws SQLException {

		return connection.getClientInfo(name);
	}

	/**
	 * @see Connection#getHoldability()
	 */
	public int getHoldability() throws SQLException {

		return connection.getHoldability();
	}

	/**
	 * @see Connection#getMetaData()
	 */
	public DatabaseMetaData getMetaData() throws SQLException {

		return connection.getMetaData();
	}

	/**
	 * @see Connection#getTransactionIsolation()
	 */
	public int getTransactionIsolation() throws SQLException {

		return connection.getTransactionIsolation();
	}

	/**
	 * @see Connection#getTypeMap()
	 */
	public Map<String, Class<?>> getTypeMap() throws SQLException {

		return connection.getTypeMap();
	}

	/**
	 * @see Connection#getWarnings()
	 */
	public SQLWarning getWarnings() throws SQLException {

		return connection.getWarnings();
	}

	/**
	 * @see Connection#isClosed()
	 */
	public boolean isClosed() throws SQLException {

		return connection.isClosed();
	}

	/**
	 * @see Connection#isReadOnly()
	 */
	public boolean isReadOnly() throws SQLException {

		return connection.isReadOnly();
	}

	/**
	 * @see Connection#isValid(int)
	 */
	public boolean isValid(int timeout) throws SQLException {

		return connection.isValid(timeout);
	}

	/**
	 * @see Connection#nativeSQL(String)
	 */
	public String nativeSQL(String sql) throws SQLException {

		return connection.nativeSQL(sql);
	}

	/**
	 * @see Connection#prepareCall(String, int, int, int)
	 */
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {

		return connection.prepareCall(sql, resultSetType, resultSetConcurrency,
				resultSetHoldability);
	}

	/**
	 * @see Connection#prepareCall(String, int, int)
	 */
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {

		return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	/**
	 * @see Connection#prepareCall(String)
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {

		return connection.prepareCall(sql);
	}

	/**
	 * @see Connection#prepareStatement(String, int, int, int)
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {

		return connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	/**
	 * @see Connection#prepareStatement(String, int, int)
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {

		return connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency);
	}

	/**
	 * @see Connection#prepareStatement(String, int)
	 */
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {

		return connection.prepareStatement(sql, autoGeneratedKeys);
	}

	/**
	 * @see Connection#prepareStatement(String, int[])
	 */
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {

		return connection.prepareStatement(sql, columnIndexes);
	}

	/**
	 * @see Connection#prepareStatement(String, String[])
	 */
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {

		return connection.prepareStatement(sql, columnNames);
	}

	/**
	 * @see Connection#prepareStatement(String)
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {

		return connection.prepareStatement(sql);
	}

	/**
	 * @see Connection#releaseSavepoint(Savepoint)
	 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		connection.releaseSavepoint(savepoint);
	}

	/**
	 * @see Connection#rollback()
	 */
	public void rollback() throws SQLException {
		connection.rollback();
	}

	/**
	 * @see Connection#rollback(Savepoint)
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		connection.rollback(savepoint);
	}

	/**
	 * @see Connection#setAutoCommit(boolean)
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);
	}

	/**
	 * @see Connection#setCatalog(String)
	 */
	public void setCatalog(String catalog) throws SQLException {
		connection.setCatalog(catalog);
	}

	/**
	 * @see Connection#setClientInfo(Properties)
	 */
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		connection.setClientInfo(properties);
	}

	/**
	 * @see Connection#setClientInfo(String, String)
	 */
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		connection.setClientInfo(name, value);
	}

	/**
	 * @see Connection#setHoldability(int)
	 */
	public void setHoldability(int holdability) throws SQLException {
		connection.setHoldability(holdability);
	}

	/**
	 * @see Connection#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean readOnly) throws SQLException {
		connection.setReadOnly(readOnly);
	}

	/**
	 * @see Connection#setSavepoint()
	 */
	public Savepoint setSavepoint() throws SQLException {

		return connection.setSavepoint();
	}

	/**
	 * @see Connection#setSavepoint(String)
	 */
	public Savepoint setSavepoint(String name) throws SQLException {

		return connection.setSavepoint(name);
	}

	/**
	 * @see Connection#setTransactionIsolation(int)
	 */
	public void setTransactionIsolation(int level) throws SQLException {
		connection.setTransactionIsolation(level);
	}

	/**
	 * @see Connection#setTypeMap(Map)
	 */
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		connection.setTypeMap(map);
	}

	/**
	 * @see Connection#isWrapperFor(Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return connection.isWrapperFor(iface);
	}

	/**
	 * @see Connection#unwrap(Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {

		return connection.unwrap(iface);
	}

	/**
	 * The mechanism to release the connection. 
	 * All implementing classes must over ride this method and provide a 
	 * mechanism to release the connection. 
	 * 
	 * @throws SQLException
	 * 		   The SQLException that may be thrown for activities related to releasing 
	 * 	       the connection
	 */
	public abstract void releaseConnection() throws SQLException;
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/common/CConnection.java                                                                                  $
 * 
 * 6     4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 5     3/09/10 12:20p Kedarr
 * Implemented java sql connection interface.
 * 
 * 4     3/05/10 11:31a Kedarr
 * Removed recursive calls as reported by findbugs. The connection dot was missing in these two methods.
 * 
 * 3     12/17/09 12:26p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/