package com.lifePreserverDiet.PFD.UserInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

public class UserSettingsPage extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
    }
    
    public void genderClick(View v) {}
    public void measureClick(View v) {}
}