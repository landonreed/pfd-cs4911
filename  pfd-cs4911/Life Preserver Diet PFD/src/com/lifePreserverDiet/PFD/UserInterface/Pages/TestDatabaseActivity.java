package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * TestDatabaseActivity is a debugging class. It lets you add days to the
 * database to better test the graph functionality of {@link ChartHistory}.
 * 
 * @author Lamine Sissoko
 *
 */
public class TestDatabaseActivity extends ListActivity {
	/** Database access object. */
	private DayDataSource datasource;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_database);

		datasource = new DayDataSource(this);
		datasource.open();

		List<Day> values = datasource.getAllDays();

		// Used to show the elements in a ListView
		ArrayAdapter<Day> adapter = 
				new ArrayAdapter<Day>(this, android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}
	
	/**
	 * This method either adds a new random day to the database or removes
	 * the day at the bottom of the database depending on the pressed
	 * button (either "Add" or "Delete").
	 * 
	 * @param view The Button that, when clicked, calls this method.
	 */
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Day> adapter = (ArrayAdapter<Day>) getListAdapter();
		Day day = null;

		switch (view.getId()) {
		case R.id.add:
			// random share values
			int[] shares = new int[7];
			for (int i = 0; i < shares.length - 1; i++)
				shares[i] = (int) (Math.random() * 4 + 1);
			int wholeGrains = shares[0];
			int dairy = shares[1];
			int meatBeans = shares[2];
			int fruit = shares[3];
			int veggies = shares[4];
			int extra = shares[5];
			
			// random exercise minutes between 0 and 180
			int exercise_minutes = 5 * (int)(Math.random() * 36);
			
			boolean exercise = (exercise_minutes > 0) ? true : false;
			
			// random date
			Date date = new Date();
			date.setTime((long) (date.getTime()*Math.random()));
			date.setMonth(Calendar.JULY);
			date.setYear(2012 - 1900);
			while(datasource.getDay(date) != null){
				date = new java.util.Date();
				date.setTime((long) (date.getTime()*Math.random()));
				date.setMonth(Calendar.JULY);
				date.setYear(2012 - 1900);
			}
			System.out.println("----- adding: " + date);
			
			// create the new day
			day = new Day();
			day.setDate(Day.dateFormat.format(date));
			day.setWholeGrains(wholeGrains);
			day.setDairy(dairy);
			day.setMeatBeans(meatBeans);
			day.setFruit(fruit);
			day.setVeggies(veggies);
			day.setExtra(extra);
			day.setExercise(exercise);
			day.setExerciseMinutes(exercise_minutes);
			if (date.before(new Date()))
				day.setVisited(true);
			
			// insert the new day in the database
			day = datasource.createDay(day);
			
			// add new day to our display list
			adapter.add(day);
			break;
		case R.id.delete:
			// delete the day at the end of the database (bottom of the list)
			if (getListAdapter().getCount() > 0) {
				day = (Day) getListAdapter().getItem( getListAdapter().getCount() - 1 );
				datasource.deleteDay(day);
				adapter.remove(day);
			}
			break;
		}
		
		adapter.notifyDataSetChanged();
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
}
