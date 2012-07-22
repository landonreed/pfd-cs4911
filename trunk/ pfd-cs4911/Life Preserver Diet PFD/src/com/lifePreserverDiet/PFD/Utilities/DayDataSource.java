package com.lifePreserverDiet.PFD.Utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lifePreserverDiet.PFD.Day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DayDataSource {

	/** Database */
	private SQLiteDatabase database;
	
	/** Database manager */
	private SQLiteDatabaseHelper dbHelper;
	
	/** Array of table columns */
	private String[] allColumns = {
			SQLiteDatabaseHelper.COLUMN_ID,
			SQLiteDatabaseHelper.COLUMN_DATE,
			SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS,
			SQLiteDatabaseHelper.COLUMN_DAIRY,
			SQLiteDatabaseHelper.COLUMN_MEATBEANS,
			SQLiteDatabaseHelper.COLUMN_FRUIT,
			SQLiteDatabaseHelper.COLUMN_VEGGIES,
			SQLiteDatabaseHelper.COLUMN_EXTRA,
			SQLiteDatabaseHelper.COLUMN_EXERCISE,
			SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES
	};

	/**
	 * Creates a new database manager.
	 * 
	 * @param context to use to open or create the database
	 */
	public DayDataSource(Context context) {
		dbHelper = new SQLiteDatabaseHelper(context);
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
	 * Inserts a new default Day object into the database.
	 * 
	 * @return The Day object retrieved from the database.
	 */
	public Day createDay()
	{
		Day day = new Day();
		
		/* Build value set for SQL query. */
		ContentValues values = new ContentValues();

		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(day.getDate());
		
	    //values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, day.getWholeGrains());
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, day.getDairy());
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, day.getMeatBeans());
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, day.getFruit());
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, day.getVeggies());
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, day.getExtra());
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(day.getExercise()));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, day.getExerciseMinutes());
				
		/* Perform table insert and get the row id. */
		long insertId = database.insert(SQLiteDatabaseHelper.TABLE_DAYS, null,
				values);
		
		/* Get a cursor to the inserted row. */
		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
				allColumns, SQLiteDatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		/* Close the cursor and return the resulting Day object. */
		cursor.moveToFirst();
		Day newDay = cursorToDay(cursor);
		cursor.close();
		return newDay;
	}
	
	/**
	 * Inserts a given Day into the the table.
	 * 
	 * @param day The Day object to copy into the database.
	 * @return The Day object retrieved from the database.
	 */
	public Day createDay(Day day)
	{
		/* Build value set for SQL query. */
		ContentValues values = new ContentValues();

		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(day.getDate());
		
	    //values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, day.getWholeGrains());
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, day.getDairy());
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, day.getMeatBeans());
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, day.getFruit());
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, day.getVeggies());
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, day.getExtra());
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(day.getExercise()));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, day.getExerciseMinutes());
				
		/* Perform table insert and get the row id. */
		long insertId = database.insert(SQLiteDatabaseHelper.TABLE_DAYS, null,
				values);
		
		/* Get a cursor to the inserted row. */
		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
				allColumns, SQLiteDatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		
		/* Close the cursor and return the resulting Day object. */
		cursor.moveToFirst();
		Day newDay = cursorToDay(cursor);
		cursor.close();
		return newDay;
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

		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(date);
		
	    //values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, wholeGrains);
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, dairy);
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, meatBeans);
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, fruit);
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, veggies);
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, extra);
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(exercise));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, exercise_minutes);
				
		/* Perform table insert and get the row id. */
		long insertId = database.insert(SQLiteDatabaseHelper.TABLE_DAYS, null,
				values);
		
		/* Get a cursor to the inserted row. */
		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
				allColumns, SQLiteDatabaseHelper.COLUMN_ID + " = " + insertId, null,
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
		database.delete(SQLiteDatabaseHelper.TABLE_DAYS, SQLiteDatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	/**
	 * 
	 * @return a list of all Days contained in the table
	 */
	public List<Day> getAllDays() {
		List<Day> days = new ArrayList<Day>();

		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
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
		
		//for (int i = 0; i<cursor.getColumnCount(); i++)
			//System.out.println(cursor.getString(i));
		
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
	
	/**
	 * Returns the Day object from the db matching the given date.
	 * 
	 * @param date The date of the Day we want to get
	 * @return the Day object from the db matching the given date
	 */
	public Day getDay(Date date){
		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(date);
		
		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
				//allColumns, MySQLiteHelper.COLUMN_DATE + " = '" + date.toString() + "'",
				allColumns, SQLiteDatabaseHelper.COLUMN_DATE + " = '" + dateString + "'",
				null, null, null, null);
		
		if (cursor.getCount() < 1)
			return null;
		
		cursor.moveToFirst();
		Day day = cursorToDay(cursor);
		cursor.close();
		return day;
	}
	
	public boolean updateDay(Day day){
		ContentValues values = new ContentValues();
		
		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(day.getDate());
		
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		//values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, day.getWholeGrains());
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, day.getDairy());
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, day.getMeatBeans());
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, day.getFruit());
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, day.getVeggies());
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, day.getExtra());
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(day.getExercise()));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, day.getExerciseMinutes());
		
		int rowsChanged = database.update(SQLiteDatabaseHelper.TABLE_DAYS, values,
				SQLiteDatabaseHelper.COLUMN_DATE + " = '" + dateString + "'", null);
				//MySQLiteHelper.COLUMN_DATE + " = '" + date.toString() + "'", null);
		
		return (rowsChanged > 0) ? true : false;
	}
	
	public boolean updateDay(Date date, int wholeGrains, int dairy, int meatBeans,
			int fruit, int veggies, int extra, boolean exercise, int exercise_minutes){
		ContentValues values = new ContentValues();
		
		String dateString = new java.text.SimpleDateFormat("EEE MMM dd yyyy").format(date);
		
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		//values.put(MySQLiteHelper.COLUMN_DATE, date.toString());
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, wholeGrains);
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, dairy);
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, meatBeans);
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, fruit);
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, veggies);
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, extra);
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(exercise));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, exercise_minutes);
		
		int rowsChanged = database.update(SQLiteDatabaseHelper.TABLE_DAYS, values,
				SQLiteDatabaseHelper.COLUMN_DATE + " = '" + dateString + "'", null);
				//MySQLiteHelper.COLUMN_DATE + " = '" + date.toString() + "'", null);
		
		return (rowsChanged > 0) ? true : false;
	}
}
