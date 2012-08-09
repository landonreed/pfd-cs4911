package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewStyle;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.LineGraphView;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDiet;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

/**
 * ChartHistory displays a user's share history for a given week (the
 * current week is displayed on page open but controls are provided
 * to move backwards or forwards in time).
 * 
 * Minutes spent on exercise are displayed on the bottom graph.
 * The other share types are displayed on the top graph.
 * 
 * @author Lamine Sissoko
 * 
 */
public class ChartHistory extends Activity {
	
	/** Database access object. */
	private DayDataSource datasource;
	
	/** Date determining which week to display. */
	private Date myDate;
	
	/** Flag for the user's gender. */
	private boolean isFemale;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.page_chart_history);
		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		isFemale = app.isFemale();

		datasource = new DayDataSource(this);
		datasource.open();
		
		// The graph should start on today's week
		myDate = new Date();
		makeGraph(myDate);
	}
	
	/**
	 * Move back or forward one week and display the new graph.
	 * 
	 * @param view The view we're using
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
	
	/**
	 * Opens the instructions page.
	 * 
	 * @param v The view we're using
	 */
	public void chInstructions(View v) {
		Intent intent = new Intent(this, CHInstructions.class);
		startActivity(intent);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
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
		// java.util.Calendar maps Sun ... Sat as 0 ... 7
		for (int i = 1; i < dates.length + 1; i++){
			if(d.getDay() + 1 == i)
				dates[i-1] = d;
			else{
				Date d1 = new Date();
				d1.setTime( d.getTime() - (d.getDay() + 1 - i)*(24*60*60*1000) );
				dates[i-1] = d1;
			}
		}
		return dates;
	}
	
	/**
	 * Creates and places the graphs on the screen using for the week (Sun - Sat)
	 * containing the given date.
	 * 
	 * The top graph shows a total score for the user's progress each day.
	 * 
	 * The bottom graph shows the exercise minutes.
	 * 
	 * @param date The chosen date
	 */
	private void makeGraph(Date date){
		
		// Get the Dates for the desired week
		Date[] dates = getWeek(date);
		
		// Arrays for the data series
		GraphViewData[] exercise = new GraphViewData[dates.length];
		GraphViewData[] actualData = new GraphViewData[dates.length];
		GraphViewData[] idealData = new GraphViewData[dates.length];
		if(isFemale){
			for (int i = 0; i < idealData.length; i++)
				idealData[i] = new GraphViewData(i, 20.0d);
		}
		else{
			for (int i = 0; i < idealData.length; i++)
				idealData[i] = new GraphViewData(i, 26.0d);
		}
		
		// Target daily share values
		double wholeGrainsTotal, dairyTotal, meatBeansTotal, fruitTotal, extraTotal;
		if(isFemale) wholeGrainsTotal = dairyTotal = meatBeansTotal = fruitTotal = extraTotal = 3.0;
		else wholeGrainsTotal = dairyTotal = meatBeansTotal = fruitTotal = extraTotal = 4.0;
		
		
		// Build the data series
		for (int j = 0; j < dates.length; j++){
			Day d = datasource.getDay(dates[j]);
			if(d != null){
				exercise[j] = new GraphViewData(j,
						Integer.valueOf( d.getExerciseMinutes() ).doubleValue());
					
				// Points for exercise = 0 if the user did not exercise, 1 otherwise
				// Points for veggies  = the number of shares checked in
				// Points for other    = target - |target - actual|
				double exerciseShare = (d.getExerciseMinutes() > 0) ? 1.0 : 0.0;
				double total =
						wholeGrainsTotal - Math.abs(wholeGrainsTotal - d.getWholeGrains()) +
						dairyTotal - Math.abs(dairyTotal - d.getDairy()) + 
						meatBeansTotal - Math.abs(meatBeansTotal - d.getMeatBeans()) +
						fruitTotal - Math.abs(fruitTotal - d.getFruit()) + 
						d.getVeggies() + 
						extraTotal - Math.abs(extraTotal - d.getExtra()) +
						exerciseShare;
				// No negative scores
				total = (total < 0) ? 0 : total;
				actualData[j] = new GraphViewData(j, total);
			}
			else{
				exercise[j] = new GraphViewData(j, 0.0d);
				actualData[j] = new GraphViewData(j, 0.0d);
			}
		}
		

		// Generate this week's header
		java.text.SimpleDateFormat myFormat =
				new java.text.SimpleDateFormat("EEE, MMM dd, yyyy");
		TextView header = (TextView) findViewById(R.id.header);
		header.setText(myFormat.format(dates[0]) + " to " + myFormat.format(dates[6]));
		
		
		
		///////////////////
		// REGULAR SHARES
		///////////////////
		
		// Create a GraphView for the regular shares
		GraphView graphView = new LineGraphView(this, "Diet Score");
		
		// If we're displaying a week containing today's date then
		// tell the graph to highlight today on the x-axis
		if ( Day.dateFormat.format(date).equals( Day.dateFormat.format(new Date()) ) )
			graphView.setDayHighlight(true);

		// Add the series to the GraphView
		graphView.addSeries(new GraphViewSeries("Target",
				new GraphViewStyle(Color.RED, 3), idealData));
		graphView.addSeries(new GraphViewSeries("Actual",
				new GraphViewStyle(Color.BLUE, 3), actualData));
		
		// Set the graph's y-axis upper bound
		int yUpper;
		double max = actualData[0].valueY;
		for (int i = 1; i < exercise.length; i++){
			if (actualData[i].valueY > max)
				max = actualData[i].valueY;
		}
		yUpper = (int)(2 * Math.ceil(max/2) + 2);
		if (isFemale && yUpper < 24)
			yUpper = 24;
		else if (!isFemale && yUpper < 30)
			yUpper = 30;
		graphView.setManualYAxisBounds(yUpper, 0);
		
		// Set graph legend
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		
		// Set the x-axis labels
		String[] horlabels = new String[] { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
		graphView.setHorizontalLabels(horlabels);
		
		// Set the y-axis labels
		String[] verlabels = new String[yUpper/2 + 1];
		for (int i = verlabels.length - 1; i >= 0; i--){
			int tick = i*2;
			if (tick >= 100)
				verlabels[verlabels.length - 1 - i] = Integer.valueOf(tick).toString();
			else if (tick >= 10)
				verlabels[verlabels.length - 1 - i] = " " + Integer.valueOf(tick).toString();
			else
				verlabels[verlabels.length - 1 - i] = "  " + Integer.valueOf(tick).toString();
		}
		graphView.setVerticalLabels(verlabels);

		// Add the GraphView to our layout
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph5);
		layout.removeAllViews();
		layout.addView(graphView);
		

		
		/////////////////////
		// EXERCISE MINUTES
		/////////////////////
		
		// Create a GraphView for the exercise minutes
		graphView = new LineGraphView(this, "Exercise Minutes");
		
		// If we're displaying a week containing today's date then
		// tell the graph to highlight today on the x-axis
		if( Day.dateFormat.format(date).equals( Day.dateFormat.format(new Date()) ) )
			graphView.setDayHighlight(true);

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
		yUpper = (yUpper < 60) ? 60 : yUpper;
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