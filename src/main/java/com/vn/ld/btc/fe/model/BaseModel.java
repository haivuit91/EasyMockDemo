package com.vn.ld.btc.fe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseModel implements Serializable {

	private static final long serialVersionUID = 5379351170550550832L;
	
	private boolean loginStatus;
	private Integer customerId;
	private Integer roleId;
	private String userName;
	private boolean isEnabled2FA;

	
	private List<String> errorMessage;
	private List<String> successMessage;
	
	
	public boolean getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean getIsEnabled2FA() {
		return isEnabled2FA;
	}
	public void setIsEnabled2FA(boolean isEnabled2FA) {
		this.isEnabled2FA = isEnabled2FA;
	}
	public List<String> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}
	public List<String> getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(List<String> successMessage) {
		this.successMessage = successMessage;
	}
	
	/**
	 * Add error message
	 * @param errorMsg
	 */
	public void addErrorContent(String errorMsg) {
		if(!errorMsg.isEmpty()) {
			if(errorMessage == null) {
				errorMessage = new ArrayList<String>();
			}
			errorMessage.add(errorMsg);
		}
	}
	
	/**
	 * Add success message
	 * @param successMsg
	 */
	public void addSuccessContent(String successMsg){
		if(!successMsg.isEmpty()) {
			if(successMessage == null) {
				successMessage = new ArrayList<String>();
			}
			successMessage.add(successMsg);
		}
	}
	
	/**
	 * Clear all error message
	 */
	public void clearErrorMesage() {
		if(errorMessage != null && errorMessage.size() > 0) {
			errorMessage.clear();
		}
	}
	
	/**
	 * Clear all success message
	 */
	public void clearSuccessMessage() {
		if(successMessage != null && successMessage.size() > 0) {
			successMessage.clear();
		}
	}
}
