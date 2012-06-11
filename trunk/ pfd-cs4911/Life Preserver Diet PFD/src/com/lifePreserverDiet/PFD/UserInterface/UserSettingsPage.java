package com.lifePreserverDiet.PFD.UserInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.User;
import com.lifePreserverDiet.PFD.Utilities.ObjectDatabaseConnection;

public class UserSettingsPage extends Activity {
	ObjectDatabaseConnection odb;
	User user;
	
	EditText email;
	CheckBox male;
	CheckBox female;
	CheckBox english;
	CheckBox metric;
	
	boolean isFemale;
	boolean isMetric;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        try {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.user_settings);
			odb = new ObjectDatabaseConnection(this);
			user = odb.getActiveUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setupViews();
    }
    
    public void setupViews() {
    	email = (EditText) findViewById(R.id.editText_userSettings_email);
    	male = (CheckBox) findViewById(R.id.checkBox_userSettings_gender_male);
    	female = (CheckBox) findViewById(R.id.checkBox_userSettings_gender_female);
    	english = (CheckBox) findViewById(R.id.checkBox_userSettings_measure_english);
    	metric = (CheckBox) findViewById(R.id.checkBox_userSettings_measure_metric);

    	email.setText(user.getEmail());

    	if(user.isFemale()) {
    		isFemale = true;
    		female.setChecked(true);
    		male.setChecked(false);
    	}
    	else {
    		isFemale = false;
    		female.setChecked(false);
    		male.setChecked(true);
    	}

    	if(user.isMetric()) {
    		isMetric = true;
    		metric.setChecked(true);
    		english.setChecked(false);
    	}
    	else {
    		isMetric = false;
    		metric.setChecked(false);
    		english.setChecked(true);
    	}
    }

    public void onPause() {
    	super.onPause();

    	user.setEmail(email.getText().toString());

    	user.setFemale(isFemale);
    	user.setMetric(isMetric);

    	odb.updateUser(user);
    }

    public void genderClick(View v) {
    	CheckBox current = (CheckBox) v;
    	if(current.equals(female)) {
    		male.setChecked(false);
    		isFemale = true;
    	}
    	else {
    		female.setChecked(false);
    		isFemale = false;
    	}
    }

    public void measureClick(View v) {
    	CheckBox current = (CheckBox) v;
    	if(current.equals(metric)) {
    		english.setChecked(false);
    		isMetric = true;
    	}
    	else {
    		metric.setChecked(false);
    		isMetric = false;
    	}
    }
}