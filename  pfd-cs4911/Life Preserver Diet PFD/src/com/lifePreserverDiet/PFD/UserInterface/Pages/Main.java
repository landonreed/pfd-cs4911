package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

public class Main extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main);
	}
	
	public void personalFlotationDevice(View v) {
		Intent intent = new Intent(this, PersonalFlotationDevice.class);
		startActivity(intent);
	}
	
	public void captainsLog(View v) {
		Intent intent = new Intent(this, CaptainsLog.class);
		startActivity(intent);
	}
	
	public void book(View v) {
		Intent intent = new Intent(this, Book.class);
		startActivity(intent);
	}
	
	public void about(View v) {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}
	
	public void contact(View v) {
		Intent intent = new Intent(this, Contact.class);
		startActivity(intent);
	}
    
    public void test_database(View v) {
    	Intent intent = new Intent(this, TestDatabaseActivity.class);
    	startActivity(intent);
    }	
}
