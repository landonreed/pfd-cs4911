package com.lifePreserverDiet.PFD.UserInterface.Pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDiet;
import com.lifePreserverDiet.PFD.UserInterface.Dialogs.PFDShareDialog;

public class PersonalFlotationDevice extends Activity {

	private Day day;
	private CheckBox femaleCheckBox;
	private SharedPreferences settings;

	boolean isFemale;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.page_pfd);

		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		day = app.getDay();

		// Get the user settings file (saves the check box state)
		settings = app.getSharedPreferences(LifePreserverDiet.PREF_NAME, MODE_PRIVATE);
		femaleCheckBox = (CheckBox) findViewById(R.id.female_checkbox);

		// Set the check box state to that of the user settings file
		femaleCheckBox.setChecked(settings.getBoolean(LifePreserverDiet.PREF_BOOL, true));
		
	}

	@Override
	public void onResume() {
		super.onResume();
		fillTable();
	}

	@Override
	public void onPause() {
		super.onPause();

		LifePreserverDiet app = (LifePreserverDiet) this.getApplication();
		app.updateDay();

		// Save the current check box state to the user settings file
		settings.edit()
		.putBoolean(LifePreserverDiet.PREF_BOOL, femaleCheckBox.isChecked())
		.commit();
	}

	public void pfdInstructions(View v) {
		Intent intent = new Intent(this, PFDInstructions.class);
		startActivity(intent);
	}

	public void pfdDialog(View v) {
		isFemale = femaleCheckBox.isChecked();
		Intent intent = new Intent(this, PFDShareDialog.class);
		intent.putExtra("id", v.getId());
		intent.putExtra("isFemale", isFemale);
		startActivity(intent);
	}

	public void fillTable() {		
		isFemale = femaleCheckBox.isChecked();
		
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

		int maxNum = (isFemale) ? 3 : 4;
		
		if(day.getMeatBeans() >= maxNum) {
			if(day.getMeatBeans() > maxNum) {
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
			meatBeansShares.setText((maxNum-day.getMeatBeans())+"");
		}

		if(day.getDairy() >= maxNum) {
			if(day.getDairy() > maxNum) {
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
			dairyShares.setText((maxNum-day.getDairy())+"");
		}

		if(day.getVeggies() >= (maxNum+1)) {
			veggies.setImageResource(R.drawable.icon_pfd_vegetables_good);
			veggiesShares.setTextColor(Color.GREEN);
			veggiesShares.setText("0");
		} else {
			veggies.setImageResource(R.drawable.icon_pfd_vegetables);
			veggiesShares.setTextColor(Color.BLACK);
			veggiesShares.setText(((maxNum+1)-day.getVeggies())+"");
		}

		if(day.getExtra() >= maxNum) {
			if(day.getExtra() > maxNum) {
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
			extraShares.setText((maxNum-day.getExtra())+"");
		}

		if(day.getFruit() >= maxNum) {
			if(day.getFruit() > maxNum) {
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
			fruitsShares.setText((maxNum-day.getFruit())+"");
		}

		if(day.getWholeGrains() >= maxNum) {
			if(day.getWholeGrains() > maxNum) {
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
			wholeGrainsShares.setText((maxNum-day.getWholeGrains())+"");
		}
	}
	
	public void onClick(View v) {
		CheckBox chkbx = (CheckBox) v;
		isFemale = chkbx.isChecked();
		fillTable();
	}

}
