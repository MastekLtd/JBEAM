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
package com.stgmastek.birt.report.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 	IO related utilities.
 * 
 * 	@author Prasanna Mondkar
 */

public class IOUtils {

	public static List<String> getTempFileWithFullPath() throws IOException {
		File file = File.createTempFile("OnlineReport", ".pdf");
		List<String> fileDetails = new ArrayList<String>(2);
		fileDetails.add( file.getParentFile().getAbsolutePath().replace("\\", "/") );
		fileDetails.add( file.getName() );
		file.delete();		
		return fileDetails;
	}
	
	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if(file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
