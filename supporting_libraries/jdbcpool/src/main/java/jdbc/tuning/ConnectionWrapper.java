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
 * $Revision: 17 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/ConnectionWrapper.java 17    9/28/09 10:28a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/ConnectionWrapper.java $
 * 
 * 17    9/28/09 10:28a Kedarr
 * Caught specific exceptions instead of a generic exception block.
 * 
 * 16    1/29/09 9:58p Kedarr
 * Minor change to add static to final variables.
 * 
 * 15    1/26/09 5:39p Kedarr
 * Implemented Oracle JDBC Driver 10.2.0.4
 * 
 * 14    6/03/08 1:56p Kedarr
 * Implemented 10.2.0.4 Oracle JDBC Driver
 * 
 * 13    3/17/08 12:28p Kedarr
 * 
 * 12    3/20/07 10:16a Kedarr
 * New methods addQuery and removeQuery added that stores the currently
 * running queries in the pool.
 * 
 * 11    3/14/07 10:49a Kedarr
 * Changes made to capture the leaked ResultSet count
 * 
 * 10    3/02/07 11:18a Kedarr
 * Added comment.
 * 
 * 9     3/02/07 9:40a Kedarr
 * Added isActive() method.
 * 
 * 8     5/02/06 4:34p Kedarr
 * Changes made for returning the cached Open Statement, if any, if the
 * statements are not closed during the execution of the program.
 * 
 * 7     1/16/06 1:17p Kedarr
 * Added Source Safe header. This will enable us to track the revision
 * number of the source from the compiled binary files.
 * 
 * 6     10/26/05 11:47a Kedarr
 * Changes made to display the Connection Info as well as to get the
 * appropriate timelimit for critical sql operation.
 * 
 * 5     9/02/05 10:19a Kedarr
 * Changes made in the logger category. Instead of JDBC.<pool name> it
 * would display JDBC.Pool[<pool name>].
 * 
 * 4     6/30/05 11:05a Kedarr
 * Changes made to appropriately send a boolean variable in the close
 * method when the finalizer method is called.
 * 
 * 3     6/07/05 12:46p Kedarr
 * Replaced this file with AdvancedPRE file and made compliant with Oracle
 * 9.2.0.6 version classes12.zip.
 * 
 * 15    6/03/05 5:55p Kedarr
 * Same for LEAK_EXCEPTION while creating exceptions and added to log
 * 
 * 14    6/03/05 5:09p Kedarr
 * Removed OPEN_EXCEPTION while creating exceptions and added to log
 * 
 * 13    6/03/05 5:04p Kedarr
 * Added OPEN_EXCEPTION while creating exceptions.
 * 
 * 12    6/01/05 12:04p Kedarr
 * Changed the name of the logger from just JDBC to JDBC.<pool name>
 * 
 * 11    5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 10    1/25/05 10:40a Kedarr
 * Changes made to add Pool Name in the JDBC Exception logs for Connection
 * Leak and Statement Leak.
 * 
 * 9     1/24/05 12:21p Kedarr
 * Changing the message for Open_Exception and added this message in the
 * exception created for the same.
 * 
 * 8     1/20/05 4:10p Kedarr
 * Changing the method closeAllOpenStatements()
 * 
 * 7     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.2  2006/02/14 06:16:39  kedar
* Changes made for Logging Exceptions to System.err stream
*
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 5     3/19/04 4:31p Kedarr
 * Changes made for isClosed() method for not throwing the exception
* Revision 1.6  2004/03/18 12:16:31  kedar
* Changes made to reflect the boolean variable for isClosed method.
*
* Revision 1.5  2004/01/16 13:22:40  kedar
* Changes made for the wrong if condition placed within the finalizer 
* method and also for the messaging for.
*
 * 
 * 3     1/02/04 12:07p Kedarr
 * Changes made for wrapping the text in the Final Variable
* Revision 1.4  2004/01/02 06:15:49  kedar
* Changed the final variable for wrapping the big texts
*
* Revision 1.3  2003/12/31 08:00:42  kedar
* Changes made in the exception message for OPEN_EXCEPTION variable.
*
 * 
 * 2     12/30/03 4:39p Kedarr
* Revision 1.2  2003/12/30 08:00:58  kedar
* Changes made to handle Open Statement exceptions
*
 * 
 * 1     12/01/03 1:35p Kedarr
* Revision 1.1  2003/11/28 09:47:21  kedar
* Added a new package for Tunning SQL queries.
*
* 
*/
package jdbc.tuning;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import jdbc.pool.CObjectWrapper;
import jdbc.pool.ConnectionPool;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleOCIFailover;
import oracle.jdbc.OracleSavepoint;
import oracle.jdbc.pool.OracleConnectionCacheCallback;

import org.apache.log4j.Logger;

/**
 * This is a wrapper class for java.sql.Connection.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 17 $
 *
 */
public class ConnectionWrapper implements Connection, OracleConnection
{
    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 17        $";

    /**
     * Returns the revision number of the .class.
     *
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }
    
    protected Connection realConnection;
    private boolean bClosed;
    
    private boolean bGarbageCollected = false;
    
    private static final String LEAK_EXCEPTION = 
        "A JDBC pool connection leak was detected. A Connection leak occurs " +
        "when a connection obtained from the pool was not closed explicitly by " +
        "calling close() and then was disposed by the garbage collector and " +
        "returned to the connection pool. The following stack trace at create " +
        "shows where the leaked connection was created.  Stack trace at " +
        "connection create:";
    
    private static final String OPEN_EXCEPTION =
        "A JDBC Statement leak was detected. Statment leak occurs " +
        "when a statement is created and not explicitly closed before freeing " +
        "the JDBC connection to the connection pool. The connection object has " +
        "closed the open statement. The following stack trace at create " +
        "shows where the statement was created along with the query.  Stack " +
        "trace at statement create:\n ";

    private Throwable leakException_;
    
    private ConnectionPool pool_;
    
    private ConcurrentHashMap<Statement, OpenStatementException> hmOpenStatements_;
    
    private Logger logger_;
    
    private volatile int iLeakedStatementCount_;
    
    protected String strLoggerName_;
    
    public Connection realConnection()
    {
        return realConnection;
    }

    public ConnectionWrapper(ConnectionPool ppool, Connection connection) throws SQLException
    {
        realConnection = connection;
        bClosed = realConnection.isClosed();
        hmOpenStatements_ = new ConcurrentHashMap<Statement, OpenStatementException>();
        pool_  = ppool;
        strLoggerName_ = "JDBC.Pool[" + pool_.getPoolName() + "]";
        leakException_ =
            new ConnectionLeakException();
        logger_ = Logger.getLogger(strLoggerName_);
        iLeakedStatementCount_ = 0;
    }

    public Statement createStatement() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        Statement st = new StatementWrapper(realConnection.createStatement(), this, null);
        hmOpenStatements_.put(st, cose);
        return st;
    }

    public Statement createStatement(
        int resultSetType,
        int resultSetConcurrency)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        StatementWrapper st = new StatementWrapper(
            realConnection.createStatement(resultSetType, resultSetConcurrency),
            this, null);
        hmOpenStatements_.put(st, cose);
        return st;
    }

    public CallableStatement prepareCall(String sql) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        CallableStatementWrapper wrapper = (CallableStatementWrapper) getOpenStatement(sql);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        CallableStatementWrapper cst =
        new CallableStatementWrapper(
            realConnection.prepareCall(sql),
            this,
            sql);
        hmOpenStatements_.put(cst, cose);
        return cst;
    }

    public CallableStatement prepareCall(
        String sql,
        int resultSetType,
        int resultSetConcurrency)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        CallableStatementWrapper wrapper = (CallableStatementWrapper) getOpenStatement(sql);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        CallableStatementWrapper cst = new CallableStatementWrapper(
            realConnection.prepareCall(
                sql,
                resultSetType,
                resultSetConcurrency),
            this,
            sql);
        hmOpenStatements_.put(cst,cose);
        return cst;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(sql);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        PreparedStatementWrapper psm = new PreparedStatementWrapper(
            realConnection.prepareStatement(sql),
            this,
            sql);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    public PreparedStatement prepareStatement(
        String sql,
        int resultSetType,
        int resultSetConcurrency)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(sql);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(
            realConnection.prepareStatement(
                sql,
                resultSetType,
                resultSetConcurrency),
            this,
            sql);
        hmOpenStatements_.put(psm, cose);
        return psm; 
    }

    public DatabaseMetaData getMetaData() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        return new DatabaseMetaDataWrapper(realConnection.getMetaData(), this);
    }

    public void clearWarnings() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.clearWarnings();
    }

    public void close() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        closeAllOpenStatements();
        pool_.releaseConnection(realConnection, bGarbageCollected, iLeakedStatementCount_);
        bClosed = true;
    }

    public void commit() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.commit();
    }

    public boolean getAutoCommit() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getAutoCommit();
    }

    public String getCatalog() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getCatalog();
    }

    public int getTransactionIsolation() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getTransactionIsolation();
    }

    public Map getTypeMap() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getWarnings();
    }

    public boolean isClosed() throws SQLException
    {
        return bClosed;
    }

    public boolean isReadOnly() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.isReadOnly();
    }

    public String nativeSQL(String sql) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.nativeSQL(sql);
    }

    public void rollback() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.rollback();
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setAutoCommit(autoCommit);
    }

    public void setCatalog(String catalog) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setCatalog(catalog);
    }

    public void setReadOnly(boolean readOnly) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setReadOnly(readOnly);
    }

    public void setTransactionIsolation(int level) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setTransactionIsolation(level);
    }

    public void setTypeMap(Map map) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setTypeMap(map);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setHoldability(int)
     */
    public void setHoldability(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.setHoldability(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#getHoldability()
     */
    public int getHoldability() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.getHoldability();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSavepoint()
     */
    public Savepoint setSavepoint() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.setSavepoint();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#setSavepoint(java.lang.String)
     */
    public Savepoint setSavepoint(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        return realConnection.setSavepoint(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#rollback(java.sql.Savepoint)
     */
    public void rollback(Savepoint arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.rollback();
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
     */
    public void releaseSavepoint(Savepoint arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        realConnection.releaseSavepoint(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#createStatement(int, int, int)
     */
    public Statement createStatement(int arg0, int arg1, int arg2)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        StatementWrapper st = new StatementWrapper(realConnection.createStatement(arg0, arg1, arg2), this, "");
        hmOpenStatements_.put(st, cose);
        return st;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
     */
    public PreparedStatement prepareStatement(
        String arg0,
        int arg1,
        int arg2,
        int arg3)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(realConnection.prepareStatement(arg0, arg1, arg2, arg3), this, arg0);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareCall(java.lang.String, int, int, int)
     */
    public CallableStatement prepareCall(
        String arg0,
        int arg1,
        int arg2,
        int arg3)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        CallableStatementWrapper wrapper = (CallableStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        CallableStatementWrapper cst = new CallableStatementWrapper(realConnection.prepareCall(arg0, arg1, arg2, arg3), this, arg0);
        hmOpenStatements_.put(cst, cose);
        return cst;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int)
     */
    public PreparedStatement prepareStatement(String arg0, int arg1)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(realConnection.prepareStatement(arg0, arg1), this, arg0);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, int[])
     */
    public PreparedStatement prepareStatement(String arg0, int[] arg1)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(realConnection.prepareStatement(arg0, arg1), this, arg0);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    /* (non-Javadoc)
     * @see java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
     */
    public PreparedStatement prepareStatement(String arg0, String[] arg1)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(realConnection.prepareStatement(arg0, arg1), this, arg0);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    protected void finalize() throws Throwable
    {
        try
        {
            if (!bClosed)
            {
                bGarbageCollected = true;
                this.close();
                logger_.warn(LEAK_EXCEPTION, leakException_);
            }
        }
        finally
        {
            super.finalize();
        }
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#archive(int, int, java.lang.String)
     */
    public void archive(int arg0, int arg1, String arg2) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).archive(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getAutoClose()
     */
    public boolean getAutoClose() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getAutoClose();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getDefaultExecuteBatch()
     */
    public int getDefaultExecuteBatch()
    {
        if ((realConnection instanceof OracleConnection))
        {
            return ((OracleConnection) realConnection).getDefaultExecuteBatch();
        }
        return -1;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getDefaultRowPrefetch()
     */
    public int getDefaultRowPrefetch()
    {
        return ((OracleConnection) realConnection).getDefaultRowPrefetch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getDescriptor(java.lang.String)
     */
    public Object getDescriptor(String arg0)
    {
        return ((OracleConnection) realConnection).getDescriptor(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getIncludeSynonyms()
     */
    public boolean getIncludeSynonyms()
    {
        return ((OracleConnection) realConnection).getIncludeSynonyms();
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#getJavaObject(java.lang.String)
     */
    public Object getJavaObject(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getJavaObject(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getRemarksReporting()
     */
    public boolean getRemarksReporting()
    {
        return ((OracleConnection) realConnection).getRemarksReporting();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getRestrictGetTables()
     */
    public boolean getRestrictGetTables()
    {
        return ((OracleConnection) realConnection).getRestrictGetTables();
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#getSQLType(java.lang.Object)
     */
    public String getSQLType(Object arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getSQLType(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#getStmtCacheSize()
     */
    public int getStmtCacheSize()
    {
        return ((OracleConnection) realConnection).getStmtCacheSize();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getStructAttrCsId()
     */
    public short getStructAttrCsId() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getStructAttrCsId();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getUserName()
     */
    public String getUserName() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getUserName();
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#getUsingXAFlag()
     */
    public boolean getUsingXAFlag()
    {
        return ((OracleConnection) realConnection).getUsingXAFlag();
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#getXAErrorFlag()
     */
    public boolean getXAErrorFlag()
    {
        return ((OracleConnection) realConnection).getXAErrorFlag();
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#prepareCallWithKey(java.lang.String)
     */
    public CallableStatement prepareCallWithKey(String arg0)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        leakException_.fillInStackTrace();
        CallableStatementWrapper wrapper = (CallableStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        CallableStatementWrapper cst = new CallableStatementWrapper(
            ((OracleConnection) realConnection).prepareCallWithKey(arg0),
            this,
            arg0);
        hmOpenStatements_.put(cst, cose);
        return cst;
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#prepareStatementWithKey(java.lang.String)
     */
    public PreparedStatement prepareStatementWithKey(String arg0)
        throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        leakException_.fillInStackTrace();
        PreparedStatementWrapper wrapper = (PreparedStatementWrapper) getOpenStatement(arg0);
        if (wrapper != null) {
            wrapper.clearParameters();
            return wrapper;
        }
        logger_.debug("Creating a new JDBC statement");
        OpenStatementException cose = new OpenStatementException();
        cose.fillInStackTrace();
        PreparedStatementWrapper psm = new PreparedStatementWrapper(
            ((OracleConnection) realConnection).prepareStatementWithKey(arg0),
            this,
            arg0);
        hmOpenStatements_.put(psm, cose);
        return psm;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#putDescriptor(java.lang.String, java.lang.Object)
     */
    public void putDescriptor(String arg0, Object arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).putDescriptor(arg0, arg1);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#registerSQLType(java.lang.String, java.lang.Class)
     */
    public void registerSQLType(String arg0, Class arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).registerSQLType(arg0, arg1);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#registerSQLType(java.lang.String, java.lang.String)
     */
    public void registerSQLType(String arg0, String arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).registerSQLType(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setAutoClose(boolean)
     */
    public void setAutoClose(boolean arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setAutoClose(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setDefaultExecuteBatch(int)
     */
    public void setDefaultExecuteBatch(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setDefaultExecuteBatch(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setDefaultRowPrefetch(int)
     */
    public void setDefaultRowPrefetch(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setDefaultRowPrefetch(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setIncludeSynonyms(boolean)
     */
    public void setIncludeSynonyms(boolean arg0)
    {
        ((OracleConnection) realConnection).setIncludeSynonyms(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setRemarksReporting(boolean)
     */
    public void setRemarksReporting(boolean arg0)
    {
        ((OracleConnection) realConnection).setRemarksReporting(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setRestrictGetTables(boolean)
     */
    public void setRestrictGetTables(boolean arg0)
    {
        ((OracleConnection) realConnection).setRestrictGetTables(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#setStmtCacheSize(int)
     */
    public void setStmtCacheSize(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setStmtCacheSize(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#setStmtCacheSize(int, boolean)
     */
    public void setStmtCacheSize(int arg0, boolean arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setStmtCacheSize(arg0, arg1);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#setUsingXAFlag(boolean)
     */
    public void setUsingXAFlag(boolean arg0)
    {
        ((OracleConnection) realConnection).setUsingXAFlag(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#setXAErrorFlag(boolean)
     */
    public void setXAErrorFlag(boolean arg0)
    {
        ((OracleConnection) realConnection).setXAErrorFlag(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#shutdown(int)
     */
    public void shutdown(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).shutdown(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleConnection#startup(java.lang.String, int)
     */
    public void startup(String arg0, int arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).startup(arg0, arg1);
    }
    
    private void closeAllOpenStatements()
    {
        for (Entry<Statement, OpenStatementException> entry : hmOpenStatements_.entrySet()) {
            try
            {
                StatementWrapper element = (StatementWrapper) entry.getKey();
                OpenStatementException cose = (OpenStatementException) entry.getValue();
                logger_.warn(OPEN_EXCEPTION + "[" + element.getSqlQuery() + "]\n" , cose);
                element.close(true);
                iLeakedStatementCount_++;
            }
            catch (SQLException e)
            {
                break;
            }
        }
        hmOpenStatements_.clear();
    }
    
    public void clearOpenStatement(StatementWrapper statement)
    {
        if (!hmOpenStatements_.isEmpty())
        {
            if (hmOpenStatements_.containsKey(statement))
            {
                hmOpenStatements_.remove(statement);
            }
        }
    }

    /**
     * The connection returned may be null if the connection object is already
     * closed. This is only used for Connection.
     * 
     * @return Connection
     * @see oracle.jdbc.OracleConnection#_getPC()
     */
    public Connection _getPC()
    {
        if (!bClosed)
        {
            if (realConnection instanceof OracleConnection)
            {
                return ((OracleConnection) realConnection)._getPC();
            }
            return null;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getCallWithKey(java.lang.String)
     */
    public CallableStatement getCallWithKey(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getCallWithKey(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getCreateStatementAsRefCursor()
     */
    public boolean getCreateStatementAsRefCursor()
    {
        if (realConnection instanceof OracleConnection)
        {
            return ((OracleConnection) realConnection).getCreateStatementAsRefCursor();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getExplicitCachingEnabled()
     */
    public boolean getExplicitCachingEnabled() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getExplicitCachingEnabled();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getImplicitCachingEnabled()
     */
    public boolean getImplicitCachingEnabled() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getImplicitCachingEnabled();
    }

    /**
     * Method if invoked on a closed connection object will return null.
     * 
     * @see oracle.jdbc.OracleConnection#getProperties()
     */
    public Properties getProperties()
    {
        if (bClosed)
        {
            return null;
        }
        if (realConnection instanceof OracleConnection)
        {
            return ((OracleConnection) realConnection).getProperties();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getSessionTimeZone()
     */
    public String getSessionTimeZone()
    {
        if (bClosed)
        {
            return null;
        }
        if (realConnection instanceof OracleConnection)
        {
	        return ((OracleConnection) realConnection).getSessionTimeZone();
        }
            return null;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getStatementCacheSize()
     */
    public int getStatementCacheSize() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getStatementCacheSize();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getStatementWithKey(java.lang.String)
     */
    public PreparedStatement getStatementWithKey(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getStatementWithKey(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#isLogicalConnection()
     */
    public boolean isLogicalConnection()
    {
        if (realConnection instanceof OracleConnection)
        {
            return ((OracleConnection) realConnection).isLogicalConnection();
        }
        return false;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#oracleReleaseSavepoint(oracle.jdbc.OracleSavepoint)
     */
    public void oracleReleaseSavepoint(OracleSavepoint arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).oracleReleaseSavepoint(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#oracleRollback(oracle.jdbc.OracleSavepoint)
     */
    public void oracleRollback(OracleSavepoint arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).oracleRollback(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#oracleSetSavepoint()
     */
    public OracleSavepoint oracleSetSavepoint() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).oracleSetSavepoint();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#oracleSetSavepoint(java.lang.String)
     */
    public OracleSavepoint oracleSetSavepoint(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).oracleSetSavepoint(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#pingDatabase(int)
     */
    public int pingDatabase(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).pingDatabase(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#purgeExplicitCache()
     */
    public void purgeExplicitCache() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).purgeExplicitCache();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#purgeImplicitCache()
     */
    public void purgeImplicitCache() throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).purgeImplicitCache();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#registerTAFCallback(oracle.jdbc.OracleOCIFailover, java.lang.Object)
     */
    public void registerTAFCallback(OracleOCIFailover arg0, Object arg1) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).registerTAFCallback(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setCreateStatementAsRefCursor(boolean)
     */
    public void setCreateStatementAsRefCursor(boolean arg0)
    {
        if (!(realConnection instanceof OracleConnection))
        {
            ((OracleConnection) realConnection).setCreateStatementAsRefCursor(arg0);
        }
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setExplicitCachingEnabled(boolean)
     */
    public void setExplicitCachingEnabled(boolean arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setExplicitCachingEnabled(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setImplicitCachingEnabled(boolean)
     */
    public void setImplicitCachingEnabled(boolean arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setImplicitCachingEnabled(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setSessionTimeZone(java.lang.String)
     */
    public void setSessionTimeZone(String arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setSessionTimeZone(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setStatementCacheSize(int)
     */
    public void setStatementCacheSize(int arg0) throws SQLException
    {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setStatementCacheSize(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setWrapper(oracle.jdbc.OracleConnection)
     */
    public void setWrapper(OracleConnection arg0)
    {
        if (realConnection instanceof OracleConnection)
        {
            ((OracleConnection) realConnection).setWrapper(arg0);
        }
    }

    /**
     * Method returns null if the connection is closed.
     * 
     * @see oracle.jdbc.OracleConnection#unwrap()
     */
    public OracleConnection unwrap()
    {
        if (bClosed)
        {
            return null;
        }
        if (realConnection instanceof OracleConnection)
        {
            return ((OracleConnection) realConnection).unwrap();
        }
        return null;
    }
    
    protected long getCriticalOperationTimeLimit(){
        return pool_.getPoolAttributes().getCriticalOperationTimeLimit();
    }
    
    /**
     * Returns an open Statment if any.
     *
     * The wrapper returns the same old statement if the query matches with the one that is supplied to
     * create a new one. This will work only in case where statement is not closed.
     *
     * @param sql Statement
     * @return Statement
     */
    private Statement getOpenStatement(String sql) {
        for (Iterator iter = hmOpenStatements_.keySet().iterator(); iter.hasNext();) {
            StatementWrapper element = (StatementWrapper) iter.next();
            if (element.lastSql.equals(sql)) {
                logger_.debug("Re-Using the Statement. [" + sql + "]");
                return element;
            }
        }
        return null;
    }
    
    protected Logger getLogger() {
        return logger_;
    }
   
     
    /**
     * Checks if the underlying JDBC Connection is active or not.
     *
     * @return true if active else false.
     */
    public boolean isActive() {
        DatabaseMetaData dmd = null;
        ResultSet rs = null;
        boolean bReturnValue = false;
        try {
            dmd = realConnection.getMetaData();
            rs = dmd.getCatalogs();
            bReturnValue = true;
        } catch (Throwable t) {
            //dummy catch.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e1) {
                    // dummy catch;
                }
            }
        }
        return bReturnValue;
    }
    
    /**
     * Sets the leaked ResultSet count to the pool.
     *
     * @param iLeakedResultSetCount
     */
    protected void setLeakedResultSetCount(int iLeakedResultSetCount) {
        pool_.addLeakedResultSetCount(iLeakedResultSetCount);
    }
    
    /**
     * Adds the query in the pool.
     *
     * @param t Thread
     * @param sql Query
     */
    protected void addQuery(Thread t, String sql) {
        CObjectWrapper objWrapper = new CObjectWrapper(sql);
        pool_.addQuery(t, objWrapper);
    }
    
    /**
     * Removes the query from the pool.
     *
     * @param t Thread
     */
    protected void removeQuery(Thread t) {
        pool_.removeQuery(t);
    }

    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        return realConnection.createArrayOf(arg0, arg1);
    }

    public Blob createBlob() throws SQLException {
        return realConnection.createBlob();
    }

    public Clob createClob() throws SQLException {
        return realConnection.createClob();
    }

    public NClob createNClob() throws SQLException {
        return realConnection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return realConnection.createSQLXML();
    }

    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        return realConnection.createStruct(arg0, arg1);
    }

    public Properties getClientInfo() throws SQLException {
        return realConnection.getClientInfo();
    }

    public String getClientInfo(String arg0) throws SQLException {
        return realConnection.getClientInfo(arg0);
    }

    public boolean isValid(int arg0) throws SQLException {
        return realConnection.isValid(arg0);
    }

    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
        realConnection.setClientInfo(arg0);        
    }

    public void setClientInfo(String arg0, String arg1) throws SQLClientInfoException {
        realConnection.setClientInfo(arg0, arg1);        
    }

    public boolean isWrapperFor(Class arg0) throws SQLException {
        return realConnection.isWrapperFor(arg0);        
    }

    public Object unwrap(Class arg0) throws SQLException {
        return realConnection.unwrap(arg0);        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#applyConnectionAttributes(java.util.Properties)
     */
    public void applyConnectionAttributes(Properties arg0) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).applyConnectionAttributes(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#close(java.util.Properties)
     */
    public void close(Properties arg0) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).close(arg0);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#close(int)
     */
    public void close(int arg0) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).close(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getConnectionAttributes()
     */
    public Properties getConnectionAttributes() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getConnectionAttributes();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getConnectionReleasePriority()
     */
    public int getConnectionReleasePriority() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getConnectionReleasePriority();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getEndToEndECIDSequenceNumber()
     */
    public short getEndToEndECIDSequenceNumber() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getEndToEndECIDSequenceNumber();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getEndToEndMetrics()
     */
    public String[] getEndToEndMetrics() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getEndToEndMetrics();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getUnMatchedConnectionAttributes()
     */
    public Properties getUnMatchedConnectionAttributes() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getUnMatchedConnectionAttributes();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#isProxySession()
     */
    public boolean isProxySession() {
        if (!(realConnection instanceof OracleConnection))
        {
            return false;
        }
        return ((OracleConnection) realConnection).isProxySession();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#openProxySession(int, java.util.Properties)
     */
    public void openProxySession(int arg0, Properties arg1) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).openProxySession(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#physicalConnectionWithin()
     */
    public oracle.jdbc.internal.OracleConnection physicalConnectionWithin() {
        if (!(realConnection instanceof OracleConnection))
        {
            return null;
        }
        return ((OracleConnection) realConnection).physicalConnectionWithin();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#registerConnectionCacheCallback(oracle.jdbc.pool.OracleConnectionCacheCallback, java.lang.Object, int)
     */
    public void registerConnectionCacheCallback(
            OracleConnectionCacheCallback arg0, Object arg1, int arg2)
            throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).registerConnectionCacheCallback(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setConnectionReleasePriority(int)
     */
    public void setConnectionReleasePriority(int arg0) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setConnectionReleasePriority(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setEndToEndMetrics(java.lang.String[], short)
     */
    public void setEndToEndMetrics(String[] arg0, short arg1)
            throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setEndToEndMetrics(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#setPlsqlWarnings(java.lang.String)
     */
    public void setPlsqlWarnings(String arg0) throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        ((OracleConnection) realConnection).setPlsqlWarnings(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleConnection#getCurrentSchema()
     */
    public String getCurrentSchema() throws SQLException {
        if (bClosed)
        {
            throw new SQLException("Connection is already closed.");
        }
        if (!(realConnection instanceof OracleConnection))
        {
            throw new SQLException("OracleConnection not found");
        }
        return ((OracleConnection) realConnection).getCurrentSchema();
    }

}
