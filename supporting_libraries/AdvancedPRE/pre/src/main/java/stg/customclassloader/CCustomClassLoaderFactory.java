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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/customclassloader/CCustomClassLoaderFactory.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/customclassloader/CCustomClassLoaderFactory.java $
 * 
 * 9     9/01/09 9:13a Kedarr
 * Externalize the inner class creation to named classes.
 * 
 * 8     3/23/09 5:35p Kedarr
 * Added Access controller to instantiate class loaders.
 * 
 * 7     3/21/09 3:57p Kedarr
 * Changes made for synchronizing the instance creation.
 * 
 * 6     2/04/09 11:36a Kedarr
 * Added static keyword to a final variable.
 * 
 * 5     3/22/08 2:16p Kedarr
 * Added REVISION number.
 * 
 * 4     3/18/08 2:51p Kedarr
 * Formatting changed.
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * 
 * 1     11/03/03 11:59a Kedarr
 * Revision 1.2  2003/10/29 07:08:09  kedar
 * Changes made for changing the Header Information from all the files.
 * These files now do belong to Systems Task Group International Ltd.
 *
 * Revision 1.1  2003/10/23 06:58:40  kedar
 * Inital Version Same as VSS
 *
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/03/03    Time: 9:53a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/customclassloader
 * Added the Javadoc comment
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/customclassloader
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:45p
 * Created in $/ProcessRequestEngine/gmac/customclassloader
 * Initial Version
 *
 */
package stg.customclassloader;

import java.security.AccessController;
import java.security.PrivilegedAction;

import stg.utils.CSettings;

/**
 * Class Loader factory class which will determine which classloader to
 * instantiate.
 * 
 * The classloader determines whether to instantiate CustomeClassLoader to load
 * the classes or system ClassLoader to load the class.
 * 
 * @version $Revision: 2382 $
 * @author Kedar C. Raybagkar
 * 
 */
public class CCustomClassLoaderFactory {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 2382              $";

    /**
     * Returns the Revision number of the class.
     * Identifies the version number of the source code that generated this class file stored
     * in the configuration management tool.
     * 
     * @return String
     */
    public String getRevision() {
        return REVISION;
    }



    private static CCustomClassLoaderFactory cclf_;

    private ClassLoader cl_;

    private CCustomClassLoaderFactory() {
    }

    /**
     * Returns the instance.
     * @return CCustomClassLoaderFactory
     */
    public synchronized static CCustomClassLoaderFactory getInstance() {

        if (cclf_ == null) {
            cclf_ = new CCustomClassLoaderFactory();
        }

        return cclf_;
    }

    /**
     * Returns the appropriate class loader for the given client.
     * 
     * @param picclc ICustomClassLoaderClient
     * @return ClassLoader
     * @throws Exception
     */
    public ClassLoader getClassLoader(final ICustomClassLoaderClient picclc)
            throws Exception {

        if (CSettings.get("pr.useprecustomclassloader", "true")
                .equalsIgnoreCase("false")) {
            return this.getClass().getClassLoader();
        } else if (picclc instanceof IPREClassLoaderClient) {
            IPREClassLoaderClient client = (IPREClassLoaderClient) picclc;
            if (((IPREClassLoaderClient) picclc).isReload()) {
                cl_ = null;
                return (ClassLoader) AccessController.doPrivileged(new PREClassLoaderAction(client, true));
            } else {

                if (cl_ == null) {
                    cl_ = (ClassLoader) AccessController.doPrivileged(new PREClassLoaderAction(client, false));
                }
            }
        } else {
            throw new Exception("ClassLoader instance creation failed");
        }

        return cl_;
    }
    
}

class CCustomClassLoaderAction implements PrivilegedAction<Object> {

    private IPREClassLoaderClient client_;

    
    public CCustomClassLoaderAction(IPREClassLoaderClient client) {
        this.client_ = client;
    }

    /* (non-Javadoc)
     * @see java.security.PrivilegedAction#run()
     */
    public Object run() {
        return new CCustomClassLoader(client_);
    }
    
}