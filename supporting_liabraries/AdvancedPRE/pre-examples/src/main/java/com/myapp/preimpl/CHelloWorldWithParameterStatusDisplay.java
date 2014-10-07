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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameterStatusDisplay.java 4     3/11/09 6:11p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameterStatusDisplay.java $
 * 
 * 4     3/11/09 6:11p Kedarr
 * Added revision and made changes to extend the abstract class.
 * 
 * 3     10/05/08 3:05p Kedarr
 * 
 * 2     7/10/08 11:47a Kedarr
 * Removed the to do tags.
 * 
 * 1     3/14/07 2:14p Kedarr
 * Sample Class that shows how to build classes around PRE
 * 
*/
package com.myapp.preimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.IProcessStatus;
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
public class CHelloWorldWithParameterStatusDisplay extends ProcessRequestServicer implements IProcessStatus {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";


    private String strStatus_;

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
    	PrintWriter pwOut_;
        try {
			pwOut_ = super.getResponseWriter();
        	strStatus_ = "processRequest method start";
        	String myText = (String) super.getParams().get("mytext");
        	pwOut_.println("Hello " + myText);
        	pwOut_.println("Hello " + getRequestId());
        	pwOut_.println();
        	pwOut_.println(getSource().getDescription());
        	strStatus_ = "Requesting the product name from the associated JDBC Connection object.";
            pwOut_.println("PRE Queue is stored in Database -->" + super.getDataSourceFactory().getDataSource("ST").getConnection().getMetaData().getDatabaseProductName());
            pwOut_.println("Request Id -->" + getRequestId());
            pwOut_.println("Parameters passed -->" + getParams().size());
            strStatus_ = "processRequest method end";
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessStatus#getStatus()
     */
    public String getStatus() {
        return strStatus_;
    }

}
