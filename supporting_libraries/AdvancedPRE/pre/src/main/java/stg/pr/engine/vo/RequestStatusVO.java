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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/vo/RequestStatusVO.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log:  $
 *
 */
package stg.pr.engine.vo;

import java.io.Serializable;

/**
 * A simple value object that stores the request id and the status.
 * 
 * This object is used for storing requests that are being currently executed
 * along with the request status. This is then used by the Passive PRE in case
 * of fail-over.
 * 
 * @author Kedar Raybagkar
 * @since V1.0R28.x
 * 
 */
public class RequestStatusVO implements Serializable {

    /**
     * Stores the serial version number.
     */
    private static final long serialVersionUID = 1011863418173715195L;

    /**
     * Stores the request id.
     */
    private long reqId;

    /**
     * Stores the status.
     */
    private String status;

    /**
     * Sets the given request id.
     * 
     * @param reqId
     */
    public final void setReqId(final long reqId) {
        this.reqId = reqId;
    }

    /**
     * Returns the request id.
     * 
     * @return long
     */
    public final long getReqId() {
        return reqId;
    }

    /**
     * Stores the request status.
     * 
     * @param status
     */
    public final void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Returns the request status.
     * 
     * @return String
     */
    public final String getStatus() {
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof RequestStatusVO) {
            RequestStatusVO other = (RequestStatusVO) obj;
            return (other.getReqId() == this.reqId);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return (Long.valueOf(reqId)).hashCode();
    }
}
