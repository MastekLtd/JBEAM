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
 * $Revision: 2959 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorld.java 3     3/11/09 6:08p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorld.java $
 * 
 * 3     3/11/09 6:08p Kedarr
 * Added revision and made changes to extend the abstract class.
 * 
 * 2     7/08/08 10:59p Kedarr
 * Parameters if not associated with the request then hashmap is passed as
 * null resulting into NullPointerException.
 * 
 * 1     3/14/07 2:14p Kedarr
 * Sample Class that shows how to build classes around PRE
 * 
*/
package com.myapp.preimpl;

import java.io.PrintStream;
import java.sql.SQLException;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;

/**
 * Hello World
 *
 * A simple class that implements {@link stg.pr.engine.IProcessRequest}
 *
 * @version $Revision: 2959 $
 * @author kedarr
 *
 */
public class CHelloWorld extends ProcessRequestServicer {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";



    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#endProcess()
     */
    public void endProcess() throws CProcessRequestEngineException {
		System.out.println("End Process called..");
		System.out.println("End Process clean activities performed.");
		super.endProcess();
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
        try {
        	PrintStream pwOut_ = System.out;
        	pwOut_.println("Hello World !!");
        	pwOut_.println("Hello " + getUserId());
        	pwOut_.println();
        	pwOut_.println(super.getSource().getDescription());
				pwOut_.println("PRE Queue is stored in Database --> " + getConnection().getMetaData().getDatabaseProductName());
				pwOut_.println("Request Id --> " + getRequestId());
				pwOut_.println("Parameters passed --> " + ((getParams() == null)?0:getParams().size()));
        } catch (SQLException e) {
            e.printStackTrace();
		}
        throw new CProcessRequestEngineException("Bla Bla Bla");
//        return true;
    }
}
