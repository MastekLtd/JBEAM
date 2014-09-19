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
package com.stgmastek.core.exception;

/**
 * An exception that indicates problems with the configuration or the setup.
 *
 *
 * @author Kedar Raybagkar
 * @since 3.3
 */
public class InvalidConfigurationException extends RuntimeException {

	/**
	 * serial version number
	 */
	private static final long serialVersionUID = -3899879782261987907L;

	/**
	 * Default constructor.
	 */
	public InvalidConfigurationException() {
		super();
	}

	/**
	 * Constructs the exception with the given message.
	 * @param message
	 */
	public InvalidConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructs the exception with the given cause.
	 * @param cause
	 */
	public InvalidConfigurationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs the exception with the given message and the cause.
	 * @param message
	 * @param cause
	 */
	public InvalidConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
