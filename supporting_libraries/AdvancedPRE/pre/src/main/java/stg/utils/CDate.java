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
* $Revision: 2382 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CDate.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/CDate.java $
 * 
 * 11    8/31/09 11:22p Kedarr
 * Changes made to catch specific exceptions instead of general try catch
 * exception block.
 * 
 * 10    2/04/09 1:04p Kedarr
 * Added static keyword to a final variable.
 * 
 * 9     4/07/08 5:04p Kedarr
 * Changed the REVISION variable to public and updated java documentation.
 * 
 * 8     3/22/08 12:31a Kedarr
 * Added REVISION variable.
 * 
 * 7     5/03/06 2:11p Kedarr
 * The Statement was getting closed before the ResultSet was closed. This
 * is rectified.
 * 
 * 6     9/13/05 11:46a Kedarr
 * Changes made to get the repective Query from pr.properties for the
 * respective JDBC Types as returned by the CDB class.
 * 
 * 5     8/24/05 10:52a Kedarr
 * Changes made to get the current SQL Timestamp from DB2 database
 * 
 * 4     7/26/05 11:14a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 5  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:10p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Organised the Import statements. Removed unncessary imports.
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:22p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for $Revision: 2382 $ in the javadoc.
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 8/11/03    Time: 3:52p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changed to throw IOException & SQLException
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 12/06/03   Time: 6:23p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Changes made for JavaDoc
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:50p
 * Created in $/DEC18/ProcessRequestEngine/gmac/utils
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:57p
 * Created in $/ProcessRequestEngine/gmac/utils
 * Initial Version
*
*/

package stg.utils;

import stg.database.CDB;
import stg.database.CDBException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/** Collection of usefull Date methods which simplifies the coding for
*    converting Strings into Dates and vice-versa.
*    <UL>
*      <LI> Most methods take java.sql.Date as arguments
*    </UL>
*
* @version $Revision: 2382 $
* @author   Kedar C. Raybagkar
*
*/
public class CDate
{
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
//    private static SimpleDateFormat sdfDatetimeFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
//    private static SimpleDateFormat sdfTimeFormat = new SimpleDateFormat( "HH:mm:ss" );
//    private static SimpleDateFormat sdfDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

    private CDate()
    {
        //so that an object cannot be created of CDate
    }


    /**
     * Returns the current database date in java.sql.Date format.
     *
     * @param con JDBC Connection for fetching the database date.
     * @return java.sql.Date
     * @throws IOException
     * @throws SQLException
     */
    public static java.sql.Date getCurrentSQLDate(Connection con)
        throws IOException, SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        java.sql.Date sdt = null;
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery(getSQL(con));
            if (rs.next())
            {
                sdt = rs.getDate(1);
            }
        }
        catch (CDBException cdbe) {
            
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
            } catch(SQLException e) {
                //dummy
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                //dummy
            }
        }
        return(sdt);
    }

    /**
     * Returns the current timestamp from the Database Server.
     *
     * @param con JDBC Connection for fetching the database timestamp.
     * @return java.sql.Timestamp
     * @throws IOException
     * @throws SQLException
     */
    public static Timestamp getCurrentSQLTimestamp( Connection con )
        throws IOException, SQLException
    {
        Statement stmt = null;
        ResultSet rs = null;
        Timestamp ts = null;
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery(getSQL(con));
            if (rs.next())
            {
                ts = rs.getTimestamp(1);
            }
        }
        catch (CDBException cdbe)
        {
            throw new SQLException(cdbe.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
            } catch(SQLException e) {
                //IO Exception which is not to be thrown
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                // dummy.
            }
        }
        return(ts);
   }

    /**
     * Returns the User Defined Format (UDF) date in the format supplied.
     *
     * @param pudt java.util.Date
     * @param pstrFormat {@link SimpleDateFormat} format.
     * @return Formatted String
     * @see #getUDFDateString(java.sql.Date, String)
     * @see #getUDFDateString(java.sql.Timestamp, String)
     */
    public static String getUDFDateString (java.util.Date pudt, String pstrFormat)
    {
        if (pudt==null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat( pstrFormat );
        sdf.setLenient(false);
        return sdf.format( pudt );
    }

    /**
     * Returns the User Defined Format (UDF) date in the format supplied.
     *
     * @param psdt java.sql.Date
     * @param pstrFormat {@link SimpleDateFormat} format.
     * @return Formatted String
     * @see #getUDFDateString(java.util.Date, String)
     * @see #getUDFDateString(java.sql.Timestamp, String)
     */
    public static String getUDFDateString (java.sql.Date psdt, String pstrFormat)
    {
        if (psdt==null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat( pstrFormat );
        sdf.setLenient(false);
        return sdf.format( psdt );
    }

    /**
     * Returns the User Defined Format (UDF) date in the format supplied.
     *
     * @param pts java.sql.Timestamp
     * @param format {@link SimpleDateFormat} format.
     * @return Formatted String
     * @see #getUDFDateString(java.util.Date, String)
     * @see #getUDFDateString(java.sql.Date, String)
     */
    public static String getUDFDateString (java.sql.Timestamp pts, String format)
    {
        if (pts==null) return null;
        java.sql.Date sdt = new java.sql.Date( pts.getTime() );
        return getUDFDateString( sdt, format );
    }

    /**
     * Returns the User Defined Format (UDF) date by converting the supplied character date with supplied format.
     *
     * @param pstrDate formatted date in String format.
     * @param pstrFormat {@link SimpleDateFormat} format.
     * @return java.util.Date
     * @throws ParseException 
     */
    public static java.util.Date getUDFUtilDate (String pstrDate, String pstrFormat)
        throws ParseException
    {
        if( pstrDate == null ) return null;
        if( pstrDate.equals("") ) return null;

        SimpleDateFormat sdf = new SimpleDateFormat( pstrFormat );
        sdf.setLenient(false);
        return sdf.parse( pstrDate );
    }

    /**
     * Returns the User Defined Format (UDF) date by converting the supplied character date with supplied format.
     *
     * @param pstrDate formatted date in String format.
     * @param pstrFormat {@link SimpleDateFormat} format.
     * @return java.sql.Date
     * @throws ParseException 
     */
    public static java.sql.Date getUDFDate (String pstrDate, String pstrFormat) throws ParseException
    {
        if( pstrDate == null ) return null;
        if( pstrDate.equals("") ) return null;

        SimpleDateFormat sdf = new SimpleDateFormat( pstrFormat );
        sdf.setLenient(false);
        return new java.sql.Date(sdf.parse( pstrDate ).getTime());
    }

    /**
     * Returns the User Defined Format (UDF) date by converting the supplied character date with supplied format.
     *
     * @param pstrTimestamp formatted date in String format.
     * @param pstrFormat {@link SimpleDateFormat} format.
     * @return java.sql.Timestamp
     * @throws ParseException 
     */
    public static Timestamp getUDFTimestamp (String pstrTimestamp, String pstrFormat) throws ParseException
    {
        if( pstrTimestamp == null ) return null;
        if( pstrTimestamp.equals("") ) return null;

        return new Timestamp(getUDFDate(pstrTimestamp, pstrFormat).getTime());

    }

    /** Converts java.util.Date to java.sql.Date.
        @param pudt java.util.Date
        @return java.sql.Date for the given java.util.Date.
    */
    public static java.sql.Date getSQLDate( java.util.Date pudt )
    {
        if (pudt == null) return null;
        return new java.sql.Date(pudt.getTime());
    }

    /** Converts java.sql.Date to java.util.Date.

        @param  psdt java.sql.Date
        @return java.util.Date for the given java.sql.Date.
    */
    public static java.util.Date getUtilDate( java.sql.Date psdt )
    {
        if( psdt == null ) return psdt;
        return new java.util.Date(psdt.getTime());
    }


    /**
     * Returns the SQL query that needs to be executed to get the current timestamp from database.
     *
     * @param pcon JDBC Connection
     * @return String SQL.
     * @throws CDBException
     */
    private static String getSQL(Connection pcon )
        throws CDBException
    {
        String sql = CSettings.get("pr." + CDB.getDBType(pcon) + ".timestamp.sql");
        if (sql == null || sql.equals("")){
            throw new CDBException("Current Timestamp Query not defined in the pr.properties for JDBC Connection type #" + CDB.getDBType(pcon));
        }
        return sql;
    }

}
