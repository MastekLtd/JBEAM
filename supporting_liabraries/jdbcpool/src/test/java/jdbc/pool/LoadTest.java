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
 * $Revision:  $
 *
 * $Header:  $
 *
 * $Log:     $
 * 
 */
package jdbc.pool;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;

/**
 * Add a one liner description of the class with a full stop at the end.
 *
 * Add a detailed multi-line description of the class stating its objectives and
 * high level usage of the class.
 *
 * @author STG
 * @since  
 */
public class LoadTest {

    private int iThreads_;
    
    private int iTimeInMinutes_;

    private CConnectionPoolManager poolManager_;

    private volatile int iThreadCounter_;

    private String strPoolName_;

    /**
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ConfigurationException 
     * 
     */
    public LoadTest(int iNumberOfThreads, int iTimeInMinutes, String strPoolName) throws ConfigurationException, ParseException, IOException, SQLException, ClassNotFoundException {
        iThreads_ = iNumberOfThreads;
        iTimeInMinutes_ = iTimeInMinutes;
        strPoolName_ = strPoolName;
        createJDBCPoolInstance();
    }

    /**
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ConfigurationException 
     * 
     */
    private void createJDBCPoolInstance() throws ConfigurationException, ParseException, IOException, SQLException, ClassNotFoundException {
        poolManager_ = CConnectionPoolManager.getInstance(System.getProperty("log4jfile"), new File(System.getProperty("poolconfigfile")));
    }

    /**
     * @param args
     * @throws ClassNotFoundException 
     * @throws SQLException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ConfigurationException 
     */
    public static void main(String[] args) throws ConfigurationException, ParseException, IOException, SQLException, ClassNotFoundException {
        LoadTest test = new LoadTest(15, 10, "MYSQL");
        test.startLoadTest();
    }

    /**
     * 
     */
    private void startLoadTest() {
    	try {
			poolManager_.getConnection("TMP");
			TimeUnit.MINUTES.sleep(50);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			poolManager_.destroy(true);
		}
//        long lStartTime = System.currentTimeMillis();
//        iThreadCounter_ = 0;
//        while ((System.currentTimeMillis() - lStartTime) <(iTimeInMinutes_ * 60 * 60 * 1000)) {
//            if (iThreadCounter_ < iThreads_) {
//                Thread th = new Thread(new JDBCPoolT(iThreadCounter_));
//                th.start();
//                iThreadCounter_++;
//            }
//        }
    }
    
    class JDBCPoolT implements Runnable {

        private int iThread_;
        public JDBCPoolT(int iThread) {
            iThread_ = iThread;
        }
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run() {
            System.out.println("Thread " + iThread_);
            Connection con;
            try {
                con = poolManager_.getConnection(strPoolName_);
                con.close();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            iThreadCounter_--;
        }
    }

}
