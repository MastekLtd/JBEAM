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
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CStackObjectPool.java 3     1/26/09 5:30p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CStackObjectPool.java $
 * 
 * 3     1/26/09 5:30p Kedarr
 * Implementation of the newly added functionality to forcefully destroy
 * the acquired objects.
 * 
 * 2     3/17/08 2:43p Kedarr
 * Added REVISION number
 * 
 * 1     5/02/06 4:46p Kedarr
 * A Stack object pool implementation of the Interface IPool. This is also
 * known as First-In-Last-Out algorithm.
 * 
 */
package jdbc.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Stack object pool implementation of {@link jdbc.pool.IPool}.
 * 
 * Algorithm used is that of Last-In-First-Out.
 * 
 * None of the methods are synchronized. The implementor should take care of concurrency.
 *  
 * @version $Revision: 3 $
 * @author kedarr
 * @since 15.00
 */
public class CStackObjectPool implements IPool {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 3                 $";


    /**
     * Locked Object pool. Comment for <code>alLockedObjectPool_</code>.
     */
    private ArrayList<Object> alLockedObjectPool_;

    /**
     * Unlocked object pool. Comment for <code>alUnLockedObjectPool_</code>.
     */
    private Stack<CObjectWrapper> stackUnLockedObjectPool_;

    /**
     * Default Constructor.
     */
    public CStackObjectPool() {
        super();
        alLockedObjectPool_ = new ArrayList<Object>();
        stackUnLockedObjectPool_ = new Stack<CObjectWrapper>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see jdbc.pool.IPool#give(java.lang.Object)
     */
    public Object acquire() {
        if (stackUnLockedObjectPool_.size() > 0) {
            CObjectWrapper obj = stackUnLockedObjectPool_.pop();
            alLockedObjectPool_.add(obj.getUnderLyingObject());
            return obj.getUnderLyingObject();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see jdbc.pool.IPool#destroy()
     */
    public void destroy() throws PoolInUseException {
        if (getNumActive() > 0) {
            throw new PoolInUseException();
        }
        stackUnLockedObjectPool_.clear();
    }

    /*
     * (non-Javadoc)
     * 
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
     * @see jdbc.pool.IPool#getIdleObjects()
     */
    public Iterator<CObjectWrapper> getIdleObjects() {
        return stackUnLockedObjectPool_.iterator();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#add(java.lang.Object)
     */
    public void add(Object object) {
        CObjectWrapper wrapper = new CObjectWrapper(object);
        if (stackUnLockedObjectPool_.contains(wrapper)) {
            stackUnLockedObjectPool_.remove(wrapper);
        }
        stackUnLockedObjectPool_.push(wrapper);
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getNumIdle()
     */
    public int getNumIdle() {
        return stackUnLockedObjectPool_.size();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getNumActive()
     */
    public int getNumActive() {
        return alLockedObjectPool_.size();
    }

    /* (non-Javadoc)
     * @see jdbc.pool.IPool#getAcquiredObjects()
     */
    public Object[] getAcquiredObjects() {
        return alLockedObjectPool_.toArray();
    }

}
