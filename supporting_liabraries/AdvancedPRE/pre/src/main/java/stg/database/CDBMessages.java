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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/database/CDBMessages.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/database/CDBMessages.java $
 * 
 * 5     2/04/09 3:17p Kedarr
 * Added static keyword to a final variable.
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
 * User: Kedarr       Date: 10/12/02   Time: 3:47p
 * Created in $/ProcessRequestEngine/gmac/database
 * Initial Version
*
*/



package stg.database;


/**
* This class stores all the Final variables with Messages in it.
**/
public class CDBMessages extends CMessages
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * Stores the message for invalid connection.
     */
    public final static String INVALID_CONNECTION = "Invalid Connection Object";

    /**
     * Stores the message for invalid database type.
     */
    public final static String INVALID_DATABASE_TYPE = "Could Not Recognize the Database Connection Type";
    
    /**
     * Stores the message for un-defined sequence.
     */
    public final static String SEQUENCE_NOT_DEFINED = "Un-Defined Sequence. Contact Administrator.";

    /**
     * Stores message for un-successful population of the bean.
     */
    public final static String UNABLE_POPULATE_BEAN = "Unable to Populate Bean";
    
    /**
     * Stores the oder by clause error message.
     */
    public final static String ORDER_BY_IN_WHERE_CLAUSE = "Order By Clause cannot be there in Where Clause";

    /**
     * Stores the oder by keyword error message.
     */
    public final static String ORDER_BY_KEYWORD = "Order By Keyword Not Found.";


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
   
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order
  
    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
    /**
     * Default
     */
    public CDBMessages()
    {
    }
    
    
} //end of CDBMessages.java
