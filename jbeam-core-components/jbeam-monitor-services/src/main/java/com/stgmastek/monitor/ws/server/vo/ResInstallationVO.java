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

public class ResInstallationVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private InstallationData installationData;
	
	private List<InstallationData> installationDataList;
	
	private Installation installation;
	
	private List<Installation> installationsList;

	/**
	 * Sets the installationDataList.
	 *
	 * @param installationDataList 
	 *        The installationDataList to set
	 */
	public void setInstallationDataList(List<InstallationData> installationDataList) {
		this.installationDataList = installationDataList;
	}

	/**
	 * Gets the installationDataList.
	 *
	 * @return the installationDataList
	 */
	public List<InstallationData> getInstallationDataList() {
		return installationDataList;
	}

	/**
	 * Sets the installationData
	 *
	 * @param installationData 
	 *        The installationData to set.
	 */
	public void setInstallationData(InstallationData installationData) {
		this.installationData = installationData;
	}

	/**
	 * Gets the installationData
	 *
	 * @return the installationData
	 */
	public InstallationData getInstallationData() {
		return installationData;
	}

	/**
	 * Gets the installation
	 *
	 * @return the installation
	 */
	public Installation getInstallation() {
		return installation;
	}

	/**
	 * Sets the installation
	 *
	 * @param installation 
	 *        The installation to set.
	 */
	public void setInstallation(Installation installation) {
		this.installation = installation;
	}

	/**
	 * Gets the installationsList
	 *
	 * @return the installationsList
	 */
	public List<Installation> getInstallationsList() {
		return installationsList;
	}

	/**
	 * Sets the installationsList
	 *
	 * @param installationsList 
	 *        The installationsList to set.
	 */
	public void setInstallationsList(List<Installation> installationsList) {
		this.installationsList = installationsList;
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResInstallationVO.java                                                        $
 * 
 * 3     1/06/10 5:04p Grahesh
 * Changed the object hierarchy
 * 
 * 2     12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/