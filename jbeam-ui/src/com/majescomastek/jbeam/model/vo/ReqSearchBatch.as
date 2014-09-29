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
	public class ReqSearchBatch extends BaseValueObject
	{
		public function ReqSearchBatch()
		{
			super();
		}

		private var _installationCode:String;
		private var _batchNo:int;
		private var _batchName:String;
		private var _batchType:String;
		private var _batchDate:String;
		private var _batchEndReason:String;
		private var _instructionSeqNo:String;

		public function get instructionSeqNo():String
		{
			return _instructionSeqNo;
		}

		public function set instructionSeqNo(value:String):void
		{
			_instructionSeqNo = value;
		}

		
		public function set installationCode(value:String):void
	    { 
	    	_installationCode = value;
    	}        
	   
		public function get installationCode():String
		{ 
			return _installationCode;
		}
		
		public function set batchNo(value:int):void
	    { 
	    	_batchNo = value;
    	}        
	   
		public function get batchNo():int
		{ 
			return _batchNo;
		}
		
		public function set batchName(value:String):void
	    { 
	    	_batchName = value;
    	}        
	   
		public function get batchName():String
		{ 
			return _batchName;
		}
		
		public function set batchType(value:String):void
	    { 
	    	_batchType = value;
    	}        
	   
		public function get batchType():String
		{ 
			return _batchType;
		}
		
		public function set batchDate(value:String):void
	    { 
	    	_batchDate = value;
    	}        
	   
		public function get batchDate():String
		{ 
			return _batchDate;
		}
		
		public function set batchEndReason(value:String):void
	    { 
	    	_batchEndReason = value;
    	}        
	   
		public function get batchEndReason():String
		{ 
			return _batchEndReason;
		}
	}
}