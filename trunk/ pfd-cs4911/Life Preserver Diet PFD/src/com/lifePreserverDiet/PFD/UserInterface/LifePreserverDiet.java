package com.lifePreserverDiet.PFD.UserInterface;

import java.util.Date;
import java.util.List;

import android.app.Application;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

public class LifePreserverDiet extends Application {
	private Day day;
	
	private DayDataSource dataSource;
	

	public void onCreate() {
		super.onCreate();	

		dataSource = new DayDataSource(this);
		dataSource.open();

		/*
		List<Day> values = dataSource.getAllDays();
		
		if(values.size() > 0) {
			Date today = new Date();
			Date temp = values.get(values.size() - 1).getDate();
			if(today.getYear() == temp.getYear() && (today.getMonth() == temp.getMonth()) && (today.getDate() == temp.getDate())) {
				day = values.get(values.size() - 1);
			} else {
				day = dataSource.createDay();
			}
		} 
		else {
			day = dataSource.createDay();
		}*/
		
		Day today = dataSource.getDay(new Date());
		if (today == null)
			day = dataSource.createDay();
		else
			day = today;
	}
	
	public Day getDay() {
		return day;
	}
	
	public void updateDay(Day day) {
		dataSource.updateDay(day);
	}
	
	public void updateDay() {
		dataSource.updateDay(day);
	}
	
}
