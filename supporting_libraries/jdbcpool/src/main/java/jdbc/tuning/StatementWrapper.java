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
 * $Revision: 10 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/StatementWrapper.java 10    1/29/09 9:59p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/StatementWrapper.java $
 * 
 * 10    1/29/09 9:59p Kedarr
 * Minor change to add static to final variables.
 * 
 * 9     6/03/08 1:57p Kedarr
 * Implemented 10.2.0.4 Oracle JDBC Driver
 * 
 * 8     3/17/08 12:28p Kedarr
 * 
 * 7     3/20/07 10:15a Kedarr
 * Changes made to pass the con object in the JDBCLogger.
 * 
 * 6     3/14/07 10:48a Kedarr
 * Changes made as per the changes done in JDBCLogger
 * 
 * 5     5/02/06 4:23p Kedarr
 * Changes made for trapping all open result sets.
 * 
 * 4     1/16/06 1:17p Kedarr
 * Added Source Safe header. This will enable us to track the revision
 * number of the source from the compiled binary files.
 * 
 * 3     10/26/05 11:47a Kedarr
 * Changes made to display the Connection Info as well as to get the
 * appropriate timelimit for critical sql operation.
 * 
 * 2     6/07/05 12:45p Kedarr
 * Replaced this file with AdvancedPRE file and made compliant with Oracle
 * 9.2.0.6 version classes12.zip.
 * 
 * 5     1/20/05 4:12p Kedarr
 * Added a new protected method close(boolean). Primarily this will not
 * clear the hashmap of open statements from the connection object. This
 * method will be called from the closeAllOpenStatements() from the
 * ConnectionWrapper class.
 * 
 * 4     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.2  2003/12/30 08:01:06  kedar
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import oracle.jdbc.OracleResultSetCache;
import oracle.jdbc.OracleStatement;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

public class StatementWrapper implements Statement, OracleStatement
{
    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 10        $";

    /**
     * Returns the revision number of the .class.
     *
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }
    
    protected Statement realStatement;
    protected ConnectionWrapper connectionParent;
    protected String lastSql;
    protected static final String PARAMETER_PRINT_FORMAT = "Parameter {0} value {1}";

    
    private ConcurrentHashMap<ResultSetWrapper, OpenResultSetException> hmOpenResultSet_ = new ConcurrentHashMap<ResultSetWrapper, OpenResultSetException>();
    
    protected final String OPEN_RESULT_SET_EXCEPTION =
        "A JDBC ResultSet leak was detected. ResultSet leak occurs " +
        "when a ResultSet is fetched from an Statement object and not explicitly closed before " +
        "closing the statement object. The connection object has " +
        "closed the open ResultSet. The following stack trace at create " +
        "shows where the ResultSet. Stack " +
        "trace at ResultSet create:\n";
    
	private Logger logger_;


    public StatementWrapper(Statement statement, ConnectionWrapper parent, String sql)
    {
    	logger_ = Logger.getLogger(parent.strLoggerName_);
        realStatement = statement;
        connectionParent = parent;
        lastSql = sql;
        if (logger_.isEnabledFor(LogLevel.FINE)) {
        	logger_.log(LogLevel.FINE, "Preparing Query [" + sql + "]");
        }
    }

    public void addBatch(String sql) throws SQLException
    {
        realStatement.addBatch(sql);
        lastSql = sql;
    }

    public void cancel() throws SQLException
    {
        realStatement.cancel();
    }

    public void clearBatch() throws SQLException
    {
        realStatement.clearBatch();
    }

    public void clearWarnings() throws SQLException
    {
        realStatement.clearWarnings();
    }

    public void close() throws SQLException
    {
        connectionParent.clearOpenStatement(this);
        connectionParent.getLogger().debug("Explicit close() called on statement. Closing all open ResultSets, if any.");
        closeAllOpenResultSet();
        realStatement.close();
    }
    
    protected void close(boolean value) throws SQLException
    {
        connectionParent.getLogger().debug("Implicit close() called on statement. Closing all open ResultSets, if any.");
        closeAllOpenResultSet();
        realStatement.close();
    }

    public boolean execute(String sql) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, connectionParent);
        boolean b = realStatement.execute(sql);
        JDBCLogger.endLogSqlQuery(t, sql, connectionParent);
        lastSql = sql;
        return b;
    }

    public int[] executeBatch() throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, "batch", connectionParent);
        int[] i = realStatement.executeBatch();
        JDBCLogger.endLogSqlQuery(t, "batch", connectionParent);
        return i;
    }

    public ResultSet executeQuery(String sql) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, connectionParent);
        ResultSet r = realStatement.executeQuery(sql);
        JDBCLogger.endLogSqlQuery(t, sql, connectionParent);
        lastSql = sql;
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(r, this, sql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    public int executeUpdate(String sql) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, connectionParent);
        int i = realStatement.executeUpdate(sql);
        JDBCLogger.endLogSqlQuery(t, sql, connectionParent);
        lastSql = sql;
        return i;
    }

    public Connection getConnection() throws SQLException
    {
        return connectionParent;
    }

    public int getFetchDirection() throws SQLException
    {
        return realStatement.getFetchDirection();
    }

    public int getFetchSize() throws SQLException
    {
        return realStatement.getFetchSize();
    }

    public int getMaxFieldSize() throws SQLException
    {
        return realStatement.getMaxFieldSize();
    }

    public int getMaxRows() throws SQLException
    {
        return realStatement.getMaxRows();
    }

    public boolean getMoreResults() throws SQLException
    {
        return realStatement.getMoreResults();
    }

    public int getQueryTimeout() throws SQLException
    {
        return realStatement.getQueryTimeout();
    }

    public ResultSet getResultSet() throws SQLException
    {
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(
                realStatement.getResultSet(),
                this,
                lastSql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    public int getResultSetConcurrency() throws SQLException
    {
        return realStatement.getResultSetConcurrency();
    }

    public int getResultSetType() throws SQLException
    {
        return realStatement.getResultSetType();
    }

    public int getUpdateCount() throws SQLException
    {
        return realStatement.getUpdateCount();
    }

    public SQLWarning getWarnings() throws SQLException
    {
        return realStatement.getWarnings();
    }

    public void setCursorName(String name) throws SQLException
    {
        realStatement.setCursorName(name);
    }

    public void setEscapeProcessing(boolean enable) throws SQLException
    {
        realStatement.setEscapeProcessing(enable);
    }

    public void setFetchDirection(int direction) throws SQLException
    {
        realStatement.setFetchDirection(direction);
    }

    public void setFetchSize(int rows) throws SQLException
    {
        realStatement.setFetchSize(rows);
    }

    public void setMaxFieldSize(int max) throws SQLException
    {
        realStatement.setMaxFieldSize(max);
    }

    public void setMaxRows(int max) throws SQLException
    {
        realStatement.setMaxRows(max);
    }

    public void setQueryTimeout(int seconds) throws SQLException
    {
        realStatement.setQueryTimeout(seconds);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int arg0) throws SQLException
    {
        return realStatement.getMoreResults(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public ResultSet getGeneratedKeys() throws SQLException
    {
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(
            realStatement.getGeneratedKeys(),
            this,
            lastSql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String arg0, int arg1) throws SQLException
    {
        return realStatement.executeUpdate(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String arg0, int[] arg1) throws SQLException
    {
        return realStatement.executeUpdate(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String arg0, String[] arg1) throws SQLException
    {
        return realStatement.executeUpdate(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String arg0, int arg1) throws SQLException
    {
        return realStatement.execute(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String arg0, int[] arg1) throws SQLException
    {
        return realStatement.execute(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
     */
    public boolean execute(String arg0, String[] arg1) throws SQLException
    {
        return realStatement.execute(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException
    {
        return realStatement.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#clearDefines()
     */
    public void clearDefines() throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).clearDefines();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#closeWithKey(java.lang.String)
     */
    public void closeWithKey(String arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).closeWithKey(arg0);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int)
     */
    public void defineColumnType(int arg0, int arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnType(arg0, arg1);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int)
     */
    public void defineColumnType(int arg0, int arg1, int arg2)
        throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnType(arg0, arg1, arg2);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, java.lang.String)
     */
    public void defineColumnType(int arg0, int arg1, String arg2)
        throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnType(arg0, arg1, arg2);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#getRowPrefetch()
     */
    public int getRowPrefetch()
    {
        return ((OracleStatement) realStatement).getRowPrefetch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setResultSetCache(oracle.jdbc.OracleResultSetCache)
     */
    public void setResultSetCache(OracleResultSetCache arg0)
        throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).setResultSetCache(arg0);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setRowPrefetch(int)
     */
    public void setRowPrefetch(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).setRowPrefetch(arg0);

    }

    public String getSqlQuery()
    {
        return lastSql;
    }

    /**
     * Method returns -1 if the connection is not of oracle.
     * @deprecated
     * @return int
     * @see oracle.jdbc.OracleStatement#creationState()
     */
    public int creationState()
    {
        if (realStatement instanceof OracleStatement)
        {
            return ((OracleStatement) realStatement).creationState();
        }
        return -1;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeBytes(int, int, int)
     */
    public void defineColumnTypeBytes(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnTypeBytes(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeChars(int, int, int)
     */
    public void defineColumnTypeChars(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnTypeChars(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#isNCHAR(int)
     */
    public boolean isNCHAR(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        return ((OracleStatement) realStatement).isNCHAR(arg0);
    }
    
    /**
     * Adds the open ResultSet to the hash map. 
     *
     * @param wrapper
     * @param orse
     */
    protected void addResultSet(ResultSetWrapper wrapper, OpenResultSetException orse) {
        hmOpenResultSet_.put(wrapper, orse);
    }
    
    /**
     * Closes all open result sets after a warning message.
     *
     */
    private void closeAllOpenResultSet() {
        connectionParent.setLeakedResultSetCount(hmOpenResultSet_.size());
        for (Entry<ResultSetWrapper, OpenResultSetException> entry : hmOpenResultSet_.entrySet()) {
        	ResultSetWrapper element = entry.getKey();
        	try {
        		element.close(true);
        	} catch (SQLException e) {
        		//dummy catch
        	}
        	connectionParent.getLogger().warn("OpenResultSetException", entry.getValue());
        }
        hmOpenResultSet_.clear();
    }
    
    /**
     * Clears the Open Result Set.
     * 
     * This method is called upon ResultSet.close() method call.
     *
     * @param wrapper
     */
    protected void clearOpenResultSet(ResultSetWrapper wrapper) {
       if (hmOpenResultSet_.containsKey(wrapper)) {
           hmOpenResultSet_.remove(wrapper);
       }
    }
    
    /**
     * Returns the real underlying statement object.
     *
     * @return Statement
     */
    public Statement realStatement() {
        return realStatement;
    }

    public boolean isClosed() throws SQLException {
        return realStatement.isClosed();
    }

    public boolean isPoolable() throws SQLException {
        return realStatement.isPoolable();
    }

    public void setPoolable(boolean arg0) throws SQLException {
        realStatement.setPoolable(arg0);        
    }

    public boolean isWrapperFor(Class arg0) throws SQLException {
        return realStatement.isWrapperFor(arg0);        
    }

    public Object unwrap(Class arg0) throws SQLException {
        return realStatement.unwrap(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int, short)
     */
    public void defineColumnType(int arg0, int arg1, int arg2, short arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleStatement))
        {
            throw new SQLException("OracleStatement Not Found");
        }
        ((OracleStatement) realStatement).defineColumnType(arg0, arg1, arg2, arg3);
    }

}
