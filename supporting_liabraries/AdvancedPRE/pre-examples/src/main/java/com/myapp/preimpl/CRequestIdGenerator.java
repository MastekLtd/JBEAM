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
 * $Revision: 2382 $
 *
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/CRequestIdGenerator.java 3     3/11/09 6:11p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/CRequestIdGenerator.java $
 * 
 * 3     3/11/09 6:11p Kedarr
 * Added revision and made changes to extend the abstract class.
 * 
 * 2     7/10/08 11:47a Kedarr
 * Removed the TODO tags.
 * 
 * 1     6/16/08 9:34a Kedarr
 * A sample class that demonstrates the generation of the request id.
 * 
*/
package com.myapp.preimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import stg.pr.engine.scheduler.IRequestIdGenerator;

/**
 * A sample class that demonstrates how to write your own Request Id generator.
 * Implementation of {@link IRequestIdGenerator}
 * 
 * @version $Revision: 2382 $
 * @author kedarr
 *
 */
public class CRequestIdGenerator implements IRequestIdGenerator {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";


    private Connection con_;

    /**
     * Default constructor.
     *
     */
    public CRequestIdGenerator() {
        super();
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.IRequestIdGenerator#setConnection(java.sql.Connection)
     */
    public void setConnection(Connection con) {
        con_ = con;
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.scheduler.IRequestIdGenerator#generateRequestId()
     */
    public long generateRequestId() throws SQLException {
        long lValue = 0L;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con_.createStatement();
            int i = st.executeUpdate("update request_id_sequence set req_id = LAST_INSERT_ID(req_id+1)");
            if (i == 0) {
                st.execute("insert into request_id_sequence values ()");
                return 1L;
            }
            rs = st.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                lValue = rs.getLong(1);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                // Do nothing
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (Exception e) {
                // Do nothing
            }
        }
        return lValue;
    }

}
