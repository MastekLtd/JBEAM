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
package com.stgmastek.core.interfaces;

import com.stgmastek.core.util.BatchContext;

/**
 * The interface defines method for authenticating with external service.
 * 
 * The main purpose of the method would be to make one time user/password based
 * authentication and store the returned token which can be used in subsequent
 * calls for performance improvement.
 * 
 * @author shantanuc
 * 
 */
public interface IAuthenticationHandler {

	/**
	 * This method will authenticate with a given service using username and
	 * password and store the token got as part of response in BatchContext.
	 * This token can be used in subsequent calls to the external service
	 * instead of username/password based authentication.
	 * 
	 * @param batchContext
	 * @return token
	 */
	public String authenticate(BatchContext batchContext);

}
