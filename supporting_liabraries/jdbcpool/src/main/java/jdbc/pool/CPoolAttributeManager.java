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
 * $Revision: 4 $
 *
 * $Header: /Utilities/JDBCPool/src/jdbc/pool/CPoolAttributeManager.java 4     3/17/08 2:46p Kedarr $
 *
 * $Log: /Utilities/JDBCPool/src/jdbc/pool/CPoolAttributeManager.java $
 * 
 * 4     3/17/08 2:46p Kedarr
 * Added REVISION number as a static field. Removed the getVersion()
 * method.
 * 
 * 3     3/17/08 1:09p Kedarr
 * Added a new abstract method delete. Also, changed the name of
 * getInstance(..) method to getPoolAttributeManager. Updated java doc.
 * 
 * 2     5/02/06 4:37p Kedarr
 * Revmoed the Singleton Factory Design Pattern and is now purely a
 * Abstract Pattern implementation which will re-create the instances
 * every time it is invoked.
 * 
 * 1     11/24/05 10:18a Kedarr
 * Abstract class to manage pool configuration file. Exhibits methods to
 * read, set and save pool attributes.
 * 
 */
package jdbc.pool;

import java.io.File;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;

/**
 * Class that is responsible to Read and Store the attributes.
 * 
 * @version $Revision: 4 $
 * @author kedarr
 * @since 13.01
 * 
 */
public abstract class CPoolAttributeManager {


    /**
     * Configuration file.
     * Comment for <code>file_</code>.
     */
    private final File file_;

    /**
     * Private constructor.
     *
     * @param file Configuration file
     */
    protected CPoolAttributeManager(File file) {
        file_ = file;
    }

    /**
     * Returns {@link CPoolAttributeManager}.
     * 
     * @param file
     *            Configuration File
     * @return CPoolAttributeManager
     * @throws ConfigurationException
     * 
     */
    public final static CPoolAttributeManager getPoolAttributeManager(File file)
            throws ConfigurationException {
//        if (instance == null) {
            int iPos = file.getName().lastIndexOf('.');
            if (iPos < 0) {
                throw new IllegalArgumentException(
                        "Invalid file passed. Need a configuration file either .xml or .properties");
            }
            if (file.getName().endsWith(".")) {
                throw new IllegalArgumentException(
                        "Invalid file passed. Need a configuration file either .xml or .properties");
            }
            String strExt = file.getName().substring(iPos);
            CPoolAttributeManager instance;
            if (strExt.equalsIgnoreCase(".properties")) {
                instance = new CPropertyManager(file);
            } else if (strExt.equalsIgnoreCase(".xml")) {
                instance = new CXMLManager(file);
            } else {
                throw new IllegalArgumentException("Unable to load the file #"
                        + file.getName() + ". Unrecognized file type.");
            }
//        }
        return instance;
    }

    /**
     * Updates the pool configuration in-memory with the given pool attribute.
     * 
     * Throws exception if the pool does not exists in the configuration file.
     * The file is changed in memory and an explict call to {@link #save()} must
     * be given to permanently save the data.
     * 
     * @param attribute
     *            Changed Pool Attributes.
     * @return boolean true if saved.
     * @throws ConfigurationException
     */
    protected abstract boolean update(CPoolAttribute attribute)
            throws ConfigurationException;

    /**
     * Creates a new pool in-memory.
     * 
     * Throws exception if the pool exists.The file is changed in memory and an
     * explict call to {@link #save()} must be given to permanently save the
     * data.
     * 
     * @param attribute CPoolAttribute
     * @return boolean true if created.
     * @throws ConfigurationException
     */
    protected abstract boolean create(CPoolAttribute attribute)
            throws ConfigurationException;

    /**
     * Saves the in-memory configuration changes to the disk.
     * 
     * @throws ConfigurationException
     */
    protected abstract void save() throws ConfigurationException;

    /**
     * Returns an map that contains all the pools attributes.
     * 
     * @return Map
     * @throws ConfigurationException 
     */
    protected abstract Map<String, CPoolAttribute> getAllPoolAttributes() throws ConfigurationException;

    /**
     * Returns the attributes for the specified pool.
     * 
     * @param name of the pool.
     * @return CPoolAttribute
     * @throws ConfigurationException 
     */
    protected abstract CPoolAttribute getPoolAttribute(String name) throws ConfigurationException;

    /**
     * Returns the configuration file.
     *
     * @return file
     */
    protected File getConfigurationFile() {
        return file_;
    }
    
    /**
     * Stores the revision number of the source code. 
     * This will be available in the .class file and then we can get the revision number of the class.
     * Comment for <code>REVISION</code>.
     */
    public static final String REVISION = "$Revision:: 4         $";

    
    /**
     * Method to delete the pool configurations from the configuration file.
     * 
     * @param strPoolName Pool Name to be deleted.
     * @return boolean True if deleted else false.
     * @throws ConfigurationException
     */
    protected abstract boolean delete(String strPoolName) throws ConfigurationException;


//    /**
//     * Destroys the instance.
//     */
//    public void destroy() {
//        instance = null;
//    }
}
