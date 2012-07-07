package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.*;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.DayDataSource;
import com.lifePreserverDiet.PFD.R;
 
import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestDatabaseActivity extends ListActivity {
	private DayDataSource datasource;
	
	private XYPlot mySimpleXYPlot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_database);

		datasource = new DayDataSource(this);
		datasource.open();

		List<Day> values = datasource.getAllDays();

		// Use the SimpleCursorAdapter to show the elements in a ListView
		ArrayAdapter<Day> adapter = 
				new ArrayAdapter<Day>(this, android.R.layout.simple_list_item_1, values);
		
		setListAdapter(adapter);
		
		
		///////////////////////////////////////////////////////////////////////////////////

		/*
		
        // initialize our XYPlot reference:
		mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
		
        Number[] numSightings = {5, 8, 9, 2, 5};
        Number[] years = {
                978307200,  // 2001
                1009843200, // 2002
                1041379200, // 2003
                1072915200, // 2004
                1104537600  // 2005
        };
        // create our series from our array of nums:
        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(years),
                Arrays.asList(numSightings),
                "Sightings in USA");
 
        mySimpleXYPlot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        mySimpleXYPlot.getGraphWidget().getGridLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getGridLinePaint().setPathEffect(new DashPathEffect(new float[]{1,1}, 1));
        mySimpleXYPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);
        mySimpleXYPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);
 
        mySimpleXYPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);
        mySimpleXYPlot.getBorderPaint().setStrokeWidth(1);
        mySimpleXYPlot.getBorderPaint().setAntiAlias(false);
        mySimpleXYPlot.getBorderPaint().setColor(Color.WHITE);
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 100, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                Color.rgb(100, 200, 0));                // fill color
 
 
        // setup our line fill paint to be a slightly transparent gradient:
        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.GREEN, Shader.TileMode.MIRROR));
 
        		// LineAndPointFormatter(line color, point color, fill color)
        LineAndPointFormatter formatter  = 
        		new LineAndPointFormatter(Color.rgb(0, 0,0), Color.BLUE, Color.RED);
        formatter.setFillPaint(lineFill);
        mySimpleXYPlot.getGraphWidget().setPaddingRight(2);
        mySimpleXYPlot.addSeries(series2, formatter);
 
        // draw a domain tick for each year:
        mySimpleXYPlot.setDomainStep(XYStepMode.SUBDIVIDE, years.length);
 
        // customize our domain/range labels
        mySimpleXYPlot.setDomainLabel("Year");
        mySimpleXYPlot.setRangeLabel("# of Sightings");
 
        // get rid of decimal points in our range labels:
        mySimpleXYPlot.setRangeValueFormat(new DecimalFormat("0"));
 
        mySimpleXYPlot.setDomainValueFormat(new Format() { 
            private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
 
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos){
                // our timestamps are in seconds and SimpleDateFormat expects milliseconds
                long timestamp = ((Number) obj).longValue() * 1000;
                Date date = new Date(timestamp);
                return dateFormat.format(date, toAppendTo, pos);
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null; 
            }
        });
 
        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():
        mySimpleXYPlot.disableAllMarkup();
        */
		

		//startGraphActivity(GraphViewDemo.class);
	}
		
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Day> adapter = (ArrayAdapter<Day>) getListAdapter();
		Day day = null;

		switch (view.getId()) {
		case R.id.add:			
			// random share values
			int wholeGrains = 0, dairy = 0, meatBeans = 0, fruit = 0, veggies = 0,
					extra = 0, exercise = 0;
			int[] shares = {wholeGrains, dairy, meatBeans, fruit, veggies, extra, exercise};
			for(int i=0; i<shares.length; i++){
				shares[i] = (int) (Math.random() * 4);
			}
			
			// random date
			java.util.Date date = new java.util.Date(); 
			date.setTime((long) (date.getTime()*Math.random()));
			
			// create new day
			day = datasource.createDay(date, wholeGrains, dairy, meatBeans,
					fruit, veggies, extra, exercise);
			
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
	
	private void startGraphActivity(Class<? extends Activity> activity) {
		Intent intent = new Intent(TestDatabaseActivity.this, activity);
		startActivity(intent);
	}
}
