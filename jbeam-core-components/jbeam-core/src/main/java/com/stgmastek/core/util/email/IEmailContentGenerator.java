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
package com.stgmastek.core.util.email;

import stg.pr.engine.mailer.EMail;

import com.stgmastek.core.aspects.Email.EMAIL_TYPE;
import com.stgmastek.core.util.BatchContext;
import com.stgmastek.core.util.BatchObject;
import com.stgmastek.core.util.ObjectMapDetails;

/**
 * Interface that defined the methods for the actual implementing classes to implement. 
 * A default implementing class is included in the system {@link DefaultEmailContentGenerator}. 
 * If any implementation requires a different email content then implementing this interface and 
 * registering the implementing class in the configuration would solve the issue.
 * <p/>
 * Steps -  
 * <OL>
 * 	<LI> Create a class that extends {@link DefaultEmailContentGenerator}. This class implements {@link IEmailContentGenerator}.
 *  <LI> Register the implementing class in the CORE_CONFIG table for 
 *  	 code1="CORE", code2="EMAIL" and code3 ="CONTENT_HANDLER"
 *  <LI> Ensure that the implementing class is in the classpath 
 *  <LI> Start the batch.  
 * </OL>
 * The system would use this implementation
 * 
 * @author grahesh.shanbhag
 * @author Kedar Raybagkar
 *
 */
public interface IEmailContentGenerator {

	/**
	 * The method that the implementing class should implement
	 *  
	 * @param context
	 * 		  The batch context 
	 * @param TYPE
	 * 	      The email type as {@link EMAIL_TYPE}
	 * @param batchObject
	 * 	      The batch object considered for execution. May be null.
	 * @param objectDetails
	 * 		  Object Map Details set for this object. May be null. 
	 * @param lastEmail
	 * 	      True if this is the last email otherwise false. Applicable only for {@link EMAIL_TYPE#WHEN_OBJECT_FAILS}.
	 * @return the Email object that is fully configured.
	 */
	public EMail getContent(BatchContext context, EMAIL_TYPE TYPE, BatchObject batchObject, ObjectMapDetails objectDetails, Boolean lastEmail);
	
	/**
	 * Returns the default header HTML as String.
	 * Do not override this method for the sake of keeping the header same for all emails.
	 * @return String
	 */
	public String getDefaultHeader();
	
	/**
	 * Returns the default footer HTML as String
	 * Do not override this method for the sake of keeping the footer same for all emails.
	 * @return String
	 */
	public String getDefaultFooter();
	
	/**
	 * Returns the default Style Sheet.
	 * Do not override this method for the sake of keeping the style sheet same for all emails.
	 * @return String
	 */
	public String getDefaultStyleSheet();
	
	/**
	 * Returns the HTML with default batch info along with parameters.
	 * @param context
	 * @return String
	 */
	public String getDefaultBatchInfo(BatchContext context);
	
}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/util/email/IEmailContentGenerator.java                                                                   $
 * 
 * 11    4/28/10 10:22a Kedarr
 * Updated javadoc
 * 
 * 10    4/19/10 11:43a Kedarr
 * Updated java doc
 * 
 * 9     4/13/10 2:32p Kedarr
 * Changes made to add the escalation level and for passing object details as changed in the interface.
 * 
 * 8     4/06/10 5:08p Kedarr
 * corrected java doc for missing tags or improper tags due to change in method signatures.
 * 
 * 7     4/06/10 2:16p Kedarr
 * Added new methods for simplicity.
 * 
 * 6     3/25/10 4:41p Mandar.vaidya
 * Updated javadoc
 * 
 * 5     3/25/10 3:29p Kedarr
 * Added a new parameter.
 * 
 * 4     3/24/10 3:07p Kedarr
 * Changes made to return an EMail object instead of just a string.
 * 
 * 3     3/24/10 12:56p Kedarr
 * Added batch object as a new parameter.
 * 
 * 2     12/28/09 5:36p Grahesh
 * Corrected the package name induced due to refactoring
 * 
 * 1     12/28/09 4:48p Grahesh
 * 
 * 2     12/28/09 4:48p Grahesh
 * 
 * 1     12/28/09 4:46p Grahesh
 * 
 * 5     12/28/09 4:46p Grahesh
 * 
 * 4     12/18/09 12:58p Grahesh
 * Updated the comments
 * 
 * 3     12/18/09 12:32p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/