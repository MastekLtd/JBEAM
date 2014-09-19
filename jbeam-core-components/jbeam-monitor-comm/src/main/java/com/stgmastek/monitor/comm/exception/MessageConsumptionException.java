/*
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.monitor.comm.exception;

public class MessageConsumptionException extends CommException{

	/** Serial Version UID */	
	private static final long serialVersionUID = 1L;

	/** 
	 * The constructor that takes the exception (occurred) as the argument
	 *   
	 * @param t
	 * 		  The exception thrown
	 */
	public MessageConsumptionException(Throwable t) {
		super(t);
	}	
	
	/** 
	 * The constructor that takes the exception (occurred) as the argument
	 *   
	 * @param excMessage
	 * 		  The exception thrown
	 */
	public MessageConsumptionException(String excMessage) {
		super(excMessage);
	}	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorComm/src/com/stgmastek/monitor/comm/exception/MessageConsumptionException.java                                                $
 * 
 * 3     12/18/09 4:07p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:59a Grahesh
 * Initial Version
*
*
*/