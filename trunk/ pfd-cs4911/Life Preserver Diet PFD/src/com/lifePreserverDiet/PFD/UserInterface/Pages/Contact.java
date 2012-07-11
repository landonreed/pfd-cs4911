package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lifePreserverDiet.PFD.R;

public class Contact extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_contact);
	}
	
	public void email(View v) {
		try {		
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			//
			emailIntent.setType("plain/text");

			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "info@lifepreserverdiet.com" });

			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Consultation Request");

			//emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Fish");

			this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

		} catch (Throwable t) {
			Toast.makeText(this, "Request failed: " + t.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
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
