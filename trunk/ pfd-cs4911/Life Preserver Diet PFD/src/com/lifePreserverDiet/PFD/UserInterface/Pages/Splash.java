package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Chronometer;

import com.lifePreserverDiet.PFD.R;

/**
 * An Activity that shows the Splash screen. 
 * It uses a Chronometer tick that counts 2-3 seconds then ends the Activity.
 * 
 * @author David Murray
 */
public class Splash extends Activity {
	/**
	 * How many seconds have gone by.
	 */
	int counter = 0;


	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout. Also sets up and starts the chronometer to tick
	 * and check the time. If it's over 3 seconds it'll close.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_splash);
		this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
		Chronometer chrono = (Chronometer) findViewById(R.id.chronometer_splash_chrono);
		chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			
			public void onChronometerTick(Chronometer chronometer) {
				counter++;
				if(counter > 2) {
					Intent intent = new Intent(Splash.this, Main.class);
					Splash.this.startActivity(intent);
					Splash.this.finish();
				}
			}
		});
		chrono.start();
	}
}
