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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/IProcessRequestHandler.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/IProcessRequestHandler.java $
 * 
 * 5     2/04/09 1:13p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     3/22/08 12:31a Kedarr
 * Added REVISION variable.
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
 * User: Kedarr       Date: 10/12/02   Time: 3:58p
 * Created in $/ProcessRequestEngine/gmac/utils
 * Initial Version
*
*/

 
package stg.utils;

import java.util.HashMap;

/**
 * An interface that helps to display the parameters with appropriate data types and labels.
 *
 * @author Kedar Raybagkar
 * @since
 */
public interface IProcessRequestHandler {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";

    /**
     * Returns the DataTypes
     * @return HashMap parameter name as key and value as {@link PREDataType}
     */
    public HashMap<String, PREDataType> getParameterDataTypes();
	
	/**
	 * Returns HashMap containing parameter name as key and the lable as the value.
	 * @return HashMap
	 */
	public HashMap<String, String> getDisplayParameters();
    
}

