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

public class ResInstallationEntitiesVO extends BaseResponseVO{

	private static final long serialVersionUID = 1L;
	
	private List<InstallationEntity> installationEntities;

	/**
	 * Returns the installationEntities
	 * 
	 * @return the installationEntities
	 */
	public List<InstallationEntity> getInstallationEntities() {
		return installationEntities;
	}

	/**
	 * Sets the installationEntities to set
	 * 
	 * @param installationEntities
	 * 		  The installationEntities to set
	 */
	public void setInstallationEntities(
			List<InstallationEntity> installationEntities) {
		this.installationEntities = installationEntities;
	}
	
	
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResInstallationEntitiesVO.java                                                $
 * 
 * 1     1/06/10 11:35a Grahesh
 * Initial Version
* 
*
*/