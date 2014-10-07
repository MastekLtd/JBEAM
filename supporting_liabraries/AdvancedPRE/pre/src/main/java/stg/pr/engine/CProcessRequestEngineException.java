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
* $Revision: 2726 $
*
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/CProcessRequestEngineException.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/CProcessRequestEngineException.java $
 * 
 * 9     2/04/09 3:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 8     4/07/08 5:06p Kedarr
 * Added/changed the REVISION variable to public. Removed the API for
 * getVersion()
 * 
 * 7     3/02/07 9:00a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 6     7/15/05 3:03p Kedarr
 * Updated Javadoc
 * 
 * 5     7/15/05 2:29p Kedarr
 * Removed All Unused variables. Added a method to return REVISION number.
 * 
 * 4     6/20/05 10:52a Kedarr
 * Changes made to call the super exception with an embedded exception.
 * Also added a new constructor to satisfy above.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:40  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:50p
 * Created in $/DEC18/ProcessRequestEngine/gmac/pr/engine
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:56p
 * Created in $/ProcessRequestEngine/gmac/pr/engine
 * Initial Version
*
*/
 
package stg.pr.engine;

/**
 * Exception class.
 *
 *	All exceptions thrown from PRE are wrapped using this class.
 *
 * @author Kedar Raybagkar
 */
public class CProcessRequestEngineException extends Exception
{

    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 95949985919065853L;
	/**
     * The version control system will update this revision variable and thus
     * the revision numbers will automatically get updated. 
     * Comment for <code>strRevision</code>
     */
    public static final String REVISION = "$Revision:: 2726              $";
    
    /**
     * Default constructor. 
     */
    public CProcessRequestEngineException()
    {
        super();
    }


    /**
     * Default constructor. 
     * @param pstr
     */
    public CProcessRequestEngineException(String pstr)
    {
        super(pstr);
    }
    
    /**
     * Default constructor. 
     * @param pstr
     * @param cause
     */
    public CProcessRequestEngineException(String pstr, Throwable cause){
        super(pstr, cause);
    }

    /**
     * Default constructor. 
     * @param cause
     */
    public CProcessRequestEngineException(Throwable cause){
    	super(cause);
    }
    
}
