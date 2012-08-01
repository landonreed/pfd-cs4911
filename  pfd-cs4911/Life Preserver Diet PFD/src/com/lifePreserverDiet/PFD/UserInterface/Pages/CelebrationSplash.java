package com.lifePreserverDiet.PFD.UserInterface.Pages;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDiet;
import com.lifePreserverDiet.PFD.Utilities.DayDataSource;

public class CelebrationSplash extends Activity {
	
	private Thread mSplashThread;
	private Date mydate;
	private DayDataSource datasource;
	private Day myDay;
	private Day d;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		datasource = new DayDataSource(this);
		datasource.open();
		
		mydate = new Date();
		myDay = datasource.getDay(mydate);
		mydate.setTime(mydate.getTime() - (24*60*60*1000)); // set date to yesterday; to test today, check in all shares (get a score of at least 20.0) and then comment out this line.
		
		d = datasource.getDay(mydate);
		double wholeGrainsTotal, dairyTotal, meatBeansTotal, fruitTotal, extraTotal;
		wholeGrainsTotal = dairyTotal = meatBeansTotal = fruitTotal = extraTotal = 3.0;
		if(d != null && !myDay.getVisited()){
			myDay.setVisited(true);
			datasource.updateDay(myDay);
			
			double exerciseShare = (d.getExerciseMinutes() > 0) ? 1.0 : 0.0;
			double total =
					wholeGrainsTotal - Math.abs(wholeGrainsTotal - d.getWholeGrains()) +
					dairyTotal - Math.abs(dairyTotal - d.getDairy()) + 
					meatBeansTotal - Math.abs(meatBeansTotal - d.getMeatBeans()) +
					fruitTotal - Math.abs(fruitTotal - d.getFruit()) + 
					d.getVeggies() + 
					extraTotal - Math.abs(extraTotal - d.getExtra()) +
					exerciseShare;
			if(total >= 0.0){
				setContentView(R.layout.page_splash_congrats);
				final CelebrationSplash splashScreen = this;
				mSplashThread = new Thread(){
					public void run(){
						try{
							synchronized(this){
								wait(4000);
							}
						}
						catch(InterruptedException ex){
						}
						finish();
						datasource.close();
						Intent intent = new Intent();
						intent.setClass(splashScreen,  Splash.class);
						startActivity(intent);
						stop();
					}
				};
				mSplashThread.start();
				
				final LinearLayout img = (LinearLayout)findViewById(R.id.linlayout);
				img.setBackgroundResource(R.drawable.celebration_splash);
				// Get the background, which has been compiled to an AnimationDrawable object.
				final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				if(true){
					img.post(new Runnable(){
						public void run(){
							frameAnimation.start();
						}
					});
				}
			}
			else{
				datasource.close();
				Intent intent = new Intent(CelebrationSplash.this, Splash.class);
				CelebrationSplash.this.startActivity(intent);
				CelebrationSplash.this.finish();
			}
		}
		else{
			datasource.close();
			Intent intent = new Intent(CelebrationSplash.this, Splash.class);
			CelebrationSplash.this.startActivity(intent);
			CelebrationSplash.this.finish();
		}
	}
}