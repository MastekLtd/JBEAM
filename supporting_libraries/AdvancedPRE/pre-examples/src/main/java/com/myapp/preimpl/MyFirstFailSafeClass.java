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
 */
package com.myapp.preimpl;

import stg.pr.engine.CProcessRequestEngineException;
import stg.pr.engine.ProcessRequestServicer;

/**
 * An example class to demonstrate the fail safe nature of PRE.
 *
 *
 * @author Kedar Raybagkar
 * @since V1.0R28.x
 */
public class MyFirstFailSafeClass extends ProcessRequestServicer {

	private long myNumber = 1L;

	/* (non-Javadoc)
	 * @see stg.pr.engine.IProcessRequest#processRequest()
	 */
	public boolean processRequest() throws CProcessRequestEngineException {
		if (getContext().containsKey(getRequestId()+"")) {
			myNumber = ((State)getContext().getAttribute(getRequestId()+"")).getNumber().longValue(); 
		}
		while( myNumber <= 30) {
			try {
				Thread.sleep(1000, 100000);
			} catch (InterruptedException e) {
				//do nothing
			}
			System.out.println("Number is " + myNumber);
			myNumber++;
			if (myNumber%2==0) {
				State state = new State();
				state.setNumber(new Long(myNumber));
				getContext().setAttribute(getRequestId()+"", state);
			}
		}
		getContext().removeAttribute(getRequestId()+"");
		return true;
	}
}
