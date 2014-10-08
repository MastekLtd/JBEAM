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
* $Revision: 3371 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/IRequestIdGenerator.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/scheduler/IRequestIdGenerator.java $
 * 
 * 6     2/04/09 1:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 5     7/26/05 11:13a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 4     7/01/05 6:26p Kedarr
 * Majir change made. The class now does not extend ISchedule therefore
 * subsequent inherited methods will not be available in this class.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.3  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.2  2003/10/23 09:07:31  kedar
* Changes made for scheduling and added a new Marker Interface
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:21p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/scheduler
 * Organising Imports
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:20p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/scheduler
 * Changes made for $Revision: 3371 $ in the javadoc.
*
*/

package stg.pr.engine.scheduler;

import java.sql.Connection;
import java.sql.SQLException;

/**
* Generates Request Id for further scheduling of requests.
*
* @version $Revision: 3371 $
* @author   Kedar C. Raybagkar
*
*/
public interface IRequestIdGenerator
{
    
    /**
     * Stores the revision number of the class.
     * Comment for <code>strRevision</code>
     */
    static final String strRevision = "$Revision: 3371 $";
    
    /**
    * Set the connection object
    *
    * @param con Connection
    */
    void setConnection(Connection con);
    
    /**
    * Generates Request Id.
    * 
    * @return long Request Id
    * @throws SQLException
    */
    long generateRequestId() throws SQLException;
    
}