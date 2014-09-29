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
 */
package com.majescomastek.jbeam.controller.shell
{
	import org.puremvc.as3.multicore.patterns.command.MacroCommand;
	
	/**
	 * The startup command for the main application / shell; fired
	 * in response to the STARTUP notification. 
	 */
	public class StartupCommand extends MacroCommand
	{
			
		override protected function initializeMacroCommand():void
		{
			addSubCommand(RegisterShellDependencyCommand);								
		}

	}
}