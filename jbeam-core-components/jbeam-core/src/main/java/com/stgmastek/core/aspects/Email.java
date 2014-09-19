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
package com.stgmastek.core.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify whether upon execution of the method, an email is to be sent
 * 
 * Any where during the batch execution if after completion of the state, an email has to be sent, then
 * the method has to be annotated with this annotation interface and also an implementation is to be provided for 
 * the email body.
 *    
 * @author grahesh.shanbhag
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Email {
	
	/**
	 * Email type - indicates 'when' the email has to be sent
	 */
	public static enum EMAIL_TYPE {WHEN_BATCH_STARTS, WHEN_BATCH_ENDS, WHEN_OBJECT_FAILS, DEFAULT};
	
	/** The parameter i.e. email type to the annotation*/
	EMAIL_TYPE type() default EMAIL_TYPE.DEFAULT;

}

/*
* Revision Log
* -------------------------------
* $Log:: /Product_Base/Projects/Batch/Code/Java/Core/src/com/stgmastek/core/aspects/Email.java                                                                                       $
 * 
 * 4     3/24/10 2:03p Kedarr
 * Added new email type
 * 
 * 3     12/17/09 12:22p Grahesh
 * Updated the comments
 * 
 * 2     12/17/09 11:46a Grahesh
 * Initial Version
*
*
*/