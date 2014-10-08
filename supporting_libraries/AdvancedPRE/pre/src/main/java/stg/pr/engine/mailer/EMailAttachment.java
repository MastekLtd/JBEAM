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
 * $Revision: 2980 $
 *
 * $Header: http://192.100.194.241:8080/svn/ProductTools/JavaTools/AdvancedPRE/trunk/src/stg/pr/engine/mailer/EMailAttachment.java 1402 2010-05-06 11:14:41Z kedar $
 *
 * $Log:    $
 *
 */
package stg.pr.engine.mailer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.activation.DataSource;
import javax.mail.Part;

/**
 * A type of an attachment to an email.
 *
 * The type can be of two types {@link SmallEmailAttachment} and {@link LargeEmailAttachment}.
 * The small email attachments can be serialized and transported asynchronously where as large
 * email attachment needs to be sent using the caller runs policy.
 * 
 * @author Kedar Raybagkar
 * @since V1.0R28.2
 */
public abstract class EMailAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4615112817340207717L;
	
	private String name;
	
	private String disposition;
	
	public static final Integer SMALL_SIZE = (5*1024*1024); 

	private String filename;

	/**
	 * Constructs the EMailAttachment.
	 * 
	 * Calling this is same as calling {@link #EMailAttachment(String, File)}.
	 * 
	 * @param name Name of the attachment
	 * @param filename File.
	 * @throws IllegalArgumentException if the file is a directory or does not exists.
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, String filename) throws IOException{
		this(name, new File(filename));
	}
	
	/**
	 * Constructs the EMailAttachment.
	 * 
	 * The file is then converted to a String representation. This is then able to be serialized and upon actual
	 * transportation it is then converted into DataSource. Thus this method can be used for sending asynchronous 
	 * emails. Please make sure that the file is made available to all PRE's which participate in cluster mode.
	 *  
	 * @param name Name of the file
	 * @param file File
	 * @throws IllegalArgumentException if the file is a directory or does not exists.
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, File file) throws IOException{
		if (name == null) {
			throw new NullPointerException("Name must be provided");
		}
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		if (file.isDirectory()) {
			throw new FileNotFoundException("File cannot be a directory");
		}
		this.name = name;
		this.filename = file.getPath();
	}
	
	/**
	 * Constructs the EMailAttachment.
	 * 
	 * The file is then converted to a String representation. This is then able to be serialized and upon actual
	 * transportation it is then converted into DataSource. Thus this method can be used for sending asynchronous 
	 * emails. Please make sure that the file is made available to all PRE's which participate in cluster mode.
	 *  
	 * @param name Name of the file
	 * @param is Input stream.
	 * @throws IllegalArgumentException if the file is a directory or does not exists.
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, InputStream is, String contentType) throws IOException{
		if (name == null) {
			throw new NullPointerException("Name must be provided");
		}
		if (is == null) {
			throw new NullPointerException("InputStream cannot be null");
		}
		if (contentType == null || "".equals(contentType)) {
			throw new NullPointerException("Content Type cannot be null");
		}
		this.name = name;
		this.filename = null;
	}
	

	/**
	 * Constructs the EMailAttachment using the name and the datasource.
	 * 
	 * Please note that if the datasource is used as it is not serializable it cannot be used
	 * to send emails through the method {@link CMailer#sendAsyncMail(EMail)} instead use {@link CMailer#sendMail(EMail)}.
	 * 
	 * @param name Name of the attachment.
	 * @param datasource ByteArrayDataSource
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, DataSource datasource) {
		if (name == null) throw new NullPointerException("name is null");
		if (datasource == null) throw new NullPointerException("DataSource is null");
		this.name = name;
		this.filename = null;
	}
	
	/**
	 * Sets the disposition of this attachment.
	 * @param disposition
	 */
	public void setDisposition(String disposition) {
		if (!(Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition)) ) {
			throw new IllegalArgumentException("Disposition must be either Part.ATTACHMENT or Part.INLINE");
		}
		this.disposition = disposition;
	}

	/**
	 * Returns the name of the attachment.
	 * May return null in case the Datasouce is used.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the datasource.
	 * 
	 * Will either return the datasource set using one of its constructor or will return
	 * the {@link DataSource} in case other constructors have been used.
	 * 
	 * @return the dataSource
	 */
	public abstract DataSource getDataSource();

	/**
	 * Returns the disposition.
	 * @return the disposition
	 */
	public String getDisposition() {
		return disposition;
	}
	
	public String getFileName() {
		return filename;
	}

}
