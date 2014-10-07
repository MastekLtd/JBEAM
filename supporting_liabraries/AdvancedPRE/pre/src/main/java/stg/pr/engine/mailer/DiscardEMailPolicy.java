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
 * $Revision: 2774 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/mailer/DiscardEMailPolicy.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/mailer/DiscardEMailPolicy.java $
 * 
 * 2     2/04/09 1:01p Kedarr
 * Added static keyword to a final variable.
 * 
 * 1     9/30/08 9:58p Kedarr
 * A default implementation of the new interface EMailPolicy.
 * 
 */
package stg.pr.engine.mailer;

import java.io.IOException;

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
    public static final String REVISION = "$Revision:: 2774              $";
    
    /* (non-Javadoc)
     * @see stg.pr.engine.mailer.EMailPolicy#loadEmails()
     */
    public EMail[] loadEmails() throws IOException {
        return new EMail[0];
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.mailer.EMailPolicy#serializeEmails(stg.pr.engine.mailer.EMail[])
     */
    public void serializeEmails(EMail[] emails) throws IOException {
        //do nothing.
    }

}
