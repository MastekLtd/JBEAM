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
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/OpenResultSetException.java 2     3/02/07 9:40a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/OpenResultSetException.java $
 * 
 * 2     3/02/07 9:40a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 1     5/02/06 4:48p Kedarr
 * Open Result Set Exception.
 * 
*/
package jdbc.tuning;

import java.sql.SQLException;

/**
 * OpenResultSetException.
 *
 * This is thrown when the ResultSet is not closed explicitly.
 *
 * @version $Revision: 2 $
 * @author kedarr
 *
 */
public class OpenResultSetException extends SQLException {

    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -8502658272676953657L;

	/**
     * Default Constructor. 
     */
    public OpenResultSetException() {
        super();
    }

    /**
     * Default Constructor.
     * @param arg0 Message.
     */
    public OpenResultSetException(String arg0) {
        super(arg0);
    }

    /**
     * Default Constructor.
     *
     * @param arg0
     * @param arg1
     */
    public OpenResultSetException(String arg0, String arg1) {
        super(arg0, arg1);
    }

    /**
     * Default Constructor.
     *
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public OpenResultSetException(String arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

}
