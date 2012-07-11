package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.Dialogs.PFDShareDialog;

public class PersonalFlotationDevice extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_pfd);
	}
	
	public void test(View v) {
		Intent x = new Intent(this, PFDShareDialog.class);
		x.putExtra("id", v.getId());
		startActivity(x);
	}

}
