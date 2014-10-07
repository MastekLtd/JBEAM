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
 * $Revision: 2382 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/IEnquiryHandler.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/utils/IEnquiryHandler.java $
 * 
 * 5     2/04/09 1:12p Kedarr
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
 * 1     11/03/03 12:01p Kedarr
 * Revision 1.1  2003/10/23 06:58:40  kedar
 * Inital Version Same as VSS
 *
*/

package stg.utils;

import java.sql.Connection;
import java.util.HashMap;

/**
 * This interface is used by the servlet to enquire about the parameters associated with the request
 * as stored in the table <code>process_request</code>.
 *
 * @author Kedar Raybagkar
 * @since
 */
public interface IEnquiryHandler {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * Connection that will be set by the servlet
     * @param con
     */
    public void    setConnection(Connection con);
    
    /**
     * Returns the parameters.
     * @return HashMap
     */
    public HashMap<String, String> getParameterDataTypes();
    
    /**
     * Returns the databean.
     * @return Object
     */
    public Object  getDataBean();
    
    /**
     * Executes the processEnquiry.
     * 
     * @throws Exception
     */
    public void    processEnquiry() throws Exception;
    
}

