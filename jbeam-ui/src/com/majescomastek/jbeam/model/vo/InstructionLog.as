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
package com.majescomastek.jbeam.model.vo
{
	import mx.collections.ArrayCollection;

	public class InstructionLog
	{
		public function InstructionLog()
		{
		}
				
		private var _installationCode:String;
		private var _seqNo:Number;
		private var _batchNo:Number;
		private var _batchRevNo:Number;
		private var _message:String;
		private var _messageParam:String;
		private var _instructingUser:String;
		private var _instructionTime:Number;
		private var _batchAction:String;
		private var _batchActionTime:Number;		
		private var _instructionParameters:ArrayCollection;
		
		
		public function set installationCode(value:String):void
		{ 
			_installationCode = value;
		}        
		
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set seqNo(value:Number):void
		{ 
			_seqNo = value;
		}        
		
		public function get seqNo():Number
		{ 
			return _seqNo;
		}
		public function set batchNo(value:Number):void
		{ 
			_batchNo = value;
		}        
		
		public function get batchNo():Number
		{ 
			return _batchNo;
		}
		public function set batchRevNo(value:Number):void
		{ 
			_batchRevNo = value;
		}        
		
		public function get batchRevNo():Number
		{ 
			return _batchRevNo;
		}
		
		public function set message(value:String):void
		{ 
			_message = value;
		}        
		
		public function get message():String
		{ 
			return _message;
		}
		public function set messageParam(value:String):void
		{ 
			_messageParam = value;
		}        
		
		public function get messageParam():String
		{ 
			return _messageParam;
		}
		public function set instructingUser(value:String):void
		{ 
			_instructingUser = value;
		}        
		
		public function get instructingUser():String
		{ 
			return _instructingUser;
		}
		public function set batchAction(value:String):void
		{ 
			_batchAction = value;
		}        
		
		public function get batchAction():String
		{ 
			return _batchAction;
		}
		public function set batchActionTime(value:Number):void
		{ 
			_batchActionTime = value;
		}        
		
		public function get batchActionTime():Number
		{ 
			return _batchActionTime;
		}
		
		public function set instructionTime(value:Number):void
		{ 
			_instructionTime = value;
		}        
		
		public function get instructionTime():Number
		{ 
			return _instructionTime;
		}
		
		public function set instructionParameters(value:ArrayCollection):void
		{ 
			_instructionParameters = value;
		}        
		
		public function get instructionParameters():ArrayCollection
		{ 
			return _instructionParameters;
		}
		
		
	}
}