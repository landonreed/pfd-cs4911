package com.lifePreserverDiet.PFD;


public class User {
	private boolean isFemale;
	private String email;
	
	public User(boolean setupValues) {
		if(setupValues) {
			isFemale = true;
			email = "";
		}
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
}
