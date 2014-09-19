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

import java.util.LinkedList;

import com.stgmastek.core.aspects.Marker;
import com.stgmastek.core.exception.BatchException;
import com.stgmastek.core.exception.BatchStopException;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.Constants;
import com.stgmastek.core.util.EntityParams;
import com.stgmastek.core.util.ExecutionStatus;
import com.stgmastek.core.util.ProgressLevel;
import com.stgmastek.core.util.ScheduledExecutable;

/**
 * 
 * The main assigner class for the assigning activity within the Core system  
 * 
 * This class has only one method to instantiate and execute appropriate 
 * assignment handler classes 
 * For all META events like the PRE and the POST, it instantiates and executes 
 * the {@link MetaEventsHandler}, and for batch events (jobs) it instantiates 
 * and executes the {@link BatchEventsHandler}
 * 
 * @author grahesh.shanbhag
 * 
 */
public abstract class Assigner {
	
	/**
	 * The main method that does two things - 
	 * <OL>
	 * 	<LI> Assignment - Assigning listener identifier to batch objects
	 * 	<LI> Scheduling - Scheduling of the needed listeners 
	 * </OL>
	 * The event handler that it instantiates and executes depends upon 
	 * the type of entity under consideration EX: PRE, BATCH, or POST 
	 * 
	 * This method is iteratively called for each of the internal cycles 
	 * as needed. An internal cycle is the one where within an entity, the next has 
	 * to wait on the previous one for completion before kicking off
	 * EX: PRE  jobs with priority code set as '2' has to wait on the PRE jobs 
	 * with priority code '1', So there would be two distinct internal cycle for 
	 * priority code within the external cycle for PRE jobs.         
	 * 
	 * <b>This method is marked with {@link Marker} to indicate that before 
	 * execution of this method the check of 'end of batch' has to be performed</b>   
	 *  
	 * @param batchContext
	 * 		  The context for the batch 
	 * @param entityParams
	 * 	 	  The instance of {@link EntityParams} that holds the 
	 * 		  information of the entity under consideration 
	 * @param cycleCounter
	 * 		  The internal looping cycle counter
	 * @return the list of {@link ScheduledExecutable} that informs the 
	 * 		   caller about the scheduled objects for this current assignment  
	 * @throws BatchException
	 * 		   Any exception, including database I/O wrapped
	 * @throws BatchStopException
	 * 		   Special exception, that indicates the caller that the batch 
	 * 		   has encountered a state where it has to stop all its activities
	 */
	@Marker
	public static LinkedList<ScheduledExecutable> assignAndSchedule(
												BatchContext batchContext, 
												EntityParams entityParams,												
												Integer cycleCounter) 
												throws BatchException, 
												BatchStopException {
		
		//Get the appropriate handler instance 
		//depending upon the entity under consideration 
		AssignerHandler handler = null;
		String entity = entityParams.getEntity();
		
		if(entity.equals(Constants.META_EVENTS.PRE.name()) 
				|| entity.equals(Constants.META_EVENTS.POST.name()))
			handler = new MetaEventsHandler();
		else
			handler = new BatchEventsHandler();
		
		Integer batchNo= batchContext.getBatchInfo().getBatchNo();
		//Do the assignment and get the number of listeners to spawn  
		ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
											entity, ProgressLevel.ACTIVITY.ASSIGNMENT, cycleCounter);
		ProgressLevel.getProgressLevel(batchNo).setExecutionStatus(
											entityParams.getEntity(), ExecutionStatus.ASSIGNMENT);
		Integer noOfListenersToSpawn = handler.assign(batchContext, entityParams);
		System.out.println("noOfListenersToSpawn : " + noOfListenersToSpawn );
		//Schedule the number of listeners 
		ProgressLevel.getProgressLevel(batchNo).setProgressLevel(
											entity, ProgressLevel.ACTIVITY.SCHEDULING, cycleCounter);
		ProgressLevel.getProgressLevel(batchNo).setExecutionStatus(
											entityParams.getEntity(), ExecutionStatus.SCHEDULING);
		ProgressLevel.getProgressLevel(batchNo).setFailedOver(batchContext.getBatchInfo().isFailedOver());
		LinkedList<ScheduledExecutable> scheduledExecutables = handler.schedule(batchContext, noOfListenersToSpawn);
		
		//return the scheduled listener details 
		return scheduledExecutables;
	}
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/logic/Assigner.java                                                                                      $
 * 
 * 7     4/21/10 11:57a Kedarr
 * Changes made as per the new enum in progress level.
 * 
 * 6     3/30/10 1:58p Kedarr
 * Changes made to return a Linked List
 * 
 * 5     3/24/10 12:57p Kedarr
 * Corrected the mistake of POST being executed using the normal batch listener count instead of pre post.
 * 
 * 4     3/11/10 11:49a Kedarr
 * Changes made for setting failed over status in progress level.
 * 
 * 3     12/18/09 11:34a Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/