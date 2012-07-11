package com.lifePreserverDiet.PFD.UserInterface.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.lifePreserverDiet.PFD.Day;
import com.lifePreserverDiet.PFD.R;
import com.lifePreserverDiet.PFD.UserInterface.LifePreserverDietPFD;

public class PFDShareDialog extends Activity {
	
	Day day;
	int id;
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		//
		LifePreserverDietPFD application = (LifePreserverDietPFD) this.getApplication();
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
		
		int count = day.getDairy();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_male);

		if(count > 0) {
			share1.setChecked(true);
		}
		if(count > 1) {
			share2.setChecked(true);
		}
		if(count > 2) {
			share3.setChecked(true);
		}
	}

	private void exercise() {
		setContentView(R.layout.dialog_pfd_exercise);
		
		int count = day.getExercise();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_exercise_share1);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_exercise_male);
		
		if(count > 0) {
			share1.setChecked(true);
		}
	}

	private void extra() {
		setContentView(R.layout.dialog_pfd_extra);
		
		int count = day.getExtra();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_male);

		if(count > 0) {
			share1.setChecked(true);
		}
		if(count > 1) {
			share2.setChecked(true);
		}
		if(count > 2) {
			share3.setChecked(true);
		}
	}

	private void fruits() {
		setContentView(R.layout.dialog_pfd_fruit);
		
		int count = day.getFruit();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_male);

		if(count > 0) {
			share1.setChecked(true);
		}
		if(count > 1) {
			share2.setChecked(true);
		}
		if(count > 2) {
			share3.setChecked(true);
		}
	}

	private void veggies() {
		setContentView(R.layout.dialog_pfd_veggies);
		
		int count = day.getVeggies();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share3);
		CheckBox share4 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share4);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_male);

		if(count > 0) {
			share1.setChecked(true);
		}
		if(count > 1) {
			share2.setChecked(true);
		}
		if(count > 2) {
			share3.setChecked(true);
		}
		if(count > 3) {
			share4.setChecked(true);
		}
		
	}

	private void meatbeans() {
		setContentView(R.layout.dialog_pfd_meatbeans);
		
		int count = day.getMeatBeans();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_male);

		if(count > 0) {
			share1.setChecked(true);
		}
		if(count > 1) {
			share2.setChecked(true);
		}
		if(count > 2) {
			share3.setChecked(true);
		}
	}

	private void wholegrains() {
		setContentView(R.layout.dialog_pfd_wholegrains);
		
		int count = day.getWholeGrains();
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_male);
		
		if(count > 1) {
			share1.setChecked(true);
		}
		if(count > 2) {
			share2.setChecked(true);
		}
		if(count > 3) {
			share3.setChecked(true);
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
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_dairy_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		
		day.setDairy(count);
	}

	private void updateExtra() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_extra_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		
		day.setExtra(count);
	}

	private void updateExercise() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_exercise_share1);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_exercise_male);

		if(share1.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		
		day.setExercise(count);
	}

	private void updateFruits() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_fruit_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}		
		
		day.setFruit(count);
	}

	private void updateMeatBeans() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_meatbeans_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		day.setMeatBeans(count);
	}

	private void updateVeggies() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share3);
		CheckBox share4 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_share4);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_veggies_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(share4.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		
		day.setVeggies(count);
	}

	private void updateWholeGrains() {
		int count = 0;
		
		CheckBox share1 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share1);
		CheckBox share2 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share2);
		CheckBox share3 = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_share3);
		CheckBox male = (CheckBox) this.findViewById(R.id.dialog_checkBox_pfd_wholegrains_male);

		if(share1.isChecked()) {
			count++;
		}
		if(share2.isChecked()) {
			count++;
		}
		if(share3.isChecked()) {
			count++;
		}
		if(male.isChecked()) {
			count++;
		}
		
		day.setWholeGrains(count);
	}

}
