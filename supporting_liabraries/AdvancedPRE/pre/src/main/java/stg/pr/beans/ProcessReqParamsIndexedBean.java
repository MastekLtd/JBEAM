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
* $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/beans/ProcessReqParamsIndexedBean.java 1402 2010-05-06 11:14:41Z kedar $
*
* $Log: /Utilities/PRE/src/stg/pr/beans/ProcessReqParamsIndexedBean.java $
 * 
 * 5     2/04/09 3:48p Kedarr
 * Added static keyword to a final variable.
 * 
 * 4     3/23/08 12:39p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 3     1/19/05 3:10p Kedarr
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



public class ProcessReqParamsIndexedBean {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";


    private int         iSize_;
    private long[]      reqId_;
    private String[]    paramFld_;
    private String[]    paramVal_;

    public void setSize(int iSize) {
        this.iSize_ = iSize;
    }
    
    public int getSize() {
        return iSize_;
    }

    public void checkSize(String strField) throws Exception {
        if (iSize_ <= 0) {
            throw new Exception("setSize() should be called before setting " + strField);
        }
    }
    
    public void setReqId(int iIndex, long plReqId) throws Exception {
        checkSize("Request Id");
        
        if (reqId_ == null) {
            reqId_ = new long[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setReqId : invalid array index -> " + iIndex);
        }
                    
        this.reqId_[iIndex] = plReqId;
    }

    public long getReqId(int iIndex) throws Exception {
        
        if(reqId_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getReqId : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getReqId : array not initialized");
        }
        
        return reqId_[iIndex];
    }   

    public void setParamFld(int iIndex, String pstrParamFld) throws Exception {
        checkSize("Param Fld");
        
        if ( paramFld_ == null) {
            paramFld_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setParamFld : invalid array index -> " + iIndex);
        }
                    
        this.paramFld_[iIndex] = pstrParamFld;
    }

    public String getParamFld(int iIndex) throws Exception {
        
        if(paramFld_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getParamFld : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getParamFld : array not initialized");
        }
        
        return paramFld_[iIndex];
    }   

    public void setParamVal(int iIndex, String pstrParamVal) throws Exception {
        checkSize("Param Fld");
        
        if ( paramVal_ == null) {
            paramVal_ = new String[iSize_];
        }

        if (iIndex >= iSize_) {
            throw new Exception("@setParamVal : invalid array index -> " + iIndex);
        }
                    
        this.paramVal_[iIndex] = pstrParamVal;
    }

    public String getParamVal(int iIndex) throws Exception {
        
        if(paramVal_ != null) {
            if (iIndex >= iSize_) {
                throw new Exception("@getParamFld : invalid array index -> " + iIndex);
            }
        }
        else {
            throw new Exception("@getParamFld : array not initialized");
        }
        
        return paramVal_[iIndex];
    }   

   

    public void reset()
    {
        if (reqId_ != null) reqId_ = null;
        if (paramFld_ != null) paramFld_ = null;
        if (paramVal_ != null) paramVal_ = null;
    }
        
    public void initialize(int iVal)
    {
        reset();
        setSize(iVal);
        reqId_ = new long[iVal];               
        paramFld_ = new String[iVal];        
        paramVal_ = new String[iVal];
		
    }
    
}