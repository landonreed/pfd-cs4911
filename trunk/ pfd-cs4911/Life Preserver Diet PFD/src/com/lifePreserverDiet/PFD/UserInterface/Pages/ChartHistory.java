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
public class ChartHistory extends Activity {
	private DayDataSource datasource;
	
	
	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_chart_history);

		datasource = new DayDataSource(this);
		datasource.open();

		List<Day> values = datasource.getAllDays();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		//ArrayAdapter<Day> adapter = 
				//new ArrayAdapter<Day>(this, android.R.layout.simple_list_item_1, values);
		//setListAdapter(adapter);

		makeGraph(((Day) values.get(0)).getDate(), datasource.getAllDays());
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
		
		int year = d.getYear() + 1900;
		if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
			dates = new Date[29];
		
		for (int i = 1; i < dates.length + 1; i++){
			if (d.getDate() == i)
				dates[i-1] = d;
			else {
				Date d1 = new Date();
				d1.setTime( d.getTime() - (d.getDate() - i)*(24*60*60*1000) );
				dates[i-1] = d1;
			}
		}
		
		// Make sure the dates are a set for the right month
		System.out.println("");
		for (int i = 0; i < dates.length; i++)
			System.out.println((dates[i].getDate() == i+1) + ", " + dates[i]);
		System.out.println("");
		
		return dates;
	}
	
	private void makeGraph(Date date, List<Day> values){
		//System.out.println("#days in db: " + values.size());
		System.out.println("chosen date: " + date);

		Date[] dates = getWeek(date);
		//Date[] dates = getMonth(date);
		
		//GraphViewData[][] data = new GraphViewData[shares.length][dates.length];
		GraphViewData[] wholeGrains = new GraphViewData[dates.length];
		GraphViewData[] dairy = new GraphViewData[dates.length];
		GraphViewData[] meatBeans = new GraphViewData[dates.length];
		GraphViewData[] fruits = new GraphViewData[dates.length];
		GraphViewData[] veggies = new GraphViewData[dates.length];
		GraphViewData[] extra = new GraphViewData[dates.length];
		GraphViewData[] exercise = new GraphViewData[dates.length];
		
		if(values.size() > 0){
			for (int j = 0; j < wholeGrains.length; j++){
				Day d = datasource.getDay(dates[j]);
				
				//System.out.println("day object for " + dates[j-1] + ": " + d);
				
				if(d != null){
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
					wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
							veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 0.0d);
				}
			}
		}
		// Default data if the db is empty
		else{
			for (int j = 0; j < dates.length; j++){
				wholeGrains[j] = dairy[j] = meatBeans[j] = fruits[j] =
						veggies[j] = extra[j] = exercise[j] = new GraphViewData(j, 1.0d);
			}
		}

		String title = "";
		if (dates.length == 7){
			java.text.SimpleDateFormat myFormat =
					new java.text.SimpleDateFormat("MMM dd, yyyy");
			String monday = myFormat.format(dates[0]);
			String sunday = myFormat.format(dates[6]);
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
		
		final int yUpperBound = 16;
		graphView.setManualYAxisBounds(yUpperBound - 1, 0);
		graphView.setShowLegend(true);
		graphView.setLegendAlign(LegendAlign.TOP);
		
		String[] horlabels = new String[dates.length];
		for (int i = 0; i < horlabels.length; i++)
			horlabels[i] = Integer.valueOf(dates[i].getDate()).toString();
		graphView.setHorizontalLabels(horlabels);
		
		String[] verlabels = new String[yUpperBound];
		for (int i = verlabels.length - 1; i >= 0; i--)
			verlabels[verlabels.length - 1 - i] = Integer.valueOf(i).toString();
		graphView.setVerticalLabels(verlabels);

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph5);
		layout.removeAllViews();
		layout.addView(graphView);
		
		
		
		// Exercise minutes
		if (getIntent().getStringExtra("type").equals("bar"))
			graphView = new BarGraphView(this, "Exercise");
		else
			graphView = new LineGraphView(this, "Exercise");

		graphView.addSeries(new GraphViewSeries("Exercise",
				new GraphViewStyle(Color.BLUE, 3), exercise));
		
		horlabels = new String[dates.length];
		for (int i = 0; i < horlabels.length; i++)
			horlabels[i] = Integer.valueOf(dates[i].getDate()).toString();
		graphView.setHorizontalLabels(horlabels);
		
		/*verlabels = new String[120];
		for (int i = verlabels.length - 1; i >= 0; i--)
			verlabels[verlabels.length - 1 - i] = Integer.valueOf(i).toString();
		graphView.setVerticalLabels(verlabels);*/
		
		layout = (LinearLayout) findViewById(R.id.graph6);
		layout.removeAllViews();
		layout.addView(graphView);
	}
}