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
 * $Revision: 6 $
 *
 * $Header: /Utilities/JDBCPool/junit-source/jdbc/pool/FIFOObjectPoolTest.java 6     3/17/08 3:17p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/junit-source/jdbc/pool/FIFOObjectPoolTest.java $
 * 
 * 6     3/17/08 3:17p Kedarr
 * Added REVISION number variable. Also, updated the javadoc
 * 
 * 5     3/28/07 8:53a Kedarr
 * Renamed TCFIFOObjectPool.java to FIFOObjectPoolTest.java
 * 
 * 4     5/09/06 1:38p Kedarr
 * Changes made in the acquire to check for initial counts.
 * 
 * 3     4/10/06 12:49p Kedarr
 * Changed Give to Acquire and Take to Release.
 * 
 * 2     4/06/06 6:24p Kedarr
 * Refactored for method give and take to acquire and release.
 * 
 * 1 jdbc.pool06 5:23p Kedarr
 * Tests the FIFO algorithm for the IPool class.
 * 
*/
package jdbc.pool;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Tests the First-In-First-Out loFIFOObjectPoolTestersion $Revision: 6 $
 * @author kedarr
 * @since 15.00
 */
public class FIFOObjectPoolTest extends TestCase {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 6                 $";
    
    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.acquire()'
     */
    public void testAcquire() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        assertEquals("Idle Count Before", 4, pool.getNumIdle());
        assertEquals("Active Count Before", 0, pool.getNumActive());
        assertEquals("Acquire test ", date1, (String) pool.acquire());
        assertEquals("Idle Count After", 3, pool.getNumIdle());
        assertEquals("Active Count After", 1, pool.getNumActive());
    }

    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.Release(Object)'
     */
    public void testRelease() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        String date = (String) pool.acquire();
        assertEquals("Before Release Idle Count ", 3, pool.getNumIdle());
        assertEquals("Before Release Active Count ", 1, pool.getNumActive());
        pool.release(date);
        assertEquals("After Release Idle Count ", 4, pool.getNumIdle());
        assertEquals("After Release Active Count ", 0, pool.getNumActive());
        try {
            pool.release(date4);
            fail("Release should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("IllegalArgumentException ", "This object was never pooled.", e.getMessage());
        }
    }

    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.destroy()'
     */
    public void testDestroy() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        assertEquals("Idle Count ", 4, pool.getNumIdle());
        assertEquals("Active Count ", 0, pool.getNumActive());
        try {
            pool.destroy();
        } catch (PoolInUseException e) {
            fail("Thrown PoolInUseException where as it should not have thrown this exception");
        }
        assertEquals("Idle Count ", 0, pool.getNumIdle());
        assertEquals("Active Count ", 0, pool.getNumActive());
    }

    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.destroy(Object)'
     */
    public void testDestroyObject() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        String date = (String) pool.acquire();
        assertEquals("Idle Count ", 3, pool.getNumIdle());
        assertEquals("Active Count ", 1, pool.getNumActive());
        pool.destroy(date);
        assertEquals("Idle Count ", 3, pool.getNumIdle());
        assertEquals("Active Count ", 0, pool.getNumActive());
    }

    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.getIdleObjects()'
     */
    public void testGetIdleObjects() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        pool.acquire();
        assertEquals("Idle Count ", 3, pool.getNumIdle());
        assertEquals("Active Count ", 1, pool.getNumActive());
        Iterator<CObjectWrapper> iter = pool.getIdleObjects();
        assertEquals("Idle Object 1 test", date2, ((CObjectWrapper) iter.next()).getUnderLyingObject());
        assertEquals("Idle Object 2 test", date3, ((CObjectWrapper) iter.next()).getUnderLyingObject());
        assertEquals("Idle Object 3 test", date4, ((CObjectWrapper) iter.next()).getUnderLyingObject());
    }

    /*
     * Test method for 'jdbc.pool.CFIFOObjectPool.add(Object)'
     */
    public void testAdd() {
        IPool pool = new CFIFOObjectPool();
        String date1 = new String("2006, 1, 20");
        String date2 = new String("2005, 1, 20");
        String date3 = new String("2007, 1, 20");
        String date4 = new String("2004, 1, 20");
        pool.add(date1);
        pool.add(date2);
        pool.add(date3);
        pool.add(date4);
        assertEquals("Idle Count ", 4, pool.getNumIdle());
        assertEquals("Active Count ", 0, pool.getNumActive());
        pool.add(date4); //As the same object is added again it will not be added by the pool.
        assertEquals("Idle Count ", 4, pool.getNumIdle());
        assertEquals("Active Count ", 0, pool.getNumActive());
    }

}
