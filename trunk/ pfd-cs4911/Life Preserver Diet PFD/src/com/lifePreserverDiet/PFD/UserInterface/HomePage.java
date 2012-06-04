package com.lifePreserverDiet.PFD.UserInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

public class HomePage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
    
    public void about(View v) {
    	Intent intent = new Intent(this, AboutPage.class);
    	startActivity(intent);
    }
    
    public void checkIn(View v) {
    	Intent intent = new Intent(this, CheckInPage.class);
    	startActivity(intent);
    }
    
    public void history(View v) {
    	Intent intent = new Intent(this, HistoryPage.class);
    	startActivity(intent);
    }
    
    public void userSettings(View v) {
    	Intent intent = new Intent(this, UserSettingsPage.class);
    	startActivity(intent);
    }
}