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

/**
 * Defines the Policy for serializing and de-serializing e-mails.
 * 
 * Note that this class is loaded only once and any change to the underlying implementation
 * will need a re-start of the PRE.
 * 
 * @author Kedar Raybagkar
 * @since  V1.0R25.00.xxx
 */
public interface EMailPolicy {
    
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 1                 $";
    
    /**
     * Method will be called when the CMailer instance is created.
     * 
     * It is expected that this method will de-serialize and load the email messages.
     * This method is responsible to delete the store once the e-mails are loaded, otherwise
     * the email will be transported every time the PRE starts.
     * 
     * @return EMail[] e-mail messages.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public EMail[] loadEmails() throws IOException, ClassNotFoundException;
    
    /**
     * Serialize the EMail messages.
     * 
     * @param emails {@link EMail}[] to be serialized.
     * @throws IOException
     */
    public void serializeEmails(EMail[] emails) throws IOException;

}
