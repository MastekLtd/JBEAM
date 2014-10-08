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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/IProcessRequestHandlerExt.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/IProcessRequestHandlerExt.java $
 * 
 * 4     2/04/09 1:13p Kedarr
 * Added static keyword to a final variable.
 * 
 * 3     3/22/08 12:31a Kedarr
 * Added REVISION variable.
 * 
 * 2     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* 
*/

package stg.utils;

import java.sql.Connection;

/**
 * The class will benifit in dynamically getting the data types
 * and the labels of the parameters used for that process. This
 * is a extended framework class, that extends {@link stg.utils.IProcessRequestHandler}.
 * 
 * Created on Aug 5, 2004.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2382 $
 * 
 */
public interface IProcessRequestHandlerExt extends IProcessRequestHandler
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * Sets the Connection object.
     * 
     * @param con java.sql.Connection
     */
    public void setConnection(Connection con);
    
    /**
     * Sets the associated request id.
     * 
     * @param lRequestId long
     */
    public void setRequestId(long lRequestId);

}
