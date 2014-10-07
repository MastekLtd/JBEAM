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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/IProcessStatus.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/IProcessStatus.java $
 * 
 * 2     2/04/09 3:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 1     1/11/05 10:01a Kedarr
 *
 * Created on Dec 3, 2004
 *
 */
package stg.pr.engine;

/**
 * To Display the status of the ongoing process of the executed class.
 * 
 * The monitor thread will be responsible to check the status of all the
 * classes that are being executed at that point in time. The class should
 * return the percentage completion of the process that is being executed.
 *  
 * @author Kedar C. Raybagkar
 * @version $Revision:: 2382                 $
 */
public interface IProcessStatus
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * Method returns the percentage completion of the process.
     * 
     * @return double actual percentage of the process.
     */
    public String getStatus();
    
}
