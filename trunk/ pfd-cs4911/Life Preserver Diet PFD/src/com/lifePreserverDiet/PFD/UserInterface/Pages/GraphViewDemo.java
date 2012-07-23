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
		//d.setTime(d.getTime() - 24*60*60*1000);
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

		// Make sure the dates are actually a Mon - Sun set
		System.out.println("");
		for (int i = 0; i < dates.length; i++)
			System.out.println((dates[i].getDay() + 1 == (i+1)%dates.length+1) + ", " + dates[i]);
		System.out.println("");
		
		return dates;
	}

	private Date[] getMonth(Date d){
		// Calendar.JANUARY = 0
		Date[] dates;
		switch (d.getMonth()){
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			dates = new Date[31];
			break;
		case Calendar.FEBRUARY:
			dates = new Date[28];
			break;
		default:
			dates = new Date[30];
			break;
		}

		for (int i = 1; i < dates.length + 1; i++){
			if(d.getDate() == i)
				dates[i-1] = d;
			else{
				Date d1 = new Date();
				d1.setTime( d.getTime() - (d.getDate() - i)*(24*60*60*1000) );
				dates[i-1] = d1;
			}
		}
		
		System.out.println("");
		for (int i = 0; i < dates.length; i++)
			System.out.println((dates[i].getDate() == i+1) + ", " + dates[i]);
		System.out.println("");
		
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

		System.out.println("#days in db: " + getListAdapter().getCount());

		Date date = ((Day) getListAdapter().getItem(0)).getDate();
		System.out.println("chosen date: " + date);

		//GraphViewData[] actualData = new GraphViewData[4];
		//int[] shares = new int[8];

		//Date[] dates = new Date[7];
		//Date[] dates = getWeek(date);
		Date[] dates = getMonth(date);
		
		//GraphViewData[][] data = new GraphViewData[shares.length][dates.length];
		GraphViewData[] wholeGrains = new GraphViewData[dates.length];
		GraphViewData[] dairy = new GraphViewData[dates.length];
		GraphViewData[] meatBeans = new GraphViewData[dates.length];
		GraphViewData[] fruits = new GraphViewData[dates.length];
		GraphViewData[] veggies = new GraphViewData[dates.length];
		GraphViewData[] extra = new GraphViewData[dates.length];
		GraphViewData[] exercise = new GraphViewData[dates.length];
		
		if(getListAdapter().getCount() > 0){
			//Day day = (Day) getListAdapter().getItem(0);
			
			// Getting the date of the first Day in the db
			//Date date = ((Day) getListAdapter().getItem(0)).getDate();
			
			//System.out.println(date);
			//System.out.println(new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(date));
			
			// Getting the Day matching the date. So for a history page,
			// the user's chosen date gets passed in instead.
			//Day day = datasource.getDay(date);

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
			
			//dates = getMonth(date);
			//dates = getWeek(date);
			
			//wholeGrains[0] = dairy[0] = meatBeans[0] = fruits[0] =
					//veggies[0] = extra[0] = exercise[0] = new GraphViewData(0, 0.0d);
			
			for (int j = 0; j < wholeGrains.length; j++){
				Day d = datasource.getDay(dates[j]);
				//System.out.println("day object for " + dates[j-1] + ": " + d);
				if(d != null){
					//shares = new int[] {0, d.getWholeGrains(), d.getDairy(),
						//d.getMeatBeans(), d.getFruit(), d.getVeggies(),
						//d.getExtra(), d.getExerciseMinutes()};
					wholeGrains[j] = new GraphViewData(j,
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
					exercise[j] = new GraphViewData(j,
							Integer.valueOf( d.getExerciseMinutes() ).doubleValue());
				}
				else{
					//shares = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
					wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
							veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 0.0d);
				}
				
				//for (int i = 0; i < shares.length; i++){
					//data[j][i] = new GraphViewData(i, Integer.valueOf(shares[i]).doubleValue());
					//data[i][j] = new GraphViewData(j, Integer.valueOf(shares[i]).doubleValue());
					//System.out.println("share value: " + shares[i]);
				//}
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
			/*
			actualData = new GraphViewData[shares.length];
			for (int i = 1; i < actualData.length; i++)
				actualData[i] = new GraphViewData(i, 1.0d);
			actualData[0] = new GraphViewData(0, 0.0d);
			*/
			/*
			data = new GraphViewData[1][shares.length];
			for (int i = 1; i < shares.length; i++)
				data[0][i] = new GraphViewData(i, 1.0d);
			data[0][0] = new GraphViewData(0, 0.0d);*/
			for (int j = 0; j < dates.length; j++){
				wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
						veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 1.0d);
			}
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
		String title = "";
		
		if (dates.length == 7){
			String monday = new java.text.SimpleDateFormat("MMM dd, yyyy").format(dates[0]);
			String sunday = new java.text.SimpleDateFormat("MMM dd, yyyy").format(dates[6]);
			title = monday + " to " + sunday;
		}
		else{
			String[] months = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
			title = months[ dates[0].getMonth() ] + ", " + (dates[0].getYear() + 1900);
		}
		
		GraphView graphView;
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, title);
		else
			graphView = new LineGraphView(this, title);
		
		// Add the data
		
		//graphView.addSeries(new GraphViewSeries("Target",
			//	new GraphViewStyle(Color.rgb(150, 62, 35), 3), idealData));
		//graphView.addSeries(new GraphViewSeries("Actual", null, actualData));
		
		
		// Add the data
		//String[] seriesTitles = new String[] { "Whole Grains", "Dairy",
				//"Meat/Beans", "Fruit", "Veggies", "Extra", "Exercise" };
		
		//System.out.println("data.length: " + data.length);
		
		//for (int j = 0; j < data.length; j++)
			//for (int k = 0; k < shares.length; k++)
				//System.out.println("data[" + j + "]: " + data[j]);
		//System.out.println("haha");
		
		/*
		for (int j = 0; j < data.length; j++){ // shares
			GraphViewData[] s = new GraphViewData[data[0].length];
		for (int i = 0; i < s.length; i++){ // days
			s[i] = data[j][i];
		}
		int c = Color.rgb((int)(Math.random()*256), (int)(Math.random()*256),
				(int)(Math.random()*256));
		graphView.addSeries( new GraphViewSeries(
				seriesTitles[j], new GraphViewStyle(c, 3), s) ); //data[j]) );
		}*/
		
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
				new GraphViewStyle(Color.YELLOW, 3), extra));
		graphView.addSeries(new GraphViewSeries("Exercise",
				new GraphViewStyle(Color.LTGRAY, 3), exercise));
		
		//graphView.setViewPort(0.0, dates.length + (int)Math.ceil((1/4.0)*dates.length));
		graphView.setManualYAxisBounds(8, 0);
		//graphView.setScrollable(true);
		//graphView.setScalable(true);
		//graphView.setBackgroundDrawable(null);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.MIDDLE);
		//graphView.setLegendWidth(dates.length); //100);
		
		String[] horlabels = new String[dates.length];
		for (int i = 0; i < horlabels.length; i++)
			horlabels[i] = Integer.valueOf(dates[i].getDate()).toString();
		graphView.setHorizontalLabels(horlabels);

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
				shares[i] = (int) (Math.random() * 6 + 1);
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
			date.setYear(2012 - 1900);

			while(datasource.find(date)){
				date = new java.util.Date();
				date.setTime((long) (date.getTime()*Math.random()));
				date.setMonth(Calendar.JULY);
				date.setYear(2012 - 1900);
			}
			
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