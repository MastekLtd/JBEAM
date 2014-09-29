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
package com.majescomastek.jbeam.common
{
	import mx.rpc.IResponder;

	/**
	 * Create a BaseResponder class to get around the limitation of Actionscript
	 * for not supporting anynoymous classes.
	 * 
	 * In case any function requires a dynamically created IResponder, just pass
	 * in the result and fault handler functions to this class constructor and it
	 * will automatically be taken care of.
	 */
	public class BaseResponder implements IResponder
	{
		private var _resultHandler:Function;
		
		private var _faultHandler:Function;
		
		public function BaseResponder(resultHandler:Function, faultHandler:Function)
		{
			_resultHandler = resultHandler;
			_faultHandler = faultHandler;
		}
		
		public function result(data:Object):void
		{
			_resultHandler(data);
		}
		
		public function fault(data:Object):void
		{
			_faultHandler(data);
		}
		
	}
}