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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/CDB.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/CDB.java $
 * 
 * 7     2/04/09 3:17p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     3/22/08 3:45p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 5     9/13/05 11:19a Kedarr
 * Changes made to get the JDBC Types from the property file and therefore
 * made it much more generic in nature. Now any user can define different
 * JDBC Connection types through property changes.
 * 
 * 4     8/24/05 10:53a Kedarr
 * Changes made to get the product name for DB2 Database.
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:12p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/database
 * Organising Imports
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/database
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:47p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
*
*/


/**
* Database Related package.
*/
package stg.database;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;

import stg.utils.CSettings;



/**
* Collection of some useful Database related methods.
**/
public class CDB extends Object
{

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 2382              $";

    /**
     * Returns the Revision number of the class.
     * Identifies the version number of the source code that generated this class file stored
     * in the configuration management tool.
     * 
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }


    //public instance constants and class constants
    
    /**
     * Stores the maximum fetch size.
     */
    public final static int MAX_FETCH_SIZE = 500;

    //public instance variables


    //public class(static) variables


    //protected instance constants and class constants

    //protected instance variables

    //protected class(static) variables


    //package instance constants and class constants

    //package instance variables

    //package class(static) variables


    //private instance constants and class constants

    //private instance variables

    //private class(static) variables


    //constructors

    // Standard constructor for the class
    /**
     * Default constructor.
     */
    public CDB()
    {
        super();
    }

    //finalize method, if any

    //main method

    //public methods of the class in the following order


    /**
    * Returns the JDBC connection type as defined in the properties.
    *
    *
    * @param   pcon JDBC Connection.
    * @return String
    * @throws  CDBException
    **/
    public static String getDBType(Connection pcon) throws CDBException {
        DatabaseMetaData dmd;
        try {
            dmd = pcon.getMetaData();
            String dbProductName = dmd.getDatabaseProductName();
            StringTokenizer tokenizer = new StringTokenizer(CSettings
                    .get("jdbctype.provider"), ";");
            while (tokenizer.hasMoreTokens()) {
                String provider = tokenizer.nextToken();
                if ("indexOf".equals(CSettings
                        .get("jdbctype.method", "indexOf"))) {
                    if (dbProductName.indexOf(CSettings.get("jdbctype."
                            + provider)) >= 0) {
                        return provider;
                    }
                } else if ("startsWith".equals(CSettings.get("jdbctype.method",
                        "indexOf"))) {
                    if (dbProductName.startsWith(CSettings.get("jdbctype."
                            + provider))) {
                        return provider;
                    }
                } else if ("endsWith".equals(CSettings.get("jdbctype.method",
                        "indexOf"))) {
                    if (dbProductName.endsWith(CSettings.get("jdbctype."
                            + provider))) {
                        return provider;
                    }
                }

            }
            throw new CDBException(CDBMessages.INVALID_DATABASE_TYPE);
        } catch (SQLException e) {
            throw new CDBException(e.getMessage());
        }
    }
    //protected constructors and methods of the class

    //package constructors and methods of the class

    //private constructors and methods of the class


} //end of DB.java
