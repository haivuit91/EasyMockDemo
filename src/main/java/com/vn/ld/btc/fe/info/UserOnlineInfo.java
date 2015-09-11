package com.vn.ld.btc.fe.info;

import java.io.Serializable;

public class UserOnlineInfo implements Serializable{

	private static final long serialVersionUID = 3609770888985995389L;
	
	private Integer userId;
	private String userName;
	private String loginEmail;
	private String password;
	private Integer googleAuthFlg;
	private String secretCode;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getGoogleAuthFlg() {
		return googleAuthFlg;
	}
	public void setGoogleAuthFlg(Integer googleAuthFlg) {
		this.googleAuthFlg = googleAuthFlg;
	}
	public String getSecretCode() {
		return secretCode;
	}
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
	
}
