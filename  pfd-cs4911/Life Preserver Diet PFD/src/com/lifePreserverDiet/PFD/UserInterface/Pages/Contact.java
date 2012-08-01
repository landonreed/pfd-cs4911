package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lifePreserverDiet.PFD.R;
/**
 * An Activity that shows the Contact Us page.  
 * 
 * From this page, the user can open an email address to ask for 
 * consultation, as well as connect to Skype.
 * 
 * @author David Murray
 */
public class Contact extends Activity {

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_contact);
	}
	
	/**
	 * The method that opens the email application with an email set up 
	 * for consultation.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void email(View v) {
		try {		
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			
			emailIntent.setType("plain/text");

			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "marciaberlin@gmail.com" });

			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Consultation Request");

			this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

		} catch (Throwable t) {
			Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * The method that opens Skype if the user has that app installed.
	 * If Skype isn't installed, it opens to the App Marketplace.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void skype(View v) {
		try {
			PackageManager packageManager = getPackageManager();
			Intent i = packageManager.getLaunchIntentForPackage("com.skype.raider");
			startActivity(i);
		} catch(NullPointerException e) {
			Intent intent = new Intent(Intent.ACTION_VIEW); 
			intent.setData(Uri.parse("https://market.android.com/search?q=pname:com.skype.raider")); 
			startActivity(intent);
		} catch (Throwable t) {
			Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
		}
	}

}
