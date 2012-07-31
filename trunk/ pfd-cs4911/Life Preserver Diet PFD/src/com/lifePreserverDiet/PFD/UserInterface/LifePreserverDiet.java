package com.lifePreserverDiet.PFD.UserInterface;

import java.util.Date;

import android.app.Application;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

public class LifePreserverDiet extends Application {
	private Day day;
	private DayDataSource dataSource;
	public static final String PREFS_NAME = "PFDPrefsFile";
	
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
		
		if (!day.getVisited()){
			day.setVisited(true);
			dataSource.updateDay(day);
		}
		
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
	
}
