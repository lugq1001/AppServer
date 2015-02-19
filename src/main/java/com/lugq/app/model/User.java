package com.lugq.app.model;

public class User {

	private String name = "";
	
	private String password = "";
	
	private long lateseLogin = 0;
	
	public void save() {
		
	}
	
	public static User findByUsername(String username) {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLateseLogin() {
		return lateseLogin;
	}

	public void setLateseLogin(long lateseLogin) {
		this.lateseLogin = lateseLogin;
	}
}
