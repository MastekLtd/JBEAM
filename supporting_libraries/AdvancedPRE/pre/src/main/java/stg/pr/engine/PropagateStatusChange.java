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
 * Propagates the request status change to the listener.
 * 
 * @author kedar460043
 * 
 */
public class PropagateStatusChange implements Runnable {

    private final REQUEST_STATUS status;
    private final RequestStatusListener listener;
    private final ProcessRequestEntityBean bean;

    public PropagateStatusChange(final ProcessRequestEntityBean bean, final REQUEST_STATUS status, final RequestStatusListener listener) {
        this.bean = (ProcessRequestEntityBean) bean.clone();
        this.status = status;
        this.listener = listener;
    }

    @Override
    public void run() {
        listener.statusChanged(bean, status);
    }

}
