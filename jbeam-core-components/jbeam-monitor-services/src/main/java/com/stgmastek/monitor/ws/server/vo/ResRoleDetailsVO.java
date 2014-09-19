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
 * This class holds the role details response. 
 *
 * @author Lakshman Pendrum
 * @since $Revision: 1 $  
 */
public class ResRoleDetailsVO extends BaseResponseVO {

		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = 1L;
		
		private RoleData roleData;		

		private List<RoleData> roleDataList;

		/**
		 * Gets the roleData
		 *
		 * @return the roleData
		 */
		public RoleData getRoleData() {
			return roleData;
		}

		/**
		 * Sets the roleData
		 *
		 * @param roleData 
		 *        The roleData to set
		 */
		public void setRoleData(RoleData roleData) {
			this.roleData = roleData;
		}

		/**
		 * Gets the roleDataList
		 *
		 * @return the roleDataList
		 */
		public List<RoleData> getRoleDataList() {
			return roleDataList;
		}

		/**
		 * Sets the roleDataList
		 *
		 * @param roleDataList 
		 *        The roleDataList to set
		 */
			public void setRoleDataList(List<RoleData> roleDataList) {
				this.roleDataList = roleDataList;
			}

  }
/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/server/vo/ResRoleDetailsVO.java                                                         $
 * 
 * 1     6/25/10 2:25p Lakshmanp
 * initial version
*
*
*/
