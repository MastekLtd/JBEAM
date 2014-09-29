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

import com.majescomastek.jbeam.common.CommonConstants;
import com.majescomastek.jbeam.model.vo.BatchDetailsData;
import com.majescomastek.jbeam.model.vo.BatchSummaryData;
import com.majescomastek.jbeam.model.vo.PerScanExecutionCountGraphData;

import mx.charts.ColumnChart;
import mx.charts.DateTimeAxis;
import mx.charts.HitData;
import mx.charts.LinearAxis;
import mx.charts.series.ColumnSeries;
import mx.charts.series.LineSeries;
import mx.collections.ArrayCollection;
import mx.graphics.Stroke;

private var X_MAX:Date = null; //The MAX value that X Axis can be plotted 
private var Y_MAX:Number = 10; //The MAX value that Y axis can be plotted 

private var X_MIN:Date = null; //The MIN value that X Axis can be plotted
private var Y_MIN:Number = 0; //The MIN value that Y Axis can be plotted

private static const X_CALIBRATION:Number = (new Date()).getTime(); 
private static const X_THRESHOLD:Number = (new Date()).getTime(); //Thrshold value for X axis (it will be Percentage value)  

private static const Y_CALIBRATION:Number = 10; 
private static const Y_THRESHOLD:Number = 10; //Thrshold value for Y axis (it will be Percentage value)

/** The event constant used to represent the request to refresh this pod */
public static const REFRESH_POD:String = "REFRESH_POD";
	
private function createXAxis():DateTimeAxis
{
	var xAxis:DateTimeAxis = new DateTimeAxis();
    xAxis.autoAdjust = true;
	xAxis.dataUnits = "seconds";
	xAxis.labelUnits= null;
	xAxis.displayLocalTime=false;
	xAxis.dataInterval = 5;
	xAxis.interval = 0;		
	xAxis.title = "Time";
	return xAxis;	
}

private function createYAxis():LinearAxis
{
	var yAxis:LinearAxis = new LinearAxis();
    yAxis.title = "Batch Objects";
	yAxis.minimum = Y_MIN;
	yAxis.maximum = Y_MAX; 
	yAxis.interval = Y_MAX / Y_CALIBRATION;
	return yAxis;
}


private function initChart(yAxis:LinearAxis, xAxis:DateTimeAxis):ColumnChart
{
	
	var bpmsColumnChart:ColumnChart;//Main chart
 
	var mySeries:Array=new Array();
	
	var lineSeries1:ColumnSeries;      
	//1) Create an array with initial values and set the ArrayCollection chartValues with that array.
	//Initialize  the array collection for the data
	var dataArr:Array = new Array(); 
	dataArr.push({bpmsObject:0, timeElapsed:new Date()});
	var chartValues:ArrayCollection = new ArrayCollection(dataArr);
	
	/* 
	2) Create the X Axis (xAxis) and Y- Axis (yAxis) (LinearAxis) and set following for both  
			i) title	ii) Minimum limit		iii) Maximum limit	iv) interval
	 */		
//	var yAxis:LinearAxis = createYAxis();	
//	var xAxis:DateTimeAxis = createXAxis();
	
	/*
	3) Create the chart object and
			Set the above created xAxis as horizontal axis and yAxis as vertical axis.
			Set the ArrayCollection (chartValues) as dataprovider for chart.
	  */
    bpmsColumnChart = new ColumnChart();
    bpmsColumnChart.percentHeight = 100;
    bpmsColumnChart.percentWidth = 100;
    bpmsColumnChart.showDataTips = true;
    bpmsColumnChart.horizontalAxis = xAxis;
	bpmsColumnChart.verticalAxis = yAxis;
    bpmsColumnChart.dataProvider = chartValues;
	bpmsColumnChart.dataTipFunction = columnChartDataTipFunction;
		
	//To remove shadow for a line series
	bpmsColumnChart.seriesFilters = [];
	
    /*   	4) Create line series
			Set the xField as bpmsObjects and yField as timeElapsed
			These values should map to the array created for chartValues 
	*/
    lineSeries1 = new ColumnSeries();
    lineSeries1.yField="bpmsObject";
    lineSeries1.xField="timeElapsed";
    lineSeries1.displayName = "Time";
    lineSeries1.useHandCursor = true;
//	lineSeries1.setStyle("form", "vertical");
            
    var s1:Stroke = new Stroke(0x0099FF,20,.2); //First 3 arguments are color, weight, and alpha.
    s1 = new Stroke(0x000000,2,1); //First 3 arguments are color, weight, and alpha.
	lineSeries1.setStyle("lineStroke", s1);
    mySeries.push(lineSeries1);
    
	/* 5) Assign the series array to chart's series */
    bpmsColumnChart.series = mySeries;
	return bpmsColumnChart;
	
}
/** The installation data used by this pod to show the installation details */
private var _batchDetails:BatchDetailsData;

/** The event constant used to denote the request to fetch the Per scan Execution count graph data */
public static const FETCH_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA:String = "FETCH_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA";

[Bindable]
public function get batchDetails():BatchDetailsData
{
	return _batchDetails;
}

public function set batchDetails(value:BatchDetailsData):void
{
	_batchDetails = value;
}

/**
 * Derive the state of the view variables based on the value of the
 * BatchDetailsData attributes.
 */
private function derivePageState():void
{
	this.title = "Per scan Execution count graph - Batch # "+ batchDetails.batchNo+ ": "+batchDetails.batchRevNo;
	this.fsPerScanExecutionCountGraph.label = "Per scan Execution count graph - Batch # "+ batchDetails.batchNo+ ": "+batchDetails.batchRevNo;
}

private var yObjectAxis:LinearAxis;	
private var xDateAxis:DateTimeAxis;;
/**
 * Handle the startup completion of this view.
 */
public function handleStartupComplete():void
{
	derivePageState();
	yObjectAxis = createYAxis();	
	xDateAxis = createXAxis();
	initChart(yObjectAxis, xDateAxis);
	sendDataEvent(FETCH_PER_SCAN_EXECUTION_COUNT_GRAPH_DATA, batchDetails);
}

/**
 * Handle the Per scan Execution count graph data retrieval.
 */
public function handlePerScanExecutionCountGraphDataRetrieval(data:Object):void
{
	var perScanExecutionCountGraphDataList:ArrayCollection = data["perScanExecutionCountGraphDataList"];
	var batchData:BatchSummaryData = data["batchSummaryData"];
	if(batchData != null)
	{
		batchDetails.execEndTime = batchData.execEndTime;
		batchDetails.batchEndReason = batchData.batchEndReason;
	}
	updateGraphData(perScanExecutionCountGraphDataList);
}

//Updates the chart data provider with new data retrieved from the webservices  
private function updateGraphData(graphDataList:ArrayCollection):void
{
//	var yAxis:LinearAxis = createYAxis();
//	var xAxis:DateTimeAxis = createXAxis();
	
	if(yObjectAxis == null)
	{
		yObjectAxis = createYAxis();
	}
	if(xDateAxis == null)
	{
		xDateAxis = createXAxis();		
	}
	
   	var chartValues:ArrayCollection = new ArrayCollection();
	var objBatchRevNo:Number = 1;
   	if(graphDataList != null)
   	{
   		for(var i:int = 0; i < graphDataList.length; i++ )
   		{
   			var perScanExecutionCountGraphData:PerScanExecutionCountGraphData = graphDataList[i]; 
   			var collectionDate:Date= new Date(perScanExecutionCountGraphData.collectTime);
			var collectedTime:Date = new Date((collectionDate.getTime() + 
					(collectionDate.getTimezoneOffset() * 60 * 1000) +  batchDetails.timezoneOffset));
	    	
	    	objBatchRevNo = perScanExecutionCountGraphData.batchRevNo;
	    	
	    	if(batchDetails.batchRevNo == objBatchRevNo)
	    	{
		    	chartValues.addItem({timeElapsed:collectedTime, bpmsObject:perScanExecutionCountGraphData.graphValue});
	    		
		    	//Recaliber the graph
//				if (perScanExecutionCountGraphData.graphValue > Y_MAX) {
//					Y_MAX = perScanExecutionCountGraphData.graphValue;
//			    	yObjectAxis.maximum = Y_MAX;
//				}
			    if(perScanExecutionCountGraphData.graphValue > Y_MAX)
			    {
					getChartLimitValue(perScanExecutionCountGraphData.graphValue, Y_THRESHOLD)
			    	yObjectAxis.maximum = Y_MAX;
		    	}
		    	if( X_MAX == null || collectedTime > X_MAX)
		    	{
			    	X_MAX = collectedTime ;//	    		 X_MAX * 2;
			    	xDateAxis.maximum = X_MAX;
					xDateAxis.dataInterval = 5;
					xDateAxis.dataUnits = "seconds";
			    }
	
			    if (X_MIN == null || collectedTime < X_MIN ) 
			    {
			    	X_MIN = collectedTime; 
			    	xDateAxis.minimum = X_MIN; 
			    }
	    	}	
		}//End of for loop
		
		if(batchDetails.batchRevNo == objBatchRevNo)
	    {
			var bpmsColumnChart:ColumnChart = initChart(yObjectAxis, xDateAxis);
	    	bpmsColumnChart.dataProvider = chartValues;
    		lineChartCanvas.addChild(bpmsColumnChart);
	    }				
    }//End of if(graphDataList != null)
}	
	
private function getChartLimitValue(maxValue:Number, thresholdValue:Number):void
{
	while (Y_MAX <= maxValue) {
		Y_MAX +=thresholdValue;
	}
	return;    		
}

private function columnChartDataTipFunction(o:HitData):String
{
	var s:String = "# of Objects = " + o.item.bpmsObject + "\n";
	s += "Time Elapsed = " + o.item.timeElapsed;
	return s;
}

/**
 * The function invoked by the mediator to request the pod to refresh
 * its contents.
 */
public function handleRefreshRequest():void
{
	if(!isBatchCompleted())
	{
		sendEvent(REFRESH_POD);
	}	
}
/**
 * Determine whether this batch is completed or not based on the 
 * batch status and the batch end time.
 */ 
private function isBatchCompleted():Boolean
{
	// If the batchStatus is CLOSURE & the batch end time
	// is not NULL, the batch has been completed.
	var completed:Boolean = this.batchDetails.execEndTime != null &&
							(this.batchDetails.batchEndReason == CommonConstants.BATCH_COMPLETED ||
							this.batchDetails.batchEndReason == CommonConstants.USER_INTERRUPTED);
	return completed; 
}

/**
 * Determine whether this installation is fresh (newly created) or not based on the 
 * batch number and batch revision number.
 */
private function isFreshInstallation():Boolean
{
	// If the batchNo & batchRevNo are null, the batch is invalid.
	var invalid:Boolean = this.batchDetails.batchNo == 0 && this.batchDetails.batchRevNo == 0;
	return invalid;
}
/**
 * Create the request data required for refreshing this pod along with the
 * mediator name for which the refresh should happen.
 */
public function getRequestDataForMediator(mediatorName:String):Object
{
	return {
		'batchDetails': batchDetails,
		'mediatorName': mediatorName
	};
}