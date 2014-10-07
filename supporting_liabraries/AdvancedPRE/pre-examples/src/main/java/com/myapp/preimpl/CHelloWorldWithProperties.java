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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithProperties.java 2     11/16/09 10:35a Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CHelloWorldWithProperties.java $
 * 
 * 2     11/16/09 10:35a Kedarr
 * Changed the property name to reflect the class name in the key.
 * 
 * 1     3/11/09 6:14p Kedarr
 * Initial version.
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

import java.util.Enumeration;
import java.util.Properties;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;
import stg.utils.CSettings;
import stg.utils.ISettingsSource;

/**
 * Hello World
 *
 * A simple class that implements {@link stg.pr.engine.IProcessRequest}
 *
 * @version $Revision: 2959 $
 * @author kedarr
 *
 */
public class CHelloWorldWithProperties extends ProcessRequestServicer {


    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2959              $";


    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
        //fetching specific property
        System.out.println(CSettings.get("pr.systemloadedclasses"));
        System.out.println(CSettings.get("mail.defaultmessageheader"));
        //fetching and printing all properties from pr.
        ISettingsSource source = CSettings.getInstance().getSource("pr");
        Properties properties = source.getProperties();
        Enumeration<?> enumerator = properties.keys();
        while(enumerator.hasMoreElements()) {
            String key = (String) enumerator.nextElement();
            System.out.println("Key " + key + "\t Value " + properties.getProperty(key));
        }
        String key = this.getClass().getName() + ".specificKey";
        System.out.println("System property " + key + "  value " + CSettings.get("system." + key));
        System.out.println("MIS Property mis.key " + CSettings.get("mis.key") );
        return true;
    }

}
