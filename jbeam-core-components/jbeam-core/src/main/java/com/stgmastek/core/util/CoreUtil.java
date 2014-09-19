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



public class CoreUtil {
	
	public static boolean isDateRun(BatchInfo batchInfo){
		if(Constants.BATCH_TYPE.DATE.name().equals(batchInfo.getBatchType())) {
			return true;
		}
		return false;
	}
	
	public static boolean isDateRun(String batchType){
		if(Constants.BATCH_TYPE.DATE.name().equals(batchType)) {
			return true;
		}
		return false;
	}
	
	public static Integer getListenerNo(int batchNo, int initListenerNo) {
		return Integer.parseInt(batchNo * 100
				+ getPaddedString(true, '0', "" + initListenerNo, 3));
	}
	
	private static String getPaddedString(boolean prepend, char paddingChar, String origString, int reqLength){
		if (origString != null && origString.length() < reqLength) {
			StringBuffer buff = new StringBuffer("");
			for (int i = 0; i < (reqLength - origString.length()); i++) {
				buff.append(paddingChar);
			}
			if (prepend) {
				return buff.append(origString).toString();
			} else {
				return origString + buff.toString();
			}
		}
		return origString;
	}

}
