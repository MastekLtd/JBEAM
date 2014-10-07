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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/datasource/IDataSourceFactory.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/datasource/IDataSourceFactory.java $
 * 
 * 2     9/08/09 3:27p Kedarr
 * Updated javadoc and refactored method parameters.
 * 
 * 1     8/30/09 10:58p Kedarr
 * New class defined.
 * 
 */
package stg.pr.engine.datasource;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * DataSource Factory interface.
 *
 * Defines methods to retrieve, initialize, shutdown the Data Source factory used.
 * This class is instantiated once and will act like a single-ton factory pattern
 * through out the life of PRE. As the class is instantiated by PRE it is not necessary 
 * to have any synchronized methods.
 *
 * @version $Revision: 2382 $
 * @author Kedar C. Raybagkar
 * @since  V1.0R26.02
 */
public interface IDataSourceFactory {

    /**
     * Returns the DataSource from which the JDBC connections will be fetched.
     * 
     * @param lookUpName of the DataSource.
     * @return DataSource
     * @throws SQLException
     * @throws IOException
     */
    public javax.sql.DataSource getDataSource(String lookUpName) throws SQLException, IOException;
    
    /**
     * Initializes the factory with a parameter file.
     * 
     * Returns true if initialized, false if unable to initialize.
     * 
     * @param configurationFile
     * @return boolean
     * @throws SQLException
     * @throws IOException
     */
    public boolean initialize(File configurationFile) throws SQLException, IOException;
    
    /**
     * Method to shutdown the Data Source Factory.
     * 
     * Return true if successful else returns false.
     * 
     * @return boolean
     * @throws SQLException
     * @throws IOException
     */
    public boolean shutdown() throws SQLException, IOException;
    
}
