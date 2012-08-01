package com.lifePreserverDiet.PFD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Day is an object that holds a user's share data for a given day and
 * will be stored in the SQLite database.
 * 
 * @author David Murray, Lamine Sissoko
 *
 */
public class Day {
	/** Database column id */
	private long id;
	
	/** The day's date. */
	private Date date;
	
	/** The day's share values. */
	private int wholeGrains, dairy, meatBeans, fruit, veggies, extra, exercise_minutes;
	
	/** Flags for if the user exercised and if the application has been used on this day. */
	private boolean exercise, visited;
	
	/** The formatting used for Dates in the database. */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");

	/**
	 * Constructor that initializes a new day with no share data.
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return date.toString();
	}
	
	
	//////////////////////////
	// GETTERS & SETTERS
	//////////////////////////
	
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
