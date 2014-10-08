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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/IStopEvent.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/startstop/IStopEvent.java $
 * 
 * 3     3/22/08 12:32a Kedarr
 * Removed the static keyword from the REVISION variable and made it
 * private. In case of interfaces made it as public.
 * 
 * 2     3/22/08 12:16a Kedarr
 * Added REVISION variable.
 * 
 * 1     8/22/05 12:30p Kedarr
 * A new interface that will help the implementor of this interface know
 * that a stop request has been requested.
 * 
*/
package stg.pr.engine.startstop;

/**
 * Interface that enables the implementor to get the Engine Stop Event notification.
 *
 * @version $Revision: 2382 $
 * @author kedarr
 *
 */
public interface IStopEvent {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final String REVISION = "$Revision:: 2382              $";
    
    /**
     * The Engine will call this method on a running JOB that implements this interface if a stop 
     * request is serviced by the Engine. 
     *
     * The implementor class must then do accordingly to stop itself. Please note that the Engine 
     * will wait till the JOB dies its own death. Therefore it is important to know for the JOB 
     * that the stop request has been requested and the JOB should then take care to terminate 
     * itself gracefully.
     *
     */
    public void notifyEngineStopEvent();

}
