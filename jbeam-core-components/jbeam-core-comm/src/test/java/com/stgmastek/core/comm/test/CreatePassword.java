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
package com.stgmastek.core.comm.test;

import com.stgmastek.core.comm.security.PasswordEncryptionService;

public class CreatePassword {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		System.out.println(PasswordEncryptionService.getInstance().encrypt("NIXON", "UTF-8"));
	}

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/CoreComm/src/com/stgmastek/core/comm/test/CreatePassword.java                                                                        $
 * 
 * 2     12/17/09 11:56a Grahesh
 * Initial Version
*
*
*/