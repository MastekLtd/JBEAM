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
*
* $Revision: 2382 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/IScheduleEvent.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/scheduler/IScheduleEvent.java $
 * 
 * 6     2/04/09 3:16p Kedarr
 * Added static keyword to a final variable.
 * 
 * 5     7/26/05 11:13a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 4     6/23/05 12:25p Kedarr
 * Interface IScheduleEvent has been changed. The PRE and POST event
 * methods will now
 * return a boolean variable. True determines that the schedule is valid
 * and the scheduler
 * can go ahead and pro-create the JOB. If the method returns false, then
 * the schedule
 * will be terminated.
 * 
 * The release will force the current existing programs to adjust
 * themselfs for this new
 * API change in the IScheduleEvent.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     12/29/03 1:00p Kedarr
 * Schedule Event can be associated with a Schedule. This interface expose
 * two methods performPreAction and performPostAction
* Revision 1.1  2003/12/26 11:26:19  kedar
*
* 
*/
package stg.pr.engine.scheduler;




/**
 * A schedule can now be associated with an Event namely pre schedule and post schedule
 * events. This interface provides a way to have the Scheduler call any specific PRE/POST 
 * event associated with the schedule. The scheduler will execute these methods on a
 * single instance created of the class that implements this interface. Thus the state of 
 * the object can be maintained on both the calls.
 * 
 * As this class also extends the {@link stg.pr.engine.scheduler.IScheduleValidator} the same
 * class is used to even validate the schedule.
 * <br>
 * <ul>
 * <li>The order of execution:
 * <dd><b>(a)</b> If the end date or end occurrence is already crossed then do nothing.
 * <dd><b>(b)</b> Instantiate the class that implements IScheduleValidator interface.
 * <dd><b>(c)</b> Validate the schedule. If returns true then proceed else cancel the schedule.
 * <dd><b>(d)</b> The same instance is checked to see if it implements IScheduleEvent. If yes, pre event is executed.
 * <dd><b>(e)</b> The schedule is advanced.
 * <dd><b>(f)</b> The same instance is checked to see if it implements IScheduleEvent. If yes, post event is executed.
 * </li>
 * </ul>
 * 
 * Please note that if the pre or post event raises exceptions then the schedule is terminated.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2382 $
 *
 */
public interface IScheduleEvent extends IScheduleValidator
{
 
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * The method is called before the schedule re-generates the next request
     * as per the schedule associated to the plOldRequestId.
     * 
     * If this method returns false, the schedule will be terminiated. 
     * <br>
     * <B><i>Note</i></b> JDBC <B>commit must not be used</B> here in this method.
     * 
     * @param plOldRequestId The orginal Request id that was processed 
     * @param plScheduleId The schedule associated for the plOldRequestId.
     * @return boolean True to continue further and false to abort schedule.
     */
    public boolean performPreAction(long plOldRequestId, long plScheduleId);
    
    /**
     * The method is called after the schedule re-generates the next request
     * as per the schedule associated to the plOldRequestId.
     * 
     * If this method returns false, the schedule will be terminiated.
     * <br>
     * <B><i>Note</i></b> JDBC <B>commit must not be used</B> here in this method.
     * 
     * @param plNewRequestId The new request id generated after scheduling.
     * @return boolean True to continue further and false to abort schedule.
     */
    public boolean performPostAction(long plNewRequestId);
    
}
