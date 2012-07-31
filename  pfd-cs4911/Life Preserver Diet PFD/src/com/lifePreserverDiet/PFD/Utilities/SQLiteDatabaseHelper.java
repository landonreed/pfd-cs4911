package com.lifePreserverDiet.PFD.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper{
	public static final String TABLE_DAYS = "days",
			COLUMN_ID = "_id",
			COLUMN_DATE = "date",
			COLUMN_WHOLEGRAINS = "wholeGrains",
			COLUMN_DAIRY = "dairy",
			COLUMN_MEATBEANS = "meatBeans",
			COLUMN_FRUIT = "fruit",
			COLUMN_VEGGIES = "veggies",
			COLUMN_EXTRA = "extra",
			COLUMN_EXERCISE = "exercise",
			COLUMN_EXERCISE_MINUTES = "exercise_minutes",
			COLUMN_VISITED = "visited",
			DATABASE_NAME = "days.db";
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table " + TABLE_DAYS
			+ "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE + " text not null, "
			+ COLUMN_WHOLEGRAINS + " integer, "
			+ COLUMN_DAIRY + " integer, "
			+ COLUMN_MEATBEANS + " integer, "
			+ COLUMN_FRUIT + " integer, "
			+ COLUMN_VEGGIES + " integer, "
			+ COLUMN_EXTRA + " integer, "
			+ COLUMN_EXERCISE + " text, "
			+ COLUMN_EXERCISE_MINUTES + " integer, "
			+ COLUMN_VISITED + " text "
			+ ");";

	public SQLiteDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(SQLiteDatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAYS);
		onCreate(db);
	}
}
