package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

/**
 * An Activity that shows the About the Diet page.  
 * 
 * From this page, the user can see the introductory video, 
 * access the Facebook page, and access the Twitter account, as well
 * read more about the diet.
 * 
 * @author David Murray
 */
public class About extends Activity {

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_about_diet);
	}
	
	/**
	 * The method that opens a browser or youtube app to show the video
	 * with the Diet/Book details.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void youtube(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=TIZeFqYaEE0")));
	}
	
	/**
	 * The method that opens the browser to the Facebook page.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void facebook(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/pages/Life-Preserver-Diet/342985727658")));
	}
	
	/**
	 * The method that opens the browser to the Twitter page.
	 * 
	 * @param v The Button that, when clicked, calls this method.
	 */
	public void twitter(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/lifepreservdiet")));
	}
}
