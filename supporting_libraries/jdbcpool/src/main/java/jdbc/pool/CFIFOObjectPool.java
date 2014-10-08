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
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CFIFOObjectPool.java 3     1/26/09 5:32p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CFIFOObjectPool.java $
 * 
 * 3     1/26/09 5:32p Kedarr
 * Implementation of the newly added functionality to forcefully destroy
 * the acquired objects.
 * 
 * 2     3/17/08 1:25p Kedarr
 * Change the VERSION number to REVISION (standard)
 * 
 * 1     5/02/06 4:44p Kedarr
 * A First-In-First-Out object pool implementation of the Interface IPool.
 * 
*/
package jdbc.pool;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * First-In-First-Out alogrithm based implementation of {@link jdbc.pool.IPool}.
 * 
 * None of the methods are synchronized. The implementor should take care of concurrency.
 *
 * @version $Revision: 3 $
 * @author kedarr
 * @since 15.00
 */
public class CFIFOObjectPool implements IPool {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 3                 $";


    /**
     * Locked object pool array list.
     * Comment for <code>alLokedObjectPool_</code>.
     */
    private ArrayList<Object> alLockedObjectPool_;
    
    /**
     * Un-Locked object pool array list.
     * Comment for <code>alUnLockedObjectPool_</code>.
     */
    private ArrayList<CObjectWrapper> alUnLockedObjectPool_;
    
    /**
     * Default Constructor.
     * Constructs the FIFO algorithm based pool.
     */
    public CFIFOObjectPool() {
        super();
        alLockedObjectPool_ = new ArrayList<Object>();
        alUnLockedObjectPool_ = new ArrayList<CObjectWrapper>();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#give(java.lang.Object)
     */
    public Object acquire() {
        if (alUnLockedObjectPool_.size() > 0) {
            CObjectWrapper obj = alUnLockedObjectPool_.get(0);
            alLockedObjectPool_.add(obj.getUnderLyingObject());
            alUnLockedObjectPool_.remove(obj);
            return obj.getUnderLyingObject();
        }
        return null;
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#take(java.lang.Object, java.lang.Object)
     */
    public void release(Object borrowed) {
        if (borrowed == null) {
            throw new NullPointerException();
        }
        if (alLockedObjectPool_.contains(borrowed)) {
            alLockedObjectPool_.remove(borrowed);
            add(borrowed);
        } else {
            throw new IllegalArgumentException("This object was never pooled.");
        }
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#destroy()
     */
    public void destroy() throws PoolInUseException{
        if (getNumActive() > 0) {
            throw new PoolInUseException();
        }
        alUnLockedObjectPool_.clear();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#destroy(java.lang.Object)
     */
    public void destroy(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        if (alLockedObjectPool_.contains(object)) {
            alLockedObjectPool_.remove(object);
        }
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getNumIdle()
     */
    public int getNumIdle() {
        return alUnLockedObjectPool_.size();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getNumActive()
     */
    public int getNumActive() {
        return alLockedObjectPool_.size();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getIdleObjects()
     */
    public Iterator<CObjectWrapper> getIdleObjects() {
        return alUnLockedObjectPool_.iterator();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#add(java.lang.Object)
     */
    public void add(Object object) {
        CObjectWrapper wrapper = new CObjectWrapper(object);
        if (alUnLockedObjectPool_.contains(wrapper)) {
            alUnLockedObjectPool_.remove(wrapper);
        }
        alUnLockedObjectPool_.add(new CObjectWrapper(object));
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getAcquiredObjects()
     */
    public Object[] getAcquiredObjects() {
        return alLockedObjectPool_.toArray();
    }
}
