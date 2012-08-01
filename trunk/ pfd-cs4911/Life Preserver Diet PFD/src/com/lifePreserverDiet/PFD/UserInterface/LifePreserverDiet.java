package com.lifePreserverDiet.PFD.UserInterface;

import java.util.Date;

import android.app.Application;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

public class LifePreserverDiet extends Application {
	private Day day;
	private DayDataSource dataSource;
	
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
	
	public Day getDay() {
		return day;
	}
	
	public void updateDay() {
		dataSource.open();
		dataSource.updateDay(day);
		dataSource.close();
	}
	

	/** User settings file fields */
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
