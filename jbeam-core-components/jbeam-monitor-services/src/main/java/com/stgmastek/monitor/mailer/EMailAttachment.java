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

import java.io.File;
import java.io.FileNotFoundException;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Part;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author kedar460043
 * @since
 */
public class EMailAttachment {

	private String name;
	private DataSource dataSource;
	private String disposition;

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
	public EMailAttachment(String name, String filename){
		this(name, new File(filename));
	}
	
	/**
	 * Constructs the EMailAttachment.
	 * 
	 * @param name Name of the file
	 * @param file File
	 * @throws IllegalArgumentException if the file is a directory or does not exists.
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, File file){
		this(name, new FileDataSource(file));
		if (!file.exists()) {
			throw new IllegalArgumentException(new FileNotFoundException(file.getPath()));
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException(new FileNotFoundException(file.getPath() + " is a directory."));
		}
	}
	
	/**
	 * Constructs the EMailAttachment.
	 * 
	 * @param name Name of the attachment.
	 * @param datasource DataSource
	 * @throws NullPointerException in case name or DataSource is null
	 */
	public EMailAttachment(String name, DataSource datasource) {
		this.name = name;
		this.dataSource = datasource;
		if (name == null) throw new NullPointerException("name is null");
		if (datasource == null) throw new NullPointerException("DataSource is null");
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the disposition
	 */
	public String getDisposition() {
		return disposition;
	}

}
