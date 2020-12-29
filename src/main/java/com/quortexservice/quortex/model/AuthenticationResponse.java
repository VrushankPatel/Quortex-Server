package com.quortexservice.quortex.model;

public class AuthenticationResponse {

	private final String token;
	private final Integer code;
	private final Boolean status;
	private final Integer userId;
	private final Role role;
	private final UserProgressLevel userProgressLevel;

	public AuthenticationResponse(String token, Integer code, Boolean status, Integer userId, Role role, UserProgressLevel userProgressLevel) {
		this.token = token;
		this.code = code;
		this.status = status;
		this.userId = userId;
		this.role = role;
		this.userProgressLevel = userProgressLevel;
	}

	public String getToken() {
		return token;
	}

	public Integer getCode() {
		return code;
	}

	public Boolean getStatus() {
		return status;
	}

	public Integer getUserId() {
		return userId;
	}

	public Role getRole() {
		return role;
	}

	public UserProgressLevel getUserProgressLevel() {
		return userProgressLevel;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [token=" + token + ", code=" + code + ", status=" + status + ", userId=" + userId
				+ ", role=" + role + "]";
	}	
}
