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
 * @author Anand Singh
 * 
 *
 * $Revision:: 1                                                                                                      $
 *	
 * $Header:: /Product_ $Log:: 
 * 
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	[Bindable]
	public class Question
	{
		private var _questionId:String;
		private var _questionDesc:String;
		private var _answer:String;
			
		public function Question()
		{
		}

		public function set questionId(value:String):void {
			this._questionId = value;
		}
		public function get questionId():String {
			return this._questionId;
		}

		public function set questionDesc(value:String):void {
			this._questionDesc = value;
		}
		public function get questionDesc():String {
			return this._questionDesc;
		}

		public function set answer(value:String):void {
			this._answer = value;
		}
		public function get answer():String {
			return this._answer;
		}

	}
}