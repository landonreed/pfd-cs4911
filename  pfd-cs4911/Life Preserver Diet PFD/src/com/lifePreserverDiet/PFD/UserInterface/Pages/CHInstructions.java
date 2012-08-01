package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;
/**
 * An Activity that simply shows the instructions screen for
 * Chart your History.
 * 
 * @author Devin Wang
 */
public class CHInstructions extends Activity {

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 */
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_ch_instructions);
	}
}
