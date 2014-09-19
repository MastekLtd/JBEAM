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
package com.stgmastek.monitor.mailer;

import java.io.IOException;

import stg.pr.engine.mailer.EMail;
import stg.pr.engine.mailer.EMailPolicy;

/**
 * A default implementation of the {@link EMailPolicy}.
 *
 * Does nothing. Just discards the emails in the queue.
 *
 * @author Kedar Raybagkar
 * @since  V1.0R25.00.xxx
 */
public class DiscardEMailPolicy implements EMailPolicy {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 1                 $";
    
    /* (non-Javadoc)
     * @see com.stgmastek.monitor.mailer.EMailPolicy#loadEmails()
     */
    public EMail[] loadEmails() throws IOException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.stgmastek.monitor.mailer.EMailPolicy#serializeEmails(com.stgmastek.monitor.mailer.EMail[])
     */
    public void serializeEmails(EMail[] emails) throws IOException {
        //do nothing.
    }

}
