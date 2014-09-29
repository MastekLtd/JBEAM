package com.majescomastek.jbeam.view
{
	/**
	 * This interface would be implemented by all the modules in our
	 * application to facilitate communication between the Shell and
	 * modules and also inter-module communication.
	 */
	public interface IModule
	{
		
		/**
		 * The data to be retrieved from this module.
		 */
		function get moduleInfo():Object;
		
		/**
		 * The data to be passed to this module.
		 */
		function set moduleInfo(value:Object):void;
		
		/**
		 * To cleanup the module.
		 */
		function cleanup():void;
	}

}