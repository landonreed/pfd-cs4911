package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
			exerciseShares.setTextColor(Color.GREEN);
			exerciseTimeShares.setTextColor(Color.GREEN);
			exerciseTimeShares.setText(day.getExerciseMinutes() + " min");
		} else {
			day.setExerciseMinutes(0);
			exerciseShares.setText("N");
			exerciseShares.setTextColor(Color.BLACK);
			exerciseTimeShares.setTextColor(Color.BLACK);
			exerciseTimeShares.setText("0 min");
		}
		
		if(day.getMeatBeans() >= 3) {
			if(day.getMeatBeans() > 3) {
				meatBeansShares.setTextColor(Color.RED);
			} else {
				meatBeansShares.setTextColor(Color.GREEN);
			}
			meatBeansShares.setText("0");
		} else {
			meatBeansShares.setTextColor(Color.BLACK);
			meatBeansShares.setText((3-day.getMeatBeans())+"");
		}
		
		if(day.getDairy() >= 3) {
			if(day.getDairy() > 3) {
				dairyShares.setTextColor(Color.RED);
			} else {
				dairyShares.setTextColor(Color.GREEN);
			}
			dairyShares.setText("0");
		} else {
			dairyShares.setTextColor(Color.BLACK);
			dairyShares.setText((3-day.getDairy())+"");
		}
		
		if(day.getVeggies() >= 4) {
			if(day.getVeggies() > 4) {
				veggiesShares.setTextColor(Color.RED);
			} else {
				veggiesShares.setTextColor(Color.GREEN);
			}
			veggiesShares.setText("0");
		} else {
			veggiesShares.setTextColor(Color.BLACK);
			veggiesShares.setText((4-day.getVeggies())+"");
		}
		
		if(day.getExtra() >= 3) {
			if(day.getExtra() > 3) {
				extraShares.setTextColor(Color.RED);
			} else {
				extraShares.setTextColor(Color.GREEN);
			}
			extraShares.setText("0");
		} else {
			extraShares.setTextColor(Color.BLACK);
			extraShares.setText((3-day.getExtra())+"");
		}
		
		if(day.getFruit() >= 3) {
			if(day.getFruit() > 3) {
				fruitsShares.setTextColor(Color.RED);
			} else {
				fruitsShares.setTextColor(Color.GREEN);
			}
			fruitsShares.setText("0");
		} else {
			fruitsShares.setTextColor(Color.BLACK);
			fruitsShares.setText((3-day.getFruit())+"");
		}
		
		if(day.getWholeGrains() >= 3) {
			if(day.getWholeGrains() > 3) {
				wholeGrainsShares.setTextColor(Color.RED);
			} else {
				wholeGrainsShares.setTextColor(Color.GREEN);
			}
			wholeGrainsShares.setText("0");
		} else {
			wholeGrainsShares.setTextColor(Color.BLACK);
			wholeGrainsShares.setText((3-day.getWholeGrains())+"");
		}
		
		
	}

}
