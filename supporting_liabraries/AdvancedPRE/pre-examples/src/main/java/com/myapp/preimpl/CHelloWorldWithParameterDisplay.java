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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameterDisplay.java 4     3/11/09 6:11p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameterDisplay.java $
 * 
 * 4     3/11/09 6:11p Kedarr
 * Added revision and made changes to extend the abstract class.
 * 
 * 3     10/05/08 3:05p Kedarr
 * 
 * 2     7/10/08 11:47a Kedarr
 * Removed the to do tags.
 * 
 * 1     3/21/07 9:53a Kedarr
 * Sample class that demonstrates IProcessRequestHandler interface.
 * 
 * 
*/
package com.myapp.preimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import stg.utils.IProcessRequestHandler;
import stg.utils.PREDataType;

/**
 * Hello World with Display Parameters.
 *
 * A simple class that implements {@link stg.pr.engine.IProcessRequest}
 *
 * @version $Revision: 2959 $
 * @author kedarr
 *
 */
public class CHelloWorldWithParameterDisplay extends ProcessRequestServicer implements IProcessRequestHandler {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";


    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
    	PrintWriter pwOut_;
        try {
			pwOut_ = getResponseWriter();
        	String myText = (String) super.getParams().get("mytext");
        	pwOut_.println("Hello " + myText);
        	pwOut_.println("Hello " + super.getUserId());
        	pwOut_.println();
        	pwOut_.println(super.getSource().getDescription());
            pwOut_.println("PRE Queue is stored in Database -->" + super.getDataSourceFactory().getDataSource("ST").getConnection().getMetaData().getDatabaseProductName());
            pwOut_.println("Request Id -->" + getRequestId());
            pwOut_.println("Parameters passed -->" + getParams().size());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return true;
    }

    /* (non-Javadoc)
     * @see stg.utils.IProcessRequestHandler#getDisplayParameters()
     */
    public HashMap<String, String> getDisplayParameters() {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("mytext", "Text");
        return hm;
    }

    /* (non-Javadoc)
     * @see stg.utils.IProcessRequestHandler#getParameterDataTypes()
     */
    public HashMap<String, PREDataType> getParameterDataTypes() {
        HashMap<String, PREDataType> hm = new HashMap<String, PREDataType>();
        hm.put("mytext", PREDataType.STRING);
        return hm;
    }

}
