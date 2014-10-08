/**
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
 *
 * $Revision:  $
 *
 * $Header:   $
 *
 * $Log:    $
 *
 */
package stg.pr.engine;

import java.io.Serializable;

/**
 * Stores the information about PRE.
 * Major.Minor.Macro.Emergency
 *
 * @author kedar460043
 * @since 29.02
 */
public interface PREInfo extends Serializable {

	/**
	 * Returns the product name as defined during the build.
	 * @return the name
	 */
	public String getName();


	/**
	 * Returns the product version as defined during the build.
	 * @return the version
	 */
	public String getVersion();


	/**
	 * Returns the product bundling time.
	 * 
	 * @return the bundledOn
	 */
	public long getBundledOn();
	
	/**
	 * Returns the product major version number as defined during the build.
	 * @return the majorVersion
	 */
	public int getMajorVersion();
	
	/**
	 * Returns the product minor version number as defined during the build.
	 * @return the minorVersion
	 */
	public int getMinorVersion();
	
	
	/**
	 * Returns the product macro version number as defined during the build.
	 * @return the macroVersion
	 */
	public int getMacroVersion();
	
	/**
	 * Returns the last version number as defined during the build.
	 * @return
	 */
	public int getPatchVersion();
	
	/**
	 * Returns true if the release is a snapshot (not production ready).
	 * @return the snapshot
	 */
	public boolean isSnapshot();

	/**
	 * Returns the symbolic name as defined during the build.
	 * @return the symbolicName
	 */
	public String getSymbolicName();

	/**
	 * Returns the build number.
	 * 
	 * @return the buildNumber
	 */
	public int getBuildNumber();

}