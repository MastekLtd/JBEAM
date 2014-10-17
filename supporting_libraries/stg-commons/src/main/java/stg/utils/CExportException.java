/*
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package stg.utils;

/**
 * Thrown when an Export is unable to format the specified field in the specified format.
 *  
 * @author Kedar C. Raybagkar
 * @version $Revision: 1 $
 */
public class CExportException extends Exception
{

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 1                 $";
    
    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -5771484058095587051L;

	/**
     * Creates an object of CExportException
     */
    public CExportException()
    {
        super();
    }

    /**
     *   Creates an object of CExportException
     *   @param pstr String Message to be thrown
     */
    public CExportException(String pstr)
    {
        super(pstr);
    }


} //end of CExportException.java
