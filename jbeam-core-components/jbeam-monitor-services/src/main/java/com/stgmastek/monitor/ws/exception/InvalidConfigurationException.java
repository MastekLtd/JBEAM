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
package com.stgmastek.monitor.ws.exception;

/**
 * An exception that indicates problems with the configuration or the setup.
 *
 *
 * @author Mandar Vaidya
 * @since 3.3
 */
public class InvalidConfigurationException extends RuntimeException {



	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** The Default constructor */
	public InvalidConfigurationException() {
		super();
	}

	/** 
	 * The constructor that takes the message for the exception (occurred) as the argument
	 *   
	 * @param message
	 * 		  The message for the thrown exception
	 */
	public InvalidConfigurationException(String message) {
		super(message);
	}

	/** 
	 * The constructor that takes the cause for the exception (occurred) as the argument
	 *
	 * @param cause
	 *  		  The cause for the thrown exception
	 */
	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

	/** 
	 * The constructor that takes the message and the cause for the exception 
	 * (occurred) as the arguments
	 *
	 * @param message
	 * 		  The message for the thrown exception
	 * 
	 * @param cause
	 *  		  The cause for the thrown exception
	 */
	public InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
