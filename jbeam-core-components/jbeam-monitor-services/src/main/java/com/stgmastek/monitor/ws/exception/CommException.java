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
package com.stgmastek.monitor.ws.exception;

/**
 * 
 * Wrapper exception class for all exceptions with the Monitor system 
 * 
 * @author mandar.vaidya
 *
 */
public class CommException extends Exception{

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The constructor that takes the exception (occurred) as the argument
	 *   
	 * @param e
	 * 		  The exception thrown
	 */
	public CommException(Exception e) {
		super(e);
	}
	
	/** 
	 * The constructor that takes the user error message as the argument
	 *   
	 * @param string
	 * 		  The exception message as string
	 */
	public CommException(String string) {
		super(string);
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/exception/CommException.java                                                            $
 * 
 * 3     12/30/09 1:06p Grahesh
 * Corrected the javadoc for warnings
 * 
 * 2     12/17/09 12:01p Grahesh
 * Initial Version
*
*
*/