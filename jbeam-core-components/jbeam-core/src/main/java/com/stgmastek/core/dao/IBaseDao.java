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
package com.stgmastek.core.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * The base interface for all Data Access layer.
 *
 * This class currently exhibits only one method.
 *  
 * @author Kedar Raybagkar
 * @since
 */
public interface IBaseDao {

	/**
	 * Protected method to have a uniform way to release the resources
	 * This method is called in the <i>finally</i> block by the implementing sub classes 
	 * 
	 * @param rs
	 * 		  The <i>ResultSet</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 * @param stmt
	 * 		  The <i>Statement</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 * @param con
	 * 		  The <i>Connection</i> held by the DAO method, could be null. 
	 * 		  If null, would be ignored.  
	 */
	public void releaseResources(ResultSet rs, Statement stmt, Connection con);
}
