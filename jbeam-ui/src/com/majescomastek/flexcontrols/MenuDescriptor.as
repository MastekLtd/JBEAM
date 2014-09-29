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
	import mx.controls.treeClasses.DefaultDataDescriptor;

	public class MenuDescriptor extends DefaultDataDescriptor
	{
		public function MenuDescriptor()
		{
			super();
		}
		 /**
	     *  @private
	     */
		override public function isBranch(node:Object, model:Object=null):Boolean
		{
			if (node == null)
            return false;
	        var branch:Boolean = false;
	        if (node is XML)
	        {
	            var childList:XMLList = node.children();
	            //accessing non-required e4x attributes is quirky
	            //but we know we'll at least get an XMLList
	            var branchFlag:XMLList = node.@isBranch;
	            //check to see if a flag has been set
	            if (branchFlag.length() == 1)
	            {
	                //check flag and return (this flag overrides termination status)
	                if (branchFlag[0] == "true")
	                    branch = true;
	            }
	            //since no flags, we'll check to see if there are children
	            else if (childList.length() != 0)
	            {
	                branch = true;
	            }
	        }
	        else if (node is Object)
	        {
	            try
	            {
	                if (node.children != undefined && node.children != null && node.children.length > 0)
	                {
	                    branch = true;
	                }
	            }
	            catch(e:Error)
	            {
	            }
	        }
        return branch;
		}
		
	}
}