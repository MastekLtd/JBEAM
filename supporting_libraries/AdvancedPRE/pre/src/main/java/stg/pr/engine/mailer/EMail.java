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
 * $Revision: 3372 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/mailer/EMail.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log: /Utilities/PRE/src/stg/pr/engine/mailer/EMail.java $
 * 
 * 6     11/18/09 11:45a Kedarr
 * Changes made to add HTML email type.
 * 
 * 5     9/01/09 8:29a Kedarr
 * Changed the map to HashMap as this implements serializable.
 * 
 * 4     8/23/09 1:17p Kedarr
 * Changes made to add methods to set the sender email id.
 * 
 * 3     2/04/09 1:03p Kedarr
 * Changed the code to avoid null pointer exception being thrown from the
 * program where it is not necessary.
 * 
 * 2     10/05/08 9:59a Kedarr
 * Updated the toString method.
 * 
 * 1     9/30/08 9:57p Kedarr
 * A new class that stores all the email attributes.
 * 
 */
package stg.pr.engine.mailer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.mail.Address;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * EMail Message.
 *
 * Set all the attributes of the EMail message and then use the CMailer functionality to send emails.
 *
 * @author Kedar Raybagkar
 * @since V1.0R25.00.x  
 */
public class EMail implements Serializable {

	public static enum TYPE {
		HTML, TEXT;
	}
    /**
     * Stores the REVISION number of the class from the configuration management tool. 
     */
    public static final String REVISION = "$Revision:: 3372              $";
    
    /**
     * 
     */
    private static final long serialVersionUID = -7707830337841609133L;

    
    /**
     * Default constructor.
     * The default values are by default applied.
     * @see #applyDefaultValues(boolean)
     */
    public EMail() {
        applyDefaultValues(true);
    }
    

    /**
     * To recipients are stored in String format.
     * Comment for <code>strTORecipient_</code>
     */
    private String strTORecipient_ = null;

    /**
     * CC Recipients are stored in this variable.
     * 
     * Comment for <code>addrCCRecipient_</code>
     */
    private Address[] addrCCRecipient_ = null;
    
    /**
     * CC Recipient are stored in String format.
     * Comment for <code>strCCRecipient_</code>
     */
    private String strCCRecipient_ = null;

    /**
     * BCC Recipient are stored in this variable.
     * 
     * Comment for <code>addrBCCRecipient_</code>
     */
    private Address[] addrBCCRecipient_ = null;
    
    
    /**
     * BCC Recipients are stored in String format.
     * Comment for <code>strBCCRecipient_</code>
     */
    private String strBCCRecipient_ = null;

    /**
     * Subject of the mail is stored in this variable.
     * 
     * Comment for <code>strSubject_</code>
     */
    private String strSubject_ = null;

    /**
     * Message Body Header is stored in this variable.
     * 
     * Comment for <code>strMessageBodyHeader_</code>
     */
    private String strMessageBodyHeader_ = null;

    /**
     * Message Body is stored in this variable.
     * 
     * Comment for <code>strMessageBody_</code>
     */
    private String strMessageBody_ = null;

    /**
     * Message Body Footer is stored in this variable.
     * 
     * Comment for <code>strMessageBodyFooter_</code>
     */
    private String strMessageBodyFooter_ = null;

    /**
     * To Recipients are stored in this variable. Comment for
     * <code>addrTORecipient_</code>
     */
    private Address[] addrTORecipient_ = null;
    

    /**
     * Stores the message header key pair values.
     */
    private HashMap<String, String> mapMessageHeader_ = null;

    /**
     * By default the value is set to true.
     */
    private boolean bDefaultValuesToBeApplied = true;

    /**
     * Flag to avoid SPAM.
     */
    private AtomicBoolean bEmailTransported_ = new AtomicBoolean(false);
    
    /**
     * Stores the unique email id.
     */
    private String strEmailId_;
    
    /**
     * Stores the EMailAttachment.
     */
    private ArrayList<EMailAttachment> attachments = new ArrayList<EMailAttachment>();

    /**
     * Currently Not-In-Use.
     * 
     * @return Address BCC Recipient.
     */
    public final Address[] getBCCRecipient() {
        if (addrBCCRecipient_ == null) {
            return new Address[0];
        }
        return (Address[]) addrBCCRecipient_.clone();
    }

    /**
     * Currently Not-In-Use.
     * 
     * @param pstrBCCRecipient
     *            String Store BCC Recipients.
     * @throws AddressException
     * @throws NullPointerException If the BCC Recipient is null
     */
    public final void setBCCRecipient(String pstrBCCRecipient)
            throws AddressException {
        strBCCRecipient_ = pstrBCCRecipient;
        addrBCCRecipient_ = InternetAddress.parse(strBCCRecipient_);
    }

    /**
     * Returns the Cc Recipients of the email message.
     * 
     * @return Address CC Recipients.
     * @throws AddressException
     */
    public final Address[] getCCRecipient() throws AddressException {
        if (addrCCRecipient_ == null) {
            return new Address[0];
        }
        return (Address[]) addrCCRecipient_.clone();
    }

    /**
     * Sets the CC Recipients.
     * 
     * @param pstrCCRecipient
     *            String A comma separated email ids.
     * @throws AddressException
     */
    public final void setCCRecipient(String pstrCCRecipient) throws AddressException {
        if (pstrCCRecipient == null || "".equals(pstrCCRecipient))
        {
            return;
        }
        addrCCRecipient_ = InternetAddress.parse(pstrCCRecipient);
        strCCRecipient_ = pstrCCRecipient;
    }

    /**
     * Returns the email's message body. The email body is divided into 3 parts
     * namely Body Header, Body and Body Footer.
     * 
     * @return String the Message Body.
     */
    public String getMessageBody() {
        return strMessageBody_;
    }

    /**
     * Sets the email's body header of the mail.
     * 
     * The email body is divided into 3 parts namely Body Header, Body and Body
     * Footer.
     * 
     * @param pstrMessageBody
     *            String Message Body.
     * @see #setMessageBodyHeader(String)
     * @see #setMessageBodyFooter(String)
     */
    public void setMessageBody(String pstrMessageBody) {
        strMessageBody_ = pstrMessageBody;
    }

    /**
     * Returns the body of the email.
     * 
     * If the message body footer is not set or set to null then the default
     * value for the footer is picked from the mail properties. Property
     * <i>mail.defaultmessagefooter </i>.
     * 
     * @return String Message Body Footer.
     */
    public String getMessageBodyFooter() {
        return strMessageBodyFooter_;
    }

    /**
     * Sets the email's body footer of the mail.
     * 
     * The email body is divided into 3 parts namely Body Header, Body and Body
     * Footer. In case the email type is {@link EMail.TYPE#HTML} then the message 
     * body header and footer are not added to the email.
     * 
     * @param pstrMessageBodyFooter
     *            String Message Body Footer.
     * @see #setMessageBodyHeader(String)
     * @see #setMessageBody(String)
     */
    public void setMessageBodyFooter(String pstrMessageBodyFooter) {
        strMessageBodyFooter_ = pstrMessageBodyFooter;
    }

    /**
     * Returns the email's message body header.
     * 
     * @return Returns the Message Body Header.
     */
    public String getMessageBodyHeader() {
        return strMessageBodyHeader_;
    }

    /**
     * Sets the email's body header of the mail.
     * 
     * The email body is divided into 3 parts namely Body Header, Body and Body
     * Footer. In case the email type is {@link EMail.TYPE#HTML} then the message 
     * body header and footer are not added to the email.
     *  
     * @param pstrMessageBodyHeader
     *            String Message Body Footer.
     * @see #setMessageBodyFooter(String)
     * @see #setMessageBody(String)
     */
    public void setMessageBodyHeader(String pstrMessageBodyHeader) {
        strMessageBodyHeader_ = pstrMessageBodyHeader;
    }

    /**
     * Returns the subject of the email message. If the subject is not set or
     * set to null then the default value for the subject is picked from the
     * mail properties. Property <i>mail.defaultsubject </i>.
     * 
     * @return Returns the Subject.
     */
    public String getSubject() {
        return strSubject_;
    }

    /**
     * Sets the subject of the email message.
     * 
     * @param pstrSubject
     *            String the Subject.
     * @see #getSubject()
     */
    public void setSubject(String pstrSubject) {
        strSubject_ = pstrSubject;
    }

    /**
     * Returns the To Recipients of the email message.
     * 
     * @return Address TO Recipients.
     * @throws AddressException
     * @see #setTORecipient(String)
     */
    public final Address[] getTORecipient() throws AddressException {
        if (addrTORecipient_ == null) {
            return new Address[0];
        }
        return (Address[]) addrTORecipient_.clone();
    }

    /**
     * Sets the TO Recipients of the email message. A comma separated value of
     * recipients email ids.
     * 
     * @param pstrTORecipient
     *            String TO recipient.
     * @throws AddressException
     * @see #getTORecipient()
     */
    public final void setTORecipient(String pstrTORecipient) throws AddressException {
        if (pstrTORecipient == null || "".equals(pstrTORecipient))
        {
            throw new AddressException("Atleast One TO recipient must be specified.");
        }
        addrTORecipient_ = InternetAddress.parse(pstrTORecipient);
        strTORecipient_ = pstrTORecipient;
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("EMailId [");
        sbuffer.append(strEmailId_);
        sbuffer.append("], TO [");
        sbuffer.append(strTORecipient_);
        sbuffer.append("], CC [");
        sbuffer.append(strCCRecipient_);
        sbuffer.append("], BCC [");
        sbuffer.append(strBCCRecipient_);
        sbuffer.append("] Subject [");
        sbuffer.append(strSubject_);
        sbuffer.append("]");
        return sbuffer.toString();
    }
    
    /**
     * The key value pair values to be added to the message header.
     * The objects stored in the map must be of type String. If they are not then during the runtime
     * an exception will be thrown.
     * 
     * @param pMessageHeaderKeyValueMap
     */
    public void setMessageHeader(HashMap<String, String> pMessageHeaderKeyValueMap) {
        if (pMessageHeaderKeyValueMap == null || pMessageHeaderKeyValueMap.isEmpty()) {
            throw new IllegalArgumentException("Header cannot be set as null or empty");
        }
        mapMessageHeader_ = pMessageHeaderKeyValueMap;
    }
    
    /**
     * Returns the e-mail message header.
     * 
     * Defaults the message header reply-to attribute with the mail.properties reply-to property.
     * 
     * @return map
     * @see #setMessageHeader(HashMap)
     */
    public Map<String, String> getMessageHeader() {
        return mapMessageHeader_;
    }
    
    /**
     * Set true to apply default values for the following fields
     * {@linkplain #getMessageBodyHeader()}, {@linkplain #getMessageBodyFooter()},
     * {@linkplain #getSubject()} and {@linkplain #getMessageHeader()}.
     * True sets the default message header false does not apply any message header.
     * 
     * @param b boolean value.
     */
    public void applyDefaultValues(boolean b) {
        this.bDefaultValuesToBeApplied = b;
    }
    
    /**
     * Returns the setting for default message header to be applied in case the message header is null.
     * 
     * @return boolean
     */
    public boolean isDefaultValuesToBeApplied() {
        return bDefaultValuesToBeApplied;
    }
    
    /**
     * Sets the flag to avoids SPAM.
     * Once the flag is set then an exception will be thrown if the same EMail message is being transported.
     * @return True if the flag is set else returns false.
     */
    protected boolean setEmailTransported() {
        return bEmailTransported_.compareAndSet(false, true);
    }
    
    /**
     * Returns true if the mail was transported otherwise false.
     * @return AtomicBoolean
     */
    protected boolean getEmailTransported() {
        return bEmailTransported_.get();
    }
    
    /**
     * Sets the Unique Email Id.
     * Ideally this can be <code>request_id + "#" + some_program_unique_serial_no</code>.
     * @param strEmailId
     * @throws NullPointerException
     */
    public void setEMailId(String strEmailId) {
        if (strEmailId.equals("")) {
            throw new NullPointerException("Email Id is null.");
        }
        this.strEmailId_ = strEmailId;
    }
    
    /**
     * Returns the Unique EMailId.
     * @return a unique email id
     */
    public String getEMailId() {
        return this.strEmailId_;
    }
    
    /**
     * Stores Sender EMail address.
     */
    private String strSenderEmailId_;

	/**
	 * EMailType.
	 */
	private TYPE emailType ;
	
//	private LinkedList<MimeBodyPart> mimeBodyPartsList = new LinkedList<MimeBodyPart>();

	private String disposition;

    /**
     * Set the Sender Email Id if different than that set through the properties.
     * 
     * @param strSenderEmailId String
     * @throws AddressException 
     * @throws NullPointerException
     */
    public void setSenderEmailId(String strSenderEmailId) throws AddressException {
        if ( strSenderEmailId == null) {
            throw new NullPointerException("Sender Email Id cannot be null");
        }
        if ("".equals(strSenderEmailId)) {
            throw new NullPointerException("Sender Email Id cannot be null");
        }
        new InternetAddress(strSenderEmailId);
        strSenderEmailId_ = strSenderEmailId;
    }
    
    /**
     * Returns the sender email id if set otherwise returns null.
     * 
     * @return String
     */
    public String getSenderEmailId() {
        return strSenderEmailId_;
    }
    
    /**
     * Returns EMailType.
     * Default is {@link EMail.TYPE#TEXT}.
     * 
     * @return Email.TYPE
     */
    public TYPE getEMailType() {
    	return emailType ;
    }
    
    /**
     * Set the type of the EMail.
     * The value can be either {@link EMail.TYPE#TEXT} or {@link EMail.TYPE#HTML} 
     * @param type
     */
    public void setEMailType(TYPE type) {
    	if (type == null) {
    		throw new NullPointerException("EMailType cannot be null.");
    	}
    	emailType = type;
    }
    
    
    /**
     * Returns the list of attachments.
     * To add new attachments call <code> getAttachments().add(EMailAttachment)</code>.
     *
     * @return ArrayList<EMailAttachment>
     */
    public ArrayList<EMailAttachment> getAttachments() {
    	return attachments;
    }
    
    /**
     * Sets the disposition of the message body as per the given value.
     * Valid values are {@link Part#ATTACHMENT} and {@link Part#INLINE}
     *   
     * @param disposition 
     */
    public void setDisposition(String disposition) {
    	this.disposition = disposition;
    	if (!(Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition))) {
    		throw new IllegalArgumentException("Invalid disposition.");
    	}
    }
    
    /**
     * Returns the disposition
     * @return String
     */
    public String getDisposition() {
    	return disposition;
    }
    
    /**
     * Adds an external file as an attachment. One cannot call this method
     * repeatedly. The last call's value will be stored. If more than one file
     * is to be attached then use {@link #addAttachment(String)} or
     * {@link #addAttachment(File)}. Calling this method is same as calling
     * {@link #addAttachments(File[])} with array size equals one.
     * 
     * @param strFileName
     *            String A fully qualified File Name.
     * @throws IOException
     * @throws NullPointerException
     * 
     * @see #addAttachments(File[])
     */
    public void addAttachment(String strFileName) throws IOException {
        addAttachments(new String[] {strFileName});
    }

    /**
     * Adds an external file as an attachment. One cannot call this method
     * repeatedly. The last call's value will be stored. If more than one file
     * is to be attached then use {@link #addAttachments(String[])}or
     * {@link #addAttachments(File[])}. Calling this method is same as calling
     * {@link #addAttachments(File[])}with array size equals one.
     * 
     * @param file File to be attached.
     * @throws IOException
     * @throws NullPointerException
     */
    public void addAttachment(File file) throws IOException {
        addAttachments(new File[] { file });
    }
    
    /**
     * Adds all the files provided as attachments to the email. One cannot call
     * this method repeatedly. The last call's value will be stored. This method
     * is provided for simplicity and internally calls
     * {@link #addAttachments(File[])}. Each value of the array is validated. If
     * the file does not exists or is a directory then {@link IOException}
     * is thrown. If the value is null or even empty then a {@link NullPointerException}
     * is thrown.
     * 
     * @param strFiles
     *            String Array of fully qualified file.
     * @throws IOException
     * @throws NullPointerException
     * 
     * @see #addAttachment(String)
     * @see #addAttachments(File[])
     * 
     */
    public void addAttachments(String[] strFiles) throws IOException {
        File[] fileArray = new File[strFiles.length];
        for (int i = 0; i < strFiles.length; i++) {
            String strFileName = strFiles[i];
            if (strFileName == null || strFileName.equals("")) {
                throw new NullPointerException(
                        "File Array cannot have any null value.");
            }
            fileArray[i] = new File(strFileName);
        }
        addAttachments(fileArray);
    }

    /**
     * Adds all the files provided as attachments to the email. One cannot call
     * this method repeatedly. The last call's value will be stored. Each value
     * of the array is validated. If the file is non-existent or is a directory
     * then {@link IOException}is thrown. If the value is null or
     * even empty then a {@link NullPointerException}is thrown.
     * 
     * The file is then converted into {@link EMailAttachment}. So to retrieve the
     * attachments give a call to {@link #getAttachments()}. Disposition is set as
     * {@link Part#INLINE} by default. 
     * 
     * Depending upon the size of the file the email attachment is categorized as {@link SmallEmailAttachment} if
     * the size is < 1MB and otherwise into {@link LargeEmailAttachment}. The basic difference is that the small
     * email attachments are fully read and stored where as large email attachments are read in chunks. This means
     * that the small attachments can be transported asynchronously whereas large attachments cannot be transported
     * using the asynchronous method.
     *
     * 
     * @param files
     *            array of File objects
     * @throws IOException
     * @throws NullPointerException
     */
    public void addAttachments(File[] files) throws IOException {
        for (File file: checkFiles(files)) {
        	String name = file.getName().substring(file.getName().lastIndexOf("/")+1);
        	EMailAttachment attachment;
        	if (file.length() <= EMailAttachment.SMALL_SIZE.longValue()) {
				attachment = new SmallEmailAttachment(name, file);
        	} else {
        		attachment = new LargeEmailAttachment(name, file);
        	}
        	attachment.setDisposition(Part.INLINE);
        	getAttachments().add(attachment);
        }
    }
    
    /**
     * Deletes the given file if present from the attachment set.
     * @param file
     */
    public void deleteAttachment(String file) {
    	deleteAttachment(new File[] { new File(file)} );
    }
    
    /**
     * Deletes the given file if present from the attachment set.
     * @param file
     */
    public void deleteAttachment(File file) {
    	deleteAttachment(new File[] {file});
    }
    
    /**
     * Deletes all the give files from the attachment set if present.
     * Ignores the file if not present.
     * 
     * @param files
     */
    public void deleteAttachment(File[] files) {
    	for (File file : files) {
    		for (EMailAttachment attachment : getAttachments()) {
    			if (attachment.getFileName().equals(file.getPath())) {
    				getAttachments().remove(attachment);
    			}
    		}
    	}
    }
    
    /**
     * Clears all attachments.
     */
    public void clearAttachments() {
    	getAttachments().clear();
    }

    /**
     * Validates the files to be attached are non-existent and are not
     * directories.
     * @param fileArray 
     * 
     * @throws FileNotFoundException
     * @throws NullPointerException
     *             if the null object is encountered.
     */
    private File[] checkFiles(File[] fileArray) throws FileNotFoundException {
        if (fileArray == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < fileArray.length; i++) {
            File file = fileArray[i];
            if (file == null) {
                throw new NullPointerException();
            }
            if (file.exists()) {
                if (file.isDirectory()) {
                    throw new FileNotFoundException(
                            "File Cannot be a Directory");
                }
            } else {
                throw new FileNotFoundException();
            }
        }
        return fileArray;
    }

}
