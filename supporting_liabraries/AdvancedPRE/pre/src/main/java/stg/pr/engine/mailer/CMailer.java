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
 * $Revision: 3523 $
 * 
 * $Header: http://172.16.209.156:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/mailer/CMailer.java 1406 2010-05-12 23:59:27Z kedar $
 * 
 * $Log: /Utilities/PRE/src/stg/pr/engine/mailer/CMailer.java $
 * 
 * 28    11/18/09 11:45a Kedarr
 * Changes made to add HTML email type.
 * 
 * 27    9/01/09 9:02a Kedarr
 * Changes made to change default policy of discard emails to caller runs
 * policy. Also changes made for incorporating HashMap as this change was
 * done in EMail.
 * 
 * 26    4/08/09 4:14p Kedarr
 * Main method removed from this version.
 * 
 * 25    3/21/09 3:51p Kedarr
 * Changes made to pass the default policy instead of instantiating the
 * same class using class . for name.
 * 
 * 24    3/11/09 5:09p Kedarr
 * Changes made for the implementation of the license file.
 * 
 * 23    2/04/09 12:59p Kedarr
 * File array is being cloned.
 * 
 * 22    10/10/08 9:46a Kedarr
 * Updates to javadoc.
 * 
 * 21    10/08/08 9:56p Kedarr
 * Updated javadoc
 * 
 * 20    10/05/08 9:59a Kedarr
 * Changed the default core threads timeout to 10 minutes.
 * 
 * 19    9/30/08 10:19p Kedarr
 * Changes made to implement EMail and CMailer changes. Also, implemented
 * Thread Pooling and EMailPolicy.
 * 
 * 18    9/15/08 9:58a Kedarr
 * Adding ASynchronous email feature.
 * 
 * 17    4/10/08 1:55p Kedarr
 * New API added. Message headers can now be added to the email message.
 * Most methods are made final. Updated spelling mistakes in Java Doc as
 * well.
 * 
 * 16    3/26/08 4:53p Kedarr
 * Added the REVISION variables to store the configuration management
 * version number of the class.
 * 
 * 15    3/02/07 1:07p Kedarr
 * updated javadoc.
 * 
 * 14    3/02/07 8:56a Kedarr
 * sendMail now checks for the property mailnotification equals to ON.
 * 
 * 13    7/26/05 11:13a Kedarr
 * Updated for JavaDoc for missing tags
 * 
 * 12    6/02/05 3:26p Kedarr
 * Changes made to set the string variables for all types of recepients in
 * their respective set methods.
 * 
 * 11    6/02/05 3:08p Kedarr
 * Commented code for getting defailt TO and CC recepients if set to null
 * or "".
 * 
 * 10    6/02/05 2:03p Kedarr
 * Changes made to check for isDebugEnabled and then log debug message.
 * 
 * 9     6/01/05 12:43p Kedarr
 * Changed the toString() method implementation.
 * 
 * 8     6/01/05 11:58a Kedarr
 * Added debug messages as well as changed the name from CMailer.class to
 * Mailer.
 * 
 * 7     5/31/05 6:19p Kedarr
 * Changes made for incorporating log4J logger.
 * 
 * 6     2/01/05 5:32p Kedarr
 * Adding a new method setAttachment(File file)
 * 
 * 5     1/25/05 10:38a Kedarr
 * Changes for InternetAddress
 * 
 * 4     1/19/05 4:36p Kedarr
 * Version Header change
 * 
 * 3     1/19/05 3:11p Kedarr
 * Advanced PRE
 * Revision 1.4  2006/02/17 07:05:05  kedar
 * Changes made for logging messages.
 *
 * Revision 1.3  2006/02/09 08:56:55  kedar
 * Bug resolved in the setAttachments(String[] files) method. This method was
 * always generating NullPointerException.
 *
 * Revision 1.2  2005/11/04 07:11:02  kedar
 * Null check added for fileArray_ variable. If and only if it is not null and the
 * length of the array is greater than ZERO  then only attach files.
 *
 * Revision 1.1  2005/11/03 04:54:42  kedar
 * *** empty log message ***
 *
 * 
 * 1 4/12/04 4:21p Kedarr Revision 1.1 2004/02/06 10:34:01 kedar New Class for
 * mailing messages.
 * 
 *  
 */
package stg.pr.engine.mailer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import stg.pr.engine.CProcessRequestEngine;
import stg.utils.CSettings;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IQueue;
import com.stg.logger.LogLevel;

/**
 * An abstract Mailer class.
 * 
 * This class provides all the basic methods to email a message. The actual transportation of the email
 * is left unimplemented. EMail can be transported in two ways namely Synchronous email {@link #sendMail(EMail)}
 * and {@link #sendAsyncMail(EMail)}. The Mailer uses thread pools to send asynchronous email. If all the the threads in
 * the thread pool are busy then subsequent emails are queued. In case the PRE is being shutdown these queued email messages 
 * will be applied with {@link EMailPolicy}. This policy defines actions for serializing the queue. When PRE is started based on 
 * the implementation of this policy serialized email messages can be re-loaded and transported to the underlying email server.
 * 
 * An object of {@link EMail} once transported cannot be transported again. Such email messages are identified as SPAM. These SPAM
 * emails are logged as warning. 
 * 
 * @author Kedar C. Raybagkar
 * @version $Revision: 3523 $
 */
public abstract class CMailer {

    /**
     * Stores the REVISION number of the class from the configuration management
     * tool.
     */
    public static final String REVISION = "$Revision:: 3523              $";

    /**
     * The Identifier for the mails with normal priority.
     * 
     * Comment for <code>NORMAL</code>
     */
    public static final String NORMAL = "normal.";

    /**
     * The identifier for the mails with Critical priority.
     * 
     * Comment for <code>CRITICAL</code>
     */
    public static final String CRITICAL = "critical.";

//    /**
//     * BCC Recepient are stored in this variable.
//     * 
//     * Comment for <code>addrBCCRecepient_</code>
//     */
//    private Address[] addrBCCRecepient_;

    /**
     * Subject of the mail is stored in this variable.
     * 
     * Comment for <code>strSubject_</code>
     */
    private String strSubject_;

    /**
     * Message Body Header is stored in this variable.
     * 
     * Comment for <code>strMessageBodyHeader_</code>
     */
    private String strMessageBodyHeader_;
    

    /**
     * Message Body Footer is stored in this variable.
     * 
     * Comment for <code>strMessageBodyFooter_</code>
     */
    private String strMessageBodyFooter_;

    /**
     * Logger instance variable.
     * 
     * Comment for <code>logger_</code>
     */
    private transient Logger logger_;

    /**
     * Stores the message header key pair values.
     */
    private Map<String,String> mapMessageHeader_;

    /**
     * Static instance of the CMailer.
     */
    private static CMailer instance_;

    /**
     * 
     */
    private ThreadPoolExecutor executor_;
    
    /**
     * 
     */
    private IQueue<Runnable> emailQueue;

    /**
     * Constructs a default Mailer.
     * @throws AddressException 
     */
    protected CMailer() {
        super();
        logger_ = Logger.getLogger("Mailer");
        emailQueue = Hazelcast.getQueue("emailQueue");
        executor_ = new ThreadPoolExecutor(1, 1, 10, TimeUnit.MINUTES, emailQueue);
//                new LinkedBlockingQueue());
        executor_.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor_.allowCoreThreadTimeOut(true);
        try {
            setDefaultValues();
        } catch (AddressException e) {
            throw new IllegalArgumentException("Property mail.senderAddress is not valid");
        }
    }

    /**
     * Returns the CMailer. 
     * Depending on the mailer type a appropriate mailer is returned. Defaults to SMTP mailer.
     * 
     * @param strType
     *            Mailer Type
     * @return CMailer
     */
    public synchronized static CMailer getInstance(String strType) {
//        if (CSettings.get("pr.mailnotification", "ON").equals("ON")) { //To be activated in the next release.
            if (instance_ == null) {
                    instance_ = new CSMTPMailer();
            }
//        }
        return instance_;
    }

    /**
     * Load Serialized email messages and Transport to the underlying server.
     * @param engine 
     */
    public synchronized final void loadAndTransportSerializedEmails(CProcessRequestEngine engine) {
        String strClass = CSettings.get("mail.emailPolicy", "stg.pr.engine.mailer.DiscardEMailPolicy");
        if (strClass == null || "".equals(strClass)) {
            strClass = "stg.pr.engine.mailer.DiscardEMailPolicy";
        }
        EMailPolicy policy;
        try {
            policy = instantiateEmailPolicy(strClass);
            if (policy != null) {
                EMail[] emails = policy.loadEmails();
                if (emails != null) {
                    if (logger_.isInfoEnabled()) {
                        logger_.info(emails.length + " emails loaded. Queueing emails..");
                    }
                    for (int i = 0; i < emails.length; i++) {
                        instance_.sendAsyncMail(emails[i]);
                    }
                    if (logger_.isInfoEnabled()) {
                        logger_.info("Emails queued for tranport.");
                    }
                } else {
                    logger_.log(LogLevel.NOTICE, "Zero emails loaded.");
                }
            }
        } catch (ClassNotFoundException e) {
            logger_.fatal("Unable to load the EMailPolicy " + strClass, e);
            logger_.log(LogLevel.NOTICE, "EMails are not loaded." );
        } catch (InstantiationException e) {
            logger_.fatal("Unable to instantiate associated EMailPolicy " + strClass, e);
            logger_.log(LogLevel.NOTICE, "EMails are not loaded." );
        } catch (IllegalAccessException e) {
            logger_.fatal("IllegalAccessException generated while loading " + strClass, e);
            logger_.log(LogLevel.NOTICE, "EMails are not loaded." );
        } catch (IOException e) {
            logger_.fatal("Unable to de-serialize the EMails ", e);
            logger_.log(LogLevel.NOTICE, "EMails are not loaded." );
        }
    }

    /**
     * Specific method for sending mails related to Monitor Thread of PRE.
     * 
     * @param pstrMailType
     *            Mail Type {@link #NORMAL}or {@link #CRITICAL}
     * @param pstrSubject
     *            Subject of the mail.
     * @param pstrMessage
     *            Message of the mail.
     */
    public static void sendMonitorMail(String pstrMailType, String pstrSubject,
            String pstrMessage) {
        // CMailer mailer = CMailer.getInstance("SMTP");
        EMail emailmessage = new EMail();
        Logger logger = Logger.getLogger("Mailer");
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("reply-to", CSettings.get("mail.reply-to", ""));
            emailmessage.setMessageHeader(map);
            StringBuffer sbuffer = new StringBuffer(100);

            sbuffer.append("mail.monitor.");
            sbuffer.append(pstrMailType);
            String strMailType = sbuffer.toString();
            sbuffer.append("recepientTO");
            emailmessage.setTORecipient(CSettings.get(sbuffer.toString()));

            sbuffer.delete(strMailType.length(), sbuffer.length());
            sbuffer.append("recepientCC");
            emailmessage.setCCRecipient(CSettings.get(sbuffer.toString()));

            sbuffer.delete(strMailType.length(), sbuffer.length());
            sbuffer.append("defaultsubject");
            emailmessage.setSubject(CSettings.get(sbuffer.toString(),
                    "defaultsubject")
                    + " " + pstrSubject);

            sbuffer.delete(strMailType.length(), sbuffer.length());
            sbuffer.append("defaultmessageheader");
            emailmessage.setMessageBodyHeader(CSettings.get(sbuffer.toString(),
                    "defaultmessageheader"));

            emailmessage.setMessageBody(pstrMessage);

            sbuffer.delete(strMailType.length(), sbuffer.length());
            sbuffer.append("defaultmessagefooter");
            emailmessage.setMessageBodyFooter(CSettings.get(sbuffer.toString(),
                    "defaultmessagefooter"));

            CMailer.getInstance("SMTP").sendAsyncMail(emailmessage);
        } catch (MessagingException me) {
            logger.error(me.getMessage(), me);
        } catch (RuntimeException re) {
            logger.error(re.getMessage(), re);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
    }

    /**
     * Sends the mail in a synchronous manner.
     * 
     * This method calls the abstract {@link #transportMail(EMail)}method for
     * transporting the mail to mail server. If you use the same EMail message for transporting the second time
     * then PRE will identify this message as a SPAM and will not allow it to be transported. The caller will wait
     * till the email is transported to the email server for delivery.
     * 
     * @param email 
     * @throws MessagingException
     */
    public final void sendMail(final EMail email) throws MessagingException {
        if (CSettings.get("pr.mailnotification", "ON").equals("ON")) {
            transportMail(email);
        } else {
            if (logger_.isInfoEnabled()) {
                logger_
                        .info("E-Mail notification is off. Email not Transported.");
            }
        }
    }

    /**
     * Sends the mail in an asynchronous manner.
     * 
     * This method calls the abstract {@link #transportMail(EMail)}method for
     * transporting the mail to mail server. If the caller uses the same EMail object for a second delivery then PRE 
     * will identify that call as a SPAM attempt and will not allow it to be transported. In this case, unlike {@link #sendMail(EMail)}
     * caller will be allowed to proceed further execution. PRE guarantees that the asynchronous email(s) will be transported provided 
     * the email server itself is not having issues or the configuration of mail properties are not incorrect.
     * 
     * Any MessagingException caught will be logged and will not be thrown.
     * 
     * @param email 
     */
    public final void sendAsyncMail(final EMail email) {
        if (CSettings.get("pr.mailnotification", "ON").equals("ON")) {
        	for (EMailAttachment attachment :email.getAttachments()) {
        		if (!(attachment instanceof SmallEmailAttachment)) {
        			throw new IllegalArgumentException("Emails with small attachments can be transported using the Async method");
        		}
        	}
            executor_.execute(new AsynchronousEMailProcess(email));
        } else {
            if (logger_.isInfoEnabled()) {
                logger_
                        .info("E-Mail notification is off. Email not transported. " + email.toString());
            }
        }
    }

    /**
     * This is an abstract method that is responsible to send or transport the
     * mail to the mail server. This method should create a new mail session,
     * set the recipients, attach any files supplied and should transport the
     * mail to the mail server.
     * 
     * @param eMail 
     * @throws MessagingException
     */
    protected abstract void transportMail(EMail eMail)
            throws MessagingException;


//    /**
//     * Currently Not-In-Use.
//     * 
//     * @return Address BCC Recepient.
//     */
//    public final Address[] getBCCRecepient() {
//        return addrBCCRecepient_;
//    }

    /**
     * Returns the default message body footer of the email.
     * 
     * @return String Message Body Footer.
     */
    public String getDefaultMessageBodyFooter() {
        return strMessageBodyFooter_;
    }

    /**
     * Returns the default email's message body header.
     * 
     * @return Returns the Message Body Header.
     */
    public String getDefaultMessageBodyHeader() {
        return strMessageBodyHeader_;
    }

    /**
     * Returns the subject of the email message. If the subject is not set or
     * set to null then the default value for the subject is picked from the
     * mail properties. Property <i>mail.defaultsubject </i>.
     * 
     * @return Returns the Subject.
     */
    public String getDefaultSubject() {
        return strSubject_;
    }

    /**
     * Returns the default e-mail message header.
     * 
     * @return map
     */
    public Map<String, String> getDefaultMessageHeader() {
        return mapMessageHeader_;
    }

    /**
     * Sender address stored in this variable. Comment for
     * <code>addrSender_</code>.
     */
    private Address addrSender_ = null;

//    /**
//     * Stores the license info in the form of a TEXT.
//     */
//    private String defaultTEXTLicenseInfo_;
//    
//    /**
//     * Stpres the license info in the form of an HTML.
//     */
//    private String defaultHTMLLicenseInfo_;

	/**
	 * HTML message header start tags.
	 */
	private String htmlMessageHeaderStartTags;

	/**
	 * HTML message header end tags.
	 */
	private String htmlMessageHeaderEndTags;

	/**
	 * HTML message footer start tags.
	 */
	private String htmlMessageFooterStartTags;

	/**
	 * HTML message footer end tags.
	 */
	private String htmlMessageFooterEndTags;

    /**
     * Returns the FROM address.
     * 
     * @return Sender's address.
     * @throws AddressException
     */
    public final Address getDefaultSender() throws AddressException {
        return addrSender_;
    }
    
    /**
     * Shutdowns the mailer.
     * Once shutdown the only way an email can be sent is through the sendMail();
     * @param engine
     */
    public final void shutdown(CProcessRequestEngine engine) {
        if (engine == null) {
            throw new NullPointerException();
        }
        if (logger_.isInfoEnabled()) {
            logger_.info("Shuting down the mailer");
        }
        List<?> list = executor_.shutdownNow();
        EMail[] emails = new EMail[list.size()];
        int i=0;
        for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            if (obj instanceof AsynchronousEMailProcess) {
                AsynchronousEMailProcess emailProcess = (AsynchronousEMailProcess) obj; 
                emails[i++] = emailProcess.getUnderlyingEmail();
            }
        }
        if (emails.length > 0) {
            String strClass = CSettings.get("mail.emailPolicy", "stg.pr.engine.mailer.DiscardEMailPolicy");
            if (strClass == null || "".equals(strClass)) {
                strClass = "stg.pr.engine.mailer.DiscardEMailPolicy";
            }
            try {
                if (logger_.isInfoEnabled()) {
                    logger_.info(emails.length + " emails could not be transported due to shutdown. Applying policy " + strClass);
                }
                EMailPolicy policy = instantiateEmailPolicy(strClass);
                if (policy != null) {
                    if (logger_.isInfoEnabled()) {
                        logger_.info("Please wait while policy #" + strClass + " is being applied.");
                    }
                    policy.serializeEmails(emails);
                    if (logger_.isInfoEnabled()) {
                        logger_.info("Policy " + strClass + "applied successfully.");
                    }
                } else {
                    logger_.fatal("Invalid Configuration. Class " + strClass + " does not implement stg.pr.engine.mailer.EMailPolicy");
                    logger_.log(LogLevel.NOTICE, "Applying default policy. Emails will be discarded.");
                }
            } catch (ClassNotFoundException e) {
                logger_.fatal("Unable to load the EMailPolicy " + strClass, e);
                logger_.log(LogLevel.NOTICE, "Applying default policy. Emails will be discarded." );
            } catch (InstantiationException e) {
                logger_.fatal("Unable to instantiate associated EMailPolicy " + strClass, e);
                logger_.log(LogLevel.NOTICE, "Applying default policy. Emails will be discarded." );
            } catch (IllegalAccessException e) {
                logger_.fatal("IllegalAccessException generated while loading " + strClass, e);
                logger_.log(LogLevel.NOTICE, "Applying default policy. Emails will be discarded." );
            } catch (IOException e) {
                logger_.fatal("Unable to apply policy " + strClass, e);
                logger_.log(LogLevel.NOTICE, "Applying default policy. Emails will be discarded." );
            }
        } else {
            if (logger_.isInfoEnabled()) {
                logger_.info("Zero emails pending in the Queue.");
            }
        }
        if (logger_.isInfoEnabled()) {
            logger_.info("Waiting for any current threads to transport emails, if any...");
        }
        while (executor_.getActiveCount() > 0) {
            //wait to complete all the active count
        }
        if (logger_.isInfoEnabled()) {
            logger_.info("Mailer shutdown complete.");
        }
    }
    
    
    /**
     * Loads and instantiate {@link EMailPolicy}.
     * 
     * @param strClass name of the class of type {@link EMailPolicy}
     * @return EMailPolicy
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public final EMailPolicy instantiateEmailPolicy(String strClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (strClass.equals("stg.pr.engine.mailer.DiscardEMailPolicy")) {
            return new DiscardEMailPolicy();
        }
        Class<?> c = this.getClass().getClassLoader().loadClass(strClass);
        Object obj = c.newInstance();
        if (obj instanceof EMailPolicy) {
            return (EMailPolicy) obj;
        } else {
            return null;
        }
    }
    
    /**
     * Sets all default values.
     * @throws AddressException 
     */
    private void setDefaultValues() throws AddressException{
        if (mapMessageHeader_ == null || mapMessageHeader_.size() == 0) {
            mapMessageHeader_ = new HashMap<String, String>();
            mapMessageHeader_.put("reply-to", CSettings.get("mail.reply-to",
                    "no-reply-please"));
        }
        if (strMessageBodyFooter_ == null || strMessageBodyFooter_.equals("")) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Setting default message footer");
            }
            strMessageBodyFooter_ = CSettings.get("mail.defaultmessagefooter");
        }
        if (strMessageBodyHeader_ == null || strMessageBodyHeader_.equals("")) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Getting default message header");
            }
            strMessageBodyHeader_ = CSettings.get("mail.defaultmessageheader");
        }
        if (addrSender_ == null) {
            addrSender_ = new InternetAddress(CSettings.get("mail.senderaddress")); 
        }
        if (strSubject_ != null) {
            if (logger_.isDebugEnabled()) {
                logger_.debug("Getting default mail subject");
            }
            strSubject_ = CSettings.get("mail.defaultsubject", "");
        }
        htmlMessageHeaderStartTags = CSettings.get("mail.htmlmessageheaderstarttags", "<html><body><font size=\"2\">");
        htmlMessageHeaderEndTags = CSettings.get("mail.htmlmessageheaderendtags", "</font><hr width=\"100%\" size=\"3\"></body></html>");
        htmlMessageFooterStartTags = CSettings.get("mail.htmlmessagefooterstarttags", "<html><body><font size=\"2\">");
        htmlMessageFooterEndTags = CSettings.get("mail.htmlmessagefooterendtags", "<html><body><font size=\"2\">");

//        LicenseContent licContent = null;
//        try {
//            licContent = (new LicenseManager(new LicenseParamImpl())).verify();
//        } catch (IllegalPasswordException e) {
//        } catch (NullPointerException e) {
//        } catch (Exception e) {
//            licContent = null;
//        }
//        boolean bPrintLicenseInfo = true;
//        if (licContent != null) {
//            Object obj = licContent.getExtra();
//            if (obj != null) {
//                if (obj instanceof Properties) {
//                    Properties properties = (Properties) obj;
//                    bPrintLicenseInfo = false;
//                    if (! properties.getProperty("licenseType", "DEMO").equalsIgnoreCase("prod") ) {
//                        defaultTEXTLicenseInfo_ = "\n\nLicensed  To: " + licContent.getHolder().getName() +
//                        "\nLicense Type: " + licContent.getInfo() + 
//                        "\nValid  Up To: " + licContent.getNotAfter() +
//                        "\nIssued    By: " + licContent.getIssuer().getName();
//                        StringBuffer buffer = new StringBuffer();
//                        buffer.append(htmlMessageFooterStartTags);
//                		buffer.append("<TABLE>");
//                		buffer.append("<TR><TD> Process Request Engine License Information</font></TD></TR>");
//                        buffer.append("<TR><TD><FONT size=2><i>Licensed To : " + licContent.getHolder().getName() + "</i></FONT></TD></TR>");
//                        buffer.append("<TR><TD><FONT size=2><i>License Type: " + licContent.getInfo() + "</i></FONT></TD></TR>");
//                        buffer.append("<TR><TD><FONT size=2><i>Valid  Up To: " + licContent.getNotAfter() + "</i></FONT></TD></TR>");
//                        buffer.append("<TR><TD><FONT size=2><i>Issued By   : " + licContent.getIssuer().getName() + "</i></FONT></TD></TR>");
//                        buffer.append(htmlMessageFooterEndTags);
//                        defaultHTMLLicenseInfo_ = buffer.toString();
//                    } else {
//                        defaultTEXTLicenseInfo_ = null;
//                        defaultHTMLLicenseInfo_ = null;
//                    }
//                } else {
//                	bPrintLicenseInfo = true;
//                }
//            } else {
//            	bPrintLicenseInfo = true;
//            }
//        } else {
//        	bPrintLicenseInfo = true;
//        }
//        if (bPrintLicenseInfo) {
//            StringBuffer buffer = new StringBuffer();
//            buffer.append(htmlMessageFooterStartTags);
//    		buffer.append("<TABLE>");
//    		buffer.append("<TR><TD align=\"center\" bgcolor=\"eb922c\" width=\"20%\"><font color=\"#ffffff\">Process Request Engine License Information</font></TD></TR>");
//        	buffer.append("<TR bgcolor=\"#000000\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\" width=\"70%\"><TD><FONT size=2><i>Demo version! Not for production use. License Information could not be located. Illegal usage.</i></FONT></TD></TR>");
//        	buffer.append("</TABLE>");
//        	buffer.append(htmlMessageFooterEndTags);
//            defaultHTMLLicenseInfo_ = buffer.toString();
//            defaultTEXTLicenseInfo_ = "Demo version! Not for production use. License Information could not be located. Illegal usage.";
//        }
    }
    
    
//    /**
//     * Returns the license information based on the type of the email {@link stg.pr.engine.mailer.EMail.TYPE#HTML} or {@link stg.pr.engine.mailer.EMail.TYPE#TEXT}
//     * @param type
//     * @return String
//     */
//    protected final String getLicenseInfo(EMail.TYPE type) {
//    	switch (type) {
//		case HTML:
//			return defaultHTMLLicenseInfo_;
//		default:
//			return defaultTEXTLicenseInfo_;
//		}
//    }
    
    /**
     * Returns the HTML message header start tags. 
     * @return String
     */
    public String getHtmlMessageHeaderStartTags() {
    	return htmlMessageHeaderStartTags;
    }
    
    /**
     * Returns the HTML message header end tags. 
     * @return String
     */
    public String getHtmlMessageHeaderEndTags() {
    	return htmlMessageHeaderEndTags;
    }

    /**
     * Returns the HTML message footer start tags. 
     * @return String
     */
    public String getHtmlMessageFooterStartTags() {
    	return htmlMessageFooterStartTags;
    }
    
    /**
     * Returns the HTML message footer end tags. 
     * @return String
     */
    public String getHtmlMessageFooterEndTags() {
    	return htmlMessageFooterEndTags;
    }
}