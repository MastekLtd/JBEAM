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

import java.io.Serializable;

/**
 * This class represents FunctionMaster table in Monitor Schema
 *
 * @author mandar440346
 *
 */
public class MenuData implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String functionId;
	private String functionName;
	private String priorFunctionId;
	private String roleId;
	
	/**
	 * Gets the functionId
	 *
	 * @return the functionId
	 */
	public String getFunctionId() {
		return functionId;
	}
	/**
	 * Sets the functionId
	 *
	 * @param functionId 
	 *        The functionId to set
	 */
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	/**
	 * Gets the functionName
	 *
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * Sets the functionName
	 *
	 * @param functionName 
	 *        The functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	/**
	 * Gets the priorFunctionId
	 *
	 * @return the priorFunctionId
	 */
	public String getPriorFunctionId() {
		return priorFunctionId;
	}
	/**
	 * Sets the priorFunctionId
	 *
	 * @param priorFunctionId 
	 *        The priorFunctionId to set
	 */
	public void setPriorFunctionId(String priorFunctionId) {
		this.priorFunctionId = priorFunctionId;
	}
	/**
	 * Gets the roleId
	 *
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * Sets the roleId
	 *
	 * @param roleId 
	 *        The roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
