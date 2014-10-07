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
 * $Revision: 15 $
 *
 * $Header: /Utilities/JDBCPool/junit-source/jdbc/pool/JDBCPoolTestCase.java 15    1/29/09 10:00p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/junit-source/jdbc/pool/JDBCPoolTestCase.java $
 * 
 * 15    1/29/09 10:00p Kedarr
 * Changed test cases to test the functionality of force shutdown of the
 * pool and to apply attributes during runtime.
 * 
 * 14    1/26/09 5:48p Kedarr
 * TestCases changed for the implementation of force shutdown of the pool.
 * 
 * 13    3/17/08 4:16p Kedarr
 * Initial version
 * 
 * 12    3/17/08 4:15p Kedarr
 * Renamed JDBCPoolT.java to JDBCPoolTestCase.java
 * 
 * 11    3/28/07 8:55a Kedarr
 * Renamed JDBCPoolTestCase.java to JDBCPoolT.java
 * 
 * 10    3/20/07 5:08p Kedarr
 * New test cases added for resultset leak count as well as for
 * statistical data collection.
 * 
 * 9     3/14/07 10:55a Kedarr
 * New test case added for testing leaked result set
 * 
 * 8     5/04/06 11:19a Kedarr
 * Test case added for Statement Caching...
 * 
 * 7     4/06/06 5:26p Kedarr
 * Changes made to add the FIFO and LIFO algorithm test cases.
 * 
 * 6     3/06/06 11:25a Kedarr
 * V14.00
 * 
 * 5     2/27/06 10:31a Kedarr
 * Added new method for testing Bad Connections. Also, added a fail clause
 * whereever the instanceof is being checked for ConnectionWrapper.
 * 
 * 4     2/24/06 4:28p Kedarr
 * Added new method for testing statement leaks. Added messages in assert
 * methods.
 * 
 * 3     2/23/06 11:28a Kedarr
 * Added Javadoc and also completed almost all the test jdbc.pool the
 * JDBCPool.
 * 
 * 1     2/22/06 3:43p Kedarr
 * 
 */
package jdbc.pool;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import jdbc.tuning.ConnectionWrapper;
import jdbc.tuning.PreparedStatementWrapper;
import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;

/**
 * JDBCPoolT.
 * 
 * All the test cases for various public methods of {@link jdbc.pool.CConnectionPoolManager},
 * {@link jdbc.pool.CPoolAttribute} and the pool statistic are included in this TestCJDBCPoolTestCase * @version $Revision: 15 $
 * @author kedarr
 * 
 */
public class JDBCPoolTestCase extends TestCase {


    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.getInstance()'
     */
    public void testGetInstanceNull() {
        System.out.println("testGetInstanceNull Start");
        try {
            CConnectionPoolManager.getInstance();
            fail("getInstance() was supposed to throw IllegalArgumentException but did not throw.");
        } catch (IllegalArgumentException e) {
            assertTrue("Caught IllegalArgumentException in testGetInstanceNull()", true);
        }
        System.out.println("testGetInstanceNull End");
    }

    /*
     * Invalid Data. Test method for
     * 'jdbc.pool.CConnectionPoolManager.getInstance(String, File)'
     */
    public void testGetInstanceStringFile1() {
        System.out.println("testGetInstanceStringFile1 Start");
        try {
            try {
                CConnectionPoolManager.getInstance();
                System.out.println("Instance is created. WRONG");
            } catch (IllegalArgumentException e) {
                System.out.println("Instance is not created. Alright");
            }
            CConnectionPoolManager manager = CConnectionPoolManager
                    .getInstance(
                            "C:/Documents and Settings/STG/workspace/jdbcpool/config/log4j.properties",
                            new File(
                                    "C:/Documents and Settings/STG/workspace/jdbcpool/config/pool1245645558888.properties"));
            System.out.println("It came here Kedar in trouble" + manager);
            fail("getInstance(invalid data) was supposed to throw Configuration exception but did not.");
        } catch (ConfigurationException e) {
            e.printStackTrace();
            assertTrue("Caught ConfigurationException", true);
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testGetInstanceStringFile1 end");
    }

    /*
     * Proper Data Test Test method for
     * 'jdbc.pool.CConnectionPoolManager.getInstance(String, File)'
     */
    public void testGetInstanceStringFile() {
        System.out.println("testGetInstanceStringFile Start");
        try {
            CConnectionPoolManager manager = null;
            manager = create();
            assertNotNull("check manager for not null", manager);
            assertNotNull("check CConnectionPoolManager.getInstance() for not null", CConnectionPoolManager.getInstance());
            assertTrue("check manager == CConnectionPoolManager.getInstance()", manager.hashCode() == CConnectionPoolManager
                    .getInstance().hashCode());
            manager.destroy(true);
            testGetInstanceNull();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testGetInstanceStringFile End");
    }

    /*
     * Proper Data Test with Null as log4j properties. Test method for
     * 'jdbc.pool.CConnectionPoolManager.getInstance(String, File)'
     */
    public void testGetInstanceStringFile2() {
        System.out.println("testGetInstanceStringFile2 Start");
        try {
            CConnectionPoolManager manager = CConnectionPoolManager
                    .getInstance(
                            null,
                            new File(
                                    "C:/Documents and Settings/STG/workspace/jdbcpool/junit-source/config/pool.properties"));
            assertNotNull("Instance not null", manager);
            assertNotNull("Ascertain CConnectionPoolManager.getInstance()also returns not null", CConnectionPoolManager.getInstance());
            assertTrue("ascertain CConnectionPoolManager.getInstance() == manager", manager.hashCode() == CConnectionPoolManager
                    .getInstance().hashCode());
            manager.destroy(true);
            testGetInstanceNull();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testGetInstanceStringFile2 end");
    }

    /*
     * Tests load on startup as well as statistics. 
     * 'jdbc.pool.CConnectionPoolManager.getPoolStatistics(String)'
     */
    public void testGetPoolStatistics() {
        try {
            CConnectionPoolManager manager = null;
            manager = create();

            try {
                manager.getPoolStatistics("ORACLE");
            } catch (NullPointerException e) {
                assertTrue("Pool attribute load-on-startup is false", true);
            }
            CPoolStatisticsBean bean = manager.getPoolStatistics("MYSQL");
            assertEquals("MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 0, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
//            assertEquals("Connections Closed Count", 0, bean.getNoOfConnectionsClosed());
//            assertEquals("Connections Created Count", 0, bean.getNoOfConnectionsCreated());
//            assertEquals("Connections Locked", 0, bean.getNoOfConnectionsLocked());
//            assertEquals("Connections Requested Count", 0, bean.getNoOfConnectionsRequested());
//            assertEquals("Connections Unlocked Count", 0, bean.getNoOfConnectionsUnlocked());
//            assertEquals("New Connections Created Count", 0, bean.getNoOfNewConnectionsRequested());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }

    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.getAllPoolStatistics()'
     */
    public void testGetAllPoolStatistics() {
        try {
            CConnectionPoolManager manager = null;
            manager = create();
            Connection con = manager.getConnection("ORACLE");
            CPoolStatisticsBean beanOracle = manager
                    .getPoolStatistics("ORACLE");
            assertEquals("pool name", "ORACLE", beanOracle.getPoolName());
            assertEquals("Bad Connections Count", 0, beanOracle.getBadConnectionCount());
            assertEquals("Connections High Count", 1, beanOracle.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, beanOracle.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, beanOracle.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, beanOracle.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, beanOracle.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, beanOracle.getLeakedResultSetCount());

            //
            CPoolStatisticsBean beanMySQL = manager.getPoolStatistics("MYSQL");
            assertEquals("pool name", "MYSQL", beanMySQL.getPoolName());
            assertEquals("Bad Connections Count", 0, beanMySQL.getBadConnectionCount());
            assertEquals("Connections High Count", 0, beanMySQL.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, beanMySQL.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, beanMySQL.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, beanMySQL.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, beanMySQL.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, beanMySQL.getLeakedResultSetCount());
            con.close();
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
    }

    /*
     * Test method for
     * 'jdbc.pool.CConnectionPoolManager.updatePoolAttributes(CPoolAttribute)'
     */
    public void testUpdatePoolAttributesCPoolAttribute() {
        System.out.println("testUpdatePoolAttributesCPoolAttribute Started.");
        try {
            CConnectionPoolManager manager = null;
            manager = create();
            Connection con = manager.getConnection("MYSQL");
            CPoolAttribute attribMySQL = manager.getPoolAttributes("MYSQL");
            int iOriginalInitialPoolSize = attribMySQL.getInitialPoolSize();
            attribMySQL.setPassword("root");
            attribMySQL.setInitialPoolSize(10);
            try {
                manager.updatePoolAttributes(attribMySQL, false);
            } catch (InvalidPoolAttributeException e) {
                e.printStackTrace();
            }
            CPoolAttribute attributes = manager.getPoolAttributes("MYSQL");
            assertFalse("Initial Pool size should not be same as the updated ones", (attributes.getInitialPoolSize() == 10));
            assertTrue("Initial Pool size should be same as the original ones", (attributes.getInitialPoolSize() == iOriginalInitialPoolSize));
            assertTrue("Connection is not closed ", !con.isClosed());
            manager.destroy(true);
            testGetInstanceNull();
            manager = create();
            attribMySQL = manager.getPoolAttributes("MYSQL");
            con = manager.getConnection("MYSQL");
            assertEquals("Initial Size ", 10, attribMySQL.getInitialPoolSize());
            attribMySQL.setPassword("root");
            attribMySQL.setInitialPoolSize(iOriginalInitialPoolSize);
            try {
                manager.updatePoolAttributes(attribMySQL, true);
                attributes = manager.getPoolAttributes("MYSQL");
                assertTrue("Connection is closed ", con.isClosed());
                assertTrue("Initial Pool size should not be same as the updated ones", (attributes.getInitialPoolSize() == iOriginalInitialPoolSize));
            } catch (InvalidPoolAttributeException e) {
                e.printStackTrace();
            }
            manager.destroy(true);

        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }

        try {
            CConnectionPoolManager manager = null;
            manager = create();
            CPoolAttribute attribMySQL = manager.getPoolAttributes("MYSQL");
            attribMySQL.setPoolName("NOTEXISTANT");
            attribMySQL.setPassword("root");
            attribMySQL.setInitialPoolSize(10);
            try {
                manager.updatePoolAttributes(attribMySQL, true); // Raises
                // configuration
                // exception.
                fail("did not throw Configuration Exception");
            } catch (ConfigurationException e) {
                e.printStackTrace();
                assertTrue("Caught ConfigurationException", true);
            } catch (InvalidPoolAttributeException e) {
                e.printStackTrace();
                fail("Invalid Pool Attribute Exception caught");
            }
            manager.destroy(true);
            testGetInstanceNull();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testUpdatePoolAttributesCPoolAttribute end.");
    }

    /*
     * Test method for
     * 'jdbc.pool.CConnectionPoolManager.updatePoolAttributes(CPoolAttribute[])'
     */
    public void testUpdatePoolAttributesCPoolAttributeArray() {
    }

    /*
     * Test method for
     * 'jdbc.pool.CConnectionPoolManager.addNewPool(CPoolAttribute)'
     */
    public void testAddNewPool() {
        long lTimeInMilis = System.currentTimeMillis();
        String strNewPoolName = "NewPoolTC_" + lTimeInMilis;
        try {
            CConnectionPoolManager manager = null;
            manager = create();

            CPoolAttribute attrib = manager.getPoolAttributes("MYSQL");
            attrib.setPoolName(strNewPoolName);
            attrib.setPassword("root");
            try {
                manager.addNewPool(attrib);
            } catch (InvalidPoolAttributeException e) {
                e.printStackTrace();
                fail();
            }
            manager.destroy(true);
            manager = create();
            CPoolAttribute attribNew = manager
                    .getPoolAttributes(strNewPoolName);
            assertTrue("Attribute equality check with new attribute", attrib.equals(attribNew));
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }

        try {
            CConnectionPoolManager manager = null;
            manager = create();
            CPoolAttribute attrib = manager.getPoolAttributes("MYSQL");
            attrib.setPoolName(strNewPoolName);
            attrib.setPassword("root");
            try {
                manager.addNewPool(attrib);
                fail();
            } catch (ConfigurationException e) {
                assertTrue("Caught ConfigurationException", true);
                e.printStackTrace();
            } catch (InvalidPoolAttributeException e) {
                e.printStackTrace();
                fail("Caught InvalidPoolAttributeException");
            }
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
    }

    /*
     * Test method for
     * 'jdbc.pool.CConnectionPoolManager.addNewPools(CPoolAttribute[])'
     */
    public void testAddNewPools() {
    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolmanager.destroy(true)'
     */
    public void testDestroy() {
        System.out.println("testDestroy Start.");
        try {
            CConnectionPoolManager manager = null;
            manager = create();
            manager.destroy(true);
            testGetInstanceNull();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testDestroy end.");
    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.getConnection(String)'
     */
    public synchronized void testGetConnection() {
        System.out.println("testGetConnection Start.");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            CPoolStatisticsBean bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 0, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());

            Connection con = manager.getConnection("MYSQL");
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 2, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            con.close();
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            Connection con1 = manager.getConnection("MYSQL");
            Connection con2 = manager.getConnection("MYSQL");
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 2, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 1, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 2, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            con1.close();
            con2.close();
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 2, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());

            manager.destroy(true);
            testGetInstanceNull();

        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            if (manager != null) manager.destroy(true);
            testGetInstanceNull();
        }
        System.out.println("testGetConnection end.");
    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.emptyPool(String)'
     */
    public void testEmptyPool() {
        System.out.println("testEmptyPool Start");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            CPoolStatisticsBean bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 0, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 3, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            try {
                manager.emptyPool("MYSQL", true);
            } catch (SQLException e) {
                fail("Caught SQLException");
            }
            try {
                bean = manager.getPoolStatistics("MYSQL");
                fail("Did not throw NullPointerException even though the pool was made empty.");
            } catch (NullPointerException e) {
                assertTrue("Caught NullPointerException", true);
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }

        try {
            manager = create();
            Connection con = manager.getConnection("MYSQL");
            CPoolAttribute attrib = manager.getPoolAttributes("MYSQL");
            try {
                manager.emptyPool("MYSQL", true);
            } catch (SQLException e) {
                assertTrue("Caught SQLException", true);
                con.close();
            }
            try {
                con = manager.getConnection("MYSQL");
                fail("Should have thrown an IOException but did not throw it.");
                con.close();
            } catch (IOException e) {
                assertTrue("Caught IOException", true);
            }
            try {
                Thread.sleep((attrib.getShrinkPoolInterval() * 2) * 1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {
                manager.emptyPool("MYSQL", true);
            } catch (SQLException e) {
                fail("Caught SQLException");
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
            testGetInstanceNull();
        }
        System.out.println("testEmptyPool end");
    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.emptyAllPools()'
     */
    public void testEmptyAllPools() {
        System.out.println("testEmptyAllPools Start");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            Connection con = manager.getConnection("ORACLE"); // MYSQL is load
            // on startup.
            con.close();
            manager.emptyAllPools(false);
            try {
                manager.getPoolStatistics("ORACLE");
                fail("Should have thrown NullPointerException.");
            } catch (NullPointerException e) {
                assertTrue("Caught NullPointerException", true);
            }
            try {
                manager.getPoolStatistics("MYSQL");
                fail();
            } catch (NullPointerException e) {
                assertTrue("Caught NullPointerException", true);
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
            testGetInstanceNull();
        }

        System.out.println("testEmptyAllPools end.");
    }

    /**
     * Creates the CConnectionPoolManager instance.
     * 
     * @return CConnectionPoolManager
     * @throws ConfigurationException
     * @throws ParseException
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private CConnectionPoolManager create() throws ConfigurationException,
            ParseException, IOException, SQLException, ClassNotFoundException {
//        if (System.getProperty("fileType").equals("XML")) {
//            return CConnectionPoolManager
//                    .getInstance(
//                            "C:/Documents and Settings/kedarr/workspace/jdbcpool/junit-source/config/log4j.properties",
//                            new File(
//                                    "C:/Documents and Settings/kedarr/workspace/jdbcpool/junit-source/config/poolconfig.xml"));
//        } else {
            return CConnectionPoolManager
                    .getInstance(
                            "C:/Documents and Settings/STG/workspace/jdbcpool/junit-source/config/log4j.properties",
                            new File(
                                    "C:/Documents and Settings/STG/workspace/jdbcpool/junit-source/config/pool.properties"));
//        }
    }

    /**
     * Test if JDBC connection is getting closed after crossing the maximum
     * usage per JDBC connection.
     * 
     */
    public void testMaxUsagePerJDBCConnection() {
        System.out.println("testMaxUsagePerJDBCConnection start.");
        try {
            CConnectionPoolManager manager = create();
            Connection realCon = null;
            Connection con = manager.getConnection("ORACLE");
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) con;
                realCon = wrapper.realConnection();
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            con.close();
            assertFalse("Connection must be active", realCon.isClosed());
            // 2
            con = manager.getConnection("ORACLE");
            con.close();
            assertFalse("Connection must be active", realCon.isClosed());
            // 3
            con = manager.getConnection("ORACLE");
            con.close();
            assertFalse("Connection must be active", realCon.isClosed());
            // 4
            con = manager.getConnection("ORACLE");
            con.close();
            assertTrue("Connection must be closed", realCon.isClosed());
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testMaxUsagePerJDBCConnection end.");
    }

    /**
     * Validates whether the connection received from the pool is as per the
     * JDBC driver specified in the configuration file.
     * 
     */
    public void testValidateConnectionForJDBCDriver() {
        System.out.println("validateConnectionForJDBCDriver Start");
        try {
            CConnectionPoolManager manager = create();
            Connection con = manager.getConnection("ORACLE");
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) con;
                assertTrue("real connection from Oracle pool is of Oracle", wrapper.realConnection() instanceof oracle.jdbc.OracleConnection);
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            con.close();
            con = manager.getConnection("MYSQL");
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) con;
                assertTrue("real connection from MYSQL pool is of MySQL", wrapper.realConnection() instanceof com.mysql.jdbc.Connection);
            }  else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            con.close();
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("validateConnectionForJDBCDriver end");
    }

    /**
     * Tests the capacity increament of the pool.
     * 
     */
    public void testCapacityIncreament() {
        System.out.println("testCapacityIncreament Start");
        try {
            CConnectionPoolManager manager = create();
            // capacity increament Oracle 1
            Connection conOracle1 = manager.getConnection("ORACLE");
            CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());

            Connection conOracle2 = manager.getConnection("ORACLE");
            bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 2, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 2, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            conOracle2.close();
            conOracle1.close();
            // capacity increament MySQL 2
            Connection conMysql1 = manager.getConnection("MYSQL");
            Connection conMysql2 = manager.getConnection("MYSQL");
            Connection conMysql3 = manager.getConnection("MYSQL");
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 3, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 3, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            Connection conMysql4 = manager.getConnection("MYSQL");
            bean = manager.getPoolStatistics("MYSQL");
            assertEquals("Pool Name", "MYSQL", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 4, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 1, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 4, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            conMysql1.close();
            conMysql2.close();
            conMysql3.close();
            conMysql4.close();
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testCapacityIncreament end.");
    }

    /**
     * Tests the finalizer call.
     * 
     * Check whether the connection returns back to the pool.
     */
    public void testFinalizerCall() {
        System.out.println("testFinalizerCall Start.");
        try {
            CConnectionPoolManager manager = create();
            try {
                manager.getConnection("ORACLE");
            } catch (Exception e) {
            }
            CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            System.gc();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 1, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 1, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testFinalizerCall end.");
    }

    /**
     * Tests Inactive time out closing of the JDBC Connection.
     * 
     * Check whether the connection returns back to the pool.
     */
    public void testInactiveTimeout() {
        System.out.println("testInactiveTimeout Start.");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            Connection conOra1 = manager.getConnection("ORACLE");
            Connection realCon = null;
            Connection conOra2 = manager.getConnection("ORACLE");
            if (conOra2 instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) conOra2;
                realCon = wrapper.realConnection();
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            conOra2.close();
            try {
                // 4 = 1 + 3 + (1) As the maximum time in which the con will be
                // closed is 4 have an additional 1 minute extra
                Thread.sleep(5 * 60 * 1000); // Shrink Pool Interval 1 minute + Inactive timeout 3 mins.
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            assertTrue("Real Connection is closed", realCon.isClosed());
            conOra1.close();
            CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            System.out.println("********************************************************");
            ArrayList al = manager.getPoolStatisticsHistory("ORACLE");
            assertTrue("Statistics History Count", 5 >= al.size());
            for (Iterator iter = al.iterator(); iter.hasNext();) {
                CPoolStatisticsBean element = (CPoolStatisticsBean) iter.next();
                System.out.println(element.toString());
            }
            System.out.println("********************************************************");
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }
        System.out.println("testInactiveTimeout end.");
    }

    /**
     * Tests the In Use Wait Time.
     * 
     * Check whether the connection returns back to the pool.
     */
    public void testInUseWaitTime() {
        System.out.println("testInUseWaitTime Start.");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            Connection conOra1 = manager.getConnection("ORACLE");
            Connection conOra2 = manager.getConnection("ORACLE");
            Connection conOra3 = manager.getConnection("ORACLE");
            Connection conOra4 = manager.getConnection("ORACLE");
            Connection conOra5 = manager.getConnection("ORACLE");
            long time = 0L;
            try {
                time = System.currentTimeMillis();
                manager.getConnection("ORACLE"); // should result in
                // SQLException
                fail();
            } catch (SQLException e) {
                assertEquals(
                        "Unable to increament pool. Max Capacity reached.", e
                                .getMessage());
                assertTrue("Time is greater than equal to 7000 millis", System.currentTimeMillis() - time >= 6900);
            } finally {
                conOra1.close();
                conOra2.close();
                conOra3.close();
                conOra4.close();
                conOra5.close();
            }
            conOra1 = manager.getConnection("ORACLE");
            conOra2 = manager.getConnection("ORACLE");
            conOra3 = manager.getConnection("ORACLE");
            conOra4 = manager.getConnection("ORACLE");
            conOra5 = manager.getConnection("ORACLE");
            (new Thread() {
                public void run() {
                    System.out.println("Internal Thread for InUseWaitTime Start.");
                    Connection con = null;
                    try {
                        long time = System.currentTimeMillis();
                        con = CConnectionPoolManager.getInstance().getConnection("ORACLE");
                        assertTrue("Time is less than 7000 millis", System.currentTimeMillis() - time < 7000);
                        assertNotNull("Connection is not null", con);
                    } catch (SQLException e) {
                        fail("Caught SQLException");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        fail("Caught ClassNotFoundException");
                    } catch (IOException e) {
                        e.printStackTrace();
                        fail("Caught IOException");
                    } finally {
                        if (con != null){
                            try {
                                con.close();
                            } catch (SQLException e) {
                            }
                        }
                    }
                    System.out.println("Internal Thread for InUseWaitTime End.");
                }
            }).start();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            } finally {
                conOra1.close();
                conOra2.close();
                conOra3.close();
                conOra4.close();
                conOra5.close();
            }
            CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
//            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
//            assertEquals("Connections High Count", 5, bean.getConnectionsHighCount());
//            assertEquals("Current Free Connections", 5, bean.getCurrentFreeConnectionCount());
//            assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
//            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
//            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
//            assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
//            assertEquals("Current Waiters", 1, bean.getCurrentWaitersCount());
            assertEquals("Waiters High", 1, bean.getWaitersHighCount());
            assertEquals("Wait Time High", 7000, bean.getWaitTimeHighInMillis());
            System.out.println(bean.getWaitTimeTotalInMillis());
            assertTrue("Total Time High", 10000 >= bean.getWaitTimeTotalInMillis());
            
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }
        System.out.println("testInUseWaitTime End.");
    }

    /**
     * Tests Inactive Timeout for -1 or 0 value.
     * This pool has different set of parameters as compared to MYSQL pool and this forces the behaviour changes
     * in the pool. The connections will not be pooled and therefore the connections will be closed upon release.
     */
    public void testTIT() {
        System.out.println("testTIT Start.");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            CPoolAttribute attrib = manager.getPoolAttributes("TIT");
            assertEquals("Driver", "com.mysql.jdbc.Driver", attrib.getDriver());
            assertEquals("Vendor", "MySQL", attrib.getVendor());
            assertEquals("URL", "jdbc:mysql://localhost/newpre", attrib.getURL());
            assertEquals("User", "root", attrib.getUser());
            assertEquals("Password", "=?UTF-8?B?cm9vdA==?=", attrib.getPassword());
            assertEquals("Initial Pool Size", 3, attrib.getInitialPoolSize());
            assertEquals("Capacity Increament", 2, attrib.getCapacityIncreament());
            assertEquals("Maximum Capacity", 50, attrib.getMaximumCapacity());
            assertEquals("Connection Idle Timeout", -1, attrib.getConnectionIdleTimeout());
            assertEquals("Shrink Pool Interval", 1, attrib.getShrinkPoolInterval());
            assertEquals("Critical Operation Timelimit", 10000, attrib.getCriticalOperationTimeLimit());
            assertEquals("In Use wait time", 10, attrib.getInUseWaitTime());
            assertFalse("load-on-startup", attrib.isLoadOnStartup());
            
            Connection conOra1 = manager.getConnection("TIT");
            Connection realCon = null;
            if (conOra1 instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) conOra1;
                realCon = wrapper.realConnection();
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            conOra1.close();
            assertTrue("Connection must get closed as the inactive-time-out <= 0", realCon.isClosed());
            attrib = manager.getPoolAttributes("TIT");
            assertEquals("Driver", "com.mysql.jdbc.Driver", attrib.getDriver());
            assertEquals("Vendor", "MySQL", attrib.getVendor());
            assertEquals("URL", "jdbc:mysql://localhost/newpre", attrib.getURL());
            assertEquals("User", "root", attrib.getUser());
            assertEquals("Password", "=?UTF-8?B?cm9vdA==?=", attrib.getPassword());
            assertEquals("Initial Pool Size", 0, attrib.getInitialPoolSize());
            assertEquals("Capacity Increament", 1, attrib.getCapacityIncreament());
            assertEquals("Maximum Capacity", 50, attrib.getMaximumCapacity());
            assertEquals("Connection Idle Timeout", -1, attrib.getConnectionIdleTimeout());
            assertEquals("Shrink Pool Interval", -1, attrib.getShrinkPoolInterval());
            assertEquals("Critical Operation Timelimit", 10000, attrib.getCriticalOperationTimeLimit());
            assertEquals("In Use wait time", 10, attrib.getInUseWaitTime());
            assertFalse("load-on-startup", attrib.isLoadOnStartup());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }
        System.out.println("testTIT end.");
    }

    /**
     * Tests the set methods of CPoolAttribute by creating a new CPoolAttribute.
     *
     */
    public void testPoolAttributesSetMethods(){
        CPoolAttribute attributes = new CPoolAttribute();
        attributes.setCapacityIncreament(6);
        assertEquals("Capacity Increament", 6, attributes.getCapacityIncreament());
        attributes.setConnectionIdleTimeout(4);
        assertEquals("Connection Idle Timeout", 4, attributes.getConnectionIdleTimeout());
        attributes.setDriver("com.mysql.jdbc.Driver");
        assertEquals("Driver", "com.mysql.jdbc.Driver", attributes.getDriver());
        attributes.setInitialPoolSize(10);
        assertEquals("Initial Pool Size", 10, attributes.getInitialPoolSize());
        attributes.setMaximumCapacity(20);
        assertEquals("Maximum Capacity", 20, attributes.getMaximumCapacity());
        attributes.setPassword(null);
        assertNull("Password is null", attributes.getPassword());
        attributes.setPassword("kedar");
        assertNotSame("Password", "kedar", attributes.getPassword());
        attributes.setPoolName("kedar");
        assertEquals("Pool Name", "kedar", attributes.getPoolName());
        attributes.setShrinkPoolInterval(5);
        assertEquals("Shrink Pool Interval", 5, attributes.getShrinkPoolInterval());
        attributes.setURL("url");
        assertEquals("URL", "url", attributes.getURL());
        attributes.setUser("user");
        assertEquals("USER", "user", attributes.getUser());
        attributes.setVendor("oracle");
        assertEquals("VENDOR", "oracle", attributes.getVendor());
        attributes.setCriticalOperationTimeLimit(100);
        assertEquals("Critical Operation Time Limit", 100, attributes.getCriticalOperationTimeLimit());
        attributes.setInUseWaitTime(100);
        assertEquals("In Use Wait Time", 100, attributes.getInUseWaitTime());
        attributes.setLoadOnStartup(true);
        assertEquals("Load On Startup", true, attributes.isLoadOnStartup());
        attributes.setMaximumCapacity(100);
        assertEquals("Maximum Capacity", 100, attributes.getMaximumCapacity());
    }


    /**
     * Tests the get methods of CPoolAttribute for pools ORACLE and MYSQL.
     *
     */
    public void testCPoolAttributeGET() {
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            CPoolAttribute attribOraclePool = CConnectionPoolManager.getInstance().getPoolAttributes("ORACLE");
            CPoolAttribute attribMySqlPool = CConnectionPoolManager.getInstance().getPoolAttributes("MYSQL");
            assertEquals("Capacity Increament", 1, attribOraclePool.getCapacityIncreament());
            assertEquals("Capacity Increament", 2, attribMySqlPool.getCapacityIncreament());
            assertEquals("Connection Idle Timeout", 3, attribOraclePool.getConnectionIdleTimeout());
            assertEquals("Connection Idle Timeout", 5, attribMySqlPool.getConnectionIdleTimeout());
            assertEquals("Driver", "oracle.jdbc.driver.OracleDriver", attribOraclePool.getDriver());
            assertEquals("Driver", "com.mysql.jdbc.Driver", attribMySqlPool.getDriver());
            assertEquals("Initial Pool Size", 1, attribOraclePool.getInitialPoolSize());
            assertEquals("Initial Pool Size", 3, attribMySqlPool.getInitialPoolSize());
            assertEquals("Maximum Capacity",5, attribOraclePool.getMaximumCapacity());
            assertEquals("Maximum Capacity",50, attribMySqlPool.getMaximumCapacity());
            assertNotNull("Password not null", attribOraclePool.getPassword());
            assertNotNull("Password not null", attribMySqlPool.getPassword());
            assertEquals("Pool Name", "ORACLE", attribOraclePool.getPoolName());
            assertEquals("Pool Name", "MYSQL", attribMySqlPool.getPoolName());
            assertEquals("Shrink Pool Interval", 1, attribOraclePool.getShrinkPoolInterval());
            assertEquals("Shrink Pool Interval", 1, attribMySqlPool.getShrinkPoolInterval());
            assertEquals("URL", "jdbc:oracle:thin:@192.100.192.114:1521:SUN2DB1", attribOraclePool.getURL());
            assertEquals("URL", "jdbc:mysql://localhost/newpre", attribMySqlPool.getURL());
            assertEquals("USER", "rendev", attribOraclePool.getUser());
            assertEquals("USER", "root", attribMySqlPool.getUser());
            assertEquals("VENDOR", "ORACLE", attribOraclePool.getVendor());
            assertEquals("VENDOR", "MySQL", attribMySqlPool.getVendor());
            assertEquals("Critical Operation Time Limit", 1000, attribOraclePool.getCriticalOperationTimeLimit());
            assertEquals("Critical Operation Time Limit", 10000, attribMySqlPool.getCriticalOperationTimeLimit());
            assertEquals("In Use Wait Time", 7, attribOraclePool.getInUseWaitTime());
            assertEquals("In Use Wait Time", 10, attribMySqlPool.getInUseWaitTime());
            assertEquals("Load On Startup", false, attribOraclePool.isLoadOnStartup());
            assertEquals("Load On Startup", true, attribMySqlPool.isLoadOnStartup());
            assertEquals("Maximum Capacity", 4, attribOraclePool.getMaxUsagePerJDBCConnection());
            assertEquals("Maximum Capacity", -1, attribMySqlPool.getMaxUsagePerJDBCConnection());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }
    }
    
    /**
     * Tests the statement leaks.
     *
     */
    public void testStatementLeak() {
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            Connection con = null;
            try {
                con = manager.getConnection("ORACLE");
                CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
                assertEquals("Pool Name", "ORACLE", bean.getPoolName());
                assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
                assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
                assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
                assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
                assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
                assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
                assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
                Statement st = con.createStatement();
                st.executeQuery("SELECT count(*) FROM process_request");
                con.close();
                bean = manager.getPoolStatistics("ORACLE");
                assertEquals("Pool Name", "ORACLE", bean.getPoolName());
                assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
                assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
                assertEquals("Current Free Connections", 1, bean.getCurrentFreeConnectionCount());
                assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
                assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
                assertEquals("Leaked Statement Count", 1, bean.getLeakedStatementCount());
                assertEquals("Leaked ResultSet Count", 1, bean.getLeakedResultSetCount());
            } catch (SQLException e) {
                fail("Caught SQLException");
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }        
    }
    
    /**
     * Tests for bad connection.
     *
     */
    public void testBadConnection() {
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            try {
                Connection con = manager.getConnection("ORACLE");
                Connection realCon = null; 
                CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
                assertEquals("Pool Name", "ORACLE", bean.getPoolName());
                assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
                assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
                assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
                assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
                assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
                assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
                assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
                if (con instanceof ConnectionWrapper) {
                    ConnectionWrapper wrapper = (ConnectionWrapper) con;
                    realCon = wrapper.realConnection();
                } else {
                    fail("Connection returned is not an instance of ConnectionWrapper");
                }
                
                con.close();
                realCon.close();
                try {
                    Thread.sleep(120000); //Wait till the self check runs. Ideally it should run in a minute.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bean = manager.getPoolStatistics("ORACLE");
                assertEquals("Pool Name", "ORACLE", bean.getPoolName());
                assertEquals("Bad Connections Count", 1, bean.getBadConnectionCount());
                assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
                assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
                assertEquals("Current Used Connection count", 0, bean.getCurrentUsedConnectionCount());
                assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
                assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
                assertEquals("Leaked ResultSet Count", 0, bean.getLeakedResultSetCount());
            } catch (SQLException e) {
                fail("Caught SQLException");
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
        }        
    }

    /**
     * Test case for FIFO Algorithm with Maximum JDBC Usage parameter.
     */
    public void testFIFOAlgorithm() {
        System.out.println("testFIFOAlgorithm with Maximum Usage Counter Start.");
        try {
            CConnectionPoolManager manager = create();
            Connection realCon = null;
            Connection con = manager.getConnection("FIFO"); //It has 3 iniital connections.
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) con;
                realCon = wrapper.realConnection();
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            con.close();
            for (int i = 2; i <= (3*4)-3; i++) {     // 3 (no. of con) * 4 (max jdbc usage) - 2 (to bring the original on top.)
                con = manager.getConnection("FIFO");
                con.close();
                assertFalse("Connection must be active #" + i, realCon.isClosed());
            }
            con = manager.getConnection("FIFO");
            con.close();
            assertTrue("Connection must be active", realCon.isClosed());
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testFIFOAlgorithm with Maximum Usage Counter End.");
    }
    
    /**
     * Test case for LIFO Algorithm with Maximum JDBC Usage parameter.
     */
    public void testLIFOAlgorithm() {
        System.out.println("testLIFOAlgorithm with Maximum Usage Counter Start.");
        try {
            CConnectionPoolManager manager = create();
            Connection realCon = null;
            Connection con = manager.getConnection("LIFO"); //It has 3 iniital connections.
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper wrapper = (ConnectionWrapper) con;
                realCon = wrapper.realConnection();
            } else {
                fail("Connection returned is not an instance of ConnectionWrapper");
            }
            con.close();
            con = manager.getConnection("LIFO");
            con.close();
            assertFalse("Connection must be active", realCon.isClosed());
            con = manager.getConnection("LIFO");
            con.close();
            assertFalse("Connection must be active", realCon.isClosed());
            con = manager.getConnection("LIFO");
            con.close();
            assertTrue("Connection must be active", realCon.isClosed());
            manager.destroy(true);
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        }
        System.out.println("testLIFOAlgorithm with Maximum Usage Counter End.");
    }
    
    /**
     * Test case for Statement Caching.
     */
    public void testStatementCaching() {
        System.out.println("testStatementCaching Start.");
        CConnectionPoolManager manager = null;
        Connection con = null; //It has 3 iniital connections.
        PreparedStatement st;
        PreparedStatement st1;
        try {
            manager = create();
            con = manager.getConnection("ORACLE");
            st = con.prepareStatement("SELECT count(*) FROM DUAL");
            PreparedStatement realPsm = null;
            if (st instanceof PreparedStatementWrapper) {
                PreparedStatementWrapper wrapper = (PreparedStatementWrapper) st;
                realPsm = (PreparedStatement) wrapper.realStatement();
            } else {
                fail("Instanceof failed");
            }
            st.execute();
            st1 = con.prepareStatement("SELECT count(*) FROM DUAL");
            if (st1 instanceof PreparedStatementWrapper) {
                PreparedStatementWrapper wrapper = (PreparedStatementWrapper) st1;
                realPsm = (PreparedStatement) wrapper.realStatement();
                assertEquals("Statement Cache", realPsm, (PreparedStatement) wrapper.realStatement());
            } else {
                con.close();
                fail("Instanceof failed");
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (manager != null) {
                manager.destroy(true);
            }
        }
        System.out.println("testStatementCaching End.");
    }

    /**
     * Test case for ResultSet leak.
     */
    public void testResultSetLeak() {
        System.out.println("testResultSetLeak Start.");
        CConnectionPoolManager manager = null;
        Connection con = null; //It has 3 iniital connections.
        PreparedStatement st;
        try {
            manager = create();
            con = manager.getConnection("ORACLE");
            st = con.prepareStatement("SELECT 'A' FROM DUAL UNION SELECT 'B' FROM DUAL");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                //do nothing
            }
            st.close();
            CPoolStatisticsBean bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 1, bean.getLeakedResultSetCount());
            st = con.prepareStatement("SELECT 'A' FROM DUAL UNION SELECT 'B' FROM DUAL");
            rs = st.executeQuery();
            while (rs.next()) {
                //do nothing
            }
            st.close();
            bean = manager.getPoolStatistics("ORACLE");
            assertEquals("Pool Name", "ORACLE", bean.getPoolName());
            assertEquals("Bad Connections Count", 0, bean.getBadConnectionCount());
            assertEquals("Connections High Count", 1, bean.getConnectionsHighCount());
            assertEquals("Current Free Connections", 0, bean.getCurrentFreeConnectionCount());
            assertEquals("Current Used Connection count", 1, bean.getCurrentUsedConnectionCount());
            assertEquals("Leaked Connection Count", 0, bean.getLeakedConnectionCount());
            assertEquals("Leaked Statement Count", 0, bean.getLeakedStatementCount());
            assertEquals("Leaked ResultSet Count", 2, bean.getLeakedResultSetCount());
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (manager != null) {
                manager.destroy(true);
            }
        }
        System.out.println("testStatementCaching End.");
    }

    /*
     * Test method for 'jdbc.pool.CConnectionPoolManager.emptyAllPools()'
     */
    public void testForceShutdown() {
        System.out.println("testForceShutdown Start");
        CConnectionPoolManager manager = null;
        try {
            manager = create();
            Connection con = manager.getConnection("ORACLE"); // MYSQL is load
            // on startup.
            manager.emptyAllPools(true);
            if (con instanceof ConnectionWrapper) {
                ConnectionWrapper realCon = (ConnectionWrapper) con;
                assertTrue("Is realConnection closed?", realCon.realConnection().isClosed());
            }
            assertTrue("Passed. Even if the pool was in use it did not throw any exception.", true);
            try {
                manager.getPoolStatistics("ORACLE");
                fail("Should have thrown NullPointerException.");
            } catch (NullPointerException e) {
                assertTrue("Caught NullPointerException", true);
            }
            try {
                manager.getPoolStatistics("ORACLE");
                fail();
            } catch (NullPointerException e) {
                assertTrue("Caught NullPointerException", true);
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
            fail("Caught ConfigurationException");
        } catch (ParseException e) {
            e.printStackTrace();
            fail("Caught ParseException");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Caught IOException");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Caught SQLException");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail("Caught ClassNotFoundException");
        } finally {
            manager.destroy(true);
            testGetInstanceNull();
        }
        
        System.out.println("testForceShutdown end.");
    }
    
}
