package com.quortexservice.quortex.model;

public class AuthenticationRequest {
	String username;
	String password;

	public AuthenticationRequest() {
	}
	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AuthenticationRequest [username=" + username + ", password=" + password + "]";
	}

	
}
