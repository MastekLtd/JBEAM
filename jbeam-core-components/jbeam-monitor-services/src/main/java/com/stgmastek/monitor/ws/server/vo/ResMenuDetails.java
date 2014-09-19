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

import java.util.List;


/**
 * This class holds the menu details response. 
 *
 * @author mandar440346
 *
 */
public class ResMenuDetails extends BaseResponseVO{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String installationCode;
	
	private MenuData menuData;
	
	private String parentFunctionId;
	
	private List<MenuData> children;
	
	private List<ResMenuDetails> menuList;

	/**
	 * Gets the menuData
	 *
	 * @return the menuData
	 */
	public MenuData getMenuData() {
		return menuData;
	}

	/**
	 * Sets the menuData
	 *
	 * @param menuData 
	 *        The menuData to set
	 */
	public void setMenuData(MenuData menuData) {
		this.menuData = menuData;
	}

	/**
	 * Gets the parentFunctionId
	 *
	 * @return the parentFunctionId
	 */
	public String getParentFunctionId() {
		return parentFunctionId;
	}

	/**
	 * Sets the parentFunctionId
	 *
	 * @param parentFunctionId 
	 *        The parentFunctionId to set
	 */
	public void setParentFunctionId(String parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}

	/**
	 * Gets the children
	 *
	 * @return the children
	 */
	public List<MenuData> getChildren() {
		return children;
	}

	/**
	 * Sets the children
	 *
	 * @param children 
	 *        The children to set
	 */
	public void setChildren(List<MenuData> children) {
		this.children = children;
	}

	/**
	 * Sets the menuList
	 *
	 * @param menuList 
	 *        The menuList to set
	 */
	public void setMenuList(List<ResMenuDetails> menuList) {
		this.menuList = menuList;
	}

	/**
	 * Gets the menuList
	 *
	 * @return the menuList
	 */
	public List<ResMenuDetails> getMenuList() {
		return menuList;
	}

	/**
	 * Sets the installationCode
	 *
	 * @param installationCode 
	 *        The installationCode to set
	 */
	public void setInstallationCode(String installationCode) {
		this.installationCode = installationCode;
	}

	/**
	 * Gets the installationCode
	 *
	 * @return the installationCode
	 */
	public String getInstallationCode() {
		return installationCode;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResMenuDetails.java                                                           $
 * 
 * 2     7/01/10 5:00p Mandar.vaidya
 * Removed field parentMenu
 * 
 * 1     6/10/10 4:11p Mandar.vaidya
 * Initial Version
*
*/