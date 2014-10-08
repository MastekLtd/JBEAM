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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/TestLogicalStop.java 1     10/27/09 2:12p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/TestLogicalStop.java $
 * 
 * 1     10/27/09 2:12p Kedarr
 * initial Program
 * 
 */
package com.myapp.preimpl;

import java.util.concurrent.TimeUnit;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.IProcessStatus;
import stg.pr.engine.ITerminateProcess;
import stg.pr.engine.ProcessRequestServicer;

/**
 * An example class to demonstrate logical stop of the program from PRE.
 *
 * @version $Revision: 2959 $
 * @author Kedar Raybagkar
 */
public class TestLogicalStop extends ProcessRequestServicer implements
        ITerminateProcess, IProcessStatus {

    String strStatus_;
	private Thread thread_ = null;
    
    /* (non-Javadoc)
     * @see stg.pr.engine.ITerminateProcess#terminate(java.lang.String)
     */
    public void terminate(String strReason) {
    	System.out.println("You want me to stop the thread loop?? okay okay.. I will...");
    	thread_.interrupt();
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessStatus#getStatus()
     */
    public String getStatus() {
        return strStatus_;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.IProcessRequest#processRequest()
     */
    public boolean processRequest() throws CProcessRequestEngineException {
    	thread_ = Thread.currentThread();
        for (int i = 0; i < 20; i++) {
        	strStatus_ = "Going into a 30 minute sleep till STOP is invoked. This is round " + (i+1) + " of 20.";
        	try {
        		TimeUnit.MINUTES.sleep(30);
        	} catch (InterruptedException e) {
        		System.out.println("I got a stop notification..... ");
        		break;
        	}
		}
        return true;
    }
}
