package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

public class Main extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_main);
	}
	
	public void personalFlotationDevice(View v) {
		Intent intent = new Intent(this, PersonalFlotationDevice.class);
		startActivity(intent);
	}
	
	public void chartHistory(View v) {
		Intent intent = new Intent(this, ChartHistory.class);
		startActivity(intent);
	}
	
	public void book(View v) {
//		Intent intent = new Intent(this, Book.class);
//		startActivity(intent);
		Intent intent = new Intent(Intent.ACTION_VIEW); 
		intent.setData(Uri.parse("http://www.amazon.com/Life-Preserver-Diet%C2%AE-happier-healthier/dp/0983566917/ref=sr_1_1?ie=UTF8&qid=1317873144&sr=8-1")); 
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
