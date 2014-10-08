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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/customclassloader/IPREClassLoaderClient.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/customclassloader/IPREClassLoaderClient.java $
 * 
 * 5     2/04/09 12:58p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     3/22/08 2:16p Kedarr
 * Added REVISION number.
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
 * User: Kedarr       Date: 9/03/03    Time: 10:17a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/customclassloader
 * Added the Javadoc comment
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/customclassloader
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:46p
 * Created in $/ProcessRequestEngine/gmac/customclassloader
 * Initial Version
*
*/
package stg.customclassloader;

/**
 *	Sub class of ICustomClassLoaderClient
 *	CustomClassLoaders ( for eg: CPREClassLoader ) use this interface to callback on the
 *	objects that implement this
 *	eg : CProcessRequestEngine implements this interface
 *
 * @version $Revision: 2382 $
 * @author   Kedar C. Raybagkar
 */
public interface IPREClassLoaderClient extends ICustomClassLoaderClient {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
	/**
	 * Returns the path from where the classes are to be loaded.
	 * 
	 * @return String
	 */
	public String   getCustomClassLoaderClassPath();
	
	/**
	 * Returns the class names that are to be loaded by the system class loader.
	 * @return String[]
	 */
	public String[] getSystemLoadedClasses();
	
	/**
	 * Returns true to reload the classes else return false.
	 * @return boolean
	 */
	public boolean  isReload();
}