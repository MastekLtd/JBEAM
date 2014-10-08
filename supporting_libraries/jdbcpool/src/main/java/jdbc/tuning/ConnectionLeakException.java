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
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/ConnectionLeakException.java 2     3/02/07 9:40a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/ConnectionLeakException.java $
 * 
 * 2     3/02/07 9:40a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * 
 * 1     12/01/03 1:35p Kedarr
 * Revision 1.1  2003/11/28 09:47:21  kedar
 * Added a new package for Tunning SQL queries.
 *
 * 
 */
package jdbc.tuning;

import java.sql.SQLException;

/**
 * Connection Leak Exception is thrown when the connection leak is found.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2 $
 *  
 */
public class ConnectionLeakException extends SQLException {

    /**
	 * Serial version. 
	 */
	private static final long serialVersionUID = 2206324896428049765L;
	
	public final String REVISION = "$Revision: 2 $";

    /**
     * Constructs a fully-specified SQLException object.
     * 
     * @param reason
     *            a description of the exception
     * @param SQLState
     *            an XOPEN or SQL 99 code identifying the exception
     * @param vendorCode
     *            a database vendor-specific exception code
     */
    public ConnectionLeakException(String reason, String SQLState,
            int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    /**
     * Constructs an SQLException object with the given reason and SQLState; the
     * vendorCode field defaults to 0.
     * 
     * @param reason -
     *            a description of the exception
     * @param SQLState -
     *            an XOPEN or SQL 99 code identifying the exception
     */
    public ConnectionLeakException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    /**
     * Constructs an SQLException object with a reason; the SQLState field
     * defaults to null, and the vendorCode field defaults to 0.
     * 
     * @param reason
     *            a description of the exception.
     */
    public ConnectionLeakException(String reason) {
        super(reason);
    }

    /**
     * Constructs an SQLException object; the reason field defaults to null, the
     * SQLState field defaults to null, and the vendorCode field defaults to 0.
     */
    public ConnectionLeakException() {
        super();
    }

}
