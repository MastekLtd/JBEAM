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
package com.stgmastek.monitor.ws.test;

import com.stgmastek.monitor.ws.exception.CommException;
import com.stgmastek.monitor.ws.security.PasswordEncryptionService;

public class PasswordEncryptionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String oPwd = "jbeam";
		try {
			String nPwd = PasswordEncryptionService.getInstance().encrypt(oPwd, "UTF-8");
			System.out.println(nPwd);
			
			oPwd = "ljzdzbdYhT1stUe3CUIGlV2oGgY=";
			if(nPwd.equals(oPwd))
				System.out.println("New Password ("+ nPwd +") matches with old password ("+ oPwd +") ");
			else
				System.out.println("New Password ("+ nPwd +") does not match with old password ("+ oPwd +") ");				
			
		} catch (CommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/MonitorServices/src/com/stgmastek/monitor/ws/test/PasswordEncryptionTest.java                                                        $
 * 
 * 18    12/17/09 12:02p Grahesh
 * Initial Version
*
*
*/