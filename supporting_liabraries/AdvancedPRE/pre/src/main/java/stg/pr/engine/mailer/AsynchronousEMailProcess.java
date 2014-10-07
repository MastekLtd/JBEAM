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
 * $Revision:  $
 *
 * $Header:   $
 *
 * $Log:    $
 *
 */
package stg.pr.engine.mailer;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import stg.utils.ILabelNameSize;

import com.hazelcast.core.Hazelcast;

/**
 * Class is used for executing the e-mails in an asynchronous manner.
 *
 *
 * @author Kedar Raybagkar
 * @since
 */
class AsynchronousEMailProcess implements Runnable, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3730504938608328458L;
    
    private EMail email_;
    
    private transient static final Logger logger_ = Logger.getLogger(AsynchronousEMailProcess.class);
    

    /**
     * @param email
     */
    public AsynchronousEMailProcess(EMail email) {
        email_ = email;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Sending Asynchronous Email ..." + Hazelcast.getQueue(ILabelNameSize.QUEUE).getLocalQueueStats().toString());
            }
            CMailer mailer = CMailer.getInstance("SMTP");
            mailer.sendMail(email_);
            try {
				TimeUnit.MINUTES.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } catch (MessagingException e) {
            logger_.error("Error caught while sending Async email",
                    e);
        }
    }
    
    /**
     * Returns the underlying email message.
     * @return EMail
     */
    public EMail getUnderlyingEmail() {
        return email_;
    }
}
