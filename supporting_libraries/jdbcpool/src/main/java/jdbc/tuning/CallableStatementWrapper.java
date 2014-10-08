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
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/CallableStatementWrapper.java 10    1/29/09 9:57p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/CallableStatementWrapper.java $
 * 
 * 10    1/29/09 9:57p Kedarr
 * Minor change to add static to final variables.
 * 
 * 9     1/26/09 5:39p Kedarr
 * Implemented Oracle JDBC Driver 10.2.0.4
 * 
 * 8     6/03/08 1:55p Kedarr
 * Implemented 10.2.0.4 Oracle JDBC Driver
 * 
 * 7     3/17/08 12:27p Kedarr
 * 
 * 6     3/20/07 10:12a Kedarr
 * Changes made to pass the con object in the JDBCLogger.
 * 
 * 5     3/14/07 10:47a Kedarr
 * Changes made as per the changes done in JDBCLogger
 * 
 * 4     5/02/06 4:30p Kedarr
 * Changes made for trapping all open result sets.
 * 
 * 3     1/16/06 1:17p Kedarr
 * Added Source Safe header. This will enable us to track the revision
 * number of the source from the compiled binary files.
 * 
 * 2     6/07/05 12:46p Kedarr
 * Replaced this file with AdvancedPRE file and made compliant with Oracle
 * 9.2.0.6 version classes12.zip.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleParameterMetaData;
import oracle.jdbc.OracleResultSetCache;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

import org.apache.log4j.Logger;

import com.stg.logger.LogLevel;

public class CallableStatementWrapper
    extends PreparedStatementWrapper
    implements CallableStatement, OracleCallableStatement
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
    
    private CallableStatement realCallableStatement;
	private Logger logger_;

    public CallableStatementWrapper(
        CallableStatement statement,
        ConnectionWrapper parent,
        String sql)
    {
        super(statement, parent, sql);
        logger_ = Logger.getLogger(parent.strLoggerName_);
        realCallableStatement = statement;
    }

    public Array getArray(int i) throws SQLException
    {
        return new SQLArrayWrapper(
            realCallableStatement.getArray(i),
            this,
            sql);
    }

    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getBigDecimal(parameterIndex);
    }

    /**
     * @deprecated
     * @see java.sql.CallableStatement#getBigDecimal(int, int)
     */
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException
    {
        return realCallableStatement.getBigDecimal(parameterIndex, scale);
    }

    public Blob getBlob(int i) throws SQLException
    {
        return realCallableStatement.getBlob(i);
    }

    public boolean getBoolean(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getBoolean(parameterIndex);
    }

    public byte getByte(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getByte(parameterIndex);
    }

    public byte[] getBytes(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getBytes(parameterIndex);
    }

    public Clob getClob(int i) throws SQLException
    {
        return realCallableStatement.getClob(i);
    }

    public Date getDate(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getDate(parameterIndex);
    }

    public Date getDate(int parameterIndex, Calendar cal) throws SQLException
    {
        return realCallableStatement.getDate(parameterIndex, cal);
    }

    public double getDouble(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getDouble(parameterIndex);
    }

    public float getFloat(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getFloat(parameterIndex);
    }

    public int getInt(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getInt(parameterIndex);
    }

    public long getLong(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getLong(parameterIndex);
    }

    public Object getObject(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getObject(parameterIndex);
    }

    public Object getObject(int i, Map map) throws SQLException
    {
        return realCallableStatement.getObject(i, map);
    }

    public Ref getRef(int i) throws SQLException
    {
        return realCallableStatement.getRef(i);
    }

    public short getShort(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getShort(parameterIndex);
    }

    public String getString(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getString(parameterIndex);
    }

    public Time getTime(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getTime(parameterIndex);
    }

    public Time getTime(int parameterIndex, Calendar cal) throws SQLException
    {
        return realCallableStatement.getTime(parameterIndex, cal);
    }

    public Timestamp getTimestamp(int parameterIndex) throws SQLException
    {
        return realCallableStatement.getTimestamp(parameterIndex);
    }

    public Timestamp getTimestamp(int parameterIndex, Calendar cal)
        throws SQLException
    {
        return realCallableStatement.getTimestamp(parameterIndex, cal);
    }

    public void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException
    {
        realCallableStatement.registerOutParameter(parameterIndex, sqlType);
    }

    public void registerOutParameter(
        int parameterIndex,
        int sqlType,
        int scale)
        throws SQLException
    {
        realCallableStatement.registerOutParameter(
            parameterIndex,
            sqlType,
            scale);
    }

    public void registerOutParameter(
        int paramIndex,
        int sqlType,
        String typeName)
        throws SQLException
    {
        realCallableStatement.registerOutParameter(
            paramIndex,
            sqlType,
            typeName);
    }

    public boolean wasNull() throws SQLException
    {
        return realCallableStatement.wasNull();
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int)
     */
    public void registerOutParameter(String arg0, int arg1) throws SQLException
    {
        realCallableStatement.registerOutParameter(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, int)
     */
    public void registerOutParameter(String arg0, int arg1, int arg2)
        throws SQLException
    {
        realCallableStatement.registerOutParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#registerOutParameter(java.lang.String, int, java.lang.String)
     */
    public void registerOutParameter(String arg0, int arg1, String arg2)
        throws SQLException
    {
        realCallableStatement.registerOutParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getURL(int)
     */
    public URL getURL(int arg0) throws SQLException
    {
        return realCallableStatement.getURL(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setURL(java.lang.String, java.net.URL)
     */
    public void setURL(String arg0, URL arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setURL(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setNull(java.lang.String, int)
     */
    public void setNull(String arg0, int arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Integer.valueOf(arg1)}));
    	}
        realCallableStatement.setNull(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setBoolean(java.lang.String, boolean)
     */
    public void setBoolean(String arg0, boolean arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Boolean.valueOf(arg1)}));
    	}
        realCallableStatement.setBoolean(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setByte(java.lang.String, byte)
     */
    public void setByte(String arg0, byte arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Byte.valueOf(arg1)}));
    	}
        realCallableStatement.setByte(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setShort(java.lang.String, short)
     */
    public void setShort(String arg0, short arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Integer.valueOf(arg1)}));
    	}
        realCallableStatement.setShort(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setInt(java.lang.String, int)
     */
    public void setInt(String arg0, int arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Integer.valueOf(arg1)}));
    	}
        realCallableStatement.setInt(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setLong(java.lang.String, long)
     */
    public void setLong(String arg0, long arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Long.valueOf(arg1)}));
    	}
        realCallableStatement.setLong(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setFloat(java.lang.String, float)
     */
    public void setFloat(String arg0, float arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Float.valueOf(arg1)}));
    	}
        realCallableStatement.setFloat(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setDouble(java.lang.String, double)
     */
    public void setDouble(String arg0, double arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, Double.valueOf(arg1)}));
    	}
        realCallableStatement.setDouble(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setBigDecimal(java.lang.String, java.math.BigDecimal)
     */
    public void setBigDecimal(String arg0, BigDecimal arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setBigDecimal(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setString(java.lang.String, java.lang.String)
     */
    public void setString(String arg0, String arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setString(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setBytes(java.lang.String, byte[])
     */
    public void setBytes(String arg0, byte[] arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setBytes(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date)
     */
    public void setDate(String arg0, Date arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setDate(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time)
     */
    public void setTime(String arg0, Time arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setTime(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setTimestamp(java.lang.String, java.sql.Timestamp)
     */
    public void setTimestamp(String arg0, Timestamp arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setTimestamp(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setAsciiStream(java.lang.String, java.io.InputStream, int)
     */
    public void setAsciiStream(String arg0, InputStream arg1, int arg2)
        throws SQLException
    {
        realCallableStatement.setAsciiStream(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setBinaryStream(java.lang.String, java.io.InputStream, int)
     */
    public void setBinaryStream(String arg0, InputStream arg1, int arg2)
        throws SQLException
    {
        realCallableStatement.setBinaryStream(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int, int)
     */
    public void setObject(String arg0, Object arg1, int arg2, int arg3)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setObject(arg0, arg1, arg2, arg3);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object, int)
     */
    public void setObject(String arg0, Object arg1, int arg2)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setObject(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setObject(java.lang.String, java.lang.Object)
     */
    public void setObject(String arg0, Object arg1) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setObject(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setCharacterStream(java.lang.String, java.io.Reader, int)
     */
    public void setCharacterStream(String arg0, Reader arg1, int arg2)
        throws SQLException
    {
        realCallableStatement.setCharacterStream(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setDate(java.lang.String, java.sql.Date, java.util.Calendar)
     */
    public void setDate(String arg0, Date arg1, Calendar arg2)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setDate(arg0, arg1, arg2);

    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setTime(java.lang.String, java.sql.Time, java.util.Calendar)
     */
    public void setTime(String arg0, Time arg1, Calendar arg2)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setTime(arg0, arg1, arg2);

    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setTimestamp(java.lang.String, java.sql.Timestamp, java.util.Calendar)
     */
    public void setTimestamp(String arg0, Timestamp arg1, Calendar arg2)
        throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, arg1}));
    	}
        realCallableStatement.setTimestamp(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#setNull(java.lang.String, int, java.lang.String)
     */
    public void setNull(String arg0, int arg1, String arg2) throws SQLException
    {
    	if (logger_.isEnabledFor(LogLevel.FINE)) {
    		logger_.log(LogLevel.FINE, MessageFormat.format(PARAMETER_PRINT_FORMAT, new Object[] {arg0, null}));
    	}
        realCallableStatement.setNull(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getString(java.lang.String)
     */
    public String getString(String arg0) throws SQLException
    {
        return realCallableStatement.getString(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getBoolean(java.lang.String)
     */
    public boolean getBoolean(String arg0) throws SQLException
    {
        return realCallableStatement.getBoolean(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getByte(java.lang.String)
     */
    public byte getByte(String arg0) throws SQLException
    {
        return realCallableStatement.getByte(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getShort(java.lang.String)
     */
    public short getShort(String arg0) throws SQLException
    {
        return realCallableStatement.getShort(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getInt(java.lang.String)
     */
    public int getInt(String arg0) throws SQLException
    {
        return realCallableStatement.getInt(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getLong(java.lang.String)
     */
    public long getLong(String arg0) throws SQLException
    {
        return realCallableStatement.getLong(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getFloat(java.lang.String)
     */
    public float getFloat(String arg0) throws SQLException
    {
        return realCallableStatement.getFloat(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getDouble(java.lang.String)
     */
    public double getDouble(String arg0) throws SQLException
    {
        return realCallableStatement.getDouble(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getBytes(java.lang.String)
     */
    public byte[] getBytes(String arg0) throws SQLException
    {
        return realCallableStatement.getBytes(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getDate(java.lang.String)
     */
    public Date getDate(String arg0) throws SQLException
    {
        return realCallableStatement.getDate(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getTime(java.lang.String)
     */
    public Time getTime(String arg0) throws SQLException
    {
        return realCallableStatement.getTime(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getTimestamp(java.lang.String)
     */
    public Timestamp getTimestamp(String arg0) throws SQLException
    {
        return realCallableStatement.getTimestamp(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getObject(java.lang.String)
     */
    public Object getObject(String arg0) throws SQLException
    {
        return realCallableStatement.getObject(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getBigDecimal(java.lang.String)
     */
    public BigDecimal getBigDecimal(String arg0) throws SQLException
    {
        return realCallableStatement.getBigDecimal(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getObject(java.lang.String, java.util.Map)
     */
    public Object getObject(String arg0, Map arg1) throws SQLException
    {
        return realCallableStatement.getObject(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getRef(java.lang.String)
     */
    public Ref getRef(String arg0) throws SQLException
    {
        return realCallableStatement.getRef(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getBlob(java.lang.String)
     */
    public Blob getBlob(String arg0) throws SQLException
    {
        return realCallableStatement.getBlob(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getClob(java.lang.String)
     */
    public Clob getClob(String arg0) throws SQLException
    {
        return realCallableStatement.getClob(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getArray(java.lang.String)
     */
    public Array getArray(String arg0) throws SQLException
    {
        return realCallableStatement.getArray(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getDate(java.lang.String, java.util.Calendar)
     */
    public Date getDate(String arg0, Calendar arg1) throws SQLException
    {
        return null;
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getTime(java.lang.String, java.util.Calendar)
     */
    public Time getTime(String arg0, Calendar arg1) throws SQLException
    {
        return realCallableStatement.getTime(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getTimestamp(java.lang.String, java.util.Calendar)
     */
    public Timestamp getTimestamp(String arg0, Calendar arg1)
        throws SQLException
    {
        return realCallableStatement.getTimestamp(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.CallableStatement#getURL(java.lang.String)
     */
    public URL getURL(String arg0) throws SQLException
    {
        return realCallableStatement.getURL(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#setURL(int, java.net.URL)
     */
    public void setURL(int arg0, URL arg1) throws SQLException
    {
        realCallableStatement.setURL(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see java.sql.PreparedStatement#getParameterMetaData()
     */
    public ParameterMetaData getParameterMetaData() throws SQLException
    {
        return realCallableStatement.getParameterMetaData();
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getMoreResults(int)
     */
    public boolean getMoreResults(int arg0) throws SQLException
    {
        return realCallableStatement.getMoreResults(arg0);
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getGeneratedKeys()
     */
    public ResultSet getGeneratedKeys() throws SQLException
    {
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper =  new ResultSetWrapper(
                realCallableStatement.getGeneratedKeys(), this, lastSql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int)
     */
    public int executeUpdate(String arg0, int arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        int i = realCallableStatement.executeUpdate(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return i;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
     */
    public int executeUpdate(String arg0, int[] arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        int i = realCallableStatement.executeUpdate(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return i;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
     */
    public int executeUpdate(String arg0, String[] arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        int i = realCallableStatement.executeUpdate(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return i;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int)
     */
    public boolean execute(String arg0, int arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        boolean b = realCallableStatement.execute(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return b;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, int[])
     */
    public boolean execute(String arg0, int[] arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        boolean b = realCallableStatement.execute(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return b;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
     */
    public boolean execute(String arg0, String[] arg1) throws SQLException
    {
        Thread t = Thread.currentThread();
        JDBCLogger.startLogSqlQuery(t, sql, con_);
        boolean b = realCallableStatement.execute(arg0, arg1);
        JDBCLogger.endLogSqlQuery(t, sql, con_);
        return b;
    }

    /* (non-Javadoc)
     * @see java.sql.Statement#getResultSetHoldability()
     */
    public int getResultSetHoldability() throws SQLException
    {
        return realCallableStatement.getResultSetHoldability();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getARRAY(int)
     */
    public ARRAY getARRAY(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getARRAY(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getAsciiStream(int)
     */
    public InputStream getAsciiStream(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getAsciiStream(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getBFILE(int)
     */
    public BFILE getBFILE(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getBFILE(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getBLOB(int)
     */
    public BLOB getBLOB(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getBLOB(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getBinaryStream(int)
     */
    public InputStream getBinaryStream(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getBinaryStream(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getCHAR(int)
     */
    public CHAR getCHAR(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getCHAR(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getCLOB(int)
     */
    public CLOB getCLOB(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getCLOB(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getCursor(int)
     */
    public ResultSet getCursor(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(
                ((OracleCallableStatement) realStatement).getCursor(arg0), this, lastSql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleCallableStatement#getCustomDatum(int, oracle.sql.CustomDatumFactory)
     */
    public Object getCustomDatum(int arg0, CustomDatumFactory arg1)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getCustomDatum(
            arg0,
            arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getDATE(int)
     */
    public DATE getDATE(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getDATE(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getNUMBER(int)
     */
    public NUMBER getNUMBER(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNUMBER(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getOracleObject(int)
     */
    public Datum getOracleObject(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getOracleObject(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getOraclePlsqlIndexTable(int)
     */
    public Datum[] getOraclePlsqlIndexTable(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return (
            (OracleCallableStatement) realStatement).getOraclePlsqlIndexTable(
            arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getPlsqlIndexTable(int)
     */
    public Object getPlsqlIndexTable(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getPlsqlIndexTable(
            arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getPlsqlIndexTable(int, java.lang.Class)
     */
    public Object getPlsqlIndexTable(int arg0, Class arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getPlsqlIndexTable(
            arg0,
            arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getRAW(int)
     */
    public RAW getRAW(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getRAW(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getREF(int)
     */
    public REF getREF(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getREF(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getROWID(int)
     */
    public ROWID getROWID(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getROWID(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getSTRUCT(int)
     */
    public STRUCT getSTRUCT(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getSTRUCT(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getUnicodeStream(int)
     */
    public InputStream getUnicodeStream(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getUnicodeStream(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#registerIndexTableOutParameter(int, int, int, int)
     */
    public void registerIndexTableOutParameter(
        int arg0,
        int arg1,
        int arg2,
        int arg3)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        (
            (
                OracleCallableStatement) realStatement)
                    .registerIndexTableOutParameter(
            arg0,
            arg1,
            arg2,
            arg3);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#registerOutParameter(int, int, int, int)
     */
    public void registerOutParameter(int arg0, int arg1, int arg2, int arg3)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).registerOutParameter(
            arg0,
            arg1,
            arg2,
            arg3);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#sendBatch()
     */
    public int sendBatch() throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).sendBatch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#setExecuteBatch(int)
     */
    public void setExecuteBatch(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setExecuteBatch(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterType(int, int, int)
     */
    public void defineParameterType(int arg0, int arg1, int arg2)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineParameterType(
            arg0,
            arg1,
            arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#getExecuteBatch()
     */
    public int getExecuteBatch()
    {
        return ((OracleCallableStatement) realStatement).getExecuteBatch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setARRAY(int, oracle.sql.ARRAY)
     */
    public void setARRAY(int arg0, ARRAY arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setARRAY(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBFILE(int, oracle.sql.BFILE)
     */
    public void setBFILE(int arg0, BFILE arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBFILE(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBLOB(int, oracle.sql.BLOB)
     */
    public void setBLOB(int arg0, BLOB arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBLOB(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBfile(int, oracle.sql.BFILE)
     */
    public void setBfile(int arg0, BFILE arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBfile(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCHAR(int, oracle.sql.CHAR)
     */
    public void setCHAR(int arg0, CHAR arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCHAR(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCLOB(int, oracle.sql.CLOB)
     */
    public void setCLOB(int arg0, CLOB arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCLOB(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCheckBindTypes(boolean)
     */
    public void setCheckBindTypes(boolean arg0)
    {

        ((OracleCallableStatement) realStatement).setCheckBindTypes(arg0);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OraclePreparedStatement#setCursor(int, java.sql.ResultSet)
     */
    public void setCursor(int arg0, ResultSet arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCursor(arg0, arg1);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OraclePreparedStatement#setCustomDatum(int, oracle.sql.CustomDatum)
     */
    public void setCustomDatum(int arg0, CustomDatum arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCustomDatum(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDATE(int, oracle.sql.DATE)
     */
    public void setDATE(int arg0, DATE arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setDATE(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFixedCHAR(int, java.lang.String)
     */
    public void setFixedCHAR(int arg0, String arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setFixedCHAR(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNUMBER(int, oracle.sql.NUMBER)
     */
    public void setNUMBER(int arg0, NUMBER arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNUMBER(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOracleObject(int, oracle.sql.Datum)
     */
    public void setOracleObject(int arg0, Datum arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setOracleObject(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setPlsqlIndexTable(int, java.lang.Object, int, int, int, int)
     */
    public void setPlsqlIndexTable(
        int arg0,
        Object arg1,
        int arg2,
        int arg3,
        int arg4,
        int arg5)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setPlsqlIndexTable(
            arg0,
            arg1,
            arg2,
            arg3,
            arg4,
            arg5);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRAW(int, oracle.sql.RAW)
     */
    public void setRAW(int arg0, RAW arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setRAW(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setREF(int, oracle.sql.REF)
     */
    public void setREF(int arg0, REF arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setREF(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setROWID(int, oracle.sql.ROWID)
     */
    public void setROWID(int arg0, ROWID arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setROWID(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefType(int, oracle.sql.REF)
     */
    public void setRefType(int arg0, REF arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setRefType(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setSTRUCT(int, oracle.sql.STRUCT)
     */
    public void setSTRUCT(int arg0, STRUCT arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setSTRUCT(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#clearDefines()
     */
    public void clearDefines() throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).clearDefines();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#closeWithKey(java.lang.String)
     */
    public void closeWithKey(String arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).closeWithKey(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int)
     */
    public void defineColumnType(int arg0, int arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineColumnType(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int)
     */
    public void defineColumnType(int arg0, int arg1, int arg2)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineColumnType(
            arg0,
            arg1,
            arg2);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, java.lang.String)
     */
    public void defineColumnType(int arg0, int arg1, String arg2)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineColumnType(
            arg0,
            arg1,
            arg2);

    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#getRowPrefetch()
     */
    public int getRowPrefetch()
    {
        return ((OracleCallableStatement) realStatement).getRowPrefetch();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setResultSetCache(oracle.jdbc.OracleResultSetCache)
     */
    public void setResultSetCache(OracleResultSetCache arg0)
        throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setResultSetCache(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#setRowPrefetch(int)
     */
    public void setRowPrefetch(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setRowPrefetch(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getAnyDataEmbeddedObject(int)
     */
    public Object getAnyDataEmbeddedObject(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getAnyDataEmbeddedObject(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getCharacterStream(int)
     */
    public Reader getCharacterStream(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getCharacterStream(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getINTERVALYM(int)
     */
    public INTERVALYM getINTERVALYM(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getINTERVALYM(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getOPAQUE(int)
     */
    public OPAQUE getOPAQUE(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getOPAQUE(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getORAData(int, oracle.sql.ORADataFactory)
     */
    public Object getORAData(int arg0, ORADataFactory arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getORAData(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getTIMESTAMP(int)
     */
    public TIMESTAMP getTIMESTAMP(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getTIMESTAMP(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getTIMESTAMPLTZ(int)
     */
    public TIMESTAMPLTZ getTIMESTAMPLTZ(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getTIMESTAMPLTZ(arg0);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getTIMESTAMPTZ(int)
     */
    public TIMESTAMPTZ getTIMESTAMPTZ(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getTIMESTAMPTZ(arg0);
    }


    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#OracleGetParameterMetaData()
     */
    public OracleParameterMetaData OracleGetParameterMetaData() throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).OracleGetParameterMetaData();
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterTypeBytes(int, int, int)
     */
    public void defineParameterTypeBytes(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineParameterTypeBytes(arg0,arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#defineParameterTypeChars(int, int, int)
     */
    public void defineParameterTypeChars(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineParameterTypeChars(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDisableStmtCaching(boolean)
     */
    public void setDisableStmtCaching(boolean arg0)
    {
        if (realStatement instanceof OracleCallableStatement)
        {
            ((OracleCallableStatement) realStatement).setDisableStmtCaching(arg0);
        }
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFormOfUse(int, short)
     */
    public void setFormOfUse(int arg0, short arg1)
    {
        if (realStatement instanceof OracleCallableStatement)
        {
            ((OracleCallableStatement) realStatement).setFormOfUse(arg0, arg1);
        }
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALYM(int, oracle.sql.INTERVALYM)
     */
    public void setINTERVALYM(int arg0, INTERVALYM arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setINTERVALYM(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOPAQUE(int, oracle.sql.OPAQUE)
     */
    public void setOPAQUE(int arg0, OPAQUE arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setOPAQUE(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setORAData(int, oracle.sql.ORAData)
     */
    public void setORAData(int arg0, ORAData arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setORAData(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStructDescriptor(int, oracle.sql.StructDescriptor)
     */
    public void setStructDescriptor(int arg0, StructDescriptor arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setStructDescriptor(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMP(int, oracle.sql.TIMESTAMP)
     */
    public void setTIMESTAMP(int arg0, TIMESTAMP arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setTIMESTAMP(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPLTZ(int, oracle.sql.TIMESTAMPLTZ)
     */
    public void setTIMESTAMPLTZ(int arg0, TIMESTAMPLTZ arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setTIMESTAMPLTZ(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPTZ(int, oracle.sql.TIMESTAMPTZ)
     */
    public void setTIMESTAMPTZ(int arg0, TIMESTAMPTZ arg1) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setTIMESTAMPTZ(arg0, arg1);
    }

    /**
     * @deprecated
     * @see oracle.jdbc.OracleStatement#creationState()
     */
    public int creationState()
    {
        if (realStatement instanceof OracleCallableStatement)
        {
	        return ((OracleCallableStatement) realStatement).creationState();
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeBytes(int, int, int)
     */
    public void defineColumnTypeBytes(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineColumnTypeBytes(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnTypeChars(int, int, int)
     */
    public void defineColumnTypeChars(int arg0, int arg1, int arg2) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).defineColumnTypeChars(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#isNCHAR(int)
     */
    public boolean isNCHAR(int arg0) throws SQLException
    {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).isNCHAR(arg0);
    }

    public Reader getCharacterStream(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getCharacterStream(arg0);
    }

    public Reader getNCharacterStream(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNCharacterStream(arg0);
    }

    public Reader getNCharacterStream(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNCharacterStream(arg0);
    }

    public NClob getNClob(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNClob(arg0);
    }

    public NClob getNClob(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNClob(arg0);
    }

    public String getNString(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNString(arg0);
    }

    public String getNString(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getNString(arg0);
    }

    public RowId getRowId(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getRowId(arg0);
    }

    public RowId getRowId(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getRowId(arg0);
    }

    public SQLXML getSQLXML(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getSQLXML(arg0);
    }

    public SQLXML getSQLXML(String arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getSQLXML(arg0);
    }

    public void setAsciiStream(String arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setAsciiStream(arg0, arg1);
        
    }

    public void setAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setAsciiStream(arg0, arg1, arg2);
        
    }

    public void setBinaryStream(String arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBinaryStream(arg0, arg1);
        
    }

    public void setBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBinaryStream(arg0, arg1, arg2);
        
    }

    public void setBlob(String arg0, Blob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBlob(arg0, arg1);
    }

    public void setBlob(String arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBlob(arg0, arg1);
    }

    public void setBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBlob(arg0, arg1, arg2);
    }

    public void setCharacterStream(String arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCharacterStream(arg0, arg1);
        
    }

    public void setCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCharacterStream(arg0, arg1, arg2);
        
    }

    public void setClob(String arg0, Clob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setClob(arg0, arg1);
        
    }

    public void setClob(String arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setClob(arg0, arg1);
        
    }

    public void setClob(String arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setClob(arg0, arg1, arg2);
        
    }

    public void setNCharacterStream(String arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNCharacterStream(arg0, arg1);
        
    }

    public void setNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNCharacterStream(arg0, arg1, arg2);
        
    }

    public void setNClob(String arg0, NClob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1);
        
    }

    public void setNClob(String arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1);
        
    }

    public void setNClob(String arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1, arg2);
        
    }

    public void setNString(String arg0, String arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNString(arg0, arg1);
        
    }

    public void setRowId(String arg0, RowId arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setRowId(arg0, arg1);
        
    }

    public void setSQLXML(String arg0, SQLXML arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setSQLXML(arg0, arg1);
        
    }

    public void setAsciiStream(int arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setAsciiStream(arg0, arg1);        
    }

    public void setAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setAsciiStream(arg0, arg1, arg2);
        
    }

    public void setBinaryStream(int arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBinaryStream(arg0, arg1);
        
    }

    public void setBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBinaryStream(arg0, arg1, arg2);
        
    }

    public void setBlob(int arg0, InputStream arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBlob(arg0, arg1);        
    }

    public void setBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setBlob(arg0, arg1, arg2);        
    }

    public void setCharacterStream(int arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCharacterStream(arg0, arg1);
        
    }

    public void setCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setCharacterStream(arg0, arg1, arg2);        
    }

    public void setClob(int arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setClob(arg0, arg1);        
    }

    public void setClob(int arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setClob(arg0, arg1, arg2);        
    }

    public void setNCharacterStream(int arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNCharacterStream(arg0, arg1);        
    }

    public void setNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNCharacterStream(arg0, arg1, arg2);
    }

    public void setNClob(int arg0, NClob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1);
        
    }

    public void setNClob(int arg0, Reader arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1);        
    }

    public void setNClob(int arg0, Reader arg1, long arg2) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNClob(arg0, arg1, arg2);        
    }

    public void setNString(int arg0, String arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setNString(arg0, arg1);        
    }

    public void setRowId(int arg0, RowId arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setRowId(arg0, arg1);        
    }

    public void setSQLXML(int arg0, SQLXML arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setSQLXML(arg0, arg1);        
    }

    public boolean isClosed() throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).isClosed();        
    }

    public boolean isPoolable() throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).isPoolable();        
    }

    public void setPoolable(boolean arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement) realStatement).setPoolable(arg0);
        
    }

    public boolean isWrapperFor(Class arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).isWrapperFor(arg0);        
    }

    public Object unwrap(Class arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).unwrap(arg0);        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#getINTERVALDS(int)
     */
    public INTERVALDS getINTERVALDS(int arg0) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        return ((OracleCallableStatement) realStatement).getINTERVALDS(arg0);        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#registerOutParameter(java.lang.String, int, int, int)
     */
    public void registerOutParameter(String arg0, int arg1, int arg2, int arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).registerOutParameter(arg0, arg1, arg2, arg3);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#setBinaryDouble(java.lang.String, oracle.sql.BINARY_DOUBLE)
     */
    public void setBinaryDouble(String arg0, BINARY_DOUBLE arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryDouble(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#setBinaryFloat(java.lang.String, oracle.sql.BINARY_FLOAT)
     */
    public void setBinaryFloat(String arg0, BINARY_FLOAT arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryFloat(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#setStringForClob(java.lang.String, java.lang.String)
     */
    public void setStringForClob(String arg0, String arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setStringForClob(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setARRAYAtName(java.lang.String, oracle.sql.ARRAY)
     */
    public void setARRAYAtName(String arg0, ARRAY arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setARRAYAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setArrayAtName(java.lang.String, java.sql.Array)
     */
    public void setArrayAtName(String arg0, Array arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setArrayAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setAsciiStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setAsciiStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setAsciiStreamAtName(arg0, arg1, arg2);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBFILEAtName(java.lang.String, oracle.sql.BFILE)
     */
    public void setBFILEAtName(String arg0, BFILE arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBFILEAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBLOBAtName(java.lang.String, oracle.sql.BLOB)
     */
    public void setBLOBAtName(String arg0, BLOB arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBLOBAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBfileAtName(java.lang.String, oracle.sql.BFILE)
     */
    public void setBfileAtName(String arg0, BFILE arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBfileAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBigDecimalAtName(java.lang.String, java.math.BigDecimal)
     */
    public void setBigDecimalAtName(String arg0, BigDecimal arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBigDecimalAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDouble(int, double)
     */
    public void setBinaryDouble(int arg0, double arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryDouble(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDouble(int, oracle.sql.BINARY_DOUBLE)
     */
    public void setBinaryDouble(int arg0, BINARY_DOUBLE arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryDouble(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDoubleAtName(java.lang.String, double)
     */
    public void setBinaryDoubleAtName(String arg0, double arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryDoubleAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryDoubleAtName(java.lang.String, oracle.sql.BINARY_DOUBLE)
     */
    public void setBinaryDoubleAtName(String arg0, BINARY_DOUBLE arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryDoubleAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloat(int, float)
     */
    public void setBinaryFloat(int arg0, float arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryFloat(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloat(int, oracle.sql.BINARY_FLOAT)
     */
    public void setBinaryFloat(int arg0, BINARY_FLOAT arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryFloat(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloatAtName(java.lang.String, float)
     */
    public void setBinaryFloatAtName(String arg0, float arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryFloatAtName(java.lang.String, oracle.sql.BINARY_FLOAT)
     */
    public void setBinaryFloatAtName(String arg0, BINARY_FLOAT arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBinaryStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setBinaryStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBinaryStreamAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBlobAtName(java.lang.String, java.sql.Blob)
     */
    public void setBlobAtName(String arg0, Blob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBlobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBooleanAtName(java.lang.String, boolean)
     */
    public void setBooleanAtName(String arg0, boolean arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBooleanAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setByteAtName(java.lang.String, byte)
     */
    public void setByteAtName(String arg0, byte arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setByteAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesAtName(java.lang.String, byte[])
     */
    public void setBytesAtName(String arg0, byte[] arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBytesAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCHARAtName(java.lang.String, oracle.sql.CHAR)
     */
    public void setCHARAtName(String arg0, CHAR arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setCHARAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCLOBAtName(java.lang.String, oracle.sql.CLOB)
     */
    public void setCLOBAtName(String arg0, CLOB arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setCLOBAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setClobAtName(java.lang.String, java.sql.Clob)
     */
    public void setClobAtName(String arg0, Clob arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setClobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCursorAtName(java.lang.String, java.sql.ResultSet)
     */
    public void setCursorAtName(String arg0, ResultSet arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setCursorAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setCustomDatumAtName(java.lang.String, oracle.sql.CustomDatum)
     */
    public void setCustomDatumAtName(String arg0, CustomDatum arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setCustomDatumAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDATEAtName(java.lang.String, oracle.sql.DATE)
     */
    public void setDATEAtName(String arg0, DATE arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setDATEAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDateAtName(java.lang.String, java.sql.Date)
     */
    public void setDateAtName(String arg0, Date arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setDateAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setDoubleAtName(java.lang.String, double)
     */
    public void setDoubleAtName(String arg0, double arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setDoubleAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFixedCHARAtName(java.lang.String, java.lang.String)
     */
    public void setFixedCHARAtName(String arg0, String arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setFixedCHARAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setFloatAtName(java.lang.String, float)
     */
    public void setFloatAtName(String arg0, float arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setFloatAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALDS(int, oracle.sql.INTERVALDS)
     */
    public void setINTERVALDS(int arg0, INTERVALDS arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setINTERVALDS(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALDSAtName(java.lang.String, oracle.sql.INTERVALDS)
     */
    public void setINTERVALDSAtName(String arg0, INTERVALDS arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setINTERVALDSAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setINTERVALYMAtName(java.lang.String, oracle.sql.INTERVALYM)
     */
    public void setINTERVALYMAtName(String arg0, INTERVALYM arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setINTERVALYMAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setIntAtName(java.lang.String, int)
     */
    public void setIntAtName(String arg0, int arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setIntAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setLongAtName(java.lang.String, long)
     */
    public void setLongAtName(String arg0, long arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setLongAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNUMBERAtName(java.lang.String, oracle.sql.NUMBER)
     */
    public void setNUMBERAtName(String arg0, NUMBER arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setNUMBERAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNullAtName(java.lang.String, int)
     */
    public void setNullAtName(String arg0, int arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setNullAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setNullAtName(java.lang.String, int, java.lang.String)
     */
    public void setNullAtName(String arg0, int arg1, String arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setNullAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOPAQUEAtName(java.lang.String, oracle.sql.OPAQUE)
     */
    public void setOPAQUEAtName(String arg0, OPAQUE arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setOPAQUEAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setORADataAtName(java.lang.String, oracle.sql.ORAData)
     */
    public void setORADataAtName(String arg0, ORAData arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setORADataAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object)
     */
    public void setObjectAtName(String arg0, Object arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setObjectAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object, int)
     */
    public void setObjectAtName(String arg0, Object arg1, int arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setObjectAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setObjectAtName(java.lang.String, java.lang.Object, int, int)
     */
    public void setObjectAtName(String arg0, Object arg1, int arg2, int arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setObjectAtName(arg0, arg1, arg2, arg3);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setOracleObjectAtName(java.lang.String, oracle.sql.Datum)
     */
    public void setOracleObjectAtName(String arg0, Datum arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setOracleObjectAtName(arg0, arg1);
        
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRAWAtName(java.lang.String, oracle.sql.RAW)
     */
    public void setRAWAtName(String arg0, RAW arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setRAWAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setREFAtName(java.lang.String, oracle.sql.REF)
     */
    public void setREFAtName(String arg0, REF arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setREFAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setROWIDAtName(java.lang.String, oracle.sql.ROWID)
     */
    public void setROWIDAtName(String arg0, ROWID arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setROWIDAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefAtName(java.lang.String, java.sql.Ref)
     */
    public void setRefAtName(String arg0, Ref arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setRefAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setRefTypeAtName(java.lang.String, oracle.sql.REF)
     */
    public void setRefTypeAtName(String arg0, REF arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setRefTypeAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setSTRUCTAtName(java.lang.String, oracle.sql.STRUCT)
     */
    public void setSTRUCTAtName(String arg0, STRUCT arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setSTRUCTAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setShortAtName(java.lang.String, short)
     */
    public void setShortAtName(String arg0, short arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setShortAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringAtName(java.lang.String, java.lang.String)
     */
    public void setStringAtName(String arg0, String arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setStringAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringForClob(int, java.lang.String)
     */
    public void setStringForClob(int arg0, String arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setStringForClob(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStringForClobAtName(java.lang.String, java.lang.String)
     */
    public void setStringForClobAtName(String arg0, String arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setStringForClobAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setStructDescriptorAtName(java.lang.String, oracle.sql.StructDescriptor)
     */
    public void setStructDescriptorAtName(String arg0, StructDescriptor arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setStructDescriptorAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPAtName(java.lang.String, oracle.sql.TIMESTAMP)
     */
    public void setTIMESTAMPAtName(String arg0, TIMESTAMP arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setTIMESTAMPAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPLTZAtName(java.lang.String, oracle.sql.TIMESTAMPLTZ)
     */
    public void setTIMESTAMPLTZAtName(String arg0, TIMESTAMPLTZ arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setTIMESTAMPLTZAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTIMESTAMPTZAtName(java.lang.String, oracle.sql.TIMESTAMPTZ)
     */
    public void setTIMESTAMPTZAtName(String arg0, TIMESTAMPTZ arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setTIMESTAMPTZAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTimeAtName(java.lang.String, java.sql.Time)
     */
    public void setTimeAtName(String arg0, Time arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setTimeAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setTimestampAtName(java.lang.String, java.sql.Timestamp)
     */
    public void setTimestampAtName(String arg0, Timestamp arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setTimestampAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setURLAtName(java.lang.String, java.net.URL)
     */
    public void setURLAtName(String arg0, URL arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setURLAtName(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setUnicodeStreamAtName(java.lang.String, java.io.InputStream, int)
     */
    public void setUnicodeStreamAtName(String arg0, InputStream arg1, int arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setUnicodeStreamAtName(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleStatement#defineColumnType(int, int, int, short)
     */
    public void defineColumnType(int arg0, int arg1, int arg2, short arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).defineColumnType(arg0, arg1, arg2, arg3);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OracleCallableStatement#setBytesForBlob(java.lang.String, byte[])
     */
    public void setBytesForBlob(String arg0, byte[] arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realStatement).setBytesForBlob(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#getReturnResultSet()
     */
    public ResultSet getReturnResultSet() throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        OpenResultSetException orse = new OpenResultSetException(OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper =  new ResultSetWrapper(
                ((OracleCallableStatement)realCallableStatement).getReturnResultSet(), this, lastSql);
        addResultSet(wrapper, orse);
        return wrapper;
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int)
     */
    public void registerReturnParameter(int arg0, int arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).registerReturnParameter(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int, int)
     */
    public void registerReturnParameter(int arg0, int arg1, int arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).registerReturnParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#registerReturnParameter(int, int, java.lang.String)
     */
    public void registerReturnParameter(int arg0, int arg1, String arg2)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).registerReturnParameter(arg0, arg1, arg2);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesForBlob(int, byte[])
     */
    public void setBytesForBlob(int arg0, byte[] arg1) throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).setBytesForBlob(arg0, arg1);
    }

    /* (non-Javadoc)
     * @see oracle.jdbc.OraclePreparedStatement#setBytesForBlobAtName(java.lang.String, byte[])
     */
    public void setBytesForBlobAtName(String arg0, byte[] arg1)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).setBytesForBlobAtName(arg0, arg1);
    }

    /**
     * @see oracle.jdbc.OracleCallableStatement#registerOutParameterBytes(int, int, int, int)
     * @deprecated
     */
    public void registerOutParameterBytes(int arg0, int arg1, int arg2, int arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).registerOutParameterBytes(arg0, arg1, arg2, arg3);
    }

    /**
     * @see oracle.jdbc.OracleCallableStatement#registerOutParameterChars(int, int, int, int)
     * @deprecated
     */
    public void registerOutParameterChars(int arg0, int arg1, int arg2, int arg3)
            throws SQLException {
        if (!(realStatement instanceof OracleCallableStatement))
        {
            throw new SQLException("OracleCallableStatement not found");
        }
        ((OracleCallableStatement)realCallableStatement).registerOutParameterChars(arg0, arg1, arg2, arg3);
    }

}
