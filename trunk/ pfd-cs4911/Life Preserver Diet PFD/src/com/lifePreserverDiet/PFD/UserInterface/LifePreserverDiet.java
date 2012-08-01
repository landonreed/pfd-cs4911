package com.lifePreserverDiet.PFD.UserInterface;

import java.util.Date;

import android.app.Application;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

/**
 * The application class. Called on startup.
 * 
 * It creates a new Day object is this is the first time that the
 * application is being run on this day.
 * 
 * Allows quick updates for the current day object and saves the user's
 * gender in a SharedPreferences file.
 * 
 * @author David Murray, Lamine Sissoko
 *
 */
public class LifePreserverDiet extends Application {
	
	/** Current day object. */
	private Day day;
	
	/** Database access object. */
	private DayDataSource dataSource;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		dataSource = new DayDataSource(this);
		dataSource.open();
		
		Day today = dataSource.getDay(new Date());
		if (today == null)
			day = dataSource.createDay();
		else
			day = today;
		
		dataSource.close();
	}
	
	/**
	 * Returns the current Day object.
	 * 
	 * @return the current Day object
	 */
	public Day getDay() {
		return day;
	}
	
	/**
	 * Updates the current Day object.
	 */
	public void updateDay() {
		dataSource.open();
		dataSource.updateDay(day);
		dataSource.close();
	}
	

	/** Static fields for the user settings file. */
	public static final String PREF_NAME = "PFDPrefsFile";
	public static final String PREF_BOOL = "isFemale";

	/**
	 * Checks if the user has checked the female check box.
	 * 
	 * @return true if the user is a female
	 */
	public boolean isFemale(){
		return getSharedPreferences(PREF_NAME, MODE_PRIVATE).getBoolean(PREF_BOOL, true);
	}
	
}
