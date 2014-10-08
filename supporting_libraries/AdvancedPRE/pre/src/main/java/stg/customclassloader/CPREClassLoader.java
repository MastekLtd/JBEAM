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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/customclassloader/CPREClassLoader.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/customclassloader/CPREClassLoader.java $
 * 
 * 15    9/01/09 9:13a Kedarr
 * Externalize the inner class creation to named classes.
 * 
 * 14    3/23/09 5:35p Kedarr
 * Added Access controller to instantiate class loaders.
 * 
 * 13    2/04/09 12:58p Kedarr
 * Added static keyword to a final variable.
 * 
 * 12    3/22/08 2:16p Kedarr
 * Added REVISION number.
 * 
 * 11    7/26/05 11:12a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 10    7/15/05 1:11p Kedarr
 * Removed All Unused variables.
 * 
 * 9     6/06/05 4:09p Kedarr
 * Info and Debug logging is first checked whether it is enabled and then
 * only the messages are logged.
 * 
 * 8     5/31/05 6:17p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 7     2/03/05 2:37p Kedarr
 * Adding some debug messages..
 * 
 * 6     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 4     1/23/04 2:33p Kedarr
 * Changes done to incroporate the CCustomClassLoader so that the classes
 * can be loaded from the ZIP or JAR file.
* Revision 1.5  2004/01/12 09:09:38  kedar
* Changes made to load the class files from the Zip or Jar files.
*
 * 
 * 3     12/09/03 9:33p Kedarr
 * Removed UnUsed variables where ever possible and made
 * the necessary changes.
* Revision 1.4  2003/12/09 16:02:08  kedar
* Removed UnUsed variables where ever possible and made
* the necessary changes.
*
 * 
 * 2     12/01/03 1:31p Kedarr
* Revision 1.3  2003/11/28 09:55:10  kedar
* removed the system.out.println
*
 * 
 * 1     11/03/03 11:59a Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 4  *****************
 * User: Kedarr       Date: 9/18/03    Time: 4:13p
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/customclassloader
 * Organising Imports
 * 
 * *****************  Version 3  *****************
 * User: Kedarr       Date: 9/03/03    Time: 10:16a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/customclassloader
 * Added the Javadoc comment
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 20/12/02   Time: 8:15p
 * Updated in $/DEC18/ProcessRequestEngine/gmac/customclassloader
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:46p
 * Created in $/ProcessRequestEngine/gmac/customclassloader
 * Initial Version
*
*/
package stg.customclassloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.log4j.Logger;


/**
*   It reloads class files other than system classes and classes that are returned
*	by the method IPREClassLoaderClient.getSystemLoadedClasses().
*	The main objective of this classloader is that the engine need not be restarted 
*	once any of the business objects are changed.
*
* @version $Revision: 2382 $
* @author   Kedar C. Raybagkar
*/
public class CPREClassLoader extends ClassLoader
{

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


	private String strBaseDir_;
    private ClassLoader objCustomClassLoader_;
	private IPREClassLoaderClient pclClient_;
	private String[] saSystemClasses_;
    private Logger logger_;
	
	/**
	 * Constructs the class loader for the given parent class loader and the client
	 * @param pparent Parent ClassLoader
	 * @param ppclClient Class loader client implementation
	 * @param isReload True indicates the classes are to be reloaded. 
	 */
	public CPREClassLoader(ClassLoader pparent, final IPREClassLoaderClient ppclClient, boolean isReload) {
        super(pparent);
        this.pclClient_ = ppclClient;
        this.strBaseDir_ = ppclClient.getCustomClassLoaderClassPath();
        objCustomClassLoader_ = (ClassLoader) AccessController.doPrivileged(new CCustomClassLoaderAction(ppclClient));
        logger_ = Logger.getLogger("PREClassLoader");
	}

	/**
	 * Constructs the class loader for the given client implementation.
	 * @param ppclClient Client implementation.
	 * @param isReload True indicates that the classes are to be reloaded.
	 */
	public CPREClassLoader(final IPREClassLoaderClient ppclClient, boolean isReload) {
        super();
        this.pclClient_ = ppclClient;
        this.strBaseDir_ = ppclClient.getCustomClassLoaderClassPath();
        objCustomClassLoader_ = (ClassLoader) AccessController.doPrivileged(new CCustomClassLoaderAction(ppclClient));
        logger_ = Logger.getLogger("PREClassLoader");
	}

	/**
	 *	Loads classes except those that exist in the ArrayList alSystemClasses
	 *
	 *	@param name	String fully qualified class name
	 *	@param resolve boolean indicating whether the class file has to be resolved
	 *	@return Class object representing the loaded Class
	 */
	public synchronized Class<?> loadClass( String name, boolean resolve)
	throws ClassNotFoundException
	{
		if (saSystemClasses_ == null) {
			saSystemClasses_ = pclClient_.getSystemLoadedClasses();
		}

		// Our goal is to get a Class object
		Class<?> clas = null;

		clas = findLoadedClass( name );
			
//		if (!bReload_) {

//			if (clas != null) {
//				return clas;
//			}
//		}

		// Create a pathname from the class name Eg:java.lang.Object => java/lang/Object
		String fileStub = name.replace( '.', '/' );

		// Build objects pointing to the object code (.class)
		String classFilename = strBaseDir_ + fileStub+".class";
//		File classFile = new File( classFilename );

		// load up the raw bytes if toBeLoadedBySystemClassLoader() returns false
		boolean bInvokeSystemClassLoader = false;
		if (clas == null)
        {
            bInvokeSystemClassLoader = toBeLoadedBySystemClassLoader(name); 
		    if (!bInvokeSystemClassLoader) {
                if (logger_.isDebugEnabled()) {
                    logger_.debug("Loading class #" + name);
                }
			    try {
				    // read the bytes
				    byte raw[] = getBytes( classFilename );

				    // try to turn them into a class				
				    clas = defineClass( name, raw, 0, raw.length );

			    } catch( IOException ie )
			    {
				    // This is not a failure! If we reach here, it might
				    // mean that we are dealing with a class in a library,
				    // such as java.lang.Object
			    }
		    }
        }

		// Maybe the class is in a library -- try loading
		// the normal way
		if(clas == null) {
            if (bInvokeSystemClassLoader)
            {
                clas = findSystemClass( name );
            }
            else
            {
                clas = objCustomClassLoader_.loadClass(name);
            }
		}

		// Resolve the class, if any, but only if the "resolve"
		// flag is set to true
		if (resolve && clas != null) {
			resolveClass( clas );
		}

		// If we still don't have a class, it's an error
		if (clas == null) {
			throw new ClassNotFoundException(name);
		}
		// Otherwise, return the class
		
		return clas;
	}

	/**
	 *	Given a filename, read the entirety of that file from disk
	 *  and return it as a byte array
	 *	
	 *	@param filename String object => name of the class file which has to be read from the disk
	 *	@return a byte array containing the contents of the file that is read
     *  @throws IOException
	 */
	private byte[] getBytes( String filename ) throws IOException
	{
		// Find out the length of the file
		File file = new File( filename );
		long len = file.length();
		
		// Create an array that's just the right size for the file's
		// contents
		byte raw[] = new byte[(int)len];
		
		// Open the file
		FileInputStream fin = null;
		try {
            fin = new FileInputStream( file );
            // Read all of it into the array; if we don't get all,
            // then it's an error.
            int r = fin.read( raw );
            if (r != len)
                throw new IOException( "Can't read all, "+r+" != "+len );
            
            // Don't forget to close the file!
            
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
		

		// And finally return the file contents as an array
		return raw;
	}

	private boolean toBeLoadedBySystemClassLoader(String name) {

		boolean bToBeLoadedBySystem = false;
		int iElement = 0;

		if (saSystemClasses_.length > 0) {

			for (;iElement < saSystemClasses_.length && !bToBeLoadedBySystem; iElement ++) {

				if (name.startsWith(saSystemClasses_[iElement])) {
					bToBeLoadedBySystem = true;
				}
			}
		}

		return bToBeLoadedBySystem;
	}
}

class PREClassLoaderAction implements PrivilegedAction<Object> {
    private IPREClassLoaderClient client_;
    private boolean bReload_;

    public PREClassLoaderAction(IPREClassLoaderClient client, boolean reload) {
        client_ = client;
        bReload_= true;
    }
    
    public Object run(){
        return new CPREClassLoader(client_, bReload_);
    }
}
