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
include "../../../common/CommonScript.as"
import com.majescomastek.flexcontrols.MultilineLabel;
import com.majescomastek.jbeam.model.vo.ProcessRequestScheduleData;

import flash.events.Event;
import flash.events.MouseEvent;

import mx.containers.Panel;
import mx.containers.TitleWindow;
import mx.managers.PopUpManager;

private var scheduleData:ProcessRequestScheduleData;

[Bindable]
private var endReasonVisible:Boolean;

[Bindable]
private var lblEndReasonVisible:Boolean;

[Bindable]
private var schEndReasonTitle:String;

private var endReasonTitleWindow:TitleWindow = new TitleWindow();
private var endReasonPanel:Panel = new Panel();
private var endReasonText:MultilineLabel = new MultilineLabel();

override public function set data(value:Object):void
{
	if(value != null)
	{
		super.data = value;
		populateData();
	}
}

/**
 * Derive the state of the view variables based on the initialization data.
 */
private function derivePageState():void
{
	schEndReasonTitle = "End Reason for Schedule id # " + scheduleData.schId;
}

private function populateData():void
{
	scheduleData = this.data as ProcessRequestScheduleData;
	endReasonVisible = true;
	lblEndReasonVisible = false;
	
	if(scheduleData.endReason == null || scheduleData.endReason.length == 0)
	{
		endReasonVisible = false;
		lblEndReasonVisible = true;
		lblEndReason.text = "--";
		return;
	}
	derivePageState();
}

/**
 * This function is used to show the title window.  
 */
//			private function openPopup(className:Class):void 
//			{
//				if(scheduleData.endReason == null || scheduleData.endReason.length == 0)
//					return;
//				
//				var parent:ViewScheduleModule = ViewScheduleModule(this.parentDocument);
//				
//				var popupModule:IFlexDisplayObject = PopUpManager.createPopUp(this, className, true);
//				PopUpManager.centerPopUp(popupModule);
//
////				var className:EndReasonWindow = new EndReasonWindow();
////				(className as Window).open();
////				
//				var windowData:Object = {
//					'view': className,
//					'scheduleData': scheduleData
//				};
//				parent.sendDataEvent(ViewScheduleModule.END_REASON_CLICK, windowData);
////				parent.sendDataEvent(ViewScheduleModule.END_REASON_CLICK, scheduleData);
//			}

protected function imgEndReason_clickHandler(event:MouseEvent):void
{
	//				openPopup(EndReasonPopup);
	openWindow(event);
}

private function createEndReasonTitleWindow():void
{
	endReasonTitleWindow.title = schEndReasonTitle;
	endReasonTitleWindow.width  = 600;
	endReasonTitleWindow.height = 300;
	endReasonTitleWindow.styleName = "popupWindowBox";
	endReasonTitleWindow.layout = "vertical";
	endReasonTitleWindow.autoLayout = true;				
	endReasonTitleWindow.showCloseButton = true;
	endReasonTitleWindow.addEventListener("close", closeTitleWindow);	
}
// Method to instantiate and display a TitleWindow container.
// This is the initial Button control's click event handler.
public function openWindow(event:MouseEvent):void {
	// Set the TitleWindow container properties.
	// Call the method to add the Button control to the TitleWindow container.
	createEndReasonTitleWindow();
	populateWindow();
	
	// Use the PopUpManager to display the TitleWindow container.
	PopUpManager.addPopUp(endReasonTitleWindow, this, true);
	//				var parent:DisplayObject = this.parent;
	//				while(parent){
	//					trace(parent);
	//					trace(parent.height)
	//					trace(parent.width)
	//					parent = parent.parent;
	//				}
	//				PopUpManager.centerPopUp(endReasonTitleWindow);
}

// The method to create and add the Button child control to the
// TitleWindow container.
public function populateWindow():void {
	endReasonPanel.title = schEndReasonTitle;
	endReasonPanel.styleName = "styleA";
	endReasonPanel.percentWidth = 100;
	endReasonPanel.percentHeight = 100;
	
	endReasonText.text = scheduleData.endReason;
	endReasonText.percentWidth = 100;
	endReasonText.percentHeight = 100;
//	endReasonText.styleName = "normalText13";
	
	endReasonPanel.addChild(endReasonText);
	endReasonTitleWindow.addChild(endReasonPanel);
	
}

// The method to close the TitleWindow container.
public function closeTitleWindow(event:Event):void {
	PopUpManager.removePopUp(endReasonTitleWindow);				
}