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
package com.stgmastek.core.interfaces;

import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Interface for implementations or projects to use to build Java Executable objects
 * that can be executed by the system.<p /> 
 * <p />
 * <b>Summary:</b> With the advent of this new batch system, one can now execute Java batch objects as against 
 * previous restriction of PLSQL, or Event Parser (also PLSQL) jobs.
 * <p />  
 * <b>Usage:</b> To create a Java executable job follow the steps. 
 * All Java terminologies and components are shown in <i>italics</i> - <p />
 * <OL>
 * 	<LI> Set the CORE <i>jar</i> (Java ARchive) namely <i>core.jar</i> in the 
 * 		 <i>classpath</i> of the project
 *  <LI> Create a <i>class</i> with the name You choose that <i>implements</i> this <i>interface</i> 
 *  	 namely <i>IExecutableBatchJob</i>
 *  <LI> Implement the <i>abstract methods</i> namely 
 *  	 <i>init(BatchContext)</i> , <i>execute(BatchContext, BatchObject)</i> and <i>destroy(BatchContext)</i>
 *  <LI> Compile the class
 *  <LI> The system uses Process Request Engine for its Scheduling and Execution needs. 
 *  	 So, the <i>classpath</i> for this compiled class has to be in the Process Request Engine's 
 *  	 <i>classpath</i> settings. HINT: Usually inside the <i>startEng.sh</i> OR property <i>javaextraclasspath</i> defined in pr.properties script
 *       or by placing your respctive jar file in the <i>lib</i> directory.
 *  <LI> Set this executable in the JOB_SCHEDULE table with 
 *  	<UL>
 *  		<LI> JOB_DETAIL as the fully qualified name of the class EX: <i>test.ABC</i> 
 *  		<LI> JOB_TPYE as 'JV'</i>
 *  		<LI> Other attributes in the JOB_SCHEDULE follows the same notion as that for a PLSQL job </i>
 *  	</UL>
 *  <LI> Run the batch as one would normally do
 * </OL>
 * <p />
 * The order in which the methods would be called is as - 
 * <OL>
 * 	<LI> {@link IExecutableBatchJob#init(BatchContext)}
 *  <LI> {@link IExecutableBatchJob#execute(BatchContext, BatchObject, ObjectMapDetails) }
 *  <LI> {@link IExecutableBatchJob#destroy(BatchContext)}
 * </OL> 
 * <p />
 * This batch information is the current batch information that picks up the job for execution.
 * Details of any previous revisions for the same batch # is not provided.
 * <p>
 * <b>IMPORTANT</b>
 * <ul>
 *  <li>The implementation must mark itself as {@link com.stgmastek.core.util.Constants.OBJECT_STATUS#COMPLETED} on successful completion
 *  <li>In case of failure the implementation must throw BatchException. JBEAM will take necessary actions such as of marking the batch job to failure.
 *  <li>The classes that implement <i>IExecutableBatchJob</i> must avoid concurrency issues.
 * </ul>
 * </p>
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 */
public interface IExecutableBatchJob {

	/**
	 * This method is called only once during the entire life cycle of the object.
	 * 
	 * One can perform the initialization aspects, if any.
	 *  
	 * @param batchContext
	 * 		  The current batch context
	 */
	public abstract void init(BatchContext batchContext);
	
	/**
	 * The main execution method that executes the given batch object.
	 * 
	 * This method may be invoked multiple times once the instance is created and only after init method is executed.
	 * Make sure that the concurrency issues are properly handled.
	 * 
	 * @param batchContext
	 * 		  The current batch context
	 * @param batchObject
	 * 		  The batch object under consideration 
	 * @param objectMapDetails
	 * 		  The object map details
	 * 
	 * @throws BatchException
	 * 	 	   Any exception occurred during execution of this method wrapped as {@link BatchException}
	 */
	public abstract void execute(BatchContext batchContext, BatchObject batchObject, ObjectMapDetails objectMapDetails) throws BatchException;
	
	/**
	 * Method to do the clean up activities, if any.
	 * 
	 * This method is called only once during the entire life cycle of the instance upon removal of the object
	 * from the cache.
	 *  
	 * @param batchContext
	 * 		  The current batch context
	 */
	public abstract void destroy(BatchContext batchContext);
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/interfaces/IExecutableBatchJob.java                                                                      $
 * 
 * 7     1/13/10 4:14p Grahesh
 * Corrected the links in java doc comments
 * 
 * 6     1/07/10 5:20p Grahesh
 * Updated Java Doc comments
 * 
 * 5     1/07/10 5:12p Grahesh
 * Addition to the signature of init for Batch Object
 * 
 * 4     12/28/09 5:30p Grahesh
 * Using BatchException instead of Throwable
 * 
 * 3     12/23/09 6:43p Grahesh
 * Updated the java doc comments.
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/