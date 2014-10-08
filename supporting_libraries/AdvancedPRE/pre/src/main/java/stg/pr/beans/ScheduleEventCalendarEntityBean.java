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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ScheduleEventCalendarEntityBean.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/beans/ScheduleEventCalendarEntityBean.java $
 * 
 * 2     2/04/09 3:51p Kedarr
 * Added static keyword to a final variable.
 * 
 * 1     9/16/08 12:05p Kedarr
 * Initial Version
 * 
 * 5     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 4     3/02/07 8:52a Kedarr
 * Added serial version as it is a good practice for serializable objects
 * 
 * 3     1/19/05 3:10p Kedarr
 * Advanced PRE
* Revision 1.1  2005/11/03 04:54:42  kedar
* *** empty log message ***
*
 * 
 * 1     11/03/03 12:00p Kedarr
* Revision 1.5  2003/11/01 09:06:41  kedar
* Organized the imports
*
* Revision 1.4  2003/10/29 07:08:09  kedar
* Changes made for changing the Header Information from all the files.
* These files now do belong to Systems Task Group International Ltd.
*
* Revision 1.3  2003/10/28 09:46:19  kedar
* Added a STATIC_DYNAMIC_FLAG to identify Static Dynamic nature of the parameter if the parameter is a date.
* If the parameter is a date then:
* If the flag is set to Dynamic then the scheduler will be advance the date with the scheduled frequency.
* If the flag is set to Static then the schedule will not advance the date.
*
* Revision 1.2  2003/10/23 09:06:29  kedar
* Added Static Dynamic Flag indicator
*
* Revision 1.1  2003/10/23 06:58:41  kedar
* Inital Version Same as VSS
*
 * 
 * *****************  Version 2  *****************
 * User: Kedarr       Date: 9/19/03    Time: 10:10a
 * Updated in $/GMACDev/ProcessRequestEngine/gmac/pr/beans
 * Organising Imports
 * 
 * *****************  Version 1  *****************
 * User: Nixon        Date: 12/18/02   Time: 3:49p
 * Created in $/DEC18/ProcessRequestEngine/gmac/pr/beans
 * 
 * *****************  Version 1  *****************
 * User: Kedarr       Date: 10/12/02   Time: 3:49p
 * Created in $/ProcessRequestEngine/gmac/pr/beans
 * Initial Version
*
*/

package stg.pr.beans;

import java.io.Serializable;

import stg.database.CBeanException;

public class ScheduleEventCalendarEntityBean implements Cloneable, Serializable
{

	/**
     * 
     */
    private static final long serialVersionUID = 53886705070606653L
    ;
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";

	public ScheduleEventCalendarEntityBean()
    {
        super(); //Default Constructor given
    }

    private long schId = 0;
    private long oldschId = 0;

    public void setSchId(long schId)
    {
        this.schId = schId;
    }

    public long getSchId()
    {
        return schId;
    }

    public void setoldSchId(long oldschId)
    {
        this.oldschId = oldschId;
    }

    public long getoldSchId()
    {
        return oldschId;
    }

    private long serialNo = 0;
    private long oldserialNo = 0;

    public void setSerialNo(long serialNo)
    {
        this.serialNo = serialNo;
    }

    public long getSerialNo()
    {
        return serialNo;
    }

    public void setoldSerialNo(long oldserialNo)
    {
        this.oldserialNo = oldserialNo;
    }

    public long getoldSerialNo()
    {
        return oldserialNo;
    }

    private String processClassNm = null;
    private String oldprocessClassNm = null;

    public void setProcessClassNm(String processClassNm)
    {
        this.processClassNm = processClassNm;
    }

    public String getProcessClassNm()
    {
        return processClassNm;
    }

    public void setoldProcessClassNm(String oldpprocessClassNm)
    {
        this.oldprocessClassNm = oldpprocessClassNm;
    }

    public String getoldProcessClassNm()
    {
        return oldprocessClassNm;
    }

    private String category = null;
    private String oldcategory = null;

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }

    public void setoldCategory(String oldcategory)
    {
        this.oldcategory = oldcategory;
    }

    public String getoldCategory()
    {
        return oldcategory;
    }

    private void checkNull(String pstrMessage, Object obj)
        throws CBeanException
    {
        if (obj == null)
            throw new CBeanException(
                pstrMessage + " : Mandatory Field. Cannot Be Left Blank.");
        if (obj.equals(""))
            throw new CBeanException(
                pstrMessage + " : Mandatory Field. Cannot Be Left Blank.");
    }

    public void checkMandatory() throws CBeanException
    {
        checkNull("processClassNm", processClassNm);
    }

    public void initialize()
    {
        schId = 0;
        oldschId = 0;
        serialNo = 0;
        oldserialNo = 0;
        processClassNm = null;
        oldprocessClassNm = null;
        category = null;
        oldcategory = null;
        rStatus = false;
    }

    public void setRStatus(boolean b)
    {
        this.rStatus = b;
    }
    public boolean getRStatus()
    {
        return rStatus;
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch (CloneNotSupportedException cnse)
        {
            return null;
        }
    }

    public String toString()
    {
        return "SCH_ID=" + schId + "," + "SERIAL=" + serialNo;
    }

    boolean rStatus = false;

}
