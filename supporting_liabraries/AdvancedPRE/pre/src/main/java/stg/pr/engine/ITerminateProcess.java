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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/ITerminateProcess.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/ITerminateProcess.java $
 * 
 * 4     2/04/09 3:54p Kedarr
 * Added static keyword to a final variable.
 * 
 * 3     4/08/08 9:26p Kedarr
 * Added/changed the REVISION variable to public.
 * 
 * 2     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
* Revision 1.1  2004/05/03 06:28:05  kedar
* Added a new Interface which would help the PRE to terminate a process.
*
* 
*/
package stg.pr.engine;

/**
 * This class is an indicator that the implementor class can be terminated.
 * To kill or terminate the on going process is implementation specific.
 * This does not garuntee that the process will get terminated but it garuntees
 * that an indication is given to the implementor that the process needs to be
 * terminated.
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 2382 $
 *
 */
public interface ITerminateProcess
{
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";
    
    /**
     * Terminate the process.
     * 
     * @param strReason String message stating the reason.
     */
    public void terminate(String strReason);
}
