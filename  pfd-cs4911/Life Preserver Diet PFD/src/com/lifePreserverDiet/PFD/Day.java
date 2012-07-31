package com.lifePreserverDiet.PFD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Day {
	/** Database column id */
	private long id;
	private Date date;
	private int wholeGrains, dairy, meatBeans, fruit, veggies, extra, exercise_minutes;
	private boolean exercise, visited;
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");

	public Day() {
		date = new Date();
		wholeGrains = 0;
		dairy = 0;
		meatBeans = 0;
		fruit = 0;
		veggies = 0;
		extra = 0;
		exercise = false;
		exercise_minutes = 0;
		visited = false;
	}
	
	@Override
	public String toString() {
		return date.toString();
	}
	
	public Date getDate(){ return date; }
	public void setDate(String dateString){
		try{
			date = dateFormat.parse(dateString);
		}catch(ParseException e){
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
	
	public boolean getExercise(){ return exercise; }
	public void setExercise(boolean exercise){ this.exercise = exercise; }
	
	public int getExerciseMinutes(){ return exercise_minutes; }
	public void setExerciseMinutes(int exerciseMinutes){ this.exercise_minutes = exerciseMinutes; }
	
	public boolean getVisited(){ return visited; }
	public void setVisited(boolean visited){ this.visited = visited; }
}
