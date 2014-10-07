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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/TestKill.java 2     10/27/09 1:02p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/TestKill.java $
 * 
 * 2     10/27/09 1:02p Kedarr
 * Changes made for sleeping.
 * 
 * 1     3/24/09 10:05a Kedarr
 * Initial Version
 * 
 */
package com.myapp.preimpl;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.IProcessStatus;
import stg.pr.engine.ProcessRequestServicer;

/**
 * This class is coded to enter into an infinite loop.
 *
 * Please use the PRE web server to kill this process.
 * http://<ipaddress>:<port> as defined in the pr.properties.
 *
 * @author Kedar Raybagkar
 */
public class TestKill extends ProcessRequestServicer implements IProcessStatus {

    private String strStatus_;
    
    private boolean toContinue = true;

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
    	while (toContinue) {
    		strStatus_ = "Going into infinite loop. This will be shown in the web console.";
    		try {
    			Thread.yield();
				Thread.sleep(3600000);
			} catch (InterruptedException e) {
				// do nothing
			}
    	}
        return true;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessStatus#getStatus()
     */
    public String getStatus() {
        return strStatus_;
    }

}
