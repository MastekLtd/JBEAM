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
package com.stgmastek.core.logic;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Base class for all execution handler classes. 
 * There are three object types PL - PLSQL, EV - Event Parser, JV - Java 
 *  
 * All default implemented classes {@link PLSQLExecutionHandler}
 * , {@link EventParserObjectExecutionHandler}, {@link JAVAExecutionHandler}
 * If any default implementation is to be changed then a separate class would have be created that 
 * extends this class, implements its abstract method and it is configured in the 
 * CONFIGURATION table accordingly
 */
public abstract class BaseExecutionHandler {
	
	/**
	 * Method for the sub classes to implement and execute a particular type 
	 * of batch object
	 *  
	 * @param batchContext
	 * 		  The context for the batch  
	 * @return the {@link BatchObject} instance with updated status
	 * @throws BatchException
	 * 		   Any exception during execution of the object 
	 */
	public abstract BatchObject execute(BatchObject batchObject, ObjectMapDetails objectMapDetails, BatchContext batchContext) throws BatchException;

    public void shutdown() {
        //do nothing
    }

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/BaseExecutionHandler.java                                                                          $
 * 
 * 5     1/07/10 6:20p Grahesh
 * Updated Java Doc comments
 * 
 * 4     12/24/09 3:21p Grahesh
 * Implemented the logic where by special execution handler classes can be configured from the outside through configurations. Though, the default implementation would be the core
 * 
 * 3     12/18/09 11:34a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/