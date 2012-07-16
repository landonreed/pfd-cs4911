package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewStyle;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.LineGraphView;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.DayDataSource;
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
public class GraphViewDemo extends ListActivity {
	private DayDataSource datasource;
	
	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_graphs);

		datasource = new DayDataSource(this);
		datasource.open();

		List<Day> values = datasource.getAllDays();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<Day> adapter = 
				new ArrayAdapter<Day>(this, android.R.layout.simple_list_item_1, values);
		
		setListAdapter(adapter);
		
		
		///////////////////////////////////////////////////////////////////////////////////

		GraphViewData[] actualData;
		
		if(getListAdapter().getCount() > 0){
			
		Day day = (Day) getListAdapter().getItem(0);
		int[] shares = {0, day.getWholeGrains(), day.getDairy(), day.getMeatBeans(),
				day.getFruit(), day.getVeggies(), day.getExtra(), day.getExerciseMinutes()};		
		
		actualData = new GraphViewData[ shares.length ];
		for (int i = 0; i < shares.length; i++){
			actualData[i] = new GraphViewData(i, Integer.valueOf(shares[i]).doubleValue() );
			System.out.println("share value: " + shares[i]);
		}
		
		}
		else{
		
		actualData = new GraphViewData[] {
				new GraphViewData(0, 0.0d) // need to create a point for the origin
				, new GraphViewData(1, 2.0d)
				, new GraphViewData(2, 1.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 2.5d)
				, new GraphViewData(4, 6.0d)
				, new GraphViewData(5, 3.0d)
				, new GraphViewData(12, 2.3d) // point beyond viewpoint (to test scrolling)
		};
		
		}

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
		//graphView.addSeries(new GraphViewSeries("Target",
			//	new GraphViewStyle(Color.rgb(150, 62, 35), 3), idealData));
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

		/**
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
		*/
	}
	
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Day> adapter = (ArrayAdapter<Day>) getListAdapter();
		Day day = null;
	
		switch (view.getId()) {
		case R.id.add:
			// random share values
			int[] shares = new int[7];
			for (int i=0; i<shares.length; i++)
				shares[i] = (int) (Math.random() * 4 + 1);
			int wholeGrains = shares[0];
			int dairy = shares[1];
			int meatBeans = shares[2];
			int fruit = shares[3];
			int veggies = shares[4];
			int extra = shares[5];
			int exercise_minutes = shares[6];
			
			boolean exercise = (exercise_minutes > 0) ? true : false;
			
			// random date
			java.util.Date date = new java.util.Date(); 
			date.setTime((long) (date.getTime()*Math.random()));
			
			// create new day
			day = datasource.createDay(date, wholeGrains, dairy, meatBeans,
					fruit, veggies, extra, exercise, exercise_minutes);
			
			// save new day to database
			adapter.add(day);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				day = (Day) getListAdapter().getItem(0);
				datasource.deleteDay(day);
				adapter.remove(day);
			}
			break;
		}
		
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}