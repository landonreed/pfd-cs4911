package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewStyle;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.LineGraphView;

import com.lifePreserverDiet.PFD.R;

/**
 * GraphViewDemo creates some dummy data to demonstrate the GraphView component.
 *
 * IMPORTANT: For examples take a look at GraphView-Demos (https://github.com/jjoe64/GraphView-Demos)
 *
 * Copyright (C) 2011 Jonas Gehring
 * Licensed under the GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/licenses/lgpl.html
 */
public class GraphViewDemo extends Activity {
	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_graphs);

		GraphViewData[] actualData = new GraphViewData[] {
				new GraphViewData(0, 0.0d) // need to create a point for the origin
				, new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 6.0d)
				, new GraphViewData(5, 3.0d)
				, new GraphViewData(12, 2.3d) // point beyond viewpoint (to test scrolling)
		};

		GraphViewData[] idealData = new GraphViewData[] {
				new GraphViewData(0, 0.0d) // need to create a point for the origin
				, new GraphViewData(1, 3.0d)
				, new GraphViewData(2, 2.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 3.5d)
				, new GraphViewData(4, 7.0d)
				, new GraphViewData(5, 5.0d)
				, new GraphViewData(12, 2.6d) // point beyond viewpoint (to test scrolling)
		};
		
		// graph with dynamically generated horizontal and vertical labels
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, "Graph Title");
		else
			graphView = new LineGraphView(this, "Graph Title");
		
		/*
		GraphView graphView = new LineGraphView(this, "example") {
			@Override
			protected String formatLabel(double value, boolean isValueX) {
				// convert unix time to human time
				if (isValueX)
			        return dateTimeFormatter.format(new Date((long) value*1000));
				// let the y-value be normal-formatted
				else
			    	return super.formatLabel(value, isValueX); 
			}
		};*/
		
		// add the data
		graphView.addSeries(new GraphViewSeries("Target",
				new GraphViewStyle(Color.rgb(150, 62, 35), 3), idealData));
		graphView.addSeries(new GraphViewSeries("Actual", null, actualData));
		
		// view bounds
		graphView.setViewPort(0, 4);
		graphView.setManualYAxisBounds(10, 0);
		
		// zoom and scale
		graphView.setScrollable(true);
		//graphView.setScalable(true);
		
		// legend
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);  
		graphView.setLegendWidth(100);

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		//layout.removeAllViews(); // replace old graph with new one
		layout.addView(graphView);

		//
		// graph with custom labels and drawBackground
		//
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, "Graph Title");
		else{
			graphView = new LineGraphView(this, "Graph Title");
			((LineGraphView) graphView).setDrawBackground(true);
		}
		
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries("Series 1", null, actualData);
		
		// custom static labels
		graphView.setHorizontalLabels(
				new String[] {"2 days ago", "yesterday", "today", "tomorrow"});
		graphView.setVerticalLabels(new String[] {"high", "middle", "low"});
		graphView.addSeries(exampleSeries); // data

		layout = (LinearLayout) findViewById(R.id.graph2);
		layout.addView(graphView);
	}
}