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
package com.majescomastek.flexcontrols
{
	import com.majescomastek.jbeam.common.CommonUtils;
	
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.controls.Text;
	import mx.events.FlexEvent;

	/**
	 * The text class extended to show the current time in the
	 * same way as would a digital clock.
	 */
	public class DigitalClock extends Text
	{
		private var _autoStart:Boolean;
		
		private var _timer:Timer;
		
		private var _started:Boolean;
		
		/**
		 * The timezone offset in case you need to show a timer for a different timezone.
		 * E.g. if the current timezone is IST, you might want to show the timer using
		 * the PST timezone. This offset helps us arrive at that date.
		 */
		private var _timezoneOffset:Number;
		
		private var _timezoneOffsetSet:Boolean;
		
		public function DigitalClock(autoStart:Boolean=true)
		{
			super();
		
			if(autoStart)
			{
				addEventListener(FlexEvent.CREATION_COMPLETE, initializeClock, false, 0, true);
			}
		}
		
		private function initializeClock(event:FlexEvent):void
		{
			 start();
		}
		
		public function start():void
		{
			if(!_started)
			{
				startClock();
			}		
		}
		
		public function stop():void
		{
			if(_started)
			{
				_started = false;
				_timer.stop();
			}
		}
		
		private function startClock():void
		{
			_timer = new Timer(1000);
			_timer.addEventListener(TimerEvent.TIMER, updateTime, false, 0, false);
			_timer.start();
			_started = true;
		}
		
		private function updateTime(event:TimerEvent):void
		{
			var dt:Date = new Date();
			if(_timezoneOffsetSet)
			{
				dt.setTime(dt.getTime() + dt.getTimezoneOffset() * 1000 * 60 + _timezoneOffset);
			}			
			super.text = CommonUtils.formatDate(dt);//.toLocaleString();
		}
		
		public function set timezoneOffset(value:Number):void
		{
			_timezoneOffset = value;
			_timezoneOffsetSet = true;
		}
		
	}
}