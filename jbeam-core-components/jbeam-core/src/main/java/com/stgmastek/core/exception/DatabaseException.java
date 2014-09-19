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
 * 
 * Exception class for all database related exceptions 
 * 
 * @author grahesh.shanbhag
 *
 * @see BatchException
 */
public class DatabaseException extends BatchException {
	
	/** The Serial Version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Public constructor that takes the Throwable as the argument
	 * 
	 * @param e
	 * 		  The database exception that occurred during any 
	 * 		  database related activity 
	 */
	public DatabaseException(Throwable e){
		super(e);
	}
	
	/**
	 * Public constructor that takes the message as the argument.
	 * 
	 * @param message
	 */
	public DatabaseException(String message) {
		super(message);
	}
	
	/**
	 * Public constructor that takes the message and throwable as the argument
	 * 
	 * @param message
	 * @param cause
	 */
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/exception/DatabaseException.java                                                                         $
 * 
 * 4     3/08/10 1:20p Kedarr
 * Added new constructors.
 * 
 * 3     12/17/09 12:35p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/