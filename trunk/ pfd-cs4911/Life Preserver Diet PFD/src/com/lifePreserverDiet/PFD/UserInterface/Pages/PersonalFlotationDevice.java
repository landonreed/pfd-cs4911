package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

		ImageView meatbeans = (ImageView) findViewById(R.id.imageView_pfd_meatandbeans);
		ImageView dairy = (ImageView) findViewById(R.id.imageView_pfd_dairy);
		ImageView veggies = (ImageView) findViewById(R.id.imageView_pfd_vegetables);
		ImageView extra = (ImageView) findViewById(R.id.imageView_pfd_extra);
		ImageView fruit = (ImageView) findViewById(R.id.imageView_pfd_fruits);
		ImageView wholegrains = (ImageView) findViewById(R.id.imageView_pfd_wholegrains);
		ImageView exercise = (ImageView) findViewById(R.id.imageView_pfd_exercise);
		
		if(day.getExercise()) {
			exercise.setImageResource(R.drawable.icon_pfd_exercise_good);
			exerciseShares.setText("Y");
			exerciseShares.setTextColor(Color.GREEN);
			exerciseTimeShares.setTextColor(Color.GREEN);
			exerciseTimeShares.setText(day.getExerciseMinutes() + " min");
		} else {
			exercise.setImageResource(R.drawable.icon_pfd_exercise);
			day.setExerciseMinutes(0);
			exerciseShares.setText("N");
			exerciseShares.setTextColor(Color.BLACK);
			exerciseTimeShares.setTextColor(Color.BLACK);
			exerciseTimeShares.setText("0 min");
		}
		
		if(day.getMeatBeans() >= 3) {
			if(day.getMeatBeans() > 3) {
				meatbeans.setImageResource(R.drawable.icon_pfd_meatandbeans_bad);
				meatBeansShares.setTextColor(Color.RED);
			} else {
				meatbeans.setImageResource(R.drawable.icon_pfd_meatandbeans_good);
				meatBeansShares.setTextColor(Color.GREEN);
			}
			meatBeansShares.setText("0");
		} else {
			meatbeans.setImageResource(R.drawable.icon_pfd_meatandbeans);
			meatBeansShares.setTextColor(Color.BLACK);
			meatBeansShares.setText((3-day.getMeatBeans())+"");
		}
		
		if(day.getDairy() >= 3) {
			if(day.getDairy() > 3) {
				dairy.setImageResource(R.drawable.icon_pfd_dairy_bad);
				dairyShares.setTextColor(Color.RED);
			} else {
				dairy.setImageResource(R.drawable.icon_pfd_dairy_good);
				dairyShares.setTextColor(Color.GREEN);
			}
			dairyShares.setText("0");
		} else {
			dairy.setImageResource(R.drawable.icon_pfd_dairy);
			dairyShares.setTextColor(Color.BLACK);
			dairyShares.setText((3-day.getDairy())+"");
		}
		
		if(day.getVeggies() >= 4) {
			veggies.setImageResource(R.drawable.icon_pfd_vegetables_good);
			veggiesShares.setTextColor(Color.GREEN);
			veggiesShares.setText("0");
		} else {
			veggies.setImageResource(R.drawable.icon_pfd_vegetables);
			veggiesShares.setTextColor(Color.BLACK);
			veggiesShares.setText((4-day.getVeggies())+"");
		}
		
		if(day.getExtra() >= 3) {
			if(day.getExtra() > 3) {
				extra.setImageResource(R.drawable.icon_pfd_extra_bad);
				extraShares.setTextColor(Color.RED);
			} else {
				extra.setImageResource(R.drawable.icon_pfd_extra_good);
				extraShares.setTextColor(Color.GREEN);
			}
			extraShares.setText("0");
		} else {
			extra.setImageResource(R.drawable.icon_pfd_extra);
			extraShares.setTextColor(Color.BLACK);
			extraShares.setText((3-day.getExtra())+"");
		}
		
		if(day.getFruit() >= 3) {
			if(day.getFruit() > 3) {
				fruit.setImageResource(R.drawable.icon_pfd_fruits_bad);
				fruitsShares.setTextColor(Color.RED);
			} else {
				fruit.setImageResource(R.drawable.icon_pfd_fruits_good);
				fruitsShares.setTextColor(Color.GREEN);
			}
			fruitsShares.setText("0");
		} else {
			fruit.setImageResource(R.drawable.icon_pfd_fruits);
			fruitsShares.setTextColor(Color.BLACK);
			fruitsShares.setText((3-day.getFruit())+"");
		}
		
		if(day.getWholeGrains() >= 3) {
			if(day.getWholeGrains() > 3) {
				wholegrains.setImageResource(R.drawable.icon_pfd_wholegrains_bad);
				wholeGrainsShares.setTextColor(Color.RED);
			} else {
				wholegrains.setImageResource(R.drawable.icon_pfd_wholegrains_good);
				wholeGrainsShares.setTextColor(Color.GREEN);
			}
			wholeGrainsShares.setText("0");
		} else {
			wholegrains.setImageResource(R.drawable.icon_pfd_wholegrains);
			wholeGrainsShares.setTextColor(Color.BLACK);
			wholeGrainsShares.setText((3-day.getWholeGrains())+"");
		}
		
		
	}

}
