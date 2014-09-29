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
	[Bindable]
	public class UserProfile extends BaseValueObject
	{
		public function UserProfile()
		{
			super();
		}

		private var _installationCode:String;

		private var _userId:String;
	    private var _userName:String;        
	    private var _userType:String;
	    private var _password:String;
	    private var _passwordEffDate:String;
	    private var _passwordExpDate:String;
	    private var _status:String;
	    private var _userLanguage:String;
	    private var _userCountry:String;
	    private var _loggerLevel:String;
	    private var _line:String;
	    private var _companyId:String;
	    private var _companyName:String;
	    private var _deptId:String;
	    private var _functionId:String;
	    private var _subFunctionId:String;
//	    private var _assignedRole:String;
	    private var _emailId:String;
	    private var _contactNumber:String;
	    private var _faxNumber:String;
	    private var _userFlag:String;
	    private var _authenticationType:String;
	    private var _dbUserId:String;
	    private var _effectiveDate:Number;
	    private var _expiryDate:Number;
	    private var _createdOn:Number;
	    private var _createdBy:String;
	    private var _sessionId:String;
	    private var _hintQuestion:String;
	    private var _hintAnswer:String;
	    
	    private var _adminRole:String;
	    private var _connectRole:String;
	    private var _forcePasswordFlag:String;
	    private var _defaultView:String;
	    
	    public function set installationCode(value:String):void
		{
			this._installationCode = value;
		}
		public function set userId(value:String):void
		{ 
			_userId = value;
		}
	    public function set userName(value:String):void
	    { 
	    	_userName = value;
    	}        
	    public function set userType(value:String):void
	    { 
	    	_userType = value;
	    }
	    public function set password(value:String):void
	    { 
	    	_password = value;
	    }
	    public function set passwordEffDate(value:String):void
	    { 
	    	_passwordEffDate = value;
	    }
	    public function set passwordExpDate(value:String):void
	    { 
	    	_passwordExpDate = value;
	    }
	    public function set status(value:String):void
	    { 
	    	_status = value;
	    }
	    public function set userLanguage(value:String):void
	    { 
	    	_userLanguage = value;
	    }
	    public function set userCountry(value:String):void
	    { 
	    	_userCountry = value;
	    }
	    public function set loggerLevel(value:String):void
	    { 
	    	_loggerLevel = value;
	    }
	    public function set line(value:String):void
	    { 
	    	_line = value;
	    }
	    public function set companyId(value:String):void
	    { 
	    	_companyId = value;
	    }
	    public function set companyName(value:String):void
	    { 
	    	_companyName = value;
	    }
	    public function set deptId(value:String):void
	    { 
	    	_deptId = value;
	    }
	    public function set functionId(value:String):void
	    { 
	    	_functionId = value;
	    }
	    public function set subFunctionId(value:String):void
	    { 
	    	_subFunctionId = value;
	    }
	    public function set emailId(value:String):void
	    { 
	    	_emailId = value;
	    }
	    public function set contactNumber(value:String):void
	    { 
	    	_contactNumber = value;
	    }
	    public function set faxNumber(value:String):void
	    { 
	    	_faxNumber = value;
	    }
	    public function set userFlag(value:String):void
	    { 
	    	_userFlag = value;
	    }
	    public function set authenticationType(value:String):void
	    { 
	    	_authenticationType = value;
	    }
	    public function set dbUserId(value:String):void
	    { 
	    	_dbUserId = value;
	    }
	    public function set effectiveDate(value:Number):void
	    { 
	    	_effectiveDate = value;
	    }
	    public function set expiryDate(value:Number):void
	    { 
	    	_expiryDate = value;
	   	}
	    public function set createdOn(value:Number):void
	    { 
	    	_createdOn = value;
	    }
	    public function set createdBy(value:String):void
	    { 
	    	_createdBy = value;
	    }
	    public function set hintQuestion(value:String):void
	    { 
	    	_hintQuestion = value;
	    }
	    public function set hintAnswer(value:String):void
	    { 
	    	_hintAnswer = value;
	    }
	    public function set adminRole(value:String):void
	    { 
	    	_adminRole = value;
	    }
	    public function set connectRole(value:String):void
	    { 
	    	_connectRole = value;
	    }
	    public function set forcePasswordFlag(value:String):void
	    { 
	    	_forcePasswordFlag = value;
	    }
	    public function set defaultView(value:String):void
	    { 
	    	_defaultView = value;
	    }
	    
	    //getters
	    public function get installationCode():String
		{
			return this._installationCode;
		}
		public function get userId():String
		{ 
			return _userId;
		}
	    public function get userName():String
	    { 
	    	return _userName;
    	}        
	    public function get userType():String
	    { 
	    	return _userType;
	    }
	    public function get password():String
	    { 
	    	return _password;
	    }
	    public function get passwordEffDate():String
	    { 
	    	return _passwordEffDate;
	    }
	    public function get passwordExpDate():String
	    { 
	    	return _passwordExpDate;
	    }
	    public function get status():String
	    { 
	    	return _status;
	    }
	    public function get userLanguage():String
	    { 
	    	return _userLanguage;
	    }
	    public function get userCountry():String
	    { 
	    	return _userCountry;
	    }
	    public function get loggerLevel():String
	    { 
	    	return _loggerLevel;
	    }
	    public function get line():String
	    { 
	    	return _line;
	    }
	    public function get companyId():String
	    { 
	    	return _companyId;
	    }
	    public function get companyName():String
	    { 
	    	return _companyName;
	    }
	    public function get deptId():String
	    { 
	    	return _deptId;
	    }
	    public function get functionId():String
	    { 
	    	return _functionId;
	    }
	    public function get subFunctionId():String
	    { 
	    	return _subFunctionId;
	    }
	    public function get emailId():String
	    { 
	    	return _emailId;
	    }
	    public function get contactNumber():String
	    { 
	    	return _contactNumber;
	    }
	    public function get faxNumber():String
	    { 
	    	return _faxNumber;
	    }
	    public function get userFlag():String
	    { 
	    	return _userFlag;
	    }
	    public function get authenticationType():String
	    { 
	    	return _authenticationType;
	    }
	    public function get dbUserId():String
	    { 
	    	return _dbUserId;
	    }
	    public function get effectiveDate():Number
	    { 
	    	return _effectiveDate;
	    }
	    public function get expiryDate():Number
	    { 
	    	return _expiryDate;
	   	}
	    public function get createdOn():Number
	    { 
	    	return _createdOn;
	    }
	    public function get createdBy():String
	    { 
	    	return _createdBy;
	    }
	    public function get hintQuestion():String
	    { 
	    	return _hintQuestion;
	    }
	    public function get hintAnswer():String
	    { 
	    	return _hintAnswer;
	    }
	    public function get adminRole():String
	    { 
	    	return _adminRole;
	    }
	    public function get connectRole():String
	    { 
	    	return _connectRole;
	    }
	    public function get forcePasswordFlag():String
	    { 
	    	return _forcePasswordFlag;
	    }
	    public function get defaultView():String
	    { 
	    	return _defaultView;
	    }

	}
}