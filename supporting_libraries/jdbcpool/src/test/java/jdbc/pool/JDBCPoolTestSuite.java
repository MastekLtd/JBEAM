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
 * $Header: /Utilities/JDBCPool/junit_source/jdbc/pool/JDBCPoolTestSuite.java 2     4/06/06 6:24p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/junit_source/jdbc/pool/JDBCPoolTestSuite.java $
 * 
 * 2     4/06/06 6:24p Kedarr
 * Updated javadoc.
 * 
 * 1     4/06/06 5:23p Kedarr
 * Test Suite.
 * 
*/
package jdbc.pool;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test Suite.
 *
 * Combines all the test cases into a test suite for package <i>jdbc.pool</i>.
 *
 * @version $Revision: 2 $
 * @author kedarr
 *
 */
public class JDBCPoolTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for jdbc.pool");
        //$JUnit-BEGIN$
        suite.addTestSuite(StackObjectPoolTest.class);
        suite.addTestSuite(FIFOObjectPoolTest.class);
        suite.addTestSuite(JDBCPoolMySQLTest.class);
        suite.addTestSuite(PBEEncryptionRoutineTest.class);
        //$JUnit-END$
        return suite;
    }

}
