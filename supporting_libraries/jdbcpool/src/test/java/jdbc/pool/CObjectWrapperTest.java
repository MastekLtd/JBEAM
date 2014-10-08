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
 * $Revision: 1 $
 *
 * $Header: /Utilities/JDBCPool/junit-source/jdbc/pool/CObjectWrapperTest.java 1     3/17/08 3:17p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/junit-source/jdbc/pool/CObjectWrapperTest.java $
 * 
 * 1     3/17/08 3:17p Kedarr
 * Initial version
 * 
*/
package jdbc.pool;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Test for {@link CObjectWrapper}
 *
 * Tests for equality, hashcode and underlying objects.
 *
 * @version $Revision: 1 $
 * @author kedarr
 *
 */
public class CObjectWrapperTest extends TestCase {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final static String REVISION = "$Revision:: 1                 $";
    
    /**
     * Test method for {@link jdbc.pool.CObjectWrapper#hashCode()}.
     */
    public void testHashCode() {
        Date date = new Date(System.currentTimeMillis());
        CObjectWrapper objWrapper = new CObjectWrapper(date);
        assertEquals("HashCode Check", date.hashCode(), objWrapper.hashCode());
    }

    /**
     * Test method for {@link jdbc.pool.CObjectWrapper#getUnderLyingObject()}.
     */
    public void testGetUnderLyingObject() {
        Date date1 = new Date(System.currentTimeMillis());
        CObjectWrapper objWrapper = new CObjectWrapper(date1);
        Object obj = objWrapper.getUnderLyingObject();
        assertTrue("Equality Check for instanceof", (obj instanceof Date));
        Date date2 = (Date) obj;
        assertEquals("Equality Check ", date1, date2);
        assertEquals("HashCode Check ", date1.hashCode(), date2.hashCode());
    }

    /**
     * Test method for {@link jdbc.pool.CObjectWrapper#toString()}.
     */
    public void testToString() {
        Date date1 = new Date(System.currentTimeMillis());
        CObjectWrapper objWrapper = new CObjectWrapper(date1);
        assertEquals("Equality Check toString()", date1.toString(), objWrapper.getUnderLyingObject().toString());
    }

    /**
     * Test method for {@link jdbc.pool.CObjectWrapper#equals(java.lang.Object)}.
     */
    public void testEqualsObject() {
        Date date1 = new Date(System.currentTimeMillis());
        CObjectWrapper objWrapper = new CObjectWrapper(date1);
        Object obj = objWrapper.getUnderLyingObject();
        assertTrue("Equality Check for instanceof", (obj instanceof Date));
        Date date2 = (Date) obj;
        assertEquals("Equality Check ", date1, date2);
    }

}
