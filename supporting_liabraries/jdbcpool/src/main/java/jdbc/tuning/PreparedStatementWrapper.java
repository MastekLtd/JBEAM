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
 * $Revision: 11 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/PreparedStatementWrapper.java 11    1/29/09 9:58p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/PreparedStatementWrapper.java $
 * 
 * 11    1/29/09 9:58p Kedarr
 * Minor change to add static to final variables.
 * 
 * 10    1/26/09 5:38p Kedarr
 * Implemented Oracle JDBC Driver 10.2.0.4
 * 
 * 9     6/03/08 1:56p Kedarr
 * Implemented 10.2.0.4 Oracle JDBC Driver
 * 
 * 8     3/17/08 12:28p Kedarr
 * 
 * 7     3/20/07 10:14a Kedarr
 * Changes made to pass the con object in the JDBCLogger.
 * 
 * 6     3/14/07 10:48a Kedarr
 * Changes made as per the changes done in JDBCLogger
 * 
 * 5     5/02/06 4:30p Kedarr
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
 * 2     6/07/05 12:46p Kedarr
 * Replaced this file with AdvancedPRE file and made compliant with Oracle
 * 9.2.0.6 version classes12.zip.
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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

import oracle.jdbc.OracleParameterMetaData;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSetCache;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

public class PreparedStatementWrapper
    extends StatementWrapper
    implements PreparedStatement, OraclePreparedStatement
{
    
    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    private static final String REVISION = "$Revision:: 11        $";

    /**
     * Returns the revision number of the .class.
     *
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }
    
    PreparedStatement realPreparedStatement;
    ConnectionWrapper con_;
    String sql;
    
	private Logger logger_;

    public PreparedStatementWrapper(
        PreparedStatement statement,
        ConnectionWrapper parent,
        String sql)
    {
        super(statement, parent, sql);
        logger_ = Logger.getLogger(parent.strLoggerName_);
        realPreparedStatement = statement;
        con_ = parent;
        this.sql = sql;
    }

    public void addBatch() throws SQLException
    {
        realPreparedStatement.addBatch();
    }

    public void clearParameters() throws SQLException
    {
        realPreparedStatement.clearParameters();
    }

    public boolean execute() throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        boolean b = realPreparedStatement.execute();
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return b;
    }

    public ResultSet executeQuery() throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        ResultSet r = realPreparedStatement.executeQuery();
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(r, this, sql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    public int executeUpdate() throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        int i = realPreparedStatement.executeUpdate();
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return i;
    }

    public ResultSetMetaData getMetaData() throws SQLException
    {
        return realPreparedStatement.getMetaData();
    }

    public void setArray(int i, Array x) throws SQLException
    {
        if (x instanceof SQLArrayWrapper)
            realPreparedStatement.setArray(i, ((SQLArrayWrapper) x).realArray);
        else
            realPreparedStatement.setArray(i, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length)
        throws SQLException
    {
        realPreparedStatement.setAsciiStream(parameterIndex, x, length);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x)
        throws SQLException
    {
        realPreparedStatement.setBigDecimal(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length)
        throws SQLException
    {
        realPreparedStatement.setBinaryStream(parameterIndex, x, length);
    }

    public void setBlob(int i, Blob x) throws SQLException
    {
        realPreparedStatement.setBlob(i, x);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException
    {
        realPreparedStatement.setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Byte.valueOf(x)}));
    	}
        realPreparedStatement.setByte(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setBytes(parameterIndex, x);
    }

    public void setCharacterStream(
        int parameterIndex,
        Reader reader,
        int length)
        throws SQLException
    {
        realPreparedStatement.setCharacterStream(
            parameterIndex,
            reader,
            length);
    }

    public void setClob(int i, Clob x) throws SQLException
    {
        realPreparedStatement.setClob(i, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setDate(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x, Calendar cal)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setDate(parameterIndex, x, cal);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Double.valueOf(x)}));
    	}
        realPreparedStatement.setDouble(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Float.valueOf(x)}));
    	}
        realPreparedStatement.setFloat(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Integer.valueOf(x)}));
    	}
        realPreparedStatement.setInt(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Long.valueOf(x)}));
    	}
        realPreparedStatement.setLong(parameterIndex, x);
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", null}));
    	}
        realPreparedStatement.setNull(parameterIndex, sqlType);
    }

    public void setNull(int paramIndex, int sqlType, String typeName)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {paramIndex+"", null}));
    	}
        realPreparedStatement.setNull(paramIndex, sqlType, typeName);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setObject(parameterIndex, x);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(
        int parameterIndex,
        Object x,
        int targetSqlType,
        int scale)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setObject(
            parameterIndex,
            x,
            targetSqlType,
            scale);
    }

    public void setRef(int i, Ref x) throws SQLException
    {
        realPreparedStatement.setRef(i, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", Short.valueOf(x)}));
    	}
        realPreparedStatement.setShort(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setString(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setTime(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setTimestamp(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {parameterIndex+"", x}));
    	}
        realPreparedStatement.setTimestamp(parameterIndex, x, cal);
    }

    /**
     * @deprecated
     * @see java.sql.PreparedStatement#setUnicodeStream(int, java.io.InputStream, int)
     */
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
        throws SQLException
    {
        realPreparedStatement.setUnicodeStream(parameterIndex, x, length);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int arg0, URL arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0+"", arg1}));
    	}
        realPreparedStatement.setURL(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getParameterMetaData()
     */
    public ParameterMetaData getParameterMetaData() throws SQLException
    {
        return realPreparedStatement.getParameterMetaData();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterType(int, int, int)
     */
    public void defineParameterType(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineParameterType(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#getExecuteBatch()
     */
    public int getExecuteBatch()
    {
        return ((OraclePreparedStatement)realPreparedStatement).getExecuteBatch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#sendBatch()
     */
    public int sendBatch() throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        return ((OraclePreparedStatement)realPreparedStatement).sendBatch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setARRAY(int, oracle.sql.ARRAY)
     */
    public void setARRAY(int arg0, ARRAY arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setARRAY(arg0, arg1);   
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBFILE(int, oracle.sql.BFILE)
     */
    public void setBFILE(int arg0, BFILE arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBFILE(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBLOB(int, oracle.sql.BLOB)
     */
    public void setBLOB(int arg0, BLOB arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBLOB(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBfile(int, oracle.sql.BFILE)
     */
    public void setBfile(int arg0, BFILE arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBfile(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCHAR(int, oracle.sql.CHAR)
     */
    public void setCHAR(int arg0, CHAR arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCHAR(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCLOB(int, oracle.sql.CLOB)
     */
    public void setCLOB(int arg0, CLOB arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCLOB(arg0, arg1);        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCheckBindTypes(boolean)
     */
    public void setCheckBindTypes(boolean arg0)
    {
        ((OraclePreparedStatement)realPreparedStatement).setCheckBindTypes(arg0);
        
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OraclePreparedStatement#setCursor(int, java.sql.ResultSet)
     */
    public void setCursor(int arg0, ResultSet arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCursor(arg0, arg1);
        
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OraclePreparedStatement#setCustomDatum(int, oracle.sql.CustomDatum)
     */
    public void setCustomDatum(int arg0, CustomDatum arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCustomDatum(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDATE(int, oracle.sql.DATE)
     */
    public void setDATE(int arg0, DATE arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setDATE(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setExecuteBatch(int)
     */
    public void setExecuteBatch(int arg0) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setExecuteBatch(arg0);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFixedCHAR(int, java.lang.String)
     */
    public void setFixedCHAR(int arg0, String arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setFixedCHAR(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNUMBER(int, oracle.sql.NUMBER)
     */
    public void setNUMBER(int arg0, NUMBER arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setNUMBER(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOracleObject(int, oracle.sql.Datum)
     */
    public void setOracleObject(int arg0, Datum arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setOracleObject(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setPlsqlIndexTable(int, java.lang.Object, int, int, int, int)
     */
    public void setPlsqlIndexTable(int arg0, Object arg1, int arg2, int arg3, int arg4, int arg5) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setPlsqlIndexTable(arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRAW(int, oracle.sql.RAW)
     */
    public void setRAW(int arg0, RAW arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRAW(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setREF(int, oracle.sql.REF)
     */
    public void setREF(int arg0, REF arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setREF(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setROWID(int, oracle.sql.ROWID)
     */
    public void setROWID(int arg0, ROWID arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setROWID(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefType(int, oracle.sql.REF)
     */
    public void setRefType(int arg0, REF arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRefType(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setSTRUCT(int, oracle.sql.STRUCT)
     */
    public void setSTRUCT(int arg0, STRUCT arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setSTRUCT(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#clearDefines()
     */
    public void clearDefines() throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).clearDefines();
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#closeWithKey(java.lang.String)
     */
    public void closeWithKey(String arg0) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).closeWithKey(arg0);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int)
     */
    public void defineColumnType(int arg0, int arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnType(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int)
     */
    public void defineColumnType(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnType(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, java.lang.String)
     */
    public void defineColumnType(int arg0, int arg1, String arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnType(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#getRowPrefetch()
     */
    public int getRowPrefetch()
    {
        return ((OraclePreparedStatement)realPreparedStatement).getRowPrefetch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setResultSetCache(oracle.jdbc.OracleResultSetCache)
     */
    public void setResultSetCache(OracleResultSetCache arg0) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setResultSetCache(arg0);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setRowPrefetch(int)
     */
    public void setRowPrefetch(int arg0) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRowPrefetch(arg0);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#OracleGetParameterMetaData()
     */
    public OracleParameterMetaData OracleGetParameterMetaData() throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        return ((OraclePreparedStatement)realPreparedStatement).OracleGetParameterMetaData();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterTypeBytes(int, int, int)
     */
    public void defineParameterTypeBytes(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineParameterTypeBytes(arg0,arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterTypeChars(int, int, int)
     */
    public void defineParameterTypeChars(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineParameterTypeChars(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDisableStmtCaching(boolean)
     */
    public void setDisableStmtCaching(boolean arg0)
    {
        if (realPreparedStatement instanceof OraclePreparedStatement)
        {
            ((OraclePreparedStatement)realPreparedStatement).setDisableStmtCaching(arg0);
        }
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFormOfUse(int, short)
     */
    public void setFormOfUse(int arg0, short arg1)
    {
        if (realPreparedStatement instanceof OraclePreparedStatement)
        {
            ((OraclePreparedStatement)realPreparedStatement).setFormOfUse(arg0, arg1);
        }
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALYM(int, oracle.sql.INTERVALYM)
     */
    public void setINTERVALYM(int arg0, INTERVALYM arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setINTERVALYM(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOPAQUE(int, oracle.sql.OPAQUE)
     */
    public void setOPAQUE(int arg0, OPAQUE arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setOPAQUE(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setORAData(int, oracle.sql.ORAData)
     */
    public void setORAData(int arg0, ORAData arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setORAData(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStructDescriptor(int, oracle.sql.StructDescriptor)
     */
    public void setStructDescriptor(int arg0, StructDescriptor arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setStructDescriptor(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMP(int, oracle.sql.TIMESTAMP)
     */
    public void setTIMESTAMP(int arg0, TIMESTAMP arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMP(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPLTZ(int, oracle.sql.TIMESTAMPLTZ)
     */
    public void setTIMESTAMPLTZ(int arg0, TIMESTAMPLTZ arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMPLTZ(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPTZ(int, oracle.sql.TIMESTAMPTZ)
     */
    public void setTIMESTAMPTZ(int arg0, TIMESTAMPTZ arg1) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMPTZ(arg0, arg1);
    }

    /**
     * @deprecated
     * @return int -1 if connection is not of oracle.
     * @see oracle.jdbc.OracleStatement#creationState()
     */
    public int creationState()
    {
        if (realPreparedStatement instanceof OraclePreparedStatement)
        {
            return ((OraclePreparedStatement)realPreparedStatement).creationState();
        }
        return -1;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeBytes(int, int, int)
     */
    public void defineColumnTypeBytes(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnTypeBytes(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeChars(int, int, int)
     */
    public void defineColumnTypeChars(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnTypeChars(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#isNCHAR(int)
     */
    public boolean isNCHAR(int arg0) throws SQLException
    {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        return ((OraclePreparedStatement)realPreparedStatement).isNCHAR(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream)
     */
    public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
        realPreparedStatement.setAsciiStream(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setAsciiStream(int, java.io.InputStream, long)
     */
    public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
        realPreparedStatement.setAsciiStream(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream)
     */
    public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
        realPreparedStatement.setBinaryStream(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBinaryStream(int, java.io.InputStream, long)
     */
    public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
        realPreparedStatement.setBinaryStream(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream)
     */
    public void setBlob(int arg0, InputStream arg1) throws SQLException {
        realPreparedStatement.setBlob(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setBlob(int, java.io.InputStream, long)
     */
    public void setBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
        realPreparedStatement.setBlob(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader)
     */
    public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
        realPreparedStatement.setCharacterStream(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setCharacterStream(int, java.io.Reader, long)
     */
    public void setCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
        realPreparedStatement.setCharacterStream(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader)
     */
    public void setClob(int arg0, Reader arg1) throws SQLException {
        realPreparedStatement.setClob(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setClob(int, java.io.Reader, long)
     */
    public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
        realPreparedStatement.setClob(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader)
     */
    public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
        realPreparedStatement.setNCharacterStream(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNCharacterStream(int, java.io.Reader, long)
     */
    public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
        realPreparedStatement.setNCharacterStream(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.sql.NClob)
     */
    public void setNClob(int arg0, NClob arg1) throws SQLException {
        realPreparedStatement.setNClob(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader)
     */
    public void setNClob(int arg0, Reader arg1) throws SQLException {
        realPreparedStatement.setNClob(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNClob(int, java.io.Reader, long)
     */
    public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
        realPreparedStatement.setNClob(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setNString(int, java.lang.String)
     */
    public void setNString(int arg0, String arg1) throws SQLException {
        realPreparedStatement.setNString(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setRowId(int, java.sql.RowId)
     */
    public void setRowId(int arg0, RowId arg1) throws SQLException {
        realPreparedStatement.setRowId(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setSQLXML(int, java.sql.SQLXML)
     */
    public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
        realPreparedStatement.setSQLXML(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setARRAYAtName(java.lang.String, oracle.sql.ARRAY)
     */
    public void setARRAYAtName(String arg0, ARRAY arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setARRAYAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setArrayAtName(java.lang.String, java.sql.Array)
     */
    public void setArrayAtName(String arg0, Array arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setArrayAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setAsciiStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setAsciiStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setAsciiStreamAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBFILEAtName(java.lang.String, oracle.sql.BFILE)
     */
    public void setBFILEAtName(String arg0, BFILE arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBFILEAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBLOBAtName(java.lang.String, oracle.sql.BLOB)
     */
    public void setBLOBAtName(String arg0, BLOB arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBLOBAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBfileAtName(java.lang.String, oracle.sql.BFILE)
     */
    public void setBfileAtName(String arg0, BFILE arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBfileAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBigDecimalAtName(java.lang.String, java.math.BigDecimal)
     */
    public void setBigDecimalAtName(String arg0, BigDecimal arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBigDecimalAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDouble(int, double)
     */
    public void setBinaryDouble(int arg0, double arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryDouble(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDouble(int, oracle.sql.BINARY_DOUBLE)
     */
    public void setBinaryDouble(int arg0, BINARY_DOUBLE arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryDouble(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDoubleAtName(java.lang.String, double)
     */
    public void setBinaryDoubleAtName(String arg0, double arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryDoubleAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDoubleAtName(java.lang.String, oracle.sql.BINARY_DOUBLE)
     */
    public void setBinaryDoubleAtName(String arg0, BINARY_DOUBLE arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryDoubleAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloat(int, float)
     */
    public void setBinaryFloat(int arg0, float arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryFloat(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloat(int, oracle.sql.BINARY_FLOAT)
     */
    public void setBinaryFloat(int arg0, BINARY_FLOAT arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryFloat(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloatAtName(java.lang.String, float)
     */
    public void setBinaryFloatAtName(String arg0, float arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloatAtName(java.lang.String, oracle.sql.BINARY_FLOAT)
     */
    public void setBinaryFloatAtName(String arg0, BINARY_FLOAT arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setBinaryStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBinaryStreamAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBlobAtName(java.lang.String, java.sql.Blob)
     */
    public void setBlobAtName(String arg0, Blob arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBlobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBooleanAtName(java.lang.String, boolean)
     */
    public void setBooleanAtName(String arg0, boolean arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBooleanAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setByteAtName(java.lang.String, byte)
     */
    public void setByteAtName(String arg0, byte arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setByteAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesAtName(java.lang.String, byte[])
     */
    public void setBytesAtName(String arg0, byte[] arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBytesAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCHARAtName(java.lang.String, oracle.sql.CHAR)
     */
    public void setCHARAtName(String arg0, CHAR arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCHARAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCLOBAtName(java.lang.String, oracle.sql.CLOB)
     */
    public void setCLOBAtName(String arg0, CLOB arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCLOBAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setClobAtName(java.lang.String, java.sql.Clob)
     */
    public void setClobAtName(String arg0, Clob arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setClobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCursorAtName(java.lang.String, java.sql.ResultSet)
     */
    public void setCursorAtName(String arg0, ResultSet arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCursorAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCustomDatumAtName(java.lang.String, oracle.sql.CustomDatum)
     */
    public void setCustomDatumAtName(String arg0, CustomDatum arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setCustomDatumAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDATEAtName(java.lang.String, oracle.sql.DATE)
     */
    public void setDATEAtName(String arg0, DATE arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setDATEAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDateAtName(java.lang.String, java.sql.Date)
     */
    public void setDateAtName(String arg0, Date arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setDateAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDoubleAtName(java.lang.String, double)
     */
    public void setDoubleAtName(String arg0, double arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setDoubleAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFixedCHARAtName(java.lang.String, java.lang.String)
     */
    public void setFixedCHARAtName(String arg0, String arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setFixedCHARAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFloatAtName(java.lang.String, float)
     */
    public void setFloatAtName(String arg0, float arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALDS(int, oracle.sql.INTERVALDS)
     */
    public void setINTERVALDS(int arg0, INTERVALDS arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setINTERVALDS(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALDSAtName(java.lang.String, oracle.sql.INTERVALDS)
     */
    public void setINTERVALDSAtName(String arg0, INTERVALDS arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setINTERVALDSAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALYMAtName(java.lang.String, oracle.sql.INTERVALYM)
     */
    public void setINTERVALYMAtName(String arg0, INTERVALYM arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setINTERVALYMAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setIntAtName(java.lang.String, int)
     */
    public void setIntAtName(String arg0, int arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setIntAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setLongAtName(java.lang.String, long)
     */
    public void setLongAtName(String arg0, long arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setLongAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNUMBERAtName(java.lang.String, oracle.sql.NUMBER)
     */
    public void setNUMBERAtName(String arg0, NUMBER arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setNUMBERAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNullAtName(java.lang.String, int)
     */
    public void setNullAtName(String arg0, int arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setNullAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNullAtName(java.lang.String, int, java.lang.String)
     */
    public void setNullAtName(String arg0, int arg1, String arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setNullAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOPAQUEAtName(java.lang.String, oracle.sql.OPAQUE)
     */
    public void setOPAQUEAtName(String arg0, OPAQUE arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setOPAQUEAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setORADataAtName(java.lang.String, oracle.sql.ORAData)
     */
    public void setORADataAtName(String arg0, ORAData arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setORADataAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object)
     */
    public void setObjectAtName(String arg0, Object arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setObjectAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object, int)
     */
    public void setObjectAtName(String arg0, Object arg1, int arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setObjectAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object, int, int)
     */
    public void setObjectAtName(String arg0, Object arg1, int arg2, int arg3)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setObjectAtName(arg0, arg1, arg2, arg3);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOracleObjectAtName(java.lang.String, oracle.sql.Datum)
     */
    public void setOracleObjectAtName(String arg0, Datum arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setOracleObjectAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRAWAtName(java.lang.String, oracle.sql.RAW)
     */
    public void setRAWAtName(String arg0, RAW arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRAWAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setREFAtName(java.lang.String, oracle.sql.REF)
     */
    public void setREFAtName(String arg0, REF arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setREFAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setROWIDAtName(java.lang.String, oracle.sql.ROWID)
     */
    public void setROWIDAtName(String arg0, ROWID arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setROWIDAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefAtName(java.lang.String, java.sql.Ref)
     */
    public void setRefAtName(String arg0, Ref arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRefAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefTypeAtName(java.lang.String, oracle.sql.REF)
     */
    public void setRefTypeAtName(String arg0, REF arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setRefTypeAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setSTRUCTAtName(java.lang.String, oracle.sql.STRUCT)
     */
    public void setSTRUCTAtName(String arg0, STRUCT arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setSTRUCTAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setShortAtName(java.lang.String, short)
     */
    public void setShortAtName(String arg0, short arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setShortAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringAtName(java.lang.String, java.lang.String)
     */
    public void setStringAtName(String arg0, String arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setStringAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringForClob(int, java.lang.String)
     */
    public void setStringForClob(int arg0, String arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setStringForClob(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringForClobAtName(java.lang.String, java.lang.String)
     */
    public void setStringForClobAtName(String arg0, String arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setStringForClobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStructDescriptorAtName(java.lang.String, oracle.sql.StructDescriptor)
     */
    public void setStructDescriptorAtName(String arg0, StructDescriptor arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setStructDescriptorAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPAtName(java.lang.String, oracle.sql.TIMESTAMP)
     */
    public void setTIMESTAMPAtName(String arg0, TIMESTAMP arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMPAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPLTZAtName(java.lang.String, oracle.sql.TIMESTAMPLTZ)
     */
    public void setTIMESTAMPLTZAtName(String arg0, TIMESTAMPLTZ arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMPLTZAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPTZAtName(java.lang.String, oracle.sql.TIMESTAMPTZ)
     */
    public void setTIMESTAMPTZAtName(String arg0, TIMESTAMPTZ arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTIMESTAMPTZAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTimeAtName(java.lang.String, java.sql.Time)
     */
    public void setTimeAtName(String arg0, Time arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTimeAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTimestampAtName(java.lang.String, java.sql.Timestamp)
     */
    public void setTimestampAtName(String arg0, Timestamp arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setTimestampAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setURLAtName(java.lang.String, java.net.URL)
     */
    public void setURLAtName(String arg0, URL arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setURLAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setUnicodeStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setUnicodeStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setUnicodeStreamAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int, short)
     */
    public void defineColumnType(int arg0, int arg1, int arg2, short arg3)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).defineColumnType(arg0, arg1, arg2, arg3);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#getReturnResultSet()
     */
    public ResultSet getReturnResultSet() throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        
        ResultSet r = ((OraclePreparedStatement)realPreparedStatement).getReturnResultSet();
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(r, this, sql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int)
     */
    public void registerReturnParameter(int arg0, int arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).registerReturnParameter(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int, int)
     */
    public void registerReturnParameter(int arg0, int arg1, int arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).registerReturnParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int, java.lang.String)
     */
    public void registerReturnParameter(int arg0, int arg1, String arg2)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).registerReturnParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesForBlob(int, byte[])
     */
    public void setBytesForBlob(int arg0, byte[] arg1) throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBytesForBlob(arg0, arg1);        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesForBlobAtName(java.lang.String, byte[])
     */
    public void setBytesForBlobAtName(String arg0, byte[] arg1)
            throws SQLException {
        if (!(realPreparedStatement instanceof OraclePreparedStatement))
        {
            throw new SQLException("OraclePreparedStatement not found");
        }
        ((OraclePreparedStatement)realPreparedStatement).setBytesForBlobAtName(arg0, arg1);        
    }
}
