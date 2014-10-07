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
 * $Revision: 2737 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/datasource/defaultimpl/PREDataSourceFactory.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/datasource/defaultimpl/PREDataSourceFactory.java $
 * 
 * 2     9/08/09 3:27p Kedarr
 * Updated javadoc and refactored method parameters.
 * 
 * 1     8/30/09 10:58p Kedarr
 * New class defined.
 * 
 */
package stg.pr.engine.datasource.defaultimpl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import jdbc.pool.CConnectionPoolManager;

import stg.pr.engine.datasource.IDataSourceFactory;

/**
 * Default implementation of the {@link IDataSourceFactory}.
 *
 * This internally uses proprietary JDBCPool tool.
 *
 * @version $Revision: 2737 $
 * @author Kedar C. Raybagkar
 * @since  V1.0R26.02
 */
public class PREDataSourceFactory implements IDataSourceFactory {

    /**
     * Connection pool instance.
     */
    private CConnectionPoolManager poolManager_;
    
    /**
     * Logger.
     */
    private static Logger logger_ = Logger.getLogger(PREDataSourceFactory.class);

    /* (non-Javadoc)
     * @see stg.pr.engine.datasource.IDataSource#getDataSource(java.lang.String)
     */
    public DataSource getDataSource(String lookUpName) throws IOException, SQLException {
        try {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Fetching DataSource #" + lookUpName);
            }
            return poolManager_.getDataSource(lookUpName);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.datasource.IDataSource#shutdown()
     */
    public boolean shutdown() throws SQLException, IOException {
        if (logger_.isInfoEnabled()) {
            logger_.info("Shutdown invoked...");
        }
        return poolManager_.emptyAllPools(true);
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.datasource.IDataSource#initialize(java.io.File)
     */
    public boolean initialize(File configurationFile) throws SQLException, IOException {
    	if (!configurationFile.exists()) {
    		logger_.fatal("Pool configuration file does not exists or wrongly configured :" + configurationFile.getPath());
    		return false;
    	}
        try {
            if (logger_.isInfoEnabled()) {
                logger_.info("Initializing the DataSourceFactory...");
            }
            poolManager_ = jdbc.pool.CConnectionPoolManager.getInstance(null, configurationFile);
            if (logger_.isInfoEnabled()) {
                logger_.info("DataSourceFactory Initialized properly..");
            }
        } catch (ConfigurationException e) {
            throw new IOException(e);
        } catch (ParseException e) {
            throw new IOException(e);
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
        return true;
    }

}
