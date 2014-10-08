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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/CFormatterException.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/CFormatterException.java $
 * 
 * 5     2/04/09 3:18p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     3/22/08 3:45p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 3     3/01/07 4:55p Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 2     1/11/05 9:58a Kedarr
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     12/01/03 1:28p Kedarr
* Revision 1.1  2003/11/20 11:46:18  kedar
* Initial Versions
*
*
*/


package stg.database;




/**
* Thrown when CFormatter is unable to clone the values from one object to another.
**/
public class CFormatterException extends CDBException
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    //public class(static) variables
    
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
    
    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -7491220775806204512L;

	/**
    * Constructs an Object of CFormatterException
    **/
    public CFormatterException()
    {
        super();
    }
    
    /**
    * Constructs an Object of CFormatterException
    * @param pstr any Message string.
    **/
    public CFormatterException(String pstr)
    {
        super(pstr);
    }
    

    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
    
    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
    
} //end of CFormatterException.java
