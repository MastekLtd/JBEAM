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
* $Revision: 2778 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/CStopEngine.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/startstop/CStopEngine.java $
 * 
 * 9     2/05/09 10:53p Kedarr
 * Added static keyword to final variable also now extends process request
 * servicer.
 * 
 * 8     3/23/08 1:13p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 7     3/22/08 12:32a Kedarr
 * Removed the static keyword from the REVISION variable and made it
 * private. In case of interfaces made it as public.
 * 
 * 6     3/22/08 12:16a Kedarr
 * Added REVISION variable.
 * 
 * 5     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
 * 
 * 4     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.2  2006/02/14 06:16:39  kedar
* Changes made for Logging Exceptions to System.err stream
*
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 7  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:20p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/startstop
 * Organising Imports
 * 
 * *****************  Version 6  *****************
 * User: Kedarr       Date: 9/02/03    Time: 6:27p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/startstop
 * Changes made for $Revision: 2778 $ in the javadoc.
 * 
 * *****************  Version 5  *****************
 * User: Kedarr       Date: 6/06/03    Time: 4:30p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/startstop
 * Added JavaDoc
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 4/06/03    Time: 2:27p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/startstop
 * Inital Version. Version Header changed as the program was copied from
 * another existing program
*
*/

package stg.pr.engine.startstop;


import java.io.PrintWriter;
import java.util.HashMap;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngine;
import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import stg.utils.IProcessRequestHandler;
import stg.utils.PREDataType;


/**
* This class is used to stop the Engine.
*
* This class does not execute System.exit(0). If any other class which implements IProcessRequest
* interface and internally calls System.exit(int) then the results are unpredictable.
* 
* @version $Revision: 2778 $
* @author   Kedar C. Raybagkar
**/
public class CStopEngine extends ProcessRequestServicer implements IProcessRequestHandler, IStartStop
{
    
    
    //public instance constants and class constants
    
    //public instance variables
    
   
    
    //public class(static) variables
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2778              $";
    
    //protected instance constants and class constants
    
    //protected instance variables
    
    //protected class(static) variables
    
    
    //package instance constants and class constants
    
    //package instance variables
    
    //package class(static) variables
    
    
    //private instance constants and class constants
    
    //private instance variables

    private Logger logger_;
    
    //private class(static) variables
    
    
    //constructors
    
    /**
    * Standard constructor for the class
    **/
    public CStopEngine()
    {
        logger_ = Logger.getLogger("StopEngine");
    }
    
    //finalize method, if any
    
    //main method
    
    //public methods of the class in the following order

    //Implementing the IProcessRequest interface methods    

    public boolean processRequest() throws CProcessRequestEngineException
    {
        try
        {
            CProcessRequestEngine.getInstance().stopEngine(this);
            PrintWriter pwOut_ = getResponseWriter();
            pwOut_.println("<html>");
            pwOut_.println("<h1>Stop Notification sent to the Engine.</h1>");
            pwOut_.println("</html>");
        }
        catch (Exception e)
        {
            logger_.error(e);
            throw new CProcessRequestEngineException(e.getClass().getName() + "-->" + e.getMessage());
        }
        finally
        {
        }
        return(true);
    }

    public void endProcess() throws CProcessRequestEngineException
    {
    }

	/* (non-Javadoc)
	 * @see stg.utils.IProcessRequestHandler#getDisplayParameters()
	 */
	public HashMap<String, String> getDisplayParameters() {
		return null;
	}

	/* (non-Javadoc)
	 * @see stg.utils.IProcessRequestHandler#getParameterDataTypes()
	 */
	public HashMap<String, PREDataType> getParameterDataTypes() {
		return null;
	}


    //Implementation ends here.

    //protected constructors and methods of the class
    
    //package constructors and methods of the class
    
    //private constructors and methods of the class
    
} //end of CStopEngine.java
