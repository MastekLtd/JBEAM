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
* $Revision: 2958 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/utils/CReloadPropertiesHandler.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/utils/CReloadPropertiesHandler.java $
 * 
 * 11    8/31/09 11:23p Kedarr
 * Initialized the print writer variable.
 * 
 * 10    2/04/09 1:10p Kedarr
 * Added static keyword to a final variable.
 * 
 * 9     3/23/08 1:05p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 8     7/15/05 3:03p Kedarr
 * Updated Javadoc
 * 
 * 7     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
 * 
 * 6     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 5     2/24/05 2:34p Kedarr
 * Changes made to set the detailedLogging of the logger class.
 * 
 * 4     2/11/05 4:22p Kedarr
 * Changed for printing HTML log to the print writer object. Also, add
 * code for updating the logger on the fly for the log level set.
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
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:19a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Organising Imports
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 12/06/03   Time: 4:58p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/utils
 * Removed the Main Method.
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

import java.io.IOException;
import java.util.HashMap;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;


/**
 * Class to re-load the properties of PRE. 
 *
 * @author Kedar Raybagkar
 * @since
 */
public class CReloadPropertiesHandler extends ProcessRequestServicer implements IProcessRequestHandler {

    /**
     * Stores the Version Number of the class.
     * Comment for code <code>REVISION</code>
     */
    public static final String REVISION = "$Revision:: 2958          $";
    

    /** Standard Constructor
     */
    public CReloadPropertiesHandler() {
    }
    
    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
		CSettings.getInstance().reLoad();
		try {
			getResponseWriter().println("<HTML>");
			getResponseWriter().println("<BODY>");
			getResponseWriter().println("<h1>Reloaded Properties</h1>");
			getResponseWriter().println("</BODY>");
			getResponseWriter().println("</HTML>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
    }
    
    /* (non-Javadoc)
     * @see stg.utils.IProcessRequestHandler#getParameterDataTypes()
     */
    public HashMap<String, PREDataType> getParameterDataTypes() {
		return null;
    }
    
    /* (non-Javadoc)
     * @see stg.utils.IProcessRequestHandler#getDisplayParameters()
     */
    public HashMap<String, String> getDisplayParameters() {
        return null;
    }
    
} //end of class