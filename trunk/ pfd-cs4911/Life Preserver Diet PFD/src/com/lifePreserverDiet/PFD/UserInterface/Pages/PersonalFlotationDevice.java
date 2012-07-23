package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDiet;
import com.lifePreserverDiet.PFD.UserInterface.Dialogs.PFDShareDialog;

public class PersonalFlotationDevice extends Activity {
	
	Day day;
	

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_pfd);
		
		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		day = app.getDay();	
		
		fillTable();
	}
	
	public void onResume() {
		super.onResume();
		
		fillTable();
	}
	
	public void test(View v) {
		Intent x = new Intent(this, PFDShareDialog.class);
		x.putExtra("id", v.getId());
		startActivity(x);
	}
	
	public void onPause() {
		super.onPause();
		
		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		app.updateDay();
	}
	
	public void fillTable() {
		TextView meatBeansShares = (TextView) findViewById(R.id.textView_pfd_shares_meatbeans);
		TextView dairyShares = (TextView) findViewById(R.id.textView_pfd_shares_dairy);
		TextView veggiesShares = (TextView) findViewById(R.id.textView_pfd_shares_veggies);
		TextView extraShares = (TextView) findViewById(R.id.textView_pfd_shares_extra);
		TextView fruitsShares = (TextView) findViewById(R.id.textView_pfd_shares_fruit);
		TextView wholeGrainsShares = (TextView) findViewById(R.id.textView_pfd_shares_wholeGrains);
		TextView exerciseShares = (TextView) findViewById(R.id.textView_pfd_shares_exercise);
		TextView exerciseTimeShares = (TextView) findViewById(R.id.textView_pfd_shares_exerciseMinutes);
		
		if(day.getExercise()) {
			exerciseShares.setText("Y");
			exerciseTimeShares.setText(day.getExerciseMinutes() + " min");
		} else {
			exerciseShares.setText("N");
			exerciseTimeShares.setText("0 min");
		}
		
		if(day.getMeatBeans() >= 3) {
			meatBeansShares.setText("0");
		} else {
			meatBeansShares.setText((3-day.getMeatBeans())+"");
		}
		
		if(day.getDairy() >= 3) {
			dairyShares.setText("0");
		} else {
			dairyShares.setText((3-day.getDairy())+"");
		}
		
		if(day.getVeggies() >= 4) {
			veggiesShares.setText("0");
		} else {
			veggiesShares.setText((4-day.getVeggies())+"");
		}
		
		if(day.getExtra() >= 3) {
			extraShares.setText("0");
		} else {
			extraShares.setText((3-day.getExtra())+"");
		}
		
		if(day.getFruit() >= 3) {
			fruitsShares.setText("0");
		} else {
			fruitsShares.setText((3-day.getFruit())+"");
		}
		
		if(day.getWholeGrains() >= 3) {
			wholeGrainsShares.setText("0");
		} else {
			wholeGrainsShares.setText((3-day.getWholeGrains())+"");
		}
		
		
	}

}
