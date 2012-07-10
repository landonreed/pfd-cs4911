package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lifePreserverDiet.PFD.R;

public class PersonalFlotationDevice extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.pfd);
	}
	
	public void test(View v) {
		int id = v.getId();
		switch(id) {
		case R.id.imageView_pfd_dairy:
			Toast.makeText(this, "Dairy", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_exercise:
			Toast.makeText(this, "Exercise", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_extra:
			Toast.makeText(this, "Extra", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_fruits:
			Toast.makeText(this, "Fruits", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_meatandbeans:
			Toast.makeText(this, "Meat & Beans", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_vegetables:
			Toast.makeText(this, "Vegetables", Toast.LENGTH_SHORT).show();
			break;
		case R.id.imageView_pfd_wholegrains:
			Toast.makeText(this, "Whole Grains", Toast.LENGTH_SHORT).show();
			break;
		}
	}

}
