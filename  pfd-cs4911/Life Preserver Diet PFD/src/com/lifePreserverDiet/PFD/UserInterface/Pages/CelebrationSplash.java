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

/**
 * An Activity that shows an animated "celebration" splash screen. 
 * It uses an AnimationDrawable and plays it for four seconds.
 * 
 * @author Devin Wang
 */
public class CelebrationSplash extends Activity {
	
	/**
	 * Thread used for Splash screen.
	 */
	private Thread mSplashThread;
	
	/**
	 * Date to access today's information in the data.
	 */
	private Date mydate;
	
	/**
	 * Database access object.
	 */
	private DayDataSource datasource;
	
	/**
	 * Day to hold today's data.
	 */
	private Day myDay;
	
	/**
	 * Day to hold yesterday's data.
	 */
	private Day d;
	
	/**
	 * boolean indicating the gender of the user.
	 */
	private boolean isFemale;

	/**
	 * Only plays the animation if the user earned a perfect score
	 * yesterday (based on his or her gender) and is opening the
	 * app for the first time today.
	 * 
	 * @param bundle
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		
		// get the user's gender
		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		isFemale = app.isFemale();
		
		datasource = new DayDataSource(this);
		datasource.open();
		
		
		// Day object for today
		myDay = app.getDay();
		
		// Set date to yesterday; to test today, check in all shares
		// (get a score of at least 20.0) and then comment out this line.
		mydate = new Date();
		mydate.setTime(mydate.getTime() - (24*60*60*1000));
		
		// Day object for yesterday
		d = datasource.getDay(mydate);
		
		
		// get the target total shares for each food group based on gender
		double wholeGrainsTotal, dairyTotal, meatBeansTotal, fruitTotal, extraTotal;
		if(isFemale) wholeGrainsTotal = dairyTotal = meatBeansTotal = fruitTotal = extraTotal = 3.0;
		else wholeGrainsTotal = dairyTotal = meatBeansTotal = fruitTotal = extraTotal = 4.0;

		
		// only play the splash screen if yesterday's data exists and
		// the user is opening the app for the first time today
		if(d != null && !myDay.getVisited()){
			
			// update the database to know that the user has already
			// opened the app today
			myDay.setVisited(true);
			datasource.updateDay(myDay);
			
			// compute the user's score for yesterday
			double exerciseShare = (d.getExerciseMinutes() > 0) ? 1.0 : 0.0;
			double total =
					wholeGrainsTotal - Math.abs(wholeGrainsTotal - d.getWholeGrains()) +
					dairyTotal - Math.abs(dairyTotal - d.getDairy()) + 
					meatBeansTotal - Math.abs(meatBeansTotal - d.getMeatBeans()) +
					fruitTotal - Math.abs(fruitTotal - d.getFruit()) + 
					d.getVeggies() + 
					extraTotal - Math.abs(extraTotal - d.getExtra()) +
					exerciseShare;
			
			// compare is the target score based on gender
			double compare;
			if(isFemale) compare = 20.0;
			else compare = 26.0;
			
			// if the user earned a perfect score or higher, play the animation
			if(total >= compare){
				setContentView(R.layout.page_splash_congrats);
				final CelebrationSplash splashScreen = this;
				
				// have to use a Thread because cannot call the run directly from
				// onCreate()
				mSplashThread = new Thread(){
					public void run(){
						try{
							synchronized(this){
								wait(4000); // play the animation for 4 seconds
							}
						}
						catch(InterruptedException ex){
						}
						finish();
						datasource.close();
						// Move on to the regular Splash screen
						Intent intent = new Intent();
						intent.setClass(splashScreen,  Splash.class);
						startActivity(intent);
						stop();
					}
				};
				
				// start the Thread
				mSplashThread.start();
				
				// set the LinearLayout background on the phone to the AnimationDrawable
				final LinearLayout img = (LinearLayout)findViewById(R.id.linlayout);
				img.setBackgroundResource(R.drawable.celebration_splash);
				// get the background, which has been compiled to an AnimationDrawable object
				final AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
				if(true){
					// run the AnimationDrawable
					img.post(new Runnable(){
						public void run(){
							frameAnimation.start();
						}
					});
				}
			}
			// otherwise, just move on to the regular Splash screen
			else{
				datasource.close();
				Intent intent = new Intent(CelebrationSplash.this, Splash.class);
				CelebrationSplash.this.startActivity(intent);
				CelebrationSplash.this.finish();
			}
		}
		// otherwise, just move on to the regular Splash screen
		else{
			datasource.close();
			Intent intent = new Intent(CelebrationSplash.this, Splash.class);
			CelebrationSplash.this.startActivity(intent);
			CelebrationSplash.this.finish();
		}

	}
}