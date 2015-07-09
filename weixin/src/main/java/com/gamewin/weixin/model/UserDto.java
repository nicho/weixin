package com.gamewin.weixin.model;

public class UserDto {
	String id;
	String userName;
	String manageAddress;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getManageAddress() {
		return manageAddress;
	}
	public void setManageAddress(String manageAddress) {
		this.manageAddress = manageAddress;
	}
	
}
