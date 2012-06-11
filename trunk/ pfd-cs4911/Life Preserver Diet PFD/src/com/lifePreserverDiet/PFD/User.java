package com.lifePreserverDiet.PFD;

import java.util.UUID;

public class User {
	private boolean isFemale;
	private String email;
	private boolean isMetric;
	
	private UUID dbNumber;
	
	public User(boolean setupValues) {
		if(setupValues) {
			isFemale = true;
			email = "";
			isMetric = false;
		}
		dbNumber = UUID.randomUUID();
	}
	
	public boolean isFemale() {
		return isFemale;
	}
	public void setFemale(boolean isFemale) {
		this.isFemale = isFemale;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isMetric() {
		return isMetric;
	}
	public void setMetric(boolean isMetric) {
		this.isMetric = isMetric;
	}
	public void setDbNumber(UUID dbNumber) {
		this.dbNumber = dbNumber;
	}
	public UUID getDbNumber() {
		return dbNumber;
	}
	
	public void setFields(User user) {
		this.isFemale = user.isFemale;
		this.email = user.email;
		this.isMetric = user.isMetric;
		
		this.dbNumber = user.dbNumber;
	}
}
