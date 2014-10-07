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
 * $Revision: 3 $
 * 
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/SQLArrayWrapper.java 3     3/17/08 12:28p Kedarr $
 * 
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/SQLArrayWrapper.java $
 * 
 * 3     3/17/08 12:28p Kedarr
 * 
 * 2     5/02/06 4:24p Kedarr
 * Changes made for trapping all open result sets.
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * 
 * 2 12/09/03 9:37p Kedarr Removed UnUsed variables where ever possible and made
 * the necessary changes. Revision 1.2 2003/12/09 16:02:08 kedar Removed UnUsed
 * variables where ever possible and made the necessary changes.
 * 
 * 
 * 1 12/01/03 1:35p Kedarr Revision 1.1 2003/11/28 09:47:21 kedar Added a new
 * package for Tunning SQL queries.
 * 
 *  
 */
package jdbc.tuning;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SQLArrayWrapper implements Array {
    
    public final String REVISION = "$Revision: 3 $";

    Array realArray;

    StatementWrapper parentStatement;

    String sql;

    public SQLArrayWrapper(Array array, StatementWrapper statement, String sql) {
        parentStatement = statement;
        realArray = array;
        this.sql = sql;
    }

    public Object getArray() throws SQLException {
        return realArray.getArray();
    }

    public Object getArray(long index, int count) throws SQLException {
        return realArray.getArray(index, count);
    }

    public Object getArray(long index, int count, Map map) throws SQLException {
        return realArray.getArray(index, count, map);
    }

    public Object getArray(Map map) throws SQLException {
        return realArray.getArray(map);
    }

    public int getBaseType() throws SQLException {
        return realArray.getBaseType();
    }

    public String getBaseTypeName() throws SQLException {
        return realArray.getBaseTypeName();
    }

    public ResultSet getResultSet() throws SQLException {
        OpenResultSetException orse = new OpenResultSetException(parentStatement.OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(realArray.getResultSet(), parentStatement,
                sql);
        parentStatement.addResultSet(wrapper, orse);
        return wrapper;
    }

    public ResultSet getResultSet(long index, int count) throws SQLException {
        OpenResultSetException orse = new OpenResultSetException(parentStatement.OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(realArray.getResultSet(index, count),
                parentStatement, sql);
        parentStatement.addResultSet(wrapper, orse);
        return wrapper;
    }

    public ResultSet getResultSet(long index, int count, Map map)
            throws SQLException {
        OpenResultSetException orse = new OpenResultSetException(parentStatement.OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(realArray.getResultSet(index, count, map),
                parentStatement, sql);
        parentStatement.addResultSet(wrapper, orse);
        return wrapper;
    }

    public ResultSet getResultSet(Map map) throws SQLException {
        OpenResultSetException orse = new OpenResultSetException(parentStatement.OPEN_RESULT_SET_EXCEPTION);
        orse.fillInStackTrace();
        ResultSetWrapper wrapper = new ResultSetWrapper(realArray.getResultSet(map),
                parentStatement, sql);
        parentStatement.addResultSet(wrapper, orse);
        return wrapper;
    }

    public void free() throws SQLException {
         realArray.free();
        
    }

}
