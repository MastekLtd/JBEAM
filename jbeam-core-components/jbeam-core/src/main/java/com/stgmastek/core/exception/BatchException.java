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
 * Base Exception class for the system 
 * 
 * @author grahesh.shanbhag
 *
 */
public class BatchException extends Exception{

	/** The Serial Version UID */
	private static final long serialVersionUID = 115676413496878L;
	
	/** 
	 * Attribute to indicate the system to call the destroy method
	 * Default is set to true 
	 */
	private Boolean closeBatch = true;
	
	/**
	 * Default Constructor 
	 */
	public BatchException() {
	}
	
	/**
	 * Public constructor that takes the exception as the argument
	 * 
	 * @param t
	 * 		  Any exception that occurred during the execution of the 
	 * 		  system 
	 */
	public BatchException(Throwable t) {
		super(t);
	}
	
	/**
	 * Public constructor that takes the exception as the argument
	 * 
	 * @param t
	 * 		  Any exception that occurred during the execution of the 
	 * 		  system 
	 * @param closeBatch
	 *        An indicator to close the batch.
	 */
	public BatchException(Throwable t, boolean closeBatch) {
	    super(t);
	    this.closeBatch = closeBatch;
	}
	
	
	/**
	 * Public constructor that takes the exception as the argument
	 * 
	 * @param message
	 * 		  The error message 
	 */
	public BatchException(String message) {
		super(message);
	}	

	/**
	 * Public constructor that takes the exception as the argument
	 * In some cases, when this exception is raised, it is needed that the destroy method is called
	 * whereas, in some case it is not. So it additionally takes the indicator as an argument. 
	 * 
	 * @param message
	 * 		  The error message
	 * @param closeBatch
	 * 		  An indicator to close the batch 
	 */
	public BatchException(String message, Boolean closeBatch) {
		super(message);
		this.closeBatch = closeBatch;
	}	
	
    /**
     * Public constructor that takes the exception as the argument
     * In some cases, when this exception is raised, it is needed that the destroy method is called
     * whereas, in some case it is not. So it additionally takes the indicator as an argument. 
     * 
     * @param message
     *        The error message
     * @param t
     *        The cause. 
     */
    public BatchException(String message, Throwable t) {
        super(message, t);
    }
    
    /**
     * Public constructor that takes the exception as the argument
     * In some cases, when this exception is raised, it is needed that the destroy method is called
     * whereas, in some case it is not. So it additionally takes the indicator as an argument. 
     * 
     * @param message
     *        The error message
     * @param t
     *        The cause
     * @param closeBatch
     *        An indicator to close the batch 
     */
    public BatchException(String message, Throwable t, Boolean closeBatch) {
        super(message, t);
        this.closeBatch = closeBatch;
    }   
    
	/**
	 * Returns an indicator for destroy method to be called 
	 *  
	 * @return an indicator for destroy method to be called
	 */
	public Boolean callCloseBatch(){
		return this.closeBatch;
	}
	
} 

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/exception/BatchException.java                                                                            $
 * 
 * 6     7/06/10 10:53a Kedarr
 * Update the serial version numbers.
 * 
 * 5     2/25/10 9:45a Grahesh
 * Modified/Added constructors to accept throwable.
 * 
 * 4     12/23/09 11:55a Grahesh
 * Changes done to separate batch run date from batch execution date time
 * 
 * 3     12/17/09 12:35p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/