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
package com.stg.crypto;

/**
 * Obfuscation Exception.
 * 
 * Obfuscation exception is thrown by the {@link IOObfuscator} class.
 *
 * @author Kedar Raybagkar
 * @version $Revision:     $
 * @since 
 *
 */
public class ObfuscationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2062578869007406729L;
	
	/**
	 * Constructs the object.
	 */
	public ObfuscationException() {
		super();
	}

	public ObfuscationException(String message) {
		super(message);
	}

	/**
	 * Constructs the object using the give underlying cause.
	 * @param cause
	 */
	public ObfuscationException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructs the object using the given message and the underlying cause.
	 * @param message
	 * @param cause
	 */
	public ObfuscationException(String message, Throwable cause) {
		super(message, cause);
	}
}
