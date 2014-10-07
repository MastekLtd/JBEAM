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
 */
package stg.pr.engine;

import stg.pr.beans.ProcessRequestEntityBean;
import stg.pr.engine.IProcessRequest.REQUEST_STATUS;

/**
 * Request Status Listener interface. The implementation must handle concurrency. Single instance of this interface is loaded and used multiple times.
 * 
 * @author kedar460043
 * 
 */
public interface RequestStatusListener {

    /**
     * This method is invoked by the engine upon every status change of the request.
     * 
     * @param requestId
     *            the process id
     * @param status
     *            changed status.
     */
    public void statusChanged(ProcessRequestEntityBean bean, REQUEST_STATUS status);

    /**
     * Shutdown is invoked when the PRE stop request comes.
     */
    public void shutdown();

}
