package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;
/**
 * Main Activity for the application. Functions as the Home screen,
 * where all other pages are linked from. 
 * 
 * Can access the PFD, History page, About Diet page, Contact page, 
 * and a link to the book's Amazon page.
 * 
 * @author David Murray
 */
public class Main extends Activity {

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_main);
	}
	
	/**
	 * The method that opens the PFD page, on a button click.
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void personalFlotationDevice(View v) {
		Intent intent = new Intent(this, PersonalFlotationDevice.class);
		startActivity(intent);
	}
	
	/**
	 * The method that opens the History page, on a button click.
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void chartHistory(View v) {
		Intent intent = new Intent(this, ChartHistory.class);
		startActivity(intent);
	}
	
	/**
	 * The method that opens the browser to the Amazon page of the 
	 * Life Preserver Diet book.
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void book(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW); 
		intent.setData(Uri.parse("http://www.amazon.com/Life-Preserver-Diet%C2%AE-happier-healthier/dp/0983566917/ref=sr_1_1?ie=UTF8&qid=1317873144&sr=8-1")); 
		startActivity(intent);
	}
	
	/**
	 * The method that opens the About Diet page, on a button click.
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void about(View v) {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}
	
	/**
	 * The method that opens the Contact page, on a button click.
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void contact(View v) {
		Intent intent = new Intent(this, Contact.class);
		startActivity(intent);
	}
	
	/**
	 * The method that opens a page used to test the database.
	 * 
	 * This doesn't get called right now because the button connected
	 * to it is hidden.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
    public void test_database(View v) {
    	Intent intent = new Intent(this, TestDatabaseActivity.class);
    	startActivity(intent);
    }
    
}
