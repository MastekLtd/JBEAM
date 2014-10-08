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
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/mailer/EMailPolicy.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/mailer/EMailPolicy.java $
 * 
 * 2     2/04/09 1:03p Kedarr
 * Added static keyword to a final variable.
 * 
 * 1     9/30/08 9:58p Kedarr
 * A new interface that defines the EMailPolicy for serializing and
 * loading serialized EMail messages.
 * 
 */
package stg.pr.engine.mailer;

import java.io.IOException;

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
    public static final String REVISION = "$Revision:: 2382              $";
    
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
