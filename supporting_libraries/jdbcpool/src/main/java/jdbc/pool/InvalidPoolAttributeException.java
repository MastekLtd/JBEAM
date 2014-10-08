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
 * $Revision: 5 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/InvalidPoolAttributeException.java 5     3/17/08 2:40p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/InvalidPoolAttributeException.java $
 * 
 * 5     3/17/08 2:40p Kedarr
 * Added REVISION number
 * 
 * 4     3/02/07 10:08a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 3     1/16/06 1:24p Kedarr
 * Added Source Safe header. This will enable us to track the revision
 * number of the source from the compiled binary files.
 * 
 * 2     8/29/05 1:48p Kedarr
 * Changes made for updating JavaDoc.
 * 
 * 1     8/29/05 1:19p Kedarr
 * Added a new Exception
 * 
*/
package jdbc.pool;

/**
 * Invalid pool attribute exception.
 *
 * This exception is thrown whenever the pool manager finds a specified pool attribute to be invalid. 
 *
 * @version $Revision: 5 $
 * @author kedarr
 *
 */
public class InvalidPoolAttributeException extends Exception {

    /**
	 * Serial version. 
	 */
	private static final long serialVersionUID = -7542394739301596685L;
	
	/**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public static final String REVISION = "$Revision:: 5         $";

    /**
     * Default Constructor.
     * @see Exception#Exception()
     */
    public InvalidPoolAttributeException() {
        super();
    }

    /**
     * @param arg0 
     * @see Exception#Exception(java.lang.String)
     */
    public InvalidPoolAttributeException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0 
     * @see Exception#Exception(java.lang.Throwable)
     */
    public InvalidPoolAttributeException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0 
     * @param arg1 
     * @see Exception#Exception(java.lang.String, java.lang.Throwable)
     */
    public InvalidPoolAttributeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
