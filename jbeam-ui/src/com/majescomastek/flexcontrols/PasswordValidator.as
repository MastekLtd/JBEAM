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
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.majescomastek.flexcontrols
{
	import mx.validators.ValidationResult;
	import mx.validators.Validator;

	public class PasswordValidator extends Validator
	{
		// Define Array for the return value of doValidation(). 
        private var results:Array; 
 
        public function PasswordValidator() 
        { 
                super(); 
        } 
 
        public var confirmationSource: Object; 
        public var confirmationProperty: String; 
 
        // Define the doValidation() method. 
        override protected function doValidation(value:Object):Array { 
 
            // Call base class doValidation(). 
                var results:Array = super.doValidation(value.password); 
 
            if (value.password != value.confirmation) { 
                        results.push(new ValidationResult(true, null, "Mismatch", 
                        "Password Dosen't match Retype!")); 
 
            } 
 
            return results; 
        }        
 
        /** 
         *  @private 
         *  Grabs the data for the confirmation password from its different sources 
         *  if its there and bundles it to be processed by the doValidation routine. 
         */ 
        override protected function getValueFromSource():Object 
        { 
                var value:Object = {}; 
 
                value.password = super.getValueFromSource(); 
 
                if (confirmationSource && confirmationProperty) 
                { 
                        value.confirmation = confirmationSource[confirmationProperty]; 
                } 
 
                return  value; 
        }   
		
	}
}