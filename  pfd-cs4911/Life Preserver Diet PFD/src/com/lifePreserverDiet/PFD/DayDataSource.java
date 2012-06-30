package com.lifePreserverDiet.PFD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DayDataSource {

	/** Database fields */
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { 
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DATE,
			MySQLiteHelper.COLUMN_WHOLEGRAINS,
			MySQLiteHelper.COLUMN_DAIRY,
			MySQLiteHelper.COLUMN_MEATBEANS,
			MySQLiteHelper.COLUMN_FRUIT,
			MySQLiteHelper.COLUMN_EXTRA,
			MySQLiteHelper.COLUMN_EXERCISE
	};

	public DayDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Inserts a new Day into the table
	 */
	public Day createDay(Date date, int wholeGrains, int dairy, int meatBeans,
			int fruit, int veggies, int extra, int exercise)
	{
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		//values.put(MySQLiteHelper.COLUMN_DATE, 
				//new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a").format(date));
		values.put(MySQLiteHelper.COLUMN_WHOLEGRAINS, wholeGrains);
		values.put(MySQLiteHelper.COLUMN_DAIRY, dairy);
		values.put(MySQLiteHelper.COLUMN_MEATBEANS, meatBeans);
		values.put(MySQLiteHelper.COLUMN_FRUIT, fruit);
		values.put(MySQLiteHelper.COLUMN_VEGGIES, veggies);
		values.put(MySQLiteHelper.COLUMN_EXTRA, extra);
		values.put(MySQLiteHelper.COLUMN_EXERCISE, exercise);
		
		long insertId = database.insert(MySQLiteHelper.TABLE_DAYS, null,
				values);
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		cursor.moveToFirst();
		Day newDay = cursorToDay(cursor);
		cursor.close();
		
		return newDay;
	}

	/**
	 * Removes a Day from the table
	 */
	public void deleteDay(Day day) {
		long id = day.getId();
		System.out.println("Day deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_DAYS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	/**
	 * Returns a list of all Days contained in the table
	 */
	public List<Day> getAllDays() {
		List<Day> days = new ArrayList<Day>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Day day = cursorToDay(cursor);
			days.add(day);
			cursor.moveToNext();
		}
		cursor.close();
		
		return days;
	}

	/**
	 * Returns the table row pointed to by the given cursor as a Day object
	 */
	private Day cursorToDay(Cursor cursor) {
		Day day = new Day();
		day.setId(cursor.getLong(0));
		day.setDate(cursor.getString(1));
		day.setWholeGrains(cursor.getInt(2));
		day.setDairy(cursor.getInt(3));
		day.setMeatBeans(cursor.getInt(4));
		day.setFruit(cursor.getInt(5));
		day.setVeggies(cursor.getInt(6));
		day.setExtra(cursor.getInt(7));
		day.setExercise(cursor.getInt(8));
		return day;
	}
}
