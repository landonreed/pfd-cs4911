package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Chronometer;

import com.lifePreserverDiet.PFD.R;

public class Splash extends Activity {
	
	int counter = 0;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_splash);
		this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
		Chronometer chrono = (Chronometer) findViewById(R.id.chronometer_splash_chrono);
		chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
			
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				counter++;
				if(counter > 1) {
					Intent intent = new Intent(Splash.this, Main.class);
					Splash.this.startActivity(intent);
					Splash.this.finish();
				}
			}
		});
		chrono.start();
	}
}
