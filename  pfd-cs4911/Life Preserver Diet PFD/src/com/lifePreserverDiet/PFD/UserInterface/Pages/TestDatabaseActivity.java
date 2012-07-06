package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.List;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.DayDataSource;
import com.lifePreserverDiet.PFD.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class TestDatabaseActivity extends ListActivity {
	private DayDataSource datasource;

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
}
