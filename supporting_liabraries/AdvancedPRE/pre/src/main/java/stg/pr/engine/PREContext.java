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
 * $Revision: 3309 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/PREContext.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/PREContext.java $
 * 
 * 3     8/24/09 5:50p Kedarr
 * Added some easy convenience methods.
 * 
 * 2     3/21/09 3:48p Kedarr
 * Changes made to as per the concurrenthashmap api.
 * 
 * 1     2/04/09 4:46p Kedarr
 * Initial version.
 * 
 */
package stg.pr.engine;

import java.io.Serializable;
import java.util.Set;

/**
 * Defines the context for requests to communicate with PRE. 
 *
 * There will be one context per instance of JVM.
 *
 * @author Kedar Raybagkar
 * @since  V1.0R26.00
 */
public interface PREContext extends Serializable {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 3309              $";

    /**
     * Returns the PREInfo
     * @return information regarding the release numbers
     */
    public PREInfo getPREInfo();
 
    /**
     * Returns true if the PRE is configured in Production Deployment mode.
     * True indicates that the classes will not be reloaded even though they
     * have changed. 
     * @return boolean
     */
    public boolean isPREInProductionDeploymentMode();

    /**
     * Returns the SerializableK stored against the key passed as a parameter.
     * 
     * @param name key
     * @return Serializable value identified by the key.
     */
    public Serializable getAttribute(String name);
    
    /**
     * Assigns and stores the object against the key.
     * 
     * @param name Key.
     * @param object Value.
     * @return Returns the old value if there was any otherwise null.
     */
    public Serializable setAttribute(String name, Serializable object);
    
    /**
     * Returns an enumeration of all the keys.
     * Remember to cast it to String.
     * 
     * @return Enumeration of all the keys.
     */
    public Set<String> getAttributeNames();
    
    /**
     * Removes the given attribute from the context and returns the value.
     * 
     * @param name Key.
     * @return Serializable that was set against the key
     */
    public Serializable removeAttribute(String name);
    
    /**
     * Removes the Serializable only if the value matches.
     * 
     * @param name key
     * @param object
     * @return True if removed.
     */
    public boolean removeAttribute(String name, Serializable object);

    /**
     * Returns true if the value object is available in the context else returns false.
     * 
     * @param value Serializable
     * @return boolean
     */
    public boolean containsValue(Serializable value);
    
    /**
     * Returns true if the key set in the context else returns false.
     * 
     * @param key Key to be searched for.
     * @return boolean
     */
    public boolean containsKey(String key);
    
    /**
     * Sets the Serializable if it is not present.
     * @param name Key
     * @param object any {@link Serializable} object.
     * @return Serializable. Old object is returned if it was set before.
     */
    public Serializable setAttributeIfAbsent(String name, Serializable object);
    
    
    /**
     * Returns true if the PRE is actively scanning for newer jobs otherwise returns false.
     * False indicates that a stop request was processed by PRE and therefore this PRE is no more active.
     * The jobs if they are pro-creating newer jobs in PRE and then are waiting for the pro-created jobs to
     * finish then this method must be checked to see if PRE is still active to process these pro-created jobs.
     * 
     * @return boolean
     */
    public boolean isPREActivelyScanning();
    
    /**
     * Returns the count of currently running stand alone jobs.
     * @return int
     */
    public int getCurrentlyRunningSJobs();
    
    /**
     * Returns the count of currently running group jobs.
     * @return int
     */
    public int getCurrentlyRunningGJobs();
    
    /**
     * Returns the instance associated with the service name. 
     * @param serviceName key
     * @return {@link Singleton}
     */
    public Singleton<?> getSingleton(String serviceName);
    
}
