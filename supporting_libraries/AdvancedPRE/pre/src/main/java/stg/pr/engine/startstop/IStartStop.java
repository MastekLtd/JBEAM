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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/startstop/IStartStop.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/engine/startstop/IStartStop.java $
 * 
 * 5     3/22/08 12:32a Kedarr
 * Removed the static keyword from the REVISION variable and made it
 * private. In case of interfaces made it as public.
 * 
 * 4     3/22/08 12:16a Kedarr
 * Added REVISION variable.
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:01p Kedarr
* Revision 1.2  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 4/06/03    Time: 2:35p
 * Created in $/GMACDev/ProcessRequestEngine/gmac/pr/engine/startstop
 * Inital Version.
*
*/
 
package stg.pr.engine.startstop;

/**
A marker interface.

An object implementing this interface is used to terminate engine.
*/
public interface IStartStop
{
    //a marker interface.
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public final String REVISION = "$Revision:: 2382              $";
}

