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
  public class BaseValueObject
  { 
     public function BaseValueObject()
     {
       //TODO: Implement function
     }
     
     private var _successFailureFlag:String;
     
     private var _exceptionMessage:String;
     
     private var _systemActivityNumber:String;
     
     private var _systemActivityCode:String;
     
     private var _sessionId:String;
     
     private var _responseTime:String;
     
     
     public function get successFailureFlag():String {
       return this._successFailureFlag;
     }
     
     public function set successFailureFlag(value:String):void {
       this._successFailureFlag = value; 
     }
     
     public function get exceptionMessage():String {
       return this._exceptionMessage;
     }
     
     public function set exceptionMessage(value:String):void {
       this._exceptionMessage = value; 
     }
     
     public function get systemActivityNumber():String {
       return this._systemActivityNumber;
     }
     
     public function set systemActivityNumber(value:String):void {
       this._systemActivityNumber = value; 
     }
     
     public function get systemActivityCode():String {
       return this._systemActivityCode;
     }
     
     public function set systemActivityCode(value:String):void {
       this._systemActivityCode = value; 
     }
     
     public function get sessionId():String {
       return this._sessionId;
     }
     
     public function set sessionId(value:String):void {
       this._sessionId = value; 
     }
     
     public function get responseTime():String {
       return this._responseTime;
     }
     
     public function set responseTime(value:String):void {
       this._responseTime = value; 
     }
        
  }

}