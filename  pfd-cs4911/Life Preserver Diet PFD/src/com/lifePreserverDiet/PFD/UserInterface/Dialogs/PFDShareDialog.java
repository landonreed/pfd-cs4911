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

/**
 * An Activity that acts as a Dialog for the PFD page.
 * This contains all the methods for each type of share.
 * 
 * It checks the resource id passed to it from the PFD page,
 * and runs a method that corresponds to the type of share,
 * showing the proper xml file and setting up the text for
 * male/female, etc. 
 * 
 * @author David Murray
 */
public class PFDShareDialog extends Activity {
	/**
	 * The current day.
	 */
	private Day day;
	/**
	 * The ID of the ImageView (acting as a button on the PFD) that tells which 
	 * share you're looking at.
	 */
	private int id;
	/**
	 * A TextView for updating the page to show how many shares you have. It is set up
	 * in the code below to be able to be generic, referencing a different TextView on
	 * each layout file. It is how the the increment and decrement methods can be generic, also.
	 */
	private TextView shares;
	/**
	 * Whether the user is female or not, given from the CheckBox on the PFD.
	 */
	boolean isFemale;

	/**
	 * Called on create of the Activity. Sets up the activity
	 * with its layout.
	 * 
	 * Will call a corresponding method for the share type you're looking at.
	 */
	public void onCreate(Bundle b) {
		super.onCreate(b);

		//Get a reference to the application, to gain access to the current Day object.
		LifePreserverDiet application = (LifePreserverDiet) this.getApplication();
		day = application.getDay();

		//Get whether or not the user is a female from the previous Activity.
		isFemale = this.getIntent().getBooleanExtra("isFemale", true);

		//Get the ID of ImageView of the type of share you're looking at, 
		//and run a corresponding method to show that layout file.
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

	/**
	 * Updates the Dialog to show the Dairy layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void dairy() {
		setContentView(R.layout.dialog_pfd_dairy);
		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_dairy_shares)).setText("3");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_dairy_shares)).setText("4");
		}
		shares = (TextView) findViewById(R.id.dialog_editText_pfd_dairy_shares);

		int count = day.getDairy();

		shares.setText(count + "");

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Extra layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void extra() {
		setContentView(R.layout.dialog_pfd_extra);

		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_extra_shares)).setText("3");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_extra_shares)).setText("4");
		}

		shares = (TextView) findViewById(R.id.dialog_editText_pfd_extra_shares);

		int count = day.getExtra();

		shares.setText(count + "");

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Fruits layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void fruits() {
		setContentView(R.layout.dialog_pfd_fruit);

		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_fruit_shares)).setText("3");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_fruit_shares)).setText("4");
		}

		shares = (TextView) findViewById(R.id.dialog_editText_pfd_fruit_shares);

		int count = day.getFruit();

		shares.setText(count + "");

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Veggies layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void veggies() {
		setContentView(R.layout.dialog_pfd_veggies);

		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_veggies_shares)).setText("4");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_veggies_shares)).setText("5");
		}

		shares = (TextView) findViewById(R.id.dialog_editText_pfd_veggies_shares);

		int count = day.getVeggies();	

		shares.setText(count + "");	

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Meat & Beans layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void meatbeans() {
		setContentView(R.layout.dialog_pfd_meatbeans);

		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_meatbeans_shares)).setText("3");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_meatbeans_shares)).setText("4");
		}

		shares = (TextView) findViewById(R.id.dialog_editText_pfd_meatbeans_shares);

		int count = day.getMeatBeans();

		shares.setText(count + "");

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Whole Grains layout file, setting the required share,
	 * current shares and calls the updateTextColor function to correct the color.
	 */
	private void wholegrains() {
		setContentView(R.layout.dialog_pfd_wholegrains);

		if(isFemale) {
			((TextView) findViewById(R.id.dialog_textView_pfd_wholegrains_shares)).setText("3");
		} else {
			((TextView) findViewById(R.id.dialog_textView_pfd_wholegrains_shares)).setText("4");
		}

		shares = (TextView) findViewById(R.id.dialog_editText_pfd_wholegrains_shares);

		int count = day.getWholeGrains();

		shares.setText(count + "");

		updateTextColor(count);
	}

	/**
	 * Updates the Dialog to show the Exercise layout file, showing whether or not 
	 * the user has already exercised today or not.
	 */
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
	
	/**
	 * Calls when the Dialog closes, and calls the corresponding share
	 * method to update to the Day object in the Application.
	 */
	@Override
	protected void onPause() {
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

	/**
	 * Update the Dairy shares to be correct when this Activity closes.
	 */
	private void updateDairy() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setDairy(count);
	}

	/**
	 * Update the Extra shares to be correct when this Activity closes.
	 */
	private void updateExtra() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setExtra(count);
	}

	/**
	 * Update the Fruits shares to be correct when this Activity closes.
	 */
	private void updateFruits() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setFruit(count);
	}

	/**
	 * Update the Meat&Beans shares to be correct when this Activity closes.
	 */
	private void updateMeatBeans() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setMeatBeans(count);
	}

	/**
	 * Update the Veggies shares to be correct when this Activity closes.
	 */
	private void updateVeggies() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setVeggies(count);
	}

	/**
	 * Update the Whole Grains shares to be correct when this Activity closes.
	 */
	private void updateWholeGrains() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setWholeGrains(count);
	}

	/**
	 * Update the Exercise shares to be correct when this Activity closes.
	 */
	private void updateExercise() {
		int count = Integer.parseInt(shares.getText().toString());

		day.setExerciseMinutes(count);

		if(((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).isChecked()) {
			day.setExercise(true);
		} else {
			day.setExercise(false);
		}
		
		//Does not set having exercised to true if the time 
		//exercised is 0 minutes.
		if(count == 0) {
			day.setExercise(false);
		}
	}

	/**
	 * Generic increment method for all share types. Increments the value
	 * already on the screen for how many shares.. lets the user check in their shares.
	 */
	public void incrementShares(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares++;		
		shares.setText(num_shares + "");

		updateTextColor(num_shares);
	}

	/**
	 * Generic decrement method for all share types. Decrements the value
	 * already on the screen for how many shares, if the user needs to change it
	 * for some reason.
	 */
	public void decrementShares(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares--;

		if(num_shares < 0) {
			num_shares = 0;
		}

		shares.setText(num_shares + "");

		updateTextColor(num_shares);
	}

	/**
	 * Updates the color of the text based on if you're 
	 * below, at or above the required shares for the day.
	 * 
	 * It sets the color to black if it is below the limit, 
	 * green if it is perfect on it, and red if it is above.
	 * 
	 * @param num_shares The number of shares already had.
	 */
	private void updateTextColor(int num_shares) {	
		if(isFemale) {
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
					//You can never go over on veggies, so it will never turn red.
					shares.setTextColor(Color.GREEN);
				} else {
					shares.setTextColor(Color.BLACK);
				}
			}
		} else {
			if(id != R.id.imageView_pfd_vegetables) {
				if(num_shares == 4) {
					shares.setTextColor(Color.GREEN);
				} else if (num_shares > 4) {
					shares.setTextColor(Color.RED);
				} else {
					shares.setTextColor(Color.BLACK);
				}
			} else {
				if(num_shares >= 5) {
					//You can never go over on veggies, so it will never turn red.
					shares.setTextColor(Color.GREEN);
				} else {
					shares.setTextColor(Color.BLACK);
				}
			}
		}
	}

	/**
	 * Increment Exercise increments the time exercised in minutes by 5 minute intervals.
	 */
	public void incrementExercise(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares+=5;		
		shares.setText(num_shares + "");

		((CheckBox) findViewById(R.id.dialog_checkBox_pfd_exercise)).setChecked(true);

		shares.setTextColor(Color.GREEN);
	}

	/**
	 * Decrement Exercise decrements the time exercised in minutes by 5 minute intervals.
	 */
	public void decrementExercise(View v) {
		int num_shares = Integer.parseInt(shares.getText().toString());
		num_shares-=5;

		//If exercise decreases in minutes to 0, the CheckBox 
		//will change to have not exercised.
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
