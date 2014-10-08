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
 * $Header: /Utilities/JDBCPool/src/jdbc/tuning/OpenStatementException.java 2     3/02/07 9:40a Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/tuning/OpenStatementException.java $
 * 
 * 2     3/02/07 9:40a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 1     5/09/05 2:37p Kedarr
 * Initial Version
 * 
 * 1     12/30/03 4:39p Kedarr
 * Revision 1.1  2003/12/30 07:50:56  kedar
 * An exception class is created which is thrown if the JDBC statement 
 * created is not explicitly closed and the connection is returned to the pool.
 *
 * 
 */
package jdbc.tuning;

/**
 * Exception class raised when statments are created but not closed while
 * returning the connection object to pool.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2 $
 *  
 */
public class OpenStatementException extends Exception {

    /**
	 * Serial Version. 
	 */
	private static final long serialVersionUID = -9173868693261638541L;
	
	public final String REVISION = "$Revision: 2 $";

    /**
     * Standarded Constructor.
     */
    public OpenStatementException() {
        super();
    }

    /**
     * Standard Constructor
     * 
     * @param arg0
     *            Message
     */
    public OpenStatementException(String arg0) {
        super(arg0);
    }

    /**
     * Standard Constructor
     * 
     * @param arg0
     *            Message
     * @param arg1
     *            Throwable
     */
    public OpenStatementException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Standard Constructor
     * 
     * @param arg0
     *            Throwable object.
     */
    public OpenStatementException(Throwable arg0) {
        super(arg0);
    }

}
