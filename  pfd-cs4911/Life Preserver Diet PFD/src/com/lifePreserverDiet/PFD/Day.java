package com.lifePreserverDiet.PFD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Day {

	private long id; // db column id
	private Date date;
	private int wholeGrains, dairy, meatBeans, fruit, veggies, extra, exercise;

	@Override
	public String toString() {
		return new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a").format(date);
	}
	
	public Date getDate(){ return date; }
	public void setDate(String date){
		try{
			this.date = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss.SSS a").parse(date);
		}catch (ParseException e){
			e.printStackTrace();
		}
	}

	public long getId(){ return id; }
	public void setId(long id){ this.id = id; }
	
	public int getWholeGrains(){ return wholeGrains; }
	public void setWholeGrains(int wg){ wholeGrains = wg; }
	
	public int getDairy(){ return dairy; }
	public void setDairy(int dairy){ this.dairy = dairy; }
	
	public int getMeatBeans(){ return meatBeans; }
	public void setMeatBeans(int meatBeans){ this.meatBeans = meatBeans; }
	
	public int getFruit(){ return fruit; }
	public void setFruit(int fruit){ this.fruit = fruit; }
	
	public int getVeggies(){ return veggies; }
	public void setVeggies(int veggies){ this.veggies = veggies; }
	
	public int getExtra(){ return extra; }
	public void setExtra(int extra){ this.extra = extra; }
	
	public int getExercise(){ return exercise; }
	public void setExercise(int exercise){ this.exercise = exercise; }
}
