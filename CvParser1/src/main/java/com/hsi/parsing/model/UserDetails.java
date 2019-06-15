package com.hsi.parsing.model;

public class UserDetails {

	private String userName;
	private String password;
	private String emailId;
	private int phoneNo;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "UserDetails [userName=" + userName + ", password=" + password + ", emailId=" + emailId + ", phoneNo="
				+ phoneNo + "]";
	}
	
}
