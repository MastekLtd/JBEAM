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
 * @author Gourav Rai
 *
 * $Revision:: 1                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/Report.as 1     3/ $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/billing/model/vo/Report.as          $
 * 
 * 1     3/26/10 10:48a Gourav.rai
 * Added by Gourav Rai
 * 
 */
package com.majescomastek.jbeam.model.vo
{
	[Bindable]
	public class Report extends BaseValueObject
	{
		public function Report()
		{
			super();
		}
		
		private var _installationCode:String;
		private var _reportId:String;
		private var _reportName:String;
		private var _programName:String;
		private var _reportNo:String;
		private var _reportType:String;
		
		public function set installationCode(value:String):void
		{
			this._installationCode = value;
		}
		public function get installationCode():String
		{
			return this._installationCode;
		}		
		public function set reportId(value:String):void
		{
			this._reportId = value;
		}
		public function get reportId():String
		{
			return this._reportId;
		}		
		
		public function set reportName(value:String):void
		{
			this._reportName = value;
		}
		public function get reportName():String
		{
			return this._reportName;
		}		
				
		public function set programName(value:String):void
		{
			this._programName = value;
		}
		public function get programName():String
		{
			return this._programName;
		}
		
		public function set reportNo(value:String):void
		{
			this._reportNo = value;
		}
		public function get reportNo():String
		{
			return this._reportNo;
		}		
		
		public function set reportType(value:String):void
		{
			this._reportType = value;
		}		
		public function get reportType():String
		{
			return this._reportType;
		}
	}
}