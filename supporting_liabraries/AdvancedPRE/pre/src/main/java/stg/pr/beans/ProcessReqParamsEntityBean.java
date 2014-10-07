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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessReqParamsEntityBean.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/beans/ProcessReqParamsEntityBean.java $
 * 
 * 6     2/04/09 3:48p Kedarr
 * Added static keyword to a final variable.
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

/**
 * A value object for table <code>process_req_params</code>
 *
 * @author Kedar Raybagkar
 * @since
 */
public class ProcessReqParamsEntityBean implements Cloneable, Serializable
{

    /**
	 * Serial Version.
	 */
	private static final long serialVersionUID = -8310229583483761535L;
	
	/**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";

	/**
	 * Default
	 */
	public ProcessReqParamsEntityBean()
    {
        super(); //Default Constructor given
    }

    private long reqId = 0;
    private long oldreqId = 0;

    public void setReqId(long reqId)
    {
        this.reqId = reqId;
    }

    public long getReqId()
    {
        return reqId;
    }

    public void setoldReqId(long oldreqId)
    {
        this.oldreqId = oldreqId;
    }

    public long getoldReqId()
    {
        return oldreqId;
    }

    private long paramNo = 0;
    private long oldparamNo = 0;

    public void setParamNo(long paramNo)
    {
        this.paramNo = paramNo;
    }

    public long getParamNo()
    {
        return paramNo;
    }

    public void setoldParamNo(long oldparamNo)
    {
        this.oldparamNo = oldparamNo;
    }

    public long getoldParamNo()
    {
        return oldparamNo;
    }

    private String paramFld = null;
    private String oldparamFld = null;

    public void setParamFld(String paramFld)
    {
        this.paramFld = paramFld;
    }

    public String getParamFld()
    {
        return paramFld;
    }

    public void setoldParamFld(String oldparamFld)
    {
        this.oldparamFld = oldparamFld;
    }

    public String getoldParamFld()
    {
        return oldparamFld;
    }

    private String paramVal = null;
    private String oldparamVal = null;

    public void setParamVal(String paramVal)
    {
        this.paramVal = paramVal;
    }

    public String getParamVal()
    {
        return paramVal;
    }

    public void setoldParamVal(String oldparamVal)
    {
        this.oldparamVal = oldparamVal;
    }

    public String getoldParamVal()
    {
        return oldparamVal;
    }

    private String paramDataType = null;
    private String oldparamDataType = null;

    public void setParamDataType(String paramDataType)
    {
        this.paramDataType = paramDataType;
    }

    public String getParamDataType()
    {
        return paramDataType;
    }

    public void setoldParamDataType(String oldparamDataType)
    {
        this.oldparamDataType = oldparamDataType;
    }

    public String getoldParamDataType()
    {
        return oldparamDataType;
    }

    private String staticDynamicFlag;
    private String oldstaticDynamicFlag;
    
    public String getoldStaticDynamicFlag()
    {
        return oldstaticDynamicFlag;
    }

    public String getStaticDynamicFlag()
    {
        return staticDynamicFlag;
    }

    public void setoldStaticDynamicFlag(String string)
    {
        if (string == null || string.equals(""))
        {
            string = "S";
        }
        oldstaticDynamicFlag = string;
    }

    public void setStaticDynamicFlag(String string)
    {
        if (string == null || string.equals(""))
        {
            string = "S";
        }
        staticDynamicFlag = string;
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
        checkNull("paramFld", paramFld);
        checkNull("paramDataType", paramDataType);
    }

    public void initialize()
    {
        reqId = 0;
        oldreqId = 0;
        paramNo = 0;
        oldparamNo = 0;
        paramFld = null;
        oldparamFld = null;
        paramVal = null;
        oldparamVal = null;
        paramDataType = null;
        oldparamDataType = null;
        rStatus = false;
        staticDynamicFlag = null;
        oldstaticDynamicFlag = null;
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
        return "REQ_ID=" + reqId + "," + "PARAM_NO=" + paramNo;
    }

    boolean rStatus = false;

}
