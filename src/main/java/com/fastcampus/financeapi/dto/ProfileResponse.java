package com.fastcampus.financeapi.dto;

public class ProfileResponse {
	private String username;
	private String email;
	private String imageBase64;

	// Constructors, getters and setters

	public ProfileResponse(String username, String email, String imageBase64) {
		this.username = username;
		this.email = email;
		this.imageBase64 = imageBase64;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
}
