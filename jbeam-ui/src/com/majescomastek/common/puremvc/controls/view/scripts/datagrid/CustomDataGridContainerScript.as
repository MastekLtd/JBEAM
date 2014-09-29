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
 * @author sanjayts
 * 
 *
 * $Revision:: 2                                                                                                       $
 *	
 * $Header:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/script $	
 *
 * $Log:: /Product_Base/Projects/Billing-UI/FLEX UI DEV/src/com/majescomastek/common/puremvc/controls/view/script $
 * 
 * 2     3/18/10 9:10a Sanjay.sharma
 * Updated CustomDataGridContainerScript
 * 
 * 1     3/05/10 4:27p Sanjay.sharma
 * Initial commit of base flex controls and advanced datagrid control
 * 
 * 5     3/05/10 3:02p Sanjay.sharma
 * Renamed BaseDataGridScript.as to CustomDataGridContainerScript.as
 * 
 * 4     3/05/10 9:52a Sanjay.sharma
 * Renamed DatagridScript.as to BaseDataGridScript.as
 * 
 * 3     3/04/10 5:11p Sanjay.sharma
 * Handled the condition wherein null was returned from the service
 * 
 * 2     3/03/10 4:35p Sandeepa
 * to make the file sync
 * 
 * 3     2/26/10 10:45a Sanjay.sharma
 * Updated script file for formatting, documentation and syncing it with ritesh's code
 * 
 * 2     2/25/10 8:15p Sanjay.sharma
 * added support for providing screen name
 * 
 * 1     2/25/10 7:31p Sanjay.sharma
 * Added script file
 * 
 * 
 */
import com.majescomastek.common.controls.framework.BaseDataGridColumn;
import com.majescomastek.common.events.PaginatedDataGridEvent;
import com.majescomastek.common.puremvc.controls.facade.datagrid.CustomDataGridContainerFacade;
import com.majescomastek.common.puremvc.controls.model.screenvo.DatagridPreferencesPopupScreenVO;
import com.majescomastek.jbeam.common.AlertBuilder;
import com.majescomastek.jbeam.model.vo.BaseValueObject;
import com.majescomastek.jbeam.model.vo.DatagridPreference;

import flash.events.ContextMenuEvent;
import flash.ui.ContextMenu;
import flash.ui.ContextMenuItem;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.events.ItemClickEvent;

import org.puremvc.as3.multicore.patterns.facade.Facade;

include "../../../../../../jbeam/common/CommonScript.as"

/** The event constant used to denote the cleanup request of this view */
public static const CLEANUP_DATAGRID:String = "CLEANUP_DATAGRID";

/** The event constant used to denote the clicking of the save button */
public static const SAVE_PREFERENCE_CLICK:String = "SAVE_PREFERENCE_CLICK"; 

/** The event constant used to denote the clicking of the change datagrid preferences 
 * menu item in the context menu.
 */
public static const CHANGE_DATAGRID_PREFERENCES_CLICK:String = 'CHANGE_DATAGRID_PREFERENCES_CLICK';

private var _paginationEnabled:Boolean = false;

/** it show no of columns in datagrid**/
private  var _columns:Array;

/** data provider  for grid **/
private  var _dataProvider:ArrayCollection;

/** Total no of row/record in database **/
private var _totalNoOfRows:Number;

/**  it show how many rows should be displayed in datagrid **/
private var _rowCount:Number = 10;
  
/** it show from where date fetch start **/
[Bindable]
private var _startIndex:Number;

/** it show to where data  fetch end **/
[Bindable]
private var _endIndex:Number;

/** total no of pages in pagination **/
[Bindable]
private var _totalNoOfPages:int;

/** use for poppulating page no  between prev and next link **/ 
[Bindable]
public var pageNumbers:ArrayCollection =new ArrayCollection();;

/** it show current page number **/
[Bindable]
private var _currentPageNo:int=1;

/** it is use for how many page no should show in itemPerpage component **/
[Bindable]
private var _itemPerPages:Number = 5;

/** it is use for how many page no shouls show in nexPrev componet **/ 
[Bindable]
private var _prevNextLinkSize:Number = 5;

/** use for showing page no in ItemPerPage component**/
[Bindable]
public var  itemPerPageArr:ArrayCollection = new ArrayCollection();

/** use for setting style name**/ 
private var _styleName:Object;

/** it show starting no of Start itemPerPage componet**/ 
private var _startItemPerPage:Number = 5;

/** it is use for putting difference  between itemPerPage component**/
private var _intervalItemPerPage:Number = 10;

/** controll visibility of ItemPerPage Component**/

private var _itemPerPageVisible:Boolean = true;

/** controll visibility of gotoPage componet **/

private var _gotoPageVisible:Boolean = true;

/** controll visibility of nextPrev component **/
[Bindable]
private var _nextPrevVisible:Boolean = true;

/** for making datagrid editable**/
private var _editable:Boolean = false;

/** set maxHeight of datagrid **/ 
private var _maxHeight:Number;

/** row height of datagrid **/
private var _rowHeight:Number;

/** make datagrid resizableColumns **/
private var _resizableColumns:Boolean = true;
 
private var _sortableColumns:Boolean = true;

private var _wordWrap:Boolean = false;

private var _headerWordWrap:Boolean;

private var _headerHeight:Number = 22;

/** The screen name used to identify the module/functionality when saving the datagrid preference */
private var _screenName:String;

[Bindable]
public function get screenName():String
{
	return _screenName;
}

public function set screenName(value:String):void
{
	_screenName = value;
}

[Bindable]
public function get paginationEnabled():Boolean
{
	return _paginationEnabled;
}

[Inspectable(category="Others", enumeration="false,true", defaultValue="false")]
public function set paginationEnabled(value:Boolean):void
{
	_paginationEnabled = value;
}

public function set columns(val:Array):void
{
	_columns = val;
	dispatchEvent(new Event("columnChange"));
}

[Bindable(event="columnChange")]
public function get columns():Array
{
	return _columns;
}

public function set dataProvider(val:ArrayCollection):void
{
	_dataProvider = val;
	dispatchEvent(new Event("dataProvider"));
	_endIndex = dataProvider.length;
}

[Bindable(event="dataProvider")]
public function get dataProvider():ArrayCollection
{
	return _dataProvider;
}

public function set rowCount(val:Number):void
{
	_rowCount = val;
	dispatchEvent(new Event("rowCount"));
}

[Bindable(event="rowCount")]	
public function get rowCount():Number
{
	return  _rowCount;
}

public function set totalNoOfRows(val:Number):void
{
	_totalNoOfRows = val;
	dispatchEvent(new Event("totalNoOfRows"));
}

[Bindable(event="totalNoOfRows")]
public function get totalNoOfRows():Number 
{
	return _totalNoOfRows;
}

public function set itemPerPages(val:Number):void
{
	_itemPerPages = val;
}

[Bindable]
public function get itemPerPages():Number
{
	return _itemPerPages;
}

public function set prevNextLinkSize(val:Number):void
{
	_prevNextLinkSize = val;
}

[Bindable]
public function get prevNextLinkSize():Number
{
	return _prevNextLinkSize;
}

public override function set styleName(val:Object):void
{
	_styleName = val;
	dispatchEvent(new Event('styleName'));
}

[Bindable(event="styleName")]
public override function get styleName():Object
{
	return _styleName;
}

public function set startItemPerPage(val:Number):void
{
	_startItemPerPage = val;
}

[Bindable]
public function get startItemPerPage():Number
{
	 return _startItemPerPage;
}

public function set intervalItemPerPage(val:Number):void
{
	_intervalItemPerPage = val;
}

[Bindable]
public function get intervalItemPerPage():Number
{
	return _intervalItemPerPage;
}

public function set itemPerPageVisible(val:Boolean):void
{
	_itemPerPageVisible = val;
	dispatchEvent(new Event('itemPerPageVisible'));
}

[Bindable(event='itemPerPageVisible')]
public function get itemPerPageVisible():Boolean
{
	return _itemPerPageVisible;
}

public function set gotoPageVisible(val:Boolean):void
{
	_gotoPageVisible = val; 
	dispatchEvent(new Event('gotoPageVisible'));
}

[Bindable (event='gotoPageVisible')]
public function get gotoPageVisible():Boolean
{
	return _gotoPageVisible;
}

public function set nextPrevVisible(val:Boolean):void
{
	_nextPrevVisible = val;
}

[Bindable]
public function get nextPrevVisible():Boolean
{
	return _nextPrevVisible;
}

public function set editable(val:Boolean):void
{
	_editable = val;
	dispatchEvent(new Event('editable'))
	
}

[Bindable(event="editable")]
public function get editable():Boolean
{
	return _editable;
}

public override function set maxHeight(val:Number):void
{
	_maxHeight = val;
	dispatchEvent(new Event('maxHeight'));
	
}
[Bindable(event="maxHeight")]
public override function get maxHeight():Number
{
	return _maxHeight;
}

public  function set rowHeight(val:Number):void
{
	_rowHeight = val;
	dispatchEvent(new Event('rowHeight'));
	
}
[Bindable(event="rowHeight")]
public  function get rowHeight():Number
{
	return _rowHeight;
}

public function set resizableColumns(val:Boolean):void
{
	_resizableColumns = val;
	dispatchEvent(new Event('resizableColumns'));
	
}
[Bindable(event="resizableColumns")]
public function get resizableColumns():Boolean
{
	return _resizableColumns;
}


public function set sortableColumns(val:Boolean):void
{
	_sortableColumns = val;
	dispatchEvent(new Event('sortableColumns'));
	
}

[Bindable(event='sortableColumns')]
public function get sortableColumns():Boolean
{
	return _sortableColumns;
}


public function set wordWrap(val:Boolean):void
{
	_wordWrap = val;
	dispatchEvent(new Event('wordWrap'));
	
}
[Bindable(event='wordWrap')]
public function get wordWrap():Boolean
{
	return _wordWrap;
}

public function set headerWordWrap(val:Boolean):void
{
	_headerWordWrap = val;
	dispatchEvent(new Event('headerWordWrap'));
	
}

[Bindable(event='headerWordWrap')]
public function get headerWordWrap():Boolean
{
	return _headerWordWrap;
}

public function set headerHeight(val:Number):void
{
	_headerHeight =  val;
	dispatchEvent(new Event('headerHeight'));
	
}

[Bindable (event='headerHeight')]
public function get headerHeight():Number
{
	return _headerHeight;
}

			
/** call when component get created **/
private function init(event:Event):void
{	
	if(paginationEnabled)
	{
		initializePagination();
	}
	var facade:CustomDataGridContainerFacade = CustomDataGridContainerFacade.getInstance(this.id + CustomDataGridContainerFacade.NAME);
	facade.startup(this);
	insertContextMenu();
	dataGridId.contextMenu = customContextMenu;
}

[Bindable]
private var customContextMenu:ContextMenu;

private function insertContextMenu():void
{
	 customContextMenu = new ContextMenu();
	customContextMenu.hideBuiltInItems();
	var contextMenuItem:ContextMenuItem = new ContextMenuItem("Change Grid Preferences");
	contextMenuItem.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, onChangePreferencesClick);
	var customItemsArr:Array = new Array();
	customItemsArr.push(contextMenuItem);
	customContextMenu.customItems = [contextMenuItem];
	dataGridId.contextMenu = customContextMenu; 
	
		/* m = new ContextMenu();
	    m.hideBuiltInItems();
	    var customItemsArr:Array = new Array();
	    var buy:ContextMenuItem = new ContextMenuItem("Buy");
	    buy.addEventListener("menuItemSelect", onChangeSettingsClick);
	    customItemsArr.push(buy);
	    m.customItems = customItemsArr; */
}

private function onChangePreferencesClick(event:ContextMenuEvent):void
{
	sendEvent(CHANGE_DATAGRID_PREFERENCES_CLICK);
}

public function getDatagridPreferences(popupView:DatagridPreferencesPopup):DatagridPreferencesPopupScreenVO
{
	var datagridPreferencesVO:DatagridPreferencesPopupScreenVO = new DatagridPreferencesPopupScreenVO();
	datagridPreferencesVO.targetDatagrid = dataGridId;
	datagridPreferencesVO.dataGridId = this.id;
	datagridPreferencesVO.view = popupView;
	datagridPreferencesVO.screenName = screenName;
	
	return datagridPreferencesVO;
}

private function onRemovedFromStage(event:Event):void
{
	Facade.removeCore(this.id + CustomDataGridContainerFacade.NAME);
}	

private function initializePagination():void
{
	// adding listener for component 
	addEventListener(PaginatedDataGridEvent.PREVIOUS_CLICK_EVENT, previousClickHandler, true);
	addEventListener(PaginatedDataGridEvent.NEXT_CLICK_EVENT, nextClickHandler, true);
	addEventListener(PaginatedDataGridEvent.PAGE_NO_CLICK_EVENT, pageNoClickHandler, true);
	addEventListener(PaginatedDataGridEvent.ITEM_PER_PAGE_CLICK_EVENT, itemPerPageClickHandler, true);
	addEventListener(PaginatedDataGridEvent.GOTO_PAGE_CLICK_EVENT, goToPageClickHandler, true);
	
	 // initialy start index is zero
	_startIndex = 0;
	
	calculateTotalnoOfPage();
	populateArrForItemPerPgae();
}

/**populating data for itemPerPage componet **/
private function populateArrForItemPerPgae():void
{
	var dispNo:int = startItemPerPage;
	for(var i:int=1; i <= itemPerPages && dispNo< _totalNoOfRows; i++  )
	{
		itemPerPageArr.addItem(dispNo);
		dispNo += intervalItemPerPage;
	}
}

/** this component use for calculating total no pages  **/
private function calculateTotalnoOfPage():void
{
	_totalNoOfPages = _totalNoOfRows/_rowCount + (_totalNoOfRows%_rowCount>0 ?1:0)
	 pageNumbers.removeAll();
	 for(var i:int=0; i< _totalNoOfPages && i < _prevNextLinkSize; i++)
	 {
	 	pageNumbers.addItem(i+1);
	 }
}

/**
 *  get call when user click on next link 
 *  and also send request for feching data
 */
public function nextClickHandler(event:Event):void
{	
	_startIndex = _endIndex;
	_endIndex  = _totalNoOfRows > _rowCount+_startIndex ?  _rowCount+_startIndex : _totalNoOfRows;
	var leastNo:Number = pageNumbers.getItemAt(pageNumbers.length-1) as Number;
	if(leastNo == _currentPageNo && leastNo != _totalNoOfPages)
	{
		pageNumbers.removeItemAt(0);
		pageNumbers.addItemAt(_currentPageNo+1, pageNumbers.length);
	}
	_currentPageNo++;
	
	sendPaginatedEvent(PaginatedDataGridEvent.NEXT_CLICK_EVENT);
	sendPaginatedEvent(PaginatedDataGridEvent.FETCH_DATA_EVENT);
}

/**
 *  get call when user click on previouslink 
 *  and also send request for feching data
 */
public function previousClickHandler(event:PaginatedDataGridEvent):void
{
	_endIndex -= dataProvider.length;
	_startIndex = _endIndex-rowCount >0 ? _endIndex-rowCount :0;
	var leastNo:Number = pageNumbers.getItemAt(0) as Number;
	if(leastNo == _currentPageNo && leastNo != 1)
	{
		pageNumbers.removeItemAt(pageNumbers.length-1);
		pageNumbers.addItemAt(_currentPageNo-1,0);
	}
	
	_currentPageNo--;
	sendPaginatedEvent(PaginatedDataGridEvent.PREVIOUS_CLICK_EVENT);
	sendPaginatedEvent(PaginatedDataGridEvent.FETCH_DATA_EVENT);
	 
}

/** use for dispatching event **/
private function sendPaginatedEvent(eventName:String):void
{
	var obj:Object = new  Object();
	obj["startIndex"] = _startIndex;
	obj["endIndex"] = _endIndex;
	var customEvent:PaginatedDataGridEvent =new PaginatedDataGridEvent(eventName,obj );
	dispatchEvent(customEvent);
}

/** get call when user specifiy page no and click on button **/
private function goToPageClickHandler(event:PaginatedDataGridEvent):void
{	
	var gotoPage:Number = new Number( event.eventData);
	
	if(gotoPage > _totalNoOfPages || gotoPage<1 )
	{
		Alert.show("Out of range");
	}
	else
	{	
		jumpOnPage(gotoPage);
	}
	
	ItemClickEvent
}

/** this function use for settin current page and shift the link of nextPrev component **/
private function jumpOnPage(gotoPage:Number):void
{	
	_currentPageNo = gotoPage;
	shiftLinkWhenGoToPageClick();
	_startIndex = (gotoPage-1)* rowCount
	_endIndex = (gotoPage * rowCount)< _totalNoOfRows ? gotoPage* rowCount : totalNoOfRows; 
	
	sendPaginatedEvent(PaginatedDataGridEvent.PAGE_NO_CLICK_EVENT);
	sendPaginatedEvent(PaginatedDataGridEvent.FETCH_DATA_EVENT);
	
	
}

/** shift  pagenumber in next prev component when reqired */ 
private function shiftLinkWhenGoToPageClick():void
{
	var upperleastNo:Number = pageNumbers.getItemAt(pageNumbers.length-1) as Number;
	var lowerLeastNo:Number = pageNumbers.getItemAt(0) as Number;
	
	// if page no comes under the disply numbers
	if(_currentPageNo >= lowerLeastNo && _currentPageNo <= upperleastNo)
	{
		if(upperleastNo == _currentPageNo && upperleastNo != _totalNoOfPages)
		{
			pageNumbers.removeItemAt(0);
			pageNumbers.addItemAt(_currentPageNo+1, pageNumbers.length);
		}
		else
		{
			
			if(lowerLeastNo == _currentPageNo && lowerLeastNo != 1)
			{
				pageNumbers.removeItemAt(pageNumbers.length-1);
				pageNumbers.addItemAt(_currentPageNo-1,0);
			}
		}
	}
	
	// when page no is out side of disply component list
	else
	{	pageNumbers.removeAll();
		if(_currentPageNo < lowerLeastNo )
		{	
			for(var i:int=0;i < prevNextLinkSize; i++)
			{
				pageNumbers.addItem(_currentPageNo + i);
				
			}
		}
		else
		{
			var tempArr:Array = new Array();
			for( i=0;i < prevNextLinkSize; i++)
			{
				tempArr.push(_currentPageNo - i);
			}
			tempArr = tempArr.reverse();
			for( i=0;i < tempArr.length; i++)
			{
				pageNumbers.addItem(tempArr[i]);
			}
			tempArr = null;
		}
	}
	
}

/* get call when user click o page number */
private function pageNoClickHandler(event:PaginatedDataGridEvent):void
{
	var pageNo:Number = new Number(event.eventData);
	jumpOnPage(pageNo);
}

/* get call when user click on itemPerPages */
private function itemPerPageClickHandler(event:PaginatedDataGridEvent):void
{
	_rowCount = new Number(event.eventData);
	_startIndex = 0;
	_endIndex  = _totalNoOfRows > _rowCount+_startIndex ?  _rowCount+_startIndex : _totalNoOfRows;
	calculateTotalnoOfPage();
	sendPaginatedEvent(PaginatedDataGridEvent.FETCH_DATA_EVENT);
}

public function cleanup():void
{
	sendEvent(CLEANUP_DATAGRID);
}

/**
 * Retrieve the notification data required for retrieving a datagrid preference.
 */
public function getNotificationData():Object
{
	//TODO: Find some way of abstracting screen name
	var data:Object = {
		'datagridName': this.id,
		'screenName': screenName
	};
	return data;
}

/**
 * Handle the success of the datagrid preference retrieval
 */
public function handleRetrievalSuccess(body:Object):void
{
	// Move this part in a Command and from the command send a notification
	// which contains the arraycollection as notification data.
	var preference:DatagridPreference = DatagridPreference(body);
	var array:Array = [];
	if(preference)
	{
		var xml:XML = new XML(preference.datagridColumns);	
		for each(var column:XML in xml.column)
		{
			// populate `array` with the ID's of the columns to be shown
			// the element index would reflect the actual index of the
			// desired column on the screen
			array.push(column.text().toString());
		}
		showColumns(new ArrayCollection(array));	
	}
	else
	{
		showAllColumns();
	}
}

private function showColumns(visibleColumns:ArrayCollection):void
{
	var newDgColumnsArray:Array = new Array();
	var dgColumns:ArrayCollection = new ArrayCollection(dataGridId.columns);
	hideAllColumns();
	
	// When we specify an ID for a component, what really happens is that
	// a property with the said name is created in the parent document.
	// This is the reason that we had to create a new custom datagrid column
	// with a new `identifier' property to keep track of the uniqueness of columns.
	//
	// http://stackoverflow.com/questions/1256535/getelementbyid-equivalent-in-actionscript
	for(var i:uint = 0, maxI:uint = visibleColumns.length; i < maxI; ++i) 
	{
		var id:String = String(visibleColumns.getItemAt(i));
		for each(var dgColumn:BaseDataGridColumn in dgColumns)
		{
			if(dgColumn.identifier == id)
			{
				dgColumn.visible = true;
				newDgColumnsArray.push(dgColumn);
			}
		}
	}
	
	for each(var dgColumn:BaseDataGridColumn in dgColumns)
	{
		if(isColumnPresent(newDgColumnsArray, dgColumn) == false)
		{
			newDgColumnsArray.push(dgColumn);
		}		
	}
	dataGridId.columns = newDgColumnsArray;
}

/**
 * Determine whether the column `dgColumnToCheck` is already present in the `dgColumns` array.
 */
private function isColumnPresent(dgColumnsArray:Array, dgColumnToCheck:BaseDataGridColumn):Boolean
{
	var isPresent:Boolean = false;
	for(var i:uint = 0, maxI:uint = dgColumnsArray.length; i < maxI; ++i) 
	{
		var dgColumn:BaseDataGridColumn = dgColumnsArray[i];
		if(dgColumn == dgColumnToCheck)
		{
			isPresent = true;
			break;
		}
	}
	return isPresent;
}

/**
 * Reset the state of the columns; hide all columns.
 */
private function hideAllColumns():void
{
	var dgColumns:ArrayCollection = new ArrayCollection(dataGridId.columns);
	for each(var dgColumn:BaseDataGridColumn in dgColumns)
	{
		dgColumn.visible = false;
	}
}

/**
 * Reset the state of the columns; hide all columns.
 */
private function showAllColumns():void
{
	var dgColumns:ArrayCollection = new ArrayCollection(dataGridId.columns);
	for each(var dgColumn:BaseDataGridColumn in dgColumns)
	{
		dgColumn.visible = true;
	}
}

/**
 * Handle the success of the datagrid preference persistence
 */
public function handleSaveSuccess(body:Object):void
{
	if(body == null)	return;
	
	var bvo:BaseValueObject = BaseValueObject(body);
	AlertBuilder.getInstance().show(bvo.exceptionMessage);
}