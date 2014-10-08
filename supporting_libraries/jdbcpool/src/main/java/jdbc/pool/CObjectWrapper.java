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
 * $Revision: 2 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CObjectWrapper.java 2     3/17/08 1:26p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CObjectWrapper.java $
 * 
 * 2     3/17/08 1:26p Kedarr
 * Added REVISION number
 * 
 * 1     5/02/06 4:40p Kedarr
 * A new Object Wrapper. This is a basic implementation and wrapps any
 * object and stores its creation time.
 * 
*/
package jdbc.pool;


/**
 * Simple object wrapper.
 * 
 * This object wrapper will store the date and time when the underlying object was wrapped. The 
 * equals method checks for equals on the underlying wrapped object.  
 *
 * @version $Revision: 2 $
 * @author kedarr
 * @since 15.00
 */
public class CObjectWrapper {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 2                 $";


    /**
     * Underlying object that is wrapped.
     * Comment for <code>object_</code>.
     */
    private Object object_;
    
    /**
     * The time when the object was created.
     * Comment for <code>date_</code>.
     */
    private long time_;

    /**
     * Constructs an Object wrapper over the passed object.
     * @param object Object that needs to be wrapped.
     * @throws NullPointerException if the null object is passed.
     */
    public CObjectWrapper(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        this.object_ = object;
        time_ = System.currentTimeMillis();
    }
    
    /**
     * Returns the time when the object was wrapped.
     *
     * @return Time.
     */
    public long getTime() {
        return time_;
    }
    
    /**
     * Returns the underlying object on which, this object is wrapped.
     *
     * @return Object
     */
    public Object getUnderLyingObject() {
        return object_;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return object_.toString();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        if (arg0 instanceof CObjectWrapper) {
            CObjectWrapper wrapper = (CObjectWrapper) arg0;
            return object_.equals(wrapper.getUnderLyingObject());
        }
        return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return object_.hashCode();
    }
}
