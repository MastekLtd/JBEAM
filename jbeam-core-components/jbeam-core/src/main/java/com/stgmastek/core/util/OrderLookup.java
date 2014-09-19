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
package com.stgmastek.core.util;

import java.io.Serializable;

public class OrderLookup implements  Serializable{

	/** Serial Version UID */
	private static final long serialVersionUID = -8411786453409132114L;

	/** The entity name */
	private String entity;

	/** The order by column */
	private String orderByColumn;

	/**
	 * Gets the entity
	 *
	 * @return the entity		
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Sets the entity
	 *
	 * @param entity 
	 *        The entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * Gets the orderByColumn
	 *
	 * @return the orderByColumn		
	 */
	public String getOrderByColumn() {
		return orderByColumn;
	}

	/**
	 * Sets the orderByColumn
	 *
	 * @param orderByColumn 
	 *        The orderByColumn to set
	 */
	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderLookup [entity=" + entity + ", orderByColumn="
				+ orderByColumn + "]";
	}
	
	
	
}
