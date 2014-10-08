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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/IController.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/IController.java $
 * 
 * 6     2/04/09 3:18p Kedarr
 * Added static keyword to a final variable.
 * 
 * 5     5/13/08 5:28p Kedarr
 * Changes made to method specifications to throw SQLException instead of
 * just thrown the beanexception. This way the underlying exception is not
 * lost.
 * 
 * 4     3/22/08 3:45p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
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
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/database
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:49p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
*
*/


package stg.database;

import java.sql.SQLException;




/**
* An Interface for Bean Controller.
* A call-back is done on these methods by Formatter class.
**/
public interface IController
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    
    //public class(static) variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
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
    
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
    
    /**
     * Populates the bean using the call back method on the object that implements this interface.
     * @param pobj Object that implements the method IController.
     * @param prs ResultSet from which the data is to be populated.
     * @return boolean True if success else false.
     * @throws CBeanException
     * @throws SQLException
     */
    public boolean populateBean(java.lang.Object pobj, java.sql.ResultSet prs) throws CBeanException, SQLException;
    
    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
    
} //end of IController.java
