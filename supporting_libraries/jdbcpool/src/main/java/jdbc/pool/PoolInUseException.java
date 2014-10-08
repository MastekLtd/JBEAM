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
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/PoolInUseException.java 3     3/17/08 2:39p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/PoolInUseException.java $
 * 
 * 3     3/17/08 2:39p Kedarr
 * Added REVISION number
 * 
 * 2     3/02/07 10:08a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 1     5/02/06 4:44p Kedarr
 * Pool In Use exception class.
 * 
*/
package jdbc.pool;

/**
 * PoolInUseException.
 * 
 * This exception is if the pool has not got all the acquired objects back to
 * the pool.
 *
 * @version $Revision: 3 $
 * @author kedarr
 * @since 15.00
 */
public class PoolInUseException extends Exception {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 3                 $";
    
    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 296730859181475544L;

	/* (non-Javadoc)
     * @see java.lang.Exception#Exception()
     */
    public PoolInUseException() {
        super();
    }

    /* (non-Javadoc)
     * @see java.lang.Exception#Exception(String arg0)
     */
    public PoolInUseException(String arg0) {
        super(arg0);
    }

    /* (non-Javadoc)
     * @see java.lang.Exception#Exception(Throwable arg0)
     */
    public PoolInUseException(Throwable arg0) {
        super(arg0);
    }

    /* (non-Javadoc)
     * @see java.lang.Exception#Exception(String arg0, Throwable arg0)
     */
    public PoolInUseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
