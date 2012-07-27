package com.lifePreserverDiet.PFD.UserInterface.Dialogs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDiet;

public class PFDShareDialog extends Activity {
	
	Day day;
	int id;
	
	TextView shares;
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		//
		LifePreserverDiet application = (LifePreserverDiet) this.getApplication();
		day = application.getDay();
		//
		id = this.getIntent().getIntExtra("id", R.id.imageView_pfd_dairy);
		switch(id) {
		case R.id.imageView_pfd_dairy:
			dairy();
			break;
		case R.id.imageView_pfd_exercise:
			exercise();
			break;
		case R.id.imageView_pfd_extra:
			extra();
			break;
		case R.id.imageView_pfd_fruits:
			fruits();
			break;
		case R.id.imageView_pfd_meatandbeans:
			meatbeans();
			break;
		case R.id.imageView_pfd_vegetables:
			veggies();
			break;
		case R.id.imageView_pfd_wholegrains:
			wholegrains();
			break;
		}
	}

	private void dairy() {
		setContentView(R.layout.dialog_pfd_dairy);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_dairy_shares)).setText("3");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_dairy_shares);
		
		int count = day.getDairy();
		
		shares.setText(count + "");
		
		updateTextColor(count);
	}

	private void extra() {
		setContentView(R.layout.dialog_pfd_extra);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_extra_shares)).setText("3");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_extra_shares);
		
		int count = day.getExtra();
		
		shares.setText(count + "");
		
		updateTextColor(count);
	}

	private void fruits() {
		setContentView(R.layout.dialog_pfd_fruit);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_fruit_shares)).setText("3");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_fruit_shares);
		
		int count = day.getFruit();
		
		shares.setText(count + "");
		
		updateTextColor(count);
	}

	private void veggies() {
		setContentView(R.layout.dialog_pfd_veggies);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_veggies_shares)).setText("4");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_veggies_shares);
		
		int count = day.getVeggies();	
		
		shares.setText(count + "");	
		
		updateTextColor(count);
	}

	private void meatbeans() {
		setContentView(R.layout.dialog_pfd_meatbeans);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_meatbeans_shares)).setText("3");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_meatbeans_shares);
		
		int count = day.getMeatBeans();
		
		shares.setText(count + "");
		
		updateTextColor(count);
	}

	private void wholegrains() {
		setContentView(R.layout.dialog_pfd_wholegrains);
		
		((TextView) findViewById(R.id.dialog_textView_pfd_wholegrains_shares)).setText("3");
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_wholegrains_shares);
		
		int count = day.getWholeGrains();
		
		shares.setText(count + "");
		
		updateTextColor(count);
	}

	private void exercise() {
		setContentView(R.layout.dialog_pfd_exercise);
		
		boolean exercised = day.getExercise();
		
		if(exercised) {
			((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).setChecked(true);
		}
		
		int count = day.getExerciseMinutes();
		
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_exercise_minutes);
		
		shares.setText(count + "");
		
		if(count > 0) {			
			shares.setTextColor(Color.GREEN);
		}
	}
	
	public void onPause() {
		super.onPause();
		
		switch(id) {
		case R.id.imageView_pfd_dairy:
			updateDairy();
			break;
		case R.id.imageView_pfd_exercise:
			updateExercise();
			break;
		case R.id.imageView_pfd_extra:
			updateExtra();
			break;
		case R.id.imageView_pfd_fruits:
			updateFruits();
			break;
		case R.id.imageView_pfd_meatandbeans:
			updateMeatBeans();
			break;
		case R.id.imageView_pfd_vegetables:
			updateVeggies();
			break;
		case R.id.imageView_pfd_wholegrains:
			updateWholeGrains();
			break;
		}
	}

	private void updateDairy() {
		int count = Integer.parseInt(shares.getText().toString());
		
		day.setDairy(count);
	}

	private void updateExtra() {
		int count = Integer.parseInt(shares.getText().toString());
		
		day.setExtra(count);
	}

	private void updateFruits() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setFruit(count);
	}

	private void updateMeatBeans() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setMeatBeans(count);
	}

	private void updateVeggies() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setVeggies(count);
	}

	private void updateWholeGrains() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setWholeGrains(count);
	}

	private void updateExercise() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setExerciseMinutes(count);
		
		if(((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).isChecked()) {
			day.setExercise(true);
		} else {
			day.setExercise(false);
		}
		
		if(count == 0) {
			day.setExercise(false);
		}
	}
	
	public void incrementShares(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares++;		
		shares.setText(num_shares + "");
		
		updateTextColor(num_shares);
	}
	
	public void decrementShares(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares--;
		
		if(num_shares < 0) {
			num_shares = 0;
		}
		
		shares.setText(num_shares + "");
		
		updateTextColor(num_shares);
	}
	
	private void updateTextColor(int num_shares) {		
		if(id != R.id.imageView_pfd_vegetables) {
			if(num_shares == 3) {
				shares.setTextColor(Color.GREEN);
			} else if (num_shares > 3) {
				shares.setTextColor(Color.RED);
			} else {
				shares.setTextColor(Color.BLACK);
			}
		} else {
			if(num_shares >= 4) {
				shares.setTextColor(Color.GREEN);
			} else {
				shares.setTextColor(Color.BLACK);
			}
		}
	}
	
	public void incrementExercise(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares+=5;		
		shares.setText(num_shares + "");
		
		((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).setChecked(true);
		
		shares.setTextColor(Color.GREEN);
	}
	
	public void decrementExercise(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares-=5;
		
		if(num_shares <= 0) {
			num_shares = 0;				
			((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).setChecked(false);		
			shares.setTextColor(Color.BLACK);
		} else {					
			((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).setChecked(true);
			shares.setTextColor(Color.GREEN);
		}
		
		shares.setText(num_shares + "");
	}

}
