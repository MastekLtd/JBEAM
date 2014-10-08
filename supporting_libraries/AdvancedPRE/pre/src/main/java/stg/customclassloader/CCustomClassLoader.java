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
* $Revision: 2771 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/customclassloader/CCustomClassLoader.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/customclassloader/CCustomClassLoader.java $
 * 
 * 13    9/01/09 9:09a Kedarr
 * Removed unnecessary null check.
 * 
 * 12    2/04/09 11:28a Kedarr
 * Added static keyword to a final variable. Also, rectified the coding
 * issue of not closing Byte Array stream.
 * 
 * 11    3/22/08 2:16p Kedarr
 * Added REVISION number.
 * 
 * 10    7/26/05 11:13a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 9     6/06/05 4:09p Kedarr
 * Info and Debug logging is first checked whether it is enabled and then
 * only the messages are logged.
 * 
 * 8     6/01/05 11:55a Kedarr
 * Changed the message.
 * 
 * 7     5/31/05 6:17p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 6     2/03/05 2:36p Kedarr
 * Adding some debug messages..
 * 
 * 5     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 3     3/01/04 4:23p Kedarr
 * Changes made for Javadoc and removed unwanted log message.
* Revision 1.7  2004/02/12 16:15:44  kedar
* Changes made for the Javadoc
*
* Revision 1.6  2004/02/10 22:34:44  kedar
* Organized the import statements
*
* Revision 1.5  2004/02/10 16:57:25  kedar
* Debugging message commented.
*
 * 
 * 2     1/23/04 2:29p Kedarr
 * Added a Logger and JavaDoc
* Revision 1.4  2004/01/16 13:25:32  kedar
* Added JavaDoc
*
* Revision 1.3  2004/01/12 09:10:06  kedar
* Changes made to close the zip file in finally block.
*
* Revision 1.2  2004/01/12 07:27:54  kedar
* Changes made to the default Constructor...
*
 * 
 * 1     12/01/03 1:29p Kedarr
* Revision 1.1  2003/11/27 08:35:53  kedar
* New Class Loader. Taken from the JUNIT.
* This reads from the jar files
*
* 
*/
package stg.customclassloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

/**
 * A custom class loader which enables the reloading
 * of classes for run depending upon the property defined. The class loader
 * can be configured with a list of package paths that
 * should be excluded from loading. The loading
 * of these packages is delegated to the system class
 * loader. They will be shared across test runs.
 * <b>Known limitation:</b> the CustomClassLoader cannot Re-Load classes
 * from jar files. The Jar file gets locked.
 */

public class CCustomClassLoader extends ClassLoader
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    private static final String REVISION = "$Revision:: 2771              $";

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


    /**
     * This variables stores the scanned class path.
     */
    private Vector<String> fPathItems;

    /**
     * This variable stores all the default classes that must not be loaded by this
     * class loader.
     * 
     * The class will always be checked by condtion String.startsWith();
     */
    private String[] defaultExclusions =
        { "java." };


    /**
     * The path that is excluded
     */
    private Vector<String> fExcluded;
    
    /**
     * The class that has implemented the interface IPREClassLoaderClient. This class
     * implements one method that gives a string array for the files that needs to be
     * loaded throught the system calss loader.
     */
    private IPREClassLoaderClient objPREClassLoaderClient_;

    static private Logger logger_ = Logger.getLogger("CustomClassLoader");

    /**
     * Constructs a tailor made class loader.
     *
     * This scans the system property <code>java.class.path</code> to
     * load the class.
     *
     * @param ppclClient IPREClassLoaderClient
     */
    public CCustomClassLoader(IPREClassLoaderClient ppclClient)
    {
        this(System.getProperty("java.class.path"), ppclClient);
    }

    /**
     * Constructs a tailor made class loader.
     *
     * This scans the given classpath to load the class.
     *
     * @param classPath String
     * @param ppclClient IPREClassLoaderClient
     */
    public CCustomClassLoader(String classPath, IPREClassLoaderClient ppclClient)
    {
        objPREClassLoaderClient_ = ppclClient;
        scanPath(classPath);
        readExcludedPackages();
    }

    /**
     * Scans the given path. This path is then used to load the class if the normal
     * mechanism to load the class fails.
     * @param classPath String representing the java class path.
     */
    private void scanPath(String classPath)
    {
        String separator = System.getProperty("path.separator");
        fPathItems = new Vector<String>(10);
        StringTokenizer st = new StringTokenizer(classPath, separator);
        while (st.hasMoreTokens())
        {
            fPathItems.addElement(st.nextToken());
        }
    }

    /* (non-Javadoc)
     * @see java.lang.ClassLoader#getResource(java.lang.String)
     */
    public URL getResource(String name)
    {
        return ClassLoader.getSystemResource(name);
    }

    /* (non-Javadoc)
     * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
     */
    public InputStream getResourceAsStream(String name)
    {
        return ClassLoader.getSystemResourceAsStream(name);
    }

    /**
     * Check whether the class file to be loaded by System Class Loader.
     * 
     * @param name java class name.
     * @return boolean true if to be loaded by System Class Loader else false.
     */
    public boolean isExcluded(String name)
    {
        for (int i = 0; i < fExcluded.size(); i++)
        {
            if (name.startsWith((String) fExcluded.elementAt(i)))
            {
                return true;
            }
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     */
    public synchronized Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException
    {

        Class<?> c = findLoadedClass(name);
        if (c != null)
            return c;
        //
        // Delegate the loading of excluded classes to the
        // standard class loader.
        //
        if (isExcluded(name))
        {
            try
            {
                c = findSystemClass(name);
                return c;
            }
            catch (ClassNotFoundException e)
            {
                // keep searching
            }
        }
        byte[] data = lookupClassData(name);
        if (data == null)
            throw new ClassNotFoundException();
        c = defineClass(name, data, 0, data.length);
        if (resolve)
            resolveClass(c);
        return c;
    }

    /**
     * This method is used to load the class file from the paths or jar, zip files
     * associated in the java classpath.
     *  
     * @param className to be loaded.
     * @return byte[] array of bytes.
     * @throws ClassNotFoundException
     */
    public byte[] lookupClassData(String className)
        throws ClassNotFoundException
    {
        byte[] data = new byte[0];
        for (int i = 0; i < fPathItems.size(); i++)
        {
            String path = (String) fPathItems.elementAt(i);
            String fileName = className.replace('.', '/') + ".class";
            if (isJar(path))
            {
                if (logger_.isDebugEnabled()) {
                    logger_.debug("Trying to load file# " + fileName + " through Zip/Jar# " + path );
                }
                data = loadJarData(path, fileName);
            }
            else
            {
                if (logger_.isDebugEnabled()) {
                    logger_.debug("Trying to load through File System " + fileName);
                }
                try {
                    data = loadFileData(path, fileName);
                } catch (IOException e) {
                }
            }
            if (data.length > 0)
                return data;
        }
        throw new ClassNotFoundException(className);
    }

    /**
     * Checks whether the path entry is of type jar or zip.
     * 
     * @param pathEntry String
     * @return boolean true if pathEntry is of type jar and or zip.
     */
    protected boolean isJar(String pathEntry)
    {
        return pathEntry.endsWith(".jar") || pathEntry.endsWith(".zip");
    }

    /**
     * Loads the class from the desired path.
     *  
     * @param path 
     * @param fileName
     * @return byte[] class data.
     * @throws IOException 
     */
    private byte[] loadFileData(String path, String fileName) throws IOException
    {
        File file = new File(path, fileName);
        if (file.exists())
        {
            return getClassData(file);
        }
        return new byte[0];
    }

    /**
     * Reads the file and sents the raw data in byte array.
     * @param f File to be read.
     * @return byte[] representing the class.
     * @throws IOException 
     */
    private byte[] getClassData(File f) throws IOException
    {
        FileInputStream stream = null;
        ByteArrayOutputStream out = null;
        try
        {
            stream = new FileInputStream(f);
            out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            out.close();
            return out.toByteArray();

        } finally {
            if (stream != null) {
                stream.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Loads the class from the associated jar and/or zip file.
     * 
     * @param path jar or zip file location
     * @param fileName file to be loaded.
     * @return byte[] array of class data.
     */
    private byte[] loadJarData(String path, String fileName)
    {
        ZipFile zipFile = null;
        InputStream stream = null;
        File archive = new File(path);
        if (!archive.exists())
            return new byte[0];
        try
        {
            zipFile = new ZipFile(archive);
        }
        catch (IOException io)
        {
            return new byte[0];
        }
        ZipEntry entry = zipFile.getEntry(fileName);
        if (entry == null)
            return new byte[0];
        int size = (int) entry.getSize();
        try
        {
            stream = zipFile.getInputStream(entry);
            byte[] data = new byte[size];
            int pos = 0;
            while (pos < size)
            {
                int n = stream.read(data, pos, data.length - pos);
                pos += n;
            }
            return data;
        }
        catch (IOException e)
        {
        }
        finally
        {
            try
            {
                if (stream != null)
                    stream.close();
            }
            catch (IOException e)
            {
            }
            try
            {
                if (zipFile != null)
                {
                    zipFile.close();
                }
            }
            catch (IOException ioe)
            {
            }
        }
        return new byte[0];
    }

    /**
     * Included the classes that are not to be loaded by the custom class loader.
     */
    private void readExcludedPackages()
    {
        fExcluded = new Vector<String>(10);
        for (int i = 0; i < defaultExclusions.length; i++)
            fExcluded.addElement(defaultExclusions[i]);
        String[] strArray = objPREClassLoaderClient_.getSystemLoadedClasses();
        if (strArray != null)
        {
            for (int i = 0; i < strArray.length; i++)
            {
                fExcluded.addElement(strArray[i]);
            }
        }
//        InputStream is = getClass().getResourceAsStream(EXCLUDED_FILE);
//        if (is == null)
//            return;
//        Properties p = new Properties();
//        try
//        {
//            p.load(is);
//        }
//        catch (IOException e)
//        {
//            return;
//        }
//        finally
//        {
//            try
//            {
//                is.close();
//            }
//            catch (IOException e)
//            {
//            }
//        }
//        for (Enumeration e = p.propertyNames(); e.hasMoreElements();)
//        {
//            String key = (String) e.nextElement();
//            if (key.startsWith("excluded."))
//            {
//                String path = p.getProperty(key);
//                path = path.trim();
//                if (path.endsWith("*"))
//                    path = path.substring(0, path.length() - 1);
//                if (path.length() > 0)
//                    fExcluded.addElement(path);
//            }
//        }
    }
}
