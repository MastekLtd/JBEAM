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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/CRebootEngine.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/startstop/CRebootEngine.java $
 * 
 * 4     2/04/09 1:14p Kedarr
 * Added static keyword to a final variable.
 * 
 * 3     3/23/08 1:04p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 2     3/23/06 2:53p Kedarr
 * Updated the javadoc and added the since clause in the class level
 * documentation.
 * 
 * 1     1/16/06 7:00p Kedarr
 * Class that reboots the engine.
*
*/

package stg.pr.engine.startstop;


import java.io.PrintWriter;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngine;
import stg.pr.engine.CProcessRequestEngineException;


/**
* This class is used to Reboot the Engine.
*
* This class does not execute System.exit(0). If any other class which implements IProcessRequest
* interface and internally calls System.exit(int) then the results are unpredictable.
* 
* @version $Revision: 2382 $
* @author   Kedar C. Raybagkar
* @since 22.00
**/
public class CRebootEngine extends CStopEngine implements  IReboot
{
    
    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public static final String REVISION = "$Revision:: 2382      $";

    /**
    * This variable stores the response writer
    *
    **/
    private PrintWriter pwOut_;
    
    private Logger logger_;
    
    
    
    //private class(static) variables
    
    
    //constructors
    
    /**
    * Standard constructor for the class
    **/
    public CRebootEngine()
    {
        logger_ = Logger.getLogger("RebootEngine");
    }
    

	/**
	 * Sets the PrintWriter object on which the logging will be done.
     * This writer can be response.getWriter() or Process request Engine's log writer.
	 *
	 * @param   pwLogWriter  
	 */
    public void setResponseWriter(PrintWriter pwLogWriter)
    {
        this.pwOut_ = pwLogWriter;
    }



    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException
    {
        try
        {
            CProcessRequestEngine.getInstance().stopEngine(this);
            pwOut_.println("<html>");
            pwOut_.println("<h1>Reboot Notification sent to the Engine.</h1>");
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

    
} //end of CStopEngine.java
