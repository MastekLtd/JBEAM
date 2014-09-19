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
package com.stgmastek.core.exception;

/**
 * Special exception class to indicate the system to stop the proceedings
 * 
 * @author grahesh.shanbhag
 *
 */
public class BatchStopException extends Exception{

	/** The serial version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor 
	 */
	public BatchStopException(){		
	}
	
	/**
	 * Default constructor 
	 * @param message String
	 */
	public BatchStopException(String message){
		super(message);
	}
	
	/**
	 * Default constructor 
	 * @param cause Throwable
	 */
	public BatchStopException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Default constructor 
	 * @param message String
	 * @param cause Throwable
	 */
	public BatchStopException(String message, Throwable cause){
		super(message, cause);
	}
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/exception/BatchStopException.java                                                                        $
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/