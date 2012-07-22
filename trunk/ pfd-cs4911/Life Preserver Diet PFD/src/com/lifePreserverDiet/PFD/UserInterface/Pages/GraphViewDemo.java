package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.Calendar;
import java.util.Date;
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
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

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
	
	private Date[] getWeek(Date d){
		d.setTime(d.getTime() - 24*60*60*1000);
		Date[] dates = new Date[7];
		// Calendar gives Sun - Sat as 1 - 7, so we get Mon - Sat first
		// and then find the next Sun
		for (int i = 1; i < 8; i++){
			if(i == d.getDay())
				dates[i-1] = d;
			else{
				Date d1 = new Date();
				d1.setTime( d.getTime() - (d.getDay() - i)*(24*60*60*1000) );
				dates[i-1] = d1;
			}
		}

		// Make sure the dates are actually a Mon - Sun set
		System.out.println();
		for (int i = 0; i < 7; i++)
			System.out.println((dates[i].getDay() + 1 == (i+1)%7+1) + ", " + dates[i]);
		System.out.println();
		
		return dates;
	}
	
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

		//System.out.println(getListAdapter().getCount());

		GraphViewData[] actualData = new GraphViewData[4];
		int[] shares = new int[8];
		Date[] dates = new Date[7];
		GraphViewData[][] data = new GraphViewData[dates.length][shares.length];
		
		if(getListAdapter().getCount() > 0){
			//Day day = (Day) getListAdapter().getItem(0);
			
			// Getting the date of the first Day in the db
			Date date = ((Day) getListAdapter().getItem(0)).getDate();
			
			//System.out.println(date);
			//System.out.println(new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(date));
			
			// Getting the Day matching the date. So for a history page,
			// the user's chosen date gets passed in instead.
			Day day = datasource.getDay(date);

			//System.out.println("day is tue: " + (day.getDate().getDay() + 1 == Calendar.TUESDAY));
			//System.out.println("day is wed: " + (day.getDate().getDay() + 1 == Calendar.WEDNESDAY));
			
			// Creating plot data
			// x = share type in the shares array (so x = 2 = dairy)
			// y = share amount
			/*
			if(day != null){
				shares = new int[] {0, day.getWholeGrains(), day.getDairy(),
						day.getMeatBeans(), day.getFruit(), day.getVeggies(),
						day.getExtra(), day.getExerciseMinutes()};
			}
			else{
				for (int i = 0; i < shares.length; i++)
					shares[i] = 0;
			}
			 */
			
			dates = getWeek(date);
			for (int j = 0; j < dates.length; j++){
				Day d = datasource.getDay(dates[j]);
				if(d != null){
					shares = new int[] {0, d.getWholeGrains(), d.getDairy(),
							d.getMeatBeans(), d.getFruit(), d.getVeggies(),
							d.getExtra(), d.getExerciseMinutes()};
				}
				else
					shares = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
				
				for (int i = 0; i < shares.length; i++){
					data[j][i] = new GraphViewData(i, Integer.valueOf(shares[i]).doubleValue());
					//System.out.println("share value: " + shares[i]);
				}
			}
			
			// Put data into GraphViewData array
			/*
			actualData = new GraphViewData[shares.length];
			for (int i = 0; i < actualData.length; i++){
				actualData[i] = new GraphViewData(i, Integer.valueOf(shares[i]).doubleValue());
				//System.out.println("share value: " + shares[i]);
			}*/
		}
		// Default data if the db is empty
		else{
			
			actualData = new GraphViewData[shares.length];
			for (int i = 1; i < actualData.length; i++)
				actualData[i] = new GraphViewData(i, 1.0d);
			actualData[0] = new GraphViewData(0, 0.0d);
			
			data = new GraphViewData[1][shares.length];
			for (int i = 1; i < shares.length; i++)
				data[0][i] = new GraphViewData(i, 1.0d);
			data[0][0] = new GraphViewData(0, 0.0d);
		}

		/*
		GraphViewData[] idealData = new GraphViewData[] {
				new GraphViewData(0, 0.0d) // need to create a point for the origin
				, new GraphViewData(1, 3.0d)
				, new GraphViewData(2, 2.5d)
				, new GraphViewData(2.5, 3.0d) // another frequency
				, new GraphViewData(3, 3.5d)
				, new GraphViewData(4, 7.0d)
				, new GraphViewData(5, 5.0d)
				, new GraphViewData(12, 2.6d) // point beyond viewpoint (to test scrolling)
		};*/
		
		// graph with dynamically generated horizontal and vertical labels
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, "Graph Title");
		else
			graphView = new LineGraphView(this, "Graph Title");
		
		// Add the data
		
		//graphView.addSeries(new GraphViewSeries("Target",
			//	new GraphViewStyle(Color.rgb(150, 62, 35), 3), idealData));
		//graphView.addSeries(new GraphViewSeries("Actual", null, actualData));
		
		
		// Add the data
		String[] seriesTitles = new String[] { "Whole Grains", "Dairy",
				"Meat/Beans", "Fruit", "Veggies", "Extra", "Exercise" };
		Color[] seriesColors = new Color[seriesTitles.length];
		for (int i = 0; i < data.length; i++){
			int c = Color.rgb((int)(Math.random()*256), (int)(Math.random()*256),
					(int)(Math.random()*256));
			graphView.addSeries( new GraphViewSeries(
					seriesTitles[i], new GraphViewStyle(c, 3), data[i]) );
		}
		
		graphView.setViewPort(0, shares.length + 2);
		graphView.setManualYAxisBounds(8, 0);
		//graphView.setScrollable(true);
		//graphView.setScalable(true);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		//graphView.setLegendWidth(100);

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
			
			date.setMonth(Calendar.JULY);
			date.setYear(2012);
			
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