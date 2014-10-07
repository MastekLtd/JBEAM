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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CDateException.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/CDateException.java $
 * 
 * 7     2/04/09 1:04p Kedarr
 * Added static keyword to a final variable.
 * 
 * 6     4/07/08 5:04p Kedarr
 * Added/changed the REVISION variable to public.
 * 
 * 5     3/22/08 12:31a Kedarr
 * Added REVISION variable.
 * 
 * 4     3/02/07 9:14a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
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

/**
*   Thrown when CDate is unable to format or convert a given parameter.
*/
public class CDateException extends Exception
{

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    

    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -1398048399778877760L;


	/**
	 * Default Constructor.
	 */
	public CDateException()
    {
        super();
    }


    /**
     * Accepts a message.
     * @param pstr
     */
    public CDateException(String pstr)
    {
        super(pstr);
    }

}