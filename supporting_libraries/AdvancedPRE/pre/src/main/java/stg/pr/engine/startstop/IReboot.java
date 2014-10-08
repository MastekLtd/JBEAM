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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/IReboot.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/startstop/IReboot.java $
 * 
 * 4     2/04/09 1:24p Kedarr
 * Added static keyword to a final variable.
 * 
 * 3     3/22/08 12:32a Kedarr
 * Removed the static keyword from the REVISION variable and made it
 * private. In case of interfaces made it as public.
 * 
 * 2     3/22/08 12:16a Kedarr
 * Added REVISION variable.
 * 
 * 1     1/16/06 7:00p Kedarr
 * A marker interface that marks the implementor class that enables the
 * engine Reboot.
 * 
*/
package stg.pr.engine.startstop;

/**
 * A marker interface.
 *
 * @version $Revision: 2382 $
 * @author kedarr
 *
 */
public interface IReboot extends IStartStop {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
}
