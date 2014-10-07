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
 * $Revision: 3523 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameter.java 3     3/11/09 6:09p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithParameter.java $
 * 
 * 3     3/11/09 6:09p Kedarr
 * Added revision and made changes to extend the abstract class also added
 * example code to show the usage of PREContext.
 * 
 * 2     7/10/08 11:47a Kedarr
 * Removed the TODO tags also changed the code to display the parameters
 * values associated to the request.
 * 
 * 1     3/14/07 2:14p Kedarr
 * Sample Class that shows how to build classes around PRE
 * 
*/
package com.myapp.preimpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map.Entry;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;

/**
 * Hello World
 *
 * A simple class that implements {@link stg.pr.engine.IProcessRequest}
 *
 * @version $Revision: 3523 $
 * @author kedarr
 *
 */
public class CHelloWorldWithParameter extends ProcessRequestServicer {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 3523              $";



    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
    	PrintWriter pwOut_;
    	Connection con = null;
        try {
            con = super.getDataSourceFactory().getDataSource("ST").getConnection();
			pwOut_ = getResponseWriter();
        	String myText = (String) super.getParams().get("mytext");
        	pwOut_.println("Hello " + myText);
        	pwOut_.println("Hello " + getUserId());
        	pwOut_.println();
        	pwOut_.println(super.getSource().getDescription());
            pwOut_.println("PRE Queue is stored in Database -->" + con.getMetaData().getDatabaseProductName());
            pwOut_.println("Request Id -->" + super.getRequestId());
            pwOut_.println("Parameters passed -->" + super.getParams().size());
            for (Entry<String, Object> entry : super.getParams().entrySet()) {
            	String key = entry.getKey();
            	Object value = entry.getValue();
            	System.out.print("Key# \"" + key + "\" \t");
            	if (value instanceof java.lang.Object[]) {
            		System.out.println("Array passed. Values are:");
            		Object[] array = (Object[]) value;
            		for (int i = 0; i < array.length; i++) {
            			System.out.print(i+1 + "\t");
            			printDataValue(array[i]);
            		}
            	} else {
            		printDataValue(value);
            	}
            }
            String str = (String) getContext().getAttribute("mysetting");
            if ( str == null) {
            	getContext().setAttribute("mysetting", "myvalue " + super.getRequestId());
            } else {
            	System.out.println("Got the value as set " + str);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
		    if (con != null) {
		        try {
                    con.close();
                } catch (SQLException e) {
                }
		    }
		}
        return true;
    }

    /**
     * Prints the data stored within the Object.
     * @param value Object whose data value needs to be printed.
     */
    private void printDataValue(Object value) {
        if (value instanceof java.lang.String) {
            System.out.println("Type# String Data# " + value);
        } else if (value instanceof java.lang.Integer) {
            System.out.println("Type# Integer Dalue# " + ((Integer)value).intValue());
        } else if (value instanceof java.lang.Long) {
            System.out.println("Type# Long Data#" + ((Long)value).longValue());
        } else if (value instanceof java.lang.Double) {
            System.out.println("Type# Double Data# " + ((Double)value).doubleValue());
        } else if (value instanceof java.sql.Date) {
            System.out.println("Type# java.sql.Date Data# " + ((java.sql.Date)value).toString());
        } else if (value instanceof java.sql.Timestamp) {
            System.out.println("Type# java.sql.Timestamp Data# " + ((java.sql.Timestamp)value).toString());
        } else {
            System.out.println("Unknown value passed");
        }
    }
    
}
