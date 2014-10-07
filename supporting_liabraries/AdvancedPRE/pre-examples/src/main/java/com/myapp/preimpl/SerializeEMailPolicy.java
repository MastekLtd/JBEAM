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
 * $Header: /Utilities/PRE/example/com/myapp/preimpl/SerializeEMailPolicy.java 2     3/11/09 6:13p Kedarr $
 *
 * $Log: /Utilities/PRE/example/com/myapp/preimpl/SerializeEMailPolicy.java $
 * 
 * 2     3/11/09 6:13p Kedarr
 * Added a warning message if the file could not be deleted.
 * 
 * 1     9/30/08 10:31p Kedarr
 * New class added to demonstrate the EMailPolicy.
 * 
 */
package com.myapp.preimpl;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import stg.pr.engine.mailer.EMail;
import stg.pr.engine.mailer.EMailPolicy;
import stg.pr.engine.serialize.Serializer;

/**
 * Sample class to serializes and de-serialize the e-mails.
 * 
 * Define a system property named email.store that points to a physical directory.
 *
 * @author Kedar Raybagkar
 * @since  V1.0R25.00.xxx
 */
public class SerializeEMailPolicy implements EMailPolicy {

    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 2382              $";


    /* (non-Javadoc)
     * @see stg.pr.engine.mailer.EMailPolicy#loadEmails()
     */
    public EMail[] loadEmails() throws IOException, ClassNotFoundException {
        //load the file in which the mails are serialized.
        Serializer serializer = new Serializer();
        File file = getEmailStore();
        if (file == null) {
            return null;
        }
        if (!file.exists()) return null;
        if (!file.canWrite()) {
            throw new IOException("Write permission needed on file " + file.getName());
        }
        try {
            return (EMail[]) serializer.load(file);
        } catch (InvalidKeyException e) {
            throw new IOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        } catch (NoSuchProviderException e) {
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            throw new IOException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IOException(e);
        }
        
    }

    /* (non-Javadoc)
     * @see stg.pr.engine.mailer.EMailPolicy#serializeEmails(stg.pr.engine.mailer.EMail[])
     */
    public void serializeEmails(EMail[] emails) throws IOException {
        File file = getEmailStore();
        Serializer serializer = new Serializer();
        try {
            serializer.serialize(emails, file);
        } catch (InvalidKeyException e) {
            throw new IOException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        } catch (NoSuchProviderException e) {
            throw new IOException(e);
        } catch (NoSuchPaddingException e) {
            throw new IOException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new IOException(e);
        }
    }
    
    /**
     * Returns the email store file.
     * @return File
     */
    private File getEmailStore() {
        String strEmailStore = System.getProperty("email.store");
        if (strEmailStore == null) {
            return null;
        }
        File file = new File(strEmailStore);
        if (file.isDirectory()) {
            return null;
        }
        return file;
    }

}
