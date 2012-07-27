package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.lifePreserverDiet.PFD.R;

public class About extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_about_diet);
	}
	
	public void youtube(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=TIZeFqYaEE0")));
	}
	
	public void facebook(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/pages/Life-Preserver-Diet/342985727658")));
	}
	
	public void twitter(View v) {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/lifepreservdiet")));
	}
}
