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
package com.stgmastek.monitor.ws.server.vo;

/**
 * Add a one liner description of the class with a period at the end.
 *
 * Add a multi-line description of the class stating its purpose/objectives
 * and its usage with each sentence having a period at the end.
 *
 * @author Kedar Raybagkar
 * @since 
 *
 */
public class ReqVersionVO extends BaseRequestVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String majorVersion; 
	private String minorVersion;
	/**
	 * @return the majorVersion
	 */
	public String getMajorVersion() {
		return majorVersion;
	}
	/**
	 * @param majorVersion the majorVersion to set
	 */
	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	/**
	 * @return the minorVersion
	 */
	public String getMinorVersion() {
		return minorVersion;
	}
	/**
	 * @param minorVersion the minorVersion to set
	 */
	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}
	
}
