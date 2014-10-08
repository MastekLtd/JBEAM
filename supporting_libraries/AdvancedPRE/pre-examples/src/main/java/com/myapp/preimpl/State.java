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
package com.myapp.preimpl;

import java.io.Serializable;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add multi-line description of the class indicating the objectives/purpose
 * of the class and the usage with each sentence ending with a period.
 *
 * @author Kedar Raybagkar
 * @since
 */
public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3554840900676936550L;
	
	private Long number;

	/**
	 * Set the number.
	 * @param number
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * Returns the number
	 * @return Long
	 */
	public Long getNumber() {
		return number;
	}
	
}
