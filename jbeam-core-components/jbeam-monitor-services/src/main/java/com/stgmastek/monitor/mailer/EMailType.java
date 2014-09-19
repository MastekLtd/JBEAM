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

import java.io.Serializable;

/**
 * Class that determines the type of the EMail. Currently supported are HTML and
 * TEXT.
 * 
 * @author Kedar Raybagkar
 * @since V1.0R27
 */
public final class EMailType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2217578781646483065L;

	/**
	 * EMail Type of HTML.
	 */
	public static final EMailType HTML = new EMailType("HTML", Integer.MAX_VALUE);
    
	/**
	 * EMail Type of TEXT.
	 */
	public static final EMailType TEXT = new EMailType("TEXT", Integer.MIN_VALUE);

	/**
	 * Stores the given emailType.
	 */
	private String emailType;

	/**
	 * Stores the given identifier.
	 */
	private int identifier;

	/**
	 * Returns the EMailType
	 * @return EMailType {@link #HTML} or {@link #TEXT}
	 */
	public String getEmailType() {
		return emailType;
	}

	/**
	 * Returns the identifier of this EMailType.
	 * 
	 * @return int
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * Constructs the EMailType for the given type and id.
	 * @param type Name of the type.
	 * @param id Unique Identifier.
	 */
	public EMailType(String type, int id) {
		emailType = type;
		identifier = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return identifier;
	}

	/**
	 * Resolves the given id to an {@linkplain EMailType}.
	 * @param id identifier
	 * @return EMailType
	 */
	public EMailType resolve(int id) {
		if (id == HTML.getIdentifier()) {
			return HTML;
		} else if (id == TEXT.getIdentifier()) {
			return TEXT;
		}
		return null;
	}

	/**
	 * Resolves a given string to either {@link EMailType#HTML} or {@link #TEXT}
	 * 
	 * @param type
	 * @return EMailType
	 */
	public EMailType resolve(String type) {
		if (HTML.getEmailType().equalsIgnoreCase(type)) {
			return HTML;
		} else if (TEXT.getEmailType().equalsIgnoreCase(type)) {
			return TEXT;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof EMailType) {
			EMailType type = (EMailType) obj;
			return (type.getEmailType().equalsIgnoreCase(this.getEmailType()) && type.getIdentifier() == this.getIdentifier());
		}
		return false;
	}
} // end of class EMailType
