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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/scheduler/IPreDefinedFrequency.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/scheduler/IPreDefinedFrequency.java $
 * 
 * 5     2/04/09 1:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     7/12/05 9:55a Kedarr
 * Updated JavaDoc.
 * 
 * 3     7/11/05 7:06p Kedarr
 * Updated JavaDoc.
 * 
 * 2     6/20/05 12:22p Kedarr
 * Added a new parameter to help the implementor to identify the parameter
 * name associated with the date parameter associated with the request id.
 * 
 * 1     6/20/05 10:50a Kedarr
 * This class defines a new type of Frequency i.e. Programmable Frequency.
 * Any class that implements this interface, will be used for scheduling
 * the request.
 * 
 * Created on Jun 20, 2005
 *
 */
package stg.pr.engine.scheduler;

import java.util.Date;

/**
 * This class defines a Programmable Frequency.
 *
 * The class will be used to advance the date parameters for a schedule.
 *
 * @author Kedar C. Raybagkar.
 * @version $Revision: 2382 $
 */
public interface IPreDefinedFrequency extends IScheduleValidator {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * This method is responsible to advance the date parameter with its own pre-defined
     * frequency.
     * The scheduler will call this method by passing the scheduled date & time that needs
     * to be advanced based on the pre-defined frequency. The scheduler will identify the 
     * parameter <code>pGivenDate</code> as a parameter date associated with the request by
     * passing a boolean variable with value equals true. The field name associated with the
     * parameter date is also passed for simplicity.
     * <BR><BR>
     * <b>Note:</b> If the method returns a date which is less than the scheduled date and time
     * then the schedule will be terminated.
     * <BR><BR>
     * The class should refer to the attributes of {@link stg.pr.beans.ProcessRequestScheduleEntityBean}
     * that may be required to do an appropriate schedule.
     * 
     * @param pGivenDate Date to be advanced.
     * @param bParameter True if the date passed is a Parameter Date associated with the Request.
     * @param pstrFieldName The field name associated with the date.
     * 
     * @return Date Advanced date.
     */
    public Date advanceDay(Date pGivenDate, boolean bParameter, String pstrFieldName);
    
}
