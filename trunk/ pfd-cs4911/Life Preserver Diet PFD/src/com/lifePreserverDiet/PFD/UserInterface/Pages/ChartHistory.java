package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewStyle;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.LineGraphView;
import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

/**
 * ChartHistory displays a user's share history for a given week (the
 * current week is displayed on page open but controls are provided
 * to move backwards or forwards in time).
 * 
 * Minutes spent on exercise are displayed on the bottom graph.
 * The other share types are displayed on the top graph.
 */
public class ChartHistory extends Activity {
	private DayDataSource datasource;
	private Date myDate;
	
	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_chart_history);

		datasource = new DayDataSource(this);
		datasource.open();

		myDate = new Date(); // The graph should start on today's week
		makeGraph(myDate);
	}
	
	/**
	 * Move back or forward one week and display the new graph.
	 * 
	 * @param view The view
	 */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.prev:
			myDate.setTime(myDate.getTime() - 7 * (24*60*60*1000));
			makeGraph(myDate);
			break;
		case R.id.next:
			myDate.setTime(myDate.getTime() + 7 * (24*60*60*1000));
			makeGraph(myDate);
			break;
		}
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

	/**
	 * Returns an array of Dates matching the Sunday - Saturday week
	 * that contains the given date.
	 * 
	 * @param d The date whose week we want
	 * @return an array of Dates for the desired week
	 */
	private Date[] getWeek(Date d){
		Date[] dates = new Date[7];
		
		// Calendar gives Sun - Sat as 1 - 7, so we get Mon - Sat first
		// and then find the next Sun
		for (int i = 1; i < dates.length + 1; i++){
			if(d.getDay() + 1 == i)
				dates[i-1] = d;
			else{
				Date d1 = new Date();
				d1.setTime( d.getTime() - (d.getDay() + 1 - i)*(24*60*60*1000) );
				dates[i-1] = d1;
			}
		}

		/*
		// Make sure the dates are actually a Mon - Sun set
		System.out.println("");
		for (int i = 0; i < dates.length; i++)
			System.out.println((dates[i].getDay() + 1 == (i+1)%dates.length+1) + ", " + dates[i]);
		System.out.println("");
		*/
		
		return dates;
	}
	
	/**
	 * Creates and places a graph on the screen using for the week (Sun - Sat)
	 * containing the given date.
	 * 
	 * @param date The chosen date
	 */
	private void makeGraph(Date date){
		
		// Get the Dates for the desired week
		Date[] dates = getWeek(date);
		
		/* // Each array will contain a particular share's values for
		// the desired week and act as a data series for the graph.
		GraphViewData[] wholeGrains = new GraphViewData[dates.length];
		GraphViewData[] dairy = new GraphViewData[dates.length];
		GraphViewData[] meatBeans = new GraphViewData[dates.length];
		GraphViewData[] fruits = new GraphViewData[dates.length];
		GraphViewData[] veggies = new GraphViewData[dates.length];
		GraphViewData[] extra = new GraphViewData[dates.length];*/
		
		GraphViewData[] exercise = new GraphViewData[dates.length];
		
		GraphViewData[] idealData = new GraphViewData[dates.length];
		for (int i = 0; i < idealData.length; i++){
			idealData[i] = new GraphViewData(i, 100.0d);
		}
		
		GraphViewData[] actualData = new GraphViewData[dates.length];
		
		// Target number of shares for one day
		double targetTotal = 3 + 3 + 3 + 3 + 4 + 3;
		
		// Build the series if the database isn't empty
		if(datasource.getAllDays().size() > 0){
			for (int j = 0; j < dates.length; j++){
				Day d = datasource.getDay(dates[j]);
				if(d != null){
					/*wholeGrains[j] = new GraphViewData(j,
							Integer.valueOf( d.getWholeGrains() ).doubleValue());
					dairy[j] = new GraphViewData(j,
							Integer.valueOf( d.getDairy() ).doubleValue());
					meatBeans[j] = new GraphViewData(j,
							Integer.valueOf( d.getMeatBeans() ).doubleValue());
					fruits[j] = new GraphViewData(j,
							Integer.valueOf( d.getFruit() ).doubleValue());
					veggies[j] = new GraphViewData(j,
							Integer.valueOf( d.getVeggies() ).doubleValue());
					extra[j] = new GraphViewData(j,
							Integer.valueOf( d.getExtra() ).doubleValue());
					*/
					exercise[j] = new GraphViewData(j,
							Integer.valueOf( d.getExerciseMinutes() ).doubleValue());
					int total = d.getWholeGrains() + d.getDairy() + d.getMeatBeans() +
							d.getFruit() + d.getVeggies() + d.getExtra();
					actualData[j] = new GraphViewData(j, total/targetTotal * 100);
				}
				else{
					//wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
						//veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 0.0d);
					exercise[j] = new GraphViewData(j, 0.0d);
					actualData[j] = new GraphViewData(j, 0.0d);
				}
			}
		}
		// Default series if the database is empty
		else{
			for (int j = 0; j < dates.length; j++){
				//wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
					//veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 0.0d);
				exercise[j] = new GraphViewData(j, 0.0d);
				actualData[j] = new GraphViewData(j, 0.0d);
			}
		}

		// Generate this week's header
		java.text.SimpleDateFormat myFormat =
				new java.text.SimpleDateFormat("EEE, MMM dd, yyyy");
		String monday = myFormat.format(dates[0]);
		String sunday = myFormat.format(dates[6]);
		TextView header = (TextView) findViewById(R.id.header);
		header.setText(monday + " to " + sunday);
		
		
		
		// Create a GraphView for the regular shares
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, "Share Percentage");
		else
			graphView = new LineGraphView(this, "Share Percentage");

		// Add the series to the GraphView
		graphView.addSeries(new GraphViewSeries("Target",
				new GraphViewStyle(Color.RED, 3), idealData));
		graphView.addSeries(new GraphViewSeries("Actual",
				new GraphViewStyle(Color.GREEN, 3), actualData));
		
		// Set the graph's y-axis upper bound
		double max = actualData[0].valueY;
		for (int i = 1; i < dates.length; i++){
			if (actualData[i].valueY > max)
				max = actualData[i].valueY;
		}
		int yUpper = (int)(Math.ceil(max) + 20);
		yUpper = (yUpper < 150) ? 150 : yUpper; // Set a minimum for the upper bound
		graphView.setManualYAxisBounds(yUpper, 0);
		
		/*
		// Add the series to the GraphView
		graphView.addSeries(new GraphViewSeries("Whole Grains",
				new GraphViewStyle(Color.RED, 3), wholeGrains));
		graphView.addSeries(new GraphViewSeries("Dairy",
				new GraphViewStyle(Color.BLUE, 3), dairy));
		graphView.addSeries(new GraphViewSeries("Meat/Beans",
				new GraphViewStyle(Color.CYAN, 3), meatBeans));
		graphView.addSeries(new GraphViewSeries("Fruit",
				new GraphViewStyle(Color.MAGENTA, 3), fruits));
		graphView.addSeries(new GraphViewSeries("Veggies",
				new GraphViewStyle(Color.GREEN, 3), veggies));
		graphView.addSeries(new GraphViewSeries("Extra",
				new GraphViewStyle(0xffaa5500, 3), extra));
		
		// Set the graph's y upper bound as 2 units higher than
		// the highest share value entered for the current week
		double max = wholeGrains[0].valueY;
		for (int i = 1; i < wholeGrains.length; i++){
			if (wholeGrains[i].valueY > max)
				max = wholeGrains[i].valueY;
			if (dairy[i].valueY > max)
				max = dairy[i].valueY;
			if (meatBeans[i].valueY > max)
				max = meatBeans[i].valueY;
			if (fruits[i].valueY > max)
				max = fruits[i].valueY;
			if (veggies[i].valueY > max)
				max = veggies[i].valueY;
			if (extra[i].valueY > max)
				max = extra[i].valueY;
		}
		int yUpper = (int)(Math.ceil(max) + 2);
		yUpper = (yUpper < 6) ? 6 : yUpper; // Set a minimum for the upper bound
		graphView.setManualYAxisBounds(yUpper, 0);
		*/
		
		// Set graph legend
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		
		// Set the x-axis labels
		String[] horlabels = new String[] { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		graphView.setHorizontalLabels(horlabels);
		
		// Set the y-axis labels
		String[] verlabels = new String[yUpper/10 + 1];
		for (int i = verlabels.length - 1; i >= 0; i--){
			int tick = i*10;
			if (tick >= 100)
				verlabels[verlabels.length - 1 - i] = Integer.valueOf(tick).toString();
			else if (tick >= 10)
				verlabels[verlabels.length - 1 - i] = " " + Integer.valueOf(tick).toString();
			else
				verlabels[verlabels.length - 1 - i] = "  " + Integer.valueOf(tick).toString();
		}
		graphView.setVerticalLabels(verlabels);
		
		/* // Set the y-axis labels
		String[] verlabels = new String[yUpper + 1];
		for (int i = verlabels.length - 1; i >= 0; i--){
			verlabels[verlabels.length - 1 - i] = Integer.valueOf(i).toString();
		}
		graphView.setVerticalLabels(verlabels); */

		// Add the GraphView to our layout
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph5);
		layout.removeAllViews();
		layout.addView(graphView);
		
		
		
		// Create a GraphView for the exercise minutes
		if (getIntent().getStringExtra("type").equals(""))
			graphView = new BarGraphView(this, "Exercise Minutes");
		else
			graphView = new LineGraphView(this, "Exercise Minutes");

		// Add the series to the GraphView
		graphView.addSeries(new GraphViewSeries("Exercise",
				new GraphViewStyle(Color.BLUE, 3), exercise));
		
		// Set the graph's y upper bound as 10 minutes greater than
		// the max exercise minutes value for the current week
		max = exercise[0].valueY;
		for (int i = 1; i < exercise.length; i++){
			if (exercise[i].valueY > max)
				max = exercise[i].valueY;
		}
		yUpper = (int)(10 * Math.ceil(max/10.0) + 10);
		yUpper = (yUpper < 60) ? 60 : yUpper; // Set a minimum for the upper bound
		graphView.setManualYAxisBounds(yUpper, 0);

		// Set the x-axis labels
		graphView.setHorizontalLabels(horlabels);
		
		// Set the y-axis labels
		int interval = yUpper / ((int)(5 * Math.ceil(dates.length/5.0)));
		interval = (int)(5 * Math.ceil(interval/5.0));
		java.util.ArrayList<String> vertlabels = new java.util.ArrayList<String>();
		for (int i = yUpper; i >= 0; i -= interval )
			vertlabels.add(Integer.valueOf(i).toString());
		graphView.setVerticalLabels(vertlabels.toArray(new String[0]));

		// Add the GraphView to our layout
		layout = (LinearLayout) findViewById(R.id.graph6);
		layout.removeAllViews();
		layout.addView(graphView);
	}
}