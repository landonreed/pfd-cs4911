package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.os.Bundle;

import com.lifePreserverDiet.PFD.R;
/**
 * An Activity that simply shows the instructions screen for
 * the Personal Food-Tracking/Flotation Device.
 * @author Devin Wang
 */
public class PFDInstructions extends Activity {

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_pfd_instructions);
	}
}
