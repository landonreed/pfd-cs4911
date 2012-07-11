package com.lifePreserverDiet.PFD.UserInterface;

import android.app.Application;

import com.lifePreserverDiet.PFD.Day;

public class LifePreserverDietPFD extends Application {
	private Day day;
	

	public void onCreate() {
		super.onCreate();		
		day = new Day();
	}
	
	public Day getDay() {
		return day;
	}
	
}
