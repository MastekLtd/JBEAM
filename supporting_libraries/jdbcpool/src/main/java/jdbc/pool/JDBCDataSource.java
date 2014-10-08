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
 *
 *
 * $Revision: 2 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/JDBCDataSource.java 2     9/03/09 12:16p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/JDBCDataSource.java $
 * 
 * 2     9/03/09 12:16p Kedarr
 * Updated javadoc.
 * 
 * 1     8/28/09 11:37a Kedarr
 * Implemented the default data source for JDBCPool.
 * 
 */
package jdbc.pool;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Default implementation of the DataSource for JDBCPool.
 * 
 * This class just exposes {@link #getConnection()} method.
 * 
 * @version $Revision: 2 $
 * @author Kedar C. Raybagkar
 * @since  16.03
 */
public class JDBCDataSource implements DataSource {

    /**
     * Stores the connection pool to which this data source belongs.
     */
    private ConnectionPool pool_;

    /**
     * Creates a datasource around the pool.
     * 
     * @param pool
     */
    protected JDBCDataSource(ConnectionPool pool) {
        this.pool_ = pool;
    }
    
    /* (non-Javadoc)
     * @see javax.sql.DataSource#getConnection()
     */
    public Connection getConnection() throws SQLException {
        try {
            return pool_.getConnection();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    /* (non-Javadoc)
     * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
     */
    public Connection getConnection(String username, String password)
            throws SQLException {
        throw new SQLException("Unimplemented Method");
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
        //
    }

    /* (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    public void setLoginTimeout(int seconds) throws SQLException {
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    /* (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    @SuppressWarnings("unchecked")
	public Object unwrap(Class arg0) throws SQLException {
        return null;
    }

}
