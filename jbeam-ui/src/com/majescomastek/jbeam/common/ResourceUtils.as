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
	import mx.resources.IResourceManager;
	import mx.resources.ResourceManager;

	[ResourceBundle('ServerConfiguration')]
	public class ResourceUtils
	{
		private static const resourceManager:IResourceManager =
			ResourceManager.getInstance();

		public static function getString
			(module:String, key:String, failSilently:Boolean = false):String
		{
			var value:String = resourceManager.getString(module, key);

			if (!value && !failSilently)
			{
				throw new Error('Error: failed to find value for [' +
					key + '] in [' + module + '] resource bundle');
			}
			return value ? value : '';
		}

	}

}