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
 * $Revision: 3 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/IPool.java 3     1/26/09 5:29p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/IPool.java $
 * 
 * 3     1/26/09 5:29p Kedarr
 * Added new functionality to return the acquired objects. This will be
 * leveraged by the implementations to forcefully destroy the acquired
 * objects.
 * 
 * 2     3/17/08 2:40p Kedarr
 * Added REVISION number
 * 
 * 1     5/02/06 4:45p Kedarr
 * A basic pool interface.
 * 
*/
package jdbc.pool;

import java.util.Iterator;

/**
 * This interface defines the basic pool interface.
 * 
 * @version $Revision: 3 $
 * @author kedarr
 * @since 15.00
 */
public interface IPool {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 3                 $";

    /**
     * Variable defines the First In First Out Algorithm.
     * Comment for <code>FIFO_ALGORITHM</code>.
     */
    final String FIFO_ALGORITHM = "FIFO";
    
    /**
     * Variable defines the Last In First Out Algorithm.
     * Comment for <code>FIFO_ALGORITHM</code>.
     */
    final String LIFO_ALGORITHM = "LIFO";
    
    /**
     * Returns the Object stored within the pool.
     * 
     * Returns null if there are no objects in the pool.
     *
     * @return Object
     * @see #release(Object)
     */
    public Object acquire();
    
    /**
     * Adds the Object to the pool.
     * 
     * If the object is already added in the pool then the pool must replace the object with the new one.
     * <i>At any given point in time there must be only one instance of the object in the pool.</i> 
     *
     * @param object
     * @throws NullPointerException if object is null.
     */
    public void add(Object object);
    
    /**
     * Method to return the acquired object back to the pool.
     * 
     * The object that is being released must have been acquired. 
     *
     * @param borrowed Object to be stored.
     * @throws IllegalArgumentException if the object that is being released was not acquired before.
     * @throws NullPointerException if borrowed is null.
     * @see #acquire()
     */
    public void release(Object borrowed);
    
    /**
     * Destroys the pool.
     * 
     * @throws PoolInUseException Exception is thrown if active object count is > zero.
     */
    public void destroy() throws PoolInUseException;
    
    
    /**
     * Destroys the object from the pool.
     * 
     * It is assumed that the object is given out before the call is given to destroy. 
     * If the object is not acquired then the pool must not do anything.
     *
     * @param object to be removed.
     * @throws NullPointerException if the object is null.
     */
    public void destroy(Object object);
    
    /**
     * Returns the idle count of objects lying in the pool.
     *
     * @return Count of idle objects.
     */
    public int getNumIdle();
    
    /**
     * Returns the active or in-use count of objects.
     *
     * @return Count of active or in-use objects.
     */
    public int getNumActive();
    
    /**
     * Returns an iterator to all Idle objects lying in the pool.
     *
     * Iterator will return the {@link CObjectWrapper} class. The caller needs to get the 
     * real underlying object from the wrapper class methods.
     * 
     * @return Iterator.
     * @see CObjectWrapper#getUnderLyingObject()
     */
    public Iterator<CObjectWrapper> getIdleObjects();
    
    /**
     * Returns an array of all acquired objects.
     * @return array of acquired objects.
     */
    public Object[] getAcquiredObjects();
}
