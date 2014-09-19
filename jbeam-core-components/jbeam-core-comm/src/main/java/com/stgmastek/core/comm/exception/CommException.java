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
package com.stgmastek.core.comm.exception;

/**
 * 
 * Wrapper exception class for all exceptions with the core communication system 
 * 
 * @author grahesh.shanbhag
 *
 */
public class CommException extends Exception{

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The constructor that takes the exception (occurred) as the argument
	 *   
	 * @param t
	 * 		  The exception thrown
	 */
	public CommException(Throwable t) {
		super(t);
	}

	/** 
	 * The constructor that takes the exception (occurred) as the argument
	 *   
	 * @param excMessage
	 * 		  The exception thrown
	 */
	public CommException(String excMessage) {
		super(excMessage);
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/exception/CommException.java                                                                    $
 * 
 * 2     12/17/09 11:55a Grahesh
 * Initial Version
*
*
*/