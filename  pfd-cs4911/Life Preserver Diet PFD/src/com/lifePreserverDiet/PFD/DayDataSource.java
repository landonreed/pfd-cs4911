package com.lifePreserverDiet.PFD;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DayDataSource {

	/** Database */
	private SQLiteDatabase database;
	
	/** Database manager */
	private MySQLiteHelper dbHelper;
	
	/** Array of table columns */
	private String[] allColumns = {
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DATE,
			MySQLiteHelper.COLUMN_WHOLEGRAINS,
			MySQLiteHelper.COLUMN_DAIRY,
			MySQLiteHelper.COLUMN_MEATBEANS,
			MySQLiteHelper.COLUMN_FRUIT,
			MySQLiteHelper.COLUMN_VEGGIES,
			MySQLiteHelper.COLUMN_EXTRA,
			MySQLiteHelper.COLUMN_EXERCISE,
			MySQLiteHelper.COLUMN_EXERCISE_MINUTES
	};

	/**
	 * Creates a new database manager.
	 * 
	 * @param context to use to open or create the database
	 */
	public DayDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	/**
	 * Opens a new database connection.
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Closes the database connection.
	 */
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Inserts a new Day into the the table.
	 * 
	 * @param date of this day
	 * @param wholeGrains share amount entered for this day
	 * @param dairy share amount entered for this day
	 * @param meatBeans share amount entered for this day
	 * @param fruit share amount entered for this day
	 * @param veggies share amount entered for this day
	 * @param extra share amount entered for this day
	 * @param exercise share amount entered for this day
	 * @return The Day object
	 */
	public Day createDay(Date date, int wholeGrains, int dairy, int meatBeans,
			int fruit, int veggies, int extra, boolean exercise, int exercise_minutes)
	{
		/* Build value set for SQL query. */
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(MySQLiteHelper.COLUMN_WHOLEGRAINS, wholeGrains);
		values.put(MySQLiteHelper.COLUMN_DAIRY, dairy);
		values.put(MySQLiteHelper.COLUMN_MEATBEANS, meatBeans);
		values.put(MySQLiteHelper.COLUMN_FRUIT, fruit);
		values.put(MySQLiteHelper.COLUMN_VEGGIES, veggies);
		values.put(MySQLiteHelper.COLUMN_EXTRA, extra);
		values.put(MySQLiteHelper.COLUMN_EXERCISE, String.valueOf(exercise));
		values.put(MySQLiteHelper.COLUMN_EXERCISE_MINUTES, exercise_minutes);
				
		/* Perform table insert and get the row id. */
		long insertId = database.insert(MySQLiteHelper.TABLE_DAYS, null,
				values);
		
		/* Get a cursor to the inserted row. */
		Cursor cursor = database.query(MySQLiteHelper.TABLE_DAYS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		/* Close the cursor and return the resulting Day object. */
		cursor.moveToFirst();
		Day newDay = cursorToDay(cursor);
		cursor.close();		
		return newDay;
	}

	/**
	 * Removes a Day from the table.
	 * 
	 * @param day The day to remove
	 */
	public void deleteDay(Day day) {
		long id = day.getId();
		System.out.println("Day deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_DAYS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	/**
	 * 
	 * @return a list of all Days contained in the table
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
	 * Returns a Day object whose fields match the given cursor's columns.
	 * 
	 * @param cursor The cursor pointing to the table row containing our desired data
	 * @return A Day object whose fields match the cursor's columns
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
		day.setExercise( Boolean.getBoolean(cursor.getString(8)) );
		day.setExerciseMinutes(cursor.getInt(9));
		return day;
	}
}
