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
			SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES,
			SQLiteDatabaseHelper.COLUMN_VISITED
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
		if (dbHelper != null)
			dbHelper.close();
	}
	
	/**
	 * Inserts a new default Day object into the database.
	 * 
	 * @return The Day object retrieved from the database.
	 */
	public Day createDay()
	{
		return createDay(new Day());
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

		String dateString = Day.dateFormat.format(day.getDate());
		
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
		values.put(SQLiteDatabaseHelper.COLUMN_VISITED, String.valueOf(day.getVisited()));
				
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
	 * Returns a list of all Days contained in the table.
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
		day.setId(cursor.getLong(0));
		day.setDate(cursor.getString(1));
		day.setWholeGrains(cursor.getInt(2));
		day.setDairy(cursor.getInt(3));
		day.setMeatBeans(cursor.getInt(4));
		day.setFruit(cursor.getInt(5));
		day.setVeggies(cursor.getInt(6));
		day.setExtra(cursor.getInt(7));
		day.setExercise( cursor.getString(8).equals("true") );
		day.setExerciseMinutes(cursor.getInt(9));
		day.setVisited( cursor.getString(10).equals("true") );
		return day;
	}
	
	/**
	 * Returns the Day object from the database matching the given date.
	 * 
	 * @param date The date of the Day we want to get
	 * @return the Day object from the database matching the given date
	 */
	public Day getDay(Date date){
		String dateString = Day.dateFormat.format(date);
		
		Cursor cursor = database.query(SQLiteDatabaseHelper.TABLE_DAYS,
				allColumns, SQLiteDatabaseHelper.COLUMN_DATE + " = '" + dateString + "'",
				null, null, null, null);
		
		if (cursor.getCount() < 1){
			cursor.close();
			return null;
		}
		
		cursor.moveToFirst();
		Day day = cursorToDay(cursor);
		cursor.close();
		return day;
	}
	
	/**
	 * Updates a given day in the database.
	 * 
	 * @param day The day to update.
	 * @return true if the update was successful
	 */
	public boolean updateDay(Day day){
		ContentValues values = new ContentValues();
		
		String dateString = Day.dateFormat.format(day.getDate());
		
		values.put(SQLiteDatabaseHelper.COLUMN_DATE, dateString);
		values.put(SQLiteDatabaseHelper.COLUMN_WHOLEGRAINS, day.getWholeGrains());
		values.put(SQLiteDatabaseHelper.COLUMN_DAIRY, day.getDairy());
		values.put(SQLiteDatabaseHelper.COLUMN_MEATBEANS, day.getMeatBeans());
		values.put(SQLiteDatabaseHelper.COLUMN_FRUIT, day.getFruit());
		values.put(SQLiteDatabaseHelper.COLUMN_VEGGIES, day.getVeggies());
		values.put(SQLiteDatabaseHelper.COLUMN_EXTRA, day.getExtra());
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE, String.valueOf(day.getExercise()));
		values.put(SQLiteDatabaseHelper.COLUMN_EXERCISE_MINUTES, day.getExerciseMinutes());
		values.put(SQLiteDatabaseHelper.COLUMN_VISITED, String.valueOf(day.getVisited()));
		
		int rowsChanged = database.update(SQLiteDatabaseHelper.TABLE_DAYS, values,
				SQLiteDatabaseHelper.COLUMN_DATE + " = '" + dateString + "'", null);
		
		return (rowsChanged > 0) ? true : false;
	}
	
	/**
	 * Checks if the database is empty.
	 * 
	 * @return true if the database is empty
	 */
	public boolean isEmpty(){
		String query = String.format("select * from %s limit %s",
				SQLiteDatabaseHelper.TABLE_DAYS, "1");
		Cursor cursor = database.rawQuery(query, null);
		int count = cursor.getCount();
		cursor.close();
		return count < 1;
	}
	
}
